package com.capgemini.pecunia.inputvalidator;

public class TransactionInputValidator {

	public boolean validateAccId(String accId) {
		boolean isValid = false;
		if (accId != null && !accId.isEmpty() && accId.length() == 12) {
			if (accId.matches("[0-9]{14}")) {
				isValid = true;
			}
		}
		return isValid;
	}

	public boolean transactionAmountisValid(double amount) {
		boolean isValid = false;
		if (amount > 0) {
			isValid = true;
		}
		return isValid;
	}

	public boolean chequeNumberisValid(int chequeNum) {
		boolean isValid = false;
		if (Integer.toString(chequeNum).length() == 6) {
			if (Integer.toString(chequeNum).matches("[0-9]{6}")) {
				isValid = true;
			}
		}
		return isValid;
	}

	public boolean validateAccountName(String accountName) {
		boolean isValid = false;
		String[] Nametmp = accountName.split("\\s+");
		if (accountName != null && !accountName.isEmpty()) {
			if (Nametmp[0].matches("[A-Za-z]+") && Nametmp[1].matches("[A-Za-z]+")) {
				isValid = true;
			}
		}
		return isValid;
	}

}
