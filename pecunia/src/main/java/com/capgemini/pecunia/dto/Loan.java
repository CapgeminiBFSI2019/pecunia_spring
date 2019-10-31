package com.capgemini.pecunia.dto;
public class Loan {
    private int loanId;
    private String accountId;
    private double amount;
    private String type;
    private int tenure;
    private double roi;
    private String loanStatus;
    private double emi;
    private int creditScore;

  

//    private double salary;
//	public double getSalary() {
//		return salary;
//	}
//	public void setSalary(double salary) {
//		this.salary = salary;
//	}

	/**
	 * @return the loanId
	 */
	public int getLoanId() {
		return loanId;
	}
	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the tenure
	 */
	public int getTenure() {
		return tenure;
	}
	/**
	 * @param tenure the tenure to set
	 */
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	/**
	 * @return the roi
	 */
	public double getRoi() {
		return roi;
	}
	/**
	 * @param roi the roi to set
	 */
	public void setRoi(double roi) {
		this.roi = roi;
	}
	/**
	 * @return the loanStatus
	 */
	public String getLoanStatus() {
		return loanStatus;
	}
	/**
	 * @param loanStatus the loanStatus to set
	 */
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	/**
	 * @return the emi
	 */
	public double getEmi() {
		return emi;
	}
	/**
	 * @param emi the emi to set
	 */
	public void setEmi(double emi) {
		this.emi = emi;
	}
	/**
	 * @return the creditScore
	 */
	public int getCreditScore() {
		return creditScore;
	}
	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	public Loan(int loanId, String accountId, double amount, String type, int tenure, double roi, String loanStatus,
			double emi, int creditScore) {
		super();
		this.loanId = loanId;
		this.accountId = accountId;
		this.amount = amount;
		this.type = type;
		this.tenure = tenure;
		this.roi = roi;
		this.loanStatus = loanStatus;
		this.emi = emi;
		this.creditScore = creditScore;
		
	}
	public Loan() {
		
	}
	
	  public String toString()
	    {
	        StringBuilder sb = new StringBuilder();
	        sb.append("------------------------Printing Loan Details---------------------------- \n");
	        sb.append("Loan Id: " + loanId +"\n");
	        sb.append("Account Id: "+ accountId +"\n");
	        sb.append("Amount: "+ amount +"\n");
	        sb.append("Type : "+ type +"\n");
	        sb.append("Tenure : "+ tenure +"\n");
	        sb.append("Rate of Interest : "+ roi +"\n");
	        sb.append("Loan Status: "+ loanStatus +"\n");
	        sb.append("Emi : "+ emi +"\n");
	        sb.append("Credit Score : "+ creditScore +"\n");
	        return sb.toString();
	    }
}
 

       