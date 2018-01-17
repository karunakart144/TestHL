/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

public class HELPDESK_SQLQueryConstants {
	


    public static final String IC_HD_CREATE_TICKET = "insert into IC_IHD_TICKET_DETAILS(REQUESTED_BY,ON_BEHALF_OF,CATEGORY_ID,LOCATION_ID,CUBICLE_CODE,CONTACT_NO,ALTERNATE_CONTACT_NO,SUBJECT,DESCRIPTION,PROJECT_ID,COPY_TO,PRIORITY_ID,WORKFLOW_STATE,ASSIGNED_GROUP,ECT,SLA_STATUS,CREATED_BY,CREATED_DATE,USERS_IMPACTED,FUNCTION_ID,SUB_CATEGORY_ID,VERSION_NO,SOURCE,MANAGER_ID,LOC_DET_ID,SUB_PROJECT_ID)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    public static final String IC_UPDATE_EX_EMP_CHECK="UPDATE IC_IHD_TICKET_DETAILS SET IS_EX_EMPLOYEE=? WHERE TICKET_ID=?";

	public static final String IC_HD_INSERT_ATTACHMENTS = "insert into IC_IHD_TICKET_ATTACHMENT_DETAILS(TICKET_ID,REFERENCE_ID,ATTACHMENT_NAME,ATTACHMENT_PATH,CREATED_BY,CREATED_DATE) values (?,?,?,?,?,?)";

	// Modified by Sali

	public static final String IC_HD_TICKET_DETAILS = "Select s.TICKET_ID as 'Ticket#',s.Status,s.REQUESTED_BY 'Requested By',s.SUBJECT 'Subject',s.DESCRIPTION 'Description', "  
			+ " s.COPY_TO as 'CC To', s.SOURCE as 'Source',s.Severity as 'Severity',s.SEVERITY_ID as 'Severity ID',s.Priority 'Priority',   "
 + " s.PRIORITY_ID as 'Priority ID',s.ASSIGNED_GROUP as 'GroupID',s.AssignedGroup 'Assigned Group',s.ASSIGNED_TO as 'Assigned To',   "
 + " s.CREATED_DATE as 'Creation Date',s.ECT as 'ECT',s.SLA_STATUS as 'SLA Status',s.Location 'Location',s.LocationID 'LocationID', "  
 + " s.REFERENCE_ID as 'ReferenceID',s.Country 'Country',s.City, s.Area,s.ON_BEHALF_OF as 'On Behalf Of(EmployeeID)', "  
 + " s.CREATED_BY as 'Created By',s.CONTACT_NO as 'Contact Number(Primary)',s.ALTERNATE_CONTACT_NO as 'Alternate Contact Number', "  
 + " s.PRIVATE_NOTES as 'Private Notes',s.FUNCTION_ID as 'FunctionID', "  
+ "  s.ADDITIONAL_INFO as 'Additional Info',s.RESOLUTION_COMMENTS as 'Resolution Comments',s.FunctionName 'Function Name', "  
 + " s.CATEGORY_ID as 'CategoryID',s.CategoryName 'Category Name',s.SUB_CATEGORY_ID as 'SubCategoryID',s.SubCategoryName 'SubCategory Name', "  
 + " s.RequestorName 'Requestor Name',s.CreatorName 'Creator Name',s.USERS_IMPACTED as 'Impacted Users',s.OUT_OF_SLA_REASON,s.PROJECT_NAME,s.PROJECT_ID, "  
 + " s.USERS_IMPACTED,s.OUT_OF_SLA_COMMENTS,s.CLOSED_DATE,s.SUSPENDED_TILL,s.SUSPENDED_COMMENTS,s.ONHOLD_COMMENTS,s.REMINDER_DATE,s.FEEDBACK,s.FEEDBACK_COMMENTS, "  
 + " s.REOPEN_COMMENTS,s.INVOICE_NO,s.VENDOR_NAME,s.VERSION_NO,s.CHECK_FCS,s.CUBICLE_CODE as 'CubicalCode',s.FILTER_GROUP_LOCATION as 'Filtered Location', "  
 + " s.LOC_DET_ID as 'Location Detail ID',s.SEARCH_REFERENCE as 'Search Reference',s.ManagerID,s.ApprovedDate 'Approved Date',s.IS_CHANGE_REQUEST 'Change Request', "  
 + " s.RESOLUTION_STATUS,s.SLA_TIME 'TOTAL_SLA_TIME',s.GRADE,s.IS_APPROVAL_REQUIRED,s.IS_AUTOMATION_REQUIRED,s.IS_ORCH_REQUIRED "  
 + " ,case when s.SLA_CLOCK_ACTIVE=0 then null else case when (s.total_time+s.SLA_ACTIVE_TIME) >S.SLA_TIME then '0' "  
			 + " else S.SLA_TIME-(s.total_time+s.SLA_ACTIVE_TIME) end end as 'TIME_REMAINING' from "   
			 + " (SELECT ticket.TICKET_ID ,workflow.NAME as Status, "   
			 + " ticket.REQUESTED_BY,icrequser.GRADE_DESC 'GRADE',GM.IS_APPROVAL_REQUIRED , "   
			 + " ticket.SUBJECT,ticket.DESCRIPTION ,ticket.COPY_TO, "   
			  + " ticket.SOURCE ,sev.DESCRIPTION as Severity,ticket.SEVERITY_ID, "   
			 + " prior.DESCRIPTION as Priority,ticket.PRIORITY_ID,ticket.ASSIGNED_GROUP, "   
			 + " ihdGroup.NAME as AssignedGroup,ticket.ASSIGNED_TO,ticket.CREATED_DATE , "   
			 + " ticket.ECT,ticket.SLA_STATUS,loc.SHORT_NAME as Location,loc.LOCATION_ID as LocationID,ticket.REFERENCE_ID, "   
			 + " loc.COUNTRY as Country,loc.CITY as City , loc.AREA as Area,ticket.ON_BEHALF_OF , "   
			 + " ticket.CREATED_BY,ticket.CONTACT_NO, "   
			 + " ticket.ALTERNATE_CONTACT_NO, "   
			 + " ticket.ADDITIONAL_INFO,ticket.RESOLUTION_COMMENTS, "   
			 + " ticket.PRIVATE_NOTES ,ticket.FUNCTION_ID,ihdFuncCat.NAME as FunctionName, "   
			 + " ticket.CATEGORY_ID,ihdCat.NAME as CategoryName,ticket.SUB_CATEGORY_ID, "   
			 + " ihdSubCat.NAME as SubCategoryName, icrequser.NAME as RequestorName,iccrtuser.NAME as CreatorName, "   
			 + " ticket.USERS_IMPACTED,ticket.PROJECT_ID as PROJECT_ID, icpm.NAME+'('+ticket.PROJECT_ID+')' as PROJECT_NAME , "   
			 + " ISNULL(ticket.OUT_OF_SLA_REASON,'') as OUT_OF_SLA_REASON, "   
			 + " ISNULL(ticket.OUT_OF_SLA_COMMENTS,'') as OUT_OF_SLA_COMMENTS, ticket.CLOSED_DATE as CLOSED_DATE, "   
			 + " ticket.SUSPENDED_TILL as SUSPENDED_TILL, ISNULL(ticket.SUSPENDED_COMMENTS,'') as SUSPENDED_COMMENTS, "   
			 + " ISNULL(ticket.ONHOLD_COMMENTS ,'') as ONHOLD_COMMENTS,ISNULL(ticket.IS_AUTOMATION_REQUIRED,'') IS_AUTOMATION_REQUIRED,ticket.IS_ORCH_REQUIRED, "   
			 + " ticket.REMINDER_DATE as REMINDER_DATE, ISNULL(ticket.FEEDBACK,'') as FEEDBACK,ISNULL(ticket.FEEDBACK_COMMENTS,'') as FEEDBACK_COMMENTS, "   
			 + " ISNULL(ticket.REOPEN_COMMENTS,'') as REOPEN_COMMENTS, "   
			 + " ticket.INVOICE_NO as INVOICE_NO, ISNULL(ticket.VENDOR_NAME,'') as VENDOR_NAME, "   
			 + " ticket.VERSION_NO as VERSION_NO ,ticket.CHECK_FCS as CHECK_FCS,ticket.CUBICLE_CODE, "   
			 + " ticket.FILTER_GROUP_LOCATION,ticket.LOC_DET_ID,ticket.SEARCH_REFERENCE , "   
			 + " MIUD.NAME+'('+MIUD.EMPLOYEE_ID+')' as ManagerID,(select min(CHANGED_DATE) "    
			 + " from IC_AUDIT_DETAILS where CURRENT_STATE='HelpDesk Queue' and "    
			  + " PREVIOUS_STATE <> 'HelpDesk Queue' and PREVIOUS_STATE is not null "    
			 + " and REFERENCE_ID=''+ticket.TICKET_ID+'') as ApprovedDate,ihdSubCat.IS_CHANGE_REQUEST , "   
			 + " ticket.RESOLUTION_STATUS as RESOLUTION_STATUS, ticket.SLA_ACTIVE_TIME,workflow.SLA_CLOCK_ACTIVE, "  
			  + " SLAMAS.TIME SLA_TIME, "  
			 + " dbo.fn_Get_ResponseTime(getdate(),(SELECT top 1 CREATED_DATE "   
	 + " FROM IC_AUDIT_DETAILS AUD "  
	 + " WHERE  AUD.REFERENCE_ID = ticket.ticket_id "  
	 + " AND AUD.CURRENT_STATE = workflow.NAME "  
	 + " ORDER BY CHANGED_DATE DESC),ticket.FUNCTION_ID,ticket.ASSIGNED_GROUP) AS TOTAL_TIME "  
		  + " FROM IC_IHD_TICKET_DETAILS ticket left outer join IC_IHD_GROUP_MASTER ihdGroup "    
			 + " ON ticket.ASSIGNED_GROUP = ihdGroup.GROUP_ID left outer join IC_SEVERITY_MASTER sev "    
			 + " ON ticket.SEVERITY_ID = sev.SEVERITY_ID left outer join IC_LOCATION_MASTER loc "    
			 + " on ticket.LOCATION_ID = loc.LOCATION_ID left outer join "    
			 + " IC_PRIORITY_MASTER prior on ticket.PRIORITY_ID = prior.PRIORITY_ID left outer join IC_WORKFLOW_STATE_MASTER workflow "    
			 + " ON ticket.WORKFLOW_STATE = workflow.STATE_ID left outer join IC_IHD_CATEGORY_MASTER ihdCat "    
			 + " ON ticket.CATEGORY_ID = ihdCat.CATEGORY_ID left outer join IC_IHD_CATEGORY_MASTER ihdFuncCat     "
			 + " ON ticket.FUNCTION_ID = ihdFuncCat.CATEGORY_ID "    
			 + " left outer join IC_IHD_CATEGORY_MASTER ihdSubCat     "
			 + " ON ticket.SUB_CATEGORY_ID = ihdSubCat.CATEGORY_ID left outer join IC_USER_DETAILS icrequser "    
			 + " ON ticket.REQUESTED_BY = icrequser.EMPLOYEE_ID left outer join IC_USER_DETAILS iccrtuser "    
			 + " ON  ticket.CREATED_BY = iccrtuser.EMPLOYEE_ID left outer join IC_PROJECT_MASTER icpm "    
			 + " on icpm.PROJECT_ID=ticket.PROJECT_ID left outer join IC_USER_DETAILS MIUD "    
			 + " on MIUD.EMPLOYEE_ID=ticket.MANAGER_ID left outer join IC_GRADE_MASTER GM on GM.GRADE = icrequser.GRADE "  
			 + " LEFT OUTER JOIN IC_IHD_CATEGORY_PRIORITY_DETAILS CATPRI ON CATPRI.CATEGORY_ID = ticket.SUB_CATEGORY_ID AND CATPRI.PRIORITY_ID = ticket.PRIORITY_ID AND CATPRI.IS_ACTIVE = 1 "  
	 + " LEFT OUTER JOIN IC_IHD_SLA_MASTER SLAMAS ON SLAMAS.SLA_ID = CATPRI.SLA_ID AND SLAMAS.IS_ACTIVE = 1 "  
			 + " left outer join IC_PROJECT_MASTER PM on PM.PROJECT_ID = ticket.PROJECT_ID and PM.STATUS = 1 "   
			 + " ) S where S.TICKET_ID=";

	public static final String IC_DELETE_LOCKED_TICKET_DETAILS = "DELETE from IC_LOCKED_TICKET_DETAILS  where REFERENCE_ID=?";

