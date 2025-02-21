package com.bami.ef.tent.repository;

import com.bami.ef.tent.domain.RequestInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {}
