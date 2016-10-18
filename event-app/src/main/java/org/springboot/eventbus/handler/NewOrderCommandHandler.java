package org.springboot.eventbus.handler;

import org.springboot.eventbus.command.NewOrderCommand;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by rdas on 9/28/2016.
 */

@Slf4j
public class NewOrderCommandHandler implements CommandHandler<NewOrderCommand> {
    @Autowired
    WorkflowService workflowService;




    private static final String INTEREST = "NewOrderCommand";


    @Override
    public void handleMessage(NewOrderCommand command) {
    	/*Map<String, Object> eventEntries = command.getEntries();
        if (eventEntries.containsKey(Constant.MAPKEY_ID)) {
            final UUID id = UUID.fromString(eventEntries.get(Constant.MAPKEY_ID).toString());

        }*/

        final Order order = (Order)command.getBody();
        if(order!=null) {
            workflowService.initWorkFlow(order);
            System.out.println("Order Submited :::");
        }
      // return null;
    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
