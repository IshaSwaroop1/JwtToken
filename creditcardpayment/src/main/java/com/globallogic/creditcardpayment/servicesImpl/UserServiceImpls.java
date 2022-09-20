package com.globallogic.creditcardpayment.servicesImpl;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Role;
import com.globallogic.creditcardpayment.entity.Users;
import com.globallogic.creditcardpayment.repositories.RoleDao;
import com.globallogic.creditcardpayment.repositories.UserDao;



@Service
public class UserServiceImpls {
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private RoleDao roledao; 
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	public Users registerNewUser(Users user) {
		Role role = roledao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
		
	}
	public void initRolesAndUser() {
		Role adminRole=new Role();
		adminRole.setRolename("Admin");
		adminRole.setRoleDescription("Admin Role");
		roledao.save(adminRole);
		 
		Role userRole=new Role();
		userRole.setRolename("User");
		userRole.setRoleDescription("Default role for newly created record");
		roledao.save(userRole);
		
		Users adminUser=new Users();
		adminUser.setUserName("admin123");
		adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        
        Set<Role> adminRoles = new HashSet<>();
        adminUser.setRole(adminRoles);
        adminRoles.add(adminRole);
      
        userDao.save(adminUser);
        
  /*      User user = new User();
     user.setUserName("raj123");
     user.setUserPassword(getEncodedPassword("raj@123"));
    user.setUserFirstName("raj");
    user.setUserLastName("sharma");
      Set<Role> userRoles = new HashSet<>();
    userRoles.add(userRole);
    user.setRole(userRoles);
      userDao.save(user);*/
       
            
	}   
	  public String getEncodedPassword(String password) {
	        return passwordEncoder.encode(password);
	    }
}
