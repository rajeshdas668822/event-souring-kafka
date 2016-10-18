package org.springboot.eventbus.services;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * Created by rdas on 10/17/2016.
 */
public interface FillOrderValidator {

    public void validateFillOrder(DelegateExecution execution);
}
