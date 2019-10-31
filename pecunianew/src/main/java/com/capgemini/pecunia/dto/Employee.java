package com.capgemini.pecunia.dto;

public class Employee {

	private String id;
	private String name;
	private String branchId;
	private String designation;

	public Employee(String id, String name, String branchId, String designation, String username, String password,
			String salt) {
		super();
		this.id = id;
		this.name = name;
		this.branchId = branchId;
		this.designation = designation;

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Employee() {

	}

}
