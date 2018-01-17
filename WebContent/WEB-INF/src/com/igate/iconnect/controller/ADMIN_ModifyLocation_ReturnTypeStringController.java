package com.igate.iconnect.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
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

import com.igate.iconnect.BO.ADMIN_ModifyLocation;
import com.igate.iconnect.BO.COMMON_Location;
import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.dao.ADMIN_SettingsDAO;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.COMMON_ListPageDAO;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ListPageSearch;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class ADMIN_ModifyLocation_ReturnTypeStringController {
	
	private static Logger log = Logger
	.getLogger(ADMIN_ModifyLocation_ReturnTypeStringController.class);
	
	@RequestMapping(value = "ADMIN_ModifyLocation.htm", method = RequestMethod.GET)
	public ModelAndView getadminupdatelocation(ModelMap model, HttpServletRequest request){
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<COMMON_Location> locList=MasterDataImpl.getICLocations();
        List<COMMON_Location> newLocList=new ArrayList<COMMON_Location>();
        
        for(COMMON_Location cl:locList){
              if(newLocList.size()==0){
                    newLocList.add(cl);
              }else{
                    int isPresent=0;
                    for(COMMON_Location ncl :newLocList){
                          if(ncl.getCITY().equalsIgnoreCase(cl.getCITY())){                                         
                                isPresent=1;
                                break;
                          }
                    }
                    if(isPresent==0){
                          newLocList.add(cl);
                    }
              }
        }
       	model.put("locations", newLocList);
		return new ModelAndView("ADMIN_ModifyLocation");
		}
	
	
	@RequestMapping(value = "/adminConsole_getLocationDetails.htm", method = RequestMethod.GET)
    public ModelAndView adminConsole_getLocationDetails(@RequestParam String locationId,String statusVal,
            HttpServletResponse response, HttpServletRequest request) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
    	ApplicationContext ctx = COMMON_AppContext.getCtx();
	COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
		.getBean("GetMasterData");
		String userLoginId = (String) request.getSession().getAttribute(
		"userLoginId");
		
		final String location_id=locationId;
		
		final String status_val=statusVal;
		
		
		String loogedinUserID = userLoginId;
		User userBean = (User) request.getSession().getAttribute(
				userLoginId);        
    
		// Defaulting userTime Zone to IST
		int userTimeZone = 67;
		if (userBean.getTimeZoneId() != null)
			userTimeZone = Integer.parseInt(userBean.getTimeZoneId());
		final int userTimeZoneToSend = userTimeZone;
		String roleId = userBean.getUserRoleId();
		String roleName = userBean.getUserRole();
		String query = "";
		final String queryToPass = query;
		
		String menuName="Location_Detail";
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
		
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		
		
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<ADMIN_ModifyLocation> page = new COMMON_Pagination<ADMIN_ModifyLocation>();

			public int getTotalRows(Limit limit) {
				page = getFilteredResultLocation(limit, startCountToPass,
						pageNoToPass, maxRowsToPass, queryToPass,
						userTimeZoneToSend,location_id,status_val);
				return page.getTotalCount();
			}

			public Collection<ADMIN_ModifyLocation> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		
		String menuId="";
		String headerMenuName="";
		tableModel.setTable(getHtmlTableLocation(menuId,menuName,headerMenuName));
		String view = tableModel.render();
		if (view == null) {
		
			view=null;
		} else {
			
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				view=null;
			} else { 
				request.setAttribute("paginationData", view); 
				
			}
		}
		
		request.setAttribute("paginationData", view);			
		return new ModelAndView("ADMIN_ViewLocationDetails");

    }
	
	 private Table getHtmlTableLocation(final String menuId, String menuName,
				String headerMenuName) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException  { 
	    	HtmlTable htmlTable=new HtmlTable().caption(menuName);
	    	htmlTable.setStyleClass("table");
			 HtmlRow htmlRow = new HtmlRow();
			 htmlRow = new HtmlRow().uniqueProperty("id");
			 htmlTable.setRow(htmlRow);
		
			 
			 HtmlColumn checkboxCol=new HtmlColumn("CHECKBOX").title("Checkbox");	
			 checkboxCol.setFilterable(false);
			 checkboxCol.setCellEditor(new CellEditor() {
					public Object getValue(Object item, String property, int rowcount) {
						Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
								"START_COUNT_PAGE", rowcount);
						int rowid=Integer.parseInt((String)START_COUNT_PAGE);
						int finalRowID=rowid+rowcount-1;
						HtmlBuilder html = new HtmlBuilder();
						html.input().type("checkbox").id("checkbox"+finalRowID)
						.value("").onclick("javascript:editLocationRow(\'"+finalRowID+"\')").end();
						return html;
					}
				});
				 htmlRow.addColumn(checkboxCol);
				 
				 HtmlColumn CityCol=new HtmlColumn("CITY").title("City");
				 CityCol.setCellEditor(new CellEditor() {
					public Object getValue(Object item, String property, int rowcount) {	
						HtmlBuilder html = new HtmlBuilder();				
						Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
								"START_COUNT_PAGE", rowcount);
						Object CITY=new HtmlCellEditor().getValue(item,
								"CITY", rowcount);
						int rowid=Integer.parseInt((String)START_COUNT_PAGE);
						int finalRowID=rowid+rowcount-1;
						String locID="";
						if(locID!=null){
							locID=CITY.toString();
						}
						
						Object loationDetailID=new HtmlCellEditor().getValue(item, "LOCDETID", rowcount);
						Object loationID=new HtmlCellEditor().getValue(item, "LOCATION_ID", rowcount);
						html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'CITY"+finalRowID+"\')").styleClass("borderlessTextInput").size("30%").id("CITY"+finalRowID).name("CITY"+finalRowID).value(locID).end();
						html.append("<span class=\"red_text\" id=\"span_CITY"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
						html.append("<input type=\'hidden\' id=\'LOCDETID"+finalRowID+"\' value=\'"+loationDetailID.toString()+"\'></input>");
						html.append("<input type=\'hidden\' id=\'LOCATION_ID"+finalRowID+"\' value=\'"+loationID.toString()+"\'></input>");
						return html;
					}
				});	
				 htmlRow.addColumn(CityCol);
				 
				 				 
			 HtmlColumn BuildingCol=new HtmlColumn("BUILDING").title("Building");
			 BuildingCol.setCellEditor(new CellEditor() {
				public Object getValue(Object item, String property, int rowcount) {	
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object BUILDING=new HtmlCellEditor().getValue(item,
							"BUILDING", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;
					String locID="";
					if(locID!=null){
						locID=BUILDING.toString();
					}
					html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'BUILDING"+finalRowID+"\')").styleClass("borderlessTextInput").size("30%").id("BUILDING"+finalRowID).name("BUILDING"+finalRowID).value(locID).end();
					html.append("<span class=\"red_text\" id=\"span_BUILDING"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
					
					return html;
				}
			});	
			 htmlRow.addColumn(BuildingCol);
			 		 
			 HtmlColumn FloorCol=new HtmlColumn("FLOOR").title("Floor");
			 
			 FloorCol.setCellEditor(new CellEditor() {			
			
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object FLOOR=new HtmlCellEditor().getValue(item,
							"FLOOR", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;		
					String locID="";
					if(locID!=null){
						locID=FLOOR.toString();
					}
					html.input().readonly().onkeypress("javascript: return check(event)").onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'FLOOR"+finalRowID+"\')").styleClass("borderlessTextInput").size("35%").id("FLOOR"+finalRowID).name("FLOOR"+finalRowID).value(locID).end();
					html.append("<span class=\"red_text\" id=\"span_FLOOR"+finalRowID+"\" align=\"right\" style=\"display:none\"><b>Mandatory!!!</b></span>");
					
							
					return html;
				}
			});
			 
			 htmlRow.addColumn(FloorCol);
			 
			 HtmlColumn StatusCol=new HtmlColumn("STATUS").title("Status");
			 
			StatusCol.setCellEditor(new CellEditor() {			
			
				public Object getValue(Object item, String property, int rowcount) {
					HtmlBuilder html = new HtmlBuilder();				
					Object START_COUNT_PAGE=new HtmlCellEditor().getValue(item,
							"START_COUNT_PAGE", rowcount);
					Object STATUS=new HtmlCellEditor().getValue(item,
							"STATUS", rowcount);
					int rowid=Integer.parseInt((String)START_COUNT_PAGE);
					int finalRowID=rowid+rowcount-1;		
					String locStatus="";
					if(locStatus!=null){
						locStatus=STATUS.toString();
					}
					html.input().onchange("javascript:changeJSONArr(\'"+finalRowID+"\',\'STATUS"+finalRowID+"\')").styleClass("borderlessTextInput").size("15%").id("STATUS"+finalRowID).name("STATUS"+finalRowID).value(locStatus).readonly().end();				
					return html;
				}
			});
			 htmlRow.addColumn(StatusCol);
			 return htmlTable;
	 }
	 
	 public COMMON_Pagination<ADMIN_ModifyLocation> getFilteredResultLocation(Limit limit,
				int startCount, int pageNo, int maxRows, String dynamicQuery,int userTimeZoneToSend,String location_id,String status_val) {

			COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
			FilterSet filterSet = limit.getFilterSet();
			Collection<Filter> filters = filterSet.getFilters();
			for (Filter filter : filters) {
				String property = filter.getProperty();
				String value = filter.getValue();
				listPageSearchFilter.addFilter(property, value);

			}
			StringBuffer buffer = listPageSearchFilter.execute("AdminConsoleLocation");
			
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
					.getBean("listDao");
			    
			return listDAO.getLocationDetails(startCount, pageNo, maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend, location_id,status_val);

		}
	 
		private  void resetCacheAndResetWorkflowMethod() throws SecurityException, NoSuchMethodException, ConnectException{
			 ApplicationContext ctx = COMMON_AppContext.getCtx();
		       

				
			 
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
	 
	 @RequestMapping(value = "ModifyLocationAC.htm", method = RequestMethod.POST)
		public @ResponseBody
		void ModifyLocationAC(@RequestBody String jsonString,
				HttpServletResponse response, HttpServletRequest request)
				throws Exception {
	    	 JSONObject jsonarray = new JSONObject(jsonString);
		 	 String locationId=jsonarray.getString("locationId");
		 	String LocationName=jsonarray.getString("locationName");
		 	jsonarray=new JSONObject(jsonarray.getString("jsonString"));	 	
		 	jsonarray = new JSONObject(jsonarray.getString("location"));	
		 			 	
		 	JSONObject objGrp=new JSONObject();
		 	ADMIN_ModifyLocation locationMap=new ADMIN_ModifyLocation();
		 	List<ADMIN_ModifyLocation> locationMapList=new ArrayList<ADMIN_ModifyLocation>();
		 	Iterator<?> keys = jsonarray.keys();
		 	while( keys.hasNext() ){			
		 		 String key = (String)keys.next();	
		 		 if( jsonarray.get(key) instanceof JSONObject ){	            	
		            	objGrp=new JSONObject(jsonarray.getString(""+key));	
		            	locationMap.setLOCATION_ID(objGrp.get("LOCATION_ID"+key).toString());//Changed by sali
		            	locationMap.setLOCDETID(objGrp.get("LOCDETID"+key).toString());
		            	locationMap.setCITY(objGrp.get("CITY"+key).toString());
		            	locationMap.setBUILDING(objGrp.get("BUILDING"+key).toString());
		            	locationMap.setFLOOR(objGrp.get("FLOOR"+key).toString());
		            	locationMap.setSTATUS(objGrp.get("STATUS"+key).toString());
		            	locationMapList.add(locationMap);
		            	locationMap=new ADMIN_ModifyLocation();
		            }
		 	}
	
		 	 	
			ApplicationContext ctx = COMMON_AppContext.getCtx();
			ADMIN_SettingsDAO adminDAO = (ADMIN_SettingsDAO) ctx
					.getBean("GetgroupSettings");
			String userLoginId = (String) request.getSession().getAttribute(
			"userLoginId");
			

			String message=adminDAO.updateLocation(locationMapList,userLoginId,LocationName );
			Map<String, Object> responsedetails = new HashMap<String, Object>();
			responsedetails.put("message", message);
			resetCacheAndResetWorkflowMethod();
			JsonUtility.sendData(responsedetails, response);
					 	 
	 }	 
			 
	 }