package com.example.sse.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReplyRequestDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long writerId;
        private String content;
        private Long boardId;
    }

}
