package com.capgemini.pecunia.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.entity.AccountEntity;
import com.capgemini.pecunia.entity.ChequeEntity;
import com.capgemini.pecunia.entity.TransactionEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.util.HibernateUtil;

public class TransactionDAOImpl implements TransactionDAO {
	
	Logger logger = Logger.getRootLogger();
	
	@Override
	public double getBalance(Account account) throws PecuniaException, TransactionException {

		double accountBalance = -1;
		org.hibernate.Transaction transaction = null;
		try {
			String accountId = account.getId();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNamedQuery("AccountEntity.getBalanceById");
			query.setParameter("accountId", accountId);
			query.setMaxResults(1);
			AccountEntity accountEntity = (AccountEntity) query.uniqueResult();
			if (accountEntity != null) {
				accountBalance = accountEntity.getBalance();
			} else {
				throw new PecuniaException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PecuniaException(e.getMessage());
		}
		return accountBalance;
	}

	@Override
	public boolean updateBalance(Account account) throws PecuniaException, TransactionException {
		boolean balanceUpdated = false;
		org.hibernate.Transaction tx = null;
		try {
			String accountId = account.getId();
			double newBalance = account.getBalance();
			Session session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			AccountEntity accountEntity = session.load(AccountEntity.class, accountId);
			accountEntity.setBalance(newBalance);
			session.update(accountEntity);

			if (accountEntity.getBalance() == newBalance) {
				balanceUpdated = true;
			} else {
				throw new TransactionException(ErrorConstants.BALANCE_UPDATE_ERROR);
			}
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TransactionException(e.getMessage());
		}
		return balanceUpdated;
	}

	@Override
	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException {
		int chequeId = 0;
		org.hibernate.Transaction txn = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			txn = session.beginTransaction();
			ChequeEntity chequeEntity = new ChequeEntity();
			chequeEntity.setNum(cheque.getNum());
			chequeEntity.setAccountNo(cheque.getAccountNo());
			chequeEntity.setHolderName(cheque.getHolderName());
			chequeEntity.setBankName(cheque.getBankName());
			chequeEntity.setIfsc(cheque.getIfsc());
			chequeEntity.setIssueDate(cheque.getIssueDate());
			chequeEntity.setStatus(cheque.getStatus());
			session.save(chequeEntity);
			chequeId = chequeEntity.getId();
			txn.commit();
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			logger.error(e.getMessage());
			throw new PecuniaException(ErrorConstants.CHEQUE_INSERTION_ERROR);
		}
		return chequeId;
	}

	

	@Override
	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException {
		int transactionId = 0;
		org.hibernate.Transaction txn = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			txn = session.beginTransaction();
			TransactionEntity transactionEntity = new TransactionEntity();
			transactionEntity.setAccountId(transaction.getAccountId());
			transactionEntity.setType(transaction.getType());
			transactionEntity.setAmount(transaction.getAmount());
			transactionEntity.setOption(transaction.getOption());
			transactionEntity.setTransDate(transaction.getTransDate().plusMinutes(330));
			transactionEntity.setChequeId(transaction.getChequeId());
			transactionEntity.setTransFrom(transaction.getTransFrom());
			transactionEntity.setTransTo(transaction.getTransTo());
			transactionEntity.setClosingBalance(transaction.getClosingBalance());
			session.save(transactionEntity);
			transactionId = transactionEntity.getId();
			txn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if (txn != null) {
				txn.rollback();
			}
			logger.error(e.getMessage());
			throw new PecuniaException(ErrorConstants.TRANSACTION_INSERTION_ERROR);
		}
		return transactionId;
	}

}
