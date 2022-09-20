package com.globallogic.creditcardpayment.entity;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Users {
  
	@Id
	private String userName;
	
	private String userPassword;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	 @JoinTable(name = "USERS_ROLE",
     joinColumns = {
             @JoinColumn(name = "USERS_ID")
     },
     inverseJoinColumns = {
             @JoinColumn(name = "ROLE_ID")
     }
)
private Set<Role> role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
}

