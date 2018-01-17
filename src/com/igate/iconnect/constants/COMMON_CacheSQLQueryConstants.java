/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.constants;

public class COMMON_CacheSQLQueryConstants {

    //public static final String IHD_CAT_LIST_SQL = "select CATEGORY_ID,NAME,LINK,PARENT_ID,IS_CHANGE_REQUEST,RECOMMENDED_PRIORITY,SPECIAL_PREFERENCES from IC_IHD_CATEGORY_MASTER WHERE IS_ACTIVE=1 ORDER BY NAME";
	
	public static final String IHD_CAT_LIST_SQL = "select CATEGORY_ID,NAME,LINK,PARENT_ID,IS_CHANGE_REQUEST,RECOMMENDED_PRIORITY,SPECIAL_PREFERENCES "
		+ "  ,AUTOMATION_REQUIRED,isnull(COMMENTS,'') 'COMMENTS' from IC_IHD_CATEGORY_MASTER WHERE IS_ACTIVE=1 order by NAME ";

    public static final String IHD_APPROVER_LIST_SQL = "select APPROVER_ID,DESCRIPTION,WORKFLOW_STATE from IC_IHD_APPROVER_MASTER WHERE IS_ACTIVE=1";

    public static final String IHD_CATEGORY_APPROVER_LIST_SQL = "select CATEGORY_ID,APPROVER_ORDER,APPROVER_ID from IC_IHD_CATEGORY_APPROVAL_DETAILS WHERE IS_ACTIVE=1";

    public static final String IHD_GROUP_LIST_SQL = "select GROUP_ID,NAME as GROUP_NAME, NAME,LEVEL,MANAGER,EMAIL_ADDRESS,LOCATION_ID from IC_IHD_GROUP_MASTER WHERE IS_ACTIVE=1 order by NAME";

    public static final String IHD_GROUP_MEMB_LIST_SQL = "select MEMBER_ID,GROUP_ID,WORK_LOAD from IC_IHD_GROUP_MEMBER_DETAILS WHERE IS_ACTIVE=1";

    public static final String IHD_SLA_LIST_SQL = "select SLA_ID,DESCRIPTION,ORGANIZATION,TIME from IC_IHD_SLA_MASTER WHERE IS_ACTIVE=1";



    
    public static final String IC_ORCH_LIST_SQL = " select distinct FIELD_ID, FIELD_NAME, FIELD_TYPE, MAS.CATEGORY_ID, IS_MANDATORY "  
    		+ " , mas.front_end_display ,mas.ORCH_ODC_ID,mas.FIELD_ORDER,cm.AUTOMATION_REQUIRED "
    					 + " from IC_ORCH_FIELD_MASTER MAS " 
    					 + " LEFT OUTER JOIN IC_ORCH_ODC_RUNBOOK_MAPPING_DETAILS CAT ON CAT.CATEGORY_ID = MAS.CATEGORY_ID and CAT.IS_ACTIVE=1 "
    					 + " join IC_ORCH_ODC_MASTER odc on odc.ORCH_ODC_ID=mas.ORCH_ODC_ID and odc.IS_ACTIVE=1 "
    					 + " join IC_IHD_CATEGORY_MASTER cm on cm.CATEGORY_ID=mas.CATEGORY_ID and cm.IS_ACTIVE=1 "
    					 + " WHERE MAS.IS_ACTIVE = 1 order by mas.FIELD_ID,mas.FIELD_ORDER ";
    
    public static final String IC_ORCH_LIST_VALUES_SQL = "select VALUE_ID, FIELD_ID, ORCH_VALUE from IC_ORCH_FIELD_VALUE_MASTER  where IS_ACTIVE =1";
    
    public static final String IC_ORCH_PRINTER_SERVER_SQL ="select LOC_DET_ID, PRINT_SERVER_DETAIL, is_odc from  IC_ORCH_LOCATION_PRINTSERVER_MAPPING where  is_active =1"; 

	//Added by anjana For Gamification L2 # 1175
    public static final String IC_GAMIFICATION_SQL= " select PARAMETER from IC_GAMIFICATION where IS_ACTIVE =1 ";
    
    public static final String IC_GAMIFICATION_SHIFT_SQL= " select SHIFT_NAME, EMPLOYEE_ID ,Shift_start_Time START_TIME  from IC_GAMIFICATION_SHIFT_DETAILS where IS_ACTIVE=1 ";
    
    //Added by Anjana For Excel Upload
    
    public static final String ISL_TABLE_NAMELIST_SQL = " SELECT '0' as id , t.name as Table_Name  FROM sys.tables  t  INNER JOIN sys.schemas   s ON t.schema_id=s.schema_id ";
    
    //End




    public static final String IC_LOCATION_LIST_SQL = "select LOCATION_ID,COUNTRY,CITY,AREA,SHORT_NAME,TIMEZONE_ID from IC_LOCATION_MASTER WHERE IS_ACTIVE=1 order by CITY";
    
    public static final String IC_COUNTRY_LIST_SQL = "Select distinct IFLM.FUNCTION_ID,ILM.COUNTRY from IC_IHD_FUNCTION_LOCATION_MAPPING IFLM , IC_LOCATION_MASTER ILM where  IFLM.LOCATION_ID=ILM.LOCATION_ID and IFLM.IS_ACTIVE=1";

    public static final String IC_LOCATION_DET_LIST_SQL = "select LOC_DET_ID,LOCATION_ID,BUILDING,FLOOR,SHORT_NAME,LAST_CUBICLE_NO from IC_LOCATION_DETAILS WHERE IS_ACTIVE=1 order by BUILDING,FLOOR";
    
   
	/*Auto Assignment*/
    public static final String IC_PRIORITY_LIST_SQL = "select PRIORITY_ID,DESCRIPTION,PRIORITY_WEIGHTAGE from IC_PRIORITY_MASTER WHERE IS_ACTIVE=1";
	/*Auto Assignment*/

    public static final String IC_ROLE_LIST_SQL = "select ROLE_ID,NAME from IC_ROLE_MASTER order by NAME";

    public static final String IC_SEVERITY_LIST_SQL = "select SEVERITY_ID,DESCRIPTION,TCR_SLA_TIME from IC_SEVERITY_MASTER WHERE IS_ACTIVE=1";


