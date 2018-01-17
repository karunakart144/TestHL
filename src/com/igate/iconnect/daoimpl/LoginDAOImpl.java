/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.LoginSQLQueryConstants;
import com.igate.iconnect.dao.LoginDAO;
@Transactional(rollbackFor = Exception.class)
public class LoginDAOImpl implements LoginDAO {
	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplateForCommonDB;//SSO
	private static final String EMPLOYEE_ID="EMPLOYEE_ID";
	private static final String EMP_LOGINMODE="EMP_LOGINMODE";
	private static final String EMP_NAME="EMP_NAME";
	private static final String EMP_EMAIL="EMP_EMAIL";
	private static final String EMP_GRADE="EMP_GRADE";
	private static final String EMP_AREA="EMP_AREA";
	private static final String EMP_CITY="EMP_CITY";
	private static final String EMP_SHORT_LOCATION_NAME="EMP_SHORT_LOCATION_NAME";
	private static final String EMP_RO_EMPID="EMP_RO_EMPID";
	private static final String EMP_RO_NAME="EMP_RO_NAME";
	private static final String EMP_ORG="EMP_ORG";
	private static final String EMP_ROLEID="EMP_ROLEID";
	private static final String EMP_ROLE_NAME="EMP_ROLE_NAME";
	private static final String TIMEZONE_ID="TIMEZONE_ID";
	private static final String APPROVAL_EXCEPTION="APPROVAL_EXCEPTION";
	//SSO
/*	public LoginDAOImpl(@Qualifier("commonDBDataSource") DataSource dataSource) {
	        this.jdbcTemplateForCommonDB = new JdbcTemplate(dataSource);
	    }*/
	
	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	public List<User> isExistingUser(String loginId) {
		String query = LoginSQLQueryConstants.IC_USER_DETAILS;
		try {
			return this.jdbcTemplate.query(query, new Object[] { loginId },
					new UserMapper());
		} catch (EmptyResultDataAccessException emptyException) {
			return null;
		}
	}

	public List<User> getUserDetails(String loginId) {
		String query = LoginSQLQueryConstants.IC_USER_DETAILS;
		return this.jdbcTemplate.query(query, new Object[] { loginId },
				new UserMapper());

	}

	private static class UserMapper implements ParameterizedRowMapper<User> {
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setLoginId(rs.getString(EMPLOYEE_ID));
			user.setAuthenticationRequired(rs.getBoolean(EMP_LOGINMODE));
			user.setUserName(rs.getString(EMP_NAME));
			user.setEmailAddress(rs.getString(EMP_EMAIL));
			user.setGrade(EMP_GRADE);
			user.setLocationArea(rs.getString(EMP_AREA));
			user.setLocationCity(rs.getString(EMP_CITY));
			user.setLocationShortName(rs.getString(EMP_SHORT_LOCATION_NAME));
			user.setReportingMangerId(rs.getString(EMP_RO_EMPID));
			user.setReportingManagerName(rs.getString(EMP_RO_NAME));
			user.setOrganization(rs.getString(EMP_ORG));
			user.setUserRoleId(rs.getString(EMP_ROLEID));
			user.setUserRole(rs.getString(EMP_ROLE_NAME));
			user.setTimeZoneId(rs.getString(TIMEZONE_ID));
			user.setApprovalExceptionFlag(rs.getString(APPROVAL_EXCEPTION));
			return user;
		}

	}

	public List<Map<String, Object>> getUserRoleDetails(String loginId) {
		String query = LoginSQLQueryConstants.IC_USER_ROLE_DETAILS;
		return this.jdbcTemplate.queryForList(query, loginId);
	}

/*	public int insertLoggedinDetails(String loginId,String status,String loggedOutBy,String sessionId) {
		int count = this.jdbcTemplate.queryForInt(LoginSQLQueryConstants.IS_SESSION_PRESENT, loginId,sessionId);
		int result=0;
		if(count<=0){
			result= this.jdbcTemplate.update(LoginSQLQueryConstants.IC_INSERT_USER_AVAILABILITY_IN,
				loginId,CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS.format(new Date()),status,sessionId);
		}else{
			result=0;
		}
		return result;
	}*/
	
	public int insertLoggedinDetails(String loginId,String status,String loggedOutBy,String sessionId) {
		   int count = this.jdbcTemplate.queryForInt(LoginSQLQueryConstants.IS_SESSION_PRESENT, loginId,sessionId);
		   
		   //To Check the Max Status of an Employe d=for 
		   String maxstatus=null;
		   String status_out="IN";
		   int result=0;

		   try{
			   maxstatus= this.jdbcTemplate.queryForObject(LoginSQLQueryConstants.EMP_MAX_STATUS_FOR_SESSION, new Object[] { loginId,sessionId,loginId },
	                              String.class);
		   }catch(EmptyResultDataAccessException erdae){
			   maxstatus=null;
		   }	     
	        if(maxstatus !=null){
	            status_out=maxstatus;
	        }
	        
	        if(count<=0 || status_out.equalsIgnoreCase("OUT")){
	              result= this.jdbcTemplate.update(LoginSQLQueryConstants.IC_INSERT_USER_AVAILABILITY_IN,
	                    loginId,CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS.format(new Date()),status,sessionId);
	        }else{
	              result=0;
	        }
	        
	        return result;

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
	//SSO
	public String getEmpIdFrmiSpace(String GUId) {
		String query = LoginSQLQueryConstants.CDB_SINGLE_SIGNON;
		return String.valueOf(this.jdbcTemplateForCommonDB.queryForObject(query,String.class,
				 new Object[]{GUId}));
	}
		
	public void deleteGUIDiSpace(String GUId) {
	        // TODO Auto-generated method stub
	        String query = LoginSQLQueryConstants.DELETE_CDB_SINGLE_SIGNON;
	        this.jdbcTemplateForCommonDB.update(query,
	                GUId);
	}
	
	//Added for Unified Self Service
	public List<User> isExistingUserForUSS(String loginId) {
		String query = LoginSQLQueryConstants.IC_USER_DETAILS_USS;
		try {
			return this.jdbcTemplate.query(query, new Object[] { loginId },
					new UserMapper());
		} catch (EmptyResultDataAccessException emptyException) {
			return null;
		}
	}
	
	
	
	public String getGUIDForHelpdeskCallLog(String empId){
		Map<String,Object> guIdMap=this.jdbcTemplateForCommonDB.queryForMap(LoginSQLQueryConstants.CDB_SINGLE_SIGNON_FOR_HELPDESK_CALL_LOGS, empId);
		String l_guId="";
		if(guIdMap.size()>0 && null!=guIdMap.get("GU_ID")){
			l_guId = guIdMap.get("GU_ID").toString();
		}
		return l_guId;
	}
	
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 15, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
