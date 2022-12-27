package com.example.sse.board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    @EntityGraph(value = "Board.replies", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Board> findById(Long boardId);
}
