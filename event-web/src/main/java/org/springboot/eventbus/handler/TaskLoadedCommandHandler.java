package org.springboot.eventbus.handler;

import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.util.Constant;
import org.springboot.eventbus.util.RequestBlocker;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class TaskLoadedCommandHandler implements CommandHandler {

    private static final String INTEREST = "TaskLoadedCommand";

    @Override
    public void handleMessage(Map<String, Object> eventEntries) {
        System.out.println("Inside TaskLoadedCommandHandler:::handleMessage");
        if (eventEntries.containsKey(Constant.MAPKEY_ID)) {
            final UUID id = UUID.fromString(eventEntries.get(Constant.MAPKEY_ID).toString());
            final List<Order> orderList = (List) eventEntries.get(Constant.MAPKEY_ORDER_LIST);
            if(orderList!=null) {
               // workflowService.initWorkFlow(order);
                RequestBlocker.addResponseToQueue(id,orderList);
                System.out.println("Task Loaded Command Handler triggered :::");
            }
        }

    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
