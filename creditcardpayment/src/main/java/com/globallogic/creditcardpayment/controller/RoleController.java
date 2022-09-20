package com.globallogic.creditcardpayment.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.creditcardpayment.entity.Role;
import com.globallogic.creditcardpayment.servicesImpl.RoleServiceImpl;


@RestController
public class RoleController {
	@Autowired
	private RoleServiceImpl roleservice;
     
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleservice.createNewRole(role);
	}
}