	public static final String IC_TICKET_APPR_REJECT_LIST = "SELECT icappdet.TICKET_ID as TICKET_ID,icappdet.APPROVER_ID as APPROVER_ID ,icappmas.DESCRIPTION as APPROVER_NAME "
			+ ",icappdet.COMMENTS as COMMENTS ,icappdet.IS_EXCEPTIONAL_APPROVAL as IS_EXCEPTIONAL_APPROVAL ,icappdet.CREATED_BY as CREATED_BY,icappdet.EXCEPTION_START_DATE,icappdet.EXCEPTION_END_DATE "
			+ ",icuser.NAME as CREATED_NAME ,icappdet.CREATED_DATE as  CREATED_DATE ,icappdet.MODIFIED_BY as MODIFIED_BY,icappdet.MODIFIED_DATE as MODIFIED_DATE,icappdet.STATUS as STATUS "
			+ " FROM IC_IHD_TICKET_APPROVAL_DETAILS icappdet left outer join IC_USER_DETAILS icuser on icappdet.CREATED_BY = icuser.EMPLOYEE_ID left outer join IC_IHD_APPROVER_MASTER icappmas "
			+ " on icappmas.APPROVER_ID = icappdet.APPROVER_ID  where icappdet.TICKET_ID = ?";

	public static final String IC_TICKET_REQUESTOR_DETAILS = "select ICUSER.EMPLOYEE_ID as EMPLOYEE_ID ,ICUSER.NAME as EMP_NAME,ICUSER.ORGANIZATION as ORGANIZATION,ICUSER.GRADE as GRADE, "
			+ " ICUSER.REPORTING_MANAGER as REPORTING_MANAGER_ID,(select NAME from IC_USER_DETAILS where EMPLOYEE_ID=ICUSER.REPORTING_MANAGER ) as REPORTING_MANAGER_NAME,"
			+ " ISNULL(ICDUM.DU_ID,0) as DU_ID,ISNULL(ICDUM.NAME,'None') as DU_NAME,ICUSER.LOCATION_ID as LOCATION_ID,ILD.COUNTRY as COUNTRY,"
			+ " ILD.CITY as CITY,ILD.AREA as AREA,ILD.SHORT_NAME as SHORT_NAME,ICPM.CUSTOMER_NAME as CUSTOMER_NAME from IC_USER_DETAILS ICUSER left outer join"
			+ " IC_LOCATION_MASTER ILD on ILD.LOCATION_ID=ICUSER.LOCATION_ID ,IC_PROJECT_MASTER ICPM left outer join IC_DU_MASTER ICDUM on ICPM.DU=ICDUM.DU_ID"
			+ " where ICPM.PROJECT_ID=? and ICUSER.EMPLOYEE_ID=?";

	public static final String IC_APPROVAL_NEXT_TRANSITION = "select top 1 am.WORKFLOW_STATE from IC_IHD_CATEGORY_APPROVAL_DETAILS cad join IC_IHD_APPROVER_MASTER am on am.APPROVER_ID=cad.APPROVER_ID  where "
			+ " cad.CATEGORY_ID=? and cad.IS_ACTIVE=1 AND cad.APPROVER_ORDER > (select APPROVER_ORDER from IC_IHD_CATEGORY_APPROVAL_DETAILS where CATEGORY_ID=? "
			+ " and APPROVER_ID=?) order by cad.APPROVER_ORDER";

	public static final String IC_APPROVAL_STATUS_SAVE = "UPDATE IC_IHD_TICKET_DETAILS set ECT=?,WORKFLOW_STATE=? where TICKET_ID=?";
	
	public static final String IC_APPROVAL_STATUS_SAVE_ORCH = "UPDATE IC_IHD_TICKET_DETAILS set ECT=?,WORKFLOW_STATE=? ,ASSIGNED_TO =?, ASSIGNED_GROUP =?,IS_ORCH_REQUIRED=?,ADDITIONAL_INFO=?  where TICKET_ID=?";
	
	public static final String IC_REJECTED_STATUS_SAVE = "UPDATE IC_IHD_TICKET_DETAILS set ECT=?,WORKFLOW_STATE=?,CLOSED_DATE=? where TICKET_ID=?";

	public static final String IC_ORCH_TICKET_UPDATE = "UPDATE IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,ADDITIONAL_INFO=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where TICKET_ID=?";

	public static final String IC_INSERT_TICKET_APPROVAL_DETAILS = "INSERT INTO IC_IHD_TICKET_APPROVAL_DETAILS(TICKET_ID,APPROVER_ID,COMMENTS,IS_EXCEPTIONAL_APPROVAL,CREATED_BY,CREATED_DATE,STATUS) VALUES(?,?,?,?,?,?,?)";

	public static final String IC_INSERT_TICKET_EXCEPTION_APPROVAL_DETAILS = "INSERT INTO IC_IHD_TICKET_APPROVAL_DETAILS(TICKET_ID,APPROVER_ID,COMMENTS,IS_EXCEPTIONAL_APPROVAL,CREATED_BY,CREATED_DATE,STATUS,EXCEPTION_START_DATE,EXCEPTION_END_DATE) VALUES(?,?,?,?,?,?,?,?,?)";

	public static final String IC_IHD_TICKET_VERSION_NO = "SELECT VERSION_NO FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID=?";
	
	public static final String IC_IHD_TICKET_RequestedBy = "SELECT REQUESTED_BY FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID=?";
	
	public static final String IC_IHD_TICKET_ITERATION = "select top 1 COUNT(l.TICKET_ID) itr, (select COUNT(1) from "   
		+" IC_AUDIT_DETAILS where REFERENCE_ID = CONVERT(varchar(20),l.TICKET_ID) and CURRENT_STATE='re-open') reopen "  
		+" , l.LOG_MESSAGE , l.LOG_ID  from IC_ORCH_AUTOMATION_LOG l where l.TICKET_ID = ?" 
		+" group by l.TICKET_ID,l.LOG_MESSAGE, l.LOG_ID "
		+" order by  l.LOG_ID desc";
	public static final String IC_PROJECTS_FOR_EMPLOYEE_SQL="declare @emp varchar(20) = ?;"
		+ "	select distinct proj.NAME MASTER_PROJECT_NAME,"
		+ "proj.PROJECT_ID MASTER_PROJECT_ID ,"
		+ "mgr.EMPLOYEE_ID MANAGER_ID ,"
		+ "mas2.PROJECT_NAME SUB_PROJECT_NAME,"
		+ "mas2.SUB_PROJECT_ID SUB_PROJECT_ID,"
		+ "mgr2.EMPLOYEE_ID SUB_PROJECT_MANAGER,"
		+ "mas3.PROJECT_NAME SUB_PROJECT_NAME2,"
		+ "mas3.SUB_PROJECT_ID2 SUB_PROJECT_ID2,"
		+ "mgr3.EMPLOYEE_ID SUB_PROJECT_MANAGER2,"
		+ "dis.EMPLOYEE_ID DISPLAY_MANAGER,"
		+ "proj.NAME+ ISNULL(' - '+mas3.PROJECT_NAME+'('+mas3.SUB_PROJECT_ID2+')',ISNULL(' - '+mas2.PROJECT_NAME+'('+mas2.SUB_PROJECT_ID+')','('+proj.PROJECT_ID+')')) "
		+ "+ ' MGR: '+ dis.NAME+'('+dis.EMPLOYEE_ID+')' DISPLAY_NAME,"
		+ "proj.PROJECT_ID+ISNULL(','+mas3.SUB_PROJECT_ID2,ISNULL(','+mas2.SUB_PROJECT_ID,'')) PROJECT_VALUE  "
		+ " from IC_PROJECT_MEMBER_DETAILS mem "
		+ " join IC_PROJECT_MASTER proj on proj.PROJECT_ID = mem.PROJECT_ID and proj.STATUS ='1'"
		+ " join IC_USER_DETAILS usr on usr.EMPLOYEE_ID = mem.EMPLOYEE_ID and usr.IS_ACTIVE = 1"
		+ " left outer join IC_USER_DETAILS mgr "
		+ " join IC_GRADE_MASTER gra on gra.GRADE = mgr.GRADE and gra.GRADE_LEVEL > 7"
		+ " on mgr.EMPLOYEE_ID = proj.MANAGER_ID and mgr.IS_ACTIVE = 1"
		+ " left outer join IC_LEVEL2_PROJECT_MEMBER_DETAILS pm2 "
		+ " left outer join IC_LEVEL2_PROJECT_MASTER mas2 on mas2.MASTER_PROJECT_ID = pm2.MASTER_PROJECT_ID and mas2.SUB_PROJECT_ID = pm2.SUB_PROJECT_ID"
		+ " and CONVERT(date,GETDATE()) between mas2.START_DATE and mas2.END_DATE"
		+ " and mas2.STATUS = 1"
		+ " on pm2.TEAM_MEMBER = mem.EMPLOYEE_ID"
		+ " and pm2.MASTER_PROJECT_ID = proj.PROJECT_ID and pm2.IS_ACTIVE = 1"
		+ " left outer join IC_USER_DETAILS mgr2 "
		+ "   join IC_GRADE_MASTER gra2 on gra2.GRADE = mgr2.GRADE and gra2.GRADE_LEVEL > 7"
		+ " on mgr2.EMPLOYEE_ID = mas2.PERSON_RESPONSIBLE and mgr2.IS_ACTIVE = 1"
		+ "	left outer join IC_LEVEL3_PROJECT_MEMBER_DETAILS pm3 "
		+ " left outer join IC_LEVEL3_PROJECT_MASTER mas3 on mas3.MASTER_PROJECT_ID = pm3.MASTER_PROJECT_ID and mas3.SUB_PROJECT_ID = pm3.SUB_PROJECT_ID"
		+ "  and mas3.SUB_PROJECT_ID2 = pm3.SUB_PROJECT_ID2"
		+ "  and CONVERT(date,GETDATE()) between mas3.START_DATE and mas3.END_DATE"
		+ "  and mas3.STATUS = 1"
		+ " on pm3.TEAM_MEMBER = usr.EMPLOYEE_ID"
		+ " and pm3.MASTER_PROJECT_ID = proj.PROJECT_ID "
		+ " and pm3.SUB_PROJECT_ID = pm2.SUB_PROJECT_ID"
		+ " and pm3.IS_ACTIVE = 1"
		+ "	left outer join IC_USER_DETAILS mgr3 "
		+ "  join IC_GRADE_MASTER gra3 on gra3.GRADE = mgr3.GRADE and gra3.GRADE_LEVEL > 7"
		+ "  on mgr3.EMPLOYEE_ID = mas3.PERSON_RESPONSIBLE and mgr3.IS_ACTIVE = 1"
		+ "	 left outer join IC_USER_DETAILS dis on dis.EMPLOYEE_ID = ("
		+ "  case when ISNULL(mgr3.EMPLOYEE_ID,ISNULL(mgr2.EMPLOYEE_ID,ISNULL(mgr.EMPLOYEE_ID,usr.REPORTING_MANAGER))) = @emp then usr.REPORTING_MANAGER"
		+ "  else  ISNULL(mgr3.EMPLOYEE_ID,ISNULL(mgr2.EMPLOYEE_ID,ISNULL(mgr.EMPLOYEE_ID,usr.REPORTING_MANAGER))) end"
		+ " )"
		+ " where mem.EMPLOYEE_ID = @emp"
		+ " and mem.IS_ACTIVE = 1"
		+ " and CONVERT(date,GETDATE()) between mem.START_DATE and mem.END_DATE";
 
	
	
