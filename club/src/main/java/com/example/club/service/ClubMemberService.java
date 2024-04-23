package com.example.club.service;

import com.example.club.dto.ClubMemberDto;

public interface ClubMemberService {
    // 회원가입
    String register(ClubMemberDto member);

    // 회원 탈퇴
    // 회원정보 수정
}
