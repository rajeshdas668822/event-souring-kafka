package org.springboot.eventbus.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by rdas on 1/3/2017.
 */
@Slf4j
public class MailComponent {

public void download() throws Exception{
        PollingConsumer pollingConsumer = null;
        CamelContext context = new DefaultCamelContext();

    String mailId="rajeshranjan.d@gmail.com";
    //String password="Rd668822";

    StringBuilder stringBuilder =
            new StringBuilder("imaps://imap.googlemail.com?username=")
                    .append(mailId)
                    .append("&password=")
                    .append("Rd668822")
                    .append("&delete=false")
                    .append("&peek=false&closeFolder=false&disconnect=false&connectionTimeout=-1");

    StringBuilder unsecureBuilder = new StringBuilder();
    unsecureBuilder.append("imap://rajeshranjan.d@gmail.com?password=")
            .append("Rd668822").append("unseen=true&consumer.delay=60000");




        Endpoint endpoint =
                context.getEndpoint(stringBuilder.toString());

        pollingConsumer = endpoint.createPollingConsumer();
        pollingConsumer.start();

        pollingConsumer.getEndpoint().createExchange();
        Exchange exchange = pollingConsumer.receive();


        while (exchange != null) {
            log.info("exchange : " + exchange.getExchangeId());
            exchange = pollingConsumer.receive();
        }


    }

    public static void main(String args []){
        MailComponent mailComponent =new MailComponent();
        try {
            mailComponent.download();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
