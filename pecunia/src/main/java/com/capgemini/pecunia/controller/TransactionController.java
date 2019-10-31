package com.capgemini.pecunia.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.dto.Transaction;

@RestController
public class TransactionController {

	@PostMapping(path = "/creditCheque")
	public String creditUsingCheque(@RequestBody Transaction transaction) {
		System.out.println("Amount :" + transaction.getAmount());
		return null;
	}
}
