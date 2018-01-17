/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.constants;

public class HELPDESK_SLASQLQueryConstants {
	
	public static final String IC_AUDITLOG ="SELECT a.PREVIOUS_STATE,a.CURRENT_STATE,a.RESPONSE_TIME,a.CHANGED_DATE FROM IC_AUDIT_DETAILS a"
		+ " WHERE a.REFERENCE_ID=? AND a.MENU_ID=? and a.audit_id >"
		+ "(select max(b.AUDIT_ID) from IC_AUDIT_DETAILS b where  b.REFERENCE_ID=? AND b.MENU_ID=? AND b.IS_FUNCTION_CHANGE=1)";

	public static final String IC_IHD_TICKET_DETAILS = "SELECT CREATED_DATE,ECT FROM IC_IHD_TICKET_DETAILS"
			+ " WHERE TICKET_ID=?";
	public static final String IC_IHD_SLA_EXCLUSION_DATES = "SELECT DATE FROM IC_IHD_SLA_EXCLUSION_DATES WHERE SLA_ID=?"
			+ " AND LOCATION_ID=? AND IS_ACTIVE=1 ORDER BY DATE";
	public static final String IC_IHD_SLA_OPERATING_TIME = "SELECT DAY,START_TIME,END_TIME FROM IC_IHD_SLA_OPERATING_TIME_DETAILS WHERE SLA_ID=?";
	public static final String IC_IHD_AUDIT_LAST_RESPONSETIME = "SELECT RESPONSE_TIME,CHANGED_DATE FROM IC_AUDIT_DETAILS"
			+ " WHERE REFERENCE_ID=? AND AUDIT_ID =(SELECT MAX(AUDIT_ID) FROM IC_AUDIT_DETAILS WHERE REFERENCE_ID=?)";
	// ECT

	/*
	 * public static final String IC_IHD_SLA_OPERTING_TIME_DETAILS=
	 * "SELECT b.SLA_ID,b.TIME,c.DAY,c.START_TIME,c.END_TIME,c.NEXT_WORKING_DAY,c.IS_ACTIVE"
	 * +
	 * " FROM  IC_IHD_CATEGORY_PRIORITY_DETAILS a,IC_IHD_SLA_MASTER b,IC_IHD_SLA_OPERATING_TIME_DETAILS c"
	 * + " WHERE a.CATEGORY_ID=? AND  a.PRIORITY_ID =?" +
	 * " AND  b.SLA_ID=a.SLA_ID AND b.ORGANIZATION in(?,'C')  and c.SLA_ID=b.SLA_ID and c.DAY like ?"
	 * ;
	 */
	public static final String IC_IHD_SLA_OPERTING_TIME_DETAILS = "SELECT b.SLA_ID,c.DAY,c.START_TIME,c.END_TIME,c.NEXT_WORKING_DAY,c.IS_ACTIVE"
			+ " FROM IC_IHD_SLA_MASTER b,IC_IHD_SLA_OPERATING_TIME_DETAILS c"
			+ " WHERE c.SLA_ID=? AND b.ORGANIZATION in(?,'C')  and c.SLA_ID=b.SLA_ID and c.DAY like ? and c.IS_ACTIVE=1";
	public static String IC_IHD_CATEGORY_PRIORITY_DETAILS = "SELECT SLA_ID FROM IC_IHD_CATEGORY_PRIORITY_DETAILS WHERE CATEGORY_ID=? AND PRIORITY_ID=?";
	public static String IC_IHD_SERVICE_WINDOW_DETAILS = "SELECT operation_id FROM IC_IHD_SERVICE_WINDOW_DETAILS WHERE LOCATION_ID=? and LOC_DET_ID=? and FUNCTION_ID=?";
	public static String IC_IHD_GROUP_FUNCTION_MAPPING = "SELECT SLA_ID FROM IC_IHD_GROUP_FUNCTION_MAPPING WHERE GROUP_ID=? and CATEGORY_ID=? and LOCATION_ID=?";
	public static String IC_IHD_TICKET_DETAILS_INFO = "select location_id,loc_det_id,function_id,sub_category_id,PRIORITY_ID,CREATED_DATE" +
			" from IC_IHD_TICKET_DETAILS where TICKET_ID=?";
	public static final String IC_AUDITLOG_FUNCTION_CHANGE_DATE ="SELECT a.CHANGED_DATE FROM IC_AUDIT_DETAILS a"
        + " WHERE a.REFERENCE_ID=? AND a.MENU_ID=? and a.audit_id ="
        + "(select max(b.AUDIT_ID) from IC_AUDIT_DETAILS b where  b.REFERENCE_ID=? AND b.MENU_ID=? AND b.IS_FUNCTION_CHANGE=1)";

	
	
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 10, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
