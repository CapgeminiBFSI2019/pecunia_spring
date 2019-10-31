package com.capgemini.pecunia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.dto.Customer;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.AccountManagementService;
import com.capgemini.pecunia.util.Constants;
import com.google.gson.JsonObject;

@RestController
public class AccountController {
	
	@Autowired
	AccountManagementService ams;
	
	@Autowired
	Account account;
	@Autowired
	Customer customer;
	@Autowired
	Address address;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateName")
	public String updateCustomerName(@RequestBody Map<String, Object> requestData) {
		
		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String custName  = requestData.get("name").toString();
		
		account.setId(accountId);
		customer.setName(custName);
		boolean updated=false;
		
		try {
			updated = ams.updateCustomerName(account, customer);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_NAME_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} 
		return dataResponse.toString();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateContact")
	public String updateCustomerContact(@RequestBody Map<String, Object> requestData) {
		
		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String custContact  = requestData.get("contact").toString();
		
		account.setId(accountId);
		customer.setName(custContact);
		boolean updated=false;
		
		try {
			updated = ams.updateCustomerContact(account, customer);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_NAME_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} 
		return dataResponse.toString();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateAddress")
	public String updateCustomerAddress(@RequestBody Map<String, Object> requestData) {
		
		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String line1 = requestData.get("line1").toString();
		String line2 = requestData.get("line2").toString();
		String city = requestData.get("city").toString();
		String state = requestData.get("state").toString();
		String country = requestData.get("country").toString();
		String zipcode = requestData.get("zipcode").toString();
		
		account.setId(accountId);
		address.setLine1(line1);
		address.setLine2(line2);
		address.setCity(city);
		address.setCountry(country);
		address.setState(state);
		address.setZipcode(zipcode);
		
		boolean updated=false;
		
		try {
			updated = ams.updateCustomerAddress(account, address);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_NAME_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} 
		return dataResponse.toString();
	}
	
	
}
