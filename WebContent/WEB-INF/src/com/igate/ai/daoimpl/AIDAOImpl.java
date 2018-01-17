package com.igate.ai.daoimpl;


import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.igate.ai.constants.AICONSTANTS;
import com.igate.ai.dao.AIDAOManager;

public class AIDAOImpl implements AIDAOManager {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(@Qualifier("sqlDataSource") DataSource dataSource) {
		this.jdbcTemplate= new JdbcTemplate(dataSource);
	}

	public String getUserComment(String srchId) {
		String usrCommentQry = "select USR_COM_ID,SRCH_ID,COMMENTS,CREATED_BY,Created_date from IC_AI_SRCH_USER_COMMENTS WHERE SRCH_ID =  '"+srchId+"'";
		String jsonUsrCmnt = "{\"srchId\" : \"1\",\"userComment\" : [";
		List<Map<String,Object>> userCommentList =  this.jdbcTemplate.queryForList(usrCommentQry);
		if(userCommentList.size() == 0)
		{
				jsonUsrCmnt += "]}";
		}
		else
		{
			for (Map<String, Object> usrComment : userCommentList) {
				jsonUsrCmnt += "{\"comment\" :\""+usrComment.get("COMMENTS")+"\",\"userName\" : \""+usrComment.get("CREATED_BY")+"\"},";
			}
			jsonUsrCmnt =jsonUsrCmnt.substring(0,(jsonUsrCmnt.length() -1))+"]}";
		}
			return jsonUsrCmnt;
	}
	
	public String getUserVote(String srchId)
	{
		String jsonUserVote = "{\"LikeCount\":\""+getUserLikeVote(srchId)+"\",\"DisLikeCount\":\""+getUserDisLikeVote(srchId)+"\"}";

		return jsonUserVote;
	}
	public int getUserLikeVote(String srchId)
	{
		String voteQuery = "select COUNT(VOTE_LIKE) as 'LikeCount' from IC_AI_SRCH_USER_VOTES  where SRCH_ID = '"
							+srchId+"' and VOTE_LIKE = 1 group by SRCH_ID ";
		int likeCount = 0;
		try
		{
			likeCount = this.jdbcTemplate.queryForInt(voteQuery);
		}catch(EmptyResultDataAccessException e)
		{
			likeCount = 0;
		}
		return likeCount;
	}
	
	
	public int getUserDisLikeVote(String srchId)
	{
		String voteQuery = "select COUNT(VOTE_DISLIKE) as 'DisLikeCount' from IC_AI_SRCH_USER_VOTES where SRCH_ID = '"
			+srchId+"' and VOTE_DISLIKE = 1 group by SRCH_ID  ";
		int disLikeCount = 0;
		try
		{
			disLikeCount = this.jdbcTemplate.queryForInt(voteQuery);
		}catch(EmptyResultDataAccessException e)
		{
			disLikeCount = 0;
		}
		return disLikeCount;
	}
	

	public int saveUserComments(String srchId, String comments,String createdBy)
	{
		String qry = "insert into IC_AI_SRCH_USER_COMMENTS (SRCH_ID,COMMENTS ,CREATED_BY,Created_date) values(?,?,?,GETDATE());";
		return this.jdbcTemplate.update(qry, new Object[] {srchId , comments ,createdBy});
		 
	}
	public void saveVote(String srchId, String vote,String createdBy)
	{
		String qry = "insert into IC_AI_SRCH_USER_VOTES (SRCH_ID,VOTE_LIKE , VOTE_DISLIKE,CREATED_BY,Created_date) values(?,?,?,?,GETDATE())";
		Object param = null;
		if(vote.equals("1"))
		{
			this.jdbcTemplate.update(
					qry,new Object[] { srchId, 1,0,createdBy });
		}else if(vote.equals("2")) 
		{
			this.jdbcTemplate.update(
					qry,
					new Object[] { srchId, 0,1,createdBy });
		}
		
		 	
	}
   
	public void saveUserTag(String tag, String tagDes,String createdBy)
	{
		String qry = "insert into IC_AI_SRCH_USER_TAGS(USR_TAG_ID,USR_TAG,USR_TAG_SUMMARY,USR_TAG_LINK,USR_TAG__LINK_NAME,CREATED_BY)values('TG_'+ CAST(IDENT_CURRENT('IC_AI_SRCH_USER_TAGS') as varchar),?,?,?,?,?)";
		this.jdbcTemplate.update(
				qry,new Object[] { tag,tagDes ,tag,tag,createdBy });
	}
	
