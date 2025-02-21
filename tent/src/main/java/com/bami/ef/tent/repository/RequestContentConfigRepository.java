package com.bami.ef.tent.repository;

import com.bami.ef.tent.domain.RequestContentConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestContentConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestContentConfigRepository extends JpaRepository<RequestContentConfig, Long> {}
