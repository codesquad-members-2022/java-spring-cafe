package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberControllerTest {

    JoinRequest joinRequest;

    @Autowired
    MemberController memberController;


    @BeforeEach
    void init() {
        joinRequest = new JoinRequest("abc@naver.com", "password", "Jay");
    }

    @Test
    @DisplayName("joinRequest를 넣으면 회원 가입이 발생하고 적절한 페이지로 이동한다.")
    void 인자테스트_join() {
        String page = memberController.join(joinRequest, new DirectFieldBindingResult("", ""));

        String expected = "redirect:/";

        assertThat(expected).isEqualTo(page);
    }

    @Test
    @DisplayName("ProfileChangeRequest를 넣으면 정보 수정이 발생하고 적절한 페이지로 이동한다.")
    void 인자테스트_edit() {
        String page = memberController.edit(new ProfileChangeRequest(1, "Kim", "abc@naver.com", LocalDateTime.now()));

        String expected = "redirect:/";

        assertThat(expected).isEqualTo(page);
    }

    @Test
    @DisplayName("findById 메서드를 사용하면 회원조회 페이지로 이동된다.")
    void 회원조회_findById() {
        String page = memberController.findMemberById(new BindingAwareModelMap(), 1);

        String expected = "user/profile";

        assertThat(expected).isEqualTo(page);
    }

    @Test
    @DisplayName("findALl 메서드를 사용하면 전체 회원조회 페이지로 이동된다.")
    void 회원조회_findAll() {
        String page = memberController.findAll(new BindingAwareModelMap());

        String expected = "user/list";

        assertThat(expected).isEqualTo(page);
    }
}