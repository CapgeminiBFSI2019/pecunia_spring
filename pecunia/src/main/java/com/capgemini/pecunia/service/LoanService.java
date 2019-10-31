package com.capgemini.pecunia.service;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;


public interface LoanService {
	

	
	public int createLoanRequest(Loan loan) throws LoanException;




}		