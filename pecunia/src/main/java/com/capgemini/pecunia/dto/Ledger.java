package com.capgemini.pecunia.dto;

import java.util.Date;
public class Ledger {
	private String id;
	private Date date;
	private double debitAmount;
	private double creditAmount;
	private int numOfLoans;
	private double totalLoanAmt;

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public int getNumOfLoans() {
		return numOfLoans;
	}

	public void setNumOfLoans(int numOfLoans) {
		this.numOfLoans = numOfLoans;
	}

	public double getTotalLoanAmt() {
		return totalLoanAmt;
	}

	public void setTotalLoanAmt(double totalLoanAmt) {
		this.totalLoanAmt = totalLoanAmt;
	}

	public Ledger(String id, Date date, double debitAmount, double creditAmount, int numOfLoans, double totalLoanAmt) {
		super();
		this.id = id;
		this.date = date;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.numOfLoans = numOfLoans;
		this.totalLoanAmt = totalLoanAmt;
	}

	public Ledger() {

	}
}
