package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestContentConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestParamDictTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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

        requestContentConfig.addRequestConfigId(requestConfigBack);
        assertThat(requestContentConfig.getRequestConfigIds()).containsOnly(requestConfigBack);
        assertThat(requestConfigBack.getRequestContentConfig()).isEqualTo(requestContentConfig);

        requestContentConfig.removeRequestConfigId(requestConfigBack);
        assertThat(requestContentConfig.getRequestConfigIds()).doesNotContain(requestConfigBack);
        assertThat(requestConfigBack.getRequestContentConfig()).isNull();

        requestContentConfig.requestConfigIds(new HashSet<>(Set.of(requestConfigBack)));
        assertThat(requestContentConfig.getRequestConfigIds()).containsOnly(requestConfigBack);
        assertThat(requestConfigBack.getRequestContentConfig()).isEqualTo(requestContentConfig);

        requestContentConfig.setRequestConfigIds(new HashSet<>());
        assertThat(requestContentConfig.getRequestConfigIds()).doesNotContain(requestConfigBack);
        assertThat(requestConfigBack.getRequestContentConfig()).isNull();
    }

    @Test
    void parameterTest() {
        RequestContentConfig requestContentConfig = getRequestContentConfigRandomSampleGenerator();
        RequestParamDict requestParamDictBack = getRequestParamDictRandomSampleGenerator();

        requestContentConfig.addParameter(requestParamDictBack);
        assertThat(requestContentConfig.getParameters()).containsOnly(requestParamDictBack);
        assertThat(requestParamDictBack.getRequestContentConfig()).isEqualTo(requestContentConfig);

        requestContentConfig.removeParameter(requestParamDictBack);
        assertThat(requestContentConfig.getParameters()).doesNotContain(requestParamDictBack);
        assertThat(requestParamDictBack.getRequestContentConfig()).isNull();

        requestContentConfig.parameters(new HashSet<>(Set.of(requestParamDictBack)));
        assertThat(requestContentConfig.getParameters()).containsOnly(requestParamDictBack);
        assertThat(requestParamDictBack.getRequestContentConfig()).isEqualTo(requestContentConfig);

        requestContentConfig.setParameters(new HashSet<>());
        assertThat(requestContentConfig.getParameters()).doesNotContain(requestParamDictBack);
        assertThat(requestParamDictBack.getRequestContentConfig()).isNull();
    }
}
