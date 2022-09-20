package com.globallogic.creditcardpayment.controller;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.creditcardpayment.entity.Users;

import com.globallogic.creditcardpayment.servicesImpl.UserServiceImpls;



@RestController
public class UsersController {
 
	@Autowired
	private UserServiceImpls userService;
	@PostConstruct
    public void initRolesAndUser() {
        userService.initRolesAndUser();
    }
	@PostMapping({"/registerNewUser"})
	public Users registerNewUser(@RequestBody Users user) {
	 return	userService.registerNewUser(user);
	}
	@GetMapping({"/AdminLoginIn"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This URL is only accessible to the Admin";
	}
	@GetMapping({"/UserLoginIn"})
	 @PreAuthorize("hasRole('User')")
	public String forUser(){
		return "This URL is only accessible to the User";
	}
}
