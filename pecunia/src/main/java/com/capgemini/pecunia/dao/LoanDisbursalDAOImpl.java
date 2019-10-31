package com.capgemini.pecunia.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.dto.LoanDisbursal;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.Constants;
import com.capgemini.pecunia.util.DBConnection;
import com.capgemini.pecunia.util.LoggerMessage;

public class LoanDisbursalDAOImpl implements LoanDisbursalDAO {

	Logger logger = Logger.getRootLogger();

	public LoanDisbursalDAOImpl() {
	

	}

	private int loanId;
	private String accountId;
	private Double amount;
	private String type;
	private int tenure;
	private int roi;
	private String status;
	private Double emi;
	private int creditScore;


	private int loanIdOfAccepted;
	private String accountIdOfAccepted;
	private Double amountOfAccepted;
	private String typeOfAccepted;
	private int tenureOfAccepted;
	private int roiOfAccepted;
	private String statusOfAccepted;
	private Double emiOfAccepted;
	private int creditScoreOfAccepted;


	private int loanIdOfRejected;
	private String accountIdOfRejected;
	private Double amountOfRejected;
	private String typeOfRejected;
	private int tenureOfRejected;
	private int roiOfRejected;
	private String statusOfRejected;
	private Double emiOfRejected;
	private int creditScoreOfRejected;
	
	private int loanDisbursedId;
	private int loanId1;
	private String accountId1;
	private Double disbursedAmount;
	private double dueAmount;
	private double emiToBePaid;
	private String loanType;
	private double emiPerMonth;

	/*******************************************************************************************************
	 * - Function Name : amountToBePaid(double emi, int tenure) - Input Parameters
	 * :double emi, int tenure - Return Type : double - Throws : None - Author :
	 * aninrana - Creation Date : 25/09/2019 - Description : Calculating the total
	 * amount to be paid after interest.
	 ********************************************************************************************************/

	public double amountToBePaid(double emi, int tenure) {
		return emi * tenure;
	}

	/*******************************************************************************************************
	 * - Function Name : retrieveLoanList() - Input Parameters :none - Return Type :
	 * List<Loan> - Throws : None - Author : IOException, PecuniaException -
	 * Creation Date : 25/09/2019 - Description : Retrieving the loan requests from
	 * the database
	 ********************************************************************************************************/

	public List<Loan> retrieveLoanList() throws IOException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Loan> requestList = new ArrayList<Loan>();

		try {
			preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.RETRIVE_ALL_QUERY_FROM_LOAN);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				loanId = resultSet.getInt("loan_id");
				accountId = resultSet.getString("account_id");
				amount = resultSet.getDouble("amount");
				type = resultSet.getString("type");
				tenure = resultSet.getInt("tenure");
				roi = resultSet.getInt("roi");
				status = resultSet.getString("loan_status");
				emi = resultSet.getDouble("emi");
				creditScore = resultSet.getInt("credit_score");
				

