package com.example.sse.member;

import com.example.sse.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(String name, String password, HttpSession session) {

        Member member = Member.of(name, password);

        Member foundMember = memberService.findMember(member);

        session.setAttribute("member", foundMember);

        return "redirect:/index";

    }

    @GetMapping("/user/{user-uuid}/notifications")
    public @ResponseBody ResponseEntity<Member> getNotifications(@PathVariable("user-uuid") String memberUUID) {
        return null;
    }
}
