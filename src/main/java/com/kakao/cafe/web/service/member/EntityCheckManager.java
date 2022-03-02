package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.web.controller.member.dto.ProfileFormRequest;

public class EntityCheckManager {
    public boolean check(Member findMember, ProfileFormRequest profileFormRequest) {
        return !findMember.getNickName().equals(profileFormRequest.getNickName())
                || !findMember.getEmail().equals(profileFormRequest.getEmail());
    }
}
