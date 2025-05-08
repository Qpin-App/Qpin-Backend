package org.example.qpin.domain.carphoto.service;

import org.example.qpin.domain.carphoto.dto.CarPhotoRequestDto;
import org.example.qpin.domain.carphoto.dto.CarPhotoResponseDto;
import org.example.qpin.domain.carphoto.entity.CarPhoto;
import org.example.qpin.global.common.repository.CarPhotoRepository;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    public CarPhoto findCarPhotoById(Long photoId) {
        return carPhotoRepository.findById(photoId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PHOTO));
    }

    // 의존성 주입
    @Autowired
    public CarPhotoServiceImpl(CarPhotoRepository carPhotoRepository, MemberRepository memberRepository) {
        this.carPhotoRepository = carPhotoRepository;
        this.memberRepository = memberRepository;
    }

    // 사진 저장
    @Override
    public void saveCarPhoto(CarPhotoRequestDto carPhotoRequestDto) {
        Member member = findMemberById(carPhotoRequestDto.getUserId());

        CarPhoto carPhoto = CarPhoto.builder()
                .carPhotoUrl(carPhotoRequestDto.getCarPhotoUrl())
                .parkingArea(carPhotoRequestDto.getParkingArea())
                .member(member)
                .build();

        carPhotoRepository.save(carPhoto);
    }

    // 모든 사진 조회
    @Override
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
    @Override
    public void deleteCarPhoto(Long photoId) {
        findCarPhotoById(photoId);

        carPhotoRepository.deleteById(photoId);
    }
}