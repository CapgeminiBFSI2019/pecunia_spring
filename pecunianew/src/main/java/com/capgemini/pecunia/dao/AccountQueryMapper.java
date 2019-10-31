package com.capgemini.pecunia.dao;

public interface AccountQueryMapper {
	
	public static final String UPDATE_ADDRESS = "UPDATE address SET address_line1=?, address_line2=?, city=?, state=?,country=?, zipcode=? WHERE address_id=?";
	public static final String UPDATE_NAME = "UPDATE customer SET name=? WHERE customer_id=?";
	public static final String UPDATE_CONTACT = "UPDATE customer SET contact=? WHERE customer_id=?";
	public static final String ADD_ADDRESS = "INSERT INTO address(address_line1, address_line2, city, state,country, zipcode) VALUES (?,?,?,?,?,?)";
	public static final String ADD_CUSTOMER = "INSERT INTO customer(name, address_id,aadhar,pan,contact,gender,dob) VALUES (?,?,?,?,?,?,?)";
	public static final String ADD_ACCOUNT = "INSERT INTO account(account_id,customer_id,branch_id,type,status,balance,interest,last_updated) VALUES (?,?,?,?,?,?,?,CONVERT_TZ(NOW(),'+00:00','+05:30'))";
	public static final String DELETE_ACCOUNT = "UPDATE account SET status='Closed' WHERE account_id=?";
	public static final String GET_RECENT_ADDRESS_ID = "SELECT MAX(address_id) FROM address";
	public static final String GET_RECENT_CUSTOMER_ID = "SELECT MAX(customer_id) FROM customer";
	public static final String GET_ADDRESS_ID = "SELECT address.address_id from customer INNER JOIN address ON address.address_id=customer.address_id "+
											"INNER JOIN account ON account.customer_id=customer.customer_id WHERE account_id=?";
	public static final String GET_CUSTOMER_ID = "SELECT customer_id FROM customer WHERE customer_id=(SELECT account.customer_id "
											+ "FROM account WHERE account.account_id=?)";
	
	public static final String GET_RECENT_ID = "SELECT account_id FROM account WHERE account_id LIKE ? ORDER BY account_id DESC LIMIT 1";
	public static final String VALIDATE_ID = "SELECT account_id FROM account WHERE account_id=?";

	public static final String DELETE_ADDRESS_ID = "DELETE FROM address WHERE address_id=?";
	public static final String DELETE_CUSTOMER_ID = "DELETE FROM customer WHERE customer_id=?";
}
