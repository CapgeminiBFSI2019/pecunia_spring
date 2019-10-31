package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.PassbookMaintenanceService;
import com.capgemini.pecunia.service.PassbookMaintenanceServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AccountSummaryServlet
 */
public class AccountSummaryServlet extends HttpServlet {
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

		JsonArray jsonArray = new JsonArray();
		Gson gson = new Gson();
		JsonElement jelem = gson.fromJson(jb.toString(), JsonElement.class);
		JsonObject jobj = jelem.getAsJsonObject();
		System.out.println("jonj :" + jobj);
		String accountId = jobj.get("accountID").getAsString();
		String strDate = jobj.get("startDate").getAsString();
		LocalDate startDate = LocalDate.parse(strDate);

		String enDate = jobj.get("endDate").getAsString();
		LocalDate endDate = LocalDate.parse(enDate);
		JsonObject dataResponse = new JsonObject();

		

		List<Transaction> accountSummaryDetails;
		PassbookMaintenanceService passbookService = new PassbookMaintenanceServiceImpl();

		try {
			accountSummaryDetails = passbookService.accountSummary(accountId, startDate, endDate);
			System.out.println("number of transactions" + accountSummaryDetails.size());
			if (accountSummaryDetails.size() > 0) {
				for (Transaction transaction : accountSummaryDetails) {
					jsonArray.add(gson.toJson(transaction, Transaction.class));
				}
				System.out.println("json array" + jsonArray);
				dataResponse.addProperty("success", true);
				dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "No summary to be displayed");
			}
		} catch (PecuniaException | PassbookException e) {
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
