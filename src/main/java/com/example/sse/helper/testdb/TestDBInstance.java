package com.example.sse.helper.testdb;

import com.example.sse.board.Board;
import com.example.sse.board.BoardRepository;
import com.example.sse.member.Member;
import com.example.sse.member.MemberRepository;
import com.example.sse.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Profile("test")
@Service
public class TestDBInstance {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BoardRepository boardRepository;

    @Bean
    public void init() {
        populateMembers();
        populateBoards();
    }

    private void populateMembers() {

        Member member2 = Member.builder()
                .name("member2")
                .password("1234")
                .build();

        Member member3 = Member.builder()
                .name("member3")
                .password("1234")
                .build();

        memberRepository.saveAll(List.of(member2, member3));
    }

    void populateBoards() {

        Member writer = memberRepository.save(Member.builder().name("member1").password("1234").build());

        Board board1 = Board.builder()
                .content("board1")
                .url("http://localhost:8080/board/1")
                .title("board1 title")
                .build();

        board1.addMember(writer);

        boardRepository.save(board1);

        populateBoardForNotificationException(writer);

    }

    /** Board without proper url that will cause {@link org.springframework.dao.DataIntegrityViolationException }
     * when persisting notification after an event published by {@link com.example.sse.reply.ReplyService}. */

    private void populateBoardForNotificationException(Member member) {

        Board board2 = Board.builder()
                .content("board2 - with url null ")
                .url(null)
                .title("board1 title")
                .build();

        board2.addMember(member);

        boardRepository.save(board2);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseGet(() -> memberRepository.save(Member.builder().name("member1").password("1234").build()));
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseGet(() -> {
            Member member = memberRepository.findById(id).orElseGet(() -> memberRepository.save(Member.builder().name("member1").password("1234").build()));

                    Board board1 = Board.builder()
                            .content("board1")
                            .url("http://localhost:8080/board/1")
                            .title("board1 title")
                            .build();

                    board1.addMember(member);
                    return boardRepository.save(board1);
                }
        );
    }
}
