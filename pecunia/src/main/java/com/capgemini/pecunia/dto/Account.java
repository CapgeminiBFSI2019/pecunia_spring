package com.capgemini.pecunia.dto;

import java.time.LocalDateTime;

public class Account {

	private String id;
	private String holderId;
	private String branchId;
	private String accountType;
	private String status;
	private double balance;
	private double interest;
	private LocalDateTime lastUpdated;

	public Account() {

	}

	public Account(String id, String holderId, String branchId, String accountType, String status, double balance,
			double interest, LocalDateTime lastUpdated) {
		super();
		this.id = id;
		this.holderId = holderId;
		this.branchId = branchId;
		this.accountType = accountType;
		this.status = status;
		this.balance = balance;
		this.interest = interest;
		this.lastUpdated = lastUpdated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHolderId() {
		return holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
