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
import com.google.gson.JsonObject;
//Autowiring Transaction service from DAO
@RestController
public class TransactionController {

	@Autowired
	Transaction creditChequeTransaction;
	@Autowired
	Cheque creditCheque;
	@Autowired
	TransactionService transactionService;
	@Autowired
	Transaction debitChequeTransaction;
	@Autowired
	Cheque debitCheque;
	@Autowired
	Transaction debitSlipTransaction;
	@Autowired
	Transaction creditSlipTransaction;
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/creditCheque")

	/*******************************************************************************************************
	 * - Function Name : creditUsingCheque(@RequestBody Map<String, Object>)
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Rohan patil - Creation Date :
	 * 02/11/2019 - Description : Credit Using Cheque
	 ********************************************************************************************************/

	public String creditUsingCheque(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();  // Creating json object

		String payeeAccountNumber = requestData.get("payeeAccountNumber").toString();
		String beneficiaryAccountNumber = requestData.get("beneficiaryAccountNumber").toString();
		String chequeNumber = requestData.get("creditChequeNumber").toString();
		String payeeName = requestData.get("payeeName").toString();
		double amount = Double.parseDouble(requestData.get("creditChequeAmount").toString());
		LocalDate chequeIssueDate = LocalDate.parse(requestData.get("creditChequeIssueDate").toString());
		String bankName = requestData.get("bankName").toString();
		String ifsc = requestData.get("payeeIfsc").toString();

		System.out.println(payeeAccountNumber + "\n" + beneficiaryAccountNumber + "\n" + chequeNumber + "\n" + payeeName
				+ "\n" + amount + "\n" + chequeIssueDate + "\n" + bankName + "\n" + ifsc);
		creditChequeTransaction.setAmount(amount);
		creditChequeTransaction.setAccountId(beneficiaryAccountNumber);
		creditChequeTransaction.setTransTo(beneficiaryAccountNumber);
		creditChequeTransaction.setTransFrom(payeeAccountNumber);

		creditCheque.setAccountNo(payeeAccountNumber);
		creditCheque.setHolderName(payeeName);
		creditCheque.setIfsc(ifsc);
		creditCheque.setIssueDate(chequeIssueDate);
		creditCheque.setNum(Integer.parseInt(chequeNumber));
		creditCheque.setBankName(bankName);

		try {
			int transId = transactionService.creditUsingCheque(creditChequeTransaction, creditCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount credited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/debitCheque")

	/*******************************************************************************************************
	 * - Function Name : debitUsingCheque(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Anish Basu - Creation Date :
	 * 02/11/2019 - Description : Debit Using Cheque
	 ********************************************************************************************************/

	public String debitUsingCheque(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();

		String accountNumber = requestData.get("accountNumber").toString();
		int debitChequeNumber = Integer.parseInt(requestData.get("debitChequeNumber").toString());
		String holderName = requestData.get("holderName").toString();
		double amount = Double.parseDouble(requestData.get("debitChequeAmount").toString());
		LocalDate chequeIssueDate = LocalDate.parse(requestData.get("issueDate").toString());
		String ifsc = requestData.get("ifsc").toString();

		System.out.println(accountNumber + "\n" + debitChequeNumber + "\n" + holderName + "\n" + amount + "\n"
				+ chequeIssueDate + "\n" + ifsc);
		debitChequeTransaction.setAmount(amount);
		debitChequeTransaction.setAccountId(accountNumber);

		debitCheque.setAccountNo(accountNumber);
		debitCheque.setHolderName(holderName);
		debitCheque.setIfsc(ifsc);
		debitCheque.setIssueDate(chequeIssueDate);
		debitCheque.setNum(debitChequeNumber);

		try {
			int transId = transactionService.debitUsingCheque(debitChequeTransaction, debitCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount debited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/debitSlip")

	/*******************************************************************************************************
	 * - Function Name : debitUsingSlip(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Anwesha Das - Creation Date :
	 * 02/11/2019 - Description : Debit Using Slip
	 ********************************************************************************************************/

	public String debitUsingSlip(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String accountNumber = requestData.get("accountNumber").toString();
		double amount = Double.parseDouble(requestData.get("debitSlipAmount").toString());
		System.out.println(accountNumber + amount + "\n");
		debitSlipTransaction.setAmount(amount);
		debitSlipTransaction.setAccountId(accountNumber);

		try {
			int transId = transactionService.debitUsingSlip(debitSlipTransaction);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount debited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/creditSlip")

	/*******************************************************************************************************
	 * - Function Name : creditUsingSlip(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Arpan Mondal - Creation Date :
	 * 02/11/2019 - Description : Credit Using Slip
	 ********************************************************************************************************/

	public String creditUsingSlip(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String accountNumber = requestData.get("accountNumber").toString();
		double amount = Double.parseDouble(requestData.get("creditSlipAmount").toString());
		System.out.println(accountNumber + amount + "\n");
		creditSlipTransaction.setAmount(amount);
		creditSlipTransaction.setAccountId(accountNumber);

		try {
			int transId = transactionService.creditUsingSlip(creditSlipTransaction);
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
