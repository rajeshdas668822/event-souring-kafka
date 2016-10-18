package org.springboot.eventbus.service.impl;

import org.activiti.engine.delegate.DelegateExecution;

import org.springboot.eventbus.dao.OrderDao;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.OrderCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service(value ="orderCreation")
public class OrderCreationImpl implements OrderCreation{

	@Autowired
	OrderDao orderDao;

	Random random = new Random(100);

	public enum Standalone{

		YES("Yes", true),
		NO("No",false);

		public String getOption() {
			return option;
		}

		public boolean getValue() {
			return value;
		}

		private String option;
		private boolean value;

		Standalone(String option, boolean value){
			 this.option = option;
			 this.value  = value;
		}

		/*public boolean findByValue(String value){
			Standalone [] standalones = Standalone.values();
			if(Standalone stdAlone:standalones){

			}

		}*/


	}


	public void execute(DelegateExecution execution) throws Exception {
		
		Order order = new Order();
		
		order.setAmount(Double.valueOf(execution.getVariable("amount").toString()));
		order.setCostPrice(Double.valueOf(execution.getVariable("costPrice").toString()));
		order.setCounterParty(execution.getVariable("counterParty").toString());
		order.setProductType(execution.getVariable("productType").toString());
		order.setQuantity(Double.valueOf(execution.getVariable("quantity").toString()));
		String standAlone = (String)execution.getVariable("isStandalone");
       if("Yes".equals(standAlone)) {
		   order.setStdAlone(Standalone.YES.getValue());
		}else{
		   order.setStdAlone(Standalone.NO.getValue());
	   }

        order.setStatus("Init");
		order.setOrderId(execution.getProcessInstanceId());

		orderDao.saveOrder(order);
		execution.setVariable("orderID", order.getOrderId());
		execution.setVariable("limitAmount", 10000);
		System.out.println("Order Created : Order ID:="+order.getOrderId());
		
		System.out.println(" getParentId :"+execution.getParentId());
		System.out.println(" getId() :"+execution.getId());
		
		
		
	}

}
