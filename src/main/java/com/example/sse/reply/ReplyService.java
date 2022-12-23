package com.example.sse.reply;

import com.example.sse.board.Board;
import com.example.sse.board.BoardService;
import com.example.sse.event.NotificationEvent;
import com.example.sse.member.Member;
import com.example.sse.member.MemberService;
import com.example.sse.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ApplicationEventPublisher publisher;

    public Reply saveReply(Reply reply) {

        Member member = memberService.findMemberOrThrow(reply.getWriter().getId());
        Board board = boardService.findBoardOrThrow(reply.getBoard().getId());

        reply.addMember(member);
        reply.addBoard(board);

        Reply savedReply = replyRepository.save(reply);

        Notification notification = Notification.of(Notification.Type.NEW_REPLY, board.getWriter(), board.getUrl());

        publisher.publishEvent(new NotificationEvent<>(notification));

        return savedReply;
    }
}