package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeBranch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DataTreeBranchRepositoryWithBagRelationshipsImpl implements DataTreeBranchRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DataTreeBranch> fetchBagRelationships(Optional<DataTreeBranch> dataTreeBranch) {
        return dataTreeBranch.map(this::fetchDataFields).map(this::fetchDataTreeBranches);
    }

    @Override
    public Page<DataTreeBranch> fetchBagRelationships(Page<DataTreeBranch> dataTreeBranches) {
        return new PageImpl<>(
            fetchBagRelationships(dataTreeBranches.getContent()),
            dataTreeBranches.getPageable(),
            dataTreeBranches.getTotalElements()
        );
    }

    @Override
    public List<DataTreeBranch> fetchBagRelationships(List<DataTreeBranch> dataTreeBranches) {
        return Optional.of(dataTreeBranches).map(this::fetchDataFields).map(this::fetchDataTreeBranches).orElse(Collections.emptyList());
    }

    DataTreeBranch fetchDataFields(DataTreeBranch result) {
        return entityManager
            .createQuery(
                "select dataTreeBranch from DataTreeBranch dataTreeBranch left join fetch dataTreeBranch.dataFields where dataTreeBranch.id = :id",
                DataTreeBranch.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<DataTreeBranch> fetchDataFields(List<DataTreeBranch> dataTreeBranches) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dataTreeBranches.size()).forEach(index -> order.put(dataTreeBranches.get(index).getId(), index));
        List<DataTreeBranch> result = entityManager
            .createQuery(
                "select dataTreeBranch from DataTreeBranch dataTreeBranch left join fetch dataTreeBranch.dataFields where dataTreeBranch in :dataTreeBranches",
                DataTreeBranch.class
            )
            .setParameter("dataTreeBranches", dataTreeBranches)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    DataTreeBranch fetchDataTreeBranches(DataTreeBranch result) {
        return entityManager
            .createQuery(
                "select dataTreeBranch from DataTreeBranch dataTreeBranch left join fetch dataTreeBranch.dataTreeBranches where dataTreeBranch.id = :id",
                DataTreeBranch.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<DataTreeBranch> fetchDataTreeBranches(List<DataTreeBranch> dataTreeBranches) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dataTreeBranches.size()).forEach(index -> order.put(dataTreeBranches.get(index).getId(), index));
        List<DataTreeBranch> result = entityManager
            .createQuery(
                "select dataTreeBranch from DataTreeBranch dataTreeBranch left join fetch dataTreeBranch.dataTreeBranches where dataTreeBranch in :dataTreeBranches",
                DataTreeBranch.class
            )
            .setParameter("dataTreeBranches", dataTreeBranches)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
