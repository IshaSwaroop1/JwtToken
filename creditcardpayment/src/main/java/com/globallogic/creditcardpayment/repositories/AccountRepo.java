package com.globallogic.creditcardpayment.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.creditcardpayment.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	@Modifying
	@Transactional
	@Query("update Account a set a.balance = :balance where a.id = :id")
	public void setBalanceForAccount(double balance,long id);

	
	
	
}