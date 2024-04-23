package com.example.club.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ClubAuthMemberDto extends User implements OAuth2User {
    // DB 에서 인증된 정보를 담을 객체
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // 소셜 로그인에 넘어오는 값을 담는 객체
    private Map<String, Object> attr;

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
        this.password = password;

    }

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}
