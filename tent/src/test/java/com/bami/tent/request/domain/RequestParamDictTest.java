package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestContentConfigTestSamples.*;
import static com.bami.tent.request.domain.RequestParamDictTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestParamDictTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestParamDict.class);
        RequestParamDict requestParamDict1 = getRequestParamDictSample1();
        RequestParamDict requestParamDict2 = new RequestParamDict();
        assertThat(requestParamDict1).isNotEqualTo(requestParamDict2);

        requestParamDict2.setId(requestParamDict1.getId());
        assertThat(requestParamDict1).isEqualTo(requestParamDict2);

        requestParamDict2 = getRequestParamDictSample2();
        assertThat(requestParamDict1).isNotEqualTo(requestParamDict2);
    }

    @Test
    void requestContentConfigTest() {
        RequestParamDict requestParamDict = getRequestParamDictRandomSampleGenerator();
        RequestContentConfig requestContentConfigBack = getRequestContentConfigRandomSampleGenerator();

        requestParamDict.addRequestContentConfig(requestContentConfigBack);
        assertThat(requestParamDict.getRequestContentConfigs()).containsOnly(requestContentConfigBack);
        assertThat(requestContentConfigBack.getParameter()).isEqualTo(requestParamDict);

        requestParamDict.removeRequestContentConfig(requestContentConfigBack);
        assertThat(requestParamDict.getRequestContentConfigs()).doesNotContain(requestContentConfigBack);
        assertThat(requestContentConfigBack.getParameter()).isNull();

        requestParamDict.requestContentConfigs(new HashSet<>(Set.of(requestContentConfigBack)));
        assertThat(requestParamDict.getRequestContentConfigs()).containsOnly(requestContentConfigBack);
        assertThat(requestContentConfigBack.getParameter()).isEqualTo(requestParamDict);

        requestParamDict.setRequestContentConfigs(new HashSet<>());
        assertThat(requestParamDict.getRequestContentConfigs()).doesNotContain(requestContentConfigBack);
        assertThat(requestContentConfigBack.getParameter()).isNull();
    }
}
