package org.springboot.eventbus.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.springboot.eventbus.dao.OrderDao;
import org.springboot.eventbus.dao.UserDao;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.AutoRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rdas on 9/28/2016.
 */

@Service(value ="autoRelease")
public class AutoReleaseChecker implements AutoRelease{

    @Autowired
    OrderDao orderDao;

    @Override
    public void validateAutoRelease(DelegateExecution execution) {
        String orderId = (String)execution.getVariable("orderID");
        Integer limitAmount = (Integer)execution.getVariable("limitAmount");
        Order order = orderDao.findOrderById(orderId);
        System.out.println(" Checking Auto Release ");
        System.out.println(" Order ID: " + order.getOrderId());
        System.out.println( " Order Amount: " + order.getAmount());
        System.out.println(" Order Limit: " + limitAmount);
        System.out.println(" Order isAutoRelease: " + order.getStandalone());
        boolean isAutoRelease = false;

        if(order.getStandalone() && order.getAmount() < limitAmount){
            isAutoRelease = true ;
            order.setStatus("Pending");
        }else{
            order.setStatus("Init");
        }
        execution.setVariable("isAutoRelease", ""+isAutoRelease);

    }
}
