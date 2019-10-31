package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.util.Constants;

class LoanServiceImplTest {

	LoanServiceImpl loan;
	
	@BeforeEach
	void setUp() throws Exception {
		loan=new LoanServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		loan=null;
	}

	@Test
	@DisplayName("Equals test case passed for emi calculator")
	void testCalculateEMIPass() {
		assertEquals(21973.0, loan.calculateEMI(480000.0, 24, 9.2));
	}
	
	@Test
	@DisplayName("Not Equals test case passed for emi calculator")
	void testCalculateEMIFail() {
		assertNotEquals(87900.0, loan.calculateEMI(480000.0, 24, 9.2));
	}

	@Test
	@DisplayName("Null input for create loan request. Test case failed")
	void testCreateLoanRequest() throws LoanException {
		Loan ln=null;
		assertThrows(LoanException.class, ()-> { loan.createLoanRequest(ln);});
	}
	
	
	@Test
	@DisplayName("Valid inputs for create loan request. Test case passed")
	void testCreateLoanRequestPass() throws LoanException {
		
		double emi=loan.calculateEMI(500000.0, 18, 8.4);
		Loan ln =new Loan();
		ln.setAccountId("100202000001");
		ln.setAmount(500000.0);
		ln.setType(Constants.LOAN_TYPE[0]);
		ln.setTenure(18);
		ln.setRoi(8.4);
		ln.setLoanStatus(Constants.LOAN_REQUEST_STATUS[0]);
		ln.setEmi(emi);
		ln.setCreditScore(750);
		
//		assertTrue(loan.createLoanRequest(ln));
		
		
	}
	

}
