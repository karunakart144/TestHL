/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.dao;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.COMMON_Menu;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Group;
import com.igate.iconnect.BO.HELPDESK_Priority;

public interface COMMON_CacheDAO {

	/**
	 * @param categoryId
	 *            primary key of the table
	 * @return List<Map<String, Object>> when categoryid passes to this method
	 *         the list of records will return where the parentid is equal to
	 *         categoryid
	 */
	public List<Map<String, Object>> getCategoriesById(String column,
			int categoryId);
	
	//Anjana
	public List<Map<String, Object>> getTableNames();
	
		/**
	 * @param categoryId
	 *            primary key of the table
	 * @return List<CategoryBean> when categoryid passes to this method the list
	 *         of records will return where the parentid is equal to categoryid
	 */
	public List<HELPDESK_Category> getCategoriesByColumn(String column,
			int categoryId);

	/**
	 * @param categoryid
	 *            - categoryid
	 * @return String when categoryid passes to this method the category name
	 *         will be returned
	 */

	public String getCategoryNameById(int categoryid);

	/**
	 * @param approverId
	 *            primary key of the table
	 * @return List<Map<String, Object>> when approverId passes to this method
	 *         the list of records will return where the approverId equals
	 */
	public List<Map<String, Object>> getApproversById(int approverId);

	/**
	 * @param categoryid
	 *            - This indicates the categoryid in the
	 *            IC_IHD_CATEGORY_APPROVAL_DETAILS table
	 * @param orderid
	 *            - This indicates the approver orderid in the
	 *            IC_IHD_CATEGORY_APPROVAL_DETAILS table
	 * @return List<Map<String, Object>> when approverId passes to this method
	 *         the list of records will return where the approverId equals
	 */
	public List<Map<String, Object>> getCategoriesApproversById(int categoryId,
			int orderid);

	public String getApproverID(String value, String column);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of Approvers details will return where the search word
	 *         contains in the Approvers table
	 */

	public List<Map<String, Object>> getApproversByName(String toSearch);

	/**
	 * @param groupId
	 *            primary key of the table
	 * @return List<Map<String, Object>> when groupId passes to this method the
	 *         list of records will return where the groupId equals
	 */

