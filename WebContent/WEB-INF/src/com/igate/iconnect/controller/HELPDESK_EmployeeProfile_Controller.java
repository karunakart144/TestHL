package com.igate.iconnect.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.dao.HELPDESK_EmployeeProfileDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class HELPDESK_EmployeeProfile_Controller {
	
	@RequestMapping(value = "HELPDESK_EmployeeProfile.htm", method = RequestMethod.GET)
	public ModelAndView getadminupdatelocation(ModelMap model, HttpServletRequest request){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_EmployeeProfileDAO MasterDataImpl = (HELPDESK_EmployeeProfileDAO) ctx
				.getBean("EmployeeProfile");
    
		return new ModelAndView("HELPDESK_EmployeeProfile");
		}
	
	@RequestMapping(value="/getEmployeeDetails.htm", method=RequestMethod.GET)
	public void getProjectDetailsForEmployee(@RequestParam String employeeid,ModelMap model, 
			HttpServletRequest request, HttpServletResponse response){
			ApplicationContext ctx= COMMON_AppContext.getCtx();
			HELPDESK_EmployeeProfileDAO MasterDataImpl = (HELPDESK_EmployeeProfileDAO) ctx
			.getBean("EmployeeProfile");
			List<Map<String, Object>> employeeProfile = MasterDataImpl.getEmployeeProfile(employeeid);
				JsonUtility.sendData(employeeProfile, response);
		}

}
