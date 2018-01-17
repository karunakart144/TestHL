/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.igate.iconnect.BO.HELPDESK_Create;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.LoginDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.exception.COMMON_Exception;

@Transactional(rollbackFor = Exception.class)
public class HELPDESK_CreateFunctionWise {

	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String WORKFLOW_DAO_IMPL = "workFlowDAOImpl";
	private static final String HELPDESK_DAO_IMPL = "HelpDeskTicket";

	private static final String USER_LOGINID_VARIABLE = "userLoginId";
	private static final String FUNCTION_ID_VARIABLE = "FUNCTION_ID";
	private static final String CATEGORY_ID_VARIABLE = "CATEGORY_ID";
	private static final String SUB_CATEGORY_ID_VARIABLE = "SUB_CATEGORY_ID";
	private static final String USERS_IMPACTED_VARIABLE = "USERS_IMPACTED";
	private static final String CONTACT_NO_VARIABLE = "CONTACT_NO";
	private static final String ALTERNATE_CONTACT_NO_VARIABLE = "ALTERNATE_CONTACT_NO";
	private static final String PROJECT_ID_VARIABLE = "PROJECT_ID";
	private static final String SUB_PROJECT_ID_VARIABLE = "SUB_PROJECT_ID";
	private static final String IS_CHANGE_REQUEST_VARIABLE = "IS_CHANGE_REQUEST";
	private static final String GROUP_ID_VARIABLE = "GROUP_ID";
	private static final String PRIORITY_ID_VARIABLE = "PRIORITY_ID";
	private static final String SUBJECT_VARIABLE = "SUBJECT";
	private static final String DESCRIPTION_VARIABLE = "DESCRIPTION";
	private static final String APPROVER_ID_VARIABLE = "APPROVER_ID";
	private static final String ON_BEHALF_OF_VARIABLE = "ON_BEHALF_OF";
	private static final String COPY_TO_VARIABLE = "COPY_TO";
	private static final String LOCATION_ID_VARIABLE = "LOCATION_ID";
	private static final String REPORTING_MANAGER_ID_VARIABLE = "REPORTING_MANAGER_ID";
	private static final String REPORTING_MANAGER_NAME_VARIABLE = "REPORTING_MANAGER_NAME";
	private static final String MANAGER_ID_VARIABLE = "MANAGER_ID";
	private static final String ECT_VARIABLE = "ECT";
	private static final String CREATED_BY_VARIABLE = "createdby";
	private static final String CREATED_DATE_VARIABLE = "createddate";
	private static final String CATEGORYID_FOR_ECT_VARIABLE = "ECTCategoryid";
	private static final String CC_EMAIL_ID_VARIABLE = "ccemailid";
	private static final String LOC_DET_ID_VARIABLE = "loc_det_id";
	private static final String OS_DETAILS_VARIABLE = "osdetails";
	private static final String BROWSER_DETAILS_VARIABLE = "browserdetails";
	private static final String REQUESTED_BY_VARIABLE = "Requestedby";
	private static final String CURRENT_STATE_VARIABLE = "currentstate";
	private static final String CUBICAL_CODE_VARIABLE = "cubicalCode";
	private static final String WORKFLOW_ID_VARIABLE = "workflowid";
	private static final String IMPACTED_USERS_VARIABLE = "impactedusers";
	private static final String PROBLEM_FILE_DATA_VARIABLE = "problemfiledata";
	private static final String APPROVAL_FILE_DATA_VARIABLE = "approvalfiledata";
	private static final String PROBLEM_ID_VARIABLE = "PROBLEM_ID";
	private static final String IS_APPROVAL_VARIABLE = "isapprovalthere";
	private static final String AUDIT_DETAILS_VARIABLE = "auditlogdetails";
	private static final String SOURCE_VARIABLE = "source";
	private static final String MAIL_ID_VARIABLE = "mailid";
	private static final String MAIL_TRACKER_TYPE_VARIABLE = "mailTrackerType";
	private static final String STATUS_VARIABLE = "status";
	private static final String SUCCESS_VARIABLE = "SUCCESS";
	private static final String TICKET_ID_VARIABLE = "ticketid";
	private static final String ASSIGNED_GROUP_VARIABLE = "ASSIGNED_GROUP";
	private static final String GROUP_NAME_VARIABLE = "GROUP_NAME";

