package org.springboot.eventbus.util;

/**
 * Created by rdas on 10/18/2016.
 */
public enum OrderStatus {


    PENDING("Pending"),
    INIT("Init"),
    WORKING("Working"),
    FILL("Fill"),
    PARTIALLY_FILLED("Partially Filled"),
    REJECT("Reject"),
    PENDING_CANCEL("Pending Cancel");


    String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getValue(){
        return status;
    }

    public static OrderStatus get(String status){
        for(OrderStatus orderStatus :OrderStatus.values()){
            if(status.equals(orderStatus.getValue()))
                return orderStatus;
        }
        return null;
    }



}
