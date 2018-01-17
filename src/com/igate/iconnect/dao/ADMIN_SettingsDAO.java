/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;

import com.igate.iconnect.BO.ADMIN_CreateUserBO;
import com.igate.iconnect.BO.ADMIN_ExcelUpload;
import com.igate.iconnect.BO.ADMIN_ModifyLocation;
import com.igate.iconnect.BO.HELPDESK_Category;

public interface ADMIN_SettingsDAO {

    public Map<String, Object> verifyAssignedTicket(String groupID,
            String groupMemID, String loginId);

    public int addGroupMemberDetails(String roleID,String groupID, String groupMemID,
            String loginId,String accesslevel,String assignmentReqd);

    public int userRoleInactive(String empId, String roleId, String loginId);

    public int addUserRoleDetails(String roleID, String empID, String loginId);
    
    public String createGroup(JSONObject jsonObj, String loginId) throws JSONException;

	public int groupFunctionMapping(String groupId, JSONObject jsonObj, int slaId, String loginId) throws DataAccessException, JSONException;

	public int groupMemberDetails(String groupId, JSONArray jsonObjMem, String loginId);

	public List<Map<String, Object>> getGroupDetailsForFunction(String functionId, String memberId, String status,String loginID,String roleName);

	public String modifyGroup(JSONArray jsonArrayObj, JSONArray oldjsonArrayObj, String loginID) throws JSONException;
	
	/**
	 * Admin Console:Location Adddition
	 * @param country
	 * @param city
	 * @param timezoneID
	 * @param isActive
	 * @param createdBy
	 * @param createdDate
	 * @return result if data inserted or not
	 */
	public int addLocationMasterDetail(String country,String city,String timezoneID,String isActive,String createdBy,String createdDate);
	
	/**
	 * Admin Console:Operation Time Addition
	 * @param operationTimeList
	 * @param cityName
	 * @return slaID
	 */
	public String insertSLAMasterAdminConsoleLocAdd( String slaID,final List<Map<String, Object>> operationTimeList,final String cityName,final String createdBy,final String createdDate,final int locnIDe,final String functionID,final int locnDetIde,final  String[] holidayList);
	
	
	public int insertLocDetailAdminConsoleLocAddIT(final List<Map<String, Object>> buildingList,final int locnIDe,final String functionID,final String createdBy,final String createdDate);
	
	public void insertApproverEmpDet(final List<Map<String, Object>> approverList,final int locnIDe,final String createdBy,final String createdDate);
	
	public int insertDefaultDeptDefaultGroupForLocation(final List<Map<String, Object>> grpMapList,final List<Map<String, Object>> grpMapDeptList,final String functionID,final String slaId,final String locationId,final String createdBy,final String createdDate);
	
	public List<Map<String, Object>> getApproverEmployeeDetail(String statusValue,String approverName,String statusVal,String subcategoryID);
	
	public List<Map<String,Object>> getOpenTicketsForCategoryAndSubCategory(List<HELPDESK_Category> categoryList,String catOrSubCat);
	
	public int countOfOpenTickets(String categoryIDe);
	
	public int countOfOpenTicketsSubCategory(String subCategoryIDe) ;
	
	public String updateCategoryAsInactive(List<HELPDESK_Category> catListInactive,List<HELPDESK_Category> subCatListInactive,String loginID);
	
	
	
	public String updateCategory(List<HELPDESK_Category> catListActive,String loginID);
	public String updateLocation(List<ADMIN_ModifyLocation> locationMapList,String loginID,String LocationName);
	


	public void groupMemberAudit(String groupID, String groupMemID, String oldStatus, String newStatus, String loginID);
	
	//Added for admin screen role addition
	public List<Map<String, Object>> getEmployeeRoleDetail(String employeeId);
	public String updateRoleDetails(JSONArray jsonArrayObj,String loginID) throws JSONException;
	
	public List<Map<String, Object>> getAddRoleDetails(String employeeId);
	
	public List<Map<String, Object>> getAddRoleLocDetails(String employeeId);
	
	public String insertRoleDetails(final JSONObject jsonArrayObj,final JSONObject locArrayObj,final String loginID) throws JSONException;
	
	public String updateGroupRoleDetails(final JSONArray jsonArrayObj,final String loginID) throws JSONException;
	// Added for Excel upload in admin screen  by anjana
//	public String updateTable(Vector dataholder, String loginID, ADMIN_ExcelUpload eUpload) throws SQLException;
	//public String checkvalue(String st, Cell myCell,int j);
	public  List<Map<String, Object>>  getTablelist(String query);

	public String createUserProfile(ADMIN_CreateUserBO aDMIN_CreateUserBO,String loginID);

	public List<Map<String, Object>> getLHProjects();
	
	public String insertLHProjectData(String projectId,String loginId);

	public List<Map<String,Object>> getEmployeeProfInfo(String employeeId);

	public String updateProjectDetails(JSONArray jsonArrayObj, String loginID);
	
	//L2: 2751
	public Map<String, Object> getEmployeeDetailsForAdminConsole(String empId);
	}
