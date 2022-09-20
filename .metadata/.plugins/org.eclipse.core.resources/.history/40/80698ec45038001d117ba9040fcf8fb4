package com.globallogic.creditcardpayment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.User;

@Service
public interface UserService {

	// GET -> CHECK ADMIN LOGIN
	public String adminSignIn(String id, String password);

	// GET -> CHECK CUSTOMER LOGIN
	public String customerSignIn(String id, String password);
	
	//GET ->SHOW ALL USER(USERID AND PASSWORD)
	public List<User> showUser();
	
	//POST ->ADD USER(USERID AND PASSWORD)
	public List<User> addUser(User user);
	
	//PUT ->UPDATE USER(USERID AND PASSWORD)
	public User updateUser(User user);
	
	//DELETE ->DELETE USER
	public String deleteUser(long id);
	

}
