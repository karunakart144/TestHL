/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.BO.HELPDESK_Approval;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.LoginDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_AutoAssignment;
import com.igate.iconnect.util.HELPDESK_ECTPopulator;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.JsonUtility;
import com.igate.iconnect.util.iConnectMailSender;

@Controller(value = "HELPDESK_Edit_ReturnTypeVoidController")
public class HELPDESK_Edit_ReturnTypeVoidController {
	private static Logger log = Logger
			.getLogger(HELPDESK_Edit_ReturnTypeVoidController.class);

	private static final String HELPDESK_EDITDAO_IMPL = "IHDEditDAO";
	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String WORKFLOW_DAO_IMPL = "workFlowDAOImpl";
	private static final String HELPDESK_DAO_IMPL = "HelpDeskTicket";
	//private static final String SERVICE_GAMIFICATION = "gamification";

	private static final String TICKET_ID_VARIABLE = "TICKET_ID";
	private static final String ADDITIONAL_INFO_VARIABLE = "ADDITIONAL_INFO";
	private static final String RESOLUTION_COMMENTS_VARIABLE = "RESOLUTION_COMMENTS";
	private static final String PRIVATE_NOTES_VARIABLE = "PRIVATE_NOTES";
	private static final String ONHOLD_COMMENTS_TR_VARIABLE = "ONHOLD_COMMENTS_TR";
	private static final String OUT_OF_SLA_COMMENTS_VARIABLE = "OUT_OF_SLA_COMMENTS";
	private static final String REOPEN_COMMENTS_TR_VARIABLE = "REOPEN_COMMENTS_TR";
	private static final String ONHOLD_COMMENTS_VARIABLE = "ONHOLD_COMMENTS";
	private static final String REOPEN_COMMENTS_VARIABLE = "REOPEN_COMMENTS";
	private static final String FUNCTION_ID_VARIABLE = "FUNCTION_ID";
	private static final String CATEGORY_ID_VARIABLE = "CATEGORY_ID";
	private static final String SUB_CATEGORY_ID_VARIABLE = "SUB_CATEGORY_ID";
	private static final String PRIORITY_ID_VARIABLE = "PRIORITY_ID";
	private static final String ASSIGNED_GROUP_VARIABLE = "ASSIGNED_GROUP";
	private static final String GROUP_ID_VARIABLE = "GROUP_ID";
	private static final String LOCATION_ID_VARIABLE = "LOCATION_ID";
	private static final String WORKFLOW_STATE_VARIABLE = "WORKFLOW_STATE";
	private static final String BROWSER_DETAILS_VARIABLE = "BROWSER_DETAILS";
	private static final String VERSION_NO_VARIABLE = "VERSION_NO";
	private static final String STATUS_VARIABLE = "status";
	private static final String MESSAGE_VARIABLE = "message";
	private static final String USER_LOGIN_ID_VARIABLE = "userLoginId";
	private static final String CREATED_DATE_STORAGE_VARIABLE = "CREATED_DATE_STORAGE";
	private static final String OPERATION_HOURS_AVAILABLE_VARIABLE = "OPERATION_HOURS_AVAILABLE";
	private static final String IS_CHANGE_REQUEST_VARIABLE = "IS_CHANGE_REQUEST";
	private static final String ECT_VARIABLE = "ECT";
	private static final String CHANGED_DATE_STORAGE_VARIABLE = "CHANGED_DATE_STORAGE";

	private static final String RBC_LIGHTHOUSE_FUNCTION_VARIABLE = "Life and Health Operations Canada";
	private static final String ASSIGNED_STATE_VARIABLE = "Assigned";
	private static final String HELPDESK_QUEUE_STATE_VARIABLE = "HelpDesk Queue";
	private static final String FUNCTION_SPECIFIC_FIELD_VARIABLE = "95";
	private static final String APPROVER_ID_VARIABLE = "APPROVER_ID";
	private static final String MANAGER_ID_VARIABLE = "MANAGER_ID";
	private static final String REPORTING_MANAGER_ID_VARIABLE = "REPORTING_MANAGER_ID";
	
	private static final String ROLE_IDE_EXECUTIVE="1";
	private static final String ROLE_IDE_TL="3";
	
	/*Variables For AutoAssignment*/
	private static final String AUTO_ASSIGNMENT_JSON_VARIABLE="autoAssignmentFlagJson";
	private static final String PRIORITY_FLAG_VARIABLE="priorityChangeFlag";
	private static final String STATUS_FLAG_VARIABLE="statusChangeFlag";
	private static final String ASSIGNED_TO_FLAG="assignedToChangeFlag";
	private static final String OLD_STATUS_VARIABLE="oldStatus";
	private static final String OLD_PRIORITY_VARIABLE="oldPriority";
	private static final String OLD_ASSIGNED_TO_VARIABLE="oldAssignedTo";
	private static final String NEW_ASSIGNED_TO_VARIABLE="newAssignedTo";
	private static final String ASSIGNED_TO_VARIABLE="ASSIGNED_TO";
	private static final String OUT_OF_OPERATION_STATUS_VARIABLE="Out of Operation Hours";
	private static final String SUCCESS_VARIABLE="Success";
	private static final String VALID_FUNCTIONS_FOR_AA_PROPERTY="validFunctionsForAutoAssignment";
	private static final String PROPERTY_FILE_NAME="iconnect";
	private static final String MANAGER_VARIABLE="MANAGER";
	private static final String ROLE_IDE_VARIABLE="ROLE_ID";
	/*END :Variables For  AutoAssignment*/
	/* Added for Emergency L1*/
	private static final String IS_EMERGENCY_TICKET_VARIABLE="IS_EMERGENCY_TICKET";
	private static final String EMERGENCY_TYPE_VARIABLE="EMERGENCY_TYPE";
	private static final String LEVEL2_ASSIGNED_ENGINEER_VARIABLE="LEVEL2_ASSIGNED_ENGINEER";
	private static final String SUB_STATUS_VARIABLE="SUB_STATUS";
	private static final String DEPLOYMENT_INSTRUCTION = "DEPLOYMENT_INSTRUCTION";
	private static final String REASON_SEND_BACK="REASON_SEND_BACK";
	/*Emergency L1*/
	
	private static final String LEVEL_2_PROJECT_ID="LEVEL_2_PROJECT_ID";
	private static final String LEVEL_3_PROJECT_ID="LEVEL_3_PROJECT_ID";
	private static final String REQUESTED_BY="REQUESTED_BY";
	
	//L2 1184
	private static final String ASSIGNED_FOR_AUTOMACTION_STATE_VARIABLE = "Assigned For Automation";
	private static final String WAITING_FOR_USER_RESPONSE_STATE_VARIABLE = "Waiting For User Response";

	
	
