package demo.orderflow;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.eventbus.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rdas on 8/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class OrderFlowProcessTest extends AbstractTest {


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
    
    private Wiser wiser;
    
    
    @Before
    public void setup() {
        wiser = new Wiser();
        wiser.setPort(1025);
        wiser.start();
    }

    @After
    public void cleanup() {
        wiser.stop();
    }

   @Test
    public void testOrderWorkFlowForFillOrder(){
        Map<String, String> processVariable = new HashMap<String, String>();
        processVariable.put("productType", "FxSwap");
        processVariable.put("amount", "4000");
        processVariable.put("quantity", "20");
        processVariable.put("counterParty", "RBS");
        processVariable.put("costPrice", "500");
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", "Yes");

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("ipdsOrder").singleResult();
        assertNotNull(definition);
       formService.submitStartFormData(definition.getId(), processVariable);
       validateOrderCreation();
       validateOrderPending("Accept");
       validateWorkingOrder("Reject");
       validateFillOrder();
    }


    @Test
    public void testOrderWorkFlowForRejectOrder(){
        Map<String, String> processVariable = new HashMap<String, String>();
        processVariable.put("productType", "FxSwap");
        processVariable.put("amount", "4000");
        processVariable.put("quantity", "20");
        processVariable.put("counterParty", "RBS");
        processVariable.put("costPrice", "500");
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", "Yes");

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("ipdsOrder").latestVersion().singleResult();
        assertNotNull(definition);
        formService.submitStartFormData(definition.getId(), processVariable);
        validateOrderCreation();
        validateOrderPending("Reject");
        validateReject();

    }



    @Test
    public void testOrderWorkFlowFromPendingToPendingCancel(){
        Map<String, String> processVariable = new HashMap<String, String>();
        processVariable.put("productType", "FxSwap");
        processVariable.put("amount", "4000");
        processVariable.put("quantity", "20");
        processVariable.put("counterParty", "RBS");
        processVariable.put("costPrice", "500");
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", "Yes");

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("ipdsOrder").latestVersion().singleResult();
        assertNotNull(definition);
        formService.submitStartFormData(definition.getId(), processVariable);
        validateOrderCreation();
        validateOrderPending("Cancel");
        validateOrderPendingCancel("Reject","Pending");
        validateOrderPending("Accept");


    }
    
    @Test
    public void testOrderWorkFlowFromPendingCancelToWorking(){
        Map<String, String> processVariable = new HashMap<String, String>();
        processVariable.put("productType", "FxSwap");
        processVariable.put("amount", "4000");
        processVariable.put("quantity", "20");
        processVariable.put("counterParty", "RBS");
        processVariable.put("costPrice", "500");
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", "Yes");

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("ipdsOrder").latestVersion().singleResult();
        assertNotNull(definition);
        formService.submitStartFormData(definition.getId(), processVariable);
        validateOrderCreation();
        validateOrderPending("Accept");
        validateWorkingOrder("Reject");
        validateWorkingOrder();
        validateOrderPendingCancel();
       


    }
    
    @Test
    public void testOrderWorkFlowFromWorkingToFill(){
        Map<String, String> processVariable = new HashMap<String, String>();
        processVariable.put("productType", "FxSwap");
        processVariable.put("amount", "4000");
        processVariable.put("quantity", "20");
        processVariable.put("counterParty", "RBS");
        processVariable.put("costPrice", "500");
        processVariable.put("limitAmount", "10000");
        processVariable.put("isStandalone", "Yes");

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("ipdsOrder").latestVersion().singleResult();
        assertNotNull(definition);
        formService.submitStartFormData(definition.getId(), processVariable);
        validateOrderCreation();
        validateOrderPending("Accept");
        validateWorkingOrder(null);
        validateFillOrder();

    }
    
   

    private void validateReject(){
        List<HistoricActivityInstance> historicDetails = historyService.createHistoricActivityInstanceQuery().orderByActivityName().asc().list();
        assertNotNull(historicDetails);
        assertEquals(8, historicDetails.size());
        HistoricActivityInstance orderUpdate = ((HistoricActivityInstance) historicDetails.get(1));
        assertEquals("End", orderUpdate.getActivityName());



    }





    private void validateOrderCreation(){
        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery().variableUpdates()
                .orderByVariableName().asc().list();
        assertNotNull(historicDetails);
        assertEquals(9, historicDetails.size());
        HistoricVariableUpdate orderUpdate = ((HistoricVariableUpdate) historicDetails.get(6));
        assertEquals("order", orderUpdate.getVariableName());
        Order order = (Order) orderUpdate.getValue();
        assertEquals(true, order.getStdAlone());
    }


    private void validateOrderPending(String actionType){
         List<Task> taskList = taskService.createTaskQuery().taskAssignee("fozzie").list();
         for (Task task : taskList) {
            System.out.println(" Task Name ::" + task.getName());
            assertEquals("Pending",task.getName());
            Map<String, Object> variables = task.getProcessVariables();
            System.out.println("Task Local Variable ::" + variables);
            variables.put("actionType", actionType);
            taskService.setVariables(task.getId(), variables);
            taskService.complete(task.getId());
        }

    }
    
    
    private void validateOrderPendingCancel(String pendingCancelAction, String previousAction ){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : taskList) {
           System.out.println(" Task Name ::" + task.getName());
           assertEquals("Pending Cancel",task.getName());
           Map<String, Object> variables = task.getProcessVariables();
           System.out.println("Task Local Variable ::" + variables);
           variables.put("pendingCancelAction", pendingCancelAction);
           variables.put("previousAction", previousAction);
           
           taskService.setVariables(task.getId(), variables);
           taskService.complete(task.getId());
       }

   }
    
    private void validateOrderPendingCancel(){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : taskList) {
           System.out.println("Task Name ::" + task.getName());
           assertEquals("Pending Cancel",task.getName());
           Map<String, Object> variables = task.getProcessVariables();
           System.out.println("Task Local Variable ::" + variables);
           taskService.setVariables(task.getId(), variables);
           taskService.complete(task.getId());
       }

   }


	private void validateWorkingOrder(String type) {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("sales").list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			assertEquals("Working Order", task.getName());
			Map<String, Object> variables = task.getProcessVariables();
			System.out.println("Task Local Variable ::" + variables);
			if (type!= null && type.equalsIgnoreCase("Reject")) {				
				variables.put("pendingCancelAction","Reject");
			} else {
				variables.put("pendingCancelAction","");
				variables.put("filledAmount", "4000");
			}
			taskService.setVariables(task.getId(), variables);
			taskService.complete(task.getId());
		}
	}
	
	
	private void validateWorkingOrder() {
		List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("sales").list();
		for (Task task : taskList) {
			System.out.println(" Task Name ::" + task.getName());
			assertEquals("Working Order", task.getName());
			Map<String, Object> variables = task.getProcessVariables();
			System.out.println("Task Local Variable ::" + variables);
		}
	}


    private void validateFillOrder(){
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("sales").list();
        for (Task task : taskList) {
            System.out.println(" Task Name ::" + task.getName());
            assertEquals("Filled",task.getName());
            Map<String, Object> variables = task.getProcessVariables();
            System.out.println("Task Local Variable ::" + variables);
           /* variables.put("filledAmount", "4000");
            taskService.setVariables(task.getId(), variables);
            taskService.complete(task.getId());*/
        }
    }



}
