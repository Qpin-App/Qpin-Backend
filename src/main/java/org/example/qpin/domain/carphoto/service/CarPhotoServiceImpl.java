package org.example.qpin.domain.carphoto.service;

import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;
import org.example.qpin.domain.carphoto.entity.CarPhoto;
import org.example.qpin.global.common.repository.CarPhotoRepository;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CarPhotoServiceImpl(CarPhotoRepository carPhotoRepository, MemberRepository memberRepository) {
        this.carPhotoRepository = carPhotoRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void saveCarPhoto(CarPhotoRequestDto carPhotoRequestDto) {
        Member member = memberRepository.findById(carPhotoRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member Not Found for id"));
        CarPhoto carPhoto = CarPhoto.builder()
                .carPhotoUrl(carPhotoRequestDto.getCarPhotoUrl())
                .parkingArea(carPhotoRequestDto.getParkingArea())
                .member(member)
                .build();

        carPhotoRepository.save(carPhoto);
    }

    @Override
    public List<CarPhotoResponseDto> getCarPhotoList(Long memberId) {
        List<CarPhoto> carPhotos = carPhotoRepository.findByMember_MemberId(memberId);

        return carPhotos.stream()
                .map(carPhoto -> CarPhotoResponseDto.builder()
                        .carPhotoId(carPhoto.getCarPhotoId())
                        .carPhotoUrl(carPhoto.getCarPhotoUrl())
                        .parkingArea(carPhoto.getParkingArea())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCarPhoto(Long photoId) {
        carPhotoRepository.deleteById(photoId);
    }
}