	/*
	 * Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To save the number of viewers for an article
	 */
	public void saveUsageCount(String documentId, String createdBy) {
		String query=AICONSTANTS.IC_AI_UPDATE_USAGE_COUNT;
		this.jdbcTemplate.update(query,new Object[] {documentId,createdBy,documentId,documentId,createdBy});
	}
	/*
	 *  Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To get the number of viewers for an article on load of the page
	 */
	public int getUsageCount(String documentId) {
		int count=0;
		String query=AICONSTANTS.IC_AI_GET_USAGE_COUNT;
		try{
		count=this.jdbcTemplate.queryForInt(query, documentId);
		}catch(EmptyResultDataAccessException erdae){
			count=0;
		}
	return count;
	}
	/*
	 *  Added by: Mohit(816452) ISRT Request No:307
	 * Comments:To get the top ten articles viewed.
	 */
	public List<Map<String,Object>> getTopTenViewedDocuments() {
		
		String query=AICONSTANTS.IC_AI_GET_TOP_TEN_VIEWED_DOCUMENTS;
		List<Map<String,Object>> documentList=this.jdbcTemplate.queryForList(query);
		return documentList;
	}
	
	public String getTicketDetails(String srch_id)
	{
		  String json = "";

			String query = "select cm.NAME as 'Category',lm.AREA as 'place',lm.CITY as 'city',SUBJECT,REPLACE(DESCRIPTION,'brlinebreakbreak',' ') as 'DESCRIPTION',REPLACE(RESOLUTION_COMMENTS,'brlinebreakbreak',' ') as 'RESOLUTION_COMMENTS'" +
			" from IC_IHD_TICKET_DETAILS itd  ,IC_IHD_CATEGORY_MASTER cm, IC_LOCATION_MASTER lm " +
			" where itd.CATEGORY_ID = cm.CATEGORY_ID and itd.LOCATION_ID = lm.LOCATION_ID " +
			" and TICKET_ID = '"+srch_id+"'";
			//conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.120.150:1433/iconnect_test", "iconnecttest", "test#123");
	  
			List<Map<String,Object>> ticketList = this.jdbcTemplate.queryForList(query);
			for (Map<String, Object> obj : ticketList) {
				json="{\"Category\" : \""+obj.get("Category").toString()+"\" , \"place\" :\""+obj.get("place").toString()+"\" , \"city\" : \""+obj.get("city").toString()+"\" , \"subject\" : \""+obj.get("SUBJECT").toString()+"\" , \"Description\" :\""+convertSpecialChars(obj.get("DESCRIPTION").toString())+"\" , \"Resolution\" : \""+convertSpecialChars(obj.get("RESOLUTION_COMMENTS").toString())+"\"}";
			}
			
		return json;
	}
	
	
	public String getTagDetails(String srch_id)	{
		  String json = "";
			String query = "select CREATED_BY, USR_TAG_SUMMARY from IC_AI_SRCH_USER_TAGS where USR_TAG_ID = '"+srch_id+"'";
			//conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.120.150:1433/iconnect_test", "iconnecttest", "test#123");
	  
			List<Map<String,Object>> tagList = this.jdbcTemplate.queryForList(query);
			for (Map<String, Object> obj : tagList) {
				json="{\"UserName\":\""+obj.get("CREATED_BY").toString()+"\",\"Description\" :\""+obj.get("USR_TAG_SUMMARY").toString().replace(System.getProperty("line.separator"), "")+"\"}";
			}
			
		return json;
	}

	public void insertSubject(String srchTxt,String createdBy)
	{
		String query= "insert into IC_AI_SRCH_USER_SUBJECT (SRCH_USER_SUBJECT,CREATED_BY,CREATED_DATE)values(?,?,GETDATE())";
	this.jdbcTemplate.update(
				query,new Object[] { srchTxt,createdBy});
		
	}
	
