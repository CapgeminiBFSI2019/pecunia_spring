package com.capgemini.pecunia.dao;

public class PassbookMaintenanceQueryMapper {

	public static final String QUERY_TRANS_DETAILS = "SELECT trans_id, date, amount, trans_from, trans_to, transaction.type,trans_option, cheque_id, trans_closing_balance FROM transaction JOIN account ON account.account_id= transaction.account_id WHERE transaction.account_id=? AND date BETWEEN account.last_updated and CONVERT_TZ(NOW(),'+00:00','+05:30')";
	public static final String QUERY_LAST_UPDATED = "UPDATE `account` SET last_updated = CONVERT_TZ(NOW(),'+00:00','+05:30') WHERE account_id=?";

	public static final String QUERY_SUMMARY = "SELECT trans_id,date, amount,trans_from,trans_to,transaction.type,trans_option, cheque_id, trans_closing_balance FROM transaction JOIN account ON account.account_id= transaction.account_id WHERE transaction.account_id=? AND date BETWEEN ? and ?";

}
