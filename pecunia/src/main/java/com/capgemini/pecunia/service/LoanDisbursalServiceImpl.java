package com.capgemini.pecunia.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.hibernate.dao.LoanDisbursalDAOImplHibernate;
import com.capgemini.pecunia.hibernate.dao.TransactionDAOImpl;
import com.capgemini.pecunia.util.Constants;
import com.capgemini.pecunia.util.LoggerMessage;

@Service
public class LoanDisbursalServiceImpl implements LoanDisbursalService {

	Logger logger = Logger.getRootLogger();



	TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl();
	ArrayList<LoanDisbursal> approvedLoanList = new ArrayList<LoanDisbursal>();

	/*******************************************************************************************************
	 * - Function Name : retrieveAll() - Input Parameters : None - Return Type :
	 * ArrayList<Loan> - Throws : PecuniaException, IOException,
	 * LoanDisbursalException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Retrieves loan requests
	 ********************************************************************************************************/

	public ArrayList<Loan> retrieveAll() throws PecuniaException, IOException, LoanDisbursalException {

		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		ArrayList<Loan> retrievedLoanRequests = new ArrayList<Loan>();
		retrievedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveLoanList();
		if (retrievedLoanRequests.size() == 0) {
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return retrievedLoanRequests;
	}

	/*******************************************************************************************************
	 * - Function Name : approveLoan(ArrayList<Loan> loanRequestList) - Input
	 * Parameters : ArrayList<Loan> loanRequestList - Return Type : void - Throws :
	 * IOException, PecuniaException, LoanDisbursalException - Author : aninrana -
	 * Creation Date : 25/09/2019 - Description : Aprroving the loan request based
	 * on condition
	 ********************************************************************************************************/

	public ArrayList<Loan> approveLoan() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		ArrayList<Loan> acceptedLoanRequests = new ArrayList<Loan>();
		acceptedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveAcceptedLoanList();
		if (acceptedLoanRequests.size() == 0) {
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}
		for (int index = 0; index < acceptedLoanRequests.size(); index++) {
			int loanId = acceptedLoanRequests.get(index).getLoanId();
			loanDisbursedDAO.updateStatus(acceptedLoanRequests, loanId, Constants.LOAN_REQUEST_STATUS[1]);
		}
		loanDisbursedDAO.releaseLoanSheet(acceptedLoanRequests);
		logger.info(LoggerMessage.LOAN_REQUEST);
		return acceptedLoanRequests;

	}

	/*******************************************************************************************************
	 * - Function Name : approveLoan(ArrayList<Loan> loanRequestList) - Input
	 * Parameters : ArrayList<Loan> loanRequestList - Return Type : void - Throws :
	 * IOException, PecuniaException, LoanDisbursalException - Author : aninrana -
	 * Creation Date : 25/09/2019 - Description : Aprroving the loan request based
	 * on condition
	 ********************************************************************************************************/

	public ArrayList<Loan> approveLoanWithoutStatus() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		ArrayList<Loan> acceptedLoanRequests = new ArrayList<Loan>();
		acceptedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveAcceptedLoanListWithoutStatus();
		if (acceptedLoanRequests.size() == 0) {
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}

