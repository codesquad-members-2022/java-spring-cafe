package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleRequestDto;
import com.kakao.cafe.dto.ArticleResponseDto;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Article upload(ArticleRequestDto articleRequestDto) {
        Article article = articleRequestDto.convertToDomain();
        validateWriter(article.getWriter());

        return articleRepository.save(article);
    }

    public List<ArticleResponseDto> findAll() {
        return articleRepository.findAll().stream().map(article -> article.convertToDto()).collect(
            Collectors.toList());
    }

    public ArticleResponseDto findOne(int id) {
        return articleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다.")).convertToDto();
    }

    private void validateWriter(String writer) {
        userRepository.findByUserId(writer).orElseThrow(() -> {
            throw new IllegalStateException("등록되지 않은 사용자입니다.");
        });
    }
}
