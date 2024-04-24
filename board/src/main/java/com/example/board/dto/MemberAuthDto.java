package com.example.board.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

// 회원 정보 + 허가와 관련된 정보(사이트 접근 여부)
@Data
public class MemberAuthDto extends User {
    private MemberDto memberDto;

    public MemberAuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberAuthDto(MemberDto memberDto) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getMemberRole())));
        this.memberDto = memberDto;
    }
}
