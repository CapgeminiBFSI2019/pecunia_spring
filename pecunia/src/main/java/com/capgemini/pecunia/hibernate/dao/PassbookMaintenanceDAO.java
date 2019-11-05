package com.capgemini.pecunia.hibernate.dao;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.PassbookException;
public interface PassbookMaintenanceDAO {
	public List<Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException;

	public boolean updateLastUpdated(String accountId) throws PecuniaException, PassbookException;

	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException;
}
