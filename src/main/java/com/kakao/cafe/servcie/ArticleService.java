package com.kakao.cafe.servcie;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void qnaWrite(Article article){
        articleRepository.save(article);
    }

    public List<Article> findAllArticle(){
        return articleRepository.findAll();
    }

    public Article findById(int id){
        return articleRepository.findById(id);
    }
}
