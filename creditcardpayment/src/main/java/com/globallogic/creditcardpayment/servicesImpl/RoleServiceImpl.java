package com.globallogic.creditcardpayment.servicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Role;
import com.globallogic.creditcardpayment.repositories.RoleDao;



@Service
public class RoleServiceImpl {
	@Autowired
	private RoleDao roledao;
	
	public Role createNewRole(Role role) {
		return roledao.save(role);
		
	}
}

