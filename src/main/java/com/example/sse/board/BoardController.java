package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import com.example.sse.helper.uri.RequestURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<Board> post(@RequestBody BoardDto.Post boardPostDto, @RequestURL String requestURL) {

        Board board = Board.of(boardPostDto);
        board.setUrl(requestURL);
        Board savedBoard = boardService.saveBoard(board);

        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

}

