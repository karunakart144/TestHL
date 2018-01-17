/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.Reports;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.ReportsDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.JsonUtility;
@Controller
public class ReportsController {

	private static Logger log = Logger
			.getLogger(ReportsController.class);

	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String REPORTS_DAO_IMPL = "ReportsDAO";
	private static final String EXCEPTION_ERROR_MESSAGE = "Problem Encountered While Creating the ticket !";
	private static final String ERROR ="error";
	private static final String EXCEPTION ="exception";
	private static final String MESSAGE ="message";
	
	/**
     * Handles all the Exceptions occured in this controller
     * 
     */
	
	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("status", ERROR);
		values.put(MESSAGE, EXCEPTION_ERROR_MESSAGE);

		values.put(EXCEPTION,EXCEPTION);
		if (e.getMessage() == null)
			values.put(MESSAGE,
					EXCEPTION_ERROR_MESSAGE);
		else
			values.put(MESSAGE, e.getMessage());

		log.error(EXCEPTION_ERROR_MESSAGE, e);
		JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
				response);

	}

	
	@RequestMapping(value = "/Reports.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(ModelMap model,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		List<Map<String, Object>> type = MasterDataImpl.getCategoriesById("PARENT_ID", 0);
		
		List<Map<String, Object>> functionList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : type){
			functionList.add(map);
		}
		type = functionList;
		model.put("type", type);
		
		List<Map<String, Object>> statusList = MasterDataImpl.getWorkflowStatusList();
		
		List<Map<String, Object>> statusData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> status : statusList){
			statusData.add(status);
		}
		statusList = statusData;
		model.put("statusList", statusList);
		
	 	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		model.addAttribute("currentDate", currentDate);
		
		model.addAttribute("GenerateReportBean", new Reports());
		
		return new ModelAndView("Reports");
		}
	 

		@RequestMapping(value = "/Reports.htm", method = RequestMethod.POST)
		public void generateReport(Reports GenerateReportBean,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			ApplicationContext ctx = COMMON_AppContext.getCtx();
			ReportsDAO ReportsDAOImpl = (ReportsDAO) ctx.getBean(REPORTS_DAO_IMPL);
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String currentDateTime = dateFormat.format(date);
			Date currentDate = dateFormat.parse(currentDateTime);
			
			if(null != GenerateReportBean.getStartDate()&& null != GenerateReportBean.getEndDate()
					&& !GenerateReportBean.getStartDate().equalsIgnoreCase("")
					&& !GenerateReportBean.getEndDate().equalsIgnoreCase("")){
			Date startDate = dateFormat.parse(GenerateReportBean.getStartDate());
			Date endDate = dateFormat.parse(GenerateReportBean.getEndDate());
			
			if ((startDate.before(currentDate)||startDate.equals(currentDate))
					 && (startDate.before(endDate)||startDate.equals(endDate))){
				
				DateFormat dateFormatForReport = new SimpleDateFormat("yyyy-MM-dd");
				GenerateReportBean.setStartDate(dateFormatForReport.format(startDate));
				GenerateReportBean.setEndDate(dateFormatForReport.format(endDate));

				String result = ReportsDAOImpl.generateTicketReport(GenerateReportBean, request, response);
				JsonUtility.sendData(result, response);

				}
			}
				
		}

	
}