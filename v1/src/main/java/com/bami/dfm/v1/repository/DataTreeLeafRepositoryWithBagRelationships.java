package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeLeaf;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DataTreeLeafRepositoryWithBagRelationships {
    Optional<DataTreeLeaf> fetchBagRelationships(Optional<DataTreeLeaf> dataTreeLeaf);

    List<DataTreeLeaf> fetchBagRelationships(List<DataTreeLeaf> dataTreeLeaves);

    Page<DataTreeLeaf> fetchBagRelationships(Page<DataTreeLeaf> dataTreeLeaves);
}
