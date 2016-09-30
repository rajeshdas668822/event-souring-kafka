package org.springboot.eventbus.dao.impl;

import org.springboot.eventbus.dao.CurrentUserService;
import org.springboot.eventbus.entity.UserProfile;

/**
 * Primitive implementation that returns hardcoded user.
 * 
 * @author mondhs
 *
 */
public class CurrentUserServiceStaticImpl implements CurrentUserService{

	UserProfile currentUser;
	
	public CurrentUserServiceStaticImpl() {
		currentUser = new UserProfile();
		currentUser.setLoginName("qa");
		currentUser.setFirstName("Qa");
		currentUser.setLastName("Qa");
		currentUser.setEmailAddress("qa@qa.lt");
	}
	
	@Override
	public UserProfile getCurrentUser() {
		return currentUser;
	}

}
