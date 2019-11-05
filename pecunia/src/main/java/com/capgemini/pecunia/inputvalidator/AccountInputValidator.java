package com.capgemini.pecunia.inputvalidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.capgemini.pecunia.util.Constants;

public class AccountInputValidator {
	public static boolean checkIfDigit(String value) {

		boolean ifDigit = true;
		for (int check = 0; check < value.length(); check++) {
			if (!Character.isDigit(value.charAt(check))) {
				ifDigit = false;
				break;
			}
		}
		return ifDigit;
	}

	public static boolean checkIfAlphaNumeric(String value) {
		int digit = 0;
		int alpha = 0;
		boolean isAlphaNumeric = false;
		for (int check = 0; check < value.length(); check++) {
			if (Character.isDigit(value.charAt(check)))
				digit++;
			else
				alpha++;
		}
		if (digit != 0 && alpha != 0)
			isAlphaNumeric = true;
		if (Pattern.matches(".*[!@#$%&*()_+=|<>?{}.\\[\\]~-]+.*", value)) {
			isAlphaNumeric = false;
		}
		return isAlphaNumeric;
	}

	public static boolean checkIfSpecialCharacter(String value) {
		return Pattern.matches(".*[!@#$%&*()_+=|<>?{}.\\[\\]~-]+.*", value);
	}

	public static boolean checkLength(int len, String value) {
		boolean length = false;
		if (value.length() == len)
			length = true;
		return length;
	}

	public static boolean dateValidator(String inputDate) {
		DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_1);
		boolean isValidDate = false;
		sdf.setLenient(false);
		try {
			sdf.parse(inputDate);
			isValidDate = true;
		} catch (ParseException e) {
			isValidDate = false;
		}
		return isValidDate;
	}

}
