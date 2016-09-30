package org.springboot.eventbus.api;

import org.springboot.eventbus.domain.Event;

/**
 * Created by rdas on 9/28/2016.
 */
public interface EventHandler<T extends Event> {
    public void handle(final T event);

}
