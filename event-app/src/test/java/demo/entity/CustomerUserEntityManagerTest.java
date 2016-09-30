package demo.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.eventbus.dao.UserDao;
import org.springboot.eventbus.entity.UserGroup;
import org.springboot.eventbus.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class CustomerUserEntityManagerTest {
	
	@Autowired
	private UserDao userDao;
	
	
	@Test
	public void testFindUser(){		
		assertNotNull(userDao);		
		UserProfile userProfile = userDao.findUserByLoginName("fozzie");
		assertNotNull(userProfile);
		assertEquals("fozzie", userProfile.getLoginName());
		
	}
	
	
	
	@Test
	public void testFindGroupByUser(){		
		assertNotNull(userDao);		
		List<UserGroup> groupList= userDao.findGroupsByUser("kermit");
		assertNotNull(groupList);
		assertEquals(2, groupList.size());
		
		groupList.forEach(group -> System.out.println(group.getGroupId()+ " -"+group.getType()));
	}
	

}
