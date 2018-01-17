/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */


package com.igate.iconnect.dao;

import java.io.Serializable;
import java.util.List;

import com.igate.iconnect.BO.User;

/**
 * authenticate() method will authenticate loginId and password with LDAP server data
 * Arguements passed : loginId,passWord
 * Return type : boolean
 * 				For authenticated user,retun type is true
 * 				For un authenticated user,retun type is false
 */
public interface LoginAuthenticationDAO  extends Serializable{
	public boolean authenticate(String loginId, String passWord);

	public String getsamaccountname(String loginId);



	public String IsUserExist(User userbean);

	public List<String> MobileAndExtn(String userBeanObj);

	public String getsamaccountnameforOrchestration(String loginID);
}


 

/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:702166

Changes Made on:Jun 15, 2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/
