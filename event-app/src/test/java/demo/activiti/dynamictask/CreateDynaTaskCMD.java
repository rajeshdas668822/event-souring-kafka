package demo.activiti.dynamictask;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cmd.SetExecutionVariablesCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class CreateDynaTaskCMD implements Command<Void>, Serializable {

	static DynamicTaskTool dynaTaskTool = new DynamicTaskTool();
	
	Integer nTasks;
	ExecutionEntity execution; 
	Integer currTaskNo;
	
	

	public CreateDynaTaskCMD(int n, ExecutionEntity execution, Integer currTaskNo) {
		nTasks = n;
		this.execution = execution;
		this.currTaskNo = currTaskNo;
	}
	
	
	@Override
	public Void execute(CommandContext commandContext) {
		if (nTasks.intValue() == 1)
			dynaTaskTool.createOneTask(execution, currTaskNo);
		else if (nTasks.intValue() == 2)
			dynaTaskTool.createTwoTask(execution, currTaskNo);
		
		return null;
		
		
	}

	
	


}
