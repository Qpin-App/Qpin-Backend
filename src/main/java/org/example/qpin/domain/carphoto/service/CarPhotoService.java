package org.example.qpin.domain.carphoto.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;
import org.example.qpin.domain.carphoto.entity.CarPhoto;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.repository.CarPhotoRepository;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    public CarPhoto findCarPhotoById(Long photoId) {
        return carPhotoRepository.findById(photoId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PHOTO));
    }

    // 사진 저장
    public Long saveCarPhoto(CarPhotoRequestDto carPhotoRequestDto) {
        Member member = findMemberById(carPhotoRequestDto.getUserId());

        CarPhoto newCarPhoto = CarPhoto.builder()
                .carPhotoUrl(carPhotoRequestDto.getCarPhotoUrl())
                .parkingArea(carPhotoRequestDto.getParkingArea())
                .member(member)
                .build();

        carPhotoRepository.save(newCarPhoto);
        return newCarPhoto.getCarPhotoId();
    }

    // 모든 사진 조회
    public List<CarPhotoResponseDto> getCarPhotoList(Long memberId) {
        findMemberById(memberId);
        List<CarPhoto> carPhotos = carPhotoRepository.findByMember_MemberId(memberId);

        return carPhotos.stream()
                .map(carPhoto -> CarPhotoResponseDto.builder()
                        .carPhotoId(carPhoto.getCarPhotoId())
                        .carPhotoUrl(carPhoto.getCarPhotoUrl())
                        .parkingArea(carPhoto.getParkingArea())
                                .build())
                .collect(Collectors.toList());
    }

    // 사진 삭제
    public Long deleteCarPhoto(Long photoId) {
        CarPhoto deleteCarPhoto = findCarPhotoById(photoId);
        carPhotoRepository.deleteById(photoId);
        return deleteCarPhoto.getCarPhotoId();
    }
}