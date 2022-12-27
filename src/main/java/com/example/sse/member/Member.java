package com.example.sse.member;

import com.example.sse.board.Board;
import com.example.sse.notification.Notification;
import com.example.sse.reply.Reply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NamedEntityGraph(name="member.replyList", attributeNodes = @NamedAttributeNode("replyList"))
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

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private String password;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
    private List<Reply> replyList = new ArrayList<>();


    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Member of(String name, String password) {
        return new Member(name, password);
    }
}
