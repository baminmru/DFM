package com.bami.tent.request.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RequestConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestConfig getRequestConfigSample1() {
        return new RequestConfig().id(1L);
    }

    public static RequestConfig getRequestConfigSample2() {
        return new RequestConfig().id(2L);
    }

    public static RequestConfig getRequestConfigRandomSampleGenerator() {
        return new RequestConfig().id(longCount.incrementAndGet());
    }
}
