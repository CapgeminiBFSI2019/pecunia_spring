package com.capgemini.pecunia.hibernate.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.entity.LoanRequestEntity;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.HibernateUtil;

@Repository

public class LoanDAOImpl implements LoanDAO {
	Logger logger = Logger.getRootLogger();

	/*******************************************************************************************************
     * - Function Name :addLoanDetails(Loan loan)
     * - Input Parameters : Loan loan
     * - Return Type : int
     * - Throws : LoanException , Pecunia Exception
     * - Author : Rishabh Rai
     * - Creation Date : 27/10/2019
     * - Description : Addition of loan request details
     ********************************************************************************************************/
	public int addLoanDetails(Loan loan) throws PecuniaException, LoanException {

		int loanId = 0;
		org.hibernate.Transaction txn = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();

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
			if (txn != null) {
				txn.rollback();
			}
			logger.error(e.getMessage());
			throw new PecuniaException(ErrorConstants.LOAN_ADD_ERROR);

		}
		return loanId;

	}

}