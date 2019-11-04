package com.capgemini.pecunia.hibernate.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.query.Query;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.dto.Customer;
import com.capgemini.pecunia.entity.AccountEntity;
import com.capgemini.pecunia.entity.AddressEntity;
import com.capgemini.pecunia.entity.CustomerEntity;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.util.Constants;
import com.capgemini.pecunia.util.HibernateUtil;

@Repository
public class AccountManagementDAOImpl implements AccountManagementDAO {
	Logger logger = Logger.getRootLogger();

	@Override
	public boolean deleteAccount(Account account) throws PecuniaException, AccountException {
		boolean isDeleted = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction txn = session.beginTransaction();
			String hql = "UPDATE AccountEntity SET status='Closed' WHERE accountId=:accountId";
			Query query = session.createQuery(hql);
			query.setParameter("accountId", account.getId());
			query.setMaxResults(1);
			int rowsAffected = query.executeUpdate();
			if (rowsAffected > 0) {
				isDeleted = true;
				txn.commit();
			} else {
				throw new PecuniaException(ErrorConstants.DELETE_ACCOUNT_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.DELETE_ACCOUNT_ERROR);
		}
		logger.info(Constants.DELETE_ACCOUNT_SUCCESSFUL);
		return isDeleted;
	}

	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {

		Account acc = new Account();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "from AccountEntity where accountId= :accountId";
			Query query = session.createQuery(hql);
			query.setParameter("accountId", account.getId());
			query.setMaxResults(1);
			AccountEntity accountEntity = (AccountEntity) query.uniqueResult();
			if (accountEntity != null) {
				acc.setId(accountEntity.getAccountId());
				acc.setBalance(accountEntity.getBalance());
				acc.setAccountType(accountEntity.getType());
				acc.setStatus(accountEntity.getStatus());
				acc.setInterest(accountEntity.getInterest());
				acc.setBranchId(accountEntity.getBranchId());
				acc.setHolderId(accountEntity.getCustomerId());
				acc.setLastUpdated(accountEntity.getLastUpdated());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
		}
		logger.info(Constants.SHOW_ACCOUNT_DETAILS);
		return acc;
	}

	@Override
	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		org.hibernate.Transaction tx = null;
		String custId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(AccountEntity.class)
				    .add(Restrictions.eq(Constants.ACCOUNT_ID, account.getId()));
			criteria.setMaxResults(1);
			List<AccountEntity> list = criteria.list();
			for(AccountEntity acc : list) {
				custId= acc.getCustomerId();
			}
			tx = session.beginTransaction();
			CustomerEntity customerEntity = session.load(CustomerEntity.class, custId);
			customerEntity.setName(customer.getName());
			session.update(customerEntity);