	public static final String IC_EMPROLE_DETAILS = "select case when exists (select 1 from IC_PROJECT_MASTER PM  where PM.MANAGER_ID = usr.EMPLOYEE_ID) " +
			"then '1' else '0' end as PM,case when exists (select 1 from IC_USER_ROLE_DETAILS urd where urd.EMPLOYEE_ID = usr.EMPLOYEE_ID and urd.ROLE_ID = '24' and IS_ACTIVE=1) " +
			"then '1' else '0' end as 'SLM_Mgr_Approver',case when exists (select 1 from IC_USER_ROLE_DETAILS urd where urd.EMPLOYEE_ID=usr.EMPLOYEE_ID and urd.ROLE_ID='11' and IS_ACTIVE=1) " +
			"then '1'  else '0' end as 'IT_Security_Approver',case when exists (select 1 from IC_USER_ROLE_DETAILS urd,IC_IHD_APPROVER_EMPLOYEE_DETAILS app where urd.EMPLOYEE_ID=usr.EMPLOYEE_ID and urd.ROLE_ID='10' and urd.IS_ACTIVE=1 and app.employee_id=urd.employee_id) " +
			"then '1'  else '0' end as 'IT_Function_Head_Approver',case when exists (select 1 from IC_USER_ROLE_DETAILS urd ,ic_tcr_approver_master app where urd.EMPLOYEE_ID=usr.EMPLOYEE_ID and urd.ROLE_ID='9' and urd.IS_ACTIVE=1 and app.employee_id=urd.employee_id) " +
			"then '1'  else '0' end as 'TechCR_Approver',case when exists (select 1 from IC_USER_ROLE_DETAILS urd,IC_IHD_APPROVER_EMPLOYEE_DETAILS apm " +
			"where urd.EMPLOYEE_ID=usr.EMPLOYEE_ID and apm.EMPLOYEE_ID=usr.EMPLOYEE_ID and urd.IS_ACTIVE=1 and urd.ROLE_ID not in('11','10','9','24') or apm.APPROVER_ID not in ('6')) then '1'  else '0' end as 'Others' " +
			"from IC_USER_DETAILS usr where usr.EMPLOYEE_ID=? and usr.IS_ACTIVE=1";
	
	//public static final String IC_DELEGATED_DETAILS = "select DELEGATE_TO,START_DATE,END_DATE,PROJECT_ID,ROLE_ID from IC_DELEGATE_APPROVAL_DETAILS where EMPLOYEE_ID=?";
	
	
	public static final String IC_IHD_DELETE_LOCKED_DETAILS = "DELETE IC_LOCKED_TICKET_DETAILS where MENU_ID=? AND REFERENCE_ID=?";

	public static final String IC_IHD_Attachment_Ticket_Details_UPDATE = "UPDATE IC_IHD_TICKET_ATTACHMENT_DETAILS SET ATTACHMENT_NAME=? , ATTACHMENT_PATH=? , VERIFIED_BY=? WHERE TICKET_ID=? AND REFERENCE_ID=?";

	public static final String IC_IHD_Attachment_Details_UPDATE = "UPDATE IC_IHD_TICKET_ATTACHMENT_DETAILS SET VERIFIED_BY=? WHERE TICKET_ID=? AND REFERENCE_ID=?";

	public static final String IC_UPDATE_TICKET_STATUS = "update IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,ECT=? where TICKET_ID=?";

	public static final String SELECT_WORKFLOW_STATE = "select STATE_ID from IC_WORKFLOW_STATE_MASTER where NAME like 'HelpDesk Queue' and WORKFLOW_ID=?";

	public static final String IC_WORKFLOWNAME = "SELECT NAME FROM IC_WORKFLOW_STATE_MASTER WHERE STATE_ID=?";

	//public static final String IC_PROJECT_DETAILS = "SELECT IPM.PROJECT_ID as 'PROJECT_ID',IPM.NAME as 'NAME',IPM.DU as 'DU',IPM.START_DATE as 'START_DATE',IPM.END_DATE as 'END_DATE',IPM.MANAGER_ID as 'MANAGER_ID',IPM.STATUS as 'STATUS',IPM.CREATED_BY as 'CREATED_BY',IPM.CREATED_DATE as 'CREATED_DATE', ICU.NAME+'('+IPM.MANAGER_ID+')' as 'MANAGER_NAME' FROM IC_PROJECT_MASTER IPM left outer join IC_USER_DETAILS ICU on IPM.MANAGER_ID= ICU.EMPLOYEE_ID   WHERE PROJECT_ID=?";
	
	
	public static final String IC_PROJECT_DETAILS = "declare @emp varchar(20) = ?, " 
			+ " @MasProj varchar(20) = ?,  "
 + " @Proj varchar(50) = ?;  "
 + " select mgr.EMPLOYEE_ID,mgr.NAME "
+ " from IC_USER_DETAILS emp " 
 + " left outer join IC_PROJECT_MASTER MasProj "  
       + " join  IC_PROJECT_MEMBER_DETAILS mem on mem.PROJECT_ID = MasProj.PROJECT_ID "  
 + " and mem.EMPLOYEE_ID = @emp "  
 + " and mem.IS_ACTIVE = 1 " 
 + " and CONVERT(date,GETDATE()) between mem.START_DATE and mem.END_DATE "
+ " on MasProj.PROJECT_ID = @MasProj "
+ " left outer join IC_USER_DETAILS usr1 on usr1.EMPLOYEE_ID = MasProj.MANAGER_ID and usr1.IS_ACTIVE = 1 " 
 + " left outer join IC_GRADE_MASTER gr1 on gr1.GRADE = usr1.GRADE and gr1.GRADE_LEVEL > 7 "    
 + " left outer join IC_LEVEL2_PROJECT_MASTER mas2 "  
       + " left outer join IC_USER_DETAILS usr2 "  
             + " join IC_GRADE_MASTER gr2 on gr2.GRADE = usr2.GRADE and gr2.GRADE_LEVEL > 7 " 
       + " on usr2.EMPLOYEE_ID = mas2.PERSON_RESPONSIBLE "  
       + " and usr2.IS_ACTIVE = 1 " 
 + " on mas2.MASTER_PROJECT_ID = @MasProj "
+ " and mas2.SUB_PROJECT_ID = @Proj " 
 + " and mas2.STATUS = 1 " 
 + " and CONVERT(date,GETDATE()) between mas2.START_DATE and mas2.END_DATE " 
 + " left outer join IC_LEVEL3_PROJECT_MASTER mas3 "  
      + " left outer join IC_USER_DETAILS usr3 "  
             + " left outer join IC_GRADE_MASTER gr3 on gr3.GRADE = usr3.GRADE and gr3.GRADE_LEVEL > 7 " 
       + " on usr3.EMPLOYEE_ID = mas3.PERSON_RESPONSIBLE "  
       + " and usr3.IS_ACTIVE = 1 "       
 + " on mas3.MASTER_PROJECT_ID = @MasProj "
+ " and mas3.SUB_PROJECT_ID2 = @Proj " 
 + " and CONVERT(date,GETDATE()) between mas3.START_DATE and mas3.END_DATE " 
+ " join IC_USER_DETAILS mgr on mgr.EMPLOYEE_ID = ISNULL(usr3.EMPLOYEE_ID,ISNULL(usr2.EMPLOYEE_ID,ISNULL(usr1.EMPLOYEE_ID,emp.REPORTING_MANAGER))) " 
 + " where emp.EMPLOYEE_ID = @emp "
+ " and emp.IS_ACTIVE = 1"; 
	
	public static final String IC_PROJECT_DETAILS_ONLY_MAIN_PROJECT = "select A.MANAGER_ID,B.NAME+'('+A.MANAGER_ID+')' MANAGER_NAME from ( "
			+ " select  "
			+ " case when temp.L3_PERSON_RESPONSIBLE is not null and UD.GRADE_DESC > 'Level 7' then temp.L3_PERSON_RESPONSIBLE "
+ " else case when (UD.GRADE_DESC < 'Level 8' Or temp.L3_PERSON_RESPONSIBLE IS null Or temp.L3_PERSON_RESPONSIBLE IS not  null) and temp.L2_PERSON_RESPONSIBLE IS NOT NULL and UD1.GRADE_DESC > 'Level 7'  THEN temp.L2_PERSON_RESPONSIBLE "
+ " else case when (UD1.GRADE_DESC < 'Level 8' or temp.L2_PERSON_RESPONSIBLE is not null OR temp.L2_PERSON_RESPONSIBLE is null) and TEMP.MANAGER_ID IS NOT NULL then TEMP.MANAGER_ID "
+ " else case when temp.MANAGER_ID is not null then temp.MANAGER_ID  "
+ " else case when temp.MANAGER_ID is null then UD3.REPORTING_MANAGER end end end end end as MANAGER_ID "
+ " from ( "
+ " select PM.PROJECT_ID,PM.EMPLOYEE_ID,MAS.MANAGER_ID,DET.SUB_PROJECT_ID,MAS1.PERSON_RESPONSIBLE AS L2_PERSON_RESPONSIBLE ,DET1.SUB_PROJECT_ID2,MAS2.PERSON_RESPONSIBLE AS L3_PERSON_RESPONSIBLE "
+ " from  "
+ " IC_PROJECT_MEMBER_DETAILS PM " 
+ " LEFT OUTER JOIN IC_LEVEL2_PROJECT_MEMBER_DETAILS DET on PM.PROJECT_ID = DET.MASTER_PROJECT_ID " 
+ " and PM.EMPLOYEE_ID = DET.TEAM_MEMBER and PM.IS_ACTIVE = 1 and DET.IS_ACTIVE = 1 "
+ " LEFT OUTER JOIN IC_LEVEL3_PROJECT_MEMBER_DETAILS DET1 on DET1.MASTER_PROJECT_ID = DET.MASTER_PROJECT_ID and DET1.SUB_PROJECT_ID = DET.SUB_PROJECT_ID "
+ " and DET1.TEAM_MEMBER = DET.TEAM_MEMBER and DET1.IS_ACTIVE='1' "
+ " LEFT OUTER JOIN IC_PROJECT_MASTER MAS on MAS.PROJECT_ID = PM.PROJECT_ID and PM.IS_ACTIVE= 1 AND MAS.STATUS = '1' "
+ " LEFT OUTER JOIN IC_LEVEL2_PROJECT_MASTER MAS1 on MAS1.MASTER_PROJECT_ID = DET.MASTER_PROJECT_ID and DET.IS_ACTIVE = 1 AND MAS1.STATUS = '1' and DET.SUB_PROJECT_ID=MAS1.SUB_PROJECT_ID "
+ " LEFT OUTER JOIN IC_LEVEL3_PROJECT_MASTER MAS2 on MAS2.MASTER_PROJECT_ID = DET1.MASTER_PROJECT_ID and MAS2.SUB_PROJECT_ID = MAS1.SUB_PROJECT_ID AND MAS2.STATUS = '1' and MAS2.SUB_PROJECT_ID2=DET1.SUB_PROJECT_ID2 "
+ " and DET1.IS_ACTIVE = 1 where GETDATE() between PM.START_DATE and PM.END_DATE "
+ " and PM.EMPLOYEE_ID = ? and PM.PROJECT_ID = ?  "
+ " ) TEMP "
+ " left outer JOIN IC_USER_DETAILS UD on UD.EMPLOYEE_ID = TEMP.L3_PERSON_RESPONSIBLE and UD.IS_ACTIVE = 1 "
+ " left outer JOIN IC_USER_DETAILS UD1 on UD1.EMPLOYEE_ID = TEMP.L2_PERSON_RESPONSIBLE and UD1.IS_ACTIVE = 1 "
+ " left outer join IC_USER_DETAILS UD3 on UD3.EMPLOYEE_ID = TEMP.EMPLOYEE_ID and UD3.IS_ACTIVE = 1) A JOIN IC_USER_DETAILS B on A.MANAGER_ID = B.EMPLOYEE_ID";

	public static final String IC_EMPLOYEE_DETAILS_SQL = "select Distinct IU.EMPLOYEE_ID,IU.NAME,IU.EMAIL_ADDRESS,IU.GRADE as 'GRADE',IU.LOCATION_ID,IU.REPORTING_MANAGER "  
			+ " as 'REPORTING_MANAGER_ID' ,isnull(IU1.NAME,'') as 'REPORTING_MANAGER_NAME',IU.ORGANIZATION as 'ORGANIZATION', "  
			  + " IU1.ORGANIZATION as 'REPORTING_MANAGER_ORGANIZATION' "  
			  + " from IC_USER_DETAILS IU left outer join IC_GRADE_MASTER GM on GM.GRADE = IU.GRADE "
			  + " left outer JOIN  IC_USER_DETAILS IU1 on IU1.EMPLOYEE_ID=IU.REPORTING_MANAGER "   
			  + " left outer JOIN IC_USER_ROLE_DETAILS urd on urd.EMPLOYEE_ID=IU.EMPLOYEE_ID and urd.IS_ACTIVE=1  "   
			  + " where urd.EMPLOYEE_ID=? "
			 + " AND IU.IS_ACTIVE=1 ";
			;
	//Added to implement the tool to track EX employees quereis for HR function
	public static final String IC_EMPLOYEE_DETAILS_EX_SQL = "select Distinct IU.EMPLOYEE_ID,IU.NAME,IU.EMAIL_ADDRESS,IU.GRADE as 'GRADE',IU.LOCATION_ID,IU.REPORTING_MANAGER "
		+ " as 'REPORTING_MANAGER_ID' ,IU1.NAME as 'REPORTING_MANAGER_NAME',IU.ORGANIZATION as 'ORGANIZATION', "
		+ " IU1.ORGANIZATION as 'REPORTING_MANAGER_ORGANIZATION',IU.IS_ACTIVE as 'IS_ACTIVE' ,"
		+ " IS_PREMIUM_MEMBER = CASE WHEN IPM.MEMBER_ID=IU.EMPLOYEE_ID THEN 'YES' ELSE 'NO' END "
		+ " from IC_USER_DETAILS IU left outer join IC_IHD_PREMIUM_MEMBERS IPM on IPM.MEMBER_ID=IU.EMPLOYEE_ID and IPM.IS_ACTIVE=1,IC_USER_DETAILS IU1 where IU.EMPLOYEE_ID=? "
		+ " and IU1.EMPLOYEE_ID=IU.REPORTING_MANAGER " 	;

