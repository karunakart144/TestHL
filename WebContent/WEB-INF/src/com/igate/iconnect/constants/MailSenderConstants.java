package com.igate.iconnect.constants;

public class MailSenderConstants {
	
	public static final String IC_SAME_STATUS_MAIL_TRIGGERING_QUERY="select TEMP.*,isnull(A.TICKET_COUNT,0) TICKET_COUNT from  " +
"(select td.TICKET_ID,func.NAME TICKET_FUNCTION, " + 
"cat.NAME CATEGORY, " + 
"subcat.NAME SUB_CATEGORY, " +
"pm.DESCRIPTION PRIORITY, " + 
"ud.NAME+'('+td.CREATED_BY+')' CREATED_BY, " + 
"ud.NAME+'('+td.REQUESTED_BY+')' REQUESTED_BY, " + 
"proj.NAME +'('+td.PROJECT_ID+')' PROJECT, " + 
"td.CREATED_DATE CREATED_DATE, " + 
"td.SUBJECT SUBJECT, " + 
"td.DESCRIPTION DESCRIPTION, " +
"gm.NAME ASSIGNED_GROUP, " + 
"ud1.NAME+'('+td.ASSIGNED_TO+')' ASSIGNED_TO, " + 
"wsm.NAME STATE, " + 
"td.CLOSED_DATE CLOSED_DATE, " + 
"td.SLA_STATUS SLA_STATUS, " + 
"td.ADDITIONAL_INFO ADDITIONAL_INFO, " + 
"td.RESOLUTION_COMMENTS RESOLUTION_COMMENTS, " + 
"isnull(ud1.EMAIL_ADDRESS,'') TO_ADDRESS, " + 
"case when exists (select 1 from IC_IHD_TICKET_DETAILS where CREATED_BY = TD.ON_BEHALF_OF and TICKET_ID = td.TICKET_ID)then " + 
"isnull(ud.EMAIL_ADDRESS,'') ELSE  " + 
"ISNULL(STUFF((SELECT ',' + RD4.EMAIL_ADDRESS FROM IC_USER_DETAILS RD4 " + 
"where RD4.EMPLOYEE_ID = td.ON_BEHALF_OF " + 
"FOR XML PATH('')),1,1,''),'') " +       
"+','+UD.EMAIL_ADDRESS END AS CC_ADDRESS, " + 
"wsm.NAME 'STATUS' " + 
"from IC_IHD_TICKET_DETAILS td left outer join IC_IHD_GROUP_MASTER gm on td.ASSIGNED_GROUP=gm.GROUP_ID " + 
"left outer join IC_WORKFLOW_STATE_MASTER wsm on td.WORKFLOW_STATE=wsm.STATE_ID " + 
"left outer join IC_USER_DETAILS ud on td.CREATED_BY=ud.EMPLOYEE_ID " + 
"left outer join IC_USER_DETAILS ud1 on td.ASSIGNED_TO = ud1.EMPLOYEE_ID " + 
"left outer join IC_IHD_CATEGORY_MASTER func on td.FUNCTION_ID=func.CATEGORY_ID " + 
"left outer join IC_IHD_CATEGORY_MASTER cat on td.CATEGORY_ID=cat.CATEGORY_ID " + 
"left outer join IC_IHD_CATEGORY_MASTER subcat on td.SUB_CATEGORY_ID=subcat.CATEGORY_ID " + 
"left outer join IC_PROJECT_MASTER proj on td.PROJECT_ID=proj.PROJECT_ID " + 
"left outer join IC_PRIORITY_MASTER pm on td.PRIORITY_ID=pm.PRIORITY_ID " + 
"where td.TICKET_ID = ? ) TEMP " + 
"left outer join   (SELECT TICKET_ID,COUNT(TICKET_ID) AS TICKET_COUNT " + 
"FROM IC_REMINDER_MAIL_TRACKER Where MAIL_TRIGGER_FLAG like 'SameStatusMail%' " +
"group by TICKET_ID) A on A.TICKET_ID = TEMP.TICKET_ID";



public static final String query_to_update_remainder_tracker = "insert into IC_REMINDER_MAIL_TRACKER (TICKET_ID,MAIL_TRIGGER_FLAG,MAIL_TRIGGER_TIME,CREATED_BY,CREATED_DATE) values (?,?,GETDATE(),'SYSTEM',GETDATE())";

}