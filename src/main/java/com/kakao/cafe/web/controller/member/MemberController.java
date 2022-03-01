package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String join(@ModelAttribute("member") JoinRequest request) {
        return "user/form";
    }

    @PostMapping("join")
    public String join(@ModelAttribute("member") JoinRequest request, BindingResult bindingResult) {
        System.out.println(memberService.size());
        if (bindingResult.hasErrors()) {
            return "user/list";
        }
        memberService.join(request);
        System.out.println(memberService.size());
        return "redirect:/";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "user/login";
    }

    @GetMapping("{id}")
    public String findMemberById(Model model, @PathVariable(value = "id") Long id) {
        Member findMember = memberService.findById(id).orElseThrow();
        model.addAttribute("findMember", findMember);
        return "user/profile";
    }

    @GetMapping("profile")
    public String getMemberProfile(Model model) {
        return "profilessf";
    }

    /**
     * 임시 조회를 위해 만든 메서드
     */
    @GetMapping("")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        int memberSize = memberService.size();
        model.addAttribute("members", members);
        model.addAttribute("memberSize", memberSize);
        return "user/list";
    }
}
