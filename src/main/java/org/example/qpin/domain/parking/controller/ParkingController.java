package org.example.qpin.domain.parking.controller;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.parking.dto.ParkingInfoResDto;
import org.example.qpin.domain.parking.dto.ParkingSearchReqDto;
import org.example.qpin.domain.parking.dto.ParkingSearchResDto;
import org.example.qpin.domain.parking.service.ParkingService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    // [Get] 주변 주차장 검색
    @GetMapping("/parking/selectList")
    public CommonResponse<List<ParkingSearchResDto>> findParkingNearby(@RequestParam ParkingSearchReqDto parkingSearchReqDto) throws ParseException {

        Double latitude=parkingSearchReqDto.getLatitude();
        Double longitude=parkingSearchReqDto.getLongitude();
        Double distance=parkingSearchReqDto.getDistance();
        String regionCode=parkingSearchReqDto.getRegionCode();

        List<ParkingSearchResDto> parkingList = parkingService.findParkingNearby(latitude, longitude, distance, regionCode);
        return new CommonResponse<>(ResponseCode.SUCCESS, parkingList);
    }

    // [Post] 주차 등록
    @PostMapping("/parking/{parkingAreaId}/{memberId}")
    public CommonResponse<String> parking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") Long parkingAreaId, @RequestBody String type) {
        parkingService.postParking(memberId, parkingAreaId, type);
        return new CommonResponse<>(ResponseCode.SUCCESS, "주차가 완료되었습니다.");
    }

    // [Delete] 주차 해제
    @DeleteMapping("/parking/{parkingAreaId}/{memberId}")
    @ResponseBody
    public CommonResponse<String> deleteParking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") Long parkingAreaId) {
        parkingService.deleteParking(memberId, parkingAreaId);
        return new CommonResponse<>(ResponseCode.SUCCESS, "주차 해제가 완료되었습니다.");
    }

    // [Get] 현재 주차한 주차장 정보
    @GetMapping("/parking/select/{memberId}")
    public CommonResponse<ParkingInfoResDto> parkingInfo(@PathVariable("memberId") Long memberId) {
        return new CommonResponse<>(ResponseCode.SUCCESS, parkingService.getParkingInfo(memberId));
    }
}
