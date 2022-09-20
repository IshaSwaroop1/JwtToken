package com.globallogic.creditcardpayment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Account;

@Service
public interface AccountService {

	// GET ->SHOW ALL Account
	public List<Account> showAccount();

	// GET ->SHOW Account By Id 
	public Account showAccountById(long id);

	// POST -> ADD Account
	public String addAccount(Account account);

	// PUT ->UPDATE Account
	public String updateAccount(Account account);

	// DELETE ->DELETE Account
	public String deleteAccount(long id);

}