	public  String convertSpecialChars(String descriptionVal)
	{		
		if(descriptionVal!=null){
		if(descriptionVal.length()!=0)
		{		
			
			if(descriptionVal.contains("&#60;")){
				descriptionVal=descriptionVal.replace("&#60;", "<");
			}
			if(descriptionVal.contains("&#62;")){
				descriptionVal=descriptionVal.replace("&#62;", ">");
			}
			if(descriptionVal.contains("&#61;")){
				descriptionVal=descriptionVal.replace("&#61;", "=");
			}			
			if(descriptionVal.contains("&#34;")){
				descriptionVal=descriptionVal.replace("&#34;", "");
			}						
			if(descriptionVal.contains("&#92;")){
				descriptionVal=descriptionVal.replace("&#92;", "");
			}
			if(descriptionVal.contains("&#47;")){
				descriptionVal=descriptionVal.replace("&#47;", "\\/");
			}
			if(descriptionVal.contains("&#126;")){
				descriptionVal=descriptionVal.replace("&#126;", "~");
			}
			if(descriptionVal.contains("&#039;") || descriptionVal.contains("\'")){
				descriptionVal=descriptionVal.replace("&#039;", "");// Changed it as &#039; as part of L2 : 4200
			}
			// Added Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("\b")){
				descriptionVal=descriptionVal.replace("\b", "\\b");
			}
			if(descriptionVal.contains("\t")){
				descriptionVal=descriptionVal.replace("\t", "\\t");
			}
			if(descriptionVal.contains("\f")){
				descriptionVal=descriptionVal.replace("\f", "\\f");
			}
			if(descriptionVal.contains("\r\n")){
				descriptionVal=descriptionVal.replace("\r\n", "\\n");
			}
			
			if(descriptionVal.contains("+")){
				descriptionVal=descriptionVal.replace("+", "");
			}
			if(descriptionVal.contains("&#037;")){
				descriptionVal=descriptionVal.replace("&#037;", "%");
			}
			if(descriptionVal.contains("&#064;")){
				descriptionVal=descriptionVal.replace("&#064;", "@");
			}
			if(descriptionVal.contains("&#033;")){
				descriptionVal=descriptionVal.replace("&#033;", "!");
			}
			if(descriptionVal.contains("&#040;")){
				descriptionVal=descriptionVal.replace("&#040;", "(");				
			}
			if(descriptionVal.contains("&#041;")){
				descriptionVal=descriptionVal.replace("&#041;", ")");				
			}			
			if(descriptionVal.contains("&#091;")){
				descriptionVal=descriptionVal.replace("&#091;", "[");				
			}
			if(descriptionVal.contains("&#093;")){
				descriptionVal=descriptionVal.replace("&#093;", "]");				
			}
			if(descriptionVal.contains("&#094;")){
				descriptionVal=descriptionVal.replace("&#094;", "^");				
			}
			if(descriptionVal.contains("&#095;")){
				descriptionVal=descriptionVal.replace("&#095;", "_");				
			}
			if(descriptionVal.contains("&#096;")){
				descriptionVal=descriptionVal.replace("&#096;", "`");				
			}
			if(descriptionVal.contains("&#123;")){
				descriptionVal=descriptionVal.replace("&#123;", "{");				
			}
			if(descriptionVal.contains("&#124;")){
				descriptionVal=descriptionVal.replace("&#124;", "|");				
			}
			if(descriptionVal.contains("&#125;")){
				descriptionVal=descriptionVal.replace("&#125;", "}");				
			}
			if(descriptionVal.contains("&#036;")){
				descriptionVal=descriptionVal.replace("&#036;", "$");				
			}
			// End of Special Characters by Nazeeb as part of L2 : 4200
			if(descriptionVal.contains("&#38;")){
				descriptionVal=descriptionVal.replace("&#38;", "&");
			}	
		}
		}
		
		return descriptionVal;
	}
	
	public String insertFileDetails(String fileNameForStorage,String requestID, byte[] attachdata,
			String docLink, String attachtype,String loginID,List<Map<String, Object>> smartSearchAttchmentIds){
		int attachmentCount=0;
		
	int count=this.jdbcTemplate.queryForInt(AICONSTANTS.SELECT_INTO_TICKET_ATTACHMENT_DETAILS,requestID,docLink,"1");
		if(count==0){
			for(int i=0;i<smartSearchAttchmentIds.size();i++){
		int flag = this.jdbcTemplate.queryForInt(AICONSTANTS.CHECK_EXISTS_IN_TICKET_ATTACHMENT_DETAILS,requestID,smartSearchAttchmentIds.get(i).get("APPROVER_ID"));
		if(flag==0){		
		this.jdbcTemplate.update(AICONSTANTS.INSERT_INTO_TICKET_ATTACHMENT_DETAILS,requestID,smartSearchAttchmentIds.get(i).get("APPROVER_ID"),"0",fileNameForStorage,docLink,"0",loginID,requestID,smartSearchAttchmentIds.get(i).get("APPROVER_ID"),fileNameForStorage,docLink,loginID,"1","1","0");
		return "SUCESS";
		}
		attachmentCount++;
		} if(attachmentCount==5){
			return "LIMIT EXCEEDED";
		}
		}else{
			return "EXISTS";
		}
		return "";
	}
	public String removeAttachmentfromTicket(String requestID, String docLink,
			String loginID){
		int count=this.jdbcTemplate.queryForInt(AICONSTANTS.SELECT_INTO_TICKET_ATTACHMENT_DETAILS,requestID,docLink,"1");
		
		if(count>0){
		this.jdbcTemplate.update(AICONSTANTS.UPDATE_INTO_TICKET_ATTACHMENT_DETAILS,"0",loginID,requestID,docLink,"1");
				return "SUCESS";
		}else{
			return "EXISTS";
		}
	}

	
}
