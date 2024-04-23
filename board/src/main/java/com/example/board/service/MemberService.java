package com.example.board.service;

import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;

public interface MemberService {
    void register(MemberDto dto);
}
