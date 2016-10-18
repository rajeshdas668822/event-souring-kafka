package org.springboot.eventbus.command;

import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class TaskLoadedCommand  extends Command{

    List<Order> orderList;
    private CommandType type;
    public List<Order> getOrderList() {
        return orderList;
    }

    public TaskLoadedCommand (UUID id,  List<Order> orderList,CommandType type ){
        this.id = id;
        this.orderList = orderList;
        this.type = type;
    }

    /*@Override
    public Map<String, Object> getEntries() {
        final Map<String, Object> commandEntries = new HashMap<>();
        commandEntries.put(Constant.MAPKEY_ID, this.id);
        commandEntries.put(Constant.MAPKEY_ORDER_LIST, this.orderList);
        commandEntries.put(Constant.MAPKEY_HANDLER_NAME, "TaskLoadedCommand");
        super.setEntries(commandEntries);
        return commandEntries;
    }*/
}
