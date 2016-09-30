package org.springboot.eventbus.dao.impl;

import org.springboot.eventbus.dao.OrderDao;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.entity.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by rdas on 9/28/2016.
 */

@Repository
public class OrderDaoImpl implements OrderDao {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Order findOrderById(String orderId) {
      return  entityManager.createNamedQuery("order.findOrderByID", Order.class)
                .setParameter("orderId", orderId).getSingleResult();
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        entityManager.persist(order);
       // entityManager.flush();
    }
}