    public static final String IC_DEFAULT_ASSIGN_DETAILS_SQL = "SELECT DAD.CATEGORY_ID AS CATEGORY_ID,DAD.LOCATION_ID AS LOCATION_ID,DAD.GROUP_ID AS GROUP_ID , IGM.NAME AS GROUP_NAME FROM "
    													   	 + " IC_IHD_DEFAULT_ASSIGNMENT_DETAILS DAD,IC_IHD_GROUP_MASTER IGM where DAD.IS_ACTIVE=1 and IGM.IS_ACTIVE=1 "
    													     + " AND DAD.GROUP_ID=IGM.GROUP_ID ORDER BY CATEGORY_ID";


    public static final String IC_UIFIELDS_LIST_SQL = "select FIELD_ID,DB_FIELDS,UI_FIELD,MENU_ID from IC_MENU_FIELD_DETAILS where IS_ACTIVE='1'";
    public static final String IC_IHD_CATEGORY_PRIORITY_LIST_SQL = "SELECT CPD.SLA_ID as SLA_ID,ISM.TIME as SLA_TIME,CPD.CATEGORY_ID as CATEGORY_ID,CPD.PRIORITY_ID as PRIORITY_ID,CPD.SLA_ID as SLA_ID,CPD.COLOR_CODE_TIME as COLOR_CODE_TIME,ISM.ORGANIZATION as ORGANIZATION FROM IC_IHD_CATEGORY_PRIORITY_DETAILS CPD,"
            + " IC_IHD_SLA_MASTER ISM where CPD.IS_ACTIVE=1 and CPD.SLA_ID=ISM.SLA_ID ";

    public static final String IC_IHD_OUT_OF_SLA_REASON_LIST_SQL = "SELECT REASON_ID,DESCRIPTION,SPECIAL_PREFERENCES FROM IC_IHD_OUT_OF_SLA_REASON_MASTER WHERE IS_ACTIVE=1";

    public static final String IC_IHD_GROUP_FUNCTION_MAPPING_SQL = "SELECT IFM.GROUP_ID as GROUP_ID, IGM.NAME as GROUP_NAME,IFM.CATEGORY_ID as CATEGROY_ID,ICM.NAME as CATEGORY_NAME,IFM.LOCATION_ID as LOCATION_ID,cast(IGM.IS_AUTO_ASSIGNMENT_REQUIRED as int) as  IS_AUTO_ASSIGNMENT_REQUIRED FROM IC_IHD_GROUP_FUNCTION_MAPPING IFM , IC_IHD_GROUP_MASTER IGM,"
            + " IC_IHD_CATEGORY_MASTER ICM  WHERE IFM.IS_ACTIVE=1 "
            + " AND IFM.CATEGORY_ID=ICM.CATEGORY_ID AND IFM.GROUP_ID = IGM.GROUP_ID order by GROUP_NAME";
    
    public static final String IC_IHD_MASTER_GROUP_FUNCTION_MAPPING_SQL = "SELECT distinct IFM.GROUP_ID as GROUP_ID, IGM.NAME as GROUP_NAME,IFM.CATEGORY_ID as CATEGROY_ID,ICM.NAME as CATEGORY_NAME  FROM IC_IHD_GROUP_FUNCTION_MAPPING IFM , IC_IHD_GROUP_MASTER IGM,"
        + " IC_IHD_CATEGORY_MASTER ICM  WHERE IFM.IS_ACTIVE=1 "
        + " AND IFM.CATEGORY_ID=ICM.CATEGORY_ID AND IFM.GROUP_ID = IGM.GROUP_ID order by GROUP_NAME";

    public static final String IC_TIME_ZONE_LIST_SQL = "SELECT TIMEZONE_ID,DESCRIPTION,DAYLIGHT_APPL,DAYLIGHT_VALUE,INTERNAL_FORMAT,DISPLAY_FORMAT FROM IC_TIMEZONE_MASTER WHERE IS_ACTIVE=1";



    public static final String IC_TIME_ZONE_LIST_VARIABLE = "timeZonesList";

    public static final String IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE = "groupFunctionMappingList";
    public static final String IC_IHD_GROUP_FUNCTION_MASTER_MAPPING_LIST_VARIABLE = "groupFunctionMASTERMappingList";


    public static final String IC_IHD_OUT_OF_SLA_REASON_LIST_VARIABLE = "SlaReasonList";

    public static final String IC_CATEGORIES_LIST_VARIABLE = "categories";
    
    public static final String IC_PRINTER_SERVER_LIST_VARIABLE = "printerServer";
    
    //L2 # 1175
    public static final String IC_GAMIFICATION_VARIABLE = "Gamification_Parameters";
    
    public static final String IC_GAMIFICATION_SHIFT = "Gamification_Shift";
    
    public static final String IC_ORCH_LIST_VARIABLE = "orchestraionList";
    
    public static final String IC_ORCH_FIELD_LIST_VARIABLE = "fieldValueList";
    //anjana
    public static final String IC_TABLENAMES_LIST = "tablenames";

    public static final String IC_IHDAPPROVER_LIST_VARIABLE = "IHDApproverList";

    public static final String IC_IHD_CATEGORY_APPROVER_LIST_VARIABLE = "IHDCategoryApproverList";

    public static final String IC_GROUP_LIST_VARIABLE = "GroupList";

    public static final String IC_GROUPMEMBER_LIST_VARIABLE = "GroupMemberList";

    public static final String IC_SLA_LIST_VARIABLE = "SLAList";

    public static final String IC_LOCATION_LIST_VARIABLE = "ICLocationList";

    public static final String IC_LOCATION_DETAILS_LIST_VARIABLE = "ICLocationDetailList";

    public static final String IC_PRIORITY_LIST_VARIABLE = "ICPriorityList";

    public static final String IC_ROLE_LIST_VARIABLE = "ICRoleList";

    public static final String IC_SEVERITY_LIST_VARIABLE = "ICSeverityList";
    
    public static final String IC_IHD_SUB_CATEGORY_APPROVER_LIST_VARIABLE = "IHDSubCategoryApproverList";

