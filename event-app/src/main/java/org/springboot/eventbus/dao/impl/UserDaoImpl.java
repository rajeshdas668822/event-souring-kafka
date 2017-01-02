package org.springboot.eventbus.dao.impl;



import org.springboot.eventbus.dao.UserDao;
import org.springboot.eventbus.entity.UserGroup;
import org.springboot.eventbus.entity.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


 /* Static DAO implementation that contains collection of system user in map
 * 
 * @author rdas
 *
 */

@Repository(value="userDao")
public class UserDaoImpl implements UserDao{

	 @PersistenceContext
     EntityManager entityManager;

	Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();
	
	
	
	@Override
	public List<UserProfile> findAll() {
		return entityManager.createNamedQuery("user.findAll",UserProfile.class).getResultList();
	}

	@Override
	public UserProfile findUserByLoginName(String id) {
		return entityManager.createNamedQuery("user.findByName",UserProfile.class)
				.setParameter("name", id).getResultList().get(0);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserGroup> findGroupsByUser(String userId) {
		UserProfile userprofile = null;
		List<UserProfile> userprofiles = entityManager.createNamedQuery("user.findByName", UserProfile.class)
				.setParameter("name", userId).getResultList();
		if(userprofiles!=null && userprofiles.size()>0) {
			userprofile = (UserProfile) userprofiles.get(0);
		}
		if(userprofile == null)
			return null;
		Set<UserGroup> userGroups = userprofile.getUserGroups();
		return new ArrayList<>(userGroups);
	}

	@Override
	public UserProfile findUserFirstName(String firstName) {
		

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserProfile> q = cb.createQuery(UserProfile.class);
		Root o = q.from(UserProfile.class);
		q.select(o);
		q.where(cb.equal(o.get("firstName"), firstName));
		UserProfile userProfile = (UserProfile)this.entityManager.createQuery(q).getSingleResult();
		return userProfile;
		
	}

	@Override
	public UserProfile findUserFirstNameLike(String firstNameLike) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserProfile> q = cb.createQuery(UserProfile.class);
		Root o = q.from(UserProfile.class);
		q.select(o);
		q.where(cb.like(o.get("firstName"), firstNameLike));
		UserProfile userProfile = (UserProfile)this.entityManager.createQuery(q).getSingleResult();
		return userProfile;
	}

	@Override
	public UserProfile findUserLastName(String lastName) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserProfile> q = cb.createQuery(UserProfile.class);
		Root o = q.from(UserProfile.class);
		q.select(o);
		q.where(cb.like(o.get("lastName"), lastName));
		UserProfile userProfile = (UserProfile)this.entityManager.createQuery(q).getSingleResult();
		return userProfile;
	}

	@Override
	public UserProfile findUserLastNameLike(String lastNameLike) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserProfile> q = cb.createQuery(UserProfile.class);
		Root o = q.from(UserProfile.class);
		q.select(o);
		q.where(cb.like(o.get("lastName"), lastNameLike));
		UserProfile userProfile = (UserProfile)this.entityManager.createQuery(q).getSingleResult();
		return userProfile;
	}

	@Override
	public UserProfile findUserFullNameLike(String fullNameLike) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findUserEmail(String email) {		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserProfile> q = cb.createQuery(UserProfile.class);
		Root o = q.from(UserProfile.class);
		q.select(o);
		q.where(cb.like(o.get("emailAddress"), email));
		UserProfile userProfile = (UserProfile)this.entityManager.createQuery(q).getSingleResult();
		return userProfile;
	}

	@Override
	public UserProfile findMemberOfGroup(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findPotentialStarter(String procDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findOrderByUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findOrderByUserFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findOrderByUserLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProfile findOrderByUserEmail() {
		// TODO Auto-generated method stub
		return null;
	}



}
