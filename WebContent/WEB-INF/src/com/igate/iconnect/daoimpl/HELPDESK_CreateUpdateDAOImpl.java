/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.daoimpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.igate.iconnect.BO.HELPDESK_Attachment;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.constants.MAILTARCKER_QueryConstants;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.util.COMMON_ExecuteProcedure;
import com.igate.iconnect.util.HELPDESK_FileUpload;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.MapToStringUtil;

@Transactional(rollbackFor = Exception.class)
public class HELPDESK_CreateUpdateDAOImpl implements HELPDESK_CreateUpdateDAO {

	private COMMON_ExecuteProcedure DAOUtilObj;
	private JdbcTemplate jdbcTemplate;
	private static Logger log = Logger
			.getLogger(HELPDESK_CreateUpdateDAOImpl.class);
	private static final String AUDITLOG_DETAILS = "auditlogdetails";
	private static final String PROBLEM_FILE_DATA = "problemfiledata";
	private static final String APPROVAL_FILE_DATA = "approvalfiledata";
	private static final String IA_APPROVAL_THERE = "isapprovalthere";
	private static final String PROBLEM_ID = "PROBLEM_ID";
	private static final String REQUESTED_BY = "Requestedby";
	private static final String CREATEDDATE = "createddate";
	private static final String APPROVER_ID = "APPROVER_ID";
	private static final String FUNCTION_ID = "FUNCTION_ID";
	private static final String PRIORITYID = "PRIORITY_ID";
	private static final String PROBLEM_FILE = "problemfile";

	private static final String APPROVAL_FILE = "approvalfile";
	private static final String GROUP_ID = "GROUP_ID";
	private static final String CURRENTSTATE = "currentstate";
	private static final String CREATEDBY = "createdby";
	private static final String REFERENCE_ID = "reference_id";
	private static final String MENU_ID = "menu_id";
	private static final String CATEGORY_ID = "category_id";
	private static final String PRIORITY_ID = "priority_id";
	private static final String ASSIGNED_GROUP = "assigned_group";
	private static final String CURRENT_STATE = "current_state";
	private static final String ACTION = "action";
	private static final String CHANGED_BY = "changed_by";
	private static final String CHANGED_DATE = "changed_date";
	private static final String FIELD_VALUE = "field_value";
	private static final String WORKFLOW_ID = "workflowid";
	private static final String PREV_STATE = "prev_state";
	private static final String MAIL_TRACKER_TYPE = "mailTrackerType";
	private static final String MAILID = "mailid";
	private static final String OS_DETAILS = "osdetails";
	private static final String BROWSER_DETAILS = "browserdetails";
	private static final String IMPACTED_USERS = "impactedusers";
	private static final String ON_BEHALF_OF = "ON_BEHALF_OF";
	private static final String CATEGORYID = "CATEGORY_ID";
	private static final String LOCATION_ID = "LOCATION_ID";
	private static final String CUBICLE_CODE = "cubicalCode";
	private static final String PHONE_NUMBER = "CONTACT_NO";
	private static final String ALT_CONTACT_NUMBER = "ALTERNATE_CONTACT_NO";
	private static final String SUBJECT = "SUBJECT";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String PROJECT_ID = "PROJECT_ID";
	private static final String SUB_PROJECT_ID_VARIABLE = "SUB_PROJECT_ID";
	private static final String CC_EMAIL_ID = "ccemailid";
	private static final String ECT = "ECT";
	private static final String SUB_CATEGORY_ID = "SUB_CATEGORY_ID";
	private static final String SOURCE = "source";
	private static final String MANAGER_ID = "MANAGER_ID";
	private static final String LOC_DET_ID = "loc_det_id";
	/* private static final String FTP_PROPERTY_FILE="ftpPropertyFile"; */
	private static final String ATTACHMENT_PATH = "ATTACHMENT_PATH";
	private static final String ATTACHMENT_NAME = "ATTACHMENT_NAME";
	private static final String NAME = "NAME";
	private static final String VERIFIED_BY = "VERIFIED_BY";
	private static final String WORKFLOW_STATE = "WORKFLOW_STATE";
	private static final String FIELD_ID = "FIELD_ID";
	private static final String FIELD_VALU = "FIELD_VALUE";
	// Added for Web Services
	private static final String ADDITIONAL_INFO_VARIABLE = "ADDITIONAL_INFO";
	private static final String ASSIGNED_TO = "ASSIGNED_TO";
	private static final String IS_AUTOMATION_REQUIRED = "IS_AUTOMATION_REQUIRED";
	private static final String IS_ORCH_REQUIRED = "IS_ORCH_REQUIRED";
	
	HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter();
	

	//anjana
	private static final String ORCH_VALUE = "ORCH_VALUE";

	private static final String argName[] = { REFERENCE_ID, MENU_ID,
			CATEGORY_ID, PRIORITY_ID, ASSIGNED_GROUP, CURRENT_STATE, ACTION,
			CHANGED_BY, CHANGED_DATE, FIELD_VALUE, PREV_STATE };
	private static final int argType[] = { Types.VARCHAR, Types.SMALLINT,
			Types.VARCHAR, Types.SMALLINT, Types.VARCHAR, Types.VARCHAR,
			Types.CHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR,
			Types.VARCHAR };

	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setiConnectDAOUtil(COMMON_ExecuteProcedure dAOUtilObj) {
		DAOUtilObj = dAOUtilObj;
	}

	public String createHelpDeskTicket(HashMap<String, Object> insertdetails)
			throws IOException,Exception {

		Map<String, Object> auditlog = (HashMap<String, Object>) insertdetails
				.get(AUDITLOG_DETAILS);
		String ticketid = insertHelpDeskTicket(insertdetails);
		String currentStatus=insertdetails.get(CURRENTSTATE).toString();
		insertFunctionSpecificDetails(insertdetails, ticketid);
		
	
		
		String problemattachmentPath = "";
		String approvalattachmentPath = "";
		String attachtype = "Problem";
		HELPDESK_FileUpload fileUpload = new HELPDESK_FileUpload();
		MultipartFile problemfile = (MultipartFile) insertdetails
				.get(PROBLEM_FILE_DATA);
		MultipartFile approvalfile = (MultipartFile) insertdetails
				.get(APPROVAL_FILE_DATA);
		// null!=problemfile condition has been added for Web Services
		if (null != problemfile && !problemfile.isEmpty()) {
			auditlog.put(PROBLEM_FILE, converter.replaceSpecialChars(problemfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")));
			problemattachmentPath = fileUpload.uploadFileForHelpdesk(
					problemfile, ticketid, attachtype);
		}
		if ((Boolean) insertdetails.get(IA_APPROVAL_THERE)
				&& !approvalfile.isEmpty()) {
			attachtype = "approval";
			auditlog.put(APPROVAL_FILE, converter.replaceSpecialChars(approvalfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")));
			approvalattachmentPath = fileUpload.uploadFileForHelpdesk(
					approvalfile, ticketid, attachtype);
		}
		if (!(Boolean) insertdetails.get(IA_APPROVAL_THERE)) {
			approvalattachmentPath = "";
		}
		if ((problemattachmentPath != null && !""
				.equalsIgnoreCase(problemattachmentPath))
				|| (approvalattachmentPath != null && !""
						.equalsIgnoreCase(approvalattachmentPath))) {
			ArrayList<Object> ArgsList = new ArrayList<Object>();
			if (problemattachmentPath != null
					&& !problemattachmentPath.equals("")) {
				Object[] GenArgs = new Object[] { ticketid,
						insertdetails.get(PROBLEM_ID),
						converter.replaceSpecialChars(problemfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")),
						problemattachmentPath,
						(String) insertdetails.get(REQUESTED_BY),
						(String) insertdetails.get(CREATEDDATE) };
				ArgsList.add(0, GenArgs);
			}
			if (!approvalattachmentPath.equals("")
					&& approvalattachmentPath != null) {
				Object[] GenArgs = new Object[] { ticketid,
						(Integer) insertdetails.get(APPROVER_ID),
						converter.replaceSpecialChars(approvalfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")),
						approvalattachmentPath,
						(String) insertdetails.get(REQUESTED_BY),
						(String) insertdetails.get(CREATEDDATE) };
				ArgsList.add(0, GenArgs);
			}

			updateHelpDeskTicketAttachment(ArgsList);
		}
		String procName = "insert_audit_log";
		/*
		 * String argName[] = { REFERENCE_ID, MENU_ID, CATEGORY_ID, PRIORITY_ID,
		 * ASSIGNED_GROUP, CURRENT_STATE, ACTION, CHANGED_BY, CHANGED_DATE,
		 * FIELD_VALUE, PREV_STATE }; int argType[] = { Types.VARCHAR,
		 * Types.SMALLINT, Types.SMALLINT, Types.SMALLINT, Types.SMALLINT,
		 * Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.TIMESTAMP,
		 * Types.VARCHAR, Types.VARCHAR };
		 */
		String paramVal[] = { ticketid, "1",
				(String) insertdetails.get(FUNCTION_ID),
				Integer.toString((Integer) insertdetails.get(PRIORITYID)),
				Integer.toString((Integer) insertdetails.get(GROUP_ID)),
				(String) insertdetails.get(CURRENTSTATE), "I",
				(String) insertdetails.get(CREATEDBY),
				(String) insertdetails.get(CREATEDDATE),
				MapToStringUtil.getStringForMap(auditlog), "" };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

		/*
		 * Changed By : 714599 Comments : Below variable mailtrackerType is used
		 * to refer the Differentiate the table like when HR Executive/Manager
		 * tries to access the Mail the details will be coming from
		 * IC_HR_MAIL_TRACKER_DETAILS else if any level0 executive tries to
		 * access details will come from IC_MAIL_TRACKER_DETAILS
		 */
		// !insertdetails.get(SOURCE).toString().equalsIgnoreCase("iTrack")
		// condition has been added for Web Services
		if (!insertdetails.get(SOURCE).toString().equalsIgnoreCase("iTrack")) {
			String mailTrackerType = (String) insertdetails
					.get(MAIL_TRACKER_TYPE);
			if (mailTrackerType.equalsIgnoreCase("HR")) {
				updateHRMailTrackerDetails((String) insertdetails.get(MAILID),
						ticketid);
			} else if (mailTrackerType.equalsIgnoreCase("HelpDesk")) {
				deleteMailTrackerDetails((String) insertdetails.get(MAILID),
						ticketid);
			}
			removeMailLock((String) insertdetails.get(MAILID));
			insertUserOSBrowserDetails((String) insertdetails.get(CREATEDBY),
					ticketid, (String) insertdetails.get(CREATEDDATE),
					(String) insertdetails.get(OS_DETAILS),
					(String) insertdetails.get(BROWSER_DETAILS));
		}

		return "SUCCESS_"+currentStatus+"-"+ticketid;
	}

	private void insertOrchTransitionValues(String orchJson, String ticketid, HashMap<String, Object> insertdetails)  {
		try {
			log.info("start insertOrchTransitionValues");
			JSONObject jsonobj = new JSONObject(orchJson);
			JSONArray jArray = jsonobj.getJSONArray("details");
			for(int i =0; i <jArray.length() ;i++)
			{
				JSONObject jsonobjOrch = new JSONObject(jArray.getString(i).toString());
				String l_fieldId = jsonobjOrch.get("FIELD_ID").toString();
				String l_fieldValue = jsonobjOrch.get("FIELD_VALUE").toString();
				String l_fieldName = jsonobjOrch.get("FIELD_NAME").toString();
				if(l_fieldName.equalsIgnoreCase("Ticket Id"))
				{
					l_fieldValue = ticketid;
				}
				if(l_fieldName.equalsIgnoreCase("Printer Server"))
				{
					l_fieldValue = insertdetails.get("PRINTER_SERVER").toString();
				}
				insertOrchValues(l_fieldId,l_fieldValue,ticketid,insertdetails);
				
			}
			log.info("insertOrchTransitionValues end ");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		log.info("insertOrchTransitionValues end ");
		
	}

	private void insertOrchValues(String f_fieldId, String f_fieldValue, String ticketid, HashMap<String, Object> insertdetails) {
		if (f_fieldId != null
				&& f_fieldValue != "") {
			try {
			this.jdbcTemplate
					.update(
							HELPDESK_SQLQueryConstants.IC_ORCH_TRANSACTION_DETAILS,
							ticketid, f_fieldId,
							f_fieldValue,
							(String) insertdetails.get("createdby"),
							(String) insertdetails.get(CREATEDDATE));
			
			  } catch (Exception e) {
			  
				  log
				  .error(
			  "Error in inserting orchestration for ticketid." +
			  ticketid + "  ." + e); e.printStackTrace();
			  
			  log
			  .error("Error in inserting orchestration fields for ticketid : "
			  + e); }
			 
		}
	}
		
	public String insertHelpDeskTicket(
			final HashMap<String, Object> insertdetails) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		// try {

		final int priorityid = (Integer) insertdetails.get(PRIORITYID);
		final int workflowid = (Integer) insertdetails.get(WORKFLOW_ID);
		final int groupid = (Integer) insertdetails.get(GROUP_ID);
		final int usersimpact = (Integer) insertdetails.get(IMPACTED_USERS);
			
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = null;
				if (insertdetails.get(SOURCE).toString().equalsIgnoreCase("iTrack")) {
					 ps = connection.prepareStatement(HELPDESK_SQLQueryConstants.IC_HD_CREATE_TICKET_ITRACK,
							new String[] { "TICKET_ID" });
				}else{
					 ps = connection.prepareStatement(HELPDESK_SQLQueryConstants.IC_HD_CREATE_TICKET,
								new String[] { "TICKET_ID" });
				}
				ps.setString(1, (String) insertdetails.get(REQUESTED_BY));
				ps.setObject(2, insertdetails.get(ON_BEHALF_OF));
				ps.setObject(3, insertdetails.get(CATEGORYID));
				ps.setObject(4, insertdetails.get(LOCATION_ID));
				ps.setString(5, (String) insertdetails.get(CUBICLE_CODE));
				ps.setString(6, (String) insertdetails.get(PHONE_NUMBER));
				ps.setString(7, (String) insertdetails.get(ALT_CONTACT_NUMBER));
				ps.setString(8, (String) insertdetails.get(SUBJECT));
				ps.setString(9, (String) insertdetails.get(DESCRIPTION));
				ps.setString(10, (String) insertdetails.get(PROJECT_ID));
				ps.setString(11, (String) insertdetails.get(CC_EMAIL_ID));
				ps.setInt(12, priorityid);
				ps.setInt(13, workflowid);

				if (groupid == 0) {
					ps.setObject(14, null);
				} else {
					ps.setInt(14, groupid);
				}
				ps.setObject(15, insertdetails.get(ECT));
				ps.setString(16, "NA");
				ps.setString(17, (String) insertdetails.get(CREATEDBY));
				ps.setObject(18, insertdetails.get(CREATEDDATE));
				ps.setInt(19, usersimpact);
				ps.setObject(20, insertdetails.get(FUNCTION_ID));
				ps.setObject(21, insertdetails.get(SUB_CATEGORY_ID));
				ps.setInt(22, 1);
				ps.setString(23, (String) insertdetails.get(SOURCE));
				ps.setObject(24, insertdetails.get(MANAGER_ID));
				ps.setObject(25, insertdetails.get(LOC_DET_ID));
				ps.setObject(26, insertdetails.get(SUB_PROJECT_ID_VARIABLE));
				if (insertdetails.get(SOURCE).toString().equalsIgnoreCase("iTrack")) {
					ps.setObject(27, insertdetails.get(ADDITIONAL_INFO_VARIABLE));		
					ps.setObject(28, insertdetails.get(ASSIGNED_TO));	
					//L2 1184 Start
					ps.setString(29, (String) insertdetails.get(IS_AUTOMATION_REQUIRED));
					ps.setBoolean(30, (Boolean) insertdetails.get(IS_ORCH_REQUIRED));
					//L2 1184 End
				}
				return ps;
			}
		}, keyHolder); 

		if (insertdetails.containsKey("IS_EX_EMPLOYEE")) {
			String exEmployeeActiveValue = (String) insertdetails
					.get("IS_EX_EMPLOYEE");
			if (exEmployeeActiveValue != null) {
				String updateSQL = HELPDESK_SQLQueryConstants.IC_UPDATE_EX_EMP_CHECK;
				if (keyHolder.getKey().toString() != null) {
					this.jdbcTemplate.update(updateSQL, new Object[] {
							exEmployeeActiveValue,
							keyHolder.getKey().toString() });
				}
			}
		}
		return keyHolder.getKey().toString();
	}

	public int updateHelpDeskTicketAttachment(ArrayList<Object> ArgsList) {

		int result = 0;
		int ArgTypes[] = { Types.INTEGER, Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
		final String sql = HELPDESK_SQLQueryConstants.IC_HD_INSERT_ATTACHMENTS;

		DAOUtilObj.executeBatchUpdation(sql, ArgsList, ArgTypes, jdbcTemplate
				.getDataSource());

		return result;
	}

	private int deleteMailTrackerDetails(String mailid, String ticketid) {
		int successval = 0;
		if (!mailid.equalsIgnoreCase("")) {
			successval = jdbcTemplate.update(
					MAILTARCKER_QueryConstants.IC_IHD_MAIL_TRACKER_UPDATE,
					ticketid, mailid);
		}
		return successval;
	}

	private int updateHRMailTrackerDetails(String mailid, String ticketid) {
		int successval = 0;
		if (!mailid.equalsIgnoreCase("")) {
			successval = jdbcTemplate.update(
					MAILTARCKER_QueryConstants.IC_IHD_HR_MAIL_TRACKER_UPDATE,
					ticketid, mailid);
		}
		return successval;
	}


	public Map<String, Object> getEmployeeDetails(String empId) {

		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_EMPLOYEE_DETAILS_SQL, empId);

	}

	// Added to implement the tool to track EX employees quereis for HR function
	public Map<String, Object> getEmployeeDetails_Ex(String empId) {

		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_EMPLOYEE_DETAILS_EX_SQL, empId);

	}

	public List<Map<String, Object>> getProjectsForEmployee(String empId) {

		return this.jdbcTemplate.queryForList(
				HELPDESK_SQLQueryConstants.IC_PROJECTS_FOR_EMPLOYEE_SQL, empId);
	}

	public void updateAttachmentTicketDetails(
			HELPDESK_Attachment attachmentbean, int validState,
			String fileName, String approvalattachmetnpath, String ticketid,
			String referenceid, String verifiedby) {
		this.jdbcTemplate
				.update(
						HELPDESK_SQLQueryConstants.IC_IHD_Attachment_Ticket_Details_UPDATE,
						fileName, approvalattachmetnpath, verifiedby, ticketid,
						referenceid);

		List<String> WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { validState }, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		List<String> OLD_WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { attachmentbean.getWORKFLOW_STATE() },
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		HashMap<String, Object> newvalues = new HashMap<String, Object>();
		newvalues.put(VERIFIED_BY, verifiedby);
		newvalues.put(WORKFLOW_STATE, WORKFLOWNAME.get(0));
		newvalues.put(ATTACHMENT_NAME, fileName);
		newvalues.put(ATTACHMENT_PATH, approvalattachmetnpath);

		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String created_date = dateFormatGmt.format(new Date());
		String procName = "insert_audit_log";
		String paramVal[] = { ticketid, "1", "0", "0", "0",
				WORKFLOWNAME.get(0), "U", verifiedby, created_date, null,
				OLD_WORKFLOWNAME.get(0) };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

	}

	public void updateAttachmentDetails(int validState,
			HELPDESK_Attachment attachmentbean, String ticketid,
			String referenceid, String verifiedby) {
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String created_date = dateFormatGmt.format(new Date());
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_IHD_Attachment_Details_UPDATE,
				verifiedby, ticketid, referenceid);

		List<String> WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { validState }, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		List<String> OLD_WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { attachmentbean.getWORKFLOW_STATE() },
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		HashMap<String, Object> newvalues = new HashMap<String, Object>();
		newvalues.put("VERIFIED_BY", verifiedby);
		newvalues.put("WORKFLOW_STATE", WORKFLOWNAME.get(0));

		String procName = "insert_audit_log";
		String argName[] = { REFERENCE_ID, MENU_ID, CATEGORY_ID, PRIORITY_ID,
				ASSIGNED_GROUP, CURRENT_STATE, ACTION, CHANGED_BY,
				CHANGED_DATE, FIELD_VALUE, PREV_STATE };
		int argType[] = { Types.VARCHAR, Types.SMALLINT, Types.VARCHAR,
				Types.SMALLINT, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR };
		String paramVal[] = { ticketid, "1", "0", "0", "0",
				WORKFLOWNAME.get(0), "U", verifiedby, created_date, null,
				OLD_WORKFLOWNAME.get(0) };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

	}

	public void updateTicketStatus(int validState, String ticketid, String ECT) {
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_UPDATE_TICKET_STATUS, validState,
				ECT, ticketid);
	}

