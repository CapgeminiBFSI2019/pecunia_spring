package com.capgemini.pecunia.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
@Component
public class Cheque {

	private String id;
	private int num;
	private String accountNo;
	private String holderName;
	private String bankName;
	private String ifsc;
	private LocalDate issueDate;
	private String status;

	public Cheque() {

	}

	public Cheque(String id, int num, String accountNo, String holderName, String bankName, String ifsc,
			LocalDate issueDate, String status) {
		super();
		this.id = id;
		this.num = num;
		this.accountNo = accountNo;
		this.holderName = holderName;
		this.bankName = bankName;
		this.ifsc = ifsc;
		this.issueDate = issueDate;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
