package com.globallogic.creditcardpayment.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Account;
import com.globallogic.creditcardpayment.entity.CreditCard;
import com.globallogic.creditcardpayment.entity.Customer;
import com.globallogic.creditcardpayment.exceptionHandling.GlobalException;
import com.globallogic.creditcardpayment.entity.Account;
import com.globallogic.creditcardpayment.repositories.AccountRepo;
import com.globallogic.creditcardpayment.repositories.CustomerRepo;
import com.globallogic.creditcardpayment.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Override
	public List<Account> showAccount() {
		return accountRepo.findAll(); // displaying all details of table account
	}

	@Override
	public Account showAccountById(long id) {
		return accountRepo.findById(id).get(); // displaying details of account having particular id
	} // and if id not present then error is thrown ("NO SUCH ID EXISTS IN DATABASE",
		// HttpStatus.NOT_FOUND)

	@Override
	public String addAccount(Account account) {

		// checking if any data is empty then exception thrown
		if (account.getAccountName().isEmpty() || account.getAccountType().isEmpty() || account.getBalance() == 0) {
			throw new GlobalException(); // ("INPUT FIELD IS EMPTY,PLEASE CHECK", HttpStatus.BAD_REQUEST)
		}

		accountRepo.save(account); // adding data in database
		return "ACCOUNT DETAILS ADDED SUCCESSFULLY";
	}

	@Override
	public String updateAccount(Account account) {

		// checking if any data is empty then exception thrown
		if (account.getAccountid() == 0 || account.getAccountName().isEmpty() || account.getAccountType().isEmpty()
				|| account.getBalance() == 0) {
			throw new GlobalException(); // ("INPUT FIELD IS EMPTY,PLEASE CHECK", HttpStatus.BAD_REQUEST)
		}

		// checking if given id is present in database
		if (accountRepo.findById(account.getAccountid()).get() == null) {
			throw new GlobalException(); // ("NO SUCH ID EXISTS IN DATABASE", HttpStatus.NOT_FOUND)
		}

		accountRepo.save(account); // updating data in database
		return "ACCOUNT DETAILS UPDATED SUCCESSFULLY";
	}

	@Override
	public String deleteAccount(long id) {
		
		//checking if given id is present in database
		if (accountRepo.findById(id).get() == null) {
			throw new GlobalException();       //("NO SUCH ID EXISTS IN DATABASE", HttpStatus.NOT_FOUND) 
		} else {
			Account account = accountRepo.findById(id).get();
			Customer customer = customerRepo.findByAccountAccountid(account.getAccountid());
			if (customer != null) {
				return "ACCOUNT DETAILS CANNOT BE DELETED AS MAPPING DONE WITH CUSTOMER";  //returning message
			}
			accountRepo.deleteById(id);             //deleting account if mapping not done
			return "ACCOUNT DETAILS DELETED SUCCESSFULLY";
		}

	}
}
