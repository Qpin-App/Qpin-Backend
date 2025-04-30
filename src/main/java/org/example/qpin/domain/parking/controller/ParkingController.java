package org.example.qpin.domain.parking.controller;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.parking.dto.ParkingSearchReqDto;
import org.example.qpin.domain.parking.dto.ParkingSearchResDto;
import org.example.qpin.domain.parking.service.ParkingService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    // [Get] 주변 주차장 검색
    @GetMapping("/parking/selectList")
    public ResponseEntity<CommonResponse<List<ParkingSearchResDto>>> findParkingNearby(@RequestBody ParkingSearchReqDto parkingSearchReqDto) throws ParseException {

        Double latitude=parkingSearchReqDto.getLatitude();
        Double longitude=parkingSearchReqDto.getLongitude();
        Double distance=parkingSearchReqDto.getDistance();
        String regionCode=parkingSearchReqDto.getRegionCode();

        CommonResponse<List<ParkingSearchResDto>> response = parkingService.findParkingNearby(latitude, longitude, distance, regionCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // [Post] 주차하기 버튼
    @PostMapping("/parking/{parkingAreaId}/{memberId}")
    @ResponseBody
    public CommonResponse<?> parking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") Long parkingAreaId) {
        parkingService.postParking(memberId, parkingAreaId);
        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

    // [Delete] 주차하기 버튼 해제
    @DeleteMapping("/parking/{parkingAreaId}/{memberId}")
    @ResponseBody
    public CommonResponse<?> deleteParking(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") String parkingAreaId) {
        parkingService.deleteParking(memberId, parkingAreaId);
        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

    // [Get] 간편 주차 주차장 정보
    @GetMapping("/parking/select/{parkingAreaId}/{memberId}")
    public CommonResponse<?> parkingInfo(@PathVariable("memberId") Long memberId, @PathVariable("parkingAreaId") String parkingAreaId) {
        return new CommonResponse<>(ResponseCode.SUCCESS, parkingService.getParkingInfo(memberId, parkingAreaId));
    }
}
