package com.bami.tent.request.repository;

import com.bami.tent.request.domain.RequestInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {}
