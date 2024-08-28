package com.sparta.jpacalendarapp.filter;

import com.sparta.jpacalendarapp.jwt.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public AuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(jwtUtil.AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(jwtUtil.BEARER_PREFIX)) {
            String token = authHeader.substring(jwtUtil.BEARER_PREFIX.length());
            try {
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getUserEmailFromToken(token);
                    String role = jwtUtil.getUserRoleFromToken(token);
                    // 요청 메서드가 PUT 또는 DELETE일 때만 ADMIN 권한 확인
                    if (("PUT".equalsIgnoreCase(request.getMethod()) || "DELETE".equalsIgnoreCase(request.getMethod())) && !"ADMIN".equals(role)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Access denied. ADMIN privileges are required.");
                        return;
                    }
                    request.setAttribute("AuthenticatedUser", email);
                    request.setAttribute("UserRole", role);
                } else {
                    throw new JwtException("Invalid token or already expired token.");
                }
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT Authentication failed: " + e.getMessage());
                return;
            }
        } else if (!isExcludedPath(request.getRequestURI())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("NOT FOUND TOKEN");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(String path) {
        return "/api/users/logins".equalsIgnoreCase(path) || "/api/users".equalsIgnoreCase(path);
    }
}
