package org.springboot.eventbus.services;

import org.springboot.eventbus.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by rdas on 9/27/2016.
 */
public interface WorkflowService {
    public void initWorkFlow(Order order);
    public void processOrder(Order order, String actionType);
  //  public void processFillOrder(Order order, String actionType);
    public void processWorkingOrder(Order order);
    public void assignOrder(String userId, Order order);
    public void processCancelOrder(Order order, String actionType);
   // public Map<String, List<Order>> loadTask(String userId);

    public List<Order> loadTask(String userId);

}
