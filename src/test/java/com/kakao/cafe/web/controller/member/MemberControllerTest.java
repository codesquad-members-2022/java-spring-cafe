package com.kakao.cafe.web.controller.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeResponse;
import com.kakao.cafe.web.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 단위 테스트")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    MemberController memberController;

    BindingResult bindingResult;

    @MockBean
    private MemberService memberService;

    private Member member;
    private Member changedMember;
    private ProfileChangeRequest request;
    private ProfileChangeResponse response;

    @BeforeEach
    void init() {
        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        member = getMember();
        request = getProfileChangeRequest();
        response = getProfileChangeResponse();
        changedMember = getChangedMember();
    }

    @Test
    @DisplayName("/members로 접속하면 user/list view를 반환한다.")
    void user_list_view_반환_get() throws Exception {
        List<Member> members = List.of();
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist())
                .andExpect(model().attribute("members", members))
                .andExpect(model().attribute("memberSize", members.size()))
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("/members/signup으로 접속하면 user/form view를 반환한다.")
    void user_join_view_반환_get() throws Exception {
        mockMvc.perform(get("/members/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/form"));
    }

    @Test
    @DisplayName("/members/signup으로 데이터를 전송하면 redirect:/를 반환한다.")
    void 회원가입_시_redirect_반환() throws Exception {
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("nickName=jun&password=1234&email=jun@gmail.com")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }

    @Test
    @DisplayName("request를 요청하면 정보가 변환된 멤버가 나오게 된다.")
    void 회원수정() {
        when(memberService.editProfile(request)).thenReturn(changedMember);
    }

    @Test
    @DisplayName("")
    void 회원_상세조회() throws Exception {
        given(memberService.findById(anyInt())).willReturn(member);

        mockMvc.perform(get("/members/" + anyInt()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("findMember"))
                .andExpect(model().attribute("findMember", member))
                .andExpect(view().name("user/profile"));
    }

    @Test
    void findAll() {
    }

    private Member getMember() {
        return new Member.Builder()
                .id(getFixedId())
                .nickName(getNickName())
                .email(getEmail())
                .password(getPassword())
                .build();
    }

    private int getFixedId() {
        return 1;
    }

    private String getNickName() {
        return "jun";
    }

    private String getEmail() {
        return "jun@gmail.com";
    }

    private String getPassword() {
        return "1234";
    }

    private ProfileChangeRequest getProfileChangeRequest() {
        return new ProfileChangeRequest(getFixedId(), getNickName(), getEmail());
    }

    private ProfileChangeResponse getProfileChangeResponse() {
        return new ProfileChangeResponse(getProfileChangeRequest());
    }

    private Member getChangedMember() {
        return new Member.Builder()
                .id(getFixedId())
                .nickName("kim")
                .email("kim@gmail.com")
                .password(getPassword())
                .build();
    }
}