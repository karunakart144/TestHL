/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.HELPDESK_AssetInformation;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Priority;
import com.igate.iconnect.BO.HELPDESK_RequestorInformation;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MASTER_Create;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.MASTER_CreateDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_EditFunctionWise;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class HELPDESK_Edit_ReturnTypeStringOrMAVController {
	private static Logger log = Logger
			.getLogger(HELPDESK_Edit_ReturnTypeStringOrMAVController.class);

	private static final String HELPDESK_EDITDAO_IMPL = "IHDEditDAO";
	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String MASTER_EDITDAO_IMPL = "MasterTicketDAO";	

	/**
     * This method handles all the exceptions occured in this controller 
     * 
     */

	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {
		log.error("", e);
		return "Error";

	}

	/**
     * This method Will return to edit page of ticket with the ticket details 
     * 
     */

	@RequestMapping(value = "/HELPDESK_Edit.htm", method = RequestMethod.GET)
	public String hdTicketEdit(@RequestParam String id, ModelMap model,
			HttpServletRequest request) {

		List<HelpDesk> ticketDetails = new ArrayList<HelpDesk>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDEditDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);

		String query = HELPDESK_SQLQueryConstants.IC_HD_TICKET_DETAILS + id;
		ticketDetails = getIHDEditDAO.getReqList(query, request);
		HelpDesk helpDeskObj = ticketDetails.get(0);
		if(null!=helpDeskObj.getPROJECT_ID() && !helpDeskObj.getPROJECT_ID().equalsIgnoreCase("")){
		Map<String,Object> allProjectDetails=getIHDEditDAO.getAllProjectDetails(id);
		helpDeskObj.setLEVEL_2_PROJECT_ID(allProjectDetails.get("LEVEL 2 PROJECT ID").toString());
		helpDeskObj.setLEVEL_3_PROJECT_ID(allProjectDetails.get("LEVEL 3 PROJECT ID").toString());
		helpDeskObj.setPROJECT_ID(allProjectDetails.get("MASTER PROJECT ID").toString());
		helpDeskObj.setLEVEL_2_PROJECT_NAME(allProjectDetails.get("LEVEL 2 PROJECT NAME").toString());
		helpDeskObj.setLEVEL_3_PROJECT_NAME(allProjectDetails.get("LEVEL 3 PROJECT NAME").toString());
		helpDeskObj.setPROJECT_NAME(allProjectDetails.get("MASTER PROJECT NAME").toString());
		}else{
			helpDeskObj.setPROJECT_NAME("None");
		}
		
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		MASTER_Create masterBean=MasterDaoImpl.getIsChildLinked(helpDeskObj.getTICKET_ID());
		if(masterBean.getREFERENCE_ID()==null){
			helpDeskObj.setIS_MASTER_LINK("No");
		}else
		{
			helpDeskObj.setIS_MASTER_LINK("Yes");
		}
		
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		helpDeskObj.setSUBJECT(converter.convertSpecialChars(helpDeskObj
				.getSUBJECT()));
		helpDeskObj.setDESCRIPTION(converter.convertSpecialChars(helpDeskObj
				.getDESCRIPTION()));
		helpDeskObj.setADDITIONAL_INFO(converter
				.convertSpecialChars(helpDeskObj.getADDITIONAL_INFO()));
		helpDeskObj.setPRIVATE_NOTES(converter.convertSpecialChars(helpDeskObj
				.getPRIVATE_NOTES()));
		helpDeskObj.setOUT_OF_SLA_COMMENTS(converter
				.convertSpecialChars(helpDeskObj.getOUT_OF_SLA_COMMENTS()));
		helpDeskObj.setOUT_OF_SLA_REASON(converter
				.convertSpecialChars(helpDeskObj.getOUT_OF_SLA_REASON()));
		helpDeskObj.setONHOLD_COMMENTS(converter
				.convertSpecialChars(helpDeskObj.getONHOLD_COMMENTS()));
		helpDeskObj.setREOPEN_COMMENTS(converter
				.convertSpecialChars(helpDeskObj.getREOPEN_COMMENTS()));		
		helpDeskObj.setRESOLUTION_COMMENTS(converter
				.convertSpecialChars(helpDeskObj.getRESOLUTION_COMMENTS()));
		helpDeskObj.setFEEDBACK_COMMENTS(converter
				.convertSpecialChars(helpDeskObj.getFEEDBACK_COMMENTS()));
		if (helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
				"Closed(By System)")
				|| helpDeskObj.getWORKFLOW_STATE().equalsIgnoreCase(
						"AutoClosed(By System)")) {
			getTicketViewDetailsOnly(request, helpDeskObj, model);
			model.addAttribute("helpDeskObj", helpDeskObj);
			return "HELPDESK_Edit";
		}

		String functionName = helpDeskObj.getFUNCTION();

		if (functionName.equalsIgnoreCase("Function Correction Required")) {
			HELPDESK_EditFunctionWise.editFunctionCorrectionRequiredticket(model,
                    request, helpDeskObj);
		}else
		if (functionName.equalsIgnoreCase("Life and Health Operations Canada")) { //L2-2078
			HELPDESK_EditFunctionWise.editLighthouseTicket(model,
                    request, helpDeskObj);
		}	else{
			HELPDESK_EditFunctionWise.editOtherFunctionticket(model,
                    request, helpDeskObj);
		}
		

		return "HELPDESK_Edit";
	}

	/**
     * This method give the only details avaiable in the ticket table 
     * This gets called when the ticket is in closed state 
     * 
     */
	
	public void getTicketViewDetailsOnly(HttpServletRequest request,
			HelpDesk helpDeskObj, ModelMap model) {

		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDEditDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);

		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		List<HELPDESK_Category> category = new ArrayList<HELPDESK_Category>();
		HELPDESK_Category catbean = new HELPDESK_Category();
		catbean.setCATEGORY_ID(helpDeskObj.getFUNCTION_ID());
		catbean.setNAME(helpDeskObj.getFUNCTION());
		category.add(catbean);
		model.addAttribute("FUNCTION", category);

		category = new ArrayList<HELPDESK_Category>();
		catbean = new HELPDESK_Category();
		catbean.setCATEGORY_ID(helpDeskObj.getCATEGORY_ID());
		catbean.setNAME(helpDeskObj.getCATEGORY());
		category.add(catbean);
		model.addAttribute("CATEGORY", category);

		category = new ArrayList<HELPDESK_Category>();
		catbean = new HELPDESK_Category();
		catbean.setCATEGORY_ID(helpDeskObj.getSUB_CATEGORY_ID());
		catbean.setNAME(helpDeskObj.getSUBCATEGORY());
		category.add(catbean);
		model.addAttribute("SUBCATEGORY", category);

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

		List<Map<String, Object>> arraylist = new ArrayList<Map<String, Object>>();

		if (helpDeskObj.getSEVERITY_ID() != null
				&& !helpDeskObj.getSEVERITY_ID().equalsIgnoreCase("")) {
			arraylist = MasterDataImpl.getSeveritiesById(Integer
					.parseInt(helpDeskObj.getSEVERITY_ID()));

		}
		model.addAttribute("SEVERITYLIST", arraylist);

		List<HELPDESK_Priority> priorities = new ArrayList<HELPDESK_Priority>();
		if (helpDeskObj.getPRIORITY_ID() != null) {
			HELPDESK_Priority priority = new HELPDESK_Priority();
			priority.setPRIORITYID(helpDeskObj.getPRIORITY_ID());
			priority.setDESCRIPTION(MasterDataImpl.getPriorityNameById(Integer
					.parseInt(helpDeskObj.getPRIORITY_ID())));
			priorities.add(priority);
		}
		model.addAttribute("PRIORITYLIST", priorities);

		arraylist = new ArrayList<Map<String, Object>>();
		if (helpDeskObj.getASSIGNED_GROUP_ID() != null) {
			Map<String, Object> groupmap = new HashMap<String, Object>();
			groupmap.put("GROUP_ID", helpDeskObj.getASSIGNED_GROUP_ID());
			groupmap.put("GROUP_NAME", helpDeskObj.getASSIGNED_GROUP());

			arraylist.add(groupmap);
		}
		model.addAttribute("ASSIGNEDGROUPLIST", arraylist);

		arraylist = new ArrayList<Map<String, Object>>();
		if (helpDeskObj.getASSIGNED_TO() != null) {
			Map<String, Object> groupmember = new HashMap<String, Object>();
			groupmember.put("member_id", helpDeskObj.getASSIGNED_TO());
			Map<String, Object> employee = getIHDEditDAO
					.getEmployeeDetails(helpDeskObj.getASSIGNED_TO());
			groupmember.put("member_name_id", employee.get("NAME") + "("
					+ employee.get("EMPLOYEE_ID") + ")");
			arraylist.add(groupmember);
		}

		model.addAttribute("ASSIGNEDTO", arraylist);

		COMMON_Location locbean = new COMMON_Location();
		locbean.setLOCATION_ID(helpDeskObj.getLOCATION_ID());
		locbean.setCITY(helpDeskObj.getLOCATION_CITY());

		List<COMMON_Location> location = new ArrayList<COMMON_Location>();
		location.add(locbean);
		model.addAttribute("LOCATION", location);


		arraylist = new ArrayList<Map<String, Object>>();
		if (helpDeskObj.getPROJECT_ID() != null) {
			Map<String, Object> singlemap = new HashMap<String, Object>();
			singlemap.put("PROJ_ID", "0");
			singlemap.put("NAME", "None");
			arraylist.add(singlemap);
			singlemap = new HashMap<String, Object>();
			singlemap.put("PROJ_ID", helpDeskObj.getPROJECT_ID());
			singlemap.put("NAME", helpDeskObj.getPROJECT_NAME());
			arraylist.add(singlemap);
			model.addAttribute("PROJECTS", arraylist);
		} else {
			model.addAttribute("PROJECTS", arraylist);
		}
		arraylist = new ArrayList<Map<String, Object>>();
		arraylist.add(MasterDataImpl.getOutofSlaRowById(Integer
				.parseInt(helpDeskObj.getOUT_OF_SLA_REASON())));
		model.addAttribute("SLAREASONS", arraylist);

		String workflowID = workflowimpl.getWorkflowID(helpDeskObj
                .getFUNCTION());

		List<String> workflowstatus = new ArrayList<String>();
		workflowstatus.add(0, helpDeskObj.getWORKFLOW_STATE());
		model.addAttribute("WORKFLOW", workflowstatus);

		HELPDESK_RequestorInformation reqbean = getIHDEditDAO
				.getRequestorDetails(helpDeskObj.getPROJECT_ID(),
                        helpDeskObj.getREQUESTED_BY());

		model.addAttribute("REQUESTOR", reqbean);

		String editbuttonaccess = "display:none";

		helpDeskObj.setREQUESTED_BY(helpDeskObj.getREQUESTOR_NAME() + "( "
				+ helpDeskObj.getREQUESTED_BY() + " )");

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
		
		int attachmentCountScript=getIHDEditDAO.getAttachment_EmergencyScript(helpDeskObj.getTICKET_ID());
		int attachmentCountTestRep=getIHDEditDAO.getAttachment_EmergencyTestRep(helpDeskObj.getTICKET_ID());
		int attachmentCountAppMail=getIHDEditDAO.getAttachment_EmergencyApp(helpDeskObj.getTICKET_ID());
		model.addAttribute("attachmentCountScript", attachmentCountScript);
		model.addAttribute("attachmentCountTestRep", attachmentCountTestRep);
		model.addAttribute("attachmentCountAppMail", attachmentCountAppMail);

		model.addAttribute("editbuttonaccess", editbuttonaccess);
		model.addAttribute("viewpermissionmap", viewpermissionmap);
		model.addAttribute("editpermissionmap", editpermissionmap);
		model.addAttribute("nonepermissionmap", nonepermissionmap);

		helpDeskObj.setASSIGNED_GROUP(helpDeskObj.getASSIGNED_GROUP_ID());
		model.addAttribute("helpDeskObj", helpDeskObj);
	}

	/**
     * This method Will show the comments page to update the comments in ticket
     * Ex : additional info,private notes..etc 
     * 
     */

	@RequestMapping(value = "/HELPDESK_UpdateComments.htm", method = RequestMethod.GET)
	public String updateCommentsForIHDTicket(ModelMap model,
			HttpServletRequest request) {

		return "HELPDESK_UpdateComments";
	}

	/**
     * This method Will return the asset parent details of the ticket
     * 
     */

	@RequestMapping(value = "HELPDESK_AssetParentDetail.htm", method = RequestMethod.GET)
	public String assetDetailView(@RequestParam String parentSlNo,
			ModelMap model, HttpServletRequest request) {
		HELPDESK_AssetInformation assetView = new HELPDESK_AssetInformation();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		assetView = getIHDDAO.getAssetDetailView(parentSlNo);
		model.addAttribute("assetView", assetView);
		return "HELPDESK_AssetParentDetail";
	}

	/**
     * This method Will return the asset component details of the ticket
     * 
     */

	@RequestMapping(value = "HELPDESK_AssetComponentDetail.htm", method = RequestMethod.GET)
	public String assetCompDetailView(@RequestParam String childSlNo,
			String assetCompID, ModelMap model, HttpServletRequest request)
			throws JSONException {
		HELPDESK_AssetInformation assetCompView = new HELPDESK_AssetInformation();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		assetCompView = getIHDDAO.getAssetCompDetailView(childSlNo);
		model.addAttribute("assetCompView", assetCompView);
		return "HELPDESK_AssetComponentDetail";
	}

	/**
     * This method Will return the asset information page 
     * 
     */
	
	@RequestMapping(value = "HELPDESK_AssetInformation.htm", method = RequestMethod.GET)
	public String ticketAssetData(ModelMap model, HttpServletRequest request) {
		return "HELPDESK_AssetInformation";
	}
	
	
	@RequestMapping(value = "/getMasterTicketDet.htm", method = RequestMethod.GET)
	public void getChildTicketList(@RequestParam String masterId,HttpServletRequest request, HttpServletResponse response)
			throws JSONException {
	
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		MASTER_Create masterCreate = MasterDaoImpl.getMasterDet(masterId);
				
		
			String ect=null;				
			ect=CustomDateFormatConstants
            .showUserTimeZonewithTimezoneID(
            		masterCreate.getECT().toString(), 67);			
			
		masterCreate.setECT(ect);
		
		
		JsonUtility.sendData(masterCreate, response);
	}

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:706638
 Changes Made on:Mar 21, 2012
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
