package com.globallogic.creditcardpayment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Transaction;

@Service
public interface TransactionService {

	// GET ->SHOW ALL Transaction
	public List<Transaction> showTransaction();
	
	// GET ->SHOW Transaction By Id
	public Transaction showTransactionDetails(long id);
	
	//GET -> SHOW ALL TRANSACTIONS FOR CUSTOMER ID
	public String showAllTranscationsForCustomerId(long custId);

	// POST -> ADD Transaction
	public String addTransaction(Transaction transaction);

	// PUT ->UPDATE Transaction
	public String updateTransaction(Transaction transaction);

	// DELETE ->DELETE Transaction
	public String deleteTransaction(long id);
	
}
