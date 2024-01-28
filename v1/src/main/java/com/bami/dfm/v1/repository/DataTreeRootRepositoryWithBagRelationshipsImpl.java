package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeRoot;
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
public class DataTreeRootRepositoryWithBagRelationshipsImpl implements DataTreeRootRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DataTreeRoot> fetchBagRelationships(Optional<DataTreeRoot> dataTreeRoot) {
        return dataTreeRoot.map(this::fetchDataFields);
    }

    @Override
    public Page<DataTreeRoot> fetchBagRelationships(Page<DataTreeRoot> dataTreeRoots) {
        return new PageImpl<>(
            fetchBagRelationships(dataTreeRoots.getContent()),
            dataTreeRoots.getPageable(),
            dataTreeRoots.getTotalElements()
        );
    }

    @Override
    public List<DataTreeRoot> fetchBagRelationships(List<DataTreeRoot> dataTreeRoots) {
        return Optional.of(dataTreeRoots).map(this::fetchDataFields).orElse(Collections.emptyList());
    }

    DataTreeRoot fetchDataFields(DataTreeRoot result) {
        return entityManager
            .createQuery(
                "select dataTreeRoot from DataTreeRoot dataTreeRoot left join fetch dataTreeRoot.dataFields where dataTreeRoot.id = :id",
                DataTreeRoot.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<DataTreeRoot> fetchDataFields(List<DataTreeRoot> dataTreeRoots) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dataTreeRoots.size()).forEach(index -> order.put(dataTreeRoots.get(index).getId(), index));
        List<DataTreeRoot> result = entityManager
            .createQuery(
                "select dataTreeRoot from DataTreeRoot dataTreeRoot left join fetch dataTreeRoot.dataFields where dataTreeRoot in :dataTreeRoots",
                DataTreeRoot.class
            )
            .setParameter("dataTreeRoots", dataTreeRoots)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
