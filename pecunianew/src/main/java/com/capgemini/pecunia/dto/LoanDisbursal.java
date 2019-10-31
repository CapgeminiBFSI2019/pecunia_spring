package com.capgemini.pecunia.dto;

public class LoanDisbursal {

	private int loanDisbursalId;
	private int loanId;
	private String accountId;
	private double disbursedAmount;
	private double dueAmount;
	private double numberOfEmiToBePaid;
	private String loanType;
	

	

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public int getLoanDisbursalId() {
		return loanDisbursalId;
	}

	public void setLoanDisbursalId(int loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(double disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getNumberOfEmiToBePaid() {
		return numberOfEmiToBePaid;
	}

	public void setNumberOfEmiToBePaid(double numberOfEmiToBePaid) {
		this.numberOfEmiToBePaid = numberOfEmiToBePaid;
	}

	public LoanDisbursal(int loanDisbursalId, int loanId, String accountId, double disbursedAmount, double dueAmount,
			double numberOfEmiToBePaid, String loanType) {
		super();
		this.loanDisbursalId = loanDisbursalId;
		this.loanId = loanId;
		this.accountId = accountId;
		this.disbursedAmount = disbursedAmount;
		this.dueAmount = dueAmount;
		this.numberOfEmiToBePaid = numberOfEmiToBePaid;
		this.loanType = loanType;

	}

	public LoanDisbursal() {

	}

	 public String toString() {
	        StringBuilder sb = new StringBuilder();
	       
	        sb.append("------------------------Printing Loan Details---------------------------- \n");
	        sb.append("loanDisbursalId=" + loanDisbursalId +"\n");
	        sb.append("loanId=: "+ loanId +"\n");
	        sb.append("accountId=: "+ accountId +"\n");
	        sb.append("disbursedAmount= : "+ disbursedAmount +"\n");
	        sb.append("dueAmount=: "+ dueAmount +"\n");
	        sb.append("numberOfEmiToBePaid=: "+ numberOfEmiToBePaid +"\n");
	        return sb.toString();
	
	

}
	 
}
