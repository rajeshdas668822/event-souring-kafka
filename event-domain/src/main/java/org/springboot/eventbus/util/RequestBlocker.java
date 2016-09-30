package org.springboot.eventbus.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by rdas on 9/29/2016.
 */
public class RequestBlocker {

    private static Map<UUID,ArrayBlockingQueue<?>> requestBlockerMap = new ConcurrentHashMap<>();

    public static Object waitForResponse(UUID id) throws Exception{
        if(requestBlockerMap.get(id)!=null){
           return  requestBlockerMap.get(id).poll(1, TimeUnit.SECONDS);
        }else{
            ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(1);
            requestBlockerMap.put(id,blockingQueue);
          return  blockingQueue.poll(1, TimeUnit.SECONDS);
        }
    }

    public static void addResponseToQueue(UUID id , Object obj){
        ArrayBlockingQueue blockingQueue =   requestBlockerMap.get(id);
        if(blockingQueue!=null) {
            blockingQueue.offer(obj);
        }
    }

   }
