package com.capgemini.pecunia.util;

public class Constants {

	public static final String CODE_SAVINGS = "01";
	public static final String CODE_CURRENT = "02";
	public static final String CODE_FD = "03";
	public static final String CODE_LOAN = "04";
	public static final String SAVINGS = "Savings";
	public static final String CURRENT = "Current";
	public static final String FD = "Fd";
	public static final String LOAN = "Loan";
	public static final String[] STATUS = { "Updated", "Not updated" };
	public static final String[] ACCOUNT_STATUS = { "Active", "Closed" };
	public static final String BANK_NAME = "PECUNIA";
	public static final String TRANSACTION_DEBIT = "DEBIT";
	public static final String TRANSACTION_CREDIT = "CREDIT";
	public static final String TRANSACTION_OPTION_SLIP = "SLIP";
	public static final String TRANSACTION_OPTION_CHEQUE = "CHEQUE";
	public static final String[] OTHER_BANK_NAME = { "ICICI", "SBI", "HDFC", "KOTAK", "AXIS" };
	public static final String[] LOAN_TYPE = { "Personal Loan", "House Loan", "Vehicle Loan", "Jewel Loan" };
	public static final String CHEQUE_STATUS_PENDING = "PENDING";
	public static final String CHEQUE_STATUS_CLEARED = "CLEARED";
	public static final String CHEQUE_STATUS_BOUNCED = "BOUNCED";
	public static final String NA = "-";
	public static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";

	public static final String[] LOAN_REQUEST_STATUS = { "Pending", "Approved", "Rejected" };

	public static final String[] STATUS_CHECK = { "Updated", "Not updated. Either there is connection problem or no data is present" };
	public static final double MINIMUM_CREDIT_SLIP_AMOUNT = 100.00;
	public static final double MAXIMUM_CREDIT_SLIP_AMOUNT = 100000.00;
	public static final String SELF_CHEQUE = "SELF";
	public static final double MINIMUM_CHEQUE_AMOUNT = 100.00;
	public static final double MAXIMUM_CHEQUE_AMOUNT = 200000.00;
	public static final double SAVING_ROI = 5.0;
	public static final double CURRENT_ROI = 0.0;
	public static final double FD_ROI = 7.0;
	public static final String NO_LOAN_REQUEST = "No Loan request is present";
	public static final String LOGIN_SUCCESSFUL = "Login Successful!!";
	public static final String LOAN_REQUEST_SUCCESSFUL="Loan Request Added Successfully" ;
	public static final String UPDATE_NAME_SUCCESSFUL = "Customer Name Updated Successfully";
	public static final String UPDATE_CONTACT_SUCCESSFUL = "Customer Contact Updated Successfully";
	public static final String UPDATE_ADDRESS_SUCCESSFUL = "Customer Address Updated Successfully";
	public static final String DELETE_ACCOUNT_SUCCESSFUL = "Account Deleted Successfully";
	public static final String ADD_ACCOUNT_SUCCESSFUL = "Account Added Successfully";
	public static final String ACCOUNT_ID_CALCULATED = "Account Id Calculated";
	public static final String ACCOUNT_ID_VALIDATED = "Account Id Validated";
	public static final String SHOW_ACCOUNT_DETAILS = "Account details fetched successfully";

}
