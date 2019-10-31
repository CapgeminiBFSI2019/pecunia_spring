package com.capgemini.pecunia.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.hibernate.dao.PassbookMaintenanceDAO;
import com.capgemini.pecunia.hibernate.dao.PassbookMaintenanceDAOImpl;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.util.LoggerMessage;

public class PassbookMaintenanceServiceImpl implements PassbookMaintenanceService {
	
	Logger logger = Logger.getRootLogger();

	public PassbookMaintenanceServiceImpl() {
	}

	/*******************************************************************************************************
	 * - Function Name : updatePassbook(String accountId) 
	 * - Input Parameters : String accountId
	 * - Return Type : List 
	 * - Throws : PecuniaException 
	 * - Author : Mansi Agarwal
	 * - Creation Date : 24/09/2019 
	 * - Description : Update transaction details in passbook
	 ********************************************************************************************************/
	
	
	public List<Transaction> updatePassbook(String accountId) throws PecuniaException, PassbookException
	{
		try {
			List<Transaction> transactionList = new ArrayList<Transaction>();
			PassbookMaintenanceDAO pdao = new PassbookMaintenanceDAOImpl();
			Account account = new Account();
			account.setId(accountId);
			AccountManagementService accountManagementService = new AccountManagementServiceImpl();
			boolean accountExist = accountManagementService.validateAccountId(account);
			if (!accountExist) {
				throw new PassbookException(ErrorConstants.ERROR_VALIDATION);
			}

			transactionList = pdao.updatePassbook(accountId);
			boolean ans = false;
			if (transactionList.size() > 0) {
				ans = pdao.updateLastUpdated(accountId);
				if (ans) {
					logger.info(LoggerMessage.UPDATE_PASSBOOK_SUCCESSFUL);
				}
			}
			return transactionList;
		} catch (Exception e) {
			throw new PassbookException(e.getMessage());
		}
	}

	/*******************************************************************************************************
	 * - Function Name : accountSummary(String accountId, Date startDate, Date endDate) 
	 * - Input Parameters : String accountId, Date startDate, Date endDate
	 * - Return Type : List 
	 * - Throws : PecuniaException 
	 * - Author : Rishav Dev
	 * - Creation Date : 24/09/2019 
	 * - Description : Provides the account summary
	 ********************************************************************************************************/
	
	
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate) throws PecuniaException, PassbookException {
		
		try {
			List<Transaction> transactionList = new ArrayList<Transaction>();
			PassbookMaintenanceDAO pdao = new PassbookMaintenanceDAOImpl();
			Account account = new Account();
			account.setId(accountId);
			AccountManagementService accountManagementService = new AccountManagementServiceImpl();
			boolean accountExist = accountManagementService.validateAccountId(account);
			if(!accountExist)
			{
				throw new PassbookException(ErrorConstants.ERROR_VALIDATION);
			}
		
			
			transactionList = pdao.accountSummary(accountId, startDate, endDate);
			
			return transactionList;
		} catch (Exception e) {
			throw new PassbookException(e.getMessage());

}
	}
}