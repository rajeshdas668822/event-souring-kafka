package org.springboot.eventbus.broker;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springboot.eventbus.command.Command;
import org.springframework.stereotype.Component;

/**
 * Created by rdas on 9/29/2016.
 */

@Component
public class ActiveMQPublisher {

   @Produce(uri = "jms:topic:commandHandler")
   ProducerTemplate producerTemplate;


    public  void publishMessage(Command command){
        producerTemplate.sendBody(command);

    }


}
