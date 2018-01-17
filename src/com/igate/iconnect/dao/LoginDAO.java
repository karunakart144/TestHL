/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */


package com.igate.iconnect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igate.iconnect.BO.User;

 


public interface LoginDAO{

	/**
	 * isExistingUser() method will check user details are avalible in DB for a particular loginId
	 * Arguements passed : loginId
	 * @param User  contains user details from DB for a particular loginId
	 */
	public List<User> isExistingUser(String loginId);
	/**
	 * getUserDetails() method will return user details  for a particular loginId
	 * Arguements passed : loginId
	 * @param User  contains user details from DB for a particular loginId
	 */
	public List<User> getUserDetails(String loginId);
	/**
	 * getUserRoleDetails() method will return List which contains all the roles mapped to a loginId
	 * Arguements passed : loginId
	 */
	public List<Map<String, Object>> getUserRoleDetails(String loginId);
	
	public int insertLoggedinDetails(String loginId,String status,String loggedOutBy,String sessionId);
	
	public int insertLoggedOutDetails(String loginId,String status,String loggedOutBy,boolean isTimeOut,String sessionId);
	//public int deleteLoggedinDetails(String loginId,String loggedOutBy);
	public String getEmpIdFrmiSpace(String GUId);
	public List<User> isExistingUserForUSS(String loginId);
	
	public void deleteGUIDiSpace(String GUId);
	
	
	public String getGUIDForHelpdeskCallLog(String empId);
	
}


 

/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:702166

Changes Made on:Jun 15, 2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/
