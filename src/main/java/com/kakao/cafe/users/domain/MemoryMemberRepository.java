package com.kakao.cafe.users.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class MemoryMemberRepository implements MemberRepository {

    private final List<Member> memberRegistry = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<Long> save(Member member) {
        try {
            validateRequiredField(member);
            validateUserIdUnique(member);

            Member idGeneratedMember = getIdGeneratedMember(member);
            memberRegistry.add(idGeneratedMember);
            return Optional.of(idGeneratedMember.getId());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

    }

    private Member getIdGeneratedMember(Member member) {
        return new Member.Builder()
                .setId(idGenerator.getAndIncrement())
                .setUserId(member.getUserId())
                .setPasswd(member.getPasswd())
                .setName(member.getName())
                .setEmail(member.getEmail())
                .setCreatedDate(member.getCreatedDate())
                .setModifiedDate(member.getModifiedDate())
                .build();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRegistry.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return memberRegistry.stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<List<Member>> findAll() {
        return Optional.of(Collections.unmodifiableList(memberRegistry));
    }

    @Override
    public void deleteAll() {
        memberRegistry.clear();
    }

    private void validateRequiredField(Member member) {
        if (member.getUserId() == null || member.getPasswd() == null ||
                member.getName() == null || member.getEmail() == null ) {
            throw new IllegalArgumentException("필수 정보가 없습니다.");
        }
    }

    private void validateUserIdUnique(Member member) {
        if (findByUserId(member.getUserId()).isPresent()) {
            throw new IllegalArgumentException("userId 는 중복될 수 없습니다.");
        }
    }
}
