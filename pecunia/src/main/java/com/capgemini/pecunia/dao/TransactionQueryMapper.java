package com.capgemini.pecunia.dao;

public interface TransactionQueryMapper {
	public static final String GET_ACCOUNT_BALANCE_QUERY = "SELECT balance FROM account WHERE account_id = ?";
	public static final String UPDATE_ACOCUNT_BALANCE_QUERY = "UPDATE account SET balance = ? WHERE account_id = ?";
	public static final String INSERT_TRANSACTION_QUERY = "INSERT INTO transaction (account_id,type,amount,trans_option,date,cheque_id,trans_from,trans_to,trans_closing_balance) VALUES (?,?,?,?,CONVERT_TZ(NOW(),'+00:00','+05:30'),?,?,?,?)";
	public static final String INSERT_CHEQUE_QUERY = "INSERT INTO cheque (cheque_num,account_num,cheque_holder_name,bank_name,IFSC,issue_date,status) values (?,?,?,?,?,?,?)";
	public static final String DEPOSIT_INTEREST_QUERY = "UPDATE account SET balance = ? AND interest = 0.0 WHERE account_id = ?";
	public static final String Get_INTEREST_QUERY = "SELECT interest FROM account WHERE account_id = ?";
	public static final String GET_CHEQUE_ID_QUERY = "SELECT cheque_id FROM cheque WHERE account_num=?";
}
