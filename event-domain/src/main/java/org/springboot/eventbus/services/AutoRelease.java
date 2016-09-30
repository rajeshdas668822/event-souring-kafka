package org.springboot.eventbus.services;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * Created by rdas on 9/28/2016.
 */
public interface AutoRelease {
    public void validateAutoRelease(DelegateExecution execution);
}