				Loan loan = new Loan(loanId, accountId, amount, type, tenure, roi, status, emi, creditScore);
				requestList.add(loan);

			}
		}

		catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return requestList;

	}
	
	/*******************************************************************************************************
	 * - Function Name : retrieveLoanList() - Input Parameters :none - Return Type :
	 * List<Loan> - Throws : None - Author : IOException, PecuniaException -
	 * Creation Date : 25/09/2019 - Description : Retrieving the loan requests from
	 * the database
	 ********************************************************************************************************/

	public ArrayList<String> uniqueIds() throws IOException, PecuniaException {
		
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> ids = new ArrayList<String>();

		try {
			preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.DISTINCT_IDS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				accountId = resultSet.getString("account_id");
				ids.add(accountId);
			}
		}

		catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return ids;
	}

	/*******************************************************************************************************
	 * - Function Name : retrieveAcceptedLoanList() - Input Parameters :none -
	 * Return Type : List<Loan> - Throws : None - Author : IOException,
	 * PecuniaException - Creation Date : 25/09/2019 - Description : Retrieving the
	 * accepted loan requests from the database
	 ********************************************************************************************************/

	public List<Loan> retrieveAcceptedLoanList() throws IOException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Loan> requestList = new ArrayList<Loan>();

		try {
			preparedStatement = connection
					.prepareStatement(LoanDisbursalQuerryMapper.RETRIVE_LOAN_REQUESTS_WITH_ENOUGH_CREDIT_SCORE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				loanIdOfAccepted = resultSet.getInt("loan_id");
				accountIdOfAccepted = resultSet.getString("account_id");
				amountOfAccepted = resultSet.getDouble("amount");
				typeOfAccepted = resultSet.getString("type");
				tenureOfAccepted = resultSet.getInt("tenure");
				roiOfAccepted = resultSet.getInt("roi");
				statusOfAccepted = resultSet.getString("loan_status");
				emiOfAccepted = resultSet.getDouble("emi");
				creditScoreOfAccepted = resultSet.getInt("credit_score");
				
				Loan loan = new Loan(loanIdOfAccepted, accountIdOfAccepted, amountOfAccepted, typeOfAccepted,
						tenureOfAccepted, roiOfAccepted, statusOfAccepted, emiOfAccepted, creditScoreOfAccepted);
				requestList.add(loan);

			}
		}

		catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return requestList;

	}
	
	/*******************************************************************************************************
	 * - Function Name : retrieveAcceptedLoanListWithoutStatus() - Input Parameters :none -
	 * Return Type : List<Loan> - Throws : None - Author : IOException,
	 * PecuniaException - Creation Date : 25/09/2019 - Description : Retrieving the
	 * accepted loan requests from the database
	 ********************************************************************************************************/

	public List<Loan> retrieveAcceptedLoanListWithoutStatus() throws IOException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Loan> requestList = new ArrayList<Loan>();

		try {
			preparedStatement = connection
					.prepareStatement(LoanDisbursalQuerryMapper.RETRIVE_LOAN_REQUESTS_WITH_ENOUGH_CREDIT_SCORE_WITHOUT_STATUS);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				loanIdOfAccepted = resultSet.getInt("loan_id");
				accountIdOfAccepted = resultSet.getString("account_id");
				amountOfAccepted = resultSet.getDouble("amount");
				typeOfAccepted = resultSet.getString("type");
				tenureOfAccepted = resultSet.getInt("tenure");
				roiOfAccepted = resultSet.getInt("roi");
				statusOfAccepted = resultSet.getString("loan_status");
				emiOfAccepted = resultSet.getDouble("emi");
				creditScoreOfAccepted = resultSet.getInt("credit_score");
				
				Loan loan = new Loan(loanIdOfAccepted, accountIdOfAccepted, amountOfAccepted, typeOfAccepted,
						tenureOfAccepted, roiOfAccepted, statusOfAccepted, emiOfAccepted, creditScoreOfAccepted);
				requestList.add(loan);

			}
		}

		catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return requestList;

	}

	/*******************************************************************************************************
	 * - Function Name : retrieveAcceptedLoanList() - Input Parameters :none -
	 * Return Type : List<Loan> - Throws : None - Author : IOException,
	 * PecuniaException - Creation Date : 25/09/2019 - Description : Retrieving the
	 * accepted loan requests from the database
	 ********************************************************************************************************/

	public List<Loan> retrieveRejectedLoanList() throws IOException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Loan> requestList = new ArrayList<Loan>();

		try {
			preparedStatement = connection
					.prepareStatement(LoanDisbursalQuerryMapper.RETRIVE_LOAN_REQUESTS_WITH_NOT_ENOUGH_CREDIT_SCORE);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				loanIdOfRejected = resultSet.getInt("loan_id");
				accountIdOfRejected = resultSet.getString("account_id");
				amountOfRejected = resultSet.getDouble("amount");
				typeOfRejected = resultSet.getString("type");
				tenureOfRejected = resultSet.getInt("tenure");
				roiOfRejected = resultSet.getInt("roi");
				statusOfRejected = resultSet.getString("loan_status");
				emiOfRejected = resultSet.getDouble("emi");
				creditScoreOfRejected = resultSet.getInt("credit_score");
				
				Loan loan = new Loan(loanIdOfRejected, accountIdOfRejected, amountOfRejected, typeOfRejected,
						tenureOfRejected, roiOfRejected, statusOfRejected, emiOfRejected, creditScoreOfRejected);
				requestList.add(loan);

			}
		}

		catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_REQUEST);
		return requestList;

	}

	/*******************************************************************************************************
	 * - Function Name : releaseLoanSheet(List<Loan> loanList) - Input Parameters
	 * :List<Loan> loanList - Return Type : void - Throws : IOException,
	 * PecuniaException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the data in loan disbursed database
	 ********************************************************************************************************/

	public void releaseLoanSheet(ArrayList<Loan> loanList) throws IOException, PecuniaException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;

		try {
			for (int index = 0; index < loanList.size(); index++) {
				double amountDue = amountToBePaid(loanList.get(index).getEmi(), loanList.get(index).getTenure());
				preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.INSERT_QUERY);
				preparedStatement.setInt(1, loanList.get(index).getLoanId());
				preparedStatement.setString(2, loanList.get(index).getAccountId());
				preparedStatement.setDouble(3, loanList.get(index).getAmount());
				preparedStatement.setDouble(4, amountDue);
				preparedStatement.setInt(5, loanList.get(index).getTenure());
				preparedStatement.setString(6, loanList.get(index).getType());
				preparedStatement.execute();
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(sqlException.getMessage());

		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}

	}

	/*******************************************************************************************************
	 * - Function Name : loanApprovedList() - Input Parameters : None - Return Type
	 * : ArrayList<LoanDisbursal> - Throws : IOException, PecuniaException - Author
	 * : aninrana - Creation Date : 25/09/2019 - Description : returning the list of
	 * loan customers whose loan request has been approved
	 ********************************************************************************************************/

	public ArrayList<LoanDisbursal> loanApprovedList() throws IOException, PecuniaException {
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<LoanDisbursal> approvedLoanList = new ArrayList<LoanDisbursal>();
		try {
			preparedStatement = connection
					.prepareStatement(LoanDisbursalQuerryMapper.RETRIVE_ALL_QUERY_FROM_APPROVED_LOAN);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				loanDisbursedId = resultSet.getInt("loan_disbursal_id");
				loanId1 = resultSet.getInt("loan_id");
				accountId = resultSet.getString("account_id");
				disbursedAmount = resultSet.getDouble("disbursed_amount");
				dueAmount = resultSet.getDouble("due_amount");
				emiToBePaid = resultSet.getDouble("emi_to_be_paid");
				loanType = resultSet.getString("loan_type");
				LoanDisbursal getDetails = new LoanDisbursal(loanDisbursedId, loanId1, accountId, disbursedAmount,
						dueAmount, emiToBePaid,loanType);
				approvedLoanList.add(getDetails);

			}
		} catch (SQLException sqlException) {
			throw new PecuniaException(ErrorConstants.CONNECTION_FAILURE);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		logger.info(LoggerMessage.LOAN_APPROVED);
		return approvedLoanList;
	}

	/*******************************************************************************************************
	 * - Function Name : updateLoanAccount(ArrayList<LoanDisbursal> loanApprovals,
	 * double dueAmount,double tenure, String accountId) - Input Parameters :
	 * ArrayList<LoanDisbursal> loanApprovals, double dueAmount,double tenure,
	 * String accountId - Return Type : void - Throws : IOException,
	 * PecuniaException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the main account balance of the loan customer
	 ********************************************************************************************************/

	public void updateLoanAccount(ArrayList<LoanDisbursal> loanApprovals, double dueAmount, double tenure,
			String accountId) throws IOException, PecuniaException {
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.UPDATE_LOAN_ACCOUNT);
			preparedStatement.setDouble(1, dueAmount);
			preparedStatement.setDouble(2, tenure);
			preparedStatement.setString(3, accountId);
			preparedStatement.execute();

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new PecuniaException(sqlException.getMessage());

		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}

		logger.info(LoggerMessage.UPDATE_ACCOUNT_BALANCE);

	}

	/*******************************************************************************************************
	 * - Function Name : updateStatus(ArrayList<Loan> loanRequests, String
	 * accountId, String Status) - Input Parameters : ArrayList<Loan> loanRequests,
	 * String accountId, String Status - Return Type : void - Throws : IOException,
	 * PecuniaException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating loan status of the loan customers
	 ********************************************************************************************************/

	public void updateStatus(ArrayList<Loan> loanRequests, int loanId, String Status)
			throws IOException, PecuniaException {
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.UPDATE_LOAN_STATUS);
			preparedStatement.setString(1, Status);
			preparedStatement.setInt(2, loanId);
			preparedStatement.execute();

		} catch (SQLException sqlException) {
			throw new PecuniaException(sqlException.getMessage());

		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}

		logger.info(LoggerMessage.UPDATE_LOAN_STATUS);
	}

	public double totalEmi(String accountId) throws PecuniaException {
		double totalEmi = 0;
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;

		try {

			preparedStatement = connection.prepareStatement(LoanDisbursalQuerryMapper.TOTAL_EMI);
			preparedStatement.setString(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				totalEmi = rs.getDouble(1);
			}

		} catch (SQLException sqlException) {
			throw new PecuniaException(sqlException.getMessage());

		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new PecuniaException(ErrorConstants.FILE_CLOSING_FAILURE);

			}
		}
		
		return totalEmi;
	}

}
