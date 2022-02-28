package com.kakao.cafe.user.domain;

import java.util.List;

import com.kakao.cafe.common.db.Repository;

public interface UserRepository extends Repository<User, Long> {
	List<User> findAll();
}
