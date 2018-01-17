/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.constants;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class COMMON_ListPageSQLQueryConstants {

	public static final String IC_SAVE_ASSIGN = "UPDATE IC_IHD_TICKET_DETAILS SET ASSIGNED_TO=?,ASSIGNED_GROUP=?,MODIFIED_BY=?,MODIFIED_DATE=?  WHERE TICKET_ID=?";
	/*Auto Assignment:Changed By Poovamma(716302) : Display Score instead of Workload*/
	public static final String IC_ENGINEER_WORKLOAD="Select iud.NAME as NAME, gmd.MEMBER_ID as ASSIGNED_TO ,ies.LOW_SCORE as low,ies.MEDIUM_SCORE as medium," +
			"	ies.HIGH_SCORE as high, ies.TOTAL_SCORE as total" +
			"	FROM IC_IHD_ENGINEER_SCORE ies, IC_USER_DETAILS iud,IC_IHD_GROUP_MEMBER_DETAILS gmd" +
			"	Where ies.EMPLOYEE_ID=iud.EMPLOYEE_ID" +
			"	and iud.IS_ACTIVE=1" +
			"	and gmd.MEMBER_ID=ies.EMPLOYEE_ID" +
			"	and gmd.IS_ACTIVE=1 and gmd.ASSIGNMENT_REQUIRED=1 " +
			"	and gmd.GROUP_ID=?";
	
	
	public static final String IC_LOGGEDIN_ENGINEER_WORKLOAD="SELECT iu.NAME as NAME,score.EMPLOYEE_ID as ASSIGNED_TO,score.HIGH_SCORE as high,score.MEDIUM_SCORE as medium,score.LOW_SCORE as low,score.TOTAL_SCORE as total FROM IC_IHD_ENGINEER_SCORE score,IC_IHD_GROUP_ROLE_MASTER grmRole ,IC_USER_DETAILS iu  WHERE score.EMPLOYEE_ID IN (" +
			"	select distinct	gmd.MEMBER_ID as member_id" +
			"	from IC_USER_AVAILABILITY_IN_OUT iua ,IC_USER_DETAILS iud,IC_IHD_GROUP_MEMBER_DETAILS gmd," +
			"	IC_IHD_GROUP_MASTER igm,IC_IHD_GROUP_ROLE_MASTER grm" +
			"	where" +
			"	iud.EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and iud.EMPLOYEE_ID=gmd.MEMBER_ID" +
			"	and igm.GROUP_ID=gmd.GROUP_ID" +
			"	and grmRole.ROLE_ID=gmd.ROLE_ID" +
			"	and iu.EMPLOYEE_ID=iud.EMPLOYEE_ID" +
			"	and grm.ROLE_ID=gmd.ROLE_ID " +
			"	and iua.STATUS=case" +
			"	when (select STATUS FROM IC_USER_AVAILABILITY_IN_OUT where DATE_TIME =" +
			"	(select MAX(DATE_TIME) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID ) and EMPLOYEE_ID=iua.EMPLOYEE_ID  )='IN' then 'IN'" +
			"	when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN')=" +
			"	(select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'" +
			"	)" +
			"	) then 'OUT'" +
			"	when ((select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='IN'" +
			"	)<" +
			"	(select COUNT(Status) from IC_USER_AVAILABILITY_IN_OUT where EMPLOYEE_ID=iua.EMPLOYEE_ID" +
			"	and DATE_TIME>DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) and STATUS='OUT'" +
			"	)" +
			"	) then 'OUT'" +
			"	else" +
			"	'IN'" +
			"	end" +
			"	and iua.STATUS='IN'  and gmd.IS_ACTIVE=1 and gmd.ASSIGNMENT_REQUIRED=1 " +
			"	and gmd.GROUP_ID=?)";
	
	
	public static final String IC_IS_AUTO_ASSIGNMENT_REQD="SELECT cast(IS_AUTO_ASSIGNMENT_REQUIRED  as int) FROM IC_IHD_GROUP_MASTER WHERE GROUP_ID=?";
	/*END: Changed By Poovamma(716302) :Auto Assignment: Display Score instead of Workload*/
	public static final String IC_DISPLAY_ENGINEER_WORKLOAD = "select tb.CATEGORY_ID,tb.CATEGORY,count(tb.low) as low,count(tb.medium) as medium,count(tb.high) as high  from (select "
			+ " td.TICKET_ID as low,"
			+ " null as medium,"
			+ " null as high,"
			+ " cm2.NAME+'-'+cm1.NAME+'-'+cm.NAME CATEGORY,"
			+ " cm.CATEGORY_ID,"
			+ " td.ASSIGNED_TO"
			+ " from "
			+ " (((IC_IHD_TICKET_DETAILS td left outer join  IC_IHD_CATEGORY_MASTER cm "
			+ "on td.SUB_CATEGORY_ID=cm.CATEGORY_ID) left outer join  IC_PRIORITY_MASTER pm "
			+ "on pm.PRIORITY_ID=td.PRIORITY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm1 "
			+ "on cm1.CATEGORY_ID=td.CATEGORY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm2 "
			+ "on cm2.CATEGORY_ID=td.FUNCTION_ID left outer join IC_WORKFLOW_STATE_MASTER st on td.WORKFLOW_STATE=st.STATE_ID "
			+ "where pm.PRIORITY_ID=1 and st.NAME not in('Cancelled','Resolved/Closed','Closed(By System)','AutoClosed(By System)','Withdraw')"
			+ " union"
			+ " select "
			+ " null as low,"
			+ " td.TICKET_ID as medium,"
			+ " null as high,"
			+ " cm2.NAME+'-'+cm1.NAME+'-'+cm.NAME CATEGORY,"
			+ " cm.CATEGORY_ID,"
			+ " td.ASSIGNED_TO"
			+ " from "
			+ " (((IC_IHD_TICKET_DETAILS td left outer join  IC_IHD_CATEGORY_MASTER cm "
			+ "on td.SUB_CATEGORY_ID=cm.CATEGORY_ID) left outer join  IC_PRIORITY_MASTER pm "
			+ "on pm.PRIORITY_ID=td.PRIORITY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm1 "
			+ "on cm1.CATEGORY_ID=td.CATEGORY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm2 "
			+ "on cm2.CATEGORY_ID=td.FUNCTION_ID left outer join IC_WORKFLOW_STATE_MASTER st on td.WORKFLOW_STATE=st.STATE_ID "
			+ "where pm.PRIORITY_ID=2 and st.NAME not in('Cancelled','Resolved/Closed','Closed(By System)','AutoClosed(By System)','Withdraw')"
			+ " union"
			+ " select "
			+ " null as low,"
			+ " null as medium,"
			+ " td.TICKET_ID as high,"
			+ " cm2.NAME+'-'+cm1.NAME+'-'+cm.NAME CATEGORY,"
			+ " cm.CATEGORY_ID,"
			+ " td.ASSIGNED_TO"
			+ " from "
			+ " (((IC_IHD_TICKET_DETAILS td left outer join  IC_IHD_CATEGORY_MASTER cm "
			+ "on td.SUB_CATEGORY_ID=cm.CATEGORY_ID) left outer join  IC_PRIORITY_MASTER pm "
			+ "on pm.PRIORITY_ID=td.PRIORITY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm1 "
			+ "on cm1.CATEGORY_ID=td.CATEGORY_ID) left outer join  IC_IHD_CATEGORY_MASTER cm2 "
			+ "on cm2.CATEGORY_ID=td.FUNCTION_ID left outer join IC_WORKFLOW_STATE_MASTER st on td.WORKFLOW_STATE=st.STATE_ID "
			+ "where pm.PRIORITY_ID=3 and st.NAME not in('Cancelled','Resolved/Closed','Closed(By System)','AutoClosed(By System)','Withdraw')"
			+ " )tb where tb.ASSIGNED_TO=? group by tb.CATEGORY_ID,tb.CATEGORY";

	public static final String relatedMailsQuery = "SELECT MAIL_ID,TO_ADDRESS,FROM_ADDRESS,SUBJECT,DESCRIPTION,ATTACHMENT_PATH,SEVERITY_ID,STATUS,RECEIVED_DATE,SOURCE,MT.REFERENCE_ID,REASON,MT.CREATED_BY,MT.CREATED_DATE,ATTACHMENT_NAME,case when (select distinct MAIL_ID from IC_LOCKED_TICKET_DETAILS ltd where ltd.REFERENCE_ID=CAST(MAIL_ID AS VARCHAR(10))) is not null then 'Yes' else 'No' END as 'LOCK',ISNULL(lockUser.NAME+'('+lockUser.EMPLOYEE_ID+')','-') AS 'LOCKED_BY',IS_PREMIUM_MAIL FROM IC_MAIL_TRACKER_DETAILS MT LEFT OUTER JOIN IC_LOCKED_TICKET_DETAILS AS lock ON lock.REFERENCE_ID=CAST(MAIL_ID AS VARCHAR(10)) LEFT OUTER JOIN IC_USER_DETAILS AS lockUser ON lock.LOCKED_BY = lockUser.EMPLOYEE_ID WHERE MT.REFERENCE_ID='";

	//Added to implement the tool to track EX employees quereis for HR function
	public static final String relatedMailsQuery_HR = "SELECT MAIL_ID,TO_ADDRESS,FROM_ADDRESS,SUBJECT,DESCRIPTION,ATTACHMENT_PATH,SEVERITY_ID,STATUS,RECEIVED_DATE,SOURCE,MT.REFERENCE_ID,REASON,MT.CREATED_BY,MT.CREATED_DATE,ATTACHMENT_NAME,case when (select distinct MAIL_ID from IC_LOCKED_TICKET_DETAILS ltd where ltd.REFERENCE_ID=CAST(MAIL_ID AS VARCHAR(10))) is not null then 'Yes' else 'No' END as 'LOCK',ISNULL(lockUser.NAME+'('+lockUser.EMPLOYEE_ID+')','-') AS 'LOCKED_BY',IS_PREMIUM_MAIL FROM IC_HR_MAIL_TRACKER_DETAILS MT LEFT OUTER JOIN IC_LOCKED_TICKET_DETAILS AS lock ON lock.REFERENCE_ID=CAST(MAIL_ID AS VARCHAR(10)) LEFT OUTER JOIN IC_USER_DETAILS AS lockUser ON lock.LOCKED_BY = lockUser.EMPLOYEE_ID WHERE MT.REFERENCE_ID='";

	private static Map<String, String> helpDeskQueryMapping = new HashMap<String, String>();

	private static Map<String, String> techCRQueryMapping = new HashMap<String, String>();

	private static Map<String, String> lockedRequestQueryMapping = new HashMap<String, String>();

	private static Map<String, String> mailtrackerQueryMapping = new HashMap<String, String>();

	private static Map<String, String> userAvailabilityQueryMapping = new HashMap<String, String>();
	
	private static Map<String,String> userLoginDetailQueryMapping=new HashMap<String, String>();
	
	/*Auto Assignment*/
	private static Map<String,String> engineerScoreQueryMapping=new HashMap<String,String>();
	
	private static Map<String,String> engineerDetScoreQueryMapping=new HashMap<String,String>();
	/*Auto Assignment*/
	
	/*Admin Console*/
	private static Map<String,String> adminConsoleGrpQueryMapping=new HashMap<String,String>();
	private static Map<String,String> adminConsoleCategoryMapping=new HashMap<String,String>();
	/*Admin Console*/
	
	public static Map<String, String> getHelpDeskQueryMapping() {
		if (helpDeskQueryMapping.size() == 0) {
			helpDeskQueryMapping.put("TICKET_ID", "ticket.TICKET_ID");
			helpDeskQueryMapping.put("LOCATION_AREA", "loc.CITY,ld.BUILDING");
			helpDeskQueryMapping.put("CREATED_DATE", "ticket.CREATED_DATE");
			helpDeskQueryMapping.put("FUNCTION", "ihdfunction.NAME");
			helpDeskQueryMapping.put("SUBJECT", "ticket.SUBJECT");
			helpDeskQueryMapping.put("ASSIGNED_TO",
					"assignedUsr.NAME,assignedUsr.EMPLOYEE_ID");
			helpDeskQueryMapping.put("WORKFLOW_STATE", "workflow.NAME");
			helpDeskQueryMapping.put("ECT", "ticket.ECT");
			helpDeskQueryMapping.put("SUBCATEGORY", "ihdsubcategory.NAME");
			helpDeskQueryMapping.put("CATEGORY", "ihdcategory.NAME");
			helpDeskQueryMapping.put("PRIORITY", "PM.DESCRIPTION");
			helpDeskQueryMapping.put("REQUESTOR_NAME",
					"usr.NAME,usr.EMPLOYEE_ID");
			helpDeskQueryMapping.put("ORGANIZATION", "usr.ORGANIZATION");
			helpDeskQueryMapping.put("SLA_STATUS", "ticket.SLA_STATUS");
			helpDeskQueryMapping.put("CLOSED_DATE", "ticket.CLOSED_DATE");
			helpDeskQueryMapping.put("DESCRIPTION", "ticket.DESCRIPTION");
			helpDeskQueryMapping.put("EXCEPTION_APP_ST_DATE", "approveId.EXCEPTION_START_DATE");
            helpDeskQueryMapping.put("EXCEPTION_APP_END_DATE", "approveId.EXCEPTION_END_DATE");
            helpDeskQueryMapping.put("ACTIVITY_TYPE", "FSF.FIELD_VALUE");

		}
		return helpDeskQueryMapping;
	}

	public static Map<String, String> getTechCRQueryMapping() {
		if (techCRQueryMapping.size() == 0) {
			techCRQueryMapping.put("ISSUE_ID", "tcrDetails.ISSUE_ID");
			techCRQueryMapping.put("LOCATION_NAME", "location.SHORT_NAME");
			techCRQueryMapping.put("ACTION_DESC", "actionMaster.DESCRIPTION");
			techCRQueryMapping.put("GROUP_NAME", "groupMaster.NAME");
			techCRQueryMapping.put("ASSIGNED_EMPIDANDNAME",
					"usr.NAME,usr.EMPLOYEE_ID");
			techCRQueryMapping.put("PARENT_CATEGORY_NAME", "catMaster.NAME");
			techCRQueryMapping.put("CHILD_CATEGORY_NAME", "subCatMaster.NAME");
			techCRQueryMapping.put("SCHEDULED_START_DATE",
					"tcrDetails.SCHEDULED_START_DATE");
			techCRQueryMapping.put("SCHEDULED_END_DATE",
					"tcrDetails.SCHEDULED_END_DATE");
			techCRQueryMapping.put("SOURCE", "tcrDetails.SOURCE");
			techCRQueryMapping.put("SUBJECT", "tcrDetails.SUBJECT");
			techCRQueryMapping.put("DESCRIPTION", "tcrDetails.DESCRIPTION");
			techCRQueryMapping.put("SEVERITY_NAME",
					"severityMaster.DESCRIPTION");
			techCRQueryMapping.put("IS_CLIENT_INITIATED",
					"tcrDetails.IS_CLIENT_INITIATED");
			techCRQueryMapping.put("EFFORT", "tcrDetails.EFFORT");
			techCRQueryMapping.put("ROLLBACK_COMMENTS",
					"tcrDetails.ROLLBACK_COMMENTS");
			techCRQueryMapping.put("ENGINEER COMMENTS",
					"tcrDetails.ENGINEER_COMMENTS");
			techCRQueryMapping.put("RISK_INVOLVED", "tcrDetails.RISK_INVOLVED");
			techCRQueryMapping.put("RCA_REQUIRED", "tcrDetails.RCA_REQUIRED");
			techCRQueryMapping.put("CREATED_BY",
					"creationUser.NAME,creationUser.EMPLOYEE_ID");
			techCRQueryMapping.put("CREATION_DATE", "tcrDetails.CREATED_DATE");
			techCRQueryMapping.put("WORKFLOW_STATE", "stateMaster.NAME");
			techCRQueryMapping.put("WORKFLOW_STATE_NAME", "stateMaster.NAME");
			techCRQueryMapping.put("APPROVER_EMPIDANDNAME",
					"approverUser.NAME,approverUser.EMPLOYEE_ID");

		}
		return techCRQueryMapping;
	}

	public static Map<String, String> getLockedQueryMapping() {
		if (lockedRequestQueryMapping.size() == 0) {
			lockedRequestQueryMapping.put("TICKET_ID", "LTD.REFERENCE_ID");
			lockedRequestQueryMapping.put("EMPLOYEE_NAME",
					"IUD.NAME,LTD.LOCKED_BY");
			lockedRequestQueryMapping.put("LOCKED_DATE", "LTD.LOCKED_DATE");	
		}
		return lockedRequestQueryMapping;
	}

	public static Map<String, String> getmailtrackerQueryMapping() {
		if (mailtrackerQueryMapping.size() == 0) {
			mailtrackerQueryMapping.put("FROM_ADDRESS", "FROM_ADDRESS");
			mailtrackerQueryMapping.put("SUBJECT", "SUBJECT");
			mailtrackerQueryMapping.put("DESCRIPTION", "DESCRIPTION");
			mailtrackerQueryMapping.put("RECEIVED_DATE", "RECEIVED_DATE");
			mailtrackerQueryMapping.put("REFERENCE_ID", "MT.REFERENCE_ID");
			mailtrackerQueryMapping.put("MAIL_ID", "MAIL_ID");
			mailtrackerQueryMapping.put("LOCKED_BY", "LOCKED_BY");
			mailtrackerQueryMapping.put("LOCKED_DATE", "LOCKED_DATE");
			//following line added by gk820877 on 5/12/2015
			mailtrackerQueryMapping.put("SOURCE", "SOURCE");
		}
		return mailtrackerQueryMapping;
	}

	public static Map<String, String> getUserAvailabilityQueryMapping() {
		if (userAvailabilityQueryMapping.size() == 0) {
			userAvailabilityQueryMapping.put("STATUS", "DATA.STATUS");
			userAvailabilityQueryMapping.put("EMPLOYEE_NAME",
					"DATA.EMPLOYEE_NAME");
		}
		return userAvailabilityQueryMapping;
	}
	/*Auto Assignment*/
	public static Map<String,String> getEngineerScoreQueryMapping(){
		if(engineerScoreQueryMapping.size() ==0){
			engineerScoreQueryMapping.put("EMPLOYEE_NAME", "DATA.EMPLOYEE_NAME");
			engineerScoreQueryMapping.put("HIGH_SCORE", "HIGH_SCORE");
			engineerScoreQueryMapping.put("MEDIUM_SCORE", "MEDIUM_SCORE");
			engineerScoreQueryMapping.put("LOW_SCORE", "LOW_SCORE");
			engineerScoreQueryMapping.put("TOTAL_SCORE", "TOTAL_SCORE");
		}
		return engineerScoreQueryMapping;
	}
	/*Auto Assignment*/
	public static Map<String, String> getUserAvailabilityLoginOutQueryMapping() {
		if (userLoginDetailQueryMapping.size() == 0) {
			userLoginDetailQueryMapping.put("STATUS", "STATUS");
			userLoginDetailQueryMapping.put("DATE_TIME", "DATE_TIME");
			userLoginDetailQueryMapping.put("EMPLOYEE_NAME","EMPLOYEE_NAME");			
		}
		return userLoginDetailQueryMapping;
	}
	/*Auto Assignment*/
	public static Map<String,String> getEngineerScoreDetailQueryMapping(){
		if (engineerDetScoreQueryMapping.size() == 0){
			engineerDetScoreQueryMapping.put("EMPLOYEE_ID", "EMPLOYEE_ID");
			engineerDetScoreQueryMapping.put("DATA_CHANGE", "DATA_CHANGE");
			engineerDetScoreQueryMapping.put("WEIGHTAGE", "WEIGHTAGE");
			engineerDetScoreQueryMapping.put("TICKET_ID", "TICKET_ID");
			engineerDetScoreQueryMapping.put("CREATED_BY", "CREATED_BY");
		}
		return engineerDetScoreQueryMapping;
	}
	/*Auto Assignment*/
	
	/*Admin Console*/
	public static Map<String,String> getGrpDetailQueryMapping(){
		if (adminConsoleGrpQueryMapping.size() == 0){
			adminConsoleGrpQueryMapping.put("CATEGORY_NAME", "CATEGORY_NAME");
			adminConsoleGrpQueryMapping.put("SUB_CATEGORY_NAME", "SUB_CATEGORY_NAME");
			adminConsoleGrpQueryMapping.put("GROUP_ID", "GROUP_ID");
			adminConsoleGrpQueryMapping.put("GROUP_NAME", "GROUP_NAME");
		
		}
		return adminConsoleGrpQueryMapping;
	}
	
	
	public static  Map<String,String> getCategoryMapping(){
		if (adminConsoleCategoryMapping.size() == 0){
			adminConsoleCategoryMapping.put("FUNCTION_ID", "funcat.CATEGORY_ID");
			adminConsoleCategoryMapping.put("FUNCTION_NAME","funcat.NAME");
			adminConsoleCategoryMapping.put("CATEGORY_ID","parentcat.CATEGORY_ID");
			adminConsoleCategoryMapping.put("CATEGORY_NAME","parentcat.name");		
			adminConsoleCategoryMapping.put("CATEGORY_STATUS","parentcat.IS_ACTIVE");	
			adminConsoleCategoryMapping.put("SUBCATEGORY_ID","subcat.CATEGORY_ID");
			adminConsoleCategoryMapping.put("SUBCATEGORY_NAME","subcat.NAME");
			adminConsoleCategoryMapping.put("SUBCATEGORY_STATUS","subcat.IS_ACTIVE");			
			adminConsoleCategoryMapping.put("RECOMMENDED_PRIORITY","pm.description");
			adminConsoleCategoryMapping.put("IS_CHANGE_REQUEST","subcat.IS_CHANGE_REQUEST");
			adminConsoleCategoryMapping.put("LINK","subcat.LINK");//aDDED BY sALI
		}
		return adminConsoleCategoryMapping;
	}
	
	private static Map<String,String> adminConsoleLocationMapping=new HashMap<String,String>();
	
	
	public static  Map<String,String> getLocationMapping(){
		if (adminConsoleLocationMapping.size() == 0){
			adminConsoleLocationMapping.put("LOCATION_ID", "locdetail.LOCATION_ID");
			adminConsoleLocationMapping.put("CITY", "locmaster.CITY");
			adminConsoleLocationMapping.put("BUILDING", "locdetail.BUILDING");
			adminConsoleLocationMapping.put("FLOOR","locdetail.FLOOR");
			adminConsoleLocationMapping.put("LOCDETID","locdetail.LOC_DET_ID");
			adminConsoleLocationMapping.put("STATUS","STATUS");

		}
		return adminConsoleLocationMapping;
	}
	
	
	
	/*Admin Console*/

    public static final String IC_USER_SELECTION_LISTPAGE_LIST_SQL = "SELECT COLUMN_ID,DISPLAY_ORDER,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE from IC_USER_SELECTION_LISTPAGE WHERE IS_ACTIVE=1 AND MENU_ID=? AND EMPLOYEE_ID=? AND ROLE_ID=? order by DISPLAY_ORDER";
    
    public static final String IC_USER_SELECTION_LISTPAGE_INSERT = "INSERT INTO IC_USER_SELECTION_LISTPAGE(COLUMN_ID,DISPLAY_ORDER,MENU_ID,EMPLOYEE_ID,ROLE_ID,IS_ACTIVE,CREATED_BY,CREATED_DATE) VALUES(?,?,?,?,?,1,?,getDate())";
    
    public static final String IC_USER_SELECTION_LISTPAGE_UPDATE = "UPDATE IC_USER_SELECTION_LISTPAGE SET DISPLAY_ORDER=? WHERE COLUMN_ID=? AND MENU_ID=? AND EMPLOYEE_ID=? AND ROLE_ID=?";
    
    public static final String IC_USER_SELECTION_LISTPAGE_DELETE = "DELETE IC_USER_SELECTION_LISTPAGE WHERE COLUMN_ID=? AND MENU_ID=? AND EMPLOYEE_ID=? AND ROLE_ID=?";
    
    public static final int IC_USER_SELECTION_LISTPAGE_INSERT_ARGTYPES[] = { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.VARCHAR};
    
    public static final int IC_USER_SELECTION_LISTPAGE_UPDATE_ARGTYPES[] = { Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
    
    public static final int IC_USER_SELECTION_LISTPAGE_DELETE_ARGTYPES[] = { Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
    
    
    

}
