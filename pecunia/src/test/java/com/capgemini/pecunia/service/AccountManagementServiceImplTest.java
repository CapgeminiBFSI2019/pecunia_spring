package com.capgemini.pecunia.service;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.capgemini.pecunia.dto.Account;
import com.capgemini.pecunia.dto.Address;
import com.capgemini.pecunia.dto.Customer;
import com.capgemini.pecunia.exception.AccountException;

import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.util.Constants;


class AccountManagementServiceImplTest {

	AccountManagementServiceImpl ams;

	@BeforeEach
	void setUp() throws Exception {
		ams = new AccountManagementServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		ams = null;
	}

	@Test
	@DisplayName("Null input parameter for delete account method")
	void testDeleteAccount() {

		assertThrows(AccountException.class, () -> {
			ams.deleteAccount(null);
		});

	}

	@Test
	@DisplayName("Account successfully deleted")
	void testDeleteAccountPass() throws PecuniaException, AccountException {

		Account account = new Account();
		account.setId("100101000001");

		assertTrue(ams.deleteAccount(account));

	}

	@Test
	@DisplayName("Null inputs for update customer name")
	void testUpdateCustomerNameNull() {
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerName(null, null);
		});
	}

	@Test
	@DisplayName("Valid inputs. Customer name updated")
	void testUpdateCustomerNamePass() throws PecuniaException, AccountException {
		
		Account account = new Account();
		Customer customer=new Customer();
		
		account.setId("100101000001");
		customer.setName("VizRazdan");
		
		assertTrue(ams.updateCustomerName(account, customer));
	}

	@Test
	@DisplayName("Null inputs for update customer contact")
	void testUpdateCustomerContactNull() {
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerContact(null, null);
		});
	}

	@Test
	@DisplayName("Valid inputs. Customer contact updated")
	void testUpdateCustomerContactPass() throws PecuniaException, AccountException {
		
		Account account = new Account();
		Customer customer=new Customer();
		
		account.setId("100101000001");
		customer.setContact("8763349049");
		
		assertTrue(ams.updateCustomerContact(account, customer));
	}
	

	@Test
	@DisplayName("Null inputs for update customer Address")
	void testUpdateCustomerAddressNull() {
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerAddress(null, null);
		});

	}

	@Test
	@DisplayName("Valid inputs. Customer address updated")
	void testUpdateCustomerAddressPass() throws PecuniaException, AccountException {

		Account account = new Account();
		Address address = new Address();

		account.setId("100101000001");
		address.setLine1("My house is near ATP");
		address.setLine2("But far away from my capg");
		address.setCity("BLR");
		address.setCountry("India");
		address.setZipcode("500076");
		address.setState("Krnt");
		assertTrue(ams.updateCustomerAddress(account, address));

	}

	@Test
	void testCalculateAccountId() {

		assertThrows(AccountException.class, () -> {
			ams.calculateAccountId(null);
		});


	}

	@Test
	@DisplayName("Null inputs in add account function")
	void testAddAccountNull() {

		assertThrows(AccountException.class, () -> {
			ams.addAccount(null, null, null);
		});

	}

	@Test
	@DisplayName("Inputs in add account function")
	void testAddAccountPass() throws PecuniaException, AccountException {

		Account account = new Account();
		Customer customer = new Customer();
		Address address = new Address();
		address.setLine1("12-Vydehi");
		address.setLine2("ATP");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setZipcode("400036");

		customer.setName("Avizzek");
		customer.setAadhar("898424476569");
		customer.setPan("AF590J986F");
		customer.setContact("6832404674");
		customer.setGender("M");
		LocalDate dob = LocalDate.parse("1995-10-17");
		customer.setDob(dob);

		account.setAccountType(Constants.FD);
		account.setBalance(9000000.00);
		account.setBranchId("1002");
		account.setInterest(0.00);
		account.setStatus(Constants.ACCOUNT_STATUS[0]);

		assertNotNull(ams.addAccount(customer, address, account));

	}

	@Test
	@DisplayName("Validation successful")
	void testValidationPass() throws PecuniaException, AccountException {

		Account account = new Account();
		account.setId("100101000001");
		assertTrue(ams.validateAccountId(account));

	}

}
