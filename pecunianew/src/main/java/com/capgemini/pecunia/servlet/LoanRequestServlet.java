package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.service.LoanService;
import com.capgemini.pecunia.service.LoanServiceImpl;
import com.capgemini.pecunia.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoanRequest1
 */
public class LoanRequestServlet extends HttpServlet {
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

		Loan loan = new Loan();

		String accountId = jobj.get("accountId").getAsString();
		double amount = Double.parseDouble(jobj.get("amount").getAsString());
		String type = jobj.get("type").getAsString();
		double roi = Double.parseDouble(jobj.get("roi").getAsString());
		int creditScore = Integer.parseInt(jobj.get("creditScore").getAsString());
		int tenure = Integer.parseInt(jobj.get("tenure").getAsString());
		String status = jobj.get("status").getAsString();

		loan.setAccountId(accountId);
		loan.setAmount(amount);
		loan.setType(type);
		loan.setRoi(roi);
		loan.setCreditScore(creditScore);
		loan.setTenure(tenure);
		loan.setLoanStatus(status);

		LoanService loanService = new LoanServiceImpl();

		double emi = LoanServiceImpl.calculateEMI(amount, tenure, roi);
		loan.setEmi(emi);
		try {
			int loanId = loanService.createLoanRequest(loan);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("message", "Loan request successful");
			dataResponse.addProperty("Loan_id", loanId);

		} catch (LoanException e) {
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
