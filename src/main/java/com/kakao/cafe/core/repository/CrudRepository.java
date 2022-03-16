package com.kakao.cafe.core.repository;


import com.kakao.cafe.config.study.NoResisteredRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoResisteredRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository {
    T save(T t);

    Optional<T> findById(ID id);

    void update(T t);
}
