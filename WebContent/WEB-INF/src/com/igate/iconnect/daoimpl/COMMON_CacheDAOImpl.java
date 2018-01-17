/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.COMMON_Menu;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Group;
import com.igate.iconnect.BO.HELPDESK_Priority;
import com.igate.iconnect.constants.COMMON_CacheSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.util.COMMON_AppContext;

@Transactional(rollbackFor = Exception.class)
public class COMMON_CacheDAOImpl implements COMMON_CacheDAO {

	private JdbcTemplate jdbcTemplate;
	private static Map<String, List<Map<String, Object>>> masterData = new HashMap<String, List<Map<String, Object>>>();
	private static List<COMMON_Menu> menuList = new ArrayList<COMMON_Menu>();
	private static List<COMMON_Menu> menuRoleQueryMapping = new ArrayList<COMMON_Menu>();
	private static Map<Integer, String> functionlistmap = new HashMap<Integer, String>();

	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		getmasterData(jdbcTemplate);
		prepareMenuRoleQueryMapping(jdbcTemplate);
		//prepareTechCRAction(jdbcTemplate);//Added by Sali
		getallFunctions();
	}

	private Map<String, List<Map<String, Object>>> getmasterData(
			JdbcTemplate jdbcTemplate) {
		getMenu(jdbcTemplate);
		// Added by Sali
		//Added by steffy
		List<Map<String,Object>> groupAccessLevelList=new ArrayList<Map<String, Object>>();
		groupAccessLevelList=jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_GROUP_ROLE_MASTER_SQL);
		
		masterData
				.put(COMMON_CacheSQLQueryConstants.IC_GROUP_ROLE_MASTER_VARIABLE, groupAccessLevelList);
		
		List<Map<String,Object>> userRoleDetailsList=new ArrayList<Map<String, Object>>();
		userRoleDetailsList=jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_USER_ROLE_DETAILS_SQL);
		
		masterData
				.put(COMMON_CacheSQLQueryConstants.IC_USER_ROLE_DETAIL_VARIABLE, userRoleDetailsList);
		
		List<Map<String, Object>> ihdGroupNameFuncitonMapList = new ArrayList<Map<String, Object>>();
		ihdGroupNameFuncitonMapList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_NAME_FUNCTION_MAPPING_SQL);

		masterData
				.put(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_NAME_FUNCTION_MAPPING_LIST_VARIABLE,
						ihdGroupNameFuncitonMapList);
		
		List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
		categoryList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_CAT_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_CATEGORIES_LIST_VARIABLE,
				categoryList);
		
		
		/***********Added for Auto Assignment*******************************************/
		List<Map<String,Object>> statusList=new ArrayList<Map<String, Object>>();
		statusList=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_WORKFLOW_STATE_MASTER_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_WORKFLOW_STATE_MASTER_VARIABLE, statusList);
		/***********END Added for Auto Assignment***************************************/

		List<Map<String, Object>> approverList = new ArrayList<Map<String, Object>>();
		approverList = this.jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_APPROVER_LIST_SQL);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_IHDAPPROVER_LIST_VARIABLE,
				approverList);

		List<Map<String, Object>> categoryapproverList = new ArrayList<Map<String, Object>>();
		categoryapproverList = this.jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_CATEGORY_APPROVER_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_APPROVER_LIST_VARIABLE,
						categoryapproverList);
		
		List<Map<String, Object>> subCategoryApproverList = new ArrayList<Map<String, Object>>();
		subCategoryApproverList = this.jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SUBCATEGORY_APPROVER_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_SUB_CATEGORY_APPROVER_LIST_VARIABLE,
						subCategoryApproverList);

		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		groupList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_GROUP_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_GROUP_LIST_VARIABLE,
				groupList);
		// Added by Sali
		List<Map<String, Object>> searchUIFieldList = new ArrayList<Map<String, Object>>();
		searchUIFieldList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_UIFIELDS_LIST_SQL);

		masterData.put(COMMON_CacheSQLQueryConstants.IC_SEARCH_FIELD_VARIABLE,
				searchUIFieldList);

		List<Map<String, Object>> groupMembList = new ArrayList<Map<String, Object>>();
		groupMembList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMB_LIST_SQL_BY_GROUPNAME);
		masterData.put(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE,
				groupMembList);
		/*****************************For Admin Remove Group Poovamma **********************************/
		List<Map<String, Object>> groupMembList_Admin = new ArrayList<Map<String, Object>>();
		groupMembList_Admin = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMB_LIST_SQL_BY_GROUPNAME_ADMIN);
		masterData.put(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE_ADMIN,
				groupMembList_Admin);
		
		/*****************************End For Admin Remove Group **********************************/
		
		/*******Added By Poovamma*********/
		List<Map<String, Object>> groupFunctionList = new ArrayList<Map<String, Object>>();
		groupFunctionList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FOR_MEMBER_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FOR_MEMBER_VARIABLE,
				groupFunctionList);
		
		/*******End Added By Poovamma*********/

		
		/*******Added By Poovamma:ADMIN CONSOLE*********/
		List<Map<String, Object>> countryList = new ArrayList<Map<String, Object>>();
		countryList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_COUNTRY_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_IHD_COUNTRY_VARIABLE,
				countryList);
		
		/*******End Added By Poovamma:ADMIN CONSOLE*********/
		List<Map<String, Object>> slaList = new ArrayList<Map<String, Object>>();
		slaList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SLA_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_SLA_LIST_VARIABLE,
				slaList);
		// Added by sali
		List<Map<String, Object>> groupIdList = new ArrayList<Map<String, Object>>();
		groupIdList = this.jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMB_LIST_SQL);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_GROUPMEMBER_LIST_VARIABLE,
				groupIdList);


		List<Map<String, Object>> icLocationList = new ArrayList<Map<String, Object>>();
		icLocationList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_VARIABLE,
				icLocationList);

		List<Map<String, Object>> icLocationDetList = new ArrayList<Map<String, Object>>();
		icLocationDetList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_LOCATION_DET_LIST_SQL);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_LOCATION_DETAILS_LIST_VARIABLE,
				icLocationDetList);

		List<Map<String, Object>> icPriorityList = new ArrayList<Map<String, Object>>();
		icPriorityList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_PRIORITY_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_PRIORITY_LIST_VARIABLE,
				icPriorityList);

		List<Map<String, Object>> icRoleList = new ArrayList<Map<String, Object>>();
		icRoleList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_VARIABLE,
				icRoleList);

		List<Map<String, Object>> icSeverityList = new ArrayList<Map<String, Object>>();
		icSeverityList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_SEVERITY_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_SEVERITY_LIST_VARIABLE,
				icSeverityList);

		List<Map<String, Object>> icdefaultassignmentList = new ArrayList<Map<String, Object>>();
		icdefaultassignmentList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_DEFAULT_ASSIGN_DETAILS_SQL);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_DEFAULT_ASSIGN_DETAILS_VARIABLE,
				icdefaultassignmentList);
		List<Map<String, Object>> ihdCategoryPriorityList = new ArrayList<Map<String, Object>>();
		ihdCategoryPriorityList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_PRIORITY_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_PRIORITY_LIST_VARIABLE,
						ihdCategoryPriorityList);

		List<Map<String, Object>> ihdSlaReasonList = new ArrayList<Map<String, Object>>();
		ihdSlaReasonList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_OUT_OF_SLA_REASON_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_OUT_OF_SLA_REASON_LIST_VARIABLE,
						ihdSlaReasonList);

		List<Map<String, Object>> ihdGroupFuncitonMapList = new ArrayList<Map<String, Object>>();
		ihdGroupFuncitonMapList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_SQL);

		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE,
						ihdGroupFuncitonMapList);
		///////////
		List<Map<String, Object>> ihdGrpFunctionList = new ArrayList<Map<String, Object>>();
		ihdGrpFunctionList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_MASTER_GROUP_FUNCTION_MAPPING_SQL);

		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MASTER_MAPPING_LIST_VARIABLE,
						ihdGrpFunctionList);

		List<Map<String, Object>> icTimeZonesList = new ArrayList<Map<String, Object>>();
		icTimeZonesList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_TIME_ZONE_LIST_SQL);

		masterData.put(COMMON_CacheSQLQueryConstants.IC_TIME_ZONE_LIST_VARIABLE,
				icTimeZonesList);
		// Added by Sali
		List<Map<String, Object>> slaExclusionList = new ArrayList<Map<String, Object>>();
		slaExclusionList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SLA_EXCLUSION_LIST_SQL);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IHD_SLA_EXCLUSION_LIST_VARIABLE,
				slaExclusionList);
		List<Map<String, Object>> slaOperatingTimeList = new ArrayList<Map<String, Object>>();
		slaOperatingTimeList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SLA_OPERATING_TIME_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IHD_SLA_OPERATING_TIME_LIST_SQL_VARIABLE,
						slaOperatingTimeList);

		List<Map<String, Object>> slaOperatingTimeOrgWiseList = new ArrayList<Map<String, Object>>();
		slaOperatingTimeOrgWiseList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SLA_ORG_OPERATING_TIME_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IHD_SLA_ORG_OPERATING_TIME_LIST_SQL_VARIBALE,
						slaOperatingTimeOrgWiseList);

		List<Map<String, Object>> functionServiceWindowList = new ArrayList<Map<String, Object>>();
		functionServiceWindowList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_SLA_SERVICE_WINDOW_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IHD_SLA_SERVICE_WINDOW_LIST_SQL_VARIABLE,
						functionServiceWindowList);
		List<Map<String, Object>> groupOpeartionList = new ArrayList<Map<String, Object>>();
		groupOpeartionList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_GROUP_OPERATION_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IHD_GROUP_OPERATION_LIST_SQL_VARIABLE,
						groupOpeartionList);


		List<Map<String, Object>> AllColumnList = new ArrayList<Map<String, Object>>();
		AllColumnList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_ALL_COLUMN_LIST_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_ALL_COLUMN_LIST_VARIABLE,
				AllColumnList);

		List<Map<String, Object>> MenuMappingColumnsList = new ArrayList<Map<String, Object>>();
		MenuMappingColumnsList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_COLUMN_MENU_MAPPING_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_COLUMN_MENU_MAPPING_LIST_VARIABLE,
						MenuMappingColumnsList);

		List<Map<String, Object>> listPageDefaultColumnList = new ArrayList<Map<String, Object>>();
		listPageDefaultColumnList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_LISTPAGE_DEFAULT_COLUMN_LIST_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_LISTPAGE_DEFAULT_COLUMN_LIST_VARIABLE,
						listPageDefaultColumnList);

		List<Map<String, Object>> icUserRoleList = new ArrayList<Map<String, Object>>();
		icUserRoleList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IHD_USER_ROLE_DETAILS);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_USER_ROLE_DETAILS_VARIABLE,
				icUserRoleList);

		List<Map<String, Object>> groupInactiveMembList = new ArrayList<Map<String, Object>>();
		groupInactiveMembList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_INACTIVE_GROUP_MEMBER);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IHD_INACTIVE_GROUP_MEMBER_VARIABLE,
				groupInactiveMembList);

		List<Map<String, Object>> groupActiveInactiveMembList = new ArrayList<Map<String, Object>>();
		groupActiveInactiveMembList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_ACTIVE_INACTIVE_GROUP_MEMBER);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IHD_ACTIVE_INACTIVE_GROUP_MEMBER_VARIABLES,
						groupActiveInactiveMembList);

		List<Map<String, Object>> functionLocationMappingList = new ArrayList<Map<String, Object>>();
		functionLocationMappingList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_FUNCTION_LOCATION_MASTER);
		masterData.put(
				COMMON_CacheSQLQueryConstants.IC_IHD_FUNCTION_LOCATION_VARIABLE,
				functionLocationMappingList);

		List<Map<String, Object>> departmentList = new ArrayList<Map<String, Object>>();
		departmentList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_MASTER_SQL);
		masterData.put(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_MASTER_VARIABLE,
				departmentList);


		List<Map<String, Object>> deptWiseCategoryApproverList = new ArrayList<Map<String, Object>>();
		deptWiseCategoryApproverList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS_VARIABLE,
						deptWiseCategoryApproverList);

		List<Map<String, Object>> approverEmployeeList = new ArrayList<Map<String, Object>>();
		approverEmployeeList = jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_APPROVER_EMPLOYEE_DETAILS_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_APPROVER_EMPLOYEE_DETAILS_VARIABLE,
						approverEmployeeList);

		List<Map<String, Object>> categoryRoleList=new ArrayList<Map<String,Object>>();
		categoryRoleList=jdbcTemplate
						.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_ROLE_MAPPING_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_ROLE_MAPPING_VARIABLE,categoryRoleList);
		
				
		List<Map<String, Object>> empDUList=new ArrayList<Map<String,Object>>();
		empDUList=jdbcTemplate
				.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_EMP_DU_MAPPING_SQL);
		masterData
				.put(
						COMMON_CacheSQLQueryConstants.IC_IHD_EMP_DU_MAPPING_VARIABLE,
						empDUList);
	    // Master data is required for project details ---This is for Web
        // Services starting here
        List<Map<String, Object>> projectList = new ArrayList<Map<String, Object>>();
        projectList = jdbcTemplate
                .queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_PROJECT_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_IHD_PROJECT_VARIABLE,
                projectList);
        // Master data is required for project details ---This is for Web
        // Services Ending here
        List<Map<String, Object>> roleListForiTrack = new ArrayList<Map<String, Object>>();
        roleListForiTrack = jdbcTemplate
                .queryForList(COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_iTrack_Cat_SQL);
        masterData.put(
                COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_iTrack_Cat_VARIABLE,
                roleListForiTrack);
        /***********ADMIN Console:Location Addition**********/
        List<Map<String,Object>> categorySubCategorList=new ArrayList<Map<String,Object>>();
        categorySubCategorList=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT_GROUP_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE, categorySubCategorList);
        
        List<Map<String,Object>> categorySubCategoryList=new ArrayList<Map<String,Object>>();
        categorySubCategoryList=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT__SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT__VARIABLE, categorySubCategoryList);
        
        
        List<Map<String,Object>> groupListSubCatDefaultAssignment=new ArrayList<Map<String,Object>>();
        groupListSubCatDefaultAssignment=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_FUNCTION_SUBCAT_GROUP_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_FUNCTION_SUBCAT_GROUP_VARIABLE, groupListSubCatDefaultAssignment);
        
        
        /***********End ADMIN Console:Location Addition******/		
        
        
        
        
        List<Map<String,Object>> defaultStatusOnCat=new ArrayList<Map<String,Object>>();
        defaultStatusOnCat=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_STATUS_FILTER_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_STATUS_FILTER_VARIABLE, defaultStatusOnCat);
        
        List<Map<String,Object>> esclationMatrix=new ArrayList<Map<String,Object>>();
        esclationMatrix=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_VARIABLE, esclationMatrix);
        
        List<Map<String,Object>> esclationFunctionMatrix=new ArrayList<Map<String,Object>>();
        esclationFunctionMatrix=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_FUNCTION_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_FUNCTION_VARIABLE, esclationFunctionMatrix);
        
      //L2: 2786
        List<Map<String,Object>> workflowStatusList=new ArrayList<Map<String,Object>>();
        workflowStatusList=jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_REPORTS_WORKFLOW_STATE_SQL);
        masterData.put(COMMON_CacheSQLQueryConstants.IC_REPORTS_WORKFLOW_STATE_VAR, workflowStatusList);
        
		return masterData;
	}
	
	

	public List<Map<String,Object>> getRolesByCategory(int categoryId){
		String categoryName = getCategoryNameById(categoryId);		
		List<Map<String,Object>> roleList=masterData.get("IcCategoryRoleMappingMaster");
		List<Map<String,Object>> filteredRoleList=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> role : roleList) {
			String catgryName=role.get("WORKFLOW_NAME").toString();
			if(catgryName.equalsIgnoreCase(categoryName))	{				
						filteredRoleList.add(role);
					}
				}
		return filteredRoleList;
	}
	
	public List<Map<String, Object>> getCategoriesById(String column,
			int parentId) {

		List<Map<String, Object>> categoryList = masterData.get("categories");
		List<Map<String, Object>> filteredCatgList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : categoryList) {
			int ParntId = (Integer) ctg.get(column);
			if (ParntId == parentId) {
				filteredCatgList.add(ctg);
			}
		}
		return filteredCatgList;
	}

