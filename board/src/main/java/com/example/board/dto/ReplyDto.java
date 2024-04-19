package com.example.board.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDto {
    private Long rno;

    @NotBlank(message = "댓글 내용을 입력해 주세요")
    private String text;

    @NotBlank(message = "작성자명을 입력해 주세요")
    private String replyer;

    private Long bno;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;
}
