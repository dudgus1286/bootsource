package com.example.club.handle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.club.dto.ClubAuthMemberDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // role 에 따라서 이동경로 지정
        // USER, MANAGER, ADMIN
        ClubAuthMemberDto authMemberDto = (ClubAuthMemberDto) authentication.getPrincipal();

        // role 추출
        List<String> roleName = new ArrayList<>();
        authMemberDto.getAuthorities().forEach(role -> {
            roleName.add(role.getAuthority());
        });

        // ROLE_USER => /club/member, ROLE_MANAGER => /club/manager, ROLE_ADMIN =>
        // /club/admin
        if (roleName.contains("ROLE_ADMIN")) {
            response.sendRedirect("/club/admin");
            return;
        }

        if (roleName.contains("ROLE_MANAGER")) {
            response.sendRedirect("/club/manager");
            return;
        }
        if (roleName.contains("ROLE_USER")) {
            response.sendRedirect("/club/member");
            return;
        }
        response.sendRedirect("/");
    }

}