//Anjana
	
	
	public List<Map<String, Object>> getTableNames() {
		List<Map<String, Object>> tableNames = masterData.get("tablenames");
		return tableNames;
	}

	public List<HELPDESK_Category> getCategoriesByColumn(String column, int parentId) {

		List<Map<String, Object>> categoryList = masterData.get("categories");
		List<HELPDESK_Category> filteredCatgList = new ArrayList<HELPDESK_Category>();

		for (Map<String, Object> ctg : categoryList) {
			int ParntId = (Integer) ctg.get(column);
			if (ParntId == parentId) {
				HELPDESK_Category cb = new HELPDESK_Category();
				cb.setCATEGORY_ID(Integer.toString((Integer) ctg
						.get("CATEGORY_ID")));
				cb.setNAME((String) ctg.get("NAME"));
				cb.setLINK((String) ctg.get("LINK"));
				cb.setPARENT_ID(Integer
						.toString((Integer) ctg.get("PARENT_ID")));
				cb.setIS_CHANGE_REQUEST(Boolean.toString((Boolean) ctg
						.get("IS_CHANGE_REQUEST")));
				filteredCatgList.add(cb);
			}
		}
		return filteredCatgList;
	}

	public String getCategoryNameById(int categoryid) {

		List<Map<String, Object>> categoryList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_CATEGORIES_LIST_VARIABLE);
		String categoryname = "";
		for (Map<String, Object> ctg : categoryList) {
			int categoryID = (Integer) ctg.get("CATEGORY_ID");
			if (categoryID == categoryid) {
				categoryname = (String) ctg.get("NAME");
				break;
			}
		}
		return categoryname;
	}
	/**********Auto Assignment************/
	public double getStatusWeightageForAutoAssignment(int stateID){
		
		List<Map<String, Object>> statusList=masterData.get(COMMON_CacheSQLQueryConstants.IC_WORKFLOW_STATE_MASTER_VARIABLE);
		double statusWeightage=0;
		for(Map<String,Object> status:statusList){
			int statusID=(Integer)status.get("STATE_ID");
			if(statusID == stateID){
				statusWeightage=(Double)status.get("STATUS_WEIGHTAGE");
				break;
			}
		}
		return statusWeightage;
		
	}
	
	public int getPriorityWeightageForAutoAssignment(int priorityID){
		
		List<Map<String, Object>> priorityList=masterData.get(COMMON_CacheSQLQueryConstants.IC_PRIORITY_LIST_VARIABLE);
		int priorityWeightage=0;
		for(Map<String,Object> priority:priorityList){
			int prtyID=(Integer)priority.get("PRIORITY_ID");
			if(prtyID == priorityID){
				priorityWeightage=(Integer)priority.get("PRIORITY_WEIGHTAGE");
				break;
			}
		}
		return priorityWeightage;
		
	}
	/**********END :Auto Assignment************/
	public List<Map<String, Object>> getApproversById(int approverId) {

		List<Map<String, Object>> approverList = masterData
				.get("IHDApproverList");
		List<Map<String, Object>> filteredApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> approver : approverList) {
			int ApprvrId = (Integer) approver.get("APPROVER_ID");
			if (ApprvrId == approverId) {
				filteredApproverList.add(approver);
			}
		}
		return filteredApproverList;
	}

	public String getApproverID(String value, String column) {

		List<Map<String, Object>> approverList = masterData
				.get("IHDApproverList");
		String approver_id = "";
		for (Map<String, Object> approver : approverList) {
			int ApprvrId = (Integer) approver.get("APPROVER_ID");
			String apprvrname = (String) approver.get("DESCRIPTION");
			int workflow_id = (Integer) approver.get("WORKFLOW_STATE");

			if (column.equalsIgnoreCase("DESCRIPTION")) {
				if (apprvrname.equalsIgnoreCase(value)) {
					approver_id = Integer.toString(ApprvrId);
				}
			} else if (column.equals("WORKFLOW_STATE")) {
				if (workflow_id == Integer.parseInt(value)) {
					approver_id = Integer.toString(ApprvrId);
				}
			} else if (column.equalsIgnoreCase("APPROVER_ID")) {
				if (ApprvrId == Integer.parseInt(value)) {
					approver_id = Integer.toString(workflow_id);
				}
			}
		}
		return approver_id;
	}

	public List<Map<String, Object>> getApproversByName(String toSearch) {

		List<Map<String, Object>> approverList = masterData
				.get("IHDApproverList");
		List<Map<String, Object>> filteredApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> approver : approverList) {
			if (approver.get("DESCRIPTION").toString().contains(toSearch)
					|| approver.get("EMPLOYEE_ID").toString()
							.contains(toSearch)) {
				filteredApproverList.add(approver);
			}
		}
		return filteredApproverList;
	}
	
	public List<Map<String, Object>> getSmartSearchAttachmenetIds() {

		List<Map<String, Object>> approverList = masterData
				.get("IHDApproverList");
		List<Map<String, Object>> filteredSmartSearchAttachmentList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> approver : approverList) {
			if (approver.get("DESCRIPTION").toString().contains("Smart Search Attachment")) {
				filteredSmartSearchAttachmentList.add(approver);
			}
		}
		return filteredSmartSearchAttachmentList;
	}

	public List<Map<String, Object>> getCategoriesApproversById(int categoryId,
			int orderid) {

		List<Map<String, Object>> categoryapproverList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_APPROVER_LIST_VARIABLE);
		List<Map<String, Object>> filteredcategApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> categoryapprover : categoryapproverList) {
			int categID = (Integer) categoryapprover.get("CATEGORY_ID");
			int approverorderID = (Integer) categoryapprover
					.get("APPROVER_ORDER");
			if (categID == categoryId && approverorderID == orderid) {
				filteredcategApproverList.add(categoryapprover);
			}
		}
		return filteredcategApproverList;

	}
	
	public List<Map<String, Object>> getSubCategoryApproversById(int categoryId,String workflowStatusName) {

		List<Map<String, Object>> subcategoryapproverList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_SUB_CATEGORY_APPROVER_LIST_VARIABLE);
		List<Map<String, Object>> filteredcategApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> subcategoryapprover : subcategoryapproverList) {
			int categID = (Integer) subcategoryapprover.get("CATEGORY_ID");
			if (categID == categoryId
					&& workflowStatusName.equalsIgnoreCase(subcategoryapprover
							.get("WORKFLOW_STATE").toString())) {
				filteredcategApproverList.add(subcategoryapprover);
			}
		}
		if(filteredcategApproverList.isEmpty()){
			for (Map<String, Object> subcategoryapprover : subcategoryapproverList) {
				int categID = (Integer) subcategoryapprover.get("CATEGORY_ID");
				if (categID == categoryId) {
					filteredcategApproverList.add(subcategoryapprover);
				}
			}
		}
		return filteredcategApproverList;

	}

	public List<Map<String, Object>> getGroupsById(int groupId) {

		List<Map<String, Object>> groupList = masterData.get("GroupList");
		List<Map<String, Object>> filteredGroupList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> group : groupList) {
			int GrpId = (Integer) group.get("GROUP_ID");
			if (GrpId == groupId) {
				filteredGroupList.add(group);
			}
		}
		return filteredGroupList;
	}
	
	public String getGroupIdById(int groupId) {

		List<Map<String, Object>> groupList = masterData.get("GroupList");
		String grpName=null;
		for (Map<String, Object> group : groupList) {
			int GrpId = (Integer) group.get("GROUP_ID");
			if (GrpId == groupId) {
				grpName=group.get("NAME").toString();
			}
		}
		return grpName;
	}

	public List<Map<String, Object>> getGroupsByName(String toSearch) {

		List<Map<String, Object>> groupList = masterData.get("GroupList");
		List<Map<String, Object>> filteredGroupList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> group : groupList) {
			if (group.get("NAME").toString().contains(toSearch)
					|| group.get("LEVEL").toString().contains(toSearch)
					|| group.get("MANAGER").toString().contains(toSearch)
					|| group.get("EMAIL_ADDRESS").toString().contains(toSearch)) {
				filteredGroupList.add(group);
			}
		}
		return filteredGroupList;
	}

	public List<Map<String, Object>> getGroupMembersById(int groupId) {

		List<Map<String, Object>> groupMemberList = masterData
				.get("GroupMemberList");
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> group : groupMemberList) {
			int grpId = (Integer) group.get("GROUP_ID");
			if (grpId == groupId) {
				filteredGroupMemberList.add(group);
			}
		}
		return filteredGroupMemberList;
	}

	public List<Map<String, Object>> getGroupMembersByName(String toSearch) {

		List<Map<String, Object>> groupMemberList = masterData
				.get("GroupMemberList");
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> group : groupMemberList) {
			if (group.get("WORK_LOAD").toString().contains(toSearch)) {
				filteredGroupMemberList.add(group);
			}
		}
		return filteredGroupMemberList;
	}

	public Map<String, Object> getSLADetailsById(int slaId) {

		List<Map<String, Object>> slaList = masterData.get("SLAList");
		Map<String, Object> filteredSLAMap = new HashMap<String, Object>();
		for (Map<String, Object> SLA : slaList) {
			int SlaId = (Integer) SLA.get("SLA_ID");
			if (SlaId == slaId) {
				filteredSLAMap = SLA;
				break;
			}
		}
		return filteredSLAMap;
	}

	public List<Map<String, Object>> getSLADetailsByName(String toSearch) {

		List<Map<String, Object>> slaList = masterData.get("SLAList");
		List<Map<String, Object>> filteredSLAList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> SLA : slaList) {
			if (SLA.get("DESCRIPTION").toString().contains(toSearch)
					|| SLA.get("ORGANIZATION").toString().contains(toSearch)
					|| SLA.get("TIME").toString().contains(toSearch)) {
				filteredSLAList.add(SLA);
			}
		}
		return filteredSLAList;
	}

	public List<Map<String, Object>> getActionDetailsById(int actionId) {

		List<Map<String, Object>> actionList = masterData.get("ISLActionList");
		List<Map<String, Object>> filteredActionList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> Action : actionList) {
			int ActnId = Integer.parseInt(Action.get("ACTION_ID").toString());
			if (ActnId == actionId) {
				filteredActionList.add(Action);
			}
		}
		return filteredActionList;
	}

	public List<Map<String, Object>> getActionDetailsByName(String toSearch) {

		List<Map<String, Object>> actionList = masterData.get("ISLActionList");
		List<Map<String, Object>> filteredActionList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> Action : actionList) {
			if (Action.get("DESCRIPTION").toString().contains(toSearch)
					|| Action.get("IS_APPROVAL_REQUIRED").toString().contains(
							toSearch)) {
				filteredActionList.add(Action);
			}
		}
		return filteredActionList;
	}

	public int getISLApproversById(int employeeid, int groupid) {

		List<Map<String, Object>> approverList = masterData
				.get("ISLApproverList");
		int approverID = 0;
		for (Map<String, Object> approver : approverList) {
			int employeeID = Integer.parseInt(approver.get("EMPLOYEE_ID")
					.toString());
			int groupID = Integer.parseInt(approver.get("GROUP_ID").toString());
			if (employeeID == employeeid && groupID == groupid) {
				approverID = Integer.parseInt(approver.get("APPROVER_ID")
						.toString());
			}
		}
		return approverID;
	}

	public List<Map<String, Object>> getISLApproversByName(String toSearch) {

		List<Map<String, Object>> approverList = masterData
				.get("ISLApproverList");
		List<Map<String, Object>> filteredApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> Approver : approverList) {
			if (Approver.get("DESCRIPTION").toString().contains(toSearch)
					|| Approver.get("EMPLOYEE_ID").toString()
							.contains(toSearch)
					|| Approver.get("LOCATION_ID").toString()
							.contains(toSearch)
					|| Approver.get("GROUP_ID").toString().contains(toSearch)) {
				filteredApproverList.add(Approver);
			}
		}
		return filteredApproverList;
	}

	public List<Map<String, Object>> getISLCategoriesById(int islCategoryId) {

		List<Map<String, Object>> islCategoryList = masterData
				.get("ISLcategories");
		List<Map<String, Object>> filteredISLCatgList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> islctg : islCategoryList) {
			int ParntId = (Integer) islctg.get("CATEGORY_ID");
			if (ParntId == islCategoryId) {
				filteredISLCatgList.add(islctg);
			}
		}
		return filteredISLCatgList;
	}

	public List<Map<String, Object>> getISLEngineersById(int islEngineerId) {

		List<Map<String, Object>> islEngineerList = masterData
				.get("ISLEngineers");
		List<Map<String, Object>> filteredISLEngnrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> islEngctg : islEngineerList) {
			int ParntId = (Integer) islEngctg.get("ENGINEER_ID");
			if (ParntId == islEngineerId) {
				filteredISLEngnrList.add(islEngctg);
			}
		}
		return filteredISLEngnrList;

	}

	public List<Map<String, Object>> getISLEngineersByName(String toSearch) {

		List<Map<String, Object>> islEngineerList = masterData
				.get("ISLEngineers");
		List<Map<String, Object>> filteredISLEngnrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ISLEngctg : islEngineerList) {
			if (ISLEngctg.get("GROUP_ID").toString().contains(toSearch)
					|| ISLEngctg.get("WORK_LOAD").toString().contains(toSearch)) {
				filteredISLEngnrList.add(ISLEngctg);
			}
		}
		return filteredISLEngnrList;

	}

	public List<Map<String, Object>> getISLGroupscategories(String column,
			int value) {
		List<Map<String, Object>> islGroupCategoryList = masterData
				.get("islGroupcategoryList");
		List<Map<String, Object>> filteredISLGroupCategoryList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> islGroup : islGroupCategoryList) {

			int ISLcategId = (Integer) islGroup.get(column);
			if (ISLcategId == value) {

				filteredISLGroupCategoryList.add(islGroup);

			}

		}

		return filteredISLGroupCategoryList;
	}

	public List<Map<String, Object>> getISLGroupsById(int islGroupMemberId) {

		List<Map<String, Object>> islGroupMemberList = masterData
				.get("ISLGroupList");
		List<Map<String, Object>> filteredISLGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> islGroup : islGroupMemberList) {
			int ISLGrpId = (Integer) islGroup.get("GROUP_ID");
			if (ISLGrpId == islGroupMemberId) {
				filteredISLGroupMemberList.add(islGroup);
			}
		}
		return filteredISLGroupMemberList;
	}

	public int getISLGroupsByName(String toSearch) {

		int islgroupid = 0;
		List<Map<String, Object>> islGroupMemberList = masterData
				.get("ISLGroupList");
		for (Map<String, Object> ISLGroup : islGroupMemberList) {
			if (ISLGroup.get("NAME").toString().equalsIgnoreCase(toSearch)) {
				islgroupid = (Integer) ISLGroup.get("GROUP_ID");
				break;
			}
		}
		return islgroupid;
	}

	public String getLocationsById(int locId, String columnname) {

		List<Map<String, Object>> locationList = masterData
				.get("ICLocationList");
		String result = "";
		for (Map<String, Object> loctn : locationList) {
			int LocationId = (Integer) loctn.get("LOCATION_ID");
			if (locId == LocationId) {
				result = (String) loctn.get(columnname);
			}

		}

		return result;
	}

	public List<Map<String, Object>> getLocations() {
		return masterData.get("ICLocationList");
	}

	public List<COMMON_Location> getICLocations() {

		List<Map<String, Object>> locationList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_VARIABLE);
		List<COMMON_Location> filteredLocList = new ArrayList<COMMON_Location>();

		for (Map<String, Object> loc : locationList) {
			COMMON_Location locbean = new COMMON_Location();
			if (loc.get("LOCATION_ID") != null)
				locbean.setLOCATION_ID(Integer.toString((Integer) loc
						.get("LOCATION_ID")));
			// locbean.setORGANIZATION((String) loc.get("ORGANIZATION"));
			if (loc.get("COUNTRY") != null)
				locbean.setCOUNTRY((String) loc.get("COUNTRY"));
			if (loc.get("CITY") != null)
				locbean.setCITY((String) loc.get("CITY"));
			if (loc.get("AREA") != null)
				locbean.setAREA((String) loc.get("AREA"));
			if (loc.get("SHORT_NAME") != null)
				locbean.setSHORT_NAME((String) loc.get("SHORT_NAME"));
			if (loc.get("TIMEZONE_ID") != null)
				locbean.setTIMEZONE_ID(Integer.toString((Integer) loc
						.get("TIMEZONE_ID")));
			// locbean.setPS_LOCATION_ID((String) loc.get("PS_LOCATION_ID"));
			// locbean.setSET_ID((String) loc.get("SET_ID"));
			filteredLocList.add(locbean);
		}
		return filteredLocList;
	}

	public List<Map<String, Object>> getLocationsByName(String toSearch) {

		List<Map<String, Object>> locationList = masterData
				.get("ICLocationList");
		List<Map<String, Object>> filteredLocationList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctn : locationList) {
			if (/*
				 * loctn.get("ORGANIZATION").toString().contains(toSearch) ||
				 */loctn.get("COUNTRY").toString().contains(toSearch)
					|| loctn.get("CITY").toString().contains(toSearch)
					|| loctn.get("AREA").toString().contains(toSearch)
					|| loctn.get("SHORT_NAME").toString().contains(toSearch)) {
				filteredLocationList.add(loctn);
			}

		}

		return filteredLocationList;
	}

	public List<Map<String, Object>> getLocationDetailsById(String column,
			int locDetId) {

		List<Map<String, Object>> locationDetlList = masterData
				.get("ICLocationDetailList");
		List<Map<String, Object>> filteredLocationDetList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctndet : locationDetlList) {
			int LocationId = (Integer) loctndet.get(column);
			if (LocationId == locDetId) {
				filteredLocationDetList.add(loctndet);
			}

		}

		return filteredLocationDetList;
	}

	public List<Map<String, Object>> getDistinctBuildingLocations(int locationid) {

		List<Map<String, Object>> locationDetlList = masterData
				.get("ICLocationDetailList");
		List<String> buildings = new ArrayList<String>();
		List<Map<String, Object>> filteredLocationDetList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctndet : locationDetlList) {
			int LocationId = (Integer) loctndet.get("LOCATION_ID");
			if (LocationId == locationid
					&& !buildings.contains(loctndet.get("BUILDING"))) {
				buildings.add((String) loctndet.get("BUILDING"));
				filteredLocationDetList.add(loctndet);
			}

		}
		return filteredLocationDetList;
	}

	public List<Map<String, Object>> getFloorsforBuilding(String buildingname) {

		List<Map<String, Object>> locationDetlList = masterData
				.get("ICLocationDetailList");
		List<Map<String, Object>> filteredLocationDetList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctndet : locationDetlList) {
			String buildingName = (String) loctndet.get("BUILDING");
			if (buildingName != null && buildingName.equals(buildingname)) {
				filteredLocationDetList.add(loctndet);
			}

		}
		return filteredLocationDetList;
	}
	
	
	public List<Map<String, Object>> getBuildingDetailsById(String column,
			int locDetId) {

		List<Map<String, Object>> locationDetlList = masterData
				.get("ICLocationDetailList");
		List<Map<String, Object>> filteredLocationDetList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctndet : locationDetlList) {
			int LocationId = (Integer) loctndet.get(column);
			if (LocationId == locDetId) {
				filteredLocationDetList.add(loctndet);
			}

		}

		return filteredLocationDetList;
	}


	public List<Map<String, Object>> getLocationDetailsByName(String toSearch) {

		List<Map<String, Object>> locationDetlList = masterData
				.get("ICLocationDetailList");
		List<Map<String, Object>> filteredLocationDetList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> loctndet : locationDetlList) {
			if (loctndet.get("BUILDING").toString().contains(toSearch)
					|| loctndet.get("FLOOR").toString().contains(toSearch)
					|| loctndet.get("SHORT_NAME").toString().contains(toSearch)
					|| loctndet.get("LAST_CUBICLE_NO").toString().contains(
							toSearch)) {
				filteredLocationDetList.add(loctndet);
			}

		}

		return filteredLocationDetList;

	}

	public List<Map<String, Object>> getPriorityList() {

		return masterData.get("ICPriorityList");
	}

	public List<HELPDESK_Priority> getAllPriorities() {

		List<Map<String, Object>> priorityList = masterData
				.get("ICPriorityList");
		List<HELPDESK_Priority> ICPriorityList = new ArrayList<HELPDESK_Priority>();

		for (Map<String, Object> priority : priorityList) {
			HELPDESK_Priority prioritybean = new HELPDESK_Priority();
			prioritybean.setPRIORITYID(Integer.toString((Integer) priority
					.get("PRIORITY_ID")));
			prioritybean.setDESCRIPTION((String) priority.get("DESCRIPTION"));
			ICPriorityList.add(prioritybean);
		}
		return ICPriorityList;
	}

	public String getPriorityNameById(int priorityid) {

		List<Map<String, Object>> priorityList = masterData
				.get("ICPriorityList");
		String priorityname = "";
		for (Map<String, Object> priority : priorityList) {
			int priority_id = (Integer) priority.get("PRIORITY_ID");
			if (priorityid == priority_id) {
				priorityname = (String) priority.get("DESCRIPTION");
			}

		}
		return priorityname;
	}

	public List<Map<String, Object>> getRolesById(int roleId) {

		List<Map<String, Object>> rolesList = masterData.get("ICRoleList");
		List<Map<String, Object>> filteredRoleList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> Role : rolesList) {
			int role_Id = (Integer) Role.get("ROLE_ID");
			if (role_Id == roleId) {
				filteredRoleList.add(Role);
			}

		}

		return filteredRoleList;
	}

	public List<Map<String, Object>> getSeveritiesById(int severityId) {

		List<Map<String, Object>> severityList = masterData
				.get("ICSeverityList");
		List<Map<String, Object>> filteredSeverityList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> Severity : severityList) {
			int severity_Id = (Integer) Severity.get("SEVERITY_ID");
			if (severity_Id == severityId) {
				filteredSeverityList.add(Severity);
			}
		}

		return filteredSeverityList;
	}

	public Map<String, Object> getDefaultAssignDetails(int categoryid,
			int locationid) {

		Map<String, Object> assignments = new HashMap<String, Object>();
		List<Map<String, Object>> defassignList = masterData
				.get("ICDefaultAssignDetails");
		for (Map<String, Object> Assignment : defassignList) {
			int category_id = (Integer) Assignment.get("CATEGORY_ID");
			int location_id = (Integer) Assignment.get("LOCATION_ID");
			if (category_id == categoryid && locationid == location_id) {
				return Assignment;
			}
		}

		return assignments;
	}
	
	public List<Map<String, Object>> getDefaultAssignDetailsForHR(int categoryid,
			int locationid) {

		List<Map<String, Object>> defassignList = masterData
				.get("ICDefaultAssignDetails");
		List<Map<String, Object>> filterDefassignList= new ArrayList<Map<String,Object>>();
		for (Map<String, Object> Assignment : defassignList) {
			int category_id = (Integer) Assignment.get("CATEGORY_ID");
			int location_id = (Integer) Assignment.get("LOCATION_ID");
			if (category_id == categoryid && locationid == location_id) {
				filterDefassignList.add(Assignment);
			}
		}

		return filterDefassignList;
	}
	
	
	public List<Map<String, Object>> getIHDDeptDefaultAssignmentDetailsForHR(int deptid,
			int categoryid, int locationid) {

		List<Map<String, Object>> filterDeptDefassignList= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> deptDefassignList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_DEFAULT_ASSIGNMENT_VARIABLE);
		for (Map<String, Object> Assignment : deptDefassignList) {
			int DEPT_ID = (Integer) Assignment.get("DEPT_ID");
			int CATEGORY_ID = (Integer) Assignment.get("CATEGORY_ID");
			int LOCATION_ID = (Integer) Assignment.get("LOCATION_ID");
			if (deptid == DEPT_ID && locationid == LOCATION_ID
					&& categoryid == CATEGORY_ID) {
				filterDeptDefassignList.add(Assignment);
			}
		}

		return filterDeptDefassignList;
	}
	
	
	public List<Map<String, Object>> getDefaultAssignDetailsForAdminFinance(
			int categoryid, int locationid){
		List<Map<String, Object>> assignments = new ArrayList();
		List<Map<String, Object>> defassignList = masterData
				.get("ICDefaultAssignDetails");
		for(int i=0;i<defassignList.size();i++){
			int category_id = (Integer) defassignList.get(i).get("CATEGORY_ID");
			int location_id = (Integer) defassignList.get(i).get("LOCATION_ID");
			if (category_id == categoryid && locationid == location_id) {
				assignments.add(defassignList.get(i));
			}
		}
		return assignments;
		
	}

	// Added by Sali
	public List<Map<String, Object>> getSearchUIFieldList() {
		return masterData
				.get(COMMON_CacheSQLQueryConstants.IC_SEARCH_FIELD_VARIABLE);
	}

	public List<Map<String, Object>> getIHDGroupMaster() {
		return masterData
				.get(COMMON_CacheSQLQueryConstants.IC_GROUP_LIST_VARIABLE);
	}

	public List<HELPDESK_Group> getAllIHDGroups() {

		List<Map<String, Object>> ihdGroupList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_GROUP_LIST_VARIABLE);
		List<HELPDESK_Group> filteredIHDGroupList = new ArrayList<HELPDESK_Group>();

		for (Map<String, Object> ihdgroup : ihdGroupList) {
			HELPDESK_Group ihdgroupbean = new HELPDESK_Group();
			ihdgroupbean.setGROUP_ID(Integer.toString((Integer) ihdgroup
					.get("GROUP_ID")));
			ihdgroupbean.setNAME((String) ihdgroup.get("NAME"));
			ihdgroupbean.setLEVEL((String) ihdgroup.get("LEVEL"));
			ihdgroupbean.setMANAGER((String) ihdgroup.get("MANAGER"));
			ihdgroupbean.setEMAIL_ADDRESS((String) ihdgroup
					.get("EMAIL_ADDRESS"));
			ihdgroupbean.setLOCATION_ID(Integer.toString((Integer) ihdgroup
					.get("LOCATION_ID")));
			filteredIHDGroupList.add(ihdgroupbean);
		}
		return filteredIHDGroupList;
	}

	public List<Map<String, Object>> getRoleMaster() {
		return masterData
				.get(COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_VARIABLE);
	}

	public List<Map<String, Object>> getIHDGroupMemberID(String groupName) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE);
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("GROUP_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupName.trim().toUpperCase())) {
				filteredGroupMemberList.add(ctg);
			}
		}
		return filteredGroupMemberList;
	}
	public List<Map<String, Object>> getIHDGroupMemberID_Admin(String groupName) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE_ADMIN);
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("GROUP_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupName.trim().toUpperCase())) {
				filteredGroupMemberList.add(ctg);
			}
		}
		return filteredGroupMemberList;
	}

	public void getMenu(JdbcTemplate jdbcTemplate) {
		String getMenuQuery = "select MRD.menu_id,MM.name,MM.link,MM.parent_id,MRD.role_id from IC_MENU_ROLE_DETAILS MRD,IC_MENU_MASTER MM where MRD.MENU_ID = MM.MENU_ID AND MRD.is_active = 1";
		menuList = jdbcTemplate.query(getMenuQuery, new RowMapper<COMMON_Menu>() {

			public COMMON_Menu mapRow(ResultSet rs, int count) throws SQLException {
				COMMON_Menu obj = new COMMON_Menu();
				obj.setMenuId(rs.getString(1));
				obj.setMenuName(rs.getString(2));
				obj.setLink(rs.getString(3));
				obj.setParentId(rs.getString(4));
				obj.setRoleId(rs.getString(5));
				return obj;
			}

		});
	}

	public List<COMMON_Menu> getParentMenuList(String roleId) {
		List<COMMON_Menu> toSendList = new ArrayList<COMMON_Menu>();
		for (COMMON_Menu menu : menuList) {
			String roleIdFromObj = menu.getRoleId();
			String parentMenuId = menu.getParentId();
			if (parentMenuId.equalsIgnoreCase("0")
					&& roleIdFromObj.equalsIgnoreCase(roleId)) {
				toSendList.add(menu);

			}
		}

		return toSendList;
	}

	public List<COMMON_Menu> getChildMenuList(String roleId, String parentMenuId) {
		List<COMMON_Menu> toSendList = new ArrayList<COMMON_Menu>();
		for (COMMON_Menu menu : menuList) {
			String roleIdFromObj = menu.getRoleId();
			String parentMenuIdObj = menu.getParentId();

			if (parentMenuIdObj.equalsIgnoreCase(parentMenuId)
					&& roleIdFromObj.equalsIgnoreCase(roleId)) {
				toSendList.add(menu);

			}
		}

		return toSendList;
	}

	public String getGroupsById(String groupMemberId) {
		String groupId_string = "";
		List<Map<String, Object>> groupMemberList = masterData
				.get("GroupMemberList");
		for (Map<String, Object> group : groupMemberList) {
			if (group.get("MEMBER_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupMemberId.trim().toUpperCase())) {
				groupId_string += "'" + group.get("GROUP_ID") + "',";
			}

		}
		return groupId_string;
	}

	public int getHeaderAndTableSize(String menuID) {
		List<Map<String, Object>> headerColumnList = masterData
				.get("HeaderMapColumSize");
		int count_of_column = 0;
		for (Map<String, Object> header : headerColumnList) {
			if (header.get("MENU_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(menuID.trim().toUpperCase())) {
				count_of_column = Integer.parseInt(header.get("colum_size")
						.toString());
			}
		}
		return count_of_column;
	}

	public List<Map<String, Object>> getHeaderDetails(String menuID) {
		List<Map<String, Object>> headerColumnDetailListList = masterData
				.get("HeaderColumnDetailList");
		List<Map<String, Object>> filteredHeaderColumnDetailList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> header : headerColumnDetailListList) {
			if (header.get("MENU_ID") != null) {
				if (header.get("MENU_ID").toString().trim().toUpperCase()
						.equalsIgnoreCase(menuID.trim().toUpperCase())) {
					filteredHeaderColumnDetailList.add(header);
				}
			}
		}
		return filteredHeaderColumnDetailList;
	}

	public List<Map<String, Object>> getTableDetails(String menuID) {

		List<Map<String, Object>> headerMapTableList = masterData
				.get("HeaderMapTableList");
		List<Map<String, Object>> filteredHeaderTableList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> header : headerMapTableList) {
			if (header.get("MENU_ID") != null) {
				if (header.get("MENU_ID").toString().trim().toUpperCase()
						.equalsIgnoreCase(menuID.trim().toUpperCase())) {
					filteredHeaderTableList.add(header);
				}
			}
		}
		return filteredHeaderTableList;
	}

	public Map<String, Object> getHeaderMap(long menuID) {
		Map<String, Object> headerMap = new HashMap<String, Object>();
		int column_size = getHeaderAndTableSize(String.valueOf(menuID));
		headerMap.put(menuID + "Column_Size", column_size);
		List<Map<String, Object>> tableList = getTableDetails(String
				.valueOf(menuID));
		int j = 1;
		// For loop iterates through tableList and adds table name and table
		// alias to the headerMap
		for (Map<String, Object> stringObj : tableList) {
			headerMap.put(menuID + "Table" + j, stringObj.get("tbl"));
			headerMap.put(menuID + "Table" + j + "_Alias", stringObj
					.get("tbl_alias"));
			headerMap.put(menuID + "Table" + j + "_Identifier", stringObj
					.get("tbl_identifier"));
			j++;
		}
		headerMap.put(menuID + "Table_Size", tableList.size());
		List<Map<String, Object>> headerDetailList = getHeaderDetails(String
				.valueOf(menuID));

		int i = 1;
		// For loop iterated through headerDetailList and adds column details to
		// the headerMap
		for (Map<String, Object> stringObj : headerDetailList) {
			headerMap.put(menuID + "Column" + i + "_MAIN_TABLE_COLUMN",
					stringObj.get("COLUMN_NAME"));
			headerMap.put(menuID + "Column" + i + "_FK", stringObj
					.get("IS_FOREIGN_KEY"));
			headerMap.put(menuID + "Column" + i + "_UI_FIELD", stringObj
					.get("UI_FIELD"));
			headerMap.put(menuID + "Column" + i + "_REF_TBL", stringObj
					.get("REF_TBL"));
			headerMap.put(menuID + "Column" + i + "_IS_JOIN_REQUIRED",
					stringObj.get("IS_JOIN_REQUIRED"));
			// added

			headerMap.put(menuID + "Column" + i + "_FIELD_DATA_TYPE", stringObj
					.get("FIELD_DATA_TYPE"));
			if (stringObj.get("REF_TBL_ALTER_ALIAS") != null) {

				if (stringObj.get("REF_TBL_ALTER_ALIAS").toString().trim()
						.toUpperCase().equalsIgnoreCase("NA")) {
					headerMap.put(menuID + "Column" + i + "_REF_TBL_ALIAS",
							stringObj.get("REF_TBL_ALIAS"));
				} else {
					headerMap.put(menuID + "Column" + i + "_REF_TBL_ALIAS",
							stringObj.get("REF_TBL_ALTER_ALIAS"));
				}
			} else {
				headerMap.put(menuID + "Column" + i + "_REF_TBL_ALIAS",
						stringObj.get("REF_TBL_ALIAS"));
			}

			headerMap.put(menuID + "Column" + i + "_REFERENCE_COLUMN",
					stringObj.get("REFERENCE_COLUMN"));
			headerMap.put(menuID + "Column" + i + "_MAIN_TBL", stringObj
					.get("MAIN_TBL"));
			headerMap.put(menuID + "Column" + i + "_MAIN_TBL_ALIAS", stringObj
					.get("MAIN_TBL_ALIAS"));

			headerMap.put(menuID + "Column" + i + "_SELECT_COLUMN", stringObj
					.get("SELECT_COLUMN"));
			headerMap.put(menuID + "Column" + i + "_DISPLAY_REQUIRED",
					stringObj.get("DISPLAY_REQUIRED"));
			i++;
		}

		int no_of_conditions = 0;
		headerMap.put(menuID + "NO_OF_CONDITIONS", no_of_conditions);
		return headerMap;
	}

	public List<Map<String, Object>> getAction() {

		return masterData.get("ISLActionList");

	}

	public List<Map<String, Object>> getISLGroupsMaster(String column, int value) {

		List<Map<String, Object>> islGroupList = masterData.get("ISLGroupList");

		List<Map<String, Object>> filteredISLGroupCategoryList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> islGroup : islGroupList) {

			int ISLcategId = (Integer) islGroup.get(column);

			if (ISLcategId == value) {

				filteredISLGroupCategoryList.add(islGroup);

			}

		}

		return filteredISLGroupCategoryList;
	}

	public List<Map<String, Object>> getISLCategoriesByName(String toSearch) {

		List<Map<String, Object>> islCategoryList = masterData
				.get("ISLcategories");
		List<Map<String, Object>> filteredISLCatgList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> category : islCategoryList) {
			if (category.get("NAME").toString().contains(toSearch)) {
				filteredISLCatgList.add(category);
			}
		}
		return filteredISLCatgList;
	}

	public List<Map<String, Object>> getISLCategoriesByParentId(
			int islCategoryId) {

		List<Map<String, Object>> islCategoryList = masterData
				.get("ISLsubcategories");
		List<Map<String, Object>> filteredISLCatgList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> islctg : islCategoryList) {
			int ParntId = (Integer) islctg.get("PARENT_ID");
			if (ParntId == islCategoryId) {
				filteredISLCatgList.add(islctg);
			}
		}
		return filteredISLCatgList;
	}

	public List<Map<String, Object>> getISLApproversByGroup(int islGroupId) {

		List<Map<String, Object>> approverList = masterData
				.get("ISLApproverList");
		List<Map<String, Object>> filteredApproverList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> approver : approverList) {
			int apprverId = (Integer) approver.get("GROUP_ID");
			if (apprverId == islGroupId) {
				filteredApproverList.add(approver);
			}
		}
		return filteredApproverList;
	}

	public List<Map<String, Object>> getISLSubCategoriesByName(String toSearch) {

		List<Map<String, Object>> islCategoryList = masterData
				.get("ISLsubcategories");
		List<Map<String, Object>> filteredISLCatgList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> category : islCategoryList) {
			if (category.get("NAME").toString().contains(toSearch)) {
				filteredISLCatgList.add(category);
			}
		}
		return filteredISLCatgList;
	}

	public List<Map<String, Object>> getSeverityList() {

		return masterData.get("ICSeverityList");
	}

	public List<Map<String, Object>> getTicketsUIFieldsById(String fieldId) {

		List<Map<String, Object>> uiFieldsList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_UIFIELDS_LIST_VARIABLE);
		List<Map<String, Object>> filteredUiFieldsList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> uiFieldsdet : uiFieldsList) {
			String field_Id = uiFieldsdet.get("FIELD_ID").toString();
			if (field_Id.equals(fieldId)) {
				filteredUiFieldsList.add(uiFieldsdet);
			}
		}
		return filteredUiFieldsList;

	}


	public String getGroupByIdString(String groupMemberId) {
		String groupId_string = "";
		List<Map<String, Object>> groupMemberList = masterData
				.get("GroupMemberList");
		for (Map<String, Object> group : groupMemberList) {
			if (group.get("MEMBER_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupMemberId.trim().toUpperCase())) {
				groupId_string += "'" + group.get("GROUP_ID") + "',";
			}

		}
		return groupId_string;
	}

	public List<Map<String, Object>> getIHDCategoryPriorityList() {

		return masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_PRIORITY_LIST_VARIABLE);
	}



	public List<Map<String, Object>> getListofData(String keyname) {

		return masterData.get(keyname);
	}

	public Map<String, String> getUIAndDBFieldForAuditLog(String menuId) {
		Map<String, String> toSend = new HashMap<String, String>();
		List<Map<String, Object>> UI_FIELD_LIST = masterData
				.get("ICSeacrhField");
		for (Map<String, Object> rowobj : UI_FIELD_LIST) {
			String menu_ID = rowobj.get("MENU_ID").toString();
			if (menu_ID.equalsIgnoreCase(menuId))
				toSend.put(rowobj.get("DB_FIELDS").toString(), rowobj.get(
						"UI_FIELD").toString());
		}
		return toSend;
	}

	public Map<String, Object> getIHDCategoryPriority(int categoryid,
			int priorityid, String org) {

		List<Map<String, Object>> categorySLATimes = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_PRIORITY_LIST_VARIABLE);
		Map<String, Object> categoryPriorityMap = new HashMap<String, Object>();
		for (Map<String, Object> map : categorySLATimes) {
			int priority = (Integer) map.get("PRIORITY_ID");
			int category = (Integer) map.get("CATEGORY_ID");
			String organization = (String) map.get("ORGANIZATION");

			if (priority == priorityid
					&& category == categoryid
					&& (organization.equalsIgnoreCase(org) || organization
							.equalsIgnoreCase("C"))) {
				categoryPriorityMap = map;
				break;
			}
		}

		return categoryPriorityMap;

	}

	public List<Map<String, Object>> getGroupsForFunction(int functionid,
			int locationid) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int CATEGROY_ID = (Integer) rowobj.get("CATEGROY_ID");
			int LOCATION_ID = (Integer) rowobj.get("LOCATION_ID");
			if (CATEGROY_ID == functionid && locationid == LOCATION_ID) {
				toSend.add(rowobj);
			}
		}
		return toSend;
	}
