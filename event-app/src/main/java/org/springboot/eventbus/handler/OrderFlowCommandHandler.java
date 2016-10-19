package org.springboot.eventbus.handler;

import org.springboot.eventbus.command.OrderFlowCommand;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springboot.eventbus.util.ActionType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rdas on 10/16/2016.
 */
public class OrderFlowCommandHandler implements CommandHandler<OrderFlowCommand> {
    private static final String INTEREST = "OrderFlowCommand";
    @Autowired
    WorkflowService workflowService;

    @Override
    public void handleMessage(OrderFlowCommand command) {
        final Order order = (Order)command.getBody();
        ActionType actionType = command.getActiontype();
        if(order!=null) {
            switch (actionType) {

                case APPROVE:
                    workflowService.processOrder(order, "Accept");
                    System.out.println("Inside Approve->" + order.getOrderId());
                    break;
                case PROCESSFILL:
                    workflowService.processWorkingOrder(order);
                    System.out.println("processWorkingOrder triggered->" + order.getOrderId());
                    break;
                case REJECT:
                    workflowService.processOrder(order,"Reject");
                    System.out.println("Reject triggered->" + order.getOrderId());
                    break;
                case CANCEL:
                    workflowService.processCancelOrder(order,"Cancel");
                    System.out.println("Cancel triggered->" + order.getOrderId());
                    break;
                case CANCELWORKINGORDER:
                    workflowService.processOrder(order,"Cancel");
                    System.out.println("Rejected Working Order Triggered->" + order.getOrderId());
                    break;

            }
        }

        /*if(order!=null && ActionType.APPROVE.equals(command.getActiontype())) {
            workflowService.processOrder(order,"Accept");
            System.out.println("Inside Approve->"+order);
        }*/
    }

    @Override
    public String getInterest() {
        return INTEREST;
    }
}
