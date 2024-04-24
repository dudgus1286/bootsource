package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.constant.MemberRole;
import com.example.board.dto.MemberAuthDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService, MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id == email == username
        Optional<Member> result = memberRepository.findById(username);
        if (!result.isPresent())
            throw new UsernameNotFoundException("이메일을 확인해 주세요");

        Member member = result.get();

        // entity => dto
        // 시큐리티 로그인 => 회원정보 + 허가와 관련된 정보(사이트 접근 여부)
        MemberDto dto = new MemberDto(member.getEmail(), member.getName(), member.getPassword(),
                member.getMemberRole());

        return new MemberAuthDto(dto);

    }

    @Override
    public void register(MemberDto insertDto) {
        log.info("회원가입 요청 {}", insertDto);
        // 이메일(ID) 중복 검사
        // ID중복 검사 안 하면 이미 있는 계정에 덮어쓰기 될 수도 있음,
        // select 없을 경우 => insert / 존재 시 => update 실행
        // 오류는 아니기 때문에 별도로 중복 검사 기능을 만들어야 함
        try {
            // 중복 이메일
            validateDuplicationMember(insertDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Member member = Member
                .builder()
                .email(insertDto.getEmail())
                .name(insertDto.getName())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);
    }

    private void validateDuplicationMember(String email) {
        Optional<Member> member = memberRepository.findById(email);
        if (member.isPresent())
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }
}
