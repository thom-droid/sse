package com.example.sse.member;

import com.example.sse.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody MemberRequestDto.Post requestDto, HttpSession httpSession) {

        Member member = Member.of(requestDto.getName(), requestDto.getPassword());

        Member foundMember = memberService.findMember(member);

        httpSession.setAttribute("member", foundMember);

        return "redirect:/index";

    }
}
