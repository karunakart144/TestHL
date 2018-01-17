/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.constants;

public class COMMON_AuditLogSQLQueryConstants {

	/**
	 * Description: This constant is used to select the ticket histroy of the
	 * specified ticket.
	 */
//L2:4836 AuditLog display query has been modified to display the created date inplace of changed date display
	public static final String SELECT_TICKET_HISTORY = "SELECT AUDIT_ID,PREVIOUS_STATE,CURRENT_STATE,IU.NAME + '(' + CHANGED_BY+')' as 'CHANGED_BY',DATEADD(minute,DATEDIFF (minute, GETDATE(), GETUTCDATE()),IAD.CREATED_DATE) as 'CREATED_DATE',ACTION," +
			"  RESPONSE_TIME AS RESPONSE_TIME "
			+ " FROM IC_AUDIT_DETAILS IAD,IC_USER_DETAILS IU where REFERENCE_ID=? AND  MENU_ID=? AND IAD.CHANGED_BY=IU.EMPLOYEE_ID order by AUDIT_ID ASC";

	

	/**
	 * Description: This constant is used to select the old & new value of the
	 * fields per ticket.
	 */

	public static final String SELECT_TICKET_HISTORY_DETAIL = "SELECT DATA_CHANGE FROM IC_AUDIT_DETAILS where AUDIT_ID=?";

}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 10,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

