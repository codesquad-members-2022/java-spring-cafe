package com.kakao.cafe.core.repository.member;


import java.util.Optional;

public interface CrudRepository<T, E> {
    T persist(T t);

    Optional<T> findById(E e);

    void update(T e);

    void delete(T e);
}
