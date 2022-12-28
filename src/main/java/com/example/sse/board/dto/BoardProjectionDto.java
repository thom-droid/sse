package com.example.sse.board.dto;

import com.example.sse.reply.dto.ReplyProjectionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardProjectionDto {

    private Long id;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;

}
