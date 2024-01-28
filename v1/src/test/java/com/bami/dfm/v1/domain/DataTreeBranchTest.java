package com.bami.dfm.v1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bami.dfm.v1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataTreeBranchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataTreeBranch.class);
        DataTreeBranch dataTreeBranch1 = new DataTreeBranch();
        dataTreeBranch1.setId(1L);
        DataTreeBranch dataTreeBranch2 = new DataTreeBranch();
        dataTreeBranch2.setId(dataTreeBranch1.getId());
        assertThat(dataTreeBranch1).isEqualTo(dataTreeBranch2);
        dataTreeBranch2.setId(2L);
        assertThat(dataTreeBranch1).isNotEqualTo(dataTreeBranch2);
        dataTreeBranch1.setId(null);
        assertThat(dataTreeBranch1).isNotEqualTo(dataTreeBranch2);
    }
}
