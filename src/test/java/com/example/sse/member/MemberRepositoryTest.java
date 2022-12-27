package com.example.sse.member;

import com.example.sse.helper.testdb.TestDBInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void givenMemberId_whenFindWithQueryHint_thenDataReturned() {

        //given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElseGet(() -> memberRepository.save(Member.builder().id(1L).name("member1").password("23").build()));

        //then
        assertDoesNotThrow(member::getReplyList);
        assertDoesNotThrow(member::getBoardList);

    }
}