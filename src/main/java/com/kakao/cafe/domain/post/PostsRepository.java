package com.kakao.cafe.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostsRepository {

    void save(Posts posts);
    Optional<Posts> findById(int id);
    List<Posts> findAll();
    void clear();
}
