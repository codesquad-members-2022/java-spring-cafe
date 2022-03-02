package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileFormRequest;
import com.kakao.cafe.web.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String join() {
        return "user/form";
    }

    @PostMapping("join")
    public String join(JoinRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/list";
        }
        memberService.join(request);
        return "redirect:/";
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @GetMapping("{id}")
    public String findMemberById(Model model, @PathVariable Long id) {
        Member findMember = memberService.findById(id);
        model.addAttribute("findMember", findMember);
        return "user/profile";
    }

    @GetMapping("{id}/edit")
    public String getDetailMember(Model model, @PathVariable Long id, ProfileFormRequest request) {
        Member findMember = memberService.findById(id);
        request.setId(findMember.getId());
        request.setEmail(findMember.getEmail());
        request.setNickName(findMember.getNickName());
        request.setCreateAt(findMember.getCreateAt());
        model.addAttribute("request", request);
        return "user/profileedit";
    }

    @PostMapping("{id}/edit")
    public String edit(ProfileFormRequest request) {
        memberService.edit(request);
        return "redirect:/";
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
