package com.capgemini.pecunia.hibernate.dao;

import org.hibernate.Session;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.entity.LoanRequestEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.HibernateUtil;

public class LoanrequestDAOImpl implements LoanRequestDAO{

//	@Override
//	public boolean addLoanDetails(Loan loan) throws PecuniaException, LoanException {
//		// TODO Auto-generated method stub
//		return false;
//	}
	public int addLoanDetails(Loan loan) throws PecuniaException, LoanException {
		int loanId = 0;
		org.hibernate.Transaction txn = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			LoanRequestEntity loanRequestEntity = new LoanRequestEntity();
			loanRequestEntity.setStatus(loan.getLoanStatus());
			loanRequestEntity.setLoanId(loan.getLoanId());
			session.save(loanRequestEntity);
			loanId = loanRequestEntity.getLoanId();
			txn.commit();
			
		} 
		catch (Exception e)
		{
			if(txn != null)
			{
				txn.rollback();
			}
			
			System.out.println("LoanDao Error :"+e.getMessage());
			e.printStackTrace();
			throw new PecuniaException(ErrorConstants.LOAN_ADD_ERROR);

		}
		
		return loanId;

		}
	}


