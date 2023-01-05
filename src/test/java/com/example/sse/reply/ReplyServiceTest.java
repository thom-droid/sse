package com.example.sse.reply;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ReplyServiceTest {

    @Test
    void givenNoRelatedURL_whenReplyCreated_thenIsReplyRolledBack() {

    }
}