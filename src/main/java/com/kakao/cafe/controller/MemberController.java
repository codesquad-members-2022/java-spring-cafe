package com.kakao.cafe.controller;

import java.lang.reflect.Member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.cafe.domain.MemberForm;

@Controller
public class MemberController {

    @GetMapping("/members/form")
    public String createForm(){
        return "members/form";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }
}
