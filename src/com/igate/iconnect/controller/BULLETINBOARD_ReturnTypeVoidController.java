/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.igate.iconnect.dao.BULLETINBOARD_CreateUpdateDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class BULLETINBOARD_ReturnTypeVoidController {
	private static final String BULLETINBOARD_DAO_IMPL = "bulletinBoardDAOImpl";
	
	  @RequestMapping(value = "/bulletinMessage", method = RequestMethod.GET)
	    public @ResponseBody
	    void getBulletinMessage(HttpServletResponse response, HttpServletRequest request) {
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
			BULLETINBOARD_CreateUpdateDAO bulletinBoardDAOImpl = (BULLETINBOARD_CreateUpdateDAO) ctx
			.getBean(BULLETINBOARD_DAO_IMPL);
			List<Map<String, Object>> bulletinList=bulletinBoardDAOImpl.getBulletinMessage();
			
	        JsonUtility.sendData(bulletinList, response);
	  }
	  @RequestMapping(value = "/removeBulletinBoardMessage", method = RequestMethod.GET)
	    public @ResponseBody
	    void removeBulletinMessage(String jsonstring,HttpServletResponse response, HttpServletRequest request) throws JSONException {
		
		    JSONArray json = new JSONArray(jsonstring);
		  ApplicationContext ctx = COMMON_AppContext.getCtx();
			BULLETINBOARD_CreateUpdateDAO bulletinBoardDAOImpl = (BULLETINBOARD_CreateUpdateDAO) ctx
			.getBean(BULLETINBOARD_DAO_IMPL);
			int result=0;
			
			 for (int i = 0; i < json.length(); i++) {
				 result=bulletinBoardDAOImpl.removeBulletinBoard(json.get(i).toString());
			 }
			 if(result==1)
				 JsonUtility.sendData("Sucess", response);
			 else
				  JsonUtility.sendData("Error", response);
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
