package com.globallogic.creditcardpayment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Address;


@Service
public interface AddressService {

	// GET ->SHOW ALL Address
	public List<Address> showAddress();

	// POST -> ADD Address
	public List<Address> addAddress(Address address);

	// PUT ->UPDATE Address
	public Address updateAddress(Address address);

	// DELETE ->DELETE Address
	public String deleteAddress(long id);

}
