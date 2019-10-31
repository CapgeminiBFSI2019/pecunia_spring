package com.capgemini.pecunia.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;


public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
		
		
		 HttpSession session=request.getSession(false);  
	        if(session!=null){  
	        String name=(String)session.getAttribute("userLoggedIn");
         session.invalidate();
         dataResponse.addProperty("userLoggedOff", name);
         out.print(dataResponse);
	        }
         
 
		
	}

}
