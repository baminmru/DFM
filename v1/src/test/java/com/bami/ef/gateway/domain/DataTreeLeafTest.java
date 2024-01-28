package com.bami.ef.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.gateway.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataTreeLeafTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataTreeLeaf.class);
        DataTreeLeaf dataTreeLeaf1 = new DataTreeLeaf();
        dataTreeLeaf1.setId(1L);
        DataTreeLeaf dataTreeLeaf2 = new DataTreeLeaf();
        dataTreeLeaf2.setId(dataTreeLeaf1.getId());
        assertThat(dataTreeLeaf1).isEqualTo(dataTreeLeaf2);
        dataTreeLeaf2.setId(2L);
        assertThat(dataTreeLeaf1).isNotEqualTo(dataTreeLeaf2);
        dataTreeLeaf1.setId(null);
        assertThat(dataTreeLeaf1).isNotEqualTo(dataTreeLeaf2);
    }
}
