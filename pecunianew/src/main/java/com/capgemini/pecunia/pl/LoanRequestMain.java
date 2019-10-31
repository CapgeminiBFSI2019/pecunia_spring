package com.capgemini.pecunia.pl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.capgemini.pecunia.dto.Loan;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.service.LoanService;
import com.capgemini.pecunia.service.LoanServiceImpl;
import com.capgemini.pecunia.util.Constants;

public class LoanRequestMain {
	public static void main(String[] args) throws IOException, NumberFormatException,PecuniaException {
		Scanner scanner = new Scanner(System.in);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		LoanServiceImpl lsi = new LoanServiceImpl();
		try {
				System.out.println("Enter account Id : ");
				String accId = br.readLine();
				System.out.println("Enter Loan Amount : ");
				double amount = scanner.nextDouble();
				System.out.println("Enter Rate of interest :");
				double roi = scanner.nextDouble();
				System.out.println("Enter Tenure:");
				int tenure = scanner.nextInt();
				System.out.println("Enter credit score : ");
				int creditScore = scanner.nextInt();
				System.out.println(
						"Select type of Loan :\n Type '1' for Personal Loan \n Type '2' for House Loan \n Type '3' for Vehicle Loan \n Type '4' for Jewel Loan");
				int input = Integer.parseInt(br.readLine());
				String type = null;
				if (input == 1) {
					type = Constants.LOAN_TYPE[0];
				} else if (input == 2) {
					type = Constants.LOAN_TYPE[1];
				} else if (input == 3) {
					type = Constants.LOAN_TYPE[2];
				} else if (input == 4) {
					type = Constants.LOAN_TYPE[3];
				}
				double emi = lsi.calculateEMI(amount, tenure, roi);
				Loan loan = new Loan();
				loan.setAmount(amount);
				loan.setCreditScore(creditScore);
				loan.setEmi(emi);
				loan.setLoanStatus("Pending");
				loan.setRoi(roi);
				loan.setTenure(tenure);
				loan.setType(type);
				loan.setAccountId(accId);
				LoanService loaserim = new LoanServiceImpl();
				int result = loaserim.createLoanRequest(loan);
				
					System.out.println("Loan request created for account id: '"+accId+"' for "+type);
				

		} catch (LoanException e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();
			br.close();
		}
	}
}
