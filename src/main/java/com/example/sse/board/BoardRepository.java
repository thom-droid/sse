package com.example.sse.board;

import com.example.sse.board.dto.BoardProjectionDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(value = "Board.replies", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Board> findById(Long boardId);


    @Query("SELECT new com.example.sse.board.dto.BoardProjectionDto(b.id, w.id, w.name, b.title, b.content) " +
            "FROM Board b " +
            "JOIN b.writer w " +
            "WHERE b.id = :boardId")
    Optional<BoardProjectionDto> findByIdWIthProjection(Long boardId);

}
