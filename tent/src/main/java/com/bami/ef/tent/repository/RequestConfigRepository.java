package com.bami.ef.tent.repository;

import com.bami.ef.tent.domain.RequestConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestConfigRepository extends JpaRepository<RequestConfig, Long> {}
