package org.springboot.eventbus.services;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * Created by rdas on 9/27/2016.
 */
public interface OrderCreation {
    public void execute(DelegateExecution execution) throws Exception;



}