    public static final String IC_IHD_CATEGORY_PRIORITY_LIST_VARIABLE = "IHDCategoryPriorityList";
    public static final String IC_DEFAULT_ASSIGN_DETAILS_VARIABLE = "ICDefaultAssignDetails";
    public static final String IHD_GROUP_MEMB_LIST_SQL_BY_GROUPNAME = "SELECT usr.NAME+'('+member.MEMBER_ID+')' as member_name_id, member.MEMBER_ID as member_id ,member.GROUP_ID,member.WORK_LOAD,grup.NAME"
        + " ,grm.ROLE_NAME as ROLE_NAME,grm.ROLE_ID,member.ASSIGNMENT_REQUIRED as ASSIGNMENT_REQUIRED," 
        + " case when  member.IS_ACTIVE = 1 then 'Active' else 'In Active' end as 'Group_Member_Status' "
		+ " FROM IC_IHD_GROUP_MEMBER_DETAILS member,IC_IHD_GROUP_MASTER grup,IC_USER_DETAILS usr,IC_IHD_GROUP_ROLE_MASTER grm"
        + " WHERE member.IS_ACTIVE=1"
        + " AND grup.GROUP_ID=member.GROUP_ID"
        + " AND member.ROLE_ID=grm.ROLE_ID"
        + " AND usr.EMPLOYEE_ID = member.MEMBER_ID order by usr.NAME";
    
    public static final String IHD_GROUP_MEMB_LIST_SQL_BY_GROUPNAME_ADMIN = "SELECT usr.NAME+'('+member.MEMBER_ID+')' as member_name_id, member.MEMBER_ID as member_id ,member.GROUP_ID,member.WORK_LOAD,grup.NAME"
        + " ,grm.ROLE_NAME as ROLE_NAME,member.ASSIGNMENT_REQUIRED as ASSIGNMENT_REQUIRED," 
        + " case when  member.IS_ACTIVE = 1 then 'Active' else 'In Active' end as 'Group_Member_Status' "
		+ " FROM IC_IHD_GROUP_MEMBER_DETAILS member,IC_IHD_GROUP_MASTER grup,IC_USER_DETAILS usr,IC_IHD_GROUP_ROLE_MASTER grm"
        + " WHERE member.IS_ACTIVE=1"
        + " AND grup.GROUP_ID=member.GROUP_ID"
        + " AND member.ROLE_ID=grm.ROLE_ID "
        + " AND usr.EMPLOYEE_ID = member.MEMBER_ID order by usr.NAME";

    public static final String IHD_GROUP_MEMBER_VARIABLE = "IHDGroupMember";
    
    public static final String IHD_GROUP_MEMBER_VARIABLE_ADMIN = "IHDGroupMemberAdmin";
    
    public static final String IC_UIFIELDS_LIST_VARIABLE = "ICUIFieldsList";
    public static final String IC_SEARCH_FIELD_VARIABLE = "ICSeacrhField";
    
    // Added by Sali
    public static final String IHD_SLA_EXCLUSION_LIST_SQL = "SELECT SLA_ID,LOCATION_ID,DATE from ic_ihd_sla_exclusion_dates WHERE IS_ACTIVE=1";
    public static final String IHD_SLA_EXCLUSION_LIST_VARIABLE = "SLAExclusionList";
    public static final String IHD_SLA_OPERATING_TIME_LIST_SQL = "SELECT SLA_ID,DAY,START_TIME,END_TIME,NEXT_WORKING_DAY FROM IC_IHD_SLA_OPERATING_TIME_DETAILS WHERE IS_ACTIVE=1";
    public static final String IHD_SLA_OPERATING_TIME_LIST_SQL_VARIABLE = "SLAOperatingTimeList";
    public static final String IHD_SLA_ORG_OPERATING_TIME_LIST_SQL = "SELECT b.ORGANIZATION,b.SLA_ID,c.DAY,c.START_TIME,c.END_TIME,c.NEXT_WORKING_DAY,c.IS_ACTIVE"
            + " FROM IC_IHD_SLA_MASTER b,IC_IHD_SLA_OPERATING_TIME_DETAILS c  where  c.IS_ACTIVE=1 and c.SLA_ID=b.SLA_ID";
    public static final String IHD_SLA_ORG_OPERATING_TIME_LIST_SQL_VARIBALE = "SLAOperatingTimeOrgWise";
    public static final String IHD_SLA_SERVICE_WINDOW_LIST_SQL = "  SELECT LOCATION_ID,LOC_DET_ID,FUNCTION_ID,OPERATION_ID FROM IC_IHD_SERVICE_WINDOW_DETAILS WHERE IS_ACTIVE=1";
    public static final String IHD_SLA_SERVICE_WINDOW_LIST_SQL_VARIABLE = "FunctionServiceWindow";
    public static final String IHD_GROUP_OPERATION_LIST_SQL = "SELECT GROUP_ID,CATEGORY_ID,LOCATION_ID,SLA_ID FROM IC_IHD_GROUP_FUNCTION_MAPPING WHERE IS_ACTIVE=1";
    public static final String IHD_GROUP_OPERATION_LIST_SQL_VARIABLE = "GroupOpearationId";

    public static final String IC_ALL_COLUMN_LIST_SQL = "SELECT COLUMN_ID,DISPLAY_NAME,DO_NAME,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE from IC_ALL_COLUMN WHERE IS_ACTIVE=1";

    public static final String IC_ALL_COLUMN_LIST_VARIABLE = "ICAllColumnList";

    public static final String IC_COLUMN_MENU_MAPPING_LIST_SQL = "SELECT ICM.COLUMN_ID AS COLUMN_ID,IAC.DISPLAY_NAME AS DISPLAY_NAME ,ICM.MENU_ID AS MENU_ID,ICM.METHOD_NAME AS METHOD_NAME,ICM.IS_MANDATORY AS IS_MANDATORY,ICM.CREATED_BY AS CREATED_BY,ICM.CREATED_DATE AS CREATED_DATE,ICM.MODIFIED_BY AS MODIFIED_BY,ICM.MODIFIED_DATE AS MODIFIED_DATE from IC_COLUMN_MENU_MAPPING ICM,IC_ALL_COLUMN IAC WHERE ICM.COLUMN_ID=IAC.COLUMN_ID AND IAC.IS_ACTIVE=1 AND ICM.IS_ACTIVE=1";

