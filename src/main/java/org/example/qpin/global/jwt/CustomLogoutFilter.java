package org.example.qpin.global.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.qpin.global.common.repository.RefreshRepository;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Process logout request
        if ("/logout".equals(httpRequest.getRequestURI()) && "POST".equals(httpRequest.getMethod())) {
            String refreshToken = httpRequest.getHeader("refresh");

            if (refreshToken == null || refreshToken.isEmpty()) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Expiry check
            try {
                jwtUtil.isExpired(refreshToken);
            } catch (ExpiredJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Check if the token is a refresh token
            String category = jwtUtil.getCategory(refreshToken);
            if (!"refresh".equals(category)) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Check if the token exists in DB
            boolean isExist = refreshRepository.existsByRefresh(refreshToken);
            if (!isExist) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Proceed with logout
            refreshRepository.deleteByRefresh(refreshToken);

            // Respond with OK status
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            httpResponse.setContentType("text/plain; charset=UTF-8");
            try (PrintWriter out = httpResponse.getWriter()) {
                out.print("로그아웃이 완료되었습니다");
            }
            return;
        }
        // If not a logout request, continue the filter chain
        chain.doFilter(request, response);
    }
}
