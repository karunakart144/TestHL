/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.igate.iconnect.BO.ADMIN_ModifyLocation;
import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Group;
import com.igate.iconnect.BO.HELPDESK_GroupMembersAvailability;
import com.igate.iconnect.BO.HELPDESK_Lock;
import com.igate.iconnect.BO.HELPDESK_ScoreDetail;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MailTracker;
import com.igate.iconnect.BO.TechCR;
import com.igate.iconnect.util.HELPDESK_ScoreCalculator;

public interface COMMON_ListPageDAO {
	/**
	 * getReqList() method will return the list of records which has to be
	 * displayed in the list page based on menuId.
	 * 
	 * @param requestList
	 *            List which contains the list of records which has to be
	 *            displayed in the list page based on menuId
	 */
	public List<Map<String, Object>> getReqList(String menuName);

	public int saveAssign(String member_Id, long ticket_Id, String group_id,
			String loginID);

	public List<Map<String, Object>> getGroupWorkLoadList(String groupName);

	public List<Map<String, Object>> getGroupMamberWorkLoadList(String memberId);

	public COMMON_Pagination<HelpDesk> getHDTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend, String orderBy, String menuName);
	public COMMON_Pagination<HelpDesk> getHDTicketColourCodeList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend, String orderBy);
	
	public COMMON_Pagination<HELPDESK_Group> getGrpList_AC(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend,List<Map<String, Object>> grpList,String functionId);
	
	public COMMON_Pagination<HELPDESK_Category> getCategoryList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend,final String function_id,final String category_name,final String status_val);
	
	public COMMON_Pagination<ADMIN_ModifyLocation> getLocationDetails(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend,final String location_id,final String status_val);
	
	
	public COMMON_Pagination<HELPDESK_Group> getGrpListDept_AC(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend,List<Map<String, Object>> grpList);
	
	public COMMON_Pagination<HelpDesk> getMasterTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend, String orderBy);

	public COMMON_Pagination<TechCR> getTechCRTicketList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend);

	public COMMON_Pagination<HELPDESK_Lock> getLockedRequestList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend);
	
	public COMMON_Pagination<MailTracker> getMailLockedRequestList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query, int userTimeZoneToSend);
	
	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getUseAvailabilityRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend);

	public COMMON_Pagination<MailTracker> getMailTrackerList(final int startCount,
			final int pageNo, final int pageSize, String filterQuery,
			String query);

	public List<Map<String, Object>> getUserSelectionMenuListPage(
			String menuID, String employeeID, String roleID);

	public String saveUserSelectionMenuList(List<Map<String, Object>> data);

	public COMMON_Pagination<HelpDesk> getApprovalTicketsRequestList(
			final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend, String orderBy);
	
	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getLoggedUserList(int startCount, int pageNo,
			int maxRows, String query, String empId,int
			userTimeZoneToSend,String fromDate,String toDate) throws ParseException;

	public int getTotalNumberOfRows(String employeeId, String fromDate,
			String toDate, String string) throws ParseException ;
			
	/*Auto Assignment*/		
	public COMMON_Pagination<HELPDESK_ScoreDetail>	getEngineerScoreList(final int startCount, final int pageNo, final int pageSize,
			String filterQuery, String query, int userTimeZoneToSend);
	
	public List<Map<String,Object>> getEngineerAuditLog(String employeeID,String fromDate,String toDate) throws ParseException;
	
	
	public COMMON_Pagination<HELPDESK_ScoreDetail> getEngineerAuditLogList(int startCount, int pageNo,
			int maxRows, String query, String empId,int
			userTimeZoneToSend,String fromDate,String toDate) throws ParseException;
	
	public int getTotalNumberOfRowsScoreDetail(String employeeId, String fromDate,
			String toDate, String filterQuery) throws ParseException ;
	/*END :Auto Assignment*/			

}
