package com.capgemini.pecunia.hibernate.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.entity.LoanRequestEntity;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;

public interface LoanDisbursalDAO {
	public List<Loan> retrieveLoanList() throws IOException, PecuniaException, LoanDisbursalException;
	
	public List<Loan> retrieveAcceptedLoanList() throws IOException, PecuniaException, LoanDisbursalException;
	
	public List<Loan> retrieveRejectedLoanList() throws IOException, PecuniaException, LoanDisbursalException;

	public void releaseLoanSheet(ArrayList<Loan> loanList) throws IOException, PecuniaException;

	public ArrayList<LoanDisbursal> loanApprovedList() throws IOException, PecuniaException, LoanDisbursalException;

	public void updateLoanAccount(ArrayList<LoanDisbursal> loanApprovals, double dueAmount, double tenure,
			String accountId, int loanDisbursalId) throws IOException, PecuniaException, LoanDisbursalException;

	public void updateStatus(ArrayList<Loan> loanRequests, int loanID, String Status)
			throws IOException, PecuniaException, LoanDisbursalException;

	public double totalEmi(String accountId) throws PecuniaException, LoanDisbursalException;
	
	public List<Loan> retrieveAcceptedLoanListWithoutStatus() throws IOException, PecuniaException, LoanDisbursalException;
	
	public ArrayList<String> uniqueIds() throws IOException, PecuniaException, LoanDisbursalException;
	

}
