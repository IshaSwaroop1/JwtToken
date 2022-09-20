package com.globallogic.creditcardpayment.servicesImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.CreditCard;
import com.globallogic.creditcardpayment.entity.Customer;
import com.globallogic.creditcardpayment.exceptionHandling.GlobalException;
import com.globallogic.creditcardpayment.exceptionHandling.InvalidCreditCardNumberException;
import com.globallogic.creditcardpayment.entity.CreditCard;
import com.globallogic.creditcardpayment.repositories.CreditCardRepo;
import com.globallogic.creditcardpayment.repositories.CustomerRepo;
import com.globallogic.creditcardpayment.repositories.TransactionRepo;
import com.globallogic.creditcardpayment.services.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	CreditCardRepo creditCardRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	TransactionRepo transactionRepo;

	@Override
	public List<CreditCard> showCreditCard() {
		return creditCardRepo.findAll();
	}

	@Override
	public CreditCard showCreditCardById(long cardId) {
		return creditCardRepo.findById(cardId).get();
	}

	@Override
	public String addCreditCard(CreditCard creditCard) {
		if (creditCard.getBankName().isEmpty() || creditCard.getCardName().isEmpty()
				|| creditCard.getCardNumber().isEmpty() || creditCard.getCardType().isEmpty()
				|| creditCard.getExpiryDate() == null) {
			throw new GlobalException();
		} else {
			if (creditCard.getCardNumber().length() != 12) {
				throw new InvalidCreditCardNumberException();
			}
		}
		creditCardRepo.save(creditCard);
		return "CREDIT CARD DETAILS ADDED SUCCESSFULLY";
	}

	@Override
	public String updateCreditCard(CreditCard creditCard) {
		if (creditCardRepo.findById(creditCard.getId()).get() != null) {
			if (creditCard.getBankName().isEmpty() || creditCard.getCardName().isEmpty()
					|| creditCard.getCardNumber().isEmpty() || creditCard.getCardType().isEmpty()
					|| creditCard.getExpiryDate() == null) {
				throw new GlobalException();
			} else {
				if (creditCard.getCardNumber().length() != 12) {
					throw new InvalidCreditCardNumberException();
				}
			}
		} else {
			throw new GlobalException();
		}
		Customer customer = customerRepo.findByCreditCardId(creditCard.getId());
		if(customer != null) {
		transactionRepo.setCardNumberForTransaction(creditCard.getCardNumber(), customer.getId());
		}
		creditCardRepo.save(creditCard);
		return "CREDIT CARD DETAILS UPDATED SUCCESSFULLY";
	}

	@Override
	public String deleteCreditCard(long id) {
		if (creditCardRepo.findById(id).get() == null) {
			throw new GlobalException();
		} else {
			CreditCard cc = creditCardRepo.findById(id).get();
			Customer customer = customerRepo.findByCreditCardId(cc.getId());
			if(customer != null) {
				return "CREDIT CARD DETAILS CANNOT BE DELETED AS MAPPING DONE WITH CUSTOMER";
			}	
			creditCardRepo.deleteById(id);
			return "CREDIT CARD DELETED SUCCESSFULLY";
		}
	}

}
