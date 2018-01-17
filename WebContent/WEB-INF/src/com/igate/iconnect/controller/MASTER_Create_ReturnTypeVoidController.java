package com.igate.iconnect.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.HELPDESK_Create;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MASTER_Create;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.BO.WORKSPACEMGMT_ExcelDump;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.constants.MASTER_SQLQueryConstants;
import com.igate.iconnect.dao.BULLETINBOARD_CreateUpdateDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.HELPDESK_EditDAO;
import com.igate.iconnect.dao.LoginDAO;
import com.igate.iconnect.dao.MASTER_CreateDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;
import com.igate.iconnect.service.LoginValidator;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;
import com.igate.iconnect.util.JsonUtility;
@Controller
public class MASTER_Create_ReturnTypeVoidController {
	
	private final LoginValidator loginValidator;
	@Autowired
	public MASTER_Create_ReturnTypeVoidController(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;

	}
	private static final String BULLETINBOARD_DAO_IMPL = "bulletinBoardDAOImpl";//Added by sali
	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String MASTER_EDITDAO_IMPL = "MasterTicketDAO";
	private static final String WORKFLOW_DAO_IMPL = "workFlowDAOImpl";
	private static final String HELPDESK_DAO_IMPL = "HelpDeskTicket";
	private static final String USER_LOGINID_VARIABLE = "userLoginId";
	private static final String FUNCTION="FUNCTION";
	private static final String FUNCTION_ID="FUNCTION_ID";
	private static final String CATEGORY="CATEGORY";
	private static final String SUB_CATEGORY="SUB_CATEGORY";
	private static final String SUBJECT="SUBJECT";
	private static final String MESSAGE_VARIABLE = "message";
	private static final String DESCRIPTION="DESCRIPTION";
	private static final String ASSIGNED_GROUP="ASSIGNED_GROUP";
	private static final String ASSIGNED_GROUP_ID="ASSIGNED_GROUP_ID";
	private static final String FUNCTION_ID_VARIABLE = "FUNCTION_ID";
	private static final String ASSIGNED_TO="ASSIGNED_TO";
	private static final String LOCATION="LOCATION";
	private static final String LOCATION_ID="LOCATION_ID";
	private static final String ECTime="ECT";
	private static final String CONTACT_NO="CONTACT_NO";
	private static final String CCEmailID="CCEmailID";
	private static final String CREATED_BY="CREATED_BY";
	private static final String CREATED_DATE="CREATED_DATE";
	private static final String WORKFLOW_STATE="WORKFLOW_STATE";
	private static final String WORKFLOW_STATE_NAME="Open";
	private static final String AUDIT_DETAILS_VARIABLE = "auditlogdetails";
	private static final String STATUS_VARIABLE = "status";
	private static final String SUCCESS_VARIABLE = "SUCCESS";
	private static final String TICKET_ID_VARIABLE="TICKET_ID";
	private static final String OS_DETAILS_VARIABLE = "osdetails";
	private static final String BROWSER_DETAILS_VARIABLE = "browserdetails";
	private static final String ERROR ="error";
	private static final String PROBLEM_FILE_DATA_VARIABLE = "problemfiledata";
	private static final String RESOLUTION_COMMENTS_VARIABLE = "RESOLUTION_COMMENTS";
	private static final String WORKFLOW_STATE_VARIABLE = "WORKFLOW_STATE";
	private static final String CREATED_DATE_STORAGE_VARIABLE = "CREATED_DATE_STORAGE";
	private static final String SUB_CATEGORY_ID="SUB_CATEGORY_ID";
	private static final String MENU_ID="menu_id";
	private static final String CATEGORY_ID="CATEGORY_ID";
	//private static final String IFIRST_TICKET_ID="IFIRST_TICKET_ID";