    public static final String IC_COLUMN_MENU_MAPPING_LIST_VARIABLE = "ICColumnMenuMapping";

    public static final String IC_LISTPAGE_DEFAULT_COLUMN_LIST_SQL = "SELECT COLUMN_ID,MENU_ID,DISPLAY_ORDER,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE from IC_LISTPAGE_DEFAULT_COLUMN WHERE IS_ACTIVE=1 order by MENU_ID,DISPLAY_ORDER";

    public static final String IC_LISTPAGE_DEFAULT_COLUMN_LIST_VARIABLE = "ICDefaultColumnMenuMappingList";

    public static String removeUnicodes(String value) {
        return value.replace("�", "");

    }

    public static final String IHD_USER_ROLE_DETAILS = "Select * from IC_USER_ROLE_DETAILS where IS_ACTIVE=1";
    public static final String IC_USER_ROLE_DETAILS_VARIABLE = "ICUserRoleList";
    public static final String IC_IHD_INACTIVE_GROUP_MEMBER = "SELECT usr.NAME+'('+member.MEMBER_ID+')' as member_name_id, member.MEMBER_ID as member_id "
            + ",grm.ROLE_NAME as ROLE_NAME,case when  member.IS_ACTIVE = 1 then 'Active' else 'In Active' end as 'Group_Member_Status',member.GROUP_ID,member.WORK_LOAD,grup.NAME "
            + "FROM IC_IHD_GROUP_MEMBER_DETAILS member,IC_IHD_GROUP_MASTER grup"
            + ",IC_USER_DETAILS usr,IC_IHD_GROUP_ROLE_MASTER grm WHERE member.IS_ACTIVE=0 AND grup.GROUP_ID=member.GROUP_ID "
            + "AND usr.EMPLOYEE_ID = member.MEMBER_ID  and grm.ROLE_ID=member.ROLE_ID order by usr.NAME";
    public static final String IC_IHD_ACTIVE_INACTIVE_GROUP_MEMBER = "SELECT usr.NAME+'('+member.MEMBER_ID+')' as member_name_id,"
            + " case when  member.IS_ACTIVE = 1 then 'Active' else 'In Active' end as 'Group_Member_Status',"
            + "member.MEMBER_ID as member_id ,member.GROUP_ID,member.WORK_LOAD,grup.NAME "
            + "FROM IC_IHD_GROUP_MEMBER_DETAILS member,IC_IHD_GROUP_MASTER grup,IC_USER_DETAILS usr "
            + "WHERE grup.GROUP_ID=member.GROUP_ID "
            + "AND usr.EMPLOYEE_ID = member.MEMBER_ID order by usr.NAME";
    public static final String IHD_INACTIVE_GROUP_MEMBER_VARIABLE = "IHDInactiveGroupMember";
    public static final String IHD_ACTIVE_INACTIVE_GROUP_MEMBER_VARIABLES = "IHDActiveInactiveGroupMember";
    
    public static final String IC_IHD_FUNCTION_LOCATION_MASTER = "SELECT FUNCTION_ID,LOCATION_ID FROM IC_IHD_FUNCTION_LOCATION_MAPPING WHERE IS_ACTIVE=1 ORDER BY FUNCTION_ID";
    
    public static final String IC_IHD_FUNCTION_LOCATION_VARIABLE = "IHDActiveGroupMember";
    
    public static final String IC_IHD_DEPT_MASTER_SQL ="select DEPT_ID,DEPT_NAME from dbo.IC_IHD_DEPT_MASTER WHERE IS_ACTIVE=1 ORDER BY DEPT_NAME";
    
    
    public static final String IC_IHD_DEPT_MASTER_VARIABLE ="IHDDepartmentList";
    
    public static final String IC_IHD_DEPT_DEFAULT_ASSIGNMENT_SQL ="select DDA.DEPT_ID AS DEPT_ID,DDA.CATEGORY_ID AS CATEGORY_ID,DDA.LOCATION_ID AS LOCATION_ID,DDA.GROUP_ID AS GROUP_ID, "
    															  +" IGM.NAME AS GROUP_NAME from dbo.IC_IHD_DEPT_DEFAULT_ASSIGNMENT DDA ,IC_IHD_GROUP_MASTER IGM WHERE DDA.IS_ACTIVE=1 " 
    															  +" AND DDA.GROUP_ID=IGM.GROUP_ID ORDER BY DEPT_ID "; 
    
    public static final String IC_IHD_DEPT_DEFAULT_ASSIGNMENT_VARIABLE ="IHDDepartmentDefaultAssignments";

    public static final String IC_IHD_LOC_DET_DEFAULT_ASSIGNMENT_SQL ="select LDA.CATEGORY_ID AS CATEGORY_ID,LDA.LOC_DET_ID AS LOC_DET_ID,LDA.GROUP_ID AS GROUP_ID,IGM.NAME AS GROUP_NAME from IC_IHD_LOC_DETAIL_DEFAULT_ASSIGNMENT_DETAILS LDA,IC_IHD_GROUP_MASTER IGM "
    																+ " WHERE LDA.IS_ACTIVE=1 AND IGM.IS_ACTIVE=1 AND LDA.GROUP_ID=IGM.GROUP_ID ORDER BY LDA.CATEGORY_ID"; 
    
    public static final String IC_IHD_LOC_DET_DEFAULT_ASSIGNMENT_VARIABLE ="IHDLocationDetailDefaultAssignments";
 
    public static final String IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS_SQL = "select CATEGORY_ID,APPROVER_ORDER,APPROVER_ID,DEPT_ID from IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS WHERE IS_ACTIVE=1";
    
    public static final String IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS_VARIABLE = "DeptWiseCategoryApproval";
    
