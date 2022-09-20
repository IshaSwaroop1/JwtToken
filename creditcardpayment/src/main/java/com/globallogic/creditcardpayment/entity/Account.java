package com.globallogic.creditcardpayment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long accountid;
	
	@Column
	String accountName;
	
	@Column
	double balance;
	
	@Column
	String accountType;
	
	public long getAccountid() {
		return accountid;
	}
	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public Account(long accountid, String accountName, double balance, String accountType) {
		super();
		this.accountid = accountid;
		this.accountName = accountName;
		this.balance = balance;
		this.accountType = accountType;
	}
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Account [accountid=" + accountid + ", accountName=" + accountName + ", balance=" + balance
				+ ", accountType=" + accountType + "]";
	}
	
	
}