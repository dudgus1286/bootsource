package com.example.board.dto;

import java.time.LocalDateTime;

import com.example.board.entity.Member;

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
public class BoardDto {
    private Long bno;

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요")
    private String content;

    @NotBlank(message = "작성자 아이디(email)을 입력해 주세요")
    private String writerEmail; // 작성자 아이디(email)

    private String writerName; // 작성자 이름
    private Long replyCount; // 해당 게시물의 댓글 수

    private LocalDateTime regDate;

    private LocalDateTime updateDate;
}
