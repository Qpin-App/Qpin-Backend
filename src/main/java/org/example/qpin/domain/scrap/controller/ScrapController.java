package org.example.qpin.domain.scrap.controller;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.scrap.service.ScrapService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("parking/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    // [Post] 주차장 스크랩 추가
    @PostMapping("/{parkId}/{memberId}")
    @ResponseBody
    public CommonResponse<?> addScrap(@PathVariable("memberId") Long memberId, @PathVariable("parkId") String parkId) {
        scrapService.postScrap(memberId, parkId);
        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

}
