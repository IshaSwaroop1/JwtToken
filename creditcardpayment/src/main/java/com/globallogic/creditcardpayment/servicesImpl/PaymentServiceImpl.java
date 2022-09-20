package com.globallogic.creditcardpayment.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.globallogic.creditcardpayment.entity.Payment;
import com.globallogic.creditcardpayment.entity.Response;
import com.globallogic.creditcardpayment.repositories.PaymentRepo;
import com.globallogic.creditcardpayment.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentRepo paymentRepo;

	@Override
	public Response showPayment() {
		List<Payment> payments = paymentRepo.findAll();
		return new Response("number of Payments:"+ payments.size(), Boolean.TRUE);
	}

	@Override
	public Payment showPaymentById(long id) {
		return paymentRepo.findById(id).get();
	}
	
	@Override
	public Response addPayment(Payment payment) {
		paymentRepo.save(payment);
		return new Response("saved payment with id" + payment.getPaymentid(), Boolean.TRUE);
	}

	@Override
	public Payment updatePayment(Payment payment) {
		paymentRepo.save(payment);
		return payment;
	}

	@Override
	public String deletePayment(long id) {
		paymentRepo.deleteById(id);
		return "PAYMENT DELETED SUCCESSFULLY";
	}



}
