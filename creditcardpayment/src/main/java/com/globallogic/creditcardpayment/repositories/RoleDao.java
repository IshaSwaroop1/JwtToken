package com.globallogic.creditcardpayment.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.globallogic.creditcardpayment.entity.Role;



@Repository
public interface RoleDao extends CrudRepository<Role,String>{

}