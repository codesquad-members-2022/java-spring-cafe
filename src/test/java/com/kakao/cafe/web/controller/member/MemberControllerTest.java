package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.JoinResponse;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import com.kakao.cafe.web.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.support.BindingAwareModelMap;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Mock mvc 테스트가 같이 딸려오는 것이 아니라
 * Autowired를 따로 주입해서 사용해줘야 한다.
 * WebMVC만 사용하게 되면.
 * WebMvcTest(.classs) -> 정말 필요한 정보만 가져오게 된다.
 */
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    JoinRequest joinRequest;

    @Mock
    MemberController memberController;

    @BeforeEach
    void init() {
//        memberService = new MemberService(memberRepository);
        //mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
        joinRequest = new JoinRequest("abc@naver.com", "password", "Jay");
    }

    /**
     * 매핑부터-> 컨트롤러까지. MockMVC 이용해서 요청 보내고 -> 컨트롤러 계층은 테스트 할 필요 없이
     * ApplicationContext에 띄워져 있지만 코드를 사용하지 않는 것.
     */
    @Test
    @DisplayName("d")
    void 더블패스_회원가입() {
        String email = "jun@gmail.com";
        String nickName = "jun";

        /**
         *  when -> 목 객체만
         * */
        // 무언가 주입을 받았을 때 값을 반환받는 것.
        //when(memberService.join(any())).thenReturn(new Member("Jun@gmail.com", "Jun"));
        when(memberController.join(new JoinRequest("Jun@gmail.com", "1234", "Jun")));
        //when(memberService.join(any())).thenReturn(new Member(email, nickName));
        String expected = "redirect:/";
        assertThat(memberController.join(any())).isEqualTo(expected);
        //assertThat(memberService.join(any()).getEmail()).isEqualTo(email);
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
//            JoinResponse joinRequest = new JoinResponse(new Member(email, nickName));
            //when(memberService.join(any())).thenReturn(new Member(email, nickName));
//            assertThat(joinRequest).isEqualTo(memberService.join(any()));
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