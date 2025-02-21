package com.bami.ef.tent.repository;

import com.bami.ef.tent.domain.RequestParamDict;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestParamDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestParamDictRepository extends JpaRepository<RequestParamDict, Long> {}
