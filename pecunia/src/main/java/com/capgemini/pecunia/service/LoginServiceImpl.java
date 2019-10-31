package com.capgemini.pecunia.service;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.LoggerMessage;
import com.capgemini.pecunia.util.Utility;

public class LoginServiceImpl implements LoginService {

	Logger logger = Logger.getRootLogger();

	public LoginServiceImpl() {

	}

	/*******************************************************************************************************
	 * - Function Name : validateEmail(Login login) - Input Parameters : Login login
	 * Return Type : boolean - Throws : LoginException - Author : Kumar Saurabh -
	 * Creation Date : 24/09/2019 - Description : Validating an account by setting
	 * secretKey and checking validity by comparing password and hashPassword
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean validateEmail(Login login) throws PecuniaException, LoginException {
		boolean isValidated = false;

		com.capgemini.pecunia.hibernate.dao.LoginDAO loginDAO = new com.capgemini.pecunia.hibernate.dao.LoginDAOImpl();
		String password = null;
		String secretKey = loginDAO.validateEmail(login);
		if (secretKey == null) {
			throw new LoginException(ErrorConstants.LOGIN_ERROR);
		} else {
			byte arr[] = null;
			try {
				arr = Utility.getSHA(login.getPassword() + secretKey);
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.LOGIN_ERROR);
			}
			String hashPassword = Utility.toHexString(arr);
			Login loginNew = new Login(login.getUsername(), null);
			try {
				password = loginDAO.fetchPassword(loginNew);
				if (password.equals(hashPassword)) {
					isValidated = true;
					logger.info(LoggerMessage.LOGIN_SUCCESSFUL);
				}
				else {
					throw new LoginException(ErrorConstants.LOGIN_ERROR);
				}
			} catch (LoginException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.LOGIN_ERROR);
			}
		}
		return isValidated;
	}
}