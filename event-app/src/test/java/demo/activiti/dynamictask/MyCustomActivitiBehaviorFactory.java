
package demo.activiti.dynamictask;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;

public class MyCustomActivitiBehaviorFactory extends DefaultActivityBehaviorFactory
{
	
      @Override	
	  public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask, TaskDefinition taskDefinition) {
		    return new MyUserTaskActiviyBehavior(userTask.getId(),taskDefinition);
		  }


}
