/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.MailTracker;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.constants.MAILTARCKER_QueryConstants;
import com.igate.iconnect.dao.MAILTRACKER_DAO;

@Transactional(rollbackFor = Exception.class)
public class MAILTRACKER_DAOImpl implements MAILTRACKER_DAO {

	private JdbcTemplate jdbcTemplate;
	private static final String MAIL_ID="MAIL_ID";
	private static final String TO_ADDRESS="TO_ADDRESS";
	private static final String FROM_ADDRESS="FROM_ADDRESS";
	private static final String SUBJEC="SUBJECT";
	private static final String DESCRIPTIO="DESCRIPTION";
	private static final String ATTACHMENT_PATH="ATTACHMENT_PATH";
	private static final String ATTACHMENT_NAME="ATTACHMENT_NAME";	
	private static final String SEVERITY_ID="SEVERITY_ID";
	private static final String RECEIVED_DATE="RECEIVED_DATE";
	private static final String REFERENCEID="REFERENCE_ID";
	private static final String CREATED_BY="CREATED_BY";
	private static final String CREATED_DATE="CREATED_DATE";
	private static final String MODIFIED_BY="MODIFIED_BY";
	private static final String MODIFIED_DATE="MODIFIED_DATE";
	private static final String EMPLOYEE_ID="EMPLOYEE_ID";
	private static final String STATUS="STATUS";
	private static final String SOURC="SOURCE";
	private static final String REASON="REASON";
	private static final String MENU_IDE="MENU_ID";
	private static final String LOCKED_BY="LOCKED_BY";
	private static final String LOCKED_DATE="LOCKED_DATE";
	private static final String DISCARD="Discard";
	private static final String STATUSS="status";
	private static final String CC_ADDRESS="CC_ADDRESS";
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public MailTracker getMailTrackerDetails(String mailid) {

		return this.jdbcTemplate.queryForObject(
					MAILTARCKER_QueryConstants.IC_IHD_MAIL_TRACKER_DETAILS,
					new Object[] { mailid }, new MailTrackerBeanMapper());
	}

	private static class MailTrackerBeanMapper implements
			ParameterizedRowMapper<MailTracker> {
		public MailTracker mapRow(ResultSet rs, int arg1) throws SQLException {

			MailTracker mailtrackerbean = new MailTracker();
			mailtrackerbean.setMAIL_ID(rs.getString(MAIL_ID));
			mailtrackerbean.setTO_ADDRESS(rs.getString(TO_ADDRESS));
			mailtrackerbean.setFROM_ADDRESS(rs.getString(FROM_ADDRESS));
			mailtrackerbean.setSUBJECT(rs.getString(SUBJEC));
			mailtrackerbean.setDESCRIPTION(rs.getString(DESCRIPTIO));
			mailtrackerbean.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));
			mailtrackerbean.setSEVERITY_ID(rs.getString(SEVERITY_ID));
			mailtrackerbean.setSTATUS(rs.getString(STATUS));
			mailtrackerbean.setRECEIVED_DATE(rs.getString(RECEIVED_DATE));
			mailtrackerbean.setSOURCE(rs.getString(SOURC));
			mailtrackerbean.setREFERENCE_ID(rs.getString(REFERENCEID));
			mailtrackerbean.setREASON(rs.getString(REASON));
			mailtrackerbean.setCREATED_BY(rs.getString(CREATED_BY));
			mailtrackerbean.setCREATED_DATE(rs.getString(CREATED_DATE));
			mailtrackerbean.setMODIFIED_BY(rs.getString(MODIFIED_BY));
			mailtrackerbean.setMODIFIED_DATE(rs.getString(MODIFIED_DATE));
			mailtrackerbean.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
			mailtrackerbean.setEMPLOYEE_ID(rs.getString(EMPLOYEE_ID));
			
