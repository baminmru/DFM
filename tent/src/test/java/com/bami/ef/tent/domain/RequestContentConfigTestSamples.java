package com.bami.ef.tent.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestContentConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RequestContentConfig getRequestContentConfigSample1() {
        return new RequestContentConfig().id(1L).requestConfigId(1).parameter(1);
    }

    public static RequestContentConfig getRequestContentConfigSample2() {
        return new RequestContentConfig().id(2L).requestConfigId(2).parameter(2);
    }

    public static RequestContentConfig getRequestContentConfigRandomSampleGenerator() {
        return new RequestContentConfig()
            .id(longCount.incrementAndGet())
            .requestConfigId(intCount.incrementAndGet())
            .parameter(intCount.incrementAndGet());
    }
}
