package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import com.example.sse.member.Member;
import com.example.sse.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Statement;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Board saveBoard(Board board) {

        Member member = memberService.findMemberOrThrow(board.getWriter().getId());
        board.addMember(member);

        boardRepository.save(board);

        String uri = UriComponentsBuilder.fromUri(URI.create(board.getUrl())).pathSegment(board.getId().toString()).toUriString();
        board.setUrl(uri);

        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Board findBoardOrThrow(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }


}
