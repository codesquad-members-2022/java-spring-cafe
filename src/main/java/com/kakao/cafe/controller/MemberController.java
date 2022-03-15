package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String create(@ModelAttribute Member member) {
        memberService.join(member);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("users", members);
        return "user/list";
    }

    @GetMapping("/users/{userIndex}")
    public String showProfile(@PathVariable int userIndex, Model model) {
        Member member = memberService.findOne(userIndex);
        model.addAttribute("users",member);
        return "user/profile";
    }

}
