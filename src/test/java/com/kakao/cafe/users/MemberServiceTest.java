package com.kakao.cafe.users;

import com.kakao.cafe.users.domain.Member;
import com.kakao.cafe.users.domain.MemberRepository;
import com.kakao.cafe.users.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Nested
    @DisplayName("join 메소드는")
    class JoinTest{

        @Test
        @DisplayName("userId 가 중복되지 않으면, 회원가입에 성공한다.")
        void nonDuplicatedUserId_joinSuccess() {
            // arrange
            Member member = new Member.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(memberRepository.save(member)).thenReturn(Optional.of(member.getId()));
            when(memberRepository.findByUserId(any())).thenReturn(Optional.empty()); // 중복된 회원이 없음

            // act
            Long id = memberService.join(member).orElseThrow();

            // assert
            assertThat(id).isEqualTo(member.getId());
        }

        @Test
        @DisplayName("userId 가 중복되면, 회원가입에 실패한다.")
        void duplicatedUserId_joinSuccess() {
            // arrange
            Member member = new Member.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(memberRepository.findByUserId(any())).thenReturn(Optional.of(member)); // 중복된 회원 있음

            // assert
            assertThatThrownBy(() -> memberService.join(member))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("이미 존재하는 회원입니다.");
        }
    }

    @Nested
    @DisplayName("findOne 메소드는")
    class FindOneTest{

        @Test
        @DisplayName("저장된 Member 의 id 를 조회하면, Member 를 반환한다.")
        void savedMember_findSuccess() {
            // arrange
            Long savedMemberId = 1L;
            Member member = new Member.Builder()
                    .setId(savedMemberId)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(memberRepository.findById(any())).thenReturn(Optional.of(member));

            // act
            Member findMember = memberService.findOne(savedMemberId).orElseThrow();

            // assert
            assertThatIsEqualToAllMemberField(findMember, member);
        }

        @Test
        @DisplayName("저장되지 않은 Member 의 id 를 조회하면, Optional.empty() 를 반환한다.")
        void duplicatedUserId_joinSuccess() {
            // arrange
            Long unsavedMemberId = 1L;
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty()); // 중복된 회원 있음

            // assert
            memberService.findOne(unsavedMemberId)
                    .ifPresent(findMember -> fail());
        }
    }

    @Nested
    @DisplayName("findMembers")
    class FindMembersTest{
        @Test
        @DisplayName("Member 가 저장되어 있으면, List<Member> 를 반환한다.")
        void memberExist_findMembersReturnsList() {
            // arrange
            Member member = new Member.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
            when(memberRepository.findAll()).thenReturn(Optional.of(List.of(member)));

            // assert
            memberService.findMembers()
                    .ifPresentOrElse(
                            members -> {
                                assertThat(members).size().isEqualTo(1);
                                Member findMember = members.get(0);
                                assertThatIsEqualToAllMemberField(findMember, member);
                            },
                            Assertions::fail
                    );
        }

        @Test
        @DisplayName("Member 가 저장되어 있으면, List<Member> 를 반환한다.")
        void memberNotExist_findMembersReturnsEmptyList() {
            // arrange
            when(memberRepository.findAll()).thenReturn(Optional.of(Collections.emptyList())); // 중복된 회원 있음

            // act
            List<Member> members = memberService.findMembers().orElseThrow();

            // assert
            assertThat(members).size().isEqualTo(0);
        }
    }

    private void assertThatIsEqualToAllMemberField(Member actual, Member expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getPasswd()).isEqualTo(expected.getPasswd());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getCreatedDate()).isEqualTo(expected.getCreatedDate());
        assertThat(actual.getModifiedDate()).isEqualTo(expected.getModifiedDate());
    }

}
