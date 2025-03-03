package org.example.qpin.domain.scrap.service;

import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.scrap.entity.Scrap;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.ScrapRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public void postScrap(Long memberId, String parkId) {
        Member member = findMemberById(memberId);

        Scrap newScrap = Scrap.builder()
                .member(member)
                .parkId(parkId)
                .build();

        scrapRepository.save(newScrap);
        return;
    }
}
