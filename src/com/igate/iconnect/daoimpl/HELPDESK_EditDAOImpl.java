/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.HELPDESK_Approval;
import com.igate.iconnect.BO.HELPDESK_AssetInformation;
import com.igate.iconnect.BO.HELPDESK_AssetTab;
import com.igate.iconnect.BO.HELPDESK_RequestorInformation;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ExecuteProcedure;
import com.igate.iconnect.util.HELPDESK_ECTPopulator;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.MapToStringUtil;

@Transactional(rollbackFor = Exception.class)
public class HELPDESK_EditDAOImpl implements HELPDESK_EditDAO {

	private JdbcTemplate jdbcTemplate;
	MapToStringUtil MapToStringUtilobj;
	COMMON_ExecuteProcedure DAOUtilObj;
	private static Logger log = Logger.getLogger(HELPDESK_EditDAOImpl.class);
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
	private static final String PREV_STATE = "prev_state";
	private static final String TICKET_ID = "TICKET_ID";
	private static final String FUNCTION_ID = "functionId";
	private static final String PRIORIT_ID = "PRIORITY_ID";
	private static final String ASSIGNED_GRP = "ASSIGNED_GROUP";
	private static final String WORKFLOW_STATE = "WORKFLOW_STATE";
	private static final String ExpCT = "ECT";
	private static final String CREATEDBY = "createdby";
	private static final String CREATEDDATE = "createddate";
	private static final String AUDIT_LOG_DETAILS = "auditlogdetails";
	private static final String OS_DETAILS = "osdetails";
	private static final String BROWSER_DETAILS = "browserdetails";
	private static final String ARG_VALUES = "argvalues";
	private static final String QUERY = "query";
	private static final String MENU_IDE = "MENU_ID";
	private static final String DATA = "DATA";
	private static final String STATE = "status";
	private static final String ERROR = "error";
	private static final String APPROVER_ID = "APPROVER_ID";
	private static final String APPROVER_NAME = "APPROVER_NAME";
	private static final String COMMENTS = "COMMENTS";
	private static final String IS_EXCEPTIONAL_APPROVAL = "IS_EXCEPTIONAL_APPROVAL";
	private static final String CREATED_BY = "CREATED_BY";
	private static final String CREATED_NAME = "CREATED_NAME";
	private static final String CREATED_DATE = "CREATED_DATE";
	private static final String MODIFIED_BY = "MODIFIED_BY";
	private static final String MODIFIED_DATE = "MODIFIED_DATE";
	private static final String STATUS = "STATUS";
	private static final String EMPLOYEE_ID = "EMPLOYEE_ID";
	private static final String EMP_NAME = "EMP_NAME";
	private static final String ORGANIZATION = "ORGANIZATION";
	private static final String iGATE = "IGATE";
	private static final String patni = "patni";
	private static final String REPORTING_MANAGER_ID = "REPORTING_MANAGER_ID";
	private static final String REPORTING_MANAGER_NAME = "REPORTING_MANAGER_NAME";
	private static final String DU_ID = "DU_ID";
	private static final String LOCATION_ID = "LOCATION_ID";
	private static final String COUNTRY = "COUNTRY";
	private static final String CITY = "CITY";
	private static final String AREA = "AREA";
	private static final String SHORT_NAME = "SHORT_NAME";
	private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
	private static final String SUB_CATEGORY_ID = "SUB_CATEGORY_ID";
	private static final String TICKETID = "TicketId";
	private static final String ACTIONS = "Action";
	private static final String CREATEDDBY = "CreatedBy";
	private static final String CREATEDD_DATE = "CreatedDate";
	private static final String STATUSS = "Status";
	private static final String ADDITIONAL_INFO = "ADDITIONAL_INFO";
	private static final String LOGIN_ID = "LOGIN_ID";
	private static final String ACTTION = "ACTION";
	private static final String DU_NAME = "DU_NAME";
	private static final String APPROVED_DATE = "Approved Date";
	private static final String CHANGE_REQUEST = "Change Request";
	private static final String TICKET_IDE = "Ticket#";
	private static final String SUBJECT = "Subject";
	private static final String DESCRIPTION = "Description";
	private static final String CATEGORY_IDE = "CategoryID";
	private static final String REQUESTOR_NAME = "Requestor Name";
	private static final String CREATOR_NAME = "Creator Name";
	private static final String CATEGORY_NAME = "Category Name";
	private static final String FUNCTION_IDE = "FunctionID";
	private static final String SUB_CATEGRY_ID = "SubCategoryID";
	private static final String FUNCTION_NAME = "Function Name";
	private static final String SUB_CATEGORY_NAME = "SubCategory Name";
	private static final String CC_TO = "CC To";
	private static final String SEVERITY_ID = "Severity ID";
	private static final String SOURCE = "Source";
	private static final String PRIORITY_IDE = "Priority ID";
	private static final String GROUP_ID = "GroupID";
	private static final String ASSIGND_GROUP = "Assigned Group";
	private static final String ASSIGND_TO = "Assigned To";
	private static final String CREATION_DATE = "Creation Date";
	private static final String SLA_STATUS = "SLA Status";
	private static final String LOCATION_IDE = "LocationID";
	private static final String COUNTRY_IDE = "Country";
	private static final String CITY_IDE = "City";
	private static final String AREA_IDE = "Area";
	private static final String LOCATION = "Location";
	private static final String ON_BEHALF_OF_IDE = "On Behalf Of(EmployeeID)";
	private static final String CREATD_BY = "Created By";
	private static final String CONTACT_NUMBER = "Contact Number(Primary)";
	private static final String ALTERNATE_CONTACT_NUMBER = "Alternate Contact Number";
	private static final String IMPACTED_USERS = "Impacted Users";
	private static final String ADDITIONL_INFO = "Additional Info";
	private static final String RESOLUTION_COMMENTS = "Resolution Comments";
	private static final String PROJECT_ID = "PROJECT_ID";
	private static final String PROJECT_NAME = "PROJECT_NAME";
	private static final String OUT_OF_SLA_REASON = "OUT_OF_SLA_REASON";
	private static final String OUT_OF_SLA_COMMENTS = "OUT_OF_SLA_COMMENTS";
	private static final String CLOSED_DATE = "CLOSED_DATE";
	private static final String SUSPENDED_TILL = "SUSPENDED_TILL";
	private static final String SUSPENDED_COMMENTS = "SUSPENDED_COMMENTS";
	private static final String ONHOLD_COMMNTS = "ONHOLD_COMMENTS";
	private static final String REMINDER_DATE = "REMINDER_DATE";
	private static final String FEEDBACK = "FEEDBACK";
	private static final String REOPEN_COMMNTS = "REOPEN_COMMENTS";
	private static final String INVOICE_NO = "INVOICE_NO";
	private static final String VENDOR_NAME = "VENDOR_NAME";
	private static final String VERSION_NO = "VERSION_NO";
	private static final String FCS = "FCS";
	private static final String CUBICLE_CODE = "CubicalCode";
	private static final String FILTERED_LOCATION = "Filtered Location";
	private static final String LOCTION_DETAIL_ID = "Location Detail ID";
	private static final String MANAGER_ID = "ManagerID";
	private static final String ASSET_DET_ID = "ASSET_DET_ID";
	private static final String COMPANY_NAME = "COMPANY_NAME";
	private static final String SERIAL_NUMBER = "SERIAL_NUMBER";
	private static final String ASSET_ID = "ASSET_ID";
	private static final String PSFT_ASSET_CODE = "PSFT_ASSET_CODE";
	private static final String COUNTRY_NAME = "COUNTRY_NAME";
	private static final String LOCATION_NAME = "LOCATION_NAME";
	private static final String DEPARTMENT = "DEPARTMENT";
	private static final String SUB_LOCATION = "SUB_LOCATION";
	private static final String ASSET_TYPE = "ASSET_TYPE";
	private static final String CLASSIFICATION = "CLASSIFICATION";
	private static final String HOST_NAME = "HOST_NAME";
	private static final String ASSET_RETURN_DATE = "ASSET_RETURN_DATE";
	private static final String MAKE = "MAKE";
	private static final String MODEL = "MODEL";
	private static final String PRIMARY_ADMIN = "PRIMARY_ADMIN";
	private static final String MANAGER = "MANAGER";
	private static final String SECONDARY_ADMIN = "SECONDARY_ADMIN";
	private static final String ALLOCATION_DATE = "ALLOCATION_DATE";
	private static final String IP_ADDRESS = "IP_ADDRESS";
	private static final String CUBICL_CODE = "CUBICLE_CODE";
	private static final String ASSIGNED_TO = "ASSIGNED_TO";
	private static final String PROJECT_MANAGER = "PROJECT_MANAGER";
	private static final String CRITICALITY = "CRITICALITY";
	private static final String IS_SECURITY_CONFIRMED = "IS_SECURITY_CONFIRMED";
	private static final String PRIORTIZATION = "PRIORTIZATION";
	private static final String LAPTOP_GATEPASS_NO = "LAPTOP_GATEPASS_NO";
	private static final String ENGINEER_REMARKS = "ENGINEER_REMARKS";
	private static final String PROC_TYPE = "PROC_TYPE";
	private static final String PURCHASE_ORDER = "PURCHASE_ORDER";
	private static final String PO_DATE = "PO_DATE";
	private static final String CURRENCY = "CURRENCY";
	private static final String APPRX_COST = "APPRX_COST";
	private static final String IS_BONDED = "IS_BONDED";
	private static final String STPI_BOND = "STPI_BOND";
	private static final String STPI_BOND_DATE = "STPI_BOND_DATE";
	private static final String STP_BOND_EXP_DATE = "STP_BOND_EXP_DATE";
	private static final String OWNER_TYPE = "OWNER_TYPE";
	private static final String OWNED_BY = "OWNED_BY";
	private static final String DISP_TYPE = "DISP_TYPE";
	private static final String ADMIN_REMARKS = "ADMIN_REMARKS";
	private static final String SUPP_TYPE = "SUPP_TYPE";
	private static final String VENDOR = "VENDOR";
	private static final String SUPPLY_VENDOR = "SUPPLY_VENDOR";
	private static final String INSTALLATION_DATE = "INSTALLATION_DATE";
	private static final String WARRANTY_START_DATE = "WARRANTY_START_DATE";
	private static final String WARRANTY_END_DATE = "WARRANTY_END_DATE";
	private static final String SUPPORT_VENDOR = "SUPPORT_VENDOR";
	private static final String SUPP_REFERENCE = "SUPP_REFERENCE";
	private static final String SUPPORT_Start_date = "SUPPORT_Start_date";
	private static final String SUPPORT_END_DATE = "SUPPORT_END_DATE";
	private static final String SUPPORT_DESC = "SUPPORT_DESC";
	private static final String OWNER = "OWNER";
	private static final String FUNCN_NAME = "FUNCTION_NAME";
	private static final String ASSET_COMP_ID = "ASSET_COMP_ID";
	private static final String PARENT_SERIAL_NUMBER = "PARENT_SERIAL_NUMBER";
	private static final String COMPONENT = "COMPONENT";
	private static final String SUPPORTING_VENDOR = "SUPPORTING_VENDOR";
	private static final String PARENT_SERIAL_NO = "PARENT_SERIAL_NO";
	private static final String GRP_ID = "GROUP_ID";
	private static final String GROUP_NAME = "GROUP_NAME";
	private static final String FIELD_ID = "FIELD_ID";
	private static final String FIELD_VAL = "FIELD_VALUE";
	private static final String FUNCN_IDE = "FunctionId";
	private static final String CHILD_SERIAL_NO = "CHILD_SERIAL_NO";
	private static final String LOCKED_BY = "LOCKED_BY";
	private static final String LOCKED_DATE = "LOCKED_DATE";
	private static final String GRADE = "GRADE";
	private static final String SPECIAL_CHAR_REPLACE = "brlinebreakbreak";
	private static final int APPROVAL_STATE_ID_IS = 2;// 'HelpDesk Queue' for
	// 'Information Systems'
	// Workflow
	private static final int APPROVAL_STATE_ID_IT = 16;// 'HelpDesk Queue' for
	// 'IT Infrastructure
	// Management' Workflow
	private static final int APPROVAL_STATE_ID_BPO = 45;// 'HelpDesk Queue' for
	// 'BPO' Workflow
	private static final int APPROVAL_STATE_ID_66 = 66;
	private static final int APPROVAL_STATE_ID_80 = 80;
	private static final int APPROVAL_STATE_ID_94 = 94;
	String argName[] = { REFERENCE_ID, MENU_ID, CATEGORY_ID, PRIORITY_ID,
			ASSIGNED_GROUP, CURRENT_STATE, ACTION, CHANGED_BY, CHANGED_DATE,
			FIELD_VALUE, PREV_STATE };
	int argType[] = { Types.VARCHAR, Types.SMALLINT, Types.VARCHAR,
			Types.SMALLINT, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
			Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR };
	private static final String EXCEPTION_START_DATE = "EXCEPTION_START_DATE";
	private static final String EXCEPTION_END_DATE = "EXCEPTION_END_DATE";
	private static final String FUNCTION_ID_FDBCK = "FUNCTION_ID";
	private static final String FEEDBACK_COMMENTS = "FEEDBACK_COMMENTS";
	private static final String FEEDBACK_RATE = "FEEDBACK_RATE";
	private static final String SEARCH_REFERENCES = "Search Reference";
	private static final String IS_APPROVAL_REQUIRED = "IS_APPROVAL_REQUIRED";
	private static final String LEVEL_2_PROJECT_ID = "LEVEL_2_PROJECT_ID";
	private static final String LEVEL_3_PROJECT_ID = "LEVEL_3_PROJECT_ID";
	private static final String LEVEL_2_PROJECT_NAME = "LEVEL_2_PROJECT_NAME";
	private static final String LEVEL_3_PROJECT_NAME = "LEVEL_3_PROJECT_NAME";
	private static final String TIME_REMAINING = "TIME_REMAINING";
	private static final String TOTAL_SLA_TIME = "TOTAL_SLA_TIME";
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setiConnectDAOUtil(COMMON_ExecuteProcedure dAOUtilObj) {
		DAOUtilObj = dAOUtilObj;
	}

