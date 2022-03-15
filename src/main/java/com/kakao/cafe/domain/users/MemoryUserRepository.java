package com.kakao.cafe.domain.users;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserRepository implements UserRepository {

	List<Users> users = new ArrayList<>();

	public List<Users> findAll() {
		return users;
	}

	public void save(Users user) {
		users.add(user);
	}

	public Users findByUserId(String userId) {
		Users user = null;
		try{
			user = users.get(users.indexOf(userId));
		}catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
}
