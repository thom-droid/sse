package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import com.example.sse.helper.uri.RequestURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<Board> post(@RequestBody BoardDto.Post boardPostDto, @RequestURL String requestURL) {

        Board board = Board.of(boardPostDto);
        board.setUrl(requestURL);
        Board savedBoard = boardService.saveBoard(board);

        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

    @GetMapping("/boards/{board-id}")
    public String getBoard(@PathVariable("board-id") Long boardId, Model model) {

        Board board = boardService.findBoardOrThrow(boardId);

        model.addAttribute("board", board);

        return "board";
    }
}

