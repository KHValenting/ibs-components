package ru.ibs.dpr.crud.spring.params;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Predicate.BooleanOperator;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class FilterSpecification<ENTITY> implements Specification<ENTITY> {

    private final List<FilterCriteria> criteriaList = new ArrayList<>();
    private BooleanOperator operator = BooleanOperator.AND;

    public FilterSpecification(FilterCriteria... criteria) {
        Collections.addAll(criteriaList, criteria);
    }

    public Predicate toPredicate(Root<ENTITY> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = criteriaList.stream()
                .map(criteria -> createPredicate(root, builder, criteria))
                .collect(Collectors.toList());

        if (predicates.size() == 1) {
            return predicates.get(0);
        } else if (predicates.size() > 1) {
            return operator == BooleanOperator.AND
                    ? builder.and(predicates.toArray(new Predicate[0]))
                    : builder.or(predicates.toArray(new Predicate[0]));
        } else {
            throw new IllegalStateException("No criteria specified");
        }
    }

    public Predicate createPredicate(Root<ENTITY> root, CriteriaBuilder builder, FilterCriteria criteria) {

        switch (criteria.getOperation()) {
            case EQ:
                return builder.equal(
                        root.get(criteria.getKey()), criteria.getValue());
            case NOT_EQ:
                return builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue());
            case LIKE:
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case NOT_LIKE:
                return builder.notLike(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case ILIKE:
                return builder.like(
                        builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            case NOT_ILIKE:
                return builder.notLike(
                        builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            case STARTS_WITH:
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue().toString());
            case NOT_STARTS_WITH:
                return builder.notLike(
                        root.get(criteria.getKey()), "%" + criteria.getValue().toString());
            case BETWEEN:
                return builder.between(
                        root.get(criteria.getKey()), criteria.getValue().toString(), criteria.getValue2().toString());
            case MORE:
                return builder.greaterThan(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case MORE_EQ:
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS:
                return builder.lessThan(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_EQ:
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case IN:
                return builder.in(
                        root.get(criteria.getKey())).value(criteria.getValue());
            case NOT_IN:
                builder.not(
                        root.get(criteria.getKey())).in(criteria.getValue());
            case IS_NULL:
                builder.isNull(root.get(criteria.getKey()));
            case IS_NOT_NULL:
                builder.isNotNull(root.get(criteria.getKey()));
            default:
                throw new IllegalArgumentException("Not supported operation " + criteria.getOperation());
        }
    }

    public FilterSpecification<ENTITY> addFilter(FilterCriteria filterCriteria) {
        criteriaList.add(filterCriteria);
        return this;
    }

    public FilterSpecification<ENTITY> setLogicalOperator(BooleanOperator operator) {
        this.operator = operator;
        return this;
    }
}