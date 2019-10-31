package com.capgemini.pecunia.service;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Cheque;
import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;

public interface TransactionService {

	public double getBalance(Account account) throws TransactionException, PecuniaException;

	public boolean updateBalance(Account account) throws TransactionException, PecuniaException;

	public int creditUsingSlip(Transaction transaction) throws TransactionException, PecuniaException;

	public int debitUsingSlip(Transaction transaction) throws TransactionException, PecuniaException;

	public int creditUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException;

	public int debitUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException;


}
