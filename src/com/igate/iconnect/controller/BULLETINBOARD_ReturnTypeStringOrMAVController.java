/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.igate.iconnect.BO.BULLETINBOARD_Create;
import com.igate.iconnect.dao.BULLETINBOARD_CreateUpdateDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class BULLETINBOARD_ReturnTypeStringOrMAVController {
	private static final String BULLETINBOARD_DAO_IMPL = "bulletinBoardDAOImpl";
	private static final String SUCCESS_VARIABLE = "SUCCESS";
	private static final String STATUS_VARIABLE = "status";
	private static final String USER_LOGINID_VARIABLE = "userLoginId";
	private static Logger log = Logger
			.getLogger(BULLETINBOARD_ReturnTypeStringOrMAVController.class);

	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";

	}

	@RequestMapping(value = "/BULLETINBOARD_Create.htm", method = RequestMethod.GET)
	public String createBulletinBoard(ModelMap model) {
			BULLETINBOARD_Create createBulletform = new BULLETINBOARD_Create();
		model.addAttribute("CreateBulletinBoardBean", createBulletform);
				return "BULLETINBOARD_Create";
		
	}
	@RequestMapping(value = "/BULLETINBOARD_Create.htm", method = RequestMethod.POST)
	public void createnewIHDTicket(BULLETINBOARD_Create CreateBulletinBoardBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
				ApplicationContext ctx = COMMON_AppContext.getCtx();
		BULLETINBOARD_CreateUpdateDAO bulletinBoardDAOImpl = (BULLETINBOARD_CreateUpdateDAO) ctx
				.getBean(BULLETINBOARD_DAO_IMPL);
		HashMap<String, Object> insertdetails = new HashMap<String, Object>();
		insertdetails.put("BULLETIN_DESCRIPTION", CreateBulletinBoardBean.getDescription());
		insertdetails.put("BULLETIN_HEADER", CreateBulletinBoardBean.getHeader());
	
		String createdby = (String) request.getSession().getAttribute(
				USER_LOGINID_VARIABLE);
		insertdetails.put("CREATEDBY", createdby);
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		insertdetails.put("CREATEDDATE",dateFormatGmt.format(new Date()));
		String result = bulletinBoardDAOImpl.insertBulletinBoard(insertdetails);
		JSONObject jsonforSuccess = new JSONObject();
		if (result.contains(SUCCESS_VARIABLE)) {
			jsonforSuccess.put(STATUS_VARIABLE, SUCCESS_VARIABLE);
			
		}

		JsonUtility.writedata(jsonforSuccess.toString(), response);
	}
	@RequestMapping(value = "/BULLETINBOARD_Remove.htm", method = RequestMethod.GET)
	public String removeBulletinBoard(ModelMap model) {
			return "BULLETINBOARD_Remove";
		
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
