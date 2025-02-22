package com.bami.tent.request.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RequestContentConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestContentConfig getRequestContentConfigSample1() {
        return new RequestContentConfig().id(1L);
    }

    public static RequestContentConfig getRequestContentConfigSample2() {
        return new RequestContentConfig().id(2L);
    }

    public static RequestContentConfig getRequestContentConfigRandomSampleGenerator() {
        return new RequestContentConfig().id(longCount.incrementAndGet());
    }
}
