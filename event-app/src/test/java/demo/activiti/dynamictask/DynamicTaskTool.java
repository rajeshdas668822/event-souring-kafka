package demo.activiti.dynamictask;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cmd.SetExecutionVariablesCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

public class DynamicTaskTool {
	MyCustomActivitiBehaviorFactory myFactory = new MyCustomActivitiBehaviorFactory();
	
	static public final String DYNATASK_NUMBER_STRING = "currentTaskNumber";
	static public final String DYNATASK_COMPLETERD_STRING = "completedTasksNumber";
	static public final String DYNATASK_PREFIX = "dynTsk";
	static public final String DYNATASK_CreateOne = "createOne";
	static public final String DYNATASK_CreateTwo = "createTwo";
	static public final String DYNATASK_FINISH = "finish";
	static public final String DYNATASK_COMMAND_VAR = "command";
	

	
	
	

	private Task createNewTask(String activiyName, DelegateExecution execution, String taskId) {
		
		ExecutionEntity executionEntity = (ExecutionEntity) execution;
		Execution newExecution = executionEntity.createExecution();
		ExecutionEntity newExectionEntity = (ExecutionEntity) newExecution;
		
		ActivityImpl newActivty = new ActivityImpl(activiyName, null);
		
		newExectionEntity.setActivity(newActivty);
		
		TaskService taskService = execution.getEngineServices().getTaskService();

		
		Task newTask = taskService.newTask(DYNATASK_PREFIX+ taskId);
		
		TaskEntity newTaskEntity = (TaskEntity) newTask;
		newTaskEntity.setExecution((DelegateExecution) newExecution);
		taskService.saveTask(newTask);
		Context
	      .getCommandContext()
	      .getDbSqlSession()
	      .update(newExectionEntity);
		
		return newTask;
		
	}

	RuntimeService runtimeService = null;
	
	public void setRuntimeService(RuntimeService rs) {
		this.runtimeService = rs;
	}
	
	public Task createOneTask(DelegateExecution execution, Integer currTaskNo) {
		return createNewTask("oneStepTask", null, execution, currTaskNo);
		
	}
	
	public Task createTwoTask(DelegateExecution execution, Integer currTaskNo) {
		return createNewTask("twoStepTaskHead", null, execution, currTaskNo);
		
	}

	
	private Task createNewTask(String activitiName, Task prevTask, DelegateExecution execution, Integer taskNum) {
 
		
			
		String taskNumStr = String.format("%03d", taskNum);
		
		String prevId = "";
		if (prevTask != null) {
			prevId = prevTask.getId();
			if (!prevId.startsWith(DYNATASK_PREFIX))
				return null;
			prevId = prevId.substring(DYNATASK_PREFIX.length(), DYNATASK_PREFIX.length()+3);
			
		}
		return createNewTask(activitiName, execution,  taskNumStr+prevId);
		
	}
	
	

}
