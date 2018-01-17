/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import org.apache.log4j.Logger;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.ADMIN_CreateUserBO;
import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.ADMIN_SettingsDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.COMMON_ListPageDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ListPageSearch;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class ADMIN_Settings_ReturnTypeVoidController {
	
	private static Logger log = Logger
	.getLogger(ADMIN_Settings_ReturnTypeVoidController.class);
	
	private static final String SUCCESS_VARIABLE = "SUCCESS";

	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}
	
	@RequestMapping(value = "getGroupMemberDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getGroupMemberDetails(@RequestParam String groupId,
            String groupMemOption, HttpServletResponse response,
            HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> groupMember = new ArrayList<Map<String, Object>>();
        if (groupMemOption.equalsIgnoreCase("Active")) {
            groupMember = commonDataForCacheObj.getIHDGroupMemberID(groupId);
        }
        if (groupMemOption.equalsIgnoreCase("InActive")) {
            groupMember = commonDataForCacheObj
                    .getIHDInActiveGroupMemberID(groupId);
        }
        if (groupMemOption.equalsIgnoreCase("All")) {
            groupMember = commonDataForCacheObj
                    .getIHDActiveInactiveGroupMemberID(groupId);
        }
        JsonUtility.sendData(groupMember, response);

    }

    @RequestMapping(value = "getGroupMember.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getGroupMemberDetails(@RequestParam String groupId,
            HttpServletResponse response, HttpServletRequest request) {

        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> groupMember = commonDataForCacheObj
                .getIHDGroupMemberID_Admin(groupId);
        JsonUtility.sendData(groupMember, response);

    }

    @RequestMapping(value = "changeGroupSettings.htm", method = RequestMethod.GET)
    public String changeGroup(Model model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0);
        ((Map<String, Object>) model).put("type", type);
        return "changeGroupSettings";
    }

    @RequestMapping(value = "getGroup.htm", method = RequestMethod.GET) 
    public @ResponseBody
    void getGroupBasedFunction(@RequestParam String functionId,
            HttpServletResponse response, HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> group = commonDataForCacheObj
                .getGroupsForFunction(Integer.parseInt(functionId));
        List<Map<String, Object>> groupNameList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mp=new HashMap<String, Object>();
        for (Map<String, Object> groupListInfo : group) {        	
            /*groupNameList.add(groupListInfo.get("GROUP_NAME").toString() + "("
                    + groupListInfo.get("GROUP_ID").toString()+")");       	
        	groupNameList.add(groupListInfo.get("GROUP_NAME").toString());*/ 
        	mp.put("GROUP_ID", groupListInfo.get("GROUP_ID").toString());
        	mp.put("GROUP_NAME", groupListInfo.get("GROUP_NAME").toString());
        	groupNameList.add(mp);
        	mp=new HashMap<String, Object>();
        }
       /* Set<String> groupName = new HashSet<String>(groupNameList);
        List<String> groupNameListSorting = new ArrayList<String>(groupName);
        Collections.sort(groupNameListSorting);*/
        JsonUtility.sendData(groupNameList, response);
    }
    
    @RequestMapping(value = "getRole.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getRoleBasedFunction(@RequestParam String functionId,
            HttpServletResponse response, HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> roleList = commonDataForCacheObj
                .getRolesByCategory(Integer.parseInt(functionId));
        List<String> roleNameList = new ArrayList<String>();
        for (Map<String, Object> roleListInfo : roleList) {
            roleNameList.add(roleListInfo.get("ROLE_NAME").toString() + ","
                    + roleListInfo.get("ROLE_ID").toString());
        }
        Set<String> roleName = new HashSet<String>(roleNameList);
        List<String> roleNameListSorting = new ArrayList<String>(roleName);
        Collections.sort(roleNameListSorting);
        JsonUtility.sendData(roleNameListSorting, response);
    }

    @RequestMapping(value = "removeGroupMember.htm", method = RequestMethod.GET)
    public @ResponseBody
    void removeGroupMemberDetails(@RequestParam String groupID,
            String groupMemID, HttpServletResponse response,
            HttpServletRequest request) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, ConnectException {
        boolean flag = false;
        String loginID = request.getSession().getAttribute("userLoginId")
                .toString();
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
                .getBean("GetgroupSettings");
        
        String nonPrivilageRoleIdListStr="";
		ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		nonPrivilageRoleIdListStr=bundle.getString("userNonPrivilegeRoles");
		String[] nonPrivilageRoleIdList=nonPrivilageRoleIdListStr.split(",");

        List<Map<String, Object>> getGroups = commonDataForCacheObj
                .getIHDGroup(groupMemID);
        for (Map<String, Object> groupListInfo : getGroups) {
            if (Integer.parseInt(groupListInfo.get("GROUP_ID").toString()) != Integer
                    .parseInt(groupID)) {
                flag = true;
            }
        }

        Map<String, Object> getStatus = groupSettingsObj.verifyAssignedTicket(
                groupID, groupMemID, loginID);
        if ((!flag) && (getStatus.get("status") != "error")) {
            List<Map<String, Object>> getRoles = commonDataForCacheObj
                    .getUserRoleById(groupMemID);
            for (Map<String, Object> groupListInfo : getRoles) {
            	for(int privilage=0;privilage<nonPrivilageRoleIdList.length;privilage++){
	                if (Integer.parseInt(groupListInfo.get("ROLE_ID").toString()) == 
	                	Integer.parseInt(nonPrivilageRoleIdList[privilage]))
	                    groupSettingsObj.userRoleInactive(groupMemID, groupListInfo
	                            .get("ROLE_ID").toString(), loginID);
            	}
            }
            
            groupSettingsObj.groupMemberAudit(groupID,groupMemID,"1","0",loginID);
        }
        
        resetCacheAndResetWorkflowMethod();
        JsonUtility.sendData(getStatus, response);
    }

    @RequestMapping(value = "addGroupMember.htm", method = RequestMethod.GET)
    public @ResponseBody
    void addGroupMemberDetails(@RequestParam String groupID, String empID,
            String roleID, String accessLevel, String assignment,String readd, HttpServletResponse response,
            HttpServletRequest request) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException,ConnectException {
        String loginID = request.getSession().getAttribute("userLoginId")
                .toString();
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
                .getBean("GetgroupSettings");
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        int status = groupSettingsObj.addGroupMemberDetails(roleID,groupID, empID,
                loginID,accessLevel,assignment);
        if ((status == 1) && (Integer.parseInt(roleID) != 0)){
            groupSettingsObj.addUserRoleDetails(roleID, empID, loginID);
            if(readd.equalsIgnoreCase("yes")){
            	groupSettingsObj.groupMemberAudit(groupID, empID, "0", "1", loginID);
            }
        }
        resetCacheAndResetWorkflowMethod();
        JsonUtility.sendData(status, response);
    }


    @RequestMapping(value = "addGroupSettings.htm", method = RequestMethod.GET)
    public String addGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0);
        model.put("type", type);
        List<Map<String, Object>> role = commonDataForCacheObj.getRoles();
        model.put("role", role);
        return "addGroupSettings";
    }


    @RequestMapping(value = "getCountry.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getCountryBasedOnFunction(@RequestParam String functionId,
            HttpServletResponse response, HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> country = commonDataForCacheObj
                .getUniqueCountryList(functionId);
        JsonUtility.sendData(country, response);
    }

