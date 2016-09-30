package org.springboot.eventbus.web.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springboot.eventbus.broker.ActiveMQPublisher;
import org.springboot.eventbus.command.FetchTaskCommand;
import org.springboot.eventbus.command.NewOrderCommand;
import org.springboot.eventbus.domain.RequestInfo;
import org.springboot.eventbus.domain.User;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springboot.eventbus.util.RequestBlocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OrderController {


	@Value("${kafka.enable:false}")
	private boolean iskafkaEnable;

	@Autowired
	private ActiveMQPublisher commandPublisher;

	
	public enum Action{
		Reject,Accept,Cancel
	}
	
	@Autowired
	WorkflowService workFlowService;
	
	 @RequestMapping(value="/submitOrder",method = RequestMethod.POST)
	 @ResponseBody
	 public void submit(@RequestBody Order order){
		 System.out.println("Hi");
		 if(order!=null){
			 order.setStatus("init");
			 if(iskafkaEnable){
				 NewOrderCommand newOrderCommand = new NewOrderCommand(UUID.randomUUID(),order);
				 commandPublisher.publishMessage(newOrderCommand);
			 }else {
				 workFlowService.initWorkFlow(order);
			 }
		 }
		
	}
	 
	 @RequestMapping(value="/loadTask",method = RequestMethod.POST)
	 @ResponseBody
	 public Map<String, List<Order>> loadTaskUser(@RequestBody User user){

		List<Order> orderList = null;
		 //return
		 if(iskafkaEnable){
			 UUID id = UUID.randomUUID();
			 FetchTaskCommand fetchTaskCommand = new FetchTaskCommand (id,user);
			 commandPublisher.publishMessage(fetchTaskCommand);
			 try {
				 orderList = (List) RequestBlocker.waitForResponse(id);
			 }catch (Exception ex){
				 ex.printStackTrace();
			 }
		 }else {
			 orderList = workFlowService.loadTask(user.getUserId());
		 }

		 Map<String,List<Order>> orderMap = orderList.parallelStream().collect(Collectors.groupingBy(Order::getStatus));
		 System.out.println(orderMap);
		 return  orderMap;
	 }
	 
	 
	 @RequestMapping(value="/approveOrder",method = RequestMethod.POST)
	 @ResponseBody
	 public boolean approveOrder(@RequestBody Order order){
		 System.out.println("Inside Approve"+order);
		 workFlowService.processOrder(order,"Accept");
		 return true;
	 }
	 
	 
	 @RequestMapping(value="/rejectOrder",method = RequestMethod.POST)
	 @ResponseBody
     public boolean rejectOrder(@RequestBody Order order){
		 
		 System.out.println("Inside Reject"+order);
		 workFlowService.processOrder(order,"Reject");
		 
		 return true;
	 }
    
    
	 @RequestMapping(value="/cancelOrder",method = RequestMethod.POST)
	 @ResponseBody
     public boolean cancelOrder(@RequestBody Order order){
		 
		 System.out.println("Inside cancelOrder"+order);		 
		 workFlowService.processCancelOrder(order,"Cancel");
		 
		 return true;
	 }
	 
	 
	 @RequestMapping(value="/cancelWorkingOrder",method = RequestMethod.POST)
	 @ResponseBody
     public boolean cancelWorkingOrder(@RequestBody Order order){
		 
		 System.out.println("Inside cancelOrder"+order);		 
		 workFlowService.processFillOrder(order,"Reject");
		 
		 return true;
	 }
	 
	 
	 
	@RequestMapping(value = "/fillOrder", method = RequestMethod.POST)
	@ResponseBody
	public boolean fillOrder(@RequestBody Order order) {
		System.out.println("Inside cancelOrder" + order.getFillAmount());
		workFlowService.processFillOrder(order,"");
		return true;
	}
	
	
	
	 
	
	 
	 
	 @RequestMapping(value="/assignOrder",method = RequestMethod.POST)
	 @ResponseBody
     public boolean assignOrder( @RequestBody RequestInfo requestInfo){		 
	  System.out.println("Inside Assign"+requestInfo.getUser().getUserName());
		 
		 return true;
	 }

}
