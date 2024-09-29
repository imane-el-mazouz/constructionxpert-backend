package com.project.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.project.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ProjectSpecification {

    public static Specification<Project> filterByCriteria(Double minBudget, Double maxBudget, Date startDate, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minBudget != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("budget"), minBudget));
            }

            if (maxBudget != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("budget"), maxBudget));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
