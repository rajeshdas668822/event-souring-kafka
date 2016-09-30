package org.springboot.eventbus.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value="order")
public class OrderService {
	
	public void validate(DelegateExecution execution) {
		System.out.println("validating order for isbn " + execution.getVariable("isbn"));
	}

	
	public Date validateIsbn(Long isbn) {
		System.out.println("validating order for isbn " + isbn);
		 return new Date();
	}
}
