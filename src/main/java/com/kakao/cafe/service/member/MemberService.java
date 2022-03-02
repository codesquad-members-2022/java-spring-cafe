package com.kakao.cafe.service.member;

import com.kakao.cafe.domain.member.Member;
import com.kakao.cafe.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.memberRepository = repository;
    }

    public Optional<Long> join(Member member) {
        validateDuplicateUser(member);

        return memberRepository.save(member);
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<List<Member>> findMembers() {
        return memberRepository.findAll();
    }

    private void validateDuplicateUser(Member member) {
        if (memberRepository.findByUserId(member.getUserId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
