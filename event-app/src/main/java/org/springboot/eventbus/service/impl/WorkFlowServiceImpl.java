package org.springboot.eventbus.service.impl;


import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.springboot.eventbus.dao.OrderDao;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.WorkflowService;
import org.springboot.eventbus.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("workFlowService")
public class WorkFlowServiceImpl implements WorkflowService {
	@Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

	@Autowired
	private OrderDao orderDao;
    
    
   @Autowired
   private  IdentityService identityService;
    
    private static final String processName="ipdsOrder";
    
    
	public void initWorkFlow(Order order){
		
		
		Map<String, Object> processVariable = new HashMap<String, Object>();
        processVariable.put("productType", order.getProductType());
        processVariable.put("amount", order.getAmount());
        processVariable.put("quantity",order.getQuantity());
        processVariable.put("counterParty", order.getCounterParty());
        processVariable.put("costPrice", order.getCostPrice());
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", order.getStandAlone());

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processName).latestVersion().singleResult();
       if(definition!=null){
    	 ProcessInstance processInstance =  runtimeService.startProcessInstanceById(definition.getId(), processVariable);
    	
    	 logProcessActivity(processInstance);
       }
		
		
	}



	public void processInitOrder(Order order, String actionType){
		List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			System.out.println("Task Local Variable ::" + variables);
			order.setStatus(OrderStatus.PENDING.getValue());
			orderDao.updateOrder(order);
			taskService.complete(task.getId(),variables);
		}

	}


	public void processOrder(Order order, String actionType){
	 List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			OrderStatus orderStatus = OrderStatus.get(order.getStatus());
			if(actionType.equals("Accept")){
				switch(orderStatus){
					case INIT:
						order.setStatus(OrderStatus.PENDING.getValue());
						break;
					case PENDING:
						order.setStatus(OrderStatus.WORKING.getValue());
						break;
					default:
				}

			}else if(actionType.equals("Reject")){
				order.setStatus(OrderStatus.REJECT.getValue());
			}
			variables.put("actionType",actionType);
			orderDao.updateOrder(order);
			taskService.complete(task.getId(),variables);
		}

	}
	
	
	/*public void processFillOrder(Order order, String actionType) {
		List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			variables.put("filledAmount", order.getFillAmount());
			variables.put("pendingCancelAction",actionType);
			order.setStatus(OrderStatus.PENDING_CANCEL.getValue());
			variables.put("order", order);
			orderDao.updateOrder(order);
			taskService.complete(task.getId(), variables);
		}

	}*/
	

	
	public void assignOrder(String userId, Order order) {
		List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			task.delegate(userId);
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			taskService.complete(task.getId(), variables);
			
		}
	}
	
	
	
	public void processCancelOrder(Order order, String actionType) {
		List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			System.out.println("Task Local Variable ::" + variables);
				order.setTaskId(task.getId());
				order.setStatus(OrderStatus.PENDING_CANCEL.getValue());
				variables.put("order", order);
				variables.put("actionType", actionType);
				variables.put("previousAction", task.getName());
				orderDao.updateOrder(order);
				taskService.complete(task.getId(), variables);

		}

	}



	public void processWorkingOrder(Order order){
		List<Task> taskList = taskService.createTaskQuery().executionId(String.valueOf(order.getOrderId())).list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			Map<String, Object> variables = taskService.getVariables(task.getId());
			variables.put("filledAmount", order.getFillAmount());
			variables.put("order", order);
			taskService.complete(task.getId(), variables);
		}
	}

	public  List<Order> loadTask(String userId) {
		Map<String, List<Order>> taskListMap = new ConcurrentHashMap<>();
		List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();

		List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
		
		if (groupList != null && !groupList.isEmpty()) {
			for (Group group : groupList) {
				if (group.getName() != null) {
					List<Task> grouptaskList = taskService.createTaskQuery().taskCandidateGroup(group.getName()).list();
					if (grouptaskList != null && !grouptaskList.isEmpty() ) {
						taskList.addAll(grouptaskList);
					}
				}
			}

		}
		List<Order> orderList = new ArrayList<>();
		for (Task task : taskList) {
			 Map<String, Object> variables = taskService.getVariables(task.getId());
			 String orderId = (String)variables.get("orderID");
			 Order order = orderDao.findOrderById(orderId);
			orderList.add(order);
		}
		System.out.println(orderList);
		return orderList;
	}

	public void logProcessActivity(ProcessInstance processInstance){
		System.out.println(" Activity ID :" +processInstance.getActivityId());
		
		
	}
	
	
	public boolean isOrderEqual(Order passOrder, Order storedOrder){
		boolean orderEqual = false;
		if(passOrder == null || storedOrder == null){
			orderEqual = false;
		}
		
		//if(passOrder.getCounterParty())
		
		return orderEqual;
	}
	

}
