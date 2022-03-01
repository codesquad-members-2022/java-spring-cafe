package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("join")
    public String join(Model model){
        return "users/form";
    }

    @GetMapping("")
    public String findAll(Model model){
        List<Member> members = memberService.findAll();
        int memberSize = memberService.size();
        model.addAttribute("members", members);
        model.addAttribute("memberSize", memberSize);
        return "users/list";
    }
}