	/*public static final String SELECT_IC_ASSET_DETAILS = "select  AssetDet.ASSET_DET_ID,Classification.CLASSIFICATION,"
			+ "Assettype.ASSET_TYPE,AssetDet.MAKE ,AssetDet.MODEL ,AssetDet.ALLOCATION_DATE,AssetDet.WARRANTY_START_DATE, "
			+ "AssetDet.WARRANTY_END_DATE,AssetDet.SERIAL_NUMBER from  JAVA_ASSET_TEST.DBO.IC_ASSET_DETAILS AssetDet left outer  join  "
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_TYPE_MASTER Assettype ON AssetDet.ASSET_TYPE_ID = Assettype.ASSET_TYPE_ID left outer  join "
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_CLASSIFICATION_MASTER Classification on  AssetDet.CLASSIFICATION_ID=Classification.CLASSIFICATION_ID "
			+ "WHERE AssetDet.ASSIGNED_TO = ? and AssetDet.STATE_ID =2 ORDER BY AssetDet.ALLOCATION_DATE desc ";*/
	//L2:1184 SQL is changed
	public static final String SELECT_IC_ASSET_DETAILS = "select  AssetDet.ASSET_DET_ID,Classification.CLASSIFICATION,"
			+ "Assettype.ASSET_TYPE,AssetDet.MAKE ,AssetDet.MODEL ,AssetDet.ALLOCATION_DATE,AssetDet.WARRANTY_START_DATE, "
			+ "AssetDet.WARRANTY_END_DATE,AssetDet.SERIAL_NUMBER "
			+ "from JAVA_ASSET_TEST.DBO.ic_asset_employee_mapping em, JAVA_ASSET_TEST.DBO.IC_ASSET_DETAILS AssetDet left outer  join  "
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_TYPE_MASTER Assettype ON AssetDet.ASSET_TYPE_ID = Assettype.ASSET_TYPE_ID left outer  join "
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_CLASSIFICATION_MASTER Classification on  AssetDet.CLASSIFICATION_ID=Classification.CLASSIFICATION_ID "
			+ "WHERE AssetDet.STATE_ID =2 "
			+ "AND em.assigned_to = ? "
			+ "AND em.ASSET_DET_ID = AssetDet.ASSET_DET_ID "
			+ "ANd em.is_active =1 "
			+ "ORDER BY AssetDet.ALLOCATION_DATE desc";

	public static final String SELECT_IC_ASSET_COMPONENTS = "Select ACM.COMPONENT,ACD.SERIAL_NUMBER,ACD.COMPONENT_ID,ACD.ASSET_COMP_ID "
			+ "from JAVA_ASSET_TEST.DBO.IC_ASSET_COMP_DETAILS ACD left outer join "
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_COMPONENT_MASTER ACM "
			+ "on ACD.COMPONENT_ID=ACM.COMPONENT_ID where ACD.PARENT_SERIAL_NUMBER=?";

	public static final String UPDATE_IC_IHD_TICKET_DETAILS_ADDITIONAL_INFO = "update IC_IHD_TICKET_DETAILS set additional_info=? where ticket_id=?";

	public static final String SELECT_ADD_INFO_IC_IHD_TICKET_DETAILS = "Select ADDITIONAL_INFO from IC_IHD_TICKET_DETAILS where TICKET_ID=?";
	
	public static final String SELECT_PRIVATE_NOTES_IC_IHD_TICKET_DETAILS = "Select PRIVATE_NOTES from IC_IHD_TICKET_DETAILS where TICKET_ID=?";

	public static final String INSERT_TICKET_ATTACHMENT = "INSERT INTO IC_IHD_TICKET_ATTACHMENT_DETAILS (TICKET_ID"
			+ ",REFERENCE_ID"
			+ ",ATTACHMENT_PATH"
			+ ",ATTACHMENT_NAME"
			+ ",VERIFIED_BY"
			+ ",CREATED_BY"
			+ " ,CREATED_DATE"
			+ " ) VALUES(?,?,?,?,?,?,?)";

	public static final String IC_IHD_INSERT_ASSET_MAPPING = "IF EXISTS (SELECT 1 FROM IC_IHD_ASSET_MAPPING WHERE CHILD_SERIAL_NO =?)"
			+ "update IC_IHD_ASSET_MAPPING set CHILD_SERIAL_NO=?,MODIFIED_BY=?,MODIFIED_DATE=? where "
			+ "CHILD_SERIAL_NO=? ELSE INSERT INTO IC_IHD_ASSET_MAPPING "
			+ "(TICKET_ID,MENU_ID,CHILD_SERIAL_NO,PARENT_SERIAL_NO,CREATED_BY,CREATED_DATE) "
			+ "VALUES(?,?,?,?,?,?)";
	public static final String IC_IHD_INSERT_ASSET_MAPPING_PARENT = "IF EXISTS (SELECT 1 FROM IC_IHD_ASSET_MAPPING WHERE PARENT_SERIAL_NO =?)"
			+ "update IC_IHD_ASSET_MAPPING set PARENT_SERIAL_NO=?,MODIFIED_BY=?,MODIFIED_DATE=? where "
			+ "PARENT_SERIAL_NO=? ELSE INSERT INTO IC_IHD_ASSET_MAPPING "
			+ "(TICKET_ID,MENU_ID,CHILD_SERIAL_NO,PARENT_SERIAL_NO,CREATED_BY,CREATED_DATE) "
			+ "VALUES(?,?,?,?,?,?)";
	public static final String IC_IHD_SELECT_ASSET_MAPPING = "SELECT CHILD_SERIAL_NO FROM IC_IHD_ASSET_MAPPING";

	public static final String IC_IHD_SELECT_PARENT_ASSET_MAPPING = "SELECT PARENT_SERIAL_NO FROM IC_IHD_ASSET_MAPPING";

	public static final String IC_IHD_SELECT_DETAIL_ASSET = "select AssetDetail.ASSET_DET_ID,Company.COMPANY_NAME,"
			+ "AssetDetail.SERIAL_NUMBER,AssetDetail.ASSET_ID,"
			+ "AssetDetail.PSFT_ASSET_CODE,Country.COUNTRY_NAME,"
			+ "Location.LOCATION_NAME,Department.DEPARTMENT,"
			+ "Sublocation.SUB_LOCATION,Assettype.ASSET_TYPE,"
			+ "Classification.CLASSIFICATION,AssetDetail.HOST_NAME,"
			+ "Workflow.NAME as STATUS,AssetDetail.ASSET_RETURN_DATE,"
			+ "AssetDetail.MAKE,AssetDetail.MODEL,PrimaryAdminDetails.NAME AS PRIMARY_ADMIN,"
			+ "ManagerDetails.NAME AS MANAGER,SecAdminDetails.NAME AS SECONDARY_ADMIN,AssetDetail.ALLOCATION_DATE"
			+ ",AssetDetail.IP_ADDRESS,AssetDetail.CUBICLE_CODE,AssigendToDetails.NAME AS ASSIGNED_TO,"
			+ "projAssigendDetails.NAME AS PROJECT_NAME,ProjManagerDetails.NAME AS PROJECT_MANAGER,"
			+ "AssetDetail.CRITICALITY,AssetDetail.IS_SECURITY_CONFIRMED,"
			+ "AssetDetail.PRIORTIZATION,AssetDetail.LAPTOP_GATEPASS_NO,"
			+ "AssetDetail.ENGINEER_REMARKS,Proctype.PROC_TYPE,"
			+ "AssetDetail.PURCHASE_ORDER,AssetDetail.PO_DATE,"
			+ "Currency.CURRENCY,AssetDetail.APPRX_COST,"
			+ "AssetDetail.IS_BONDED,AssetDetail.STPI_BOND,"
			+ "AssetDetail.STPI_BOND_DATE,AssetDetail.STP_BOND_EXP_DATE,"
			+ "Ownertype.OWNER_TYPE,AssetDetail.OWNED_BY,"
			+ "Disptype.DISP_TYPE,AssetDetail.ADMIN_REMARKS,"
			+ "Supporttype.SUPP_TYPE,PrinciVendor.NAME AS VENDOR,"
			+ "SupplyVendor.NAME AS SUPPLY_VENDOR,AssetDetail.INSTALLATION_DATE,"
			+ "AssetDetail.WARRANTY_START_DATE,AssetDetail.WARRANTY_END_DATE,"
			+ "SupportVendor.NAME  AS SUPPORT_VENDOR,AssetDetail.SUPP_REFERENCE,"
			+ "AssetDetail.SUPPORT_Start_date,AssetDetail.SUPPORT_END_DATE,"
			+ "AssetDetail.SUPPORT_DESC,AssetOwnerDetails.NAME AS OWNER,"
			+ "AssetDetail.FUNCTION_NAME,AssetDetail.CREATED_BY,"
			+ "AssetDetail.CREATED_DATE,AssetDetail.MODIFIED_BY,"
			+ "AssetDetail.MODIFIED_DATE from JAVA_ASSET_TEST.DBO.IC_ASSET_DETAILS AssetDetail "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_TYPE_MASTER Assettype on AssetDetail.ASSET_TYPE_ID=Assettype.ASSET_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_USER_DETAILS PrimaryAdminDetails on AssetDetail.PRIMARY_ADMIN=PrimaryAdminDetails.EMPLOYEE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_USER_DETAILS ManagerDetails on AssetDetail.MANAGER=ManagerDetails.EMPLOYEE_ID "
			+ "left outer join  JAVA_ASSET_TEST.DBO.IC_USER_DETAILS SecAdminDetails on AssetDetail.SECONDARY_ADMIN= SecAdminDetails.EMPLOYEE_ID "
			+ "left outer join  JAVA_ASSET_TEST.DBO.IC_USER_DETAILS AssigendToDetails on AssetDetail.ASSIGNED_TO= AssigendToDetails.EMPLOYEE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_PROJECT_MASTER projAssigendDetails on AssetDetail.PROJ_ASSIGNED_TO=projAssigendDetails.PROJECT_ID "
			+ "left outer join  JAVA_ASSET_TEST.DBO.IC_USER_DETAILS ProjManagerDetails on AssetDetail.PROJECT_MANAGER= ProjManagerDetails.EMPLOYEE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_PROC_TYPE_MASTER Proctype on AssetDetail.PROCUREMENT_TYPE_ID =Proctype.PROC_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_CURRENCY_MASTER Currency on AssetDetail.CURRENCY_ID = Currency.CURRENCY_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_OWNER_TYPE_MASTER Ownertype on AssetDetail.OWNER_TYPE_ID =Ownertype.TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_DISP_TYPE_MASTER Disptype on AssetDetail.DISP_TYPE_ID=Disptype.DISP_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_SUPP_TYPE_MASTER Supporttype on AssetDetail.SUPPORT_TYPE_ID = Supporttype.SUPP_TYPE_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS PrinciVendor on AssetDetail.PRINCIPAL_VENDOR_ID =PrinciVendor.VENDOR_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS SupplyVendor on AssetDetail.SUPPLYING_VENDOR_ID =SupplyVendor.VENDOR_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS SupportVendor on AssetDetail.SUPPORTING_VENDOR =SupportVendor.VENDOR_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_USER_DETAILS AssetOwnerDetails on AssetDetail.ASSET_OWNER= AssetOwnerDetails.EMPLOYEE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_DEPARTMENT_MASTER Department on AssetDetail.DEPARTMENT_ID=Department.DEPARTMENT_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_CLASSIFICATION_MASTER Classification on AssetDetail.CLASSIFICATION_ID=Classification.CLASSIFICATION_ID,"
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_COUNTRY_MASTER Country ,JAVA_ASSET_TEST.DBO.IC_ASSET_COMPANY_MASTER Company,"
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_LOCATION_MASTER Location,JAVA_ASSET_TEST.DBO.IC_ASSET_SUBLOCATION_MASTER Sublocation,"
			+ "JAVA_ASSET_TEST.DBO.IC_WORKFLOW_STATE_MASTER Workflow  where  AssetDetail.SERIAL_NUMBER=? "
			+ "and AssetDetail.COUNTRY_ID=Country.COUNTRY_ID and AssetDetail.COMPANY_ID=Company.COMPANY_ID "
			+ "and AssetDetail.LOCATION_ID=Location.LOCATION_ID and "
			+ "AssetDetail.SUB_LOCATION_ID=Sublocation.SUBLOCATION_ID and AssetDetail.STATE_ID = Workflow.STATE_ID";

