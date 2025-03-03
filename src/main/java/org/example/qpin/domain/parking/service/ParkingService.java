package org.example.qpin.domain.parking.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.parking.dto.ParkingInfoResDto;
import org.example.qpin.domain.parking.dto.ParkingSearchResDto;
import org.example.qpin.domain.parking.entity.Parking;
import org.example.qpin.domain.scrap.entity.Scrap;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.example.qpin.global.common.repository.ScrapRepository;
import org.example.qpin.global.exception.BadRequestException;
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
import java.util.Optional;

import static org.example.qpin.global.exception.ExceptionCode.NOT_FOUND_PARKING;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    //latitude: 위도, longtitude: 경도

    /**
     * 위도와 경도, 거리, 지역 코드를 입력하면
     * 해당 위치로부터 입력한 거리 이내의 주차장의 정보를 반환함.
     */
    public List<ParkingSearchResDto> findParkingNearby(double mylatitude, double mylongtitude, double distance, String regionCode) throws ParseException {
        final int page=1;
        final int perPage=150;
        final String DECODING_KEY="yncOh3M5FtqbW1UwmQmkBKpkkyYqZMj1FddwHcalnFzVCFtnlwkDOhRPFHkhnJPRKYy4scMVfbJMxn954Ym/Eg=="; //키 암호화 필요
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

        String response=webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();


        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject=(JSONObject) jsonParser.parse(response); //예외처리?
        JSONArray dataList=(JSONArray) jsonObject.get("data");

        List<ParkingSearchResDto> parkingSearchResDtoList =new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            JSONObject data=(JSONObject) dataList.get(i); //해당되는 주차장이 없으면 null예외처리 해야함. 500에러 발생.
            double latitude=Double.parseDouble((String) data.get("위도"));
            double longtitude=Double.parseDouble((String) data.get("경도"));
            /**
             * 설정한 거리보다 가까운 주차장만 리스트에 추가
             */

            double parkingDistance = distance(mylatitude, mylongtitude, latitude, longtitude);
            if(distance>=parkingDistance){
                ParkingSearchResDto parkingSearchResDto = new ParkingSearchResDto().builder()
                        .latitude(latitude)
                        .longtitude(longtitude)
                        .parkId(Long.parseLong((String) data.get("주차장관리번호")))
                        .name((String) data.get("주차장명"))
                        .address((String) data.get("주차장도로명주소"))
                        .price((String) data.get("요금정보"))
                        .parkingDistance(parkingDistance)
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
    // dsitance(첫번쨰 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
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


    public void postParking(Long memberId, String parkingAreaId) {
        Member member = findMemberById(memberId);

        Parking newParking = Parking.builder()
                .member(member)
                .parkingAreaId(parkingAreaId)
                .build();

        parkingRepository.save(newParking);
        return;
    }

    public void deleteParking(Long memberId, String parkingAreaId) {
        Member member = findMemberById(memberId);

        Parking parking = parkingRepository.findParkingByParkingAreaIdAndMember(parkingAreaId, member)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PARKING));

        parkingRepository.delete(parking);
        return;
    }

    public ParkingInfoResDto getParkingInfo(Long memberId, String parkingAreaId) {
        Member member = findMemberById(memberId);

        // parkingStatus, parkingDate, parkingTime
        boolean parkingStatus;
        LocalDateTime parkingDate = null;
        int parkingTime = 0;

        Optional<Parking> parking = parkingRepository.findParkingByParkingAreaIdAndMember(parkingAreaId, member);
        if (parking.isPresent()) {
            parkingStatus = true;
            parkingDate = parking.get().getCreatedAt();

            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(parkingDate, now);
            parkingTime = (int) duration.toMinutes(); // 분 단위로 반환하여 전송
        } else {
            parkingStatus = false;
        }

        // scrapStatus
        boolean scrapStatus;
        Optional<Scrap> scrap = scrapRepository.findScrapByParkIdAndMember(parkingAreaId, member);
        if (scrap.isPresent()) {
            scrapStatus = true;
        } else {
            scrapStatus = false;
        }

        return ParkingInfoResDto.builder()
                .parkingStatus(parkingStatus)
                .scrapStatus(scrapStatus)
                .parkingDate(parkingDate)
                .parkingTime(parkingTime)
                .build();
    }

}
