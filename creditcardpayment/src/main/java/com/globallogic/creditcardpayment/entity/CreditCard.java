package com.globallogic.creditcardpayment.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "creditcard")
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name = "cardName")
	String cardName;
	
	@Column(name = "cardType")
	String cardType;
	
	@Column(name = "cardNumber")
	String cardNumber;
	
	@Column(name = "expiryDate")
	LocalDate expiryDate;
	
	@Column(name = "bankName")
	String bankName;

	public CreditCard(long id, String cardName, String cardType, String cardNumber, LocalDate expiryDate, String bankName) {
		super();
		this.id = id;
		this.cardName = cardName;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.bankName = bankName;
	}

	public CreditCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "Creditcard [id=" + id + ", cardName=" + cardName + ", cardType=" + cardType + ", cardNumber="
				+ cardNumber + ", expiryDate=" + expiryDate + ", bankName=" + bankName + "]";
	}

}