	public static final String IC_IHD_SELECT_COMP_DETAIL_ASSET = "SELECT  CompDetails.ASSET_COMP_ID,Company.COMPANY_NAME,CompDetails.PARENT_SERIAL_NUMBER,"
			+ "CompDetails.SERIAL_NUMBER,CompDetails.ASSET_ID,CompDetails.PSFT_ASSET_CODE,"
			+ "Country.COUNTRY_NAME,Location.LOCATION_NAME,Assettype.ASSET_TYPE,"
			+ "Classification.CLASSIFICATION ,Department.DEPARTMENT,"
			+ "Component.COMPONENT ,Workflow.NAME as STATUS,CompDetails.MAKE,"
			+ "CompDetails.MODEL,CompDetails.CRITICALITY,CompDetails.IS_SECURITY_CONFIRMED,"
			+ "CompDetails.PRIORTIZATION,CompDetails.ENGINEER_REMARKS,"
			+ "Proctype.PROC_TYPE,CompDetails.PURCHASE_ORDER,CompDetails.PO_DATE,"
			+ "Currency.CURRENCY,CompDetails.APPRX_COST,CompDetails.IS_BONDED,"
			+ "CompDetails.STPI_BOND,CompDetails.STPI_BOND_DATE,CompDetails.STP_BOND_EXP_DATE,"
			+ "Ownertype.OWNER_TYPE ,CompDetails.OWNED_BY,Disptype.DISP_TYPE,"
			+ "CompDetails.ADMIN_REMARKS,Supporttype.SUPP_TYPE,PrinciVendor.NAME AS VENDOR,"
			+ "SupplyVendor.NAME AS SUPPLY_VENDOR,CompDetails.INSTALLATION_DATE,"
			+ "CompDetails.WARRANTY_START_DATE,CompDetails.WARRANTY_END_DATE,"
			+ "SupportVendor.NAME AS SUPPORTING_VENDOR,CompDetails.SUPP_REFERENCE,"
			+ "CompDetails.SUPPORT_START_DATE,CompDetails.SUPPORT_END_DATE,"
			+ "CompDetails.SUPPORT_DESC ,AssetOwnerDetails.NAME AS OWNER,"
			+ "CompDetails.FUNCTION_NAME ,CompDetails.CREATED_BY ,CompDetails.CREATED_DATE,"
			+ "CompDetails.MODIFIED_BY,CompDetails.MODIFIED_DATE FROM JAVA_ASSET_TEST.DBO.IC_ASSET_COMP_DETAILS CompDetails "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_TYPE_MASTER Assettype on CompDetails.ASSET_TYPE_ID=Assettype.ASSET_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_DEPARTMENT_MASTER Department on CompDetails.DEPARTMENT_ID=Department.DEPARTMENT_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_CLASSIFICATION_MASTER Classification on "
			+ "CompDetails.CLASSIFICATION_ID=Classification.CLASSIFICATION_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_OWNER_TYPE_MASTER Ownertype on CompDetails.OWNER_TYPE_ID =Ownertype.TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_DISP_TYPE_MASTER Disptype on "
			+ "CompDetails.DISP_TYPE_ID=Disptype.DISP_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_SUPP_TYPE_MASTER Supporttype on CompDetails.SUPPORT_TYPE_ID = Supporttype.SUPP_TYPE_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS PrinciVendor on CompDetails.PRINCIPAL_VENDOR_ID =PrinciVendor.VENDOR_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS SupplyVendor on CompDetails.SUPPLYING_VENDOR_ID =SupplyVendor.VENDOR_ID "
			+ "left  outer join JAVA_ASSET_TEST.DBO.IC_ASSET_VENDOR_DETAILS SupportVendor on CompDetails.SUPPORTING_VENDOR =SupportVendor.VENDOR_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_USER_DETAILS AssetOwnerDetails on CompDetails.ASSET_OWNER= AssetOwnerDetails.EMPLOYEE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_PROC_TYPE_MASTER Proctype on CompDetails.PROCUREMENT_TYPE_ID =Proctype.PROC_TYPE_ID "
			+ "left outer join JAVA_ASSET_TEST.DBO.IC_ASSET_CURRENCY_MASTER Currency on CompDetails.CURRENCY_ID = Currency.CURRENCY_ID,"
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_COUNTRY_MASTER Country ,JAVA_ASSET_TEST.DBO.IC_ASSET_COMPANY_MASTER Company,"
			+ " JAVA_ASSET_TEST.DBO.IC_ASSET_LOCATION_MASTER Location,JAVA_ASSET_TEST.DBO.IC_WORKFLOW_STATE_MASTER Workflow ,"
			+ "JAVA_ASSET_TEST.DBO.IC_ASSET_COMPONENT_MASTER Component where CompDetails.SERIAL_NUMBER=? and "
			+ "CompDetails.COUNTRY_ID=Country.COUNTRY_ID and CompDetails.COMPANY_ID=Company.COMPANY_ID "
			+ "and CompDetails.LOCATION_ID=Location.LOCATION_ID and CompDetails.STATE_ID = Workflow.STATE_ID "
			+ "and CompDetails.COMPONENT_ID=Component.COMPONENT_ID";

	public static final String IC_USER_DETAILS = "SELECT EMPLOYEE_ID,NAME,EMAIL_ADDRESS FROM IC_USER_DETAILS WHERE EMPLOYEE_ID=?";

	public static final String IC_USER_SYSTEM_DETAILS = "INSERT INTO IC_USER_SYSTEM_DETAILS(EMPLOYEE_ID,TICKET_ID,ACTION,ACTION_DATE,OS_DETAILS,BROWSER_DETAILS) VALUES(?,?,?,?,?,?) ";

	public static final String IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS = "INSERT INTO IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS(TICKET_ID,FUNCTION_ID,FIELD_ID,FIELD_VALUE,IS_ACTIVE,CREATED_BY,CREATED_DATE) VALUES(?,?,?,?,1,?,?) ";

	public static final String UPDATE_IC_IHD_TICKET_DETAILS_ASSIGNED_GROUP_ECT = "update IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,ASSIGNED_GROUP=?,ECT=? where TICKET_ID=?";

	public static final String IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_COUNT = "SELECT COUNT(*) FROM IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS WHERE TICKET_ID=? AND FUNCTION_ID=? AND FIELD_ID=?";

	public static final String IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_SELECT = "SELECT TICKET_ID,FUNCTION_ID,FIELD_ID,FIELD_VALUE FROM IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS WHERE TICKET_ID=? AND FUNCTION_ID=? AND FIELD_ID=?";

	public static final String IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_UPDATE = "UPDATE IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS SET FIELD_VALUE=?,MODIFIED_BY=?,MODIFIED_DATE=? WHERE TICKET_ID=? AND FUNCTION_ID=? AND FIELD_ID=?";

	public static final String IC_LOCKED_TICKET_DETAILS = "select ILTD.LOCKED_BY,IUD.NAME from IC_LOCKED_TICKET_DETAILS ILTD,IC_USER_DETAILS IUD where IUD.EMPLOYEE_ID=ILTD.LOCKED_BY AND ILTD.MENU_ID=? AND ILTD.REFERENCE_ID=?";

	public static final String IC_INSERT_LOCK_DETAILS = "insert into IC_LOCKED_TICKET_DETAILS(REFERENCE_ID,MENU_ID,DATA,LOCKED_BY,LOCKED_DATE) values(?,?,?,?,?)";

	/*
	 * public static final String IC_LOGGED_IN_GROUP_MEMBERS_AVAILABLE=
	 * "	select gmd.MEMBER_ID+'('+iud.NAME+')' as member_name_id," +
	 * "	gmd.MEMBER_ID as member_id,gmd.GROUP_ID,gmd.WORK_LOAD,igm.NAME, " +
	 * "	grm.ROLE_NAME,gmd.ASSIGNMENT_REQUIRED " +
	 * "	from IC_USER_AVAILABILITY_IN_OUT iua,IC_IHD_GROUP_MEMBER_DETAILS gmd,IC_USER_DETAILS iud,"
	 * + "	IC_IHD_GROUP_ROLE_MASTER grm ,IC_IHD_GROUP_MASTER igm" +
	 * "	where iua.DATE_TIME >DATEADD(DAY,-1,GETDATE ())" +
	 * "	and iua.STATUS = 'IN'" +
	 * "	and iua.DATE_TIME in (select MAX(inusrav.DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT inusrav "
	 * + "	where inusrav.EMPLOYEE_ID = iua.EMPLOYEE_ID " +
	 * "	and inusrav.DATE_TIME = (select MAX(ininav.DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT ininav "
	 * +
	 * "   where ininav.EMPLOYEE_ID = inusrav.EMPLOYEE_ID ) and inusrav.STATUS = 'IN')"
	 * + "	and iua.EMPLOYEE_ID=gmd.MEMBER_ID " + "	and gmd.IS_ACTIVE=1 " +
	 * "	and iud.EMPLOYEE_ID=gmd.MEMBER_ID" + "	and igm.GROUP_ID=gmd.GROUP_ID "
	 * + "	and grm.ROLE_ID=gmd.ROLE_ID " + "	and gmd.GROUP_ID=?";
	 */

	public static final String IC_LOGGED_IN_GROUP_MEMBERS_AVAILABLE = "select distinct iud.NAME+'('+iua.EMPLOYEE_ID+')' as member_name_id,"
			+ "	gmd.MEMBER_ID as member_id,gmd.GROUP_ID,gmd.WORK_LOAD,igm.NAME,grm.ROLE_NAME,gmd.ASSIGNMENT_REQUIRED,gmd.IS_ACTIVE "
			+ "	from IC_USER_AVAILABILITY_IN_OUT iua ,IC_USER_DETAILS iud,IC_IHD_GROUP_MEMBER_DETAILS gmd,"
			+ "	IC_IHD_GROUP_MASTER igm,IC_IHD_GROUP_ROLE_MASTER grm "
			+ "	where "
			+ "	iud.EMPLOYEE_ID=iua.EMPLOYEE_ID "
			+ "	and iud.EMPLOYEE_ID=gmd.MEMBER_ID "
			+ "	and igm.GROUP_ID=gmd.GROUP_ID "
			+ "	and grm.ROLE_ID=gmd.ROLE_ID	"
			+ "	and iua.STATUS=	"
			+ "	case"
			+ "	when (select STATUS FROM IC_USER_AVAILABILITY_IN_OUT where DATE_TIME ="
			+ "	(select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID ) and EMPLOYEE_ID=iua.EMPLOYEE_ID  )='IN' then 'IN'"
			+ "	when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID"
			+ "	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN'"
			+ "	)="
			+ "	(select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID"
			+ "	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'"
			+ "	  )"
			+ "	 ) then 'OUT'"
			+ "	when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID"
			+ "	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN'"
			+ "	  )<"
			+ "	  (select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID"
			+ "		and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'"
			+ "	  )"
			+ "	 ) then 'OUT'"
			+ "	else"
			+ "	'IN'"
			+ "	end"
			+ "	and iua.STATUS='IN'  and gmd.IS_ACTIVE=1 and gmd.ASSIGNMENT_REQUIRED=1  "
			+ "	and gmd.GROUP_ID=?";
	public static final String SELECT_DEPTID_FROM_IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS = "SELECT FIELD_VALUE FROM IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS WHERE TICKET_ID=? AND FUNCTION_ID=? AND FIELD_ID=?";
	
	public static final String SELECT_MASTER_ID_QUERY="SELECT TICKET_ID FROM IC_IHD_MASTER_TICKET_DETAILS WHERE TICKET_ID=(SELECT TICKET_ID FROM IC_IHD_TICKET_DETAILS WHERE REFERENCE_ID=?)";

	public static final String SELECT_TICKET_ID_FROM_IC_IHD_TICKET_APPROVAL_DETAILS = "SELECT TICKET_ID FROM IC_IHD_TICKET_APPROVAL_DETAILS WHERE TICKET_ID=? AND APPROVER_ID=?";

