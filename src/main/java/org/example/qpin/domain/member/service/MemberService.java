package org.example.qpin.domain.member.service;

import com.google.zxing.WriterException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.insurance.entity.Insurance;
import org.example.qpin.domain.member.dto.request.LoginRequestDto;
import org.example.qpin.domain.member.dto.request.MemberEditRequestDto;
import org.example.qpin.domain.member.dto.request.SignupRequestDto;
import org.example.qpin.domain.member.dto.response.*;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.member.entity.RefreshEntity;
import org.example.qpin.domain.qr.entity.Qr;
import org.example.qpin.global.common.repository.InsuranceRepository;
import org.example.qpin.global.common.repository.MemberRepository;
import org.example.qpin.global.common.repository.QrRepository;
import org.example.qpin.global.common.repository.RefreshRepository;
import org.example.qpin.global.common.response.ResponseCode;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.jwt.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.example.qpin.global.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final QrRepository qrRepository;
    private final MemberRepository memberRepository;
    private final InsuranceRepository insuranceRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    // 회원가입 조건 체크
    public boolean checkSignupCondition(SignupRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        boolean checkCondition = true;
        String pattern = "^[a-zA-Z0-9]*$";
        if (!(Pattern.matches(pattern, password) && password.length() >= 4 && password.length() <= 32)) {
            checkCondition = false;
        }
        else if( !password.equals(passwordCheck) ){
            checkCondition=false;
        }
        return checkCondition;
    }

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto request) {

        // 요구조건 확인
        if(!checkSignupCondition(request)){
            throw new BadRequestException(INVALID_REQUEST);
        };

        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        if(memberRepository.existsByEmail(email)){
            throw new BadRequestException(DUPLICATED_ADMIN_USERID);
        }

//        Member data = new Member(username, bCryptPasswordEncoder.encode(password), "ROLE_ADMIN", email, phoneNumber);

            Member newMember = Member.builder()
                .name(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_ADMIN")
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        memberRepository.save(newMember);
    }

    // 토큰 재발급
    @Transactional
    public TokenResponseDto reissueToken(String refreshToken){
        if (refreshToken == null) {
            throw new BadRequestException(NOT_FOUND_REFRESH_TOKEN);
        }

        // 만료 여부 체크
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new BadRequestException(EXPIRED_PERIOD_REFRESH_TOKEN);
        }

        // refresh token인지 확인
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new BadRequestException(INVALID_REFRESH_TOKEN);
        }

        // refresh token이 DB에 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            throw new BadRequestException(NOT_FOUND_REFRESH_TOKEN);
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // 새로운 jwt 토큰 발급
        String newAccess = jwtUtil.createJwt("access", username, role, 1000L * 60 * 60 * 2);    // 2시간
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 1000L * 60 * 60 * 24 * 14);    // 2주

        // 새로운 refresh token을 DB에 저장
        refreshRepository.deleteByRefresh(refreshToken);
        addRefreshEntity(username, newRefresh, 1000L * 60 * 60 * 24 * 14);

        return new TokenResponseDto(newAccess, newRefresh);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshEntity refreshEntity = new RefreshEntity(username, refresh, date.toString());
        refreshRepository.save(refreshEntity);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    public Insurance findInsuranceById(Long insuranceId) {
        return insuranceRepository.findById(insuranceId).orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = findMemberById(memberId);
        List<Qr> qrList = qrRepository.findAllByMember(member);
        List<MemberQrDto> memberQrDtoList = qrList.stream()
            .map(qr ->new MemberQrDto(
                    qr.getQrImage(),
                    qr.getMemo(),
                    qr.getSafePhoneNumber().getSafePhoneNumber(),
                    qr.getCreatedAt().toLocalDate()
            )).collect(Collectors.toList());

        return MemberInfoResponseDto.builder()
                .name(member.getName())
                .memberQrDtoList(memberQrDtoList)
                .build();
    }

    @Transactional
    public void updateMemberInfo(MemberEditRequestDto request, Long memberId) {
        Member member = findMemberById(memberId);
        Insurance insurance = findInsuranceById(request.getInsuranceId());

        member.setMemberInfo(request.getImageUrl(), request.getName(), request.getEmail(), request.getPhoneNumber(), insurance);
        memberRepository.save(member);
    }

    public MemberEditInfoResponseDto getMemberEditInfo(Long memberId) {

        Member member = findMemberById(memberId);

        return MemberEditInfoResponseDto.builder()
                .imageUrl(member.getImageUrl())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .insuranceId(member.getInsurance().getInsuranceId())
                .build();
    }
}
