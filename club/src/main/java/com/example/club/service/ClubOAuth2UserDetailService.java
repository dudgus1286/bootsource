package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClubOAuth2UserDetailService extends DefaultOAuth2UserService {
    private final ClubMemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("===============================");
        // userRequest
        // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@7b7183bb
        log.info("userRequest {}", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        // clientName Google
        log.info("clientName {}", clientName);
        // {id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6ImUxYjkzYzY0MDE0NGI4NGJkMDViZjI5NmQ2NzI2MmI2YmM2MWE0ODciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNTk1NjQyODIzOC1sNDBsbjI1Y25sOTBwY2JzcDN1YmU3OHUwZnMxbzE2Mi5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImF1ZCI6IjE1OTU2NDI4MjM4LWw0MGxuMjVjbmw5MHBjYnNwM3ViZTc4dTBmczFvMTYyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE1NjgyNzU2NjAxNzIwNjU3MjE0IiwiZW1haWwiOiJkdWRndXMxMjg2QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiWHRBNUdTMWl2RENWMWhxOUFFOWQwQSIsImlhdCI6MTcxMzg0Mzc1NSwiZXhwIjoxNzEzODQ3MzU1fQ.Al6k3BvJpymU5Itm0t1pfyaP53qMU2yAK1CHu7SExRnD0Bn2GVkFwrbYX126avP-C9z61rPwAih1HrF82qJ9DIAgQjJ8XET5UOcdJL8jjHSMtM7AGli-DoGXqKIZq__NtW-K7OSPIsJV43ud9fYCvp9-1phUAGzeN-dktqsdRWJrulQUdI0-Xp0lPQi4wKg2OGGQTOzDr42iEPOzie_fA0A6fNGR8Si9nWLsvIdKdFX5J0NFmRKqz7cAfO-VCSBUqeFGvooXRB1tzJGm8Ncj6M-EhOvYnbPZCUFhftws7Vf6pUa9wuf1NUIS4cA9QgIEu-DPvx310wimFW16KOJ6ag}
        log.info(userRequest.getAdditionalParameters());
        log.info("===============================");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("{} : {}", k, v);
            /*
             * sub : 115682756601720657214
             * picture : (구글 계정 프로필 이미지)
             * https://lh3.googleusercontent.com/a-/ALV-UjWUIp9RnzmzJ0hS-I0oYYLE90VQ5u-Db-
             * 8LE1wa0EU-ldsnVXM=s96-c
             * email : dudgus1286@gmail.com
             * email_verified : true
             */
        });

        ClubMember clubMember = saveSocialMember(oAuth2User.getAttribute("email"));
        // entity => dto
        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(
                clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()),
                oAuth2User.getAttributes());
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111")) // 임의 지정
                .fromSocial(true)
                .build();
        clubMember.addMemberRole(ClubMemberRole.USER);
        memberRepository.save(clubMember);
        return clubMember;
    }
}
