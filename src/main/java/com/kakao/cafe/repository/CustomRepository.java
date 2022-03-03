package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

public interface CustomRepository<T> {
    Optional<T> findByUserId(String id);
    List<T> findAll();
    void save(T t);
}
