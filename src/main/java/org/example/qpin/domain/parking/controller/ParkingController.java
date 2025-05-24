package org.example.qpin.domain.parking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.parking.dto.*;
import org.example.qpin.domain.parking.service.ParkingService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;

    // [Get] 주변 주차장 검색
    @GetMapping("/selectList")
    public CommonResponse<List<ParkingSearchResDto>> findParkingNearby(@Valid ParkingSearchReqDto parkingSearchReqDto) throws ParseException {

        List<ParkingSearchResDto> parkingList = parkingService.findParkingNearby(
                parkingSearchReqDto.getLatitude(),
                parkingSearchReqDto.getLongitude(),
                parkingSearchReqDto.getDistance(),
                parkingSearchReqDto.getRegionCode()
        );

        return new CommonResponse<>(ResponseCode.SUCCESS, parkingList);
    }

    // [Post] 주차 등록
    @PostMapping
    public CommonResponse<ParkingActionResDto> parking(@Valid @RequestBody ParkingRegisterReqDto requestDto) {
        Long postParkingId = parkingService.postParking(requestDto.getMemberId(), requestDto.getParkingAreaId(), requestDto.getType());

        ParkingActionResDto response = ParkingActionResDto.builder()
                .parkingId(postParkingId)
                .message("주차 등록이 완료되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, response);
    }

    // [Delete] 주차 해제
    @DeleteMapping
    public CommonResponse<ParkingActionResDto> deleteParking(@Valid @RequestBody ParkingReleaseReqDto requestDto) {
        Long deleteParkingId = parkingService.deleteParking(requestDto.getMemberId(), requestDto.getParkingAreaId());

        ParkingActionResDto response = ParkingActionResDto.builder()
                .parkingId(deleteParkingId)
                .message("주차 해제가 완료되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, response);
    }

    // [Get] 현재 주차한 주차장 정보
    @GetMapping("/select")
    public CommonResponse<ParkingInfoResDto> parkingInfo(@Valid @RequestBody ParkingInfoReqDto requestDto) {
        return new CommonResponse<>(ResponseCode.SUCCESS, parkingService.getParkingInfo(requestDto.getMemberId()));
    }
}