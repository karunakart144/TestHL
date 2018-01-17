/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

public class LoginSQLQueryConstants {
    public static final String IC_USER_DETAILS = "SELECT   loggedInUser.EMPLOYEE_ID as 'EMPLOYEE_ID', "
    		+ " loggedInUser.NAME as 'EMP_NAME', loggedInUser.EMAIL_ADDRESS as 'EMP_EMAIL',   "
+ " loggedInUser.GRADE as 'EMP_GRADE',  "
+ " loc.CITY as 'EMP_CITY', loc.AREA as 'EMP_AREA', " 
+ " loc.SHORT_NAME as 'EMP_SHORT_LOCATION_NAME', "  
+ " loggedInUserManager.EMPLOYEE_ID as 'EMP_RO_EMPID', "  
+ " loggedInUserManager.NAME as 'EMP_RO_NAME', "  
+ " loggedInUser.ORGANIZATION as 'EMP_ORG', "  
+ " roleMapping.ROLE_ID as 'EMP_ROLEID',  roleMaster.NAME as 'EMP_ROLE_NAME', " 
+ " loggedInUser.LOGIN_MODE as 'EMP_LOGINMODE', " 
+ " loggedInUser.USER_TIMEZONE as 'TIMEZONE_ID', "  
+ " gradeMaster.IS_APPROVAL_REQUIRED as 'APPROVAL_EXCEPTION' "      
+ " FROM    IC_USER_DETAILS loggedInUser "             
+ " join IC_LOCATION_MASTER loc on loc.LOCATION_ID=loggedInUser.LOCATION_ID "              
+ " left outer join IC_USER_DETAILS loggedInUserManager on loggedInUserManager.EMPLOYEE_ID=loggedInUser.REPORTING_MANAGER " 
+ " join IC_USER_ROLE_DETAILS roleMapping   on loggedInUser.EMPLOYEE_ID = roleMapping.EMPLOYEE_ID " 
+ " join IC_ROLE_MASTER roleMaster on roleMapping.ROLE_ID = roleMaster.ROLE_ID "              
+ " join IC_GRADE_MASTER gradeMaster on gradeMaster.GRADE=loggedInUser.GRADE AND gradeMaster.IS_ACTIVE=1 "   
+ " WHERE loggedInUser.IS_ACTIVE = 1 AND roleMapping.IS_ACTIVE = 1 AND loggedInUser.EMPLOYEE_ID = ?";
    
    
    
    public static final String IC_USER_ROLE_DETAILS = "SELECT B.NAME FROM IC_USER_ROLE_DETAILS A,IC_ROLE_MASTER B"
            + " WHERE B.ROLE_ID =A.ROLE_ID AND A.EMPLOYEE_ID = ?";

    public static final String INVALID = "!@#$%\"'^&*()~,'<>/?;:|[]{}+=`*";

    public static final String IC_INSERT_USER_AVAILABILITY_IN="INSERT INTO IC_USER_AVAILABILITY_IN_OUT(EMPLOYEE_ID,DATE_TIME,STATUS,SESSION_ID) VALUES(?,?,?,?)";
    
    public static final String IC_INSERT_USER_AVAILABILITY_OUT="INSERT INTO IC_USER_AVAILABILITY_IN_OUT(EMPLOYEE_ID,DATE_TIME,STATUS,LOGGED_OUT_BY,SESSION_ID) VALUES(?,?,?,?,?)";
    
    public static final String IC_USER_LOG_OUT_DETAILS="SELECT COUNT(DATE_TIME) FROM IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? and STATUS='OUT' and DATE_TIME=?";
    
    public static final String IC_USER_ROLE_COUNT="SELECT COUNT(ROLE_ID) FROM IC_USER_ROLE_DETAILS WHERE EMPLOYEE_ID=?";
    
    public static final String CDB_SINGLE_SIGNON="Select EMPLOYEE_ID from CDB_SINGLE_SIGNON where GU_ID=?";
    
    public static final String IS_SESSION_PRESENT="SELECT COUNT(SESSION_ID) FROM IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? and SESSION_ID=?";

	
    public static final String EMP_MAX_STATUS_FOR_SESSION="SELECT STATUS FROM IC_USER_AVAILABILITY_IN_OUT WHERE DATE_TIME IN (SELECT MAX(DATE_TIME) FROM IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? and SESSION_ID=?) AND EMPLOYEE_ID=?";
    
