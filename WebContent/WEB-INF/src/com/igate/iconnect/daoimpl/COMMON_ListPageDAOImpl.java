/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

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
import com.igate.iconnect.constants.COMMON_ListPageSQLQueryConstants;
import com.igate.iconnect.dao.COMMON_ListPageDAO;
import com.igate.iconnect.util.COMMON_ExecuteProcedure;
import com.igate.iconnect.util.COMMON_PaginationHelper;

@Transactional(rollbackFor = Exception.class)
public  class COMMON_ListPageDAOImpl implements COMMON_ListPageDAO {
	private JdbcTemplate jdbcTemplate;
	COMMON_ExecuteProcedure DAOUtilObj;
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setiConnectDAOUtil(COMMON_ExecuteProcedure dAOUtilObj) {
		DAOUtilObj = dAOUtilObj;
	}
	public COMMON_Pagination<HelpDesk> getHDTicketColourCodeList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, final int userTimeZoneToSend, String orderBy) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		String queryForCount = query.substring(query.indexOf("FROM"), query
				.indexOf("$"));
		// Below variable stores the complete query till $ symbol.
		String finalQuery = query.substring(0, query.indexOf("$"));
		
		StringBuffer dynamicQueryForCount=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) ");
		dynamicQueryForCount.append(queryForCount);
		dynamicQueryForCount.append(filterQuery);
		
		// Below variable stores only the orderby clause from query using
		// seperator $
		String orderByClause = query.substring(query.indexOf("$") + 1, query
				.length());
		StringBuffer dynamicdataQuery=new StringBuffer();
		
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (");
		dynamicdataQuery.append(orderByClause);
		dynamicdataQuery.append(" ");
		dynamicdataQuery.append(orderBy);
		dynamicdataQuery.append(") AS Row,");
		
		String dataQuery = finalQuery.replace("SELECT",dynamicdataQuery.toString());
		dynamicdataQuery=new StringBuffer();
		
		dynamicdataQuery.append(dataQuery);
		dynamicdataQuery.append(filterQuery);
		dynamicdataQuery.append( ") AS DATA WHERE Row BETWEEN ? AND ? ");
		
		return ph.fetchHDColourCodePage(jdbcTemplate, dynamicQueryForCount.toString(), dynamicdataQuery.toString(),
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend,DAOUtilObj);
	}

	public COMMON_Pagination<HelpDesk> getHDTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, final int userTimeZoneToSend, String orderBy,String menuName) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		String queryForCount = query.substring(query.indexOf("FROM"), query
				.indexOf("$"));
		// Below variable stores the complete query till $ symbol.
		String finalQuery = query.substring(0, query.indexOf("$"));

		
		StringBuffer dynamicQueryForCount=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) ");
		dynamicQueryForCount.append(queryForCount);
		dynamicQueryForCount.append(filterQuery);

		// Below variable stores only the orderby clause from query using
		// seperator $
		String orderByClause = query.substring(query.indexOf("$") + 1, query
				.length());
		
		StringBuffer dynamicdataQuery=new StringBuffer();
		
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (");
		dynamicdataQuery.append(orderByClause);
		dynamicdataQuery.append(" ");
		dynamicdataQuery.append(orderBy);
		dynamicdataQuery.append(") AS Row,");
		
		String dataQuery = finalQuery.replace("SELECT",dynamicdataQuery.toString());
		dynamicdataQuery=new StringBuffer();
		
		dynamicdataQuery.append(dataQuery);
		dynamicdataQuery.append(filterQuery);
		dynamicdataQuery.append( ") AS DATA WHERE Row BETWEEN ? AND ? ");
		if(menuName.equalsIgnoreCase("Queue")){
			dynamicdataQuery.append(" order by HIT_TIME");
		}
		
		return ph.fetchHDPage(jdbcTemplate, dynamicQueryForCount.toString(), dynamicdataQuery.toString(),
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend,DAOUtilObj);
	}

	public COMMON_Pagination<HELPDESK_Lock> getLockedRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend) {
		COMMON_PaginationHelper<HELPDESK_Lock> ph = new COMMON_PaginationHelper<HELPDESK_Lock>();
		String queryForCount = query.substring(query.indexOf("FROM"), query
				.length());
		StringBuffer dynamicQueryForCount=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) ");
		dynamicQueryForCount.append(queryForCount);
		dynamicQueryForCount.append(filterQuery);
		
		String dataQuery = "";
		StringBuffer dynamicdataQuery=new StringBuffer();

		if (query.contains("PREMIUM")) {
			
			dynamicdataQuery=new StringBuffer();
			
			dynamicdataQuery
					.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY case when (select MEMBER_ID from IC_IHD_PREMIUM_MEMBERS pm where pm.FUNCTION_ID=ITD.FUNCTION_ID and pm.MEMBER_ID=ITD.REQUESTED_BY and pm.IS_ACTIVE=1) is not null then 'Yes' else 'No' END DESC,ITD.CREATED_DATE DESC) AS Row,");
			
			dataQuery=query.replace("SELECT",dynamicdataQuery.toString());
			dynamicdataQuery=new StringBuffer();
			dynamicdataQuery.append(dataQuery);
			dynamicdataQuery.append(filterQuery);
			dynamicdataQuery.append(") AS DATA WHERE Row BETWEEN ? AND ? ");
			return ph.fetchPremiumLockedTicketPage(jdbcTemplate, dynamicQueryForCount.toString(),
					dynamicdataQuery.toString(), new Object[] { startCount, pageSize }, pageNo,
					pageSize, userTimeZoneToSend);

		} else {
			dynamicdataQuery=new StringBuffer();
			dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY LTD.REFERENCE_ID DESC) AS Row,");
			dataQuery = query
					.replace(
							"SELECT",
							dynamicdataQuery.toString());
			dynamicdataQuery=new StringBuffer();
			dynamicdataQuery.append(dataQuery);
			dynamicdataQuery.append(filterQuery);
			dynamicdataQuery.append(") AS DATA WHERE Row BETWEEN ? AND ? ");
			return ph.fetchLockedTicketPage(jdbcTemplate, dynamicQueryForCount.toString(),
					dynamicdataQuery.toString(), new Object[] { startCount, pageSize }, pageNo,
					pageSize, userTimeZoneToSend);
		}

	}

	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getUseAvailabilityRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend) {
		COMMON_PaginationHelper<HELPDESK_GroupMembersAvailability> ph = new COMMON_PaginationHelper<HELPDESK_GroupMembersAvailability>();
		
		StringBuffer dynamicQueryForCount=new StringBuffer();
		StringBuffer dynamicfilterQuery=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) FROM( SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY STATUS DESC,EMPLOYEE_NAME ASC) AS Row,* FROM (");
		dynamicQueryForCount.append(query.replace("SELECTT", "SELECT"));
		dynamicQueryForCount.append(" ) AS DATA ");
		
		if (!filterQuery.equals("")) {
			dynamicfilterQuery.append(" WHERE ");
			dynamicfilterQuery.append(filterQuery.substring(4, filterQuery.length()));
		}else{
			dynamicfilterQuery.append(filterQuery);
		}
		dynamicQueryForCount.append(dynamicfilterQuery.toString());
		dynamicQueryForCount.append(" ) AS DATA2  ) AS COUNT ");
		
		StringBuffer dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY STATUS DESC,EMPLOYEE_NAME ASC) AS Row,* FROM (SELECT ");
		
		String dataQuery = query
				.replace(
						"SELECTT",
						dynamicdataQuery);
		dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append(dataQuery);
		dynamicdataQuery.append(" ) AS DATA ");
		dynamicdataQuery.append(dynamicfilterQuery.toString());
		dynamicdataQuery.append(") AS DATA2 WHERE Row BETWEEN ? AND ? ");
		return ph.fetchUserAvailabilityPage(jdbcTemplate, dynamicQueryForCount.toString(),
				dynamicdataQuery.toString(), new Object[] { startCount, pageSize }, pageNo,
				pageSize, userTimeZoneToSend);
	}

	public COMMON_Pagination<HelpDesk> getApprovalTicketsRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend,
			String orderBy) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		
		StringBuffer dynamicQueryForCount=new StringBuffer();
		StringBuffer dynamicfilterQuery=new StringBuffer();

		if (!filterQuery.equals("")) {
			dynamicfilterQuery.append(" AND ");
			dynamicfilterQuery.append(filterQuery.substring(4, filterQuery.length()));
		}else{
			dynamicfilterQuery.append(filterQuery);
		}
		String finalQuery = query.substring(0, query.indexOf("$"));
		
		dynamicQueryForCount.append("SELECT COUNT(*) FROM( SELECT ROW_NUMBER() OVER (ORDER BY DATA.TICKET_ID ");
		dynamicQueryForCount.append(orderBy);
		dynamicQueryForCount.append(") AS Row,* FROM (");
		
		dynamicQueryForCount.append(finalQuery);
		dynamicQueryForCount.append(dynamicfilterQuery);
		dynamicQueryForCount.append(" ) AS DATA ) AS COUNT ");

		String orderByClause = query.substring(query.indexOf("$") + 1, query
				.length());
		
		StringBuffer dynamicdataQuery=new StringBuffer();
		
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (");
		dynamicdataQuery.append(orderByClause);
		dynamicdataQuery.append(" ");
		dynamicdataQuery.append(orderBy);
		dynamicdataQuery.append(" ) AS Row,* FROM ( ");
		dynamicdataQuery.append(finalQuery);
		dynamicdataQuery.append(dynamicfilterQuery);
		dynamicdataQuery.append(" ) AS DATA ) AS DATA2 WHERE Row BETWEEN ? AND ? ");
		
		return ph.fetchHDPage(jdbcTemplate, dynamicQueryForCount.toString(), dynamicdataQuery.toString(),
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend,DAOUtilObj);
	}

	public COMMON_Pagination<TechCR> getTechCRTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		String queryForCount = query.substring(query.indexOf("from"), query
				.length());
		StringBuffer dynamicQueryForCount=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) ");
		dynamicQueryForCount.append(queryForCount);
		dynamicQueryForCount.append(filterQuery);
		
		StringBuffer dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY tcrDetails.ISSUE_ID DESC) AS Row,");
		
		String dataQuery = query
				.replaceFirst(
						"select",
						dynamicdataQuery.toString());
		
		dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append(dataQuery);
		dynamicdataQuery.append(filterQuery);
		dynamicdataQuery.append(") AS DATA WHERE Row BETWEEN ? AND ? ");
		return ph.fetchTechCRPage(jdbcTemplate, dynamicQueryForCount.toString(), dynamicdataQuery.toString(),
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend);

	}

	public COMMON_Pagination<MailTracker> getMailTrackerList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		String queryForCount = query.substring(query.indexOf(" FROM"), query
				.length());
		StringBuffer dynamicQueryForCount=new StringBuffer();
		dynamicQueryForCount.append("SELECT COUNT(*) ");
		dynamicQueryForCount.append(queryForCount);
		dynamicQueryForCount.append(filterQuery);
		
		StringBuffer dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append("SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY IS_PREMIUM_MAIL DESC,MT.CREATED_DATE ASC,  MAIL_ID DESC) AS Row,");
		String dataQuery = query
				.replaceFirst(
						"SELECT",
						dynamicdataQuery.toString());
		dynamicdataQuery=new StringBuffer();
		dynamicdataQuery.append(dataQuery);
		dynamicdataQuery.append(filterQuery);
		dynamicdataQuery.append(") AS DATA WHERE Row BETWEEN ? AND ? order by row");
		
		return ph.fetchMailTrackerPage(jdbcTemplate, dynamicQueryForCount.toString(), dynamicdataQuery.toString(),
				new Object[] { startCount, pageSize }, pageNo, pageSize);
	}

	public List<Map<String, Object>> getReqList(String query) {
		return this.jdbcTemplate.queryForList(query, new Object[] {});
	}

	public int saveAssign(String member_Id, long ticket_Id, String group_id,
			String loginID) {
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String modified_date = dateFormatGmt.format(new Date());
		String query = COMMON_ListPageSQLQueryConstants.IC_SAVE_ASSIGN;
		return this.jdbcTemplate.update(query, member_Id, group_id, loginID,
				modified_date, ticket_Id);
	}

	public List<Map<String, Object>> getGroupWorkLoadList(String groupName) {
		//get if AA is enabled /disabled for a group
		
		int isReqd=this.jdbcTemplate.queryForInt(COMMON_ListPageSQLQueryConstants.IC_IS_AUTO_ASSIGNMENT_REQD, groupName);		
		if(isReqd==0){		
		return this.jdbcTemplate.queryForList(
				COMMON_ListPageSQLQueryConstants.IC_ENGINEER_WORKLOAD,
				groupName);
		}else{
			return this.jdbcTemplate.queryForList(
					COMMON_ListPageSQLQueryConstants.IC_LOGGEDIN_ENGINEER_WORKLOAD,
					groupName);
		}
	}

	public List<Map<String, Object>> getGroupMamberWorkLoadList(String memberId) {
		return this.jdbcTemplate.queryForList(
				COMMON_ListPageSQLQueryConstants.IC_DISPLAY_ENGINEER_WORKLOAD,
				memberId);
	}

	public List<Map<String, Object>> getUserSelectionMenuListPage(
			String menuID, String employeeID, String roleID) {

		return this.jdbcTemplate
				.queryForList(
						COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_LIST_SQL,
						menuID, employeeID, roleID);
	}

	public String saveUserSelectionMenuList(List<Map<String, Object>> data) {

		DAOUtilObj.executeBatchUpdationForCustomList(data, this.jdbcTemplate
				.getDataSource());
		return "success";

	}

	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getLoggedUserList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String employeeId, int userTimeZoneToSend,
			String fromDate, String toDate) throws ParseException {
		COMMON_PaginationHelper<HELPDESK_GroupMembersAvailability> ph = new COMMON_PaginationHelper<HELPDESK_GroupMembersAvailability>();

		StringBuilder queryToAppend = new StringBuilder();

		queryToAppend
				.append("SELECT IUD.NAME+' ( '+IUD.EMPLOYEE_ID+' )' as EMPLOYEE_NAME,IUIO.DATE_TIME,IUIO.STATUS  from IC_USER_AVAILABILITY_IN_OUT IUIO,");
		queryToAppend.append("  IC_USER_DETAILS IUD where IUD.EMPLOYEE_ID='")
				.append(employeeId).append(
						"' and IUIO.EMPLOYEE_ID=IUD.EMPLOYEE_ID ");
		String logInDate = null;
		String logOutDate = null;
		/* if (fromDate != null) { */
		if ((fromDate == null && toDate == null)
				|| (fromDate.length() == 0 && toDate.length() == 0)) {
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			logInDate = dateFormatGmt.format(new Date());
			queryToAppend.append(" AND DATE_TIME>='");
			queryToAppend.append(logInDate);
			queryToAppend.append("'");

		} else {
			DateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatGmt1 = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			DateFormat dateFormatGmt2 = new SimpleDateFormat(
					"yyyy-MM-dd 23:59:59");
			dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			dateFormatGmt2.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String fromDateFormatted = null;
			String toDateFormatted = null;
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			dateFormatGmt.setCalendar(cal);
			logInDate = dateFormatGmt.parse(fromDate).toString();
			fromDateFormatted = dateFormatGmt1.format(dateFormatGmt
					.parse(fromDate));
			logOutDate = dateFormatGmt.parse(toDate).toString();
			toDateFormatted = dateFormatGmt2
					.format(dateFormatGmt.parse(toDate));
			queryToAppend.append(" AND DATE_TIME>='");
			queryToAppend.append(fromDateFormatted);
			queryToAppend.append("' AND DATE_TIME<='");
			queryToAppend.append(toDateFormatted);
			queryToAppend.append("'");

		}
		/* } */

		String query = queryToAppend.toString();

		String queryForCount = "SELECT COUNT(*) FROM( SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY DATE_TIME ASC) AS Row,* FROM ("
				+ query + " ) AS DATA ";
		if (!filterQuery.equals("")) {
			filterQuery = "WHERE "
					+ filterQuery.substring(4, filterQuery.length());
		}

		queryForCount += filterQuery + ") AS DATA2  ) AS COUNT";
		String dataQuery = query
				.replace(
						"SELECT",
						"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY DATE_TIME ASC) AS Row,* FROM (SELECT ");
		dataQuery = dataQuery + ") AS DATA " + filterQuery + ") AS DATA2 "
				+ "WHERE Row BETWEEN ? AND ? ";
		return ph.fetchLoggedUserPage(jdbcTemplate, queryForCount, dataQuery,
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				employeeId, userTimeZoneToSend);
	}

	public COMMON_Pagination<MailTracker> getMailLockedRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend) {
		COMMON_PaginationHelper<MailTracker> ph = new COMMON_PaginationHelper<MailTracker>();
		String queryForCount = query.substring(query.indexOf("FROM"), query
				.length());
		queryForCount = "SELECT COUNT(*) " + queryForCount + filterQuery;
		String dataQuery = query
				.replaceFirst(
						"SELECT",
						"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY IS_PREMIUM_MAIL DESC,MT.CREATED_DATE ASC,  MAIL_ID DESC) AS Row,");
		dataQuery = dataQuery + filterQuery + ") AS DATA "
				+ "WHERE Row BETWEEN ? AND ? ";
		return ph.fetchMailLockedPage(jdbcTemplate, queryForCount, dataQuery,
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend);
	}
	/**********Auto Assignment************/
	
	public COMMON_Pagination<HELPDESK_ScoreDetail> getEngineerScoreList(
			int startCount, int pageNo, int pageSize, String filterQuery,
			String query, int userTimeZoneToSend) {
		COMMON_PaginationHelper<HELPDESK_ScoreDetail> ph=new COMMON_PaginationHelper<HELPDESK_ScoreDetail>();
		//String queryForCount=query.substring(query.indexOf("FROM"),query.length());
		//queryForCount="SELECT COUNT(*) "+queryForCount +filterQuery;
		String dataQuery=query.replaceFirst("SELECT", "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY iud.NAME ASC) AS Row,");
		dataQuery = dataQuery +") AS DATA WHERE Row BETWEEN ? AND ? " +filterQuery;
		
		
		String queryCount = query.toString();
		String queryForCount = "SELECT COUNT(*) FROM( SELECT * FROM (SELECT * FROM ("
				+ queryCount + " ) AS DATA ";
		if (!filterQuery.equals("")) {
			filterQuery = "WHERE "
					+ filterQuery.substring(4, filterQuery.length());
		}

		queryForCount += filterQuery + ") AS DATA2  ) AS COUNT";
		
		
		return ph.fetchEngineerScorePage(jdbcTemplate,queryForCount,dataQuery,new Object[]{startCount,pageSize},pageNo,pageSize,userTimeZoneToSend);
	}
	
	/**********END :Auto Assignment************/	
	public int getTotalNumberOfRows(String employeeId, String fromDate,
			String toDate, String filterQuery) throws ParseException {
		int count = 0;

		StringBuffer queryToAppend = new StringBuffer();

		queryToAppend
				.append("SELECT IUD.NAME+' ( '+IUD.EMPLOYEE_ID+' )' as EMPLOYEE_NAME,IUIO.DATE_TIME,IUIO.STATUS  from IC_USER_AVAILABILITY_IN_OUT IUIO,");
		queryToAppend.append("  IC_USER_DETAILS IUD where IUD.EMPLOYEE_ID='"
				+ employeeId + "' and IUIO.EMPLOYEE_ID=IUD.EMPLOYEE_ID ");

		String logInDate = null;
		String logOutDate = null;
		if ((fromDate == null && toDate == null)
				|| (fromDate.length() == 0 && toDate.length() == 0)) {
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			logInDate = dateFormatGmt.format(new Date());
			queryToAppend.append(" AND DATE_TIME>='");
			queryToAppend.append(logInDate);
			queryToAppend.append("'");

		} else {
			DateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatGmt1 = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String dt1 = null;
			String dt2 = null;
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			dateFormatGmt.setCalendar(cal);

			logInDate = dateFormatGmt.parse(fromDate).toString();
			dt1 = dateFormatGmt1.format(dateFormatGmt.parse(fromDate));
			logOutDate = dateFormatGmt.parse(toDate).toString();
			dt2 = dateFormatGmt1.format(dateFormatGmt.parse(toDate));

			// logInDate=dateFormatGmt.format(fromDate);
			// logOutDate=dateFormatGmt.format(toDate);
			if (dt1.equalsIgnoreCase(dt2)) {
				queryToAppend.append(" AND DATE_TIME>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND DATE_TIME<='");
				queryToAppend.append(dt1.substring(0, 10));
				queryToAppend.append(" 23:59:00");
				queryToAppend.append("'");
			} else {
				queryToAppend.append(" AND DATE_TIME>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND DATE_TIME<='");
				queryToAppend.append(dt2);
				queryToAppend.append("'");
			}

		}

		String query = queryToAppend.toString();
		String queryForCount = "SELECT COUNT(*) FROM( SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY DATE_TIME ASC) AS Row,* FROM ("
				+ query + " ) AS DATA ";
		if (!filterQuery.equals("")) {
			filterQuery = "WHERE "
					+ filterQuery.substring(4, filterQuery.length());
		}

		queryForCount += filterQuery + ") AS DATA2  ) AS COUNT";

		count = this.jdbcTemplate.queryForInt(queryForCount);

		return count;
	}

	public COMMON_Pagination<HelpDesk> getMasterTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, final int userTimeZoneToSend, String orderBy) {
		COMMON_PaginationHelper<HelpDesk> ph = new COMMON_PaginationHelper<HelpDesk>();
		String queryForCount = query.substring(query.indexOf("FROM"), query
				.length());
		queryForCount = "SELECT COUNT(*) " + queryForCount + filterQuery;
		String dataQuery = query.replace("SELECT",
				"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ticket.TICKET_ID "
						+ orderBy + ") AS Row,");
		if (query.contains("PREMIUM")) {
			dataQuery = query
					.replace(
							"SELECT",
							"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY case when (select MEMBER_ID from IC_IHD_PREMIUM_MEMBERS pm where pm.FUNCTION_ID=ticket.FUNCTION_ID"
									+ " and pm.MEMBER_ID=ticket.REQUESTED_BY and pm.IS_ACTIVE=1) is not null then 'Yes' else 'No' END DESC,ticket.CREATED_DATE "
									+ orderBy + ") AS Row,");
		}
		dataQuery = dataQuery + filterQuery + ") AS DATA "
				+ "WHERE Row BETWEEN ? AND ? ";
		return ph.fetchMasterPage(jdbcTemplate, queryForCount, dataQuery,
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				userTimeZoneToSend);

	}



	/**********Auto Assignment************/
	public List<Map<String, Object>> getEngineerAuditLog(String employeeID,
			String fromDate, String toDate) throws ParseException{
		//>='?' AND CREATED_DATE<='?'
		StringBuffer queryToAppend=new StringBuffer("SELECT EMPLOYEE_ID,DATA_CHANGE,CREATED_BY,CREATED_DATE FROM IC_IHD_ENGINEER_SCORE_AUDIT_LOG WHERE EMPLOYEE_ID='"+employeeID+"' ");
		String logInDate = null;
		String logOutDate = null;
		if ((fromDate == null && toDate == null)
				|| (fromDate.length() == 0 && toDate.length() == 0)) {
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			logInDate = dateFormatGmt.format(new Date());
			queryToAppend.append(" AND CREATED_DATE>='");
			queryToAppend.append(logInDate);
			queryToAppend.append("'");

		} else {
			DateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatGmt1 = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String dt1 = null;
			String dt2 = null;
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			dateFormatGmt.setCalendar(cal);

			logInDate = dateFormatGmt.parse(fromDate).toString();
			dt1 = dateFormatGmt1.format(dateFormatGmt.parse(fromDate));
			logOutDate = dateFormatGmt.parse(toDate).toString();
			dt2 = dateFormatGmt1.format(dateFormatGmt.parse(toDate));
			
			if (dt1.equalsIgnoreCase(dt2)) {
				queryToAppend.append(" AND CREATED_DATE>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND CREATED_DATE<='");
				queryToAppend.append(dt1.substring(0, 10));
				queryToAppend.append(" 23:59:00");
				queryToAppend.append("'");
			} else {
				queryToAppend.append(" AND CREATED_DATE>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND CREATED_DATE<='");
				queryToAppend.append(dt2);
				queryToAppend.append("'");
			}
			
	}
		List<Map<String, Object>> scoreAuditList=this.jdbcTemplate.queryForList(queryToAppend.toString());
		return scoreAuditList;
	}
	
	
	public COMMON_Pagination<HELPDESK_ScoreDetail> getEngineerAuditLogList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String employeeId, int userTimeZoneToSend,
			String fromDate, String toDate) throws ParseException {
		COMMON_PaginationHelper<HELPDESK_ScoreDetail> ph = new COMMON_PaginationHelper<HELPDESK_ScoreDetail>();
		StringBuilder queryToAppend = new StringBuilder("SELECT IUD.NAME+' ( '+IUD.EMPLOYEE_ID+' )' as EMPLOYEE_ID,audit.DATA_CHANGE,audit.CREATED_BY,audit.CREATED_DATE FROM IC_IHD_ENGINEER_SCORE_AUDIT_LOG audit,IC_USER_DETAILS IUD WHERE audit.EMPLOYEE_ID=IUD.EMPLOYEE_ID   and audit.EMPLOYEE_ID='"+employeeId+"'");
		String logInDate = null;
		String logOutDate = null;
		/* if (fromDate != null) { */
		if ((fromDate == null && toDate == null)
				|| (fromDate.length() == 0 && toDate.length() == 0)) {
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			logInDate = dateFormatGmt.format(new Date());
			queryToAppend.append(" AND audit.CREATED_DATE>='");
			queryToAppend.append(logInDate);
			queryToAppend.append("'");

		} else {
			DateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatGmt1 = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			DateFormat dateFormatGmt2 = new SimpleDateFormat(
					"yyyy-MM-dd 23:59:59");
			dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			dateFormatGmt2.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String fromDateFormatted = null;
			String toDateFormatted = null;
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			dateFormatGmt.setCalendar(cal);
			logInDate = dateFormatGmt.parse(fromDate).toString();
			fromDateFormatted = dateFormatGmt1.format(dateFormatGmt
					.parse(fromDate));
			logOutDate = dateFormatGmt.parse(toDate).toString();
			toDateFormatted = dateFormatGmt2
					.format(dateFormatGmt.parse(toDate));
			queryToAppend.append(" AND audit.CREATED_DATE>='");
			queryToAppend.append(fromDateFormatted);
			queryToAppend.append("' AND audit.CREATED_DATE<='");
			queryToAppend.append(toDateFormatted);
			queryToAppend.append("'");

		}
		/* } */

		String query = queryToAppend.toString();

		String queryForCount = "SELECT COUNT(*) FROM( SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY CREATED_DATE ASC) AS Row,* FROM ("
				+ query + " ) AS DATA ";
		if (!filterQuery.equals("")) {
			filterQuery = "WHERE "
					+ filterQuery.substring(4, filterQuery.length());

		}

		queryForCount += filterQuery + ") AS DATA2  ) AS COUNT";
		String dataQuery = query
				.replace(
						"SELECT",
						"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY CREATED_DATE ASC) AS Row,* FROM (SELECT ");
		dataQuery = dataQuery + ") AS DATA " + filterQuery + ") AS DATA2 ";
				
		return ph.fetchScorePage(jdbcTemplate, queryForCount, dataQuery,
				new Object[] { startCount, pageSize }, pageNo, pageSize,
				employeeId, userTimeZoneToSend);
	}
	
	public int getTotalNumberOfRowsScoreDetail(String employeeId, String fromDate,
			String toDate, String filterQuery) throws ParseException {
		int count = 0;

		StringBuffer queryToAppend = new StringBuffer();

		queryToAppend
				.append("SELECT IUD.NAME+' ( '+IUD.EMPLOYEE_ID+' )' as EMPLOYEE_ID,audit.DATA_CHANGE,audit.CREATED_BY,audit.CREATED_DATE FROM IC_IHD_ENGINEER_SCORE_AUDIT_LOG audit,IC_USER_DETAILS IUD WHERE audit.EMPLOYEE_ID=IUD.EMPLOYEE_ID   and audit.EMPLOYEE_ID='"+employeeId+"'");

		String logInDate = null;
		String logOutDate = null;
		if ((fromDate == null && toDate == null)
				|| (fromDate.length() == 0 && toDate.length() == 0)) {
			DateFormat dateFormatGmt = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			logInDate = dateFormatGmt.format(new Date());
			queryToAppend.append(" AND audit.CREATED_DATE>='");
			queryToAppend.append(logInDate);
			queryToAppend.append("'");

		} else {
			DateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatGmt1 = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));
			String dt1 = null;
			String dt2 = null;
			Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
			dateFormatGmt.setCalendar(cal);

			logInDate = dateFormatGmt.parse(fromDate).toString();
			dt1 = dateFormatGmt1.format(dateFormatGmt.parse(fromDate));
			logOutDate = dateFormatGmt.parse(toDate).toString();
			dt2 = dateFormatGmt1.format(dateFormatGmt.parse(toDate));

			
			if (dt1.equalsIgnoreCase(dt2)) {
				queryToAppend.append(" AND audit.CREATED_DATE>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND audit.CREATED_DATE<='");
				queryToAppend.append(dt1.substring(0, 10));
				queryToAppend.append(" 23:59:00");
				queryToAppend.append("'");
			} else {
				queryToAppend.append(" AND audit.CREATED_DATE>='");
				queryToAppend.append(dt1);
				queryToAppend.append("' AND audit.CREATED_DATE<='");
				queryToAppend.append(dt2);
				queryToAppend.append("'");
			}

		}

		String query = queryToAppend.toString();
		String queryForCount = "SELECT COUNT(*) FROM( SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY CREATED_DATE ASC) AS Row,* FROM ("
				+ query + " ) AS DATA ";
		if (!filterQuery.equals("")) {
			filterQuery = "WHERE "
					+ filterQuery.substring(4, filterQuery.length());
		}

		queryForCount += filterQuery + ") AS DATA2  ) AS COUNT";

		count = this.jdbcTemplate.queryForInt(queryForCount);

		return count;
	}
