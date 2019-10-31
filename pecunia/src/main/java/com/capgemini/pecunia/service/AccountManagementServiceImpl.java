package com.capgemini.pecunia.service;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.dto.Customer;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.hibernate.dao.AccountManagementDAO;
import com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl;
import com.capgemini.pecunia.util.Constants;

public class AccountManagementServiceImpl implements AccountManagementService {

	Logger logger = Logger.getRootLogger();

	public AccountManagementServiceImpl() {
	}

	com.capgemini.pecunia.hibernate.dao.AccountManagementDAO accountDAO;

	/*******************************************************************************************************
	 * - Function Name : deleteAccount(Account account) - Input Parameters : Account
	 * account - Return Type : boolean - Throws : AccountException - Author : Rohit
	 * Kumar - Creation Date : 24/09/2019 - Description : Deleting an account by
	 * setting account status "Closed"
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean deleteAccount(Account account) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

			boolean isValidated = validateAccountId(account);
			if (isValidated) {
				accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
				isUpdated = accountDAO.deleteAccount(account);
			} else {
				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerName(Account account, Customer customer) -
	 * Input Parameters : Account account, Customer customer - Return Type : boolean
	 * - Throws : AccountException - Author : Aditi Singh - Creation Date :
	 * 24/09/2019 - Description : Updating customer name
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

			boolean isValidated = validateAccountId(account);
			if (isValidated) {
				accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
				isUpdated = accountDAO.updateCustomerName(account, customer);
			} else {
				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

		} catch (Exception e) {
			logger.error(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerContact(Account account, Customer customer) -
	 * Input Parameters : Account account, Customer customer - Return Type : boolean
	 * - Throws : AccountException - Author : Aditi Singh - Creation Date :
	 * 24/09/2019 - Description : Updating customer contact
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

			boolean isValidated = validateAccountId(account);
			if (isValidated) {
				accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
				isUpdated = accountDAO.updateCustomerContact(account, customer);
			} else {
				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerName(Account account, Address address) -
	 * Input Parameters : Account account, Address address - Return Type : boolean -
	 * Throws : AccountException - Author : Aditi Singh - Creation Date : 24/09/2019
	 * - Description : Updating customer address
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException {

		boolean isUpdated = false;
		try {
			boolean isValidated = validateAccountId(account);
			if (isValidated) {
				accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
				isUpdated = accountDAO.updateCustomerAddress(account, address);
			} else {
				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : calculateAccountId(Account account) - Input Parameters :
	 * Account account - Return Type : String - Throws : AccountException - Author :
	 * Aditi Singh - Creation Date : 24/09/2019 - Description : Generation of a new
	 * account ID with the given branch ID and type of Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public String calculateAccountId(Account account) throws PecuniaException, AccountException {
		try {
			String id = "";
			id = id.concat(account.getBranchId());
			String type = account.getAccountType();
			switch (type) {
			case Constants.SAVINGS:
				id = id.concat(Constants.CODE_SAVINGS);
				break;
			case Constants.CURRENT:
				id = id.concat(Constants.CODE_CURRENT);
				break;
			case Constants.FD:
				id = id.concat(Constants.CODE_FD);
				break;
			case Constants.LOAN:
				id = id.concat(Constants.CODE_LOAN);
				break;
			}
			accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
			account.setId(id);
			id = accountDAO.calculateAccountId(account);
			return id;
		} catch (Exception e) {
			logger.error(ErrorConstants.TECH_ERROR);
			throw new AccountException(ErrorConstants.TECH_ERROR);
		}

	}

	/*******************************************************************************************************
	 * - Function Name : validateAccountId(Account account) - Input Parameters :
	 * Account account - Return Type : double - Throws : AccountException - Author :
	 * Aditi Singh - Creation Date : 24/09/2019 - Description : Validation of
	 * Account ID
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean validateAccountId(Account account) throws PecuniaException, AccountException {
		boolean isValidated = false;
		boolean doesExist = false;
		accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
		doesExist = accountDAO.validateAccountId(account);
		if (doesExist) {
			AccountManagementService ams = new AccountManagementServiceImpl();
			try {
				Account validAccount = ams.showAccountDetails(account);
				if ("Active".equals(validAccount.getStatus())) {
					isValidated = true;
				} else {
					throw new AccountException(ErrorConstants.ACCOUNT_CLOSED);
				}
			} catch (Exception e) {
				logger.error(ErrorConstants.ACCOUNT_CLOSED);
				throw new AccountException(e.getMessage());
			}

		}
		return isValidated;
	}

	/*******************************************************************************************************
	 * - Function Name : addAccount(Customer customer, Address address,Account
	 * account) - Input Parameters : Customer customer, Address address,Account
	 * account - Return Type : String - Throws : AccountException - Author : Vidushi
	 * Razdan - Creation Date : 24/09/2019 - Description : Addition of new Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public String addAccount(Customer customer, Address address, Account account)
			throws PecuniaException, AccountException {
		try {
			accountDAO = new com.capgemini.pecunia.hibernate.dao.AccountManagementDAOImpl();
			String custId = accountDAO.addCustomerDetails(customer, address);
			account.setHolderId(custId);
			String accountId = calculateAccountId(account);
			account.setId(accountId);
			account.setLastUpdated(LocalDateTime.now());
			String createdId = accountDAO.addAccount(account);
			if (createdId == null) {
				logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);
				throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
			}
			return accountId;
		} catch (Exception e) {
			logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);

			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
	}

	@Override
	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {
		Account accountRequested = new Account();
		try {
			AccountManagementDAO accountDAO = new AccountManagementDAOImpl();

			accountRequested = accountDAO.showAccountDetails(account);
		} catch (AccountException | PecuniaException e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return accountRequested;
	}

}
