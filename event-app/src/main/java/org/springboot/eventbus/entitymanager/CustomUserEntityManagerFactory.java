package org.springboot.eventbus.entitymanager;


import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

import org.springboot.eventbus.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Factory is used for defining for activiti that {@link CustomUserEntityManager} should be used instead of {@link UserEntityManager}.
 * This class is used from <code>./src/main/resources/META-INF/spring/custom-activiti-beans.xml</code>
 * 
 * @author mondhs
 *
 */

@Service
public class CustomUserEntityManagerFactory implements SessionFactory {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Class<?> getSessionType() {
		 return UserIdentityManager.class;
	}

	@Override
	public Session openSession() {
		CustomUserEntityManager aUserEntityManager = new CustomUserEntityManager();
		aUserEntityManager.setUserDao(userDao);
		return (Session) aUserEntityManager;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
