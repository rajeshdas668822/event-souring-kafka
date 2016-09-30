package org.springboot.eventbus.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

/**
 * Created by rdas on 9/28/2016.
 */

@Service(value = "mailSender")
public class MailSender {


    public void sendMail(DelegateExecution execution){

    	Double orderId = (Double)execution.getVariable("orderID");
        System.out.println("Mail Has been sent to CounterParty :: With Order ::"+orderId);

    }
}
