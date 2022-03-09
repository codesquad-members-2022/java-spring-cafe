package com.kakao.cafe.repository;

import java.util.List;

public interface CustomRepository<T> {
    List<T> findAll();
    void save(T t);
}
