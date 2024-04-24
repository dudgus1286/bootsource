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

    private String writerEmail; // 작성자 아이디(email)
    private String writerName; // 작성자 이름

    private Long bno;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;
}
