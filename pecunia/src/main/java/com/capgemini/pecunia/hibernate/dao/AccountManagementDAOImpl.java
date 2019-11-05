package com.capgemini.pecunia.hibernate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
import com.capgemini.pecunia.util.Constants;
import com.capgemini.pecunia.util.HibernateUtil;
//implementing hibernate for AccountManagement DAO layer
//fetching the data for account management operations from AccountManagement DAO layer

@Repository
public class AccountManagementDAOImpl implements AccountManagementDAO {
	Logger logger = Logger.getRootLogger();

	/*******************************************************************************************************
	 * - Function Name : deleteAccount(Account account) 
	 * - Input Parameters : Account account 
	 * - Return Type : boolean 
	 * - Throws : AccountException 
	 * - Author : Rohit Kumar 
	 * - Creation Date : 27/10/2019 
	 * - Description : Deleting an account by setting account status "Closed"
	 * @throws PecuniaException
	 ********************************************************************************************************/
	
	public boolean deleteAccount(Account account) throws PecuniaException, AccountException {
		boolean isDeleted = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaUpdate<AccountEntity> criteriaUpdate = cb.createCriteriaUpdate(AccountEntity.class);
			Root<AccountEntity> rootNew = criteriaUpdate.from(AccountEntity.class);
			criteriaUpdate.set("status", Constants.ACCOUNT_STATUS[1]);
			criteriaUpdate.where(cb.equal(rootNew.get(Constants.ACCOUNT_ID), account.getId()));

			Transaction transaction = session.beginTransaction();
			session.createQuery(criteriaUpdate).executeUpdate();
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.DELETE_ACCOUNT_ERROR);
		}
		logger.info(Constants.DELETE_ACCOUNT_SUCCESSFUL);
		return isDeleted;
	}

	/*******************************************************************************************************
	 * - Function Name : showAccountDetails(Account account) 
	 * - Input Parameters : Account account 
	 * - Return Type : String 
	 * - Throws : AccountException 
	 * - Author : Rohit Kumar	 
	 * - Creation Date : 29/10/2019 
	 * - Description : Returns details of an account by account ID
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {

		Account acc = new Account();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<AccountEntity> cr = cb.createQuery(AccountEntity.class);
			Root<AccountEntity> root = cr.from(AccountEntity.class);
			cr.select(root).where(cb.equal(root.get(Constants.ACCOUNT_ID), account.getId()));
			Query<AccountEntity> q = session.createQuery(cr);
			AccountEntity accObj = q.getSingleResult();
			if (accObj != null) {
				acc.setId(accObj.getAccountId());
				acc.setBalance(accObj.getBalance());
				acc.setAccountType(accObj.getType());
				acc.setStatus(accObj.getStatus());
				acc.setInterest(accObj.getInterest());
				acc.setBranchId(accObj.getBranchId());
				acc.setHolderId(accObj.getCustomerId());
				acc.setLastUpdated(accObj.getLastUpdated());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
		}
		logger.info(Constants.SHOW_ACCOUNT_DETAILS);
		return acc;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerName(Account account, Customer customer) 
	 * - Input Parameters : Account account, Customer customer 
	 * - Return Type : boolean
	 * - Throws : AccountException 
	 * - Author : Aditi Singh 
	 * - Creation Date : 27/10/2019 
	 * - Description : Updating customer name
	 * @throws PecuniaException
	 ********************************************************************************************************/
	
	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<AccountEntity> cr = cb.createQuery(AccountEntity.class);
			Root<AccountEntity> root = cr.from(AccountEntity.class);
			cr.select(root).where(cb.equal(root.get(Constants.ACCOUNT_ID), account.getId()));
			Query<AccountEntity> q = session.createQuery(cr);
			AccountEntity acc = q.getSingleResult();
			custId = acc.getCustomerId();
			CriteriaUpdate<CustomerEntity> criteriaUpdate = cb.createCriteriaUpdate(CustomerEntity.class);
			Root<CustomerEntity> rootNew = criteriaUpdate.from(CustomerEntity.class);
			criteriaUpdate.set("name", customer.getName());
			criteriaUpdate.where(cb.equal(rootNew.get(Constants.CUSTOMER_ID), custId));

			Transaction transaction = session.beginTransaction();
			session.createQuery(criteriaUpdate).executeUpdate();
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_NAME_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerContact(Account account, Customer customer) 
	 * - Input Parameters : Account account, Customer customer 
	 * - Return Type : boolean
	 * - Throws : AccountException 
	 * - Author : Aditi Singh 
	 * - Creation Date : 27/10/2019 
	 * - Description : Updating customer contact
	 * @throws PecuniaException
	 ********************************************************************************************************/
	
	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<AccountEntity> cr = cb.createQuery(AccountEntity.class);
			Root<AccountEntity> root = cr.from(AccountEntity.class);
			cr.select(root).where(cb.equal(root.get(Constants.ACCOUNT_ID), account.getId()));
			Query<AccountEntity> q = session.createQuery(cr);
			AccountEntity acc = q.getSingleResult();
			custId = acc.getCustomerId();
			CriteriaUpdate<CustomerEntity> criteriaUpdate = cb.createCriteriaUpdate(CustomerEntity.class);
			Root<CustomerEntity> rootNew = criteriaUpdate.from(CustomerEntity.class);
			criteriaUpdate.set("contact", customer.getContact());
			criteriaUpdate.where(cb.equal(rootNew.get(Constants.CUSTOMER_ID), custId));

			Transaction transaction = session.beginTransaction();
			session.createQuery(criteriaUpdate).executeUpdate();
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_CONTACT_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerAddress(Account account, Address address) 
	 * -Input Parameters : Account account, Address address 
	 * - Return Type : boolean 
	 * - Throws : AccountException 
	 * - Author : Aditi Singh 
	 * - Creation Date : 27/10/2019
	 * - Description : Updating customer address
	 * @throws PecuniaException
	 ********************************************************************************************************/
	
	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		String addrId = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<AccountEntity> cr = cb.createQuery(AccountEntity.class);
			Root<AccountEntity> root = cr.from(AccountEntity.class);
			cr.select(root).where(cb.equal(root.get(Constants.ACCOUNT_ID), account.getId()));
			Query<AccountEntity> q = session.createQuery(cr);
			AccountEntity acc = q.getSingleResult();
			custId = acc.getCustomerId();
			CriteriaBuilder cb2 = session.getCriteriaBuilder();
			CriteriaQuery<CustomerEntity> cr2 = cb2.createQuery(CustomerEntity.class);
			Root<CustomerEntity> root2 = cr2.from(CustomerEntity.class);
			cr2.select(root2).where(cb2.equal(root2.get(Constants.CUSTOMER_ID), custId));
			Query<CustomerEntity> q2 = session.createQuery(cr2);
			CustomerEntity cust = q2.getSingleResult();
			addrId = cust.getAddressId();
			CriteriaUpdate<AddressEntity> criteriaUpdate = cb.createCriteriaUpdate(AddressEntity.class);
			Root<AddressEntity> rootNew = criteriaUpdate.from(AddressEntity.class);
			criteriaUpdate.set("addressLine1", address.getLine1());
			criteriaUpdate.set("addressLine2", address.getLine2());
			criteriaUpdate.set("city", address.getCity());
			criteriaUpdate.set("state", address.getState());
			criteriaUpdate.set("country", address.getCountry());
			criteriaUpdate.set("zipcode", address.getZipcode());
			criteriaUpdate.where(cb.equal(rootNew.get(Constants.ADDRESS_ID), addrId));
			Transaction transaction = session.beginTransaction();
			session.createQuery(criteriaUpdate).executeUpdate();
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
		logger.info(Constants.UPDATE_ADDRESS_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : addCustomerDetails(Customer customer, Address address)
	 * - Input Parameters : Customer customer, Address address
	 * - Return Type : String 
	 * - Throws : AccountException 
	 * - Author : Vidushi Razdan 
	 * - Creation Date : 27/10/2019 
	 * - Description : Addition of address and customer details for creation of new account 
	 * @throws PecuniaException
	 ********************************************************************************************************/	
	
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
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Object> crt1 = cb.createQuery(Object.class);
			Root<AddressEntity> root1 = crt1.from(AddressEntity.class);
			crt1.select(cb.max(root1.get(Constants.ADDRESS_ID)));
			Query query1 = session.createQuery(crt1);

			

			List results = query1.getResultList();
			for (Object addrObj : results) {
				addrId = (String) addrObj;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Object> crt1 = cb.createQuery(Object.class);
			Root<CustomerEntity> root1 = crt1.from(CustomerEntity.class);
			crt1.select(cb.max(root1.get(Constants.CUSTOMER_ID)));
			Query query1 = session.createQuery(crt1);
			List results = query1.getResultList();
			for (Object custObj : results) {
				custId = (String) custObj;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ADD_DETAILS_ERROR);
		}
		logger.info(Constants.ADD_ACCOUNT_SUCCESSFUL);
		return custId;
	}

	/*******************************************************************************************************
	 * - Function Name : addAccount(Customer customer, Address address,Account account) 
	 * - Input Parameters : Customer customer, Address address,Account account 
	 * - Return Type : String 
	 * - Throws : AccountException 
	 * - Author : Vidushi Razdan 
	 * - Creation Date : 27/10/2019 
	 * - Description : Addition of new Account
	 * @throws PecuniaException
	 ********************************************************************************************************/	
	
	public String addAccount(Account account) throws PecuniaException, AccountException, SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
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

	/*******************************************************************************************************
	 * - Function Name : calculateAccountId(Account account) 
	 * - Input Parameters : Account account 
	 * - Return Type : String 
	 * - Throws : AccountException 
	 * - Author : Aditi Singh
	 * - Creation Date : 27/10/2019 
	 * - Description : Calculation of new Account ID 
	 * @throws PecuniaException
	 ********************************************************************************************************/	
	
	public String calculateAccountId(Account account) throws PecuniaException, AccountException {
		long oldId = 0;
		String oldIdstr = null;
		String id = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Object> crt1 = cb.createQuery(Object.class);
			Root<AccountEntity> root1 = crt1.from(AccountEntity.class);
			crt1.select(cb.max(root1.get(Constants.ACCOUNT_ID))).where(cb.like(root1.<String>get(Constants.ACCOUNT_ID), account.getId()+"%"));
			Query query1 = session.createQuery(crt1);
			List results = query1.getResultList();			
			for(Object accObj : results) {
				if(accObj!=null) {
					oldIdstr = (String) accObj;
				}
				else {
					oldIdstr = account.getId() + "000000";
				}
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

	/*******************************************************************************************************
	 * - Function Name : validateAccountId(Account account) 
	 * - Input Parameters : Account account 
	 * - Return Type : boolean
	 * - Throws : AccountException 
	 * - Author : Aditi Singh 
	 * - Creation Date : 27/10/2019 
	 * - Description : Validation of Account ID
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean validateAccountId(Account account) throws PecuniaException, AccountException {
		boolean isValidated = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<AccountEntity> cr = cb.createQuery(AccountEntity.class);
			Root<AccountEntity> root = cr.from(AccountEntity.class);
			cr.select(root).where(cb.equal(root.get(Constants.ACCOUNT_ID), account.getId()));
			Query<AccountEntity> q = session.createQuery(cr);
			List results = q.getResultList();
			if (!results.isEmpty()) {
				isValidated = true;
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(e.getMessage());
		}
		logger.info(Constants.ACCOUNT_ID_VALIDATED);
		return isValidated;
	}

}
