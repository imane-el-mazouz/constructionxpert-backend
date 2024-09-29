package com.resource.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.resource.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceSpecification {

    public static Specification<Resource> filterByCriteria(String provider, Integer minQuantity, Integer maxQuantity) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (provider != null && !provider.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("provider"), provider));
            }

            if (minQuantity != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), minQuantity));
            }

            if (maxQuantity != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), maxQuantity));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
