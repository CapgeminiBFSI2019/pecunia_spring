package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.exception.PassbookException;



class PassbookMaintenanceServiceImplTest {
PassbookMaintenanceServiceImpl pbm;
	
	@BeforeEach
	void setUp() throws Exception {
		pbm = new PassbookMaintenanceServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		pbm=null;
	}

	@Test
	@DisplayName("Input is null")
	void testPassbookMaintenanceNull() {
		assertThrows(PassbookException.class, ()-> {pbm.updatePassbook(null);});
	}
	
	@Test
	@DisplayName("Input is empty")
	void testPassbookMaintenanceEmpty() {
		assertThrows(PassbookException.class, ()-> {pbm.updatePassbook("");});
	}
	
	@Test
	@DisplayName("Input is not an integer")
	void testPassbookMaintenanceSpecialChar() {
		assertThrows(PassbookException.class, ()-> {pbm.updatePassbook("ab123567890^AVN");});
	}
	
	@Test
	@DisplayName("All inputs are null")
	
	void testAccountSummaryAllNull() {
		assertThrows(PassbookException.class, ()-> {pbm.accountSummary(null, null, null);});
	}
	
	@Test
	@DisplayName("Account ID is empty")
	void testAccountSummaryEmpty() throws ParseException {
		String sDate1="2018-10-10";
		String sDate2="2019-10-10";
		LocalDate date1=LocalDate.parse(sDate1);
		LocalDate date2=LocalDate.parse(sDate2);
		assertThrows(PassbookException.class, ()-> {pbm.accountSummary("",date1 ,date2 );});
	}
	
	@Test
	@DisplayName("Account ID is null")
	void testAccountSummaryNull() throws ParseException {
		String sDate1="2018-10-10";
		String sDate2="2019-10-10";
		LocalDate date1=LocalDate.parse(sDate1);
		LocalDate date2=LocalDate.parse(sDate2);
		assertThrows(PassbookException.class, ()-> {pbm.accountSummary(null,date1 ,date2 );});
	}
	
	@Test
	@DisplayName("Input for Account ID is invalid")
	void testAccountSummarySpecialChar() throws ParseException {
		String sDate1="2018-10-10";
		String sDate2="2019-10-10";
		LocalDate date1=LocalDate.parse(sDate1);
		LocalDate date2=LocalDate.parse(sDate2);
		assertThrows(PassbookException.class, ()-> {pbm.accountSummary("1as*&Ak1234",date1 ,date2 );});
	}
	

	
	
	
}