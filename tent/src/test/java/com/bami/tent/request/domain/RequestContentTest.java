package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestContentTestSamples.*;
import static com.bami.tent.request.domain.RequestInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestContent.class);
        RequestContent requestContent1 = getRequestContentSample1();
        RequestContent requestContent2 = new RequestContent();
        assertThat(requestContent1).isNotEqualTo(requestContent2);

        requestContent2.setId(requestContent1.getId());
        assertThat(requestContent1).isEqualTo(requestContent2);

        requestContent2 = getRequestContentSample2();
        assertThat(requestContent1).isNotEqualTo(requestContent2);
    }

    @Test
    void requestInfoIdTest() {
        RequestContent requestContent = getRequestContentRandomSampleGenerator();
        RequestInfo requestInfoBack = getRequestInfoRandomSampleGenerator();

        requestContent.setRequestInfoId(requestInfoBack);
        assertThat(requestContent.getRequestInfoId()).isEqualTo(requestInfoBack);

        requestContent.requestInfoId(null);
        assertThat(requestContent.getRequestInfoId()).isNull();
    }
}
