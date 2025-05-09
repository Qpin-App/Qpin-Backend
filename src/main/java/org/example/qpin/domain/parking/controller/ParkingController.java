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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CommonResponse<Map<String, String>> parking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") Long parkingAreaId, @RequestBody String type) {
        Long postParkingId = parkingService.postParking(memberId, parkingAreaId, type);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(postParkingId));
        result.put("message", "주차 등록이 완료되었습니다.");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }

    // [Delete] 주차 해제
    @DeleteMapping("/parking/{parkingAreaId}/{memberId}")
    @ResponseBody
    public CommonResponse<Map<String, String>> deleteParking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") Long parkingAreaId) {
        Long deleteParkingId = parkingService.deleteParking(memberId, parkingAreaId);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(deleteParkingId));
        result.put("message", "주차 해제가 완료되었습니다.");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }

    // [Get] 현재 주차한 주차장 정보
    @GetMapping("/parking/select/{memberId}")
    public CommonResponse<ParkingInfoResDto> parkingInfo(@PathVariable("memberId") Long memberId) {
        return new CommonResponse<>(ResponseCode.SUCCESS, parkingService.getParkingInfo(memberId));
    }
}