    public static final String IC_IHD_APPROVER_EMPLOYEE_DETAILS_SQL = "SELECT AED.APPROVER_ID AS APPROVER_ID,AED.EMPLOYEE_ID AS EMPLOYEE_ID,AED.LOCATION_ID AS LOCATION_ID,iud.NAME AS EMPLOYEE_NAME FROM IC_IHD_APPROVER_EMPLOYEE_DETAILS AED,IC_USER_DETAILS IUD "
    																+ " WHERE IUD.IS_ACTIVE=1 AND AED.IS_ACTIVE=1 AND AED.EMPLOYEE_ID=IUD.EMPLOYEE_ID ORDER BY AED.APPROVER_ID ";
    
    public static final String IC_IHD_APPROVER_EMPLOYEE_DETAILS_VARIABLE = "ApproverEmployeeDetails";
    
    public static final String IC_IHD_ODC_MASTER_SQL = "select ODC_ID,ODC_NAME from IC_IHD_ODC_MASTER WHERE IS_ACTIVE=1";
    
    public static final String IC_IHD_ODC_MASTER_VARIABLE = "ICodcMaster";

    public static final String IC_IHD_CATEGORY_ODC_MAPPING_SQL = "select COM.CATEGORY_ID AS CATEGORY_ID,COM.LOC_DET_ID AS LOC_DET_ID,COM.ODC_ID AS ODC_ID,IOM.ODC_NAME AS ODC_NAME from IC_IHD_CATEGORY_ODC_MAPPING COM,IC_IHD_ODC_MASTER IOM "
    															+ " WHERE COM.IS_ACTIVE=1 AND COM.ODC_ID=IOM.ODC_ID";
    
    public static final String IC_IHD_CATEGORY_ODC_MAPPING_VARIABLE = "ICodcLocattionMappingMaster";
    
    public static final String IC_IHD_CATEGORY_ROLE_MAPPING_SQL="select frm.FUNCTION_ID,iwm.NAME as WORKFLOW_NAME,frm.ROLE_ID,irm.NAME as ROLE_NAME" +
	" from IC_IHD_FUNCTION_ROLE_MAPPING frm,IC_ROLE_MASTER irm," +
	"IC_WORKFLOW_MASTER iwm where frm.ROLE_ID=irm.ROLE_ID and iwm.WORKFLOW_ID=frm.FUNCTION_ID";
	

    public static final String IC_IHD_CATEGORY_ROLE_MAPPING_VARIABLE="IcCategoryRoleMappingMaster";

    public static final String IC_IHD_USER_DEPT_MAPPING_SQL="select EMP_DU_ID,EMP_DU_NAME,MAPPING_DU_ID as DEPT_ID,MAPPING_DU_NAME as DEPT_NAME,IS_ACTIVE from IC_IHD_USER_DEPT_DU_MAPPING ORDER BY MAPPING_DU_ID";
    
    public static final String IC_IHD_USER_DEPT_MAPPING_VARIABLE="IcIhdUserDeptMappingVariable";
    
    public static final String IC_IHD_EMP_DU_MAPPING_SQL="Select EMPLOYEE_ID,DU_ID from IC_USER_DETAILS where IS_ACTIVE=1 order by EMPLOYEE_ID";
    
    public static final String IC_IHD_EMP_DU_MAPPING_VARIABLE="IcIhdEmpDuMappingVariable";
    
    public static final String IC_IHD_GROUP_FOR_MEMBER_SQL=" SELECT distinct GMD.GROUP_ID as GROUP_ID,IGM.NAME as GROUP_NAME,ICM.CATEGORY_ID as CATEGROY_ID,ICM.NAME as CATEGORY_NAME," +
    		" GMD.MEMBER_ID as MEMBER_ID,IUD.NAME as EMPLOYEE_NAME" +
    		" from IC_IHD_GROUP_FUNCTION_MAPPING GFM,IC_IHD_GROUP_MEMBER_DETAILS GMD," +
    		" IC_IHD_CATEGORY_MASTER ICM,IC_IHD_GROUP_MASTER IGM, IC_USER_DETAILS IUD" +
    		" WHERE" +
    		" GMD.GROUP_ID=GFM.GROUP_ID AND" +
    		" GFM.CATEGORY_ID=ICM.CATEGORY_ID AND" +
    		" IGM.GROUP_ID=GMD.GROUP_ID AND" +
    		" IUD.EMPLOYEE_ID=GMD.MEMBER_ID AND" +
    		" GMD.IS_ACTIVE=1 AND" +
    		" IUD.IS_ACTIVE=1 ";
    
    public static final String IC_IHD_GROUP_FOR_MEMBER_VARIABLE="IcIhdGroupForMemberVariable";
    
    
    public static final String IC_IHD_COUNTRY_VARIABLE="IcIhdCountryVariable";
    //Added for iconnect with itrack web services PROJECT_NAME IS REQUIRED.
    
    public static final String IC_IHD_PROJECT_VARIABLE="IcProjectVariable";

    public static final String IC_IHD_PROJECT_SQL="Select PROJECT_ID,NAME from IC_PROJECT_MASTER where STATUS=1";
    
    public static final String IC_ROLE_LIST_iTrack_Cat_SQL="SELECT CRM.CATEGORY_ID,RM.NAME FROM IC_IHD_CATEGORY_ROLE_MAPPING CRM,IC_ROLE_MASTER RM WHERE IS_ACTIVE=1 AND RM.ROLE_ID=CRM.ROLE_ID";
    
    public static final String IC_ROLE_LIST_iTrack_Cat_VARIABLE="IcCatRoleMap";

	public static final String IHD_SUBCATEGORY_APPROVER_LIST_SQL = "select CAD.CATEGORY_ID,CAD.APPROVER_ID,WSM.NAME 'WORKFLOW_STATE' " +
" from IC_IHD_CATEGORY_APPROVAL_DETAILS CAD " +
" JOIN IC_IHD_APPROVER_MASTER AM ON AM.APPROVER_ID=CAD.APPROVER_ID AND AM.IS_ACTIVE=1 " +
" JOIN IC_WORKFLOW_STATE_MASTER WSM ON WSM.STATE_ID=AM.WORKFLOW_STATE " +
" WHERE CAD.IS_ACTIVE=1";
	
