package com.bami.ef.tent.domain;

import static com.bami.ef.tent.domain.RequestContentTestSamples.*;
import static com.bami.ef.tent.domain.RequestInfoTestSamples.*;
import static com.bami.ef.tent.domain.RequestTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.tent.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestInfo.class);
        RequestInfo requestInfo1 = getRequestInfoSample1();
        RequestInfo requestInfo2 = new RequestInfo();
        assertThat(requestInfo1).isNotEqualTo(requestInfo2);

        requestInfo2.setId(requestInfo1.getId());
        assertThat(requestInfo1).isEqualTo(requestInfo2);

        requestInfo2 = getRequestInfoSample2();
        assertThat(requestInfo1).isNotEqualTo(requestInfo2);
    }

    @Test
    void requestTypeTest() {
        RequestInfo requestInfo = getRequestInfoRandomSampleGenerator();
        RequestType requestTypeBack = getRequestTypeRandomSampleGenerator();

        requestInfo.addRequestType(requestTypeBack);
        assertThat(requestInfo.getRequestTypes()).containsOnly(requestTypeBack);
        assertThat(requestTypeBack.getRequestInfo()).isEqualTo(requestInfo);

        requestInfo.removeRequestType(requestTypeBack);
        assertThat(requestInfo.getRequestTypes()).doesNotContain(requestTypeBack);
        assertThat(requestTypeBack.getRequestInfo()).isNull();

        requestInfo.requestTypes(new HashSet<>(Set.of(requestTypeBack)));
        assertThat(requestInfo.getRequestTypes()).containsOnly(requestTypeBack);
        assertThat(requestTypeBack.getRequestInfo()).isEqualTo(requestInfo);

        requestInfo.setRequestTypes(new HashSet<>());
        assertThat(requestInfo.getRequestTypes()).doesNotContain(requestTypeBack);
        assertThat(requestTypeBack.getRequestInfo()).isNull();
    }

    @Test
    void requestContentTest() {
        RequestInfo requestInfo = getRequestInfoRandomSampleGenerator();
        RequestContent requestContentBack = getRequestContentRandomSampleGenerator();

        requestInfo.setRequestContent(requestContentBack);
        assertThat(requestInfo.getRequestContent()).isEqualTo(requestContentBack);

        requestInfo.requestContent(null);
        assertThat(requestInfo.getRequestContent()).isNull();
    }
}
