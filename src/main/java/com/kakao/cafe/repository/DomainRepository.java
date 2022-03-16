package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository<T, V> {

    List<T> findAll();

    Optional<T> save(T obj);

    Optional<T> findById(V primaryKey);

    int deleteById(V primaryKey);
}
