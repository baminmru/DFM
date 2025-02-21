package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestContentConfigTestSamples.*;
import static com.bami.ef.tent.domain.RequestParamDictTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
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

        requestParamDict.setRequestContentConfig(requestContentConfigBack);
        assertThat(requestParamDict.getRequestContentConfig()).isEqualTo(requestContentConfigBack);

        requestParamDict.requestContentConfig(null);
        assertThat(requestParamDict.getRequestContentConfig()).isNull();
    }
}
