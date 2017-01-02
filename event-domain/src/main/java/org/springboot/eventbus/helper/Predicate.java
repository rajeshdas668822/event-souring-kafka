package org.springboot.eventbus.helper;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property="op", visible = true, defaultImpl = ComparisonPredicate.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompoundPredicate.class, name = "AND"),
        @JsonSubTypes.Type(value = CompoundPredicate.class, name = "OR")
})
public abstract class Predicate {

    protected PredicateOperator op;

    public javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Criteria c) {
        return op.build(b, cq, this, c);
    }

    public static javax.persistence.criteria.Predicate[] buildPredicates(
            CriteriaBuilder b, CriteriaQuery<?> cq, List<Predicate> predicates, Criteria c, boolean reserveHead) {
        // ReserveHead here is a performance optimization. When buildPredicates() is called with a cq that
        //  already has a predicate, that predicate needs to be carried along. This reserveHead is for that
        //  purpose: it reserves one extra space at the head of the result array for that predicate, otherwise
        //  we would have to allocate and copy the array in QueryBuilder.buildQuery().
        //
        // See also QueryBuilder.buildQuery()
        //
        int offset = reserveHead ? 1 : 0;
        javax.persistence.criteria.Predicate[] r = new javax.persistence.criteria.Predicate[predicates.size() + offset];
        for (int i = 0; i < predicates.size(); ++i) {
            r[i+offset] = predicates.get(i).build(b, cq, c);
        }
        return r;
    }

    public static javax.persistence.criteria.Predicate[] buildPredicates(
            CriteriaBuilder b, CriteriaQuery<?> cq, List<Predicate> predicates, Criteria c) {
        return buildPredicates(b, cq, predicates, c, false);
    }

    // factory methods
    //
    public static Predicate and(Predicate... predicates) {
        return new CompoundPredicate(Arrays.asList(predicates), PredicateOperator.AND);
    }

    public static Predicate or(Predicate... predicates) {
        return new CompoundPredicate(Arrays.asList(predicates), PredicateOperator.OR);
    }

    public static Predicate eq(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.EQ);
    }

    public static Predicate ne(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.NE);
    }

    public static Predicate ge(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.GE);
    }

    public static Predicate gt(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.GT);
    }

    public static Predicate le(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.LE);
    }

    public static Predicate lt(String path, Object value) {
        return new ComparisonPredicate(path, value, PredicateOperator.LT);
    }

    public static Predicate like(String path, String pattern) {
        return new ComparisonPredicate(path, pattern, PredicateOperator.LIKE);
    }
}
