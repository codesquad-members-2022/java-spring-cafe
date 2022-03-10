package com.kakao.cafe.domain.posts;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PostRepository {

    public static Map<Long, Post> postMap = new HashMap<>();
    private static long seq = 0L;

    public void save(Post post) {
        post.setId(++seq);
        post.setLocalDateTime(LocalDateTime.now());
        postMap.put(post.getId(), post);
    }

    public Post findById(Long id) {
        return postMap.get(id);
    }

    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }
}
