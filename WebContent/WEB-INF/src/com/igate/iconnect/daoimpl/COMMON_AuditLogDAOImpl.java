/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.COMMON_AuditLog;
import com.igate.iconnect.constants.COMMON_AuditLogSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_AuditLogDAO;

@Transactional(rollbackFor = Exception.class)
public class COMMON_AuditLogDAOImpl implements COMMON_AuditLogDAO {

	public JdbcTemplate jdbcTemplate;
	private static Logger log = Logger.getLogger(COMMON_AuditLogDAOImpl.class);
	private static final String AUDIT_ID = "AUDIT_ID";
	private static final String PREVIOUS_STATE = "PREVIOUS_STATE";
	private static final String CURRENT_STATE = "CURRENT_STATE";
	private static final String CREATED_DATE = "CREATED_DATE";
	private static final String CHANGED_BY = "CHANGED_BY";
	private static final String ACTION = "ACTION";
	private static final String DATA_CHANGE = "DATA_CHANGE";
	private static final String Create = "Create";
	private static final String Update = "Update";
	private static final String Delete = "Delete";
	private static final String RESPONSE_TIME="RESPONSE_TIME";

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Description: To get the history information per ticket.
	 * 
	 * @param To
	 *            pass the ticket no either issue log ticket or helpdesk ticket.
	 * @return To return the list of values per ticket.
	 */
	public List<COMMON_AuditLog> getAuditLogInfo(String ticketNo,
			String menuID, final int userTimeZone) {
		String sql = COMMON_AuditLogSQLQueryConstants.SELECT_TICKET_HISTORY;
		return this.jdbcTemplate.query(sql, new Object[] { ticketNo, menuID },
				new RequestMapper(userTimeZone));
	}

	private static class RequestMapper implements
			ParameterizedRowMapper<COMMON_AuditLog> {
		int userTimeZone = 0;

		public RequestMapper(int userTimeZone) {
			this.userTimeZone = userTimeZone;
		}

		public COMMON_AuditLog mapRow(ResultSet rs, int arg1)
				throws SQLException {

			COMMON_AuditLog auditlogInfo = new COMMON_AuditLog();

			auditlogInfo.setHistoryId(rs.getLong(AUDIT_ID));
			String prevState = rs.getString(PREVIOUS_STATE);
			if (prevState == null)
				auditlogInfo.setPreviousState("-");
			else
				auditlogInfo.setPreviousState(prevState);

			String currentState = rs.getString(CURRENT_STATE);
			if (currentState == null)
				auditlogInfo.setCurrentState("-");
			else
				auditlogInfo.setCurrentState(currentState);

			String timeStamp = rs.getString(CREATED_DATE);
			if (timeStamp != null && !timeStamp.equalsIgnoreCase("")) {
				auditlogInfo.setChangedDate(CustomDateFormatConstants
						.showUserTimeZonewithTimezoneID(timeStamp,
								this.userTimeZone));
			}

			auditlogInfo.setChangedBy(rs.getString(CHANGED_BY));
			if (rs.getString(ACTION).equals("I")) {
				auditlogInfo.setAction(Create);
			}
			if (rs.getString(ACTION).equals("U")) {
				auditlogInfo.setAction(Update);
			}
			if (rs.getString(ACTION).equals("D")) {
				auditlogInfo.setAction(Delete);
			}
			String responseTime = rs.getString(RESPONSE_TIME);
			if(responseTime==null)
				auditlogInfo.setResponseTime("-");
			else if(responseTime.equalsIgnoreCase(""))
				auditlogInfo.setResponseTime("-");
			else
				auditlogInfo.setResponseTime(rs.getString(RESPONSE_TIME));
			return auditlogInfo;
		}
	}

	/**
	 * Description: To get the history detail information per ticket
	 * e.g:field_name,old_Val & New_Val.
	 * 
	 * @param To
	 *            pass the history id of the ticket.
	 * @return To return the list of values per ticket.
	 */
	public List<COMMON_AuditLog> getAuditLogDetailInfo(int historyId) {
		String sql = COMMON_AuditLogSQLQueryConstants.SELECT_TICKET_HISTORY_DETAIL;
		return this.jdbcTemplate.query(sql, new Object[] { historyId },
				new RequestMapperDetail());
	}

	private static class RequestMapperDetail implements
			ParameterizedRowMapper<COMMON_AuditLog> {

		public COMMON_AuditLog mapRow(ResultSet rs, int arg1)
				throws SQLException {

			COMMON_AuditLog auditlogInfo = new COMMON_AuditLog();

			auditlogInfo.setDataChange(rs.getString(DATA_CHANGE));

			return auditlogInfo;
		}
	}

}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 10,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

