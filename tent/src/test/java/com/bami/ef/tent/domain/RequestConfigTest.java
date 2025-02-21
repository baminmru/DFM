package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestContentConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestConfig.class);
        RequestConfig requestConfig1 = getRequestConfigSample1();
        RequestConfig requestConfig2 = new RequestConfig();
        assertThat(requestConfig1).isNotEqualTo(requestConfig2);

        requestConfig2.setId(requestConfig1.getId());
        assertThat(requestConfig1).isEqualTo(requestConfig2);

        requestConfig2 = getRequestConfigSample2();
        assertThat(requestConfig1).isNotEqualTo(requestConfig2);
    }

    @Test
    void requestTypeTest() {
        RequestConfig requestConfig = getRequestConfigRandomSampleGenerator();
        RequestType requestTypeBack = getRequestTypeRandomSampleGenerator();

        requestConfig.addRequestType(requestTypeBack);
        assertThat(requestConfig.getRequestTypes()).containsOnly(requestTypeBack);
        assertThat(requestTypeBack.getRequestConfig()).isEqualTo(requestConfig);

        requestConfig.removeRequestType(requestTypeBack);
        assertThat(requestConfig.getRequestTypes()).doesNotContain(requestTypeBack);
        assertThat(requestTypeBack.getRequestConfig()).isNull();

        requestConfig.requestTypes(new HashSet<>(Set.of(requestTypeBack)));
        assertThat(requestConfig.getRequestTypes()).containsOnly(requestTypeBack);
        assertThat(requestTypeBack.getRequestConfig()).isEqualTo(requestConfig);

        requestConfig.setRequestTypes(new HashSet<>());
        assertThat(requestConfig.getRequestTypes()).doesNotContain(requestTypeBack);
        assertThat(requestTypeBack.getRequestConfig()).isNull();
    }

    @Test
    void requestContentConfigTest() {
        RequestConfig requestConfig = getRequestConfigRandomSampleGenerator();
        RequestContentConfig requestContentConfigBack = getRequestContentConfigRandomSampleGenerator();

        requestConfig.setRequestContentConfig(requestContentConfigBack);
        assertThat(requestConfig.getRequestContentConfig()).isEqualTo(requestContentConfigBack);

        requestConfig.requestContentConfig(null);
        assertThat(requestConfig.getRequestContentConfig()).isNull();
    }
}
