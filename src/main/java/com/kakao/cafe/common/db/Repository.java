package com.kakao.cafe.common.db;

import java.util.Optional;

public interface Repository<T, ID> {
	T save(T entity);

	Optional<T> findById(ID id);

	void deleteAll();
}
