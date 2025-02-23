package com.bami.tent.request.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RequestConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestConfig getRequestConfigSample1() {
        return new RequestConfig().id(1L).version("version1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static RequestConfig getRequestConfigSample2() {
        return new RequestConfig().id(2L).version("version2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static RequestConfig getRequestConfigRandomSampleGenerator() {
        return new RequestConfig()
            .id(longCount.incrementAndGet())
            .version(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
