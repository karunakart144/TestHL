package com.igate.iconnect.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igate.iconnect.BO.Reports;

public interface ReportsDAO {

	/**
	 * @param gernerate reports
	 *            - Used to generate the on demand report
	 * 
	 */
	public String generateTicketReport(Reports GenerateReportBean,
			HttpServletRequest request, HttpServletResponse response);

}