			return mailtrackerbean;
		}
	}

	public int discardMailTracker(String mailid) {
		return this.jdbcTemplate.update(
				MAILTARCKER_QueryConstants.IC_IHD_MAIL_TRACKER_DISCARD_UPDATE,
                DISCARD, mailid);
	}

	/*
	 * Changed By :714599 Comments : Below method created to get the details
	 * from HR Mail tracker when HR Executive/HR Manager tries to access the
	 * mail
	 */
	public MailTracker getMailTrackerDetailsForHR(String mailid) {

		try {
			return this.jdbcTemplate.queryForObject(
						MAILTARCKER_QueryConstants.IC_IHD_HR_MAIL_TRACKER_DETAILS,
						new Object[] { mailid }, new MailTrackerBeanMapperHR());
		} catch (DataAccessException e) {
			return this.jdbcTemplate.queryForObject(
					MAILTARCKER_QueryConstants.IC_IHD_HR_MAIL_TRACKER_DETAILS_ONEXCEPTION,
					new Object[] { mailid }, new MailTrackerBeanMapper());
		}
	}
	private static class MailTrackerBeanMapperHR implements
	ParameterizedRowMapper<MailTracker> {
public MailTracker mapRow(ResultSet rs, int arg1) throws SQLException {

	MailTracker mailtrackerbean = new MailTracker();
	mailtrackerbean.setMAIL_ID(rs.getString(MAIL_ID));
	mailtrackerbean.setTO_ADDRESS(rs.getString(TO_ADDRESS));
	mailtrackerbean.setFROM_ADDRESS(rs.getString(FROM_ADDRESS));
	mailtrackerbean.setSUBJECT(rs.getString(SUBJEC));
	mailtrackerbean.setDESCRIPTION(rs.getString(DESCRIPTIO));
	mailtrackerbean.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));
	mailtrackerbean.setSEVERITY_ID(rs.getString(SEVERITY_ID));
	mailtrackerbean.setSTATUS(rs.getString(STATUS));
	mailtrackerbean.setRECEIVED_DATE(rs.getString(RECEIVED_DATE));
	mailtrackerbean.setSOURCE(rs.getString(SOURC));
	mailtrackerbean.setREFERENCE_ID(rs.getString(REFERENCEID));
	mailtrackerbean.setREASON(rs.getString(REASON));
	mailtrackerbean.setCREATED_BY(rs.getString(CREATED_BY));
	mailtrackerbean.setCREATED_DATE(rs.getString(CREATED_DATE));
	mailtrackerbean.setMODIFIED_BY(rs.getString(MODIFIED_BY));
	mailtrackerbean.setMODIFIED_DATE(rs.getString(MODIFIED_DATE));
	mailtrackerbean.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
	mailtrackerbean.setEMPLOYEE_ID(rs.getString(EMPLOYEE_ID));
	
	mailtrackerbean.setCC_ADDRESS(rs.getString("CC_ADDRESS"));
	
	
	return mailtrackerbean;
}
}

	/*
	 * Changed By :714599 Comments : Below method created to Discard the Mail
	 * from HR Mail tracker when HR Executive/HR Manager tries to discard the
	 * mail
	 */

	public int discardMailTrackerForHR(String mailid) {
		return this.jdbcTemplate
				.update(
						MAILTARCKER_QueryConstants.IC_IHD_HR_MAIL_TRACKER_DISCARD_UPDATE,
                        DISCARD, mailid);
	}

	public Map<String, Object> lockMail(JSONObject jsonobj)
			throws JSONException {
		Map<String, Object> lockdetails = new HashMap<String, Object>();

		try {
			lockdetails = this.jdbcTemplate.queryForMap(
					HELPDESK_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
                    jsonobj.get(MENU_IDE).toString(),
                    jsonobj.get(MAIL_ID).toString());
			lockdetails.put(STATUSS, "error");
		} catch (EmptyResultDataAccessException erde) {
			if (lockdetails.isEmpty()) {
				this.jdbcTemplate.update(
						HELPDESK_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
                        jsonobj.get(MAIL_ID).toString(),
                        jsonobj.get(MENU_IDE).toString(), "",
                        jsonobj.get(LOCKED_BY).toString(),
                        jsonobj.get(LOCKED_DATE).toString());
				lockdetails.put(STATUSS, "success");
			} else {
				lockdetails.put(STATUSS, "error");
			}

		}
		return lockdetails;
	}

}
