/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.constants.HELPDESK_SLASQLQueryConstants;
import com.igate.iconnect.dao.HELPDESK_SlaDAO;

@Transactional(rollbackFor = Exception.class)
public class HELPDESK_SlaDAOImpl implements HELPDESK_SlaDAO {
	private JdbcTemplate jdbcTemplate;
	private static Logger log = Logger.getLogger(HELPDESK_SlaDAOImpl.class);

	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getAuditLog(String ticketID, long menuID) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_AUDITLOG, ticketID, menuID,ticketID, menuID);
	}

	public List<Map<String, Object>> geTicketDetails(long ticketID) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_TICKET_DETAILS, ticketID);
	}

	public List<Map<String, Object>> getSLAExclusionDates(long slaID,
			long locationID) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_SLA_EXCLUSION_DATES,
				slaID, locationID);
	}

	public List<Map<String, Object>> getSLAOperatingTime(long slaID) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_SLA_OPERATING_TIME, slaID);
	}

	public List<Map<String, Object>> getAuditLastResponseTime(String ticketID) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_AUDIT_LAST_RESPONSETIME,
				ticketID, ticketID);
	}

	public List<Map<String, Object>> getSLAOpertingTimeInfo(long sla_id,
			String org, String day) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_SLA_OPERTING_TIME_DETAILS,
				sla_id, org, day);
	}

	public String getSLAID(long categoryId, int priorityId) {

		return this.jdbcTemplate.queryForObject(
				HELPDESK_SLASQLQueryConstants.IC_IHD_CATEGORY_PRIORITY_DETAILS,
				new Object[] { categoryId, priorityId }, String.class);

	}

	public long getServiceOPRID(long locationID, long locationDetailId,
			long functionID) {

		String result = "";
		long opr_id = 0;
		try {
			String sql = HELPDESK_SLASQLQueryConstants.IC_IHD_SERVICE_WINDOW_DETAILS;
			result = this.jdbcTemplate.queryForObject(sql, new Object[] {
					locationID, locationDetailId, functionID }, String.class);
			opr_id = Long.parseLong(result.trim());
		} catch (EmptyResultDataAccessException e) {
			log.error("Error while retrieving ServiceOPR ID :" + e);
		}
		return opr_id;
	}

	public long getGroupOPRID(long groupID, long functionID, long locationID) {

		String result = "";
		long opr_id = 0;
		try {
			String sql = HELPDESK_SLASQLQueryConstants.IC_IHD_GROUP_FUNCTION_MAPPING;
			result = this.jdbcTemplate.queryForObject(sql, new Object[] {
					groupID, functionID, locationID }, String.class);
			opr_id = Long.parseLong(result.trim());
		} catch (EmptyResultDataAccessException e) {
			log.error("Error while retrieving GroupOPR ID :" + e);
			// e.printStackTrace();
		}
		return opr_id;

	}

	public List<Map<String, Object>> getTicketInfo(long ticketId) {
		return this.jdbcTemplate.queryForList(
				HELPDESK_SLASQLQueryConstants.IC_IHD_TICKET_DETAILS_INFO,
				ticketId);
	}
}

/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:702166

 Changes Made on:Jun 10, 2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/
