package com.example.sse.member;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberOrThrow(Long memberId) {

        return memberRepository.findById(memberId).orElseThrow();
    }

    public Member findMember(Member member) {
        return memberRepository.findMemberByNameAndPassword(member.getName(), member.getPassword()).orElseThrow();
    }

}
