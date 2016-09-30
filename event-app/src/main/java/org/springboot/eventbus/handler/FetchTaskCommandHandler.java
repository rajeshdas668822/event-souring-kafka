package org.springboot.eventbus.handler;

import org.springboot.eventbus.broker.ActiveMQPublisher;
import org.springboot.eventbus.command.TaskLoadedCommand;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springboot.eventbus.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class FetchTaskCommandHandler implements  CommandHandler {

    private static final String INTEREST = "FetchTaskCommand";

    @Autowired
    WorkflowService workflowService;

    @Autowired
    ActiveMQPublisher aqmPublisher;


    @Override
    public void handleMessage(Map<String, Object> eventEntries) {

        if (eventEntries.containsKey(Constant.MAPKEY_ID)) {
            final UUID id = UUID.fromString(eventEntries.get(Constant.MAPKEY_ID).toString());
            final String userId  = (String) eventEntries.get(Constant.MAPKEY_USER_ID);
            if(userId!=null) {
              List<Order> orderList = workflowService.loadTask(userId);
              System.out.println("FetchTaskCommandHandler Handle message :::for User->"+userId);
                aqmPublisher.publishMessage(new TaskLoadedCommand(id,orderList));
            }
        }

    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
