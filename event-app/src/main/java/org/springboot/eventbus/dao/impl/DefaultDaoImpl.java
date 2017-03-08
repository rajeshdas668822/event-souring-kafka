package org.springboot.eventbus.dao.impl;

import org.springboot.eventbus.builder.Criteria;
import org.springboot.eventbus.builder.QueryBuilder;
import org.springboot.eventbus.dao.DefaultDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdas on 12/11/2016.
 */
@Repository
public class DefaultDaoImpl  implements DefaultDao{



    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public <T> T insert(T type) {
         em.persist(type);
        return type;
    }

    @Override
    @Transactional
    public <T> List<T> insert(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if (i % 1000 == 0) {
                em.flush();
                em.clear();
            }
        }
        return list;
    }

    @Override
    @Transactional
    public <T> T save(T type) {
        return em.merge(type);
    }

    @Override
    @Transactional
    public <T> List<T> save(List<T> list) {
        List<T> results = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            results.add(em.merge(list.get(i)));
            if (i % 1000 == 0) {
                em.flush();
                em.clear();
            }
        }
        return results;
    }

    @Override
    @Transactional
    public <T> void deleteById(Class<T> type, Object id) {
        Object entity = em.find(type, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    @Override
    @Transactional
    public <T> void deleteAll(Class<T> type) {
        em.createQuery("delete from " + type.getSimpleName()).executeUpdate();

    }

    @Transactional
    public <T> List<T>  getByNamedQuery(String hql,List<Object> params){
      Query query =  em.createNamedQuery(hql);
        int i=1;
        if(params!=null) {
            for (Object object : params) {
                query.setParameter(i, object);
                i++;
            }
        }
     return  query.getResultList();
    }


    @Override
    public <T> List<T> getByCriteria(Class<T> type, Criteria criteria) {
        if (criteria == null) {
            return getAll(type);
        }
        return QueryBuilder.query(em, type, criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> type) {
        return em.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
    }

}
