package com.capgemini.pecunia.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.LoanDisbursalService;
import com.capgemini.pecunia.service.LoanDisbursalServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class LoanDisbursalDataController {
	@Autowired
	LoanDisbursal loanDisbursal;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = "/loandisbursaldata")
	public String retrieveRequests() throws IOException {
		JsonObject dataResponse = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		Gson gson = new Gson();
		ArrayList<LoanDisbursal> retrieveLoanDisbursedData = new ArrayList<LoanDisbursal>();
		LoanDisbursalService loanDisbursalService = new LoanDisbursalServiceImpl();
		try {
			retrieveLoanDisbursedData = loanDisbursalService.approvedLoanList();
			if (retrieveLoanDisbursedData.size() > 0) {
				for (LoanDisbursal loanDisbursal : retrieveLoanDisbursedData) {
					jsonArray.add(gson.toJson(loanDisbursal, LoanDisbursal.class));
				}
				dataResponse.addProperty("success", true);
				dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "No transaction to update");
			}
		} catch (PecuniaException | LoanDisbursalException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();

	}
}
