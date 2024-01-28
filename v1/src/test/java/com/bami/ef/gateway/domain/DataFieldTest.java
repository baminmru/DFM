package com.bami.ef.gateway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bami.ef.gateway.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataField.class);
        DataField dataField1 = new DataField();
        dataField1.setId(1L);
        DataField dataField2 = new DataField();
        dataField2.setId(dataField1.getId());
        assertThat(dataField1).isEqualTo(dataField2);
        dataField2.setId(2L);
        assertThat(dataField1).isNotEqualTo(dataField2);
        dataField1.setId(null);
        assertThat(dataField1).isNotEqualTo(dataField2);
    }
}
