package com.kakao.cafe.web.controller;

import com.kakao.cafe.core.repository.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;

    public HomeController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("")
    public String home(Model model) {
        return "user/list";
    }
}
