package com.bami.ef.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.gateway.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataTreeRootTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataTreeRoot.class);
        DataTreeRoot dataTreeRoot1 = new DataTreeRoot();
        dataTreeRoot1.setId(1L);
        DataTreeRoot dataTreeRoot2 = new DataTreeRoot();
        dataTreeRoot2.setId(dataTreeRoot1.getId());
        assertThat(dataTreeRoot1).isEqualTo(dataTreeRoot2);
        dataTreeRoot2.setId(2L);
        assertThat(dataTreeRoot1).isNotEqualTo(dataTreeRoot2);
        dataTreeRoot1.setId(null);
        assertThat(dataTreeRoot1).isNotEqualTo(dataTreeRoot2);
    }
}
