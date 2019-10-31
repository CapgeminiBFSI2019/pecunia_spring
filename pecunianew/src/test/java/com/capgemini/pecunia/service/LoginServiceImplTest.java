package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;


class LoginServiceImplTest {
	
	LoginServiceImpl login;

	@BeforeEach
	void setUp() throws Exception {
		login = new LoginServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		login = null;
	}

	@Test
	@DisplayName("Input parameter object is null")
	void testValidateEmailNull() {
		assertThrows(NullPointerException.class, ()-> { login.validateEmail(null);});
	}

	@Test
	@DisplayName("Valid Input.Test case passed")
	void testValidateEmailPass() throws PecuniaException, LoginException {
		
		Login loginData= new Login();
		loginData.setUsername("saurabh5881@gmail.com");
		loginData.setPassword("12345");
		assertTrue(login.validateEmail(loginData));
	}
	
	
}
