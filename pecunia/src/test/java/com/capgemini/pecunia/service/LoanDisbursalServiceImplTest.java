package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.LoanException;

class LoanDisbursalServiceImplTest {

LoanDisbursalServiceImpl loanDisbursal;
	
	@BeforeEach
	void setUp() throws Exception {
		loanDisbursal=new LoanDisbursalServiceImpl();

	}

	@AfterEach
	void tearDown() throws Exception {
		loanDisbursal=null;
	}
	
	@Test
	@DisplayName("Null input for create loan request. Test case failed")
	void testCreateLoanRequest() throws LoanException {

		assertThrows(LoanDisbursalException.class, ()-> { ArrayList<Loan> ln1 = null;
		ArrayList<Loan> ln=null;
		loanDisbursal.updateLoanStatus(ln,ln1);});
	}
	


}
