package demo.activiti.dynamictask;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.BeforeClass;
import org.junit.Test;


public class MyUnitTest {
	
	static DynamicTaskTool dynamicTaskTool = new DynamicTaskTool();
	

	private void showInfo(ProcessEngine processEngine) {
		TaskService taskService = processEngine.getTaskService();
		System.out.println();
		for (Task task: taskService.createTaskQuery().list())
			System.out.println("task id=" + task.getId());
		List<Execution> executions = processEngine.getRuntimeService().createExecutionQuery().list();
		for (Execution execution: executions)
			System.out.println("execution id =" + execution.getId() + " activity=" + execution.getActivityId());
		
	}

	
	void completeWithCommand(ProcessEngine processEngine,  Task task, String command) {
		TaskService taskService = processEngine.getTaskService();

		System.out.println("**********************************");
		System.out.println("comleting task " + task.getId());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(DynamicTaskTool.DYNATASK_COMMAND_VAR, command);
		taskService.complete(	task.getId(), variables);
		System.out.println("tasks after completing ");
		showInfo(processEngine);
		
	}

	
	static ProcessEngine processEngine = null;
	static ProcessInstance processInstance = null;
	static TaskService taskService = null;
	static RuntimeService runtimeService = null;
	public static DynaTaskService dynaTaskService = null;
	
	@BeforeClass
	static public void setup() {
		processEngine = ((ProcessEngineConfigurationImpl)ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration())
				  .setActivityBehaviorFactory(new MyCustomActivitiBehaviorFactory())
				  .setHistoryLevel(HistoryLevel.NONE)
				  .buildProcessEngine();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		dynamicTaskTool.setRuntimeService(runtimeService);
		taskService = processEngine.getTaskService();
		dynaTaskService =  new DynaTaskServiceImpl();
		((DynaTaskServiceImpl) dynaTaskService).setCommandExecutor(((TaskServiceImpl) taskService).getCommandExecutor());
		
		
		

		repositoryService
				  .createDeployment()
				  .addClasspathResource("org/activiti/test/my-process.bpmn20.xml")
				  .deploy()
				  .getId();

		
	}
	
	
	
	
	
	// This is the simplest form. There is only one task. The user opts for no further tasks
	@Test
	public void testNoExtraSteps() {
		processInstance = runtimeService.startProcessInstanceByKey("my-process");

		System.out.println("at start");
		showInfo(processEngine);
		
		assertNotNull(processInstance);
		
		assertEquals(1, taskService.createTaskQuery().count());
		
		System.out.println("just after starting");
		showInfo(processEngine);
		
		Task task = taskService.createTaskQuery().singleResult();
		completeWithCommand(processEngine, task, DynamicTaskTool.DYNATASK_FINISH);
		assertEquals(0, taskService.createTaskQuery().count());
		
		showInfo(processEngine);
		assertEquals(0, runtimeService.createExecutionQuery().count());
		
	}
	
	// In this test, the user decides to create another single step task
	@Test
	public void testExtraOneStepTask() {
		processInstance = runtimeService.startProcessInstanceByKey("my-process");
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(processInstance.getId()).singleResult();

		System.out.println("at start");
		
		assertNotNull(processInstance);
		
		assertEquals(1, taskService.createTaskQuery().count());
		Task initialTask = taskService.createTaskQuery().singleResult();
		
		System.out.println("just after starting");
		
		dynaTaskService.createOneTask(execution);

		// there should be two tasks waiting, one the old one and one the new created one
		assertEquals(2, taskService.createTaskQuery().count());

		
		// close the old one
		taskService.complete(initialTask.getId());

		// now there is only the new one
		assertEquals(1, taskService.createTaskQuery().count());
		
		
		// close that generated new task
		Task newGeneratedTask = taskService.createTaskQuery().singleResult();
		taskService.complete(newGeneratedTask.getId());

		
		// and process should be finished, since all tasks are closed
		assertEquals(0, runtimeService.createExecutionQuery().count());

		
		
	}
	
	// In this test, the user decides to create Two step task
	@Test
	public void testExtraTwoStepTask() {
		processInstance = runtimeService.startProcessInstanceByKey("my-process");
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(processInstance.getId()).singleResult();

		System.out.println("at start");
		
		assertNotNull(processInstance);
		
		assertEquals(1, taskService.createTaskQuery().count());
		
		Task initialTask = taskService.createTaskQuery().singleResult();
		
		
		dynaTaskService.createTwoTasks(execution);

		// there should be two tasks waiting, one the old one and one the new created one
		assertEquals(2, taskService.createTaskQuery().count());

		
		// close the old one
		taskService.complete(initialTask.getId());

		// now there is only the new one
		assertEquals(1, taskService.createTaskQuery().count());
		
		
		// close that generated new task
		Task newGeneratedTask = taskService.createTaskQuery().singleResult();
		taskService.complete(newGeneratedTask.getId());

		// closing the new one will cause the subsequent task to appear
		assertEquals(1, taskService.createTaskQuery().count());
		Task subsequentTask = taskService.createTaskQuery().singleResult();
		
		taskService.complete(subsequentTask.getId());

		// Now there should be no tasks
		assertEquals(0, taskService.createTaskQuery().count());
		
		// and process should be finished, since all tasks are closed
		assertEquals(0, runtimeService.createExecutionQuery().count());

	}

	// In this test, the user decides to create two extra tasks. One OneTask and the other TwoTasks
	@Test
	public void testMixOneAndTwoSteps() {
		processInstance = runtimeService.startProcessInstanceByKey("my-process");
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(processInstance.getId()).singleResult();
		
		assertNotNull(processInstance);
		
		assertEquals(1, taskService.createTaskQuery().count());
		
		Task initialTask = taskService.createTaskQuery().singleResult();
		

		// create extra one task
		dynaTaskService.createOneTask(execution);
		// there should be two tasks waiting now. 
		assertEquals(2, taskService.createTaskQuery().count());
		
		// remember it for later use
		Task extraOneTask = taskService.createTaskQuery().orderByTaskCreateTime().asc().list().get(1);
		

		// now create one additianl two step task
		dynaTaskService.createTwoTasks(execution);

		// there should be two tasks waiting, one the old one and one the new created one
		assertEquals(3, taskService.createTaskQuery().count());
		// remember it for later use
		Task extraTwoTask = taskService.createTaskQuery().orderByTaskCreateTime().asc().list().get(2);

		
		// close the initial one
		taskService.complete(initialTask.getId());


		// there should be two tasks waiting
		assertEquals(2, taskService.createTaskQuery().count());
		
		
		// close the two stepTask
		
		taskService.complete(extraTwoTask.getId());
		// there should be again two tasks, as the deleted one will be replaced by the proceeding task
		assertEquals(2, taskService.createTaskQuery().count());

		
		taskService.complete(extraOneTask.getId());


		// now there should be one task
		assertEquals(1, taskService.createTaskQuery().count());
		
		Task theLastTask = taskService.createTaskQuery().singleResult();
		
		taskService.complete(theLastTask.getId());

		// Now there should be no tasks
		assertEquals(0, taskService.createTaskQuery().count());
		
		// and process should be finished, since all tasks are closed
		assertEquals(0, runtimeService.createExecutionQuery().count());

	}


}
