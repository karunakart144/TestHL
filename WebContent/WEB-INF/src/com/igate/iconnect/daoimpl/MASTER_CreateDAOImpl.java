package com.igate.iconnect.daoimpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.multipart.MultipartFile;

import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MASTER_Create;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.HELPDESK_SQLQueryConstants;
import com.igate.iconnect.constants.MASTER_SQLQueryConstants;
import com.igate.iconnect.dao.BULLETINBOARD_CreateUpdateDAO;
import com.igate.iconnect.dao.MASTER_CreateDAO;

import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ExecuteProcedure;
import com.igate.iconnect.util.HELPDESK_FileUpload;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.MapToStringUtil;


public class MASTER_CreateDAOImpl implements MASTER_CreateDAO{
	public JdbcTemplate jdbcTemplate;
	COMMON_ExecuteProcedure DAOUtilObj;
	private static Logger log = Logger
			.getLogger(MASTER_CreateDAOImpl.class);
	private static final String BULLETINBOARD_DAO_IMPL = "bulletinBoardDAOImpl";//Added by sali
	private static final String AUDITLOG_DETAILS = "auditlogdetails";
	private static final String PROBLEM_FILE_DATA = "problemfiledata";
	private static final String PROBLEM_FILE = "problemfile";
	private static final String MASTER_TICKET_ID = "Ticket#";
	private static final String WORKFLOW_NAME ="Status";
	private static final String WORKFLOW_STATE_ID ="STATE_ID";
	private static final String WORKFLOW_STATE="WORKFLOW_STATE";
	private static final String CREATE_BY="CREATED_BY";
	private static final String USER_NAME="USER_NAME";
	private static final String CREATE_DATE="CREATED_DATE";	
	private static final String SUBJECT="Subject";
	private static final String DESCRIPTION="Description";
	private static final String MAIL_ID="CC To";
	private static final String ASSIGNED_GROUP="GroupID";
	private static final String ASSIGNED_GROUP_NAME="Assigned Group";
	private static final String ASSIGNED_TO="Assigned To";
	private static final String CREATED_DATE="Creation Date";
	private static final String ECT="ECT";
	private static final String LOCATION_ID="LocationID";	
	private static final String CREATED_BY="Created By";
	private static final String CONTACT_NO="Contact Number(Primary)";
	private static final String ASSIGNED_TO_NAME="Assigned To Name";	
	private static final String FUNCTION_ID="FunctionID";
	private static final String FUNCTION_NAME="Function Name";
	private static final String CATEGORY_ID="CategoryID";
	private static final String CATEGORY_NAME="Category Name";
	private static final String SUB_CATEGORY_ID="SubCategoryID";
	private static final String SUB_CATEGORY_NAME="SubCategory Name";	
	private static final String CREATOR_NAME="Creator Name";
	private static final String CLOSED_DATE="CLOSED_DATE";
	private static final String VERSION_NO="VERSION_NO";	
	private static final String REFERENCE_ID = "reference_id";
    private static final String MENU_ID = "menu_id";
    private static final String CATEGORY_IDs = "category_id";
    private static final String PRIORITY_ID = "priority_id";
    private static final String ASSIGNED_GROUPs = "assigned_group";
    private static final String CURRENT_STATE = "current_state";
    private static final String ACTION = "action";
    private static final String CHANGED_BY = "changed_by";
    private static final String CHANGED_DATE = "changed_date";
    private static final String FIELD_VALUE = "field_value";
    private static final String PREV_STATE = "prev_state";
    private static final String QUERY = "query";
    private static final String MENU_IDE = "MENU_ID";
    private static final String DATA = "DATA";
    private static final String STATE = "status";
    private static final String ERROR = "error";
    private static final String ARG_VALUES = "argvalues";
    private static final String TICKET_ID = "TICKET_ID";
    private static final String CREATEDBY ="createdby";
    private static final String CREATEDDATE ="createddate";
    private static final String AUDIT_LOG_DETAILS = "auditlogdetails";
	private static final String LOCKED_BY = "LOCKED_BY";
	private static final String LOCKED_DATE = "LOCKED_DATE";
	private static final String procName = "insert_audit_log";
	private static final String problem="Problem";
	private static final String FUNCTION="FUNCTION";
	private static final String OS_DETAILS= "osdetails";
	private static final String BROWSER_DETAILS="browserdetails";
	private static final String ASSIGNED_GRP="ASSIGNED_GROUP";
	private static final String iconnect="iconnect";
	private static final String OS_BRWSR_DET="OsBrowserDetailsNeedToBeSaved";
	private static final String CATEGORY="CATEGORY";
	private static final String SUB_CATEGORY="SUB_CATEGORY";
	private static final String CCEmailID="CCEmailID";
	private static final String LOCATION="LOCATION";
	private static final String CONTACT_NUM ="CONTACT_NO";
	private static final String SUBJECTS="SUBJECT";
	private static final String DESCRPTION ="DESCRIPTION";
	private static final String ASSIGND_TO="ASSIGNED_TO";
	private static final String FUNCN_ID="FUNCTION_ID";
	private static final String categoryName="categoryName";
	private static final String WF_NAME ="WF_NAME";
	private static final String PREVIOUS_STATE="PREVIOUS_STATE";
	private static final String PREVIOUS_ECT="PREVIOUS_ECT";
	private static final String IFIRST_TICKET_ID="IFIRST_TICKET_ID";
	HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter();
	 /**********Argument Names For Audit Log**************/
	String argName[] = { REFERENCE_ID, MENU_ID, CATEGORY_IDs, PRIORITY_ID,
			ASSIGNED_GROUPs, CURRENT_STATE, ACTION, CHANGED_BY, CHANGED_DATE,
            FIELD_VALUE, PREV_STATE };
	 /**********End Argument Names For Audit Log**************/
	 /**********Argument Types For Audit Log**************/
    int argType[] = { Types.VARCHAR, Types.SMALLINT, Types.VARCHAR,
            Types.SMALLINT, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
            Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR };
    /**********End Argument Types For Audit Log**************/
	@Autowired
	public void init(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setiConnectDAOUtil(COMMON_ExecuteProcedure dAOUtilObj) {
		DAOUtilObj = dAOUtilObj;
	}
	
	
	/**********Method  to Insert Master Ticket Details**************/	
	public String insertMasterTicket(HashMap<String, Object> insertdetails) throws IOException {
		Map<String, Object> auditlog = (HashMap<String, Object>) insertdetails
		.get(AUDITLOG_DETAILS);
		String masterTicketId=insertMasterTicketDet(insertdetails);
		String problemattachmentPath = "";
		String approvalattachmentPath = "";
		String attachtype = problem;
		HELPDESK_FileUpload fileUpload = new HELPDESK_FileUpload();
		MultipartFile problemfile = (MultipartFile) insertdetails
		.get(PROBLEM_FILE_DATA);
		if (!problemfile.isEmpty()) {
			auditlog.put(PROBLEM_FILE, converter.replaceSpecialChars(problemfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")));
			problemattachmentPath = fileUpload.uploadFileForHelpdesk(
					problemfile, masterTicketId, attachtype);
		}
		if ((problemattachmentPath != null && !""
				.equalsIgnoreCase(problemattachmentPath))
				|| (approvalattachmentPath != null && !""
						.equalsIgnoreCase(approvalattachmentPath))) {
			ArrayList<Object> ArgsList = new ArrayList<Object>();
			if (problemattachmentPath != null && !problemattachmentPath.equals("")) {
				Object[] GenArgs = new Object[] { masterTicketId,
						converter.replaceSpecialChars(problemfile.getOriginalFilename().replaceAll("[^A-Za-z0-9_#&.()&<>=\\~%@!^{}|$*\\/ ]","")),
						problemattachmentPath,
						(String) insertdetails.get(CREATE_BY),
						(String) insertdetails.get(CREATE_DATE) };
				ArgsList.add(0, GenArgs);
			}
			updateHelpDeskTicketAttachment(ArgsList);
		}
		/*insertUserOSBrowserDetails((String) insertdetails.get(CREATE_BY),
				masterTicketId, (String) insertdetails.get(CREATE_DATE),
				(String) insertdetails.get(OS_DETAILS), (String) insertdetails
						.get(BROWSER_DETAILS));	*/	
		String paramVal[] = { masterTicketId, "114",
				(String) insertdetails.get(FUNCTION),
				"3",
				insertdetails.get(ASSIGNED_GRP).toString(),
				"Open", "I",
				(String) insertdetails.get(CREATE_BY),
				(String) insertdetails.get(CREATE_DATE),
				MapToStringUtil.getStringForMap(auditlog), " " };
		try
		{
		DAOUtilObj.executeiconnectProc(procName, argName,
				argType, paramVal, jdbcTemplate.getDataSource());
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return "SUCCESS-" + masterTicketId;
	}	
	/**********End of Method  to Upd Master Ticket Details**************/
	
	/**********Method  to Insert User Os Browser  Details**************/
	private void insertUserOSBrowserDetails(String employeeID, String ticketID,
			String createdDate, String osdetails, String browserdetails) {

		ResourceBundle bundle = ResourceBundle.getBundle(iconnect);
		String OsBrowserDetailsNeedToBeSaved = bundle
				.getString(OS_BRWSR_DET);
		if (OsBrowserDetailsNeedToBeSaved.equalsIgnoreCase("true")) {			
			this.jdbcTemplate.update(
					HELPDESK_SQLQueryConstants.IC_USER_SYSTEM_DETAILS,
					employeeID, ticketID, "I", createdDate, osdetails,
					browserdetails);			
		}
	}
	/**********End Of Method  to Insert User Os Browser  Details**************/
	
	
	/**********Method  to Call Query for Insert Master  Details**************/
	public String insertMasterTicketDet(
			final HashMap<String, Object> insertdetails) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String insertQuery=MASTER_SQLQueryConstants.INSERT_MASTER_TICKET_QUERY;
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertQuery,	new String[] { TICKET_ID });
				ps.setString(1,(String)insertdetails.get(CREATE_BY));
				ps.setString(2,(String)insertdetails.get(FUNCTION));
				ps.setString(3,(String)insertdetails.get(CATEGORY));
				ps.setString(4,(String)insertdetails.get(SUB_CATEGORY));
				ps.setString(5,(String)insertdetails.get(CCEmailID));
				ps.setString(6,(String)insertdetails.get(LOCATION));
				ps.setString(7,(String)insertdetails.get(CONTACT_NUM));
				ps.setString(8,(String)insertdetails.get(SUBJECTS));
				ps.setString(9,(String)insertdetails.get(DESCRPTION));
				ps.setString(10,insertdetails.get(WORKFLOW_STATE).toString());
				ps.setString(11,(String)insertdetails.get(ASSIGNED_GRP));
				ps.setString(12,(String)insertdetails.get(ASSIGND_TO));
				ps.setString(13,(String)insertdetails.get(ECT));
				ps.setInt(14,1);
				ps.setString(15,(String)insertdetails.get(CREATE_BY));
				ps.setString(16,(String)insertdetails.get(CREATE_DATE));
				ps.setString(17,(String)insertdetails.get(IFIRST_TICKET_ID));

				return ps;
			}
		},keyHolder);
		return keyHolder.getKey().toString();
	}
	/**********End of Method  to Call Query for Insert Master  Details**************/
	/**********Method  to get the  Master  Details**************/
	public List<Map<String, Object>> getMasterTicketList(String id) {

		return this.jdbcTemplate.queryForList(
				MASTER_SQLQueryConstants.SELECT_MASTER_TICKET_LIST, new Object[]{id});
	}
	/**********End Method  to get the  Master  Details**************/

	/********** Method  to get the  Child Ticket Details**************/
	public List<Map<String, Object>> getChildTicketList(String masterId) {

		return this.jdbcTemplate.queryForList(
				MASTER_SQLQueryConstants.SELECT_ALL_CHILD_DET_LINKED_TO_MASTER,new Object[]{masterId});
	}
	/********** End of Method  to get the  Child Ticket Details**************/
	/********** Method  to Link Child Ticket Details to Master**************/
	public String saveChildToMaster(String childId, String masterId,
			String loginId) {
		String message=null;
		/************Get Child Det************************/
		 HelpDesk helpdesk=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.IC_IHD_CHILD_TICKET_DETAIL,new Object[] {childId},
				new RowMapper<HelpDesk>() {
			 public HelpDesk mapRow(ResultSet rs,
                     int count) throws SQLException {
				 HelpDesk obj=new HelpDesk();
				 obj.setTICKET_ID(rs.getString(TICKET_ID));
				 obj.setECT(rs.getString(ECT));
				 obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
				 obj.setFUNCTION_ID(rs.getString(FUNCN_ID));
				 obj.setFUNCTION(rs.getString(categoryName));
				 obj.setWORKFLOW_NAME(rs.getString("workflowName"));
				 return obj;
				 
			 }
		});
		 /************End of Get Child Det************************/
		 /************Get Master  Det*****************************/
		 MASTER_Create masterDet=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.SELECT_MASTER_TICKET_DET ,new Object[] {masterId},
					new RowMapper<MASTER_Create>() {
				 public MASTER_Create mapRow(ResultSet rs,
	                     int count) throws SQLException {
					 MASTER_Create obj=new MASTER_Create();
					 obj.setMASTER_TICKET_ID(rs.getString(TICKET_ID));
					 obj.setECT(rs.getString(ECT));
					 obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
					 obj.setASSIGNED_GROUP_ID(rs.getString(ASSIGNED_GRP));
					 obj.setASSIGNED_TO(rs.getString(ASSIGND_TO));
					 obj.setWORKFLOW_NAME(rs.getString("WfName"));
					  return obj;
					 
				 }
			});
		 /************End of Get Master  Det*****************************/
		String masterWfName=masterDet.getWORKFLOW_NAME();
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");	
		
		
		String childStateIDs=bundle.getString("childStateIdFor"+masterWfName.replaceAll("\\s",""));
		String childFuncnIDs=bundle.getString("childFunctionFor"+masterWfName.replaceAll("\\s",""));
		String funcnName=helpdesk.getFUNCTION();
		
		String[] childStateIDsList=childStateIDs.split(",");
		String[] childFuncnIDsList=childFuncnIDs.split(",");
		
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createdDate = dateFormatGmt.format(new Date());
		
		String childWfID=null;
		for(int i=0;i<childFuncnIDsList.length;i++){
			if(childFuncnIDsList[i].equals(funcnName)){
				childWfID=childStateIDsList[i];
			}
		}		    
			int argTypePrev[] = { Types.INTEGER, Types.SMALLINT,Types.TIMESTAMP,Types.VARCHAR};
			String paramValPrev[] = { helpdesk.getTICKET_ID(), helpdesk.getWORKFLOW_STATE(),helpdesk.getECT(),
					loginId};
		/****insert child to locked ticket detls*******************************************************************/
			StringBuffer xmldata=new StringBuffer();
			xmldata.append("<data>");
			xmldata.append("<WORKFLOW_STATE>");
			xmldata.append(helpdesk.getWORKFLOW_NAME());
			xmldata.append("</WORKFLOW_STATE>");
			xmldata.append("<ECT>");
			xmldata.append(helpdesk.getECT());
			xmldata.append("</ECT></data>");
			
			//check lock det
			Map<String, Object> lockdetails = new HashMap<String, Object>();
			try{
				lockdetails = this.jdbcTemplate.queryForMap(
						MASTER_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
						"1", childId);
				
			}catch(EmptyResultDataAccessException erdae){
				if(lockdetails.isEmpty()){
					this.jdbcTemplate.update(
							MASTER_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
							childId,
							"1",
							xmldata.toString(),
							loginId,							
							createdDate
							);		
			}
			}
		/****insert child to locked ticket detls*******************************************************************/
			
		int childPrevDetCount=this.jdbcTemplate.update(MASTER_SQLQueryConstants.INSERT_IC_IHD_CHILD_DELINK_PREV_DET, paramValPrev, argTypePrev);
			
		int result=this.jdbcTemplate.update(MASTER_SQLQueryConstants.UPDATE_CHILD_TO_MASTER_QUERY,masterId,childWfID,masterDet.getECT(),loginId,childId);
		
		
		String ectDate=null;
		try{
			ectDate=dateFormatGmt.format(dateFormatGmt.parse(masterDet.getECT().toString()));
		} catch (java.text.ParseException e) {
		
			e.printStackTrace();
		}		
		HashMap<String, Object> auditlogdetails = new HashMap<String, Object>();
		auditlogdetails.put(WORKFLOW_STATE,masterDet.getWORKFLOW_NAME().toString());
		auditlogdetails.put(ECT,ectDate);
		String paramVals[] = { helpdesk.getTICKET_ID().toString(), "1",
				helpdesk.getFUNCTION_ID().toString(),
				"3",
				masterDet.getASSIGNED_GROUP_ID().toString(),
				masterDet.getWORKFLOW_NAME().toString(), "U",
				loginId,
				createdDate,
				MapToStringUtil.getStringForMap(auditlogdetails), 
				" " };
		 String resJson = DAOUtilObj.executeiconnectProc(procName, argName,
	                argType, paramVals, jdbcTemplate.getDataSource());	
		 
		  this.jdbcTemplate.update(
	                HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
	                childId.toString());
		if(result>0 && childPrevDetCount>0){
			message="SUCCESS";
		}else{
			message="ERROR";
		}
		return message;
	}
	/**********End of  Method  to Link Child Ticket Details to Master**************/
	/********** Method  to Insert Master Ticket Attachments**************/
	public int updateHelpDeskTicketAttachment(ArrayList<Object> ArgsList) {

		int result = 0;
		int ArgTypes[] = { Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
		final String sql = MASTER_SQLQueryConstants.IC_HD_INSERT_ATTACHMENTS;

		DAOUtilObj.executeBatchUpdation(sql, ArgsList, ArgTypes, jdbcTemplate
				.getDataSource());

		return result;
	}
	/**********End Of Method  to Insert Master Ticket Attachments**************/
	/********** Method  to Check if Child is Linked to Master**************/
	public MASTER_Create getIsChildLinked(String childId) {
		
		 MASTER_Create masterDet=new MASTER_Create();
		 try{
		  masterDet=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.SELECT_IS_CHILD_LINKED_DET ,new Object[] {childId},
					new RowMapper<MASTER_Create>() {
				 public MASTER_Create mapRow(ResultSet rs,
	                     int count) throws SQLException {
					 MASTER_Create obj=new MASTER_Create();
					 obj.setMASTER_TICKET_ID(rs.getString(TICKET_ID));
					 obj.setREFERENCE_ID(rs.getString("REFERENCE_ID"));
					 return obj;
					 
				 }
			});
		 }catch(DataAccessException dae){
			 masterDet=new MASTER_Create();
		 }
		
		return masterDet;
	}
	/**********End of  Method  to Check if Child is Linked to Master**************/
	/********** Method  to Remove a Child Ticket from Master *********************/
	public String delinkChildFromMaster(String childId,String loginID) {
		String message="";		
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createdDate = dateFormatGmt.format(new Date());
		 MASTER_Create masterDet=new MASTER_Create();
		 try{
		  masterDet=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.SELECT_IC_DELINK_DETAILS ,new Object[] {childId},
					new RowMapper<MASTER_Create>() {
				 public MASTER_Create mapRow(ResultSet rs,
	                     int count) throws SQLException {
					 MASTER_Create obj=new MASTER_Create();
					obj.setWORKFLOW_STATE(rs.getString("PREVIOUS_STATE"));
					obj.setWORKFLOW_STATE_NAME(rs.getString("workflowName"));
					obj.setECT(rs.getString("PREVIOUS_ECT"));
					obj.setASSIGNED_GROUP_ID(rs.getString("ASSIGNED_GROUP"));
					obj.setASSIGNED_GROUP_NAME(rs.getString("groupName"));
					obj.setASSIGNED_TO(rs.getString("ASSIGNED_TO"));
					obj.setASSIGNED_TO_NAME(rs.getString("employeeName"));
					 return obj;
					 
				 }
			});
		 }catch(DataAccessException dae){
			 masterDet=new MASTER_Create();
		 }
		 HelpDesk helpdesk=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.IC_IHD_CHILD_TICKET_DETAIL,new Object[] {childId},
					new RowMapper<HelpDesk>() {
				 public HelpDesk mapRow(ResultSet rs,
	                     int count) throws SQLException {
					 HelpDesk obj=new HelpDesk();
					 obj.setTICKET_ID(rs.getString(TICKET_ID));
					 obj.setECT(rs.getString(ECT));
					 obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
					 obj.setFUNCTION_ID(rs.getString(FUNCN_ID));
					 obj.setFUNCTION(rs.getString(categoryName));
					 obj.setWORKFLOW_NAME(rs.getString("workflowName"));
					 return obj;
					 
				 }
			});
		 /****insert child to locked ticket detls*******************************************************************/
			StringBuffer xmldata=new StringBuffer();
			xmldata.append("<data>");
			xmldata.append("<WORKFLOW_STATE>");
			xmldata.append(helpdesk.getWORKFLOW_NAME());
			xmldata.append("</WORKFLOW_STATE>");
			xmldata.append("<ECT>");
			xmldata.append(helpdesk.getECT());
			xmldata.append("</ECT></data>");
			
			
			//check lock det
			Map<String, Object> lockdetails = new HashMap<String, Object>();
			try{
				lockdetails = this.jdbcTemplate.queryForMap(
						MASTER_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
						"1", childId);
				lockdetails.put(STATE, ERROR);
				}catch(EmptyResultDataAccessException erdae){
					if(lockdetails.isEmpty()){
						this.jdbcTemplate.update(
								MASTER_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
								childId,
								"1",
								xmldata.toString(),
								loginID,							
								createdDate
								);	
					}
				}
			

		/****insert child to locked ticket detls*******************************************************************/
			 int updateCount=this.jdbcTemplate.update(MASTER_SQLQueryConstants.UPDATE_DELINK_DETAIL,childId,childId,loginID,childId);
			 
		/********************Insert to audit log ***************************************/
			 HashMap<String, Object> auditlogdetails = new HashMap<String, Object>();
				auditlogdetails.put(WORKFLOW_STATE,masterDet.getWORKFLOW_STATE_NAME().toString());
				auditlogdetails.put(ECT,masterDet.getECT());
				
				
				String paramVals[] = { childId.toString(), "1",
						helpdesk.getFUNCTION_ID().toString(),
				"3",
				masterDet.getASSIGNED_GROUP_ID().toString(),
				masterDet.getWORKFLOW_STATE_NAME().toString(), "U",
				loginID,
				createdDate,
				MapToStringUtil.getStringForMap(auditlogdetails), 
				" " };
				
				String resJson = DAOUtilObj.executeiconnectProc(procName, argName,
			                argType, paramVals, jdbcTemplate.getDataSource());
				  this.jdbcTemplate.update(
			                HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
			                childId.toString());
		/********************End Insert to audit log***************************************/
			 int deleteCount=this.jdbcTemplate.update(MASTER_SQLQueryConstants.DELETE_DELINK_DETAIL, childId);
			 if(updateCount>0 && deleteCount>0){
				 message="SUCCESS";
			 }else
			 {
				 message="ERROR";
			 }
		
		return message;
	}
	/**********End of  Method  to Remove a Child Ticket from Master *********************/
	
	/********** Method  to get Master Ticket Details to Master_Create Bean*************************************/
	 public List<MASTER_Create> getReqList(String query, HttpServletRequest request) {
	        return this.jdbcTemplate.query(query, new Object[] {},
	                new helpdeskMapper(request));
	    }

	  private static class helpdeskMapper implements ParameterizedRowMapper<MASTER_Create> {
		  private HttpServletRequest request;

		  public helpdeskMapper(HttpServletRequest Request) {
			  this.request = Request;
		  }

		  public MASTER_Create mapRow(ResultSet rs, int arg1) throws SQLException {
			  MASTER_Create masterCreateBean=new MASTER_Create();
			  masterCreateBean.setMASTER_TICKET_ID(rs.getString(MASTER_TICKET_ID));
			  HELPDESK_SpecialCharacterConverter converter=new HELPDESK_SpecialCharacterConverter();
			  Timestamp timeStamp = rs.getTimestamp(CREATED_DATE);
	            if (timeStamp != null) {
	            	masterCreateBean.setCREATED_DATE(CustomDateFormatConstants
	                        .showUserTimeZonewithRequest(
	                                CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
	                                        .format(timeStamp), request));	               
	            }			 
			  masterCreateBean.setCREATOR_NAME(rs.getString(CREATOR_NAME));
			  masterCreateBean.setFUNCTION(rs.getString(FUNCTION_ID));
			  masterCreateBean.setFUNCTION_NAME(rs.getString(FUNCTION_NAME));
			  masterCreateBean.setCATEGORY(rs.getString(CATEGORY_ID));
			  masterCreateBean.setCATEGORY_NAME(rs.getString(CATEGORY_NAME));
			  masterCreateBean.setSUB_CATEGORY(rs.getString(SUB_CATEGORY_ID));
			  masterCreateBean.setSUB_CATEGORY_NAME(rs.getString(SUB_CATEGORY_NAME));
			  masterCreateBean.setSUBJECT(converter.convertSpecialChars(rs.getString(SUBJECT)));
			  masterCreateBean.setCC_TO_MAIL_ID(rs.getString(MAIL_ID));
			  masterCreateBean.setLOCATION_ID(rs.getString(LOCATION_ID));
			  masterCreateBean.setRESOLUTION_COMMENTS(converter.convertSpecialChars(rs.getString("Resolution Comments")));
			  masterCreateBean.setCONTACT_NO(rs.getString(CONTACT_NO));
			  masterCreateBean.setASSIGNED_TO(rs.getString(ASSIGNED_TO));
			  masterCreateBean.setASSIGNED_TO_NAME(rs.getString(ASSIGNED_TO_NAME)+"("+masterCreateBean.getASSIGNED_TO()+")");
			  masterCreateBean.setDESCRIPTION(converter.convertSpecialChars(rs.getString(DESCRIPTION)));			 
			  masterCreateBean.setASSIGNED_GROUP_NAME(rs.getString(ASSIGNED_GROUP_NAME));
			  masterCreateBean.setECT(rs.getString(ECT));
			  masterCreateBean.setASSIGNED_GROUP(rs.getString(ASSIGNED_GROUP));
			  masterCreateBean.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE_ID));
			  masterCreateBean.setWORKFLOW_NAME(rs.getString(WORKFLOW_NAME));
			  masterCreateBean.setCREATED_BY(rs.getString("RequestedName"));
			  masterCreateBean.setIFIRST_TICKET_ID(rs.getString(IFIRST_TICKET_ID));
			  Timestamp timeStmp = rs.getTimestamp(CLOSED_DATE);
	            if (timeStmp != null) {
	            	masterCreateBean.setCLOSED_DATE(CustomDateFormatConstants
	                        .showUserTimeZonewithRequest(
	                                CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS
	                                        .format(timeStmp), request));	               
	            }
			 masterCreateBean.setVERSION_NO(rs.getString(VERSION_NO));
			return masterCreateBean;
			  
		  }
	  }
	  /**********End of Method  to get Master Ticket Details to Master_Create Bean*************************************/
	  /**********Method  to UPDATE(EDIT) Master Ticket Details *************************************/
	public String updateHelpDeskRequest(Map<String, Object> daodetails) {
		 String query = (String) daodetails.get(QUERY);
		 Object[] GenArgs = (Object[]) daodetails.get(ARG_VALUES);
		 
		 this.jdbcTemplate.update(query, GenArgs);	
		 String paramVal[] = {
	                (String) daodetails.get(TICKET_ID),
	                "114",
	                (String) daodetails.get(FUNCTION_ID),// Modified by Sali on
	                // SEp14th
	               "3",
	                (String) daodetails.get(ASSIGNED_GROUP),
	                (String) daodetails.get(WORKFLOW_STATE),
	                "U",
	                (String) daodetails.get(CREATEDBY),
	                (String) daodetails.get(CREATEDDATE),
	                MapToStringUtil
	                        .getStringForMap((Map<String, Object>) daodetails
	                                .get(AUDIT_LOG_DETAILS)), "" };
		 
		 String resJson = DAOUtilObj.executeiconnectProc(procName, argName,
	                argType, paramVal, jdbcTemplate.getDataSource());
		 
		List<Map<String,Object>> listOfChildTickets=this.jdbcTemplate.queryForList(
					MASTER_SQLQueryConstants.SELECT_ALL_CHILD_DET_LINKED_TO_MASTER,new Object[]{(String) daodetails.get(TICKET_ID)});
		
		ResourceBundle bundle = ResourceBundle.getBundle(iconnect);		
		int i=0;
		StringBuffer strBuffer=new StringBuffer();
		for (Iterator<Map<String, Object>> iterator = listOfChildTickets.iterator(); iterator.hasNext();) {
			
			Map<String,Object> tickets=(Map<String, Object>)iterator.next();
			String wfState=(String) daodetails.get(WORKFLOW_STATE);
			String wfStateID=null;
			String prevState=tickets.get(WF_NAME).toString();
			//String ect=tickets.get("ECT").toString();
			
			String ids=wfState.replaceAll("\\s","");
			if(ids.contains("Resolved")){
				ids=wfState.replace("/", "");
				
			}
			String childStateIDs=bundle.getString(("childStateIdFor"+ids));
			String childFunctions=bundle.getString(("childFunctionFor"+ids));
			
			String[] childStateIDsList=childStateIDs.split(",");
			String[] childFunctionsList=childFunctions.split(",");
			for(int j=0;j<childFunctionsList.length;j++){
				if(tickets.get("FUNCTION_NAME").toString().equals(childFunctionsList[j])){
					wfStateID=childStateIDsList[j];
				}	
			}	
				strBuffer.append("UPDATE IC_IHD_TICKET_DETAILS SET WORKFLOW_STATE='");
				strBuffer.append(wfStateID);
				strBuffer.append("'");
				if(ids.contains("Resolved")){
					strBuffer.append(",RESOLUTION_COMMENTS='");				
					strBuffer.append( (String) daodetails.get("RESOLUTION_COMMENTS"));
					strBuffer.append("',CLOSED_DATE='");	
					strBuffer.append((String) daodetails.get(CREATEDDATE));
					strBuffer.append("'");
					Date closedDate=new Date();
					Date ect_date=null;
					DateFormat formatter = CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS_ECT_MASTER;
					try {
						 ect_date = (Date) formatter
						.parse((String) daodetails.get("ECT"));
						
					} catch (ParseException e) {
						
						
					}
					if(closedDate.after(ect_date)){
						strBuffer.append(",SLA_STATUS='OUT'");	
					}else{
						strBuffer.append(",SLA_STATUS='IN'");	
					}
					
				}
				strBuffer.append(",MODIFIED_BY='");				
				strBuffer.append( (String) daodetails.get(CREATEDBY));
				strBuffer.append("',MODIFIED_DATE=GETDATE()");
				strBuffer.append(" WHERE TICKET_ID='");
				strBuffer.append(tickets.get(TICKET_ID));
				strBuffer.append("'");			
			int resultstr=this.jdbcTemplate.update(strBuffer.toString());		
			strBuffer=new StringBuffer();			
			String parameterVal[]={
					tickets.get(TICKET_ID).toString(),
		                "1",
		                tickets.get(FUNCN_ID).toString(),		                
		               "3",
		                (String) daodetails.get(ASSIGNED_GROUP),
		                (String) daodetails.get(WORKFLOW_STATE),
		                "U",
		                (String) daodetails.get(CREATEDBY),
		                (String) daodetails.get(CREATEDDATE),
		                MapToStringUtil
		                        .getStringForMap((Map<String, Object>) daodetails
		                                .get(AUDIT_LOG_DETAILS)), "" };
			
			String resJsonChild = DAOUtilObj.executeiconnectProc(procName, argName,
	                argType, parameterVal, jdbcTemplate.getDataSource());
			  this.jdbcTemplate.update(
		                HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
		                tickets.get(TICKET_ID).toString());
		}
		
	
		  this.jdbcTemplate.update(
	                HELPDESK_SQLQueryConstants.IC_DELETE_LOCKED_TICKET_DETAILS,
	                daodetails.get(TICKET_ID));
		
		//update to the bulletin Board
			//Added by Sali
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
			BULLETINBOARD_CreateUpdateDAO bulletinBoardDAOImpl = (BULLETINBOARD_CreateUpdateDAO) ctx
			.getBean(BULLETINBOARD_DAO_IMPL);
			bulletinBoardDAOImpl.removeMasterTicketFromBulletinBoard((String)daodetails.get(TICKET_ID));
		 //
		 return "Success";
	}
	  /**********End Of Method  to UPDATE(EDIT) Master Ticket Details *************************************/
	  /**********Method  to get  Master Ticket Details *************************************/
	public MASTER_Create getMasterDet(String masterId) {
		 MASTER_Create masterDet=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.SELECT_MASTER_TICKET_DET ,new Object[] {masterId},
					new RowMapper<MASTER_Create>() {
				 public MASTER_Create mapRow(ResultSet rs,
	                     int count) throws SQLException {
					 MASTER_Create obj=new MASTER_Create();
					 obj.setMASTER_TICKET_ID(rs.getString(TICKET_ID));
					 obj.setECT(rs.getString(ECT));
					 obj.setWORKFLOW_STATE(rs.getString(WORKFLOW_STATE));
					 obj.setWORKFLOW_NAME(rs.getString("WfName"));
					 obj.setASSIGNED_GROUP_ID(rs.getString(ASSIGNED_GRP));
					 obj.setASSIGNED_TO(rs.getString(ASSIGND_TO));
					 obj.setSUBJECT(rs.getString(SUBJECTS));
					 obj.setDESCRIPTION(rs.getString(DESCRPTION));
					  return obj;
					 
				 }
			});
		return masterDet;
	}
	 /**********End Of Method  to get  Master Ticket Details *************************************/
	 /**********Method  to get Lock Master Ticket Details *************************************/
	public Map<String, Object> lockMasterTicket(JSONObject jsonobj)
			throws JSONException {
		
		Map<String, Object> lockdetails = new HashMap<String, Object>();
		lockChildTicket(jsonobj);
		try {
			lockdetails = this.jdbcTemplate.queryForMap(
					MASTER_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
					jsonobj.get(MENU_IDE), jsonobj.get(TICKET_ID));
			lockdetails.put(STATE, ERROR);
		} catch (EmptyResultDataAccessException erde) {
			if (lockdetails.isEmpty()) {
				this.jdbcTemplate.update(
						MASTER_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
						jsonobj.get(TICKET_ID), jsonobj.get(MENU_IDE), jsonobj
								.get(DATA), jsonobj.get(LOCKED_BY), jsonobj
								.get(LOCKED_DATE));
				lockdetails.put(STATE, "success");
			} else {
				lockdetails.put(STATE, ERROR);
			}

		}

		return lockdetails;
	}
	/**********End Of Method  to get Lock Master Ticket Details *************************************/
	/**********Method  to  Lock Child Ticket Details *********************************************/
	public Map<String, Object> lockChildTicket(JSONObject jsonobj)
	throws JSONException {
		Map<String, Object> lockdetails = new HashMap<String, Object>();
		String masterTicketId=jsonobj.get(TICKET_ID).toString();
		List<Map<String,Object>> childTicketList=getChildTicketList(masterTicketId);
		String data=jsonobj.get(DATA).toString();		
		Iterator<Map<String,Object>> childLstIterator=childTicketList.iterator();
		while(childLstIterator.hasNext()){
			Map<String,Object> child=childLstIterator.next();
			String childId=child.get(TICKET_ID).toString();
			try{
			lockdetails = this.jdbcTemplate.queryForMap(
					MASTER_SQLQueryConstants.IC_LOCKED_TICKET_DETAILS,
					"1", childId);
			lockdetails.put(STATE, ERROR);
			}catch(EmptyResultDataAccessException erdae){
				if(lockdetails.isEmpty()){
					this.jdbcTemplate.update(
							MASTER_SQLQueryConstants.IC_INSERT_LOCK_DETAILS,
							childId,
							"1",
							jsonobj.get(DATA),
							jsonobj.get(LOCKED_BY),							
							jsonobj.get(LOCKED_DATE)
							);
					lockdetails.put(STATE, "success");
				}
			}
			
			
		}
		
		
		return lockdetails;
	}
	/**********End Of Method  to  Lock Child Ticket Details *********************************************/
	/**********Method  to  Unlock Master Ticket Details *************************************************/
	public int unlockMasterTickets(ArrayList<Object> ArgsList) {

		int result = 0;
		int ArgTypes[] = { Types.INTEGER, Types.VARCHAR };
		final String sql = HELPDESK_SQLQueryConstants.IC_IHD_DELETE_LOCKED_DETAILS;

		DAOUtilObj.executeBatchUpdation(sql, ArgsList, ArgTypes, jdbcTemplate
				.getDataSource());

		return result;
	}
	/**********End Of Method  to  Unlock Child Ticket Details *********************************************/
	/******************User Details**********************************************************************/
	public User getUserDetails(String empId){
		
		User userObj=this.jdbcTemplate.queryForObject(MASTER_SQLQueryConstants.IC_USER_DETAILS ,new Object[] {empId},
				new RowMapper<User>() {
			 public User mapRow(ResultSet rs,
                     int count) throws SQLException {
				 User obj=new User();
				 obj.setUserName(rs.getString(USER_NAME));
				  return obj;
				 
			 }
		});
		return userObj;
		
	}
	/******************End Of User Details**********************************************************************/

}
