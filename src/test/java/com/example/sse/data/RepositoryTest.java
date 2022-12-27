package com.example.sse.data;

import com.example.sse.member.Member;
import com.example.sse.member.MemberRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.LazyInitializationBeanFactoryPostProcessor;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class RepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        memberRepository.save(Member.builder().id(1L).name("member1").password("1234").build());
    }

    @Test
    public void givenMemberId_whenSessionClosed_thenDoesThrowLazyInitializationException() {

        Member member = memberRepository.findById(1L).orElseThrow();

//        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//
//        EntityGraph<?> entityGraph = entityManager.createEntityGraph("member.replyList");
//
//        TypedQuery<Member> q = transactionTemplate.execute(status -> entityManager
//                .createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
//                .setParameter("memberId", 1L)
//                .setHint("javax.persistence.fetchgraph", entityGraph));
//
//        Member member = q.getSingleResult();
//
//        entityManager.close();

        assertDoesNotThrow(member::getReplyList);
        assertThrows(LazyInitializationException.class, member::getBoardList);

    }
}
