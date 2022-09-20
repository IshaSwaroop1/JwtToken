package com.globallogic.creditcardpayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.creditcardpayment.entity.Address;
import com.globallogic.creditcardpayment.services.AddressService;



@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	AddressService addressService;

	@GetMapping("/")
	public List<Address> showAddress() {
		return addressService.showAddress();
	}

	@PostMapping("/")
	public List<Address> addAddress(@RequestBody Address Address) {
		return addressService.addAddress(Address);
	}

	@PutMapping("/")
	public Address updateAddress(@RequestBody Address Address) {
		return addressService.updateAddress(Address);
	}

	@DeleteMapping("/{id}")
	public String deleteAddress(@PathVariable("id") long id) {
		return addressService.deleteAddress(id);
	}

}
