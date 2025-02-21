package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestInfoTestSamples.*;
import static com.bami.ef.tent.domain.RequestTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
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

        requestType.setRequestInfo(requestInfoBack);
        assertThat(requestType.getRequestInfo()).isEqualTo(requestInfoBack);

        requestType.requestInfo(null);
        assertThat(requestType.getRequestInfo()).isNull();
    }

    @Test
    void requestConfigTest() {
        RequestType requestType = getRequestTypeRandomSampleGenerator();
        RequestConfig requestConfigBack = getRequestConfigRandomSampleGenerator();

        requestType.setRequestConfig(requestConfigBack);
        assertThat(requestType.getRequestConfig()).isEqualTo(requestConfigBack);

        requestType.requestConfig(null);
        assertThat(requestType.getRequestConfig()).isNull();
    }
}
