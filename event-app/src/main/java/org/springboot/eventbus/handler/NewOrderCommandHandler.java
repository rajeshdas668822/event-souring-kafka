package org.springboot.eventbus.handler;

import lombok.extern.slf4j.Slf4j;

import org.springboot.eventbus.broker.ActiveMQPublisher;
import org.springboot.eventbus.command.TaskLoadedCommand;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springboot.eventbus.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/28/2016.
 */

@Slf4j
public class NewOrderCommandHandler implements CommandHandler {
    @Autowired
    WorkflowService workflowService;




    private static final String INTEREST = "NewOrderCommand";


    @Override
    public void handleMessage(Map<String, Object> eventEntries) {
        if (eventEntries.containsKey(Constant.MAPKEY_ID)) {
            final UUID id = UUID.fromString(eventEntries.get(Constant.MAPKEY_ID).toString());
            final Order order = (Order) eventEntries.get(Constant.MAPKEY_ORDER);
           if(order!=null) {
               workflowService.initWorkFlow(order);
               System.out.println("Order Submited :::");
           }
        }
    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
