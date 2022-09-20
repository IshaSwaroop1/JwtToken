package com.globallogic.creditcardpayment.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.creditcardpayment.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
	
	@Modifying
	@Transactional
	@Query("update Transaction t set t.cardNumber = :cardNumber where t.customer.id = :id")
	public void setCardNumberForTransaction(String cardNumber,long id);
	
	//@Query("select t from Transaction t where t.customer.id = :custId")
	public List<Transaction> findAllByCustomerId(long custId);
	
	@Query("select sum(t.amount) from Transaction t where t.tranDate > :start And t.tranDate <= :end And t.customer.id = :customerId And t.status= :status")
	public double findAllTransactionsTranDate(LocalDate start , LocalDate end , long customerId , String status);
	

	@Query("select t from Transaction t where t.tranDate > :start And t.tranDate <= :end And t.customer.id = :customerId And t.status= :status")
	public List<Transaction> findAllTransactionTranDate(LocalDate start , LocalDate end , long customerId , String status);
	
}