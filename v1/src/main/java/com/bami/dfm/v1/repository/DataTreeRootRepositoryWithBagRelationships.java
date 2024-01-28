package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeRoot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DataTreeRootRepositoryWithBagRelationships {
    Optional<DataTreeRoot> fetchBagRelationships(Optional<DataTreeRoot> dataTreeRoot);

    List<DataTreeRoot> fetchBagRelationships(List<DataTreeRoot> dataTreeRoots);

    Page<DataTreeRoot> fetchBagRelationships(Page<DataTreeRoot> dataTreeRoots);
}
