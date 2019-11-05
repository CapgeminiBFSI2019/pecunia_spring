package com.capgemini.pecunia.dto;

import org.springframework.stereotype.Component;

@Component
//Specifying datatypes for Branch class in DTO
//Specifying getter and setter method for Branch class in DTO
public class Branch {
	private String id;
	private String name;
	private String ifsc;
	private String address;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Branch(String id, String name, String ifsc, String address) {
		super();
		this.id = id;
		this.name = name;
		this.ifsc = ifsc;
		this.address = address;
	}

	public Branch() {

	}

}