/**************Auto Assignment******************************************************/	
	public List<Map<String, Object>> getGroupsForFunctionforAA(int groupID) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int GROUP_ID = (Integer) rowobj.get("GROUP_ID");			
			if (GROUP_ID == groupID) {				
				toSend.add(rowobj);
				break;
			}
		}
		return toSend;
	}
	/**************Auto Assignment******************************************************/	
	public List<Map<String, Object>> getGroupsForFunction(int functionid,
			List<String> locationidlist) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int CATEGROY_ID = (Integer) rowobj.get("CATEGROY_ID");
			int LOCATION_ID = (Integer) rowobj.get("LOCATION_ID");
			if (CATEGROY_ID == functionid ) {
				for(int i=0;i<locationidlist.size();i++){
					if(LOCATION_ID==Integer.parseInt(locationidlist.get(i))){
						toSend.add(rowobj);
					}
				}
				
			}
		}
		return toSend;
	}
	
	public List<Map<String, Object>> getGroupsForFunction(int functionid) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MASTER_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int CATEGROY_ID = (Integer) rowobj.get("CATEGROY_ID");			
			if (CATEGROY_ID == functionid) {
				toSend.add(rowobj);
			}
		}
		return toSend;
	}
	

	public List<Map<String, Object>> getAllGroupsForFunction(int functionid) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int CATEGROY_ID = (Integer) rowobj.get("CATEGROY_ID");
			if (CATEGROY_ID == functionid) {
				toSend.add(rowobj);
			}
		}
		return toSend;
	}


	private void prepareMenuRoleQueryMapping(JdbcTemplate jdbcTemplate) {
		String query = "select MENU_ID,ROLE_ID,QUERY from IC_MENU_ROLE_DETAILS ";
		menuRoleQueryMapping = jdbcTemplate.query(query, new RowMapper<COMMON_Menu>() {

			public COMMON_Menu mapRow(ResultSet rs, int count) throws SQLException {
				COMMON_Menu obj = new COMMON_Menu();
				obj.setMenuId(rs.getString(1));
				obj.setRoleId(rs.getString(2));
				obj.setQuery(rs.getString(3));
				return obj;
			}

		});
	}

	public String getQueryForMenuAndRole(String menuId, String roleId) {
		for (COMMON_Menu obj : menuRoleQueryMapping) {
			if (obj.getMenuId().equalsIgnoreCase(menuId)
					&& obj.getRoleId().equalsIgnoreCase(roleId)) {
				return obj.getQuery();
			}

		}
		return "";
	}
	public synchronized boolean resetQueryForMenuAndRole() {
		menuRoleQueryMapping.clear();
		//try {
			prepareMenuRoleQueryMapping(this.jdbcTemplate);
		/*} catch (Exception e) {
			return false;
		}*/
		return true;
	}

	public synchronized boolean resetgetMasterData() {
		masterData.clear();
			getmasterData(this.jdbcTemplate);
		return true;
	}


	public Map<String, Object> getTimeZone(int timezoneID) {

		List<Map<String, Object>> timezonelist = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_TIME_ZONE_LIST_VARIABLE);
		Map<String, Object> resultzone = new HashMap<String, Object>();
		for (Map<String, Object> timezn : timezonelist) {
			int zoneid = (Integer) timezn.get("TIMEZONE_ID");
			if (zoneid == timezoneID) {
				resultzone = timezn;
			}
		}
		return resultzone;
	}

	public List<Map<String, Object>> getSLAExclusionDateList(long slaId,
			long locationId) {

		List<Map<String, Object>> slaExclusionList = masterData
				.get("SLAExclusionList");
		List<Map<String, Object>> filteredSLAMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> SLA : slaExclusionList) {
			if (SLA.get("LOCATION_ID") != null && SLA.get("SLA_ID") != null) {
				int slaId_cache = (Integer) SLA.get("SLA_ID");
				int locationId_cache = (Integer) SLA.get("LOCATION_ID");
				if (slaId_cache == slaId && locationId_cache == locationId) {
					filteredSLAMap.add(SLA);
					// break;
				}
			}

		}
		return filteredSLAMap;
	}

	public List<Map<String, Object>> getSLAOperatingTimeList(long slaId) {

		List<Map<String, Object>> slaOperatingTimeList = masterData
				.get("SLAOperatingTimeList");
		List<Map<String, Object>> filteredSLAMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> SLA : slaOperatingTimeList) {
			if (SLA.get("SLA_ID") != null) {

				if (Long.parseLong(SLA.get("SLA_ID").toString()) == slaId) {
					filteredSLAMap.add(SLA);
					// break;
				}
			}

		}
		return filteredSLAMap;
	}

	public List<Map<String, Object>> getSLAOpertingTimeOrgWise(long sla_id,
			String org, String day) {
		List<Map<String, Object>> slaOperatingTimeList = masterData
				.get("SLAOperatingTimeOrgWise");
		List<Map<String, Object>> filteredSLAMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> SLA : slaOperatingTimeList) {
			if (SLA.get("SLA_ID") != null && SLA.get("DAY") != null
					&& SLA.get("START_TIME") != null
					&& SLA.get("END_TIME") != null
					&& SLA.get("NEXT_WORKING_DAY") != null) {
				long slaId_cache = Long.parseLong(SLA.get("SLA_ID").toString());
				if (slaId_cache == sla_id
						&& SLA.get("DAY").toString().trim().toUpperCase()
								.equalsIgnoreCase(day.trim().toUpperCase())) {

					if (org.trim().toUpperCase().equalsIgnoreCase(
							SLA.get("ORGANIZATION").toString().trim()
									.toUpperCase())
							|| SLA.get("ORGANIZATION").toString().trim()
									.toUpperCase().equalsIgnoreCase("C")) {

						filteredSLAMap.add(SLA);
						break;
					}
				}
			}

		}
		return filteredSLAMap;
	}

	public long getServiceOPRID(long locationID, long locationDetailId,
			long functionID) {

		List<Map<String, Object>> functionServiceWindowList = masterData
				.get("FunctionServiceWindow");
		long opr_id = 0l;
		for (Map<String, Object> SLA : functionServiceWindowList) {
			if (SLA.get("LOCATION_ID") != null && SLA.get("LOC_DET_ID") != null
					&& SLA.get("FUNCTION_ID") != null) {
				if (Long.parseLong(SLA.get("LOCATION_ID").toString()) == locationID
						&& Long.parseLong(SLA.get("LOC_DET_ID").toString()) == locationDetailId
						&& Long.parseLong(SLA.get("FUNCTION_ID").toString()) == functionID) {
					opr_id = Long.parseLong(SLA.get("OPERATION_ID").toString());
					break;
				}
			}

		}
		return opr_id;
	}

	public long getGroupOPRID(long groupID, long functionID, long locationID) {

		List<Map<String, Object>> groupOpearationList = masterData
				.get("GroupOpearationId");
		long opr_id = 0l;
		for (Map<String, Object> SLA : groupOpearationList) {
			if (SLA.get("GROUP_ID") != null && SLA.get("CATEGORY_ID") != null
					&& SLA.get("LOCATION_ID") != null) {
				if (Long.parseLong(SLA.get("GROUP_ID").toString()) == groupID
						&& Long.parseLong(SLA.get("CATEGORY_ID").toString()) == functionID
						&& Long.parseLong(SLA.get("LOCATION_ID").toString()) == locationID) {
					opr_id = Long.parseLong(SLA.get("SLA_ID").toString());
					break;
				}
			}

		}
		return opr_id;
	}


	public List<Map<String, Object>> getColumnsForMenu(int menuid) {

		List<Map<String, Object>> filteredColumns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> COLUMN_MENU_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_COLUMN_MENU_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : COLUMN_MENU_LIST) {
			int MENU_ID = (Integer) rowobj.get("MENU_ID");
			if (menuid == MENU_ID) {
				filteredColumns.add(rowobj);
			}
		}
		return filteredColumns;
	}

	public List<Map<String, Object>> getDefaultColumnsForMenu(int menuid) {

		List<Map<String, Object>> filteredColumns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> COLUMN_MENU_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_LISTPAGE_DEFAULT_COLUMN_LIST_VARIABLE);
		for (Map<String, Object> rowobj : COLUMN_MENU_LIST) {
			int MENU_ID = (Integer) rowobj.get("MENU_ID");
			if (menuid == MENU_ID) {
				filteredColumns.add(rowobj);
			}
		}
		return filteredColumns;
	}

	public List<Map<String, Object>> getUserRoleById(String empId) {
		List<Map<String, Object>> roleList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_USER_ROLE_DETAILS_VARIABLE);
		List<Map<String, Object>> filteredRoleList = new ArrayList<Map<String, Object>>();
        //Changed by Dhiren
		for (Map<String, Object> aRoleList : roleList) {
			//Map<String, Object> ctg = aRoleList;
			if (aRoleList.get("EMPLOYEE_ID").toString().equalsIgnoreCase(empId)) {
				filteredRoleList.add(aRoleList);
			}
		}
		return filteredRoleList;
	}

	public List<Map<String, Object>> getIHDGroup(String memID) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE);
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("member_id").toString().trim().toUpperCase()
					.equalsIgnoreCase(memID.trim().toUpperCase())) {
				filteredGroupMemberList.add(ctg);
			}
		}
		return filteredGroupMemberList;
	}

	public List<Map<String, Object>> getRoles() {

		return masterData
				.get(COMMON_CacheSQLQueryConstants.IC_ROLE_LIST_VARIABLE);
	}

	public List<Map<String, Object>> getIHDInActiveGroupMemberID(
			String groupName) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_INACTIVE_GROUP_MEMBER_VARIABLE);
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("GROUP_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupName.trim().toUpperCase())) {
				filteredGroupMemberList.add(ctg);
			}
		}
		return filteredGroupMemberList;
	}

	public List<Map<String, Object>> getIHDActiveInactiveGroupMemberID(
			String groupName) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_ACTIVE_INACTIVE_GROUP_MEMBER_VARIABLES);
		List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("GROUP_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupName.trim().toUpperCase())) {
				filteredGroupMemberList.add(ctg);
			}
		}
		return filteredGroupMemberList;
	}

	public Map<String, Object> getOutofSlaRowById(int outofslaid) {

		List<Map<String, Object>> categoryList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_OUT_OF_SLA_REASON_LIST_VARIABLE);
        for (Map<String, Object> reason : categoryList) {
            int reasonId = (Integer) reason.get("REASON_ID");
            if (outofslaid == reasonId) {
                return reason;
            }
        }
		return new HashMap<String, Object>();
	}
	
	public List<Map<String, Object>> getOUTSLAReason(String functionID) {
		List<Map<String, Object>> slareasons = masterData
		.get(COMMON_CacheSQLQueryConstants.IC_IHD_OUT_OF_SLA_REASON_LIST_VARIABLE);
		List<Map<String, Object>> filteredslareasonsList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> reason : slareasons) {
			String special_prefrences = reason.get("SPECIAL_PREFERENCES")
					.toString();
			if (special_prefrences.equalsIgnoreCase("C") || special_prefrences.equalsIgnoreCase(functionID))
				{	
				filteredslareasonsList.add(reason);
				}
		}
		return filteredslareasonsList;
	}

	public List<COMMON_Location> getLocationsForFunction(int functionID) {

		List<Map<String, Object>> funcitonLocationMappingList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_FUNCTION_LOCATION_VARIABLE);
		List<Integer> filteredLocationsList = new ArrayList<Integer>();
		for (Map<String, Object> locationmap : funcitonLocationMappingList) {
			if ((Integer) locationmap.get("FUNCTION_ID") == functionID) {
				filteredLocationsList.add((Integer) locationmap
						.get("LOCATION_ID"));
			}
		}

		List<Map<String, Object>> locationList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_VARIABLE);
		List<COMMON_Location> filteredLocList = new ArrayList<COMMON_Location>();

		for (Map<String, Object> loc : locationList) {
			if (filteredLocationsList
					.contains((Integer) loc.get("LOCATION_ID"))) {
				COMMON_Location locbean = new COMMON_Location();
				if (loc.get("LOCATION_ID") != null)
					locbean.setLOCATION_ID(Integer.toString((Integer) loc
							.get("LOCATION_ID")));
				// locbean.setORGANIZATION((String) loc.get("ORGANIZATION"));
				if (loc.get("COUNTRY") != null)
					locbean.setCOUNTRY((String) loc.get("COUNTRY"));
				if (loc.get("CITY") != null)
					locbean.setCITY((String) loc.get("CITY"));
				if (loc.get("AREA") != null)
					locbean.setAREA((String) loc.get("AREA"));
				if (loc.get("SHORT_NAME") != null)
					locbean.setSHORT_NAME((String) loc.get("SHORT_NAME"));
				if (loc.get("TIMEZONE_ID") != null)
					locbean.setTIMEZONE_ID(Integer.toString((Integer) loc
							.get("TIMEZONE_ID")));
				filteredLocList.add(locbean);
			}
		}
		return filteredLocList;

	}

	public void getallFunctions() {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> functions = MasterDataImpl.getCategoriesById(
				"PARENT_ID", 0);
		for (Map<String, Object> functionIterator : functions) {
			functionlistmap.put((Integer) functionIterator.get("CATEGORY_ID"),
					(String) functionIterator.get("NAME"));
		}
	}
	
	

	public Map<Integer, String> returnAllAvailableFunctions() {
		return functionlistmap;
	}

	public List<COMMON_Location> getLocationsForCategory(int categoryID) {

		List<Integer> filteredLocationsList = new ArrayList<Integer>();
		List<Map<String, Object>> defassignList = masterData
				.get("ICDefaultAssignDetails");
		for (Map<String, Object> Assignment : defassignList) {
			int category_id = (Integer) Assignment.get("CATEGORY_ID");
			if (category_id == categoryID) {
				filteredLocationsList.add((Integer) Assignment
						.get("LOCATION_ID"));
			}
		}

		List<Map<String, Object>> locationList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_VARIABLE);
		List<COMMON_Location> filteredLocList = new ArrayList<COMMON_Location>();

		for (Map<String, Object> loc : locationList) {
			if (filteredLocationsList
					.contains((Integer) loc.get("LOCATION_ID"))) {
				COMMON_Location locbean = new COMMON_Location();
				if (loc.get("LOCATION_ID") != null)
					locbean.setLOCATION_ID(Integer.toString((Integer) loc
							.get("LOCATION_ID")));
				if (loc.get("COUNTRY") != null)
					locbean.setCOUNTRY((String) loc.get("COUNTRY"));
				if (loc.get("CITY") != null)
					locbean.setCITY((String) loc.get("CITY"));
				if (loc.get("AREA") != null)
					locbean.setAREA((String) loc.get("AREA"));
				if (loc.get("SHORT_NAME") != null)
					locbean.setSHORT_NAME((String) loc.get("SHORT_NAME"));
				if (loc.get("TIMEZONE_ID") != null)
					locbean.setTIMEZONE_ID(Integer.toString((Integer) loc
							.get("TIMEZONE_ID")));
				filteredLocList.add(locbean);
			}
		}
		return filteredLocList;

	}

	public Map<String, Object> getIHDDeptDefaultAssignmentDetails(int deptid,
			int categoryid, int locationid) {

		Map<String, Object> Assignments = new HashMap<String, Object>();
		List<Map<String, Object>> deptDefassignList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_DEFAULT_ASSIGNMENT_VARIABLE);
		for (Map<String, Object> Assignment : deptDefassignList) {
			int DEPT_ID = (Integer) Assignment.get("DEPT_ID");
			int CATEGORY_ID = (Integer) Assignment.get("CATEGORY_ID");
			int LOCATION_ID = (Integer) Assignment.get("LOCATION_ID");
			if (deptid == DEPT_ID && locationid == LOCATION_ID
					&& categoryid == CATEGORY_ID) {
				return Assignment;
			}
		}

		return Assignments;
	}

	public Map<String, Object> getIHDLocationDetailDefaultAssignmentDetails(
			int categoryid, int locationDetid) {

		Map<String, Object> assignments = new HashMap<String, Object>();
		List<Map<String, Object>> locdetDefassignList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_LOC_DET_DEFAULT_ASSIGNMENT_VARIABLE);
		for (Map<String, Object> Assignment : locdetDefassignList) {
			int CATEGORY_ID = (Integer) Assignment.get("CATEGORY_ID");
			int LOCATION_ID = (Integer) Assignment.get("LOC_DET_ID");
			if (locationDetid == LOCATION_ID && categoryid == CATEGORY_ID) {
				return Assignment;
			}
		}

		return assignments;
	}
	
	public List<Map<String, Object>> getIHDLocationDetailDefaultAssignmentDetailsForAdmin(
			int categoryid, int locationDetid) {

		List<Map<String, Object>> assignments = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> locdetDefassignList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_LOC_DET_DEFAULT_ASSIGNMENT_VARIABLE);
		for (Map<String, Object> Assignment : locdetDefassignList) {
			int CATEGORY_ID = (Integer) Assignment.get("CATEGORY_ID");
			int LOCATION_ID = (Integer) Assignment.get("LOC_DET_ID");
			if (locationDetid == LOCATION_ID && categoryid == CATEGORY_ID) {
				assignments.add(Assignment);
			}
		}

		return assignments;
	}

	public Map<String, Object> getDeptWiseCategoriesApproversById(
			int categoryId, int orderid, int deptid) {

		List<Map<String, Object>> categoryapproverList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_DEPT_APPROVAL_DETAILS_VARIABLE);
		Map<String, Object> filteredcategApproverMap = new HashMap<String, Object>();
		for (Map<String, Object> categoryapprover : categoryapproverList) {
			int categID = (Integer) categoryapprover.get("CATEGORY_ID");
			int approverorderID = (Integer) categoryapprover
					.get("APPROVER_ORDER");
			int deptID = (Integer) categoryapprover.get("DEPT_ID");
			if (categID == categoryId && approverorderID == orderid
					&& deptID == deptid) {
				return categoryapprover;
			}
		}
		return filteredcategApproverMap;

	}

	public Map<String, Object> getApproverEmployeeDetailsByLocWise(
			int approverID, int locationID) {

		List<Map<String, Object>> approverEmployeeList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_APPROVER_EMPLOYEE_DETAILS_VARIABLE);
		
		Map<String, Object> filteredApproverEmployeeMap = new HashMap<String, Object>();
		for (Map<String, Object> approverEmployee : approverEmployeeList) {
			int approverid = (Integer) approverEmployee.get("APPROVER_ID");
			int locID = (Integer) approverEmployee.get("LOCATION_ID");
			if (approverid == approverID && locID == locationID) {
				return approverEmployee;
			}
		}
		return filteredApproverEmployeeMap;

	}

	public List<Map<String, Object>> getodcsForLocation(int locdetid) {

		List<Map<String, Object>> odcList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_ODC_MAPPING_VARIABLE);
		List<Map<String, Object>> filteredodclist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> odc : odcList) {
			int LOC_DET_ID = (Integer) odc.get("LOC_DET_ID");
			if (locdetid == LOC_DET_ID) {
				filteredodclist.add(odc);
			}
		}
		return filteredodclist;

	}

	public Map<String, Object> getodcbyID(int odcid) {

		List<Map<String, Object>> odcList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_ODC_MASTER_VARIABLE);
		Map<String, Object> odcmap = new HashMap<String, Object>();
		for (Map<String, Object> odc : odcList) {
			int ODC_ID = (Integer) odc.get("ODC_ID");
			if (odcid == ODC_ID) {
				return odc;
			}
		}
		return odcmap;

	}

	public Map<String, Object> getdeptbyID(int deptid) {

		List<Map<String, Object>> deptList = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_MASTER_VARIABLE);
		Map<String, Object> deptmap = new HashMap<String, Object>();
		for (Map<String, Object> dept : deptList) {
			int DEPT_ID = (Integer) dept.get("DEPT_ID");
			if (deptid == DEPT_ID) {
				return dept;
			}
		}
		return deptmap;

	}

	public List<Map<String, Object>> getUserDeptMappedID(String duId){
		
		List<Map<String, Object>> deptList=masterData
			.get(COMMON_CacheSQLQueryConstants.IC_IHD_USER_DEPT_MAPPING_VARIABLE);
		List<Map<String, Object>> toSendDeptList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> dept : deptList) {
			String EMP_DEPT_ID = (String) dept.get("EMP_DU_ID");
			if (duId.equals(EMP_DEPT_ID)) {
				toSendDeptList.add(dept);
			}
		}
		return toSendDeptList;
		
	}

	public String getUserDUID(String employeeId) {
		
		List<Map<String, Object>> userDuList=masterData
		.get(COMMON_CacheSQLQueryConstants.IC_IHD_EMP_DU_MAPPING_VARIABLE);
		String duId=null;
		for (Map<String, Object> userDu : userDuList) {
			if(userDu.get("EMPLOYEE_ID").equals(employeeId)){
				duId=(String)userDu.get("DU_ID");
				return duId;
			}
		}
		
		return null;
	}

	
	public List<Map<String, Object>> getGroupsForMemberId(String empId,String functionId) {
		List<Map<String, Object>> groupsList=masterData
		.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_FOR_MEMBER_VARIABLE);
		List<Map<String, Object>> toSendGroupList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> groupObj : groupsList) {
			if(groupObj.get("MEMBER_ID").toString().equalsIgnoreCase(empId) && groupObj.get("CATEGROY_ID").toString().equalsIgnoreCase(functionId)){
				toSendGroupList.add(groupObj);
			}
			
		}
		return toSendGroupList;
	}
	 // This method is required for get project name ---This is for Web Services
    // starting here
    public String getProjectNameById(String proId) {

        List<Map<String, Object>> projectList = masterData
                .get(COMMON_CacheSQLQueryConstants.IC_IHD_PROJECT_VARIABLE);
        String projectname = "";
        for (Map<String, Object> pro : projectList) {
            String proID = pro.get("PROJECT_ID").toString();
            if (proId.equalsIgnoreCase(proID)) {
                projectname = (String) pro.get("NAME");
                break;
            }
        }
        return projectname;
    }

    // This method is required for get project name ---This is for Web Services
    // Ending here
    public List<Map<String, Object>> getRoleMappingForCat() {
        List<Map<String, Object>> roleList = masterData.get("IcCatRoleMap");        
        return roleList;
    }
    
    public List<Map<String, Object>> getBUMappingForCat(String loginID) {
    	List<Map<String, Object>> categoryList = jdbcTemplate.queryForList(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_BU_MAPPING_SQL,loginID);
		return categoryList;
	}
    
    public List<Map<String, Object>> getGroupAccessLevelList(){
    	List<Map<String, Object>> groupAccessList = masterData
    	.get(COMMON_CacheSQLQueryConstants.IC_GROUP_ROLE_MASTER_VARIABLE);
    	return groupAccessList;
    }    
   
    //Added for Emergency L1
    public boolean getIHDGroupMemCheck_DBA(String memID) {
		List<Map<String, Object>> groupMemberMasterList = masterData
				.get(COMMON_CacheSQLQueryConstants.IHD_GROUP_MEMBER_VARIABLE);
		//List<Map<String, Object>> filteredGroupMemberList = new ArrayList<Map<String, Object>>();
		boolean getDBAMemCheck=false;
		for (Map<String, Object> ctg : groupMemberMasterList) {
			if (ctg.get("member_id").toString().trim().toUpperCase()
					.equalsIgnoreCase(memID.trim().toUpperCase()) && (ctg.get("NAME").toString().equalsIgnoreCase("DBA"))) {
				//filteredGroupMemberList.add(ctg);
				getDBAMemCheck=true;
			}
		}
		return getDBAMemCheck;
	}
	/***********ADMIN Console:Location Addition**********/    
    public List<Map<String, Object>> getUniqueCountryList(String functionID){
    	List<Map<String, Object>> countryList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> countryList_new=new ArrayList<Map<String,Object>>();
    List<Map<String, Object>> functionLocationList = masterData
    .get(COMMON_CacheSQLQueryConstants.IC_IHD_COUNTRY_VARIABLE);
    for(Map<String, Object> country:functionLocationList){
    	Map<String, Object> countryMap=new HashMap<String, Object>();    	
    	if(country.get("FUNCTION_ID").toString().equals(functionID)){
    			countryMap.put("COUNTRY",country.get("COUNTRY").toString() );	
    			countryList.add(countryMap);
    	}
    }    	
    Map<String, Object> countryMap_new=new HashMap<String, Object>();
    countryMap_new.put("COUNTRY", countryList.get(0).get("COUNTRY").toString());
	countryList_new.add(countryMap_new);
    	for(int i=1;i<countryList.size();i++){
    		boolean isExist=true;
    		countryMap_new=new HashMap<String, Object>();
    		for(int j=0;j<countryList_new.size();j++){
    			outerloop:
    			if(countryList.get(i).get("COUNTRY").toString().equalsIgnoreCase(countryList_new.get(j).get("COUNTRY").toString())==true){
    				isExist=true;      				
    			}else{
    				isExist=false;
    				break outerloop;
    			}
    		}
    		if(isExist==false){
    			countryMap_new.put("COUNTRY", countryList.get(i).get("COUNTRY").toString());
    			countryList_new.add(countryMap_new);
    		}
    		
    	}   
    return countryList_new;
    }
    
    
    public int getSLAOperatingTime(final JSONArray oprArray,String groupName, final String loginId, final int locationId) throws JSONException {

		List<Map<String, Object>> slaOperatingTimeList = masterData
				.get("SLAOperatingTimeList");
		int slaId = 0;
		int count= 0;
		String previousSLAId=null;
		
		for (Map<String, Object> SLA : slaOperatingTimeList) {

			if(!(SLA.get("SLA_ID").toString()).equalsIgnoreCase(previousSLAId)){
					count=0;
			}
				
			if (SLA.get("SLA_ID") != null) {
				for(int i = 0 ; i < oprArray.length(); i++){
					
					JSONArray subOprArray = (JSONArray)oprArray.get(i);
					if (((String) SLA.get("DAY")).equalsIgnoreCase(subOprArray.get(0).toString())
							&& ((String) SLA.get("START_TIME")).equalsIgnoreCase(subOprArray.get(1).toString())
							&& ((String) SLA.get("END_TIME")).equalsIgnoreCase(subOprArray.get(2).toString())
							&& ((String) SLA.get("NEXT_WORKING_DAY")).equalsIgnoreCase(subOprArray.get(3).toString())) {
						
						count=count+1;
					}
				}
				if(count == oprArray.length()){
					slaId = Integer.parseInt(SLA.get("SLA_ID").toString());
				}
				previousSLAId = SLA.get("SLA_ID").toString();
			}
			
		}
		if(slaId == 0){
			//insert new sla and return sla id
			
			int max_slaId = this.jdbcTemplate.queryForInt("SELECT MAX(SLA_ID) FROM IC_IHD_SLA_MASTER");
			final int new_slaId = max_slaId + 1;
			
			slaId=new_slaId;
			
			String SQL = "INSERT INTO IC_IHD_SLA_MASTER(SLA_ID,DESCRIPTION,ORGANIZATION,TIME,IS_ACTIVE," +
					"CREATED_BY,CREATED_DATE) VALUES(?,?,'C',0,1,?,?)";
			
			int result = this.jdbcTemplate.update(SQL,slaId,groupName,loginId,CustomDateFormatConstants.creationDateGMTFormat());
			
			if(result>0){
				
				String SLA_Operating_Time_Sql="INSERT INTO IC_IHD_SLA_OPERATING_TIME_DETAILS(SLA_ID," +
						"DAY,START_TIME,END_TIME,NEXT_WORKING_DAY,IS_ACTIVE,CREATED_BY,CREATED_DATE) " +
						"VALUES(?,?,?,?,?,?,?,?)";
				
				int[] row = jdbcTemplate.batchUpdate(SLA_Operating_Time_Sql,
						new BatchPreparedStatementSetter(){
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								try {
									JSONArray subSLAArray = (JSONArray) oprArray.get(i);
									ps.setInt(1, new_slaId);
									ps.setString(2,subSLAArray.get(0).toString());
									ps.setString(3, subSLAArray.get(1).toString());
									ps.setString(4, subSLAArray.get(2).toString());
									ps.setString(5,subSLAArray.get(3).toString());
									ps.setString(6,"1");
									ps.setString(7,loginId);
									ps.setString(8,CustomDateFormatConstants.creationDateGMTFormat());
								
								} catch (JSONException e) {
									e.printStackTrace();
								}
								
							}
							
							public int getBatchSize() {
								return oprArray.length();
							}
					
				});
				
				if(row.length > 0){
					
					final List<Map<String,Object>> exclusionDates = this.jdbcTemplate.queryForList("select distinct top 10 DATE from " +
							"IC_IHD_SLA_EXCLUSION_DATES where IS_ACTIVE=1 and LOCATION_ID="+locationId+" order by DATE desc");
					
					String SLA_Excision_Dates_Sql="INSERT INTO IC_IHD_SLA_EXCLUSION_DATES(SLA_ID,LOCATION_ID," +
							"DATE,IS_ACTIVE,CREATED_BY,CREATED_DATE) VALUES(?,?,?,?,?,?)";
					
					int[] holidays = jdbcTemplate.batchUpdate(SLA_Excision_Dates_Sql,
							new BatchPreparedStatementSetter(){

								public void setValues(PreparedStatement ps, int i)
										throws SQLException {
										ps.setInt(1, new_slaId);
										ps.setInt(2,locationId);
										ps.setString(3, exclusionDates.get(i).get("DATE").toString() );
										ps.setString(4,"1");
										ps.setString(5,loginId);
										ps.setString(6,CustomDateFormatConstants.creationDateGMTFormat());
								
								}
								
								public int getBatchSize() {
									return exclusionDates.size();
								}
						
					});
				}
			}			
		}
		
		
		return slaId;
	} 
   
    public String getSLAForOperatingTime(List<Map<String, Object>> operationTimeList) {

		List<Map<String, Object>> slaOperatingTimeList = masterData
				.get("SLAOperatingTimeList");
		String slaId = null;
		int count= 0;
		String previousSLAId=null;
		
		for (Map<String, Object> SLA : slaOperatingTimeList) {

			if(!(SLA.get("SLA_ID").toString()).equalsIgnoreCase(previousSLAId)){
					count=0;
			}	
			for (Iterator<Map<String, Object>> iterator = operationTimeList.iterator(); iterator
						.hasNext();) {
					Map<String, Object> map = iterator
							.next();
					if (((String) SLA.get("DAY")).equalsIgnoreCase(map.get("DAY").toString())
							&& ((String) SLA.get("START_TIME")).equalsIgnoreCase(map.get("START_TIME").toString())
							&& ((String) SLA.get("END_TIME")).equalsIgnoreCase(map.get("END_TIME").toString())
							&& ((String) SLA.get("NEXT_WORKING_DAY")).equalsIgnoreCase(map.get("NEXT_WORKING_DAY").toString())) {
						
						
						count=count+1;
					}					
				}
				if(count == operationTimeList.size() ){
					slaId = SLA.get("SLA_ID").toString();
				}
				previousSLAId = SLA.get("SLA_ID").toString();
			
			
		}
		
		return slaId;
	}

	public List<Map<String, Object>> getUserRoleDetailsList() {

		List<Map<String, Object>> userRoleList = masterData
    	.get(COMMON_CacheSQLQueryConstants.IC_USER_ROLE_DETAIL_VARIABLE);
    	return userRoleList;		
	}
	public List<Map<String, Object>> getAllGroupNamesForFunction(int functionid) {

		List<Map<String, Object>> toSend = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> FUNCTION_GROUP_LIST = masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_GROUP_NAME_FUNCTION_MAPPING_LIST_VARIABLE);
		for (Map<String, Object> rowobj : FUNCTION_GROUP_LIST) {
			int CATEGROY_ID = (Integer) rowobj.get("CATEGROY_ID");
			if (CATEGROY_ID == functionid) {
				toSend.add(rowobj);
			}
		}
		return toSend;
	}
    
