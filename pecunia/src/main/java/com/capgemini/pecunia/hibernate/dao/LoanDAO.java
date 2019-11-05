package com.capgemini.pecunia.hibernate.dao;
import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;

public interface LoanDAO {
	public int addLoanDetails(Loan loan) throws PecuniaException, LoanException;
}
