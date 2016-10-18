package org.springboot.eventbus.command;

import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class NewOrderCommand extends Command<Order> {


    //private Order order;
    private CommandType type;

    /*public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;

    }*/

    public NewOrderCommand(final UUID id, Order order,CommandType type) {
        this.id   = id;
        this.body = order;
        this.type = type;

    }

    public NewOrderCommand() {
        super();
    }

   /* @Override
    public Map<String, Object> getEntries() {
        final Map<String, Object> commandEntries = new HashMap<>();

        commandEntries.put(Constant.MAPKEY_ID, this.id);
        commandEntries.put(Constant.MAPKEY_ORDER, this.order);
        commandEntries.put(Constant.MAPKEY_HANDLER_NAME, "NewOrderCommand");
        super.setEntries(commandEntries);
        return commandEntries;
    }*/
}
