package com.capgemini.pecunia.dao;

public interface LoanDisbursalQuerryMapper {
public static final String RETRIVE_ALL_QUERY_FROM_LOAN = "SELECT * FROM loan";
public static final String RETRIVE_LOAN_REQUESTS_WITH_ENOUGH_CREDIT_SCORE = "SELECT * FROM loan WHERE credit_score >= 670 AND loan_status = 'Pending'" ;
public static final String RETRIVE_LOAN_REQUESTS_WITH_ENOUGH_CREDIT_SCORE_WITHOUT_STATUS = "SELECT * FROM loan WHERE credit_score >= 670" ;
public static final String RETRIVE_LOAN_REQUESTS_WITH_NOT_ENOUGH_CREDIT_SCORE = "SELECT * FROM loan WHERE credit_score < 670 AND loan_status = 'Pending'";
public static final String INSERT_QUERY = "INSERT INTO loan_disbursal(loan_id,account_id,disbursed_amount,due_amount,emi_to_be_paid,loan_type) VALUES(?,?,?,?,?,?)";
public static final String RETRIVE_ALL_QUERY_FROM_APPROVED_LOAN = "SELECT * FROM loan_disbursal";
public static final String UPDATE_LOAN_ACCOUNT = "UPDATE loan_disbursal SET due_amount = ? , emi_to_be_paid = ? WHERE account_id = ?";
public static final String UPDATE_LOAN_STATUS = "UPDATE loan SET loan_status = ?  WHERE loan_id = ?";
public static final String UPDATE_AMOUNT = "UPDATE account SET balance = ? WHERE account_id = ?";
public static final String TOTAL_EMI = "SELECT SUM(emi) FROM loan WHERE account_id = ?";
public static final String DISTINCT_IDS = "SELECT DISTINCT account_id FROM loan_disbursal";
}