	public static final String GET_NEXT_VALID_APPROVER = "select top 1 am.APPROVER_ID from IC_IHD_CATEGORY_APPROVAL_DETAILS cad join IC_IHD_APPROVER_MASTER am on am.APPROVER_ID=cad.APPROVER_ID  where"
			+ " cad.CATEGORY_ID=? and cad.IS_ACTIVE=1 AND cad.APPROVER_ORDER > (select APPROVER_ORDER from IC_IHD_CATEGORY_APPROVAL_DETAILS where CATEGORY_ID=?"
			+ " and APPROVER_ID=?) order by cad.APPROVER_ORDER";
	
	 public static final String INSERT_ICONNECT_ITRACK_STATUS_EXCEPTIONAL = "IF EXISTS (SELECT 1 FROM IC_IHD_TICKET_ITRACK_EXCEPTIONAL WHERE ICONNECT_TICKETID = ?) "
         + "UPDATE IC_IHD_TICKET_ITRACK_EXCEPTIONAL SET STATUS=?,IS_PROCESSED=?,MODIFIED_DATE=? where ICONNECT_TICKETID=? "
         + "ELSE INSERT INTO IC_IHD_TICKET_ITRACK_EXCEPTIONAL(ICONNECT_TICKETID,STATUS,CREATED_DATE) VALUES(?,?,?)";

	 public static final String UPDATE_ICONNECT_ITRACK_STATUS_UNEXCEPTIONAL = "IF EXISTS (SELECT 1 FROM IC_IHD_TICKET_ITRACK_EXCEPTIONAL WHERE ICONNECT_TICKETID = ?) "
         + "UPDATE IC_IHD_TICKET_ITRACK_EXCEPTIONAL SET STATUS=?,IS_PROCESSED=?,MODIFIED_DATE=? where ICONNECT_TICKETID=? ";
	 
	 public static final String UPDATE_FEEDBACK_COMMENTS="UPDATE IC_IHD_TICKET_DETAILS SET FEEDBACK=?, FEEDBACK_COMMENTS=? ,MODIFIED_BY=? ,MODIFIED_DATE=GETDATE() WHERE TICKET_ID=?";

	public static final String UPDATE_ADDITIONAL_INFO_CHECKFCS_IC_IHD_TICKET_DETAILS = "UPDATE IC_IHD_TICKET_DETAILS SET CHECK_FCS=1,ADDITIONAL_INFO=? WHERE TICKET_ID=?";

	public static final String UPDATE_TICKET_APPROVAL_DETAILS = "UPDATE IC_IHD_TICKET_APPROVAL_DETAILS set ACCESS_REVOKED_DATE=? where TICKET_ID=? and EXCEPTION_START_DATE is not null";

	public static final String INSERT_TICKET_APPROVAL_DETAILS_FOR_PMRO = "INSERT INTO IC_IHD_TICKET_APPROVAL_DETAILS (TICKET_ID,APPROVER_ID,COMMENTS,STATUS,CREATED_BY,CREATED_DATE) values (?,?,?,?,?,GETDATE())";

	public static final String SELECT_APPROVER_FROM_IC_IHD_TICKET_APPROVAL_DETAILS_FOR_APPROVER_TICKET = "SELECT APPROVER_ID FROM IC_IHD_TICKET_APPROVAL_DETAILS WHERE APPROVER_ID=? AND TICKET_ID=?";

	public static final String UPDATE_CHECK_FCS_IN_TICKET_DETAILS = "UPDATE IC_IHD_TICKET_DETAILS SET CHECK_FCS=0 where TICKET_ID=?";
	/*Auto Assignment*/
	 public static final String SELECT_TICKET_LIST_FOR_ENGNR="SELECT *,NAME as STATE_NAME, prty.DESCRIPTION as PRIORITY_NAME FROM IC_IHD_TICKET_DETAILS det,IC_WORKFLOW_STATE_MASTER stMaster, IC_PRIORITY_MASTER prty  " +
	 		"WHERE det.WORKFLOW_STATE=stMaster.STATE_ID and prty.PRIORITY_ID=det.PRIORITY_ID  " +
		"and stMaster.NAME  not in ('Cancelled','Resolved/Closed','Closed(By System)','Closed(By User)','Closed with Success','Closed with Rollback','AutoClosed(By System)','Closed with No Change','Withdraw') and ASSIGNED_TO=?";

	public static final String SELECT_MAX_LOGIN_TIME_FOR_ENGNR="Select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? AND STATUS='IN'";
	
	public static final String SELECT_PREV_LOGIN_TIME_FOR_ENGNR="Select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? and DATE_TIME <(Select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT WHERE EMPLOYEE_ID=? AND STATUS='IN')";
	
	public static final String SELECT_PREV_MAX_IN_TIME_FOR_ENGNR="Select MAX(DATE_TIME) FROM IC_USER_AVAILABILITY_IN_OUT WHERE STATUS='IN' and DATE_TIME<(Select MAX(DATE_TIME) FROM IC_USER_AVAILABILITY_IN_OUT WHERE STATUS='IN'" +
			" AND EMPLOYEE_ID=?) AND EMPLOYEE_ID=?" ;

	public static final String INSERT_SCORE_FOR_ENGINEER="INSERT INTO IC_IHD_ENGINEER_SCORE(EMPLOYEE_ID,HIGH_SCORE,MEDIUM_SCORE,LOW_SCORE,TOTAL_SCORE,CREATED_BY,CREATED_DATE) " +
			"VALUES(?,?,?,?,?,?,GETDATE())";
	
	public static final String UPDATE_SCORE_FOR_ENGINEER="UPDATE IC_IHD_ENGINEER_SCORE SET HIGH_SCORE=?,MEDIUM_SCORE=?,LOW_SCORE=?,TOTAL_SCORE=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE()" +
			"WHERE EMPLOYEE_ID=?";
	
	public static final String INSERT_SCORE_FOR_ENGINEER_AUDIT_LOG="INSERT INTO IC_IHD_ENGINEER_SCORE_AUDIT_LOG(EMPLOYEE_ID,DATA_CHANGE,CREATED_BY,CREATED_DATE)" +
			" VALUES(?,?,?,GETDATE())";
	public static final String SELECT_COUNT_ENGNR_IN_SCORE_TABLE="Select COUNT(EMPLOYEE_ID) from IC_IHD_ENGINEER_SCORE WHERE EMPLOYEE_ID=?";
	
	
	public static final String SELECT_LOGGED_MEMBERS_SCORE="SELECT score.EMPLOYEE_ID,score.HIGH_SCORE,score.MEDIUM_SCORE,score.LOW_SCORE,score.TOTAL_SCORE,score.CREATED_BY,score.CREATED_DATE,grmRole.ROLE_ID FROM IC_IHD_ENGINEER_SCORE score,IC_IHD_GROUP_ROLE_MASTER grmRole   WHERE score.EMPLOYEE_ID IN ( " +
			" select distinct	gmd.MEMBER_ID as member_id " +
			" from IC_USER_AVAILABILITY_IN_OUT iua ,IC_USER_DETAILS iud,IC_IHD_GROUP_MEMBER_DETAILS gmd," +
			" IC_IHD_GROUP_MASTER igm,IC_IHD_GROUP_ROLE_MASTER grm" +
			" where" +
			" iud.EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and iud.EMPLOYEE_ID=gmd.MEMBER_ID" +
			" and igm.GROUP_ID=gmd.GROUP_ID" +
			" and grmRole.ROLE_ID=gmd.ROLE_ID"+
			" and grm.ROLE_ID=gmd.ROLE_ID and grm.ROLE_ID<>'2' " +
			" and iua.STATUS=" +
			" case" +
			"  when (select STATUS FROM IC_USER_AVAILABILITY_IN_OUT where DATE_TIME =" +
			"  (select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID ) and EMPLOYEE_ID=iua.EMPLOYEE_ID  )='IN' then 'IN'" +
			"   when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN')=" +
			"	(select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'" +
			"   )" +
			"   ) then 'OUT'" +
			"	when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN'" +
			"   )<" +
			"  (select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'" +
			"   )" +
			" 	) then 'OUT'" +
			" 	else" +
			"	'IN'" +
			"	end" +
			"	and iua.STATUS='IN'  and gmd.IS_ACTIVE=1  and gmd.ASSIGNMENT_REQUIRED=1 " +
			"	and gmd.GROUP_ID=?" +
			" )";
	
	
	public static final String SELECT_MIN_SCORE_FOR_GROUP="SELECT MIN(LOW_SCORE) as LOW_SCORE,MIN(MEDIUM_SCORE) as MEDIUM_SCORE,MIN(HIGH_SCORE) as HIGH_SCORE,MIN(TOTAL_SCORE) as TOTAL_SCORE FROM IC_IHD_ENGINEER_SCORE  WHERE EMPLOYEE_ID IN ( " +
			" select distinct	gmd.MEMBER_ID as member_id " +
			" from IC_USER_AVAILABILITY_IN_OUT iua ,IC_USER_DETAILS iud,IC_IHD_GROUP_MEMBER_DETAILS gmd," +
			" IC_IHD_GROUP_MASTER igm,IC_IHD_GROUP_ROLE_MASTER grm" +
			" where" +
			" iud.EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and iud.EMPLOYEE_ID=gmd.MEMBER_ID" +
			" and igm.GROUP_ID=gmd.GROUP_ID" +
			" and grm.ROLE_ID=gmd.ROLE_ID and grm.ROLE_ID<>'2' " +
			" and iua.STATUS=" +
			" case" +
			" when (select STATUS FROM IC_USER_AVAILABILITY_IN_OUT where DATE_TIME =" +
			" (select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID ) and EMPLOYEE_ID=iua.EMPLOYEE_ID  )='IN' then 'IN'" +
			"  when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN')=" +
			" (select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'  )" +
			" ) then 'OUT'" +
			" when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN'" +
			" )<" +
			" (select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			" and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'" +
			"  ) ) then 'OUT'" +
			"	else" +
			" 'IN'" +
			" end" +
			" and iua.STATUS='IN'  and gmd.IS_ACTIVE=1   and gmd.ASSIGNMENT_REQUIRED=1  " + 
			" and gmd.GROUP_ID=?" +
			" )";
	
	public static final String SELECT_EMPLOYEE_SCORE_DETAIL="Select HIGH_SCORE,MEDIUM_SCORE,LOW_SCORE,TOTAL_SCORE from IC_IHD_ENGINEER_SCORE WHERE EMPLOYEE_ID='?'";
	
	public static final String SELECT_LAST_TICKET_ASSIGNED_TO="SELECT TOP(1) ASSIGNED_TO FROM IC_IHD_TICKET_DETAILS " +
			"	WHERE ASSIGNED_TO_DATE IN (SELECT MIN(ASSIGNED_TO_DATE) FROM IC_IHD_TICKET_DETAILS " +
			"	WHERE TICKET_ID IN (";
	
	public static final String SELECT_MAX_TICKET_ID="SELECT TICKET_ID,ASSIGNED_TO_DATE FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID IN (SELECT MAX(TICKET_ID) FROM IC_IHD_TICKET_DETAILS WHERE ASSIGNED_TO IN (?))";
			/*Auto Assignment*/
	/* Added for Emergency L1 */ 
	public static final String SELECT_IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_EMERGENCY = "SELECT TICKET_ID,FUNCTION_ID,FIELD_ID,FIELD_VALUE "
			+ "FROM IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS WHERE TICKET_ID=? AND FUNCTION_ID=? AND FIELD_ID IN(102,103,105,106,107,108)";
	public static final String SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_SCRIPT = "Select COUNT(AD.REFERENCE_ID) from IC_IHD_TICKET_ATTACHMENT_DETAILS AD "
			+ "WHERE AD.REFERENCE_ID IN(76) AND AD.TICKET_ID=?";
	public static final String SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_TESTREPORT = "Select COUNT(AD.REFERENCE_ID) from IC_IHD_TICKET_ATTACHMENT_DETAILS AD "
		+ "WHERE AD.REFERENCE_ID IN(77) AND AD.TICKET_ID=?";
	public static final String SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_APPMAIL = "Select COUNT(AD.REFERENCE_ID) from IC_IHD_TICKET_ATTACHMENT_DETAILS AD "
		+ "WHERE AD.REFERENCE_ID IN(75) AND AD.TICKET_ID=?";
	/* Emergency L1 */
	
	//Added for iconnect - iTrack integartion uninstallation request creation
	
