package com.globallogic.creditcardpayment.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.globallogic.creditcardpayment.entity.Users;




@Repository
public interface UserDao extends CrudRepository<Users,String>{

}
