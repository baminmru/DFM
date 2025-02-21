package com.bami.ef.tent.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RequestConfig getRequestConfigSample1() {
        return new RequestConfig().id(1L).requestType(1);
    }

    public static RequestConfig getRequestConfigSample2() {
        return new RequestConfig().id(2L).requestType(2);
    }

    public static RequestConfig getRequestConfigRandomSampleGenerator() {
        return new RequestConfig().id(longCount.incrementAndGet()).requestType(intCount.incrementAndGet());
    }
}
