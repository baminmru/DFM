package com.bami.tent.request.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RequestInfo getRequestInfoSample1() {
        return new RequestInfo().id(1L).contract(1).codeAtSource("codeAtSource1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static RequestInfo getRequestInfoSample2() {
        return new RequestInfo().id(2L).contract(2).codeAtSource("codeAtSource2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static RequestInfo getRequestInfoRandomSampleGenerator() {
        return new RequestInfo()
            .id(longCount.incrementAndGet())
            .contract(intCount.incrementAndGet())
            .codeAtSource(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
