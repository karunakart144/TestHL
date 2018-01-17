/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.HELPDESK_Create;
import com.igate.iconnect.constants.COMMON_CacheSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.HELPDESK_CreateUpdateDAO;
import com.igate.iconnect.dao.LoginAuthenticationDAO;
import com.igate.iconnect.service.LoginValidator;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.HELPDESK_CreateFunctionWise;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class HELPDESK_Create_ReturnTypeVoidController {

	private final LoginValidator loginValidator;

	private static Logger log = Logger
			.getLogger(HELPDESK_Create_ReturnTypeVoidController.class);

	@Autowired
	public HELPDESK_Create_ReturnTypeVoidController(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;

	}

	private static final String COMMON_CACHE_IMPL = "GetMasterData";
	private static final String HELPDESK_DAO_IMPL = "HelpDeskTicket";
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

	/**
     * To get all the categories available in the system.
     * When pass the parentid then all the child categories belongs to that will return
     * 
     * Ex: functions,categories,subcategories.
     * 
     */
	@RequestMapping(value = "/getHelpDeskCategories.htm", method = RequestMethod.GET)
	public void getCategoriesForCreatingHelpdeskTicket(
			@RequestParam String CategoryId, HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> categories = MasterDataImpl
				.getCategoriesById("PARENT_ID", Integer.parseInt(CategoryId));
		JsonUtility.sendData(categories, response);
	}

	/**
     * Will give the buildings for each location when we pass the locationid
     * 
     */
	@RequestMapping(value = "/getBuildingsforLocation.htm", method = RequestMethod.GET)
	public void getBuildingsforLocation(@RequestParam String locationID,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> distinctBuildinglocations = MasterDataImpl
				.getDistinctBuildingLocations(Integer.parseInt(locationID));
		String location = MasterDataImpl.getLocationsById(Integer
				.parseInt(locationID), "CITY");
		if (location.equalsIgnoreCase("OTHERS")) {
			distinctBuildinglocations.remove(0);
		}
		JsonUtility.sendData(distinctBuildinglocations, response);
	}

	/**
     * Return all the available floors for the passed building
     * 
     */
	
	@RequestMapping(value = "/getFloorsforBuilding.htm", method = RequestMethod.GET)
	public void getFloorsForLocation(@RequestParam String buildingname,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> locationDetails = MasterDataImpl
				.getFloorsforBuilding(COMMON_CacheSQLQueryConstants
						.removeUnicodes(buildingname));
		JsonUtility.sendData(locationDetails, response);
	}

	/**
     * validates employee whether the passed employee id exists in the igatepatni database or not
     * 
     */
	
	@RequestMapping(value = "/validateEmployee.htm", method = RequestMethod.GET)
	public void validateEmployee(@RequestParam String employeeId,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);
		Map<String, Object> employee = HelpDeskImpl.getEmployeeDetails(String
				.valueOf(employeeId));
		JsonUtility.sendData(employee, response);
	}
	//Added to implement the tool to track EX employees quereis for HR function
	@RequestMapping(value = "/validateEmployee_ex.htm", method = RequestMethod.GET)
	public void validateEmployee_ex(@RequestParam String employeeId,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);
		Map<String, Object> employee = HelpDeskImpl.getEmployeeDetails_Ex(String
				.valueOf(employeeId));
		JsonUtility.sendData(employee, response);
	}//END
	/**
     * retrieve all the avaiable subjects for the passed category
     * 
     */
	
