package com.capgemini.pecunia.hibernate.dao;
import java.sql.SQLException;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.dto.Customer;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;

public interface AccountManagementDAO {
	public boolean deleteAccount(Account account) throws PecuniaException, AccountException;

	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException;

	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException;

	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException;

	public String addCustomerDetails(Customer customer, Address address) throws PecuniaException, AccountException, SQLException;

	public String addAccount(Account account) throws PecuniaException, AccountException, SQLException;

	public String calculateAccountId(Account account) throws PecuniaException, AccountException;

	public boolean validateAccountId(Account account) throws PecuniaException, AccountException;
	
	public Account showAccountDetails(Account account) throws AccountException,PecuniaException;
}
