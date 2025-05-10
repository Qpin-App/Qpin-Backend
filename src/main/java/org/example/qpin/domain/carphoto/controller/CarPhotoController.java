package org.example.qpin.domain.carphoto.controller;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;
import org.example.qpin.domain.carphoto.service.CarPhotoService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class CarPhotoController {
    public final CarPhotoService carPhotoService;

    // [Post] 차량 사진 저장
    @PostMapping
    public CommonResponse<Map<String, String>> saveCarPhoto(@RequestBody CarPhotoRequestDto carPhotoRequestDto) {
        Long newPhoto = carPhotoService.saveCarPhoto(carPhotoRequestDto);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(newPhoto));
        result.put("message", "차량 사진 저장이 완료되었습니다");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }

    @GetMapping
    public CommonResponse<List<CarPhotoResponseDto>> getCarPhotoList(@RequestParam Long memberId) {
        List<CarPhotoResponseDto> carPhotoList = carPhotoService.getCarPhotoList(memberId);
        return new CommonResponse<>(ResponseCode.SUCCESS, carPhotoList);
    }

    @DeleteMapping("/{photoId}")
    public CommonResponse<Map<String, String>> deleteCarPhoto(@PathVariable Long photoId) {
        Long deletePhoto = carPhotoService.deleteCarPhoto(photoId);

        Map<String, String> result = new HashMap<>();
        result.put("parkingId", String.valueOf(deletePhoto));
        result.put("message", "차량 사진 삭제가 완료되었습니다");

        return new CommonResponse<>(ResponseCode.SUCCESS, result);
    }
}
