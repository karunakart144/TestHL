package com.igate.iconnect.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.igate.iconnect.constants.BULLETINBOARD_SQLQueryConstants;
import com.igate.iconnect.dao.BULLETINBOARD_CreateUpdateDAO;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;

public class BULLETINBOARD_CreateUpdateDAOImpl implements
		BULLETINBOARD_CreateUpdateDAO {
	private static final String BULLETIN_DESCRIPTION = "BULLETIN_DESCRIPTION";
	private static final String BULLETIN_HEADER = "BULLETIN_HEADER";
	private static final String CREATEDBY = "CREATEDBY";
	private static final String CREATEDDATE = "CREATEDDATE";
	private static final String MASTER_TICKET_ID = "MASTER_TICKET_ID";
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String insertBulletinBoard( final HashMap<String, Object> insertdetails) {
		final HELPDESK_SpecialCharacterConverter convert= new HELPDESK_SpecialCharacterConverter();
		KeyHolder keyHolder = new GeneratedKeyHolder();

		final String sql =BULLETINBOARD_SQLQueryConstants.IC_BULLETIN_CREATE_MESSAGE;
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql,
						new String[] { "BULLETIN_ID" });
				ps.setString(1, (String) convert.replaceSpecialChars(insertdetails.get(BULLETIN_DESCRIPTION).toString()));
				ps.setString(2, (String) convert.replaceSpecialChars(insertdetails.get(BULLETIN_HEADER).toString()));
				ps.setString(3, (String) insertdetails.get(MASTER_TICKET_ID));
				ps.setString(4, "1");
				ps.setString(5, (String) insertdetails.get(CREATEDBY));
				ps.setObject(6, insertdetails.get(CREATEDDATE));
				return ps;
			}
		}, keyHolder);

		return "SUCESS"+keyHolder.getKey().toString();
	
	}

	public List<Map<String, Object>> getBulletinMessage() {
		return this.jdbcTemplate.queryForList("SELECT BULLETIN_ID,BULLETIN_DESCRIPTION,BULLETIN_HEADER FROM IC_IHD_BULLETIN_BOARD where IS_ACTIVE=1 order by BULLETIN_ID", new Object[] {});
	}

	public int removeBulletinBoard(String bulletinId) {
		final String sql =BULLETINBOARD_SQLQueryConstants.IC_BULLETIN_REMOVE_MESSAGE;
		return jdbcTemplate.update(sql,bulletinId);
	}
	 public int removeMasterTicketFromBulletinBoard(String master_ticketId){
		 final String sql =BULLETINBOARD_SQLQueryConstants.IC_MASTER_REMOVE_MESSAGE;
			return jdbcTemplate.update(sql,master_ticketId);
	 }
}
