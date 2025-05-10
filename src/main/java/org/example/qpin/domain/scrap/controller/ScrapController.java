package org.example.qpin.domain.scrap.controller;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.scrap.dto.ScrapResponseDto;
import org.example.qpin.domain.scrap.service.ScrapService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("scrap")
public class ScrapController {

    private final ScrapService scrapService;

    // [Post] 주차장 스크랩 추가
    @PostMapping("/{parkId}/{memberId}")
    public CommonResponse<Map<String, String>> addScrap(@PathVariable("memberId") Long memberId, @PathVariable("parkId") Long parkId) {
        Long newScrap = scrapService.postScrap(memberId, parkId);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(newScrap));
        result.put("message", "즐겨찾기에 추가되었습니다.");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }

    // [Get] 스크랩 리스트
    @GetMapping("/{memberId}")
    public CommonResponse<List<ScrapResponseDto>> getScrapByMember(@PathVariable Long memberId) {
        List<ScrapResponseDto> scrapList = scrapService.getScrapList(memberId);
        return new CommonResponse<>(ResponseCode.SUCCESS, scrapList);
    }

    // [Delete] 스크랩 삭제
    @DeleteMapping("/{scrapId}")
    public CommonResponse<Map<String, String>> deleteScrap(@PathVariable Long scrapId) {
        Long deletedScrap = scrapService.deleteScrap(scrapId);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(deletedScrap));
        result.put("message", "즐겨찾기에서 삭제되었습니다.");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }
}