	/**
	 * This method Will handle all the exceptions occured in this controller
	 * 
	 */

	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("", e);		
		if(e.getMessage().contains("JSONObject[\"ASSIGNED_TO\"] not found")){
			result.put(STATUS_VARIABLE, "Error");
			result.put(
					MESSAGE_VARIABLE,
					"No Engineers/TL have logged in .Manager of the selected group is inactive.Hence unable to assign the ticket !!");
		}else{
			result.put(STATUS_VARIABLE, "Error");
			result.put(MESSAGE_VARIABLE, "System Error !!");
		}
		JsonUtility.sendData(result, response);
	}

	/**
	 * To lock the Ticket when user clicks on edit button
	 * 
	 */

	@RequestMapping(value = "/lockIHDTicket", method = RequestMethod.POST)
	public @ResponseBody
	void lockIHDTicket(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {
		JSONObject json = new JSONObject(jsonString.replace("\\n",
				"brlinebreakbreak"));
		json = new JSONObject(json.getString("jsonString"));
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		json.put("LOCKED_BY", loginID);
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String createddate = dateFormatGmt.format(new Date());
		json.put("LOCKED_DATE", createddate);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		Map<String, Object> lockdetails = getIHDDAO.lockIHDTicket(json);

		JsonUtility.sendData(lockdetails, response);

	}
	
	@RequestMapping(value = "/getOutOfSLAReasons.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getOutOfSLAReasons(@RequestBody String functionID,
			HttpServletResponse response, HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String, Object>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> getOUTSLAReason=MasterDataImpl.getOUTSLAReason(functionID);
		map.put("getOUTSLAReason", getOUTSLAReason);
		JsonUtility.sendData(map, response);
	}

	/**
	 * To update the helpdesk ticket in edit page
	 * 
	 */
	
	@RequestMapping(value = "/closeMultipleTicket.htm", method = RequestMethod.GET)
	public @ResponseBody
	void closeMultipleTicket(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
					.getBean(HELPDESK_EDITDAO_IMPL);
			Map<String, Object> statusmessage = new HashMap<String, Object>();
			JSONObject jsonarray = new JSONObject(json);
			JSONArray ticketarray = jsonarray.getJSONArray("JSONARRAY");
			String loginID = (String) request.getSession().getAttribute(
					USER_LOGIN_ID_VARIABLE);
			String result="";
			for (int i = 0; i < ticketarray.length(); i++) {
				result=getIHDDAO.updateMultipleTickets(ticketarray.getString(i),loginID);
			}
			if(result.equalsIgnoreCase("SUCCESS")){
			statusmessage.put(STATUS_VARIABLE, "Success");
			}else{
				statusmessage.put("Error", "Error");
			}
			JsonUtility.sendData(statusmessage, response);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	@RequestMapping(value = "/slaStatus.htm", method = RequestMethod.GET)
	public @ResponseBody
	void slaStatus(@RequestParam String ticketIDs,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		List<String> slaStatus=new ArrayList<String>();
		String SLA="";
		String[] tickets=ticketIDs.split(",");
		for(int i=0;i<tickets.length;i++){
			if(!tickets[i].equalsIgnoreCase("")){
				SLA=getIHDDAO.getSLAStatusProcedureCall(tickets[i]);
				if(SLA.equalsIgnoreCase("OUT")){
					slaStatus.add(tickets[i]);
				}
			}
			
		}
		JsonUtility.sendData(slaStatus, response);
		
		
	}
	
	
	@RequestMapping(value = "/iConnectIHDTicketUpdation.htm", method = RequestMethod.POST)
	public @ResponseBody
	void updateIHDTicket(@RequestBody String jsonString,@RequestParam String approvalExceptionFlag,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		log.info("Ticket Updatation jsonString:"+jsonString);
		Map<String, String> result = new HashMap<String, String>();
		// try {
		JSONObject jsonarray = new JSONObject(jsonString.replace("\\n",
				"brlinebreakbreak"));
		jsonarray = new JSONObject(jsonarray.getString("jsonString"));
		String updatejson = jsonarray.getString("updateJson");
		log.info("The JSON Object I Got Is : " + updatejson);
		JSONObject jsonobj = new JSONObject(updatejson);
		String functionID=jsonobj.getString("FUNCTION_ID");
		
		// added for orchestration 			
		if(functionID.equalsIgnoreCase("256")&& jsonobj.getString("WORKFLOW_STATE").toString().equalsIgnoreCase("Assigned For Automation"))
			{
				if(jsonobj.getString("ASSIGNED_GROUP").toString().equalsIgnoreCase("2548"))
				{
					String orchAssigned = jsonobj.getString("ASSIGNED_TO").toString();
					if(orchAssigned.equalsIgnoreCase("") || orchAssigned == null)
					{
						jsonobj.remove("ASSIGNED_TO");
						jsonobj.put("ASSIGNED_TO", "OrchEngg1");
						
					}
					jsonarray.put("updateJson",jsonobj);
				}
			
		}
		// added for orchestration-------end
		
		String locationDetailsId = jsonobj.getString("LOC_DET_ID");
		String isOrchRequried=jsonobj.getString("IS_ORCH_REQUIRED");
		
		log.info("The Location Details id i got Is : "
				+ locationDetailsId);
		String WorkFlow=jsonobj.getString("WORKFLOW_STATE");
		String Browser=jsonobj.getString("BROWSER_DETAILS");
		String catagory = jsonobj.getString("CATEGORY_ID");
		String ticketId = jsonobj.getString("TICKET_ID");
		// String sub_category_id = jsonobj.getString("SUB_CATEGORY_ID");
		log.info("The Is's I Got Is : " + catagory + "  " + ticketId);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);
		/*
		 * if (((jsonobj.get("FUNCTION_ID").toString().trim()).toString().
		 * equalsIgnoreCase("256") &&
		 * ((sub_category_id.equalsIgnoreCase("299")))) ||
		 * ((jsonobj.get("FUNCTION_ID"
		 * ).toString().trim()).toString().equalsIgnoreCase("256") &&
		 * ((sub_category_id.equalsIgnoreCase("299"))))) {
		 */
		String sub_category_id = getIHDDAO
				.getSubCatagoryIdBasedOnTicket(ticketId);
		log.info("The Sub Catagory Id I Got Is : " + sub_category_id);
		/*
		 * log.info("The Sub Catagory Id I Got From DB = " +
		 * getIHDDAO.getAutomationStatus(sub_category_id));
		 */

		if (sub_category_id != null ? ((getIHDDAO
				.getAutomationStatus(sub_category_id).equalsIgnoreCase("Y") || (isOrchRequried.equalsIgnoreCase("false") && WorkFlow
						.equalsIgnoreCase("Assigned For Automation")) || isOrchRequried.equalsIgnoreCase("true"))
				 && (WorkFlow
				.equalsIgnoreCase("Assigned For Automation") || (WorkFlow
				.equalsIgnoreCase("Responded") && isOrchRequried.equalsIgnoreCase("true"))))
				: false) {
			log
					.info("Now Executing Orchestration Automation Code After Submitted By Help Desk.");
			
			String l_empProjectId=jsonobj.get("PROJECT_ID").toString();
			if(l_empProjectId.contains("(")){
				l_empProjectId=l_empProjectId.substring(l_empProjectId.indexOf("(")+1,l_empProjectId.indexOf(")"));
			}
			
			int l_orchOdcId=HelpDeskImpl.getOrchOdcId(l_empProjectId,Integer.parseInt(sub_category_id));
			if (sub_category_id.equalsIgnoreCase("329") ? getIHDDAO
					.getLocationBasedTicketForAutoMation(locationDetailsId) : true) {
				
				log.info("And Coming Inside After Checking ");
				
				Map<String, String> l_finalMap = new HashMap<String, String>();

				for (Map<String, Object> l_m : getIHDDAO
						.getOrchestrationGUIDMapping(ticketId, sub_category_id,l_orchOdcId)) {
					l_finalMap.put((String) l_m.get("GU_ID"),
							(String) l_m.get("ORCH_VALUE"));
					log.info("The Data Coming As Map : " + l_m.get("GU_ID") + "    " + l_m.get("ORCH_VALUE"));
				}
			/*	if (new OrchestrationSchedularForEachRequestController().run(
						l_finalMap, ticketId, WorkFlow, sub_category_id,l_orchOdcId).equalsIgnoreCase(
						"Success")) {
					String updatestatus = submit(jsonarray.toString(),approvalExceptionFlag,
							response, request);
					result.put(STATUS_VARIABLE, updatestatus);
					result.put(MESSAGE_VARIABLE, updatestatus);
					result.put(FUNCTION_ID_VARIABLE, functionID);
					result.put(WORKFLOW_STATE_VARIABLE, WorkFlow);
					result.put(BROWSER_DETAILS_VARIABLE, Browser);
				} else {*/
					String jsonString1 = "{\"TICKET_ID\":\"" + ticketId
					+ "\",\"WORKFLOW_STATE\":\"" + "Assigned For Automation"
					+ "\",\"FUNCTION\":\""
					+ "IT Infrastructure Management"
					+ "\",\"SUB_CATEGORY_ID\":\""
					+ sub_category_id
					+ "\",\"PRIORITY_ID\":\""
					+ jsonobj.getString("PRIORITY_ID")
					+ "\",\"ISORCH\":\"true"
					+ "\",\"LOCATION_ID\":\""
					+ jsonobj.getString("LOCATION_ID")
					+ "\",\"LOC_DET_ID\":\""
					+ jsonobj.getString("LOC_DET_ID")
					+ "\",\"FUNCTION_ID\":\""
					+ jsonobj.getString("LOC_DET_ID")
					+ "\",\"COMMENTS\":\"" + "Automation Failed Due To Some Problem"
					+ "\",\"ACTION\":\"Send to Helpdesk\"}";
					/*String jsonString1 = "{\"ACTION\":\"Send to Helpdesk\",\"TICKET_ID\":\""
							+ ticketId + "\"}";*/
					updateStatusForTickets(jsonString1, (String) request
							.getSession().getAttribute(USER_LOGIN_ID_VARIABLE),
							"I");
					result.put(STATUS_VARIABLE, "Error");
					result.put(MESSAGE_VARIABLE,
							"Assign For Automation Failed.");
				//}
			} else {
				result.put(STATUS_VARIABLE, "Error");
				result.put(MESSAGE_VARIABLE,
						"Ticket Belongs To ODC Location. Cannot Go For Automation.");
			}
		} else {
		String current_version_no = getIHDDAO
					.getIHDTicketVersionNo((String) jsonobj
							.get(TICKET_ID_VARIABLE));
			Map<String, Object> lockedDetails = getIHDDAO
					.getLockedTicketDetails("1",
							(String) jsonobj.get(TICKET_ID_VARIABLE));
		// Code updating CHECK_FCS field to be 0 on ticket updation.

		getIHDDAO.updateFCSCheck((String) jsonobj.get(TICKET_ID_VARIABLE));

		// Code updation Ends
		if (current_version_no.equalsIgnoreCase(jsonobj
				.getString(VERSION_NO_VARIABLE))
				&& lockedDetails.containsKey("LOCKED_BY")) {
			String updatestatus = submit(jsonarray.toString(),approvalExceptionFlag, response,
					request);
			result.put(STATUS_VARIABLE, updatestatus);
			result.put(MESSAGE_VARIABLE, updatestatus);
			result.put(FUNCTION_ID_VARIABLE, functionID);
			result.put(WORKFLOW_STATE_VARIABLE, WorkFlow);
			result.put(BROWSER_DETAILS_VARIABLE, Browser);
		} else {
			result.put(STATUS_VARIABLE, "Error");
			result.put(MESSAGE_VARIABLE,
					"Please refresh the ticket to get an updated status");
		}
		}
		JsonUtility.sendData(result, response);

	}

	/**
	 * To update the helpdesk ticket in edit page gets called from the
	 * updateIHDTicket method
	 * 
	 * @throws ConnectException
	 * @throws WebServiceIOException
	 * 
	 */

	public String submit(String jsonString,String approvalExceptionFlag, HttpServletResponse response,
			HttpServletRequest request) throws JSONException {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean(WORKFLOW_DAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		LoginDAO loginDAO = (LoginDAO) ctx.getBean("loginDAOImpl");
		
    	//GamificationWebService service = (GamificationWebService) ctx.getBean("gamificationService");

		JSONObject jsonarray = new JSONObject(jsonString);
		String updatejson = jsonarray.getString("updateJson");
		log.info("Ticket updatejson jsonString:"+updatejson);
		JSONObject jsonobj = new JSONObject(updatejson);
		log.info("Ticket updatejson jsonObj:"+jsonobj);
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		if (updatejson.contains(ADDITIONAL_INFO_VARIABLE)) {
			String addtionalInfo = converter.replaceSpecialChars(jsonobj
					.getString(ADDITIONAL_INFO_VARIABLE));
			jsonobj.remove(ADDITIONAL_INFO_VARIABLE);
			jsonobj.put(ADDITIONAL_INFO_VARIABLE, addtionalInfo);
		}
		if (updatejson.contains(RESOLUTION_COMMENTS_VARIABLE)) {
			String resolutionComments = converter.replaceSpecialChars(jsonobj
					.getString(RESOLUTION_COMMENTS_VARIABLE));
			jsonobj.remove(RESOLUTION_COMMENTS_VARIABLE);
			jsonobj.put(RESOLUTION_COMMENTS_VARIABLE, resolutionComments);
		}
		if (updatejson.contains(PRIVATE_NOTES_VARIABLE)) {
			String privateNotes = converter.replaceSpecialChars(jsonobj
					.getString(PRIVATE_NOTES_VARIABLE));
			jsonobj.remove(PRIVATE_NOTES_VARIABLE);
			jsonobj.put(PRIVATE_NOTES_VARIABLE, privateNotes);
		}
		if (updatejson.contains(ONHOLD_COMMENTS_TR_VARIABLE)) {
			String onHoldComments = converter.replaceSpecialChars(jsonobj
					.getString(ONHOLD_COMMENTS_TR_VARIABLE));
			jsonobj.remove(ONHOLD_COMMENTS_TR_VARIABLE);
			jsonobj.put(ONHOLD_COMMENTS_TR_VARIABLE, onHoldComments);
		}
		if (updatejson.contains(OUT_OF_SLA_COMMENTS_VARIABLE)) {
			String OutSlaComments = converter.replaceSpecialChars(jsonobj
					.getString(OUT_OF_SLA_COMMENTS_VARIABLE));
			jsonobj.remove(OUT_OF_SLA_COMMENTS_VARIABLE);
			jsonobj.put(OUT_OF_SLA_COMMENTS_VARIABLE, OutSlaComments);
		}
		if (updatejson.contains(REOPEN_COMMENTS_TR_VARIABLE)) {
			String reopenComments = converter.replaceSpecialChars(jsonobj
					.getString(REOPEN_COMMENTS_TR_VARIABLE));
			jsonobj.remove(REOPEN_COMMENTS_TR_VARIABLE);
			jsonobj.put(REOPEN_COMMENTS_TR_VARIABLE, reopenComments);
		}

		String auditlogjson = jsonarray.getString("auditlogJson");
		log.info("Ticket auditlogjson jsonString:"+auditlogjson);
		JSONObject auditjsonobj = new JSONObject(auditlogjson);
		if (auditlogjson.contains(ADDITIONAL_INFO_VARIABLE)) {
			String addtionalInfo = converter.replaceSpecialChars(auditjsonobj
					.getString(ADDITIONAL_INFO_VARIABLE));
			auditjsonobj.remove(ADDITIONAL_INFO_VARIABLE);
			auditjsonobj.put(ADDITIONAL_INFO_VARIABLE, addtionalInfo);
		}
		if (auditlogjson.contains(RESOLUTION_COMMENTS_VARIABLE)) {
			String resolutionComments = converter
					.replaceSpecialChars(auditjsonobj
							.getString(RESOLUTION_COMMENTS_VARIABLE));
			auditjsonobj.remove(RESOLUTION_COMMENTS_VARIABLE);
			auditjsonobj.put(RESOLUTION_COMMENTS_VARIABLE, resolutionComments);
		}
		if (auditlogjson.contains(PRIVATE_NOTES_VARIABLE)) {
			String privateNotes = converter.replaceSpecialChars(auditjsonobj
					.getString(PRIVATE_NOTES_VARIABLE));
			auditjsonobj.remove(PRIVATE_NOTES_VARIABLE);
			auditjsonobj.put(PRIVATE_NOTES_VARIABLE, privateNotes);
		}
		if (auditlogjson.contains(ONHOLD_COMMENTS_VARIABLE)) {
			String onHoldComments = converter.replaceSpecialChars(auditjsonobj
					.getString(ONHOLD_COMMENTS_VARIABLE));
			auditjsonobj.remove(ONHOLD_COMMENTS_VARIABLE);
			auditjsonobj.put(ONHOLD_COMMENTS_VARIABLE, onHoldComments);
		}
		if (auditlogjson.contains(OUT_OF_SLA_COMMENTS_VARIABLE)) {
			String OutSlaComments = converter.replaceSpecialChars(auditjsonobj
					.getString(OUT_OF_SLA_COMMENTS_VARIABLE));
			auditjsonobj.remove(OUT_OF_SLA_COMMENTS_VARIABLE);
			auditjsonobj.put(OUT_OF_SLA_COMMENTS_VARIABLE, OutSlaComments);
		}
		if (auditlogjson.contains(REOPEN_COMMENTS_TR_VARIABLE)) {
			String reopenComments = converter.replaceSpecialChars(auditjsonobj
					.getString(REOPEN_COMMENTS_VARIABLE));
			auditjsonobj.remove(REOPEN_COMMENTS_VARIABLE);
			auditjsonobj.put(REOPEN_COMMENTS_VARIABLE, reopenComments);
		}

		boolean needECTCalculation = false;
		JSONArray removingarray = jsonarray.getJSONArray("removingJson");		
		log.info("Ticket removingarray jsonString:"+removingarray);
		JSONArray list = new JSONArray();
		int foundcount = 0;
		// Modified by Sali

		boolean isFunctionChanged = true;
		boolean isSubcategoryChanged = true;
		boolean isGroupChanged = true;
		boolean isWorkflowStateChanged = true;
		for (int i = 0; i < removingarray.length(); i++) {
			if (removingarray.getString(i).equalsIgnoreCase(
					FUNCTION_ID_VARIABLE)
					|| removingarray.getString(i).equalsIgnoreCase(
							CATEGORY_ID_VARIABLE)
					|| removingarray.getString(i).equalsIgnoreCase(
							SUB_CATEGORY_ID_VARIABLE)
					|| removingarray.getString(i).equalsIgnoreCase(
							PRIORITY_ID_VARIABLE)
					|| removingarray.getString(i).equalsIgnoreCase(
							ASSIGNED_GROUP_VARIABLE)) {
				foundcount++;
			}
			if (removingarray.getString(i).equalsIgnoreCase(
					FUNCTION_ID_VARIABLE)) {
				isFunctionChanged = false;
			}
			if (removingarray.getString(i).equalsIgnoreCase(
					SUB_CATEGORY_ID_VARIABLE)) {
				isSubcategoryChanged = false;
			}
			if (removingarray.getString(i).equalsIgnoreCase(
					ASSIGNED_GROUP_VARIABLE)) {
				isGroupChanged = false;
			}
			if(removingarray.getString(i).equalsIgnoreCase(
                    WORKFLOW_STATE_VARIABLE)){
			    isWorkflowStateChanged = false;
            }
		}		
		/*********Auto Assignment***********/		
		
		String autoAssignmentFlagJson = jsonarray
				.getString(AUTO_ASSIGNMENT_JSON_VARIABLE);
		JSONObject autoAssgnObj=new JSONObject(autoAssignmentFlagJson);			
		int priorityChangeFlag = Integer.parseInt(autoAssgnObj.get(
				PRIORITY_FLAG_VARIABLE).toString());
		int statusChangeFlag = Integer.parseInt(autoAssgnObj.get(
				STATUS_FLAG_VARIABLE).toString());
		int assignedToChangeFlag = Integer.parseInt(autoAssgnObj.get(
				ASSIGNED_TO_FLAG).toString());
		int autoAssignmentFlag = Integer.parseInt(autoAssgnObj.get(
				"isAutoAssignmentReqdFlag").toString());
		String newEngineer=null;
		String assignedToEmployee=null;
		String currentStatus=null;
		String currentPriority=null;
		String newPriority=null;
		String currentEngineer=null;
		String newStatus=null;		
		String assignedToName=null;		
		String currentstate = (String) jsonobj.get(WORKFLOW_STATE_VARIABLE);
		String selectedStatus = (String) jsonobj.get(WORKFLOW_STATE_VARIABLE);
		if (currentstate.equalsIgnoreCase("Responded")
				&& jsonobj.getString("IS_ORCH_REQUIRED").equalsIgnoreCase(
						"true")) {
			currentstate=ASSIGNED_FOR_AUTOMACTION_STATE_VARIABLE;
		}
		
	
		
		if (autoAssgnObj.get(OLD_STATUS_VARIABLE).toString().trim().length() > 0
				&& jsonobj.has(WORKFLOW_STATE_VARIABLE)) {
			currentStatus=autoAssgnObj.get(OLD_STATUS_VARIABLE).toString();
		}				
		String groupID=jsonobj.get(ASSIGNED_GROUP_VARIABLE).toString();
		if (autoAssgnObj.get(OLD_PRIORITY_VARIABLE).toString().trim().length() > 0
				&& jsonobj.has(PRIORITY_ID_VARIABLE)) {
			currentPriority = autoAssgnObj.get(OLD_PRIORITY_VARIABLE)
					.toString();
		}		
		if(jsonobj.has(PRIORITY_ID_VARIABLE)){
			newPriority=jsonobj.get(PRIORITY_ID_VARIABLE).toString();
		}		
		if (autoAssgnObj.get(OLD_ASSIGNED_TO_VARIABLE).toString().trim()
				.length() > 0) {
			currentEngineer = autoAssgnObj.get(OLD_ASSIGNED_TO_VARIABLE)
					.toString();
		}
		if(!currentStatus.equalsIgnoreCase(HELPDESK_QUEUE_STATE_VARIABLE)){
			if (autoAssgnObj.get(NEW_ASSIGNED_TO_VARIABLE).toString().trim()
					.length() > 0
					&& jsonobj.has(ASSIGNED_TO_VARIABLE)) {
				newEngineer = autoAssgnObj.get(NEW_ASSIGNED_TO_VARIABLE)
						.toString();
				
			}	
		}
		
		if(jsonobj.has(WORKFLOW_STATE_VARIABLE)){
			newStatus=jsonobj.get(WORKFLOW_STATE_VARIABLE).toString();
		}
		//check if the AA flag for a group is on /off	
		if(autoAssignmentFlag==1){
		if(currentStatus.equalsIgnoreCase(HELPDESK_QUEUE_STATE_VARIABLE)){
				if(isGroupChanged==true){
				
					List<Map<String, Object>> scoreList = getIHDDAO
							.getLoggedInGroupMembersScore(groupID);
				
				List<Map<String, Object>> scoreListExecutives=new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> scoreListTLs=new ArrayList<Map<String,Object>>();
				
				if(scoreList.size()>0){
					for(Map<String,Object> scoreMapForEngineerRole:scoreList){
						
							if (scoreMapForEngineerRole.get(ROLE_IDE_VARIABLE)
									.toString().equals(ROLE_IDE_EXECUTIVE)) {
								scoreListExecutives
										.add(scoreMapForEngineerRole);
							} else if (scoreMapForEngineerRole
									.get(ROLE_IDE_VARIABLE).toString()
									.equals(ROLE_IDE_TL)) {
							scoreListTLs.add(scoreMapForEngineerRole);
						}
					}
				}
				
					Map<String, Object> minimumScoreMap = getIHDDAO
							.getMinimumScore(groupID);
			
					/*
					 * Find Assigned to employee based on Score List of Logged
					 * In Engineers
					 */
				if(scoreListExecutives.size()>0){
						assignedToEmployee = HELPDESK_AutoAssignment
								.getAssignedToEmployeeForTicket_V1(
										scoreListExecutives, groupID,
										minimumScoreMap);
					if(newStatus.equals(OUT_OF_OPERATION_STATUS_VARIABLE)){
						newEngineer=null;	
					}else{
						newEngineer=assignedToEmployee;	
							assignedToName = loginDAO
									.getUserDetails(newEngineer).get(0)
									.getUserName()
									+ "(" + newEngineer + ")";
					}
					
					assignedToChangeFlag=1;
					if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
						jsonobj.remove(ASSIGNED_TO_VARIABLE);
					}
						jsonobj.put(ASSIGNED_TO_VARIABLE, newEngineer);
						auditjsonobj.put(ASSIGNED_TO_VARIABLE, assignedToName);
					
					} else /*
							 * If no Executives are present :Find Assigned to
							 * employee based on Score List of Logged In TL of
							 * the group
							 */
				if(scoreListTLs.size()>0){	
						assignedToEmployee = HELPDESK_AutoAssignment
								.getAssignedToEmployeeForTicket_V1(
										scoreListTLs, groupID, minimumScoreMap);
					if(newStatus.equals(OUT_OF_OPERATION_STATUS_VARIABLE)){
						newEngineer=null;	
					}else{
						newEngineer=assignedToEmployee;	
							assignedToName = loginDAO
									.getUserDetails(newEngineer).get(0)
									.getUserName()
									+ "(" + newEngineer + ")";
					}
					
					assignedToChangeFlag=1;
					if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
						jsonobj.remove(ASSIGNED_TO_VARIABLE);
					}
						jsonobj.put(ASSIGNED_TO_VARIABLE, newEngineer);
						auditjsonobj.put(ASSIGNED_TO_VARIABLE, assignedToName);
					} else/*
						 * If no Executives/TLs are present :Find Assigned to
						 * employee based on Score List of Group Manager of the
						 * group irrespective of his login status
						 */
				{
						// Check work timing and Operation hr.If Operation hr
						// status change.if not assign to manager.
						String functionid = (String) jsonobj
								.get(FUNCTION_ID_VARIABLE);
					long locationIdForSelGroup = 0;
					List<Map<String, Object>> groupList = MasterDataImpl
							.getIHDGroupMaster();
					String groupManager=null;
					for (Map<String, Object> stringObj : groupList) {
						if (stringObj.get(GROUP_ID_VARIABLE) != null) {
							if (stringObj.get(GROUP_ID_VARIABLE).toString()
									.equalsIgnoreCase(groupID)) {
									locationIdForSelGroup = Long
											.parseLong(stringObj.get(
													LOCATION_ID_VARIABLE)
													.toString());
									groupManager = stringObj.get(
											MANAGER_VARIABLE).toString();
							}
						}
					}
						long opr_id = MasterDataImpl.getGroupOPRID(
								Long.parseLong(groupID),
								Long.parseLong(functionid),
								locationIdForSelGroup);
					List<Map<String, Object>> slaList = MasterDataImpl
							.getSLAOperatingTimeList(opr_id);					
					List<Map<String, Object>> slaExclusionDateList = MasterDataImpl
								.getSLAExclusionDateList(opr_id,
										locationIdForSelGroup);
					String holiday = "";
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					boolean holiday_check = false;
					for (Map<String, Object> stringObj : slaExclusionDateList) {
						holiday = stringObj.get("DATE").toString();
						if (sd.format(new Date()).equalsIgnoreCase(holiday)) {
							holiday_check = true;
						}
					}
					if (!holiday_check) {
						if (isServiceWindowAvailable(slaList)) {						
							if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
								jsonobj.remove(ASSIGNED_TO_VARIABLE);							
							}			
								/*
								 * Check for if Service Window is available and
								 * user has selected Out Of Opertaion as status
								 * new Engineer will be null
								 */
								if (newStatus
										.equals(OUT_OF_OPERATION_STATUS_VARIABLE)) {
									newEngineer=null;
								}else{
									newEngineer=groupManager;
									try{
									assignedToName=loginDAO.getUserDetails(newEngineer).get(0).getUserName()+"("+newEngineer+")";
									}catch(IndexOutOfBoundsException e){
										//Exception is thrown in the HandleExceptions
									}
								}
								jsonobj.put(ASSIGNED_TO_VARIABLE, newEngineer);
								auditjsonobj.put(ASSIGNED_TO_VARIABLE, assignedToName);						
								assignedToChangeFlag=1;
								
						} else {
							
							if(newStatus.equals(OUT_OF_OPERATION_STATUS_VARIABLE)){							
							newStatus=OUT_OF_OPERATION_STATUS_VARIABLE;
							currentstate=newStatus;
							if(jsonobj.has(WORKFLOW_STATE_VARIABLE)){
								jsonobj.remove(WORKFLOW_STATE_VARIABLE);							
							}
								jsonobj.put(WORKFLOW_STATE_VARIABLE, newStatus);
								auditjsonobj.put(WORKFLOW_STATE_VARIABLE, newStatus);		
								statusChangeFlag=1;		
								for(int i=0;i<removingarray.length();i++){
									if(!removingarray.get(i).toString().contains(WORKFLOW_STATE_VARIABLE)){
										list.put(removingarray.get(i).toString());
									}
								}
								if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
									jsonobj.remove(ASSIGNED_TO_VARIABLE);							
								}
									jsonobj.put(ASSIGNED_TO_VARIABLE, "");
									auditjsonobj.put(ASSIGNED_TO_VARIABLE, "");								
									newEngineer=null;
									assignedToChangeFlag=1;
								removingarray=new JSONArray();
								for(int i=0;i<list.length();i++){
									removingarray.put(list.get(i).toString());
								}
								
		
						}else{
							if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
								jsonobj.remove(ASSIGNED_TO_VARIABLE);							
							}
							if(assignedToEmployee==null){
								newEngineer=groupManager;								
							}else{
								newEngineer=assignedToEmployee;
							}
							assignedToName=loginDAO.getUserDetails(newEngineer).get(0).getUserName()+"("+newEngineer+")";
								jsonobj.put(ASSIGNED_TO_VARIABLE, newEngineer);
								auditjsonobj.put(ASSIGNED_TO_VARIABLE, assignedToName);							
								assignedToChangeFlag=1;
						}
						}
					} else {
						if(newStatus.equalsIgnoreCase(OUT_OF_OPERATION_STATUS_VARIABLE)){						
						newStatus=OUT_OF_OPERATION_STATUS_VARIABLE;
						currentstate=newStatus;
						if(jsonobj.has(WORKFLOW_STATE_VARIABLE)){
							jsonobj.remove(WORKFLOW_STATE_VARIABLE);							
						}
							jsonobj.put(WORKFLOW_STATE_VARIABLE, newStatus);
							auditjsonobj.put(WORKFLOW_STATE_VARIABLE, newStatus);								
							statusChangeFlag=1;
							for(int i=0;i<removingarray.length();i++){
								if(!removingarray.get(i).toString().contains(WORKFLOW_STATE_VARIABLE)){
									list.put(removingarray.get(i).toString());
								}
							}
							if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
								jsonobj.remove(ASSIGNED_TO_VARIABLE);							
							}
								jsonobj.put(ASSIGNED_TO_VARIABLE, "");
								auditjsonobj.put(ASSIGNED_TO_VARIABLE, "");								
								newEngineer=null;
								assignedToChangeFlag=1;
							removingarray=new JSONArray();
							for(int i=0;i<list.length();i++){
								removingarray.put(list.get(i).toString());
							}
					}else{
						if(jsonobj.has(ASSIGNED_TO_VARIABLE)){
							jsonobj.remove(ASSIGNED_TO_VARIABLE);							
						}
						if(assignedToEmployee==null){
							newEngineer=groupManager;
						}else{
							newEngineer=assignedToEmployee;
						}
						assignedToName=loginDAO.getUserDetails(newEngineer).get(0).getUserName()+"("+newEngineer+")";
						jsonobj.put(ASSIGNED_TO_VARIABLE, newEngineer);
						auditjsonobj.put(ASSIGNED_TO_VARIABLE, assignedToName);							
						assignedToChangeFlag=1;
					}
					}

				}//END : Check work timing and Operation hr.If Operation hour status change.if not assign to manager.
				}
		}	//END: End of check if the AA flag for a group is on /off	
		}else{
			if(autoAssgnObj.get(NEW_ASSIGNED_TO_VARIABLE).toString().trim().length()>0 && jsonobj.has(ASSIGNED_TO_VARIABLE)){
				newEngineer=autoAssgnObj.get(NEW_ASSIGNED_TO_VARIABLE).toString();
				
			}
		}
		String modifiedBy=loginID;
		String ticketID=jsonobj.getString(TICKET_ID_VARIABLE);		
		/*********End Auto Assignment******************************************************************************/
		
		if (foundcount != 5) {
			needECTCalculation = true;
		}

		if (isSubcategoryChanged && jsonobj.has(SUB_CATEGORY_ID_VARIABLE)
				&& !jsonobj.getString("SUB_CATEGORY_ID").equalsIgnoreCase("")) {

			boolean isChangeRequest = false;
			List<Map<String, Object>> categories = MasterDataImpl
					.getCategoriesById(CATEGORY_ID_VARIABLE, Integer
							.parseInt(jsonobj.get(SUB_CATEGORY_ID_VARIABLE)
									.toString()));
			for (Map<String, Object> category : categories) {
				if(!approvalExceptionFlag.equalsIgnoreCase("1")){
				isChangeRequest = (Boolean) category
						.get(IS_CHANGE_REQUEST_VARIABLE);
				}
			}
			if (isChangeRequest) {
				String ManagerIdForApproval = getManagerIdForApproval(jsonobj);
				if (jsonobj.has(MANAGER_ID_VARIABLE)) {
					jsonobj.remove(MANAGER_ID_VARIABLE);
				}
				jsonobj.put(MANAGER_ID_VARIABLE, ManagerIdForApproval);
				auditjsonobj.put(MANAGER_ID_VARIABLE, ManagerIdForApproval);
			}
		}

		
		String categoryID = "";
		String functionId = (String) jsonobj.get(FUNCTION_ID_VARIABLE);// Modified
		// by
		// sali on Sep14
		if (jsonobj.has(FUNCTION_ID_VARIABLE)
				|| jsonobj.has(CATEGORY_ID_VARIABLE)
				|| jsonobj.has(SUB_CATEGORY_ID_VARIABLE)) {

			if (jsonobj.has(SUB_CATEGORY_ID_VARIABLE)
					&& !jsonobj.get(SUB_CATEGORY_ID_VARIABLE).toString()
							.equalsIgnoreCase("")) {
				categoryID = (String) jsonobj.get(SUB_CATEGORY_ID_VARIABLE);
			} else if (jsonobj.has(CATEGORY_ID_VARIABLE)
					&& !jsonobj.get(CATEGORY_ID_VARIABLE).toString()
							.equalsIgnoreCase("")) {
				categoryID = (String) jsonobj.get(CATEGORY_ID_VARIABLE);
			} else if (jsonobj.has(FUNCTION_ID_VARIABLE)
					&& !jsonobj.get(FUNCTION_ID_VARIABLE).toString()
							.equalsIgnoreCase("")) {
				categoryID = (String) jsonobj.get(FUNCTION_ID_VARIABLE);
			}
			if (!categoryID.equalsIgnoreCase("")) {
				String ECT = null;
				if (needECTCalculation && !approvalExceptionFlag.equalsIgnoreCase("1")) {

					long opr_id = 0l;
					long group_base_locationId = 0l;
					if (!jsonobj.get(ASSIGNED_GROUP_VARIABLE).toString()
							.equalsIgnoreCase("")) {
						// Location needs to be changed
						// Instead of 'FILTER_GROUP_LOCATION',take group base
						// location from IC_IHD_GROUP_MASTER

						List<Map<String, Object>> groupList = MasterDataImpl
								.getIHDGroupMaster();

						for (Map<String, Object> stringObj : groupList) {
							if (stringObj.get(GROUP_ID_VARIABLE) != null) {
								if (stringObj
										.get(GROUP_ID_VARIABLE)
										.toString()
										.equalsIgnoreCase(
												jsonobj
														.get(
																ASSIGNED_GROUP_VARIABLE)
														.toString())) {
									group_base_locationId = Long
											.parseLong(stringObj.get(
													LOCATION_ID_VARIABLE)
													.toString());
								}
							}
						}

						//
						opr_id = MasterDataImpl.getGroupOPRID(Long
								.valueOf((String) jsonobj
										.get(ASSIGNED_GROUP_VARIABLE)), Long
								.valueOf((String) jsonobj
										.get(FUNCTION_ID_VARIABLE)),
								group_base_locationId);

					} else {
						opr_id = MasterDataImpl.getServiceOPRID(Long
								.valueOf((String) jsonobj
										.get(LOCATION_ID_VARIABLE)), Long
								.valueOf((String) jsonobj.get("LOC_DET_ID")),
								Long.valueOf((String) jsonobj
										.get(FUNCTION_ID_VARIABLE)));
					}

					if (opr_id != 0) {
						DateFormat dateFormatGmt = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
						String modified_date = dateFormatGmt.format(new Date());
						if (jsonobj.get(IS_CHANGE_REQUEST_VARIABLE).toString()
								.equalsIgnoreCase("1"))// Modified by Sali on
						// Sep 13th
						{

							if (isFunctionChanged) {
								ECT = HELPDESK_ECTPopulator.getECT(
										currentstate,
										Integer.parseInt((String) jsonobj
												.get(PRIORITY_ID_VARIABLE)),
										modified_date,
										Long.valueOf(categoryID), "I",
										group_base_locationId, opr_id);
							} else {
								if (jsonobj.get(CHANGED_DATE_STORAGE_VARIABLE) != null
										&& !jsonobj.get(
												CHANGED_DATE_STORAGE_VARIABLE)
												.toString()
												.equalsIgnoreCase("")) {

									ECT = HELPDESK_ECTPopulator
											.getECT(
													currentstate,
													Integer
															.parseInt((String) jsonobj
																	.get(PRIORITY_ID_VARIABLE)),
													(String) jsonobj
															.get(CHANGED_DATE_STORAGE_VARIABLE),
													Long.valueOf(categoryID),
													"I", group_base_locationId,
													opr_id);
								} else {
									ECT = HELPDESK_ECTPopulator
											.getECT(
													currentstate,
													Integer
															.parseInt((String) jsonobj
																	.get(PRIORITY_ID_VARIABLE)),
													modified_date,
													Long.valueOf(categoryID),
													"I", group_base_locationId,
													opr_id);
								}
							}

						} else {
							if (isFunctionChanged) {
								ECT = HELPDESK_ECTPopulator.getECT(
										currentstate,
										Integer.parseInt((String) jsonobj
												.get(PRIORITY_ID_VARIABLE)),
										modified_date,
										Long.valueOf(categoryID), "I",
										group_base_locationId, opr_id);
							} else {
								ECT = HELPDESK_ECTPopulator
										.getECT(
												currentstate,
												Integer
														.parseInt((String) jsonobj
																.get(PRIORITY_ID_VARIABLE)),
												(String) jsonobj
														.get(CREATED_DATE_STORAGE_VARIABLE),
												Long.valueOf(categoryID), "I",
												group_base_locationId, opr_id);
							}

						}
						// END :Modified by Sali on Sep 13th
					}
					jsonobj.put(ECT_VARIABLE, ECT);
					auditjsonobj.put(ECT_VARIABLE, ECT + " (GMT)");
				}
			}
		}

		//L2 1184 Start
		if (jsonobj.has(WORKFLOW_STATE_VARIABLE)) {
			String currStatusName=jsonobj.get(WORKFLOW_STATE_VARIABLE).toString();
			if (currStatusName.equalsIgnoreCase("Responded")
					&& jsonobj.getString("IS_ORCH_REQUIRED").equalsIgnoreCase(
							"true")) {
				currStatusName=ASSIGNED_FOR_AUTOMACTION_STATE_VARIABLE;
				if(auditjsonobj.has(WORKFLOW_STATE_VARIABLE)){
					auditjsonobj.remove(WORKFLOW_STATE_VARIABLE);
					auditjsonobj.put(WORKFLOW_STATE_VARIABLE, currStatusName);	
				}
			}
			
			if (currentstate.equalsIgnoreCase("Assigned For Automation")
					&& jsonobj.getString("IS_ORCH_REQUIRED").equalsIgnoreCase(
							"false")) {
				if(auditjsonobj.has("IS_ORCH_REQUIRED")){
					auditjsonobj.remove("IS_ORCH_REQUIRED");
					auditjsonobj.put("IS_ORCH_REQUIRED", true);	
				}else{
					auditjsonobj.put("IS_ORCH_REQUIRED", true);	
				}
				
				if(jsonobj.has("IS_ORCH_REQUIRED")){
					jsonobj.remove("IS_ORCH_REQUIRED");
					jsonobj.put("IS_ORCH_REQUIRED", true);	
				}else{
					jsonobj.put("IS_ORCH_REQUIRED", true);	
				}
			}
			int workflowid = Integer.parseInt(workflowimpl.getStateId(
					currStatusName, workflowimpl
							.getWorkflowID((String) auditjsonobj.get(
									FUNCTION_ID_VARIABLE).toString())));
			jsonobj.remove(WORKFLOW_STATE_VARIABLE);
			jsonobj.put(WORKFLOW_STATE_VARIABLE, workflowid);
			//L2 1184 End
		}

		jsonobj.remove(CREATED_DATE_STORAGE_VARIABLE);
		auditjsonobj.remove(CREATED_DATE_STORAGE_VARIABLE);

		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createddate = dateFormatGmt.format(new Date());

		if (jsonobj.has(VERSION_NO_VARIABLE)) {
			int version_no = Integer.parseInt(jsonobj.get(VERSION_NO_VARIABLE)
					.toString());
			version_no = version_no + 1;
			jsonobj.remove(VERSION_NO_VARIABLE);
			jsonobj.put(VERSION_NO_VARIABLE, String.valueOf(version_no));
			auditjsonobj.remove(VERSION_NO_VARIABLE);
			auditjsonobj.put(VERSION_NO_VARIABLE, String.valueOf(version_no));
		}

		if (currentstate.equalsIgnoreCase("Resolved/Closed") || currentstate.equalsIgnoreCase("Withdraw")) {
			jsonobj.put("CLOSED_DATE", createddate);
			auditjsonobj.put("CLOSED_DATE", createddate);
		}
		Map<String, Object> emergencyDetails =new HashMap<String, Object>();
		emergencyDetails.put("functionId", functionId);
		emergencyDetails.put("createdby", loginID);
		emergencyDetails.put("createddate", createddate);
		if (jsonobj.get(FUNCTION_ID_VARIABLE)
				.toString().equalsIgnoreCase("1")){
			if(jsonobj.has(IS_EMERGENCY_TICKET_VARIABLE)) {
				emergencyDetails.put("FIELD_ID", "102");
				emergencyDetails.put("FIELD_VALUE", (String) jsonobj.get(IS_EMERGENCY_TICKET_VARIABLE));
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(IS_EMERGENCY_TICKET_VARIABLE);
			}
			if(jsonobj.has(EMERGENCY_TYPE_VARIABLE)) {
				emergencyDetails.put("FIELD_ID", "103");
				emergencyDetails.put("FIELD_VALUE", (String) jsonobj.get(EMERGENCY_TYPE_VARIABLE));
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(EMERGENCY_TYPE_VARIABLE);
			}
			if(jsonobj.has(LEVEL2_ASSIGNED_ENGINEER_VARIABLE)) {
				emergencyDetails.put("FIELD_ID", "105");
				emergencyDetails.put("FIELD_VALUE", (String) jsonobj.get(LEVEL2_ASSIGNED_ENGINEER_VARIABLE));
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(LEVEL2_ASSIGNED_ENGINEER_VARIABLE);
			}
			if(jsonobj.has(SUB_STATUS_VARIABLE)) {
				emergencyDetails.put("FIELD_ID", "106");
				emergencyDetails.put("FIELD_VALUE", (String) jsonobj.get(SUB_STATUS_VARIABLE));
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(SUB_STATUS_VARIABLE);
			}
			if(jsonobj.has(DEPLOYMENT_INSTRUCTION)) {
				String deploy_instruction = converter.replaceSpecialChars(jsonobj
						.getString(DEPLOYMENT_INSTRUCTION));
				emergencyDetails.put("FIELD_ID", "107");
				emergencyDetails.put("FIELD_VALUE", deploy_instruction);
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(DEPLOYMENT_INSTRUCTION);
			}
			if(jsonobj.has(REASON_SEND_BACK)) {
				String reason_sendbck = converter.replaceSpecialChars(jsonobj
						.getString(REASON_SEND_BACK));
				emergencyDetails.put("FIELD_ID", "108");
				emergencyDetails.put("FIELD_VALUE", reason_sendbck);
				getIHDDAO.insertorUpdateFunctionSpecificDetails(emergencyDetails,(String) jsonobj
						.get(TICKET_ID_VARIABLE));
				jsonobj.remove(REASON_SEND_BACK);
			}
		}
		if(jsonobj.has(LEVEL_3_PROJECT_ID)){
			jsonobj.remove(LEVEL_3_PROJECT_ID);
		}
		if(jsonobj.has(LEVEL_2_PROJECT_ID)){
			jsonobj.remove(LEVEL_2_PROJECT_ID);
		}
		//L2 1184 Start
		if (jsonobj.has("IS_ORCH_REQUIRED")
				&& !jsonobj.get("IS_AUTOMATION_REQUIRED").toString()
						.equalsIgnoreCase("Y") ) {
			jsonobj.remove("IS_ORCH_REQUIRED");
		}
		if (jsonobj.has("IS_ORCH_REQUIRED")
				&& jsonobj.get("IS_AUTOMATION_REQUIRED").toString()
						.equalsIgnoreCase("Y")
				&& currentstate.equalsIgnoreCase("Assigned For Automation")
				&& !jsonobj.get("IS_ORCH_REQUIRED").toString()
						.equalsIgnoreCase("true")) {
			jsonobj.remove("IS_ORCH_REQUIRED");
		} else if (jsonobj.has("IS_ORCH_REQUIRED")
				&& jsonobj.get("IS_AUTOMATION_REQUIRED").toString()
						.equalsIgnoreCase("Y")
				&& jsonobj.get("IS_ORCH_REQUIRED").toString()
						.equalsIgnoreCase("false")
				&& !currentstate.equalsIgnoreCase("Assigned For Automation")) {
			jsonobj.remove("IS_ORCH_REQUIRED");
		} else if (jsonobj.has("IS_ORCH_REQUIRED")
				&& jsonobj.get("IS_AUTOMATION_REQUIRED").toString()
						.equalsIgnoreCase("Y")
				&& currentstate.equalsIgnoreCase("Assigned For Automation")
				&& jsonobj.get("IS_ORCH_REQUIRED").toString()
						.equalsIgnoreCase("true")
				&& selectedStatus.equalsIgnoreCase("Responded")) {
			jsonobj.remove("IS_ORCH_REQUIRED");
		}

		if(jsonobj.has("IS_AUTOMATION_REQUIRED")){
			jsonobj.remove("IS_AUTOMATION_REQUIRED");
		}
		//L2 1184 End
		
		/*End Emergency L1*/
		JSONObject JsonArray = new JSONObject();
		JsonArray.put("updatejson", jsonobj);
		JsonArray.put("auditjson", auditjsonobj);
		JsonArray.put("removingJson", /*jsonarray.getJSONArray("removingJson")*/removingarray);

		log.info("Ticket updatejson jsonString after :"+jsonobj);
		log.info("Ticket auditLogjson jsonString after:"+auditjsonobj);
		log.info("Ticket removingarray jsonString after:"+removingarray);
		
		Map<String, Object> daodetails = returnUpdateQuery(JsonArray, loginID);
		log.info("daodetails Returned:"+daodetails);
		daodetails.put(TICKET_ID_VARIABLE, (String) jsonobj
				.get(TICKET_ID_VARIABLE));
		daodetails.put("ECTCategoryid", categoryID);
		daodetails.put("functionId", functionId);// Modified by sali on Sep14
		daodetails.put(PRIORITY_ID_VARIABLE, (String) jsonobj
				.get(PRIORITY_ID_VARIABLE));
		
		//Added by Sali
		if(isGroupChanged)
		{
			
			if(!autoAssgnObj.get("oldAssignedGroup").toString()
					.equalsIgnoreCase(""))
			{
				daodetails.put(ASSIGNED_GROUP_VARIABLE, autoAssgnObj.get("oldAssignedGroup").toString());
			}
			else
			{
				daodetails.put(ASSIGNED_GROUP_VARIABLE, jsonobj.get(
						ASSIGNED_GROUP_VARIABLE).toString());
			}
			
			
		}
		else
		{
			if (jsonobj.get(ASSIGNED_GROUP_VARIABLE).toString()
					.equalsIgnoreCase("")) {
				daodetails.put(ASSIGNED_GROUP_VARIABLE, "0");
			} else {
				daodetails.put(ASSIGNED_GROUP_VARIABLE, jsonobj.get(
						ASSIGNED_GROUP_VARIABLE).toString());
			}
		}
		daodetails.put(WORKFLOW_STATE_VARIABLE, currentstate);
		daodetails.put("createdby", loginID);

		daodetails.put("createddate", createddate);
		daodetails.put("osdetails", jsonobj.get("OS_DETAILS").toString());
		daodetails.put("browserdetails", jsonobj.get("BROWSER_DETAILS")
				.toString());
		/*
		 * Changed By :Nagamanikanta (714599) TO store the function speific
		 * fields like department id, odcid
		 */
		String functionName = (String) auditjsonobj.get(FUNCTION_ID_VARIABLE)
				.toString();
		
		log.info("daodetails updateHelpDeskRequest:"+daodetails);
		String result=getIHDDAO.updateHelpDeskRequest(daodetails);
			// Added for Orchestration

		/***************************************AUTO ASSIGNMENT*******************************************************************************/
		
		/*Score Calculation should happen only for IS and IT Functions*/
		ResourceBundle bundle=ResourceBundle.getBundle(PROPERTY_FILE_NAME);
		String functionStr=bundle.getString(VALID_FUNCTIONS_FOR_AA_PROPERTY);
		String[] functionList=functionStr.split(",");
		boolean isFunction=false;
		for(int i=0;i<functionList.length;i++){
			if(functionName.equalsIgnoreCase(functionList[i])){
				/*Set boolean true if ticket function is IS or IT*/
				isFunction=true;
				
			}
		}
		/*Score Calculation should happen only for IS and IT Functions*/
		if(result.equalsIgnoreCase(SUCCESS_VARIABLE) && isFunction==true){		
			/*Refrain from calling the procedure if all the flag changes are zero i.e., if no change has happened to status,priority and assigned to*/
			if(priorityChangeFlag==1 || statusChangeFlag==1 || assignedToChangeFlag==1){
			getIHDDAO.calculateScoreOnTicketUpdation(priorityChangeFlag, statusChangeFlag, assignedToChangeFlag, currentPriority, newPriority, 
														currentEngineer, newEngineer, currentStatus, newStatus, modifiedBy, ticketID);
			}			
		}
		/***************************************END AUTO ASSIGNMENT*******************************************************************************/
		String newState=auditjsonobj.getString("WORKFLOW_STATE");
		String oldState=autoAssgnObj.getString("oldStatus");
		String oldassignedTo=autoAssgnObj.getString("oldAssignedTo");
		String newassignedTo=autoAssgnObj.getString("newAssignedTo");
		if(oldState.equalsIgnoreCase(newState))
        {
              if(!oldassignedTo.equalsIgnoreCase(newassignedTo) && !newassignedTo.equalsIgnoreCase(""))
              {
                    
                    iConnectMailSender mail=(iConnectMailSender) ctx.getBean("iConnectMailSender");
                    mail.SameStatusMailSender(ticketID,request);
                    
              }
        }




		return result;
	}

	/**
	 * Will generate the dynamic query and send back to submit method to update
	 * the ticket
	 * 
	 */

	public Map<String, Object> returnUpdateQuery(JSONObject jsonString,
			String loginID) throws JSONException {
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		String[] fieldsArray = new String[] { ASSIGNED_GROUP_VARIABLE,
				CATEGORY_ID_VARIABLE, SUB_CATEGORY_ID_VARIABLE };
		String updatejson = jsonString.getString("updatejson");
		JSONObject jsonobj = new JSONObject(updatejson);
		String auditlogjson = jsonString.getString("auditjson");
		JSONObject auditjsonobj = new JSONObject(auditlogjson);

		JSONArray removingarray = jsonString.getJSONArray("removingJson");
		for (int i = 0; i < removingarray.length(); i++) {
			if (jsonobj.has(removingarray.getString(i))) {
				jsonobj.remove(removingarray.getString(i));
			}
			if (auditjsonobj.has(removingarray.getString(i))) {
				auditjsonobj.remove(removingarray.getString(i));
			}
		}

		Map<String, Object> daodetails = new HashMap<String, Object>();
		Map<String, Object> auditLogDetails = new HashMap<String, Object>();
		Object[] GenArgs = new Object[jsonobj.length() + 2];
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE IC_IHD_TICKET_DETAILS SET ");
		Iterator iterator = jsonobj.keys();
		int i = 0;
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (!key.equalsIgnoreCase(TICKET_ID_VARIABLE)) {
				sb.append(key);
				sb.append("=?,");
				if (Arrays.asList(fieldsArray).contains(key)
						&& jsonobj.get(key).toString().equalsIgnoreCase("")) {
					GenArgs[i] = null;
				} else {
					GenArgs[i] = jsonobj.get(key);
				}
				i++;
				auditLogDetails.put(key, auditjsonobj.get(key));
			}
		}
		sb.append("MODIFIED_BY=?,MODIFIED_DATE=?");
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createddate = dateFormatGmt.format(new Date());
		if(jsonobj.has("ASSIGNED_TO")){			
			sb.append(",ASSIGNED_TO_DATE=");
			sb.append("'");
			sb.append(createddate);
			sb.append("'");
		}
		GenArgs[i] = loginID;
		i++;
		
		GenArgs[i] = createddate;
		i++;
		GenArgs[i] = jsonobj.get(TICKET_ID_VARIABLE);
		daodetails.put("argvalues", GenArgs);
		daodetails.put("query", sb.toString() + " WHERE TICKET_ID=?");
		if (auditLogDetails.containsKey("FILTER_GROUP_LOCATION")) {
			auditLogDetails.remove("FILTER_GROUP_LOCATION");
		}
		//Added for Emergency L1
		/*if(auditjsonobj.has(IS_EMERGENCY_TICKET_VARIABLE))
			auditLogDetails.put(IS_EMERGENCY_TICKET_VARIABLE, auditjsonobj.get(IS_EMERGENCY_TICKET_VARIABLE));*/
		if(auditjsonobj.has(EMERGENCY_TYPE_VARIABLE))
			auditLogDetails.put(EMERGENCY_TYPE_VARIABLE, auditjsonobj.get(EMERGENCY_TYPE_VARIABLE));
		if(auditjsonobj.has(LEVEL2_ASSIGNED_ENGINEER_VARIABLE))
			auditLogDetails.put(LEVEL2_ASSIGNED_ENGINEER_VARIABLE, auditjsonobj.get(LEVEL2_ASSIGNED_ENGINEER_VARIABLE));
		if(auditjsonobj.has(SUB_STATUS_VARIABLE))
			auditLogDetails.put(SUB_STATUS_VARIABLE, auditjsonobj.get(SUB_STATUS_VARIABLE));
		if(auditjsonobj.has(DEPLOYMENT_INSTRUCTION)){
			String deploy_instruction = converter.replaceSpecialChars(auditjsonobj
					.getString(DEPLOYMENT_INSTRUCTION));
			auditLogDetails.put(DEPLOYMENT_INSTRUCTION,deploy_instruction);
		}
		if(auditjsonobj.has(REASON_SEND_BACK)){
			String reason_sendbck = converter.replaceSpecialChars(auditjsonobj
					.getString(REASON_SEND_BACK));
			auditLogDetails.put(REASON_SEND_BACK, reason_sendbck);
		}
		/***********Emergency L1 ending here*************/ 
		daodetails.put("auditlogdetails", auditLogDetails);
		
		//Added for Orchestration
	/*	String reopenQuery = "UPDATE IC_IHD_TICKET_DETAILS SET SLA_STATUS='NA',VERSION_NO="+jsonobj.get(VERSION_NO_VARIABLE)+",WORKFLOW_STATE= '16' ,ASSIGNED_TO= NULL, "
			+ " ASSIGNED_GROUP = NULL , MODIFIED_BY= '"+loginID+"',MODIFIED_DATE='"+createddate+"' WHERE TICKET_ID='"+jsonobj.get(TICKET_ID_VARIABLE)+"'";
		daodetails.put("reopenQuery", reopenQuery);*/
		daodetails.put("VERSION_NO", jsonobj.get(VERSION_NO_VARIABLE));
		// End Orchestration
		return daodetails;
	}

	/**
	 * To get the all approvers details who approved this ticket
	 * 
	 */

	@RequestMapping(value = "/hdAppRejListforTicket.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdAppRejListforTicket(String ticketNo, HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		List<HELPDESK_Approval> apprlist = getIHDDAO
				.getApprRejectListforTicket(ticketNo);

		JsonUtility.sendData(apprlist, response);

	}

	/**
	 * To update ticket as approved or rejected
	 * 
	 */

	@RequestMapping(value = "/updateApproveRejectForTicket.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getApprovalRejectedStatus(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		String dataMessage = updateStatusForTickets(json, loginID, userObj
				.getOrganization());
		JsonUtility.sendData(dataMessage, response);

	}

	/**
	 * To approve the ticket
	 * 
	 */

	@RequestMapping(value = "/approveTickets.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getApprovalStatusCheck(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);

		JSONObject jsonarray = new JSONObject(json);
		JSONArray ticketarray = jsonarray.getJSONArray("JSONARRAY");
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		String dataMessage = null;
		for (int i = 0; i < ticketarray.length(); i++) {
			dataMessage = updateStatusForTickets(ticketarray.getString(i),
					loginID, userObj.getOrganization());
		}
		JsonUtility.sendData(dataMessage, response);

	}

	/**
	 * To update the next available state to the passed ticket
	 * 
	 */

	public String updateStatusForTickets(String json, String loginID, String org)
			throws JSONException {
		String updateMessage = "";

		JSONObject jsonobj = new JSONObject(json.replace("\n",
				"brlinebreakbreak"));
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean(WORKFLOW_DAO_IMPL);

		String workflowid = workflowimpl.getWorkflowID(jsonobj.get("FUNCTION")
				.toString());

		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String createddate = dateFormatGmt.format(new Date());
		jsonobj.put("CREATED_DATE", createddate);
		
		Map<String, Object> ticketDetails = new HashMap<String, Object>();
		ticketDetails.put("Function", jsonobj.get("FUNCTION").toString());
		ticketDetails.put("TicketId", jsonobj.get(TICKET_ID_VARIABLE)
				.toString());
		ticketDetails.put("SubCategoryId", jsonobj
				.get(SUB_CATEGORY_ID_VARIABLE).toString());
		ticketDetails.put("Status", jsonobj.get(WORKFLOW_STATE_VARIABLE)
				.toString());
		ticketDetails.put("CreatedDate", createddate);
		ticketDetails.put("CreatedBy", loginID);
		ticketDetails.put(PRIORITY_ID_VARIABLE, jsonobj.get(
				PRIORITY_ID_VARIABLE).toString());
		ticketDetails.put("Action", jsonobj.get("ACTION").toString());
		ticketDetails.put("FunctionId", jsonobj.get(FUNCTION_ID_VARIABLE)
				.toString());
		
		// Approval flow Changes done by Kruthi START:
		String approverid = "";
		String subcat = jsonobj.get(SUB_CATEGORY_ID_VARIABLE).toString();
		String ticketId = jsonobj.get(TICKET_ID_VARIABLE).toString();
		
		
		if(!jsonobj.has("ISORCH")){
		List<Map<String, Object>> allApproversForSelectedSubCat = MasterDataImpl
				.getSubCategoryApproversById(Integer.parseInt(subcat),jsonobj.get("WORKFLOW_STATE").toString());
		List<String> approvers = new ArrayList<String>();
		if (allApproversForSelectedSubCat.size() != 0) {
			for (Map<String, Object> category : allApproversForSelectedSubCat) {
				approvers.add(category.get(APPROVER_ID_VARIABLE).toString());
			}
		} else {

			String deptId = getIHDDAO.getDeptIdFromFuncSpecList(ticketDetails
					.get("TicketId").toString(), Long.parseLong(ticketDetails
					.get("FunctionId").toString()),
					FUNCTION_SPECIFIC_FIELD_VARIABLE);

			Map<String, Object> allDeptwiseApproversForSelectedSubCat = MasterDataImpl
					.getDeptWiseCategoriesApproversById(Integer
							.parseInt(subcat), 1, Integer.parseInt(deptId));

			approvers.add(allDeptwiseApproversForSelectedSubCat.get(
					APPROVER_ID_VARIABLE).toString());
		}
		
		for (Iterator approverIter = approvers.iterator(); approverIter
				.hasNext();) {
			String approver = (String) approverIter.next();
			boolean alreadyApprovedByFirstApp = getIHDDAO.checkApprovalExists(
					approver, ticketId);
			if (alreadyApprovedByFirstApp) {
				continue;
			} else {
				approverid = approver;
				break;
			}
		}
		// Approval flow Changes done by Kruthi END:
		jsonobj.put("APPROVER_ID", approverid);
		}
		jsonobj.put("LOGIN_ID", loginID);
		
		
		Map<String, Object> assignmentDetails = new HashMap<String, Object>();
		long locationid = 0;
		long loc_det_id = 0;
		long functionid = 0;
		long priorityid = 0;
		long sub_category_id = 0;
		if (jsonobj.get(LOCATION_ID_VARIABLE) != null) {
			locationid = Long.parseLong(jsonobj.get(LOCATION_ID_VARIABLE)
					.toString());
		}
		if (jsonobj.get("LOC_DET_ID") != null) {
			loc_det_id = Long.parseLong(jsonobj.get("LOC_DET_ID").toString());
		}
		if (jsonobj.get(FUNCTION_ID_VARIABLE) != null) {
			functionid = Long.parseLong(jsonobj.get(FUNCTION_ID_VARIABLE)
					.toString());
		}
		if (jsonobj.get(PRIORITY_ID_VARIABLE) != null) {
			priorityid = Long.parseLong(jsonobj.get(PRIORITY_ID_VARIABLE)
					.toString());
		}
		if (jsonobj.get(SUB_CATEGORY_ID_VARIABLE) != null) {
			sub_category_id = Long.parseLong(jsonobj.get(
					SUB_CATEGORY_ID_VARIABLE).toString());
		}
		if (jsonobj.get("ACTION").toString().equalsIgnoreCase("Approved")) {

				int approvalValidStateId = getIHDDAO
						.getApprovalValidState(jsonobj);
				if (approvalValidStateId == 0) {
					if (jsonobj.get("FUNCTION").toString().equalsIgnoreCase(
							"Life and Health Operations Canada")) {

						String openStateId = workflowimpl
								.getStateId("Open", workflowid);

						int groupID;

						assignmentDetails = MasterDataImpl.getDefaultAssignDetails(
								(int) (sub_category_id), (int) (locationid));

						try {
							groupID = Integer.parseInt(assignmentDetails.get(
									GROUP_ID_VARIABLE).toString());
						} catch (NullPointerException e) {
							groupID = 0;
						}

						// ECT method has been added
						String ECT = "";
						// try {
						List<Map<String, Object>> groupList = MasterDataImpl
								.getIHDGroupMaster();
						long group_base_locationId = 0l;
						for (Map<String, Object> stringObj : groupList) {
							if (stringObj.get(GROUP_ID_VARIABLE) != null) {
								if (stringObj.get(GROUP_ID_VARIABLE).toString()
										.equalsIgnoreCase(String.valueOf(groupID))) {
									group_base_locationId = Long.parseLong(stringObj
											.get(LOCATION_ID_VARIABLE).toString());
								}
							}
						}

						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						cal.add(Calendar.HOUR_OF_DAY, -5);
						cal.add(Calendar.MINUTE, -30);
						Date modified_date = cal.getTime();
						SimpleDateFormat sd = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String modified_date_string = sd.format(modified_date);
						long opr_id = MasterDataImpl.getGroupOPRID(groupID, functionid,
								group_base_locationId);

						ECT = HELPDESK_ECTPopulator.getECT("Open", Integer
								.parseInt((String) jsonobj.get(PRIORITY_ID_VARIABLE)),
								modified_date_string, sub_category_id, "I",
								group_base_locationId, opr_id);

						String ticketNo = ticketDetails.get("TicketId").toString();
						String wfStatus = ticketDetails.get("Status").toString();

						String jsonString = "{\"TICKET_ID\":" + "\"" + ticketNo + "\""
								+ ",\"DATA\":\"<data><ASSIGNED_GROUP>-"
								+ "</ASSIGNED_GROUP><WORKFLOW_STATE>" + wfStatus
								+ "</WORKFLOW_STATE></data>\",\"MENU_ID\":\"1\"}";

						JSONObject jsonNew = new JSONObject(jsonString);
						jsonNew.put("LOCKED_BY", loginID);
						jsonNew.put("LOCKED_DATE", createddate);
						HELPDESK_EditDAO getHelpdeskDAO = (HELPDESK_EditDAO) ctx
								.getBean(HELPDESK_EDITDAO_IMPL);
						getHelpdeskDAO.lockIHDTicket(jsonNew);

						getIHDDAO.updateAssignedGroup(ECT, openStateId, groupID,
								ticketDetails);
						getIHDDAO.insertTicketApproverDetails(jsonobj);
						updateMessage = "Success";

					}
				}else{

				int approvalValidState_update = getIHDDAO.updateNextStatus(
						locationid, loc_det_id, functionid, (int) priorityid,
						createddate, sub_category_id, org, jsonobj
								.get("ACTION").toString(),
						approvalValidStateId, workflowid, jsonobj.get(
								TICKET_ID_VARIABLE).toString(), ticketDetails,
						jsonobj);

				if (approvalValidState_update == 1)
					updateMessage = "Success";
				else
					updateMessage = "Status not updated";
			}
		} else if (jsonobj.get("ACTION").toString()
				.equalsIgnoreCase("Rejected")) {
			String approvalValidStateId = workflowimpl.getStateId("Cancelled",
					workflowid);
			int cancelledStateId = Integer.parseInt(approvalValidStateId);
			int approvalValidState_update = getIHDDAO.updateNextStatus(
					locationid, loc_det_id, functionid, (int) priorityid,
					createddate, sub_category_id, org, jsonobj.get("ACTION")
							.toString(), cancelledStateId, workflowid, jsonobj
							.get(TICKET_ID_VARIABLE).toString(), ticketDetails,
					jsonobj);
			if (approvalValidState_update == 1)
				updateMessage = "Success";
			else
				updateMessage = "Status not updated";
		} else if (jsonobj.get("ACTION").toString().equalsIgnoreCase(
				"Need More Info")) {
			String infoStateId = workflowimpl.getStateId("Need More Info",
					workflowid);
			int moreInfoStateId = Integer.parseInt(infoStateId);
			HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
			String getAdditonalInfo = converter.replaceSpecialChars(getIHDDAO
					.getAdditionalInfoforTicket(jsonobj.get(TICKET_ID_VARIABLE) // Used replaceSpecialChars instead of convertSpecialChars
							.toString()));
			ticketDetails.put(ADDITIONAL_INFO_VARIABLE, getAdditonalInfo);

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date.setTimeZone(TimeZone.getTimeZone("GMT"));
			Map<String, Object> dateobject = new HashMap<String, Object>();
			dateobject.put("datetime", CustomDateFormatConstants
					.convertToEST(date.format(new Date())));

			if (getAdditonalInfo == null) {
				getAdditonalInfo = "";
			}
			String username = ticketDetails.get("CreatedBy").toString();

			String additionalInfo = jsonobj.get("COMMENTS").toString();
			String additionalinfo = "By :" + username + ",\nDate:"
					+ dateobject.get("datetime").toString() + ",\nComments:"
					+ additionalInfo + "\n--------------------\n"
					+ converter.convertSpecialChars(getAdditonalInfo); // Used convertSpecialChars instead of replaceSpecialChars

			ticketDetails.put("COMMENTS", additionalinfo);

			String ticketNo = ticketDetails.get("TicketId").toString();
			String wfStatus = ticketDetails.get("Status").toString();

			String jsonString = "{\"TICKET_ID\":" + "\"" + ticketNo + "\""
					+ ",\"DATA\":\"<data><ADDITIONAL_INFO>"
					+ getAdditonalInfo.replace("\n", "brlinebreak")
					+ "</ADDITIONAL_INFO><WORKFLOW_STATE>" + wfStatus
					+ "</WORKFLOW_STATE></data>\",\"MENU_ID\":\"1\"}";

			JSONObject jsonNew = new JSONObject(jsonString);
			jsonNew.put("LOCKED_BY", username);
			DateFormat dateFormatGMT = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String creationDate = dateFormatGMT.format(new Date());
			jsonNew.put("LOCKED_DATE", creationDate);
			ApplicationContext appCtx = COMMON_AppContext.getCtx();
			HELPDESK_EditDAO getIHDDAOImpl = (HELPDESK_EditDAO) appCtx
					.getBean(HELPDESK_EDITDAO_IMPL);
			getIHDDAOImpl.lockIHDTicket(jsonNew);

			int approvalValidState_update = getIHDDAO.updateNextStatus(
					locationid, loc_det_id, functionid, (int) priorityid,
					createddate, sub_category_id, org, jsonobj.get("ACTION")
							.toString(), moreInfoStateId, workflowid, jsonobj
							.get(TICKET_ID_VARIABLE).toString(), ticketDetails,
					jsonobj);

			if (approvalValidState_update == 1)
				updateMessage = "Success";
			else
				updateMessage = "Status not updated";
		} else if (jsonobj.get("ACTION").toString().equalsIgnoreCase(
				"Send to Helpdesk")) {
			int hdQueueStateId = Integer.parseInt(workflowimpl.getStateId(
					HELPDESK_QUEUE_STATE_VARIABLE, workflowid));
			HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
			String getAdditonalInfo = converter.replaceSpecialChars(getIHDDAO
					.getAdditionalInfoforTicket(jsonobj.get(TICKET_ID_VARIABLE) // Used replaceSpecialChars instead of convertSpecialChars
							.toString()));
			ticketDetails.put(ADDITIONAL_INFO_VARIABLE, getAdditonalInfo);

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date.setTimeZone(TimeZone.getTimeZone("GMT"));
			Map<String, Object> dateobject = new HashMap<String, Object>();
			dateobject.put("datetime", CustomDateFormatConstants
					.convertToEST(date.format(new Date())));

			if (getAdditonalInfo == null) {
				getAdditonalInfo = "";
			}
			String username = ticketDetails.get("CreatedBy").toString();
			if(jsonobj.has("ISORCH")){
				username="OrchEngg1";
			}
			String additionalInfo = jsonobj.get("COMMENTS").toString();
			String additionalinfo = "By :" + username + ",\nDate:"
					+ dateobject.get("datetime").toString() + ",\nComments:"
					+ additionalInfo + "\n--------------------\n"
					+ converter.convertSpecialChars(getAdditonalInfo); // Used convertSpecialChars instead of replaceSpecialChars

			ticketDetails.put("COMMENTS", additionalinfo);

			String ticketNo = ticketDetails.get("TicketId").toString();
			String wfStatus = ticketDetails.get("Status").toString();

			String jsonString = "{\"TICKET_ID\":" + "\"" + ticketNo + "\""
					+ ",\"DATA\":\"<data><ADDITIONAL_INFO>"
					+ getAdditonalInfo.replace("\n", "brlinebreak")
					+ "</ADDITIONAL_INFO><WORKFLOW_STATE>" + wfStatus
					+ "</WORKFLOW_STATE></data>\",\"MENU_ID\":\"1\"}";

			JSONObject jsonNew = new JSONObject(jsonString);
			jsonNew.put("LOCKED_BY", username);
			DateFormat dateFormatGMT = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String creationDate = dateFormatGMT.format(new Date());
			jsonNew.put("LOCKED_DATE", creationDate);
			ApplicationContext appCtx = COMMON_AppContext.getCtx();
			HELPDESK_EditDAO getIHDDAOImpl = (HELPDESK_EditDAO) appCtx
					.getBean(HELPDESK_EDITDAO_IMPL);
			getIHDDAOImpl.lockIHDTicket(jsonNew);

			int approvalValidState_update = getIHDDAO.updateNextStatus(
					locationid, loc_det_id, functionid, (int) priorityid,
					createddate, sub_category_id, org, jsonobj.get("ACTION")
							.toString(), hdQueueStateId, workflowid, jsonobj
							.get(TICKET_ID_VARIABLE).toString(), ticketDetails,
					jsonobj);

			if (approvalValidState_update == 1)
				updateMessage = "Success";
			else
				updateMessage = "Status not updated";
		} 
		return updateMessage;

	}

	/**
	 * To get SLA Status for the ticket as IN or OUT
	 * 
	 */

	 @RequestMapping(value = "/hdSLAStatusforTicket.htm", method = RequestMethod.GET)
     public @ResponseBody
     void hdSLAStatusforTicket(String jsonString, HttpServletResponse response,
                 HttpServletRequest request) throws ParseException, IOException,
                 JSONException {
           ApplicationContext ctx = COMMON_AppContext.getCtx();
           JSONObject jsonobj = new JSONObject(jsonString);
           HELPDESK_EditDAO getIHDDAOImpl = (HELPDESK_EditDAO) ctx
           .getBean(HELPDESK_EDITDAO_IMPL);
           String slastatus=getIHDDAOImpl.getSLAStatusProcedureCall(jsonobj
                       .getString(TICKET_ID_VARIABLE).toString());

           JsonUtility.sendData(slastatus, response);
           
     }

	/**
	 * To get the next approval of the ticket
	 * 
	 */

	@RequestMapping(value = "/hdgetApprovalStateforTicket.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetApprovalStateforTicket(String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		JSONObject jsonobj = new JSONObject(jsonString);
		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		User userObj = (User) request.getSession().getAttribute(loginID);
		String roleID = userObj.getUserRoleId();

		String ticketId = jsonobj.getString("TICKET_ID");
		String function_name = jsonobj.getString("FUNCTION_NAME");
		String deptID = jsonobj.getString("DEPT_ID");
		String approvelFlag=jsonobj.getString("IS_APPROVAL_REQUIRED");
		int sub_category_id = Integer.parseInt(jsonobj
				.getString("SUBCATEGORY_ID"));
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean(WORKFLOW_DAO_IMPL);
		 HELPDESK_EditDAO getIHDDAOImpl = (HELPDESK_EditDAO) ctx
		           .getBean(HELPDESK_EDITDAO_IMPL);

		String menuID = workflowimpl.getWorkflowID(function_name);

		List<String> approvalstates = new ArrayList<String>();

		List<Map<String, Object>> categories = MasterDataImpl
				.getCategoriesById(CATEGORY_ID_VARIABLE, sub_category_id);
		Map<String, Object> resultMap = categories.get(0);
		if ((Boolean) resultMap.get(IS_CHANGE_REQUEST_VARIABLE) && !approvelFlag.equalsIgnoreCase("1")) {
			Map<String, Object> categoryresultmap = null;
			int orderid=1;
			
			List<Map<String, Object>> categoryApprovers = MasterDataImpl
					.getCategoriesApproversById(sub_category_id, orderid);
			if (categoryApprovers.size() <= 0 && deptID != "") {
				categoryresultmap = MasterDataImpl
						.getDeptWiseCategoriesApproversById(sub_category_id, 1,
								Integer.parseInt(deptID));
			} else {
				categoryresultmap = categoryApprovers.get(0);
			}
			int APPROVER_ID = (Integer) categoryresultmap.get("APPROVER_ID");
			int workflowid = Integer.parseInt(MasterDataImpl.getApproverID(
					Integer.toString(APPROVER_ID), "APPROVER_ID"));
			String currentstate = workflowimpl.getStateName(Integer
					.toString(workflowid), menuID);

			// Check already approval done for the ticket.
			HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
					.getBean(HELPDESK_EDITDAO_IMPL);
			int checkApprovalExists = getIHDDAO.checkApprovalExists(ticketId,
					APPROVER_ID);
		
			if (checkApprovalExists != 0) {
				
				int nextValidApprover = getIHDDAO.getNextApproverId(jsonobj,
						APPROVER_ID);
				if(nextValidApprover!=0)
					APPROVER_ID = nextValidApprover;
				
				if (getIHDDAO.checkApprovalExists(ticketId, APPROVER_ID) != 0) {
					currentstate = ASSIGNED_STATE_VARIABLE;
					//approvalstates.add(currentstate);
					approvalstates = workflowimpl
							.getStatesForSelectedFunctionOnRoleWise(jsonobj.getString("CATEGORY_ID"), jsonobj.getString("SUBCATEGORY_ID"), function_name , Integer.parseInt(roleID));
				} else {
					
					workflowid = Integer.parseInt(MasterDataImpl.getApproverID(
							Integer.toString(APPROVER_ID), "APPROVER_ID"));
					currentstate = workflowimpl.getStateName(Integer
							.toString(workflowid), menuID);
					approvalstates.add(currentstate);
				}
			} else {
				approvalstates.add(currentstate);
			}

		} else {
			//By Nisha --changed for HR Helpdesk --start
			String subCategoryID=jsonobj.getString("SUBCATEGORY_ID");
			String categoryID=jsonobj.getString("CATEGORY_ID");
				approvalstates = workflowimpl
					.getStatesForSelectedFunctionOnRoleWise(categoryID, subCategoryID, function_name , Integer.parseInt(roleID));
			}
		//By Nisha --changed for HR Helpdesk --end
		JsonUtility.sendData(approvalstates, response);
	}

	/**
	 * To get the assigned groups for given function&location
	 * 
	 */

	public static List<Map<String, Object>> getGroupsForFunciton(
			HttpServletRequest request, int functionid, int locationid) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();

		assigned_groups = MasterDataImpl.getGroupsForFunction(functionid,
				locationid);
		return assigned_groups;
	}

	/**
	 * To get the assigned groups for given function&location
	 * 
	 * 
	 */

	@RequestMapping(value = "/hdgetGroupsforFunction.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforFunction(@RequestParam String functionid,
			String locationid, HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {

		List<Map<String, Object>> assigned_groups = getGroupsForFunciton(
				request, Integer.parseInt(functionid), Integer
						.parseInt(locationid));

		JsonUtility.sendData(assigned_groups, response);
	}
	
	@RequestMapping(value = "/hdgetGroupsforFinanceAdminFunction.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforFinanceAdminFunction(@RequestParam int categoryid,
			int locationid, HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> assignmentDetails = MasterDataImpl
		.getDefaultAssignDetailsForAdminFinance(categoryid, locationid);

		JsonUtility.sendData(assignmentDetails, response);
	}

	@RequestMapping(value = "/hdgetGroupsForAA.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforFunctionAA(@RequestParam String groupid,
			HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> assigned_groups =MasterDataImpl.getGroupsForFunctionforAA(
				Integer.parseInt(groupid));		

		JsonUtility.sendData(assigned_groups, response);
	}
	
	/**
	 * To get the EST Time to update into Additional info
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/getESTTime.htm", method = RequestMethod.GET)
	public void getESTTime(HttpServletRequest request,
			HttpServletResponse response) {

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTimeZone(TimeZone.getTimeZone("GMT"));
		Map<String, Object> dateobject = new HashMap<String, Object>();
		dateobject.put("datetime", CustomDateFormatConstants.convertToEST(date
				.format(new Date())));

		JsonUtility.sendData(dateobject, response);
	}

	/**
	 * To unlock the locked tickets
	 * 
	 * 
	 */

	@RequestMapping(value = "/unlockTickets.htm", method = RequestMethod.GET)
	public void unlockTickets(@RequestParam String json,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		JSONObject jsonarray = new JSONObject(json);
		JSONArray ticketarray = jsonarray.getJSONArray("JSONARRAY");
		ArrayList<Object> ArgsList = new ArrayList<Object>();

		Map<String, Object> statusmessage = new HashMap<String, Object>();
		for (int i = 0; i < ticketarray.length(); i++) {
			JSONObject jsonobj = ticketarray.getJSONObject(i);
			Object[] GenArgs = new Object[] { jsonobj.get("MENU_ID"),
					jsonobj.get(TICKET_ID_VARIABLE) };
			ArgsList.add(i, GenArgs);
		}

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		// try {
		getIHDDAO.unlockTickets(ArgsList);
		statusmessage.put(STATUS_VARIABLE, "Success");
		JsonUtility.sendData(statusmessage, response);
	}

	/**
	 * To get the Looged in executives in iconnect
	 * 
	 * 
	 */

	public static List<Map<String, Object>> getLoggedInGroupMembers(
			String type, String groupid) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);

		List<Map<String, Object>> groupMembers = new ArrayList<Map<String, Object>>();

		if (type.equalsIgnoreCase("All")) {
			groupMembers = MasterDataImpl.getIHDGroupMemberID(groupid);
		} else {
			groupMembers = getIHDDAO.getLoggedInGroupMembers(groupid);
		}

		return groupMembers;
	}

	/**
	 * To get the group members for the asigned group
	 * 
	 * 
	 */
	@RequestMapping(value = "/hdgetGroupmembers.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupmembers(@RequestParam String groupId, String type,
			String functionid, String locationid, String from,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {

		Map<String, Object> responsedetails = new HashMap<String, Object>();
		List<Map<String, Object>> group_members = getLoggedInGroupMembers(type,
				groupId);
		String isOperational = "";
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
		.getBean(HELPDESK_EDITDAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		String groupManager=null;
		if (!from.equalsIgnoreCase("checkbox")) {
			long locationIdForSelGroup = 0;
			List<Map<String, Object>> groupList = MasterDataImpl
					.getIHDGroupMaster();

			for (Map<String, Object> stringObj : groupList) {
				if (stringObj.get(GROUP_ID_VARIABLE) != null) {
					if (stringObj.get(GROUP_ID_VARIABLE).toString()
							.equalsIgnoreCase(groupId)) {
						locationIdForSelGroup = Long.parseLong(stringObj.get(
								LOCATION_ID_VARIABLE).toString());
						groupManager=stringObj.get(
						"MANAGER").toString();
					}
				}
			}
			
			// Out of Operation msg
			if(null!=groupId && null!=functionid){
			isOperational=getIHDDAO.getOperatinalTimeFlag(groupId,functionid);
			}
			
			if(isOperational.equalsIgnoreCase("YES")){
				responsedetails.put(OPERATION_HOURS_AVAILABLE_VARIABLE,
						true);
			}else{
				responsedetails.put(OPERATION_HOURS_AVAILABLE_VARIABLE,
						false);
			}
			//Out of Operation msg End
			
			long opr_id = MasterDataImpl.getGroupOPRID(Long.parseLong(groupId),
					Long.parseLong(functionid), locationIdForSelGroup);
			
			List<Map<String, Object>> slaList = MasterDataImpl
					.getSLAOperatingTimeList(opr_id);
			responsedetails.put("SERVICE_WINDOW_DETAILS", slaList);
		} else {
			responsedetails.put(OPERATION_HOURS_AVAILABLE_VARIABLE, true);
		}
		responsedetails.put("MEMBER_DETAILS", group_members);
		
		responsedetails.put("MANAGER_ID", groupManager);

		JsonUtility.sendData(responsedetails, response);
	}

	/**
	 * To get the asset details for the ticket
	 * 
	 * 
	 */

	@RequestMapping(value = "hdTicketAssetDetail.htm", method = RequestMethod.GET)
	public @ResponseBody
	void ticketAssetDetails(@RequestParam String empid, ModelMap model,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		int i = 0;
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		List<Map<String, Object>> assetData = getIHDDAO.getAssetDetail(empid);
		JSONArray jsonArr = new JSONArray();

		for (Map<String, Object> anAssetData : assetData) {
			JSONObject json = new JSONObject();
			// Map<String, Object> assetListInfo = anAssetData;
			List<Map<String, Object>> assetCompData = getIHDDAO
					.getAssetCompDetail(anAssetData.get("SERIAL_NUMBER")
							.toString());
			if (null != anAssetData.get("ASSET_DET_ID")) {
				json.append("ASSET_DET_ID", anAssetData.get("ASSET_DET_ID")
						.toString());
			} else
				json.append("ASSET_DET_ID", "-");
			if (null != anAssetData.get("CLASSIFICATION")) {
				json.append("CLASSIFICATION", anAssetData.get("CLASSIFICATION")
						.toString());
			} else
				json.append("CLASSIFICATION", "-");
			if (null != anAssetData.get("ASSET_TYPE")) {
				json.append("ASSET_TYPE", anAssetData.get("ASSET_TYPE")
						.toString());
			} else
				json.append("ASSET_TYPE", "-");
			if (null != anAssetData.get("MAKE")) {
				json.append("MAKE", anAssetData.get("MAKE").toString());
			} else
				json.append("MAKE", "-");
			if (null != anAssetData.get("MODEL")) {
				json.append("MODEL", anAssetData.get("MODEL").toString());
			} else
				json.append("MODEL", "-");
			if (null != anAssetData.get("ALLOCATION_DATE")) {
				json.append("ALLOCATION_DATE", anAssetData.get(
						"ALLOCATION_DATE").toString());
			} else
				json.append("ALLOCATION_DATE", "-");
			if (null != anAssetData.get("WARRANTY_START_DATE")) {
				json.append("WARRANTY_START_DATE", anAssetData.get(
						"WARRANTY_START_DATE").toString());
			} else
				json.append("WARRANTY_START_DATE", "-");
			if (null != anAssetData.get("WARRANTY_END_DATE")) {
				json.append("WARRANTY_END_DATE", anAssetData.get(
						"WARRANTY_END_DATE").toString());
			} else
				json.append("WARRANTY_END_DATE", "-");

			json.append("SERIAL_NUMBER", anAssetData.get("SERIAL_NUMBER")
					.toString());

			json.append("EXIST_PARENT", getIHDDAO
					.checkExistParentAsset(anAssetData.get("SERIAL_NUMBER")
							.toString()));

			for (Map<String, Object> assetCompListInfo : assetCompData) {
				if (null != assetCompListInfo.get("COMPONENT")) {
					json.append("COMPONENT", assetCompListInfo.get("COMPONENT")
							.toString());
				} else
					json.append("COMPONENT", "-");
				if (null != assetCompListInfo.get("SERIAL_NUMBER")) {
					json.append("CHILD_SERIAL_NUMBER", assetCompListInfo.get(
							"SERIAL_NUMBER").toString());
				} else
					json.append("CHILD_SERIAL_NUMBER", "-");
				if (null != assetCompListInfo.get("COMPONENT_ID")) {
					json.append("COMPONENT_ID", assetCompListInfo.get(
							"COMPONENT_ID").toString());
				} else
					json.append("COMPONENT_ID", "-");
				if (null != assetCompListInfo.get("ASSET_COMP_ID")) {
					json.append("ASSET_COMP_ID", assetCompListInfo.get(
							"ASSET_COMP_ID").toString());
				} else
					json.append("ASSET_COMP_ID", "-");
				json.append("EXIST_CHILD", getIHDDAO
						.checkExistAsset(assetCompListInfo.get("SERIAL_NUMBER")
								.toString()));
			}
			jsonArr.put(i, json.toString());
			i++;
		}
		if (jsonArr.length() != 0)
			JsonUtility.sendData(jsonArr.toString(), response);
		else
			JsonUtility.sendData("No Data", response);
	}


	/**
	 * To know whether the service window opertating times are avaiable or not
	 * 
	 * 
	 */
	private boolean isServiceWindowAvailable(List<Map<String, Object>> slalist) {

		Date date = new Date();
		SimpleDateFormat smp = new SimpleDateFormat("E");
		String currentday = smp.format(date);
		currentday = currentday.toUpperCase();
		smp = new SimpleDateFormat("k");
		int currenthour = Integer.parseInt(smp.format(date));
		if (currenthour == 24) {
			currenthour = 0;
		}
		smp = new SimpleDateFormat("m");
		int currentminute = Integer.parseInt(smp.format(date));

		boolean opertaionHoursAvailable = false;
		for (Map<String, Object> rowobj : slalist) {
			String day = (String) rowobj.get("DAY");
			String startTime = String.valueOf(rowobj.get("START_TIME"));
			String endTime = String.valueOf(rowobj.get("END_TIME"));
			int starthour = Integer.parseInt(startTime.substring(0, 2));
			int endhour = Integer.parseInt(endTime.substring(0, 2));
			int startminute = Integer.parseInt(startTime.substring(3, 5));
			int endminute = Integer.parseInt(endTime.substring(3, 5));

			if (starthour < endhour) {
				// if loop is Added to fix 24 *7 group out of operation issue
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (starthour == 0 && endhour == 23)) {
					opertaionHoursAvailable = true;
					break;
				}
				// if loop has been added to fix 8 :00 am op window msg removal
				if (day != null
						&& day.equalsIgnoreCase(currentday)
						&& (endminute == 0 && startminute == 0 && currentminute == 0)
						&& (currenthour == starthour)) {
					opertaionHoursAvailable = true;
					break;
				}
				// if loop is added for startminute an dend minute is not zero
				if (day != null
						&& day.equalsIgnoreCase(currentday)
						&& (endminute != 0 && startminute != 0)
						&& (currenthour == starthour
								&& currentminute >= startminute && currenthour < endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (endminute != 0 && startminute != 0)
						&& (currenthour > starthour && currenthour < endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null
						&& day.equalsIgnoreCase(currentday)
						&& (endminute != 0 && startminute != 0)
						&& (currenthour == endhour && currentminute <= endminute)) {
					opertaionHoursAvailable = true;
					break;
				}
				//
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (endminute != 0 && startminute == 0)
						&& (currenthour >= starthour && currenthour <= endhour)
						&& (currentminute <= endminute)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null
						&& day.equalsIgnoreCase(currentday)
						&& (endminute == 0 && startminute == 0 && currentminute == 0)
						&& (currenthour >= starthour && currenthour == endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (endminute == 0 && startminute == 0)
						&& (currenthour >= starthour && currenthour < endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (endminute == 0 && startminute != 0)
						&& (currenthour >= starthour && currenthour <= endhour)
						&& (currentminute >= startminute)) {
					opertaionHoursAvailable = true;
					break;
				}
			}

			// dEFECT FIX:265
			if (starthour > endhour) {
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (currenthour == starthour && currenthour > endhour)
						&& (currentminute >= startminute)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (currenthour > starthour && currenthour > endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (currenthour < starthour && currenthour == endhour)
						&& (endminute >= currentminute)) {
					opertaionHoursAvailable = true;
					break;
				}
				if (day != null && day.equalsIgnoreCase(currentday)
						&& (currenthour < starthour && currenthour < endhour)) {
					opertaionHoursAvailable = true;
					break;
				}
			}
			// Add logic for the gorup works in

		}
		return opertaionHoursAvailable;
	}
	
	/**
	 * To get the assigned groups for Admin
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/hdgetGroupsforAdmin.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforAdmin(@RequestParam String functionname,
			String subcatID, String deptid, String locid, String locdetID,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {

		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> assignmentDetails = new ArrayList<Map<String,Object>>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

			assignmentDetails = MasterDataImpl
					.getIHDLocationDetailDefaultAssignmentDetailsForAdmin(Integer
							.parseInt(subcatID), Integer.parseInt(locdetID));
		assigned_groups.addAll(assignmentDetails);

		JsonUtility.sendData(assigned_groups, response);
	}
	
	@RequestMapping(value = "/hdgetGroupsforAdminOnLoad.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforAdminOnLoad(@RequestParam String functionname,
			String subcatID, String deptid, String locid, String locdetID,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {

		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> assignmentDetails = new ArrayList<Map<String,Object>>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

			assignmentDetails = MasterDataImpl
					.getIHDLocationDetailDefaultAssignmentDetailsForAdmin(Integer
							.parseInt(subcatID), Integer.parseInt(locdetID));
		assigned_groups.addAll(assignmentDetails);

		JsonUtility.sendData(assigned_groups, response);
	}

	/**
	 * To get the assigned groups for Finance & HR functions
	 * 
	 * 
	 */

	@RequestMapping(value = "/hdgetGroupsforAdminFinHRFunction.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforAdminFinHRFunction(@RequestParam String functionname,
			String subcatID, String deptid, String locid, String locdetID,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();
		Map<String, Object> assignmentDetails = new HashMap<String, Object>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		if (functionname.equalsIgnoreCase(RBC_LIGHTHOUSE_FUNCTION_VARIABLE)) {
			assignmentDetails = MasterDataImpl.getDefaultAssignDetails(Integer
					.parseInt(subcatID), Integer.parseInt(locid));
		}
		if(assigned_groups.size()==0){
		assigned_groups.add(assignmentDetails);
		}

		JsonUtility.sendData(assigned_groups, response);
	}

	// Added other methods

	public String getManagerIdForApproval(JSONObject jsonobj)
			throws JSONException {

		String ManagerID = null;

		ApplicationContext ctx = COMMON_AppContext.getCtx();

		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);

		int APPROVER_ID = 0;
		String levelSubProjectId ="";
	
		String requestBy = (String) jsonobj.get(REQUESTED_BY);
		requestBy=requestBy.substring(requestBy.indexOf("(")+1,requestBy.indexOf(")"));

		List<Map<String, Object>> categoryApprovers = MasterDataImpl
				.getCategoriesApproversById(Integer.parseInt(jsonobj
						.getString(SUB_CATEGORY_ID_VARIABLE)), 1);
		if (categoryApprovers.size() <= 0 && jsonobj.has("DEPT_ID")) {
			Map<String, Object> categapprover = MasterDataImpl
					.getDeptWiseCategoriesApproversById(Integer
							.parseInt(jsonobj
									.getString(SUB_CATEGORY_ID_VARIABLE)), 1,
							Integer.parseInt(jsonobj.get("DEPT_ID").toString()));
			APPROVER_ID = (Integer) categapprover.get(APPROVER_ID_VARIABLE);
		} else {
			for (Map<String, Object> categapprover : categoryApprovers) {
				APPROVER_ID = (Integer) categapprover.get(APPROVER_ID_VARIABLE);
			}
		}

		Map<String, Object> approverEmployeedetails = MasterDataImpl
				.getApproverEmployeeDetailsByLocWise(APPROVER_ID, Integer
						.parseInt(jsonobj.getString(LOCATION_ID_VARIABLE)));

		String approverEmployee = (String) approverEmployeedetails
				.get("EMPLOYEE_ID");

		String employeeProjectID = jsonobj.get("PROJECT_ID").toString();

		String requestedBy = jsonobj.getString("REQUESTED_BY");
		requestedBy = requestedBy.substring(requestedBy.indexOf("(") + 1,
				requestedBy.indexOf(")"));

		Map<String, Object> employeedetails = HelpDeskImpl
				.getEmployeeDetails(requestedBy);

		boolean isExceptionalProject = false;
		if (null != employeeProjectID
				&& !employeeProjectID.equalsIgnoreCase(""))

		if (approverEmployee != null && !approverEmployee.equalsIgnoreCase("")) {

			ManagerID = approverEmployee;
		} else if (null != employeeProjectID
				&& !employeeProjectID.equalsIgnoreCase("")
				&& !employeeProjectID.equalsIgnoreCase("0")
				&& !isExceptionalProject && !employeeProjectID.equalsIgnoreCase("None") && !employeeProjectID.equalsIgnoreCase("None(0)")) {

			Map<String, Object> projectdetailmap = HelpDeskImpl
					.getProjectDetails(requestBy,employeeProjectID,levelSubProjectId);

			ManagerID = (String) projectdetailmap.get("EMPLOYEE_ID");

			if (ManagerID == null || ManagerID.equalsIgnoreCase("null")) {
				ManagerID = employeedetails.get(REPORTING_MANAGER_ID_VARIABLE)
						.toString();
			}
		} else if (employeeProjectID.equalsIgnoreCase("0")) {
			ManagerID = employeedetails.get(REPORTING_MANAGER_ID_VARIABLE)
					.toString();
		} else if (!employeeProjectID.equalsIgnoreCase("0")
				&& isExceptionalProject) {
			ManagerID = employeedetails.get(REPORTING_MANAGER_ID_VARIABLE)
					.toString();
		}

		// If requestor of ticket is the PM then RO is considered as Approving
		// Manager.
		if (requestedBy.equals(ManagerID)) {
			ManagerID = employeedetails.get(REPORTING_MANAGER_ID_VARIABLE)
					.toString();
		}
		return ManagerID;
	}

	@RequestMapping(value = "/saveFeedbackComments.htm", method = RequestMethod.GET)
	public @ResponseBody
	void saveFeedbackComments(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGIN_ID_VARIABLE);
		String dataMessage = updateFeedbackComment(json, loginID, userObj
				.getOrganization());
		JsonUtility.sendData(dataMessage, response);

	}

	public String updateFeedbackComment(String json, String loginID, String org)
			throws JSONException {
		Map<String, Object> ticketDetails = new HashMap<String, Object>();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		JSONObject jsonobj = new JSONObject(json.replace("\n",
				"brlinebreakbreak"));
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();

		String ticketID = jsonobj.get("TICKET_ID").toString();
		String wfStatusName = jsonobj.get("WORKFLOW_STATE").toString();
		String action = jsonobj.getString("ACTION").toString();
		String functionName = jsonobj.getString("FUNCTION").toString();
		String subCategoryName = jsonobj.getString("SUB_CATEGORY_ID")
				.toString();
		String priorityName = jsonobj.getString("PRIORITY_ID").toString();
		String locationName = jsonobj.getString("LOCATION_ID").toString();
		String functionID = jsonobj.getString("FUNCTION_ID").toString();
		String feedBackComments = converter.replaceSpecialChars(jsonobj
				.getString("FEEDBACK_COMMENTS").toString());
		// String
		// feedBackComments=jsonobj.getString("FEEDBACK_COMMENTS").toString();
		String feedBackRate = jsonobj.getString("FEEDBACK_RATE").toString();
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String createddate = dateFormatGmt.format(new Date());

		ticketDetails.put("TICKET_ID", ticketID);
		ticketDetails.put("WORKFLOW_STATE", wfStatusName);
		ticketDetails.put("ACTION", action);
		ticketDetails.put("FUNCTION", functionName);
		ticketDetails.put("SUB_CATEGORY_ID", subCategoryName);
		ticketDetails.put("PRIORITY_ID", priorityName);
		ticketDetails.put("LOCATION_ID", locationName);
		ticketDetails.put("FUNCTION_ID", functionID);
		ticketDetails.put("FEEDBACK_COMMENTS", feedBackComments);
		ticketDetails.put("FEEDBACK_RATE", feedBackRate);
		ticketDetails.put("CREATED_DATE", createddate);
		ticketDetails.put("CREATED_BY", loginID);

		String jsonString = "{\"TICKET_ID\":"
				+ "\""
				+ ticketID
				+ "\""
				+ ",\"DATA\":\"<data><ASSIGNED_GROUP>-"
				+ "</ASSIGNED_GROUP><WORKFLOW_STATE>"
				+ wfStatusName
				+ "</WORKFLOW_STATE><FEEDBACK_COMMENTS>-</FEEDBACK_COMMENTS><FEEDBACK>-</FEEDBACK></data>\",\"MENU_ID\":\"1\"}";

		JSONObject jsonNew = new JSONObject(jsonString);
		jsonNew.put("LOCKED_BY", loginID);
		jsonNew.put("LOCKED_DATE", createddate);

		HELPDESK_EditDAO getHelpdeskDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		getHelpdeskDAO.lockIHDTicket(jsonNew);
		int result = 0;
		result = getIHDDAO.saveFeedbackComments(ticketDetails);
		String returnMessage = null;
		if (result == 1) {
			returnMessage = "Success";
		} else {
			returnMessage = "Feed back comment not updated";
		}
		return returnMessage;
	}

	// Below method added to get iTrack related categories. - START (ADDED BY
	// KRUTHI)
	@RequestMapping(value = "/getCategoryRoleMapping.htm", method = RequestMethod.GET)
	public void getCategoriesRoleMapping(HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> role = MasterDataImpl.getRoleMappingForCat();
		JsonUtility.sendData(role, response);
	}

	// Below method added to get iTrack related categories - END. (ADDED BY
	// KRUTHI)

	@RequestMapping(value = "/updateRevokeDate.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getRevokeDate(@RequestParam String ticketId, String revokeDate,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getHelpdeskDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		int revokeDateUpdate = getHelpdeskDAO.updateDateForTicket(ticketId,
				revokeDate);
		String dataMessage = null;
		if (revokeDateUpdate != 0) {
			dataMessage = "Success";
		} else {
			dataMessage = "Status not updated";
		}
		JsonUtility.sendData(dataMessage, response);

	}

	/**
	 * To get the Iteration count from IC_ORCH_AUTOMATION_LOG for this ticket
	 * 
	 */

	@RequestMapping(value = "/getAutomationLogIteration.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getAutomationIteration(String ticketId, HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean(HELPDESK_EDITDAO_IMPL);
		List<Map<String, Object>> l_count = getIHDDAO.getAutomationIternationCount(ticketId);

		JsonUtility.sendData(l_count, response);

	}
	
}
