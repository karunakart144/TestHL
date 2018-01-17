/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Priority;
import com.igate.iconnect.BO.HELPDESK_RequestorInformation;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.COMMON_CacheSQLQueryConstants;
import com.igate.iconnect.controller.HELPDESK_Edit_ReturnTypeVoidController;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.daoimpl.COMMON_CacheDAOImpl;


public class HELPDESK_EditFunctionWise {
	private static final String s_gradeOrchestration[] = {"AA","AB","AC","BA","BB","GA","GB"};


	private static List<Map<String, Object>> getOUTSLAReason(
			HelpDesk helpDeskObj, COMMON_CacheDAO MasterDataImpl) {
		List<Map<String, Object>> slareasons = MasterDataImpl
				.getListofData(COMMON_CacheSQLQueryConstants.IC_IHD_OUT_OF_SLA_REASON_LIST_VARIABLE);
		List<Map<String, Object>> filteredslareasonsList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> reason : slareasons) {
			String special_prefrences = reason.get("SPECIAL_PREFERENCES")
					.toString();
			if (special_prefrences.equalsIgnoreCase("C") || special_prefrences.equalsIgnoreCase(helpDeskObj
					.getFUNCTION_ID()))
				{	
				filteredslareasonsList.add(reason);
				}
		}
		return filteredslareasonsList;
	}



	private static List<HELPDESK_Category> generateListOfCategories(
			String[] ids, String[] values) {
		List<HELPDESK_Category> categories = new ArrayList<HELPDESK_Category>();
		for (int i = 0; i < ids.length; i++) {
			HELPDESK_Category bean = new HELPDESK_Category();
			bean.setCATEGORY_ID(ids[i]);
			bean.setNAME(values[i]);
			bean.setLINK("");
			bean.setIS_CHANGE_REQUEST("0");
			bean.setPARENT_ID("0");
			categories.add(bean);
		}
		return categories;
	}


	
	public static String editFunctionCorrectionRequiredticket(ModelMap model, HttpServletRequest request,
			HelpDesk helpDeskObj) {

		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDEditDAO = (HELPDESK_EditDAO) ctx
				.getBean("IHDEditDAO");

		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean("HelpDeskTicket");

		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		
		List<HELPDESK_Category> function = getFunctionsBasedOnLoginAndFunction(request,helpDeskObj);

		model.addAttribute("FUNCTION", function);

		List<HELPDESK_Category> category = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getFUNCTION_ID() != null)
			category = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getFUNCTION_ID()));
		else
			category = new ArrayList<HELPDESK_Category>();
		model.addAttribute("CATEGORY", category);

		List<HELPDESK_Category> subcategory = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getCATEGORY_ID() != null)
			subcategory = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getCATEGORY_ID()));
		else
			subcategory = new ArrayList<HELPDESK_Category>();
		model.addAttribute("SUBCATEGORY", subcategory);

		int recommendedPriority = 0;
		if (helpDeskObj.getSUB_CATEGORY_ID() != null) {
			List<Map<String, Object>> categorylsit = MasterDataImpl
					.getCategoriesById("CATEGORY_ID", Integer
							.parseInt(helpDeskObj.getSUB_CATEGORY_ID()));
			// Fixed By Dhiren for Something Wrong
			if (categorylsit != null && categorylsit.size() > 0
					&& categorylsit.get(0).get("RECOMMENDED_PRIORITY") != null)
				recommendedPriority = (Integer) categorylsit.get(0).get(
						"RECOMMENDED_PRIORITY");
			else
				recommendedPriority = 0;

		}
		model.addAttribute("recomendedpriority", MasterDataImpl
				.getPriorityNameById(recommendedPriority));

			model.addAttribute("filterlocationDisplay", "display:none");
		List<Map<String, Object>> severity = MasterDataImpl
				.getListofData(COMMON_CacheSQLQueryConstants.IC_SEVERITY_LIST_VARIABLE);
		model.addAttribute("SEVERITYLIST", severity);

		List<HELPDESK_Priority> priority = MasterDataImpl.getAllPriorities();
		model.addAttribute("PRIORITYLIST", priority);

		List<Map<String, Object>> assigned_groups = HELPDESK_Edit_ReturnTypeVoidController
				.getGroupsForFunciton(request, Integer.parseInt(helpDeskObj
                        .getFUNCTION_ID()), Integer.parseInt(helpDeskObj
                        .getFILTER_GROUP_LOCATION()));

		model.addAttribute("ASSIGNEDGROUPLIST", assigned_groups);

		List<Map<String, Object>> assignedto = null;
		if (helpDeskObj.getASSIGNED_GROUP_ID() != null)
			assignedto = HELPDESK_Edit_ReturnTypeVoidController
					.getLoggedInGroupMembers("All", helpDeskObj
							.getASSIGNED_GROUP_ID());
		else
			assignedto = new ArrayList<Map<String, Object>>();

		model.addAttribute("ASSIGNEDTO", assignedto);

		List<COMMON_Location> location = MasterDataImpl
				.getLocationsForFunction(Integer.parseInt(helpDeskObj
						.getFUNCTION_ID()));
		model.addAttribute("LOCATION", location);

		String selectedbuilding = "NA";
		String selectedfloor = "NA";
		if (helpDeskObj.getLOC_DET_ID() != null) {

			List<Map<String, Object>> LocationDetails = MasterDataImpl
					.getLocationDetailsById("LOC_DET_ID", Integer
							.parseInt(helpDeskObj.getLOC_DET_ID()));
			selectedbuilding = LocationDetails.get(0).get("BUILDING")
					.toString();
			String floorname = String.valueOf(LocationDetails.get(0).get(
					"FLOOR"));
			if (floorname == null || floorname.equalsIgnoreCase("")) {
				selectedfloor = "NA";
			} else {
				selectedfloor = floorname;
			}
		}

		helpDeskObj.setBUILDING(selectedbuilding);
		helpDeskObj.setFLOOR(selectedfloor);

		List<Map<String, Object>> distinctBuildinglocations = new ArrayList<Map<String, Object>>();

		if (helpDeskObj.getLOCATION_ID() != null) {
			distinctBuildinglocations = MasterDataImpl
					.getDistinctBuildingLocations(Integer.parseInt(helpDeskObj
							.getLOCATION_ID()));
		}
		model.addAttribute("BUILDINGS", distinctBuildinglocations);

		List<Map<String, Object>> floorsforbuilding = new ArrayList<Map<String, Object>>();
		if (!selectedfloor.equalsIgnoreCase("NA")) {
			List<Map<String, Object>> locationdetail = MasterDataImpl
					.getLocationDetailsById("LOC_DET_ID", Integer
							.parseInt(helpDeskObj.getLOC_DET_ID()));

			floorsforbuilding = MasterDataImpl
					.getFloorsforBuilding(COMMON_CacheSQLQueryConstants
							.removeUnicodes(locationdetail.get(0).get(
									"BUILDING").toString()));
		}
		model.addAttribute("FLOORS", floorsforbuilding);

		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(helpDeskObj.getREQUESTED_BY());
		model.addAttribute("PROJECTS", projects);

		// Modified as part of implemenation of function wise OUT of SLA Reason/.
		List<Map<String, Object>> slareasons = getOUTSLAReason(helpDeskObj,
				MasterDataImpl);
		model.addAttribute("SLAREASONS", slareasons);

		String workflowID = workflowimpl.getWorkflowID(helpDeskObj
				.getFUNCTION());

		List<String> workflowstatus = null;
		if (helpDeskObj.getWORKFLOW_STATE() != null) {
			workflowstatus = workflowimpl.getNextValidTransition(workflowID,
					helpDeskObj.getWORKFLOW_STATE(), userObj.getUserRole());
		} else {
			workflowstatus = new ArrayList<String>();
		}
		workflowstatus.add(0, helpDeskObj.getWORKFLOW_STATE());
		model.addAttribute("WORKFLOW", workflowstatus);

		HELPDESK_RequestorInformation reqbean = getIHDEditDAO
				.getRequestorDetails(helpDeskObj.getPROJECT_ID(), helpDeskObj
						.getREQUESTED_BY());

		model.addAttribute("REQUESTOR", reqbean);

		String editbuttonaccess = "display:block";

		boolean loginuserExists = false;
		boolean isbelongsToGroup = false;
		for (Map<String, Object> anAssignedto : assignedto) {
			if (anAssignedto.containsValue(loginID)) {
				loginuserExists = true;
				isbelongsToGroup = true;
				break;
			}
		}
		if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			loginuserExists = true;
		} else if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& isbelongsToGroup) {
			loginuserExists = true;
		}
		if (helpDeskObj.getASSIGNED_TO() != null
				&& helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"HelpDesk Queue")) {
			loginuserExists = true;
		}
		if (helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
				"Out of Operation Hours")
				&& (userObj.getUserRole().equalsIgnoreCase("Level 0 Executive") || userObj
						.getUserRole().equalsIgnoreCase("Level 0 Manager"))) {
			loginuserExists = true;
		}
		if (helpDeskObj.getASSIGNED_TO() != null && !loginuserExists) {
			editbuttonaccess = "display:none";
		} else if (!loginuserExists
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			editbuttonaccess = "display:none";
		}

		helpDeskObj.setREQUESTED_BY(helpDeskObj.getREQUESTOR_NAME() + "("
				+ helpDeskObj.getREQUESTED_BY() + ")");

		List<String> viewpermissionmap = workflowimpl
				.getViewOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		List<String> editpermissionmap = workflowimpl
				.getEditOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		List<String> nonepermissionmap = workflowimpl
				.getNoneOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		if (editpermissionmap.contains("PRIORITY_ID")) {
			model.addAttribute("recomendedprioritydisplay", "");
		} else {
			model.addAttribute("recomendedprioritydisplay", "display:none");
		}

		/*
		 * Changed By : Nagamanikanta (714599) Comments : Below Mentioned roles
		 * can't update the ticket without assigning to them self
		 */
		String rolestoRestrictToupdateTicket = bundle
				.getString("rolesCantUpdateTicketWithoutAssign");
		String[] roles_List = rolestoRestrictToupdateTicket.split(",");

		List<String> rolesList = Arrays.asList(roles_List);
		model.addAttribute("rolesToRestrictTheUpdateTicket", rolesList);

		model.addAttribute("editbuttonaccess", editbuttonaccess);
		model.addAttribute("viewpermissionmap", viewpermissionmap);
		model.addAttribute("editpermissionmap", editpermissionmap);
		model.addAttribute("nonepermissionmap", nonepermissionmap);

		helpDeskObj.setASSIGNED_GROUP(helpDeskObj.getASSIGNED_GROUP_ID());
		model.addAttribute("helpDeskObj", helpDeskObj);
		return "HELPDESK_Edit";
	}

	public static String editOtherFunctionticket(ModelMap model, HttpServletRequest request,
			HelpDesk helpDeskObj) {

		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDEditDAO = (HELPDESK_EditDAO) ctx
				.getBean("IHDEditDAO");

		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean("HelpDeskTicket");

		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		
		List<HELPDESK_Category> function = getFunctionsBasedOnLoginAndFunction(request,helpDeskObj);

		model.addAttribute("FUNCTION", function);

		List<HELPDESK_Category> category = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getFUNCTION_ID() != null)
			category = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getFUNCTION_ID()));
		else
			category = new ArrayList<HELPDESK_Category>();
		model.addAttribute("CATEGORY", category);

		List<HELPDESK_Category> subcategory = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getCATEGORY_ID() != null)
			subcategory = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getCATEGORY_ID()));
		else
			subcategory = new ArrayList<HELPDESK_Category>();
		model.addAttribute("SUBCATEGORY", subcategory);

		int recommendedPriority = 0;
		if (helpDeskObj.getSUB_CATEGORY_ID() != null) {
			List<Map<String, Object>> categorylsit = MasterDataImpl
					.getCategoriesById("CATEGORY_ID", Integer
							.parseInt(helpDeskObj.getSUB_CATEGORY_ID()));
			// Fixed By Dhiren for Something Wrong
			if (categorylsit != null && categorylsit.size() > 0
					&& categorylsit.get(0).get("RECOMMENDED_PRIORITY") != null)
				recommendedPriority = (Integer) categorylsit.get(0).get(
						"RECOMMENDED_PRIORITY");
			else
				recommendedPriority = 0;

		}
		model.addAttribute("recomendedpriority", MasterDataImpl
				.getPriorityNameById(recommendedPriority));

		if ((userObj.getUserRole().equalsIgnoreCase("Level 0 Executive") || userObj
				.getUserRole().equalsIgnoreCase("Level 0 Manager"))
				&& (helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"HelpDesk Queue") || helpDeskObj.getWORKFLOW_STATE()
						.equalsIgnoreCase("Assigned"))
				|| helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"Re-Assigned")
				|| helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"Work In Progress")) {
			model.addAttribute("filterlocationDisplay", "");
		} else {
			model.addAttribute("filterlocationDisplay", "display:none");
		}

		List<Map<String, Object>> severity = MasterDataImpl
				.getListofData(COMMON_CacheSQLQueryConstants.IC_SEVERITY_LIST_VARIABLE);
		model.addAttribute("SEVERITYLIST", severity);

		List<HELPDESK_Priority> priority = MasterDataImpl.getAllPriorities();
		model.addAttribute("PRIORITYLIST", priority);

		List<Map<String, Object>> assigned_groups = HELPDESK_Edit_ReturnTypeVoidController
				.getGroupsForFunciton(request, Integer.parseInt(helpDeskObj
                        .getFUNCTION_ID()), Integer.parseInt(helpDeskObj
                        .getFILTER_GROUP_LOCATION()));

		model.addAttribute("ASSIGNEDGROUPLIST", assigned_groups);

		List<Map<String, Object>> assignedto = null;
		if (helpDeskObj.getASSIGNED_GROUP_ID() != null)
			assignedto = HELPDESK_Edit_ReturnTypeVoidController
					.getLoggedInGroupMembers("All", helpDeskObj
							.getASSIGNED_GROUP_ID());
		else
			assignedto = new ArrayList<Map<String, Object>>();

		model.addAttribute("ASSIGNEDTO", assignedto);

		List<COMMON_Location> location = MasterDataImpl
				.getLocationsForFunction(Integer.parseInt(helpDeskObj
						.getFUNCTION_ID()));
		model.addAttribute("LOCATION", location);

		String selectedbuilding = "NA";
		String selectedfloor = "NA";
		if (helpDeskObj.getLOC_DET_ID() != null) {

			List<Map<String, Object>> LocationDetails = MasterDataImpl
					.getLocationDetailsById("LOC_DET_ID", Integer
							.parseInt(helpDeskObj.getLOC_DET_ID()));
			selectedbuilding = LocationDetails.get(0).get("BUILDING")
					.toString();
			String floorname = String.valueOf(LocationDetails.get(0).get(
					"FLOOR"));
			if (floorname == null || floorname.equalsIgnoreCase("")) {
				selectedfloor = "NA";
			} else {
				selectedfloor = floorname;
			}
		}

		helpDeskObj.setBUILDING(selectedbuilding);
		helpDeskObj.setFLOOR(selectedfloor);

		List<Map<String, Object>> distinctBuildinglocations = new ArrayList<Map<String, Object>>();

		if (helpDeskObj.getLOCATION_ID() != null) {
			distinctBuildinglocations = MasterDataImpl
					.getDistinctBuildingLocations(Integer.parseInt(helpDeskObj
							.getLOCATION_ID()));
		}
		model.addAttribute("BUILDINGS", distinctBuildinglocations);

		List<Map<String, Object>> floorsforbuilding = new ArrayList<Map<String, Object>>();
		if (!selectedfloor.equalsIgnoreCase("NA")) {
			List<Map<String, Object>> locationdetail = MasterDataImpl
					.getLocationDetailsById("LOC_DET_ID", Integer
							.parseInt(helpDeskObj.getLOC_DET_ID()));

			floorsforbuilding = MasterDataImpl
					.getFloorsforBuilding(COMMON_CacheSQLQueryConstants
							.removeUnicodes(locationdetail.get(0).get(
									"BUILDING").toString()));
		}
		model.addAttribute("FLOORS", floorsforbuilding);

		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(helpDeskObj.getREQUESTED_BY());
		model.addAttribute("PROJECTS", projects);

		// Modified as part of implemenation of function wise OUT of SLA Reason/.
		List<Map<String, Object>> slareasons = getOUTSLAReason(helpDeskObj,
				MasterDataImpl);
		model.addAttribute("SLAREASONS", slareasons);

		String workflowID = workflowimpl.getWorkflowID(helpDeskObj
				.getFUNCTION());

		List<String> workflowstatus = null;
		if (helpDeskObj.getWORKFLOW_STATE() != null) {
			workflowstatus = workflowimpl.getNextValidTransition(workflowID,
					helpDeskObj.getWORKFLOW_STATE(), userObj.getUserRole());
		} else {
			workflowstatus = new ArrayList<String>();
		}
		workflowstatus.add(0, helpDeskObj.getWORKFLOW_STATE());
		model.addAttribute("WORKFLOW", workflowstatus);

		HELPDESK_RequestorInformation reqbean = getIHDEditDAO
				.getRequestorDetails(helpDeskObj.getPROJECT_ID(), helpDeskObj
						.getREQUESTED_BY());

		model.addAttribute("REQUESTOR", reqbean);

		String editbuttonaccess = "display:block";

		boolean loginuserExists = false;
		boolean isbelongsToGroup = false;
		for (Map<String, Object> anAssignedto : assignedto) {
			if (anAssignedto.containsValue(loginID)) {
				loginuserExists = true;
				isbelongsToGroup = true;
				break;
			}
		}
		if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			loginuserExists = true;
		} else if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& isbelongsToGroup) {
			loginuserExists = true;
		}
		if (helpDeskObj.getASSIGNED_TO() != null
				&& helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"HelpDesk Queue")) {
			loginuserExists = true;
		}
		if (helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
				"Out of Operation Hours")
				&& (userObj.getUserRole().equalsIgnoreCase("Level 0 Executive") || userObj
						.getUserRole().equalsIgnoreCase("Level 0 Manager"))) {
			loginuserExists = true;
		}
		if (helpDeskObj.getASSIGNED_TO() != null && !loginuserExists) {
			editbuttonaccess = "display:none";
		} else if (!loginuserExists
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			editbuttonaccess = "display:none";
		}

		helpDeskObj.setREQUESTED_BY(helpDeskObj.getREQUESTOR_NAME() + "("
				+ helpDeskObj.getREQUESTED_BY() + ")");

		List<String> viewpermissionmap = workflowimpl
				.getViewOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		List<String> editpermissionmap = workflowimpl
				.getEditOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		List<String> nonepermissionmap = workflowimpl
				.getNoneOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());

		if (editpermissionmap.contains("PRIORITY_ID")) {
			model.addAttribute("recomendedprioritydisplay", "");
		} else {
			model.addAttribute("recomendedprioritydisplay", "display:none");
		}

		/*
		 * Changed By : Nagamanikanta (714599) Comments : Below Mentioned roles
		 * can't update the ticket without assigning to them self
		 */
		String rolestoRestrictToupdateTicket = bundle
				.getString("rolesCantUpdateTicketWithoutAssign");
		String[] roles_List = rolestoRestrictToupdateTicket.split(",");

		List<String> rolesList = Arrays.asList(roles_List);
		model.addAttribute("rolesToRestrictTheUpdateTicket", rolesList);

		model.addAttribute("editbuttonaccess", editbuttonaccess);
		model.addAttribute("viewpermissionmap", viewpermissionmap);
		model.addAttribute("editpermissionmap", editpermissionmap);
		model.addAttribute("nonepermissionmap", nonepermissionmap);

		helpDeskObj.setASSIGNED_GROUP(helpDeskObj.getASSIGNED_GROUP_ID());
		model.addAttribute("helpDeskObj", helpDeskObj);
		return "HELPDESK_Edit";
	}
	
	public static String editLighthouseTicket(ModelMap model,
			HttpServletRequest request, HelpDesk helpDeskObj) {


		String loginID = (String) request.getSession().getAttribute(
		"userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDEditDAO = (HELPDESK_EditDAO) ctx
				.getBean("IHDEditDAO");
		
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean("HelpDeskTicket");
		
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");

		List<HELPDESK_Category> function = getFunctionsBasedOnLoginAndFunction(request,helpDeskObj);

		model.addAttribute("FUNCTION", function);
		
		List<HELPDESK_Category> category = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getFUNCTION_ID() != null)
			category = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getFUNCTION_ID()));
		else
			category = new ArrayList<HELPDESK_Category>();
		model.addAttribute("CATEGORY", category);
		
		List<HELPDESK_Category> subcategory = null; // new
													// ArrayList<CategoryBean>();
		if (helpDeskObj.getCATEGORY_ID() != null)
			subcategory = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
					Integer.parseInt(helpDeskObj.getCATEGORY_ID()));
		else
			subcategory = new ArrayList<HELPDESK_Category>();
		model.addAttribute("SUBCATEGORY", subcategory);
		
		int recommendedPriority = 0;
		if (helpDeskObj.getSUB_CATEGORY_ID() != null) {
			List<Map<String, Object>> categorylsit = MasterDataImpl
					.getCategoriesById("CATEGORY_ID", Integer
							.parseInt(helpDeskObj.getSUB_CATEGORY_ID()));
			if (categorylsit != null && categorylsit.size() > 0
					&& categorylsit.get(0).get("RECOMMENDED_PRIORITY") != null)
				recommendedPriority = (Integer) categorylsit.get(0).get(
						"RECOMMENDED_PRIORITY");
			else
				recommendedPriority = 0;
		
		}
		model.addAttribute("recomendedpriority", MasterDataImpl
				.getPriorityNameById(recommendedPriority));

		model.addAttribute("filterlocationDisplay", "display:none");

		List<Map<String, Object>> severity = MasterDataImpl
				.getListofData(COMMON_CacheSQLQueryConstants.IC_SEVERITY_LIST_VARIABLE);
		model.addAttribute("SEVERITYLIST", severity);
		
		List<HELPDESK_Priority> priority = MasterDataImpl.getAllPriorities();
		model.addAttribute("PRIORITYLIST", priority);
		
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();
		Map<String, Object> groupmap = new HashMap<String, Object>();
		groupmap.put("GROUP_ID", helpDeskObj.getASSIGNED_GROUP_ID());
		groupmap.put("GROUP_NAME", helpDeskObj.getASSIGNED_GROUP());
		
		assigned_groups.add(groupmap);
		
		model.addAttribute("ASSIGNEDGROUPLIST", assigned_groups);
		
		List<Map<String, Object>> assignedto = null;
		if (helpDeskObj.getASSIGNED_GROUP_ID() != null)
			assignedto = HELPDESK_Edit_ReturnTypeVoidController
					.getLoggedInGroupMembers("All", helpDeskObj
							.getASSIGNED_GROUP_ID());
		else
			assignedto = new ArrayList<Map<String, Object>>();
		
		model.addAttribute("ASSIGNEDTO", assignedto);
		
		List<COMMON_Location> location = MasterDataImpl
				.getLocationsForCategory(Integer.parseInt(helpDeskObj
						.getSUB_CATEGORY_ID()));
		model.addAttribute("LOCATION", location);
		
		
		List<Map<String, Object>> distinctBuildinglocations = new ArrayList<Map<String, Object>>();
		
		if (helpDeskObj.getLOCATION_ID() != null) {
			distinctBuildinglocations = MasterDataImpl
					.getDistinctBuildingLocations(Integer.parseInt(helpDeskObj
							.getLOCATION_ID()));
		}
		
		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(helpDeskObj.getREQUESTED_BY());
		model.addAttribute("PROJECTS", projects);
		
		// Modified as part of implemenation of function wise OUT of SLA Reason/.
		List<Map<String, Object>> slareasons = getOUTSLAReason(helpDeskObj,
				MasterDataImpl);
		model.addAttribute("SLAREASONS", slareasons);
		
		String workflowID = workflowimpl.getWorkflowID(helpDeskObj
				.getFUNCTION());
		
		List<String> workflowstatus = null;
		if (helpDeskObj.getWORKFLOW_STATE() != null) {
			workflowstatus = workflowimpl.getNextValidTransition(workflowID,
					helpDeskObj.getWORKFLOW_STATE(), userObj.getUserRole());
		} else {
			workflowstatus = new ArrayList<String>();
		}
		workflowstatus.add(0, helpDeskObj.getWORKFLOW_STATE());
		model.addAttribute("WORKFLOW", workflowstatus);
		
		HELPDESK_RequestorInformation reqbean = getIHDEditDAO
				.getRequestorDetails(helpDeskObj.getPROJECT_ID(), helpDeskObj
						.getREQUESTED_BY());
		
		model.addAttribute("REQUESTOR", reqbean);
		
		String editbuttonaccess = "display:block";
		
		boolean loginuserExists = false;
		boolean isbelongsToGroup = false;
		for (Map<String, Object> anAssignedto : assignedto) {
			if (anAssignedto.containsValue(loginID)) {
				loginuserExists = true;
				isbelongsToGroup = true;
				break;
			}
		}
		if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			loginuserExists = true;
		} else if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& isbelongsToGroup) {
			loginuserExists = true;
		}
		if (helpDeskObj.getASSIGNED_TO() != null
				&& helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"HelpDesk Queue")) {
			loginuserExists = true;
		}
		
		if (helpDeskObj.getASSIGNED_TO() != null && !loginuserExists) {
			editbuttonaccess = "display:none";
		} else if (helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase("Open")
				&& !userObj.getUserRole().equalsIgnoreCase("Manager")
				&& !userObj.getUserRole().equalsIgnoreCase("Executive")
				&& !helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)) {
			editbuttonaccess = "display:none";
		} else if (helpDeskObj.getREQUESTED_BY().equalsIgnoreCase(loginID)
				&& !userObj.getUserRole().equalsIgnoreCase("User")
				&& !isbelongsToGroup) {
			editbuttonaccess = "display:none";
		}else if(helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase("Sent For Approval")){
			editbuttonaccess = "display:none";
		}
		
		helpDeskObj.setREQUESTED_BY(helpDeskObj.getREQUESTOR_NAME() + "("
				+ helpDeskObj.getREQUESTED_BY() + ")");
		
		List<String> viewpermissionmap = workflowimpl
				.getViewOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());
		
		List<String> editpermissionmap = workflowimpl
				.getEditOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());
		
		List<String> nonepermissionmap = workflowimpl
				.getNoneOnlyPermissionForField(workflowID, userObj
						.getUserRole(), helpDeskObj.getWORKFLOW_STATE());
		
		if (editpermissionmap.contains("PRIORITY_ID")) {
			model.addAttribute("recomendedprioritydisplay", "");
		} else {
			model.addAttribute("recomendedprioritydisplay", "display:none");
		}
		
		String rolestoRestrictToupdateTicket = bundle
				.getString("rolesCantUpdateTicketWithoutAssign");
		String[] roles_List = rolestoRestrictToupdateTicket.split(",");
		
		List<String> rolesList = Arrays.asList(roles_List);
		model.addAttribute("rolesToRestrictTheUpdateTicket", rolesList);
		
		model.addAttribute("editbuttonaccess", editbuttonaccess);
		model.addAttribute("viewpermissionmap", viewpermissionmap);
		model.addAttribute("editpermissionmap", editpermissionmap);
		model.addAttribute("nonepermissionmap", nonepermissionmap);
		
		helpDeskObj.setASSIGNED_GROUP(helpDeskObj.getASSIGNED_GROUP_ID());
		model.addAttribute("helpDeskObj", helpDeskObj);
		return "HELPDESK_Edit";
	}
	
	public static List<HELPDESK_Category> getFunctionsBasedOnLoginAndFunction(HttpServletRequest request,HelpDesk helpDeskObj){

		List<HELPDESK_Category> function = null;
		String loginID = (String) request.getSession().getAttribute("userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);		
		if(userObj.getUserRole().equalsIgnoreCase("Level 0 Executive") || userObj
				.getUserRole().equalsIgnoreCase("Level 0 Manager")){

			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
					.getBean("GetMasterData");
			
			function =	 MasterDataImpl.getCategoriesByColumn("PARENT_ID", 0);
			if(!helpDeskObj.getFUNCTION().equalsIgnoreCase("Function Correction Required")){
				List<HELPDESK_Category> sortedfunc = new ArrayList<HELPDESK_Category>();
				for(HELPDESK_Category func : function){
					if(!func.getNAME().equalsIgnoreCase("Function Correction Required")){
						sortedfunc.add(func);
					}
				}
				function = sortedfunc;
			}

		}else if(helpDeskObj.getFUNCTION().equalsIgnoreCase("Function Correction Required")){
			
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
					.getBean("GetMasterData");
			
			function =	 MasterDataImpl.getCategoriesByColumn("PARENT_ID", 0);
		}
		else{
			
			String[] categoryids_List = {helpDeskObj.getFUNCTION_ID()};

			String[] categorynames_List = {helpDeskObj.getFUNCTION()};

			function = generateListOfCategories(categoryids_List, categorynames_List);
		}
		
		return function;
	}
	
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Nov 16, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
