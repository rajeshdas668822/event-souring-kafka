package org.springboot.eventbus.conf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springboot.eventbus.command.Command;
import org.springboot.eventbus.handler.CommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * Created by rdas on 9/29/2016.
 */


public class ActiveMqCommandConsumer {

    private Long timeout;
    private Gson gson;
    private Map<String, CommandHandler> handlerRegistry;

    public ActiveMqCommandConsumer(Long timeout ,Gson gson, Map<String, CommandHandler> handlerRegistry ){
        this.gson = gson;
        this.timeout = timeout;
        this.handlerRegistry = handlerRegistry;
    }



    public void handleCommand(Command command){
       // String handlerName = ClassUtils.getClassFileName(command.getClass());
        String shortName = ClassUtils.getShortName(command.getClass());
        //handlerName = handlerName!=null?handlerName.substring(0,handlerName.indexOf(".")):handlerName;
        System.out.println(" Command Being Callded :::->"+shortName);
        if(shortName!=null) {
            CommandHandler handler = handlerRegistry.get(shortName);
            if (handler != null) {
                handler.handleMessage(command.getEntries());
            } else {
                System.out.println(" The Handle Command Called");
            }
        }
    }



}
