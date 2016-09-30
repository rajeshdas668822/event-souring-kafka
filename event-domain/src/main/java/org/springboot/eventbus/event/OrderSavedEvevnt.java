package org.springboot.eventbus.event;

import org.springboot.eventbus.domain.Event;

import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/28/2016.
 */
public class OrderSavedEvevnt extends Event {

    public OrderSavedEvevnt (final UUID aggregateId, final Long version){
        super(aggregateId , version);
    }

    @Override
    public Map<String, Object> getEntries() {
        return null;
    }
}
