package com.capgemini.pecunia.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.entity.LoanRequestEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.HibernateUtil;

public class LoanDAOImpl implements LoanDAO {

	@Override
	public int addLoanDetails(Loan loan) throws PecuniaException, LoanException {
		// TODO Auto-generated method stub
		// return false;
		int loanId = 0;
		org.hibernate.Transaction txn = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			//begin here
			 txn = session.beginTransaction();
			LoanRequestEntity loanRequestEntity = new LoanRequestEntity();
			loanRequestEntity.setAccountId(loan.getAccountId());
			loanRequestEntity.setAmount(loan.getAmount());
			loanRequestEntity.setCreditScore(loan.getCreditScore());
			loanRequestEntity.setEmi(loan.getEmi());
			loanRequestEntity.setRoi(loan.getRoi());
			loanRequestEntity.setTenure(loan.getTenure());
			loanRequestEntity.setType(loan.getType());
			loanRequestEntity.setStatus(loan.getLoanStatus());
			loanRequestEntity.setLoanId(loan.getLoanId());
			session.save(loanRequestEntity);
			loanId = loanRequestEntity.getLoanId();
			txn.commit();


		} catch (Exception e) {
			if(txn != null) {
				txn.rollback();
			}
			System.out.println("LoanDao Error :"+e.getMessage());
			e.printStackTrace();
			throw new PecuniaException(ErrorConstants.LOAN_ADD_ERROR);

		}
		return loanId;

	}

}