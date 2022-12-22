package com.example.sse.reply;

import com.example.sse.board.Board;
import com.example.sse.member.Member;
import com.example.sse.reply.dto.ReplyRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    public static Reply of(ReplyRequestDto.Post requestDto) {
        Board board = new Board();
        board.setId(requestDto.getBoardId());
        Member writer = new Member();
        writer.setId(requestDto.getWriterId());

        return Reply.builder()
                .content(requestDto.getContent())
                .board(board)
                .writer(writer)
                .build();

    }

    public void addMember(Member writer) {
        this.writer = writer;
        if (!writer.getReplyList().contains(this)) {
            writer.getReplyList().add(this);
        }
    }

    public void addBoard(Board board) {
        this.board = board;
        if (!board.getReplies().contains(this)) {
            board.getReplies().add(this);
        }
    }
}
