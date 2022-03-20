package com.kakao.cafe.domain.users;

import java.util.List;

public interface UserRepository {
	public List<Users> findAll();
	public void save(Users user);
	public Users findByUserId(String userId);
}