			if (customer.getName().equals(customerEntity.getName())) {
				isUpdated = true;
			} else {
				throw new PecuniaException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			}
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_NAME_SUCCESSFUL);
		return isUpdated;
	}

	@Override
	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		org.hibernate.Transaction tx = null;
		String custId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(AccountEntity.class)
		    .add(Restrictions.eq(Constants.ACCOUNT_ID, account.getId()));
			criteria.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<AccountEntity> list = criteria.list();
			for(AccountEntity acc : list) {
				custId= acc.getCustomerId();
			}
			tx = session.beginTransaction();
			CustomerEntity customerEntity = session.load(CustomerEntity.class, custId);
			customerEntity.setContact(customer.getContact());
			session.update(customerEntity);

			if (customer.getContact().equals(customerEntity.getContact())) {
				isUpdated = true;
			} else {
				throw new PecuniaException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			}	
			tx.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_CONTACT_SUCCESSFUL);
		return isUpdated;
	}

	@Override
	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId=null;
		String addrId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(AccountEntity.class)
		    .add(Restrictions.eq(Constants.ACCOUNT_ID, account.getId()));
			criteria.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<AccountEntity> list = criteria.list();
			for(AccountEntity acc : list) {
				custId= acc.getCustomerId();
				@SuppressWarnings("deprecation")
				Criteria criteria1 = session.createCriteria(CustomerEntity.class)
			    .add(Restrictions.eq(Constants.CUSTOMER_ID, custId));
				criteria1.setMaxResults(1);
				@SuppressWarnings("unchecked")
				List<CustomerEntity> listCustomer = criteria1.list();
				for(CustomerEntity cust: listCustomer) {
					addrId = cust.getAddressId();
				}
			}

			Transaction txn = session.beginTransaction();
			AddressEntity addressEntity = session.load(AddressEntity.class, addrId);
			addressEntity.setAddressLine1(address.getLine1());
			addressEntity.setAddressLine2(address.getLine2());
			addressEntity.setCity(address.getCity());
			addressEntity.setState(address.getState());
			addressEntity.setCountry(address.getCountry());
			addressEntity.setZipcode(address.getZipcode());
			session.update(addressEntity);

			if (address.getLine1().equals(addressEntity.getAddressLine1()) && 
					address.getLine2().equals(addressEntity.getAddressLine2()) && address.getCity().equals(addressEntity.getCity()) &&
					address.getState().equals(addressEntity.getState()) && address.getCountry().equals(addressEntity.getCountry()) &&
					address.getZipcode().equals(addressEntity.getZipcode())) {
				isUpdated = true;
			} else {
				throw new PecuniaException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			}	
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_ADDRESS_SUCCESSFUL);
		return isUpdated;
	}

	@Override
	public String addCustomerDetails(Customer customer, Address address)
			throws PecuniaException, AccountException, SQLException {
		String addrId = null;

		String custId = null;
		CustomerEntity cust = new CustomerEntity();
		AddressEntity addr = new AddressEntity();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = session.beginTransaction();
		addr.setAddressLine1(address.getLine1());
		addr.setAddressLine2(address.getLine2());
		addr.setCity(address.getCity());
		addr.setState(address.getState());
		addr.setCountry(address.getCountry());
		addr.setZipcode(address.getZipcode());
		session.save(addr);
		session.getTransaction().commit();
		try {
			String hql = "SELECT MAX(id) FROM AddressEntity";
			Query query = session.createQuery(hql);

			query.setMaxResults(1);

			if (query.uniqueResult() != null) {
				addrId = (String) query.uniqueResult();

			} else {
				throw new PecuniaException(ErrorConstants.ADD_DETAILS_ERROR);
			}

		} catch (Exception e) {

			throw new AccountException(ErrorConstants.ADD_DETAILS_ERROR);
		}

		session = HibernateUtil.getSessionFactory().openSession();
		txn = session.beginTransaction();
		cust.setAadhar(customer.getAadhar());
		cust.setName(customer.getName());
		cust.setGender(customer.getGender());
		cust.setContact(customer.getContact());
		cust.setDob(customer.getDob());
		cust.setPan(customer.getPan());
		cust.setAddressId(addrId);
		session.save(cust);
		session.getTransaction().commit();
		try {

			String hql1 = "SELECT MAX(customerId) FROM CustomerEntity";
			Query query = session.createQuery(hql1);

			query.setMaxResults(1);
			// cust = (CustomerEntity) query.uniqueResult();
			if (query.uniqueResult() != null) {
				custId = (String) query.uniqueResult();

			} else {
				throw new PecuniaException(ErrorConstants.ADD_DETAILS_ERROR);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ADD_DETAILS_ERROR);
		}
		logger.info(Constants.ADD_ACCOUNT_SUCCESSFUL);
		return custId;

	}

	@Override
	public String addAccount(Account account) throws PecuniaException, AccountException, SQLException {
		// String accId = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		// session.beginTransaction();
		Transaction txn = session.beginTransaction();

		AccountEntity acc = new AccountEntity();

		acc.setBranchId(account.getBranchId());
		acc.setType(account.getAccountType());
		acc.setStatus(account.getStatus());
		acc.setBalance(account.getBalance());
		acc.setInterest(account.getInterest());
		acc.setLastUpdated(account.getLastUpdated());
		acc.setStatus(Constants.ACCOUNT_STATUS[0]);
		acc.setAccountId(account.getId());
		acc.setCustomerId(account.getHolderId());
		session.save(acc);
		txn.commit();
		logger.info(Constants.ADD_ACCOUNT_SUCCESSFUL);
		return account.getId();
	}

	@Override
	public String calculateAccountId(Account account) throws PecuniaException, AccountException {
		long oldId = 0;
		String oldIdstr = null;
		String id = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "SELECT max(accountId) FROM AccountEntity WHERE accountId LIKE :accountId";
			Query query = session.createQuery(hql);
			query.setParameter("accountId", account.getId() + "%");
			query.setMaxResults(1);
			if (query.uniqueResult() != null) {
				oldIdstr = (String) query.uniqueResult();
			} else {
				oldIdstr = account.getId() + "000000";
			}
			oldId = Long.parseLong(oldIdstr);
			id = Long.toString(oldId + 1);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
		logger.info(Constants.ACCOUNT_ID_CALCULATED);
		return id;
	}

	@Override
	public boolean validateAccountId(Account account) throws PecuniaException, AccountException {
		boolean isValidated = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "SELECT accountId FROM AccountEntity WHERE accountId=:accountId";
			Query query = session.createQuery(hql);
			query.setParameter("accountId", account.getId());
			query.setMaxResults(1);
			if (query.uniqueResult() != null) {
				isValidated = true;
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ERROR_VALIDATION);
		}
		logger.info(Constants.ACCOUNT_ID_VALIDATED);
		return isValidated;
	}

}