	public String updateHelpDeskRequest(Map<String, Object> daodetails) {

		//Added for Orchestration
		int listAudit =0;
		try{
		listAudit = this.jdbcTemplate.queryForInt(
				"select count(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID = ?"
						, (String) daodetails.get(TICKET_ID));
		}catch (Exception e) {
			listAudit =0;
		}
		if (daodetails.get(WORKFLOW_STATE).toString().equalsIgnoreCase(
				"Re-Open")
				&& listAudit > 0 && daodetails.get(FUNCTION_ID).toString().equalsIgnoreCase("256")) {
			log.info("sending to helpdesk queue!");
			String query = (String) daodetails.get("reopenQuery");
			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_REOPEN_TICKETS,
					daodetails.get("VERSION_NO"), daodetails.get(CREATEDBY),
					daodetails.get(CREATEDDATE), daodetails.get(TICKET_ID));
		}
		// End Orchestration
		else{
		String query = (String) daodetails.get(QUERY);
		Object[] GenArgs = (Object[]) daodetails.get(ARG_VALUES);

		this.jdbcTemplate.update(query, GenArgs);
		}
		insertorUpdateFunctionSpecificDetails(daodetails,
				(String) daodetails.get(TICKET_ID));
		String procName = "insert_audit_log";
		String paramVal[] = {
				(String) daodetails.get(TICKET_ID),
				"1",
				(String) daodetails.get(FUNCTION_ID),// Modified by Sali on
				// SEp14th
				(String) daodetails.get(PRIORIT_ID),
				(String) daodetails.get(ASSIGNED_GRP),
				(String) daodetails.get(WORKFLOW_STATE),
				"U",
				(String) daodetails.get(CREATEDBY),
				(String) daodetails.get(CREATEDDATE),
				MapToStringUtil
						.getStringForMap((Map<String, Object>) daodetails
								.get(AUDIT_LOG_DETAILS)), "" };
		String resJson = DAOUtilObj.executeiconnectProc(procName, argName,
				argType, paramVal, jdbcTemplate.getDataSource());

		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
				daodetails.get(TICKET_ID));

		insertUserOSBrowserDetails((String) daodetails.get(CREATEDBY),
				(String) daodetails.get(TICKET_ID),
				(String) daodetails.get(CREATEDDATE),
				(String) daodetails.get(OS_DETAILS),
				(String) daodetails.get(BROWSER_DETAILS));
		return "Success";
	}

	public Map<String, Object> lockIHDTicket(JSONObject jsonobj)
			throws JSONException {

		Map<String, Object> lockdetails = new HashMap<String, Object>();

		try {
			lockdetails = this.jdbcTemplate.queryForMap(
					HELPDESK_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
					jsonobj.get(MENU_IDE), jsonobj.get(TICKET_ID));
			lockdetails.put(STATE, ERROR);
		} catch (EmptyResultDataAccessException erde) {
			if (lockdetails.isEmpty()) {
				this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
						jsonobj.get(TICKET_ID), jsonobj.get(MENU_IDE),
						jsonobj.get(DATA), jsonobj.get(LOCKED_BY),
						jsonobj.get(LOCKED_DATE));
				lockdetails.put(STATE, "success");
			} else {
				lockdetails.put(STATE, ERROR);
			}

		}

		return lockdetails;
	}

	public Map<String, Object> getLockedTicketDetails(String menuid,
			String ticketid) {

		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS, menuid,
				ticketid);
	}

	public List<HELPDESK_Approval> getApprRejectListforTicket(String ticketID) {
		return this.jdbcTemplate.query(
				HELPDESK_SQLQueryConstants.IC_TICKET_APPR_REJECT_LIST,
				new Object[] { ticketID }, new apprRejListMapper());
	}

	private static class apprRejListMapper implements
			ParameterizedRowMapper<HELPDESK_Approval> {
		public HELPDESK_Approval mapRow(ResultSet rs, int arg1)
				throws SQLException {

			HELPDESK_Approval HDAppBean = new HELPDESK_Approval();

			HDAppBean.setTICKET_ID(Integer.toString((Integer) rs
					.getInt(TICKET_ID)));
			HDAppBean.setAPPROVER_ID(Integer.toString((Integer) rs
					.getInt(APPROVER_ID)));
			HDAppBean.setAPPROVER_NAME(rs.getString(APPROVER_NAME));
			String comments = rs.getString(COMMENTS);
			if (comments == null)
				HDAppBean.setCOMMENTS("-");
			else
				HDAppBean.setCOMMENTS(comments.replace(SPECIAL_CHAR_REPLACE,
						"\n"));
			HDAppBean.setIS_EXCEPTIONAL_APPROVAL(rs
					.getString(IS_EXCEPTIONAL_APPROVAL));
			HDAppBean.setEXCEPTION_START_DATE(rs
					.getString(EXCEPTION_START_DATE));
			HDAppBean.setEXCEPTION_END_DATE(rs.getString(EXCEPTION_END_DATE));
			HDAppBean.setCREATED_BY(rs.getString(CREATED_BY));
			HDAppBean.setCREATED_NAME(rs.getString(CREATED_NAME));
			String timeStamp = rs.getString(CREATED_DATE);
			if (timeStamp != null && !timeStamp.equalsIgnoreCase("")) {
				HDAppBean.setCREATED_DATE(CustomDateFormatConstants
						.convertToEST(timeStamp));
			}
			HDAppBean.setMODIFIED_BY(rs.getString(MODIFIED_BY));
			String timeStamp1 = rs.getString(MODIFIED_DATE);
			if (timeStamp1 != null && !timeStamp1.equalsIgnoreCase("")) {
				HDAppBean.setMODIFIED_DATE(CustomDateFormatConstants
						.convertToEST(timeStamp1));
			}

			HDAppBean.setSTATUS(rs.getString(STATUS));
			return HDAppBean;

		}

	}

	public HELPDESK_RequestorInformation getRequestorDetails(String projectid,
			String empid) {
		// Added to implement the tool to track EX employees quereis for HR
		// function
		if (projectid == null || projectid.length() == 0) {
			projectid = "0";
		}

		// END
		return this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.IC_TICKET_REQUESTOR_DETAILS,
				new Object[] { projectid, empid }, new requestorbeanMapper());
	}

	private static class requestorbeanMapper implements
			ParameterizedRowMapper<HELPDESK_RequestorInformation> {
		public HELPDESK_RequestorInformation mapRow(ResultSet rs, int arg1)
				throws SQLException {

			HELPDESK_RequestorInformation reqbean = new HELPDESK_RequestorInformation();
			reqbean.setEMPLOYEE_ID(rs.getString(EMPLOYEE_ID));
			reqbean.setEMP_NAME(rs.getString(EMP_NAME));
			String org = rs.getString(ORGANIZATION);
			if (org != null && org.equalsIgnoreCase("I"))
				reqbean.setORGANIZATION(iGATE);
			else
				reqbean.setORGANIZATION(patni);

			String reportingManagerID = rs.getString(REPORTING_MANAGER_ID);
			if (reportingManagerID != null)
				reqbean.setREPORTING_MANAGER_ID(reportingManagerID);

			String reportingManagerName = rs.getString(REPORTING_MANAGER_NAME);
			if (reportingManagerName != null)
				reqbean.setREPORTING_MANAGER_NAME(reportingManagerName);
			// Grade Addition
			String empGrade = rs.getString(GRADE);
			if (empGrade != null)
				reqbean.setGRADE(empGrade);

			String duID = rs.getString(DU_ID);
			if (duID != null)
				reqbean.setDU_ID(duID);

			String duName = rs.getString(DU_NAME);
			if (duName != null)
				reqbean.setDU_NAME(duName);

			String locationID = rs.getString(LOCATION_ID);
			if (locationID != null)
				reqbean.setLOCATION_ID(locationID);

			String country = rs.getString(COUNTRY);
			if (country != null)
				reqbean.setCOUNTRY(country);

			String city = rs.getString(CITY);
			if (city != null)
				reqbean.setCITY(city);

			String area = rs.getString(AREA);
			if (area != null)
				reqbean.setAREA(rs.getString(AREA));

			String shortName = rs.getString(SHORT_NAME);
			if (shortName != null)
				reqbean.setSHORT_NAME(shortName);

			String custName = rs.getString(CUSTOMER_NAME);
			if (custName != null)
				reqbean.setCUSTOMER_NAME(custName);

			return reqbean;

		}

	}

	public int getApprovalValidState(JSONObject jsonobj) throws JSONException {
		int approverStateId;
		try {
			approverStateId = this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.IC_APPROVAL_NEXT_TRANSITION,
					new Object[] { jsonobj.get(SUB_CATEGORY_ID).toString(),
							jsonobj.get(SUB_CATEGORY_ID).toString(),
							jsonobj.get(APPROVER_ID).toString() });
		} catch (EmptyResultDataAccessException erde) {
			log.error("Error Encountered while retreiving the next valid stateId after approval"
							+ erde);
			approverStateId = 0;
		}
		return approverStateId;
	}

	public int updateNextStatus(long locationID, long locationdetID,
			long functionid, int priorityID, String createddate,
			long categoryName, String org, String status,
			int approvalValidStateId, String workflowid, String ticketId,
			Map<String, Object> ticketDetails, JSONObject jsonObj)
			throws JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		int result = 0;
		String groupId="0";
		
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String rejectedDate = dateFormatGmt.format(new Date());

		if (approvalValidStateId == APPROVAL_STATE_ID_IS
				|| approvalValidStateId == APPROVAL_STATE_ID_IT
				|| approvalValidStateId == APPROVAL_STATE_ID_BPO
				|| approvalValidStateId == APPROVAL_STATE_ID_66
				|| approvalValidStateId == APPROVAL_STATE_ID_80
				|| approvalValidStateId == APPROVAL_STATE_ID_94) {

			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
					.getBean("GetMasterData");
			long opr_id = 0;
			opr_id = MasterDataImpl.getServiceOPRID((long) locationID,
					locationdetID, functionid);
			String ECT = "";
			if (opr_id != 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.HOUR_OF_DAY, -5);
				cal.add(Calendar.MINUTE, -30);
				Date modified_date = cal.getTime();
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String modified_date_string = sd.format(modified_date);

				ECT = HELPDESK_ECTPopulator.getECT(status, priorityID,
						modified_date_string, categoryName, org,
						(long) locationID, opr_id);
			}

			Boolean ISORCH = false;
			if(jsonObj.has("ISORCH"))
			{
			ISORCH = jsonObj.getBoolean("ISORCH");	
			functionid=256;
			groupId="2548";
			}
			if(ISORCH)
			{
				result = this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.IC_APPROVAL_STATUS_SAVE_ORCH, ECT,
						approvalValidStateId, null,null,0,ticketDetails.get("COMMENTS"),ticketId);
			}
			else
			{
			result = this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_APPROVAL_STATUS_SAVE, ECT,
					approvalValidStateId, ticketId);
			}
		} else {
			if (status.trim().toUpperCase().equalsIgnoreCase("REJECTED")) {
				result = this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.IC_REJECTED_STATUS_SAVE, null,
						approvalValidStateId,rejectedDate, ticketId);
			}
		}
		
		String currentApprovalState = Integer.toString(approvalValidStateId);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");

		String currentStatus = workflowimpl.getStateName(currentApprovalState,
				workflowid);
		if (ticketDetails.get(ACTIONS).toString()
				.equalsIgnoreCase("Need More Info")) {
			String enterComments = ticketDetails.get(COMMENTS).toString();
			this.jdbcTemplate
					.update(HELPDESK_SQLQueryConstants.UPDATE_IC_IHD_TICKET_DETAILS_ADDITIONAL_INFO,
							enterComments, ticketId);

			HashMap<String, Object> auditdetails = new HashMap<String, Object>();
			auditdetails.put(ADDITIONAL_INFO, ticketDetails.get(COMMENTS)
					.toString());
			auditdetails.put(WORKFLOW_STATE, ticketDetails.get(ACTIONS)
					.toString());

			String procName = "insert_audit_log";
			String paramVal[] = { ticketDetails.get(TICKETID).toString(), "1",
					String.valueOf(functionid),
					ticketDetails.get(PRIORIT_ID).toString(), groupId,
					ticketDetails.get(ACTIONS).toString(), "U",
					ticketDetails.get(CREATEDDBY).toString(),
					ticketDetails.get(CREATEDD_DATE).toString(),
					MapToStringUtil.getStringForMap(auditdetails),
					(String) ticketDetails.get(STATUSS) };

			DAOUtilObj.executeiconnectProc(procName, argName, argType,
					paramVal, jdbcTemplate.getDataSource());

			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
					ticketDetails.get(TICKETID).toString());

		} else if (ticketDetails.get(ACTIONS).toString()
				.equalsIgnoreCase("Send to Helpdesk")) {
			this.jdbcTemplate
					.update(HELPDESK_SQLQueryConstants.UPDATE_ADDITIONAL_INFO_CHECKFCS_IC_IHD_TICKET_DETAILS,
							ticketDetails.get(COMMENTS).toString(), ticketId);

			HashMap<String, Object> auditdetails = new HashMap<String, Object>();
			auditdetails.put(ADDITIONAL_INFO, ticketDetails.get(COMMENTS)
					.toString());
			auditdetails.put(WORKFLOW_STATE, "HelpDesk Queue");

			String procName = "insert_audit_log";
			String paramVal[] = { ticketDetails.get(TICKETID).toString(), "1",
					String.valueOf(functionid),
					ticketDetails.get(PRIORIT_ID).toString(), groupId,
					"Helpdesk Queue", "U",
					ticketDetails.get(CREATEDDBY).toString(),
					ticketDetails.get(CREATEDD_DATE).toString(),
					MapToStringUtil.getStringForMap(auditdetails),
					(String) ticketDetails.get(STATUSS) };

			DAOUtilObj.executeiconnectProc(procName, argName, argType,
					paramVal, jdbcTemplate.getDataSource());
			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
					ticketDetails.get(TICKETID).toString());
			
		} else {

			String procName = "insert_audit_log";
			String paramVal[] = {
					ticketDetails.get(TICKETID).toString(),
					"1",
					String.valueOf(functionid),
					"1",// Modified by sali
					"0", currentStatus, "U",
					ticketDetails.get(CREATEDDBY).toString(),
					ticketDetails.get(CREATEDD_DATE).toString(), null,
					ticketDetails.get(STATUSS).toString() };
			DAOUtilObj.executeiconnectProc(procName, argName, argType,
					paramVal, this.jdbcTemplate.getDataSource());
		}
		if (!(jsonObj.get(ACTTION).toString()
				.equalsIgnoreCase("Need More Info") || jsonObj.get(ACTTION)
				.toString().equalsIgnoreCase("Send to Helpdesk"))) {
			insertTicketApproverDetails(jsonObj);
		}
		return result;
	}
	
	public int updateOrchTicketStatus(long locationID, long locationdetID,
			long functionid, int priorityID, String createddate,
			long categoryName, String org, String status,
			int approvalValidStateId, String workflowid, String ticketId,
			Map<String, Object> ticketDetails, JSONObject jsonObj){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean("IHDEditDAO");
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		int result = 0;
		String currentApprovalState = Integer.toString(approvalValidStateId);
		String currentStatus = workflowimpl.getStateName(currentApprovalState,
				workflowid);
		result = this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_ORCH_TICKET_UPDATE,
				approvalValidStateId,ticketDetails.get("COMMENTS"),"ORCH SYSTEM",ticketId);
		String procName = "insert_audit_log";
		String paramVal[] = {
				ticketDetails.get(TICKETID).toString(),
				"1",
				String.valueOf(functionid),
				"1",
				"2548", currentStatus, "U",
				ticketDetails.get(CREATEDDBY).toString(),
				ticketDetails.get(CREATEDD_DATE).toString(), "",
				ticketDetails.get(STATUSS).toString() };
		DAOUtilObj.executeiconnectProc(procName, argName, argType,
				paramVal, this.jdbcTemplate.getDataSource());
		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
				ticketDetails.get(TICKETID).toString());
		return result;
	}

	public int insertTicketApproverDetails(JSONObject jsonobj)
			throws JSONException {
		int update;
		if (jsonobj.get(EXCEPTION_START_DATE).toString().equalsIgnoreCase("")) {
			update = this.jdbcTemplate
					.update(HELPDESK_SQLQueryConstants.IC_INSERT_TICKET_APPROVAL_DETAILS,
							jsonobj.get(TICKET_ID).toString(),
							jsonobj.get(APPROVER_ID).toString(),
							jsonobj.get(COMMENTS).toString(),
							jsonobj.get(IS_EXCEPTIONAL_APPROVAL).toString(),
							jsonobj.get(LOGIN_ID).toString(),
							jsonobj.get(CREATED_DATE).toString(),
							jsonobj.get(ACTTION).toString());
		} else {
			update = this.jdbcTemplate
					.update(HELPDESK_SQLQueryConstants.IC_INSERT_TICKET_EXCEPTION_APPROVAL_DETAILS,
							jsonobj.get(TICKET_ID).toString(),
							jsonobj.get(APPROVER_ID).toString(),
							jsonobj.get(COMMENTS).toString(),
							jsonobj.get(IS_EXCEPTIONAL_APPROVAL).toString(),
							jsonobj.get(LOGIN_ID).toString(),
							jsonobj.get(CREATED_DATE).toString(),
							jsonobj.get(ACTTION).toString(),
							jsonobj.get(EXCEPTION_START_DATE).toString(),
							jsonobj.get(EXCEPTION_END_DATE).toString());
		}
		return update;
	}

	public String getIHDTicketVersionNo(String ticketID) {

		return this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.IC_IHD_TICKET_VERSION_NO,
				new Object[] { ticketID }, String.class);
	}

	public List<HelpDesk> getReqList(String query, HttpServletRequest request) {
		return this.jdbcTemplate.query(query, new Object[] {},
				new helpdeskMapper(request));
	}

	private static class helpdeskMapper implements
			ParameterizedRowMapper<HelpDesk> {
		private HttpServletRequest request;

		public helpdeskMapper(HttpServletRequest Request) {
			this.request = Request;
		}

		public HelpDesk mapRow(ResultSet rs, int arg1) throws SQLException {
			HelpDesk helpdeskBean = new HelpDesk();
			// Modified by Sali
			helpdeskBean.setCHANGED_DATE_STORAGE(rs.getString(APPROVED_DATE));// TODO:remove
			// hardcoded
			// value
			helpdeskBean.setIS_CHANGE_REQUEST(rs.getString(CHANGE_REQUEST));// TODO:remove
			// hardcoded
			// value
			// tickets changes
			helpdeskBean.setTICKET_ID(rs.getString(TICKET_IDE));
			helpdeskBean.setSUBJECT(rs.getString(SUBJECT));
			helpdeskBean.setSUBJECT_LIST(rs.getString(SUBJECT));
			helpdeskBean.setDESCRIPTION(rs.getString(DESCRIPTION));
			helpdeskBean.setCATEGORY_ID(rs.getString(CATEGORY_IDE));
			helpdeskBean.setREQUESTED_BY(rs.getString("Requested By"));
			helpdeskBean.setREQUESTOR_NAME(rs.getString(REQUESTOR_NAME));
			helpdeskBean.setCREATOR_NAME(rs.getString(CREATOR_NAME));
			helpdeskBean.setCATEGORY(rs.getString(CATEGORY_NAME));
			helpdeskBean.setFUNCTION_ID(rs.getString(FUNCTION_IDE));
			helpdeskBean.setFUNCTION(rs.getString(FUNCTION_NAME));
			helpdeskBean.setSUB_CATEGORY_ID(rs.getString(SUB_CATEGRY_ID));
			helpdeskBean.setSUBCATEGORY(rs.getString(SUB_CATEGORY_NAME));
			helpdeskBean.setCOPY_TO(rs.getString(CC_TO));
			helpdeskBean.setSOURCE(rs.getString(SOURCE));
			helpdeskBean.setSEVERITY_ID(rs.getString(SEVERITY_ID));
			helpdeskBean.setPRIORITY_ID(rs.getString(PRIORITY_IDE));
			helpdeskBean.setASSIGNED_GROUP_ID(rs.getString(GROUP_ID));
			helpdeskBean.setASSIGNED_GROUP(rs.getString(ASSIGND_GROUP));
			helpdeskBean.setASSIGNED_TO(rs.getString(ASSIGND_TO));
			helpdeskBean.setREFERENCE_ID(rs.getString("ReferenceID"));
			helpdeskBean.setRESOLUTION_STATUS(rs.getString("RESOLUTION_STATUS"));
			helpdeskBean.setIS_APPROVAL_REQUIRED(rs.getString("IS_APPROVAL_REQUIRED"));
			helpdeskBean.setGRADE(rs.getString(GRADE));
			// Feedback Enhancement
			helpdeskBean.setFEEDBACK_COMMENTS(rs.getString(FEEDBACK_COMMENTS)
					.replace(SPECIAL_CHAR_REPLACE, "\n"));
			Timestamp timeStamp = rs.getTimestamp(CREATION_DATE);
			if (timeStamp != null) {
				helpdeskBean.setCREATED_DATE(CustomDateFormatConstants
						.showUserTimeZonewithRequest(
								CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
										.format(timeStamp), request));
				helpdeskBean
						.setCREATED_DATE_STORAGE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(timeStamp));
			}
			timeStamp = rs.getTimestamp(ExpCT);
			if (timeStamp != null) {
				helpdeskBean.setECT(CustomDateFormatConstants
						.showUserTimeZonewithRequest(
								CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
										.format(timeStamp), request));
				helpdeskBean
						.setECT_STORAGE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(timeStamp));
			}
			helpdeskBean.setSLA_STATUS(rs.getString(SLA_STATUS));
			helpdeskBean.setLOCATION_ID(rs.getString(LOCATION_IDE));
			helpdeskBean.setLOCATION_COUNTRY(rs.getString(COUNTRY_IDE));
			helpdeskBean.setLOCATION_CITY(rs.getString(CITY_IDE));

			helpdeskBean.setLOCATION_AREA(rs.getString(AREA_IDE));
			helpdeskBean.setLOCATION_SHORTNAME(rs.getString(LOCATION));
			helpdeskBean.setON_BEHALF_OF(rs.getString(ON_BEHALF_OF_IDE));
			helpdeskBean.setCREATED_BY(rs.getString(CREATD_BY));
			helpdeskBean.setCONTACT_NO(rs.getString(CONTACT_NUMBER));
			helpdeskBean.setALTERNATE_CONTACT_NO(rs
					.getString(ALTERNATE_CONTACT_NUMBER));
			helpdeskBean.setUSERS_IMPACTED(rs.getString(IMPACTED_USERS));
			String additional_info = rs.getString(ADDITIONL_INFO);
			if (additional_info != null)
				helpdeskBean.setADDITIONAL_INFO(additional_info.replace(
						SPECIAL_CHAR_REPLACE, "\n"));
			else
				helpdeskBean.setADDITIONAL_INFO("");
			if (rs.getString(RESOLUTION_COMMENTS) != null)
				helpdeskBean.setRESOLUTION_COMMENTS(rs.getString(
						RESOLUTION_COMMENTS)
						.replace(SPECIAL_CHAR_REPLACE, "\n"));
			else

				helpdeskBean.setRESOLUTION_COMMENTS("");
			String PRIVATE_NOTES = rs.getString("Private Notes");
			if (PRIVATE_NOTES != null)
				helpdeskBean.setPRIVATE_NOTES(PRIVATE_NOTES.replace(
						SPECIAL_CHAR_REPLACE, "\n"));
			else
				helpdeskBean.setPRIVATE_NOTES("");
			helpdeskBean.setWORKFLOW_STATE(rs.getString(STATUSS));
			helpdeskBean.setPROJECT_ID(rs.getString(PROJECT_ID));
			helpdeskBean.setPROJECT_NAME(rs.getString(PROJECT_NAME));
			helpdeskBean.setOUT_OF_SLA_REASON(rs.getString(OUT_OF_SLA_REASON));
			if (rs.getString(OUT_OF_SLA_COMMENTS) != null)
				helpdeskBean.setOUT_OF_SLA_COMMENTS(rs.getString(
						OUT_OF_SLA_COMMENTS)
						.replace(SPECIAL_CHAR_REPLACE, "\n"));
			else
				helpdeskBean.setOUT_OF_SLA_COMMENTS("");
			timeStamp = rs.getTimestamp(CLOSED_DATE);
			if (timeStamp != null) {
				helpdeskBean.setCLOSED_DATE(CustomDateFormatConstants
						.showUserTimeZonewithRequest(
								CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
										.format(timeStamp), request));
			}
			helpdeskBean.setSUSPENDED_TILL(rs.getString(SUSPENDED_TILL));
			helpdeskBean
					.setSUSPENDED_COMMENTS(rs.getString(SUSPENDED_COMMENTS));
			String ONHOLD_COMMENTS = rs.getString(ONHOLD_COMMNTS);
			if (ONHOLD_COMMENTS != null)
				helpdeskBean.setONHOLD_COMMENTS(ONHOLD_COMMENTS.replace(
						SPECIAL_CHAR_REPLACE, "\n"));
			else
				helpdeskBean.setREOPEN_COMMENTS("");
			helpdeskBean.setREMINDER_DATE(rs.getString(REMINDER_DATE));
			helpdeskBean.setFEEDBACK(rs.getString(FEEDBACK));

			String REOPEN_COMMENTS = rs.getString(REOPEN_COMMNTS);
			if (REOPEN_COMMENTS != null)
				helpdeskBean.setREOPEN_COMMENTS(REOPEN_COMMENTS.replace(
						SPECIAL_CHAR_REPLACE, "\n"));
			else
				helpdeskBean.setREOPEN_COMMENTS("");

			helpdeskBean.setINVOICE_NO(rs.getString(INVOICE_NO));
			helpdeskBean.setVENDOR_NAME(rs.getString(VENDOR_NAME));
			helpdeskBean.setVERSION_NO(rs.getString(VERSION_NO));
			// helpdeskBean.setCHECK_FCS(rs.getString(CHECK_FCS));
			String cubicalcode = rs.getString(CUBICLE_CODE);
			if (cubicalcode == null || cubicalcode.equalsIgnoreCase("NULL")) {
				helpdeskBean.setCUBICLE_CODE("NA");
			} else {
				helpdeskBean.setCUBICLE_CODE(cubicalcode);
			}
			String filteredLocation = rs.getString(FILTERED_LOCATION);
			if (filteredLocation == null || filteredLocation.equals("")) {
				helpdeskBean.setFILTER_GROUP_LOCATION(rs
						.getString(LOCATION_IDE));
			} else {
				helpdeskBean.setFILTER_GROUP_LOCATION(rs
						.getString(FILTERED_LOCATION));
			}
			String LocationDetID = rs.getString(LOCTION_DETAIL_ID);
			if (LocationDetID != null) {
				helpdeskBean.setLOC_DET_ID(LocationDetID);
			} else {
				helpdeskBean.setLOC_DET_ID("0");
			}
			String ProjManagerId = rs.getString(MANAGER_ID);
			if (ProjManagerId != null) {
				helpdeskBean.setMANAGER_ID(ProjManagerId);
			}
			// Added for Search references
			String check_searchPreference = rs.getString(SEARCH_REFERENCES);
			if (check_searchPreference != null
					&& check_searchPreference.equalsIgnoreCase("1"))
				helpdeskBean.setSEARCH_REFERENCE(true);
			else
				helpdeskBean.setSEARCH_REFERENCE(false);
			if(null==rs.getString(TIME_REMAINING)){
				helpdeskBean.setTIME_REMAINING("");
			}else{
			helpdeskBean.setTIME_REMAINING(rs.getString(TIME_REMAINING));
			}
			helpdeskBean.setTOTAL_SLA_TIME(rs.getString(TOTAL_SLA_TIME));
			return helpdeskBean;
		}
	}

	public int unlockTickets(ArrayList<Object> ArgsList) {

		int result = 0;
		int ArgTypes[] = { Types.INTEGER, Types.VARCHAR };
		final String sql = HELPDESK_SQLQueryConstants.IC_IHD_DELETE_LOCKED_DETAILS;

		DAOUtilObj.executeBatchUpdation(sql, ArgsList, ArgTypes,
				jdbcTemplate.getDataSource());

		return result;
	}

	public int getValidState(String subcategoryId, String approverId) {
		int approvalValidStateId = 0;
		try {
			approvalValidStateId = this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.IC_APPROVAL_NEXT_TRANSITION,
					new Object[] { subcategoryId, subcategoryId, approverId });
		} catch (EmptyResultDataAccessException e) {

		}
		return approvalValidStateId;

	}

	public List<Map<String, Object>> getLoggedInGroupMembers(String groupid) {
		/*
		 * return this.jdbcTemplate.queryForList(
		 * hdConstants.IC_LOGGED_IN_GROUP_MEMBERS, new Object[] { groupid });
		 */
		return this.jdbcTemplate
				.queryForList(
						HELPDESK_SQLQueryConstants.IC_LOGGED_IN_GROUP_MEMBERS_AVAILABLE,
						groupid);

	}

	public int updateAdditionalInfo(JSONObject json) throws JSONException {
		return jdbcTemplate
				.update(HELPDESK_SQLQueryConstants.UPDATE_IC_IHD_TICKET_DETAILS_ADDITIONAL_INFO,
						json.get(COMMENTS).toString(), json.get(TICKET_ID)
								.toString());
	}

	public String getAdditionalInfoforTicket(String needAdditonalInfo) {
		return this.jdbcTemplate
				.queryForObject(
						HELPDESK_SQLQueryConstants.SELECT_ADD_INFO_IC_IHD_TICKET_DETAILS,
						new Object[] { needAdditonalInfo }, String.class);

	}

	public List<Map<String, Object>> getAssetDetail(String empId) {
		String getAssetDetailQuery = HELPDESK_SQLQueryConstants.SELECT_IC_ASSET_DETAILS;
		return this.jdbcTemplate.queryForList(getAssetDetailQuery, empId);
	}

	public List<Map<String, Object>> getAssetCompDetail(String assetDetId) {
		String getAssetCompDetailQuery = HELPDESK_SQLQueryConstants.SELECT_IC_ASSET_COMPONENTS;
		return this.jdbcTemplate.queryForList(getAssetCompDetailQuery,
				assetDetId);
	}

	public String insertAssetDetails(final HELPDESK_AssetTab asset,
			final String loginID) throws JSONException {

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(HELPDESK_SQLQueryConstants.IC_IHD_INSERT_ASSET_MAPPING);
				ps.setString(1, asset.getASSET_CHILD_SERIAL_NO());
				ps.setString(2, asset.getASSET_CHILD_SERIAL_NO());
				ps.setString(3, loginID);
				ps.setString(4,
						CustomDateFormatConstants.creationDateGMTFormat());
				ps.setString(5, asset.getASSET_CHILD_SERIAL_NO());
				ps.setString(6, asset.getTICKET_ID());
				ps.setString(7, "1");
				ps.setString(8, asset.getASSET_CHILD_SERIAL_NO());
				ps.setString(9, asset.getASSET_PARENT_SERIAL_NO());
				ps.setString(10, loginID);// Later Modification
				ps.setString(11,
						CustomDateFormatConstants.creationDateGMTFormat());// Later
				return ps;
			}
		});
		return "Success";
	}

	public String insertAssetParentDetails(final HELPDESK_AssetTab asset,
			final String loginID) throws JSONException {

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(HELPDESK_SQLQueryConstants.IC_IHD_INSERT_ASSET_MAPPING_PARENT);
				ps.setString(1, asset.getASSET_PARENT_SERIAL_NO());
				ps.setString(2, asset.getASSET_PARENT_SERIAL_NO());
				ps.setString(3, loginID);
				ps.setString(4,
						CustomDateFormatConstants.creationDateGMTFormat());
				ps.setString(5, asset.getASSET_PARENT_SERIAL_NO());
				ps.setString(6, asset.getTICKET_ID());
				ps.setString(7, "1");
				ps.setString(8, asset.getASSET_CHILD_SERIAL_NO());
				ps.setString(9, asset.getASSET_PARENT_SERIAL_NO());
				ps.setString(10, loginID);// Later Modification
				ps.setString(11,
						CustomDateFormatConstants.creationDateGMTFormat());// Later
				return ps;
			}
		});
		return "Success";
	}

	public void auditlogForAsset(final String ticketID,
			final String workflowSt, final String loginID,
			HashMap<String, Object> auditlogdetails) {

		auditlogdetails.put(WORKFLOW_STATE, workflowSt);
		String procName = "insert_audit_log";
		String paramVal[] = { ticketID, "1", "0", "0", "0", workflowSt, "U",
				loginID, CustomDateFormatConstants.creationDateGMTFormat(),
				MapToStringUtil.getStringForMap(auditlogdetails), workflowSt };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

		this.jdbcTemplate.update(
				"DELETE IC_LOCKED_TICKET_DETAILS WHERE REFERENCE_ID= ?",
				ticketID);

	}

	public int checkExistAsset(String childAssetId) {

		List<Map<String, Object>> childAssetList = this.jdbcTemplate
				.queryForList(HELPDESK_SQLQueryConstants.IC_IHD_SELECT_ASSET_MAPPING);
		int childID = 0;
		for (Map<String, Object> childAsset : childAssetList) {
			if (null != childAsset.get(CHILD_SERIAL_NO)) {
				if (childAsset.get(CHILD_SERIAL_NO).toString()
						.contains(childAssetId))
					childID = 1;
				break;
			}
		}
		return childID;
	}

	public int checkExistParentAsset(String parentAssetId) {

		List<Map<String, Object>> parentAssetList = this.jdbcTemplate
				.queryForList(HELPDESK_SQLQueryConstants.IC_IHD_SELECT_PARENT_ASSET_MAPPING);
		int parentID = 0;
		for (Map<String, Object> parentAsset : parentAssetList) {
			if (null != parentAsset.get(PARENT_SERIAL_NO)) {
				if (parentAsset.get(PARENT_SERIAL_NO).toString()
						.contains(parentAssetId))
					parentID = 1;
				break;
			}
		}
		return parentID;
	}

	public HELPDESK_AssetInformation getAssetDetailView(String parentSlNo) {
		final HELPDESK_AssetInformation obj = new HELPDESK_AssetInformation();
		// try {
		String getAssetDetailViewQuery = HELPDESK_SQLQueryConstants.IC_IHD_SELECT_DETAIL_ASSET;
		this.jdbcTemplate.queryForObject(getAssetDetailViewQuery,
				new Object[] { parentSlNo },
				new RowMapper<HELPDESK_AssetInformation>() {

					public HELPDESK_AssetInformation mapRow(ResultSet rs,
							int count) throws SQLException {
						obj.setASSET_DET_ID(rs.getString(ASSET_DET_ID));
						obj.setCOMPANY_NAME(rs.getString(COMPANY_NAME));
						obj.setSERIAL_NUMBER(rs.getString(SERIAL_NUMBER));
						obj.setASSET_ID(rs.getString(ASSET_ID));
						obj.setPSFT_ASSET_CODE(rs.getString(PSFT_ASSET_CODE));
						obj.setCOUNTRY_NAME(rs.getString(COUNTRY_NAME));
						obj.setLOCATION_NAME(rs.getString(LOCATION_NAME));
						obj.setDEPARTMENT(rs.getString(DEPARTMENT));
						obj.setSUB_LOCATION(rs.getString(SUB_LOCATION));
						obj.setASSET_TYPE(rs.getString(ASSET_TYPE));
						obj.setCLASSIFICATION(rs.getString(CLASSIFICATION));
						obj.setHOST_NAME(rs.getString(HOST_NAME));
						obj.setSTATUS(rs.getString(STATUS));
						obj.setASSET_RETURN_DATE(rs
								.getString(ASSET_RETURN_DATE));
						obj.setMAKE(rs.getString(MAKE));
						obj.setMODEL(rs.getString(MODEL));
						obj.setPRIMARY_ADMIN(rs.getString(PRIMARY_ADMIN));
						obj.setMANAGER(rs.getString(MANAGER));

						obj.setSECONDARY_ADMIN(rs.getString(SECONDARY_ADMIN));
						obj.setALLOCATION_DATE(rs.getString(ALLOCATION_DATE));
						obj.setIP_ADDRESS(rs.getString(IP_ADDRESS));
						obj.setCUBICLE_CODE(rs.getString(CUBICL_CODE));
						obj.setASSIGNED_TO(rs.getString(ASSIGNED_TO));
						obj.setPROJECT_NAME(rs.getString(PROJECT_NAME));
						obj.setPROJECT_MANAGER(rs.getString(PROJECT_MANAGER));
						obj.setCRITICALITY(rs.getString(CRITICALITY));
						obj.setIS_SECURITY_CONFIRMED(rs
								.getString(IS_SECURITY_CONFIRMED));
						obj.setPRIORTIZATION(rs.getString(PRIORTIZATION));
						obj.setLAPTOP_GATEPASS_NO(rs
								.getString(LAPTOP_GATEPASS_NO));
						obj.setENGINEER_REMARKS(rs.getString(ENGINEER_REMARKS));
						obj.setPROC_TYPE(rs.getString(PROC_TYPE));
						obj.setPURCHASE_ORDER(rs.getString(PURCHASE_ORDER));
						obj.setPO_DATE(rs.getString(PO_DATE));
						obj.setCURRENCY(rs.getString(CURRENCY));
						obj.setAPPRX_COST(rs.getString(APPRX_COST));
						obj.setIS_BONDED(rs.getString(IS_BONDED));
						obj.setSTPI_BOND(rs.getString(STPI_BOND));
						obj.setSTPI_BOND_DATE(rs.getString(STPI_BOND_DATE));
						obj.setSTP_BOND_EXP_DATE(rs
								.getString(STP_BOND_EXP_DATE));
						obj.setOWNER_TYPE(rs.getString(OWNER_TYPE));
						obj.setOWNED_BY(rs.getString(OWNED_BY));
						obj.setDISP_TYPE(rs.getString(DISP_TYPE));
						obj.setADMIN_REMARKS(rs.getString(ADMIN_REMARKS));
						obj.setSUPP_TYPE(rs.getString(SUPP_TYPE));
						obj.setVENDOR(rs.getString(VENDOR));
						obj.setSUPPLY_VENDOR(rs.getString(SUPPLY_VENDOR));
						obj.setINSTALLATION_DATE(rs
								.getString(INSTALLATION_DATE));
						obj.setWARRANTY_START_DATE(rs
								.getString(WARRANTY_START_DATE));
						obj.setWARRANTY_END_DATE(rs
								.getString(WARRANTY_END_DATE));
						obj.setSUPPORT_VENDOR(rs.getString(SUPPORT_VENDOR));
						obj.setSUPP_REFERENCE(rs.getString(SUPP_REFERENCE));
						obj.setSUPPORT_Start_date(rs
								.getString(SUPPORT_Start_date));
						obj.setSUPPORT_END_DATE(rs.getString(SUPPORT_END_DATE));
						obj.setSUPPORT_DESC(rs.getString(SUPPORT_DESC));
						obj.setOWNER(rs.getString(OWNER));
						obj.setFUNCTION_NAME(rs.getString(FUNCN_NAME));
						obj.setCREATED_BY(rs.getString(CREATED_BY));
						obj.setCREATED_DATE(rs.getString(CREATED_DATE));
						obj.setMODIFIED_BY(rs.getString(MODIFIED_BY));
						obj.setMODIFIED_DATE(rs.getString(MODIFIED_DATE));
						return obj;
					}

				});
		return obj;
	}

	public HELPDESK_AssetInformation getAssetCompDetailView(String childSlNo) {
		final HELPDESK_AssetInformation obj = new HELPDESK_AssetInformation();
		// try {
		String getAssetDetailViewQuery = HELPDESK_SQLQueryConstants.IC_IHD_SELECT_COMP_DETAIL_ASSET;
		this.jdbcTemplate.queryForObject(getAssetDetailViewQuery,
				new Object[] { childSlNo },
				new RowMapper<HELPDESK_AssetInformation>() {

					public HELPDESK_AssetInformation mapRow(ResultSet rs,
							int count) throws SQLException {
						obj.setASSET_COMP_ID(rs.getString(ASSET_COMP_ID));
						obj.setCOMPANY_NAME(rs.getString(COMPANY_NAME));
						obj.setPARENT_SERIAL_NUMBER(rs
								.getString(PARENT_SERIAL_NUMBER));
						obj.setCOMPONENT(rs.getString(COMPONENT));
						obj.setSERIAL_NUMBER(rs.getString(SERIAL_NUMBER));
						obj.setASSET_ID(rs.getString(ASSET_ID));
						obj.setPSFT_ASSET_CODE(rs.getString(PSFT_ASSET_CODE));
						obj.setCOUNTRY_NAME(rs.getString(COUNTRY_NAME));
						obj.setLOCATION_NAME(rs.getString(LOCATION_NAME));
						obj.setDEPARTMENT(rs.getString(DEPARTMENT));
						obj.setASSET_TYPE(rs.getString(ASSET_TYPE));
						obj.setCLASSIFICATION(rs.getString(CLASSIFICATION));
						obj.setSTATUS(rs.getString(STATUS));
						obj.setMAKE(rs.getString(MAKE));
						obj.setMODEL(rs.getString(MODEL));
						obj.setCRITICALITY(rs.getString(CRITICALITY));
						obj.setIS_SECURITY_CONFIRMED(rs
								.getString(IS_SECURITY_CONFIRMED));
						obj.setPRIORTIZATION(rs.getString(PRIORTIZATION));
						obj.setENGINEER_REMARKS(rs.getString(ENGINEER_REMARKS));
						obj.setPROC_TYPE(rs.getString(PROC_TYPE));
						obj.setPURCHASE_ORDER(rs.getString(PURCHASE_ORDER));
						obj.setPO_DATE(rs.getString(PO_DATE));
						obj.setCURRENCY(rs.getString(CURRENCY));
						obj.setAPPRX_COST(rs.getString(APPRX_COST));
						obj.setIS_BONDED(rs.getString(IS_BONDED));
						obj.setSTPI_BOND(rs.getString(STPI_BOND));
						obj.setSTPI_BOND_DATE(rs.getString(STPI_BOND_DATE));
						obj.setSTP_BOND_EXP_DATE(rs
								.getString(STP_BOND_EXP_DATE));
						obj.setOWNER_TYPE(rs.getString(OWNER_TYPE));
						obj.setOWNED_BY(rs.getString(OWNED_BY));
						obj.setDISP_TYPE(rs.getString(DISP_TYPE));
						obj.setADMIN_REMARKS(rs.getString(ADMIN_REMARKS));
						obj.setSUPP_TYPE(rs.getString(SUPP_TYPE));
						obj.setVENDOR(rs.getString(VENDOR));
						obj.setSUPPLY_VENDOR(rs.getString(SUPPLY_VENDOR));
						obj.setINSTALLATION_DATE(rs
								.getString(INSTALLATION_DATE));
						obj.setWARRANTY_START_DATE(rs
								.getString(WARRANTY_START_DATE));
						obj.setWARRANTY_END_DATE(rs
								.getString(WARRANTY_END_DATE));
						obj.setSUPPORT_VENDOR(rs.getString(SUPPORTING_VENDOR));
						obj.setSUPP_REFERENCE(rs.getString(SUPP_REFERENCE));
						obj.setSUPPORT_Start_date(rs
								.getString(SUPPORT_Start_date));
						obj.setSUPPORT_END_DATE(rs.getString(SUPPORT_END_DATE));
						obj.setSUPPORT_DESC(rs.getString(SUPPORT_DESC));
						obj.setOWNER(rs.getString(OWNER));
						obj.setFUNCTION_NAME(rs.getString(FUNCN_NAME));
						obj.setCREATED_BY(rs.getString(CREATED_BY));
						obj.setCREATED_DATE(rs.getString(CREATED_DATE));
						obj.setMODIFIED_BY(rs.getString(MODIFIED_BY));
						obj.setMODIFIED_DATE(rs.getString(MODIFIED_DATE));
						return obj;
					}

				});
		return obj;
	}

	public Map<String, Object> getEmployeeDetails(String employeeid) {
		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_USER_DETAILS, employeeid);
	}

	public void insertUserOSBrowserDetails(String employeeID, String ticketID,
			String createdDate, String osdetails, String browserdetails) {

		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		String OsBrowserDetailsNeedToBeSaved = bundle
				.getString("OsBrowserDetailsNeedToBeSaved");
		if (OsBrowserDetailsNeedToBeSaved.equalsIgnoreCase("true")) {
			// try {
			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_USER_SYSTEM_DETAILS,
					employeeID, ticketID, "U", createdDate, osdetails,
					browserdetails);
		}
	}

	public int updateAssignedGroup(String ECT, String stateId, int groupID,
			Map<String, Object> details) {
		this.jdbcTemplate
				.update(HELPDESK_SQLQueryConstants.UPDATE_IC_IHD_TICKET_DETAILS_ASSIGNED_GROUP_ECT,
						stateId, groupID, ECT, details.get(TICKETID).toString());
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");

		String workflowid = workflowimpl.getWorkflowID(details.get("Function")
				.toString());
		String currentStatus = workflowimpl.getStateName(stateId, workflowid);

		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		// Get Group Name using GroupId from Master cache data
		List<Map<String, Object>> groupList = MasterDataImpl
				.getIHDGroupMaster();
		String groupName = null;
		for (Map<String, Object> stringObj : groupList) {
			if (stringObj.get(GRP_ID) != null) {
				if (stringObj.get(GRP_ID).toString()
						.equalsIgnoreCase(Integer.toString(groupID))) {
					groupName = stringObj.get(GROUP_NAME).toString();
					break;
				}
			}
		}

		HashMap<String, Object> auditdetails = new HashMap<String, Object>();
		auditdetails.put(ASSIGNED_GRP, groupName);
		auditdetails.put(WORKFLOW_STATE, "Open");
		auditdetails.put(ExpCT, ECT);
		String procName = "insert_audit_log";
		String paramVal[] = { details.get(TICKETID).toString(), "1",
				details.get(FUNCN_IDE).toString(), "1", "0", currentStatus,
				"U", details.get(CREATEDDBY).toString(),
				details.get(CREATEDD_DATE).toString(),
				MapToStringUtil.getStringForMap(auditdetails),
				"Sent For Approval" };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
				details.get(TICKETID));
		return 0;
	}

	public void insertorUpdateFunctionSpecificDetails(
			Map<String, Object> insertdetails, String ticketid) {

		if (insertdetails.get(FIELD_ID) != null
				&& insertdetails.get(FIELD_ID) != "") {
			// try {
			int count = this.jdbcTemplate
					.queryForInt(
							HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_COUNT,
							ticketid, (String) insertdetails.get(FUNCTION_ID),
							(String) insertdetails.get(FIELD_ID));
			if (count <= 0) {
				if (insertdetails.get(FIELD_ID).toString()
						.equalsIgnoreCase("106")) {
					this.jdbcTemplate
							.update(HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS,
									ticketid,
									(String) insertdetails.get(FUNCTION_ID),
									"102", "Yes",
							(String) insertdetails.get(CREATEDBY),
							(String) insertdetails.get(CREATEDDATE));
				}
				this.jdbcTemplate
						.update(HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS,
								ticketid,
								(String) insertdetails.get(FUNCTION_ID),
								(String) insertdetails.get(FIELD_ID),
								(String) insertdetails.get(FIELD_VAL),
								(String) insertdetails.get(CREATEDBY),
								(String) insertdetails.get(CREATEDDATE));
			} else {
				this.jdbcTemplate
						.update(HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_UPDATE,
								(String) insertdetails.get(FIELD_VAL),
								(String) insertdetails.get(CREATEDBY),
								(String) insertdetails.get(CREATEDDATE),
								ticketid,
								(String) insertdetails.get(FUNCTION_ID),
								(String) insertdetails.get(FIELD_ID));
			}
		}
	}

	public Map<String, Object> getFunctionSpecificFieldsForTicket(
			String ticketid, String functionid, String fieldid) {

		Map<String, Object> fields = null;
		try {
			fields = this.jdbcTemplate
					.queryForMap(
							HELPDESK_SQLQueryConstants.IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_SELECT,
							ticketid, functionid, fieldid);
		} catch (EmptyResultDataAccessException e) {
			fields = new HashMap<String, Object>();
		}
		return fields;
	}

	public String getDeptIdFromFuncSpecList(String ticketId, long functionid,
			String fieldId) {
		String fieldvalue = null;
		try {
			fieldvalue = this.jdbcTemplate
					.queryForObject(
							HELPDESK_SQLQueryConstants.SELECT_DEPTID_FROM_IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS,
							new Object[] { ticketId, functionid, fieldId },
							String.class);
		} catch (EmptyResultDataAccessException e) {
			fieldvalue = "";
		}
		return fieldvalue;
	}

	public String getMasterIdForReferenceId(String referenceId) {

		String masterId = null;
		try {
			masterId = this.jdbcTemplate.queryForObject(
					HELPDESK_SQLQueryConstants.SELECT_MASTER_ID_QUERY,
					new Object[] { referenceId }, String.class);
		} catch (EmptyResultDataAccessException e) {
			masterId = null;
		}
		return masterId;
	}

	// Approval check for second time approving tickets.
	public int checkApprovalExists(String ticketId, int approverId) {
		int approvalExists;
		try {
			approvalExists = this.jdbcTemplate
					.queryForInt(
							HELPDESK_SQLQueryConstants.SELECT_TICKET_ID_FROM_IC_IHD_TICKET_APPROVAL_DETAILS,
							new Object[] { ticketId, approverId });
		} catch (EmptyResultDataAccessException erde) {
			log.error("Error Encountered while checking the approval exisitng for the ticket"
							+ erde);
			approvalExists = 0;
		}
		return approvalExists;
	}

	public int getNextApproverId(JSONObject jsonobj, int approverId)
			throws JSONException {
		int approverStateId;
		try {
			approverStateId = this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.GET_NEXT_VALID_APPROVER,
					new Object[] { jsonobj.get("SUBCATEGORY_ID").toString(),
							jsonobj.get("SUBCATEGORY_ID").toString(),
							approverId });
		} catch (EmptyResultDataAccessException erde) {
			log.error("Error Encountered while retreiving the next valid stateId after approval"
							+ erde);
			approverStateId = 0;
		}
		return approverStateId;
	}

	public void statusUpdationExceptionalCase(String ticketId, String status) {
		this.jdbcTemplate
				.update(HELPDESK_SQLQueryConstants.INSERT_ICONNECT_ITRACK_STATUS_EXCEPTIONAL,
						ticketId, status, "N",
						CustomDateFormatConstants.creationDateGMTFormat(),
						ticketId, ticketId, status,
						CustomDateFormatConstants.creationDateGMTFormat());
	}

	public void statusUpdationUnexceptionalCase(String ticketId, String status) {
		try {
			this.jdbcTemplate
					.update(HELPDESK_SQLQueryConstants.UPDATE_ICONNECT_ITRACK_STATUS_UNEXCEPTIONAL,
							ticketId, status, "Y",
							CustomDateFormatConstants.creationDateGMTFormat(),
							ticketId);
		} catch (EmptyResultDataAccessException erde) {
			log.error("Error Encountered while retreiving the exceptional status updation"
							+ erde);
		}

	}
	// Feedback Enhancement
	public int saveFeedbackComments(Map<String, Object> ticketDetails) {
		int result = 0;
		String ticketID = ticketDetails.get(TICKET_ID).toString();
		String feedbackComment = ticketDetails.get(FEEDBACK_COMMENTS)
				.toString();
		String feedbackRate = ticketDetails.get(FEEDBACK_RATE).toString();
		String functionID = ticketDetails.get(FUNCTION_ID_FDBCK).toString();
		String currentStatus = ticketDetails.get(WORKFLOW_STATE).toString();
		String createdBy = ticketDetails.get(CREATED_BY).toString();
		String createdDate = ticketDetails.get(CREATED_DATE).toString();

		result = this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.UPDATE_FEEDBACK_COMMENTS,
				new Object[] { feedbackRate, feedbackComment, createdBy,
						ticketID });

		String procName = "insert_audit_log";
		HashMap<String, Object> auditdetails = new HashMap<String, Object>();
		auditdetails.put(FEEDBACK_COMMENTS, feedbackComment);
		auditdetails.put(FEEDBACK, feedbackRate);

		String paramVal[] = { ticketID, "1", functionID, "1", "0",
				currentStatus, "U", createdBy, createdDate,
				MapToStringUtil.getStringForMap(auditdetails), currentStatus };
		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				this.jdbcTemplate.getDataSource());

		this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
				ticketID);
		return result;
	}

	public int updateDateForTicket(String ticketId, String revokeDate) {
		int result = 0;
		result = this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.UPDATE_TICKET_APPROVAL_DETAILS,
				new Object[] { revokeDate, ticketId });
		return result;
	}


	public boolean checkApprovalExists(String firstApprover, String ticketId) {
		boolean result;
		try {
			this.jdbcTemplate
					.queryForInt(
							HELPDESK_SQLQueryConstants.SELECT_APPROVER_FROM_IC_IHD_TICKET_APPROVAL_DETAILS_FOR_APPROVER_TICKET,
							new Object[] { firstApprover, ticketId });
			result = true;
		} catch (EmptyResultDataAccessException erde) {
			result = false;
			log.error("Error" + erde);
		}
		return result;
	}

	public int updateFCSCheck(String ticketId) {
		int result = 0;
		result = this.jdbcTemplate.update(
				HELPDESK_SQLQueryConstants.UPDATE_CHECK_FCS_IN_TICKET_DETAILS,
				new Object[] {  ticketId });
		return result;
		
	}
	
	/**********Auto Assignment************/
	public int getEngrAvailabilityStatus(String empID, String opStartTime,
			String opEndTime) {
		int count=0;
		count=this.jdbcTemplate.queryForInt(HELPDESK_SQLQueryConstants.GET_ENGINEER_STATUS,empID,opStartTime,opEndTime);
		return count;
	}
	
	public List<Map<String, Object>> getTicketListForEngineer(String empID){
		String sql=HELPDESK_SQLQueryConstants.SELECT_TICKET_LIST_FOR_ENGNR;
		return this.jdbcTemplate.queryForList(sql,new Object[]{empID});
	}
	public String getMaxLoginTimeForEngineer(String empID){
		String maxLoginTime=null;
		maxLoginTime = this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.SELECT_MAX_LOGIN_TIME_FOR_ENGNR,
				new Object[] { empID }, String.class);
		return maxLoginTime;
	}
	public String getPrevLoginTimeForEngineer(String empID){
		String maxLoginTime=null;
		maxLoginTime = this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.SELECT_PREV_LOGIN_TIME_FOR_ENGNR,
				new Object[] { empID, empID }, String.class);
		return maxLoginTime;
	}
	
	public String getPrevMaxINTimeForEngineer(String empID){
		String maxLoginTime=null;
		maxLoginTime = this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.SELECT_PREV_MAX_IN_TIME_FOR_ENGNR,
				new Object[] { empID, empID }, String.class);
		return maxLoginTime;
	}
	
	public void insertEngineerScore(String empID, double HIGH_SCORE,
			double LOW_SCORE, double MEDIUM_SCORE, double TOTAL_SCORE,
			String data_change) {
		int isEngnrExistCount = this.jdbcTemplate.queryForInt(
				HELPDESK_SQLQueryConstants.SELECT_COUNT_ENGNR_IN_SCORE_TABLE,
				new Object[] { empID });
		if(isEngnrExistCount>0){
			String updateQuery=HELPDESK_SQLQueryConstants.UPDATE_SCORE_FOR_ENGINEER;
			this.jdbcTemplate.update(updateQuery, new Object[] { HIGH_SCORE,
					MEDIUM_SCORE, LOW_SCORE, TOTAL_SCORE, empID, empID });
		}else{
			String insertQuery=HELPDESK_SQLQueryConstants.INSERT_SCORE_FOR_ENGINEER;
			this.jdbcTemplate.update(insertQuery, new Object[] { empID,
					HIGH_SCORE, MEDIUM_SCORE, LOW_SCORE, TOTAL_SCORE, empID });
		}
		String auditQuery=HELPDESK_SQLQueryConstants.INSERT_SCORE_FOR_ENGINEER_AUDIT_LOG;
		this.jdbcTemplate.update(auditQuery, empID,data_change,empID);		
	}
	
	public List<Map<String, Object>> getLoggedInGroupMembersScore(String groupid){
		return this.jdbcTemplate.queryForList(
				HELPDESK_SQLQueryConstants.SELECT_LOGGED_MEMBERS_SCORE,
				new Object[] { groupid });
	}
	
	public Map<String, Object> getMinimumScore(String  groupid){
		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.SELECT_MIN_SCORE_FOR_GROUP,
				new Object[] { groupid });
	}
	public Map<String, Object> getScoreDetailForEngineer(String empID){
		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.SELECT_EMPLOYEE_SCORE_DETAIL,
				new Object[] { empID });
	}	
	public String calculateScoreOnTicketUpdation(int priorityChangeFlag,int statusChangeFlag,int assignedToChangeFlag,String currentPriority,
		String newPriority,String currentEngineer, String newEngineer,String currentStatus,String newStatus,String modifiedBy,String ticketID){
		String procName="getScoreForUpdate";
		String argName_Score[]={"priorityChangeFlag","statusChangeFlag","assignedToChangeFlag","currentPriority","newPriority","currentEngineer","newEngineer",
								"currentStatus","newStatus","ticketID","modifiedBy"};
		int argType_Score[]={Types.BIT,Types.BIT,Types.BIT,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,
							Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		String inputValues[]={String.valueOf(priorityChangeFlag),String.valueOf(statusChangeFlag),String.valueOf(assignedToChangeFlag),
				currentPriority,newPriority,currentEngineer,newEngineer,currentStatus,newStatus,ticketID,modifiedBy};
		
		
		String result=DAOUtilObj.executeProcedureForScoreUpdation(procName, argName_Score, argType_Score, inputValues, jdbcTemplate.getDataSource());
		return result;
	}

	
	public String getAssignedToBasedOnTicketAssignedTime(
			StringBuffer engineerWithLowestScoreStr) {		
		StringBuffer query=new StringBuffer(HELPDESK_SQLQueryConstants.SELECT_LAST_TICKET_ASSIGNED_TO);
		StringBuffer queryToAppend=new StringBuffer("))");
		query.append(engineerWithLowestScoreStr.toString());
		query.append(queryToAppend.toString());
		String assignedTo=null;
		try{
		assignedTo=this.jdbcTemplate.queryForObject(query.toString(), String.class);
		}catch(EmptyResultDataAccessException erdae){
			assignedTo=null;
		}
		return assignedTo;
	}
	public List<Map<String, Object>> gettcktList() {
	String query="SELECT TICKET_ID,ATTACHMENT_PATH,ATTACHMENT_NAME FROM IC_IHD_TICKET_ATTACHMENT_DETAILS WHERE TICKET_ID IN ('306307','330313','336475','349332','349743','349785','349791','349977','349982','350002','351249','352842','356477','357163','359914','362557','362692','362708','363089','363509','363566','363587','363612','363643','363668','363683','363688','363730','363733','363738','363746','363758','363761','363776','363830','363850','363851','363870','363913','363944','363956','363968','363975','364005','364023','364070','364126','364161','364163','364248','364277','364313','364427','364474','364490','364511','364565','364576','364580','364592','364607','364612','364618','364635','364637','364639','364641','364642','364651','364655','364657','364681','364683','364691','364692','364693','364705','364707','364718','364732','364737','364739','364744','364761','364762','364763','364773','364776','364780','364784','364785','364789','364792','364793','364800','364841','364852','364857','364858','364860','364867','364870','364871','364873','364880','364883','364884','364887','364888','364894','364896','364899','364902','364905','364912','364920','364945','364958','364969','364982','365227','365410','365612','365776','365847','365857','365963','366036','366146','366171','366176','366188','366193','366198','366206','366211','366265','366295','366304','366305','366315','366320','366344','366377','366400','366431','366442','366444','366449','366452','366455','366500','366505','366523','366531','366535','366539','366559','366566','366570','366573','366574','366578','366581','366969','367106','367401','367433','367508','367517','367831','367858','367977','368099','368127','368157','368177','368192','368258','368259','368261','368262','368292','368301','368312','368318','368319','368322','368324','368325','368361','368363','368378','368383','368420','368468','368531','368533','368647','368663','368665','369170','369315','369328','369346','369389','369392','369693','369752','369804','369822','369840','369843','369855','369860','369865','369877','369887','369896','369912','369917','369938','369956','369962','369965','369969','369972','369975','369982','369983','370003','370006','370014','370019','370030','370032','370038','370053','370061','370065','370075','370079','370080','370092','370097','370110','370111','370130','370134','370149','370162','370177','370212','370287','370381','370931','370999','371070','371086','371193','371199','371328','371623','371632','371640','371669','371704','371716','371751','371782','371805','371809','371848','371850','371876','371888','371899','371902','371910','371915','371941','371989','372062','372070','372218','372254','372318','372509','372582','372950','372954','372989','373307','373348','373414','373450','373485','373509','373511','373574','373588','373610','373611','373625','373674','373836','373848','373939','373973','374065','374251','374355','374485','374586','374657','374739','374755','374821','374830','374850','374853','374864','374886','374940','374944','374973','374991','375001','375013','375049','375066','375070','375082','375083','375084','375090','375101','375103','375113','375115','375116','375117','375118','375119','375121','375123','375124','375127','375132','375134','375136','375162','375167','375169','375205','375223','375870','375923','376293','376345','376440','376614','376703','376721','376750','376773','376778','376796','376799','376818','376820','376825','376836','376839','376842','376843','376848','376853','376859','376865','376869','376877','376882','376884','376887','376892','376900','376918','376936','376937','376948','376950','376954','376958','376963','377070','377129','377136','377183','377194','377295','377332','377523','377775','377815','377816','377871','377923','377945','377955','378170','378181','378191','378306','378351','378485','378635','378686','378703','378763','378781','378785','378805','378819','378846','378856','378857','378859','378894','378939','378961','378963','378964','378992','379017','379043','379064','379072','379078','379096','379124','379141','379184','380622','380689','382095','382310','383543','383564','385603','387006','388970','389647','390798','392021','396827','398122','399413','399425','399687','399736','406707','406899','415744','423853','426461','427780','428069','432990','433374','433455','433655','435411','435525','435528','437235','437243','443767','444961','446823','448090','449065','449205','449213','449716','450411','451898','451953','451961','452077','452601','453573','454643','457773','457995','460026','461256','461498','463253','466102','471316','481194','482510','482609','485790','486040','487643','488632','490731','492511','493654','495041')";		
		return this.jdbcTemplate.queryForList(query);
	}

	public List<Map<String, Object>> getMaximumTicketIDList(
			List<Map<String, Object>> lowestPrtyScoreList) {
		List<Map<String, Object>> ticketList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<lowestPrtyScoreList.size();i++){
			String query=HELPDESK_SQLQueryConstants.SELECT_MAX_TICKET_ID;
			Map<String,Object> ticketMap=new HashMap<String, Object>();
			try{
			ticketMap=this.jdbcTemplate.queryForMap(query,new Object[]{lowestPrtyScoreList.get(i).get("EMPLOYEE_ID").toString()});
			ticketList.add(ticketMap);		
			}catch(EmptyResultDataAccessException e){
				ticketMap=new HashMap<String, Object>();
			}
			
		}
		
		return ticketList;
	}
	
	/**********END :Auto Assignment************/
	
	
	/*EMERGENCY L1 */
	public List<Map<String, Object>> getFunctionSpecificFieldsForTicketForEmergency(
			String ticketid, String functionid) {
		List<Map<String, Object>> fields = new ArrayList<Map<String,Object>>();
		try {
			fields = this.jdbcTemplate
					.queryForList(
							HELPDESK_SQLQueryConstants.SELECT_IC_IHD_TICKET_FUNCTION_SPECIFIC_FIELDS_EMERGENCY,
							ticketid, functionid);
		} catch (EmptyResultDataAccessException e) {
			fields =  new ArrayList<Map<String,Object>>();
		}
		return fields;
	}
	public int getAttachment_EmergencyApp(String ticketId){
		return this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_APPMAIL,
					new Object[] {ticketId});
	}
	public int getAttachment_EmergencyScript(String ticketId){
		return this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_SCRIPT,
					new Object[] {ticketId});
	}
	public int getAttachment_EmergencyTestRep(String ticketId){
		return this.jdbcTemplate.queryForInt(
					HELPDESK_SQLQueryConstants.SELECT_COUNT_ATTACHMENT_EMERGENCY_L1_TESTREPORT,
					new Object[] {ticketId});
	}
	
	public List<Map<String, Object>> getOrchestrationGUIDMapping(
			String ticketId, String catagoryID,int l_orchOdcId) {
		log.info("The Ticket Id Is : " + ticketId);
		log.info("jdbcTemplate : " + jdbcTemplate);
		return this.jdbcTemplate
				.queryForList(
						"select iofm.GU_ID, iotd.ORCH_VALUE from IC_ORCH_FIELD_MASTER iofm, IC_ORCH_TRANSACTION_DETAILS iotd "
						+ "where CATEGORY_ID=? and iofm.FIELD_ID = iotd.FIELD_ID and TICKET_ID=? and iofm.ORCH_ODC_ID=?",
						catagoryID, ticketId,l_orchOdcId);
	}

	public void closeTicketByOrchestration(String ticketId, String comment,
			Map<String, Object> map) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EditDAO getIHDDAO = (HELPDESK_EditDAO) ctx
				.getBean("IHDEditDAO");
		String status = getSLAStatusProcedureCall(ticketId);
		log.info("The SLA Status Is : " + status);
		int workflowId = this.jdbcTemplate
				.queryForInt(
						"select ST.STATE_ID from (select TD.WORKFLOW_STATE,MAS.WORKFLOW_ID from IC_IHD_TICKET_DETAILS TD "
								+ "JOIN IC_WORKFLOW_STATE_MASTER WSM on TD.WORKFLOW_STATE = WSM.STATE_ID JOIN IC_WORKFLOW_MASTER MAS "
								+ "on MAS.WORKFLOW_ID = WSM.WORKFLOW_ID where TICKET_ID =? )temp JOIN IC_WORKFLOW_STATE_MASTER ST "
								+ "on ST.WORKFLOW_ID = temp.WORKFLOW_ID and ST.NAME = 'Resolved/Closed'",
								ticketId);
		this.jdbcTemplate.update(HELPDESK_SQLQueryConstants.CLOSE_ORCH_TICKETS,workflowId,comment,status,ticketId);

		String procName = "insert_audit_log";
		String paramVal[] = { ticketId, "1", map.get("FUNCTION_ID").toString(),
				"1", "2548", "Resolved/Closed", "U",
				"SYSTEM",
				map.get("CREATED_DATE").toString(), "", status };
		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				this.jdbcTemplate.getDataSource());
		
		Map<String,Object> isOrchRequired=this.jdbcTemplate.queryForMap(HELPDESK_SQLQueryConstants.GET_AUTOMATION_FLAG,ticketId);
		
		if (isOrchRequired.size() > 0
				&& isOrchRequired.get("IS_AUTOMATION_REQUIRED").toString()
						.equalsIgnoreCase("Y")) {
			/*try {
				
				HDStatusUpdationClientDAO client = (HDStatusUpdationClientDAO) ctx
						.getBean("client");
				String jsonForTicketStatus = "{" + "\""
						+ ticketId + "\":"
						+ "\"Resolved/Closed\"" + "}";
				String updateStatus = client
						.getStatusTransitionFlag(jsonForTicketStatus);
				if (updateStatus.equalsIgnoreCase("SUCCESS")) {
					getIHDDAO.statusUpdationUnexceptionalCase(ticketId,
							"Resolved/Closed");
				}
			} catch (WebServiceIOException e) {
				log.error("Error Encountered while updating status in iTrack" + e
						+ "Ticket Id" + ticketId);
				getIHDDAO.statusUpdationExceptionalCase(ticketId, "Resolved/Closed");
			} catch (ConnectException e) {
				log.error("Error Encountered while updating status in iTrack" + e
						+ "Ticket Id" + ticketId);
				getIHDDAO.statusUpdationExceptionalCase(ticketId, "Resolved/Closed");
			}*/

		}
	}

	public List<Map<String, Object>> getTicketInfo(String ticketId) {
		return this.jdbcTemplate.queryForList(
				"select * from IC_IHD_TICKET_DETAILS where TICKET_ID=?",
				ticketId);
	}

	public String getSLAStatusProcedureCall(String ticketId) {
		String argName[] = { "TICKETS" };
		int argType[] = { Types.VARCHAR };
		String paramVal[] = { ticketId };
		String procName = "getColorCode";
		String SLAStatus="IN";
		List resJson = (List) DAOUtilObj.executeiconnectProcColourCode(
				procName, argName, argType, paramVal, jdbcTemplate.getDataSource());
		for (int i = 0; i < resJson.size(); i++) {
				Map amap = (Map) resJson.get(i);
				if(amap.get("COLOR_CODE").toString().equalsIgnoreCase("RED"))
				{
					SLAStatus="OUT";
				}
		}
		return SLAStatus;
	}
	/*CALCULATING SLA STATUS*/
	public Map<String, Object> getSLATime(String ticketID) {
		return this.jdbcTemplate.queryForMap(
				HELPDESK_SQLQueryConstants.IC_SLATIME, ticketID);
	}
	/*CALCULATING SLA STATUS*/

	/*
	 * This will insert the Field Id, Orch Values in IC_ORCH_TRANSACTION_DETAILS
	 * when editing the ticket
	 */
	public void updateOrchTransitionValues(JSONArray orchJson,
			String TICKET_ID, Map<String, Object> daodetails)
			throws JSONException {

		for (int i = 0; i < orchJson.length(); i++) {
			JSONObject jsonobjOrch = new JSONObject(orchJson.getString(i)
					.toString());
			String l_fieldId = jsonobjOrch.get("FIELD_ID").toString();
			String l_fieldValue = jsonobjOrch.get("FIELD_VALUE").toString();
			String l_fieldName = jsonobjOrch.get("FIELD_NAME").toString();
			if(l_fieldName.equalsIgnoreCase("Ticket Id"))
			{
				l_fieldValue = TICKET_ID;
			}
			if(l_fieldName.equalsIgnoreCase("Printer Server"))
			{
				l_fieldValue = daodetails.get("PRINTER_SERVER").toString();
			}	
			updateOrchValues(l_fieldId, l_fieldValue, TICKET_ID, daodetails);
			log.info(" successfully updated ");
		}

	}

	private void updateOrchValues(String f_fieldId, String f_fieldValue,
			String TICKET_ID, Map<String, Object> daodetails) {

		log.info("insering to IC_ORCH_TRANSACTION_DETAILS");
		if (f_fieldId != null && f_fieldValue != "") {
			try {
				this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.IC_ORCH_TRANSACTION_DETAILS,
						TICKET_ID, f_fieldId, f_fieldValue,
						(String) daodetails.get("createdby"),
						(String) daodetails.get("createddate"));

			} catch (Exception e) {

				log.error("Error in inserting orchestration fields for ticketid : "
						+ e);
			}

		}

	}

	public String getRequestedByTicketId(String ticketID) {

		return this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.IC_IHD_TICKET_RequestedBy,
				new Object[] { ticketID }, String.class);
	}

	public String getAutomationStatus(String catagoryId) {
		return this.jdbcTemplate.queryForObject(
				"select AUTOMATION_REQUIRED from IC_IHD_CATEGORY_MASTER where CATEGORY_ID='"
						+ catagoryId + "'", String.class);
	}

	public String getSubCatagoryIdBasedOnTicket(String ticketId) {
		return this.jdbcTemplate.queryForObject(
				"select SUB_CATEGORY_ID from IC_IHD_TICKET_DETAILS where TICKET_ID=?", String.class,new Object[] { ticketId });
	}
	
	/**
	 * returns the Iteration count from IC_ORCH_AUTOMATION_LOG for this ticket
	 * 
	 */
	
	public List<Map<String, Object>> getAutomationIternationCount(String ticketId) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SQLQueryConstants.IC_IHD_TICKET_ITERATION,
				new Object[] { ticketId });
	}
	
	public boolean getLocationBasedTicketForAutoMation(String loc){
		String str = this.jdbcTemplate.queryForObject(
				"select IS_ODC from IC_ORCH_LOCATION_PRINTSERVER_MAPPING where LOC_DET_ID=?", String.class,new Object[] { loc });
		if(str.equalsIgnoreCase("N")){
			return true;
		}else{
			return false;
		}
	}

	public boolean logAutomatedDataForOrchestration(String ticketId,
			String comment, String status) {
		
		log.info("The Status For Loging Information : " + status);
		log.info("The Ticket Id In logAutomated Data : " + ticketId);
		log.info("The Comment for logautomation : " + comment);
		int totalCount = this.jdbcTemplate.queryForObject(
				"select MAX(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID = ?", Integer.class,new Object[] { ticketId }) == null ? 0
				: this.jdbcTemplate.queryForObject(
						"select MAX(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID = ?", Integer.class,new Object[] { ticketId });
		if (totalCount == 0) {
			try {
				this.jdbcTemplate
				.update(HELPDESK_SQLQueryConstants.IC_ORCH_AUTOMATION_LOG,ticketId,1,"Automation Status : Pending",comment,"SYSTEM");
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			try {
				Integer counter = this.jdbcTemplate.queryForObject(
						"select MAX(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID=?", Integer.class,new Object[] { ticketId });
				counter = counter + 1;
				String l_statusMsg="Automation Status : " + status;
				this.jdbcTemplate
				.update(HELPDESK_SQLQueryConstants.IC_ORCH_AUTOMATION_LOG,ticketId,counter,l_statusMsg,comment,"SYSTEM");
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	public boolean getAutomationHelpDeskStatus(String ticketId) {
		int integer = this.jdbcTemplate.queryForObject(
				"select MAX(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID = ?", Integer.class,new Object[] { ticketId }) == null ? 0
				: this.jdbcTemplate.queryForObject(
						"select MAX(ITERATION_COUNT) from IC_ORCH_AUTOMATION_LOG where TICKET_ID =?", Integer.class,new Object[] { ticketId });
		log.info("The Value I Got Is : " + integer);
		if (integer > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getValidTicketIdInAutomation(String ticketId) {
		String str = this.jdbcTemplate.queryForObject(
				"select DISTINCT TICKET_ID from IC_ORCH_TRANSACTION_DETAILS where TICKET_ID=?", String.class,new Object[] { ticketId });
		if (str == null || str.equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}

	public void updatePrivateNoteInTicketDetails(String ticketId, String comment){
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTimeZone(TimeZone.getTimeZone("GMT"));
		Map<String, Object> dateobject = new HashMap<String, Object>();
		dateobject.put("datetime", CustomDateFormatConstants
				.convertToEST(date.format(new Date())));
		String privateNotes = this.jdbcTemplate
				.queryForObject(
						HELPDESK_SQLQueryConstants.SELECT_PRIVATE_NOTES_IC_IHD_TICKET_DETAILS,
						new Object[] { ticketId }, String.class);
		String additionalinfo = "By :OrchEngg1,\nDate:"
				+ dateobject.get("datetime").toString() + ",\nComments:"
				+ comment + "\n--------------------\n"
				+ converter.convertSpecialChars(privateNotes);
		
		this.jdbcTemplate
		.update("update IC_IHD_TICKET_DETAILS set PRIVATE_NOTES=? where TICKET_ID=?",additionalinfo,ticketId);
	}
	
	
	public String getOperatinalTimeFlag(String groupId, String functionid){
		return this.jdbcTemplate.queryForObject(HELPDESK_SQLQueryConstants.GET_OPERATIONAL_TIME_FLAG,new Object[] { groupId ,functionid}, String.class);
	}
	public String updateMultipleTickets(String jsonString, String loginID){
		try {
			JSONObject jsonobj = new JSONObject(jsonString.replace("\n",
			"brlinebreakbreak"));
			String slaStatus="IN";
			String outOfSlaReason="";
			String outOfSlaComments="";
			String l_resolutionStatus=null;
			if (null != jsonobj.get("RESOLUTION_STATUS")
					&& !jsonobj.get("RESOLUTION_STATUS").toString()
							.equalsIgnoreCase("")) {
				l_resolutionStatus=jsonobj.get("RESOLUTION_STATUS").toString();

			}
			
			if (null != jsonobj.get("OUT_OF_SLA_REASON")
					&& !jsonobj.get("OUT_OF_SLA_REASON").toString()
							.equalsIgnoreCase("")) {
				slaStatus="OUT";
				outOfSlaReason=jsonobj.get("OUT_OF_SLA_REASON").toString();
				outOfSlaComments= jsonobj.get("OUT_OF_SLA_COMMENTS").toString().replace(SPECIAL_CHAR_REPLACE, "\n");
				this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.UPDATE_TICKET_DETAILS_SLA_OUT, "20",
						jsonobj.get("RESOLUTION_COMMENTS").toString().replace(SPECIAL_CHAR_REPLACE, "\n"),outOfSlaReason,outOfSlaComments,slaStatus,l_resolutionStatus ,loginID, jsonobj
								.get("TICKET_ID"));
			}else{
				this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.UPDATE_TICKET_DETAILS_SLA_IN, "20",
						jsonobj.get("RESOLUTION_COMMENTS").toString().replace(SPECIAL_CHAR_REPLACE, "\n"),slaStatus,l_resolutionStatus, loginID, jsonobj
								.get("TICKET_ID"));
			}
			HashMap<String, Object> auditdetails = new HashMap<String, Object>();
			auditdetails.put("RESOLUTION_COMMENTS", jsonobj.get("RESOLUTION_COMMENTS").toString().replace(SPECIAL_CHAR_REPLACE, "\n"));
			if(!outOfSlaReason.equalsIgnoreCase("")){
			auditdetails.put("OUT_OF_SLA_REASON", jsonobj.get("OUT_OF_SLA_REASON_NAME"));
			auditdetails.put("OUT_OF_SLA_COMMENTS", outOfSlaComments);
			}
			auditdetails.put("SLA_STATUS", slaStatus);
			
			String procName = "insert_audit_log";
			
			String paramVal[] = { jsonobj.get("TICKET_ID").toString(), "1", "256",
					"1", "0", "Resolved/Closed", "U",
					loginID,
					CustomDateFormatConstants.creationDateGMTFormat(), MapToStringUtil.getStringForMap(auditdetails),"Resolved/Closed" };
			DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
					this.jdbcTemplate.getDataSource());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		return "SUCCESS";
	}
	
	public Map<String, Object> getAllProjectDetails(String ticketId){
		return this.jdbcTemplate.queryForMap(HELPDESK_SQLQueryConstants.GET_ALL_PROJECT_DETAILS_FOR_TICKET,ticketId);
		
	}
	
	public String getManagerIDFromTicketDetails(String ticketId) {
		return this.jdbcTemplate.queryForObject(
				HELPDESK_SQLQueryConstants.IC_IHD_TICKET_DETAILS_MANAGERID,
				String.class, new Object[] { ticketId });
	}

}
