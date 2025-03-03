package org.example.qpin.domain.member.service;

import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // DB에서 이메일로 사용자 조회
        Optional<Member> userDataOptional = memberRepository.findByEmail(email);

        Member userData = userDataOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        // UserDetails 객체 반환
        return new CustomUserDetails(userData);
    }
}
