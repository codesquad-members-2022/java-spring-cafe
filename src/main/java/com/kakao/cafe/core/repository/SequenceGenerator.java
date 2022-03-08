package com.kakao.cafe.core.repository;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {

    private static final int MEMBER_SEQUENCE_START_NUMBER = 1;
    private static final int ARTICLE_SEQUENCE_START_NUMBER = 0;

    private static final AtomicInteger memberSequence = new AtomicInteger(MEMBER_SEQUENCE_START_NUMBER);
    private static final AtomicInteger articleSequence = new AtomicInteger(ARTICLE_SEQUENCE_START_NUMBER);

    public static int getMemberSequence() {
        return memberSequence.incrementAndGet();
    }

    public static int getArticleSequence() {
        return articleSequence.incrementAndGet();
    }
}