	@RequestMapping(value = "/MASTER_Create.htm", method = RequestMethod.GET)
	public ModelAndView showMasterCreation(ModelMap model,
			HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> type = MasterDataImpl.getCategoriesById(
		"PARENT_ID", 0);
		List<Map<String, Object>> functionList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : type){
			if(!map.get("NAME").toString().equalsIgnoreCase("Function Correction Required")){
				functionList.add(map);
			}
		}
		type = functionList;
		model.put("type", type);
		List<Map<String, Object>> priorities = MasterDataImpl.getPriorityList();				
		model.put("priorities", priorities);			
		model.put("attachmentselect", "");
		model.put("attachmentdisplay", "display:none");
		MASTER_Create masterCreate=new MASTER_Create();
		model.addAttribute("CreateMasterTicketBean",masterCreate);
		return new ModelAndView("MASTER_Create");
	}
	@RequestMapping(value = "/getHelpDeskCategory.htm", method = RequestMethod.GET)
	public void getCategoriesForCreatingMasterTicket(
			@RequestParam String CategoryId, HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> categories = MasterDataImpl
				.getCategoriesById("PARENT_ID", Integer.parseInt(CategoryId));
		JsonUtility.sendData(categories, response);
	}
	/**
	 * To get the assigned groups for given function&location
	 * 
	 * 
	 */

	@RequestMapping(value = "/getMasterGroupsforFunction.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforFunction(@RequestParam JSONObject json,
			HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {
		String functionid=json.getString("functionid");
		String locationid=json.getString("locationid");
		List<String> items = Arrays.asList(locationid.split("\\s*,\\s*"));
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String,Object>>();
			 assigned_groups = 	getGroupsForFunction(
				request, Integer.parseInt(functionid),items);

		JsonUtility.sendData(assigned_groups, response);
	}
	
	@RequestMapping(value = "/getMasterGroupsforFunctionAll.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupsforFunctionAll(@RequestParam String functionid,
			HttpServletResponse response,
			HttpServletRequest request) throws ParseException, IOException,
			JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();
		assigned_groups = MasterDataImpl.getGroupsForFunction(Integer.parseInt(functionid));
		JsonUtility.sendData(assigned_groups, response);
	}
	
	
	@RequestMapping(value = "/MASTER_getGroupmember.htm", method = RequestMethod.GET)
	public @ResponseBody
	void hdgetGroupmembers(@RequestParam String groupId,
			String functionid,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {

		Map<String, Object> responsedetails = new HashMap<String, Object>();
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean(COMMON_CACHE_IMPL);
			List<Map<String, Object>> group_members = MasterDataImpl.getIHDGroupMemberID(groupId);
			responsedetails.put("MEMBER_DETAILS", group_members);
			JsonUtility.sendData(responsedetails, response);
	}

	public static List<Map<String, Object>> getGroupsForFunction(
			HttpServletRequest request, int functionid, List<String> locationid) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> assigned_groups = new ArrayList<Map<String, Object>>();

		assigned_groups = MasterDataImpl.getGroupsForFunction(functionid,locationid);
		return assigned_groups;
	}
	@RequestMapping(value = "/MASTER_Create.htm", method = RequestMethod.POST)
	public void createnewMasterTicket(MASTER_Create CreateMasterTicketBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		MultipartFile file = CreateMasterTicketBean.getProblemfile();
		if (!file.isEmpty()) {
			String status = fileValidation(CreateMasterTicketBean.getProblemfile());
			if (!status.equalsIgnoreCase("")) {
				values.put("problemfile", status);
			}
		}
		if (values.size() > 0) {
			values.put(ERROR, "Error");
			JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
					response);
		} else {
		createMasterTicket(CreateMasterTicketBean, request, response);
		}
		
	}

	public void createMasterTicket(MASTER_Create CreateMasterTicketBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject jsonforSuccess = new JSONObject();
		String ECT=CreateMasterTicketBean.getECT();
	/*	if(compareDates(ECT)==false){
			JSONObject jsonforSuccess = new JSONObject();
			jsonforSuccess.put(STATUS_VARIABLE, "ECT date should be within 48 hours");
			JsonUtility.writedata(jsonforSuccess.toString(), response);
		}*/
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
		.getBean(HELPDESK_DAO_IMPL);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
		.getBean(WORKFLOW_DAO_IMPL);
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String userName=userObj.getUserName();
		HashMap<String, Object> insertdetails = new HashMap<String, Object>();
		HashMap<String, Object> auditlogdetails = new HashMap<String, Object>();
		String functionId=CreateMasterTicketBean.getFUNCTION();
		
		String categoryId=CreateMasterTicketBean.getCATEGORY();
		String subCategoryId=CreateMasterTicketBean.getSUB_CATEGORY();
		String subject=CreateMasterTicketBean.getSUBJECT();
		subject=converter.replaceSpecialChars(subject);
		String description=CreateMasterTicketBean.getDESCRIPTION();
		description=converter.replaceSpecialChars(description);
		String assignedGroup=CreateMasterTicketBean.getASSIGNED_GROUP_ID();
		String assignedTo=CreateMasterTicketBean.getASSIGNED_TO();

		
		
		String contactNo=CreateMasterTicketBean.getPhonenumber();
		String ccToMailId=CreateMasterTicketBean.getCC_TO_MAIL_ID();
		String osDetails=CreateMasterTicketBean.getOS_DETAILS();
		String browserDetails=CreateMasterTicketBean.getBROWSER_DETAILS();	
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createdDate = dateFormatGmt.format(new Date());	
	
		String workflowIDStr =null;		
		String functionListStr=null;
		String menuID =null;
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		workflowIDStr=bundle.getString("masterWorkflowIdForIT");
		functionListStr=bundle.getString("masterWorflowNameForFunction");
		String[] workflowList=workflowIDStr.split(",");
		String[] functionList=functionListStr.split(",");
		for(int i=0;i<functionList.length;i++){
			if(CreateMasterTicketBean
				.getFUNCTION_NAME().equals(functionList[i])){
				menuID=workflowList[i];
			}
		}
		int workflowState = Integer.parseInt(workflowimpl.getStateId(
				WORKFLOW_STATE_NAME, menuID));
		String workflowStateName=workflowimpl.getStateName(Integer
				.toString(workflowState), menuID);
		
		insertdetails.put(FUNCTION, functionId);
		auditlogdetails.put(FUNCTION_ID,
				MasterDataImpl
						.getCategoryNameById(Integer
								.parseInt(functionId)));
		
/*		if (null != functionId
				&& (functionId.equalsIgnoreCase("256") || functionId
						.equalsIgnoreCase("1"))) {
			insertdetails.put(IFIRST_TICKET_ID, "0");
		} else {
			insertdetails.put(IFIRST_TICKET_ID, null);
		}*/
		insertdetails.put(CATEGORY,categoryId);
		auditlogdetails.put(CATEGORY_ID,
				MasterDataImpl
						.getCategoryNameById(Integer
								.parseInt(categoryId)));
		insertdetails.put(SUB_CATEGORY,subCategoryId);
		auditlogdetails.put(SUB_CATEGORY_ID,
				MasterDataImpl
						.getCategoryNameById(Integer
								.parseInt(subCategoryId)));
		insertdetails.put(SUBJECT,subject);
		auditlogdetails.put(SUBJECT,subject);
		insertdetails.put(DESCRIPTION,description);
		auditlogdetails.put(DESCRIPTION,description);
		insertdetails.put(ASSIGNED_GROUP,assignedGroup);
		String groupName=MasterDataImpl.getGroupIdById(Integer.parseInt(assignedGroup));		
		auditlogdetails.put(ASSIGNED_GROUP,groupName);
		insertdetails.put(ASSIGNED_TO,assignedTo);
		User userObject=MasterDaoImpl.getUserDetails(assignedTo);
		String assignedToName=userObject.getUserName();
		auditlogdetails.put(ASSIGNED_TO,assignedToName+"("+assignedTo+")");
		String locationName=null;
		String location=CreateMasterTicketBean.getLOCATION_ID();
		List<String> items=new ArrayList<String>();
		if(location==null || location.equals("0")){
			items=null;
		}else{
			items = Arrays.asList(location.split("\\s*,\\s*"));			
		}	
		int i=0;
		StringBuffer locationIdToInsert=new StringBuffer();
		StringBuffer locationNameToInsert=new StringBuffer();
		
		if(items !=null){
		Iterator<String> itemsListIterator = items.iterator();
		while (itemsListIterator.hasNext()) {
			
			String locationId=itemsListIterator.next();
			locationName = MasterDataImpl.getLocationsById(Integer
					.parseInt(locationId), "CITY");
			locationIdToInsert.append(locationId);
			locationNameToInsert.append(locationName);
			if(items.size()!=(i+1)){
			locationIdToInsert.append(",");
			locationNameToInsert.append(",");
			}
			i++;
		}
			
		}else{
			locationIdToInsert.append("0");
			locationNameToInsert.append("0");
		}
		
		insertdetails.put(LOCATION,locationIdToInsert.toString());
		
		auditlogdetails.put(LOCATION_ID,locationNameToInsert.toString());
		
		DateFormat dateFormatGmtECT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date ectDate=dateFormatGmtECT.parse(CreateMasterTicketBean.getECT());
		
		insertdetails.put(ECTime,dateFormatGmt.format(ectDate));
		auditlogdetails.put(ECTime,CustomDateFormatConstants.convertToEST(dateFormatGmt.format(ectDate)));
		insertdetails.put(CONTACT_NO, contactNo);
		insertdetails.put(CCEmailID,ccToMailId);
		insertdetails.put(CREATED_BY,createdby);
		insertdetails.put(CREATED_DATE,createdDate);
		insertdetails.put(WORKFLOW_STATE,workflowState);
		auditlogdetails.put(WORKFLOW_STATE,workflowStateName);		
		auditlogdetails.put(CREATED_BY,userName+"("+createdby+")");
		auditlogdetails.put(CREATED_DATE,CustomDateFormatConstants.convertToEST(createdDate));
		insertdetails.put(AUDIT_DETAILS_VARIABLE,auditlogdetails);
		insertdetails.put(OS_DETAILS_VARIABLE,osDetails);
		insertdetails.put(BROWSER_DETAILS_VARIABLE,browserDetails);
		insertdetails.put(MENU_ID, menuID);
		
		insertdetails.put(PROBLEM_FILE_DATA_VARIABLE, CreateMasterTicketBean
				.getProblemfile());
		
		String result=MasterDaoImpl.insertMasterTicket(insertdetails);
		//Added by Sali start
		HashMap<String, Object> insertbulletindetails = new HashMap<String, Object>();
		insertbulletindetails.put("BULLETIN_DESCRIPTION", CreateMasterTicketBean.getDESCRIPTION());
		insertbulletindetails.put("BULLETIN_HEADER", "Master Ticket");
		String bulletincreatedby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		insertbulletindetails.put("CREATEDBY", bulletincreatedby);
		insertbulletindetails.put("CREATEDDATE",createdDate);
		BULLETINBOARD_CreateUpdateDAO bulletinBoardDAOImpl = (BULLETINBOARD_CreateUpdateDAO) ctx
		.getBean(BULLETINBOARD_DAO_IMPL);
		
		//End
		
		if (result.contains(SUCCESS_VARIABLE)) {
			jsonforSuccess.put(STATUS_VARIABLE, SUCCESS_VARIABLE);
			jsonforSuccess.put(TICKET_ID_VARIABLE, result.substring(result
					.indexOf("-") + 1, result.length()));
			//Added by Sali
			/*insertbulletindetails.put("MASTER_TICKET_ID",result.substring(result
					.indexOf("-") + 1, result.length()));
			bulletinBoardDAOImpl.insertBulletinBoard(insertbulletindetails);*/
		}
		JsonUtility.writedata(jsonforSuccess.toString(), response);
		
		
	}
	
	
	@RequestMapping(value = "/DeLinkChildTicket", method = RequestMethod.GET)
	public @ResponseBody
	void deLinkChildTicket(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		JSONObject jsonobj = new JSONObject(json);
		String childId=jsonobj.getString("CHILD_TICKET_ID").toString();		
		String dataMessage=MasterDaoImpl.delinkChildFromMaster(childId,loginID);
		JsonUtility.sendData(dataMessage, response);

	}
	
	@RequestMapping(value = "/MASTER_Edit.htm", method = RequestMethod.GET)
	public String editMasterCreation(@RequestParam String id,ModelMap model,
			HttpServletRequest request) {
		String loginID = (String) request.getSession().getAttribute(
		"userLoginId");
		
		User userObj = (User) request.getSession().getAttribute(loginID);		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
		.getBean("workFlowDAOImpl");		
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean(COMMON_CACHE_IMPL);
			
		String query=MASTER_SQLQueryConstants.SELECT_MASTER_DETAILS+id;		
		List<MASTER_Create> ticketDetails = new ArrayList<MASTER_Create>();		
		ticketDetails=MasterDaoImpl.getReqList(query, request);
		MASTER_Create masterEditBean=new MASTER_Create();	
		masterEditBean=ticketDetails.get(0);
		
		

		/*String workflowID = workflowimpl.getWorkflowID(masterEditBean
				.getFUNCTION_NAME());*/
		String workflowIDStr =null;
		String workflowID =null;
		String functionListStr=null;
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		workflowIDStr=bundle.getString("masterWorkflowIdForIT");
		functionListStr=bundle.getString("masterWorflowNameForFunction");
		String[] workflowList=workflowIDStr.split(",");
		String[] functionList=functionListStr.split(",");
		for(int i=0;i<functionList.length;i++){
			if(masterEditBean
				.getFUNCTION_NAME().equals(functionList[i])){
				workflowID=workflowList[i];
			}
		}
		
		
		List<String> workflowstatus = null;
		if (masterEditBean.getWORKFLOW_STATE() != null) {
			workflowstatus = workflowimpl.getNextValidTransition(workflowID,
					masterEditBean.getWORKFLOW_NAME(), userObj.getUserRole());
		} else {
			workflowstatus = new ArrayList<String>();
		}
		workflowstatus.add(0, masterEditBean.getWORKFLOW_NAME());
		model.addAttribute("WORKFLOW", workflowstatus);
		 request.setAttribute("id", masterEditBean.getMASTER_TICKET_ID());
		 
		 String locationId=masterEditBean.getLOCATION_ID();
		 
		 
		 List<String> items = Arrays.asList(locationId.split("\\s*,\\s*"));
		 StringBuffer stringBuffer=new StringBuffer();
		 int i=0;
		 if(items!=null){
			 Iterator<String> itemsListIterator = items.iterator();
				while (itemsListIterator.hasNext()) {
					
					String location=itemsListIterator.next();
					String locationNm = MasterDataImpl.getLocationsById(Integer
							.parseInt(location), "CITY");
					stringBuffer.append(locationNm);
					if(items.size()!=(i+1)){
					
						stringBuffer.append(",");
					}
					i++;
				}
					
				}else{					
					stringBuffer.append("0");
				}
		 
			
		 
		 if(stringBuffer.toString().equals("")){
			 masterEditBean.setLOCATION_NAME("All");
		 }else{
		 masterEditBean.setLOCATION_NAME(stringBuffer.toString());
		 }
		 

		 String ect=masterEditBean.getECT();
		 masterEditBean.setECT(CustomDateFormatConstants.convertToEST(String
					.valueOf(ect))); 
		model.addAttribute("EditMasterTicketBean", masterEditBean);
		
		List<String> viewpermissionmap = workflowimpl
		.getViewOnlyPermissionForField(workflowID, userObj
				.getUserRole(), masterEditBean.getWORKFLOW_NAME());

		List<String> editpermissionmap = workflowimpl
		.getEditOnlyPermissionForField(workflowID, userObj
				.getUserRole(), masterEditBean.getWORKFLOW_NAME());

		List<String> nonepermissionmap = workflowimpl
		.getNoneOnlyPermissionForField(workflowID, userObj
				.getUserRole(), masterEditBean.getWORKFLOW_NAME());
		String editbuttonaccess = "display:block";
		boolean loginuserExists = false;
		boolean isbelongsToGroup = false;
		
			if (masterEditBean.getASSIGNED_TO().equals(loginID)) {
				loginuserExists = true;
				isbelongsToGroup = true;
		}
		
		if (masterEditBean.getCREATED_BY().equalsIgnoreCase(loginID)
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			loginuserExists = true;
		} else if (masterEditBean.getCREATED_BY().equalsIgnoreCase(loginID)
				&& isbelongsToGroup) {
			loginuserExists = true;
		}				
		if (masterEditBean.getASSIGNED_TO() != null && !loginuserExists) {
			editbuttonaccess = "display:none";
		} else if (!loginuserExists
				&& userObj.getUserRole().equalsIgnoreCase("User")) {
			editbuttonaccess = "display:none";
		}else
		{
			 editbuttonaccess = "display:block";
		}
		model.addAttribute("editbuttonaccess", editbuttonaccess);
		model.addAttribute("viewpermissionmap", viewpermissionmap);
		model.addAttribute("editpermissionmap", editpermissionmap);
		model.addAttribute("nonepermissionmap", nonepermissionmap);
		
		return "MASTER_Edit";
	}
	public String fileValidation(MultipartFile file) {

		String result = "";
		if (file.getSize() > 5243000) {
			result = "File size can't be more than 5 MB !";
		}

		String mimeType = file.getContentType();

		String data[] = CustomDateFormatConstants.getDataType().toArray(
				new String[CustomDateFormatConstants.getDataType().size()]);

		final Set<String> filetypes = new HashSet<String>(Arrays.asList(data));

		String fileextension = CustomDateFormatConstants
				.getFileTypeExtension(file.getOriginalFilename());
		if (CustomDateFormatConstants.getDataType().contains(fileextension)) {
			result += "</br> File type <b>" + fileextension
					+ "</b> is not supported";
		}
		String fileName=file.getOriginalFilename();
		if(fileName.length()>200){
			result += "</br> File name cannot be more than 200 characters";
		}
		
		String pattern = "[a-zA-Z0-9_. ]*";
		boolean namePattern = fileName.matches(pattern);
		if(!namePattern){
			result += "</br> File name can be alphanumeric and can have a dot(.),space( ) & underscore(_)";
		}
		return result;
	}
	
	
	@RequestMapping(value = "/saveChildToMaster", method = RequestMethod.GET)
	public @ResponseBody
	void saveChildToMasterTicket(@RequestParam String json,
			HttpServletResponse response, HttpServletRequest request)
			throws JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		User userObj = (User) request.getSession().getAttribute(createdby);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		JSONObject jsonobj = new JSONObject(json);
		String childId=jsonobj.getString("TICKET_ID").toString();
		String masterId=jsonobj.getString("MASTER_TICKET_ID").toString();
		String dataMessage=MasterDaoImpl.saveChildToMaster(childId,masterId,loginID);
		JsonUtility.sendData(dataMessage, response);

	}
	
	@RequestMapping(value = "/iConnectMasterTicketUpdation.htm", method = RequestMethod.POST)
	public @ResponseBody
	void updateMasterTicket(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		JSONObject jsonarray = new JSONObject(jsonString.replace("\\n",
		"brlinebreakbreak"));
		jsonarray = new JSONObject(jsonarray.getString("jsonString"));
		String updatejson = jsonarray.getString("updateJson");
		JSONObject jsonobj = new JSONObject(updatejson);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO getMasterDAO = (MASTER_CreateDAO) ctx
				.getBean(MASTER_EDITDAO_IMPL);
		String updatestatus = submit(jsonString, response,
				request);
		result.put(STATUS_VARIABLE, updatestatus);
		result.put(MESSAGE_VARIABLE, updatestatus);
		JsonUtility.sendData(result, response);
		
	}
	public String submit(String  jsonString, HttpServletResponse response,
			HttpServletRequest request) throws JSONException {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO getMasterDAO = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean(WORKFLOW_DAO_IMPL);
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);		
		JSONObject jsonarray = new JSONObject(jsonString.replace("\\n",
		"brlinebreakbreak"));
		jsonarray = new JSONObject(jsonarray.getString("jsonString"));
		String updatejson = jsonarray.getString("updateJson");
		JSONObject jsonobj = new JSONObject(updatejson);
		HELPDESK_SpecialCharacterConverter converter = new HELPDESK_SpecialCharacterConverter();
		if (updatejson.contains(RESOLUTION_COMMENTS_VARIABLE)) {
			String resolutionComments = converter.replaceSpecialChars(jsonobj
					.getString(RESOLUTION_COMMENTS_VARIABLE));
			jsonobj.remove(RESOLUTION_COMMENTS_VARIABLE);
			jsonobj.put(RESOLUTION_COMMENTS_VARIABLE, resolutionComments);
			
		}
		String auditlogjson = jsonarray.getString("auditlogJson");
		JSONObject auditjsonobj = new JSONObject(auditlogjson);
		if (auditlogjson.contains(RESOLUTION_COMMENTS_VARIABLE)) {
			String resolutionComments = converter
					.replaceSpecialChars(auditjsonobj
							.getString(RESOLUTION_COMMENTS_VARIABLE));
			auditjsonobj.remove(RESOLUTION_COMMENTS_VARIABLE);
			auditjsonobj.put(RESOLUTION_COMMENTS_VARIABLE, resolutionComments);
		}
		String currentstate = (String) jsonobj.get(WORKFLOW_STATE_VARIABLE);
		
	
		
		String functionId = (String) jsonobj.get(FUNCTION_ID_VARIABLE);
		String functionName = (String) jsonobj.get("FUNCTION");
		String workflowID =null;
		if (jsonobj.has(WORKFLOW_STATE_VARIABLE)) {		
			String workflowIDStr =null;
			
			String functionListStr=null;
			ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
			workflowIDStr=bundle.getString("masterWorkflowIdForIT");
			functionListStr=bundle.getString("masterWorflowNameForFunction");
			String[] workflowList=workflowIDStr.split(",");
			String[] functionList=functionListStr.split(",");
			for(int i=0;i<functionList.length;i++){
				if(functionName.equals(functionList[i])){
					workflowID=workflowList[i];
				}
			}
			int workflowStateId = Integer.parseInt(workflowimpl.getStateId(
					(String) jsonobj.get(WORKFLOW_STATE_VARIABLE),workflowID));
			
			
			jsonobj.remove(WORKFLOW_STATE_VARIABLE);
			jsonobj.put(WORKFLOW_STATE_VARIABLE, workflowStateId);
		}
		
		
		
		jsonobj.remove(CREATED_DATE_STORAGE_VARIABLE);
		auditjsonobj.remove(CREATED_DATE_STORAGE_VARIABLE);
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createddate = dateFormatGmt.format(new Date());
		if (jsonobj.has("VERSION_NO")) {
			int version_no = Integer.parseInt(jsonobj.get("VERSION_NO")
					.toString());
			version_no = version_no + 1;
			jsonobj.remove("VERSION_NO");
			jsonobj.put("VERSION_NO", String.valueOf(version_no));
			auditjsonobj.remove("VERSION_NO");
			auditjsonobj.put("VERSION_NO", String.valueOf(version_no));
		}
		if (currentstate.equalsIgnoreCase("Resolved/Closed")) {
			jsonobj.put("CLOSED_DATE", createddate);
			auditjsonobj.put("CLOSED_DATE",CustomDateFormatConstants.convertToEST(createddate));
		}
		JSONObject JsonArray = new JSONObject();
		
		if(jsonobj.has("FUNCTION_ID")){
			jsonobj.remove("FUNCTION_ID");
			auditjsonobj.remove("FUNCTION_ID");
			jsonobj.remove("FUNCTION");
			auditjsonobj.remove("FUNCTION");
		}
		String ect=(String)jsonobj.get("ECT");
		jsonobj.remove("ECT");
		auditjsonobj.remove("ECT");
		JsonArray.put("updatejson", jsonobj);		
		JsonArray.put("auditjson", auditjsonobj);
		JsonArray.put("removingJson", jsonarray.getJSONArray("removingJson"));
		Map<String, Object> daodetails = returnUpdateQuery(JsonArray, loginID);
		daodetails.put(TICKET_ID_VARIABLE, (String) jsonobj
				.get(TICKET_ID_VARIABLE));
		if (updatejson.contains(RESOLUTION_COMMENTS_VARIABLE)) {
			daodetails.put(RESOLUTION_COMMENTS_VARIABLE,converter.replaceSpecialChars(jsonobj
					.getString(RESOLUTION_COMMENTS_VARIABLE)) );
		}
		daodetails.put(WORKFLOW_STATE_VARIABLE, currentstate);
		daodetails.put("createdby", loginID);
		daodetails.put(MENU_ID, workflowID);
		daodetails.put("FunctionID", functionId);
		daodetails.put("ECT", ect);
		daodetails.put("createddate", createddate);
		
		return getMasterDAO.updateHelpDeskRequest(daodetails);
	}
	public Map<String, Object> returnUpdateQuery(JSONObject jsonString,
			String loginID) throws JSONException {

		
		String updatejson = jsonString.getString("updatejson");
		JSONObject jsonobj = new JSONObject(updatejson);
		String auditlogjson = jsonString.getString("auditjson");
		JSONObject auditjsonobj = new JSONObject(auditlogjson);
		
		

		JSONArray removingarray = jsonString.getJSONArray("removingJson");
		for (int i = 0; i < removingarray.length(); i++) {
			if (jsonobj.has(removingarray.getString(i))) {
				jsonobj.remove(removingarray.getString(i));
			}
			if (auditjsonobj.has(removingarray.getString(i))) {
				auditjsonobj.remove(removingarray.getString(i));
			}
		}

		Map<String, Object> daodetails = new HashMap<String, Object>();
		Map<String, Object> auditLogDetails = new HashMap<String, Object>();
		Object[] GenArgs = new Object[jsonobj.length() + 2];
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE IC_IHD_MASTER_TICKET_DETAILS SET ");
		Iterator iterator = jsonobj.keys();
		int i = 0;
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (!key.equalsIgnoreCase(TICKET_ID_VARIABLE)) {
				sb.append(key);
				sb.append("=?,");
				
					GenArgs[i] = jsonobj.get(key);
				
				i++;
				auditLogDetails.put(key, auditjsonobj.get(key));
			}
		}
		sb.append("MODIFIED_BY=?,MODIFIED_DATE=?");
		GenArgs[i] = loginID;
		i++;
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createddate = dateFormatGmt.format(new Date());
		GenArgs[i] = createddate;
		i++;
		GenArgs[i] = jsonobj.get(TICKET_ID_VARIABLE);
		daodetails.put("argvalues", GenArgs);
		daodetails.put("query", sb.toString() + " WHERE TICKET_ID=?");
		
		daodetails.put("auditlogdetails", auditLogDetails);
		return daodetails;
	}
	
	@RequestMapping(value = "/getMasterTicketList.htm", method = RequestMethod.GET)
	public void getMasterTicketList(@RequestParam String id,HttpServletRequest request, HttpServletResponse response)
			throws JSONException {	
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		List<Map<String, Object>> masterTicketList = MasterDaoImpl.getMasterTicketList(id);
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		Iterator<Map<String, Object>> iterator=masterTicketList.iterator();
		while(iterator.hasNext()){
			Map<String, Object> mstrId=iterator.next();
			String ect=null;
			String createdDt=null;
			createdDt=CustomDateFormatConstants
            .showUserTimeZonewithTimezoneID(
            		mstrId.get("CREATED_DATE").toString(), 67);
			ect=CustomDateFormatConstants
            .showUserTimeZonewithTimezoneID(
            		mstrId.get("ECT").toString(), 67);
			mstrId.remove("CREATED_DATE");
			mstrId.put("CREATED_DATE",createdDt);
			mstrId.remove("ECT");
			mstrId.put("ECT",ect);
		}
		
		result.put("MasterTicketList", masterTicketList);
		JsonUtility.sendData(result, response);
	}

	@RequestMapping(value = "/getChildTicketListForMaster.htm", method = RequestMethod.GET)
	public void getChildTicketList(@RequestParam String masterId,HttpServletRequest request, HttpServletResponse response)
			throws JSONException {
	
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		List<Map<String, Object>> childTicketList = MasterDaoImpl.getChildTicketList(masterId);
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();	
		
		
		Iterator<Map<String, Object>> iterator=childTicketList.iterator();
		while(iterator.hasNext()){
			Map<String, Object> mstrId=iterator.next();
			String ect=null;				
			ect=CustomDateFormatConstants
            .showUserTimeZonewithTimezoneID(
            		mstrId.get("ECT").toString(), 67);			
			mstrId.remove("ECT");
			mstrId.put("ECT",ect);
		}
		
		result.put("childTicketList", childTicketList);
		JsonUtility.sendData(result, response);
	}
	
	@RequestMapping(value = "/getGroupsForMember.htm", method = RequestMethod.GET)
	public void getGroupsForMemberList(@RequestParam String employeeId,String functionId,HttpServletRequest request, HttpServletResponse response)
			throws JSONException {	
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		List<Map<String, Object>> groupList=MasterDataImpl.getGroupsForMemberId(employeeId,functionId);
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		result.put("GROUP_LIST", groupList);
		JsonUtility.sendData(result, response);
	}
	@RequestMapping(value = "/lockIHDMasterTicket", method = RequestMethod.POST)
	public @ResponseBody
	void lockMasterTicket(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {
		JSONObject json = new JSONObject(jsonString.replace("\\n",
				"brlinebreakbreak"));
		json = new JSONObject(json.getString("jsonString"));
		String loginID = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		json.put("LOCKED_BY", loginID);
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String createddate = dateFormatGmt.format(new Date());
		json.put("LOCKED_DATE", createddate);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		Map<String, Object> lockdetails = MasterDaoImpl.lockMasterTicket(json);

		JsonUtility.sendData(lockdetails, response);

	}
	
	@RequestMapping(value = "/unlockMasterTickets.htm", method = RequestMethod.GET)
	public void unlockMasterTicket(@RequestParam String json,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		JSONObject jsonarray = new JSONObject(json);
		JSONArray ticketarray = jsonarray.getJSONArray("JSONARRAY");
		ArrayList<Object> ArgsList = new ArrayList<Object>();

		Map<String, Object> statusmessage = new HashMap<String, Object>();
		for (int i = 0; i < ticketarray.length(); i++) {
			JSONObject jsonobj = ticketarray.getJSONObject(i);
			Object[] GenArgs = new Object[] { jsonobj.get("MENU_ID"),
					jsonobj.get(TICKET_ID_VARIABLE) };
			ArgsList.add(i, GenArgs);
		}

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
		.getBean(MASTER_EDITDAO_IMPL);
		// try {
		MasterDaoImpl.unlockMasterTickets(ArgsList);
		statusmessage.put(STATUS_VARIABLE, "Success");
		/*
		 * } catch (Exception e) { statusmessage.put("status", "Error"); if
		 * (e.getMessage() == null) statusmessage.put("message",
		 * "Problem encountered while unlocking the ticket's !"); else
		 * statusmessage.put("message", e.getMessage()); }
		 */
		JsonUtility.sendData(statusmessage, response);
	}
	
	public boolean compareDates(String ectDate){
        Date dt1 =null;
        Date dt2 =null;        
        Calendar now = Calendar.getInstance();
      
        now.setTime(new Date());
        now.add(Calendar.HOUR, 48);
        
      StringBuffer strBuffr=new StringBuffer();
      strBuffr.append(now.get(Calendar.MONTH));
      strBuffr.append('/');
      strBuffr.append(now.get(Calendar.DATE));
      strBuffr.append('/');
      strBuffr.append(now.get(Calendar.YEAR));
      strBuffr.append(" ");
      strBuffr.append(now.get(Calendar.HOUR));
      strBuffr.append(':');
      strBuffr.append(now.get(Calendar.MINUTE));
      strBuffr.append(':');
      strBuffr.append(now.get(Calendar.SECOND));
      
      String ectMaxDate=strBuffr.toString();
      
        try {
            dt1 = CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS_IST_CompareDt
            .parse(ectDate);
            dt2=CustomDateFormatConstants.YYYY_MM_DD_HH_MM_SS_IST_CompareDt
            .parse(ectMaxDate);
           
        } catch (ParseException e) {
          
        }
        if(dt1.after(dt2))
            return false;
        else
            return true;
    }
}
