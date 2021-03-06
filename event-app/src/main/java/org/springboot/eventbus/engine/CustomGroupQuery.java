package org.springboot.eventbus.engine;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.springboot.eventbus.dao.GroupDao;
import org.springboot.eventbus.domain.identity.CustomGroup;
import org.springboot.eventbus.entity.UserGroup;


import java.util.List;
import java.util.stream.Collectors;


public class CustomGroupQuery implements GroupQuery {
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	private GroupDao groupDao;
	private CustomGroup customGroup;
	private String userId;
	  
	
	 public CustomGroupQuery() {
		super();
	}


	 public GroupDao getGroupDao() {
		return groupDao;
	}




	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}




	public CustomGroup getCustomGroup() {
		return customGroup;
	}




	public void setCustomGroup(CustomGroup customGroup) {
		this.customGroup = customGroup;
	}




	  public CustomGroupQuery (CustomGroup customGroup){
		  this.customGroup = customGroup;
	  }




	@Override
	public GroupQuery asc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery desc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Group singleResult() {
		return customGroup;
	}

	@Override
	public List<Group> list() {
		// TODO Auto-generated method stub
		List<UserGroup> userGroups = groupDao.listAll(this);
		/*List<CustomGroup> customGroups = new ArrayList<>();
		return userGroups.stream().map(userGrp ->{
			List<UserGroupMapping> groupMapping = userGrp.getUserGroupMappings();
			groupMapping.forEach(userGrpMapping -> {
				if(userGrpMapping.getUser().getLoginName().equals(userId)){
					customGroups.add(new CustomGroup(userGrp.getGroupId(),userGrp.getType()));
				}
			});
			return customGroups;
		}).flatMap(p ->p.stream()).collect(Collectors.toList());
		*/

		return userGroups.stream().map(userGrp -> {
		 return new CustomGroup(userGrp.getName()
				 ,userGrp.getType()) ;
		}).collect(Collectors.toList());


	}

	@Override
	public List<Group> listPage(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery groupId(String groupId) {
		UserGroup userGrp = groupDao.findByGroupId(groupId);
		CustomGroup customUserGrp = new CustomGroup(userGrp.getName(), userGrp.getType());
		return new CustomGroupQuery(customUserGrp);
	}

	@Override
	public GroupQuery groupName(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery groupNameLike(String groupNameLike) {
		UserGroup userGrp = groupDao.findByGroupNameLike(groupNameLike);
		CustomGroup customUserGrp = new CustomGroup(userGrp.getName(), userGrp.getType());
		return new CustomGroupQuery(customUserGrp);
	}

	@Override
	public GroupQuery groupType(String groupType) {
		UserGroup userGrp = groupDao.findByGroupType(groupType);
		CustomGroup customUserGrp = new CustomGroup(userGrp.getName(), userGrp.getType());
		return new CustomGroupQuery(customUserGrp);
	}

	@Override
	public GroupQuery groupMember(String groupMemberUserId) {
		this.userId = groupMemberUserId;
		return this;
	}

	@Override
	public GroupQuery potentialStarter(String procDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery orderByGroupId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery orderByGroupName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupQuery orderByGroupType() {
		// TODO Auto-generated method stub
		return null;
	}

	  

	

}
