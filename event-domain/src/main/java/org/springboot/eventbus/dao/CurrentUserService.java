package org.springboot.eventbus.dao;


import org.springboot.eventbus.entity.UserProfile;

public interface CurrentUserService {

	UserProfile getCurrentUser();

}
