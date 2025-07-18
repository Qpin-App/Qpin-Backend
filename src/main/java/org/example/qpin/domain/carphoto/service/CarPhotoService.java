package org.example.qpin.domain.carphoto.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.carphoto.dto.GetPhotoResDto;
import org.example.qpin.domain.carphoto.dto.SavePhotoReqDto;
import org.example.qpin.domain.carphoto.entity.CarPhoto;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.parking.entity.Parking;
import org.example.qpin.global.common.repository.CarPhotoRepository;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;
    private final ParkingRepository parkingRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    public Parking findParkingById(Long parkingId) {
        return parkingRepository.findByParkingAreaId(parkingId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PARKING));
    }

    public CarPhoto findCarPhotoById(Long photoId) {
        return carPhotoRepository.findById(photoId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PHOTO));
    }

    // 사진 저장
    public Long saveCarPhoto(SavePhotoReqDto carPhotoRequestDto) {
        Member member = findMemberById(carPhotoRequestDto.getUserId());
        Parking parking = findParkingById(carPhotoRequestDto.getParkingAreaId());

        CarPhoto newCarPhoto = CarPhoto.builder()
                .photoUrl(carPhotoRequestDto.getCarPhotoUrl())
                .parking(parking)
                .member(member)
                .build();

        carPhotoRepository.save(newCarPhoto);
        return newCarPhoto.getPhotoId();
    }

    // 모든 사진 조회
    public List<GetPhotoResDto> getCarPhotoList(Long memberId) {
        findMemberById(memberId);
        List<CarPhoto> carPhotos = carPhotoRepository.findByMember_MemberId(memberId);

        return carPhotos.stream()
                .map(carPhoto -> GetPhotoResDto.builder()
                        .carPhotoId(carPhoto.getPhotoId())
                        .carPhotoUrl(carPhoto.getPhotoUrl())
                        .parkingAreaId(carPhoto.getParking().getParkingAreaId())
                        .build())
                .collect(Collectors.toList());
    }

    // 사진 삭제
    public Long deleteCarPhoto(Long photoId) {
        CarPhoto deleteCarPhoto = findCarPhotoById(photoId);
        carPhotoRepository.deleteById(deleteCarPhoto.getPhotoId());
        return deleteCarPhoto.getPhotoId();
    }
}