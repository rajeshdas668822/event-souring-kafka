package org.springboot.eventbus.entitymanager;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springboot.eventbus.dao.GroupDao;
import org.springboot.eventbus.dao.UserDao;
import org.springboot.eventbus.domain.identity.CustomGroup;
import org.springboot.eventbus.engine.CustomGroupQuery;
import org.springboot.eventbus.entity.UserGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomGroupEntityManager  extends GroupEntityManager{

	private GroupDao groupDao;	
	
	private UserDao userDao;

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	
	
	@Override
	public List<Group> findGroupsByUser(String userId) {	
	 List<UserGroup> userGroups =	userDao.findGroupsByUser(userId);
	 return userGroups.stream().map(userGrp ->{
		 return new CustomGroup(userGrp.getGroupId(), userGrp.getType());
	 }).collect(Collectors.toList());
		 
	}
	
	
	@Override
	public GroupQuery createNewGroupQuery() {
		CustomGroupQuery customGroupQuery =	new CustomGroupQuery();
		customGroupQuery.setGroupDao(groupDao);
		return customGroupQuery;
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

}
