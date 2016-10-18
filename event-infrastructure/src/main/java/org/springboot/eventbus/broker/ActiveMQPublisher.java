package org.springboot.eventbus.broker;

import org.apache.camel.ExchangePattern;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springboot.eventbus.command.Command;
import org.springboot.eventbus.util.EventPattern;
import org.springboot.eventbus.util.RequestBlocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by rdas on 9/29/2016.
 */

@Component
public class ActiveMQPublisher  implements EventPublisher{


   @Autowired
   private Gson gson;

   @Autowired
   ProducerTemplate producerTemplate;

    @Value("${producer.topic}")
    String topicEndpoint;


	@Value("${producer.queue}")
	String queueEndpoint;





    public  void publish(Command command){
    	String message = gson.toJson(command.getEntries());
        System.out.println("command---------------->"+message);
        producerTemplate.sendBody(topicEndpoint,command);



    }



	@Override
	public Object send(Command command) {

		try {
			Command enrichedCommand =(Command) producerTemplate.sendBody(queueEndpoint, ExchangePattern.InOut ,command);
			return enrichedCommand.getResponse();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}


	@Override
	public Object asyncSend(Command command) {

		try {
			Future<Object> responseFuture = producerTemplate.asyncRequestBody(queueEndpoint,command);
			Command enrichedCommand = (Command)responseFuture.get(10, TimeUnit.SECONDS);
			return enrichedCommand.getResponse();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