    //Added for Unified Self Service SSO
    
    public static final String IC_USER_DETAILS_USS = "SELECT   loggedInUser.EMPLOYEE_ID as 'EMPLOYEE_ID', "
        + " loggedInUser.NAME as 'EMP_NAME',"
        + " loggedInUser.EMAIL_ADDRESS as 'EMP_EMAIL', "
        + " loggedInUser.GRADE as 'EMP_GRADE',"
        + " loc.CITY as 'EMP_CITY',"
        + " loc.AREA as 'EMP_AREA',"
        + " loc.SHORT_NAME as 'EMP_SHORT_LOCATION_NAME', "
        + " loggedInUserManager.EMPLOYEE_ID as 'EMP_RO_EMPID', "
        + " loggedInUserManager.NAME as 'EMP_RO_NAME', "
        + " loggedInUserManager.ORGANIZATION as 'EMP_ORG', "
        + " roleMapping.ROLE_ID as 'EMP_ROLEID', "
        + " roleMaster.NAME as 'EMP_ROLE_NAME',"
        + " loggedInUser.LOGIN_MODE as 'EMP_LOGINMODE',"
        + " loggedInUser.USER_TIMEZONE as 'TIMEZONE_ID', "
        + " gradeMaster.IS_APPROVAL_REQUIRED as 'APPROVAL_EXCEPTION' "
        + "     FROM    IC_USER_DETAILS loggedInUser, "
        + "             IC_LOCATION_MASTER loc, "
        + "             IC_USER_DETAILS loggedInUserManager, "
        + "             IC_USER_ROLE_DETAILS roleMapping, "
        + "             IC_ROLE_MASTER roleMaster, "
        + "             IC_GRADE_MASTER gradeMaster "
        + "     WHERE   loc.LOCATION_ID=loggedInUser.LOCATION_ID "
        + "             AND loggedInUserManager.EMPLOYEE_ID=loggedInUser.REPORTING_MANAGER "
        + "             AND loggedInUser.EMPLOYEE_ID = roleMapping.EMPLOYEE_ID "
        + "             AND roleMapping.ROLE_ID = roleMaster.ROLE_ID "
        + "             AND loggedInUser.IS_ACTIVE = 1 AND roleMapping.IS_ACTIVE = 1"
        + "             AND gradeMaster.GRADE=loggedInUser.GRADE AND gradeMaster.IS_ACTIVE=1"
        + "             AND loggedInUser.EMPLOYEE_ID = ? order by roleMaster.NAME DESC ";
    
    public static final String DELETE_CDB_SINGLE_SIGNON="DELETE from CDB_SINGLE_SIGNON where GU_ID=?";
    
    
  //Added for workspace planning grade checking by 811322 on 10/06/14
    public static final String GET_USER_GRADE_QUERY="Select EMPLOYEE_ID,gradeMaster.GRADE,gradeMaster.GRADE_DESCRIPTION," +
    		" gradeMaster.GRADE_LEVEL" +
    		" from IC_USER_DETAILS a, IC_GRADE_MASTER gradeMaster" +
    		" WHERE a.GRADE=gradeMaster.GRADE" +
    		" and a.IS_ACTIVE='1' and gradeMaster.IS_ACTIVE='1'" +
    		" and a.EMPLOYEE_ID = ? " ;
    
    
    public static final String WORKSPACEPLANNING_ACCESS="Select COUNT(SBU_ID) from IC_SU_ORG_SBU_MASTER WHERE SBU_ID IN " +
    		"	(SELECT L6_CODE FROM IC_SU_SBU_DEPARTMENT_MAPPING WHERE DEPT_ID IN" +
    		"	(Select DU_ID from IC_USER_DETAILS WHERE EMPLOYEE_ID=? and IS_ACTIVE=1))";
    
    
    public static final String CDB_SINGLE_SIGNON_FOR_HELPDESK_CALL_LOGS="Select top 1 GU_ID,MAX(CREATED_DATE) CREATED_DATE,EMPLOYEE_ID from CDB_SINGLE_SIGNON where EMPLOYEE_ID=? group by GU_ID,EMPLOYEE_ID";
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 17, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
