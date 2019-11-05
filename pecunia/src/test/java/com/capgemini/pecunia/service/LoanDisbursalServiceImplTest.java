package com.capgemini.pecunia.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;

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
	void testUpdateLoanStatus() throws LoanException {

		assertThrows(LoanDisbursalException.class, ()-> { ArrayList<Loan> ln1 = null;
		ArrayList<Loan> ln=null;
		loanDisbursal.updateLoanStatus(ln,ln1);});
	}
	
	
	@Test
	@DisplayName("Null input for create loan request. Test case failed")
	void testUpdateLoanStatus1() throws LoanException {

		assertThrows(LoanDisbursalException.class, ()-> { ArrayList<Loan> ln1 = null;
		ArrayList<Loan> ln= loanDisbursal.approveLoan() ;
		loanDisbursal.updateLoanStatus(ln,ln1);});
	}
	
	@Test
	@DisplayName("Null input for create loan request. Test case failed")
	void testUpdateLoanStatus2() throws LoanException {

		assertThrows(LoanDisbursalException.class, ()-> { ArrayList<Loan> ln1 = loanDisbursal.rejectedLoanRequests();
		ArrayList<Loan> ln= null ;
		loanDisbursal.updateLoanStatus(ln,ln1);});
	}

	


}
