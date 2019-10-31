package com.capgemini.pecunia.dao;

public interface LoanQuerryMapper {
	
	
	
	public static final String ADD_LOAN_DETAILS = "INSERT INTO loan (account_id,amount,type,tenure,roi,loan_status,emi,credit_score) VALUES(?,?,?,?,?,?,?,?) ";	
	
}
