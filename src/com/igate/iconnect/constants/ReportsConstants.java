package com.igate.iconnect.constants;

public class ReportsConstants {
	
	public static final String IC_ON_DEMAND_REPORT_QUERY =
			" DECLARE " 
			 + "@from_date DATETIME = ?, " 
			 + "@to_date DATETIME = ?, " 
			 + "@funcId SMALLINT = ?, " 
			 + "@catId SMALLINT = ?, " 
			 + "@statusId SMALLINT = ?, "
			 + "@onBasisOf SMALLINT = ?; " 
			 + "select distinct tick.TICKET_ID as 'Ticket ID' " 
			 + ",isnull (tick.USERS_IMPACTED,'') as 'No. of Users Impacted' " 
			 + ",isnull(tick.CREATED_BY,'')  as 'Created By EMPID' " 
			 + ",isnull(crby.NAME,'') as 'Created By Name' " 
			 + ",isnull(crloc.CITY,'') as 'Created By Location' " 
			 + ",isnull(tick.REQUESTED_BY,'') as 'Requested By EMPID' " 
			 + ",isnull(rqby.NAME,'') as 'Requested By Name' " 
			 + ",isnull(rqloc.CITY,'') as 'Requested By Location' " 
			 + ",isnull(crloc.CITY,'') as 'Ticket Location' " 
			 + ",isnull(proj.NAME+' ('+proj.PROJECT_ID+')','') as 'Project Name & ID' " 
			 + ",isnull(DU.NAME+'('+rqby.DU_ID+')','') as 'Department Name & ID' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CREATED_DATE),121),'') as 'Creation Time (EST)' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,ap1.CREATED_DATE),121),'') as 'L1 Approver Time (EST)' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,ap2.CREATED_DATE),121),'') as 'L2 Approver Time (EST)' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,ap3.CREATED_DATE),121),'') as 'DB Approver Time (EST)' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,ap4.CREATED_DATE),121),'') as 'Privilege Approver Time (EST)' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,ap5.CREATED_DATE),121),'') as 'Telecom Approver Time (EST)' " 
			 + ",isnull(tick.SOURCE,'') as 'Source' " 
			 + ",isnull(func.NAME,'') 'Function' " 
			 + ",isnull(prio.DESCRIPTION,'') 'Priority' " 
			 + ",isnull(cat.NAME,'') 'Category' " 
			 + ",isnull(subcat.NAME,'') 'Sub Category' " 
			 + ",isnull(tick.SUBJECT,'') as 'Subject' " 
			 + ",isnull(clby.EMPLOYEE_ID,'') as 'Assigned To' " 
			 + ",isnull(clby.NAME,'') as 'Assigned To Name' " 
			 + ",isnull(clbygp.NAME,'') as 'Assigned To Group' " 
			 + ",isnull(gpmgr.NAME+' ('+gpmgr.EMPLOYEE_ID+')','')  as 'Group Manager' " 
			 + ",isnull(CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CLOSED_DATE),121),'') as 'Closure Time (EST)' " 
			 + ",isnull(curstat.NAME,'') as 'Current State' " 
			 + ",isnull(dbo.fn_ReplaceSpecialChar(tick.RESOLUTION_COMMENTS),'') as 'Resolution Comments' " 
			 + ",isnull(tick.SLA_ACTIVE_TIME,'') as  'Active Response Time (In Mins)' " 
			 + ",isnull(tick.SLA_STATUS,'') as 'SLA Status' " 
			 + ",isnull(outsla.DESCRIPTION,'') as 'Out of SLA Reason' " 
			 + ",isnull(tick.OUT_OF_SLA_COMMENTS,'') as 'Out of SLA Comments' " 
			 + ",isnull(case when exists (select 1 from IC_AUDIT_DETAILS where REFERENCE_ID = CONVERT(varchar(20),tick.TICKET_ID) and CURRENT_STATE = 'Re-Open') " 
			 + "then 'Yes' else 'No' end,'') as 'Reopen' " 
			 + ",isnull(dbo.fn_ReplaceSpecialChar(tick.REOPEN_COMMENTS),'') as 'Reopen Comments' " 
			 + ",isnull(dbo.fn_ReplaceSpecialChar(tick.[DESCRIPTION]),'') as 'Description' " 
			 + "from IC_IHD_TICKET_DETAILS tick " 
			 + "left outer join IC_USER_DETAILS crby on crby.EMPLOYEE_ID = tick.CREATED_BY " 
			 + "left outer join IC_LOCATION_MASTER crloc on crloc.LOCATION_ID = tick.LOCATION_ID " 
			 + "left outer join IC_LOCATION_MASTER crbyloc on crbyloc.LOCATION_ID = crby.LOCATION_ID " 
			 + "left outer join IC_USER_DETAILS rqby " 
			 + "JOIN IC_DU_MASTER DU on DU.DU_ID = rqby.DU_ID " 
			 + "left outer join IC_LOCATION_MASTER rqloc on rqloc.LOCATION_ID = rqby.LOCATION_ID " 
			 + "on rqby.EMPLOYEE_ID = tick.REQUESTED_BY " 
			 + "left outer join IC_LOCATION_DETAILS locdet on tick.LOC_DET_ID = locdet.LOC_DET_ID " 
			 + "left outer join IC_PROJECT_MASTER proj on proj.PROJECT_ID = tick.PROJECT_ID " 
			 + "left outer join IC_IHD_TICKET_APPROVAL_DETAILS ap1 on tick.TICKET_ID = ap1.TICKET_ID and ap1.APPROVER_ID = 1 " 
			 + "left outer join IC_IHD_TICKET_APPROVAL_DETAILS ap2 on tick.TICKET_ID = ap2.TICKET_ID and ap2.APPROVER_ID = 2 " 
			 + "left outer join IC_IHD_TICKET_APPROVAL_DETAILS ap3 on tick.TICKET_ID = ap3.TICKET_ID and ap3.APPROVER_ID = 3 " 
			 + "left outer join IC_IHD_TICKET_APPROVAL_DETAILS ap4 on tick.TICKET_ID = ap4.TICKET_ID and ap4.APPROVER_ID = 4 " 
			 + "left outer join IC_IHD_TICKET_APPROVAL_DETAILS ap5 on tick.TICKET_ID = ap5.TICKET_ID and ap5.APPROVER_ID = 6 " 
			 + "left outer join IC_IHD_CATEGORY_MASTER func on func.CATEGORY_ID = tick.FUNCTION_ID AND func.IS_ACTIVE = 1 " 
			 + "join IC_IHD_CATEGORY_MASTER cat on cat.CATEGORY_ID = tick.CATEGORY_ID AND cat.IS_ACTIVE = 1 " 
			 + "left outer join IC_IHD_CATEGORY_MASTER subcat on subcat.CATEGORY_ID = tick.SUB_CATEGORY_ID AND subcat.IS_ACTIVE = 1 " 
			 + "left outer join IC_PRIORITY_MASTER prio on tick.PRIORITY_ID = prio.PRIORITY_ID " 
			 + "left outer join IC_USER_DETAILS clby on tick.ASSIGNED_TO = clby.EMPLOYEE_ID " 
			 + "join IC_WORKFLOW_STATE_MASTER curstat on curstat.STATE_ID = tick.WORKFLOW_STATE " 
			 + "left outer join IC_IHD_OUT_OF_SLA_REASON_MASTER outsla on outsla.REASON_ID = tick.OUT_OF_SLA_REASON " 
			 + "left outer join IC_AUDIT_DETAILS audit on audit.REFERENCE_ID = tick.TICKET_ID " 
			 + "left outer join IC_IHD_GROUP_MASTER clbygp " 
			 + "left outer join IC_USER_DETAILS gpmgr on gpmgr.EMPLOYEE_ID = clbygp.MANAGER " 
			 + "on clbygp.GROUP_ID = tick.ASSIGNED_GROUP " 
			 + "where tick.FUNCTION_ID = @funcId " 
			 + "AND " 
			 + "CASE WHEN @catId != 9999 AND tick.CATEGORY_ID in ( @catId ) THEN 1 " 
			 + "	 WHEN @catId = 9999  THEN 1 " 
			 + "END = 1 " 
			 + "AND " 
			 + "CASE WHEN @statusId != 0 AND tick.WORKFLOW_STATE in ( @statusId ) THEN 1 " 
			 + "	 WHEN @statusId = 0  THEN 1 " 
			 + "END = 1 " 
			 + "AND "
			 + "CASE WHEN @onBasisOf = 0 "
			 + "and audit.AUDIT_ID in(select MAX(AUDIT_ID) from IC_AUDIT_DETAILS group by REFERENCE_ID) "
			 + "and audit.MENU_ID=1 "
			 + "and cast((CONVERT(VARCHAR(30),dateadd(second,-14400,audit.CHANGED_DATE),121)) as date) >= @from_date "  
			 + "and cast((CONVERT(VARCHAR(30),dateadd(second,-14400,audit.CHANGED_DATE),121)) as date) <= @to_date THEN 1 " 
			 + "WHEN @onBasisOf = 1 AND cast((CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CREATED_DATE),121)) as date) >= @from_date " 
			 + "AND cast((CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CREATED_DATE),121)) as date) <= @to_date THEN 1 "
			 + "WHEN @onBasisOf = 2 AND cast((CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CLOSED_DATE),121)) as date) >= @from_date " 
			 + "AND cast((CONVERT(VARCHAR(30),dateadd(second,-14400,tick.CLOSED_DATE),121)) as date) <= @to_date THEN 1 "
			 + "END = 1 "
			 + "order by tick.TICKET_ID ";
}