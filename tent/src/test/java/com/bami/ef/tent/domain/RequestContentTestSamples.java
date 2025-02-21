package com.bami.ef.tent.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RequestContent getRequestContentSample1() {
        return new RequestContent().id(1L).requestInfoId(1).paramCode("paramCode1").paramValue("paramValue1");
    }

    public static RequestContent getRequestContentSample2() {
        return new RequestContent().id(2L).requestInfoId(2).paramCode("paramCode2").paramValue("paramValue2");
    }

    public static RequestContent getRequestContentRandomSampleGenerator() {
        return new RequestContent()
            .id(longCount.incrementAndGet())
            .requestInfoId(intCount.incrementAndGet())
            .paramCode(UUID.randomUUID().toString())
            .paramValue(UUID.randomUUID().toString());
    }
}
