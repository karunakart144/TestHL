/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.BO.COMMON_Menu;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.LoginDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class MainLayout_ReturnTypeVoidController {
	private static Logger log = Logger.getLogger(MainLayout_ReturnTypeVoidController.class);
	
	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}
	  @RequestMapping(value = "/menuList", method = RequestMethod.GET)
	    public @ResponseBody
	    void getMenuList(@RequestParam String parentmenuId,
	            HttpServletResponse response, HttpServletRequest request) {
	        String empId = (String) request.getSession()
	                .getAttribute("userLoginId");
	        User userObj = (User) request.getSession().getAttribute(empId);
	        String roleId = userObj.getUserRoleId();
	        ApplicationContext ctx = COMMON_AppContext.getCtx();
	        COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
	                .getBean("GetMasterData");
	        List<COMMON_Menu> parentMenuList = MasterDataImpl.getParentMenuList(roleId);
	        request.getSession().setAttribute("parentMenu", parentMenuList);
	        if (parentMenuList.size() != 0) {
	            COMMON_Menu parentMenuObj = parentMenuList.get(0);
	            parentMenuObj.getMenuId();

	        }
	        List<COMMON_Menu> childMenuList = MasterDataImpl.getChildMenuList(roleId,
	                parentmenuId);
	        for (int i = 0; i < childMenuList.size(); i++) {
	            COMMON_Menu childMenuObj = childMenuList.get(i);
	            List<COMMON_Menu> childRecord = MasterDataImpl.getChildMenuList(roleId,
	                    childMenuObj.getMenuId());
	            if (childRecord.size() != 0) {
	                for (COMMON_Menu childRecObj : childRecord) {
	                    childMenuObj.setChildExsists("true");
	                    childMenuList.add(childRecObj);
	                }
	            } else {
	                childMenuObj.setChildExsists("false");
	            }
	        }
	        JsonUtility.sendData(childMenuList, response);
	    }
	  
	  
	  @RequestMapping(value = "/getGUIDForHelpdeskCallLogSSO.htm", method = RequestMethod.GET)
	    public @ResponseBody
	    void getGUIDForHelpdeskCallLogSSO(HttpServletResponse response, HttpServletRequest request) {
	        String empId = (String) request.getSession()
	                .getAttribute("userLoginId");
	        Map<String,Object> responseMap=new HashMap<String, Object>();
	        ApplicationContext ctx = COMMON_AppContext.getCtx();
	        LoginDAO loginDAO = (LoginDAO) ctx
	                .getBean("loginDAOImpl");
	        String l_guId=loginDAO.getGUIDForHelpdeskCallLog(empId);
	        responseMap.put("GU_ID", l_guId);
	        JsonUtility.sendData(responseMap, response);
	        
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
