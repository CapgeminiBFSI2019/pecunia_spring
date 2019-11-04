package com.capgemini.pecunia.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.PassbookMaintenanceService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class PassbookController {
	
	@Autowired
	Transaction accountSummary;
	@Autowired
	PassbookMaintenanceService Passbook;
	@Autowired
	PassbookMaintenanceService account;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/accountSummary")
	public String accountSummary(@RequestBody Map<String, Object> requestData) {
		


		String accountID = requestData.get("accountID").toString();
		LocalDate startDate = LocalDate.parse(requestData.get("startDate").toString());
		LocalDate endDate = LocalDate.parse(requestData.get("endDate").toString());
				
		JsonArray jsonArray = new JsonArray();
	
		Gson gson = new Gson();
	
		JsonObject dataResponse = new JsonObject();


		try {
			List<Transaction> accountSumm = account.accountSummary(accountID, startDate, endDate);
			if (accountSumm.size() > 0) {
				for (Transaction transaction : accountSumm) {
					jsonArray.add(gson.toJson(transaction, Transaction.class));
				}
			System.out.println("json array" + jsonArray);	
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("message", "Account Summary \t");
			dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "No summary to be displayed");
			}
			
		} catch (PassbookException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
		
	}

}
