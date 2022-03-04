package com.kakao.cafe.service;

import com.kakao.cafe.domain.posts.Posts;
import com.kakao.cafe.domain.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }


    public void qnaRegistration(Posts posts) {
        postsRepository.save(posts);
    }

    public List<Posts> qnaList() {
        return postsRepository.findAll();
    }

    public Posts qnaShowContent(Long id) {
        return postsRepository.findById(id);
    }
}
