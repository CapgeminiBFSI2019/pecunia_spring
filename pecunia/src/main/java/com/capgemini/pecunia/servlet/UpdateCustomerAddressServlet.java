package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.AccountManagementService;
import com.capgemini.pecunia.service.AccountManagementServiceImpl;
import com.capgemini.pecunia.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UpdateCustomerAddressServlet extends HttpServlet {
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

		String accountId = jobj.get("accountId").getAsString();

		String line1 = jobj.get("line1").getAsString();
		String line2 = jobj.get("line2").getAsString();
		String city = jobj.get("city").getAsString();
		String state = jobj.get("state").getAsString();
		String country = jobj.get("country").getAsString();
		String zipcode = jobj.get("zipcode").getAsString();

		Account account = new Account();
		Address address = new Address();
		account.setId(accountId);
		address.setLine1(line1);
		address.setLine2(line2);
		address.setCity(city);
		address.setCountry(country);
		address.setState(state);
		address.setZipcode(zipcode);

		AccountManagementService ams = new AccountManagementServiceImpl();
				boolean updated = false;
		try {
			updated = ams.updateCustomerAddress(account, address);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_ADDRESS_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
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
