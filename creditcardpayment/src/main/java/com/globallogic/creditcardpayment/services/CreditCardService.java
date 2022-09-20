package com.globallogic.creditcardpayment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.CreditCard;

@Service
public interface CreditCardService {

	// GET ->SHOW ALL CreditCard
	public List<CreditCard> showCreditCard();
	
	// GET ->SHOW CreditCard By Id
	public CreditCard showCreditCardById(long cardId);

	// POST -> ADD CreditCard
	public String addCreditCard(CreditCard creditCard);

	// PUT ->UPDATE CreditCard
	public String updateCreditCard(CreditCard creditCard);

	// DELETE ->DELETE CreditCard
	public String deleteCreditCard(long id);

	

}
