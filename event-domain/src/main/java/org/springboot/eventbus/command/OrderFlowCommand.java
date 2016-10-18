package org.springboot.eventbus.command;

import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.util.ActionType;

/**
 * Created by rdas on 10/16/2016.
 */
public class OrderFlowCommand extends  Command<Order> {
    ActionType actiontype;
    public ActionType getActiontype() {
        return actiontype;
    }

    public void setActiontype(ActionType actiontype) {
        this.actiontype = actiontype;
    }



}
