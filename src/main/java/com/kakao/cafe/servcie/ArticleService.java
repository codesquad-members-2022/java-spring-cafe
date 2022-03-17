package com.kakao.cafe.servcie;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.JdbcTemplateArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final JdbcTemplateArticleRepository jdbcTemplateArticleRepository;

    public ArticleService(JdbcTemplateArticleRepository jdbcTemplateArticleRepository) {
        this.jdbcTemplateArticleRepository = jdbcTemplateArticleRepository;
    }

    public void qnaWrite(Article article){
        jdbcTemplateArticleRepository.save(article);
    }

    public List<Article> findAllArticle(){
        return jdbcTemplateArticleRepository.findAll();
    }

    public Optional<Article> findById(int id){
        return jdbcTemplateArticleRepository.findById(id);
    }
}
