package com.example.sse.member;

import com.example.sse.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(String name, String password, Model model) {

        Member member = Member.of(name, password);

        Member foundMember = memberService.findMember(member);

        model.addAttribute("member", foundMember);

        return "index";

    }
}
