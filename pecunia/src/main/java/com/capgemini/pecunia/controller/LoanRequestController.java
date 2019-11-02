package com.capgemini.pecunia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.service.LoanService;
import com.google.gson.JsonObject;

@RestController
public class LoanRequestController {
	@Autowired
	Loan loan;
	@Autowired
	LoanService loanService;
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/LoanRequest")
	public String Loanrequest(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String accountId=requestData.get("accountId").toString();
		double amount=Double.parseDouble(requestData.get("amount").toString());
		String type=dataResponse.get("type").toString();
		int tenure=Integer.parseInt(dataResponse.get("tenure").toString());
		double roi=Double.parseDouble(dataResponse.get("roi").toString());
		String loanStatus=dataResponse.get("status").toString();
		//double emi=Double.parseDouble(dataResponse.get("emi").toString());
		int creditScore=Integer.parseInt(dataResponse.get("creditScore").toString());
		
		loan.setAccountId(accountId);
		loan.setAmount(amount);
		loan.setCreditScore(creditScore);
		//loan.setEmi(emi);
		loan.setLoanStatus(loanStatus);
		loan.setType(type);
		loan.setTenure(tenure);
		loan.setRoi(roi);

		try {
			int loanId = loanService.createLoanRequest(loan);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", loanId);
			dataResponse.addProperty("message", "Loan Request added succesfully \t" + loanId);

		} catch (LoanException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}
}
		



