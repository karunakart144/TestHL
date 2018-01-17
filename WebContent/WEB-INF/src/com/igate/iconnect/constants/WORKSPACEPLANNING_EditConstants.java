package com.igate.iconnect.constants;

public class WORKSPACEPLANNING_EditConstants {


	
	public static final String GET_REQUEST_DETAILS =  "  Select distinct iwmrd.MAIN_REQUEST_ID, iwmrd.CREATED_BY AS REQUESTER_ID, usr.NAME AS REQUESTER_NAME, iwmrd.NEW_SPACE_REQUIREMENT_FLAG,iwmrd.NEW_SPACE_OR_EXPANSION_FLAG,   " + 
	 " 	  iwmrd.ODC_FLAG,isosm.L6_SBU_NAME as SBU_NAME,iwmrd.SUB_BUSINESS_UNIT_ID as SUB_BUSINESS_UNIT_ID,   " + 
	 " 	  iwmrd.OPPORTUNITY_ID,ipm.NAME as NAME,ipm.NAME as PROJECT_NAME,iwmrd.PROJECT_ID as PROJECT_ID,ISNULL(ipm.CUSTOMER_NAME, iwmrd.CUSTOMER_FOR_OPPORTUNITY) AS CUSTOMER_NAME,iwmrd.CUSTOMER_ID,   " + 
	 " 	  iwmrd.REQUESTED_QUARTER,iwmrd.REQUESTED_YEAR,iwmrd.REQUESTED_MONTH,iwmrd.STATE_ID,wsm.NAME as STATE_NAME,wsm.STATE_ID as STATE_ID, " + 
	 " 	  iwmrd.TOTAL_SEATS_REQUESTED,iwmrd.TOTAL_SEATS_PROVIDED,iwmrd.DATE_OF_REQUISITION as DATE   " + 
	 " 	  ,iwmrd.CREATED_BY,iwmrd.CREATED_DATE,iwmrd.REQUESTOR_COMMENTS,iwmrd.DELIVERY_HEAD_COMMENTS,iwmrd.DEPT_HEAD_COMMENTS   " + 
	 " 	  ,iwmrd.REQUESTED_MONTH + ' - ' + iwmrd.REQUESTED_YEAR AS REQUESTED_QTR_MONTH, iwmrd.DELIVERY_HEAD_ID, iwmrd.CUSTOMER_FOR_OPPORTUNITY , iwmrd.SPACE_PLANNING_TEAM_COMMENTS,  " + 
	 " 	     " + 
	 " 	  case when iwmrd.STATE_ID=301 and DEPT_HEAD_ID=iud.EMPLOYEE_ID then 'TRUE'    " + 
	 " 	         when iwmrd.STATE_ID=295 and DELIVERY_HEAD_ID=iud.EMPLOYEE_ID then 'TRUE'   " + 
	 " 	        when iwmrd.STATE_ID in (297,298,299) and (Select COUNT(EMPLOYEE_ID) FROM IC_USER_ROLE_DETAILS WHERE ROLE_ID=40 and EMPLOYEE_ID=iud.EMPLOYEE_ID )>0 then 'TRUE'   " + 
	 " 	        when iwmrd.STATE_ID=296 and iwmrd.CREATED_BY=iud.EMPLOYEE_ID then 'TRUE'   " + 
	 " 	        else 'FALSE' end as 'IS_EDITABLE'   " + 
	 " 	     " + 
	 " 	  from IC_WP_MAIN_REQUEST_DETAIL iwmrd  " + 
	 " 	  inner join IC_USER_DETAILS usr on usr.EMPLOYEE_ID = iwmrd.CREATED_BY " + 
	 " 	  left outer join IC_SU_ORG_SBU_MASTER isosm on iwmrd.SUB_BUSINESS_UNIT_ID = isosm.SBU_ID   " + 
	 " 	  left outer join IC_PROJECT_MASTER ipm on iwmrd.PROJECT_ID=ipm.PROJECT_ID   " + 
	 " 	  and iwmrd.CUSTOMER_ID = ipm.CUSTOMER_ID   " + 
	 " 	  left outer join IC_WORKFLOW_STATE_MASTER wsm on wsm.STATE_ID=iwmrd.STATE_ID   " + 
	 " 	  left outer join IC_USER_DETAILS iud on iud.EMPLOYEE_ID = ? " + 
	 " 	  where iwmrd.MAIN_REQUEST_ID = ? " ;
	
	
	
	
	
