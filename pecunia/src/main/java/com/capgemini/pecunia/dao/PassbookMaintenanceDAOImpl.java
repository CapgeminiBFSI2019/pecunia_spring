package com.capgemini.pecunia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.util.DBConnection;
import com.capgemini.pecunia.util.LoggerMessage;

public class PassbookMaintenanceDAOImpl implements PassbookMaintenanceDAO {

	Logger logger = Logger.getRootLogger();

	public PassbookMaintenanceDAOImpl() {
	}

	/*******************************************************************************************************
	 * - Function Name : updatePassbook(String accountId) 
	 * - Input Parameters : String accountId
	 * - Return Type : List 
	 * - Throws : PassbookException, PecuniaException 
	 * - Author : Mansi Agarwal
	 * - Creation Date : 24/09/2019 
	 * - Description : Stores the transaction details in the list and returns it to service layer
	 ********************************************************************************************************/
	
	public List<Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
		List<Transaction> transactionList = new ArrayList<Transaction>();

		
			ps = connection.prepareStatement(PassbookMaintenanceQueryMapper.QUERY_TRANS_DETAILS);
			ps.setString(1, accountId);
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Transaction details = new Transaction();
				//details.setId(resultSet.getString(1));
				//details.setTransDate((resultSet.getDate(2)).toLocalDate());
				details.setAmount(resultSet.getDouble(3));
				details.setTransFrom(resultSet.getString(4));
				details.setTransTo(resultSet.getString(5));
				details.setType(resultSet.getString(6));
				details.setOption(resultSet.getString(7));
				details.setChequeId(resultSet.getInt(8));
				details.setClosingBalance(resultSet.getDouble(9));
				transactionList.add(details);
			}
			return transactionList;
		} catch (Exception e) {
            logger.error(e.getMessage());
			throw new PassbookException(ErrorConstants.TECH_ERROR);

		} finally {

			try {

				resultSet.close();
				ps.close();
				connection.close();
				
				
			} catch (Exception e) {

				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.DB_CONNECTION_ERROR);

			}
		}

		
		

	}
	
	/*******************************************************************************************************
	 * - Function Name : updateLastUpdated(String accountId) 
	 * - Input Parameters : String accountId
	 * - Return Type : boolean 
	 * - Throws : PassbookException, PecuniaException 
	 * - Author : Mansi Agarwal
	 * - Creation Date : 25/09/2019 
	 * - Description : Updates the date of last transaction that was printed in the passbook
	 ********************************************************************************************************/
	
	
	public boolean updateLastUpdated(String accountId) throws PecuniaException, PassbookException 
	{
		boolean updated = false;
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement ps = null;		
		int queryResult = 0;
		
		try {
			ps = connection.prepareStatement(PassbookMaintenanceQueryMapper.QUERY_LAST_UPDATED);
			ps.setString(1, accountId);
			queryResult= ps.executeUpdate();
			
			if(queryResult==0)
			{
				logger.error(LoggerMessage.LAST_UPDATE_DATE_ERROR);
				throw new PassbookException(ErrorConstants.UPDATE_ACCOUNT_ERROR);

			}
			else {
				updated = true;
			}
				
		}catch(Exception e) {
			throw new PassbookException(ErrorConstants.TECH_ERROR);
		}
		finally {

			try {
				ps.close();
				connection.close();
			} catch (Exception e) 
			{
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.DB_CONNECTION_ERROR);
			}
		}
		
		logger.info(LoggerMessage.UPDATE_PASSBOOK_SUCCESSFUL);
		return updated;
	
	}

	
	
	/*******************************************************************************************************
	 * - Function Name : accountSummary(String accountId, Date startDate, Date endDate) 
	 * - Input Parameters : String accountId, Date startDate, Date endDate
	 * - Return Type : List 
	 * - Throws : PassbookException, PecuniaException 
	 * - Author : Rishav Dev
	 * - Creation Date : 24/09/2019 
	 * - Description : Stores the account summary in the list and returns it to service layer
	 ********************************************************************************************************/
	
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException {

		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		List<Transaction> transactionList = new ArrayList<Transaction>();

		try {
			ps = connection.prepareStatement(PassbookMaintenanceQueryMapper.QUERY_SUMMARY);
			ps.setString(1, accountId);
			ps.setDate(2, java.sql.Date.valueOf(startDate));
			ps.setDate(3, java.sql.Date.valueOf(endDate));
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Transaction details = new Transaction();
//				details.setId(resultSet.getString(1));
//				details.setTransDate((resultSet.getDate(2)).toLocalDate());
				details.setAmount(resultSet.getDouble(3));
				details.setTransFrom(resultSet.getString(4));
				details.setTransTo(resultSet.getString(5));
				details.setType(resultSet.getString(6));
				details.setOption(resultSet.getString(7));
				details.setChequeId(resultSet.getInt(8));
				details.setClosingBalance(resultSet.getDouble(9));
				transactionList.add(details);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PassbookException(ErrorConstants.TECH_ERROR);

		} finally {

			try {

				resultSet.close();
				ps.close();
				connection.close();
			} catch (Exception e) {
			
				logger.error(e.getMessage());
				throw new PecuniaException(ErrorConstants.DB_CONNECTION_ERROR);

			}
		}
		return transactionList;

	}

	
}
