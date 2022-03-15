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
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}