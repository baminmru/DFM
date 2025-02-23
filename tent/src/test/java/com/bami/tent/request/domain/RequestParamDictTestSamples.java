package com.bami.tent.request.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RequestParamDictTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestParamDict getRequestParamDictSample1() {
        return new RequestParamDict()
            .id(1L)
            .code("code1")
            .name("name1")
            .paramtype("paramtype1")
            .referenceTo("referenceTo1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static RequestParamDict getRequestParamDictSample2() {
        return new RequestParamDict()
            .id(2L)
            .code("code2")
            .name("name2")
            .paramtype("paramtype2")
            .referenceTo("referenceTo2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static RequestParamDict getRequestParamDictRandomSampleGenerator() {
        return new RequestParamDict()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .paramtype(UUID.randomUUID().toString())
            .referenceTo(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
