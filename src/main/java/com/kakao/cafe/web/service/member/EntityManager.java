package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;

public class EntityManager {

    public boolean check(Member findMember, ProfileChangeRequest profileChangeRequest) {
        return isNotSameNickName(findMember, profileChangeRequest) || isNotSameEmail(findMember, profileChangeRequest);
    }

    private boolean isNotSameEmail(Member findMember, ProfileChangeRequest profileChangeRequest) {
        return !findMember.getEmail().equals(profileChangeRequest.getEmail());
    }

    private boolean isNotSameNickName(Member findMember, ProfileChangeRequest profileChangeRequest) {
        return !findMember.getNickName().equals(profileChangeRequest.getNickName());
    }
}
