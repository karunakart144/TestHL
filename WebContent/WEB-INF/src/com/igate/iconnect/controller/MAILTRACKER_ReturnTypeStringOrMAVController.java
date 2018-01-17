/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.igate.iconnect.BO.HELPDESK_Category;
import com.igate.iconnect.BO.HELPDESK_Create;
import com.igate.iconnect.BO.MailTracker;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.MAILTRACKER_DAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_SpecialCharacterConverter;

@Controller 
public class MAILTRACKER_ReturnTypeStringOrMAVController {
	private static Logger log = Logger.getLogger(MAILTRACKER_ReturnTypeStringOrMAVController.class);
	//Added to implement the tool to track EX employees quereis for HR function
	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	@ExceptionHandler
	public String handleExceptions(Exception e, HttpServletResponse response) {

		log.error("", e);
		return "Error";

	}
	
	@RequestMapping(value = "/convertMailtoHelpdesk.htm", method = RequestMethod.GET)
	public ModelAndView convertMailtoHelpdesk(@RequestParam String mailid,
			String mailTrackerType, ModelMap model, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		MAILTRACKER_DAO MailTrackerImpl = (MAILTRACKER_DAO) ctx
				.getBean("MailTrackerDAO");
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean("HelpDeskTicket");
				//Added to implement the tool to track EX employees quereis for HR function
		MailTracker mailtracker = null;		
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean(COMMON_CACHE_IMPL);
		/*
		 * Changed By : 714599 Comments : Below variable mailtrackerType is used
		 * to refer the Differentiate the table like when HR Executive/Manager
		 * tries to access the Mail the details will be coming from
		 * IC_HR_MAIL_TRACKER_DETAILS else if any level0 executive tries to
		 * access details will come from IC_MAIL_TRACKER_DETAILS
		 */
		HELPDESK_Create createIHDform = new HELPDESK_Create();
		final HELPDESK_SpecialCharacterConverter convert= new HELPDESK_SpecialCharacterConverter();
		if (mailTrackerType.equalsIgnoreCase("HelpDesk")) {
			mailtracker = MailTrackerImpl.getMailTrackerDetails(mailid.trim());
			createIHDform.setMailTrackerType("HelpDesk");
		} else if (mailTrackerType.equalsIgnoreCase("HR")) {
			mailtracker = MailTrackerImpl.getMailTrackerDetailsForHR(mailid
					.trim());
			createIHDform.setMailTrackerType("HR");
			createIHDform.setCCEmailID(mailtracker.getFROM_ADDRESS());		}

		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
	

		if (null != mailtracker.getREFERENCE_ID()
				&& !mailtracker.getREFERENCE_ID().equalsIgnoreCase("")) {
			return new ModelAndView(new RedirectView("HELPDESK_Edit.htm?id="
					+ mailtracker.getREFERENCE_ID()));
		}
		List<Map<String, Object>> type = MasterDataImpl.getCategoriesById(
				"PARENT_ID", 0);
		model.put("type", type);
		List<Map<String, Object>> priorities = MasterDataImpl.getPriorityList();
		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(loginID);
		model.put("priorities", priorities);
		model.put("projects", projects);
		//Converting ascii value in subject and description from db into special characters to display to user
		//Changed by gk820877 on 5/21/2015
		createIHDform.setSubject(convert.convertSpecialChars(mailtracker.getSUBJECT()));
		String description = convert.convertSpecialChars(mailtracker.getDESCRIPTION());
		if (description.length() > 2000) {
			description = description.substring(0, 2000);
		}
		 ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
		 String hr_address=bundle.getString("HR_ADDRESS");
		if(mailtracker.getTO_ADDRESS()!=null){
			if(mailtracker.getTO_ADDRESS().length()>=0){
				createIHDform.setToAddressMailId(hr_address);
			List<HELPDESK_Category> category = null;		
			List<HELPDESK_Category> subcategory = null;
				category = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
						632);
				model.put("category", category);
				subcategory = MasterDataImpl.getCategoriesByColumn("PARENT_ID",
						639);
				model.put("subcategory", subcategory);
		}
			for(int i=0;i<projects.size();i++){
				if(i>0){
					projects.remove(i);
				}
			}
		}
		
		model.put("projects", projects);
		createIHDform.setDescription(description);
		createIHDform.setOnbeofEmpId(mailtracker.getEMPLOYEE_ID());
		createIHDform.setSource(mailtracker.getSOURCE());
		createIHDform.setCreateddate(mailtracker.getCREATED_DATE());
		createIHDform.setMailid(mailtracker.getMAIL_ID());
		/*Mail Tracker: IS ACTIVE EMPLOYEE CHECK is set to default 1 (as ACTIVE EMPLOYEE)*/
		createIHDform.setIsEXEmployeeCheck("0");
		//Mail Tracker
		
		if(mailTrackerType.equalsIgnoreCase("HR")){
		createIHDform.setCCEmailID(mailtracker.getFROM_ADDRESS().trim()+","+mailtracker.getCC_ADDRESS().trim());
		}
		if (mailtracker.getATTACHMENT_NAME() != null
				&& !mailtracker.getATTACHMENT_NAME().equalsIgnoreCase("")) {
			createIHDform.setAttachmentName(mailtracker.getATTACHMENT_NAME());
			createIHDform.setAttachmentPath(mailtracker.getATTACHMENT_PATH());
			model.put("attachmentselect", "display:none");
			model.put("attachmentdisplay", "");
		} else {
			model.put("attachmentselect", "");
			model.put("attachmentdisplay", "display:none");
		}
		//Changed by gk820877 on 5/19/2015
		if(mailtracker.getSOURCE().equalsIgnoreCase("mApps")){	
			model.put("sourceValue", "mApps");
		}else{
			model.put("sourceValue", "Email");
		}
		//set from Address to validate if mail has come from Active/Ex Employee
		createIHDform.setFromAddress(mailtracker.getFROM_ADDRESS());
		model.addAttribute("CreateHelpdeskBean", createIHDform);
		return new ModelAndView("HELPDESK_Create", model);
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
