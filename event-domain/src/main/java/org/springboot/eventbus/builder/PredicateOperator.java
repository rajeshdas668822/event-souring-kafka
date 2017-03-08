package org.springboot.eventbus.builder;
import org.springboot.eventbus.builder.helper.CriteriaHelper;

import javax.persistence.criteria.*;
import java.util.Collection;
public enum PredicateOperator {
    AND {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            return b.and(Predicate.buildPredicates(b, cq, ((CompoundPredicate) p).getPredicates(), c));
        }
    },
    OR {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            return b.or(Predicate.buildPredicates(b, cq, ((CompoundPredicate) p).getPredicates(), c));
        }
    },
    EQ {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            if (cp.getValue() instanceof Collection) {
                return e.in((Collection) cp.getValue());
            } else {
                return b.equal(e, cp.getValue());
            }
        }
    },
    NE {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            return b.notEqual(e, cp.getValue());
        }
    },
    GE {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            checkComparable(cp, e);
            return b.greaterThanOrEqualTo((Expression<? extends Comparable>) e, (Comparable) cp.getValue());
        }
    },
    GT {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            checkComparable(cp, e);
            return b.greaterThan((Expression<? extends Comparable>) e, (Comparable) cp.getValue());
        }
    },
    LE {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            checkComparable(cp, e);
            return b.lessThanOrEqualTo((Expression<? extends Comparable>) e, (Comparable) cp.getValue());
        }
    },
    LT {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes());
            checkComparable(cp, e);
            return b.lessThan((Expression<? extends Comparable>) e, (Comparable) cp.getValue());
        }
    },
    LIKE {
        @Override
        javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c) {
            ComparisonPredicate cp = (ComparisonPredicate) p;
            Path e = CriteriaHelper.getCompoundJoinedPath(getRoot(cq), c.mapFieldName(cp.getName()), c.getJoinTypes()); // assume only one root
            if (!e.getJavaType().equals(String.class)) {
                throw new IllegalArgumentException(String.format("[ComparisonPredicate::%s] Field %s is not String, actual type: %s", p.getOp(), cp.getName(), e.getJavaType().getName()));
            }
            if (!(cp.getValue() instanceof String)) {
                throw new IllegalArgumentException(String.format("[ComparisonPredicate::%s] Pattern %s for Field %s is not String", p.getOp(), cp.getValue(), cp.getName()));
            }
            return b.like((Expression<String>) e, (String) cp.getValue());
        }
    }
    ;

    abstract javax.persistence.criteria.Predicate build(CriteriaBuilder b, CriteriaQuery<?> cq, Predicate p, Criteria c);

    private static void checkComparable(ComparisonPredicate p, Expression<?> e) {
        if (!e.getJavaType().isInstance(p.getValue())) {
            throw new IllegalArgumentException(String.format("[ComparisonPredicate::%s] Value %s is not comparable to Field %s[%s]",  p.getOp(), p.getValue(), p.getName(), e.getJavaType().getName()));
        }
    }

    private static Root<?> getRoot(CriteriaQuery<?> cq) {
        return cq.getRoots().iterator().next(); // assume only one root
    }
}
