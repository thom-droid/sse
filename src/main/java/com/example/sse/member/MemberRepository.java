package com.example.sse.member;

import org.hibernate.graph.GraphSemantic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByNameAndPassword(@Param("name") String name, @Param("password") String password);

    @EntityGraph(value = "member.replyList", type = EntityGraph.EntityGraphType.FETCH)
//    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.replyList WHERE m.id = :memberId")
    Optional<Member> findById(Long memberId);

}
