package org.springboot.eventbus.entity;

import javax.persistence.*;

/**
 * Created by rdas on 9/8/2016.g.
 */

@Entity
@Table(name = "T_MEMBERSHIP")
@NamedQueries( { 
//@NamedQuery(name="findGroupByName",  query="SELECT g FROM UserGroupMapping g  join  g.user user WHERE user.loginName = :name"),
//@NamedQuery(name="user.findGroupByName",  query="SELECT c FROM UserGroupMapping c  c.user.loginName = :name")
}     )
public class UserGroupMapping {
	
	@Id	
	@Column(name = "USER_ID", unique = true, nullable = false )
	String userId;

    
	@OneToOne (cascade=CascadeType.ALL)	
    @JoinColumn(name="USER_ID",insertable=false, updatable=false)
    private UserProfile user;

	@OneToOne (cascade=CascadeType.ALL)	
    @JoinColumn(name="GROUP_ID")
    private UserGroup group;



    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
