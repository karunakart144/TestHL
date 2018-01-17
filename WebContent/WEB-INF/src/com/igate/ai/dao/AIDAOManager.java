package com.igate.ai.dao;

import java.util.List;
import java.util.Map;

public interface AIDAOManager {
	public String getUserComment(String srchId);
	public String getUserVote(String srchId);
	public int saveUserComments(String srchId, String comments,String createdBy);
	public void saveVote(String srchId, String vote,String createdBy);
	public void saveUserTag(String tag, String tagDes,String createdBy);
	public String getTicketDetails(String srch_id);
	public String getTagDetails(String srch_id);
	public void insertSubject(String srchTxt,String createdBy);
	public String insertFileDetails(String fileNameForStorage,String requestID, byte[] attachdata,
			String docLink, String attachtype,String loginID, List<Map<String, Object>> smartSearchAttchmentIds);
	public String removeAttachmentfromTicket(String requestID, String docLink,
			String loginID);
				public void saveUsageCount(String documentId,String createdBy);
	public List<Map<String, Object>> getTopTenViewedDocuments();
	public int getUsageCount(String documentId);

}
