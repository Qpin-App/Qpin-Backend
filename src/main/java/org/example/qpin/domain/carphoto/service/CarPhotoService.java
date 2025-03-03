package org.example.qpin.domain.carphoto.service;

import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;

import java.util.List;

public interface CarPhotoService {
    void saveCarPhoto(CarPhotoRequestDto carPhotoRequestDto);
    List<CarPhotoResponseDto> getCarPhotoList(Long memberId);
    void deleteCarPhoto(Long photoId);
}
