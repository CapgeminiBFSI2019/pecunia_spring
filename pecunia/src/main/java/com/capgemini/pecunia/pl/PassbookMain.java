package com.capgemini.pecunia.pl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.capgemini.pecunia.dto.Transaction;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.service.PassbookMaintenanceService;
import com.capgemini.pecunia.service.PassbookMaintenanceServiceImpl;

class PassbookMain{
public static void main(String[] args) throws PecuniaException, PassbookException  {
    //updatePassbook(); 	
	accountSummary();
	}

	public static void updatePassbook()
	{
		PassbookMaintenanceService PassbookService = new PassbookMaintenanceServiceImpl();
		List<Transaction> updatePassbook = new ArrayList<Transaction>();
		Scanner obj = new Scanner(System.in);
		System.out.println("Enter accountId:");
		String accountId = obj.nextLine();
		try {
			updatePassbook = PassbookService.updatePassbook(accountId);

			if (updatePassbook.size() < 1) {
				System.out.println("no trans");
			}

			else {
				for (int index = 0; index < updatePassbook.size(); index++) {
					System.out.print(updatePassbook.get(index).getId() + "\t");
					System.out.print(updatePassbook.get(index).getTransDate() + "\t");
					System.out.print(updatePassbook.get(index).getAmount() + "\t");
					System.out.print(updatePassbook.get(index).getTransFrom() + "\t");
					System.out.print(updatePassbook.get(index).getTransTo() + "\t");
					System.out.print(updatePassbook.get(index).getType() + "\t");
					System.out.print(updatePassbook.get(index).getOption() + "\t");
					if (updatePassbook.get(index).getOption().equalsIgnoreCase("cheque")) {
						System.out.print(updatePassbook.get(index).getChequeId() + "\t");

					} else {
						System.out.print("-\t");
					}
					System.out.print(updatePassbook.get(index).getClosingBalance() + "\t");
					System.out.println();
				}
			}
		} catch (PecuniaException | PassbookException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void accountSummary()
	{
		Scanner obj = new Scanner(System.in);
		
		PassbookMaintenanceService accountSummaryService = new PassbookMaintenanceServiceImpl();

		System.out.println("Enter accountId:");
		String accountId = obj.nextLine();

		System.out.println("Enter start date:");
		String sdate1 = obj.nextLine();

		System.out.println("Enter end date:");
		String sdate2 = obj.nextLine();

	LocalDate startDate = LocalDate.parse(sdate1);
	LocalDate endDate = LocalDate.parse(sdate2);

		List<Transaction> accountSummary = new ArrayList<Transaction>();
		try {
			accountSummary = accountSummaryService.accountSummary(accountId, startDate, endDate);
				for (int index = 0; index < accountSummary.size(); index++) {
					System.out.print(accountSummary.get(index).getId() + "\t");
					System.out.print(accountSummary.get(index).getTransDate() + "\t");
					System.out.print(accountSummary.get(index).getAmount() + "\t");
					System.out.print(accountSummary.get(index).getTransFrom() + "\t");
					System.out.print(accountSummary.get(index).getTransTo() + "\t");
					System.out.print(accountSummary.get(index).getType() + "\t");
					System.out.print(accountSummary.get(index).getOption() + "\t");
					if (accountSummary.get(index).getOption().equalsIgnoreCase("cheque")) {
						System.out.print(accountSummary.get(index).getChequeId() + "\t");

					} else {
						System.out.print("-\t");
					}
					System.out.print(accountSummary.get(index).getChequeId() + "\t");
					System.out.print(accountSummary.get(index).getClosingBalance() + "\t");
					System.out.println();
				}
			//}
		} catch (PecuniaException | PassbookException e) {
			System.out.println(e.getMessage());
		}

		
	}
}
	