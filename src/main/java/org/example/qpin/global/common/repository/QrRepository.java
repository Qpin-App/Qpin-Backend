package org.example.qpin.global.common.repository;

import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.qr.entity.Qr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrRepository extends JpaRepository<Qr,Long> {
    List<Qr> findAllByMember(Member member);
}
