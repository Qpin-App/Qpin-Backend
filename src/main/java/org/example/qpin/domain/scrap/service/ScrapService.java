package org.example.qpin.domain.scrap.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.parking.entity.Parking;
import org.example.qpin.domain.scrap.dto.getScrapResDto;
import org.example.qpin.domain.scrap.entity.Scrap;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.example.qpin.global.common.repository.ScrapRepository;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ParkingRepository parkingRepository;
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    public Parking findParkingById(Long parkingId) {
        return parkingRepository.findByParkingAreaId(parkingId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PARKING));
    }

    public Scrap findScrapById(Long scrapId) {
        return scrapRepository.findById(scrapId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_SCRAP));
    }

    public Long postScrap(Long memberId, Long parkId) {
        Member member = findMemberById(memberId);
        Parking parking = findParkingById(parkId);

        Scrap newScrap = Scrap.builder()
                .member(member)
                .parking(parking)
                .build();

        scrapRepository.save(newScrap);
        return newScrap.getScrapId();
    }

    public List<getScrapResDto> getScrapList(Long memberId) {
        findMemberById(memberId);
        List<Scrap> scrapList = scrapRepository.findAllByMember(memberId);

        return scrapList.stream()
                .map(scrap -> getScrapResDto.builder()
                        .scrapId(scrap.getScrapId())
                        .parkingId(scrap.getParking().getParkingAreaId())
                        .build())
                .toList();
    }

    public Long deleteScrap(Long scrapId) {
        Scrap scrap = findScrapById(scrapId);
        scrapRepository.delete(scrap);
        return scrap.getScrapId();
    }
}
