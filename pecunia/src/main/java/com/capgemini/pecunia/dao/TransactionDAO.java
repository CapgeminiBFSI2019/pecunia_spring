package com.capgemini.pecunia.dao;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;

public interface TransactionDAO {
	public double getBalance(Account account) throws PecuniaException, TransactionException;

	public boolean updateBalance(Account account) throws PecuniaException, TransactionException;

	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException;

	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException;
}
