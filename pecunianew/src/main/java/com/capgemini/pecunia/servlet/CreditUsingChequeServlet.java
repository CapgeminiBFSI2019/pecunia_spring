package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.service.TransactionService;
import com.capgemini.pecunia.service.TransactionServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class CreditUsingChequeServlet
 */
public class CreditUsingChequeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, Content-Length, X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
		}

		JsonObject dataResponse = new JsonObject();

		Gson gson = new Gson();
		JsonElement jelem = gson.fromJson(jb.toString(), JsonElement.class);
		JsonObject jobj = jelem.getAsJsonObject();

		Transaction creditTransaction = new Transaction();
		Cheque creditCheque = new Cheque();
		String payeeAccountNumber = jobj.get("payeeAccountNumber").getAsString();
		String beneficiaryAccountNumber = jobj.get("beneficiaryAccountNumber").getAsString();
		String chequeNumber = jobj.get("creditChequeNumber").getAsString();
		String payeeName = jobj.get("payeeName").getAsString();
		double amount = Double.parseDouble(jobj.get("creditChequeAmount").getAsString());
		LocalDate chequeIssueDate = LocalDate.parse(jobj.get("creditChequeIssueDate").getAsString());
		String bankName = jobj.get("bankName").getAsString();
		String ifsc = jobj.get("payeeIfsc").getAsString();


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

		TransactionService trans = new TransactionServiceImpl();
		try {
			int transId = trans.creditUsingCheque(creditTransaction, creditCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount credited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} finally {
			out.print(dataResponse);
		}
	}

	@Override
	public void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String reqOrigin = request.getHeader("Origin");
		if (reqOrigin == null) {
			reqOrigin = "*";
		}
		response.setHeader("Access-Control-Allow-Origin", reqOrigin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
	}

}
