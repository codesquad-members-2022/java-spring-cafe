package com.kakao.cafe.domain.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryPostsRepository implements PostsRepository{

    private static final int STORAGE_KEY = 1;

    private final List<Posts> postsStorage = new ArrayList<>();

    @Override
    public void save(Posts posts) {
        posts.setId(generateId());
        postsStorage.add(posts);
    }

    @Override
    public Optional<Posts> findById(int id) {
        return Optional.ofNullable(postsStorage.get(id - STORAGE_KEY));
    }

    @Override
    public List<Posts> findAll() {
        return postsStorage;
    }

    @Override
    public void clear() {
        postsStorage.clear();
    }

    private int generateId() {
        return postsStorage.size()+STORAGE_KEY;
    }
}
