package org.springboot.eventbus.dao;



import org.springboot.eventbus.entity.UserGroup;
import org.springboot.eventbus.entity.UserProfile;

import java.util.List;

/**
 * Simplified representation of custom User management DAO service.
 * It is called by {@link CustomUserEntityManager}.
 * @author rdas
 *
 */
public interface UserDao {

	List<UserProfile> findAll();

	UserProfile findUserByLoginName(String id);
	
	List<UserGroup> findGroupsByUser(String userId);
	UserProfile findUserFirstName(String firstName) ;
	UserProfile findUserFirstNameLike(String firstNameLike);
	UserProfile findUserLastName(String lastName);
	UserProfile findUserLastNameLike(String lastNameLike) ;
	UserProfile findUserFullNameLike(String fullNameLike);
	UserProfile findUserEmail(String email);
	UserProfile findMemberOfGroup(String groupId) ;
	UserProfile findPotentialStarter(String procDefId);
	UserProfile findOrderByUserId();
	UserProfile findOrderByUserFirstName();
	UserProfile findOrderByUserLastName() ;
	UserProfile findOrderByUserEmail();


}