	public static final String IC_INACTIVE_EMPLOYEE_DETAILS_SQL = "select Distinct IU.EMPLOYEE_ID,IU.NAME,IU.EMAIL_ADDRESS,IU.GRADE as 'GRADE',IU.LOCATION_ID,IU.REPORTING_MANAGER "
		+ " as 'REPORTING_MANAGER_ID' ,IU1.NAME as 'REPORTING_MANAGER_NAME',IU.ORGANIZATION as 'ORGANIZATION', "
		+ " IU1.ORGANIZATION as 'REPORTING_MANAGER_ORGANIZATION' ,"
		+ " IS_PREMIUM_MEMBER = CASE WHEN IPM.MEMBER_ID=IU.EMPLOYEE_ID THEN 'YES' ELSE 'NO' END "
		+ " from IC_USER_DETAILS IU left outer join IC_IHD_PREMIUM_MEMBERS IPM on IPM.MEMBER_ID=IU.EMPLOYEE_ID and IPM.IS_ACTIVE=1,IC_USER_DETAILS IU1 where IU.EMPLOYEE_ID=? "
		+ " and IU1.EMPLOYEE_ID=IU.REPORTING_MANAGER ";
	
	public static final String IC_HD_CREATE_TICKET_ITRACK = "insert into IC_IHD_TICKET_DETAILS(REQUESTED_BY,ON_BEHALF_OF,CATEGORY_ID,LOCATION_ID,CUBICLE_CODE,CONTACT_NO,ALTERNATE_CONTACT_NO,SUBJECT,DESCRIPTION,PROJECT_ID,COPY_TO,PRIORITY_ID,WORKFLOW_STATE,ASSIGNED_GROUP,ECT,SLA_STATUS,CREATED_BY,CREATED_DATE,USERS_IMPACTED,FUNCTION_ID,SUB_CATEGORY_ID,VERSION_NO,SOURCE,MANAGER_ID,LOC_DET_ID,SUB_PROJECT_ID,ADDITIONAL_INFO,ASSIGNED_TO,IS_AUTOMATION_REQUIRED,IS_ORCH_REQUIRED)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	/*public static final String IC_INSERT_ROLE_SQL ="INSERT INTO [IC_USER_ROLE_DETAILS]"
	             + " ([EMPLOYEE_ID]"
	             + " ,[ROLE_ID]"
	             + "  ,[IS_ACTIVE]"
	             + "  ,[CREATED_BY]"
	             + " ,[CREATED_DATE]"
	             + " )"
	             + " VALUES(?,?,1,'SYSTEM',GETDATE())";*/
	public static final String IC_ROLE_MERGE_SQL="MERGE INTO IC_USER_ROLE_DETAILS m"
		 + " USING(SELECT ? v_role_id ,? v_emp_id)val "
		 + " on (m.role_id= val.v_role_id"
		 + " AND m.employee_id = val.v_emp_id)"
		 + " when matched then "
		 + " UPDATE set is_active =1,"
		 + " modified_by =?,"
		 + " modified_date = current_timestamp"
		 + " when not matched then"
		 + " insert(employee_id,role_id,is_active,created_by,created_date)"
		 + " values(val.v_emp_id,val.v_role_id,1,'DELEGATED',current_timestamp);";
	
	public static final String IC_INSERT_TECHCR_APPROVER_SQL="INSERT INTO [IC_TCR_APPROVER_MASTER]"
	             + " ([APPROVER_ID]"
	             + " ,[DESCRIPTION]"
	             + " ,[EMPLOYEE_ID]"
	             + " ,[LOCATION_ID]"
	             + " ,[GROUP_ID]"
	             + " ,[IS_ACTIVE]"
	             + " ,[CREATED_BY]"
	               + " ,[CREATED_DATE]"
	               + ")"
	               + " select (select (max(APPROVER_ID)+1) from IC_TCR_APPROVER_MASTER ),DESCRIPTION,?,LOCATION_ID,GROUP_ID,IS_ACTIVE,'DELEGATED',GETDATE()"
	               + " from IC_TCR_APPROVER_MASTER where EMPLOYEE_ID=? AND IS_ACTIVE=1";
	
	
	public static final String IC_UPDATE_TECHCR_APPROVER_SQL = "update IC_TCR_APPROVER_MASTER set IS_ACTIVE=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where EMPLOYEE_ID=? and CREATED_BY='DELEGATED'";
	
	public static final String IC_UPDATE_USER_ROLE_SQL = "UPDATE IC_USER_ROLE_DETAILS set IS_ACTIVE=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where EMPLOYEE_ID=? and CREATED_BY='DELEGATED'";
	
	public static final String IC_UPDATE_IHD_APPROVER_EMPLOYEE_DETAILS_SQL = "update IC_IHD_APPROVER_EMPLOYEE_DETAILS set IS_ACTIVE=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where EMPLOYEE_ID=? and CREATED_BY='DELEGATED'";
	
	
	
	public static final String IC_ITHEAD_INSERT_SQL="INSERT INTO [IC_IHD_APPROVER_EMPLOYEE_DETAILS] "
		 + " ([APPROVER_ID]"
		 + " ,[EMPLOYEE_ID]"
		 + " ,[IS_ACTIVE]"
		 + " ,[CREATED_BY]"
		 + " ,[CREATED_DATE]"
		 + "  ,[LOCATION_ID])"
		 + " select APPROVER_ID,?,IS_ACTIVE,'DELEGATED',GETDATE(),LOCATION_ID"
		 + "  from IC_IHD_APPROVER_EMPLOYEE_DETAILS where EMPLOYEE_ID=? and IS_ACTIVE=1";
	
	   public static final String IC_ITHEAD_UPDATE_SQL="UPDATE IC_IHD_APPROVER_EMPLOYEE_DETAILS"
           + " SET IS_ACTIVE=1,MODIFIED_BY=?,MODIFIED_DATE=getdate()"
           + " WHERE EMPLOYEE_ID=? AND APPROVER_ID=2 AND LOCATION_ID IN ("
           + " select LOCATION_ID"
           + " from IC_IHD_APPROVER_EMPLOYEE_DETAILS where EMPLOYEE_ID=? and IS_ACTIVE=1)";

	   public static final String IC_SLATIME="SELECT SLA_ACTIVE_TIME FROM IC_IHD_TICKET_DETAILS WHERE TICKET_ID=? ";

	   public static final String IC_ORCH_TRANSACTION_DETAILS = "INSERT INTO IC_ORCH_TRANSACTION_DETAILS "	
		   		+" (TICKET_ID,FIELD_ID,ORCH_VALUE,CREATED_BY,CREATED_DATE) "  
		   		+" VALUES(?,?,?,?,?) ";

	   public static final String update_IC_ORCH_TRANSACTION_DETAILS = "UPDATE IC_ORCH_TRANSACTION_DETAILS set ORCH_VALUE=?,MODIFIED_BY=?,MODIFIED_DATE=GETDATE() where TICKET_ID=? and FIELD_ID=?";
	   
	   public static final String GET_OPERATIONAL_TIME_FLAG="select CASE WHEN (convert(VARCHAR(10), GETDATE(), 23) IN (select DATE from IC_IHD_SLA_EXCLUSION_DATES where SLA_ID=s.SLA_ID AND LOCATION_ID = s.LOCATION_ID and IS_ACTIVE=1)) THEN 'NO'" 
		   + " else Case when (s.START_TIME < s.END_TIME ) "
		   + " then case when CONVERT (time, CURRENT_TIMESTAMP) between s.start_time and s.END_TIME" 
		   + " then 'YES' else 'NO' end  "
		   + " ELSE "
		   + " case when (CONVERT (time, CURRENT_TIMESTAMP) between '00:00' and s.END_TIME" 
		   + " OR CONVERT (time, CURRENT_TIMESTAMP) between  s.START_TIME and '23:59:59') "
		   + " then 'YES' else 'NO' end  "
		   + " end end as 'IS_AVAILABLE' "
		   + " from "
		   + " (select op.SLA_ID, op.DAY, op.START_TIME, op.END_TIME, op.NEXT_WORKING_DAY,gr.LOCATION_ID " 
		   + " from IC_IHD_GROUP_MASTER gr "
		   + " LEFT OUTER JOIN ic_ihd_group_function_mapping fun ON fun.LOCATION_ID = gr.LOCATION_ID  and fun.GROUP_ID = gr.GROUP_ID and fun.IS_ACTIVE=1"
		   + " left outer join IC_IHD_SLA_OPERATING_TIME_DETAILS op on op.SLA_ID = fun.SLA_ID and op.IS_ACTIVE=1 and op.DAY = LEFT(DATENAME(weekday, getdate()),3)"
		   + " where fun.GROUP_ID=? AND fun.CATEGORY_ID = ? and gr.IS_ACTIVE=1 )s";
	   
	   public static final String UPDATE_TICKET_DETAILS_SLA_IN="UPDATE IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,RESOLUTION_COMMENTS=?,SLA_STATUS=?,RESOLUTION_STATUS=?,MODIFIED_BY=?,MODIFIED_DATE=GETUTCDATE() "
		   + "where TICKET_ID=?";
	   
	   public static final String UPDATE_TICKET_DETAILS_SLA_OUT="UPDATE IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,RESOLUTION_COMMENTS=?,OUT_OF_SLA_REASON=?,OUT_OF_SLA_COMMENTS=?,SLA_STATUS=?,RESOLUTION_STATUS=?,MODIFIED_BY=?,MODIFIED_DATE=GETUTCDATE() "
		   + "where TICKET_ID=?";
	   
	   //L1 1558105
	   public static final String IC_PROJECT_MASTER_DETAILS="select pm.MANAGER_ID,ud.NAME+'('+pm.MANAGER_ID+')' 'MANAGER_NAME' from IC_PROJECT_MASTER pm, IC_USER_DETAILS ud where pm.PROJECT_ID=? and ud.EMPLOYEE_ID=pm.MANAGER_ID";
	   
	   public static final String GET_ALL_PROJECT_DETAILS_FOR_TICKET="select ISNULL(proj.PROJECT_ID,'') 'MASTER PROJECT ID' "
			   + " ,ISNULL(proj.NAME+'('+proj.PROJECT_ID+')','') 'MASTER PROJECT NAME', "
+ " ISNULL(mas2.SUB_PROJECT_ID,ISNULL(mas32.SUB_PROJECT_ID,'')) 'LEVEL 2 PROJECT ID' "
+ " ,ISNULL(mas2.PROJECT_NAME+'('+ISNULL(mas2.SUB_PROJECT_ID,mas32.SUB_PROJECT_ID)+')',ISNULL(mas32.PROJECT_NAME+'('+ISNULL(mas2.SUB_PROJECT_ID,mas32.SUB_PROJECT_ID)+')','')) 'LEVEL 2 PROJECT NAME', "
+ " ISNULL(mas3.SUB_PROJECT_ID2,'') 'LEVEL 3 PROJECT ID' "
+ " ,ISNULL(mas3.PROJECT_NAME+'('+ISNULL(mas3.SUB_PROJECT_ID2,'')+')','') 'LEVEL 3 PROJECT NAME' "
+ " from IC_IHD_TICKET_DETAILS tick "
+ " join IC_PROJECT_MASTER proj on proj.PROJECT_ID = tick.PROJECT_ID "
+ " left outer join IC_LEVEL2_PROJECT_MASTER mas2 " 
+ " on mas2.MASTER_PROJECT_ID = proj.PROJECT_ID " 
+ " and mas2.SUB_PROJECT_ID = tick.SUB_PROJECT_ID "
+ " and mas2.STATUS = 1 "
+ " left outer join IC_LEVEL3_PROJECT_MASTER mas3 " 
      + " LEFT OUTER JOIN IC_LEVEL2_PROJECT_MASTER mas32 on mas32.MASTER_PROJECT_ID = mas3.MASTER_PROJECT_ID and mas32.SUB_PROJECT_ID = mas3.SUB_PROJECT_ID "
      + " and mas32.STATUS = 1 "
+ " on mas3.MASTER_PROJECT_ID = proj.PROJECT_ID "
+ " and mas3.SUB_PROJECT_ID2 = tick.SUB_PROJECT_ID "
+ " and mas3.STATUS = 1 "
+ " where tick.TICKET_ID = ?";



public static final String INSERT_IC_IHD_ITRACK_ASSET_LINK="INSERT INTO IC_IHD_ITRACK_ASSET_LINK (TICKET_ID,EMPLOYEE_ID,CORE_ID,CREATED_BY,CREATED_DATE) VALUES (?,?,?,?,?)";

public static final String GET_AUTOMATION_FLAG="select IS_AUTOMATION_REQUIRED,IS_ORCH_REQUIRED from IC_IHD_TICKET_DETAILS where TICKET_ID=?";




