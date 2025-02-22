package com.bami.tent.request.repository;

import com.bami.tent.request.domain.RequestConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestConfigRepository extends JpaRepository<RequestConfig, Long> {}
