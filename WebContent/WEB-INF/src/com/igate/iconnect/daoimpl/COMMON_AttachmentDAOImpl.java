/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.igate.iconnect.BO.HELPDESK_Attachment;
import com.igate.iconnect.BO.HELPDESK_Emergency_Attachment;
import com.igate.iconnect.BO.TECHCR_Attachment;
import com.igate.iconnect.constants.COMMON_AttachmentSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_AttachmentDAO;
import com.igate.iconnect.util.COMMON_ExecuteProcedure;
import com.igate.iconnect.util.MapToStringUtil;

@Transactional(rollbackFor = Exception.class)
public class COMMON_AttachmentDAOImpl implements COMMON_AttachmentDAO {

	public JdbcTemplate jdbcTemplate;
	COMMON_ExecuteProcedure DAOUtilObj;
	MapToStringUtil MapToStringUtilobj;
	private static Logger log = Logger
			.getLogger(COMMON_AttachmentDAOImpl.class);
	private static final String CATEGORY_ID = "CATEGORY_ID";
	private static final String SUBCATEGORY_ID = "SUBCATEGORY_ID";
	private static final String WORKFLOW_STATE = "WORKFLOW_STATE";
	private static final String TICKET_ID = "TICKET_ID";
	private static final String REFERENCE_ID = "REFERENCE_ID";
	private static final String APPROVER = "APPROVER";
	private static final String ATTACHMENT_NAME = "ATTACHMENT_NAME";
	private static final String ATTACHMENT_PATH = "ATTACHMENT_PATH";
	private static final String VERIFIED_BY = "VERIFIED_BY";
	private static final String VERIFIED_NAME = "VERIFIED_NAME";
	private static final String NEXTSTATE = "NEXTSTATE";
	private static final String NEXTAPPROVERID = "NEXTAPPROVERID";
	private static final String VERIFICATION_COMMENTS = "VERIFICATION_COMMENTS";
	private static final String CREATED_BY = "CREATED_BY";
	private static final String CREATED_NAME = "CREATED_NAME";
	private static final String FUNCTION_NAME = "FUNCTION_NAME";
	private static final String CREATED_DATE = "CREATED_DATE";
	private static final String MODIFIED_BY = "MODIFIED_BY";
	private static final String MODIFIED_NAME = "MODIFIED_NAME";
	private static final String MODIFIED_DATE = "MODIFIED_DATE";
	private static final String ISSUE_ID = "ISSUE_ID";
	private static final String REFERENC_ID = "reference_id";
	private static final String MENU_ID = "menu_id";
	private static final String CATEGOR_ID = "category_id";
	private static final String PRIORITY_ID = "priority_id";
	private static final String ASSIGNED_GROUP = "assigned_group";
	private static final String CURRENT_STATE = "current_state";
	private static final String ACTION = "action";
	private static final String CHANGED_BY = "changed_by";
	private static final String CHANGED_DATE = "changed_date";
	private static final String FIELD_VALUE = "field_value";
	private static final String PREV_STATE = "prev_state";
	//modified by 720307
	private static final String WORKFLOW_NAME = "WORKFLOW_NAME";
	private static final String IS_EX_EMPLOYEE = "IS_EX_EMPLOYEE";
	private static final String IS_CHECKED = "IS_CHECKED";
	
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setiConnectDAOUtil(COMMON_ExecuteProcedure dAOUtilObj) {
		DAOUtilObj = dAOUtilObj;
	}

	public void setiConnectMapToStringUtil(MapToStringUtil MapToStringUtil) {
		MapToStringUtilobj = MapToStringUtil;
	}

	/**
	 * Description: To get the attachment path per ticket.
	 * 
	 * @param To
	 *            pass the ticket no of the helpdesk ticket.
	 * @return To return the list of values per ticket.
	 */
	public List<HELPDESK_Attachment> getAttachmentList(String ticketid) {
		String sql = COMMON_AttachmentSQLQueryConstants.SELECT_ATTACHMENTS_FOR_TICKET;
		return this.jdbcTemplate.query(sql, new Object[] { ticketid },
				new RequestMapper());

	}

