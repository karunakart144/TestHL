/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */


package com.igate.iconnect.dao;

import java.util.List;
import java.util.Map;

public interface HELPDESK_SlaDAO {
	/**
	 * getAuditLog() method will return a list which contains the history of a particular ticket.
	 * Arguments passed : Ticket ID and MENU ID 
	 * 
	 * @param auditLogList
	 *            List which contains the history of the ticket.
	 */
	public List<Map<String, Object>> getAuditLog(String ticketID,long menuID);
	/**
	 * geTicketDetails() method will return a list which contains ticket details like  ECT,CREATION_DATE .
	 * Arguments passed : Ticket ID
	 * @param ticketDetailsList
	 *            List which contains ticket details like  ECT,CREATION_DATE .
	 */
	public List<Map<String, Object>> geTicketDetails(long ticketID);
	/**
	 * getSLAExclusionDates() method will return a list which contains location wise  holiday for a particular SLAID .
	 * Arguments passed : SLA ID,LOCATION ID
	 * @param slaExclusionDateList
	 *            List which contains  location wise  holiday for a particular SLAID .
	 */
	public List<Map<String, Object>> getSLAExclusionDates(long slaID,long locationID);
	/**
	 * getSLAOperatingTime() method will return a list which contains operating timings for a particular SLAID .
	 * Arguments passed : SLA ID
	 * @param slaOperatingTimeList
	 *            List which contains  operating timings for a particular SLAID .
	 */
	public List<Map<String, Object>> getSLAOperatingTime(long slaID);
	/**
	 * getAuditLastResponseTime() method will return a list which contains the response time of the last entry in the auditlog for a particular ticket.
	 * Arguments passed : Ticket ID
	 * @param slaOperatingTimeList
	 *            List which contains the response time of the last entry in the auditlog for a particular ticket.
	 */
	public List<Map<String, Object>> getAuditLastResponseTime(String ticketID);
	//ECT
	
	public  List<Map<String, Object>> getSLAOpertingTimeInfo(long sla_id,String org, String day);
	public  String getSLAID(long categoryId,int priorityId);
	public long getServiceOPRID(long locationID,long locationDetailId,long functionID);
	public long getGroupOPRID(long groupID,long functionID,long locationID);
	public  List<Map<String, Object>> getTicketInfo(long ticketId);

}


 

/*-----------------------------------------------------------------------------

Log: 

Start-----Version 1.0-----

Changes Made:New File Created

Changes Made By:702166

Changes Made on:Jun 10, 2011

End-------Version 1.0-------

            

-----------------------------------------------------------------------------*/
