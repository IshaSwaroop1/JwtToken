package com.globallogic.creditcardpayment.services;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Payment;
import com.globallogic.creditcardpayment.entity.Response;

@Service
public interface PaymentService {

	// GET ->SHOW ALL PAYMENT
	public Response showPayment();
	
	// GET ->SHOW PAYMENT BY ID
	public Payment showPaymentById(long id);

	// POST -> ADD PAYMENT
	public Response addPayment(Payment payment);

	// PUT ->UPDATE PAYMENT
	public Payment updatePayment(Payment payment);

	// DELETE ->DELETE PAYMENT
	public String deletePayment(long id);

	

}
