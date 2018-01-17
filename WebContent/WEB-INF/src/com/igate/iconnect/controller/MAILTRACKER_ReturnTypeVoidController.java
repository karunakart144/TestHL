/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igate.iconnect.dao.MAILTRACKER_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class MAILTRACKER_ReturnTypeVoidController {
	private static Logger log = Logger.getLogger(MAILTRACKER_ReturnTypeVoidController.class);

	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error(e);
		result.put("status", "Error");
		result.put("message", "Internal System Error !!");
		JsonUtility.sendData(result, response);
	}

	@RequestMapping(value = "/DiscardMail.htm", method = RequestMethod.GET)
	public void discardMail(@RequestParam String mailid,
			String mailtrackerType, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MAILTRACKER_DAO MailTrackerImpl = (MAILTRACKER_DAO) ctx
				.getBean("MailTrackerDAO");
		/*
		 * Changed By : 714599 Comments : Below variable mailtrackerType is used
		 * to refer the Differentiate the table like when HR Executive/Manager
		 * tries to access the Mail the details will be coming from
		 * IC_HR_MAIL_TRACKER_DETAILS else if any level0 executive tries to
		 * access details will come from IC_MAIL_TRACKER_DETAILS
		 */
		if (mailtrackerType.equalsIgnoreCase("HelpDesk")) {
			MailTrackerImpl.discardMailTracker(mailid);
		} else if (mailtrackerType.equalsIgnoreCase("HR")) {
			MailTrackerImpl.discardMailTrackerForHR(mailid);
		}

		JsonUtility.sendData("SUCCESS", response);
	}

	@RequestMapping(value = "/lockMail.htm", method = RequestMethod.POST)
	public @ResponseBody
	void lockIHDTicket(@RequestBody String jsonString,
			HttpServletResponse response, HttpServletRequest request)
			throws ParseException, IOException, JSONException {
		JSONObject json = new JSONObject(jsonString.replace("\\n",
				"brlinebreakbreak"));
		json = new JSONObject(json.getString("jsonString"));
		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		json.put("LOCKED_BY", loginID);
		DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT:00"));
		String createddate = dateFormatGmt.format(new Date());
		json.put("LOCKED_DATE", createddate);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MAILTRACKER_DAO MailTrackerImpl = (MAILTRACKER_DAO) ctx
				.getBean("MailTrackerDAO");
		Map<String, Object> lockMailDetails = MailTrackerImpl.lockMail(json);
		JsonUtility.sendData(lockMailDetails, response);

	}
	

}
