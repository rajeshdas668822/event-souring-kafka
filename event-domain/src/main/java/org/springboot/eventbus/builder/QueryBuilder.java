package org.springboot.eventbus.builder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private static <T> CriteriaQuery<T> buildQuery(CriteriaBuilder b, Class<T> clazz, Criteria c) {
        CriteriaQuery<T> q = b.createQuery(clazz);
        q.from(clazz);
        return buildQuery(b, q, c);
    }

    private static <T> CriteriaQuery<T> buildQuery(CriteriaBuilder b, CriteriaQuery<T> q, Criteria c) {
        if (!c.getFilters().isEmpty()) {
            javax.persistence.criteria.Predicate[] p;
            if (null != q.getRestriction()) {
                p = Predicate.buildPredicates(b, q, c.getFilters(), c, true);
                p[0] = q.getRestriction();
            } else {
                p = Predicate.buildPredicates(b, q, c.getFilters(), c);
            }
            q.where(p);
        }
        if (!c.getOrderBy().isEmpty()) {
            List<javax.persistence.criteria.Order> orderList = new ArrayList<>(q.getOrderList().size() + c.getOrderBy().size());
            orderList.addAll(q.getOrderList());
            for (Order o : c.getOrderBy()) {
                orderList.add(o.build(b, q, c));
            }
            q.orderBy(orderList);
        }
        return q;
    }

    private static <T> CriteriaQuery<Long> buildCountQuery(CriteriaBuilder b, Class<T> clazz, Criteria c) {
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<T> root = q.from(clazz);
        q.select(b.count(root));
        if (c != null && !c.getFilters().isEmpty()) {
            q.where(Predicate.buildPredicates(b, q, c.getFilters(), c));
        }
        return q;
    }

    public static <T> List<T> query(EntityManager em, Class<T> clazz, Criteria criteria) {
        TypedQuery<T> typedQuery = em.createQuery(buildQuery(em.getCriteriaBuilder(), clazz, criteria));
        applyPagination(typedQuery, criteria);
        return typedQuery.getResultList();
    }

    public static <T> T queryFirst(EntityManager em, Class<T> clazz, Criteria criteria) {
        List<T> query = query(em, clazz, criteria);
        return query.isEmpty() ? null : query.get(0);
    }

    public static <T> List<T> query(EntityManager em, CriteriaQuery<T> query, Criteria criteria) {
        TypedQuery<T> typedQuery = em.createQuery(buildQuery(em.getCriteriaBuilder(), query, criteria));
        applyPagination(typedQuery, criteria);
        return typedQuery.getResultList();
    }

    public static <T> long count(EntityManager em, Class<T> clazz, Criteria criteria) {
        return em.createQuery(buildCountQuery(em.getCriteriaBuilder(), clazz, criteria)).getSingleResult();
    }



    /**
     * Apply pagination
     * @param typedQuery
     * @param criteria
     */
    public static void applyPagination(TypedQuery<?> typedQuery, Criteria criteria) {
        if(criteria == null) {
            return;
        }
        if(criteria.getOffset() != null){
            typedQuery.setFirstResult(criteria.getOffset());
        }
        if(criteria.getSize() != null) {
            typedQuery.setMaxResults(criteria.getSize());
        }
    }
}
