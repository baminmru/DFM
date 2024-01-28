package com.bami.dfm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bami.dfm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataForestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataForest.class);
        DataForest dataForest1 = new DataForest();
        dataForest1.setId(1L);
        DataForest dataForest2 = new DataForest();
        dataForest2.setId(dataForest1.getId());
        assertThat(dataForest1).isEqualTo(dataForest2);
        dataForest2.setId(2L);
        assertThat(dataForest1).isNotEqualTo(dataForest2);
        dataForest1.setId(null);
        assertThat(dataForest1).isNotEqualTo(dataForest2);
    }
}
