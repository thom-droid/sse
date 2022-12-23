package com.example.sse.member.dto;

import lombok.*;

public class MemberRequestDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class Post {

        String name;
        String password;
    }

}
