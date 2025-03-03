package org.example.qpin.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.qpin.domain.member.service.CustomUserDetails;
import org.example.qpin.domain.member.entity.RefreshEntity;
import org.example.qpin.domain.member.dto.request.LoginRequestDto;
import org.example.qpin.domain.member.dto.response.LoginResponseDto;
import org.example.qpin.global.common.repository.RefreshRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequestDto loginRequestDto;

        try {
            // JSON 요청 본문을 읽어 LoginRequestDto 객체로 변환
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginRequestDto = objectMapper.readValue(messageBody, LoginRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String loginId = loginRequestDto.getLoginId();
        String password = loginRequestDto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        try {
            //유저 정보
            String name = authentication.getName();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            GrantedAuthority auth = iterator.next();
            String role = auth.getAuthority();

            //토큰 생성
            String access = jwtUtil.createJwt("access", name, role, 1000L * 60 * 60 * 2);   // 2시간
            String refresh = jwtUtil.createJwt("refresh", name, role, 1000L * 60 * 60 * 24 * 14);   // 2주

            addRefreshEntity(name, refresh, 1000L * 60 * 60 * 24 * 14);

            LoginResponseDto responseDto = LoginResponseDto.builder()
                    .memberId(((CustomUserDetails) authentication.getPrincipal()).getMemberId())
                    .name(name)
                    .accessToken(access)
                    .refreshToken(refresh)
                    .build();

            // 응답으로 JSON 반환
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
            response.setStatus(HttpStatus.OK.value());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    private void addRefreshEntity(String name, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity(name, refresh, date.toString());

        refreshRepository.save(refreshEntity);
    }
}