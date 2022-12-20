package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import com.example.sse.member.Member;
import com.example.sse.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Board saveBoard(Board board) {

        Member member = memberService.findMemberOrThrow(board.getWriter().getId());
        board.addMember(member);

        return boardRepository.save(board);
    }


}
