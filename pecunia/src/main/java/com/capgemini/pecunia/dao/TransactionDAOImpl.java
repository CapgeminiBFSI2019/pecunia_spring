package com.capgemini.pecunia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.util.DBConnection;
import com.capgemini.pecunia.util.LoggerMessage;

import jdk.nashorn.internal.runtime.options.LoggingOption.LoggerInfo;

public class TransactionDAOImpl implements TransactionDAO {

	Logger logger = Logger.getRootLogger();

	public TransactionDAOImpl() {
	}

	/*******************************************************************************************************
	 * - Function Name : getBalance(Account account) - Input Parameters : account
	 * object - Return Type : double - Throws :
	 * TransactionException,PecuniaException - Author : Rohan Patil - Creation Date
	 * : 23/09/2019 - Description : Getting balance of the specified account
	 ********************************************************************************************************/

	@Override
	public double getBalance(Account account) throws PecuniaException, TransactionException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String accountId = account.getId();
		double balance = -1;

		try {
			preparedStatement = connection.prepareStatement(TransactionQueryMapper.GET_ACCOUNT_BALANCE_QUERY);

			preparedStatement.setString(1, accountId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				balance = resultSet.getDouble("balance");
			}

			if (balance == -1) {
				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new TransactionException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());

			throw new PecuniaException(e.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(ErrorConstants.DB_CONNECTION_ERROR);
				throw new PecuniaException(ErrorConstants.DB_CONNECTION_ERROR);
			}

		}
		logger.info(LoggerMessage.ACCOUNT_BALANCE_SUCCESSFUL);
		return balance;
	}

	/*******************************************************************************************************
	 * - Function Name : updateBalance(Account account) - Input Parameters : account
	 * object - Return Type : boolean - Throws :
	 * TransactionException,PecuniaException - Author : Anwesha Das - Creation Date
	 * : 23/09/2019 - Description : update balance of the specified account
	 ********************************************************************************************************/

	@Override
	public boolean updateBalance(Account account) throws PecuniaException, TransactionException {
		boolean balanceUpdated = false;
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		String accountId = account.getId();
		int numAccountAffected = 0;
		try {
			preparedStatement = connection.prepareStatement(TransactionQueryMapper.UPDATE_ACOCUNT_BALANCE_QUERY);
			preparedStatement.setDouble(1, account.getBalance());
			preparedStatement.setString(2, accountId);
			numAccountAffected = preparedStatement.executeUpdate();
			if (numAccountAffected != 0) {
				balanceUpdated = true;
			} else {
				logger.error(ErrorConstants.BALANCE_UPDATE_ERROR);
				throw new TransactionException(ErrorConstants.BALANCE_UPDATE_ERROR);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PecuniaException(e.getMessage());
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {

				logger.error(ErrorConstants.DB_CONNECTION_ERROR);
				throw new PecuniaException(ErrorConstants.DB_CONNECTION_ERROR);
			}
		}
		logger.info(LoggerMessage.BALANCE_UPDATED_SUCCESSFUL);
		return balanceUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : generateChequeId(Cheque cheque) - Input Parameters : cheque
	 * object - Return Type : int - Throws : TransactionException,PecuniaException -
	 * Author : Anish Basu - Creation Date : 23/09/2019 - Description : generate
	 * cheque id of the specified account
	 ********************************************************************************************************/

	@Override
	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int chequeId = 0;
		try {
			preparedStatement = connection.prepareStatement(TransactionQueryMapper.INSERT_CHEQUE_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, cheque.getNum());
			preparedStatement.setString(2, cheque.getAccountNo());
			preparedStatement.setString(3, cheque.getHolderName());
			preparedStatement.setString(4, cheque.getBankName());
			preparedStatement.setString(5, cheque.getIfsc());
			preparedStatement.setDate(6, java.sql.Date.valueOf(cheque.getIssueDate().plusDays(1)));
			preparedStatement.setString(7, cheque.getStatus());
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				chequeId = resultSet.getInt(1);
			} else {
				logger.error(ErrorConstants.CHEQUE_INSERTION_ERROR);
				throw new TransactionException(ErrorConstants.CHEQUE_INSERTION_ERROR);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PecuniaException(e.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(e.getMessage());
			}

		}
		logger.info(LoggerMessage.CHEQUE_ID_SUCCESSFUL);
		return chequeId;
	}

	/*******************************************************************************************************
	 * - Function Name : generateTransactionId(Transaction transaction) - Input
	 * Parameters : transaction object - Return Type : int - Throws :
	 * TransactionException,PecuniaException - Author : Arpan Mondal - Creation Date
	 * : 23/09/2019 - Description : generate transaction id of the specified account
	 ********************************************************************************************************/

	@Override
	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int transId = 0;

		try {
			preparedStatement = connection.prepareStatement(TransactionQueryMapper.INSERT_TRANSACTION_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, transaction.getAccountId());
			preparedStatement.setString(2, transaction.getType());
			preparedStatement.setDouble(3, transaction.getAmount());
			preparedStatement.setString(4, transaction.getOption());
			preparedStatement.setInt(5, transaction.getChequeId());
			preparedStatement.setString(6, transaction.getTransFrom());
			preparedStatement.setString(7, transaction.getTransTo());
			preparedStatement.setDouble(8, transaction.getClosingBalance());

			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				transId = resultSet.getInt(1);
			} else {
				logger.error(ErrorConstants.TRANSACTION_INSERTION_ERROR);
				throw new TransactionException(ErrorConstants.TRANSACTION_INSERTION_ERROR);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PecuniaException(e.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new PecuniaException(e.getMessage());
			}

		}
		logger.info(LoggerMessage.TRANSACTION_ID_SUCCESSFUL);
		return transId;
	}

}
