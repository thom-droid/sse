package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<Board> post(@RequestBody BoardDto.Post boardPostDto) {

        Board board = Board.of(boardPostDto);
        String url  = "someurl";
        Board savedBoard = boardService.saveBoard(board);

        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

}

