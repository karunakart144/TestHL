/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.igate.iconnect.BO.ADMIN_ModifyLocation;
import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Group;
import com.igate.iconnect.BO.HELPDESK_GroupMembersAvailability;
import com.igate.iconnect.BO.HELPDESK_Lock;
import com.igate.iconnect.BO.HELPDESK_ScoreDetail;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MailTracker;
import com.igate.iconnect.BO.TechCR;
import com.igate.iconnect.BO.WORKSPACEMGMT_SeatingDetails;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.exception.DateParsingException;

public class COMMON_PaginationHelper<E> {
	private static final String TICKET_ID = "TICKET_ID";
	private static final String LOCATION_AREA = "LOCATION_AREA";
	private static final String CREATED_DATE = "CREATED_DATE";
	private static final String FUNCTION = "FUNCTION";
	private static final String ASSIGNED_TO = "ASSIGNED_TO";
	private static final String SUBJECT = "SUBJECT";
	private static final String ASSIGNED_GROUP_ID = "ASSIGNED_GROUP_ID";
	private static final String WORKFLOW_STATE = "WORKFLOW_STATE";
	private static final String ECT = "ECT";
	private static final String SUBCATEGORY = "SUBCATEGORY";
	private static final String CATEGORY = "CATEGORY";
	private static final String PRIORITY = "PRIORITY";
	private static final String REQUESTOR_NAME = "REQUESTOR_NAME";
	private static final String SLA_STATUS = "SLA_STATUS";
	private static final String CLOSED_DATE = "CLOSED_DATE";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String ORGANIZATION = "ORGANIZATION";
	private static final String FUNCTION_ID = "FUNCTION_ID";
	private static final String PRIORITY_ID = "PRIORITY_ID";
	private static final String LOCATION_ID = "LOCATION_ID";
	private static final String SUB_CATEGORY_ID = "SUB_CATEGORY_ID";
	private static final String LOC_DET_ID = "LOC_DET_ID";
	private static final String ATTACHMENT = "ATTACHMENT";
	private static final String IS_CHANGE_REQUEST = "IS_CHANGE_REQUEST";
	private static final String LOCK = "LOCK";
	private static final String AUTOMATION = "AUTOMATION";
	private static final String LOCKED_BY = "LOCKED_BY";
	//private static final String PREMIUM = "PREMIUM";
	private static final String ISSUE_ID = "ISSUE_ID";
	private static final String LOCATION_NAME = "LOCATION_NAME";
	private static final String ACTION_DESC = "ACTION_DESC";
	private static final String GROUP_NAME = "GROUP_NAME";
	private static final String ASSIGNED_EMPIDANDNAME = "ASSIGNED_EMPIDANDNAME";
	private static final String PARENT_CATEGORY_NAME = "PARENT_CATEGORY_NAME";
	private static final String CHILD_CATEGORY_NAME = "CHILD_CATEGORY_NAME";
	private static final String SCHEDULED_START_DATE = "SCHEDULED_START_DATE";
	private static final String SCHEDULED_END_DATE = "SCHEDULED_END_DATE";
	private static final String SOURCE = "SOURCE";
	private static final String IS_CLIENT_INITIATED = "IS_CLIENT_INITIATED";
	private static final String EFFORT = "EFFORT";
	private static final String ROLLBACK_COMMENTS = "ROLLBACK_COMMENTS";
	private static final String RISK_INVOLVED = "RISK_INVOLVED";
	private static final String RCA_REQUIRED = "RCA_REQUIRED";
	private static final String CREATED_BY = "CREATED_BY";
	private static final String APPROVER_EMPIDANDNAME = "APPROVER_EMPIDANDNAME";
	private static final String SEVERITY_NAME = "SEVERITY_NAME";
	private static final String CREATION_DATE = "CREATION_DATE";
	private static final String EMPLOYEE_NAME = "EMPLOYEE_NAME";
	private static final String LOCKED_DATE = "LOCKED_DATE";
	private static final String IS_PREMIUM_MAIL = "IS_PREMIUM_MAIL";
	private static final String STATUS = "STATUS";
	private static final String MAIL_ID = "MAIL_ID";
	private static final String TO_ADDRESS = "TO_ADDRESS";
	private static final String FROM_ADDRESS = "FROM_ADDRESS";
	private static final String ATTACHMENT_PATH = "ATTACHMENT_PATH";
	private static final String SEVERITY_ID = "SEVERITY_ID";
	private static final String RECEIVED_DATE = "RECEIVED_DATE";
	private static final String REFERENCE_ID = "REFERENCE_ID";
	private static final String REASON = "REASON";
	private static final String ATTACHMENT_NAME = "ATTACHMENT_NAME";
	private static final String AUDIT_ID = "AUDIT_ID";
	private static final String MODIFIED_DATE = "MODIFIED_DATE";
	private static final String MODIFIED_BY = "MODIFIED_BY";
	private static final String REMARKS = "REMARKS";
	private static final String DATE_TIME = "DATE_TIME";
	// Added for exceptional start and end date
	private static final String EXCEPTION_APP_ST_DATE = "EXCEPTION_APP_ST_DATE";
	private static final String EXCEPTION_APP_END_DATE = "EXCEPTION_APP_END_DATE";
	// Added DU Name for executives
	private static final String DU_NAME = "DU_NAME";
	private static final String CHECK_FCS = "CHECK_FCS";
	private static final String ACTIVITY_TYPE="ACTIVITY_TYPE";
	boolean expAppDates = false;
	boolean duName = false;
	boolean checkFCS = false;
	boolean checkActivityType=false;
	boolean checkAutomation=false;