	public static final String GET_BUILDING_DETAILS="Select LOCATION_DETAIL_ID,NAME  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure" +
			" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID and IS_ACTIVE='A' and locDet.REFERENCE_ID = ?";
	
	
	public static final  String GET_AREA_VALUE_DETAILS="Select distinct locDet.LOCATION_DETAIL_ID,NAME,VALUE  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure, IC_SU_SEATING_DETAIL seat			 Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID"
		+	 " and suStructure.STRUCTURE_NAME= ? and IS_ACTIVE='A' and locDet.REFERENCE_ID = ?"
		+		" and seat.LOCATION_DETAIL_ID=locDet.LOCATION_DETAIL_ID and seat.DB_COLUMN_ID=15	";
	

	 public static final String GET_LOCATION_DETAILS="Select LOCATION_DETAIL_ID,NAME  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure" +
				" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID" +
				" and suStructure.STRUCTURE_NAME= ? and IS_ACTIVE='A' and locDet.REFERENCE_ID = ?";
	 
	 public static final String GET_BUILDINGS_ON_CITY = " Select LOCATION_DETAIL_ID,NAME  from CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure" +
	 		" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID " +
	 		" and suStructure.STRUCTURE_NAME='Building' and IS_ACTIVE='A' and locDet.REFERENCE_ID in (select LOCATION_DETAIL_ID from " +
	 		" CC_LOCATION_DETAILS locDet,IC_SU_STRUCTURE suStructure " +
	 		" Where locDet.STRUCTURE_ID=suStructure.STRUCTURE_ID and locDet.REFERENCE_ID = ?) ";
	 
	 public static final String UPDATE_REQUEST_STATUS = "update IC_WP_MAIN_REQUEST_DETAIL" +
	 		" set STATE_ID= ? where MAIN_REQUEST_ID= ?";
	 
	 public static final String GET_SUB_REQUEST_STATE_DETAILS="select STATE_ID from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID = ";
	 
	 public static final String GET_COUNT_OF_OPEN_REQUESTS="SELECT ISNULL(SUM (a.STATE_ID_COUNT),0) as COUNT_OF_OPEN_REQ FROM (Select (COUNT(STATE_ID))  as STATE_ID_COUNT" +
	 		"	 from IC_WP_SUB_REQUEST_LOCATION_DETAIL where MAIN_REQUEST_ID=? and STATE_ID is not null group by STATE_ID)a "; 

	 
	 public static final String SELECT_APPROVER_COMMENTS_FLAG="" +
		"" +
		"SELECT CASE " +
	"WHEN state_id = 301 THEN 'Sent for Delivery Head Approval' " +
	" WHEN state_id = 295 THEN 'Sent to Space Planning Team' " +
	" END AS 'STATUS', " +
	"  CASE " +
	"    WHEN dept_head_id = ? THEN 'DEPT_HEAD_COMMENTS' " +
	"    WHEN delivery_head_id = ? THEN 'DELIVERY_HEAD_COMMENTS' " +
	"   END AS 'COMMENTS' " +
	"FROM   ic_wp_main_request_detail " +
	"WHERE  main_request_id = ?  " ;
	public static final String PROJ_DETAILS = "Select distinct PROJECT_ID,UPPER(a.NAME) as PROJECT_NAME ,dept.DEPT_ID ,dept.DEPT_HEAD,SBU_ID,BU_HEAD,sbuUser.NAME as BU_HEAD_NAME,deptUser.NAME as DEPT_HEAD_NAME"+
	" from IC_PROJECT_MASTER a ,"+
	" IC_SU_ORG_DEPT_MASTER dept,IC_SU_SBU_DEPARTMENT_MAPPING mpn , IC_SU_ORG_SBU_MASTER sbu,IC_USER_DETAILS deptUser,IC_USER_DETAILS sbuUser WHERE"+
	 "  STATUS=1 and START_DATE <= GETDATE()"+
	                " and END_DATE >= GETDATE() and CUSTOMER_ID in (SELECT CUSTOMER_ID FROM IC_WP_MAIN_REQUEST_DETAIL WHERE MAIN_REQUEST_ID=?)"+
	                " and dept.DEPT_ID=a.DU"+
	                " and mpn.DEPT_ID=dept.DEPT_ID"+
	                " and mpn.L6_CODE=sbu.SBU_ID  and PROJECT_ID in (SELECT PROJECT_ID FROM IC_WP_MAIN_REQUEST_DETAIL WHERE MAIN_REQUEST_ID=?)"+
	                " and sbu.BU_HEAD=sbuUser.EMPLOYEE_ID and dept.DEPT_HEAD=deptUser.EMPLOYEE_ID";
	 
}