		logger.info(LoggerMessage.LOAN_REQUEST);
		return acceptedLoanRequests;

	}

	/*******************************************************************************************************
	 * - Function Name : approvedLoanList() - Input Parameters : None - Return Type
	 * : ArrayList<LoanDisbursal> - Throws : PecuniaException,IOException - Author :
	 * aninrana - Creation Date : 25/09/2019 - Description : retrieving the loan
	 * disbursed data
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public ArrayList<LoanDisbursal> approvedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();

		approvedLoanList = loanDisbursedDAO.loanApprovedList();
		if (approvedLoanList.size() == 0) {
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
		logger.info(LoggerMessage.APPROVED_LOAN_REQUESTS_FETCHED);
		return approvedLoanList;

	}

	/*******************************************************************************************************
	 * - Function Name : rejectedLoanRequests() - Input Parameters : void - Return
	 * Type : ArrayList<Loan> - Throws : PecuniaException, LoanDisbursalException -
	 * Author : aninrana - Creation Date : 25/09/2019 - Description : Retrieving the
	 * rejected loan rejected
	 * 
	 * @throws IOException
	 ********************************************************************************************************/

	public ArrayList<Loan> rejectedLoanRequests() throws PecuniaException, LoanDisbursalException, IOException {

		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		ArrayList<Loan> rejectedLoanRequests = new ArrayList<Loan>();
		rejectedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveRejectedLoanList();
		if (rejectedLoanRequests.size() == 0) {
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
		for (int index = 0; index < rejectedLoanRequests.size(); index++) {
			int loanId = rejectedLoanRequests.get(index).getLoanId();
			loanDisbursedDAO.updateStatus(rejectedLoanRequests, loanId, Constants.LOAN_REQUEST_STATUS[2]);
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return rejectedLoanRequests;
	}

	/*******************************************************************************************************
	 * - Function Name : updateLoanAccount(ArrayList<LoanDisbursal>
	 * updateLoanApprovals, int numberOfMonths) - Input Parameters :
	 * ArrayList<LoanDisbursal> updateLoanApprovals, int numberOfMonths - Return
	 * Type : void - Throws : throws PecuniaException - Author : aninrana - Creation
	 * Date : 25/09/2019 - Description : Updating the data in loan disbursed
	 * database
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public String updateLoanAccount(ArrayList<LoanDisbursal> updateLoanApprovals, int numberOfMonths)
			throws PecuniaException, LoanDisbursalException {
		String status = Constants.STATUS_CHECK[0];
		if (updateLoanApprovals != null) {
			LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
			for (int index = 0; index < updateLoanApprovals.size(); index++) {
				double updatedDueAmount = updateLoanApprovals.get(index).getDisbursedAmount()
						- (updateLoanApprovals.get(index).getDisbursedAmount()
								/ updateLoanApprovals.get(index).getNumberOfEmiToBePaid()) * numberOfMonths;

				System.out.println(updatedDueAmount + " from hibernate");

				double updatedTenure = updateLoanApprovals.get(index).getNumberOfEmiToBePaid() - numberOfMonths;

				System.out.println(updatedTenure + " from hibernate");

				String accountId = updateLoanApprovals.get(index).getAccountId();
				int loanDisbursalId = updateLoanApprovals.get(index).getLoanDisbursalId();
				System.out.println(accountId);

				System.out.println(updateLoanApprovals);

				try {
					System.out.println("called");
					loanDisbursedDAO.updateLoanAccount(updateLoanApprovals, updatedDueAmount, updatedTenure, accountId,
							loanDisbursalId);
					System.out.println("updated");
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new LoanDisbursalException(e.getMessage());
				}

			}
		}

		else {
			status = Constants.STATUS_CHECK[1];
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}

		return status;
	}

	/*******************************************************************************************************
	 * - Function Name : updateLoanStatus(ArrayList<Loan> rejectedLoanList) - Input
	 * Parameters : ArrayList<Loan> rejectedLoanList - Return Type : void - Throws :
	 * PecuniaException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the loan status after operation
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public String updateLoanStatus(ArrayList<Loan> rejectedLoanList, ArrayList<Loan> approvedLoanList)
			throws PecuniaException, LoanDisbursalException {
		String status = Constants.STATUS_CHECK[0];
		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		if (rejectedLoanList != null || approvedLoanList != null) {
			try {
				for (int index = 0; index < rejectedLoanList.size(); index++) {
					int loanId = rejectedLoanList.get(index).getLoanId();
					loanDisbursedDAO.updateStatus(rejectedLoanList, loanId, Constants.LOAN_REQUEST_STATUS[2]);

				}

				for (int index = 0; index < approvedLoanList.size(); index++) {
					int loanId = approvedLoanList.get(index).getLoanId();
					loanDisbursedDAO.updateStatus(approvedLoanList, loanId, Constants.LOAN_REQUEST_STATUS[1]);

				}

			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new LoanDisbursalException(e.getMessage());
			}

		} else {
			status = Constants.STATUS_CHECK[1];
			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
		logger.info(LoggerMessage.UPDATE_LOAN_STATUS);
		return status;
	}

	/*******************************************************************************************************
	 * - Function Name : updateExistingBalance(ArrayList<Loan> approvedLoanRequests)
	 * - Input Parameters : ArrayList<Loan> approvedLoanRequests - Return Type :
	 * void - Throws : PecuniaException, TransactionException,
	 * LoanDisbursalException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the Account balance of the customer
	 * 
	 * @throws IOException
	 ********************************************************************************************************/

	public ArrayList<String> updateExistingBalance(ArrayList<Loan> approvedLoanRequests,
			ArrayList<LoanDisbursal> approvedLoanList)
			throws PecuniaException, TransactionException, LoanDisbursalException, IOException {
		LoanDisbursalDAOImplHibernate loanDisbursedDAO = new LoanDisbursalDAOImplHibernate();
		ArrayList<String> status = new ArrayList<String>();
		ArrayList<String> accId = new ArrayList<String>();
		accId = loanDisbursedDAO.uniqueIds();
		for (int i = 0; i < accId.size(); i++) {
			Account account = new Account();
			account.setId(accId.get(i));
			double oldBalance = transactionDAOImpl.getBalance(account);
			double totalEMI = loanDisbursedDAO.totalEmi(accId.get(i));
			System.out.println(totalEMI);
			double updatedBalance = oldBalance - totalEMI;

			if (updatedBalance < 0) {
				status.add("Not enough balance for account number " + accId.get(i) + " Balance left " + oldBalance);
			} else {
				account.setBalance(updatedBalance);
				transactionDAOImpl.updateBalance(account);
				updateLoanAccount(approvedLoanList, 1);
				status.add("Balance updated for " + accId.get(i) + " Amount deducted " + totalEMI + " Balance left "
						+ updatedBalance);
			}

		}
		logger.info(LoggerMessage.UPDATE_ACCOUNT_BALANCE);
		return status;
	}

}
