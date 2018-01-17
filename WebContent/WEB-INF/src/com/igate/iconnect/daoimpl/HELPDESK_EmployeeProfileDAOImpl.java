package com.igate.iconnect.daoimpl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.igate.iconnect.constants.HELPDESK_EmployeeProfileSQLQueryConstants;

import com.igate.iconnect.dao.HELPDESK_EmployeeProfileDAO;


public class HELPDESK_EmployeeProfileDAOImpl implements HELPDESK_EmployeeProfileDAO {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//Added for Employee Profile-by 717170
	public List<Map<String, Object>> getEmployeeProfile(String EmpID) {
			

			return this.jdbcTemplate.queryForList(
					HELPDESK_EmployeeProfileSQLQueryConstants.IC_EMPLOYEE_PROFILE, EmpID);
			
		}
	
}