public List<Map<String, Object>> getGroupListDefAssignment(String functionID){
    	List<Map<String, Object>> groupList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> filteredGroupList=new ArrayList<Map<String,Object>>();
    	groupList=masterData.get(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE);
    	for(Map<String,Object> grpMap:groupList){    		
    		if(grpMap.get("PARENT_ID").toString().equals(functionID)){
    			filteredGroupList.add(grpMap);
    		}
    	}
    	return filteredGroupList;
    	
    }

    public List<Map<String, Object>> getGroupListDefAssignmentSubCat(String subCategoryID){
    	List<Map<String, Object>> groupList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> filteredGroupList=new ArrayList<Map<String,Object>>();
    	groupList=masterData.get(COMMON_CacheSQLQueryConstants.IC_FUNCTION_SUBCAT_GROUP_VARIABLE);
    	if(groupList!=null){
	    	for(Map<String,Object> grpMap:groupList){    	
	    	if(grpMap.get("SUB_CATEGORY_ID")!=null && subCategoryID!=null){
				if(grpMap.get("SUB_CATEGORY_ID").toString().length()>0){
				    		if(grpMap.get("SUB_CATEGORY_ID").toString().equals(subCategoryID)){
				    			filteredGroupList.add(grpMap);
				    		}
				}
    		}
	    	}
    	}
    	return filteredGroupList;
    	
    }
    
    public List<Map<String, Object>> getGroupListDepDefAssignment(String functionID){
    	List<Map<String, Object>> groupList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> filteredGroupList=new ArrayList<Map<String,Object>>();
    	groupList=masterData.get(COMMON_CacheSQLQueryConstants.IC_DEF_ASSG_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE);
    	for(Map<String,Object> grpMap:groupList){    		
    		if(grpMap.get("PARENT_ID").toString().equals(functionID)){
    			filteredGroupList.add(grpMap);
    		}
    	}
    	return filteredGroupList;
    	
    }
    
    public List<Map<String, Object>> getGroupListLocAssignmentStep1(String functionID){
    	List<Map<String, Object>> groupList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> filteredGroupList=new ArrayList<Map<String,Object>>();
    	groupList=masterData.get(COMMON_CacheSQLQueryConstants.IC_LOC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE);
    	for(Map<String,Object> grpMap:groupList){    		
    		if(grpMap.get("PARENT_ID").toString().equals(functionID)){
    			filteredGroupList.add(grpMap);
    		}
    	}
    	return filteredGroupList;
    	
    }
    
    public List<Map<String, Object>> getGroupSubCategoryList(String subCatID){
    	List<Map<String, Object>> subcatList=new ArrayList<Map<String,Object>>();  
    	subcatList=masterData.get(COMMON_CacheSQLQueryConstants.IC_LOC_FUNCTION_SUBCAT_GROUP_VARIABLE);
    	for(int i=0;i<subcatList.size();i++){
    		if(subcatList.get(i).get("SUB_CATEGORY_ID").toString().equals(subCatID)){
    			
    		}
    	}
    	 
    	return subcatList;    	
    }
    
    
    public List<Map<String, Object>> getGroupSubCategoryListNew(String subCatID){
    	List<Map<String, Object>> subcatList=new ArrayList<Map<String,Object>>();  
    	List<Map<String, Object>> newSubcatList=new ArrayList<Map<String,Object>>();
    	subcatList=masterData.get(COMMON_CacheSQLQueryConstants.IC_LOC_FUNCTION_SUBCAT_GROUP_VARIABLE);
    	for(int i=0;i<subcatList.size();i++){
    		if(subcatList.get(i).get("SUB_CATEGORY_ID").toString().equals(subCatID)){
    			newSubcatList.add(subcatList.get(i));
    		}
    	}
    	 
    	return newSubcatList;    	
    }
    
    
    
    
    public List<Map<String, Object>> getGroupListLocAssignment(String functionID,String stRow,String endRow){
    	List<Map<String, Object>> groupList=new ArrayList<Map<String,Object>>();
    	List<Map<String, Object>> filteredGroupSList=new ArrayList<Map<String,Object>>();
    	groupList=masterData.get(COMMON_CacheSQLQueryConstants.IC_LOC_FUNCTION_CAT_SUBCAT_GROUP_VARIABLE);
    	Map<String, Object> totalMap=new HashMap<String, Object>();
    	int startRw=Integer.parseInt(stRow);
    	for(int i=0;i<groupList.size();i++){    		
    		Map<String, Object> grpMap=new HashMap<String, Object>();    		
    		//For GRP dropdown on the basis of SubCategory
    		List<Map<String, Object>> grpList=new ArrayList<Map<String,Object>>();
	    		grpMap.put("GROUP_ID",groupList.get(i).get("GROUP_ID").toString() );
	    		grpMap.put("GROUP_NAME",groupList.get(i).get("GROUP_NAME").toString() );    		
	    		grpList.add(grpMap);    		
	    		grpMap=new HashMap<String, Object>();
    		for(int j=startRw;j<groupList.size();j++){
    			if(i!=j){
    				if(groupList.get(i).get("SUB_CATEGORY_ID").toString().equals(groupList.get(j).get("SUB_CATEGORY_ID").toString())){
    					grpMap.put("GROUP_ID",groupList.get(j).get("GROUP_ID").toString() );
    		    		grpMap.put("GROUP_NAME",groupList.get(j).get("GROUP_NAME").toString() );
    		    		
    				}
    				grpList.add(grpMap);
        			grpMap=new HashMap<String, Object>();
    			}
    			
    		}    		
    		totalMap.put("grpMap", grpList);
    		totalMap.put("CATEGORY_NAME", groupList.get(i).get("CATEGORY_NAME").toString() );
    		totalMap.put("SUB_CATEGORY_NAME", groupList.get(i).get("SUB_CATEGORY_NAME").toString() );
    		totalMap.put("DATA_SIZE", groupList.size());
    		filteredGroupSList.add(totalMap); 
    		totalMap=new  HashMap<String, Object>();
    	}
    	
    	return filteredGroupSList;    	
    }

    public int isCatSubCatNameExistCategoryConsole(List<HELPDESK_Category>  catListActive,String functionId){
    	List<Map<String, Object>> categorySubCategoryList=masterData.get(COMMON_CacheSQLQueryConstants.IC_FUNCTION_CAT_SUBCAT__VARIABLE);
    	int isValidCategory=1;
    	int isValidSubCategory=1;
    	int result=1;
    	
    	for (Iterator<HELPDESK_Category> iterator = catListActive.iterator(); iterator.hasNext();) {
			HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator.next();
			String categoryID=helpdeskCategory.getCATEGORY_ID();
			String subCategoryID=helpdeskCategory.getSUBCATEGORY_ID();
			String categoryName=helpdeskCategory.getCATEGORY_NAME();
			String subCategoryName=helpdeskCategory.getSUBCATEGORY_NAME();
			
			for(int i=0;i<categorySubCategoryList.size();i++){
				if(categorySubCategoryList.get(i).get("PARENT_ID").toString().equalsIgnoreCase(functionId)){
					if(categoryName.equalsIgnoreCase(categorySubCategoryList.get(i).get("CATEGORY_NAME").toString())   && !categoryID.equalsIgnoreCase(categorySubCategoryList.get(i).get("CATEGORY_ID").toString()) 
							&& categorySubCategoryList.get(i).get("CAT_STATUS").toString().equals("1")){
						isValidCategory=0;
						break;
					}
				}
			}
			
			for(int i=0;i<categorySubCategoryList.size();i++){
				if(categorySubCategoryList.get(i).get("CATEGORY_ID").toString().equalsIgnoreCase(categoryID)){
					if((subCategoryName.equalsIgnoreCase(categorySubCategoryList.get(i).get("SUB_CATEGORY_NAME").toString()))&& !subCategoryID.equalsIgnoreCase(categorySubCategoryList.get(i).get("SUB_CATEGORY_ID").toString()
							) && categorySubCategoryList.get(i).get("SUB_CAT_STATUS").toString().equals("1")){
						isValidSubCategory=0;
						break;
					}
				}
			}	
			
		}
    	
    	if(isValidCategory==0 || isValidSubCategory==0){
    		result=0;
    	}
    	
    	return result;
    }
    
    
    
    
	
	
	//By Nisha --changed for HR Helpdesk --start
	public String getCategoryStatusFilter(String categoryId, String flag){ 
		List<Map<String, Object>> categoryList =masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_CATEGORY_STATUS_FILTER_VARIABLE);
		String result = ""; 

		if(flag.equalsIgnoreCase("subCategory")) {
			for (Map<String, Object> category : categoryList) {
				int catId = (Integer) category.get("Sub Category ID");
				if (Integer.parseInt(categoryId) == catId) {
					result = (String) category.get("Sub Category State Name");
				}
			}
		}else if(flag.equalsIgnoreCase("category")) {
			for (Map<String, Object> category : categoryList) {
				int catId = (Integer) category.get("Category ID");
				if (Integer.parseInt(categoryId) == catId) {
					result = (String) category.get("Category State Name");
				}
			}
		}else if(flag.equalsIgnoreCase("function")) {
			for (Map<String, Object> category : categoryList) {
				int catId = (Integer) category.get("Function ID");
				if (Integer.parseInt(categoryId) == catId) {
					result = (String) category.get("Function");
				}
			}
		}
		System.out.println("result"+result);
		return result;
	} 
	//By Nisha --changed for HR Helpdesk --end
	
	

	/***********ADMIN Console:Location Addition**********/ 
	
	  //ITrack ticket assignment start  
	public List<Map<String, Object>> getAutoApproverEmpIDList(){
		return  masterData
				.get(COMMON_CacheSQLQueryConstants.IC_IHD_AUTO_APPROVAL_MEMBER_DETAILS_VARIABLE);
	}
	
	public String getIHDGroupName(String l_groupID) {
		String l_groupName="";
		
		List<Map<String, Object>> groupList= masterData
				.get(COMMON_CacheSQLQueryConstants.IC_GROUP_LIST_VARIABLE);
		
		for (Map<String, Object> groupNameMap : groupList) {
			if(groupNameMap.get("GROUP_ID").toString().equalsIgnoreCase(l_groupID)){
				l_groupName=groupNameMap.get("GROUP_NAME").toString();
			}
		}
		 
		 return l_groupName;
	}
	
	
	public List<Map<String,Object>> getAllLoactionList(){
		return masterData.get(COMMON_CacheSQLQueryConstants.IC_LOCATION_LIST_VARIABLE);
	}
	
	
	public List<Map<String,Object>> getAllTimeZoneList(){
		return masterData.get(COMMON_CacheSQLQueryConstants.IC_TIME_ZONE_LIST_VARIABLE);
	}
	
	public List<Map<String,Object>> getFunctionDetailsForEscaltionMatrix(){
		return masterData.get(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_VARIABLE);
		}
		
		
	public List<Map<String,Object>> getFunctionForEscaltionMatrix(){
		return masterData.get(COMMON_CacheSQLQueryConstants.IC_ESCALATION_MATRIX_FUNCTION_VARIABLE);
		}
	
	//L2: 2786
	public List<Map<String,Object>> getWorkflowStatusList(){
		return masterData.get(COMMON_CacheSQLQueryConstants.IC_REPORTS_WORKFLOW_STATE_VAR);
		}

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 3, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
