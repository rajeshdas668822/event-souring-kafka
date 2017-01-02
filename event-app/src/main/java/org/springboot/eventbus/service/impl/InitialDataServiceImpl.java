package org.springboot.eventbus.service.impl;

import org.activiti.engine.impl.util.CollectionUtil;
import org.springboot.eventbus.dao.DefaultDao;
import org.springboot.eventbus.dao.GroupDao;
import org.springboot.eventbus.dao.UserDao;
import org.springboot.eventbus.entity.UserGroup;
import org.springboot.eventbus.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdas on 12/11/2016.
 */
@Service
public class InitialDataServiceImpl  implements InitialDataService{

    @Autowired
    DefaultDao defaultDao;

    @Autowired
    UserDao userDao;

    @Autowired
    GroupDao groupDao;


    @PostConstruct
    public void populateUserAndGroup(){
        List<UserProfile> userProfileList = new ArrayList<>();

        UserGroup checkerGroup = new UserGroup();
        checkerGroup.setName("checker");
        checkerGroup.setType("Checker");
        checkerGroup.setRevision(1);
        checkerGroup = defaultDao.insert(checkerGroup);

        UserGroup makerGroup = new UserGroup();
        makerGroup.setName("maker");
        makerGroup.setType("Maker");
        makerGroup.setRevision(1);
        makerGroup = defaultDao.insert(makerGroup);

        UserGroup salesGroup = new UserGroup();
        salesGroup.setName("Sales");
        salesGroup.setType("Sales");
        salesGroup.setRevision(1);
        salesGroup = defaultDao.save(salesGroup);

        UserProfile userProfile = new UserProfile();
        userProfile.setRevision(1);
        userProfile.setFirstName("Rajesh");
        userProfile.setEmailAddress("rajeshranjan.d@gmail.com");
        userProfile.setLoginName("rajesh");
        userProfile.setLastName("Das");
        userProfile.setPassword("rajesh");

        userProfile.getUserGroups().add(salesGroup);
        userProfile.getUserGroups().add(checkerGroup);

       // userProfileList.add(userProfile);
        userProfile = defaultDao.save(userProfile);

        UserProfile  userProfile2 = new UserProfile();
        userProfile2.setRevision(1);
        userProfile2.setFirstName("Kermit");
        userProfile2.setEmailAddress("kermit@activiti.org");
        userProfile2.setLoginName("kermit");
        userProfile2.setLastName("The Frog");
        userProfile2.setPassword("kermit");

        userProfile2.getUserGroups().add(salesGroup);
        userProfile2.getUserGroups().add(makerGroup);

        userProfile2 = defaultDao.save(userProfile2);

       UserProfile fozzieUser = new UserProfile();
        fozzieUser.setRevision(1);
        fozzieUser.setFirstName("Fozzie");
        fozzieUser.setEmailAddress("fozzie@activiti.org");
        fozzieUser.setLoginName("fozzie");
        fozzieUser.setLastName("Bear");
        fozzieUser.setPassword("fozzie");
        fozzieUser.getUserGroups().add(salesGroup);
        fozzieUser.getUserGroups().add(makerGroup);
        userProfile = defaultDao.save(fozzieUser);

        verifyAndPrintResult();

    }


    public void verifyAndPrintResult(){
      List<UserProfile>  userProfiles=  userDao.findAll();
      System.out.println(userProfiles.size());
      List<Object> objectList = defaultDao.getByNamedQuery("group.findAll",null);
      System.out.println(objectList.size());
        List<UserGroup> userGroupList = userDao.findGroupsByUser("kermit");
        if(userGroupList!=null){
            for (UserGroup userGroup : userGroupList) {
                System.out.println(userGroup);
            }
        }


    }





}
