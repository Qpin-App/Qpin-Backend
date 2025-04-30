package org.example.qpin.domain.parking.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.parking.dto.ParkingInfoResDto;
import org.example.qpin.domain.parking.dto.ParkingSearchResDto;
import org.example.qpin.domain.parking.entity.Parking;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.example.qpin.global.common.repository.ScrapRepository;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.exception.ExceptionCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    public Parking findParkingById(Long parkingAreaId) {
        return parkingRepository.findById(parkingAreaId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PARKING));
    }

    //latitude: 위도, longitude: 경도

    /*
     * 위도, 경도, 거리, 지역 코드를 입력하면
     * 해당 위치로부터 입력한 거리 이내의 주차장의 정보를 반환함.
     */
    public List<ParkingSearchResDto> findParkingNearby(Double myLatitude, Double myLongitude, Double distance, String regionCode) throws ParseException {
        if (myLatitude == null || myLongitude == null || distance == null || regionCode == null) {
            throw new BadRequestException(ExceptionCode.NOT_FOUND);
        }

        // 공공포털에서 데이터 가져오기
        final int page=1;
        final int perPage=150;
        final String DECODING_KEY="yncOh3M5FtqbW1UwmQmkBKpkkyYqZMj1FddwHcalnFzVCFtnlwkDOhRPFHkhnJPRKYy4scMVfbJMxn954Ym/Eg=="; // 키 암호화 필요
        final String API_URL="https://api.odcloud.kr/api/15050093/v1/uddi:d19c8e21-4445-43fe-b2a6-865dff832e08?"
                +"page="        + page
                +"&perPage="    + perPage
                +"&cond%5B%EC%A7%80%EC%97%AD%EC%BD%94%EB%93%9C%3A%3AEQ%5D=" + regionCode
                +"&serviceKey=" + DECODING_KEY;

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(API_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient=WebClient
                .builder()
                .uriBuilderFactory(factory)
                .baseUrl(API_URL)
                .build();

        String response;
        try {
            response = webClient.get()
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            // 서버 연결 실패
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        JSONArray dataList;

        try {
            jsonObject = (JSONObject) jsonParser.parse(response);
            dataList = (JSONArray) jsonObject.get("data");
        } catch (Exception e) {
            // JSON 파싱 오류 시 500번대 에러 반환
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }

        // 데이터가 없으면 빈 리스트로 반환
        if (dataList == null || dataList.isEmpty()) {
            return new ArrayList<>();
        }

        List<ParkingSearchResDto> parkingSearchResDtoList =new ArrayList<>();
        for (Object obj : dataList){
            JSONObject data = (JSONObject) obj;
            double latitude=Double.parseDouble((String) data.get("위도"));
            double longitude=Double.parseDouble((String) data.get("경도"));
            /*
             * 설정한 거리보다 가까운 주차장만 리스트에 추가
             */

            double parkingDistance = distance(myLatitude, myLongitude, latitude, longitude);
            if(distance>=parkingDistance) {
                ParkingSearchResDto parkingSearchResDto = ParkingSearchResDto.builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .parkId(Long.parseLong((String) data.get("주차장번호")))
                        .name((String) data.get("주차장명"))
                        .address((String) data.get("주차장도로명주소"))
                        .price((String) data.get("요금정보"))
                        .parkingDistance((Double) data.get("현위치와의 거리"))

                        .weekStartTime((String) data.get("평일운영시작시각"))
                        .weekEndTime((String) data.get("평일운영종료시각"))
                        .SaturdayStartTime((String) data.get("토요일운영시작시각"))
                        .SaturdayEndTime((String) data.get("토요일운영종료시각"))
                        .HolidayStartTime((String) data.get("공휴일운영시작시각"))
                        .HolidayEndTime((String) data.get("공휴일운영종료시각"))
                        .build();
                parkingSearchResDtoList.add(parkingSearchResDto);
            }
        }

        return parkingSearchResDtoList;
    }

    // 두 좌표 사이의 거리를 구하는 함수
    // dsitance(첫번째 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    private static double distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 meter
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }

    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }



    // 주차 등록하기
    public void postParking(Long memberId, Long parkingAreaId, String type) {
        Member member = findMemberById(memberId);
        Parking parking = findParkingById(parkingAreaId);

        // 해당 멤버가 이미 주차 중인 상태인지 확인
        if (member.isParking()) {
            throw new BadRequestException(ExceptionCode.DUPLICATED_ADMIN_USERID);
        }

        Parking newParking = Parking.builder()
                .parkingAreaId(parkingAreaId)
                .type(type)
                .build();
        parkingRepository.save(newParking);

        member.setParking(true);
        memberRepository.save(member);
    }

    // 주차 삭제하기
    public void deleteParking(Long memberId, Long parkingAreaId) {
        Member member = findMemberById(memberId);
        Parking parking = findParkingById(parkingAreaId);

        Parking parkingToDelete = parkingRepository.findParkingByParkingAreaIdAndMember(parkingAreaId, memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PARKING));

        parkingRepository.delete(parkingToDelete);

        member.setParking(false);
        memberRepository.save(member);
    }

    // 현재 주차된 주차장 정보 불러오기
    public ParkingInfoResDto getParkingInfo(Long memberId) {
        Member member = findMemberById(memberId);

        // 멤버가 주차 중인 상태인지를 확인
        if (!member.isParking()) {
            throw new BadRequestException(ExceptionCode.NOT_FOUND_PARKING);
        }

        // 멤버가 주차 중인 주차장 정보를 가져옴
        Parking parking = parkingRepository.findParkingByMemberIdAndIsParkingTrue(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PARKING));

        // 주차 시작 시간 및 기타 정보를 가져옴
        LocalDateTime parkingDate = parking.getCreatedAt();
        int parkingTime = (int) Duration.between(parkingDate, LocalDateTime.now()).toMinutes();

        // 주차장 정보 반환
        return ParkingInfoResDto.builder()
                .parkingDate(parkingDate)
                .parkingTime(parkingTime)
                .parkingAreaId(parking.getParkingAreaId())
                .parkingType(parking.getType())
                .build();
    }

}
