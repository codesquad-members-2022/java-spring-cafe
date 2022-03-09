package com.kakao.cafe;

import com.kakao.cafe.domain.article.ArticleJdbcTemplateRepository;
import com.kakao.cafe.domain.article.ArticleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new ArticleJdbcTemplateRepository(dataSource);
    }
}