 public static final String GET_ALL_AVILABLE_CUBICLE_CODE="select top 10 pos.CUBICLE_CODE CUBICLE_CODE from " 
		   +"	IC_TEMP_TABLE_FOR_ICONNECT_CAMPUSCRAWLER_MAPPING map join	" 
		   +" CC_POSITION_MASTER pos on pos.REFERENCE_ID = map.CC_MAPPED_LOCATION and pos.IS_ACTIVE = 1 " 
		   +" where map.LOC_DET_ID = ? and map.IS_ACTIVE = 1 and pos.CUBICLE_CODE like ? "
		   +" order by pos.CUBICLE_CODE ";  
	   
	   public static final String IC_SU_ONLOAD_EMP_MAPPING_OR_NOT=
		    " declare @Emp Varchar(20) = ?, "
		   +"  @LocDetID smallint = NULL, "
		   + "  @CubicleCode varchar(100) = '', " 
		   + " @TCubicleCode varchar(100) = NULL;  " 

		   + " IF EXISTS(SELECT 1 FROM CC_EMPLOYEE_MAPPING WHERE EMPLOYEE_ID = @Emp AND VERIFIED_STATUS = 'Sent for Verification' and IS_ACTIVE = 1) begin  " 
		   + " SELECT @LocDetID = IC_LOC_DET_ID,@CubicleCode = CUBICLE_CODE  " 
		   + " FROM CC_EMPLOYEE_MAPPING  " 
		   + " WHERE EMPLOYEE_ID = @Emp AND VERIFIED_STATUS = 'Sent for Verification' and IS_ACTIVE = 1;  " 
		   + " end  " 
		   + "  ELSE IF EXISTS (SELECT 1 FROM CC_EMPLOYEE_MAPPING WHERE EMPLOYEE_ID = @Emp AND VERIFIED_STATUS = 'Verified' and IS_ACTIVE = 1) begin  " 
		   + " SELECT top 1 @LocDetID = IC_LOC_DET_ID,@CubicleCode = CUBICLE_CODE  " 
		   + " FROM CC_EMPLOYEE_MAPPING  " 
		   + " WHERE EMPLOYEE_ID = @Emp AND VERIFIED_STATUS = 'Verified' and IS_ACTIVE = 1  " 
		   + " order by EMPLOYEE_MAPPING_ID desc;  " 
		   + " end " 
		   + " if @LocDetID IS NULL " 
		   + " begin " 
		   + " select top 1 @LocDetID = tick.LOC_DET_ID,@TCubicleCode = tick.CUBICLE_CODE from IC_IHD_TICKET_DETAILS tick " 
		   + " where tick.REQUESTED_BY = @Emp " 
		   + " and tick.FUNCTION_ID = 256 " 
		   + " and tick.LOC_DET_ID is not null " 
		   + " order by tick.TICKET_ID desc	 " 
		   + " if (@CubicleCode = @TCubicleCode) " 
		   + " begin " 
		   + " SET NOCOUNT ON; " 
			
		   + " 			UPDATE CC_EMPLOYEE_MAPPING  " 
		   + " SET IC_LOC_DET_ID = @LocDetID, " 
		   + " IC_LOC_DET_ID_MODIFIED_DATE = GETDATE() " 
		   + " 			WHERE EMPLOYEE_ID = @Emp  " 
		   + " and CUBICLE_CODE = @CubicleCode " 
		   + " and IS_ACTIVE = 1; end  end " 
		   + " select @Emp EMPLOYEE_ID, " 
		   + " CASE WHEN @CubicleCode = ISNULL(@TCubicleCode,@CubicleCode) THEN @CubicleCode ELSE '' END CUBICLE_CODE_NUMBER, @LocDetID LOC_DET_ID, map.LOCATION_ID LOCATION_ID, map.BUILDING BUILDING from IC_TEMP_TABLE_FOR_ICONNECT_CAMPUSCRAWLER_MAPPING map join IC_LOCATION_DETAILS loc on loc.LOC_DET_ID = @LocDetID and loc.IS_ACTIVE = 1 where map.LOC_DET_ID = @LocDetID;";
	   
	   public static final String IC_SU_INSERT_EMP_MAPPING = " DECLARE @CubicleCode Varchar(20) = ?, "
		   +" @EmpID Varchar(20) = ?, "
		   +" @ICLocDetID int = ?, "
		   +" @ProjID Varchar(20) = ?"
		   +" IF EXISTS (SELECT 1 FROM CC_EMPLOYEE_MAPPING WHERE CUBICLE_CODE = @CubicleCode and EMPLOYEE_ID = @EmpID and IS_ACTIVE = 1) BEGIN"
		   +" IF EXISTS (SELECT 1 FROM CC_EMPLOYEE_MAPPING WHERE CUBICLE_CODE = @CubicleCode and EMPLOYEE_ID = @EmpID and IS_ACTIVE = 1 AND ISNULL(IC_LOC_DET_ID,0) <> @ICLocDetID) "
		   +" BEGIN update emap "
		   +" set IC_LOC_DET_ID = @ICLocDetID, "
		   +" IC_LOC_DET_ID_MODIFIED_DATE = GETDATE(), "
		   +" PROJECT_ID = @ProjID "
		   +" FROM CC_EMPLOYEE_MAPPING emap "
		   +" WHERE CUBICLE_CODE = @CubicleCode " 
		   +" and EMPLOYEE_ID = @EmpID " 
		   +" and IS_ACTIVE = 1 	AND ISNULL(IC_LOC_DET_ID,0) <> @ICLocDetID "
		   +" END END  ELSE  BEGIN "
		   +" UPDATE  CC_EMPLOYEE_MAPPING "
		   +" SET IS_ACTIVE = 0,  VERIFIED_STATUS = 'Cancelled', "
		   +" MODIFIED_BY = @EmpID, 	MODIFIED_DATE = GETDATE() "
		   +" WHERE EMPLOYEE_ID = @EmpID 	AND VERIFIED_STATUS = 'Sent for Verification' "
		   +" AND IS_ACTIVE = 1 "	
		   +" insert into CC_EMPLOYEE_MAPPING(POSITION_ID,CUBICLE_CODE,EMPLOYEE_ID,CC_SOURCE,CREATED_BY,VERIFIED_STATUS,IC_LOC_DET_ID,IC_LOC_DET_ID_MODIFIED_DATE,PROJECT_ID) "
		   +" select pos.POSITION_ID,@CubicleCode,@EmpID,'IConnect',@EmpID,'Sent for Verification',@ICLocDetID,GETDATE(),@ProjID "
		   +" from CC_POSITION_MASTER pos "
		   +" where pos.CUBICLE_CODE = @CubicleCode 	and pos.IS_ACTIVE = 1"	
		   +" END ";
	   
	   public static final String IC_SU_LOCATION_CCODE_FORMAT=" SELECT TOP (1) " +
		" COUNTRY.LOCATION_DETAIL_ID AS COUNTRY_ID,CITY.CUBICLE_CODE AS CITYCC,CITY.LOCATION_DETAIL_ID AS CITYLD" +
		",AREA.CUBICLE_CODE AS AREACC,AREA.LOCATION_DETAIL_ID AS AREALD,BUILDING.CUBICLE_CODE AS BUILDINGCC" +
		",BUILDING.LOCATION_DETAIL_ID AS BUILDINGLD,FLOOR.LOCATION_DETAIL_ID AS FLOORLD" +
		",FLOOR.CUBICLE_CODE AS FLOORCC,WING.CUBICLE_CODE AS WINGCC,WING.LOCATION_DETAIL_ID AS WINGLD " +
		" FROM IC_SU_LOCATION_DETAILS COUNTRY " +
		" INNER JOIN IC_SU_LOCATION_DETAILS CITY ON CITY.CUBICLE_CODE = ? AND COUNTRY.IS_ACTIVE='A' " +
		" AND CITY.REFERENCE_ID=COUNTRY.LOCATION_DETAIL_ID " +
		" LEFT OUTER JOIN IC_SU_LOCATION_DETAILS AREA ON AREA.CUBICLE_CODE = ? " +
		" AND AREA.IS_ACTIVE='A' AND AREA.REFERENCE_ID=CITY.LOCATION_DETAIL_ID " +
		" INNER JOIN IC_SU_LOCATION_DETAILS BUILDING ON BUILDING.CUBICLE_CODE = ? " +
		" AND BUILDING.IS_ACTIVE='A' AND BUILDING.REFERENCE_ID=AREA.LOCATION_DETAIL_ID " +
		" LEFT OUTER JOIN IC_SU_LOCATION_DETAILS FLOOR ON FLOOR.CUBICLE_CODE = ? " +
		" AND FLOOR.IS_ACTIVE='A' AND FLOOR.REFERENCE_ID=BUILDING.LOCATION_DETAIL_ID " +
		" LEFT OUTER JOIN IC_SU_LOCATION_DETAILS WING ON WING.CUBICLE_CODE =? AND " +
		" WING.IS_ACTIVE='A' AND WING.REFERENCE_ID=FLOOR.LOCATION_DETAIL_ID ";
	   
	   public static final String IC_CHECK_VALID_CUBICCODE="select COUNT(1) 'Output'  from CC_POSITION_MASTER pos where " 
		   +" pos.CUBICLE_CODE = UPPER(?) and pos.IS_ACTIVE = 1";
		   
		   public static final String UPDATE_IHD_TICKET_DETAILS = "UPDATE IC_IHD_TICKET_DETAILS SET WORKFLOW_STATE=? where TICKET_ID=?";

		// L2 # 1175 Gamification
		public static final String IC_INSERT_GAMIFICATION_LOG = "insert into IC_GAMIFICATION_log(TICKET_ID,MESSAGE,CREATED_BY,CREATED_DATE) values(?,?,?,GETDATE())";
		
		public static final String IC_IHD_TICKET_STATUS_CHECK="select COUNT(td.TICKET_ID) from IC_IHD_TICKET_DETAILS TD JOIN IC_WORKFLOW_STATE_MASTER WSM ON WSM.STATE_ID=TD.WORKFLOW_STATE " 
				+" where TD.TICKET_ID=? AND  "
+" WSM.NAME NOT IN ('Cancelled','Resolved/Closed','Closed(By System)','Closed(By User)','AutoClosed(By System)','Withdraw') and ASSIGNED_TO='OrchEngg1'";
		
		
		public static final String GET_ENGINEER_STATUS="Select COUNT(*) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=? and DATE_TIME>? and DATE_TIME<?";
		
		public static final String CLOSE_ORCH_TICKETS="update IC_IHD_TICKET_DETAILS set WORKFLOW_STATE=?,RESOLUTION_COMMENTS=?,MODIFIED_BY='SYSTEM', MODIFIED_DATE=GETDATE(), CLOSED_DATE=GETUTCDATE(), SLA_STATUS=? where TICKET_ID=?";
		
		public static final String IC_ORCH_AUTOMATION_LOG="insert into IC_ORCH_AUTOMATION_LOG (TICKET_ID,ITERATION_COUNT,DATA_SENT,LOG_MESSAGE,CREATED_BY,CREATED_DATE) values (?,?,?,?,?,GETDATE())";
		
		public static final String IC_REOPEN_TICKETS="UPDATE IC_IHD_TICKET_DETAILS SET SLA_STATUS='NA',VERSION_NO=?,WORKFLOW_STATE= '16' ,ASSIGNED_TO= NULL,ASSIGNED_GROUP = NULL , MODIFIED_BY= ?,MODIFIED_DATE=? WHERE TICKET_ID=?";
		
		public static final String IC_IHD_TICKET_DETAILS_MANAGERID="select MANAGER_ID from IC_IHD_TICKET_DETAILS where TICKET_ID=?";
		
		public static final String IC_ORCH_ODC_ID="  select omd.ORCH_ODC_ID from IC_PROJECT_MASTER pm " +
				" join IC_ORCH_ODC_MAPPING_DETAILS omd on pm.OWNING_BU_ID=omd.OWNING_BU_ID and pm.STATUS='1' and omd.IS_ACTIVE=1 " +
				" where pm.PROJECT_ID=? and omd.CATEGORY_ID=? ";
		
		public static final String IC_CATEGORY_PRIORITY_LIST="select CM.RECOMMENDED_PRIORITY,ipm.DESCRIPTION from IC_IHD_CATEGORY_MASTER CM join IC_PRIORITY_MASTER ipm on ipm.PRIORITY_ID=CM.RECOMMENDED_PRIORITY where CM.CATEGORY_ID=? and CM.IS_ACTIVE=1 and ipm.IS_ACTIVE=1";
}
