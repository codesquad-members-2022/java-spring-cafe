package com.kakao.cafe.config;

import com.kakao.cafe.core.repository.article.ArticleRepository;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.controller.member.MemberController;
import com.kakao.cafe.web.service.member.EntityManager;
import com.kakao.cafe.web.service.member.MemberService;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    /**
     * 더티 체킹을 담당하는 엔티티 매니저
     */
    @Bean
    public EntityManager entityManager() {
        return new EntityManager();
    }

    /**
     * Controller, Service, Repository 모두
     * 자동 Component 대상이지만 학습을 위해 등록해
     * 두었습니다.
     * */
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
    public ArticleRepository articleRepository() {
        return new ArticleRepository();
    }
}
