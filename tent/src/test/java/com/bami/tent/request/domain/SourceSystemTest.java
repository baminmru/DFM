package com.bami.tent.request.domain;

import static com.bami.tent.request.domain.RequestInfoTestSamples.*;
import static com.bami.tent.request.domain.SourceSystemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bami.tent.request.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SourceSystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceSystem.class);
        SourceSystem sourceSystem1 = getSourceSystemSample1();
        SourceSystem sourceSystem2 = new SourceSystem();
        assertThat(sourceSystem1).isNotEqualTo(sourceSystem2);

        sourceSystem2.setId(sourceSystem1.getId());
        assertThat(sourceSystem1).isEqualTo(sourceSystem2);

        sourceSystem2 = getSourceSystemSample2();
        assertThat(sourceSystem1).isNotEqualTo(sourceSystem2);
    }

    @Test
    void requestInfoTest() {
        SourceSystem sourceSystem = getSourceSystemRandomSampleGenerator();
        RequestInfo requestInfoBack = getRequestInfoRandomSampleGenerator();

        sourceSystem.addRequestInfo(requestInfoBack);
        assertThat(sourceSystem.getRequestInfos()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestSource()).isEqualTo(sourceSystem);

        sourceSystem.removeRequestInfo(requestInfoBack);
        assertThat(sourceSystem.getRequestInfos()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestSource()).isNull();

        sourceSystem.requestInfos(new HashSet<>(Set.of(requestInfoBack)));
        assertThat(sourceSystem.getRequestInfos()).containsOnly(requestInfoBack);
        assertThat(requestInfoBack.getRequestSource()).isEqualTo(sourceSystem);

        sourceSystem.setRequestInfos(new HashSet<>());
        assertThat(sourceSystem.getRequestInfos()).doesNotContain(requestInfoBack);
        assertThat(requestInfoBack.getRequestSource()).isNull();
    }
}
