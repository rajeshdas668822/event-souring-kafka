package org.springboot.eventbus.dao;

import org.activiti.engine.identity.GroupQuery;
import org.springboot.eventbus.entity.UserGroup;

import java.util.List;

/**
 * 
 * @author rdas
 *
 */

public interface GroupDao {
	
	
	
	public List<UserGroup> listAll(GroupQuery groupQuery);
	public List<UserGroup> listPage(int firstResult, int maxResults); 
	
	public UserGroup findByGroupId(String groupId) ;

	
	public UserGroup findByGroupName(String groupName);
	
	
	public UserGroup findByGroupNameLike(String groupNameLike);

	public UserGroup findByGroupType(String groupType);

	
	public UserGroup findByGroupMember(String groupMemberUserId);

	
	public UserGroup potentialStarter(String procDefId) ;

	
	public UserGroup orderByGroupId() ;

	
	public UserGroup orderByGroupName() ;


	public UserGroup orderByGroupType() ;
	
	

}
