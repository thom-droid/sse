package com.example.sse.reply;

import com.example.sse.reply.dto.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @RequestMapping("/reply")
    public ResponseEntity<Reply> post(@RequestBody ReplyRequestDto.Post requestDto) {

        Reply reply = Reply.of(requestDto);

        Reply savedReply = replyService.saveReply(reply);

        return new ResponseEntity<>(savedReply, HttpStatus.CREATED);
    }
}
