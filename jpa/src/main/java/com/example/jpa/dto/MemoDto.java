package com.example.jpa.dto;

import groovy.transform.ToString;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemoDto {
    private Long mno;

    @NotBlank(message = "메모 내용을 확인해 주세요")
    private String memoText;
}
