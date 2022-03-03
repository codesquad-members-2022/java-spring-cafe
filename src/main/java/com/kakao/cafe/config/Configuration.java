package com.kakao.cafe.config;

import com.kakao.cafe.web.service.member.EntityManager;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    /**
     * 더티 체킹을 담당하는 엔티티 매니저
     */
    @Bean
    public EntityManager entityManager() {
        EntityManager entityManager = new EntityManager();
        return entityManager;
    }
}
