package com.example.sse.integration;

import com.example.sse.board.BoardController;
import com.example.sse.board.BoardRepository;
import com.example.sse.board.BoardService;
import com.example.sse.board.dto.BoardDto;
import com.example.sse.member.Member;
import com.example.sse.member.MemberRepository;
import com.example.sse.member.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.gson.Gson;
import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BoardTest {

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void givenBoardDto_whenPost_thenBoardReturn() throws Exception {

        //given
        BoardDto.Post post = BoardDto.Post.of(1L, "title1", "content1");
        String requestBody = gson.toJson(post);
        Member member = Member.builder().id(1L).name("name").build();
        memberRepository.save(member);

        String requestUrl = "http://localhost:8080/board";

        //when
        ResultActions resultActions = mockMvc.perform(post(requestUrl).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpectAll(jsonPath("$.writer.id").value(1L),
                        jsonPath("$.content").value(post.getContent()),
                        jsonPath("$.url").value("http://localhost:8080/board/1"));


    }

}
