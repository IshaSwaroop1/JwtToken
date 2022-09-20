package com.globallogic.creditcardpayment.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "statement")
public class Statement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long statementid;

	@Column(name = "dueAmount")
	double dueAmount;

	@Column(name = "billingDate")
	LocalDate billingDate;

	@Column(name = "dueDate")
	LocalDate dueDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	Customer customer;

	public Statement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Statement(long statementid, double dueAmount, LocalDate billingDate, LocalDate dueDate, Customer customer) {
		super();
		this.statementid = statementid;
		this.dueAmount = dueAmount;
		this.billingDate = billingDate;
		this.dueDate = dueDate;
		this.customer = customer;
	}

	public long getStatementid() {
		return statementid;
	}

	public void setStatementid(long statementid) {
		this.statementid = statementid;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public LocalDate getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(LocalDate billingDate) {
		this.billingDate = billingDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Statement [statementid=" + statementid + ", dueAmount=" + dueAmount + ", billingDate=" + billingDate
				+ ", dueDate=" + dueDate + ", customer=" + customer + "]";
	}

}