package com.kakao.cafe.core.repository.member;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    private static final AtomicInteger sequence = new AtomicInteger(10);

    public static int getSequence(){
        return sequence.incrementAndGet();
    }
}
