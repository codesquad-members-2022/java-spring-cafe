package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {

    MemberRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new MemberRepository();
    }

    @Test
    @DisplayName("사용자를 저장하면 id를 통해서 찾을 수 있어야 한다.")
    void find_saved_user_by_id() {
        String email = "nohri@naver.com";
        String password = "1234";
        String nickName = "Nohri";
        JoinRequest request = new JoinRequest(email, password, nickName);
        Member insertMember = userRepository.insert(request.toEntity());

        Optional<Member> findMember = userRepository.findById(11);

        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(insertMember.getId());
    }
}