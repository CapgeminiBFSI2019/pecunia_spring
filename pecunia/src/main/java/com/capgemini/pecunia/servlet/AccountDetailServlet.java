package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.AccountManagementService;
import com.capgemini.pecunia.service.AccountManagementServiceImpl;
import com.capgemini.pecunia.service.LoanDisbursalService;
import com.capgemini.pecunia.service.LoanDisbursalServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AccountDetailServlet
 */
public class AccountDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountDetailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AccountManagementService accountService = new AccountManagementServiceImpl();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, Content-Length, X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
//		StringBuffer jb = new StringBuffer();
//		String line = null;
//		try {
//			BufferedReader reader = request.getReader();
//			while ((line = reader.readLine()) != null)
//				jb.append(line);
//		} catch (Exception e) {
//		}
//
		Gson gson = new Gson();
		JsonObject dataResponse = new JsonObject();
		
		String accountId = request.getParameter("accountId");
		System.out.println(accountId);
		Account account = new Account();
		account.setId(accountId);
		Account accountRequested = new Account();
		try {
			accountRequested= accountService.showAccountDetails(account);
			dataResponse.addProperty("message", "Successfull");
			dataResponse.addProperty("data", gson.toJson(accountRequested, Account.class));
		} catch (AccountException | PecuniaException e) {
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
