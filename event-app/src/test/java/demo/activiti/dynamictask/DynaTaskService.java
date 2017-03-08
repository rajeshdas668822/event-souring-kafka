package demo.activiti.dynamictask;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public interface DynaTaskService  {
	public void createOneTask(ExecutionEntity execution);

	public void createTwoTasks(ExecutionEntity execution);

}
