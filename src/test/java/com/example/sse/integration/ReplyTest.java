package com.example.sse.integration;

import com.example.sse.board.Board;
import com.example.sse.board.BoardRepository;
import com.example.sse.helper.testdb.TestDBInstance;
import com.example.sse.member.Member;
import com.example.sse.notification.Notification;
import com.example.sse.notification.NotificationService;
import com.example.sse.reply.Reply;
import com.example.sse.reply.ReplyService;
import com.example.sse.reply.dto.ReplyRequestDto;
import com.google.gson.Gson;
import org.apache.tomcat.util.modeler.NotificationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class ReplyTest {

    @Autowired
    Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDBInstance testDBInstance;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NotificationService notificationService;

    private final String urlSuffix = "http://localhost:8080/reply";


    @Test
    public void givenBoardSetNullUrl_whenReplyPosted_thenNotificationIsNotSent() throws Exception {

        //given
        Board board = testDBInstance.getBoardById(1L);
        board.setUrl(null);
        boardRepository.save(board);
        ReplyRequestDto.Post requestDto = new ReplyRequestDto.Post(1L, "댓글", 1L);
        String request = gson.toJson(requestDto);

        Member member = Member.builder().id(1L).name("test").build();
        Notification notification = Notification.of(Notification.Type.NEW_REPLY, member, null);
        //when
        ResultActions resultActions = mockMvc.perform(post(urlSuffix).contentType(MediaType.APPLICATION_JSON).content(request));

        //then
        assertThrows(DataIntegrityViolationException.class, () -> notificationService.sendNotification(notification));

        resultActions.andExpect(status().is2xxSuccessful()).andDo(print());



    }
}
