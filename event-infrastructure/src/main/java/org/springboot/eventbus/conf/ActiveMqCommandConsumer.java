package org.springboot.eventbus.conf;

import java.util.Map;

import org.springboot.eventbus.command.Command;
import org.springboot.eventbus.handler.CommandHandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springboot.eventbus.util.RequestBlocker;
import org.springframework.util.ClassUtils;

/**
 * Created by rdas on 9/29/2016.
 */


public class ActiveMqCommandConsumer {
	
	 public static final String MAPKEY_HANDLER_NAME="HandlerName";

    private Long timeout;
    private Gson gson;
    

    private Map<String, CommandHandler> handlerRegistry;

    
    
    
    public ActiveMqCommandConsumer(Long timeout ,Gson gson, Map<String, CommandHandler> handlerRegistry ){
        this.gson = gson;
        this.timeout = timeout;
        this.handlerRegistry = handlerRegistry;
    }


    /**
     *  Handler method to handle all the command. It will check if the handler present for event.
     *  if yes respective handler will be called else it will be ignored.
     * @param command
     */

    public void handleCommand(Command command){
    	/*Map<String, Object>  entries = gson.fromJson(body ,new TypeToken<Map<String, Object>>() {
        }.getType());*/
    	/*Map<String, Object>  entries = command.getEntries();

    	String shortName = (String)entries.get(MAPKEY_HANDLER_NAME);
        System.out.println(" Command Being Callded :::->"+shortName);
        System.out.println("  entries Callded :::->"+entries);*/
        String handlerName = ClassUtils.getShortName(command.getClass());
        if(handlerName!=null) {
            CommandHandler handler = handlerRegistry.get(handlerName);
            if (handler != null) {
               /* if(command.isSyncRequest()) {
                    handler.handleMessage(command);
                   // RequestBlocker.addResponseToQueue(command.getId(),command.getResponse());
                }else{

                }*/

                handler.handleMessage(command);
            } else {
                System.out.println(" No  Handler Command Called");
            }
        }
    }

}
