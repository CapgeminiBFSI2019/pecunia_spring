package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.LoginService;
import com.capgemini.pecunia.service.LoginServiceImpl;
import com.capgemini.pecunia.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LoginServlet extends HttpServlet {
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

		String username = jobj.get("username").getAsString();
		String password = jobj.get("password").getAsString();
		System.out.println("Here : " + username + " " + password);
		Login loginObject = new Login();
		loginObject.setUsername(username);
		loginObject.setPassword(password);
		LoginService loginService = new LoginServiceImpl();
		boolean result = false;

		try {

			result = loginService.validateEmail(loginObject);
			String name = request.getParameter("username");
			System.out.println("name - " + name);
			if (result) {
				HttpSession session = request.getSession();
				session.setAttribute("userLoggedIn", name);

				dataResponse.addProperty("userLoggedIn", name);
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.LOGIN_SUCCESSFUL);
			} else {

				throw new PecuniaException(ErrorConstants.LOGIN_ERROR);
			}

		} catch (PecuniaException | LoginException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} finally {
			out.print(dataResponse);
		}

	}

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
