package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestInfoTestSamples.*;
import static com.bami.tent.request.domain.RequestTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestType.class);
        RequestType requestType1 = getRequestTypeSample1();
        RequestType requestType2 = new RequestType();
        assertThat(requestType1).isNotEqualTo(requestType2);

        requestType2.setId(requestType1.getId());
        assertThat(requestType1).isEqualTo(requestType2);

        requestType2 = getRequestTypeSample2();
        assertThat(requestType1).isNotEqualTo(requestType2);
    }

    @Test
    void requestInfoTest() {
        RequestType requestType = getRequestTypeRandomSampleGenerator();
        RequestInfo requestInfoBack = getRequestInfoRandomSampleGenerator();

        requestType.addRequestInfo(requestInfoBack);
        assertThat(requestType.getRequestInfos()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestType()).isEqualTo(requestType);

        requestType.removeRequestInfo(requestInfoBack);
        assertThat(requestType.getRequestInfos()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestType()).isNull();

        requestType.requestInfos(new HashSet<>(Set.of(requestInfoBack)));
        assertThat(requestType.getRequestInfos()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestType()).isEqualTo(requestType);

        requestType.setRequestInfos(new HashSet<>());
        assertThat(requestType.getRequestInfos()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestType()).isNull();
    }

    @Test
    void requestConfigTest() {
        RequestType requestType = getRequestTypeRandomSampleGenerator();
        RequestConfig requestConfigBack = getRequestConfigRandomSampleGenerator();

        requestType.addRequestConfig(requestConfigBack);
        assertThat(requestType.getRequestConfigs()).containsOnly(requestConfigBack);
        assertThat(requestConfigBack.getRequestType()).isEqualTo(requestType);

        requestType.removeRequestConfig(requestConfigBack);
        assertThat(requestType.getRequestConfigs()).doesNotContain(requestConfigBack);
        assertThat(requestConfigBack.getRequestType()).isNull();

        requestType.requestConfigs(new HashSet<>(Set.of(requestConfigBack)));
        assertThat(requestType.getRequestConfigs()).containsOnly(requestConfigBack);
        assertThat(requestConfigBack.getRequestType()).isEqualTo(requestType);

        requestType.setRequestConfigs(new HashSet<>());
        assertThat(requestType.getRequestConfigs()).doesNotContain(requestConfigBack);
        assertThat(requestConfigBack.getRequestType()).isNull();
    }
}
