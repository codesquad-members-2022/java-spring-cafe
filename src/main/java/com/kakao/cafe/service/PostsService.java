package com.kakao.cafe.service;

import com.kakao.cafe.domain.posts.Post;
import com.kakao.cafe.domain.posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostRepository postRepository;

    @Autowired
    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public void qnaRegister(Post post) {
        postRepository.save(post);
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Post findPost(Long id) {
        return postRepository.findById(id);
    }
}
