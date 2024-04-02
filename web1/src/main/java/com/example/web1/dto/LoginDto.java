package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "이메일을 확인해 주세요") // null 체크는 하지 않음
    @NotEmpty // @NotNull + "" 값 불가
    private String email;

    // @NotBlank // @NotEmpty + "" 값 불가
    @Length(min = 2, max = 6) // min 설정을 지정하면 굳이 @NotBlank 설정을 걸 필요가 없음
    private String name;
}
