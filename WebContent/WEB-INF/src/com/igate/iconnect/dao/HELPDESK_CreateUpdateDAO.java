/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igate.iconnect.BO.HELPDESK_Attachment;

public interface HELPDESK_CreateUpdateDAO {

	/**
	 * @param insertdetails
	 *            - This indicates all the ticket details for insertion
	 * @return String - success And this method will take care of insertion in
	 *         to table and updating attachments and uploading files in to FTP
	 * 
	 */
	public String insertHelpDeskTicket(HashMap<String, Object> insertdetails);

	/**
	 * @param insertdetails
	 *            - This indicates all the ticket details for insertion
	 * @return String - success
	 * @throws Exception 
	 * 
	 */
	public String createHelpDeskTicket(HashMap<String, Object> insertdetails)throws IOException, Exception;

	/**
	 * @param ArgsList
	 *            - This indicates problem attachment path if there
	 * @return int - 1 for success
	 * 
	 */
	public int updateHelpDeskTicketAttachment(ArrayList<Object> ArgsList);

	/**
	 * @param employeeID
	 *            - primary key of the table
	 * @return Map<String, Object> when employeeID passes to this method the
	 *         list of employee details will return
	 */
	public Map<String, Object> getEmployeeDetails(String empId);
	//Added to implement the tool to track EX employees quereis for HR function
	public Map<String, Object> getEmployeeDetails_Ex(String empId);
	/**
	 * @param employeeID
	 *            - primary key of the table
	 * @return Map<String, Object> when employeeID passes to this method the
	 *         list of employee details will return
	 */
	public List<Map<String, Object>> getProjectsForEmployee(String empId);

	/**
	 * @param attachmentbean
	 *            - attachement details
	 * @return void
	 */
	
	public void updateAttachmentTicketDetails(HELPDESK_Attachment attachmentbean,
			int validState, String fileName, String ticketid,
			String referenceid, String verifiedby, String verifiedby2);

	public void updateAttachmentDetails(int validState,
			HELPDESK_Attachment attachmentbean, String ticketid, String referenceid,
			String verifiedby);

	public void updateTicketStatus(int validState, String ticketid, String ECT);

	public int getWorkflowState(String workflowID);

	public Map<String, Object> getProjectDetails(String createdby,String projectID, String subProjectID);

	public void insertAttachmentDetails(HELPDESK_Attachment attachmentbean,
			int validState, String fileName, String approvalattachmetnpath,
			String ticketid, String referenceid, String verifiedby);

	public void insertSimpleAttachment(String ticketid, String referenceid,
			String approvalattachmetnpath, String fileName, String verifiedby);

	public void updateTicketApprovalDetails(String ticketid, String verifiedby);
	
	//Added for iconnect - iTrack integartion uninstallation request creation
	public Map<String, Object> getInActiveEmployeeDetails(String empId);

	public Map<String, Object> getMasterProjectDetails(String empProject);

	public List<Map<String, Object>> getCubiclecodeByLocID(String locID,String cubicleCode);
	
	public List<Map<String, Object>> getEmployeeMappedOrNOTOnLoad(String empId);
	
	public Map<String, Object> getCubicalCodeWithCCFormat(String cityCC,String locCC,String buildingCC,String floorCC,String wingCC);
	
	public void insertFloorEmpCCMapping(String cubicalCode,String empId,int locationdetID, String projectId) throws SQLException;

	public int checkValidCubicCode(String cubicalcode);

	public Map<String,Object> getAssigedToName(String assignedTo);

	public int getOrchOdcId(String empProject,int l_subcategoryId);
	
	public List<Map<String, Object>> getCatgPriority(String catId);

}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 13, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/