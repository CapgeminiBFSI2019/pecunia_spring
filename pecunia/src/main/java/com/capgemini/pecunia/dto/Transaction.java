package com.capgemini.pecunia.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
	private int id;
	private String accountId;
	private String type;
	private Double amount;
	private String option;
	private LocalDateTime transDate;
	private int chequeId;
	private String transFrom;
	private String transTo;
	private Double closingBalance;

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public LocalDateTime getTransDate() {
		return transDate;
	}

	public void setTransDate(LocalDateTime transDate) {
		this.transDate = transDate;
	}

	public int getChequeId() {
		return chequeId;
	}

	public void setChequeId(int chequeId) {
		this.chequeId = chequeId;
	}

	public String getTransFrom() {
		return transFrom;
	}

	public void setTransFrom(String transFrom) {
		this.transFrom = transFrom;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}

	public Double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public Transaction(int id, String type, Double amount, String option, LocalDateTime transDate,
			int chequeId, String transFrom, String transTo, Double closingBalance) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.option = option;
		this.transDate = transDate;
		this.chequeId = chequeId;
		this.transFrom = transFrom;
		this.transTo = transTo;
		this.closingBalance = closingBalance;
	}

	public Transaction() {

	}
}
