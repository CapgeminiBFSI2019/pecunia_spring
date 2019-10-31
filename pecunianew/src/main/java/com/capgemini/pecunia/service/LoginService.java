package com.capgemini.pecunia.service;


import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;

public interface LoginService {

	public boolean validateEmail(Login log) throws PecuniaException,LoginException;


}
