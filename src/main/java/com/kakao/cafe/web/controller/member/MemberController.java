package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.web.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


}
