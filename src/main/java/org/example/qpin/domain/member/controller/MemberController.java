package org.example.qpin.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.member.dto.response.TokenResponseDto;
import org.example.qpin.domain.member.service.CustomUserDetails;
import org.example.qpin.domain.member.dto.request.MemberEditRequestDto;
import org.example.qpin.domain.member.dto.request.SignupRequestDto;
import org.example.qpin.domain.member.service.MemberService;
import org.example.qpin.global.common.response.CommonResponse;
import org.example.qpin.global.common.response.ResponseCode;
import org.example.qpin.global.exception.BadRequestException;
import org.example.qpin.global.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    public CommonResponse<?> signup(@RequestBody SignupRequestDto request) {

        memberService.signup(request);
        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

    @PostMapping("/auth/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 요청 API")
    public ResponseEntity<TokenResponseDto> reissue(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return new ResponseEntity<>(new TokenResponseDto(), HttpStatus.BAD_REQUEST);
        }

        try {
            TokenResponseDto tokenResponseDto = memberService.reissueToken(refreshToken);
            return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new TokenResponseDto(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/member")
    @Operation(summary = "메인화면 기본 정보 조회", description = "메인 홈 화면 기본 회원 정보 보여주기 API")
    public CommonResponse<?> memberInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMemberId();
        return new CommonResponse<>(ResponseCode.SUCCESS, memberService.getMemberInfo(memberId));
    }

    @GetMapping("/member/mypage")
    @Operation(summary = "내 프로필 수정 조회", description = "내 프로필 수정 시 회원 정보 보여주기 API")
    public CommonResponse<?> memberEditInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMemberId();
        return new CommonResponse<>(ResponseCode.SUCCESS, memberService.getMemberEditInfo(memberId));
    }

    @PutMapping("/member/edit")
    @Operation(summary = "내 프로필 수정", description = "프로필 수정 API")
    public CommonResponse<?> memberEdit(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberEditRequestDto request) {

        Long memberId = userDetails.getMemberId();
        memberService.updateMemberInfo(request, memberId);

        return new CommonResponse<>(ResponseCode.SUCCESS);
    }

}
