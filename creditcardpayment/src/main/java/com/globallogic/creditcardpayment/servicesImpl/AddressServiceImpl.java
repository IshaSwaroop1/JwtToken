package com.globallogic.creditcardpayment.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Address;
import com.globallogic.creditcardpayment.repositories.AddressRepo;
import com.globallogic.creditcardpayment.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepo addressRepo;

	@Override
	public List<Address> showAddress() {
		return addressRepo.findAll();
	}

	@Override
	public List<Address> addAddress(Address address) {
		addressRepo.save(address);
		return addressRepo.findAll();
	}

	@Override
	public Address updateAddress(Address address) {
		addressRepo.save(address);
		return address;
	}

	@Override
	public String deleteAddress(long id) {
		addressRepo.deleteById(id);
		return "ADDRESS DELETED SUCCESSFULLY";
	}

}
