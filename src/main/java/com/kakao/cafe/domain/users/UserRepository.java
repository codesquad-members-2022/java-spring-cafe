package com.kakao.cafe.domain.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	public List<Users> findAll();
	public void save(Users user);
	public Users findByUserId = null;
}
