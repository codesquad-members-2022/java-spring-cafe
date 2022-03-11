package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.JoinResponse;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import com.kakao.cafe.web.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest
class MemberControllerTest {

    JoinRequest joinRequest;

    @Mock
    BindingResult bindingResult;

    @Mock
    MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        memberController = new MemberController(memberService);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
        joinRequest = new JoinRequest("abc@naver.com", "password", "Jay");
    }

    @Test
    @DisplayName("af")
    void m() throws Exception {

    };

    @Test
    @DisplayName("d")
    void 더블패스_회원가입() {

        String email = "jun@gmail.com";
        String nickName = "jun";
        when(memberService.join(any())).thenReturn(new Member(email, nickName));

        assertThat(memberService.join(any()).getEmail()).isEqualTo(email);
    }

    @Nested
    @DisplayName("회원가입은")
    class JoinTest {
        @Test
        @DisplayName("d")
        void 더블패스_회원가입() throws Exception {
            String email = "jun@gmail.com";
            String nickName = "jun";
            String expectedRedirectUrl = "/members";
            JoinResponse joinRequest = new JoinResponse(new Member(email, nickName));
            //when(memberService.join(any())).thenReturn(new Member(email, nickName));
            assertThat(joinRequest).isEqualTo(memberService.join(any()));
        }
    }

    @Test
    @DisplayName("joinRequest를 넣으면 회원 가입이 발생하고 적절한 페이지로 이동한다.")
    void 인자테스트_join() {
        String page = memberController.join(joinRequest);

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