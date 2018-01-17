/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.igate.iconnect.BO.HELPDESK_Approval;
import com.igate.iconnect.BO.HELPDESK_AssetInformation;
import com.igate.iconnect.BO.HELPDESK_AssetTab;
import com.igate.iconnect.BO.HELPDESK_RequestorInformation;
import com.igate.iconnect.BO.HelpDesk;

public interface HELPDESK_EditDAO {

	/**
	 * @param daodetails
	 *            - ticket details
	 * @return success message
	 */

	public String updateHelpDeskRequest(Map<String, Object> daodetails);

	/**
	 * @param jsonobj
	 *            - locked ticket details
	 * @return locked details
	 */
	public Map<String, Object> lockIHDTicket(JSONObject jsonobj)
			throws JSONException;

	/**
	 * @param menuid
	 *            - whether helpdesk or techcr
	 * @param ticketid
	 *            - ticketid
	 * @return locked ticket details
	 */
	public Map<String, Object> getLockedTicketDetails(String menuid,
			String ticketid);

	/**
	 * @return approvers details for the ticket
	 */

	public List<HELPDESK_Approval> getApprRejectListforTicket(String ticketID);

	/**
	 * @return requestor details of the ticket with dept,odc,location ...etc
	 */
	public HELPDESK_RequestorInformation getRequestorDetails(String projectid,
			String empid);

	/**
	 * @return validastate of the next approval
	 */
	public int getApprovalValidState(JSONObject jsonobj) throws JSONException;

	/**
	 * 
	 * Will update the next status of the ticket
	 * 
	 * @param jsonobj
	 * @return 1 for success other for failure
	 * @throws JSONException
	 */
	public int updateNextStatus(long locationID, long locationdetID,
			long functionid, int priorityID, String createddate,
			long categoryName, String org, String status,
			int approvalValidStateId, String workflowid, String ticketId,
			Map<String, Object> ticketDetails, JSONObject jsonobj)
			throws JSONException;

	/**
	 * Insert the approver details when ticket gets approved
	 * 
	 * @return 1 for success other for failure
	 */

	public int insertTicketApproverDetails(JSONObject json)
			throws JSONException;

	/**
	 * to get the ticket current version number
	 * 
	 * @return ticket current version number
	 */

	public String getIHDTicketVersionNo(String ticketID);

	/**
	 * Get the helpdesk ticket details
	 * 
	 * @return the ticket details
	 */

	public List<HelpDesk> getReqList(String dynamicQuery,
			HttpServletRequest request);

	/**
	 * unlock the locked tickets
	 * 
	 * @return 1 for success other for failure
	 */
	public int unlockTickets(ArrayList<Object> ArgsList);

	/**
	 * get the valid state for the passed subcategory and approver
	 * 
	 * @return 1 for success other for failure
	 */
	public int getValidState(String subcategory_id, String approver_id);

	/**
	 * @return all the logged in executive details
	 */
	public List<Map<String, Object>> getLoggedInGroupMembers(String groupid);

	/**
	 * update the additional info for the ticket
	 * 
	 * @return 1 for success other for failure
	 */
	public int updateAdditionalInfo(JSONObject jsonobj) throws JSONException;

	/**
	 * To get the additional info for the ticket
	 * 
	 * @return 1 for success other for failure
	 */

	public String getAdditionalInfoforTicket(String needAdditonalInfo);

	/**
	 * To get the asset details for the employee
	 * 
	 * @return asset details of the employee
	 */
	public List<Map<String, Object>> getAssetDetail(String empId);

	/**
	 * To get the asset component details for the asset
	 * 
	 * @return asset component details for the asset
	 */

	public List<Map<String, Object>> getAssetCompDetail(String assetDetId);

	/**
	 * insert the asset asset details for the asset
	 * 
	 * @return success
	 */

	public String insertAssetDetails(final HELPDESK_AssetTab asset,
			final String loginID) throws JSONException;

	/**
	 * 
	 * @return json for asset auditlog
	 */
	public void auditlogForAsset(final String ticketID,
			final String workflowSt, final String loginID,
			HashMap<String, Object> auditlogdetails);

	public int checkExistAsset(String childAssetId);

	public HELPDESK_AssetInformation getAssetDetailView(String parentSlNo);

	public HELPDESK_AssetInformation getAssetCompDetailView(String childSlNo);

	public String insertAssetParentDetails(final HELPDESK_AssetTab asset,
			final String loginID) throws JSONException;

	public int checkExistParentAsset(String parentAssetId);

	/**
	 * 
	 * @return the valid employee details
	 */

	public Map<String, Object> getEmployeeDetails(String employeeid);

	/**
	 * To update the assigned group for the ticket
	 * 
	 * @return 1 for success other for failure
	 */

	public int updateAssignedGroup(String ECT, String stateId, int groupID,
			Map<String, Object> ticketDetails);

