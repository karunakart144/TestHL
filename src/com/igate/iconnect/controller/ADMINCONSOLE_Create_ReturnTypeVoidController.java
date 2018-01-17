package com.igate.iconnect.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.HELPDESK_Group;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.ADMIN_SettingsDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.COMMON_ListPageDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ListPageSearch;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class ADMINCONSOLE_Create_ReturnTypeVoidController {
	
	private static Logger log = Logger
	.getLogger(ADMINCONSOLE_Create_ReturnTypeVoidController.class);
	
	 @RequestMapping(value="ADMINCONSOLE_AddLocation.htm",method = RequestMethod.GET)
	    public String addLocation(ModelMap model){
	    	ApplicationContext ctx=COMMON_AppContext.getCtx();
	    	COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");
	    	List<Map<String,Object>> functionList=commonDataForCacheObj.getCategoriesById("PARENT_ID",0);
	    	  List<Map<String, Object>> funcnList=new ArrayList<Map<String,Object>>();          
	          for(Map<String, Object> functionMap:functionList){        	
	               if(!functionMap.get("NAME").equals("Function Correction Required") && !functionMap.get("NAME").equals("Delivery")  && !functionMap.get("NAME").equals("Admin") ){
	              	 funcnList.add(functionMap);
	               }
	          }   
	    	model.put("functionList", funcnList);    	
	    	return "ADMINCONSOLE_AddLocation";
		}
	 
	 //anjana
	 
	    @RequestMapping(value = "/get_country_list", method = RequestMethod.GET)
	    public void getCountryList(@RequestParam("term") String query,HttpServletResponse response,
				HttpSession session, HttpServletRequest request) throws JSONException {
	   	 ApplicationContext ctx=COMMON_AppContext.getCtx();
		 ADMIN_SettingsDAO uploadsettings =(ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		 List<Map<String, Object>>  employeelist = uploadsettings.getTablelist(query);
	    	JsonUtility.sendData(employeelist , response);
	    	}

	@RequestMapping(value="/ADMIN_ExcelUpload.htm",method = RequestMethod.GET)
	 public ModelAndView  excelUpload(ModelMap model,HttpServletRequest request)throws Exception
	 {
		 return new ModelAndView ("ADMIN_ExcelUpload");
	 }
/*	@RequestMapping(value="/ADMIN_ExcelUpload.htm",method = RequestMethod.POST)
	 public void  uploadExcelUpload(@ModelAttribute("UploadForm") ADMIN_ExcelUpload eUpload,HttpSession session, HttpServletRequest request,HttpServletResponse response )throws Exception
	 {
		 ApplicationContext ctx=COMMON_AppContext.getCtx();
		 String loginID = request.getSession().getAttribute("userLoginId").toString();
		 String fileName="";
		 String result="";
		 MultipartFile multipartFile = eUpload.getExcelUpload();
		 try{
		 if(multipartFile!=null){
				fileName = multipartFile.getOriginalFilename();
			}
		 Vector<Vector<Cell>> dataHolder=ReadCSV(fileName,eUpload,multipartFile,request);
		 ADMIN_SettingsDAO uploadsettings = (ADMIN_SettingsDAO) ctx.getBean("GetgroupSettings");
		 result=uploadsettings.updateTable(dataHolder,loginID,eUpload);		 
		 }
		 catch (Exception e) {
			 log.error("Exception occured while excel upload", e);
		}
		 JSONObject jsonforSuccess = new JSONObject();
		 if (result.contains("SUCCESS")) {
			 jsonforSuccess.put("status", "SUCCESS");
		 }
		 else
		 {
			 jsonforSuccess.put("status", "FALSE");
			 jsonforSuccess.put("exception", result.substring(result
						.indexOf("-") + 1, result.length()));
		 }
		 JsonUtility.writedata(jsonforSuccess.toString(), response);

	 }*/	
	
	/*private Vector<Vector<Cell>> ReadCSV(String fileName,ADMIN_ExcelUpload eUpload, MultipartFile multipartFile ,HttpServletRequest request) {
			 Vector<Vector<Cell>> cellVectorHolder = new Vector<Vector<Cell>>();
			 
			 try{
					InputStream inputStream = null;
					int rn=0;
					 if (multipartFile.getSize() > 0) 
					 	{
						 	inputStream = multipartFile.getInputStream();
						 	Workbook  workbook = WorkbookFactory.create(inputStream);	
						 	Sheet  sheet = workbook.getSheet(eUpload.getSheetName());		 	
						 	Iterator<Row> rowIterator = sheet.iterator();
						 	while(rowIterator.hasNext()) 
						 	{
						 		Vector<Cell> cellStoreVector = new Vector<Cell>();
						 		Row row = sheet.getRow(rn);
						 		short minColIx = row.getFirstCellNum();
						 		if(minColIx==0)
						 		 {
						 			 for(int cn=0; cn<row.getLastCellNum(); cn++) 
						 			 {
						 				 Cell cell = row.getCell(cn,Row.CREATE_NULL_AS_BLANK);
						 				 cellStoreVector.addElement(cell);
						 			 }
						 			
						 		 }
						 		
						 		cellVectorHolder.addElement(cellStoreVector);
						 		rn++;	
						 		 
						 	}
					 	}		
					
					} catch (Exception e) {
						 log.error("Exception occured in ReadCSV", e);
					}
			return cellVectorHolder;
		}*/


	

	@RequestMapping(value = "/insertAdminConsole_NewLocation_Step1.htm", method = RequestMethod.POST)
		public @ResponseBody
		void insertNewLocationDetStep1(@RequestBody String jsonString,
				HttpServletResponse response, HttpServletRequest request)
				throws Exception {
			Map<String, String> result = new HashMap<String, String>();
			JSONObject jsonarray = new JSONObject(jsonString);
			jsonarray = new JSONObject(jsonarray.getString("jsonString"));
			ApplicationContext ctx = COMMON_AppContext.getCtx();
	        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
	                .getBean("GetgroupSettings");
	        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");
			String functionID=jsonarray.getString("function");
			String countryName=jsonarray.getString("countryName");
			String newCountryName=jsonarray.getString("newCountry"); 
			String cityName=jsonarray.getString("city");
			
			int isValidLoc=1;
			
			List<COMMON_Location> locList=commonDataForCacheObj.getICLocations();	
			
			for (Iterator<COMMON_Location> iterator = locList.iterator(); iterator.hasNext();) {
				COMMON_Location commonLocation = iterator
						.next();
				
				if(commonLocation.getCITY().equalsIgnoreCase(cityName)){
					isValidLoc=0;
					break;
				}else
				if(commonLocation.getCOUNTRY().equalsIgnoreCase(newCountryName)){
					isValidLoc=0;
					break;
				}
				
			}
			if(isValidLoc==1)	
			{
					String holiday=jsonarray.getString("holiday") ;
					String[] holidayList = holiday.split(",");
					
					
					String loginID = request.getSession().getAttribute("userLoginId")
				     .toString();
					 
					if(newCountryName.length()>0){
						countryName=newCountryName;
					}
					
				    
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
				            	if(opTimeMap.get("START_TIME").toString().equalsIgnoreCase(opTimeMap.get("END_TIME").toString())){
				            		opTimeMap.remove("START_TIME");
				            		opTimeMap.remove("END_TIME");
				            		
				            		opTimeMap.put("START_TIME","00:00:00" );
				            		opTimeMap.put("END_TIME","23:59:59" );
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
							JSONObject obj=new JSONObject();
							Iterator<?> apprKeys = approverArray.keys();
							while( apprKeys.hasNext() ){
								 String keysName = (String)apprKeys.next();
								 obj=new JSONObject(approverArray.getString(""+keysName));						
									 approverMap.put("APPROVER_ID", obj.get("APPROVER_ROLE").toString());						 
									 approverMap.put("EMPLOYEE_ID",obj.get("APPROVER").toString());
							}	
							approverList.add(approverMap);
							approverMap=new HashMap<String, Object>();
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
							slaID=groupSettingsObj.insertSLAMasterAdminConsoleLocAdd(slaID,operationTimeList, cityName, loginID, createdDate,locnIDe,functionID,locnDetIde,holidayList);
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
						result.put("res", "1");
						result.put("messsage", "Location saved successfully!!!");
						result.put("slaID", slaID);
						result.put("locationId", String.valueOf(locnIDe));
					}else{
						result.put("res", "2");
						result.put("error", "Location insertion failed!!!");
						result.put("slaID", "");
						result.put("locationId", String.valueOf(locnIDe));
					}	
						
					/*Admin Console: Call reset cache and reset workflow cache method*/		
					resetCacheAndResetWorkflowMethod();
					/*Admin Console: Call reset cache and reset workflow cache method*/
					
				/***********************************Insertion of Admin Console:Location Detail**************************************************/	
				}else{
					result.put("res", "3");
					result.put("error", "Location already exists!!!");
					result.put("slaID", "");
					result.put("locationId", "");
				}		
			
					JsonUtility.sendData(result, response);
		}
	 
	 /**
		 * Admin Console:Location Addition: Call reset cache and reset workflow cache method
		 * @throws SecurityException
		 * @throws NoSuchMethodException
	 * @throws ConnectException 
	 * @throws WebServiceIOException 
		 */
		private  void resetCacheAndResetWorkflowMethod() throws SecurityException, NoSuchMethodException, ConnectException{
			
			 ApplicationContext ctx = COMMON_AppContext.getCtx();
		     /*  
				try {
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
		
		@RequestMapping(value = "ADMIN_Console_GrpListDetail.htm", method = RequestMethod.GET)
	    public  ModelAndView getGroupListJmesa(ModelMap model, HttpServletRequest request,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
			.getBean("GetMasterData");
			String userLoginId = (String) request.getSession().getAttribute(
			"userLoginId");
			final String functionId=request.getParameter("functionId");
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


			int pageNo = 1;
			int maxRows = 15;
			boolean firstTime = false;
			try {
				pageNo = Integer.parseInt(request.getParameter(menuName + "_p_"));
			} catch (NumberFormatException e) {			
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
			final int maxRowsToPass = maxRows;
			
			
			tableModel.setItems(new PageItems() {
				COMMON_Pagination<HELPDESK_Group> page = new COMMON_Pagination<HELPDESK_Group>();

				public int getTotalRows(Limit limit) {
					page = getFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, queryToPass,
							userTimeZoneToSend,grpList,functionId);
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
			
	 }
		
	public COMMON_Pagination<HELPDESK_Group> getFilteredResult(Limit limit,
					int startCount, int pageNo, int maxRows, String dynamicQuery,int userTimeZoneToSend,List<Map<String, Object>> grpList,String functionId) {

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
				      
				
				return listDAO.getGrpList_AC(startCount, pageNo, maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend, grpList,functionId);

	}
	
	public COMMON_Pagination<HELPDESK_Group> getFilteredResultDept(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery,int userTimeZoneToSend,List<Map<String, Object>> grpList) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);

		}
		StringBuffer buffer = listPageSearchFilter.execute("AdminConsoleGrpLocationDept");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		      
		
		return listDAO.getGrpListDept_AC(startCount, pageNo, maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend, grpList);

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
			Object rowCountByPage=new HtmlCellEditor().getValue(item,
					"rowCountPage", rowcount);
			int rowIncrement=Integer.parseInt(String.valueOf(rowCountByPage));
			
			String[] grpList=groupMap.toString().split(",");
			HtmlBuilder htmlBuilder=new HtmlBuilder();
			String grpName=null;
			String grpID=null;
			if(grpList.length>0){				
				htmlBuilder.select().id("grpIDJ_"+rowIncrement).styleClass("myTextInputForSelectAdmin").append(">");
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
	 checkboxCol.setHeaderEditor(new HeaderEditor() {
			
		public Object getValue() {	
			HtmlBuilder html = new HtmlBuilder();
			html.input().type("checkbox").id("checkboxAllJ").styleClass("group_detail_Checkbox")
			.onclick("javascript:editCheckBoxJAll(\'Group_Detail\',\'checkboxClick\')").end();
			return html;
			
		}
	});
	 HtmlBuilder html = new HtmlBuilder();	 
	 checkboxCol.setCellEditor(new CellEditor() {
		public Object getValue(Object item, String property, int rowcount) {
			Object subCatID = new HtmlCellEditor().getValue(item,
					"SUB_CATEGORY_ID", rowcount);
			Object categoryID=new HtmlCellEditor().getValue(item,
					"CATEGORY_ID", rowcount);
			Object grpID=new HtmlCellEditor().getValue(item,
					"GROUP_ID", rowcount);
			
			Object rowCountByPage=new HtmlCellEditor().getValue(item,
					"rowCountPage", rowcount);
			rowcount=Integer.parseInt(String.valueOf(rowCountByPage));
			HtmlBuilder html = new HtmlBuilder();
			html.input().type("checkbox").id("checkboxJ"+rowcount).name("group_detail_Checkbox").styleClass("group_detail_Checkbox")
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
				Object rowCountByPage=new HtmlCellEditor().getValue(item,
						"rowCountPage", rowcount);			 
				rowcount=Integer.parseInt(String.valueOf(rowCountByPage));
				htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:editGrp("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+")").close();		
				 htmlViewOrEdit.append("Edit");
				 htmlViewOrEdit.aEnd();
				 htmlViewOrEdit.append(" | ");
				 htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:undoGrp("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+")").close();
				 htmlViewOrEdit.append("Undo");
				 htmlViewOrEdit.aEnd();			
				return htmlViewOrEdit;
		}
	});
	 htmlRow.addColumn(viewOREdit);	 
	 return htmlTable;
 }
	
	 @RequestMapping(value = "/insertNewLocation_grpDetail_Step2.htm", method = RequestMethod.POST)
		public @ResponseBody
		void insertNewLocation_grpDetail(@RequestBody String jsonString,
				HttpServletResponse response, HttpServletRequest request)
				throws Exception {
		 
		 ApplicationContext ctx = COMMON_AppContext.getCtx();
	        ADMIN_SettingsDAO groupSettingsObj = (ADMIN_SettingsDAO) ctx
	                .getBean("GetgroupSettings");
	        COMMON_CacheDAO commonDataForCacheObj = (COMMON_CacheDAO) ctx
	        .getBean("GetMasterData");
		 
		 	Map<String, String> result = new HashMap<String, String>();
			JSONObject jsonarray = new JSONObject(jsonString);
			jsonarray = new JSONObject(jsonarray.getString("jsonString"));			
			String functionID=jsonarray.getString("functionId");
			String locationId=jsonarray.getString("locationId");
			String slaId=jsonarray.getString("slaId");
			
			JSONObject defaultGroupArray=new JSONObject();
			defaultGroupArray=new JSONObject(jsonarray.getString("defaultGroupJSON"));
			Map<String,Object> grpMap=new HashMap<String, Object>();
			List<Map<String,Object>> grpMapList=new ArrayList<Map<String,Object>>();
			JSONObject objGrp=new JSONObject();
			Iterator<?> keys = defaultGroupArray.keys();
			while( keys.hasNext() ){				
	            String key = (String)keys.next();	           
	            if( defaultGroupArray.get(key) instanceof JSONObject ){	            	
	            	objGrp=new JSONObject(defaultGroupArray.getString(""+key));		            	
	            	grpMap.put("GROUP_ID", objGrp.get("GROUP_ID").toString());						 
	            	grpMap.put("SUB_CATEGORY_ID",objGrp.get("SUB_CATEGORY_ID").toString());
	            	grpMapList.add(grpMap);
	            	grpMap=new HashMap<String, Object>();
	            }
			}
			
			JSONObject groupDeptArray=new JSONObject();
			groupDeptArray=new JSONObject(jsonarray.getString("deptDefaultGroupJSON"));
			Map<String,Object> grpDeptMap=new HashMap<String, Object>();
			List<Map<String,Object>> grpMapDeptList=new ArrayList<Map<String,Object>>();
			JSONObject objDeptGrp=new JSONObject();
			Iterator<?> keysDeptGrp = groupDeptArray.keys();
			while( keysDeptGrp.hasNext() ){
	            String keyGrp= (String)keysDeptGrp.next();	           
	            if( groupDeptArray.get(keyGrp) instanceof JSONObject ){
	               	objDeptGrp=new JSONObject(groupDeptArray.getString(""+keyGrp));						
	            	grpDeptMap.put("GROUP_ID", objDeptGrp.get("GROUP_ID").toString());						 
	            	grpDeptMap.put("SUB_CATEGORY_ID",objDeptGrp.get("SUB_CATEGORY_ID").toString());	            	
	            	grpDeptMap.put("DEPT_ID",objDeptGrp.get("DEPT_ID").toString());
	            	grpMapDeptList.add(grpDeptMap);
	            	grpDeptMap=new HashMap<String, Object>();
	            }
			}
			String loginID = request.getSession().getAttribute("userLoginId")
	         .toString();
			
			 DateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
				String createdDate = dateFormatGmt.format(new Date());	
			
			//Insert to the dept and dept default table
			
			int resultOfInsertGrps=groupSettingsObj.insertDefaultDeptDefaultGroupForLocation(grpMapList, grpMapDeptList,functionID,slaId,locationId,loginID,createdDate);			
			
			if(resultOfInsertGrps==1){
				result.put("messsage", "Groups are successfully mapped!!!");
			}else{
				result.put("error", "Groups are not mapped. Try updating again!!!");
			}
			
			/*Admin Console: Call reset cache and reset workflow cache method*/		
			resetCacheAndResetWorkflowMethod();
			/*Admin Console: Call reset cache and reset workflow cache method*/
			JsonUtility.sendData(result, response);
	 }

	 @RequestMapping(value = "ADMIN_Console_Dept_GrpListDetail.htm", method = RequestMethod.GET)
	    public  ModelAndView getGroupDeptListJmesa(ModelMap model, HttpServletRequest request,
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
			
			String menuName="Group_Detail_Dept";
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
				maxRows = Integer.parseInt(request.getParameter("maxRows"));
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
			final int maxRowsToPass = maxRows;
			
			
			tableModel.setItems(new PageItems() {
				COMMON_Pagination<HELPDESK_Group> page = new COMMON_Pagination<HELPDESK_Group>();

				public int getTotalRows(Limit limit) {
					page = getFilteredResultDept(limit, startCountToPass,
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
			tableModel.setTable(getHtmlTableDept(menuId,menuName,headerMenuName));
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
					request.setAttribute("paginationData_Dept", view); // Set the Html in
					// the
					// request for the
					// JSP.
				}
			}
			
			request.setAttribute("paginationData_Dept", view);			
			return new ModelAndView("ADMIN_AddLocationDeptGrpList");
			
	 }
	 
	 private Table getHtmlTableDept(final String menuId, String menuName,
				String headerMenuName) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException  { 
		//Group_Detail_Dept 
		 HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		 htmlTable.setStyleClass("table");
		 HtmlRow htmlRow = new HtmlRow();
		 htmlRow = new HtmlRow().uniqueProperty("id");
		 htmlTable.setRow(htmlRow);
		 
		 HtmlColumn categoryNameCol=new HtmlColumn("CATEGORY_NAME").title("Category Name");
		 htmlRow.addColumn(categoryNameCol);
		 
		 HtmlColumn deptNameCol=new HtmlColumn("DEPT_NAME").title("Department Name");
		 htmlRow.addColumn(deptNameCol);
		 
		 HtmlColumn subCategoryNameCol=new HtmlColumn("SUB_CATEGORY_NAME").title("Subcategory Name");
		 htmlRow.addColumn(subCategoryNameCol);	 
		 
		 final HtmlColumn groupNameCol=new HtmlColumn("GROUP_MAP").title("Group Name");
		 groupNameCol.setCellEditor(new CellEditor() {

			public Object getValue(Object item, String property, int rowcount) {
				
				Object groupMap = new HtmlCellEditor().getValue(item,
						"GROUP_MAP", rowcount);	
				Object SUB_CATEGORY_NAME = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_NAME", rowcount);				
				Object rowCountByPage=new HtmlCellEditor().getValue(item,
						"rowCountPage", rowcount);
				int rowIncrement=Integer.parseInt(String.valueOf(rowCountByPage));
				
				String[] grpList=groupMap.toString().split(",");
				HtmlBuilder htmlBuilder=new HtmlBuilder();
				String grpName=null;
				String grpID=null;
				if(grpList.length>0){				
					htmlBuilder.select().id("grpIdDept_"+rowIncrement).styleClass("myTextInputForSelectAdmin").append(">");
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
		 checkboxCol.setHeaderEditor(new HeaderEditor() {
				
			public Object getValue() {	
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("checkbox").id("checkboxAllDept").styleClass("group_dept_Checkbox")
				.onclick("javascript:editCheckBoxJAll(\'Group_Detail_Dept\',\'checkboxClick\')").end();
				return html;
				
			}
		});
		 HtmlBuilder html = new HtmlBuilder();	 
		 checkboxCol.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object subCatID = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_ID", rowcount);
				Object categoryID=new HtmlCellEditor().getValue(item,
						"CATEGORY_ID", rowcount);
				Object grpID=new HtmlCellEditor().getValue(item,
						"GROUP_ID", rowcount);
				Object deptID=new HtmlCellEditor().getValue(item,
						"DEPT_ID", rowcount);
				
				Object rowCountByPage=new HtmlCellEditor().getValue(item,
						"rowCountPage", rowcount);
				rowcount=Integer.parseInt(String.valueOf(rowCountByPage));
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("checkbox").id("checkboxDept"+rowcount).name("group_dept_Checkbox").styleClass("group_dept_Checkbox")
				.value(String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+String.valueOf(deptID)).onclick("javascript:editCheckBoxDeptAC("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+","+String.valueOf(deptID)+")").end();
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
					Object deptID=new HtmlCellEditor().getValue(item,
							"DEPT_ID", rowcount);
					Object rowCountByPage=new HtmlCellEditor().getValue(item,
							"rowCountPage", rowcount);			 
					rowcount=Integer.parseInt(String.valueOf(rowCountByPage));
					htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:editGrpDept("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+","+deptID+")").close();		
					 htmlViewOrEdit.append("Edit");
					 htmlViewOrEdit.aEnd();
					 htmlViewOrEdit.append(" | ");
					 htmlViewOrEdit.a().href("#").styleClass("").onclick("javascript:undoGrpDept("+String.valueOf(categoryID)+","+String.valueOf(subCatID)+","+String.valueOf(grpID)+","+rowcount+","+deptID+")").close();
					 htmlViewOrEdit.append("Undo");
					 htmlViewOrEdit.aEnd();					
					return htmlViewOrEdit;
			}
		});
		 htmlRow.addColumn(viewOREdit);	 
		 return htmlTable;
	 }
	 
	 
}
