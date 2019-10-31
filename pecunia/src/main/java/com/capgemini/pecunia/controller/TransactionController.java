package com.capgemini.pecunia.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.service.TransactionService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@RestController
public class TransactionController {

	@Autowired
	Transaction creditTransaction;
	@Autowired
	Cheque creditCheque;
	@Autowired
	TransactionService trans;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/creditCheque")
	public String creditUsingCheque(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();

		String payeeAccountNumber = requestData.get("payeeAccountNumber").toString();
		String beneficiaryAccountNumber = requestData.get("beneficiaryAccountNumber").toString();
		String chequeNumber = requestData.get("creditChequeNumber").toString();
		String payeeName = requestData.get("payeeName").toString();
		double amount = Double.parseDouble(requestData.get("creditChequeAmount").toString());
		LocalDate chequeIssueDate = LocalDate.parse(requestData.get("creditChequeIssueDate").toString());
		String bankName = requestData.get("bankName").toString();
		String ifsc = requestData.get("payeeIfsc").toString();

		System.out.println(payeeAccountNumber + "\n" + beneficiaryAccountNumber + "\n" + chequeNumber + "\n" + payeeName + "\n" + amount + "\n" + chequeIssueDate + "\n" + bankName + "\n" + ifsc);
		creditTransaction.setAmount(amount);
		creditTransaction.setAccountId(beneficiaryAccountNumber);
		creditTransaction.setTransTo(beneficiaryAccountNumber);
		creditTransaction.setTransFrom(payeeAccountNumber);

		creditCheque.setAccountNo(payeeAccountNumber);
		creditCheque.setHolderName(payeeName);
		creditCheque.setIfsc(ifsc);
		creditCheque.setIssueDate(chequeIssueDate);
		creditCheque.setNum(Integer.parseInt(chequeNumber));
		creditCheque.setBankName(bankName);

		try {
			int transId = trans.creditUsingCheque(creditTransaction, creditCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount credited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}
}
