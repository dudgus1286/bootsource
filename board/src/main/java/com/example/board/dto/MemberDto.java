package com.example.board.dto;

import com.example.board.constant.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    @NotBlank(message = "이메일을 확인해 주세요")
    private String email;

    @NotBlank(message = "이름을 확인해 주세요")
    private String name;

    @NotBlank(message = "비밀번호를 확인해 주세요")
    private String password;

    private MemberRole memberRole;
}
