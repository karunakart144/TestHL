/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.igate.iconnect.constants.COMMON_ListPageSQLQueryConstants;
import com.igate.iconnect.constants.WORKSPACEMGMT_SQLQueryConstants;
import com.igate.iconnect.exception.COMMON_Exception;

public class COMMON_ListPageSearch {

	private List<Filter> filters = new ArrayList<Filter>();

	public void addFilter(String property, String value) {
		filters.add(new Filter(property, value));

	}

	public StringBuffer execute(String headerMenuName) {
		StringBuffer buffer = new StringBuffer();
		Map<String, String> queryMapping = new HashMap<String, String>();
		if (headerMenuName.equalsIgnoreCase("HelpDesk")) {
			queryMapping = COMMON_ListPageSQLQueryConstants.getHelpDeskQueryMapping();
		} else if (headerMenuName.equalsIgnoreCase("Tech-CR")) {
			queryMapping = COMMON_ListPageSQLQueryConstants.getTechCRQueryMapping();
		} else if (headerMenuName.equalsIgnoreCase("LockedRequest")) {
			queryMapping = COMMON_ListPageSQLQueryConstants.getLockedQueryMapping();
		}else if (headerMenuName.equalsIgnoreCase("Mail-Tracker") || headerMenuName.equalsIgnoreCase("NOC-Alerts")) {
			queryMapping = COMMON_ListPageSQLQueryConstants.getmailtrackerQueryMapping();
		}else if (headerMenuName.equalsIgnoreCase("UserAvailability")) {
			queryMapping = COMMON_ListPageSQLQueryConstants.getUserAvailabilityQueryMapping();
		}
		else if (headerMenuName.equalsIgnoreCase("spaceauditlog")) {
			queryMapping = WORKSPACEMGMT_SQLQueryConstants.getspaceauditquerymapping();
		}else if (headerMenuName.equalsIgnoreCase("UserAvailabilityLogInOut")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getUserAvailabilityLoginOutQueryMapping();
		}else if(headerMenuName.equalsIgnoreCase("EngineerScoreDetail")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getEngineerScoreQueryMapping();
		}else if(headerMenuName.equalsIgnoreCase("EngineerScoreDetailList")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getEngineerScoreDetailQueryMapping();
		}else if(headerMenuName.equalsIgnoreCase("AdminConsoleGrpLocation")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getGrpDetailQueryMapping();
		}else if(headerMenuName.equalsIgnoreCase("AdminConsoleGrpLocationDept")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getGrpDetailQueryMapping();
		}else if(headerMenuName.equalsIgnoreCase("AdminConsoleCategory")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getCategoryMapping();
		}else if(headerMenuName.equalsIgnoreCase("AdminConsoleLocation")){
			queryMapping=COMMON_ListPageSQLQueryConstants.getLocationMapping();
		}
		
		for (Filter filter : filters) {
	
			if(filter.getProperty().equals("LOCKED_DATE") || filter.getProperty().equals("CREATED_DATE")){				
				String dateVal=null;				
				String toDateFormatted = null;
				SimpleDateFormat dateFormatGmt1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("GMT:00"));	
				DateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));				
					dateVal=filter.getValue().toString().trim();					
					try{
						toDateFormatted=dateFormatGmt1.format(dateFormatGmt.parse(dateVal+" 00:00:00"));
						}catch(ParseException e){
							throw new COMMON_Exception("Parse Exception"+e.getMessage());
					}	
				
				buildDateQuery(buffer, filter.getProperty(), toDateFormatted,
						queryMapping);
			}else{
			buildQuery(buffer, filter.getProperty(), filter.getValue(),
					queryMapping);
			}			
		}
		return buffer;
	}
	private void buildDateQuery(StringBuffer buffer, String property, Object value,
			Map<String, String> queryMapping) {
		String val=null;		
		String queryValue = queryMapping.get(property);
		StringTokenizer token = new StringTokenizer(queryValue, ",");
		buffer.append(" AND ");		
		val=value.toString();
		while (token.hasMoreElements()) {					
			String element = (String) token.nextElement();
					buffer.append(element);
					buffer.append(" >='");
					buffer.append(val);
					buffer.append("' AND ");
					buffer.append(element);
					buffer.append(" <='");
					buffer.append(val.substring(0,10));
					buffer.append(" 23:59:59");
					buffer.append("'");			
			}
		
	}

	private void buildQuery(StringBuffer buffer, String property, Object value,
			Map<String, String> queryMapping) {
		String val=null;
		HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter();
		if(property.equalsIgnoreCase("SUBJECT")||property.equalsIgnoreCase("DESCRIPTION"))
		{
			val=converter.replaceSpecialChars(value.toString());
		}else
		{
			val=value.toString();
		}
		String queryValue = queryMapping.get(property);
		StringTokenizer token = new StringTokenizer(queryValue, ",");
		buffer.append(" AND ");
		boolean firstTime = false;
		while (token.hasMoreElements()) {
			if (!firstTime) {
				firstTime = true;
				buffer.append("( LOWER(");

			} else
				buffer.append(" OR LOWER(");
			String element = (String) token.nextElement();
			buffer.append(element);
			buffer.append(") like '%");
			buffer.append(val);
			buffer.append("%'");
		}
		if (firstTime)
			buffer.append(" )");
		else {
			buffer.append(" LOWER(");
			buffer.append(property);
			buffer.append(") like '%");
			buffer.append(val);
			buffer.append("%'");
		}
	}
	private static class Filter {
		private final String property;
		private final Object value;

		public Filter(String property, Object value) {
			this.property = property;
			this.value = value;
		}

		public String getProperty() {
			return property;
		}

		public Object getValue() {
			return value;
		}
	}
	
	
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:701901
 Changes Made on:Jul 20, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/