	public List<Map<String, Object>> getGroupsById(int groupId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of groups will return where the search word contains in the
	 *         groups table
	 */

	public List<Map<String, Object>> getGroupsByName(String toSearch);

	/**
	 * @param groupMemberId
	 *            primary key of the table
	 * @return List<Map<String, Object>> when groupMemberId passes to this
	 *         method the list of records will return where the groupMemberId
	 *         equals
	 */

	public List<Map<String, Object>> getGroupMembersById(int groupMemberId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of groupmember details will return where the search word
	 *         contains in the groupMembers table
	 */

	public List<Map<String, Object>> getGroupMembersByName(String toSearch);

	/**
	 * @param slaId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when slaId passes to this method the
	 *         list of SLA's will return where the slaId equals
	 */

	public Map<String, Object> getSLADetailsById(int slaId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of SLA details will return where the search word contains in
	 *         the SLA Details table
	 */

	public List<Map<String, Object>> getSLADetailsByName(String toSearch);

	/**
	 * @param actionId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when actionId passes to this method the
	 *         list of records will return where the actionId equals
	 */

	public List<Map<String, Object>> getActionDetailsById(int actionId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of Actions will return where the search word contains in the
	 *         ActionDetails table
	 */

	public List<Map<String, Object>> getActionDetailsByName(String toSearch);

	/**
	 * @param issuelogApproverId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when islApproverId passes to this
	 *         method the list of records will return where the islApproverId
	 *         equals
	 */

	public int getISLApproversById(int employeeid, int groupid);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of Issuelog Approver details will return where the search
	 *         word contains in the IssueLog Approvers table
	 */

	public List<Map<String, Object>> getISLApproversByName(String toSearch);

	/**
	 * @param issuelogCategoryId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when islCategoryId passes to this
	 *         method the list of records will return where the islCategoryId
	 *         equals
	 */

	public List<Map<String, Object>> getISLCategoriesById(int islCategoryId);

	/**
	 * @param issuelogEngineerId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when islEngineerId passes to this
	 *         method the list of records will return where the islEngineerId
	 *         equals
	 */

	public List<Map<String, Object>> getISLEngineersById(int islEngineerId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of Engineers will return where the search word contains in
	 *         the IssueLog Engineers table
	 */

	public List<Map<String, Object>> getISLEngineersByName(String toSearch);

	/**
	 * @param column
	 *            - column of the table
	 * @param value
	 *            - to search
	 * @return List<Map<String, Object>> when column passes to this method the
	 *         list of records will return where the column data equals to value
	 */
	public List<Map<String, Object>> getISLGroupscategories(String column,
			int value);

	/**
	 * @param issuloglGroupMemberId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when islGroupMemberId passes to this
	 *         method the list of records will return where the
	 *         issuloglGroupMemberId equals
	 */
	public List<Map<String, Object>> getISLGroupsById(int islGroupMemberId);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of group details will return where the search word contains
	 *         in the IssueLog groups table
	 */

	public int getISLGroupsByName(String toSearch);

	/**
	 * @param locationId
	 *            - primary key of the table
	 * @param columnname
	 *            - column name result
	 * @return String when locationId and column name passes to this method the
	 *         column value will pass
	 */

	public String getLocationsById(int locId, String columnname);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of location details will return where the search word
	 *         contains in the Location table
	 */

	public List<Map<String, Object>> getLocationsByName(String toSearch);

	/**
	 * @return List<Map<String, Object>> it returns all the locations in the
	 *         table
	 */

	public List<Map<String, Object>> getLocations();

	/**
	 * @return List<LocationBean> it returns all the locations in the table
	 */
	public List<COMMON_Location> getICLocations();

	/**
	 * @param locDetId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when locDetId passes to this method the
	 *         list of records will return where the locDetId equals
	 */

	public List<Map<String, Object>> getLocationDetailsById(String column,
			int locDetId);

	public List<Map<String, Object>> getDistinctBuildingLocations(int locationid);

	public List<Map<String, Object>> getFloorsforBuilding(String buildingname);

	/**
	 * @param toSearch
	 *            - Its a search word
	 * @return List<Map<String, Object>> when toSearch passes to this method the
	 *         list of location details will return where the search word
	 *         contains in the Locationdetails table
	 */

	public List<Map<String, Object>> getLocationDetailsByName(String toSearch);

	/**
	 * @return List<Map<String, Object>> return the list of priority records
	 */

	public List<Map<String, Object>> getPriorityList();

	/**
	 * @return List<PriorityBean> return the list of priority records
	 */
	public List<HELPDESK_Priority> getAllPriorities();

	/**
	 * @param priorityid
	 *            - Id of priority
	 * @return String return the priority description
	 */

	public String getPriorityNameById(int priorityid);

	/**
	 * @param roleId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when roleId passes to this method the
	 *         list of roles will return where the roleid equals
	 */

	public List<Map<String, Object>> getRolesById(int roleId);

	/**
	 * @param severityId
	 *            - primary key of the table
	 * @return List<Map<String, Object>> when severityId passes to this method
	 *         the list of records will return where the severityId equals
	 */

	public List<Map<String, Object>> getSeveritiesById(int severityId);

	/**
	 * @param categoryid
	 *            - categoryid of the table
	 * @param locationid
	 *            - locationid of the table
	 * @return Map<String, Object> when categoryid and locationid passes to this
	 *         method the groupid will return
	 */
	public Map<String, Object> getDefaultAssignDetails(int categoryid,
			int locationid);

	/**
	 * getRoleMaster () method will return the roleid,role name details from
	 * role master table in a list
	 */
	public List<Map<String, Object>> getRoleMaster();

	/**
	 * getIHDGroupMaster () method will return the group master details for IHD
	 * in a list
	 */
	public List<Map<String, Object>> getIHDGroupMaster();

	/**
	 * getAllIHDGroups () method will return the group master details for IHD in
	 * a list
	 */
	public List<HELPDESK_Group> getAllIHDGroups();

	/**
	 * getIHDGroupMemberID() method will return a list which contains group
	 * members name with id for a groupName Arguements passed : GroupName
	 * 
	 * @param groupMemberMasterList
	 *            : List contains group members name with id for a groupName
	 */
	public List<Map<String, Object>> getIHDGroupMemberID(String groupName);

	/**
	 * 
	 * @param roleId
	 *            : Pass the Role id of the user
	 * @return: List of Menu Objects with ParentId as 0
	 */
	public List<COMMON_Menu> getParentMenuList(String roleId);

	/**
	 * 
	 * @param roleId
	 *            : Pass the Role id of the user
	 * @return: List of Menu Objects
	 */
	public List<COMMON_Menu> getChildMenuList(String roleId, String menuId);

	/**
	 * 
	 * @return String when groupMemberId passes to this method the list of
	 *         groupids will return where the groupMemberId equals
	 */

	public String getGroupsById(String groupMemberId);

	/**
	 * getHeaderAndTableSize() method will return distinct column size and
	 * distinct table size for a menuID from IC_MENU_HEADER_DETAILS table.
	 * 
	 * @param query
	 *            Query to find out distinct column size and distinct table size
	 *            for a menuID.
	 * @param sizeList
	 *            List which contains distinct column size and distinct table
	 *            size for a menuID.
	 */
	public int getHeaderAndTableSize(String menuID);

	/**
	 * getTableDetails() method will return distinct table details for a menuID
	 * from IC_MENU_HEADER_DETAILS table.
	 * 
	 * @param query
	 *            Query to find out distinct table details for a menuID.
	 * @param tableDetailList
	 *            List which contains distinct table details for a menuID.
	 */
	public List<Map<String, Object>> getTableDetails(String menuID);

	/**
	 * getHeaderMap() method will return all the headers details which have to
	 * be displayed in the list page based on menuId.
	 * 
	 * @param headerMap
	 *            Map which contains the header details.
	 * @param sizeList
	 *            List which contains distinct column size and distinct table
	 *            size in the headerMap.
	 * @param tableList
	 *            List which contains distinct table name & table alias
	 * @param headerDetailList
	 *            List which contains column details
	 */
	public Map<String, Object> getHeaderMap(long menuID);

	/**
	 * getHeaderDetails() method will return distinct column details for a
	 * menuID from IC_MENU_HEADER_DETAILS table.
	 * 
	 * @param query
	 *            Query to find out distinct column details for a menuID
	 * @param tableDetailList
	 *            List which contains distinct column details for a menuID .
	 */
	public List<Map<String, Object>> getHeaderDetails(String menuID);

	/**
	 * @param categoryid
	 *            - categoryid
	 * @return Map<String, Object> when categoryid passes to this method the
	 *         list of subjects will return
	 */

	public List<Map<String, Object>> getAction();

	public List<Map<String, Object>> getISLGroupsMaster(String column, int value);

	public List<Map<String, Object>> getISLCategoriesByName(String toSearch);

	public List<Map<String, Object>> getISLCategoriesByParentId(
			int islCategoryId);

	public List<Map<String, Object>> getISLApproversByGroup(int islGroupId);

	public List<Map<String, Object>> getISLSubCategoriesByName(String toSearch);

	public List<Map<String, Object>> getSeverityList();

	public List<Map<String, Object>> getTicketsUIFieldsById(String fieldId);

	public String getGroupByIdString(String groupMemberId);

	public List<Map<String, Object>> getIHDCategoryPriorityList();



	public List<Map<String, Object>> getListofData(String keyname);

	public Map<String, String> getUIAndDBFieldForAuditLog(String menuId);

	public Map<String, Object> getIHDCategoryPriority(int categoryid,
			int priorityid, String org);

	public List<Map<String, Object>> getGroupsForFunction(int functionid,
			int locationid);
	
	public List<Map<String, Object>> getGroupsForFunctionforAA(int groupID);
	
	public List<Map<String, Object>> getGroupsForFunction(int functionid,
			List<String> locationidlist);
	
	public List<Map<String, Object>> getGroupsForFunction(int functionid);
	
	public List<Map<String, Object>> getAllGroupsForFunction(int functionid);


	public String getQueryForMenuAndRole(String menuId, String roleId);

	public boolean resetQueryForMenuAndRole();

	public boolean resetgetMasterData();


	public Map<String, Object> getTimeZone(int timezoneID);

	// Added by Sali
	public List<Map<String, Object>> getSLAExclusionDateList(long slaId,
			long locationId);

	List<Map<String, Object>> getSLAOperatingTimeList(long slaId);

	public List<Map<String, Object>> getSLAOpertingTimeOrgWise(long sla_id,
			String org, String day);

	public long getServiceOPRID(long locationID, long locationDetailId,
			long functionID);

	public long getGroupOPRID(long groupID, long functionID, long locationID);



	public List<Map<String, Object>> getColumnsForMenu(int menuid);

	public List<Map<String, Object>> getDefaultColumnsForMenu(int menuid);

	public List<Map<String, Object>> getUserRoleById(String empId);

	public List<Map<String, Object>> getIHDGroup(String memID);

	public List<Map<String, Object>> getRoles();

	public List<Map<String, Object>> getIHDInActiveGroupMemberID(
			String groupName);

	public List<Map<String, Object>> getIHDActiveInactiveGroupMemberID(
			String groupName);

	/**
	 * @param outofslaid
	 *            - REASON_ID
	 * @return Map<String, Object> when reasonId passes to this method the
	 *         matched row will return
	 */

	public Map<String, Object> getOutofSlaRowById(int reasonId);

	/**
	 * @param functionID
	 *            - FUNCTION_ID
	 * @return List<LocationBean> when functionID passes to this method all the
	 *         locations mapped to this function will return
	 */

	public List<COMMON_Location> getLocationsForFunction(int functionID);

	/**
	 * 
	 * @return Map<Integer,String> when function gets called it will pass the
	 *         available funcitons with id and value
	 */
	public Map<Integer, String> returnAllAvailableFunctions();

	/**
	 * @param categoryID
	 *            - CATEGORY_ID
	 * @return List<LocationBean> when categoryID passes to this method all the
	 *         locations mapped to this categoryID will return
	 */

	public List<COMMON_Location> getLocationsForCategory(int categoryID);

	/**
	 * @param deptid
	 *            - DEPT_ID
	 * @param categoryid
	 *            - CATEGORY_ID
	 * @param locationid
	 *            - LOCATION_ID
	 * @return the groupid or else returns 0 when the data is not available
	 */
	public Map<String, Object> getIHDDeptDefaultAssignmentDetails(int deptid,
			int categoryid, int locationid);

	/**
	 * @param categoryid
	 *            - CATEGORY_ID
	 * @param locationdetid
	 *            - LOC_DET_ID
	 * @return the groupid or else returns 0 when the data is not available
	 */

	public Map<String, Object> getIHDLocationDetailDefaultAssignmentDetails(
			int categoryid, int locationdetid);

	public Map<String, Object> getDeptWiseCategoriesApproversById(
			int categoryId, int orderid, int deptid);

	public Map<String, Object> getApproverEmployeeDetailsByLocWise(
			int approverID, int locationID);

	public List<Map<String, Object>> getodcsForLocation(int locdetid);

	public Map<String, Object> getodcbyID(int odcid);

	public Map<String, Object> getdeptbyID(int deptid);
	
	public List<Map<String,Object>> getRolesByCategory(int categoryId);
	
	public List<Map<String,Object>> getUserDeptMappedID(String duId);
	
	public String getUserDUID(String employeeId);
	
	//public int getUserDeptID(String duId);
	
	public String getGroupIdById(int groupId);
	
	public List<Map<String,Object>> getGroupsForMemberId(String empId,String functionId);
	
	//This method has been added for getting PROJECT_NAME
	
	public String getProjectNameById(String proId);
	
	 public List<Map<String, Object>> getRoleMappingForCat();
	 
	 public List<Map<String, Object>> getIHDGroupMemberID_Admin(String groupName);

	public List<Map<String, Object>> getSubCategoryApproversById(int subcat,String workflowStatusName);
	
	/*
	*Auto Assignment:Get Status and Priority Weightage
	*/
	public double getStatusWeightageForAutoAssignment(int stateID);
	 
	public int getPriorityWeightageForAutoAssignment(int priorityID);
	
	
	/*END:
	*Auto Assignment:Get Status and Priority Weightage
	*/
	
	//Added for Emergency L1
	 public boolean getIHDGroupMemCheck_DBA(String memID);
	
	
	//Admin Console
	public List<Map<String, Object>> getGroupAccessLevelList();
	public int getSLAOperatingTime(JSONArray oprArray,String groupName,String loginId,int locationId) throws JSONException;
	public List<Map<String, Object>> getUserRoleDetailsList();
	public List<Map<String, Object>> getAllGroupNamesForFunction(int functionid);


/**
	 *  This method will provide unique country list
	 */
	 public List<Map<String, Object>> getUniqueCountryList(String functionID);
	 
	
	/**
	 *  
	 * @param functionID
	 * @return List of Groups for a particular function,category and subcategory
	 */
	 public List<Map<String, Object>> getGroupListDefAssignment(String functionID);
	 
	 public List<Map<String, Object>> getGroupListDefAssignmentSubCat(String functionID);
	 
	 /**
		 *  
		 * @param functionID
		 * @return List of Groups for a particular HR Function Dept Wise,category and subcategory
		 */
	public List<Map<String, Object>> getGroupListDepDefAssignment(String functionID);
	 /**
	 *  
	 * @param functionID
	 * @return List of Groups for a particular Admin Function Loc Wise,category and subcategory
	 */
	  public List<Map<String, Object>> getGroupListLocAssignment(String functionID,String stRow,String endRow);
	  public List<Map<String, Object>> getGroupListLocAssignmentStep1(String functionID);
	  public List<Map<String, Object>> getGroupSubCategoryList(String groupID);
	  
	  
	  public List<Map<String, Object>> getGroupSubCategoryListNew(String subCatID);
	  /**
	   * Admin Console:Location Addition
	   * @param operationTimeList
	   * @return Sla ID
	   */
	  
	  public String getSLAForOperatingTime(List<Map<String, Object>> operationTimeList);
	  
	  public int isCatSubCatNameExistCategoryConsole(List<HELPDESK_Category>  catListActive,String functionId);

	public List<Map<String, Object>> getSmartSearchAttachmenetIds();
		
	public List<Map<String, Object>> getDefaultAssignDetailsForAdminFinance(
			int categoryid, int locationid);

	public List<Map<String, Object>> getBuildingDetailsById(String locationIDDept,
			int parseInt);

	public List<Map<String, Object>> getIHDLocationDetailDefaultAssignmentDetailsForAdmin(
			int categoryid, int locationDetid);

	public List<Map<String, Object>> getOUTSLAReason(String functionID);
	

	public List<Map<String, Object>> getBUMappingForCat(String loginID);
	
	//By Nisha --changed for HR Helpdesk --start
	public String getCategoryStatusFilter(String categoryId, String flag);
	//By Nisha --changed for HR Helpdesk --end
	
	public List<Map<String, Object>> getDefaultAssignDetailsForHR(int categoryid,
			int locationid);

	public List<Map<String, Object>> getIHDDeptDefaultAssignmentDetailsForHR(int deptid,
			int categoryid, int locationid);
			


	public List<Map<String, Object>> getAutoApproverEmpIDList();
	
	public String getIHDGroupName(String l_groupID);
	
	/*public List<Map<String,Object>> getuserDetailsTableColumns();*/
	
	public List<Map<String,Object>> getAllLoactionList();
	public List<Map<String,Object>> getAllTimeZoneList();
	
	public List<Map<String,Object>> getFunctionDetailsForEscaltionMatrix();
	
	public List<Map<String,Object>> getFunctionForEscaltionMatrix();
	
	public List<Map<String,Object>> getWorkflowStatusList(); //L2: 2786

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 3, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
