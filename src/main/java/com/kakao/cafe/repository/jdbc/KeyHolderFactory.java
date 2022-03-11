package com.kakao.cafe.repository.jdbc;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {

    KeyHolder newKeyHolder();
}
