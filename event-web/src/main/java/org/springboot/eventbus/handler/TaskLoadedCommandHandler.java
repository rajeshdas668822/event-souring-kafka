package org.springboot.eventbus.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springboot.eventbus.command.TaskLoadedCommand;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.util.Constant;
import org.springboot.eventbus.util.RequestBlocker;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by rdas on 9/29/2016.
 */
public class TaskLoadedCommandHandler implements CommandHandler <TaskLoadedCommand>{

    private static final String INTEREST = "TaskLoadedCommand";

    @Override
    public void handleMessage(TaskLoadedCommand command) {

        final List<Order> orderList = command.getOrderList();
        if(orderList!=null) {
            RequestBlocker.addResponseToQueue(command.getId(),orderList);
            System.out.println("Task Loaded Command Handler triggered :::"+orderList.size());
        }
      //  return null;
    }

    @Override
    public String getInterest() {
        return INTEREST;
    }


    private List<Order> convertToOrder(List<LinkedHashMap> orderList){
        List<Order> convertorderList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


       // orderId=30001, productType=FXSwap, amount=50000.0, quantity=30.0, counterParty=JP Morgan, costPrice=30.0, status=Init, taskId=null, fillAmount=null, standalone=true
        for(Map map : orderList){
            Order order = new Order();
            order.setOrderId((String)map.get("orderId"));
            order.setAmount((Double) map.get("amount"));
            order.setCostPrice((Double) map.get("costPrice"));

            order.setCounterParty((String)map.get("counterParty"));
            order.setFillAmount((Double)map.get("fillAmount"));
            order.setProductType((String)map.get("productType"));

            order.setStatus((String)map.get("status"));
            convertorderList.add(order);

        }


       return convertorderList;

    }
}
