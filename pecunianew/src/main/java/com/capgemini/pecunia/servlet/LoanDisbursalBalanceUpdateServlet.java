package com.capgemini.pecunia.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.service.LoanDisbursalService;
import com.capgemini.pecunia.service.LoanDisbursalServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LoanDisbursalBalanceUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 2212946981490266552L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoanDisbursalService loanDisbursalService = new LoanDisbursalServiceImpl();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		ArrayList<Loan> retrieveAccepted = new ArrayList<Loan>();
		try {
			retrieveAccepted = loanDisbursalService.approveLoanWithoutStatus();
		} catch (PecuniaException | LoanDisbursalException e1) {

			e1.printStackTrace();
		}
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, Content-Length, X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");

		JsonArray jsonArray = new JsonArray();
		Gson gson = new Gson();
		JsonObject dataResponse = new JsonObject();
		ArrayList<LoanDisbursal> retrieveLoanDisbursedData = new ArrayList<LoanDisbursal>();
		try {
			retrieveLoanDisbursedData = loanDisbursalService.approvedLoanList();
		} catch (PecuniaException | LoanDisbursalException e1) {
			e1.printStackTrace();
		}

		try {
			ArrayList<String> msg = loanDisbursalService.updateExistingBalance(retrieveAccepted,
					retrieveLoanDisbursedData);
			if (msg.size() > 0) {
				for (String loanDisbursal : msg) {
					jsonArray.add(gson.toJson(loanDisbursal, String.class));
				}
				dataResponse.addProperty("success", true);
				dataResponse.add("data", jsonArray);
			}

		} catch (PecuniaException | LoanDisbursalException | TransactionException e) {
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