	/**
	 * To get the function specific details Ex : Department for HR Ticket ODC
	 * for Admin
	 * 
	 * @return 1 for success other for failure
	 */

	public Map<String, Object> getFunctionSpecificFieldsForTicket(
			String ticketid, String functionid, String fieldid);

	public String getDeptIdFromFuncSpecList(String string, long functionid,
			String fieldId);
	public String getMasterIdForReferenceId(String referenceId);

	public int checkApprovalExists(String ticketId, int approverId);

	public int getNextApproverId(JSONObject jsonobj, int aPPROVERID)
			throws JSONException;
	
	public void statusUpdationExceptionalCase(String ticketId,String status);
	
	public void statusUpdationUnexceptionalCase(String ticketId,String status);
	
	public int saveFeedbackComments(Map<String, Object> ticketDetails);

	public int updateDateForTicket(String ticketId, String revokeDate);

	public boolean checkApprovalExists(String firstApprover, String ticketId);

	public int updateFCSCheck(String ticketId);
	
	/*Auto Assignment*/
	
	public int getEngrAvailabilityStatus(String empID, String opStartTime,
			String opEndTime);

	public List<Map<String, Object>> getTicketListForEngineer(String empID);
	/**
	 * getMaxLoginTimeForEngineer() returns the maximum login time of an
	 * Engineer for IN status.
	 * 
	 * @param empID
	 * @return
	 */
	public String getMaxLoginTimeForEngineer(String empID);
	/**
	 * getPrevLoginTimeForEngineer() returns the second maximum login time of an
	 * Engineer .
	 * 
	 * @param empID
	 * @return
	 */
	public String getPrevLoginTimeForEngineer(String empID);
	/**
	 * getPrevMaxINTimeForEngineer() method returns the prev maximum IN Time for
	 * Engineer .
	 * 
	 * @param empID
	 * @return
	 */	
	public String getPrevMaxINTimeForEngineer(String empID);	
	
	public void insertEngineerScore(String empID, double HIGH_SCORE,
			double LOW_SCORE, double MEDIUM_SCORE, double TOTAL_SCORE,
			String data_change);
	
	public List<Map<String, Object>> getLoggedInGroupMembersScore(String groupid);
	
	public Map<String, Object> getMinimumScore(String  groupid);
	
	public Map<String, Object> getScoreDetailForEngineer(String empID);
	
	public String calculateScoreOnTicketUpdation(int priorityChangeFlag,
			int statusChangeFlag, int assignedToChangeFlag,
			String currentPriority, String newPriority, String currentEngineer,
			String newEngineer, String currentStatus, String newStatus,
			String modifiedBy, String ticketID);
	
	public String getAssignedToBasedOnTicketAssignedTime(
			StringBuffer engineerWithLowestScoreStr);
	
	public List<Map<String, Object>> getMaximumTicketIDList(
			List<Map<String, Object>> lowestPrtyScoreList);
	
	public List<Map<String, Object>> gettcktList();
	
	/*END : Auto Assignment*/
		
	/*Emergency L1 */
	public List<Map<String, Object>> getFunctionSpecificFieldsForTicketForEmergency(
			String ticketid, String functionid) ;
	
	public void insertorUpdateFunctionSpecificDetails(
			Map<String, Object> insertdetails, String ticketid);
	
	public int getAttachment_EmergencyApp(String ticketId);
	public int getAttachment_EmergencyScript(String ticketId);
	public int getAttachment_EmergencyTestRep(String ticketId);

	public List<Map<String, Object>> getOrchestrationGUIDMapping(
			String ticketId, String catagoryID,int l_orchOdcId);

	public void closeTicketByOrchestration(String ticketId, String comment,
			Map<String, Object> map);

	public List<Map<String, Object>> getTicketInfo(String ticketId);

	public String getSLAStatusProcedureCall(String ticketId);
	
	public Map<String, Object> getSLATime(String ticketID);
	
	public void updateOrchTransitionValues(JSONArray orchJson, String string,
			Map<String, Object> daodetails) throws JSONException;

	public String getRequestedByTicketId(String ticketID);

	public String getAutomationStatus(String catagoryId);

	public String getSubCatagoryIdBasedOnTicket(String ticketId);

	public boolean logAutomatedDataForOrchestration(String ticketId,
			String comment, String status);

	public boolean getAutomationHelpDeskStatus(String ticketId);

	public List<Map<String, Object>> getAutomationIternationCount(String ticketId);

	public boolean getLocationBasedTicketForAutoMation(String loc);
	
	public boolean getValidTicketIdInAutomation(String ticketId);
	
	
	public void updatePrivateNoteInTicketDetails(String ticketId, String comment);

	public String getOperatinalTimeFlag(String groupId, String functionid);

	public String updateMultipleTickets(String jsonString, String loginID);

	public Map<String, Object> getAllProjectDetails(String id);


	public String getManagerIDFromTicketDetails(String ticketId);
}

