package com.kakao.cafe.core.repository.member;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceGenerator {
    private static final AtomicLong sequence = new AtomicLong(10);

    public static long getSequence(){
        return sequence.incrementAndGet();
    }
}