	private static class RequestMapper implements
			ParameterizedRowMapper<HELPDESK_Attachment> {

		public HELPDESK_Attachment mapRow(ResultSet rs, int arg1)
				throws SQLException {
			HELPDESK_Attachment attachment = new HELPDESK_Attachment();
			attachment.setCATEGORY_ID(Integer.toString(rs.getInt(CATEGORY_ID)));
			attachment.setSUBCATEGORY_ID(Integer.toString(rs
					.getInt(SUBCATEGORY_ID)));
			attachment.setWORKFLOW_STATE(Integer.toString(rs
					.getInt(WORKFLOW_STATE)));
			attachment.setTICKET_ID(Integer.toString(rs.getInt(TICKET_ID)));
			attachment.setREFERENCE_ID(Integer
					.toString(rs.getInt(REFERENCE_ID)));
			attachment.setAPPROVER(rs.getString(APPROVER));
			attachment.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
			attachment.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));
			attachment.setVERIFIED_BY(rs.getString(VERIFIED_BY));
			attachment.setVERIFIED_NAME(rs.getString(VERIFIED_NAME));
			attachment.setNEXTSTATE(rs.getString(NEXTSTATE));
			attachment.setNEXTAPPROVERID(rs.getString(NEXTAPPROVERID));
			attachment.setVERIFICATION_COMMENTS(rs
					.getString(VERIFICATION_COMMENTS));
			attachment.setCREATED_BY(rs.getString(CREATED_BY));
			attachment.setCREATED_NAME(rs.getString(CREATED_NAME));
			attachment.setFUNCTION_NAME(rs.getString(FUNCTION_NAME));
			attachment.setIS_EX_EMPLOYEE(rs.getString(IS_EX_EMPLOYEE));
			attachment.setIS_CHECKED(rs.getString(IS_CHECKED));
			Timestamp createdDateTimeStamp = rs.getTimestamp(CREATED_DATE);
			if (createdDateTimeStamp != null) {
				attachment
						.setCREATED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(createdDateTimeStamp));
			} else {
				attachment.setCREATED_DATE("");
			}
			attachment.setMODIFIED_BY(rs.getString(MODIFIED_BY));
			attachment.setMODIFIED_NAME(rs.getString(MODIFIED_NAME));
			//modified by 720307
			attachment.setWORKFLOW_NAME(rs.getString(WORKFLOW_NAME));
			// try {
			Timestamp modifiedDateTimeStamp = rs.getTimestamp(MODIFIED_DATE);
			if (modifiedDateTimeStamp != null) {
				attachment
						.setMODIFIED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(modifiedDateTimeStamp));
			} else {
				attachment.setMODIFIED_DATE("");
			}
			// } catch (Exception e) {
			// attachment.setMODIFIED_DATE("");
			// }
			return attachment;
		}
	}

	/**
	 * Description: To get the attachment path per ticket.
	 * 
	 * @param To
	 *            pass the ticket no of the helpdesk ticket.
	 * @return To return the list of values per ticket.
	 */
	public List<TECHCR_Attachment> getAttachmentListForTCR(String issueid) {
        String sql = COMMON_AttachmentSQLQueryConstants.SELECT_ATTACHMENTS_FOR_TCR_TICKET;
        List<TECHCR_Attachment> listofAttachments = new ArrayList<TECHCR_Attachment>();
        try {
        	listofAttachments=this.jdbcTemplate.query(sql, new Object[] { issueid },
                    new TCRRequestMapper());
        } catch (EmptyResultDataAccessException erd) {
            log.error("Empty result set for risk" + erd);
            
        }
        return listofAttachments;
    }
	private static class TCRRequestMapper implements
			ParameterizedRowMapper<TECHCR_Attachment> {

		public TECHCR_Attachment mapRow(ResultSet rs, int arg1)
				throws SQLException {
			TECHCR_Attachment attachment = new TECHCR_Attachment();
			attachment.setISSUE_ID(Integer.toString(rs.getInt(ISSUE_ID)));
			attachment.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
			attachment.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));
			attachment.setAPPROVER_ATTACHMENT_NAME(rs.getString("approver_ATTACHMENT_NAME"));
			attachment.setAPPROVER_ATTACHMENT_PATH(rs.getString("approver_ATTACHMENT_PATH"));
			attachment.setCREATED_BY(rs.getString(CREATED_BY));
			attachment.setCREATED_NAME(rs.getString(CREATED_NAME));
			Timestamp createdDateTimeStamp = rs.getTimestamp(CREATED_DATE);
			if (createdDateTimeStamp != null) {
				attachment
						.setCREATED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(createdDateTimeStamp));
			} else {
				attachment.setCREATED_DATE("");
			}
			attachment.setMODIFIED_BY(rs.getString(MODIFIED_BY));
			attachment.setMODIFIED_NAME(rs.getString(MODIFIED_NAME));
			Timestamp modifiedDateTimeStamp = rs.getTimestamp(MODIFIED_DATE);
			if (modifiedDateTimeStamp != null) {
				attachment
						.setMODIFIED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(modifiedDateTimeStamp));
			} else {
				attachment.setMODIFIED_DATE("");
			}
			return attachment;
		}
	}

	public String updateAttachmentFortechCRTicket(
			final TECHCR_Attachment techCRBean,
			HashMap<String, Object> auditlogdetails, final String loginID) {

		this.jdbcTemplate
				.update(
						"update IC_TCR_TICKET_DETAILS set ATTACHMENT_NAME=?,ATTACHMENT_PATH=?,MODIFIED_BY=?,MODIFIED_DATE=? where ISSUE_ID=?",
						techCRBean.getATTACHMENT_NAME(), techCRBean
								.getATTACHMENT_PATH(), loginID,
						CustomDateFormatConstants.creationDateGMTFormat(),
						techCRBean.getISSUE_ID());

		String procName = "insert_audit_log";
		String argName[] = { REFERENC_ID, MENU_ID, CATEGOR_ID, PRIORITY_ID,
				ASSIGNED_GROUP, CURRENT_STATE, ACTION, CHANGED_BY,
				CHANGED_DATE, FIELD_VALUE, PREV_STATE };
		int argType[] = { Types.VARCHAR, Types.SMALLINT, Types.SMALLINT,
				Types.SMALLINT, Types.SMALLINT, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR };
		String paramVal[] = { techCRBean.getISSUE_ID(), "2", "0", "0", "0",
				techCRBean.getWORKFLOW_STATE(), "U", loginID,
				CustomDateFormatConstants.creationDateGMTFormat(),
				MapToStringUtil.getStringForMap(auditlogdetails),
				techCRBean.getWORKFLOW_STATE() };

		DAOUtilObj.executeiconnectProc(procName, argName, argType, paramVal,
				jdbcTemplate.getDataSource());

		this.jdbcTemplate.update(
				"DELETE IC_LOCKED_TICKET_DETAILS WHERE REFERENCE_ID= ?",
				techCRBean.getISSUE_ID());

		return "Success";
	}
	public List<HELPDESK_Attachment> getAttachmentMasterList(String ticketNo) {
		String sql = COMMON_AttachmentSQLQueryConstants.SELECT_ATTACHMENTS_FOR_MASTER_TICKET;
		return this.jdbcTemplate.query(sql, new Object[] { ticketNo },
				new RequestMapperMaster());
		
	}
	
	private static class RequestMapperMaster implements
	ParameterizedRowMapper<HELPDESK_Attachment> {

		public HELPDESK_Attachment mapRow(ResultSet rs, int arg1)
		throws SQLException {
			HELPDESK_Attachment attachment = new HELPDESK_Attachment();
			attachment.setCATEGORY_ID(Integer.toString(rs.getInt(CATEGORY_ID)));
			attachment.setSUBCATEGORY_ID(Integer.toString(rs
					.getInt(SUBCATEGORY_ID)));
			attachment.setWORKFLOW_STATE(Integer.toString(rs
					.getInt(WORKFLOW_STATE)));
			attachment.setTICKET_ID(Integer.toString(rs.getInt(TICKET_ID)));
			
			attachment.setATTACHMENT_NAME(rs.getString(ATTACHMENT_NAME));
			attachment.setATTACHMENT_PATH(rs.getString(ATTACHMENT_PATH));		
			attachment.setCREATED_BY(rs.getString(CREATED_BY));
			attachment.setCREATED_NAME(rs.getString(CREATED_NAME));
			attachment.setFUNCTION_NAME(rs.getString(FUNCTION_NAME));
			Timestamp createdDateTimeStamp = rs.getTimestamp(CREATED_DATE);
			if (createdDateTimeStamp != null) {
				attachment
						.setCREATED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(createdDateTimeStamp));
			} else {
				attachment.setCREATED_DATE("");
			}
			attachment.setMODIFIED_BY(rs.getString(MODIFIED_BY));
			attachment.setMODIFIED_NAME(rs.getString(MODIFIED_NAME));
			// try {
			Timestamp modifiedDateTimeStamp = rs.getTimestamp(MODIFIED_DATE);
			if (modifiedDateTimeStamp != null) {
				attachment
						.setMODIFIED_DATE(CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
								.format(modifiedDateTimeStamp));
			} else {
				attachment.setMODIFIED_DATE("");
			}
			// } catch (Exception e) {
			// attachment.setMODIFIED_DATE("");
			// }
	return attachment;
}
}
	/****************Added for Emergency L1*******************/
	public void batchInsert_Emergency(final List<HELPDESK_Emergency_Attachment> attachmentList, final String verifiedby){
		final String insertAttachmentQuery = "IF EXISTS (SELECT 1 FROM IC_IHD_TICKET_ATTACHMENT_DETAILS WHERE TICKET_ID=? AND REFERENCE_ID=?) " 
			+ " UPDATE IC_IHD_TICKET_ATTACHMENT_DETAILS SET ATTACHMENT_PATH=?,ATTACHMENT_NAME=?,VERIFIED_BY=?,MODIFIED_BY=?,MODIFIED_DATE=? ,IS_CHECKED=? WHERE TICKET_ID=? AND REFERENCE_ID=? " 
			+ " ELSE INSERT INTO IC_IHD_TICKET_ATTACHMENT_DETAILS (TICKET_ID"
			+ ",REFERENCE_ID"
			+ ",ATTACHMENT_PATH"
			+ ",ATTACHMENT_NAME"
			+ ",VERIFIED_BY"
			+ ",CREATED_BY"
			+ " ,CREATED_DATE"
			+ " ) VALUES(?,?,?,?,?,?,?)";
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		final String created_date = dateFormatGmt.format(new Date());
		
		jdbcTemplate.batchUpdate(insertAttachmentQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						HELPDESK_Emergency_Attachment obj = attachmentList.get(i);
						ps.setObject(1, Long.parseLong(obj.getTICKET_ID()));
						ps.setInt(2, Integer.parseInt(obj.getREFERENCE_ID()));
						ps.setString(3, obj.getATTACHMENT_PATH());
						ps.setString(4, obj.getATTACHMENT_NAME());
						ps.setString(5, verifiedby);
						ps.setString(6, verifiedby);
						ps.setString(7, created_date);
						ps.setBoolean(8, obj.isAttachmentCkBox());
						ps.setObject(9, Long.parseLong(obj.getTICKET_ID()));
						ps.setInt(10, Integer.parseInt(obj.getREFERENCE_ID()));
						ps.setObject(11, Long.parseLong(obj.getTICKET_ID()));
						ps.setInt(12, Integer.parseInt(obj.getREFERENCE_ID()));
						ps.setString(13, obj.getATTACHMENT_PATH());
						ps.setString(14, obj.getATTACHMENT_NAME());
						ps.setString(15, verifiedby);
						ps.setString(16, verifiedby);
						ps.setString(17, created_date);
					}

					public int getBatchSize() {
						return attachmentList.size();
					}
		});
	}
	public void batchUpdateWithoutAttachmentList(final List<HELPDESK_Emergency_Attachment> withoutAttachmentList, final String verifiedby){
		final String upadteWithoutAttachmentQuery = "UPDATE IC_IHD_TICKET_ATTACHMENT_DETAILS SET IS_CHECKED=?,VERIFIED_BY=?,MODIFIED_BY=?,MODIFIED_DATE=? WHERE TICKET_ID=? AND REFERENCE_ID=?";
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		final String created_date = dateFormatGmt.format(new Date());
		jdbcTemplate.batchUpdate(upadteWithoutAttachmentQuery,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						HELPDESK_Emergency_Attachment obj = withoutAttachmentList.get(i);
				        ps.setBoolean(1, obj.isAttachmentCkBox());
						ps.setString(2, verifiedby);
						ps.setString(3, verifiedby);
						ps.setString(4, created_date);
						ps.setObject(5, Long.parseLong(obj.getTICKET_ID()));
						ps.setInt(6, Integer.parseInt(obj.getREFERENCE_ID()));
										
					}

					public int getBatchSize() {
						return withoutAttachmentList.size();
					}
		});
	
	}
}
/*-----------------------------------------------------------------------------

 Log: 

 Start-----Version 1.0-----

 Changes Made:New File Created

 Changes Made By:712836

 Changes Made on:JUNE 13,2011

 End-------Version 1.0-------



 -----------------------------------------------------------------------------*/

