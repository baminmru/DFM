package com.bami.tent.request.repository;

import com.bami.tent.request.domain.RequestParamDict;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestParamDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestParamDictRepository extends JpaRepository<RequestParamDict, Long> {}
