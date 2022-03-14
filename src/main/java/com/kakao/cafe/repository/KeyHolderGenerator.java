package com.kakao.cafe.repository;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class KeyHolderGenerator {

    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public KeyHolder getKeyHolder() {
        return keyHolder;
    }
}