	/*Auto Assignment*/
	public static final String IC_WORKFLOW_STATE_MASTER_SQL="Select STATE_ID,NAME,WORKFLOW_ID,CREATED_DATE,STATUS_WEIGHTAGE from IC_WORKFLOW_STATE_MASTER";
    public static final String IC_WORKFLOW_STATE_MASTER_VARIABLE="IcWfStateMasterVariable";
    /*Auto Assignment*/
    
    //Admin Console
    public static final String IC_GROUP_ROLE_MASTER_SQL="SELECT ROLE_ID,ROLE_NAME FROM IC_IHD_GROUP_ROLE_MASTER WHERE IS_ACTIVE=1";
    public static final String IC_GROUP_ROLE_MASTER_VARIABLE="GroupAccessLevelVariable";

	public static final String IC_USER_ROLE_DETAILS_SQL = "SELECT EMPLOYEE_ID,ROLE_ID FROM IC_USER_ROLE_DETAILS WHERE IS_ACTIVE=1 AND ROLE_ID NOT IN (6)";
 /*ADMIN Console:Location Addition*/
    public static final String IC_FUNCTION_CAT_SUBCAT_GROUP_SQL="SELECT " +
    		"	distinct ICM1.PARENT_ID AS PARENT_ID,ICM2.NAME AS PARENT_NAME," +
    		"	ICM1.CATEGORY_ID as CATEGORY_ID,ICM1.NAME as CATEGORY_NAME," +
    		"	DAD.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME" +
    		"	FROM IC_IHD_CATEGORY_MASTER ICM," +
    		"	IC_IHD_CATEGORY_MASTER ICM1," +
    		"	IC_IHD_CATEGORY_MASTER ICM2," +
    		"	IC_IHD_DEFAULT_ASSIGNMENT_DETAILS DAD," +
    		"	IC_IHD_GROUP_MASTER IGM" +
    		"	WHERE DAD.CATEGORY_ID=ICM.CATEGORY_ID" +
    		"	AND DAD.IS_ACTIVE=1" +
    		"	AND IGM.GROUP_ID=DAD.GROUP_ID" +
    		"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
    		"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID";
    
    public static final String IC_FUNCTION_CAT_SUBCAT__SQL="	Select distinct ICM.CATEGORY_ID AS PARENT_ID,ICM.NAME AS PARENT_NAME,ICM1.CATEGORY_ID as CATEGORY_ID,ICM1.NAME as CATEGORY_NAME	,ICM2.CATEGORY_ID AS SUB_CATEGORY_ID,ICM2.NAME as SUB_CATEGORY_NAME,ICM2.IS_ACTIVE as SUB_CAT_STATUS, ICM1.IS_ACTIVE AS CAT_STATUS" +
	" 	from IC_IHD_CATEGORY_MASTER ICM, 		IC_IHD_CATEGORY_MASTER ICM1," +
	" IC_IHD_CATEGORY_MASTER ICM2" +
	" WHERE ICM1.PARENT_ID=ICM.CATEGORY_ID" +
	" AND ICM2.PARENT_ID=ICM1.CATEGORY_ID	";
    
    public static final String IC_FUNCTION_SUBCAT_GROUP_SQL="SELECT " +
	"	distinct " +
	"	DAD.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME," +
	"	DAD.GROUP_ID AS GROUP_ID , IGM.NAME AS GROUP_NAME" +
	"	FROM IC_IHD_CATEGORY_MASTER ICM," +
	"	IC_IHD_CATEGORY_MASTER ICM1," +
	"	IC_IHD_CATEGORY_MASTER ICM2," +
	"	IC_IHD_DEFAULT_ASSIGNMENT_DETAILS DAD," +
	"	IC_IHD_GROUP_MASTER IGM" +
	"	WHERE DAD.CATEGORY_ID=ICM.CATEGORY_ID" +
	"	AND DAD.IS_ACTIVE=1" +
	"	AND IGM.GROUP_ID=DAD.GROUP_ID" +
	"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
	"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID";
    
    public static final String IC_DEF_ASSG_FUNCTION_CAT_SUBCAT_GROUP_SQL="select distinct ICM1.PARENT_ID AS PARENT_ID,ICM2.NAME AS PARENT_NAME," +
    		"	ICM1.CATEGORY_ID as CATEGORY_ID,ICM1.NAME as CATEGORY_NAME," +
    		"	DDA.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME,DDA.DEPT_ID AS DEPT_ID,DEP.DEPT_NAME,DDA.GROUP_ID AS GROUP_ID," +
    		"	IGM.NAME AS GROUP_NAME" +
    		"	from dbo.IC_IHD_DEPT_DEFAULT_ASSIGNMENT DDA ," +
    		"	IC_IHD_GROUP_MASTER IGM ," +
    		"	IC_IHD_DEPT_MASTER DEP," +
    		"	IC_IHD_CATEGORY_MASTER ICM," +
    		"	IC_IHD_CATEGORY_MASTER ICM1," +
    		"	IC_IHD_CATEGORY_MASTER ICM2" +
    		"	WHERE DDA.IS_ACTIVE=1" +
    		"	AND DDA.GROUP_ID=IGM.GROUP_ID" +
    		"	AND DEP.DEPT_ID=DDA.DEPT_ID" +
    		"	AND DDA.CATEGORY_ID=ICM.CATEGORY_ID" +
    		"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
    		"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID" +
    		"	ORDER BY DEPT_ID";
    
    public static final String IC_LOC_FUNCTION_CAT_SUBCAT_GROUP_SQL="select distinct ICM1.PARENT_ID AS PARENT_ID,ICM2.NAME AS PARENT_NAME," +
    		"	ICM1.CATEGORY_ID as CATEGORY_ID,ICM1.NAME as CATEGORY_NAME," +
    		"	LDA.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME " +
    		"	from IC_IHD_LOC_DETAIL_DEFAULT_ASSIGNMENT_DETAILS LDA," +
    		"	IC_IHD_GROUP_MASTER IGM ," +
    		"	IC_IHD_CATEGORY_MASTER ICM," +
    		"	IC_IHD_CATEGORY_MASTER ICM1," +
    		"	IC_IHD_CATEGORY_MASTER ICM2" +
    		"	WHERE LDA.IS_ACTIVE=1 AND LDA.GROUP_ID=IGM.GROUP_ID" +
    		"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
    		"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID" +
    		"	AND LDA.CATEGORY_ID=ICM.CATEGORY_ID" +
    		"	 ORDER BY LDA.CATEGORY_ID";
    
    
    
