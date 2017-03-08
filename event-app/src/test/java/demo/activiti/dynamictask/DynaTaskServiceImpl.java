package demo.activiti.dynamictask;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.cmd.GetExecutionVariableCmd;
import org.activiti.engine.impl.cmd.SetExecutionVariablesCmd;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Service;

@Service
public class DynaTaskServiceImpl extends ServiceImpl implements DynaTaskService{

	
	private void setTaskNo(DelegateExecution execution, Integer taskNo) {
		
	    Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put(DynamicTaskTool.DYNATASK_NUMBER_STRING, taskNo);
	    commandExecutor.execute(new SetExecutionVariablesCmd(execution.getId(), variables, false));

	}

	@Override
	public void createOneTask(ExecutionEntity execution) {
		Integer currTaskNo =  (Integer) commandExecutor.execute(new GetExecutionVariableCmd(execution.getId(),  DynamicTaskTool.DYNATASK_NUMBER_STRING, false));
		if (currTaskNo == null)
			currTaskNo = 1;
		
		CreateDynaTaskCMD dynaTaskCMD = new CreateDynaTaskCMD(1, execution, currTaskNo);
		commandExecutor.execute(dynaTaskCMD);
		setTaskNo(execution, currTaskNo + 1);
		
	}

	@Override
	public void createTwoTasks(ExecutionEntity execution) {
		Integer currTaskNo =  (Integer) commandExecutor.execute(new GetExecutionVariableCmd(execution.getId(),  DynamicTaskTool.DYNATASK_NUMBER_STRING, false));
		if (currTaskNo == null)
			currTaskNo = 1;
		CreateDynaTaskCMD dynaTaskCMD = new CreateDynaTaskCMD(2, execution, currTaskNo);
		commandExecutor.execute(dynaTaskCMD);
		setTaskNo(execution, currTaskNo + 1);
		
	}

}