	public COMMON_Pagination<HelpDesk> fetchHDPage(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend,final COMMON_ExecuteProcedure DAOUtilObj) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		// check the query contains EXCEPTION_APP_ST_DATE
		if (sqlFetchRows.contains("EXCEPTION_APP_ST_DATE")) {
			expAppDates = true;
		}
		if (sqlFetchRows.contains("DU_NAME")) {
			duName = true;
		}
		if (sqlFetchRows.contains("CHECK_FCS")) {
			checkFCS = true;
		}
		if (sqlFetchRows.contains("ACTIVITY_TYPE")) {
			checkActivityType = true;
		}
		if (sqlFetchRows.contains("AUTOMATION")) {
			checkAutomation = true;
		}
		// create the page object
		final COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HelpDesk> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<HelpDesk>() {

					public HelpDesk mapRow(ResultSet rs, int count)
							throws SQLException {
						HelpDesk obj = new HelpDesk();
						HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
						obj.setTICKET_ID(rs.getString(TICKET_ID));
						obj.setLOCATION_AREA(rs.getString(LOCATION_AREA));
						String createdDate = rs.getString(CREATED_DATE);
						if (createdDate != null)
							obj.setCREATED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(
											createdDate, userTimeZoneToSend));
						else
							obj.setCREATED_DATE("-");
						obj.setFUNCTION(rs.getString(FUNCTION));
						obj.setSUBJECT(converter.convertSpecialChars(rs
								.getString(SUBJECT)));
						String assignedTo = rs.getString(ASSIGNED_TO);
						obj.setASSIGNED_TO(assignedTo);
						obj.setASSIGNED_GROUP_ID(rs
								.getString(ASSIGNED_GROUP_ID));
						obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
						String ect = rs.getString(ECT);
						if (ect != null)
							obj.setECT(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(ect,
											userTimeZoneToSend));
						else
							obj.setECT("-");
						obj.setSUBCATEGORY(rs.getString(SUBCATEGORY));
						obj.setCATEGORY(rs.getString(CATEGORY));
						obj.setPRIORITY(rs.getString(PRIORITY));
						obj.setREQUESTOR_NAME(rs.getString(REQUESTOR_NAME));
						obj.setSLA_STATUS(rs.getString(SLA_STATUS));
						String closedDate = rs.getString(CLOSED_DATE);
						if (closedDate != null)
							obj.setCLOSED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(closedDate,
											userTimeZoneToSend));
						else
							obj.setCLOSED_DATE("-");
						obj.setDESCRIPTION(converter.convertSpecialChars(rs
								.getString(DESCRIPTION)));
						obj.setORGANIZATION(rs.getString(ORGANIZATION));
						obj.setFUNCTION_ID(rs.getString(FUNCTION_ID));
						obj.setPRIORITY_ID(rs.getString(PRIORITY_ID));
						obj.setLOCATION_ID(rs.getString(LOCATION_ID));
						obj.setSUB_CATEGORY_ID(rs.getString(SUB_CATEGORY_ID));
						obj.setLOC_DET_ID(rs.getString(LOC_DET_ID));
						obj.setATTACHMENT(rs.getString(ATTACHMENT));
						obj.setIS_CHANGE_REQUEST(rs
								.getString(IS_CHANGE_REQUEST));
						obj.setLOCK(rs.getString(LOCK));
						obj.setLOCKED_BY(rs.getString(LOCKED_BY));
						//obj.setPREMIUM(rs.getString(PREMIUM));
						if (expAppDates) {
							obj.setEXCEPTION_APP_ST_DATE(rs
									.getString(EXCEPTION_APP_ST_DATE));
							obj.setEXCEPTION_APP_END_DATE(rs
									.getString(EXCEPTION_APP_END_DATE));
						}
						if (duName) {
							obj.setDU_NAME(rs.getString(DU_NAME));
						}
						if (checkFCS) {
							obj.setCHECK_FCS(rs.getString(CHECK_FCS));
						}
						if(checkActivityType)
							obj.setACTIVITY_TYPE(rs.getString(ACTIVITY_TYPE));

