package com.igate.iconnect.daoimpl;

import java.util.Date;

import javax.sql.DataSource;



import org.springframework.jdbc.core.JdbcTemplate;

import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.LoginSQLQueryConstants;
import com.igate.iconnect.dao.SESSION_ListenerDAO;

public class SESSION_ListenerDAOImpl implements SESSION_ListenerDAO{

	private JdbcTemplate jdbcTemplate;


	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	public int insertLoggedOutDetails(String loginId,String status,String loggedOutBy,boolean isTimeOut,String sessionId) {
		
		int result1=0;	
		int countOfRoles=this.jdbcTemplate.queryForInt(LoginSQLQueryConstants.IC_USER_ROLE_COUNT,loginId);
		if(countOfRoles>1){
			String logOutDate=CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS.format(new Date());	
			result1=this.jdbcTemplate.queryForInt(LoginSQLQueryConstants.IC_USER_LOG_OUT_DETAILS,loginId,logOutDate);
		if(result1==0){
			result1=this.jdbcTemplate.update(LoginSQLQueryConstants.IC_INSERT_USER_AVAILABILITY_OUT,
				loginId,CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS.format(new Date()),status,loggedOutBy,sessionId);
		}
		}
		return result1;
	}





}
