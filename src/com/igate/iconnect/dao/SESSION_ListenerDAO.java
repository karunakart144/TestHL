package com.igate.iconnect.dao;

public interface SESSION_ListenerDAO {
	
	public int insertLoggedOutDetails(String loginId,String status,String loggedOutBy,boolean isTimeOut,String sessionId);

}
