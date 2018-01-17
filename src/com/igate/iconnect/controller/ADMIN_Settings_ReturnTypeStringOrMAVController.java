/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.dao.ADMIN_SettingsDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class ADMIN_Settings_ReturnTypeStringOrMAVController {

	private static Logger log = Logger
	.getLogger(ADMIN_Settings_ReturnTypeStringOrMAVController.class);
	

	@ExceptionHandler
	public String handleExceptionsF(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "ERROR";

	}
	
    @RequestMapping(value = "ADMIN_ViewGroup.htm", method = RequestMethod.GET)
    public String showGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0); 
        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
       
        for(Map<String, Object> functionList:type){        	 
             if(!functionList.get("NAME").equals("Function Correction Required")){
            	 funcnList.add(functionList);
             }
        }     
        
        model.put("type", funcnList);
        return "ADMIN_ViewGroup";
    }

    @RequestMapping(value = "ADMIN_ChangeGroup.htm", method = RequestMethod.GET)
    public String changeGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0);
        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
        
        for(Map<String, Object> functionList:type){        	 
             if(!functionList.get("NAME").equals("Function Correction Required")){
            	 funcnList.add(functionList);
             }
        }     
        
        model.put("type", funcnList);
        return "ADMIN_ChangeGroup";
    }

    @RequestMapping(value = "ADMIN_AddGroup.htm", method = RequestMethod.GET)
    public String addGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0);
        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
        
        for(Map<String, Object> functionList:type){        	
             if(!functionList.get("NAME").equals("Function Correction Required")){
            	 funcnList.add(functionList);
             }
        }     
        
        model.put("type", funcnList);
        List<Map<String, Object>> role = commonDataForCacheObj.getRoles();
        model.put("role", role);
        return "ADMIN_AddGroup";
    }
    
    @RequestMapping(value = "ADMIN_CreateGroup.htm", method = RequestMethod.GET)
    public String createGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0); 
        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
       
        for(Map<String, Object> functionList:type){        	 
             if(!functionList.get("NAME").equals("Function Correction Required")){
            	 funcnList.add(functionList);
             }
        }     
        model.put("type", funcnList);

        return "ADMIN_CreateGroup";
    }
    
    @RequestMapping(value = "ADMIN_EditGroup.htm", method = RequestMethod.GET)
    public String modifyGroup(ModelMap model) {
        ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        List<Map<String, Object>> type = commonDataForCacheObj
                .getCategoriesById("PARENT_ID", 0); 
        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
       
        for(Map<String, Object> functionList:type){        	 
             if(!functionList.get("NAME").equals("Function Correction Required")){
            	 funcnList.add(functionList);
             }
        }     
        model.put("type", funcnList);

        return "ADMIN_EditGroup";
    }
	@RequestMapping(value = "ADMIN_EditGroupDetails.htm", method = RequestMethod.GET)
	public String showCategoryDetailPage(ModelMap model, HttpServletRequest request) {
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
                .getBean("GetMasterData");
        
        String groupId = request.getParameter("id").toString();
        String groupManager = request.getParameter("groupManager").toString();
        
        List<Map<String, Object>> groupDetailsList = commonDataForCacheObj.getIHDGroupMemberID(groupId);
        
        List<Map<String, Object>> groupDetailsListWithoutManager = new ArrayList<Map<String,Object>>();
        
        for (Map<String, Object> detailList : groupDetailsList) {
			if (!detailList.get("MEMBER_ID").toString().trim().toUpperCase()
					.equalsIgnoreCase(groupManager.trim().toUpperCase())) {
				groupDetailsListWithoutManager.add(detailList);
			}
		}
        
        
        model.put("type", groupDetailsListWithoutManager);
		
		return "ADMIN_EditGroupDetails";
	}
	
	/******************Admin Console: Category Addition*************************************************/
	 @RequestMapping(value = "ADMIN_CategoryDisplayConsole.htm", method = RequestMethod.GET)
	    public String showCategory(ModelMap model) {
	        ApplicationContext ctx = COMMON_AppContext.getCtx();
	        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	                .getBean("GetMasterData");
	        List<Map<String, Object>> type = commonDataForCacheObj
	                .getCategoriesById("PARENT_ID", 0); 
	        List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();
	       
	        for(Map<String, Object> functionList:type){        	 
	             if(!functionList.get("NAME").equals("Function Correction Required")){
	            	 funcnList.add(functionList);
	             }
	        }     
	        
	        model.put("type", funcnList);
	        return "ADMIN_CategoryDisplayConsole";
	    }
	 /******************Admin Console: Category Addition*************************************************/
	 
	 /*********************************Admin Console:Role Manipulation******************************/
	 @RequestMapping(value = "ADMIN_RoleManipulation.htm", method = RequestMethod.GET)
	    public String manipulateRole(ModelMap model) {
	        return "ADMIN_RoleManipulation";
	    }
	 
	  @RequestMapping(value = "MemRoleDetail.htm", method = RequestMethod.GET)
	   public @ResponseBody void getRoleDetails(@RequestParam String employeeId,HttpServletResponse response,
	            HttpServletRequest request) {
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
		  ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
          .getBean("GetgroupSettings");
		  List<Map<String, Object>> roleDetails=groupSettingsObj.getEmployeeRoleDetail(employeeId);
		  JsonUtility.sendData(roleDetails, response);
	    }
	 /*********************************Admin Console:Role Manipulation******************************/
}