	public int getWorkflowState(String workflowID) {
		return this.jdbcTemplate.queryForInt(
				HELPDESK_SQLQueryConstants.SELECT_WORKFLOW_STATE, workflowID);
	}


	public void insertAttachmentDetails(HELPDESK_Attachment attachmentbean,
			int validState, String fileName, String approvalattachmetnpath,
			String ticketid, String referenceid, String verifiedby) {
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String created_date = dateFormatGmt.format(new Date());
		// try {
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.INSERT_TICKET_ATTACHMENT, ticketid,
				referenceid, approvalattachmetnpath, fileName, verifiedby,
				verifiedby, created_date);

		List<String> WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { validState }, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		List<String> OLD_WORKFLOWNAME = this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_WORKFLOWNAME,
				new Object[] { attachmentbean.getWORKFLOW_STATE() },
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int count)
							throws SQLException {
						return rs.getString(NAME);
					}
				});
		HashMap<String, Object> newvalues = new HashMap<String, Object>();
		newvalues.put("VERIFIED_BY", verifiedby);
		newvalues.put("WORKFLOW_STATE", WORKFLOWNAME.get(0));
		newvalues.put("ATTACHMENT_NAME", fileName);
		newvalues.put(ATTACHMENT_PATH, approvalattachmetnpath);

		String procName = "insert_audit_log";
		String paramVal[] = { ticketid, "1", "0", "0", "0",
				WORKFLOWNAME.get(0), "U", verifiedby, created_date, null,
				OLD_WORKFLOWNAME.get(0) };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());
	}

	public void insertSimpleAttachment(String ticketid, String referenceid,
			String approvalattachmetnpath, String fileName, String verifiedby) {
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String created_date = dateFormatGmt.format(new Date());
		// try {
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.INSERT_TICKET_ATTACHMENT, ticketid,
				referenceid, approvalattachmetnpath, fileName, verifiedby,
				verifiedby, created_date);
	}

	private void insertUserOSBrowserDetails(String employeeID, String ticketID,
			String createdDate, String osdetails, String browserdetails) {

		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		String OsBrowserDetailsNeedToBeSaved = bundle
				.getString("OsBrowserDetailsNeedToBeSaved");
		if (OsBrowserDetailsNeedToBeSaved.equalsIgnoreCase("true")) {
			// try {
			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_USER_SYSTEM_DETAILS,
					employeeID, ticketID, "I", createdDate, osdetails,
					browserdetails);
		}
	}

	private void insertFunctionSpecificDetails(
			Map<String, Object> insertdetails, String ticketid) {

		if (insertdetails.get(FIELD_ID) != null
				&& insertdetails.get(FIELD_ID) != "") {
			this.jdbcTemplate
					.update(
							HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS,
							ticketid, (String) insertdetails.get(FUNCTION_ID),
							(String) insertdetails.get(FIELD_ID),
							(String) insertdetails.get(FIELD_VALU),
							(String) insertdetails.get(REQUESTED_BY),
							(String) insertdetails.get(CREATEDDATE));
		}
	}

	public void removeMailLock(String mailId) {
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
				new Object[] { mailId });
	}

	public void updateTicketApprovalDetails(String ticketid,
			String verifiedby) {
		this.jdbcTemplate
				.update(
						HELPDESK_SQLQueryConstants.INSERT_TICKET_APPROVAL_DETAILS_FOR_PMRO,
						ticketid, "1", "Approved", "Approved",verifiedby);

	}
	
	//Added for iconnect - iTrack integartion uninstallation request creation
	
	public Map<String, Object> getInActiveEmployeeDetails(String empId) {

		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_INACTIVE_EMPLOYEE_DETAILS_SQL, empId);

	}
	

	
	public Map<String, Object> getProjectDetails(String createdby,String projectID, String subProjectID) {

		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_PROJECT_DETAILS,
				new Object[] { createdby,projectID,subProjectID });
	}
	
	public Map<String, Object> getMasterProjectDetails(String empProject){
		return this.jdbcTemplate.queryForMap(HELPDESK_SQLQueryConstants.IC_PROJECT_MASTER_DETAILS,empProject);
	}
	
	
	public List<Map<String, Object>> getCubiclecodeByLocID(String locID,String cubicleCode) {
		String filterStr = "%" + cubicleCode + "%";
		return this.jdbcTemplate.queryForList(
				HELPDESK_SQLQueryConstants.GET_ALL_AVILABLE_CUBICLE_CODE,locID,filterStr);
	}
	
	public List<Map<String, Object>> getEmployeeMappedOrNOTOnLoad(String empId){
		List<Map<String, Object>> empMappedInfoList=new ArrayList<Map<String,Object>>();
		try{
			empMappedInfoList=this.jdbcTemplate.queryForList(HELPDESK_SQLQueryConstants.IC_SU_ONLOAD_EMP_MAPPING_OR_NOT,empId);
		}catch(EmptyResultDataAccessException ae){
			return empMappedInfoList;
		}
		return empMappedInfoList;
	}
	
	public void insertFloorEmpCCMapping(String cubicalCode,String empId, int locationdetID,String projectId	) throws SQLException {
		jdbcTemplate.update(HELPDESK_SQLQueryConstants.IC_SU_INSERT_EMP_MAPPING,new Object[]{cubicalCode,empId,locationdetID,projectId});
	
	}

	public Map<String, Object> getCubicalCodeWithCCFormat(String cityCC,String locCC,String buildingCC,String floorCC,String wingCC){
		return this.jdbcTemplate.queryForMap(HELPDESK_SQLQueryConstants.IC_SU_LOCATION_CCODE_FORMAT,cityCC,locCC,buildingCC,floorCC,wingCC);
	}

	public int checkValidCubicCode(String cubicalcode) {
		int isValidCubicCode = 0;
		isValidCubicCode = jdbcTemplate.queryForInt(HELPDESK_SQLQueryConstants.IC_CHECK_VALID_CUBICCODE,cubicalcode);
		
		return isValidCubicCode;
	}
	
	public Map<String,Object> getAssigedToName(String assignedTo){
		
		
		return this.jdbcTemplate.queryForMap(HELPDESK_SQLQueryConstants.IC_USER_DETAILS,assignedTo);
	}

	public int getOrchOdcId(String empProject,int l_subcategoryId){
		try {
			return this.jdbcTemplate.queryForInt(HELPDESK_SQLQueryConstants.IC_ORCH_ODC_ID,empProject,l_subcategoryId);
		} catch (DataAccessException e) {
			return 1;
		}
	}
	
	public List<Map<String, Object>> getCatgPriority(String catId) {

		return this.jdbcTemplate.queryForList(
				HELPDESK_SQLQueryConstants.IC_CATEGORY_PRIORITY_LIST, catId);
	}
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 13, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/