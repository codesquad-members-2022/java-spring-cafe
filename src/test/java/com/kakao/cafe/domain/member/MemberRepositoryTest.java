package com.kakao.cafe.domain.member;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("save 매소드는")
    class SaveTest {

        @Nested
        @DisplayName("회원의 id 가 없고")
        class InsertTest {
            @Test
            @DisplayName("사용자 정보가 정상적으로 들어왔을 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
            void insert_Success() {
                // given
                Member member = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members.get(0).getUserId()).isEqualTo(member.getUserId()),
                                Assertions::fail);
            }

            @Test
            @DisplayName("userId 가 다른 두 사용자를 저장하는 경우, 등록에 성공한다.")
            @Transactional
            @Rollback
            void differentUserId_Success() {
                // given
                Member member1 = new Member.Builder()
                        .setUserId("Jay")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                Member member2 = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member1);
                repository.save(member2);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(2),
                                Assertions::fail);
            }

            @Test
            @DisplayName("userId 가 중복된 두 사용자를 저장하는 경우, 등록에 실패한다.")
            void duplicatedUserId_Success() {
                // given
                Member member1 = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                Member member2 = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member1);
                repository.save(member2);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(1),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 userId 가 빠졌을 경우, 등록에 실패한다.")
            void userIdNull_throwSQLException() {
                // given
                Member member = new Member.Builder()
                        .setUserId(null)
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 Passwd 가 빠졌을 경우, 등록에 실패한다.")
            void passwdNull_throwSQLException() {
                // given
                Member member = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd(null)
                        .setName("김진완")
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 name 이 빠졌을 경우, 등록에 실패한다.")
            void nameNull_throwSQLException() {
                // given
                Member member = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName(null)
                        .setEmail("wlsdhls0423@naver.com")
                        .build();

                // then
                repository.save(member);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(0),
                                Assertions::fail);
            }

            @Test
            @DisplayName("사용자 정보에서 email 이 빠졌을 경우, 등록에 실패한다.")
            void emailNull_throwSQLException() {
                // given
                Member member = new Member.Builder()
                        .setUserId("jwkim")
                        .setPasswd("1234")
                        .setName("김진완")
                        .setEmail(null)
                        .build();

                // then
                repository.save(member);
                repository.findAll()
                        .ifPresentOrElse(
                                members -> assertThat(members).size().isEqualTo(0),
                                Assertions::fail);
            }
        }

    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindByIdTest {

        @Test
        @DisplayName("1명의 User 가 있을때, 저장된 id 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByIdSuccess() {
            // given
            Member member = new Member.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            Long savedId = repository.save(member).orElseThrow();

            // then
            repository.findById(savedId)
                    .ifPresentOrElse(findMember -> {
                        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
                        assertThat(findMember.getName()).isEqualTo(member.getName());
                        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
                    }, Assertions::fail);
        }

        @Test
        @DisplayName("1명의 User 가 있을때, 다른 id 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // given
            Member member = new Member.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            Long savedId = repository.save(member).orElseThrow();

            // then
            repository.findById(savedId + 1)
                    .ifPresent(findMember -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("findByUserId 메소드는")
    class FindByUserIdTest {

        @Test
        @DisplayName("저장된 userId 로 조회하면 정상적으로 결과를 반환한다.")
        void oneUserSaved_findByUserIdSuccess() {
            // given
            String expectedUserId = "jwkim";
            Member member = new Member.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(member).orElseThrow();

            // then
            repository.findByUserId(expectedUserId)
                    .ifPresentOrElse(findMember -> {
                        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
                        assertThat(findMember.getName()).isEqualTo(member.getName());
                        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
                    }, Assertions::fail);
        }

        @Test
        @DisplayName("저장된 userId 와 다른 userId 로 조회하면 조회되지 않는다.")
        void oneUserSaved_findByIdFailed() {
            // given
            String expectedUserId = "jwkim";
            Member member = new Member.Builder()
                    .setUserId(expectedUserId)
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(member).orElseThrow();

            // then
            repository.findByUserId(expectedUserId + "_hello")
                    .ifPresent(findMember -> Assertions.fail());

        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class FindALLTest {

        @Test
        @DisplayName("1명의 User 가 있을때, 길이가 1개인 User 리스트를 반환한다.")
        void oneUserSaved_findAllReturnList_size_1 () {
            // given
            Member member = new Member.Builder()
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(member);
            List<Member> members = repository.findAll().orElseThrow();

            // then
            assertThat(members).size().isEqualTo(1);

            // when
            Member findMember = members.get(0);

            // then
            assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
            assertThat(findMember.getName()).isEqualTo(member.getName());
            assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        }

        @Test
        @DisplayName("2명의 User 가 있을때, 길이가 2개인 User 리스트를 반환한다.")
        void twoUserSaved_findAllReturnList_size_2() {
            // given
            Member member1 = new Member.Builder()
                    .setUserId("jwkim1")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();
            Member member2 = new Member.Builder()
                    .setUserId("jwkim2")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .build();

            // when
            repository.save(member1);
            repository.save(member2);
            List<Member> members = repository.findAll().orElseThrow();

            // then
            assertThat(members).size().isEqualTo(2);

            // when
            Member findMember1 = members.get(0);
            Member findMember2 = members.get(1);

            // then
            assertThat(findMember1.getUserId()).isEqualTo(member1.getUserId());
            assertThat(findMember1.getName()).isEqualTo(member1.getName());
            assertThat(findMember1.getEmail()).isEqualTo(member1.getEmail());
            assertThat(findMember2.getUserId()).isEqualTo(member2.getUserId());
            assertThat(findMember2.getName()).isEqualTo(member2.getName());
            assertThat(findMember2.getEmail()).isEqualTo(member2.getEmail());
        }

        @Test
        @DisplayName("User 가 없을때, 비어있는 리스트를 반환한다.")
        void notSaved_findAllReturnEmpty() {
            // when
            List<Member> members = repository.findAll().orElseThrow();

            // then
            assertThat(members).size().isEqualTo(0);
        }
    }
}
