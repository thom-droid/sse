package com.example.sse.member;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberOrThrow(Long memberId) {

        return memberRepository.findById(memberId).orElseThrow();
    }

}
