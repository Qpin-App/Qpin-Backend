package org.example.qpin.domain.scrap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.scrap.dto.*;
import org.example.qpin.domain.scrap.service.ScrapService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("scrap")
@Validated
public class ScrapController {

    private final ScrapService scrapService;

    // [Post] 주차장 스크랩 추가
    @PostMapping
    public CommonResponse<addScrapResDto> addScrap(@Valid @RequestBody addScrapReqDto requestDto) {
        Long newScrapId = scrapService.postScrap(requestDto.getMemberId(), requestDto.getParkId());

        addScrapResDto responseDto = addScrapResDto.builder()
                .scrapId(newScrapId)
                .message("즐겨찾기에 추가되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, responseDto);
    }

    // [Get] 스크랩 리스트
    @GetMapping("/{memberId}")
    public CommonResponse<List<getScrapResDto>> getScrapByMember(@Valid @RequestBody getScrapReqDto requestDto)  {
        List<getScrapResDto> scrapList = scrapService.getScrapList(requestDto.getMemberId());
        return new CommonResponse<>(ResponseCode.SUCCESS, scrapList);
    }

    // [Delete] 스크랩 삭제
    @DeleteMapping("/{scrapId}")
    public CommonResponse<deleteScrapResDto> deleteScrap(@Valid @RequestBody deleteScrapReqDto requestDto) {
        Long deletedScrap = scrapService.deleteScrap(requestDto.getScrapId());

        deleteScrapResDto responseDto = deleteScrapResDto.builder()
                .parkingId(deletedScrap)
                .message("즐겨찾기에서 삭제되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, responseDto);
    }
}
