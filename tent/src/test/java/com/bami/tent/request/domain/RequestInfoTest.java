package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestContentTestSamples.*;
import static com.bami.tent.request.domain.RequestInfoTestSamples.*;
import static com.bami.tent.request.domain.RequestTypeTestSamples.*;
import static com.bami.tent.request.domain.SourceSystemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
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
    void requestContentTest() {
        RequestInfo requestInfo = getRequestInfoRandomSampleGenerator();
        RequestContent requestContentBack = getRequestContentRandomSampleGenerator();

        requestInfo.addRequestContent(requestContentBack);
        assertThat(requestInfo.getRequestContents()).containsOnly(requestContentBack);
        assertThat(requestContentBack.getRequestInfoId()).isEqualTo(requestInfo);

        requestInfo.removeRequestContent(requestContentBack);
        assertThat(requestInfo.getRequestContents()).doesNotContain(requestContentBack);
        assertThat(requestContentBack.getRequestInfoId()).isNull();

        requestInfo.requestContents(new HashSet<>(Set.of(requestContentBack)));
        assertThat(requestInfo.getRequestContents()).containsOnly(requestContentBack);
        assertThat(requestContentBack.getRequestInfoId()).isEqualTo(requestInfo);

        requestInfo.setRequestContents(new HashSet<>());
        assertThat(requestInfo.getRequestContents()).doesNotContain(requestContentBack);
        assertThat(requestContentBack.getRequestInfoId()).isNull();
    }

    @Test
    void requestTypeTest() {
        RequestInfo requestInfo = getRequestInfoRandomSampleGenerator();
        RequestType requestTypeBack = getRequestTypeRandomSampleGenerator();

        requestInfo.setRequestType(requestTypeBack);
        assertThat(requestInfo.getRequestType()).isEqualTo(requestTypeBack);

        requestInfo.requestType(null);
        assertThat(requestInfo.getRequestType()).isNull();
    }

    @Test
    void requestSourceTest() {
        RequestInfo requestInfo = getRequestInfoRandomSampleGenerator();
        SourceSystem sourceSystemBack = getSourceSystemRandomSampleGenerator();

        requestInfo.setRequestSource(sourceSystemBack);
        assertThat(requestInfo.getRequestSource()).isEqualTo(sourceSystemBack);

        requestInfo.requestSource(null);
        assertThat(requestInfo.getRequestSource()).isNull();
    }
}
