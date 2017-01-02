package org.springboot.eventbus.dao;

import java.util.List;

/**
 * Created by rdas on 12/11/2016.
 */
public interface DefaultDao {


    <T> T insert(T type);

    <T> List<T> insert(List<T> list);

    <T> T save(T type);

    <T> List<T> save(List<T> list);

    <T> void deleteById(Class<T> type, Object id);

    <T> void deleteAll(Class<T> type);

    public <T> List<T>  getByNamedQuery(String hql,List<Object> params);
}
