package com.bami.tent.request.repository;

import com.bami.tent.request.domain.RequestContentConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestContentConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestContentConfigRepository extends JpaRepository<RequestContentConfig, Long> {}