/*	@RequestMapping(value = "/getSubjectsforIHD.htm", method = RequestMethod.GET)
	public void getSubjectList(@RequestParam int CategoryId,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		List<Map<String, Object>> subjects = MasterDataImpl
				.getSubjectsForCategory(CategoryId);
		JsonUtility.sendData(subjects, response);
	}*/
	
	/**
     * To create the helpdesk ticket
     * will pass the bean and model to view to create the ticket
     * 
     */
	
	@RequestMapping(value = "/HELPDESK_Create.htm", method = RequestMethod.GET)
	public ModelAndView showticketCreation(ModelMap model,
			HttpServletRequest request) {

		String loginID = (String) request.getSession().getAttribute(
				"userLoginId");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);

		List<Map<String, Object>> type = MasterDataImpl.getCategoriesById(
				"PARENT_ID", 0);
		// Removing Function Correction Required function from the Function dropdown to restrict the creation.
		// Changed By : Nagamanikanta(714599)
		
		List<Map<String, Object>> functionList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : type){
			if(!map.get("NAME").toString().equalsIgnoreCase("Function Correction Required")){
				functionList.add(map);
			}
		}
		type = functionList;
		model.put("type", type);
		List<Map<String, Object>> priorities = MasterDataImpl.getPriorityList();
		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(loginID);
		// List<LocationBean> locationList = MasterDataImpl.getICLocations();
		model.put("priorities", priorities);
		model.put("projects", projects);
		model.put("projectsSize", projects.size());
		// model.put("locations", locationList);
		model.put("attachmentselect", "");
		model.put("attachmentdisplay", "display:none");
		
		String subject=request.getParameter("subjectText");
		if(subject==null){
			subject="";
		}else{
			if(subject.toString().length()==0)
			subject="";
		}
	
/*		String duId=MasterDataImpl.getUserDUID(loginID);
		int deptId=0;
		if(duId==null){
			deptId=0;
		}else
		{
			deptId=MasterDataImpl.getUserDeptID(duId);		
		}	*/	
		HELPDESK_Create createIHDform = new HELPDESK_Create();
