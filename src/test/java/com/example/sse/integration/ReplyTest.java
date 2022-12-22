package com.example.sse.integration;

import com.example.sse.helper.testdb.TestDBInstance;
import com.example.sse.member.Member;
import com.example.sse.reply.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class ReplyTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDBInstance testDBInstance;

    private final String urlSuffix = "http://localhost:8080/reply";

    @Test
    public void givenReply_whenPostRequested_thenNotificationSent() {

    }
}
