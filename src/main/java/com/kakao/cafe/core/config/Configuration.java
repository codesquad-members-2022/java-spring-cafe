package com.kakao.cafe.core.config;

import com.kakao.cafe.web.service.member.EntityCheckManager;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public EntityCheckManager entityCheckManager(){
        EntityCheckManager entityCheckManager = new EntityCheckManager();
        return entityCheckManager;
    }
}
