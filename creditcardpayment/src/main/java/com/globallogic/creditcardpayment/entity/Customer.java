package com.globallogic.creditcardpayment.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

//	@OneToOne(cascade = CascadeType.PERSIST)
//	User user;

	@Column
	String name;

	@Column
	String email;

	@Column
	long phoneNo;

	@Column
	LocalDate dateOfBirth;

	@OneToMany(cascade = CascadeType.PERSIST)
	List<Address> address;

	@OneToOne(cascade = CascadeType.PERSIST)
	Account account;

	@OneToOne(cascade = CascadeType.PERSIST)
	CreditCard creditCard;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(long id, String name, String email, long phoneNo, LocalDate dateOfBirth, List<Address> address,
			Account account, CreditCard creditCard) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.account = account;
		this.creditCard = creditCard;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + ", account=" + account + ", creditCard=" + creditCard + "]";
	}


	
}
