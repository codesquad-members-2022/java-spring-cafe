package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, V> {

    T save(T t);

    Optional<T> findByName(V name);

    List<T> findAll();
}
