package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/join")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/user/join")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setJoinTime(form.getJoinTime());

        memberService.join(member);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("users",members);
        return "user/list";
    }
}
