package demo.entity;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springboot.eventbus.dao.GroupDao;
import org.springboot.eventbus.engine.CustomGroupQuery;
import org.springboot.eventbus.entity.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class CustomGroupEntityManagerTest {
	
	
	@Autowired
	GroupDao groupDao;
	
	@Test
	public void testUserGroup(){
		
		CustomGroupQuery customGroupQuery = new CustomGroupQuery();
		customGroupQuery.setUserId("kermit");
		List<UserGroup> userGroupList = groupDao.listAll(customGroupQuery);
		assertNotNull(userGroupList);
		assertEquals(2, userGroupList.size());
		
	}
	
	

}
