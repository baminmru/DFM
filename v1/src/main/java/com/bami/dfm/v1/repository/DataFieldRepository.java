package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataField;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataFieldRepository extends JpaRepository<DataField, Long> {}
