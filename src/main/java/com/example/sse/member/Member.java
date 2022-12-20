package com.example.sse.member;

import com.example.sse.board.Board;
import com.example.sse.notification.Notification;
import com.example.sse.reply.Reply;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Reply> replyList = new ArrayList<>();


}
