package com.capgemini.pecunia.hibernate.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.Transaction;
import com.capgemini.pecunia.entity.TransactionEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.HibernateUtil;
//implementing hibernate for PassbookMaintenance DAO layer
//fetching the data for passbook maintenance operations from PassbookMaintenance DAO layer

@Repository
public class PassbookMaintenanceDAOImpl implements PassbookMaintenanceDAO {
	
	private int transId;
	private String type;
	private double amount;
	private String option;
	private LocalDateTime transDate;
	private int chequeId;
	private String transFrom;
	private String transTo;
	private double closingBalance;

	@Override
	public List<com.capgemini.pecunia.dto.Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException {
		ArrayList<com.capgemini.pecunia.dto.Transaction> transList = new ArrayList<>();
		System.out.println("dao");
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "from TransactionEntity where accountId= :accountId AND date BETWEEN (SELECT lastUpdated from AccountEntity where accountId= :accountId) and :currentDate";
			Query<TransactionEntity> query = session.createQuery(hql);
			query.setParameter("accountId", accountId);
			query.setParameter("currentDate",java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(330)));
			List<TransactionEntity> results = (List<TransactionEntity>)query.list();
			System.out.println(results);
            System.out.println("****************");
            transList = passbookDetails(results);            
            System.out.println(transList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
            throw new PassbookException(ErrorConstants.UPDATE_PASSBOOK_ERROR);
        }
	 return transList;
	}
		

	@Override
	public boolean updateLastUpdated(String accountId) throws PecuniaException, PassbookException {
		
		boolean isUpdated = false;
		System.out.println("before try");
		try {
			System.out.println("try update");
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction txn = session.beginTransaction();
			String hql = "UPDATE AccountEntity SET lastUpdated = :lastUpdated WHERE accountId= :accountId";
			Query query = session.createQuery(hql);
			query.setParameter("accountId", accountId);
			query.setParameter("lastUpdated", LocalDateTime.now().plusMinutes(330));
			int rowsAffected = query.executeUpdate();
			
			if (rowsAffected > 0) {
				isUpdated = true;
				txn.commit();
			} else {
				System.out.println("error");
				throw new PecuniaException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			}
		} catch (Exception e) {
			System.out.println("catch error");
			e.printStackTrace();
			throw new PassbookException(e.getMessage());
		}
		return isUpdated;
	}

	public List<com.capgemini.pecunia.dto.Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException {
		ArrayList<com.capgemini.pecunia.dto.Transaction> transList = new ArrayList<>();
		System.out.println("dao");
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hql = "from TransactionEntity where accountId= :accountId AND date BETWEEN :startDate and :endDate";
			Query<TransactionEntity> query = session.createQuery(hql);
 			query.setParameter("accountId", accountId);
			query.setParameter("startDate", java.sql.Date.valueOf(startDate));
			query.setParameter("endDate", java.sql.Date.valueOf(endDate));
			List<TransactionEntity> results = (List<TransactionEntity>)query.list();
			System.out.println(results);
            System.out.println("****************");
            transList = passbookDetails(results);            
            System.out.println(transList);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
            throw new PassbookException(ErrorConstants.TECH_ERROR);
        }
	 return transList;
	}

	private ArrayList<com.capgemini.pecunia.dto.Transaction> passbookDetails(List<TransactionEntity> res){
		ArrayList<com.capgemini.pecunia.dto.Transaction> transList = new ArrayList<>();
		for(TransactionEntity object : res){
			transId = object.getId();
			type = object.getType();
			amount = object.getAmount();
			option = object.getOption();
			transDate = object.getTransDate();
			chequeId = object.getChequeId();
			transFrom = object.getTransFrom();
			transTo = object.getTransTo();
			closingBalance = object.getClosingBalance();
			
			com.capgemini.pecunia.dto.Transaction transaction = new com.capgemini.pecunia.dto.Transaction(transId, type, amount, option, transDate, chequeId, transFrom, transTo, closingBalance );
			transList.add(transaction);
		}
		
		return transList;
		
	}


	
	

}
