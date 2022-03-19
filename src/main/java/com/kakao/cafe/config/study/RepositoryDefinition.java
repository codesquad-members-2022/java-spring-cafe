package com.kakao.cafe.config.study;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Indexed
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RepositoryDefinition {
    Class<?> domainClass();

    Class<?> idClass();
}
