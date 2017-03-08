package demo.activiti.dynamictask;

import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;
import org.activiti.engine.impl.task.TaskDefinition;

public class MyUserTaskActiviyBehavior extends UserTaskActivityBehavior {
	DynamicTaskTool dynamicTaskTool = new DynamicTaskTool();

	public MyUserTaskActiviyBehavior(String userTaskId,TaskDefinition taskDefinition) {
		super(userTaskId,taskDefinition);
		// TODO Auto-generated constructor stub
	}
	
	private void increaseCompletedNumber(ActivityExecution execution) {
		   Integer completed = (Integer) execution.getVariable(DynamicTaskTool.DYNATASK_COMPLETERD_STRING);
		   if (completed == null)
			   completed = 0;
		   execution.setVariable(DynamicTaskTool.DYNATASK_COMPLETERD_STRING, completed + 1);
		   
		   
		
	}
	
	  public void signal(ActivityExecution execution, String signalName, Object signalData) throws Exception {
		  
		  RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
		  String activityName = execution.getActivity().getId();
		  String command = (String) execution.getVariable(DynamicTaskTool.DYNATASK_COMMAND_VAR);
 		    System.out.println("Task signalled. Task name =" + activityName + " execution id=" + execution.getId());
 		    
 		    boolean isDynaTask = activityName.equals("oneStepTask") || activityName.equals("twoStepTaskHead") ||activityName.startsWith(DynamicTaskTool.DYNATASK_PREFIX);
		   
	
			   List<PvmTransition> outgoingTransitions = execution.getActivity().getOutgoingTransitions();
		   
		   if (outgoingTransitions.size() == 0) {
			   
				   ActivityExecution parentExecution = execution.getParent();
				   increaseCompletedNumber(execution);
				   ((ExecutionEntity) execution).setScope(false);
				   ((ExecutionEntity) execution).performOperation(AtomicOperation.ACTIVITY_END);
				   runtimeService.signal(parentExecution.getId());
				   
			   }
		   else
		    if (!((ExecutionEntity)execution).getTasks().isEmpty())
		      throw new ActivitiException("UserTask should not be signalled before complete");
			   leave(execution);
		  }


}