    public static final String IC_LOC_FUNCTION_SUBCAT_GROUP_SQL="select distinct  LDA.GROUP_ID AS GROUP_ID,IGM.NAME AS GROUP_NAME ,LDA.CATEGORY_ID AS SUB_CATEGORY_ID " +
	"	from IC_IHD_LOC_DETAIL_DEFAULT_ASSIGNMENT_DETAILS LDA," +
	"	IC_IHD_GROUP_MASTER IGM ," +
	"	IC_IHD_CATEGORY_MASTER ICM," +
	"	IC_IHD_CATEGORY_MASTER ICM1," +
	"	IC_IHD_CATEGORY_MASTER ICM2" +
	"	WHERE LDA.IS_ACTIVE=1 AND LDA.GROUP_ID=IGM.GROUP_ID" +
	"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
	"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID" +
	"	AND LDA.CATEGORY_ID=ICM.CATEGORY_ID" +
	"	 ORDER BY LDA.CATEGORY_ID";
    public static final String IC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE="IcFunctionCatSubCatGroupVariable";
    
    public static final String IC_FUNCTION_CAT_SUBCAT__VARIABLE="IcFunctionCatSubCaVariable";
    public static final String IC_FUNCTION_SUBCAT_GROUP_VARIABLE="IcFunctionCSubCatGroupVariable";
    public static final String IC_LOC_FUNCTION_SUBCAT_GROUP_VARIABLE="IcFunctionSubCatGroupVariable";
    public static final String IC_DEF_ASSG_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE="IcDefAssgnmntFuncnCatSubCatGrpVariable";
    public static final String IC_LOC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE="IcLocFuncnCatSubCatGrpVariable";
    /*ADMIN Console:Location Addition*/
    

	public static final String IC_USER_ROLE_DETAIL_VARIABLE = "IcUserRoleDetail";

	public static final String IC_IHD_GROUP_NAME_FUNCTION_MAPPING_SQL = "SELECT IFM.GROUP_ID as GROUP_ID, IGM.NAME as GROUP_NAME,IFM.CATEGORY_ID as CATEGROY_ID," +
			"ICM.NAME as CATEGORY_NAME,IFM.LOCATION_ID as LOCATION_ID,cast(IGM.IS_AUTO_ASSIGNMENT_REQUIRED as int) as  IS_AUTO_ASSIGNMENT_REQUIRED " +
			"FROM IC_IHD_GROUP_FUNCTION_MAPPING IFM , IC_IHD_GROUP_MASTER IGM,IC_IHD_CATEGORY_MASTER ICM  " +
			"WHERE IFM.CATEGORY_ID=ICM.CATEGORY_ID AND IFM.GROUP_ID = IGM.GROUP_ID and IFM.LOCATION_ID=IGM.LOCATION_ID " +
			"order by GROUP_NAME";
	
	public static final String IC_IHD_GROUP_NAME_FUNCTION_MAPPING_LIST_VARIABLE = "groupNameFunctionMappingList";
	
	 /**************PRIVATE CLOUD****************/
	
	public static final String IC_SUBCAT_ID_FOR_AUTOMATION_SQL="select CATEGORY_ID,NAME 'CATEGORY_NAME',AUTOMATION_REQUIRED,SPECIAL_PREFERENCES from IC_IHD_CATEGORY_MASTER where AUTOMATION_REQUIRED='Y' and IS_ACTIVE=1";
	
	public static final String IC_SUBCAT_ID_FOR_AUTOMATION_VARIABLE="ihdCategoryForAutomation";
	
	public static final String IC_AUTOMATION_CAT_MAPPING_SQL="select CATEGORY_ID,TABLE_NAME from IC_IHD_ITRACK_AUTOMATION_CATEGORY_MAPPING where IS_ACTIVE=1";
	
	public static final String IC_AUTOMATION_CAT_MAPPING_VARIABLE="ihdAutomationCatMapping";
	 /**************END PRIVATE CLOUD****************/
	
	/*Added by Mohit for iTrack Resolution Status List*/
	public static final String IC_IHD_RESOLUTION_STATUS_MASTER_VARIABLE="resolutionStatusList";
	
	public static final String IC_IHD_RESOLUTION_STATUS_MASTER_SQL="select RM.REASON_ID,RM.DESCRIPTION,RMD.CATEGORY_ID from IC_IHD_RESOLUTION_STATUS_MASTER RM " 
			+" JOIN IC_IHD_RESOLUTION_STATUS_CATEGORY_MAPPING RMD ON RMD.REASON_ID=RM.REASON_ID "
+" WHERE RM.IS_ACTIVE=1 AND RMD.IS_ACTIVE=1";
	
	
public static final String IC_IHD_RESOLUTION_STATUS_MASTER_DATA_VARIABLE="resolutionStatusMasterList";
	
	public static final String IC_IHD_RESOLUTION_STATUS_MASTER_DATA_SQL="select REASON_ID,DESCRIPTION from IC_IHD_RESOLUTION_STATUS_MASTER WHERE IS_ACTIVE=1";
	
	public static final String IC_IHD_CATEGORY_BU_MAPPING_SQL="select CBM.CATEGORY_ID,ICM.NAME 'CATEGORY_NAME' from IC_USER_DETAILS DET " +
		"left outer join IC_DU_MASTER IDM on DET.DU_ID=IDM.DU_ID and DET.IS_ACTIVE=1 and IDM.IS_ACTIVE=1 " +
		"left outer join IC_IHD_CATEGORY_BU_MAPPING CBM on IDM.BU_ID=CBM.BU_ID and CBM.IS_ACTIVE=1 " + 
		"join IC_IHD_CATEGORY_MASTER ICM on CBM.CATEGORY_ID=ICM.CATEGORY_ID and ICM.IS_ACTIVE=1 " + 
		"where DET.EMPLOYEE_ID=?";
	/*End of Added by Mohit*/
	