						//added for orchestration
						if(checkAutomation)
						{
							obj.setAUTOMATION(rs.getString(AUTOMATION));
						}
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	public COMMON_Pagination<HelpDesk> fetchHDColourCodePage(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend,final COMMON_ExecuteProcedure DAOUtilObj) {
		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		// check the query contains EXCEPTION_APP_ST_DATE
		if (sqlFetchRows.contains("EXCEPTION_APP_ST_DATE")) {
			expAppDates = true;
		}
		if (sqlFetchRows.contains("DU_NAME")) {
			duName = true;
		}
		if (sqlFetchRows.contains("CHECK_FCS")) {
			checkFCS = true;
		}
		if (sqlFetchRows.contains("ACTIVITY_TYPE")) {
			checkActivityType = true;
		}

		// create the page object
		final COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		
		List<HelpDesk> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<HelpDesk>() {

					public HelpDesk mapRow(ResultSet rs, int count)
							throws SQLException {
						HelpDesk obj = new HelpDesk();
						HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
						obj.setTICKET_ID(rs.getString(TICKET_ID));
						obj.setLOCATION_AREA(rs.getString(LOCATION_AREA));
						String createdDate = rs.getString(CREATED_DATE);
						if (createdDate != null)
							obj.setCREATED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(
											createdDate, userTimeZoneToSend));
						else
							obj.setCREATED_DATE("-");
						obj.setFUNCTION(rs.getString(FUNCTION));
						obj.setSUBJECT(converter.convertSpecialChars(rs
								.getString(SUBJECT)));
						String assignedTo = rs.getString(ASSIGNED_TO);
						obj.setASSIGNED_TO(assignedTo);
						obj.setASSIGNED_GROUP_ID(rs
								.getString(ASSIGNED_GROUP_ID));
						obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE)); 
						String ect = rs.getString(ECT);
						if (ect != null)
							obj.setECT(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(ect,
											userTimeZoneToSend));
						else
							obj.setECT("-");
						obj.setSUBCATEGORY(rs.getString(SUBCATEGORY));
						obj.setCATEGORY(rs.getString(CATEGORY));
						obj.setPRIORITY(rs.getString(PRIORITY));
						obj.setREQUESTOR_NAME(rs.getString(REQUESTOR_NAME));
						obj.setSLA_STATUS(rs.getString(SLA_STATUS));
						String closedDate = rs.getString(CLOSED_DATE);
						if (closedDate != null)
							obj.setCLOSED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(closedDate,
											userTimeZoneToSend));
						else
							obj.setCLOSED_DATE("-");
						obj.setDESCRIPTION(converter.convertSpecialChars(rs
								.getString(DESCRIPTION)));
						obj.setORGANIZATION(rs.getString(ORGANIZATION));
						obj.setFUNCTION_ID(rs.getString(FUNCTION_ID));
						obj.setPRIORITY_ID(rs.getString(PRIORITY_ID));
						obj.setLOCATION_ID(rs.getString(LOCATION_ID));
						obj.setSUB_CATEGORY_ID(rs.getString(SUB_CATEGORY_ID));
						obj.setLOC_DET_ID(rs.getString(LOC_DET_ID));
						obj.setATTACHMENT(rs.getString(ATTACHMENT));
						obj.setIS_CHANGE_REQUEST(rs
								.getString(IS_CHANGE_REQUEST));
						obj.setLOCK(rs.getString(LOCK));
						obj.setLOCKED_BY(rs.getString(LOCKED_BY));
						//obj.setPREMIUM(rs.getString(PREMIUM));
						//L2 1184 Start
						ResultSetMetaData rsmd = rs.getMetaData();
					    int columnsCount = rsmd.getColumnCount();
					    for (int i = 1; i <= columnsCount; i++) {
					        if ("CORE_ID".equals(rsmd.getColumnName(i))) {
					        	obj.setCORE_ID(rs.getString("CORE_ID"));
					        }
					        if ("ALLOW_CLOSEABLE_FLAG".equals(rsmd.getColumnName(i))) {
					        	obj.setALLOW_CLOSEABLE_FLAG(rs.getString("ALLOW_CLOSEABLE_FLAG"));
					        }
					    }
					    //L2 1184 End
						if (expAppDates) {
							obj.setEXCEPTION_APP_ST_DATE(rs
									.getString(EXCEPTION_APP_ST_DATE));
							obj.setEXCEPTION_APP_END_DATE(rs
									.getString(EXCEPTION_APP_END_DATE));
						}
						if (duName) {
							obj.setDU_NAME(rs.getString(DU_NAME));
						}
						if (checkFCS) {
							obj.setCHECK_FCS(rs.getString(CHECK_FCS));
						}
						if(checkActivityType)
							obj.setACTIVITY_TYPE(rs.getString(ACTIVITY_TYPE));
						return obj;
						
						
					}

				});
		
		
		if(resultList.size()>0)
		{
		String ticketIDvar="";
		for (Iterator<HelpDesk> hdIterator = resultList.iterator(); hdIterator
			.hasNext();) {
				final HelpDesk helpDesk = hdIterator.next();
				ticketIDvar=ticketIDvar+","+helpDesk.getTICKET_ID();
		}
		ticketIDvar=ticketIDvar.substring(1, ticketIDvar.length());
		String argName[] = { "TICKETS" };
		int argType[] = { Types.VARCHAR };
		String paramVal[] = { ticketIDvar };
		String procName = "getColorCode";
		
		
		List resJson = (List) DAOUtilObj.executeiconnectProcColourCode(
				procName, argName, argType, paramVal, jt.getDataSource());
		for (Iterator<HelpDesk> hdIterator = resultList.iterator(); hdIterator
				.hasNext();) {
			final HelpDesk helpDesk = hdIterator.next();
			for (int i = 0; i < resJson.size(); i++) {
				Map amap = (Map) resJson.get(i);
				if (amap.get("TICKET_ID").toString().equalsIgnoreCase(
						helpDesk.getTICKET_ID())) {
					helpDesk.setCOLOUR_CODE(amap.get("COLOR_CODE").toString());
					if(null!=amap.get("TIME_REMAINING"))
						helpDesk.setTIME_REMAINING(amap.get("TIME_REMAINING").toString());
					else
						helpDesk.setTIME_REMAINING("-");
					resJson.remove(helpDesk.getTICKET_ID());
					break;
				}
			}
		}
		}
		
		page.setPageItems(resultList);
		return page;
	}
	public COMMON_Pagination<HelpDesk> fetchMasterPage(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HelpDesk> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<HelpDesk>() {

					public HelpDesk mapRow(ResultSet rs, int count)
							throws SQLException {
						HelpDesk obj = new HelpDesk();
						HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
						obj.setTICKET_ID(rs.getString(TICKET_ID));
						// obj.setLOCATION_AREA(rs.getString(LOCATION_AREA));
						String createdDate = rs.getString(CREATED_DATE);
						if (createdDate != null)
							obj.setCREATED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(
											createdDate, userTimeZoneToSend));
						else
							obj.setCREATED_DATE("-");
						obj.setFUNCTION(rs.getString(FUNCTION));
						obj.setSUBJECT(converter.convertSpecialChars(rs
								.getString(SUBJECT)));
						String assignedTo = rs.getString(ASSIGNED_TO);
						obj.setASSIGNED_TO(assignedTo);
						obj.setASSIGNED_GROUP_ID(rs
								.getString(ASSIGNED_GROUP_ID));
						/*
						 * //Added by Sali
						 * if(!assignedTo.equalsIgnoreCase("-")){ try{ String
						 * assingedGroupId = rs.getString("ASSIGNED_GROUP_ID");
						 * obj.setASSIGNED_GROUP_ID(assingedGroupId);
						 * }catch(Exception e){ //knowingly suppressing the
						 * exception }
						 * 
						 * }
						 */
						obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
						String ect = rs.getString(ECT);
						if (ect != null)
							obj.setECT(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(ect,
											userTimeZoneToSend));
						else
							obj.setECT("-");
						// obj.setECT(ect);
						obj.setSUBCATEGORY(rs.getString(SUBCATEGORY));
						obj.setCATEGORY(rs.getString(CATEGORY));
						obj.setREQUESTOR_NAME(rs.getString(REQUESTOR_NAME));
						String closedDate = rs.getString(CLOSED_DATE);
						if (closedDate != null)
							obj.setCLOSED_DATE(CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(closedDate,
											userTimeZoneToSend));
						else
							obj.setCLOSED_DATE("-");
						obj.setDESCRIPTION(converter.convertSpecialChars(rs
								.getString(DESCRIPTION)));
						obj.setORGANIZATION(rs.getString(ORGANIZATION));
						obj.setFUNCTION_ID(rs.getString(FUNCTION_ID));
						// obj.setLOCATION_ID(rs.getString(LOCATION_ID));
						obj.setSUB_CATEGORY_ID(rs.getString(SUB_CATEGORY_ID));
						obj.setATTACHMENT(rs.getString(ATTACHMENT));
						obj.setIS_CHANGE_REQUEST(rs
								.getString(IS_CHANGE_REQUEST));
						obj.setLOCK(rs.getString(LOCK));
						obj.setLOCKED_BY(rs.getString(LOCKED_BY));
						//obj.setPREMIUM(rs.getString(PREMIUM));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}
	/*****************************************Admin Console************************************************************************/
	public COMMON_Pagination<HELPDESK_Group> fetchGrpPage(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,String dataQueryForGrp,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend) {
		final COMMON_Pagination<HELPDESK_Group> page = new COMMON_Pagination<HELPDESK_Group>();
		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		int startcount=(Integer) args[0];
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}		
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<Map<String, Object>> subCategoryList =new ArrayList<Map<String, Object>>();
		subCategoryList=jt.queryForList(sqlFetchRows,args);
		
		List<Map<String, Object>> grpList =new ArrayList<Map<String, Object>>();
		grpList=jt.queryForList(dataQueryForGrp);
		
		List<HELPDESK_Group> filteredGrpList =new ArrayList<HELPDESK_Group>();
		
		if(subCategoryList!=null && grpList!=null){
			if(subCategoryList.size()>0 && grpList.size()>0){
				for (Iterator<Map<String, Object>> iterator = subCategoryList.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();					
					String subCategoryID= map.get("SUB_CATEGORY_ID").toString();
					
					Map<String, Object> newGrpMap =new HashMap<String, Object>();	
					List<Map<String, Object>> newGrpList =new ArrayList<Map<String, Object>>();
					HELPDESK_Group newDataMap=new HELPDESK_Group();
					for (Iterator<Map<String, Object>> iterator2 = grpList.iterator(); iterator2.hasNext();) {						
						Map<String, Object> grpMap = (Map<String, Object>) iterator2.next();						
						String subCatID=grpMap.get("SUB_CATEGORY_ID").toString();						
						if(subCategoryID.equalsIgnoreCase(subCatID)){
							newGrpMap.put("GROUP_ID", grpMap.get("GROUP_ID").toString());
							newGrpMap.put("GROUP_NAME", grpMap.get("GROUP_NAME").toString());
							newGrpList.add(newGrpMap);
							newGrpMap =new HashMap<String, Object>();
						}						
					}			
					newDataMap.setCATEGORY_ID( map.get("CATEGORY_ID").toString());
					newDataMap.setCATEGORY_NAME(map.get("CATEGORY_NAME").toString());
					newDataMap.setSUB_CATEGORY_ID(map.get("SUB_CATEGORY_ID").toString());
					newDataMap.setSUB_CATEGORY_NAME(map.get("SUB_CATEGORY_NAME").toString());
					newDataMap.setRowCountPage(startcount);
					newDataMap.setGROUP_MAP(newGrpList);	
					startcount++;
					filteredGrpList.add(newDataMap);
					newGrpMap =new HashMap<String, Object>();				
					newGrpList =new ArrayList<Map<String, Object>>();
				}				
			}
		}	
		page.setPageItems(filteredGrpList);
		
		return page;
	}

	public COMMON_Pagination<HELPDESK_Category> fetchCategoryPage(final JdbcTemplate jdbcTemplate,final String queryForCount, final String dataQuery,
			final Object args[],final int pageNo,final int pageSize,
			final int userTimeZoneToSend){
		final COMMON_Pagination<HELPDESK_Category> page = new COMMON_Pagination<HELPDESK_Category>();		
		final int rowCount=jdbcTemplate.queryForInt(queryForCount);
		final int startCount=(Integer)args[0];
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		
		List<HELPDESK_Category> resultList = jdbcTemplate.query(dataQuery, args,
				new RowMapper<HELPDESK_Category>() {
					public HELPDESK_Category mapRow(ResultSet rs, int count)
							throws SQLException {
						HELPDESK_Category obj = new HELPDESK_Category();
						obj.setFUNCTION_ID(rs.getString("FUNCTION_ID"));
						obj.setFUNCTION_NAME(rs.getString("FUNCTION_NAME"));
						obj.setCATEGORY_ID(rs.getString("CATEGORY_ID"));
						obj.setCATEGORY_NAME(rs.getString("CATEGORY_NAME"));
						obj.setCATEGORY_STATUS(rs.getString("CATEGORY_STATUS"));
						obj.setSUBCATEGORY_ID(rs.getString("SUBCATEGORY_ID"));
						obj.setSUBCATEGORY_NAME(rs.getString("SUBCATEGORY_NAME"));
						obj.setSUBCATEGORY_STATUS(rs.getString("SUBCATEGORY_STATUS"));
						obj.setAPPROVER_LEVEL_1(rs.getString("APPROVER_LEVEL_1"));
						obj.setAPPROVER_LEVEL_2(rs.getString("APPROVER_LEVEL_2"));
						obj.setAPPROVER_LEVEL_3(rs.getString("APPROVER_LEVEL_3"));
						obj.setRECOMMENDED_PRIORITY(rs.getString("RECOMMENDED_PRIORITY"));
						obj.setIS_CHANGE_REQUEST(rs.getString("IS_CHANGE_REQUEST"));
						obj.setLINK(rs.getString("LINK"));//Added by Sali
						obj.setSTART_COUNT_PAGE(String.valueOf(startCount));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;		
	}
	
	public COMMON_Pagination<HELPDESK_Group> fetchGrpPageDept(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,String dataQueryForGrp,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend) {
		final COMMON_Pagination<HELPDESK_Group> page = new COMMON_Pagination<HELPDESK_Group>();
		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		int startcount=(Integer) args[0];
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}		
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<Map<String, Object>> subCategoryList =new ArrayList<Map<String, Object>>();
		subCategoryList=jt.queryForList(sqlFetchRows,args);
		
		List<Map<String, Object>> grpList =new ArrayList<Map<String, Object>>();
		grpList=jt.queryForList(dataQueryForGrp);
		
		List<HELPDESK_Group> filteredGrpList =new ArrayList<HELPDESK_Group>();
		
		if(subCategoryList!=null && grpList!=null){
			if(subCategoryList.size()>0 && grpList.size()>0){
				for (Iterator<Map<String, Object>> iterator = subCategoryList.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();					
					String subCategoryID= map.get("SUB_CATEGORY_ID").toString();
					String deptID=map.get("DEPT_ID").toString();
					Map<String, Object> newGrpMap =new HashMap<String, Object>();	
					List<Map<String, Object>> newGrpList =new ArrayList<Map<String, Object>>();
					HELPDESK_Group newDataMap=new HELPDESK_Group();
					for (Iterator<Map<String, Object>> iterator2 = grpList.iterator(); iterator2.hasNext();) {						
						Map<String, Object> grpMap = (Map<String, Object>) iterator2.next();						
						String subCatID=grpMap.get("SUB_CATEGORY_ID").toString();		
						String depID=grpMap.get("DEPT_ID").toString();	
						if(subCategoryID.equalsIgnoreCase(subCatID)  && deptID.equalsIgnoreCase(depID)){
							newGrpMap.put("GROUP_ID", grpMap.get("GROUP_ID").toString());
							newGrpMap.put("GROUP_NAME", grpMap.get("GROUP_NAME").toString());
							newGrpList.add(newGrpMap);
							newGrpMap =new HashMap<String, Object>();
						}						
					}			
					newDataMap.setCATEGORY_ID( map.get("CATEGORY_ID").toString());
					newDataMap.setCATEGORY_NAME(map.get("CATEGORY_NAME").toString());
					newDataMap.setSUB_CATEGORY_ID(map.get("SUB_CATEGORY_ID").toString());
					newDataMap.setSUB_CATEGORY_NAME(map.get("SUB_CATEGORY_NAME").toString());
					newDataMap.setDEPT_ID(map.get("DEPT_ID").toString());
					newDataMap.setDEPT_NAME(map.get("DEPT_NAME").toString());
					newDataMap.setRowCountPage(startcount);
					newDataMap.setGROUP_MAP(newGrpList);	
					startcount++;
					filteredGrpList.add(newDataMap);
					newGrpMap =new HashMap<String, Object>();				
					newGrpList =new ArrayList<Map<String, Object>>();
				}				
			}
		}	
		page.setPageItems(filteredGrpList);
		
		return page;
	}
	
	/*****************************************END: Admin Console************************************************************************/
	public COMMON_Pagination<TechCR> fetchTechCRPage(final JdbcTemplate jt,
			final String sqlCountRows, final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize,
			final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		
		// create the page object
		final COMMON_Pagination<TechCR> page = new COMMON_Pagination<TechCR>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<TechCR> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<TechCR>() {
					public TechCR mapRow(ResultSet rs, int count)
							throws SQLException {
						TechCR obj = new TechCR();
						HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
						obj.setISSUE_ID(rs.getString(ISSUE_ID));
						obj.setLOCATION_NAME(rs.getString(LOCATION_NAME));
						obj.setACTION_DESC(rs.getString(ACTION_DESC));
						obj.setGROUP_NAME(rs.getString(GROUP_NAME));
						obj.setASSIGNED_EMPIDANDNAME(rs
								.getString(ASSIGNED_EMPIDANDNAME));
						obj.setPARENT_CATEGORY_NAME(rs
								.getString(PARENT_CATEGORY_NAME));
						obj.setCHILD_CATEGORY_NAME(rs
								.getString(CHILD_CATEGORY_NAME));
						obj.setSCHEDULED_START_DATE(rs
								.getString(SCHEDULED_START_DATE));
						obj.setSCHEDULED_END_DATE(rs
								.getString(SCHEDULED_END_DATE));
						obj.setSERVER_DEVICE(rs.getString(SOURCE));
						obj.setSUBJECT(converter.convertSpecialChars(rs
								.getString(SUBJECT)));
						obj.setDESCRIPTION(converter.convertSpecialChars(rs
								.getString(DESCRIPTION)));
						obj.setIS_CLIENT_INITIATED(rs
								.getString(IS_CLIENT_INITIATED));
						obj.setEFFORT(rs.getString(EFFORT));
						obj.setROLLBACK_COMMENTS(rs
								.getString(ROLLBACK_COMMENTS));
						obj.setENGINEER_COMMENTS(rs
								.getString("ENGINEER_COMMENTS"));
						obj.setRISK_INVOLVED(rs.getString(RISK_INVOLVED));
						obj.setRCA_REQUIRED(rs.getString(RCA_REQUIRED));
						obj.setCREATED_BY(rs.getString(CREATED_BY));
						obj.setCREATION_DATE(rs.getString(CREATION_DATE));
						obj
								.setWORKFLOW_STATE_NAME(rs
										.getString(WORKFLOW_STATE));
						obj.setAPPROVER_EMPIDANDNAME(rs
								.getString(APPROVER_EMPIDANDNAME));
						obj.setSEVERITY_NAME(rs.getString(SEVERITY_NAME));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	public COMMON_Pagination<HELPDESK_Lock> fetchLockedTicketPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HELPDESK_Lock> page = new COMMON_Pagination<HELPDESK_Lock>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HELPDESK_Lock> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<HELPDESK_Lock>() {
					public HELPDESK_Lock mapRow(ResultSet rs, int count)
							throws SQLException {
						HELPDESK_Lock obj = new HELPDESK_Lock();
						obj.setTICKET_ID(rs.getString(TICKET_ID));
						obj.setEMPLOYEE_NAME(rs.getString(EMPLOYEE_NAME));
						obj.setPREMIUM("No");
						String lockedDate = rs.getString(LOCKED_DATE);
						if (lockedDate != null)
							lockedDate = CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(lockedDate,
											userTimeZoneToSend);
						obj.setLOCKED_DATE(lockedDate);
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	// L2_3186: for Premium Locked Tickets display
	public COMMON_Pagination<HELPDESK_Lock> fetchPremiumLockedTicketPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HELPDESK_Lock> page = new COMMON_Pagination<HELPDESK_Lock>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HELPDESK_Lock> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<HELPDESK_Lock>() {
					public HELPDESK_Lock mapRow(ResultSet rs, int count)
							throws SQLException {
						HELPDESK_Lock obj = new HELPDESK_Lock();
						obj.setTICKET_ID(rs.getString(TICKET_ID));
						obj.setEMPLOYEE_NAME(rs.getString(EMPLOYEE_NAME));
						//obj.setPREMIUM(rs.getString(PREMIUM));
						String lockedDate = rs.getString(LOCKED_DATE);
						if (lockedDate != null)
							lockedDate = CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(lockedDate,
											userTimeZoneToSend);
						obj.setLOCKED_DATE(lockedDate);
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	// End

	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> fetchUserAvailabilityPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HELPDESK_GroupMembersAvailability> page = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HELPDESK_GroupMembersAvailability> resultList = jt.query(
				sqlFetchRows, args,
				new RowMapper<HELPDESK_GroupMembersAvailability>() {
					public HELPDESK_GroupMembersAvailability mapRow(
							ResultSet rs, int count) throws SQLException {
						HELPDESK_GroupMembersAvailability obj = new HELPDESK_GroupMembersAvailability();
						obj.setEMPLOYEE_NAME(rs.getString(EMPLOYEE_NAME));
						obj.setSTATUS(rs.getString(STATUS));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	public COMMON_Pagination<MailTracker> fetchMailTrackerPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<MailTracker> page = new COMMON_Pagination<MailTracker>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<MailTracker> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<MailTracker>() {
					public MailTracker mapRow(ResultSet rs, int count)
							throws SQLException {
						MailTracker obj = new MailTracker();
						HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
						obj.setMAIL_ID(rs.getString(MAIL_ID));
						obj.setTO_ADDRESS(rs.getString(TO_ADDRESS));
						obj.setFROM_ADDRESS(rs.getString(FROM_ADDRESS));
						obj.setSUBJECT(converter.convertSpecialChars(rs
								.getString(SUBJECT)));
						obj.setDESCRIPTION(converter.convertSpecialChars(rs
								.getString(DESCRIPTION)));
						obj.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));
						obj.setSEVERITY_ID(rs.getString(SEVERITY_ID));
						obj.setSTATUS(rs.getString(STATUS));
						obj.setRECEIVED_DATE(rs.getString(RECEIVED_DATE));
						obj.setSOURCE(rs.getString(SOURCE));
						obj.setREFERENCE_ID(rs.getString(REFERENCE_ID));
						obj.setREASON(rs.getString(REASON));
						obj.setCREATED_BY(rs.getString(CREATED_BY));
						obj.setCREATED_DATE(rs.getString(CREATED_DATE));
						obj.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
						obj.setLOCK(rs.getString(LOCK));
						obj.setLOCKED_BY(rs.getString(LOCKED_BY));
						obj.setIS_PREMIUM_MAIL(rs.getString(IS_PREMIUM_MAIL));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	public COMMON_Pagination fetchspaceauditlog(JdbcTemplate jdbcTemplate,
			String queryForCount, String dataQuery, Object[] objects,
			int pageNo, int pageSize) {
		// determine how many rows are available
		final int rowCount = jdbcTemplate.queryForInt(queryForCount);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		// create the page object
		final COMMON_Pagination<WORKSPACEMGMT_SeatingDetails> page = new COMMON_Pagination<WORKSPACEMGMT_SeatingDetails>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<WORKSPACEMGMT_SeatingDetails> resultList = jdbcTemplate.query(
				dataQuery, objects,
				new RowMapper<WORKSPACEMGMT_SeatingDetails>() {
					public WORKSPACEMGMT_SeatingDetails mapRow(ResultSet rs,
							int count) throws SQLException {
						WORKSPACEMGMT_SeatingDetails obj = new WORKSPACEMGMT_SeatingDetails();
						obj.setAuditid(rs.getString(AUDIT_ID));
						obj.setModifieddate(rs.getString(MODIFIED_DATE));
						obj.setModifiedid(rs.getString(MODIFIED_BY));
						String remark = rs.getString(REMARKS);
						if (remark.equalsIgnoreCase(""))
							obj.setRemarks("NA");
						else {
							if (remark.contains("----------------"))
								remark = remark.replace("----------------", "");
							obj.setRemarks(remark);
						}
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}

	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> fetchLoggedUserPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final String employeeId,
			final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = 0;
		if (pageSize != 0) {
			pageCount = rowCount / pageSize;
		}
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HELPDESK_GroupMembersAvailability> page = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HELPDESK_GroupMembersAvailability> resultList = jt.query(
				sqlFetchRows, args,
				new RowMapper<HELPDESK_GroupMembersAvailability>() {
					public HELPDESK_GroupMembersAvailability mapRow(
							ResultSet rs, int count) throws SQLException {
						HELPDESK_GroupMembersAvailability obj = new HELPDESK_GroupMembersAvailability();
						obj.setEMPLOYEE_NAME(rs.getString(EMPLOYEE_NAME));
						String dateTime = rs.getString(DATE_TIME);
						// Made the fixes for Date format change in Login
						// Availability.
						// Retrieving the date in the same format from DB
						SimpleDateFormat sd1 = new SimpleDateFormat(
								"dd-MM-yyyy hh:mm:ss a");
						SimpleDateFormat sd = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date dt = null;
						String formatted_date = "";
						try {
							dt = sd.parse(dateTime);
							formatted_date = sd1.format(dt) + " IST";
						} catch (ParseException e) {
							/* e.printStackTrace(); */
							throw new DateParsingException("Date " + dt
									+ "can't be parsed");
						}
						obj.setDATE_TIME(formatted_date);
						// End of Fix Change
						obj.setSTATUS(rs.getString(STATUS));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}
	/**********Auto Assignment************/
	public COMMON_Pagination<HELPDESK_ScoreDetail> fetchScorePage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final String employeeId,
			final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = 0;
		if (pageSize != 0) {
			pageCount = rowCount / pageSize;
		}
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<HELPDESK_ScoreDetail> page = new COMMON_Pagination<HELPDESK_ScoreDetail>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		
		
		List<HELPDESK_ScoreDetail> auditList = jt.query(
				sqlFetchRows, 
				new RowMapper<HELPDESK_ScoreDetail>() {
					public HELPDESK_ScoreDetail mapRow(
							ResultSet rs, int count) throws SQLException {
						HELPDESK_ScoreDetail obj = new HELPDESK_ScoreDetail();
						obj.setEMPLOYEE_ID(rs.getString("EMPLOYEE_ID"));										
						obj.setDATE_CHANGE(rs.getString("DATA_CHANGE"));
						obj.setCREATED_BY(rs.getString("CREATED_BY"));
						String dateTime = rs.getString("CREATED_DATE");						
						SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date dt = null;
						String formatted_date = "";
						try {
							dt = sd.parse(dateTime);
							formatted_date = sd1.format(dt) + " IST";
						} catch (ParseException e) {
							throw new DateParsingException("Date " + dt	+ "can't be parsed");
						}
						obj.setCREATED_DATE(formatted_date);		
						return obj;
					}

				});
		
	
		List<HELPDESK_ScoreDetail> auditListNew=new ArrayList<HELPDESK_ScoreDetail>();		
		HELPDESK_ScoreDetail auditObj=new HELPDESK_ScoreDetail();
		String dataChange=null;
		 String oldTotalScore=null;
		 String newTotalScore=null;
	
		for(int m=0;m<auditList.size();m++){
			dataChange=auditList.get(m).getDATE_CHANGE();
			if(!dataChange.contains("None")){
			DocumentBuilder db=null;
			try {
				db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {				
				e.printStackTrace();
			}
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(dataChange));
			Document parentNodes=null;;
			try {
				parentNodes = db.parse(is);
			} catch (SAXException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			parentNodes.getDocumentElement().normalize();
			NodeList nodeLst = parentNodes.getElementsByTagName("data_change");
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					 NodeList nodeLst_fields = fstNode.getChildNodes();
					 for (int i = 0; i < nodeLst_fields.getLength(); i++) {						 
						 Node fstFieldNode = nodeLst_fields.item(i);	
						 
						 if (fstFieldNode.getNodeType() == Node.ELEMENT_NODE && fstFieldNode.getNodeName().equals("total")) {
							 Element fstFieldElmnt = (Element) fstFieldNode;
							 NodeList fields = fstFieldElmnt.getChildNodes();							 
								 oldTotalScore=fields.item(0).getFirstChild().getNodeValue();
								 newTotalScore=fields.item(0).getNextSibling().getFirstChild().getNodeValue();
							
						 }
						
						 if (fstFieldNode.getNodeType() == Node.ELEMENT_NODE && fstFieldNode.getNodeName().equals("tickets")) {
							 Element fstFieldElmnt = (Element) fstFieldNode;
							 NodeList fields = fstFieldElmnt.getChildNodes();							 
							 for(int k=0;k<fields.getLength();k+=2){						
								auditObj.setEMPLOYEE_ID(auditList.get(m).getEMPLOYEE_ID().toString());
								auditObj.setCREATED_DATE( auditList.get(m).getCREATED_DATE().toString());
								auditObj.setCREATED_BY(auditList.get(m).getCREATED_BY().toString());
								if(auditList.get(m).getCREATED_BY().toString().equals("SYSTEM")){
									auditObj.setIS_FIRST_LOGIN("Yes");
								}else{
									auditObj.setIS_FIRST_LOGIN("No");
								}
								try{
								auditObj.setTICKET_ID(fields.item(k).getFirstChild().getNodeValue());								
								auditObj.setWEIGHTAGE(fields.item(k).getNextSibling().getFirstChild().getNodeValue());
								}catch(NullPointerException e){
									auditObj.setTICKET_ID("None");								
									auditObj.setWEIGHTAGE("0");
								}
								auditObj.setOLD_TOTAL_SCORE(oldTotalScore);
								auditObj.setNEW_TOTAL_SCORE(newTotalScore);
								auditListNew.add(auditObj);
								auditObj=new HELPDESK_ScoreDetail();								
								
							 }
						 	}
					 }

				}

			}
		}
		}
		
		if (pageSize != 0) {
			pageCount = auditListNew.size() / pageSize;
		}
		if (auditListNew.size() > pageSize * pageCount) {
			pageCount++;
		}
		page.setPageNumber(pageNo);
		page.setTotalCount(auditListNew.size());	
		page.setPagesAvailable(pageCount);
		page.setPageItems(auditListNew);
		return page;
	}
	
	/**********Auto Assignment************/
	public COMMON_Pagination<MailTracker> fetchMailLockedPage(
			final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final int userTimeZoneToSend) {

		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		final COMMON_Pagination<MailTracker> page = new COMMON_Pagination<MailTracker>();
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<MailTracker> resultList = jt.query(sqlFetchRows, args,
				new RowMapper<MailTracker>() {
					public MailTracker mapRow(ResultSet rs, int count)
							throws SQLException {
						MailTracker obj = new MailTracker();
						obj.setMAIL_ID(rs.getString(MAIL_ID));
						obj.setLOCKED_BY(rs.getString(LOCKED_BY));
						obj.setIS_PREMIUM_MAIL(rs.getString(IS_PREMIUM_MAIL));
						String lockedDate = rs.getString(LOCKED_DATE);
						if (lockedDate != null)
							lockedDate = CustomDateFormatConstants
									.showUserTimeZonewithTimezoneID(lockedDate,
											userTimeZoneToSend);
						obj.setLOCKED_DATE(lockedDate);
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;
	}
	
	/**********Auto Assignment************/
	public COMMON_Pagination<HELPDESK_ScoreDetail> fetchEngineerScorePage(final JdbcTemplate jt, final String sqlCountRows,
			final String sqlFetchRows, final Object args[], final int pageNo,
			final int pageSize, final int userTimeZoneToSend){
		final COMMON_Pagination<HELPDESK_ScoreDetail> page=new COMMON_Pagination<HELPDESK_ScoreDetail>();
		
		// determine how many rows are available
		final int rowCount = jt.queryForInt(sqlCountRows);
		int pageCount = rowCount / pageSize;
		// calculate the number of pages
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		List<HELPDESK_ScoreDetail> resultList=jt.query(sqlFetchRows, args,new RowMapper<HELPDESK_ScoreDetail>(){		
			public HELPDESK_ScoreDetail mapRow(ResultSet rs, int count)
					throws SQLException {
				HELPDESK_ScoreDetail obj=new HELPDESK_ScoreDetail();
				obj.setEMPLOYEE_NAME(rs.getString("EMPLOYEE_NAME"));
				obj.setHIGH_SCORE(rs.getString("HIGH_SCORE"));
				obj.setLOW_SCORE(rs.getString("LOW_SCORE"));
				obj.setMEDIUM_SCORE(rs.getString("MEDIUM_SCORE"));
				obj.setTOTAL_SCORE(rs.getString("TOTAL_SCORE"));				
				return obj;
			}
			
		});
		page.setPageItems(resultList);		
		return page;
	}
/**********Auto Assignment************/
	
	public COMMON_Pagination<ADMIN_ModifyLocation> fetchlocationPage(final JdbcTemplate jdbcTemplate,final String queryForCount, final String dataQuery,
			final Object args[],final int pageNo,final int pageSize,
			final int userTimeZoneToSend){
		final COMMON_Pagination<ADMIN_ModifyLocation> page = new COMMON_Pagination<ADMIN_ModifyLocation>();	
		final int rowCount=jdbcTemplate.queryForInt(queryForCount);
		final int startCount=(Integer)args[0];
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		page.setPageNumber(pageNo);
		page.setTotalCount(rowCount);
		page.setPagesAvailable(pageCount);
		
		List<ADMIN_ModifyLocation> resultList = jdbcTemplate.query(dataQuery, args,
				new RowMapper<ADMIN_ModifyLocation>() {
					public ADMIN_ModifyLocation mapRow(ResultSet rs, int count)
							throws SQLException {
						ADMIN_ModifyLocation obj = new ADMIN_ModifyLocation();
						obj.setLOCATION_ID(rs.getString("LOCATION_ID"));
						obj.setCITY(rs.getString("CITY"));
						obj.setBUILDING(rs.getString("BUILDING"));
						obj.setFLOOR(rs.getString("FLOOR"));
						obj.setLOCDETID(rs.getString("LOCDETID"));
						obj.setSTATUS(rs.getString("STATUS"));
						
						obj.setSTART_COUNT_PAGE(String.valueOf(startCount));
						return obj;
					}

				});
		page.setPageItems(resultList);
		return page;		
	}
}


/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jul 19, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
