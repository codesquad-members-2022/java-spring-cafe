package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import com.kakao.cafe.web.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("members")
@Controller
public class MemberController {

    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("signup")
    public String join() {
        return "user/form";
    }

    @PostMapping("signup")
    public String join(JoinRequest request) {
        memberService.join(request.toEntity());
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String findMemberById(Model model, @PathVariable int id) {
        Member findMember = memberService.findById(id);
        model.addAttribute("findMember", findMember);
        return "user/profile";
    }

    @PutMapping("{id}")
    public String editProfile(ProfileChangeRequest request) {
        memberService.editProfile(request);
        return "redirect:/members";
    }

    /**
     * 임시 조회를 위해 만든 메서드
     */
    @GetMapping("")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        int memberSize = members.size();
        model.addAttribute("members", members);
        model.addAttribute("memberSize", memberSize);
        return "user/list";
    }
}
