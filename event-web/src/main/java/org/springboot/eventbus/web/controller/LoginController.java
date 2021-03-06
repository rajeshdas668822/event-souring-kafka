package org.springboot.eventbus.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springboot.eventbus.domain.User;
import org.springboot.eventbus.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
	
	public Map<String,User> existingUsers = new HashMap<String, User>();
	
	@PostConstruct
	public void initUserDetails(){
		
		User user = new User();
		user.setUserId("kermit");
		user.setPassword("kermit");
		//user.setUserName(userName);
		existingUsers.put(user.getUserId(),user);
		
		user = new User();
		user.setUserId("fozzie");
		user.setPassword("fozzie");
		existingUsers.put(user.getUserId(),user);
		
		user = new User();
		user.setUserId("rajesh");
		user.setPassword("rajesh");
		//user.setUserName(userName);
		existingUsers.put(user.getUserId(),user);
		
		
		user = new User();
		user.setUserId("gulzar");
		user.setPassword("gulzar");
		//user.setUserName(userName);
		existingUsers.put(user.getUserId(),user);
		
		
		
		
	}
	
   @RequestMapping(value="/validateUser",method = RequestMethod.POST)
   @ResponseBody
   public  boolean verifyUser(@RequestBody User user){
		boolean validUser=false;
		User storedUser = existingUsers.get(user.getUserId());
		if(storedUser.getPassword().equals(user.getPassword())){
			validUser = true;
		}
		 
		System.out.println("Valid User : "+validUser);
		return validUser;
		 
	 }
   
   
   
   @RequestMapping(value="/users",method = RequestMethod.GET)
   @ResponseBody
   public  Collection<User> lodUser(){		
	   return existingUsers.values();
	 }

}
