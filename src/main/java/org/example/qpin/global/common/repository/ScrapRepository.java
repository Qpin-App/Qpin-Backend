package org.example.qpin.global.common.repository;

import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByParking_ParkingAreaIdAndMember_MemberId(Long parkingId, Long memberId);
    List<Scrap> findAllByMember(Member member);
}