	public static final String IC_IHD_CATEGORY_STATUS_FILTER_SQL = "select FUNC.CATEGORY_ID 'Function ID', "
			+" FUNC.NAME 'Function', "
+" CAT.CATEGORY_ID 'Category ID', "
+" CAT.NAME 'Category', "
+" SUBCAT.CATEGORY_ID 'Sub Category ID', "
+" SUBCAT.NAME 'Sub Category', "
+" CAT.DEFAULT_STATE 'Category Default State', "
+" WSM.NAME 'Category State Name' "
+" ,SUBCAT.DEFAULT_STATE 'Sub Category Default State' "
+" ,WSM2.NAME 'Sub Category State Name' "
+" ,WSM.WORKFLOW_ID 'Workflow ID' "
+" ,WM.NAME 'Workflow Name' "
+" from IC_IHD_CATEGORY_MASTER FUNC " 
+" JOIN IC_IHD_CATEGORY_MASTER CAT on FUNC.CATEGORY_ID = CAT.PARENT_ID and CAT.IS_ACTIVE = 1 and FUNC.IS_ACTIVE = 1 "
+" JOIN IC_IHD_CATEGORY_MASTER SUBCAT on CAT.CATEGORY_ID = SUBCAT.PARENT_ID and SUBCAT.IS_ACTIVE = 1 "
+" LEFT JOIN IC_WORKFLOW_STATE_MASTER WSM on WSM.STATE_ID = CAT.DEFAULT_STATE " 
+" LEFT JOIN IC_WORKFLOW_STATE_MASTER WSM2 on WSM2.STATE_ID = SUBCAT.DEFAULT_STATE " 
+" LEFT JOIN IC_WORKFLOW_MASTER WM on WM.WORKFLOW_ID = WSM.WORKFLOW_ID "
+" order by FUNC.CATEGORY_ID ";
	
	public static final String IC_IHD_CATEGORY_STATUS_FILTER_VARIABLE="defaultStatusOnCat";
	
	public static final String IC_IHD_DEFAULT_GROUPS_FOR_ITRACK_SQL="SELECT IT.FUNCTION_ID, LD.LOCATION_ID,IT.LOC_DET_ID, IT.GROUP_ID,GM.LOCATION_ID 'BASE_LOCATION_ID',gm.MANAGER 'GROUP_MANAGER'  "
			+" FROM IC_IHD_ITRACK_FUNC_LOC_GROUP_MAPPING IT "
+" JOIN IC_LOCATION_DETAILS LD ON LD.LOC_DET_ID = IT.LOC_DET_ID "
+" JOIN IC_IHD_GROUP_MASTER GM ON IT.GROUP_ID = GM.GROUP_ID "
+" Where IT.IS_ACTIVE = 1 AND LD.IS_ACTIVE = 1 AND GM.IS_ACTIVE = 1";
	
	public static final String IC_IHD_DEFAULT_GROUPS_FOR_ITRACK_VARIABLE="defaultGroupsForITrackTickets";
	
	public static final String IC_IHD_AUTO_APPROVAL_MEMBER_DETAILS_SQL="select MEMBER_ID 'MANAGER_ID' from IC_IHD_AUTO_APPROVAL_MEMBER_DETAILS where IS_ACTIVE=1";
	
	public static final String IC_IHD_AUTO_APPROVAL_MEMBER_DETAILS_VARIABLE="autoApproverMemberdatilsList";
	
/*	public static final String IC_USER_DETAILS_COLUMS_VARIABLE="userDetailsTableColumns";
	
	public static final String IC_USER_DETAILS_COLUMS_SQL="SELECT "
			+" replace(c.name,'_',' ') 'Column_Name', "
			 +"c.name 'Column', "
    +" t.Name 'Data_type', "
    +" c.max_length 'Max_Length', "
    +" c.is_nullable "
+" FROM "    
    +" sys.columns c "
+" INNER JOIN " 
    +" sys.types t ON c.user_type_id = t.user_type_id "
+" LEFT OUTER JOIN " 
    +" sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id "
+" LEFT OUTER JOIN " 
    +" sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id "
+" WHERE "
    +" c.object_id = OBJECT_ID('IC_USER_DETAILS')";*/
	
	public static final String IC_ESCALATION_MATRIX_SQL="select WM.NAME 'WORKFLOW_NAME',ud.NAME 'EMPLOYEE_NAME',DM.DESIGNATION_NAME,ud.EMAIL_ADDRESS, "
			+" ud.MOBILE_NUMBER,ud.CONTACT_NUMBER,EM.ESCALATION_LEVEL  "
+" from IC_ESCALATION_MATRIX em "
+" join IC_USER_DETAILS ud on ud.EMPLOYEE_ID=em.EMPLOYEE_ID " 
+" JOIN IC_DESIGNATION_MASTER DM ON DM.DESIGNATION_ID=ud.DESIGNATION_ID "
+" JOIN IC_WORKFLOW_MASTER WM ON WM.WORKFLOW_ID=em.WORKFLOW_ID "
+" WHERE EM.IS_ACTIVE=1";
	
	public static final String IC_ESCALATION_MATRIX_VARIABLE="escalationmatrixList";
	
	
	public static final String IC_ESCALATION_MATRIX_FUNCTION_SQL="select distinct WM.NAME 'WORKFLOW_NAME' "
			+" from IC_ESCALATION_MATRIX em "
+" JOIN IC_WORKFLOW_MASTER WM ON WM.WORKFLOW_ID=em.WORKFLOW_ID "
+" WHERE EM.IS_ACTIVE=1";
	
	public static final String IC_ESCALATION_MATRIX_FUNCTION_VARIABLE="escalationmatrixFunctionList";
	
	//L2: 2786
	public static final String IC_REPORTS_WORKFLOW_STATE_SQL="Select STATE_ID,NAME,WORKFLOW_ID,CREATED_DATE,STATUS_WEIGHTAGE from IC_WORKFLOW_STATE_MASTER where STATE_ID not in (16,30,31,32,33,34,35) and WORKFLOW_ID=1";
	
    public static final String IC_REPORTS_WORKFLOW_STATE_VAR="workflowStateMaster";
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 3, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
