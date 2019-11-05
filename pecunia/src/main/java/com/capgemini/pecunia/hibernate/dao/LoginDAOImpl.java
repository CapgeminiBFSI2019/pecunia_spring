package com.capgemini.pecunia.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.entity.LoginEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.HibernateUtil;
//implementing hibernate for Login DAO layer
//fetching the data for Login operation from Login DAO layer

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Override
	public String validateEmail(Login login) throws PecuniaException, LoginException {
		String secretKey = null;
		org.hibernate.Transaction transaction = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNamedQuery("LoginEntity.getsecret_keyByusername");
			query.setParameter("email", login.getUsername());
			query.setMaxResults(1);
			LoginEntity loginEntity = (LoginEntity) query.uniqueResult();
			if (loginEntity != null) {
				secretKey = loginEntity.getSecretKey();
			} else {
				throw new PecuniaException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

	
		
		transaction.commit();
		session.close();
	}
	catch (Exception e) {
		throw new PecuniaException(e.getMessage());
	}
		return secretKey;

	}

	@Override
	public String fetchPassword(Login login) throws PecuniaException, LoginException {

		String password = null;

		org.hibernate.Transaction transaction = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNamedQuery("LoginEntity.getpasswordByusername");
			query.setParameter("email", login.getUsername());
			query.setMaxResults(1);
			LoginEntity loginEntity = (LoginEntity) query.uniqueResult();
			if (loginEntity != null) {
				password = loginEntity.getPassword();
			} else {
				throw new PecuniaException(ErrorConstants.LOGIN_ERROR);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new PecuniaException(e.getMessage());
		}
		return password;

	}

}

