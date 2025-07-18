package org.example.qpin.domain.carphoto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.carphoto.dto.*;
import org.example.qpin.domain.carphoto.service.CarPhotoService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class CarPhotoController {
    public final CarPhotoService carPhotoService;

    // [Post] 차량 사진 저장
    @PostMapping
    public CommonResponse<SavePhotoResDto> savePhoto(@RequestBody SavePhotoReqDto reqDto) {
        Long newPhoto = carPhotoService.saveCarPhoto(reqDto);

        SavePhotoResDto response = SavePhotoResDto.builder()
                .carPhotoId(newPhoto)
                .message("차량 사진 저장이 완료되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, response);
    }

    @GetMapping
    public CommonResponse<List<GetPhotoResDto>> getCarPhotoList(@Valid @RequestBody GetPhotoReqDto reqDto) {
        List<GetPhotoResDto> carPhotoList = carPhotoService.getCarPhotoList(reqDto.getMemberId());
        return new CommonResponse<>(ResponseCode.SUCCESS, carPhotoList);
    }

    @DeleteMapping("/{photoId}")
    public CommonResponse<DeletePhotoResDto> deleteCarPhoto(@Valid @RequestBody DeletePhotoReqDto reqDto) {
        Long deletePhoto = carPhotoService.deleteCarPhoto(reqDto.getPhotoId());

        DeletePhotoResDto response = DeletePhotoResDto.builder()
                .photoId(deletePhoto)
                .message("차량 사진 삭제가 완료되었습니다.")
                .build();

        return new CommonResponse<>(ResponseCode.SUCCESS, response);
    }
}
