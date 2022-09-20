package com.globallogic.creditcardpayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.creditcardpayment.entity.Account;
import com.globallogic.creditcardpayment.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping("/showAccount")
	public List<Account> showAccount() {
		return accountService.showAccount();
	}
	
	@GetMapping("/showAccountById/{id}")
	public Account showAccountById(@PathVariable("id") long id) {
		return accountService.showAccountById(id);
	}

	@PostMapping("/addAccount")
	public String addAccount(@RequestBody Account account) {
		return accountService.addAccount(account);
	}

	@PutMapping("/updateAccount")
	public String updateAccount(@RequestBody Account account) {
		return accountService.updateAccount(account);
	}

	@DeleteMapping("/deleteAccountById/{id}")
	public String deleteAccount(@PathVariable("id") long id) {
		return accountService.deleteAccount(id);
	}

}
