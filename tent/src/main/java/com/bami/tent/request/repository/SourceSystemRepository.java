package com.bami.tent.request.repository;

import com.bami.tent.request.domain.SourceSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SourceSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceSystemRepository extends JpaRepository<SourceSystem, Long> {}
