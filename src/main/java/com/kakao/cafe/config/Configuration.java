package com.kakao.cafe.config;

import com.kakao.cafe.core.repository.article.ArticleRepository;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.service.article.ArticleService;
import com.kakao.cafe.web.controller.member.MemberController;
import com.kakao.cafe.web.service.member.EntityManager;
import com.kakao.cafe.web.service.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public EntityManager entityManager() {
        return new EntityManager(database());
    }

    /**
     * Controller, Service, Repository 모두
     * 학습을 위해 등록해 두었습니다.
     **/
    @Bean
    public DataGenerator dataGenerator() {
        return new DataGenerator(memberRepository(), articleRepository());
    }

    @Bean
    public MemberController memberController() {
        return new MemberController(memberService());
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepository();
    }

    @Bean
    public ArticleService articleService() {
        return new ArticleService(articleRepository());
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new ArticleRepository(entityManager());
    }

    @Bean
    public Database database() {
        return new Database();
    }

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}