package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestContentConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestParamDictTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestContentConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestContentConfig.class);
        RequestContentConfig requestContentConfig1 = getRequestContentConfigSample1();
        RequestContentConfig requestContentConfig2 = new RequestContentConfig();
        assertThat(requestContentConfig1).isNotEqualTo(requestContentConfig2);

        requestContentConfig2.setId(requestContentConfig1.getId());
        assertThat(requestContentConfig1).isEqualTo(requestContentConfig2);

        requestContentConfig2 = getRequestContentConfigSample2();
        assertThat(requestContentConfig1).isNotEqualTo(requestContentConfig2);
    }

    @Test
    void requestConfigIdTest() {
        RequestContentConfig requestContentConfig = getRequestContentConfigRandomSampleGenerator();
        RequestConfig requestConfigBack = getRequestConfigRandomSampleGenerator();

        requestContentConfig.setRequestConfigId(requestConfigBack);
        assertThat(requestContentConfig.getRequestConfigId()).isEqualTo(requestConfigBack);

        requestContentConfig.requestConfigId(null);
        assertThat(requestContentConfig.getRequestConfigId()).isNull();
    }

    @Test
    void parameterTest() {
        RequestContentConfig requestContentConfig = getRequestContentConfigRandomSampleGenerator();
        RequestParamDict requestParamDictBack = getRequestParamDictRandomSampleGenerator();

        requestContentConfig.setParameter(requestParamDictBack);
        assertThat(requestContentConfig.getParameter()).isEqualTo(requestParamDictBack);

        requestContentConfig.parameter(null);
        assertThat(requestContentConfig.getParameter()).isNull();
    }
}
