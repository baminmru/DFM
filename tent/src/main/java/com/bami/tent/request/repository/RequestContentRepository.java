package com.bami.tent.request.repository;

import com.bami.tent.request.domain.RequestContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestContentRepository extends JpaRepository<RequestContent, Long> {}
