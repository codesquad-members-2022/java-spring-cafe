package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.service.MemberService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CafeController {

    Logger logger = LoggerFactory.getLogger(CafeController.class);
    MemberService memberService = new MemberService();

    @PostMapping("/users")
    public String create(Member form) {
        Member member = new Member();
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

        logger.info("회원가입 호출");
        System.out.println("form.getUserId = " + form.getUserId());
        System.out.println("form.getName = " + form.getName());
        System.out.println("form.getEmail = " + form.getEmail());
        System.out.println("form.getPassword = " + form.getPassword());
        memberService.join(member);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        return "users";
    }

}
