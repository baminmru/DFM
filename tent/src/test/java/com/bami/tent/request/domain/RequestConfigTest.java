package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestContentConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
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
    void requestContentConfigTest() {
        RequestConfig requestConfig = getRequestConfigRandomSampleGenerator();
        RequestContentConfig requestContentConfigBack = getRequestContentConfigRandomSampleGenerator();

        requestConfig.addRequestContentConfig(requestContentConfigBack);
        assertThat(requestConfig.getRequestContentConfigs()).containsOnly(requestContentConfigBack);
        assertThat(requestContentConfigBack.getRequestConfigId()).isEqualTo(requestConfig);

        requestConfig.removeRequestContentConfig(requestContentConfigBack);
        assertThat(requestConfig.getRequestContentConfigs()).doesNotContain(requestContentConfigBack);
        assertThat(requestContentConfigBack.getRequestConfigId()).isNull();

        requestConfig.requestContentConfigs(new HashSet<>(Set.of(requestContentConfigBack)));
        assertThat(requestConfig.getRequestContentConfigs()).containsOnly(requestContentConfigBack);
        assertThat(requestContentConfigBack.getRequestConfigId()).isEqualTo(requestConfig);

        requestConfig.setRequestContentConfigs(new HashSet<>());
        assertThat(requestConfig.getRequestContentConfigs()).doesNotContain(requestContentConfigBack);
        assertThat(requestContentConfigBack.getRequestConfigId()).isNull();
    }

    @Test
    void requestTypeTest() {
        RequestConfig requestConfig = getRequestConfigRandomSampleGenerator();
        RequestType requestTypeBack = getRequestTypeRandomSampleGenerator();

        requestConfig.setRequestType(requestTypeBack);
        assertThat(requestConfig.getRequestType()).isEqualTo(requestTypeBack);

        requestConfig.requestType(null);
        assertThat(requestConfig.getRequestType()).isNull();
    }
}
