package com.kakao.cafe.domain.posts;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostsRepository {

    public static Map<Long, Posts> postsMap = new HashMap<>();
    private static long seq = 0L;

    public void save(Posts posts) {
        posts.setId(++seq);
        postsMap.put(posts.getId(), posts);
    }

    public Posts findById(Long id) {
        return postsMap.get(id);
    }

    public List<Posts> findAll() {
        return new ArrayList<>(postsMap.values());
    }
}
