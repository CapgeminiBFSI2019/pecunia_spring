package com.capgemini.pecunia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.DBConnection;

public class LoginDAOImpl implements LoginDAO {

	Logger logger = Logger.getRootLogger();

	public LoginDAOImpl() {
	}
	/*******************************************************************************************************
	 * Function Name : validateEmail(Login login) - Input Parameters : Login login
	 * Return Type : String Author : Rishabh Rai - Creation Date :24/09/2019
	 * Description :Validate Email from Database ,  returns Email
	 ********************************************************************************************************/
	@Override
	public String validateEmail(Login login) throws PecuniaException, LoginException {
		String secretKey = null;
		Connection connection = null;
		connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(LoginQueryMapper.GET_SALT);
			preparedStatement.setString(1, login.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				secretKey = resultSet.getString("secret_key");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new LoginException(ErrorConstants.LOGIN_ERROR);
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.LOGIN_ERROR);
			}
		}
		return secretKey;
	}
	/*******************************************************************************************************
	 * Function Name :fetchPassword(Login login) - Input Parameters : Login login
	 * Return Type : String Author :Kumar Saurabh - Creation Date : 24/09/2019
	 * Description : Fetching validation details from database
	 ********************************************************************************************************/
	@Override
	public String fetchPassword(Login login) throws PecuniaException, LoginException {
		Connection connection = null;
		String password = null;
		connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(LoginQueryMapper.GET_PASSWORD);
			preparedStatement.setString(1, login.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				password = resultSet.getString("password");
			}
			return password;
		} catch (SQLException e) {
			logger.error(ErrorConstants.LOGIN_ERROR);
			throw new LoginException(ErrorConstants.LOGIN_ERROR);
		} finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.DB_CONNECTION_ERROR);
			}
		}
	}

}