/*		if(deptId!=0){
			createIHDform.setDepartment(Integer.toString(deptId));
		}else{
			createIHDform.setDepartment("0");
		}*/
		createIHDform.setSubject(subject);
		createIHDform.setSource("Web");
		model.addAttribute("CreateHelpdeskBean", createIHDform);

		return new ModelAndView("HELPDESK_Create");
	}
	
	/**
     * This method Will create the Helpdesk Ticket with the entered inputs 
     * 
     */

	@RequestMapping(value = "/HELPDESK_Create.htm", method = RequestMethod.POST)
	public void createnewIHDTicket(HELPDESK_Create CreateHelpdeskBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> values = new HashMap<String, Object>();
		MultipartFile file = CreateHelpdeskBean.getProblemfile();
		MultipartFile approvalfile = CreateHelpdeskBean.getApprovalfile();
		// try {
		if (!file.isEmpty()) {
			String status = fileValidation(CreateHelpdeskBean.getProblemfile());
			if (!status.equalsIgnoreCase("")) {
				values.put("problemfile", status);
			}
		}
		if (!approvalfile.isEmpty()) {
			String status = fileValidation(CreateHelpdeskBean.getApprovalfile());
			if (!status.equalsIgnoreCase("")) {
				values.put("approvalfile", status);
			}
		}
		if (values.size() > 0) {
			values.put(ERROR, "Error");
			JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
					response);
		} else {
			createTicket(CreateHelpdeskBean, request, response);
		}
		/*
		 * } catch (Exception e) { log.error(e); e.printStackTrace();
		 * values.put("exception", "exception"); if (e.getMessage() == null)
		 * values.put("message",
		 * "Problem encountered while updating the data !"); else
		 * values.put("message", e.getMessage());
		 * 
		 * JsonUtility.writedata(JsonUtility.converthashmapToJson(values),
		 * response); }
		 */
	}

	/**
     * This method Will create the Helpdesk Ticket with the entered inputs 
     * 
     */
	
	public void createTicket(HELPDESK_Create CreateHelpdeskBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);
		Map<Integer, String> functionlistmap = MasterDataImpl.returnAllAvailableFunctions();
		//Added to implement the tool to track EX employees quereis for HR function
		
		CreateHelpdeskBean.setFunction(CreateHelpdeskBean.getFunction().replace(",", ""));
		CreateHelpdeskBean.setCategory(CreateHelpdeskBean.getCategory().replace(",", ""));
		CreateHelpdeskBean.setSubcategory(CreateHelpdeskBean.getSubcategory().replace(",", ""));
		CreateHelpdeskBean.setLocationId(CreateHelpdeskBean.getLocationId().replace(",", ""));
		/*End*/
		String functionName = functionlistmap.get(Integer
				.parseInt(CreateHelpdeskBean.getFunction()));

		if (functionName.equalsIgnoreCase("Life and Health Operations Canada")) { //L2-2078
			HELPDESK_CreateFunctionWise.createLighthouseTicket(
                    CreateHelpdeskBean, request, response);
		}

	}

	/**
     * This method Will validate the attached file 
     * Ex : when the file size is more than 5 MB it throws error message 
     * 
     */
	public String fileValidation(MultipartFile file) {

		String result = "";
		if (file.getSize() > 5243000) {
			result = "File size can't be more than 5 MB !";
		}


		String fileextension = CustomDateFormatConstants
				.getFileTypeExtension(file.getOriginalFilename());
		if (CustomDateFormatConstants.getDataType().contains(fileextension)) {
			result += "</br> File type <b>" + fileextension
					+ "</b> is not supported";
		}
		return result;
	}

	/**
     * This method Will return the projects allocated to passed employee 
     * 
     */
	
	@RequestMapping(value = "/getProjectList.htm", method = RequestMethod.GET)
	public void getProjectListForEmployeeMail(@RequestParam String employeeid,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();

		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
				.getBean(HELPDESK_DAO_IMPL);
		List<Map<String, Object>> projects = HelpDeskImpl
				.getProjectsForEmployee(employeeid);

		JsonUtility.sendData(projects, response);
	}

	/**
     * This method Will give the Mobile number and extension number for passed employee.
     * All data will get from LDAP 
     * 
     */
	
	@RequestMapping(value = "/getEmployeeMobileAndExtn.htm", method = RequestMethod.GET)
	public void getEmployeeMobileAndExtn(@RequestParam String employeeid,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<String> MobileAndExtn = new ArrayList<String>();
		MobileAndExtn = loginValidator.MobileAndExtension(employeeid);

		JsonUtility.sendData(MobileAndExtn, response);
	}

	/**
     * This method Will return all the available locations for the passed function  
     * 
     */
	
	@RequestMapping(value = "/getLocationListForFunction.htm", method = RequestMethod.GET)
	public void getLocationListForFunction(@RequestParam String functionid,
			HttpServletRequest request, HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		List<COMMON_Location> locationList = MasterDataImpl
				.getLocationsForFunction(Integer.parseInt(functionid));

		JsonUtility.sendData(locationList, response);
	}

	/*
	 * Changed By : 714599 1.The Below controller required to filter the
	 * locations on selection of subcategory 2.Locations will be taken from
	 * default assignment details table
	 */
	@RequestMapping(value = "/getLocationListForSubcategory.htm", method = RequestMethod.GET)
	public void getLocationListForSubcategory(@RequestParam String categoryid,
			HttpServletRequest request, HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		List<COMMON_Location> locationList = MasterDataImpl
				.getLocationsForCategory(Integer.parseInt(categoryid));

		JsonUtility.sendData(locationList, response);
	}

	/**
     * This method Will return all the available departments in igatepatni 
     * 
     */
	
	
	@RequestMapping(value = "/departmentList.htm", method = RequestMethod.GET)
	public void departmentList(@RequestParam String employeeID,HttpServletRequest request,
			HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		String duId=MasterDataImpl.getUserDUID(employeeID.trim());		
		int duIdLength=0;
		if(duId!=null){
			duIdLength=duId.length();
		}
		List<Map<String, Object>> departmentList=new ArrayList<Map<String,Object>>();
		if(duIdLength!=0){
			if(duId!=null){
			departmentList=MasterDataImpl.getUserDeptMappedID(duId);
			}
		}else
		{
			departmentList = MasterDataImpl
			.getListofData(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_MASTER_VARIABLE);
		}
		
		if(departmentList.size()==0){
			departmentList = MasterDataImpl
			.getListofData(COMMON_CacheSQLQueryConstants.IC_IHD_DEPT_MASTER_VARIABLE);
		}
		JsonUtility.sendData(departmentList, response);
	}
	
	/**
     * This method Will return all the available ODCS in igatepatni 
     * 
     */

	@RequestMapping(value = "/odcList.htm", method = RequestMethod.GET)
	public void odcList(@RequestParam String locdetID,
			HttpServletRequest request, HttpServletResponse response) {

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean(COMMON_CACHE_IMPL);

		List<Map<String, Object>> odcList = MasterDataImpl
				.getodcsForLocation(Integer.parseInt(locdetID));

		JsonUtility.sendData(odcList, response);
	}
	
	@RequestMapping(value = "/getHelpDeskCatRoleMapping.htm", method = RequestMethod.GET)
	 public void getCategoriesRoleMappingForCreatingHelpdeskTicket(HttpServletResponse response)
	    {
	        ApplicationContext ctx = COMMON_AppContext.getCtx();
	        COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO)ctx.getBean("GetMasterData");
	        List<Map<String, Object>> role = MasterDataImpl.getRoleMappingForCat();	       
	        JsonUtility.sendData(role, response);
	    }
	
	@RequestMapping(value = "/getHelpDeskCatBUMapping.htm", method = RequestMethod.GET)
	 public void getCategoriesBUMappingForCreatingHelpdeskTicket(@RequestParam String loginID,HttpServletResponse response)
	    {
	        ApplicationContext ctx = COMMON_AppContext.getCtx();
	        COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO)ctx.getBean("GetMasterData");
	        List<Map<String, Object>> categoryList = MasterDataImpl.getBUMappingForCat(loginID);	       
	        JsonUtility.sendData(categoryList, response);
	    }
	

	
	@RequestMapping(value = "/checkEmailExistinLdap.htm", method = RequestMethod.GET)
	public void checkEmailExistinLdap(@RequestParam String LoginID,
			HttpServletResponse response) {
		log.info("checkEmailExistinLdap.htm");
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
		LoginAuthenticationDAO ldapCheck = (LoginAuthenticationDAO) ctx.getBean("ldapCheck");
        //String name =ldapCheck.IsEmailExist(LoginID);
		String samaccountname =ldapCheck.getsamaccountnameforOrchestration(LoginID);	
		JsonUtility.sendData(samaccountname, response);
	}
	
	//Added for Cubicle code generation from Campus Crawler
	
	@RequestMapping(value = "getCubicleCodeByLoc.htm", method = RequestMethod.POST)
	public void filterBuildingOrAreaOrCity(@RequestParam String LOC_DET_ID,String cubicleCode,
			HttpServletRequest request, HttpServletResponse response) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
		.getBean(HELPDESK_DAO_IMPL);
		List<Map<String, Object>> cubicleCodeByLocID = new ArrayList<Map<String, Object>>();
		cubicleCodeByLocID = HelpDeskImpl
				.getCubiclecodeByLocID(LOC_DET_ID,cubicleCode);
		JsonUtility.sendData(cubicleCodeByLocID, response);
	}
	
	@RequestMapping(value = "/checkEmpMappedToSeatOrNot.htm", method = RequestMethod.GET)
	public void checkEmpMappedToSeatOrNot(@RequestParam String LoginID,
			HttpServletResponse response) {
		
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
		 HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
			.getBean(HELPDESK_DAO_IMPL);
		List<Map<String, Object>> empInfo =HelpDeskImpl.getEmployeeMappedOrNOTOnLoad(LoginID);
		JsonUtility.sendData(empInfo, response);
	}
	
	@RequestMapping(value = "/getPriorityBasedOnCategory.htm", method = RequestMethod.GET)
	public void getPriority(@RequestParam String CategoryId,
			HttpServletResponse response) {
		
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
		 HELPDESK_CreateUpdateDAO HelpDeskImpl = (HELPDESK_CreateUpdateDAO) ctx
			.getBean(HELPDESK_DAO_IMPL);
		 List<Map<String, Object>> priorities = HelpDeskImpl.getCatgPriority(CategoryId.trim());
		JsonUtility.sendData(priorities, response);
		
	}
}

/*-----------------------------------------------------------------------------
 Log: 
 Start-----Version 1.0-----
 Changes Made:New File Created
 Changes Made By:714599
 Changes Made on:Jun 8, 2011
 End-------Version 1.0-------

 -----------------------------------------------------------------------------*/