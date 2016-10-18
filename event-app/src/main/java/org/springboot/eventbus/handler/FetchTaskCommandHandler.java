package org.springboot.eventbus.handler;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springboot.eventbus.broker.EventPublisher;

import org.springboot.eventbus.command.FetchTaskCommand;
import org.springboot.eventbus.command.TaskLoadedCommand;
import org.springboot.eventbus.domain.User;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rdas on 9/29/2016.
 */
public class FetchTaskCommandHandler implements  CommandHandler <FetchTaskCommand>{

    private static final String INTEREST = "FetchTaskCommand";

     @Autowired
     WorkflowService workflowService;

     @Autowired
     EventPublisher aqmPublisher;


    @Override
    public void handleMessage(FetchTaskCommand command) {
        //Map<String, Object> eventEntries = command.getEntries();

       /* if (eventEntries.containsKey(Constant.MAPKEY_ID)) {
            final UUID id = UUID.fromString(eventEntries.get(Constant.MAPKEY_ID).toString());
            final String userId  = (String) eventEntries.get(Constant.MAPKEY_USER_ID);

        }*/

        User user = (User)command.getBody();
        List<Order> orderList = null;
        if(user!=null && user.getUserId()!=null) {
            String userId = user.getUserId();
            orderList = workflowService.loadTask(userId);
            System.out.println("FetchTaskCommandHandler Handle message :::for User->"+userId);
        }
        //return orderList;
        command.setResponse(orderList);
    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
