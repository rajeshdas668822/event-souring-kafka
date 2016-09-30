package org.springboot.eventbus.dao;

import org.springboot.eventbus.entity.Order;

/**
 * Created by rdas on 9/28/2016.
 */
public interface OrderDao {

    public Order findOrderById(String orderId);
    public  void saveOrder(Order order);
}
