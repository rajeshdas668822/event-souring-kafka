package org.springboot.eventbus.broker;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;

/**
 * Created by rdas on 10/4/2016.
 */
public class TaskCompeteCallBack implements Synchronization {


    @Override
    public void onComplete(Exchange exchange) {

    }

    @Override
    public void onFailure(Exchange exchange) {

    }
}
