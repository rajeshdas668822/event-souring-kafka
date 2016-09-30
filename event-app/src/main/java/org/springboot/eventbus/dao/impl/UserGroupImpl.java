package org.springboot.eventbus.dao.impl;


import org.activiti.engine.identity.GroupQuery;
import org.springboot.eventbus.dao.GroupDao;
import org.springboot.eventbus.engine.CustomGroupQuery;
import org.springboot.eventbus.entity.UserGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository(value="groupDao")
public class UserGroupImpl implements GroupDao {

	
    @PersistenceContext
    EntityManager entityManager;
	 
	 
	@Override
	public List<UserGroup> listAll(GroupQuery customQuery) {		
		 return entityManager.createNamedQuery("group.findGroupByName", UserGroup.class)
				.setParameter("name", ((CustomGroupQuery)customQuery).getUserId()).getResultList();
		
		
	}

	@Override
	public List<UserGroup> listPage(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroup findByGroupId(String groupId) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserGroup> q = cb.createQuery(UserGroup.class);
		Root o = q.from(UserGroup.class);
		q.select(o);
		q.where(cb.equal(o.get("groupId"), groupId));
		return this.entityManager.createQuery(q).getSingleResult();
	}

	@Override
	public UserGroup findByGroupName(String groupName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserGroup> q = cb.createQuery(UserGroup.class);
		Root o = q.from(UserGroup.class);
		q.select(o);
		q.where(cb.equal(o.get("name"), groupName));
		return this.entityManager.createQuery(q).getSingleResult();
	}

	@Override
	public UserGroup findByGroupNameLike(String groupNameLike) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserGroup> q = cb.createQuery(UserGroup.class);
		Root o = q.from(UserGroup.class);
		q.select(o);
		q.where(cb.like(o.get("name"), groupNameLike));
		return this.entityManager.createQuery(q).getSingleResult();
	}

	@Override
	public UserGroup findByGroupType(String groupType) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserGroup> q = cb.createQuery(UserGroup.class);
		Root o = q.from(UserGroup.class);
		q.select(o);
		q.where(cb.like(o.get("type"), groupType));
		return this.entityManager.createQuery(q).getSingleResult();
	}

	@Override
	public UserGroup findByGroupMember(String groupMemberUserId) {
		UserGroup userGroup = entityManager.createNamedQuery("group.findGroupByName", UserGroup.class)
				.setParameter("name", groupMemberUserId).getSingleResult();		
		return userGroup;
	}

	@Override
	public UserGroup potentialStarter(String procDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroup orderByGroupId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroup orderByGroupName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroup orderByGroupType() {
		// TODO Auto-generated method stub
		return null;
	}

}
