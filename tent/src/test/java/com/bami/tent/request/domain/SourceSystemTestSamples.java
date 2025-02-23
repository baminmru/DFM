package com.bami.tent.request.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SourceSystemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SourceSystem getSourceSystemSample1() {
        return new SourceSystem().id(1L).code("code1").name("name1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static SourceSystem getSourceSystemSample2() {
        return new SourceSystem().id(2L).code("code2").name("name2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static SourceSystem getSourceSystemRandomSampleGenerator() {
        return new SourceSystem()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
