package com.bami.tent.request.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RequestContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestContent getRequestContentSample1() {
        return new RequestContent()
            .id(1L)
            .paramCode("paramCode1")
            .paramValue("paramValue1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static RequestContent getRequestContentSample2() {
        return new RequestContent()
            .id(2L)
            .paramCode("paramCode2")
            .paramValue("paramValue2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static RequestContent getRequestContentRandomSampleGenerator() {
        return new RequestContent()
            .id(longCount.incrementAndGet())
            .paramCode(UUID.randomUUID().toString())
            .paramValue(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
