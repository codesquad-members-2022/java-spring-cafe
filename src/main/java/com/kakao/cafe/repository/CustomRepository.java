package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

public interface CustomRepository<T> {
    List<T> findAll();

    void save(T t);

    Optional<T> findById(Long id);
}
