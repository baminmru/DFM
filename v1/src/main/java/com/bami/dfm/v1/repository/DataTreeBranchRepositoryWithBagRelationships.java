package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeBranch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DataTreeBranchRepositoryWithBagRelationships {
    Optional<DataTreeBranch> fetchBagRelationships(Optional<DataTreeBranch> dataTreeBranch);

    List<DataTreeBranch> fetchBagRelationships(List<DataTreeBranch> dataTreeBranches);

    Page<DataTreeBranch> fetchBagRelationships(Page<DataTreeBranch> dataTreeBranches);
}