@RequestMapping(value = "getAccessLevel.htm", method = RequestMethod.GET)
	public @ResponseBody
	void accessLevelList(HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
		.getBean("GetMasterData");
		List<Map<String, Object>> accessLevel = commonDataForCacheObj
		.getGroupAccessLevelList();
		List<String> accessNameList = new ArrayList<String>();
		for (Map<String, Object> accessListInfo : accessLevel) {
			accessNameList.add(accessListInfo.get("ROLE_NAME").toString() + ","
					+ accessListInfo.get("ROLE_ID").toString());
		}
		Set<String> roleName = new HashSet<String>(accessNameList);
		List<String> roleNameListSorting = new ArrayList<String>(roleName);
		Collections.sort(roleNameListSorting);
		JsonUtility.sendData(roleNameListSorting, response);
	}
	
	@RequestMapping(value = "/insertNewLocationAdminConsoleStep2.htm", method = RequestMethod.POST)
	public @ResponseBody
	void insertNewLocationDetStep2(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		
		JSONObject jsonarray = new JSONObject(jsonString);
		jsonarray = new JSONObject(jsonarray.getString("jsonString"));
		
		String functionID=jsonarray.getString("function");
		String countryID=jsonarray.getString("country");
		String newCountryName=jsonarray.getString("newCountry");
		
		List<Map<String,Object>> buildingList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> operationTimeList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> approverList=new ArrayList<Map<String,Object>>();	
		
		
		
		result.put("messsage", "Success");
		JsonUtility.sendData(result, response);
	}
	
	
	@RequestMapping(value = "/insertNewLocationAdminConsoleStep1.htm", method = RequestMethod.POST)
	public @ResponseBody
	void insertNewLocationDetStep1(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		JSONObject jsonarray = new JSONObject(jsonString);
		jsonarray = new JSONObject(jsonarray.getString("jsonString"));
		
		String functionID=jsonarray.getString("function");
		String countryID=jsonarray.getString("country");
		String countryName=jsonarray.getString("countryName");
		String newCountryName=jsonarray.getString("newCountry"); 
		String cityName=jsonarray.getString("city");
		String loginID = request.getSession().getAttribute("userLoginId")
         .toString();
		 
		if(newCountryName.length()>0){
			countryName=newCountryName;
		}
		ApplicationContext ctx = COMMON_AppContext.getCtx();
        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
                .getBean("GetgroupSettings");
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
        .getBean("GetMasterData");
        
        DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String createdDate = dateFormatGmt.format(new Date());	
		
        
		
		List<Map<String,Object>> buildingList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> operationTimeList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> approverList=new ArrayList<Map<String,Object>>();
		//******************************Building List************************************//*
		JSONObject buildarray=new JSONObject();
		JSONObject buildObj=new JSONObject();
		if(jsonarray.has("BuildJSON")){			
			
			Map<String,Object> buildMap=new HashMap<String, Object>();
			buildarray=new JSONObject(jsonarray.getString("BuildJSON"));			
			int buildArrSize=buildarray.length();
			for(int i=1;i<=buildArrSize;i++)
			{
				buildObj=new JSONObject(buildarray.getString(""+i));
				if(buildObj.has("BuildingTD_"+i)){
					buildMap.put("Building",buildObj.get("BuildingTD_"+i) );
				}
				if(buildObj.has("FloorTD_"+i)){
					buildMap.put("Floor",buildObj.get("FloorTD_"+i) );
				}
				if(buildObj.has("ODCTD_"+i)){
					buildMap.put("ODC",buildObj.get("ODCTD_"+i) );
				}
				if(buildMap.size()>0){
					buildingList.add(buildMap);
				}
				buildMap=new HashMap<String, Object>();
			}
		}
		
		//******************************END Building List************************************//*
		
		//******************************Operating Time List************************************//*
		JSONObject opTimearray=new JSONObject();
		JSONObject opTimeObj=new JSONObject();
		if(jsonarray.has("BuildTimeJSON")){			
			Map<String,Object> opTimeMap=new HashMap<String, Object>();
			opTimearray=new JSONObject(jsonarray.getString("BuildTimeJSON"));
			Iterator<?> keys = opTimearray.keys();
			while( keys.hasNext() ){
	            String key = (String)keys.next();	           
	            if( opTimearray.get(key) instanceof JSONObject ){
	            	opTimeObj=new JSONObject(opTimearray.getString(key));
	            	Iterator<?> objKeys = opTimeObj.keys();
	            	while( objKeys.hasNext() ){
	            		 String objkey = (String)objKeys.next();	            		 
	            		 if(objkey.contains("DayTD_")){
								opTimeMap.put("DAY", opTimeObj.get(objkey));
							}					
							if(objkey.contains("startTime_")){
								opTimeMap.put("START_TIME",opTimeObj.get(objkey)+":00" );
							}
							if(objkey.contains("endTime_")){
								opTimeMap.put("END_TIME",opTimeObj.get(objkey)+":00" );
							}
							if(objkey.contains("nextDay_")){
								opTimeMap.put("NEXT_WORKING_DAY",opTimeObj.get(objkey) );
							}
	            	}
	            	operationTimeList.add(opTimeMap);
	            	opTimeMap=new HashMap<String, Object>();
	            }
	        }
			/*******Remove Duplicates in Operation Time List*********************************************/
			Set set = new HashSet();
			 List newList = new ArrayList();
			 for (Iterator iter = operationTimeList.iterator();    iter.hasNext(); ) {
			 Object element = iter.next();
			   if (set.add(element))
			      newList.add(element);
			    }
			 operationTimeList.clear();
			 operationTimeList.addAll(newList);
			 /*******END:Remove Duplicates in Operation Time List*********************************************/
			
			
		}
			
		//******************************END Operating Time List************************************//*
			
		//******************************Approvers Time List***************************************//*
			// Fetch the location ID of the new Location that will be inserted to map the approver ids to the location
			JSONObject approverArray=new JSONObject();
			
			if(jsonarray.has("BuildApproverJSON")){	
				Map<String,Object> approverMap=new HashMap<String, Object>();
				approverArray=new JSONObject(jsonarray.getString("BuildApproverJSON"));
				Iterator<?> apprKeys = approverArray.keys();
				while( apprKeys.hasNext() ){
					 String keysName = (String)apprKeys.next();
					 approverMap.put("APPROVER_ID", 2);
					 approverMap.put("EMPLOYEE_ID", approverArray.get(""+keysName));
					 approverList.add(approverMap);
					 approverMap=new HashMap<String, Object>();
				}				
			}
			
		//******************************END Approvers Time List************************************//*
		
			
	/***********************************Insertion of Admin Console:Location Detail**************************************************/		
		int locnIDe=groupSettingsObj.addLocationMasterDetail(countryName, cityName, null, "1", loginID, createdDate);
		int locnDetIde=0;	
		String slaID=null;
		
	/***********************************Insert to IC_IHD_LOCATION_DETAILS if Function is IT*****************************************/
		if(functionID.equalsIgnoreCase("256")){
			 locnDetIde=groupSettingsObj.insertLocDetailAdminConsoleLocAddIT(buildingList,locnIDe,functionID,loginID,createdDate);
		}
	/***********************************END : Insert to IC_IHD_LOCATION_DETAILS*****************************************************/	
		
	/***********************************Check if Location is inserted.**************************************************************/	
		if(locnIDe!=0){	
			if(operationTimeList.size()>0){
	/***********************************Check if Sla ID exists for the inserted Operation Time.If yes, insert the operation time det*************************************/
				slaID=commonDataForCacheObj.getSLAForOperatingTime(operationTimeList);						
				//slaID=groupSettingsObj.insertSLAMasterAdminConsoleLocAdd(slaID,operationTimeList, cityName, loginID, createdDate,locnIDe,functionID,locnDetIde);
	/***********************************END :Check if Sla ID exists for the inserted Operation Time.*************************************/
			}
		}
	/***********************************END:Check if Location is inserted.**************************************************************/	
		
	/***********************************If Function is IT ,insert into Approver Det table.*************************************/		
		if(functionID.equalsIgnoreCase("256")){
			groupSettingsObj.insertApproverEmpDet(approverList,locnIDe,loginID,createdDate);
		}
	/***********************************If Function is IT ,insert into Approver Det table.*************************************/
		
		
		if(slaID!=null){
			result.put("messsage", "Location saved successfully!!!");
		}else{
			result.put("error", "Location insertion failed!!!");
		}	
			
		/*Admin Console: Call reset cache and reset workflow cache method*/		
		resetCacheAndResetWorkflowMethod();
		/*Admin Console: Call reset cache and reset workflow cache method*/
		
	/***********************************Insertion of Admin Console:Location Detail**************************************************/	
				JsonUtility.sendData(result, response);
	}
	/**
	 * Admin Console:Location Addition: Call reset cache and reset workflow cache method
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ConnectException 
	 * @throws WebServiceIOException 
	 */
	public  void resetCacheAndResetWorkflowMethod() throws SecurityException, NoSuchMethodException, ConnectException{
		
		 ApplicationContext ctx = COMMON_AppContext.getCtx();

			/*try {

				HDCacheInvokeClient79DAO clientFor79 = (HDCacheInvokeClient79DAO) ctx
						.getBean("clientFrom79Server");
				 clientFor79
						.getResponseFlag("InvokeMasterCache");
			} catch (ConnectException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (WebServiceIOException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (SocketException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (IOException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (JSONException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (SOAPException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (SecurityException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (NoSuchMethodException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (IllegalArgumentException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (IllegalAccessException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (InvocationTargetException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			} catch (NoSuchElementException e) {
				
				log.error("Error Encountered while doing reset cache in 79 server",
						e);
			}

			try {
				HDCacheInvokeClient77DAO clientFor77 = (HDCacheInvokeClient77DAO) ctx
				.getBean("clientFrom77Server");
				clientFor77
						.getResponseFlag("InvokeMasterCache");
			} catch (ConnectException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (WebServiceIOException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (SocketException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (IOException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (JSONException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (SOAPException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (SecurityException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (NoSuchMethodException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (IllegalArgumentException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (IllegalAccessException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (InvocationTargetException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			} catch (NoSuchElementException e) {
				
				log.error("Error Encountered while doing reset cache in 77 server",
						e);
			}


			try {
				HDCacheInvokeClientODCDAO clientForODC = (HDCacheInvokeClientODCDAO) ctx
				.getBean("clientFromODCServer");
				clientForODC.getResponseFlag("InvokeMasterCache");
			} catch (ConnectException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (WebServiceIOException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (SocketException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (IOException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (JSONException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (SOAPException e) {
				
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (SecurityException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (NoSuchMethodException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (IllegalArgumentException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (IllegalAccessException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (InvocationTargetException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			} catch (NoSuchElementException e) {
				log.error(
						"Error Encountered while doing reset cache in ODC server",
						e);
			}*/
		 COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
         .getBean("GetMasterData");

	     List<String> resetCacheObj = new ArrayList<String>();
	     Class c = commonDataForCacheObj.getClass();
	     Method allMethodsArr[] = c.getDeclaredMethods();	
	     for (Method methodMp : allMethodsArr) {
	         if (methodMp.getName().startsWith("reset")) {
	             resetCacheObj.add(methodMp.getName());
	         }
	     }
			
	     boolean result = false;
	     for (Iterator<String> iterator = resetCacheObj.iterator(); iterator.hasNext();) {
				String methodName = (String) iterator.next();
				Method invokeMethod = c.getMethod(methodName);
				try {
					result = (Boolean) invokeMethod.invoke(commonDataForCacheObj);
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				} catch (IllegalAccessException e) {				
					e.printStackTrace();
				} catch (InvocationTargetException e) {				
					e.printStackTrace();
				}
			}
      
	}
	
	
	/* @RequestMapping(value = "ADMIN_AddLocationGrpList.htm", method = RequestMethod.GET)
	    public  ModelAndView getGroupListJmesa(ModelMap model, HttpServletRequest request,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
			.getBean("GetMasterData");
			String userLoginId = (String) request.getSession().getAttribute(
			"userLoginId");
			String loogedinUserID = userLoginId;
			User userBean = (User) request.getSession().getAttribute(
					userLoginId);

	        
	        final List<Map<String, Object>> grpList =null; 
			// Defaulting userTime Zone to IST
			int userTimeZone = 67;
			if (userBean.getTimeZoneId() != null)
				userTimeZone = Integer.parseInt(userBean.getTimeZoneId());
			final int userTimeZoneToSend = userTimeZone;
			String roleId = userBean.getUserRoleId();
			String roleName = userBean.getUserRole();
			String query = "";
			final String queryToPass = query;
			
			String menuName="Group_Detail";
			TableModel tableModel = new TableModel(menuName, request, response);
			tableModel.setEditable(true);
			
			
			tableModel.saveWorksheet(new WorksheetSaver() {
	            public void saveWorksheet(Worksheet worksheet) {
	                //saveWorksheetChanges(worksheet);
	            }				
	        });

			int pageNo = 1;
			int maxRows = 15;
			boolean firstTime = false;
			try {
				pageNo = Integer.parseInt(request.getParameter(menuName + "_p_"));
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				firstTime = true;
			}
			catch(NullPointerException npe){
				firstTime = true;
			}
			try {
				maxRows = Integer.parseInt(request.getParameter("maxRows"));
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				firstTime = true;
			}
			catch(NullPointerException npe){
				firstTime = true;
			}

			int startCount = ((pageNo - 1) * maxRows) + 1;

			if (firstTime || pageNo == 1) {
				startCount = 1;
				// maxRows = 15;
			} else {
				maxRows = (startCount + maxRows) - 1;
			}
TODO:
			if (menuID == 0) {
				startCount = 1;
				maxRows = 15;
}
			tableModel.setStateAttr("restore");
			// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
			final int startCountToPass = startCount;
			final int pageNoToPass = pageNo;
			final int maxRowsToPass = 100;//maxRows;//TODO
			
			
			tableModel.setItems(new PageItems() {
				COMMON_Pagination<HELPDESK_Group> page = new COMMON_Pagination<HELPDESK_Group>();

				public int getTotalRows(Limit limit) {
					page = getFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, queryToPass,
							userTimeZoneToSend,grpList);
					return page.getTotalCount();
				}

				public Collection<HELPDESK_Group> getItems(Limit limit) {
					return page.getPageItems();
				}
			});
			//TODO
			String menuId="";
			String headerMenuName="";
			tableModel.setTable(getHtmlTable(menuId,menuName,headerMenuName));
			String view = tableModel.render();
			if (view == null) {
				//return null; // an export
				view=null;
			} else {
				// Setting a parameter to signal that this is an Ajax request.
				String ajax = request.getParameter("ajax");
				if (ajax != null && ajax.equals("true")) {
					byte[] contents = view.getBytes();
					try {
						response.getOutputStream().write(contents);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//return null;
					view=null;
				} else { // Not using Ajax if invoke the controller for the first
					// time.
					request.setAttribute("paginationData", view); // Set the Html in
					// the
					// request for the
					// JSP.
				}
			}
			
			request.setAttribute("paginationData", view);			
			return new ModelAndView("ADMIN_AddLocationGrpList");
			
	 }*/
	 
	/* public COMMON_Pagination<HELPDESK_Group> getFilteredResult(Limit limit,
				int startCount, int pageNo, int maxRows, String dynamicQuery,int userTimeZoneToSend,List<Map<String, Object>> grpList) {

			COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
			FilterSet filterSet = limit.getFilterSet();
			Collection<Filter> filters = filterSet.getFilters();
			for (Filter filter : filters) {
				String property = filter.getProperty();
				String value = filter.getValue();
				listPageSearchFilter.addFilter(property, value);

			}
			StringBuffer buffer = listPageSearchFilter.execute("AdminConsoleGrpLocation");
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
					.getBean("listDao");		      
			
			return listDAO.getGrpList_AC(startCount, pageNo, maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend, grpList);

		}
	 */
	 
	 public COMMON_Pagination<HELPDESK_Category> getFilteredResultCategory(Limit limit,
				int startCount, int pageNo, int maxRows, String dynamicQuery,int userTimeZoneToSend,String function_id,String category_name,String status_val) {

			COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
			FilterSet filterSet = limit.getFilterSet();
			Collection<Filter> filters = filterSet.getFilters();
			for (Filter filter : filters) {
				String property = filter.getProperty();
				String value = filter.getValue();
				listPageSearchFilter.addFilter(property, value);

			}
			StringBuffer buffer = listPageSearchFilter.execute("AdminConsoleCategory");
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
					.getBean("listDao");
			      
			
			return listDAO.getCategoryList(startCount, pageNo, maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend, function_id,category_name,status_val);

		}
	 
	
	private Table getHtmlTable(final String menuId, String menuName,
				String headerMenuName) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException  { 
		 
		 HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		 htmlTable.setStyleClass("table");
		 HtmlRow htmlRow = new HtmlRow();
		 htmlRow = new HtmlRow().uniqueProperty("id");
		 htmlTable.setRow(htmlRow);
		 
		 HtmlColumn categoryNameCol=new HtmlColumn("CATEGORY_NAME").title("Category Name");
		 htmlRow.addColumn(categoryNameCol);
		 
		 HtmlColumn subCategoryNameCol=new HtmlColumn("SUB_CATEGORY_NAME").title("Subcategory Name");
		 htmlRow.addColumn(subCategoryNameCol);	 
		 
		 final HtmlColumn groupNameCol=new HtmlColumn("GROUP_MAP").title("Group Name");
		 groupNameCol.setCellEditor(new CellEditor() {
	
			public Object getValue(Object item, String property, int rowcount) {
				
				Object groupMap = new HtmlCellEditor().getValue(item,
						"GROUP_MAP", rowcount);	
				Object SUB_CATEGORY_NAME = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_NAME", rowcount);	
				
				String[] grpList=groupMap.toString().split(",");
				HtmlBuilder htmlBuilder=new HtmlBuilder();
				String grpName=null;
				String grpID=null;
				if(grpList.length>0){
					htmlBuilder.select().id("grpIDJ_"+rowcount).styleClass("myTextInputForSelectAdmin").append(">");
					for(int i=0;i<grpList.length;i+=2){					
						String grp=grpList[i].toString();
						String grpNext=grpList[i+1].toString();
						grpID=grp.replace("{GROUP_ID=", "").replace("GROUP_NAME=", "").replace("[","").trim();
						grpName=grpNext.replace("GROUP_NAME=", "").replace("}", "").replace("{GROUP_ID=", "").replace("]","").trim();
						htmlBuilder.option().value(grpID).append(">").append(grpName).optionEnd();
						grpName=null;
						grpID=null;
					}
					htmlBuilder.selectEnd();
				}
				
				return htmlBuilder;
			}
		});

		 htmlRow.addColumn(groupNameCol);	 
 
		 HtmlColumn checkboxCol=new HtmlColumn("CHECKBOX").title("CheckBox");
		 HtmlBuilder html = new HtmlBuilder();
		 
		// html.input().type("checkbox").id("checkbox"+rowcount).value("").end();
		
		 
		 checkboxCol.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object subCatID = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_ID", rowcount);
				Object categoryID=new HtmlCellEditor().getValue(item,
						"CATEGORY_ID", rowcount);
				Object grpID=new HtmlCellEditor().getValue(item,
						"GROUP_ID", rowcount);
				
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("checkbox").id("checkboxJ"+rowcount)
				.value(String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)).onclick("javascript:editCheckBoxAC("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+")").end();
				return html;
			}
		});
		 htmlRow.addColumn(checkboxCol);	 
		 
		 HtmlColumn viewOREdit = new HtmlColumn("ACTION_ON_GROUP")
			.title("View/Edit");
		 
		 viewOREdit.setCellEditor(new CellEditor() {			
				public Object getValue(Object item, String property, int rowcount) {				
					HtmlBuilder htmlViewOrEdit = new HtmlBuilder();
					Object subCatID = new HtmlCellEditor().getValue(item,
							"SUB_CATEGORY_ID", rowcount);
					Object categoryID=new HtmlCellEditor().getValue(item,
							"CATEGORY_ID", rowcount);
					Object grpID=new HtmlCellEditor().getValue(item,
							"GROUP_ID", rowcount);
									 
					htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:editGrp("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+")").close();		
					 htmlViewOrEdit.append("Edit");
					 htmlViewOrEdit.aEnd();
					 htmlViewOrEdit.append(" | ");
					 htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:undoGrp("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+")").close();
					 htmlViewOrEdit.append("Undo");
					 htmlViewOrEdit.aEnd();
					 htmlViewOrEdit.append(" | ");
					return htmlViewOrEdit;
			}
		});
		 htmlRow.addColumn(viewOREdit);	 
		 

		 return htmlTable;
	 }

	
	 @RequestMapping(value="ADMIN_AddLocation.htm",method = RequestMethod.GET)
	    public String addLocation(ModelMap model){
	    	ApplicationContext ctx=COMMON_AppContext.getCtx();
	    	COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");
	    	List<Map<String,Object>> functionList=commonDataForCacheObj.getCategoriesById("PARENT_ID",0);
	    	  List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();          
	          for(Map<String, Object> functionMap:functionList){        	
	               if(!functionMap.get("NAME").equals("Function Correction Required")){
	              	 funcnList.add(functionMap);
	               }
	          }   
	    	model.put("functionList", funcnList);    	
	    	return "ADMIN_AddLocation";
		}
	
	
	   @RequestMapping(value = "getGroupsForSubCategoryAC.htm", method = RequestMethod.GET)
	    public @ResponseBody
	    void getGroupsForSubCategoryAC(@RequestParam String subCategoryID,String startRow,String endRow,
	            HttpServletResponse response, HttpServletRequest request) {
		   ApplicationContext ctx=COMMON_AppContext.getCtx();
		   COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");		   
		   List<Map<String, Object>> grpSubCategoryList = commonDataForCacheObj.getGroupListDefAssignmentSubCat(subCategoryID);
		   JsonUtility.sendData(grpSubCategoryList, response);
	   }
	   	@RequestMapping(value = "createGroup.htm", method = RequestMethod.GET)
	public @ResponseBody
	void createGroup(@RequestParam String json,String jsonOpr,String jsonMem, HttpServletResponse response,
			HttpServletRequest request) throws JSONException, SecurityException, NoSuchMethodException,ConnectException {

		JSONObject jsonObj=new JSONObject(json);
		JSONArray jsonObjOpr = new JSONArray(jsonOpr);
		JSONArray jsonObjMem = new JSONArray(jsonMem);

		String loginID = request.getSession().getAttribute("userLoginId")
		.toString();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx.getBean("GetMasterData");
		
		int status= 0;
		String groupId = groupSettingsObj.createGroup(jsonObj,loginID);
		int slaId = 0;

		if(!groupId.equalsIgnoreCase("0")){
			status=1;
			slaId = commonDataForCacheObj.getSLAOperatingTime(jsonObjOpr,jsonObj.get("GROUP_NAME").toString(),
					loginID,Integer.parseInt(jsonObj.get("LOCATION_ID").toString()));
			status = groupSettingsObj.groupFunctionMapping(groupId,jsonObj,slaId,loginID);
			
			if(status !=0){
				
				status=groupSettingsObj.groupMemberDetails(groupId, jsonObjMem, loginID);
				for(int i=0; i<jsonObjMem.length(); i++){
					JSONArray subMemArray = (JSONArray) jsonObjMem.get(i);
					groupSettingsObj.addUserRoleDetails(subMemArray.get(1).toString(), subMemArray.get(0).toString(), loginID);
				}
			}
			
		}else{
			status=0;
		}

		resetCacheAndResetWorkflowMethod();
		JsonUtility.sendData(status, response);
	}
	
	@RequestMapping(value = "getGroupListForFunction.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getGroupListForFunction(String functionId,String memberId,String status,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
        .getBean("GetgroupSettings");
		String loginID = request.getSession().getAttribute("userLoginId").toString();
		User userBean = (User) request.getSession().getAttribute(
				loginID);  
		String roleName=userBean.getUserRole();
		List<Map<String, Object>> groupDetailsList = groupSettingsObj
		.getGroupDetailsForFunction(functionId,memberId,status,loginID,roleName);
		JsonUtility.sendData(groupDetailsList, response);
	}
	@RequestMapping(value = "updateGroupList.htm", method = RequestMethod.GET)
	public @ResponseBody
	void modifyGroup(@RequestParam String json,String oldjson, HttpServletResponse response,
			HttpServletRequest request) throws JSONException, SecurityException, NoSuchMethodException, ConnectException {

		JSONArray jsonArrayObj = new JSONArray(json);
		JSONArray oldjsonArrayObj = new JSONArray(oldjson);

		String loginID = request.getSession().getAttribute("userLoginId").toString();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		
		String status = groupSettingsObj.modifyGroup(jsonArrayObj,oldjsonArrayObj,loginID);
		
		resetCacheAndResetWorkflowMethod();
		
		JsonUtility.sendData(status, response);
	}
	
	
    @RequestMapping(value = "getGroupNameDetailsList.htm", method = RequestMethod.GET)
    public @ResponseBody
    void getGroupNameBasedFunction(@RequestParam String functionId,
            HttpServletResponse response, HttpServletRequest request) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> group = commonDataForCacheObj
                .getAllGroupNamesForFunction(Integer.parseInt(functionId));
        List<String> groupNameList = new ArrayList<String>();
        for (Map<String, Object> groupListInfo : group) {
            groupNameList.add(groupListInfo.get("GROUP_NAME").toString() + ","
                    + groupListInfo.get("GROUP_ID").toString());
        }
        Set<String> groupName = new HashSet<String>(groupNameList);
        List<String> groupNameListSorting = new ArrayList<String>(groupName);
        Collections.sort(groupNameListSorting);
        JsonUtility.sendData(groupNameListSorting, response);
    }
	
    @RequestMapping(value = "adminConsole_getCategoryList.htm", method = RequestMethod.GET)
    public ModelAndView getCategory(@RequestParam String functionId,String categoryName,String statusVal,
            HttpServletResponse response, HttpServletRequest request) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
    	ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean("GetMasterData");
		String userLoginId = (String) request.getSession().getAttribute(
		"userLoginId");
		
		final String function_id=functionId;
		final String category_name=categoryName;
		final String status_val=statusVal;
		
		
		String loogedinUserID = userLoginId;
		User userBean = (User) request.getSession().getAttribute(
				userLoginId);        
    
		// Defaulting userTime Zone to IST
		int userTimeZone = 67;
		if (userBean.getTimeZoneId() != null)
			userTimeZone = Integer.parseInt(userBean.getTimeZoneId());
		final int userTimeZoneToSend = userTimeZone;
		String roleId = userBean.getUserRoleId();
		String roleName = userBean.getUserRole();
		String query = "";
		final String queryToPass = query;
		
		String menuName="Category_Detail";
		TableModel tableModel = new TableModel(menuName, request, response);

		int pageNo = 1;
		int maxRows = 15;
		boolean firstTime = false;
		try {
			pageNo = Integer.parseInt(request.getParameter(menuName + "_p_"));
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			firstTime = true;
		}
		catch(NullPointerException npe){
			firstTime = true;
		}
		try {
			maxRows = Integer.parseInt(request.getParameter(menuName + "_mr_"));
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			firstTime = true;
		}
		catch(NullPointerException npe){
			firstTime = true;
		}

		int startCount = ((pageNo - 1) * maxRows) + 1;

		if (firstTime && pageNo == 1) {
			startCount = 1;
			 maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;//maxRows;//TODO
		
		
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<HELPDESK_Category> page = new COMMON_Pagination<HELPDESK_Category>();

			public int getTotalRows(Limit limit) {
				page = getFilteredResultCategory(limit, startCountToPass,
						pageNoToPass, maxRowsToPass, queryToPass,
						userTimeZoneToSend,function_id,category_name,status_val);
				return page.getTotalCount();
			}

			public Collection<HELPDESK_Category> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		//TODO
		String menuId="";
		String headerMenuName="";
		tableModel.setTable(getHtmlTableCategory(menuId,menuName,headerMenuName));
		String view = tableModel.render();
		if (view == null) {
			//return null; // an export
			view=null;
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//return null;
				view=null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationData", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		
		request.setAttribute("paginationData", view);			
		return new ModelAndView("ADMIN_ViewCategoryList");

    }
    
    
    private Table getHtmlTableCategory(final String menuId, String menuName,
			String headerMenuName) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException  { 
    	HtmlTable htmlTable=new HtmlTable().caption(menuName);
   	 htmlTable.setStyleClass("table");
		 HtmlRow htmlRow = new HtmlRow();
		 htmlRow = new HtmlRow().uniqueProperty("id");
		 htmlTable.setRow(htmlRow);
		 
		 HtmlColumn checkboxCol=new HtmlColumn("CHECKBOX").title("Checkbox");			 
		 checkboxCol.setCellEditor(new CellEditor() {
				public Object getValue(Object item, String property, int rowcount) {
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;
					HtmlBuilder html = new HtmlBuilder();
					html.input().type("checkbox").id("checkbox"+finalRowID)
					.value("").onclick("editCategoryRow(\'"+finalRowID+"\')").end();
					return html;
				}
			});
			 htmlRow.addColumn(checkboxCol);
		 HtmlColumn categoryNameCol=new HtmlColumn("CATEGORY_NAME").title("Category Name");
		 categoryNameCol.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {	
				HtmlBuilder html = new HtmlBuilder();				
				Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
						"START_COUNT_PAGE", rowcount);
				Object CATEGORY_NAME=new HtmlCellEditor().getValue(item,
						"CATEGORY_NAME", rowcount);
				Object CATEGORY_ID=new HtmlCellEditor().getValue(item,
						"CATEGORY_ID", rowcount);
				int rowid=Integer.parseInt((String)START_COUNT_PAGE);
				int finalRowID=rowid+rowcount-1;
				String catName="";
				String catID="";
				if(catName!=null){
					catName=CATEGORY_NAME.toString();
					catID=CATEGORY_ID.toString();
				}
				html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'CATEGORY_NAME"+finalRowID+"\')").styleClass("borderlessTextInput").size("30%").id("CATEGORY_NAME"+finalRowID).name("CATEGORY_NAME"+finalRowID).value(catName).end();
				html.append("<span class=\"red_text\" id=\"span_CATEGORY_NAME"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
				html.append("<input type=\'hidden\' id=\'CATEGORY_ID"+finalRowID+"\' value=\'"+catID+"\'></input>");
				return html;
			}
		});		 
		 htmlRow.addColumn(categoryNameCol);
		 
		 
		 HtmlColumn categoryStatusCol=new HtmlColumn("CATEGORY_STATUS").title("Category Status");
		 categoryStatusCol.setFilterable(false);
		 categoryStatusCol.setCellEditor(new CellEditor() {			
		
			public Object getValue(Object item, String property, int rowcount) {
				HtmlBuilder html = new HtmlBuilder();				
				Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
						"START_COUNT_PAGE", rowcount);
				Object CATEGORY_STATUS=new HtmlCellEditor().getValue(item,
						"CATEGORY_STATUS", rowcount);
				int rowid=Integer.parseInt((String)START_COUNT_PAGE);
				int finalRowID=rowid+rowcount-1;		
				String catStatus="";
				if(catStatus!=null){
					catStatus=CATEGORY_STATUS.toString();
				}
				html.input().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'CATEGORY_STATUS"+finalRowID+"\')").styleClass("borderlessTextInput").id("CATEGORY_STATUS"+finalRowID).name("CATEGORY_STATUS"+finalRowID).value(catStatus).readonly().end();				
				return html;
			}
		});
		 htmlRow.addColumn(categoryStatusCol);
		 
		 HtmlColumn subCategorynameCol=new HtmlColumn("SUBCATEGORY_NAME").title("Sub Category Name");
		 subCategorynameCol.setCellEditor(new CellEditor() {			
				
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object SUBCATEGORY_NAME=new HtmlCellEditor().getValue(item,
							"SUBCATEGORY_NAME", rowcount);
					Object SUBCATEGORY_ID=new HtmlCellEditor().getValue(item,
							"SUBCATEGORY_ID", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String subCat="";
					String subCatID="";
					if(subCat!=null){
						if(SUBCATEGORY_NAME!=null){
						subCat=SUBCATEGORY_NAME.toString();
						subCatID=SUBCATEGORY_ID.toString();
						}
						
						
					}
					html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'SUBCATEGORY_NAME"+finalRowID+"\')").styleClass("borderlessTextInput").id("SUBCATEGORY_NAME"+finalRowID).value((subCat.toString())).end();		
					html.append("<span class=\"red_text\" id=\"span_SUBCATEGORY_NAME"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
					html.append("<input type=\'hidden\' id=\'SUBCATEGORY_ID"+finalRowID+"\' value=\'"+SUBCATEGORY_ID+"\'></input>");
					return html;
				}
			});
		 htmlRow.addColumn(subCategorynameCol);
		 
		 HtmlColumn subCategoryStatusCol=new HtmlColumn("SUBCATEGORY_STATUS").title("Sub Category Status");
		 subCategoryStatusCol.setFilterable(false);
		 subCategoryStatusCol.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object SUBCATEGORY_STATUS=new HtmlCellEditor().getValue(item,
							"SUBCATEGORY_STATUS", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String subCatStatus="";
					if(subCatStatus!=null){
						subCatStatus=SUBCATEGORY_STATUS.toString();
					}
					html.input().readonly().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'SUBCATEGORY_STATUS"+finalRowID+"\')").styleClass("borderlessTextInput").id("SUBCATEGORY_STATUS"+finalRowID).value(subCatStatus).end();		
					return html;
				}
			});
		 htmlRow.addColumn(subCategoryStatusCol);
		 
		 HtmlColumn isChangeReqCol=new HtmlColumn("IS_CHANGE_REQUEST").title("Is Change Request");
		 isChangeReqCol.setFilterable(false);
		 isChangeReqCol.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object IS_CHANGE_REQUEST=new HtmlCellEditor().getValue(item,
							"IS_CHANGE_REQUEST", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;
					String isChangeReq="";
					if(isChangeReq!=null){
						isChangeReq=IS_CHANGE_REQUEST.toString();
					}
					html.input().readonly().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'IS_CHANGE_REQUEST"+finalRowID+"\')").styleClass("borderlessTextInput").id("IS_CHANGE_REQUEST"+finalRowID).value(isChangeReq).end();		
					return html;
				}
			});
		 htmlRow.addColumn(isChangeReqCol);
		 
		 HtmlColumn recommendedPriotyCol=new HtmlColumn("RECOMMENDED_PRIORITY").title("Recommended Priority");
		 recommendedPriotyCol.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object RECOMMENDED_PRIORITY=new HtmlCellEditor().getValue(item,
							"RECOMMENDED_PRIORITY", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String recommendedPrty="";
					if(RECOMMENDED_PRIORITY!=null){
						recommendedPrty=RECOMMENDED_PRIORITY.toString();
					}
					html.input().readonly().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'RECOMMENDED_PRIORITY"+finalRowID+"\')").styleClass("borderlessTextInput").id("RECOMMENDED_PRIORITY"+finalRowID).value(recommendedPrty).end();		
					return html;
				}
			});
		 htmlRow.addColumn(recommendedPriotyCol);
		 
		 HtmlColumn approverLevel1=new HtmlColumn("APPROVER_LEVEL_1").title("Approver Level 1");
		 approverLevel1.setFilterable(false);
		 approverLevel1.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object APPROVER_LEVEL_1=new HtmlCellEditor().getValue(item,
							"APPROVER_LEVEL_1", rowcount);
					Object SUBCATEGORY_ID=new HtmlCellEditor().getValue(item,
							"SUBCATEGORY_ID", rowcount);
					Object IS_CHANGE_REQUEST=new HtmlCellEditor().getValue(item,
							"IS_CHANGE_REQUEST", rowcount);
					String isChangeReq="";
					if(isChangeReq!=null){
						isChangeReq=IS_CHANGE_REQUEST.toString();
					}
					
					String subCatID="";
					if(subCatID!=null){		
						if(SUBCATEGORY_ID!=null){
							subCatID=SUBCATEGORY_ID.toString();
						}
					}
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String approverLevel="";
					int isActive=0;
					if(APPROVER_LEVEL_1!=null){
						if(isChangeReq.equalsIgnoreCase("Yes")){
							approverLevel=APPROVER_LEVEL_1.toString();
							isActive=1;				
						}
					}
						html.input().readonly().onchange("javascript:changeJSONAppArr(\'"+finalRowID+"\',\'APPROVER_LEVEL_1_"+finalRowID+"\',\'APPROVER_LEVEL_STATUS_1_"+finalRowID+"\')").styleClass("borderlessTextInput").id("APPROVER_LEVEL_1_"+finalRowID).value(approverLevel).end();
						html.append("<input type=\'hidden\' id=\'APPROVER_LEVEL_STATUS_1_"+finalRowID+"\' value=\'"+isActive+"\'></input>");
						html.append("<span class=\"red_text\" id=\"span_APPROVER_LEVEL_1_"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
						
										
					return html;
				}
			});
		 htmlRow.addColumn(approverLevel1);
		 
		 HtmlColumn approverLevel2=new HtmlColumn("APPROVER_LEVEL_2").title("Approver Level 2");
		 approverLevel2.setFilterable(false);
		 approverLevel2.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object APPROVER_LEVEL_2=new HtmlCellEditor().getValue(item,
							"APPROVER_LEVEL_2", rowcount);
					Object IS_CHANGE_REQUEST=new HtmlCellEditor().getValue(item,
							"IS_CHANGE_REQUEST", rowcount);
					String isChangeReq="";
					if(isChangeReq!=null){
						isChangeReq=IS_CHANGE_REQUEST.toString();
					}
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String approverLevel="";
					int isActive=0;
					if(APPROVER_LEVEL_2!=null){
						if(isChangeReq.equalsIgnoreCase("Yes")){
						approverLevel=APPROVER_LEVEL_2.toString();					
						isActive=1;
						}
					}
						html.input().readonly().onchange("javascript:changeJSONAppArr(\'"+finalRowID+"\',\'APPROVER_LEVEL_2_"+finalRowID+"\',\'APPROVER_LEVEL_STATUS_2_"+finalRowID+"\')").styleClass("borderlessTextInput").id("APPROVER_LEVEL_2_"+finalRowID).value(approverLevel).end();	
						html.append("<input type=\'hidden\' id=\'APPROVER_LEVEL_STATUS_2_"+finalRowID+"\' value=\'"+isActive+"\'></input>");
						html.append("<span class=\"red_text\" id=\"span_APPROVER_LEVEL_2_"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
					
					return html;
				}
			});
		 htmlRow.addColumn(approverLevel2);
		 
		 HtmlColumn approverLevel3=new HtmlColumn("APPROVER_LEVEL_3").title("Approver Level 3");
		 approverLevel3.setFilterable(false);
		 approverLevel3.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object APPROVER_LEVEL_3=new HtmlCellEditor().getValue(item,
							"APPROVER_LEVEL_3", rowcount);
					Object IS_CHANGE_REQUEST=new HtmlCellEditor().getValue(item,
							"IS_CHANGE_REQUEST", rowcount);
					String isChangeReq="";
					if(isChangeReq!=null){
						isChangeReq=IS_CHANGE_REQUEST.toString();
					}
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;		
					String approverLevel="";
					int isActive=0;
					if(APPROVER_LEVEL_3!=null){
						if(isChangeReq.equalsIgnoreCase("Yes")){
							approverLevel=APPROVER_LEVEL_3.toString();
							isActive=1;
						}
					}
						html.input().readonly().onchange("javascript:changeJSONAppArr(\'"+finalRowID+"\',\'APPROVER_LEVEL_3_"+finalRowID+"\',\'APPROVER_LEVEL_STATUS_3_"+finalRowID+"\')").styleClass("borderlessTextInput").id("APPROVER_LEVEL_3_"+finalRowID).value(approverLevel).end();
						html.append("<input type=\'hidden\' id=\'APPROVER_LEVEL_STATUS_3_"+finalRowID+"\' value=\'"+isActive+"\'></input>");
						html.append("<span class=\"red_text\" id=\"span_APPROVER_LEVEL_3_"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
									
					return html;
				}
			});
		 htmlRow.addColumn(approverLevel3);
		 
		 HtmlColumn modifyApprover=new HtmlColumn("MODIFY_APPROVER").title("Modify Approver");
		 modifyApprover.setFilterable(false);
		 modifyApprover.setCellEditor(new CellEditor() {
			
			public Object getValue(Object item, String property, int rowcount) {
				HtmlBuilder html = new HtmlBuilder();
				Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
						"START_COUNT_PAGE", rowcount);
				Object APPROVER_LEVEL_1=new HtmlCellEditor().getValue(item,
						"APPROVER_LEVEL_1", rowcount);
				Object APPROVER_LEVEL_2=new HtmlCellEditor().getValue(item,
						"APPROVER_LEVEL_2", rowcount);
				Object APPROVER_LEVEL_3=new HtmlCellEditor().getValue(item,
						"APPROVER_LEVEL_3", rowcount);
				String approverLevel1="";
				if(APPROVER_LEVEL_1!=null){
				approverLevel1=APPROVER_LEVEL_1.toString();
				}
				String approverLevel2="";
				if(APPROVER_LEVEL_2!=null){
				approverLevel2=APPROVER_LEVEL_2.toString();
				}
				String approverLevel3="";
				if(APPROVER_LEVEL_3!=null){
				approverLevel3=APPROVER_LEVEL_3.toString();
				}
				int rowid=Integer.parseInt((String)START_COUNT_PAGE);
				int finalRowID=rowid+rowcount-1;	
				html.input().type("button").id("MODIFY_APPROVER_"+finalRowID).style("display:none").styleClass("MODIFY_APPROVER_"+finalRowID).onclick("javascript:modifyApprovers(\'"+finalRowID+"\',\'APPROVER_LEVEL_1_"+finalRowID+"\',\'APPROVER_LEVEL_2_"+finalRowID+"\',\'APPROVER_LEVEL_3_"+finalRowID+"\')").value("Modify Approver").end();
				return html;
			}
		});
		 htmlRow.addColumn(modifyApprover);
		 //Added  by Sali
		 
		 HtmlColumn linkCol=new HtmlColumn("LINK").title("Doc Link");
		 linkCol.setCellEditor(new CellEditor() {						
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object LINK=new HtmlCellEditor().getValue(item,
							"LINK", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;	
					String linkString="";
					if(LINK!=null){
						linkString=LINK.toString();
					}
					
					//html.input().readonly().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'LINK"+finalRowID+"\')").styleClass("borderlessTextInput").id("LINK"+finalRowID).value(linkString).end();		
					
					html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'LINK"+finalRowID+"\')").styleClass("borderlessTextInput").id("LINK"+finalRowID).value((linkString.toString())).end();
					html.append("<input type=\'hidden\' id=\'LINK_ID"+finalRowID+"\' value=\'"+linkString+"\'></input>");
					return html;
				}
			});
		 htmlRow.addColumn(linkCol);
		 //EOF
		 
   	return htmlTable;
   }
    //adminConsole_getApproverMemberList
    @RequestMapping(value = "adminConsole_getApproverMemberList.htm", method = RequestMethod.GET)
    public @ResponseBody
    void adminConsole_getApproverMemberList(@RequestParam String statusValue,String approverName,String statusVal,String subcategoryID,
            HttpServletResponse response, HttpServletRequest request) {

        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
        .getBean("GetgroupSettings");
        List<Map<String, Object>>  approverEmpList=new ArrayList<Map<String,Object>>();
        approverEmpList= groupSettingsObj.getApproverEmployeeDetail(statusValue, approverName, statusVal, subcategoryID);

       JsonUtility.sendData(approverEmpList, response);

    }
    
    @RequestMapping(value = "/insertNewCategoryAC.htm", method = RequestMethod.POST)
	public @ResponseBody
	void insertNewCategoryAC(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
    	
    	
    	ApplicationContext ctx = COMMON_AppContext.getCtx();
        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
                .getBean("GetgroupSettings");
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
        .getBean("GetMasterData");	 
        
        String loginID = request.getSession().getAttribute("userLoginId")
        .toString();
        
	 	Map<String, String> result = new HashMap<String, String>();
	 	JSONObject jsonarray = new JSONObject(jsonString);
	 	 String functionId=jsonarray.getString("functionId");
	 	jsonarray=new JSONObject(jsonarray.getString("jsonString"));	 	
	
	 	jsonarray = new JSONObject(jsonarray.getString("category"));	
	 	
	 	
	 	JSONObject objGrp=new JSONObject();
	 	HELPDESK_Category categoryMap=new HELPDESK_Category();
	 	List<HELPDESK_Category> categoryMapList=new ArrayList<HELPDESK_Category>();
	 	Iterator<?> keys = jsonarray.keys();
	 	while( keys.hasNext() ){			
	 		 String key = (String)keys.next();	
	 		 if( jsonarray.get(key) instanceof JSONObject ){	            	
	            	objGrp=new JSONObject(jsonarray.getString(""+key));		            	
	            	categoryMap.setCATEGORY_NAME(objGrp.get("CATEGORY_NAME"+key).toString());
	            	categoryMap.setCATEGORY_ID(objGrp.get("CATEGORY_ID"+key).toString());
	            	categoryMap.setCATEGORY_STATUS(objGrp.get("CATEGORY_STATUS"+key).toString());
	            	categoryMap.setSUBCATEGORY_ID(objGrp.get("SUBCATEGORY_ID"+key).toString());
	            	categoryMap.setSUBCATEGORY_NAME(objGrp.get("SUBCATEGORY_NAME"+key).toString());
	            	categoryMap.setSUBCATEGORY_STATUS(objGrp.get("SUBCATEGORY_STATUS"+key).toString());
	            	categoryMap.setIS_CHANGE_REQUEST(objGrp.get("IS_CHANGE_REQUEST"+key).toString());
	            	categoryMap.setRECOMMENDED_PRIORITY(objGrp.get("RECOMMENDED_PRIORITY"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_1(objGrp.get("APPROVER_LEVEL_1_"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_2(objGrp.get("APPROVER_LEVEL_2_"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_3(objGrp.get("APPROVER_LEVEL_3_"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_STATUS_1(objGrp.get("APPROVER_LEVEL_STATUS_1_"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_STATUS_2(objGrp.get("APPROVER_LEVEL_STATUS_2_"+key).toString());
	            	categoryMap.setAPPROVER_LEVEL_STATUS_3(objGrp.get("APPROVER_LEVEL_STATUS_3_"+key).toString());
	            	categoryMap.setLINK(objGrp.get("LINK"+key).toString());//added by sali
	            	categoryMapList.add(categoryMap);
	            	categoryMap=new HELPDESK_Category();
	            }
	 	}
	 	
	 	//Check for Inactive category and sub category in the categoryMapList	 	
	 	 List<HELPDESK_Category> catListInactive = new ArrayList<HELPDESK_Category>();
	 	 List<HELPDESK_Category> subCatListInactive = new ArrayList<HELPDESK_Category>();
	 	List<HELPDESK_Category> catListActive = new ArrayList<HELPDESK_Category>();
	 	List<HELPDESK_Category> subCatListActive = new ArrayList<HELPDESK_Category>();
	 	 
	    /*    for (HELPDESK_Category catMapInfo : categoryMapList) {
	     	        
	      	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Active") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Active") ){
	        		catListActive.add(catMapInfo);
		         }else
	        	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Active") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Inactive") ){
	        		catListActive.add(catMapInfo);
	        		subCatListInactive.add(catMapInfo);
		         }else
	        	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Inactive") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Active") ){
	        		catListActive.add(catMapInfo);
	        		catListInactive.add(catMapInfo);
		         }else
	        	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Inactive") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Inactive") ){
	        		catListInactive.add(catMapInfo);
	        	subCatListInactive.add(catMapInfo);
		         }	
		        }*/
	 	
	 	for (HELPDESK_Category catMapInfo : categoryMapList) {
	 		if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Active") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Active") ){
	 			catListActive.add(catMapInfo);
	 		}else
	        	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Active") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Inactive") ){
	        		subCatListInactive.add(catMapInfo);
	        }else 
	        	if(catMapInfo.getCATEGORY_STATUS().equalsIgnoreCase("Inactive") && catMapInfo.getSUBCATEGORY_STATUS().equalsIgnoreCase("Active") ){
	        		catListInactive.add(catMapInfo);
	        }else{
	        	catListInactive.add(catMapInfo);
	        	subCatListInactive.add(catMapInfo);
	        }
	 	}
	 	     
	 
	            
	        //Determine categories which have no Open Tickets from the 2 lists:catListInactive and subCatListInactive
	        List<HELPDESK_Category> inactiveCategoryWithNoOpenTickets=new ArrayList<HELPDESK_Category>();
	        List<HELPDESK_Category> inactiveCategoryWithOpenTickets=new ArrayList<HELPDESK_Category>();
	        
	        for (Iterator<HELPDESK_Category> iterator = catListInactive.iterator(); iterator
					.hasNext();) {
				HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator
						.next();
				String categoryIDe=helpdeskCategory.getCATEGORY_ID();
				int countOfTickets=groupSettingsObj.countOfOpenTickets(categoryIDe);
				if(countOfTickets<=0){
					inactiveCategoryWithNoOpenTickets.add(helpdeskCategory);
				}else{
					inactiveCategoryWithOpenTickets.add(helpdeskCategory);
				}
			}
	        
	        List<HELPDESK_Category> inactiveSubCategoryWithNoOpenTickets=new ArrayList<HELPDESK_Category>();
	        List<HELPDESK_Category> inactiveSubCategoryWithOpenTickets=new ArrayList<HELPDESK_Category>();
	        
	        for (Iterator<HELPDESK_Category> iterator = subCatListInactive.iterator(); iterator
					.hasNext();) {
				HELPDESK_Category helpdeskCategory = (HELPDESK_Category) iterator
						.next();
				String subCategoryIDe=helpdeskCategory.getSUBCATEGORY_ID();
				int countOfTickets=groupSettingsObj.countOfOpenTicketsSubCategory(subCategoryIDe);
				if(countOfTickets<=0){
					inactiveSubCategoryWithNoOpenTickets.add(helpdeskCategory);
				}else{
					inactiveSubCategoryWithOpenTickets.add(helpdeskCategory);
				}
			}
	        
	        /** 
	         * Now we have 5 lists :
	         * 1.catListActive
	         * 2.inactiveCategoryWithNoOpenTickets
	         * 3.inactiveCategoryWithOpenTickets
	         * 4.inactiveSubCategoryWithNoOpenTickets
	         * 5.inactiveSubCategoryWithOpenTickets
	         */
	        
	        /**
	         * First Step: Take inactiveCategoryWithOpenTickets and inactiveSubCategoryWithOpenTickets and find how many open tickets are present 
	         */
	        List<Map<String, Object>> openTicketListCategory=new ArrayList<Map<String,Object>>();
	        List<Map<String, Object>> openTicketListSubCategory=new ArrayList<Map<String,Object>>();
	        List<Map<String, Object>> ticketListCategory=new ArrayList<Map<String,Object>>();	       
	        if(catListInactive.size()>0){
	        	openTicketListCategory=	groupSettingsObj.getOpenTicketsForCategoryAndSubCategory(inactiveCategoryWithOpenTickets,"category");
	        }
	        if(subCatListInactive.size()>0){
	        	openTicketListSubCategory=	groupSettingsObj.getOpenTicketsForCategoryAndSubCategory(inactiveSubCategoryWithOpenTickets,"subcategory");
	        }
	        
	        if(openTicketListCategory!=null){
	        	ticketListCategory.addAll(openTicketListCategory);
	        }
	        if(openTicketListSubCategory!=null){
	        	ticketListCategory.addAll(openTicketListSubCategory);
	        }
	      
	        /**
	         * Second Step: Take inactiveCategoryWithNoOpenTickets and inactiveSubCategoryWithNoOpenTickets and update the status as IS_ACTIVE=0 in
	         * required tables
	        */
	        if(inactiveCategoryWithNoOpenTickets.size()>0 || inactiveSubCategoryWithNoOpenTickets.size()>0){
	        groupSettingsObj.updateCategoryAsInactive(inactiveCategoryWithNoOpenTickets,inactiveSubCategoryWithNoOpenTickets,loginID);
	        }
	        
	        /**
	         * Third Step:Take catListActive: Check for Category Name duplication against parent category and Sub category Name 
	         * duplication against parent category
	         */
	        int isValidName=commonDataForCacheObj.isCatSubCatNameExistCategoryConsole(catListActive,functionId);
	        
	       
	        
	        /**
	         * Fourth Step:Take catListActive : Update all the data in the category data
	         */
	       String resultCat="";
	       
	       if(isValidName==1){
	    	   resultCat=groupSettingsObj.updateCategory(catListActive,loginID);
	       }else{
	    	   resultCat="invalidName"  ;
	       }
	       Map<String, Object> responsedetails = new HashMap<String, Object>();
	       responsedetails.put("message", resultCat);
	       responsedetails.put("responseList", ticketListCategory);
	       resetCacheAndResetWorkflowMethod();
	       JsonUtility.sendData(responsedetails, response);
	        
	}
    
    //Added for Admin role screen
    @RequestMapping(value = "updateRoleList.htm", method = RequestMethod.GET)
	public @ResponseBody
	void modifyRole(@RequestParam String json, HttpServletResponse response,
			HttpServletRequest request) throws JSONException, SecurityException, NoSuchMethodException,ConnectException {
    	JSONArray jsonArrayObj = new JSONArray(json);
		String loginID = request.getSession().getAttribute("userLoginId").toString();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO roleSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		String status = roleSettingsObj.updateRoleDetails(jsonArrayObj,loginID);
		resetCacheAndResetWorkflowMethod();
		JsonUtility.sendData(status, response);
	}
    
    @RequestMapping(value = "MemRoleForAddition.htm", method = RequestMethod.GET)
	   public @ResponseBody void getRoleDetails(@RequestParam String employeeId,HttpServletResponse response,
	            HttpServletRequest request) {
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
		  ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
          .getBean("GetgroupSettings");
		  List<Map<String, Object>> roleDetails=groupSettingsObj.getAddRoleDetails(employeeId);
		  JsonUtility.sendData(roleDetails, response);
	}
	
	@RequestMapping(value = "MemRoleForLocAddition.htm", method = RequestMethod.GET)
	   public @ResponseBody void getRoleLocDetails(@RequestParam String employeeId,HttpServletResponse response,
	            HttpServletRequest request) {
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
		  ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
       .getBean("GetgroupSettings");
		  List<Map<String, Object>> roleDetails=groupSettingsObj.getAddRoleLocDetails(employeeId);
		  JsonUtility.sendData(roleDetails, response);
	}
	
	@RequestMapping(value = "insertRoleLoc.htm", method = RequestMethod.GET)
	public @ResponseBody
	void addRoleLoc(@RequestParam String json,String locJsonobj,HttpServletResponse response,
			HttpServletRequest request) throws JSONException, SecurityException, NoSuchMethodException, ConnectException {
		JSONObject jsonArrayObj = new JSONObject(json);		
    	JSONObject locObj=new JSONObject(locJsonobj);
    	String loginID = request.getSession().getAttribute("userLoginId").toString();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO roleSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		String status = roleSettingsObj.insertRoleDetails(jsonArrayObj,locObj,loginID);
		resetCacheAndResetWorkflowMethod();
		JsonUtility.sendData(status, response);
	}
	
	@RequestMapping(value = "changeGroupMemberRole.htm", method = RequestMethod.GET)
    public @ResponseBody
    void changeGroupMemberDetails(@RequestParam String json,
            String groupMemID, HttpServletResponse response,
            HttpServletRequest request) throws JSONException, SecurityException, NoSuchMethodException, ConnectException  {
		JSONArray jsonArrayObj = new JSONArray(json);
		String loginID = request.getSession().getAttribute("userLoginId").toString();
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO roleSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		String status = roleSettingsObj.updateGroupRoleDetails(jsonArrayObj,loginID);
		resetCacheAndResetWorkflowMethod();
		JsonUtility.sendData(status, response);
    }
	
	
	@RequestMapping(value = "ADMIN_CreateUser.htm", method = RequestMethod.GET)
	public ModelAndView getadminupdatelocation(ModelMap model, HttpServletRequest request){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		//List<Map<String,Object>> userDetailsTableColumns=MasterDataImpl.getuserDetailsTableColumns();
		ADMIN_CreateUserBO admin_CreateUserBO=new ADMIN_CreateUserBO();
		//model.put("userDetailsTableColumns", userDetailsTableColumns);
		model.addAttribute("admincreateuserBean", admin_CreateUserBO);
		return new ModelAndView("ADMIN_CreateUser");
		}
	
	
    @RequestMapping(value = "getMasterData.htm", method = RequestMethod.GET)
	   public void getMasterData(HttpServletResponse response,
	            HttpServletRequest request) {
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
		  COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
					.getBean("GetMasterData");
		  List<Map<String, Object>> locationList=MasterDataImpl.getAllLoactionList();
		  List<Map<String, Object>> timeZoneList=MasterDataImpl.getAllTimeZoneList();
		  Map<String,Object> map =new HashMap<String, Object>();
		  map.put("locationList", locationList);
		  map.put("timeZoneList", timeZoneList);
		  JsonUtility.sendData(map, response);
	}
    
	@RequestMapping(value = "ADMIN_CreateUser.htm", method = RequestMethod.POST)
	public void createUserProfile(ADMIN_CreateUserBO aDMIN_CreateUserBO,
			HttpServletRequest request, HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO admin = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		String loginID = request.getSession().getAttribute("userLoginId").toString();
		String result=admin.createUserProfile(aDMIN_CreateUserBO,loginID);
		JSONObject jsonforSuccess = new JSONObject();
			try {
				jsonforSuccess.put("response", result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		JsonUtility.writedata(jsonforSuccess.toString(), response);
	}
	
	@RequestMapping(value = "ADMIN_AddLHProject.htm", method = RequestMethod.GET)
	public ModelAndView getLHProjectsPage(ModelMap model, HttpServletRequest request){
		return new ModelAndView("ADMIN_AddLHProject");
		}
	
	
	@RequestMapping(value = "getLHProjectList.htm", method = RequestMethod.POST)
	public void getLHProjectsToDisplay(ModelMap model, HttpServletRequest request,HttpServletResponse response){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO roleSettingsObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		List<Map<String,Object>> lhProjects=roleSettingsObj.getLHProjects();
		JsonUtility.sendData(lhProjects, response);
		}
	
	@RequestMapping(value = "addLHProject.htm", method = RequestMethod.POST)
	public void addLHProject(@RequestBody String projectId,
			HttpServletRequest request, HttpServletResponse response) {
		 try {
			JSONObject jsonarray = new JSONObject(projectId);
			 String projId=jsonarray.getString("projectId");
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			ADMIN_SettingsDAO admin = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
			String loginID = request.getSession().getAttribute("userLoginId").toString();
			String result=admin.insertLHProjectData(projId,loginID);
			JSONObject jsonforSuccess = new JSONObject();
					jsonforSuccess.put("response", result);
			JsonUtility.writedata(jsonforSuccess.toString(), response);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "getEmployeeProfileInfo.htm", method = RequestMethod.POST)
	public void getEmpProfileInfo(@RequestBody String empId,
			HttpServletRequest request, HttpServletResponse response) {
		 try {
			JSONObject jsonarray = new JSONObject(empId);
			 String employeeId=jsonarray.getString("empId");
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			ADMIN_SettingsDAO admin = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
			List<Map<String,Object>> empProfile=admin.getEmployeeProfInfo(employeeId);
			JsonUtility.sendData(empProfile, response);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
    @RequestMapping(value = "updateProjectList.htm", method = RequestMethod.POST)
	public @ResponseBody
	void modifyLHProjects(@RequestBody String json, HttpServletResponse response,
			HttpServletRequest request) {
    	try {
    		JSONObject jsonarray = new JSONObject(json);
			JSONArray jsonArrayObj = new JSONArray(jsonarray.getString("json"));
			String loginID = request.getSession().getAttribute("userLoginId").toString();
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			ADMIN_SettingsDAO adminObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
			String status = adminObj.updateProjectDetails(jsonArrayObj,loginID);
			JsonUtility.sendData(status, response);
		} catch (BeansException e) {
			e.printStackTrace();
		}  catch (SecurityException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}
    
    /**
     * Validate Employess for providing them roles through Admin console.L2: 2751
     * 
     */
	@RequestMapping(value = "/validateEmployeeForAdminConsole.htm", method = RequestMethod.GET)
	public void validateEmployeeForAdminConsole(@RequestParam String employeeId,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		ADMIN_SettingsDAO adminObj = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		Map<String, Object> employee = adminObj.getEmployeeDetailsForAdminConsole(String
				.valueOf(employeeId));
		JsonUtility.sendData(employee, response);
	}

	
}

/*-----------------------------------------------------------------------------
Log: 
Start-----Version 1.0-----
Changes Made:New File Created
Changes Made By:706638
Changes Made on:Mar 21, 2012
End-------Version 1.0-------
            
-----------------------------------------------------------------------------*/
