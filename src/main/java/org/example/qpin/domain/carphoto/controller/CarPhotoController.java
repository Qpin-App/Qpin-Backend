package org.example.qpin.domain.carphoto.controller;

import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;
import org.example.qpin.domain.carphoto.service.CarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class CarPhotoController {
    public final CarPhotoService carPhotoService;

    @Autowired
    public CarPhotoController(CarPhotoService carPhotoService) {
        this.carPhotoService =  carPhotoService;
    }

    @PostMapping
    public void saveCarPhoto(@RequestBody CarPhotoRequestDto carPhotoRequestDto) {
        carPhotoService.saveCarPhoto(carPhotoRequestDto);
    }

    @GetMapping("/selectList")
    public List<CarPhotoResponseDto> getCarPhotoList(@RequestParam Long memberId) {
        return carPhotoService.getCarPhotoList(memberId);
    }

    @DeleteMapping("/remove/{photoId}")
    public void deleteCarPhoto(@PathVariable Long photoId) {
        carPhotoService.deleteCarPhoto(photoId);
    }
}
