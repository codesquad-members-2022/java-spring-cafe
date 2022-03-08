package com.kakao.cafe.core.repository;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {

    private static final AtomicInteger memberSequence = new AtomicInteger(10);
    private static final AtomicInteger articleSequence = new AtomicInteger(10);

    public static int getMemberSequence(){
        return memberSequence.incrementAndGet();
    }

    public static int getArticleSequence(){
        return articleSequence.incrementAndGet();
    }
}