/**********END :Auto Assignment************/

	
	public COMMON_Pagination<HELPDESK_Group> getGrpList_AC(int startCount,
			int pageNo, int pageSize, String filterQuery, String query,
			int userTimeZoneToSend,List<Map<String, Object>> grpList,String functionId) {
		COMMON_PaginationHelper<HELPDESK_Group> ph = new COMMON_PaginationHelper<HELPDESK_Group>();
/*		queryToAppend.append("  SELECT  distinct 	DAD.CATEGORY_ID AS SUB_CATEGORY_ID");
		queryToAppend.append("	FROM IC_IHD_CATEGORY_MASTER ICM, IC_IHD_CATEGORY_MASTER ICM1,IC_IHD_CATEGORY_MASTER ICM2, ");
		queryToAppend.append("  IC_IHD_DEFAULT_ASSIGNMENT_DETAILS DAD,IC_IHD_GROUP_MASTER IGM WHERE DAD.CATEGORY_ID=ICM.CATEGORY_ID	AND DAD.IS_ACTIVE=1	AND IGM.GROUP_ID=DAD.GROUP_ID");
		queryToAppend.append("  AND ICM.PARENT_ID=ICM1.CATEGORY_ID AND ICM1.PARENT_ID=ICM2.CATEGORY_ID");
		String queryForCount = queryToAppend.toString().substring(queryToAppend.indexOf("FROM"), queryToAppend.length());
		queryForCount = " SELECT COUNT( distinct DAD.CATEGORY_ID ) " + queryForCount +filterQuery;*/
		
		String queryForCount="";
		String dataQuery="	Select * from (SELECT ROW_NUMBER() OVER (ORDER BY SUB_CATEGORY_ID ASC ) as Row,SUB_CATEGORY_ID,SUB_CATEGORY_NAME,CATEGORY_ID,CATEGORY_NAME,PARENT_ID,PARENT_NAME  from (" +
			  "	SELECT distinct  dad.CATEGORY_ID as SUB_CATEGORY_ID,icm.NAME  as SUB_CATEGORY_NAME  ,icm2.CATEGORY_ID as CATEGORY_ID,icm2.NAME as CATEGORY_NAME,icm3.CATEGORY_ID as PARENT_ID,icm3.NAME as PARENT_NAME" +
			  "	from IC_IHD_DEFAULT_ASSIGNMENT_DETAILS dad,IC_IHD_CATEGORY_MASTER icm,IC_IHD_CATEGORY_MASTER icm2,IC_IHD_CATEGORY_MASTER icm3" +
			  "	where icm.CATEGORY_ID=dad.CATEGORY_ID" +
			  "	and icm.PARENT_ID=icm2.CATEGORY_ID" +
			  "	and  DAD.IS_ACTIVE=1" +
			  "	and icm2.PARENT_ID=icm3.CATEGORY_ID	and icm3.CATEGORY_ID="+functionId+ "  "+ filterQuery+") as data1 	) AS DATA	WHERE Row BETWEEN ? AND ? order by row";
		
		queryForCount="	Select count(*) from (SELECT ROW_NUMBER() OVER (ORDER BY SUB_CATEGORY_ID ASC ) as Row,SUB_CATEGORY_ID,SUB_CATEGORY_NAME,CATEGORY_ID,CATEGORY_NAME,PARENT_ID,PARENT_NAME  from (" +
		  "	SELECT distinct  dad.CATEGORY_ID as SUB_CATEGORY_ID,icm.NAME  as SUB_CATEGORY_NAME  ,icm2.CATEGORY_ID as CATEGORY_ID,icm2.NAME as CATEGORY_NAME,icm3.CATEGORY_ID as PARENT_ID,icm3.NAME as PARENT_NAME" +
		  "	from IC_IHD_DEFAULT_ASSIGNMENT_DETAILS dad,IC_IHD_CATEGORY_MASTER icm,IC_IHD_CATEGORY_MASTER icm2,IC_IHD_CATEGORY_MASTER icm3" +
		  "	where icm.CATEGORY_ID=dad.CATEGORY_ID" +
		  "	and icm.PARENT_ID=icm2.CATEGORY_ID" +
		  "	and  DAD.IS_ACTIVE=1" +
		  "	and icm2.PARENT_ID=icm3.CATEGORY_ID	and icm3.CATEGORY_ID="+functionId+ "  "+ filterQuery+") as data1 	) AS DATA";
		
		String dataQueryForGrp="SELECT 		distinct 		DAD.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME, " +
				"		DAD.GROUP_ID AS GROUP_ID , IGM.NAME AS GROUP_NAME " +
				"		FROM IC_IHD_CATEGORY_MASTER ICM, " +
				"		IC_IHD_CATEGORY_MASTER ICM1, " +
				"		IC_IHD_CATEGORY_MASTER ICM2, " +
				"		IC_IHD_DEFAULT_ASSIGNMENT_DETAILS DAD, " +
				"		IC_IHD_GROUP_MASTER IGM " +
				"		WHERE DAD.CATEGORY_ID=ICM.CATEGORY_ID " +
				"		AND DAD.IS_ACTIVE=1 " +
				"		AND IGM.GROUP_ID=DAD.GROUP_ID " +
				"		AND ICM.PARENT_ID=ICM1.CATEGORY_ID " +
				"		AND ICM1.PARENT_ID=ICM2.CATEGORY_ID order by DAD.CATEGORY_ID";
		 
	
		 
		 return ph.fetchGrpPage(jdbcTemplate, queryForCount, dataQuery,dataQueryForGrp,
					new Object[] { startCount, pageSize }, pageNo, pageSize,
					userTimeZoneToSend);
		
	}
	
	
	public COMMON_Pagination<HELPDESK_Group> getGrpListDept_AC(int startCount,
			int pageNo, int pageSize, String filterQuery, String query,
			int userTimeZoneToSend,List<Map<String, Object>> grpList) {
		StringBuffer queryToAppend = new StringBuffer();
		COMMON_PaginationHelper<HELPDESK_Group> ph = new COMMON_PaginationHelper<HELPDESK_Group>();
		
		queryToAppend.append("SELECT distinct DDA.DEPT_ID AS DEPT_ID,DDA.CATEGORY_ID AS SUB_CATEGORY_ID	");		
		queryToAppend.append("		from dbo.IC_IHD_DEPT_DEFAULT_ASSIGNMENT DDA ,"); 
		queryToAppend.append("		IC_IHD_GROUP_MASTER IGM ,"); 
		queryToAppend.append("		IC_IHD_DEPT_MASTER DEP,"); 
		queryToAppend.append("		IC_IHD_CATEGORY_MASTER ICM, ");
		queryToAppend.append("		IC_IHD_CATEGORY_MASTER ICM1,"); 
		queryToAppend.append("		IC_IHD_CATEGORY_MASTER ICM2 ");
		queryToAppend.append("		WHERE DDA.IS_ACTIVE=1 ");
		queryToAppend.append("				AND DDA.GROUP_ID=IGM.GROUP_ID ");
		queryToAppend.append("				AND DEP.DEPT_ID=DDA.DEPT_ID ");
		queryToAppend.append("				AND DDA.CATEGORY_ID=ICM.CATEGORY_ID ");
		queryToAppend.append("				AND ICM.PARENT_ID=ICM1.CATEGORY_ID ");
		queryToAppend.append("				AND ICM1.PARENT_ID=ICM2.CATEGORY_ID ");   
		queryToAppend.append(				filterQuery	);
		queryToAppend.append("				group by DDA.DEPT_ID,DDA.CATEGORY_ID");
		String queryForCount ="SELECT COUNT( * ) FROM   ("+queryToAppend.toString() +") as a" ;
		
		
		String dataQuery="Select * from (SELECT ROW_NUMBER() OVER (ORDER BY DEPT_ID ASC ) as Row	,DEPT_ID,DEPT_NAME,SUB_CATEGORY_ID," +
				"	SUB_CATEGORY_NAME,CATEGORY_ID,CATEGORY_NAME,PARENT_ID,PARENT_NAME from (" +
				"	select distinct ICM1.CATEGORY_ID as CATEGORY_ID,ICM1.NAME as CATEGORY_NAME," +
				"  	DDA.DEPT_ID AS DEPT_ID,DEP.DEPT_NAME,DDA.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME   ," +
				"	ICM2.CATEGORY_ID AS PARENT_ID,ICM2.NAME AS PARENT_NAME" +
				"	from dbo.IC_IHD_DEPT_DEFAULT_ASSIGNMENT DDA ,IC_IHD_GROUP_MASTER IGM , IC_IHD_DEPT_MASTER DEP, IC_IHD_CATEGORY_MASTER ICM," +
				"	IC_IHD_CATEGORY_MASTER ICM1, IC_IHD_CATEGORY_MASTER ICM2" +
				"	WHERE DDA.IS_ACTIVE=1" +
				"	AND DDA.GROUP_ID=IGM.GROUP_ID" +
				"	AND DEP.DEPT_ID=DDA.DEPT_ID" +
				"	AND DDA.CATEGORY_ID=ICM.CATEGORY_ID" +
				"	AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
				"	AND ICM1.PARENT_ID=ICM2.CATEGORY_ID "+filterQuery +
				"	) as data1" +
				"	) AS DATA WHERE Row BETWEEN ? AND ? order by row";
		
		String dataQueryForGrp="select distinct DDA.DEPT_ID AS DEPT_ID,DEP.DEPT_NAME,DDA.CATEGORY_ID AS SUB_CATEGORY_ID,ICM.NAME as SUB_CATEGORY_NAME," +
				"    	DDA.GROUP_ID AS GROUP_ID," +
				"    	IGM.NAME AS GROUP_NAME" +
				"   	from dbo.IC_IHD_DEPT_DEFAULT_ASSIGNMENT DDA ," +
				"   	IC_IHD_GROUP_MASTER IGM , " +
				"		IC_IHD_DEPT_MASTER DEP," +
				"		IC_IHD_CATEGORY_MASTER ICM, " +
				"		IC_IHD_CATEGORY_MASTER ICM1, " +
				"		IC_IHD_CATEGORY_MASTER ICM2 " +
				"		WHERE DDA.IS_ACTIVE=1 " +
				"   	AND DDA.GROUP_ID=IGM.GROUP_ID " +
				"		AND DEP.DEPT_ID=DDA.DEPT_ID" +
				"		AND DDA.CATEGORY_ID=ICM.CATEGORY_ID" +
				"		AND ICM.PARENT_ID=ICM1.CATEGORY_ID" +
				"		AND ICM1.PARENT_ID=ICM2.CATEGORY_ID" +
				"		ORDER BY DEPT_ID";
		 
	
		 
		 return ph.fetchGrpPageDept(jdbcTemplate, queryForCount, dataQuery,dataQueryForGrp,
					new Object[] { startCount, pageSize }, pageNo, pageSize,
					userTimeZoneToSend);
		
	}


	public COMMON_Pagination<HELPDESK_Category> getCategoryList(int startCount,
			int pageNo, int pageSize, String filterQuery, String query,
			int userTimeZoneToSend, String function_id,String category_name,String status_val) {
		
		COMMON_PaginationHelper<HELPDESK_Category> ph = new COMMON_PaginationHelper<HELPDESK_Category>();
		//Exclude Reporting Officer ie.,  Catapp.APPROVER_ID<>1
		String queryForActiveOrInactiveStatus="";
		if(status_val.equals("0")){
			queryForActiveOrInactiveStatus="(subcat.IS_ACTIVE='"+status_val+"' or parentcat.IS_ACTIVE='"+status_val+"')";
		}else{
			queryForActiveOrInactiveStatus="(subcat.IS_ACTIVE='"+status_val+"' and  parentcat.IS_ACTIVE='"+status_val+"')";
		}
		
		query="SELECT  funcat.CATEGORY_ID as 'FUNCTION_ID', funcat.NAME as 'FUNCTION_NAME', parentcat.CATEGORY_ID as 'CATEGORY_ID'," +
			  "	 parentcat.name as 'CATEGORY_NAME'," +
			  "  case when parentcat.IS_ACTIVE= 1 then 'Active'else 'Inactive' end 'CATEGORY_STATUS', " +
			  "	 subcat.CATEGORY_ID as 'SUBCATEGORY_ID'," +
			  "	 subcat.NAME as 'SUBCATEGORY_NAME'," +
			  "	 case when subcat.IS_ACTIVE= 1 then 'Active' else 'Inactive' end 'SUBCATEGORY_STATUS'," +
			  "	 MAX(CASE WHEN catapp.APPROVER_ORDER = 1 THEN appr.DESCRIPTION  ELSE NULL END) AS 'APPROVER_LEVEL_1'," +
			  "	 MAX(CASE WHEN catapp.APPROVER_ORDER = 2 THEN appr.DESCRIPTION  ELSE NULL END )AS 'APPROVER_LEVEL_2'," +
			  "	 MAX(CASE WHEN catapp.APPROVER_ORDER = 3 THEN appr.DESCRIPTION  ELSE NULL END )AS 'APPROVER_LEVEL_3'," +
			  "	 pm.description as 'RECOMMENDED_PRIORITY'," +
			  "	 case when subcat.IS_CHANGE_REQUEST =1 then 'Yes' else 'No' end  'IS_CHANGE_REQUEST'," +
			  "  subcat.LINK 'LINK'"+//Added
			  "	 FROM IC_IHD_CATEGORY_MASTER funcat" +
			  "	 left outer join  IC_IHD_CATEGORY_MASTER parentcat  on funcat.CATEGORY_ID=parentcat.PARENT_ID" +
			  "	 left outer join  IC_IHD_CATEGORY_MASTER subcat on parentcat.CATEGORY_ID=subcat.PARENT_ID" +
			  "	 left outer join IC_IHD_CATEGORY_APPROVAL_DETAILS Catapp  on  Catapp.CATEGORY_ID=subcat.CATEGORY_ID and Catapp.IS_ACTIVE=1" +
			  "	 left outer join IC_PRIORITY_MASTER PM on subcat.RECOMMENDED_PRIORITY=PM.PRIORITY_ID" +
			  "	 left outer join IC_IHD_APPROVER_MASTER appr on appr.APPROVER_ID = catapp.APPROVER_ID   " +
			  "	 where " + queryForActiveOrInactiveStatus+"  and (subcat.NAME like '%"+category_name+"%' or parentcat.NAME like '%"+category_name+"%')" +
			  "	 and funcat.PARENT_ID=0" +
			  "  and funcat.CATEGORY_ID=" +function_id+ filterQuery +""+
			  "	 Group by funcat.CATEGORY_ID, funcat.NAME,parentcat.name,parentcat.CATEGORY_ID,parentcat.IS_ACTIVE,subcat.NAME,subcat.CATEGORY_ID," +
			  "	 subcat.IS_ACTIVE, pm.description ,subcat.IS_CHANGE_REQUEST,subcat.LINK	";

		
		
		String dataQuery = query
		.replace(
				"SELECT",
				"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY FUNCTION_NAME ASC) AS Row,* FROM (SELECT ");
				dataQuery = dataQuery+ ") AS DATA  ) AS DATA2 "
							+ "WHERE Row BETWEEN ? AND ? ";
					
	String queryForCount=query
				.replace(
						"SELECT",
						"SELECT MAX(Row) FROM (SELECT ROW_NUMBER() OVER (ORDER BY FUNCTION_NAME ASC) AS Row,* FROM (SELECT ");
	queryForCount = queryForCount+ ") AS DATA  ) AS DATA2  ";
						
	
				
		return ph.fetchCategoryPage(jdbcTemplate, queryForCount, dataQuery,
						new Object[] { startCount, pageSize }, pageNo, pageSize,
						userTimeZoneToSend);
		
	}
	
	public COMMON_Pagination<ADMIN_ModifyLocation> getLocationDetails(int startCount,
			int pageNo, int pageSize, String filterQuery, String query,
			int userTimeZoneToSend, String locationID,String status_val) {
	
			
		COMMON_PaginationHelper<ADMIN_ModifyLocation> ph = new COMMON_PaginationHelper<ADMIN_ModifyLocation>();
	
		query="SELECT locmaster.LOCATION_ID as LOCATION_ID,locdetail.LOC_DET_ID as LOCDETID,locmaster.CITY as CITY," +
				"locdetail.BUILDING as BUILDING," +
				"locdetail.FLOOR as FLOOR,case when locdetail.IS_ACTIVE=1 then 'Active' else 'Inactive' end as 'STATUS'" +
				"from IC_LOCATION_DETAILS locdetail,IC_LOCATION_MASTER locmaster where " +
				"locmaster.LOCATION_ID=locdetail.LOCATION_ID AND locmaster.CITY LIKE '%"+locationID+"%' "+filterQuery;
		
		
		String dataQuery = query
		.replace(
				"SELECT",
				"SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY CITY ASC) AS Row,* FROM (SELECT ");
				dataQuery = dataQuery+ ") AS DATA  ) AS DATA2 "
							+ "WHERE Row BETWEEN ? AND ? ";
					
	String queryForCount=query
				.replace(
						"SELECT",
						"SELECT MAX(Row) FROM (SELECT ROW_NUMBER() OVER (ORDER BY CITY ASC) AS Row,* FROM (SELECT ");
	queryForCount = queryForCount+ ") AS DATA  ) AS DATA2  ";
	
					
		return ph.fetchlocationPage(jdbcTemplate, queryForCount, dataQuery,
						new Object[] { startCount, pageSize }, pageNo, pageSize,
						userTimeZoneToSend);
		
	}
}
