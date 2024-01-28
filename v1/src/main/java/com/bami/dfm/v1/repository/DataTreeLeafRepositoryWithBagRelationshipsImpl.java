package com.bami.dfm.v1.repository;

import com.bami.dfm.v1.domain.DataTreeLeaf;
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
public class DataTreeLeafRepositoryWithBagRelationshipsImpl implements DataTreeLeafRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DataTreeLeaf> fetchBagRelationships(Optional<DataTreeLeaf> dataTreeLeaf) {
        return dataTreeLeaf.map(this::fetchDataFields);
    }

    @Override
    public Page<DataTreeLeaf> fetchBagRelationships(Page<DataTreeLeaf> dataTreeLeaves) {
        return new PageImpl<>(
            fetchBagRelationships(dataTreeLeaves.getContent()),
            dataTreeLeaves.getPageable(),
            dataTreeLeaves.getTotalElements()
        );
    }

    @Override
    public List<DataTreeLeaf> fetchBagRelationships(List<DataTreeLeaf> dataTreeLeaves) {
        return Optional.of(dataTreeLeaves).map(this::fetchDataFields).orElse(Collections.emptyList());
    }

    DataTreeLeaf fetchDataFields(DataTreeLeaf result) {
        return entityManager
            .createQuery(
                "select dataTreeLeaf from DataTreeLeaf dataTreeLeaf left join fetch dataTreeLeaf.dataFields where dataTreeLeaf.id = :id",
                DataTreeLeaf.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<DataTreeLeaf> fetchDataFields(List<DataTreeLeaf> dataTreeLeaves) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dataTreeLeaves.size()).forEach(index -> order.put(dataTreeLeaves.get(index).getId(), index));
        List<DataTreeLeaf> result = entityManager
            .createQuery(
                "select dataTreeLeaf from DataTreeLeaf dataTreeLeaf left join fetch dataTreeLeaf.dataFields where dataTreeLeaf in :dataTreeLeaves",
                DataTreeLeaf.class
            )
            .setParameter("dataTreeLeaves", dataTreeLeaves)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
