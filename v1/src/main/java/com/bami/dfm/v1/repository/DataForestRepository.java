package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataForest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataForest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataForestRepository extends JpaRepository<DataForest, Long> {}
