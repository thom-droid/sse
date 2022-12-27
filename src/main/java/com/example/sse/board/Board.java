package com.example.sse.board;

import com.example.sse.board.dto.BoardDto;
import com.example.sse.member.Member;
import com.example.sse.reply.Reply;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.web.util.UriComponents;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "Board.replies", attributeNodes = @NamedAttributeNode("replies"))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    private String title;
    private String content;
    private String url;

    public void addMember(Member member) {
        this.writer = member;
        if (!member.getBoardList().contains(this)) {
            member.getBoardList().add(this);
        }
    }

    public static Board of(BoardDto.Post boardPostDto) {
        Member member = new Member();
        member.setId(boardPostDto.getWriterId());

        return Board.builder()
                .content(boardPostDto.getContent())
                .title(boardPostDto.getTitle())
                .writer(member)
                .build();
    }

}
