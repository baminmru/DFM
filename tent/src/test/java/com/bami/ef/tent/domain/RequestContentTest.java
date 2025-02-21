package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestContentTestSamples.*;
import static com.bami.ef.tent.domain.RequestInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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

        requestContent.addRequestInfoId(requestInfoBack);
        assertThat(requestContent.getRequestInfoIds()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestContent()).isEqualTo(requestContent);

        requestContent.removeRequestInfoId(requestInfoBack);
        assertThat(requestContent.getRequestInfoIds()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestContent()).isNull();

        requestContent.requestInfoIds(new HashSet<>(Set.of(requestInfoBack)));
        assertThat(requestContent.getRequestInfoIds()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestContent()).isEqualTo(requestContent);

        requestContent.setRequestInfoIds(new HashSet<>());
        assertThat(requestContent.getRequestInfoIds()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestContent()).isNull();
    }
}
