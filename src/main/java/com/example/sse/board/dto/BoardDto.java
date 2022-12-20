package com.example.sse.board.dto;

import lombok.*;

public class BoardDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class Post {

        private Long writerId;
        private String title;
        private String content;

        public static Post of(Long writerId, String title, String content) {
            return Post.builder()
                    .writerId(writerId)
                    .title(title)
                    .content(content)
                    .build();
        }

    }
}