	private static final String HELPDESK_QUEUE_STATE_VARIABLE = "HelpDesk Queue";
	private static final String PENDING_FOR_APPROVAL_VER_STATE_VARIABLE = "Pending for Approval Verification";
	private static final String ASSIGNED_STATE_VARIABLE = "Assigned";
	private static final String OPEN_STATE_VARIABLE = "Open";

	private static final String OPERATION_EXCEPTION_MESSAGE = "Operation Time is not avalible for the selected location.Please contact helpdesk team to resove this.";
	private static final String ENGINEERS_NOT_AVAILABLE_EXCEPTION_MESSAGE = "Engineers are not avalible for the selected subcategory.Please contact helpdesk team to resove this.";
	
	private static Logger log = Logger
            .getLogger(HELPDESK_CreateFunctionWise.class);



	private static long getGroupLocation(String group_id) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		long group_base_locationId = 0l;
		List<Map<String, Object>> groupList = MasterDataImpl
				.getIHDGroupMaster();

		for (Map<String, Object> stringObj : groupList) {
			if (stringObj.get(GROUP_ID_VARIABLE) != null) {
				if (stringObj.get(GROUP_ID_VARIABLE).toString()
						.equalsIgnoreCase(group_id)) {
					group_base_locationId = Long.parseLong(stringObj.get(
							LOCATION_ID_VARIABLE).toString());
				}
			}
		}
		return group_base_locationId;
	}




	private static FileItem getfileforPath(String name, String path) throws SocketException, IOException
			 {
		byte[] b = null;
		FileItemFactory factory = new DiskFileItemFactory(25, null);
		// String textFieldName = "textField";
		FileItem item = factory.createItem("text/plain",
				"application/octet-stream", true, name);
		OutputStream os = null;
		// try {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		com.igate.iconnect.BO.COMMON_Ftp customFTP = (com.igate.iconnect.BO.COMMON_Ftp) ctx
				.getBean("ftpPropertyFile");

		FTPClient client = new FTPClient();

		client.connect(customFTP.getFtpIP().trim(), 21);

		client.login(customFTP.getFtpUser().trim(), customFTP.getFtpPassword()
				.trim());

		client.setFileType(FTP.BINARY_FILE_TYPE);

		InputStream ins = client.retrieveFileStream(path);

		b = IOUtils.toByteArray(ins);

		os = item.getOutputStream();
		os.write(b);

		return item;
	}

	
	
	public static void createLighthouseTicket(
			HELPDESK_Create CreateHelpdeskBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean(WORKFLOW_DAO_IMPL);

		String createdby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String Requestedby = "";
		String onbehalfof = CreateHelpdeskBean.getOnbeofEmpId();
		int impactedusers = 1;
		String phonenumber = "";
		String AltContactNumber = "";
		String categoryName = "";
		int categoryID = 0;
		int locationID = 0;
		String functionid = null;
		String catid = null;
		String subcatid = null;
		int groupID = 0;
		String cubicalCode = "";
		int priorityID = 0;
		int workflowid = 1;
		String ECT = null;
		String currentstate = "";
		String projectId = "0";
		boolean isChangeRequest = false;
		boolean isSubCategorySelected = false;
		boolean isApprovalAttachmentavialble = false;
		HashMap<String, Object> insertdetails = new HashMap<String, Object>();
		HashMap<String, Object> auditlogdetails = new HashMap<String, Object>();
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();

		String impactUserCheck = CreateHelpdeskBean.getImpactusercheck();
		if (impactUserCheck != null) {
			if (CreateHelpdeskBean.getImpactusercheck().equalsIgnoreCase("on")) {
				impactedusers = Integer.parseInt(CreateHelpdeskBean
						.getImpactusercount());
				auditlogdetails.put(USERS_IMPACTED_VARIABLE, impactedusers);
			}
		}
		if (CreateHelpdeskBean.getPhoneoption().equalsIgnoreCase("Mobile")) {
			phonenumber = "Mob-" + CreateHelpdeskBean.getPhonenumber();
		} else if (CreateHelpdeskBean.getPhoneoption().equalsIgnoreCase("Extn")) {
			phonenumber = "Extn-" + CreateHelpdeskBean.getPhonenumber();
		}
		auditlogdetails.put(CONTACT_NO_VARIABLE, phonenumber);
		if (!CreateHelpdeskBean.getAltcontactnumber().equalsIgnoreCase("")) {
			AltContactNumber = CreateHelpdeskBean.getAltcontactnumber();
			auditlogdetails
					.put(ALTERNATE_CONTACT_NO_VARIABLE, AltContactNumber);
		}
		auditlogdetails.put(PROJECT_ID_VARIABLE, converter
				.replaceSpecialChars(CreateHelpdeskBean
				.getProjectname()));
		if (!CreateHelpdeskBean.getFunction().equalsIgnoreCase("")) {
			if (!CreateHelpdeskBean.getCategory().equalsIgnoreCase("")) {
				if (!CreateHelpdeskBean.getSubcategory().equalsIgnoreCase("")) {
					categoryName = CreateHelpdeskBean.getSubcategory();
					functionid = CreateHelpdeskBean.getFunction();
					catid = CreateHelpdeskBean.getCategory();
					subcatid = categoryName;
					isSubCategorySelected = true;
					auditlogdetails.put(FUNCTION_ID_VARIABLE,
							MasterDataImpl
									.getCategoryNameById(Integer
											.parseInt(CreateHelpdeskBean
													.getFunction())));
					auditlogdetails.put(CATEGORY_ID_VARIABLE,
							MasterDataImpl
									.getCategoryNameById(Integer
											.parseInt(CreateHelpdeskBean
													.getCategory())));
					auditlogdetails.put(SUB_CATEGORY_ID_VARIABLE,
							MasterDataImpl.getCategoryNameById(Integer
									.parseInt(CreateHelpdeskBean
											.getSubcategory())));
				} else {
					categoryName = CreateHelpdeskBean.getCategory();
					functionid = CreateHelpdeskBean.getFunction();
					catid = categoryName;
					auditlogdetails.put(FUNCTION_ID_VARIABLE,
							MasterDataImpl
									.getCategoryNameById(Integer
											.parseInt(CreateHelpdeskBean
													.getFunction())));
					auditlogdetails.put(CATEGORY_ID_VARIABLE,
							MasterDataImpl
									.getCategoryNameById(Integer
											.parseInt(CreateHelpdeskBean
													.getCategory())));
				}
			} else {
				categoryName = CreateHelpdeskBean.getFunction();
				functionid = categoryName;
				auditlogdetails.put(FUNCTION_ID_VARIABLE, MasterDataImpl
						.getCategoryNameById(Integer
								.parseInt(CreateHelpdeskBean.getFunction())));
			}

		}
		List<Map<String, Object>> categories = MasterDataImpl
				.getCategoriesById(CATEGORY_ID_VARIABLE, Integer
						.parseInt(categoryName));
		for (Map<String, Object> category : categories) {
			categoryID = (Integer) category.get(CATEGORY_ID_VARIABLE);
			if(CreateHelpdeskBean.isApprovalFlag()!=true){
			isChangeRequest = (Boolean) category
					.get(IS_CHANGE_REQUEST_VARIABLE);
			}
		}
		locationID = Integer.parseInt(CreateHelpdeskBean.getLocationId());

		Map<String, Object> assignmentDetails = MasterDataImpl
				.getDefaultAssignDetails(categoryID, locationID);
		String groupNameForAuditLog = "";
		try {
			groupID = Integer.parseInt(assignmentDetails.get(GROUP_ID_VARIABLE)
					.toString());
			groupNameForAuditLog = assignmentDetails.get(GROUP_NAME_VARIABLE)
					.toString();
		} catch (NullPointerException e) {
			groupID = 0;
		}
		if (groupID != 0) {
			auditlogdetails.put(ASSIGNED_GROUP_VARIABLE, groupNameForAuditLog);
		}

		priorityID = Integer.parseInt(CreateHelpdeskBean.getPriority());
		String priorityname = MasterDataImpl.getPriorityNameById(priorityID);
		auditlogdetails.put(PRIORITY_ID_VARIABLE, priorityname);
		auditlogdetails.put(SUBJECT_VARIABLE, converter
				.replaceSpecialChars(CreateHelpdeskBean.getSubject()));
		auditlogdetails.put(DESCRIPTION_VARIABLE, converter
				.replaceSpecialChars(CreateHelpdeskBean.getDescription()));
		if (!"".equalsIgnoreCase(CreateHelpdeskBean.getCCEmailID())) {
			auditlogdetails.put(COPY_TO_VARIABLE, CreateHelpdeskBean
					.getCCEmailID());
		}
		projectId = CreateHelpdeskBean.getEmpProject();

		if (onbehalfof.equalsIgnoreCase("")) {
			onbehalfof = null;
			Requestedby = createdby;
		} else {
			auditlogdetails.put(ON_BEHALF_OF_VARIABLE, onbehalfof);
			Requestedby = onbehalfof;
		}
		int APPROVER_ID = 0;
		if (isSubCategorySelected && isChangeRequest && CreateHelpdeskBean.isApprovalFlag()!=true) {
			isApprovalAttachmentavialble = true;
			List<Map<String, Object>> categoryApprovers = MasterDataImpl
					.getCategoriesApproversById(categoryID, 1);
			for (Map<String, Object> categapprover : categoryApprovers) {
				APPROVER_ID = (Integer) categapprover.get(APPROVER_ID_VARIABLE);
			}
		}

		String menuID = workflowimpl.getWorkflowID(MasterDataImpl
				.getCategoryNameById(Integer.parseInt(CreateHelpdeskBean
						.getFunction())));
		if (isChangeRequest && !CreateHelpdeskBean.getApprovalfile().isEmpty() && CreateHelpdeskBean.isApprovalFlag()!=true) {
			workflowid = Integer.parseInt(workflowimpl.getStateId(
					PENDING_FOR_APPROVAL_VER_STATE_VARIABLE, menuID));
			currentstate = PENDING_FOR_APPROVAL_VER_STATE_VARIABLE;
		} else if (isChangeRequest && CreateHelpdeskBean.isApprovalFlag()!=true) {
			workflowid = Integer.parseInt(MasterDataImpl.getApproverID(Integer
					.toString(APPROVER_ID), APPROVER_ID_VARIABLE));
			currentstate = workflowimpl.getStateName(Integer
					.toString(workflowid), menuID);
		} else if (groupID == 0) {
			throw new COMMON_Exception(
					ENGINEERS_NOT_AVAILABLE_EXCEPTION_MESSAGE);
		} else {
			workflowid = Integer.parseInt(workflowimpl.getStateId(
					OPEN_STATE_VARIABLE, menuID));
			currentstate = OPEN_STATE_VARIABLE;
		}
		String problemID = MasterDataImpl.getApproverID("PROBLEM",
				DESCRIPTION_VARIABLE);

		String Floor = CreateHelpdeskBean.getFloor();
		int locationdetID = 0;// ECT Change
		if (Floor != null && !Floor.equalsIgnoreCase("")) {
			cubicalCode = CreateHelpdeskBean.getCubicalcode();
			locationdetID = Integer.parseInt(Floor);
		} else {
			cubicalCode = "NA";
			Floor = null;
		}

		String locationName = MasterDataImpl.getLocationsById(Integer
				.parseInt(CreateHelpdeskBean.getLocationId()), "CITY");

		auditlogdetails.put(LOCATION_ID_VARIABLE, locationName);

		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createddate = "";
		if (null != CreateHelpdeskBean.getCreateddate()
				&& !CreateHelpdeskBean.getCreateddate().equalsIgnoreCase("")) {
			createddate = CreateHelpdeskBean.getCreateddate();
		} else {
			createddate = dateFormatGmt.format(new Date());
		}
		// ECT Change
		if (isChangeRequest) {
			// ECT = null;
		} else {

			long group_location_id = getGroupLocation(String.valueOf(groupID));
			long opr_id = MasterDataImpl.getGroupOPRID(groupID, Long
					.valueOf(functionid), group_location_id);

			if (opr_id != 0) {
				ECT = HELPDESK_ECTPopulator.getECT(currentstate, priorityID,
						createddate, Long.valueOf(categoryName), userObj
								.getOrganization(), group_location_id, opr_id);
				auditlogdetails.put(ECT_VARIABLE, ECT + "(GMT)");
			} else {
				throw new COMMON_Exception(OPERATION_EXCEPTION_MESSAGE);
			}
		}

		if (!CreateHelpdeskBean.getAttachmentPath().equalsIgnoreCase("")
				&& CreateHelpdeskBean.getProblemfile().isEmpty()) {
			CommonsMultipartFile cmf = new CommonsMultipartFile(getfileforPath(
					CreateHelpdeskBean.getAttachmentName(), CreateHelpdeskBean
							.getAttachmentPath()));
			CreateHelpdeskBean.setProblemfile(cmf);
		}

		String manager_id_for_project = null;
		String displayStatusTouser = "";

		Map<String, Object> employeedetails = HelpDeskImpl
				.getEmployeeDetails(Requestedby);
		
		if (CreateHelpdeskBean.getEmpProject().contains(",")) {
			CreateHelpdeskBean
			.setEmplevelProject(CreateHelpdeskBean.getEmpProject()
					.substring(
							CreateHelpdeskBean.getEmpProject()
									.indexOf(",") + 1,
							CreateHelpdeskBean.getEmpProject()
									.length()));
			
			CreateHelpdeskBean
					.setEmpProject(CreateHelpdeskBean.getEmpProject()
							.substring(
									0,
									CreateHelpdeskBean.getEmpProject()
											.indexOf(",")));
			projectId = CreateHelpdeskBean.getEmpProject();
			
		}

/*		boolean isExceptionalProject = MasterDataImpl
				.isExceptionalProject(CreateHelpdeskBean.getEmpProject());*/
		
		if (null != CreateHelpdeskBean.getEmpProject()
				&& !CreateHelpdeskBean.getEmpProject().equalsIgnoreCase("")
				&& !CreateHelpdeskBean.getEmpProject()
						.equalsIgnoreCase("0") ) {
			manager_id_for_project = CreateHelpdeskBean.getProjectname()
					.substring(
							CreateHelpdeskBean.getProjectname()
									.lastIndexOf("(")+1,
							CreateHelpdeskBean.getProjectname()
									.lastIndexOf(")"));
			displayStatusTouser=CreateHelpdeskBean.getProjectname()
					.substring(
							CreateHelpdeskBean.getProjectname()
									.lastIndexOf(":")+1,
							CreateHelpdeskBean.getProjectname()
									.lastIndexOf("("));
			if (manager_id_for_project == null
					|| manager_id_for_project.equalsIgnoreCase("null")) {
				manager_id_for_project = employeedetails.get(
						REPORTING_MANAGER_ID_VARIABLE).toString();
				displayStatusTouser = employeedetails.get(
						REPORTING_MANAGER_NAME_VARIABLE).toString()
						+ "(" + manager_id_for_project + ")";
			}
		} else if (CreateHelpdeskBean.getEmpProject().equalsIgnoreCase("0")) {
			manager_id_for_project = employeedetails.get(
					REPORTING_MANAGER_ID_VARIABLE).toString();
			displayStatusTouser = employeedetails.get(
					REPORTING_MANAGER_NAME_VARIABLE).toString()
					+ "(" + manager_id_for_project + ")";
		} 
	// If Project Manager creates ticket then RO is considered as Approving Manager.
	if(Requestedby.equals(manager_id_for_project)){
		manager_id_for_project = employeedetails.get(
				REPORTING_MANAGER_ID_VARIABLE).toString();
		displayStatusTouser = employeedetails.get(
				REPORTING_MANAGER_NAME_VARIABLE).toString()
				+ "(" + manager_id_for_project + ")";
	}
		
		
		displayStatusTouser = ". Approver : " + displayStatusTouser;

		insertdetails.put(REQUESTED_BY_VARIABLE, Requestedby);
		insertdetails.put(ON_BEHALF_OF_VARIABLE, onbehalfof);
		insertdetails.put(CATEGORYID_FOR_ECT_VARIABLE, categoryName);
		insertdetails.put(CURRENT_STATE_VARIABLE, currentstate);
		insertdetails.put(FUNCTION_ID_VARIABLE, functionid);
		insertdetails.put(CATEGORY_ID_VARIABLE, catid);
		insertdetails.put(SUB_CATEGORY_ID_VARIABLE, subcatid);
		insertdetails.put(LOCATION_ID_VARIABLE, Integer.toString(locationID));
		insertdetails.put(CUBICAL_CODE_VARIABLE, cubicalCode);
		insertdetails.put(CONTACT_NO_VARIABLE, phonenumber);
		insertdetails.put(ALTERNATE_CONTACT_NO_VARIABLE, AltContactNumber);
		insertdetails.put(SUBJECT_VARIABLE, converter
				.replaceSpecialChars(CreateHelpdeskBean.getSubject()));
		insertdetails.put(DESCRIPTION_VARIABLE, converter
				.replaceSpecialChars(CreateHelpdeskBean.getDescription()));
		insertdetails.put(CC_EMAIL_ID_VARIABLE, CreateHelpdeskBean
				.getCCEmailID());
		insertdetails.put(PROJECT_ID_VARIABLE, projectId);
		insertdetails.put(PRIORITY_ID_VARIABLE, priorityID);
		insertdetails.put(SUB_PROJECT_ID_VARIABLE, CreateHelpdeskBean.getEmplevelProject());
		insertdetails.put(WORKFLOW_ID_VARIABLE, workflowid);
		
		//Approver sending back to Helpdesk queue then group is displaying
		if(APPROVER_ID==0){
			insertdetails.put(GROUP_ID_VARIABLE, groupID);
			}else{
				insertdetails.put(GROUP_ID_VARIABLE, 0);
			}
		insertdetails.put(ECT_VARIABLE, ECT);
		insertdetails.put(CREATED_BY_VARIABLE, createdby);
		insertdetails.put(IMPACTED_USERS_VARIABLE, impactedusers);
		insertdetails.put(PROBLEM_FILE_DATA_VARIABLE, CreateHelpdeskBean
				.getProblemfile());
		insertdetails.put(APPROVAL_FILE_DATA_VARIABLE, CreateHelpdeskBean
				.getApprovalfile());
		insertdetails.put(CREATED_DATE_VARIABLE, createddate);
		insertdetails.put(APPROVER_ID_VARIABLE, APPROVER_ID);
		insertdetails.put(PROBLEM_ID_VARIABLE, problemID);
		insertdetails.put(IS_APPROVAL_VARIABLE, isApprovalAttachmentavialble);
		insertdetails.put(AUDIT_DETAILS_VARIABLE, auditlogdetails);
		insertdetails.put(SOURCE_VARIABLE, CreateHelpdeskBean.getSource());
		insertdetails.put(MAIL_ID_VARIABLE, CreateHelpdeskBean.getMailid());
		insertdetails.put(MAIL_TRACKER_TYPE_VARIABLE, CreateHelpdeskBean
				.getMailTrackerType());
		insertdetails.put(MANAGER_ID_VARIABLE, manager_id_for_project);
		insertdetails.put(LOC_DET_ID_VARIABLE, Floor);
		insertdetails.put(OS_DETAILS_VARIABLE, CreateHelpdeskBean
				.getOsdetails());
		insertdetails.put(BROWSER_DETAILS_VARIABLE, CreateHelpdeskBean
				.getBrowserdetails());

		String result = HelpDeskImpl.createHelpDeskTicket(insertdetails);

		JSONObject jsonforSuccess = new JSONObject();
		if (result.contains(SUCCESS_VARIABLE)) {
			jsonforSuccess.put(STATUS_VARIABLE, SUCCESS_VARIABLE);
			jsonforSuccess.put(TICKET_ID_VARIABLE, result.substring(result
					.indexOf("-") + 1, result.length()));
			if ((!currentstate.equalsIgnoreCase(HELPDESK_QUEUE_STATE_VARIABLE))
					&& (!currentstate.equalsIgnoreCase(OPEN_STATE_VARIABLE))
					&& (!currentstate.equalsIgnoreCase(ASSIGNED_STATE_VARIABLE))) {
				jsonforSuccess.put(CURRENT_STATE_VARIABLE, currentstate);
			}
			if (!isChangeRequest) {
				jsonforSuccess.put(ECT_VARIABLE, CustomDateFormatConstants
						.convertToEST(ECT));
			}
		}
		JsonUtility.writedata(jsonforSuccess.toString(), response);
}
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Nov 15, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
