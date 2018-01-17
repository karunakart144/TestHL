/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */
package com.igate.iconnect.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;
import org.jmesa.model.TableModel;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.igate.iconnect.BO.COMMON_Pagination;
import com.igate.iconnect.BO.HELPDESK_GroupMembersAvailability;
import com.igate.iconnect.BO.HELPDESK_Lock;
import com.igate.iconnect.BO.HELPDESK_ScoreDetail;
import com.igate.iconnect.BO.HelpDesk;
import com.igate.iconnect.BO.MailTracker;
import com.igate.iconnect.BO.TechCR;
import com.igate.iconnect.BO.User;
import com.igate.iconnect.constants.COMMON_ListPageSQLQueryConstants;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.COMMON_ListPageDAO;
import com.igate.iconnect.exception.COMMON_Exception;
import com.igate.iconnect.util.COMMON_AppContext;
import com.igate.iconnect.util.COMMON_ListPageSearch;
import com.igate.iconnect.util.COMMON_Toolbar;
import com.igate.iconnect.util.HELPDESK_CustomToolbar;
import com.igate.iconnect.util.JsonUtility;

@Controller
public class COMMON_ListPage_ReturnTypeVoidController {
	private static Logger log = Logger
	.getLogger(COMMON_ListPage_ReturnTypeVoidController.class);
	
	@ExceptionHandler
	public void handleExceptions(Exception e, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.error("",e);
		result.put("status", "Error");
		result.put("message", "System Error !!");
		JsonUtility.sendData(result, response);

	}
	
	//public List<Map<String, Object>> groupWorkLoadList = new ArrayList<Map<String, Object>>();
	String roleId = "";
	String loogedinUserID = "";
	String roleName = "";

	@RequestMapping(value = "/UNIVERSAL_ListPage.htm", method = RequestMethod.GET)
	public String showListPage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return getListPageData(model, request, response) ;
	}
	
	@RequestMapping(value = "/HELPDESK_SimilarTicketslistPage.htm", method = RequestMethod.GET)
	public void showSimilarTicketsListPage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String result =  getListPageData(model, request, response) ;
		JsonUtility.sendData(result, response);
	}
	
	@RequestMapping(value = "/HELPDESK_RelatedMailslistPage.htm", method = RequestMethod.GET)
	public void showrelatedMailsListPage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String result =  getListPageData(model, request, response) ;
		JsonUtility.sendData(result, response);
	}
	//Added to implement the tool to track EX employees quereis for HR function
	@RequestMapping(value = "/HELPDESK_RelatedMailslistPage_HR.htm", method = RequestMethod.GET)
	public void showrelatedMailsListPage_HR(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String result =  getListPageData(model, request, response) ;
		JsonUtility.sendData(result, response);
	}
	//END
	public String getListPageData(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException 
			{
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		final String menuName = request.getParameter("menuName");
		String headerMenuName = request.getParameter("parentMenuName");
		final long menuID = Long.parseLong(request.getParameter("menuId"));
		String userLoginId = (String) request.getSession().getAttribute(
				"userLoginId");
		loogedinUserID = userLoginId;

		User userBean = (User) request.getSession().getAttribute(
				userLoginId);
		// Defaulting userTime Zone to IST
		int userTimeZone = 67;
		if (userBean.getTimeZoneId() != null)
			userTimeZone = Integer.parseInt(userBean.getTimeZoneId());
		final int userTimeZoneToSend = userTimeZone;
		roleId = userBean.getUserRoleId();
		roleName = userBean.getUserRole();
		String query = MasterDataImpl.getQueryForMenuAndRole(String
				.valueOf(menuID), roleId);
		String userLoginIdToSend = "'" + userLoginId + "'";
		boolean inSimilarTickets = false;
		boolean inRelatedMails = false;
		query = query.replace("LOGGEDINUSER", userLoginIdToSend);
		if (request.getParameter("subject") != null
				&& request.getParameter("category") != null
				&& request.getParameter("subcategory") != null) {
			query = getSingleSearchQueryForSimilarTickets(request
					.getParameter("subject"), request.getParameter("category"),
					request.getParameter("subcategory"));
			inSimilarTickets = true;
		}//Added to implement the tool to track EX employees quereis for HR function 
		else if (menuName.equalsIgnoreCase("RelatedMails") && menuID == 0) {			
			if(null!=request.getParameter("functionName") && request.getParameter("functionName").equalsIgnoreCase("HR")){
				query = COMMON_ListPageSQLQueryConstants.relatedMailsQuery_HR
				+ request.getParameter("ticketid") + "'";
			}else{
			query = COMMON_ListPageSQLQueryConstants.relatedMailsQuery
					+ request.getParameter("ticketid") + "'";
			}
			inRelatedMails = true;
		}//END
		final String queryToPass = query;
		//Below code has been commentd as part of L2:4501
		/*long parentMenuId = 0;
		if (request.getParameter("parentMenuId") != null)
			parentMenuId = Long.parseLong(request.getParameter("parentMenuId"));*/
		/*Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap = MasterDataImpl.getHeaderMap(menuID);*/

		// For Pagination
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

		if (firstTime || pageNo == 1) {
			startCount = 1;
			// maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}

		if (menuID == 0) {
			startCount = 1;
			maxRows = 15;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		if (headerMenuName.equalsIgnoreCase("HelpDesk")) {
			if (menuID == 62 || menuID == 63 || menuID == 96) {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<HELPDESK_Lock> page = new COMMON_Pagination<HELPDESK_Lock>();

					public int getTotalRows(Limit limit) {
						page = getLockedFilteredResult(limit, startCountToPass,
								pageNoToPass, maxRowsToPass, queryToPass,
								userTimeZoneToSend);
						return page.getTotalCount();
					}

					public Collection<HELPDESK_Lock> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			} else if (menuID == 99) {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<HELPDESK_GroupMembersAvailability> page = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();

					public int getTotalRows(Limit limit) {
						page = getUseAvailabilityFilteredResult(limit,
								startCountToPass, pageNoToPass, maxRowsToPass,
								queryToPass, userTimeZoneToSend);
						return page.getTotalCount();
					}

					public Collection<HELPDESK_GroupMembersAvailability> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			} else if (menuID == 12 || menuID == 107 || menuID == 108) {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();

					public int getTotalRows(Limit limit) {
						page = getApprovalTicketsFilteredResult(limit,
								startCountToPass, pageNoToPass, maxRowsToPass,
								queryToPass, userTimeZoneToSend);
						return page.getTotalCount();
					}

					public Collection<HelpDesk> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			} else if (menuID == 114 || menuID == 118 || menuID == 119 || menuID ==120 || menuID==121) {
				/**********************************/
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();

					public int getTotalRows(Limit limit) {
						page = getMasterFilteredResult(limit, startCountToPass,
								pageNoToPass, maxRowsToPass, queryToPass,
								userTimeZoneToSend, menuName);
						return page.getTotalCount();
					}

					public Collection<HelpDesk> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
				
				
				/**********************************/
				
			}else if (menuID ==129){
				/*************Auto Assignment: Display Score List for Engineers********************/
				tableModel.setItems(new PageItems() {					
					COMMON_Pagination<HELPDESK_ScoreDetail> page=new COMMON_Pagination<HELPDESK_ScoreDetail>();
					public int getTotalRows(Limit limit) {
						page=getScoreListForEngineerResult(limit,startCountToPass,pageNoToPass,maxRowsToPass,queryToPass,userTimeZoneToSend);
						return page.getTotalCount();
					}	
					
					public Collection<HELPDESK_ScoreDetail> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
				/**************END Auto Assignment: Display Score List for Engineers***************/
			}
			
			else {
				if (menuID == 46 || menuID == 13 || menuID == 149
						|| menuID == 152 || menuID == 172 || menuID == 173
						|| menuID == 174 || menuID == 175 || menuID == 176
						|| menuID == 177 || menuID == 178 || menuID == 179
						|| menuID == 180 || menuID == 181 || menuID == 182
						|| menuID == 183 || menuID == 184 || menuID == 185 || menuID == 186
						|| menuID == 187 || menuID == 188 || menuID == 189 || menuID == 190
						|| menuID == 191 || menuID == 192 || menuID == 193 || menuID == 194 || menuID == 195) {
					// menuID: 46 MyTeamBin(Open), menuID:13 AssignedToMe(Open)
					tableModel.setItems(new PageItems() {
						COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();

						public int getTotalRows(Limit limit) {
							page = getHDFilteredColourCodeResult(limit,
									startCountToPass, pageNoToPass,
									maxRowsToPass, queryToPass,
									userTimeZoneToSend, menuName);
							return page.getTotalCount();
						}

						public Collection<HelpDesk> getItems(Limit limit) {
							return page.getPageItems();
						}
					});
				} else {
					tableModel.setItems(new PageItems() {
						COMMON_Pagination<HelpDesk> page = new COMMON_Pagination<HelpDesk>();

						public int getTotalRows(Limit limit) {
							page = getHDFilteredResult(limit, startCountToPass,
									pageNoToPass, maxRowsToPass, queryToPass,
									userTimeZoneToSend, menuName);
							return page.getTotalCount();
						}

						public Collection<HelpDesk> getItems(Limit limit) {
							return page.getPageItems();
						}
					});
				}

			}
		} else if (headerMenuName.equalsIgnoreCase("Tech-CR")) {
			if (menuID == 63) {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<HELPDESK_Lock> page = new COMMON_Pagination<HELPDESK_Lock>();

					public int getTotalRows(Limit limit) {
						page = getLockedFilteredResult(limit, startCountToPass,
								pageNoToPass, maxRowsToPass, queryToPass,
								userTimeZoneToSend);
						return page.getTotalCount();
					}

					public Collection<HELPDESK_Lock> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			} else {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<TechCR> page = new COMMON_Pagination<TechCR>();

					public int getTotalRows(Limit limit) {
						page = getTechCRFilteredResult(limit, startCountToPass,
								pageNoToPass, maxRowsToPass, queryToPass,
								userTimeZoneToSend);
						return page.getTotalCount();
					}

					public Collection<TechCR> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			}
		} else if (headerMenuName.equalsIgnoreCase("Mail-Tracker")
				|| headerMenuName.equalsIgnoreCase("NOC-Alerts")) {
			if (menuID == 106) {
				{
					tableModel.setItems(new PageItems() {
						COMMON_Pagination<MailTracker> page = new COMMON_Pagination<MailTracker>();

						public int getTotalRows(Limit limit) {
							page = getMailLockedFilteredResult(limit,
									startCountToPass, pageNoToPass,
									maxRowsToPass, queryToPass,
									userTimeZoneToSend);
							return page.getTotalCount();
						}

						public Collection<MailTracker> getItems(Limit limit) {
							return page.getPageItems();
						}
					});
				}

			} else {
				tableModel.setItems(new PageItems() {
					COMMON_Pagination<MailTracker> page = new COMMON_Pagination<MailTracker>();

					public int getTotalRows(Limit limit) {
						page = getMailTrackerResult(limit, startCountToPass,
								pageNoToPass, maxRowsToPass, queryToPass);
						return page.getTotalCount();
					}

					public Collection<MailTracker> getItems(Limit limit) {
						return page.getPageItems();
					}
				});
			}
		}
		if (tableModel.isExporting()) {
			tableModel.setTable(getExportTable());
		} else {
			if (inSimilarTickets) {
				tableModel.setTable(getHtmlTableforSimilarTickets(String
						.valueOf(menuID), menuName, headerMenuName));
			} else if (inRelatedMails) {
				tableModel.setTable(getHtmlTableforRelatedMails(String
						.valueOf(menuID), menuName, headerMenuName));
			} else if(menuID == 114 || menuID == 118  || menuID == 119 || menuID ==120 || menuID==121){
				tableModel.setTable(getHtmlTableforMaster(String
						.valueOf(menuID), menuName, headerMenuName));
			}else{			
				tableModel.setTable(getHtmlTable(String.valueOf(menuID),
						menuName, headerMenuName));
			}
		}
		// For providing Approve Icon for IT Security,IT Function Head,Need More Info,Information Provided Menus
		if (menuID == 12 || menuID == 51 || menuID == 49 || menuID == 107 || menuID == 108 || menuID==149) {
			tableModel.setToolbar(new HELPDESK_CustomToolbar(userBean.getUserRole(),menuID));
		} else if (!inRelatedMails
				&& !userBean.getUserRole().equalsIgnoreCase("User")) {
			tableModel.setToolbar(new COMMON_Toolbar());
		}
			
		
		String view = tableModel.render();
		if (view == null) {
			return null; // an export
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				response.getOutputStream().write(contents);
				return null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationData", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		if (menuID == 0
				&& request.getParameter("parentMenuName").equalsIgnoreCase(
						"HelpDesk")) {
			return view;
		} else if (menuID == 0
				&& request.getParameter("parentMenuName").equalsIgnoreCase(
						"Mail-Tracker")) {
			return view;
		} else {
			return "UNIVERSAL_ListPage";
		}

		// For Pagination
		/*
		 * model.put("req", requestList); return new
		 * ModelAndView("commonlistPage");
		 */

	}


	@RequestMapping(value = "/groupList", method = RequestMethod.GET)
	public @ResponseBody
	void getGroupList(@RequestParam String menuId,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		if (Integer.parseInt(menuId) == 1) {
			groupList = MasterDataImpl.getIHDGroupMaster();
		}
		JsonUtility.sendData(groupList, response);

	}

	@RequestMapping(value = "/groupMemberWorkLoad", method = RequestMethod.GET)
	public @ResponseBody
	void getGroupMemberWorkLoad(@RequestParam String groupMember,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		List<Map<String, Object>> groupMemberWorkLoadList = new ArrayList<Map<String, Object>>();
		groupMemberWorkLoadList = listDAO
				.getGroupMamberWorkLoadList(groupMember);
		JsonUtility.sendData(groupMemberWorkLoadList, response);

	}

	@RequestMapping(value = "/groupMember", method = RequestMethod.GET)
	public @ResponseBody
	void getGroupMember(@RequestParam String groupName,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> groupMemberList = new ArrayList<Map<String, Object>>();
		groupMemberList = MasterDataImpl.getIHDGroupMemberID(groupName);
		JsonUtility.sendData(groupMemberList, response);

	}

	@RequestMapping(value = "/groupWorkLoad.htm", method = RequestMethod.GET)
	public @ResponseBody
	void getGroupWorkLoad(@RequestParam String groupName,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		List<Map<String, Object>> groupWorkLoadList = new ArrayList<Map<String, Object>>();
		groupWorkLoadList = listDAO.getGroupWorkLoadList(groupName.trim()
				.toUpperCase());
		JsonUtility.sendData(groupWorkLoadList, response);

	}

	@RequestMapping(value = "/assignSave", method = RequestMethod.GET)
	public @ResponseBody
	void assignSave(@RequestParam String selectedTicketArray,
			String newAssignementNameWithID, String groupId,
			HttpServletResponse response, HttpServletRequest request) {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		// long group_id = 0l;

		// groupMemberList = MasterDataImpl.getIHDGroupMemberID(groupName);
		/*
		 * for (Iterator<Map<String, Object>> iterator = groupMemberList
		 * .iterator(); iterator.hasNext();) { Map<String, Object> stringObj =
		 * iterator.next(); if
		 * (stringObj.get("NAME").toString().trim().toUpperCase()
		 * .equalsIgnoreCase(groupName.trim().toUpperCase())) { group_id =
		 * Long.parseLong(stringObj.get("GROUP_ID").toString()); }
		 * 
		 * }
		 */
		String[] selected_ticket = selectedTicketArray.split(",");
		String newAssignement_id = newAssignementNameWithID.substring(
				newAssignementNameWithID.indexOf("(") + 1,
				newAssignementNameWithID.indexOf(")"));
		long ticket_Id = 0l;
		int result = 0;
		String msg = "";
		String loginID = request.getSession().getAttribute("userLoginId")
				.toString();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		// int workLoad = selected_ticket.length - 1;
		for (int i = 1; i < selected_ticket.length; i++) {
			ticket_Id = Long.parseLong(selected_ticket[i]);
			result = listDAO.saveAssign(newAssignement_id, ticket_Id, groupId,
					loginID);
		}
		if (result == 1) {
			msg = "Success";
			/*
			 * for (Iterator<Map<String, Object>> iterator = groupWorkLoadList
			 * .iterator(); iterator.hasNext();) { Map<String, Object> stringObj
			 * = iterator.next();
			 * 
			 * if (stringObj.get("WORK_LOAD") != null) {
			 * 
			 * workLoad = Integer.parseInt(stringObj.get("WORK_LOAD")
			 * .toString()) + workLoad; }
			 * 
			 * } int result_update = listDAO.saveWorkLoad(workLoad,
			 * newAssignement_id, group_id); if (result_update == 1) msg =
			 * "Success"; else msg = "WorkLoad updation is not done";
			 */} else
			msg = "Assignment is not done";
		JsonUtility.sendData(msg, response);
	}

	public Table getExportTable() {
		return null;
	}

	public COMMON_Pagination<HelpDesk> getHDFilteredResult(Limit limit, int startCount,
			int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend, String menuName) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("HelpDesk");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		String orderBy = "DESC";
		ResourceBundle bundle = ResourceBundle.getBundle("sla");
		if (menuName.contains(bundle
				.getString("ascending_order_for_open_state"))
				|| menuName.contains(bundle
						.getString("ascending_order_for_queue_state"))) {
			orderBy = "ASC";
		}
		
		return listDAO.getHDTicketList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend, orderBy,menuName);

	}
	
	public COMMON_Pagination<HelpDesk> getHDFilteredColourCodeResult(Limit limit, int startCount,
			int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend, String menuName) {
		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("HelpDesk");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		String orderBy = "DESC";
		ResourceBundle bundle = ResourceBundle.getBundle("sla");
		if (menuName.contains(bundle
				.getString("ascending_order_for_open_state"))
				|| menuName.contains(bundle
						.getString("ascending_order_for_queue_state"))) {
			orderBy = "ASC";
		}
		return listDAO.getHDTicketColourCodeList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend, orderBy);

	}
	public COMMON_Pagination<HelpDesk> getMasterFilteredResult(Limit limit, int startCount,
			int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend, String menuName) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("HelpDesk");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		String orderBy = "DESC";
	/*	ResourceBundle bundle = ResourceBundle.getBundle("sla");
		if (menuName.contains(bundle
				.getString("ascending_order_for_open_state"))
				|| menuName.contains(bundle
						.getString("ascending_order_for_queue_state"))) {
			orderBy = "ASC";
		}*/
		return listDAO.getMasterTicketList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend, orderBy);

	}

	public COMMON_Pagination<HELPDESK_Lock> getLockedFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("LockedRequest");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getLockedRequestList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend);

	}
	
	public COMMON_Pagination<MailTracker> getMailLockedFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend) {
		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("Mail-Tracker");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getMailLockedRequestList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend);
	}

	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getUseAvailabilityFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("UserAvailability");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getUseAvailabilityRequestList(startCount, pageNo,
				maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend);

	}
	/*Auto Assignment:Get the Score detail */
	public COMMON_Pagination<HELPDESK_ScoreDetail> getScoreListForEngineerResult(Limit limit,int startCount,int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend){
		COMMON_ListPageSearch listPageSearchFilter=new COMMON_ListPageSearch();
		FilterSet filterSet=limit.getFilterSet();
		Collection<Filter> filters=filterSet.getFilters();
		for(Filter filter : filters) {
			String property=filter.getProperty();
			String value=filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer=listPageSearchFilter.execute("EngineerScoreDetail");
		ApplicationContext ctx=COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO=(COMMON_ListPageDAO)ctx.getBean("listDao");
		return listDAO.getEngineerScoreList(startCount,pageNo,maxRows,buffer.toString(),dynamicQuery,userTimeZoneToSend);
	}
	/*END : Auto Assignment:Get the Score detail */

	public COMMON_Pagination<HelpDesk> getApprovalTicketsFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery,
			int userTimeZoneToSend) {
		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("HelpDesk");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		ResourceBundle bundle = ResourceBundle.getBundle("sla");
		String orderBy = bundle.getString("WaitingForApproval_OrderBy");

		return listDAO.getApprovalTicketsRequestList(startCount, pageNo,
				maxRows, buffer.toString(), dynamicQuery, userTimeZoneToSend,
				orderBy);

	}

	public COMMON_Pagination<TechCR> getTechCRFilteredResult(Limit limit, int startCount,
			int pageNo, int maxRows, String dynamicQuery, int userTimeZoneToSend) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter.execute("Tech-CR");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getTechCRTicketList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery, userTimeZoneToSend);

	}

	public COMMON_Pagination<MailTracker> getMailTrackerResult(Limit limit,
			int startCount, int pageNo, int maxRows, String dynamicQuery) {

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);

		}
		StringBuffer buffer = listPageSearchFilter.execute("Mail-Tracker");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getMailTrackerList(startCount, pageNo, maxRows, buffer
				.toString(), dynamicQuery);

	}

	public Table getHtmlTable(final String menuId, String menuName,
			String headerMenuName) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		HtmlRow htmlRow = new HtmlRow();
		htmlTable.setRow(htmlRow);

		Class thisClass = null;
		Method thisMethod = null;
		Object iClass = null;
		Class params[] = {};
		Object paramsObj[] = {};
		//try {
			thisClass = Class
					.forName("com.igate.iconnect.util.COMMON_ListPageColumnGenerator");
			Constructor c = thisClass.getConstructor(String.class,
					String.class, String.class, String.class, String.class);

			iClass = c.newInstance(menuId, roleId, headerMenuName,
					loogedinUserID, roleName);

			ApplicationContext ctx = COMMON_AppContext.getCtx();

			COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
					.getBean("GetMasterData");

			List<Map<String, Object>> columnsforMenu = MasterDataImpl
					.getColumnsForMenu(Integer.parseInt(menuId));

			Map<Integer, String> methodForColumns = getMethodForColumns(columnsforMenu);

			COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
					.getBean("listDao");

			List<Map<String, Object>> userSelectionForMenu = listDAO
					.getUserSelectionMenuListPage(menuId, loogedinUserID,
							roleId);

			List<Map<String, Object>> columnstoBeDisplay = null;
			if (userSelectionForMenu.size() <= 0) {
				columnstoBeDisplay = MasterDataImpl
						.getDefaultColumnsForMenu(Integer.parseInt(menuId));
			} else {
				columnstoBeDisplay = userSelectionForMenu;
			}
			for (Map<String, Object> rowobj : columnstoBeDisplay) {
				int COLUMN_ID = (Integer) rowobj.get("COLUMN_ID");
				thisMethod = thisClass.getDeclaredMethod(methodForColumns
						.get(COLUMN_ID), params);
				htmlRow.addColumn((HtmlColumn) thisMethod.invoke(iClass,
						paramsObj));
			}
		return htmlTable;
	}

	public Table getHtmlTableforSimilarTickets(final String menuId,
			String menuName, String headerMenuName) {
		HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		HtmlRow htmlRow = new HtmlRow();
		htmlTable.setRow(htmlRow);

		HtmlColumn attachment = new HtmlColumn("ATTACHMENT").title("A");
		attachment.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object attachment = new HtmlCellEditor().getValue(item,
						"ATTACHMENT", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (attachment.toString().equalsIgnoreCase("Yes")) {

					html.img().src("images/Attachment.png").title("Attachment")
							.close();
				}
				return html.toString();
			}
		});
		attachment.setFilterable(false);
		htmlRow.addColumn(attachment);

		HtmlColumn ticketId = new HtmlColumn("TICKET_ID").title("Ticket #");
		ticketId.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, property,
						rowcount);
				Object categoryId = new HtmlCellEditor().getValue(item,
						"CATEGORY_ID", rowcount);
				Object subcategoryId = new HtmlCellEditor().getValue(item,
						"SUBCATEGORY_ID", rowcount);
				Object subject = new HtmlCellEditor().getValue(item, "SUBJECT",
						rowcount);
				Object function = new HtmlCellEditor().getValue(item,
						"FUNCTION", rowcount);
				HtmlBuilder html = new HtmlBuilder(); 
				html.a().href().quote().append("HELPDESK_Edit.htm?id=")
						.append(value).append("&subject=").append(subject)
						.append("&category=").append(categoryId).append(
								"&subcategory=").append(subcategoryId).append(
								"&function=").append(function).quote().close();
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		htmlRow.addColumn(ticketId);

		HtmlColumn priority = new HtmlColumn("PRIORITY").title("Priority");
		htmlRow.addColumn(priority);

		HtmlColumn requestor = new HtmlColumn("REQUESTOR_NAME")
				.title("Requested By");
		htmlRow.addColumn(requestor);

		HtmlColumn requestorLocation = new HtmlColumn("LOCATION_AREA")
				.title("Current Location");
		htmlRow.addColumn(requestorLocation);

		HtmlColumn subject = new HtmlColumn("SUBJECT").title("Subject");
		htmlRow.addColumn(subject);

		HtmlColumn creationDate = new HtmlColumn("CREATED_DATE")
				.title("Creation Date");
		htmlRow.addColumn(creationDate);

		HtmlColumn ect = new HtmlColumn("ECT").title("ECT");
		htmlRow.addColumn(ect);

		HtmlColumn workFlowStatus = new HtmlColumn("WORKFLOW_STATE")
				.title("Status");
		htmlRow.addColumn(workFlowStatus);

		return htmlTable;
	}

	public Table getHtmlTableforRelatedMails(final String menuId,
			String menuName, String headerMenuName) {
		HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		HtmlRow htmlRow = new HtmlRow();
		htmlTable.setRow(htmlRow);

		HtmlColumn from_address = new HtmlColumn("FROM_ADDRESS").title("From");
		htmlRow.addColumn(from_address);

		HtmlColumn SUBJECT = new HtmlColumn("SUBJECT").title("Subject");
		htmlRow.addColumn(SUBJECT);

		HtmlColumn DESCRIPTION = new HtmlColumn("DESCRIPTION")
				.title("Description");
		htmlRow.addColumn(DESCRIPTION);

		HtmlColumn RECEIVED_DATE = new HtmlColumn("RECEIVED_DATE")
				.title("Received Date");
		RECEIVED_DATE.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, property,
						rowcount);

				HtmlBuilder html = new HtmlBuilder();
				html.append(CustomDateFormatConstants.convertToEST(String
						.valueOf(value)));
				return html.toString();
			}
		});
		htmlRow.addColumn(RECEIVED_DATE);
		
		//Following two lines added by gk820877 on 5/12/2015
		//To add source column in mail tracker page
		HtmlColumn SOURCE = new HtmlColumn("SOURCE").title("Source");
		htmlRow.addColumn(SOURCE);

		return htmlTable;
	}
	
	public Table getHtmlTableforMaster(final String menuId,
			String menuName, String headerMenuName) {
		HtmlTable htmlTable = new HtmlTable().caption(menuName).width("100%");
		HtmlRow htmlRow = new HtmlRow();
		htmlTable.setRow(htmlRow);		
		
		HtmlColumn ticketId = new HtmlColumn("TICKET_ID").title("Ticket #");
		ticketId.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, property,
						rowcount);
				HtmlBuilder html = new HtmlBuilder(); 
				html.a().href().quote().append("MASTER_Edit.htm?id=")
						.append(value).quote().close();
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		htmlRow.addColumn(ticketId);
		
		HtmlColumn requestedBy = new HtmlColumn("REQUESTOR_NAME").title("Requested By");
		htmlRow.addColumn(requestedBy);
		
		HtmlColumn createdDate=new HtmlColumn("CREATED_DATE").title("Created Date");
		htmlRow.addColumn(createdDate);

		HtmlColumn function=new HtmlColumn("FUNCTION").title("Function");
		htmlRow.addColumn(function);
		
		HtmlColumn subject=new HtmlColumn("SUBJECT").title("Subject");
		htmlRow.addColumn(subject);
		
		HtmlColumn description=new HtmlColumn("DESCRIPTION").title("Description");
		htmlRow.addColumn(description);		
		
		HtmlColumn ect=new HtmlColumn("ECT").title("ECT");
		ect.setFilterable(false);
		htmlRow.addColumn(ect);
		
		HtmlColumn category=new HtmlColumn("CATEGORY").title("Category");
		htmlRow.addColumn(category);
		
		HtmlColumn subCategory=new HtmlColumn("SUBCATEGORY").title("Sub Category");
		htmlRow.addColumn(subCategory);
		
		HtmlColumn WorkFlowState=new HtmlColumn("WORKFLOW_STATE").title("Status");
		htmlRow.addColumn(WorkFlowState);


		return htmlTable;
	}
	
	private String getSingleSearchQueryForSimilarTickets(String subject,
			String categoryID, String subcategoryID) {

		StringBuilder sql = new StringBuilder();
		sql
				.append("SELECT ISNULL(ticket.TICKET_ID, '-') AS 'TICKET_ID',ISNULL(loc.CITY+'-'+loc.AREA, '-') AS 'LOCATION_AREA',ISNULL(ticket.CREATED_DATE, '-') AS 'CREATED_DATE',ISNULL(ihdfunction.NAME, '-') AS 'FUNCTION',ISNULL(ticket.SUBJECT, '-') AS 'SUBJECT',ISNULL(assignedUsr.NAME+'('+assignedUsr.EMPLOYEE_ID+')', '-') AS 'ASSIGNED_TO',ISNULL(workflow.NAME, '-') AS 'WORKFLOW_STATE',ticket.ECT AS 'ECT',ISNULL(ticket.ASSIGNED_GROUP, '-') AS 'ASSIGNED_GROUP_ID',ISNULL(ihdsubcategory.NAME, '-') AS 'SUBCATEGORY',ISNULL(ihdcategory.NAME, '-') AS 'CATEGORY',ISNULL(PM.DESCRIPTION, '-') AS 'PRIORITY',ISNULL(usr.NAME+'('+usr.EMPLOYEE_ID+')', '-') AS 'REQUESTOR_NAME',ISNULL(usr.ORGANIZATION, '-') AS 'ORGANIZATION',ticket.SLA_STATUS AS 'SLA_STATUS',ticket.CLOSED_DATE AS 'CLOSED_DATE',ticket.DESCRIPTION AS 'DESCRIPTION',case when ATTACHMENT_NAME is not null then 'Yes' else 'No' END as 'ATTACHMENT',ticket.FUNCTION_ID AS 'FUNCTION_ID',ticket.PRIORITY_ID AS 'PRIORITY_ID',ticket.LOCATION_ID AS 'LOCATION_ID' ,ticket.SUB_CATEGORY_ID AS 'SUB_CATEGORY_ID',ticket.LOC_DET_ID as 'LOC_DET_ID',case when ihdsubcategory.IS_CHANGE_REQUEST='1' then 'Yes' else 'No' end as 'IS_CHANGE_REQUEST',case when (select distinct ticket.TICKET_ID from IC_LOCKED_TICKET_DETAILS ltd where ltd.REFERENCE_ID=CAST(ticket.TICKET_ID AS VARCHAR(10))) is not null then 'Yes' else 'No' END as 'LOCK',ISNULL(lockUser.NAME+'('+lockUser.EMPLOYEE_ID+')', '-') AS 'LOCKED_BY',case when (select MEMBER_ID from IC_IHD_PREMIUM_MEMBERS pm where pm.FUNCTION_ID=ticket.FUNCTION_ID and pm.MEMBER_ID=ticket.REQUESTED_BY and pm.IS_ACTIVE=1) is not null then 'Yes' else 'No' END as 'PREMIUM' FROM IC_IHD_TICKET_DETAILS AS ticket LEFT OUTER JOIN IC_IHD_CATEGORY_MASTER AS ihdfunction ON ticket.FUNCTION_ID = ihdfunction.CATEGORY_ID LEFT OUTER JOIN  IC_LOCATION_MASTER AS loc ON ticket.LOCATION_ID = loc.LOCATION_ID LEFT OUTER JOIN IC_IHD_CATEGORY_MASTER AS ihdcategory ON ticket.CATEGORY_ID = ihdcategory.CATEGORY_ID LEFT OUTER JOIN IC_IHD_CATEGORY_MASTER AS ihdsubcategory ON ticket.SUB_CATEGORY_ID = ihdsubcategory.CATEGORY_ID LEFT OUTER JOIN IC_WORKFLOW_STATE_MASTER AS workflow ON ticket.WORKFLOW_STATE = workflow.STATE_ID LEFT OUTER JOIN IC_USER_DETAILS AS usr ON ticket.REQUESTED_BY = usr.EMPLOYEE_ID LEFT OUTER JOIN IC_USER_DETAILS AS assignedUsr ON ticket.ASSIGNED_TO = assignedUsr.EMPLOYEE_ID LEFT OUTER JOIN IC_PRIORITY_MASTER AS PM ON ticket.PRIORITY_ID = PM.PRIORITY_ID LEFT OUTER JOIN IC_IHD_TICKET_ATTACHMENT_DETAILS ticketAttachments ON ticketAttachments.TICKET_ID=ticket.TICKET_ID LEFT OUTER JOIN IC_LOCKED_TICKET_DETAILS AS lock ON lock.REFERENCE_ID=CAST(ticket.TICKET_ID AS VARCHAR(10)) LEFT OUTER JOIN IC_USER_DETAILS AS lockUser ON lock.LOCKED_BY = lockUser.EMPLOYEE_ID WHERE  ticket.TICKET_ID in (select top(10) ticket1.TICKET_ID from IC_IHD_TICKET_DETAILS ticket1 WHERE  (ticket1.WORKFLOW_STATE IN ");
		sql.append("(6,10,23,20)) AND ");
		if (subject.equalsIgnoreCase("")) {
			sql.append("(ticket1.SUBJECT like ''");
		} else {
			sql.append("(ticket1.SUBJECT like '%");
			sql.append(subject);
			sql.append("%'");
		}

		if (categoryID.equalsIgnoreCase("")) {
			sql.append(" or ticket1.CATEGORY_ID like ''");
		} else {
			sql.append(" or ticket1.CATEGORY_ID like '%");
			sql.append(categoryID);
			sql.append("%'");
		}

		if (subcategoryID.equalsIgnoreCase("")) {
			sql.append(" or ticket1.SUB_CATEGORY_ID like ''");
		} else {
			sql.append(" or ticket1.SUB_CATEGORY_ID like '%");
			sql.append(subcategoryID);
			sql.append("%'");
		}
		sql.append(")) $ORDER BY ticket.TICKET_ID ");
		return sql.toString();
	}

	@RequestMapping(value = "/getColumnListForMenu.htm", method = RequestMethod.GET)
	public void getColumnListForMenu(@RequestParam String json,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		JSONObject jsonobj = new JSONObject(json);
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> columnsforMenu = MasterDataImpl
				.getColumnsForMenu(Integer.parseInt(jsonobj.get("MENU_ID")
						.toString()));
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		List<Map<String, Object>> userSelectionForMenu = listDAO
				.getUserSelectionMenuListPage(
						jsonobj.get("MENU_ID").toString(), jsonobj.get(
								"EMPLOYEE_ID").toString(), jsonobj.get(
								"ROLE_ID").toString());

		List<Map<String, Object>> defaultsColumnsForMenu = MasterDataImpl
				.getDefaultColumnsForMenu(Integer.parseInt(jsonobj.get(
						"MENU_ID").toString()));

		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		result.put("MenuColumnList", columnsforMenu);
		result.put("UserSelectionList", userSelectionForMenu);
		result.put("DefaultColumnsForMenu", defaultsColumnsForMenu);
		JsonUtility.sendData(result, response);
	}
	
	@RequestMapping(value = "/getChildTicketList.htm", method = RequestMethod.GET)
	public void getMasterTicketList(HttpServletRequest request, HttpServletResponse response)
			throws JSONException {
	
		//List<Map<String, Object>> masterTicketList = MasterDaoImpl.getMasterTicketList();
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		//result.put("MasterTicketList", masterTicketList);
		JsonUtility.sendData(result, response);
	}

	@RequestMapping(value = "/saveSelectedColumnListForMenu.htm", method = RequestMethod.GET)
	public void saveSelectedColumnListForMenu(@RequestParam String json,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException {

		JSONArray jsonarr = new JSONObject(json)
				.getJSONArray("USER_SELECTION_FOR_MENU");

		ArrayList<Object> ArgsListForInsert = new ArrayList<Object>();
		ArrayList<Object> ArgsListForUpdate = new ArrayList<Object>();
		ArrayList<Object> ArgsListForDelete = new ArrayList<Object>();

		for (int i = 0; i < jsonarr.length(); i++) {
			JSONObject jsonobj = jsonarr.getJSONObject(i);
			String mode = jsonobj.get("MODE").toString();
			if (mode.equalsIgnoreCase("I")) {
				Object[] GenArgs = new Object[] {
						jsonobj.get("COLUMN_ID").toString(),
						jsonobj.get("DISPLAY_ORDER").toString(),
						jsonobj.get("MENU_ID").toString(),
						jsonobj.get("EMPLOYEE_ID").toString(),
						jsonobj.get("ROLE_ID").toString(),
						jsonobj.get("EMPLOYEE_ID").toString() };
				ArgsListForInsert.add(GenArgs);
			} else if (mode.equalsIgnoreCase("U")) {
				Object[] GenArgs = new Object[] {
						jsonobj.get("DISPLAY_ORDER").toString(),
						jsonobj.get("COLUMN_ID").toString(),
						jsonobj.get("MENU_ID").toString(),
						jsonobj.get("EMPLOYEE_ID").toString(),
						jsonobj.get("ROLE_ID").toString() };
				ArgsListForUpdate.add(GenArgs);
			} else if (mode.equalsIgnoreCase("D")) {
				Object[] GenArgs = new Object[] {
						jsonobj.get("COLUMN_ID").toString(),
						jsonobj.get("MENU_ID").toString(),
						jsonobj.get("EMPLOYEE_ID").toString(),
						jsonobj.get("ROLE_ID").toString() };
				ArgsListForDelete.add(GenArgs);
			}
		}
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		List<Map<String, Object>> dataToSave = new ArrayList<Map<String, Object>>();

		if (ArgsListForInsert.size() > 0) {
			Map<String, Object> insertmap = new HashMap<String, Object>();
			insertmap.put("QUERY",
					COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_INSERT);
			insertmap.put("ARGLIST", ArgsListForInsert);
			insertmap
					.put(
							"ARGTYPE",
							COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_INSERT_ARGTYPES);
			dataToSave.add(insertmap);
		}
		if (ArgsListForUpdate.size() > 0) {
			Map<String, Object> updatemap = new HashMap<String, Object>();
			updatemap.put("QUERY",
					COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_UPDATE);
			updatemap.put("ARGLIST", ArgsListForUpdate);
			updatemap
					.put(
							"ARGTYPE",
							COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_UPDATE_ARGTYPES);
			dataToSave.add(updatemap);
		}
		if (ArgsListForDelete.size() > 0) {
			Map<String, Object> deletemap = new HashMap<String, Object>();
			deletemap.put("QUERY",
					COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_DELETE);
			deletemap.put("ARGLIST", ArgsListForDelete);
			deletemap
					.put(
							"ARGTYPE",
							COMMON_ListPageSQLQueryConstants.IC_USER_SELECTION_LISTPAGE_DELETE_ARGTYPES);
			dataToSave.add(deletemap);
		}
		String output = "";
			output = listDAO.saveUserSelectionMenuList(dataToSave);

		JsonUtility.sendData(output, response);
	}
	public Map<Integer, String> getMethodForColumns(
			List<Map<String, Object>> columnsforMenu) {
		Map<Integer, String> methodsForColumn = new HashMap<Integer, String>();

		for (Map<String, Object> rowobj : columnsforMenu) {
			int COLUMN_ID = (Integer) rowobj.get("COLUMN_ID");
			String methodName = (String) rowobj.get("METHOD_NAME");
			methodsForColumn.put(COLUMN_ID, methodName);
		}
		return methodsForColumn;
	}
	@RequestMapping(value = "/HELPDESK_GroupMembersAvailability.htm", method = RequestMethod.GET)
	public ModelAndView userAvailabilityy(
			@RequestParam("employeeId") String employeeId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {

		int b1 = employeeId.indexOf("(");
		int b2 = employeeId.indexOf(")");
		final String empId = employeeId.substring(b1 + 1, b2);
		

		// Defaulting userTime Zone to IST
		//int userTimeZone = 67;
		final int userTimeZoneToSend = 67;

		// For Pagination
		TableModel tableModel = new TableModel("loggedUser", request, response);
		
		tableModel.setExportTypes(ExportType.JEXCEL);
		int pageNo = 1;
		int maxRows = 15;
		boolean firstTime = false;
		try {
			pageNo = Integer.parseInt(request.getParameter("loggedUser_p_"));
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

		if (firstTime || pageNo == 1) {
			startCount = 1;
			// maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		final String fromDate ;
		final String toDate;
		if(request.getParameter("frmDate")!=null) 
		{
			fromDate = request.getParameter("frmDate");			
		}else
		{
			fromDate = null;
		}
		if(request.getParameter("tDate")!=null)
		{			
			toDate = request.getParameter("tDate");			
		}else
		{
			toDate = null;
		}
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<HELPDESK_GroupMembersAvailability> page = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();

			public int getTotalRows(Limit limit) {
				try {
					page = getUserFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, empId, userTimeZoneToSend,
							fromDate, toDate);
				} catch (ParseException e) {					
					throw new COMMON_Exception("ParseException");
				}
				return page.getTotalCount();
			}

			public Collection<?> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		if (tableModel.isExporting()) {			
			tableModel.setItems(new PageItems() {		
				COMMON_Pagination<HELPDESK_GroupMembersAvailability> newPage = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();				
				public int getTotalRows(Limit limit) {
					try{						
					int countOfRows=getTotalNumOfRows(limit,empId,fromDate,toDate);				
					newPage = getUserFilteredResult(limit, startCountToPass,
							pageNoToPass, countOfRows, empId, userTimeZoneToSend,
							fromDate, toDate);
					}catch (ParseException e) {						
						throw new COMMON_Exception("ParseException");
					}
					return newPage.getTotalCount();
				}				
				public Collection<?> getItems(Limit arg0) {
					return newPage.getPageItems();
				}
			});
			tableModel.setTable(getExportTableLoginReport());	
		} else {
			tableModel.setTable(getHtmlTableforLoggedInUsers());
		}

		String view = tableModel.render();
		if (view == null) {
			return null; // an export
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationDat", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		HELPDESK_GroupMembersAvailability userAvailability = new HELPDESK_GroupMembersAvailability();
		userAvailability.setEMPLOYEE_NAME(employeeId);
		userAvailability.setFROM_DATE(null);
		userAvailability.setTO_DATE(null);
		request.setAttribute("employeeId", employeeId);
		model.addAttribute("UserAvailability", userAvailability);
		return new ModelAndView("HELPDESK_GroupMembersAvailability");
	}
	
	
	
	@RequestMapping(value = "/HELPDESK_EngineerScoreDetail.htm", method = RequestMethod.GET)
	public ModelAndView getEngineerScoreDetail(
			@RequestParam("employeeId") String employeeId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
	
		int b1 = employeeId.indexOf("(");
		int b2 = employeeId.indexOf(")");
		final String empId = employeeId.substring(b1 + 1, b2);		
		// Defaulting userTime Zone to IST
		//int userTimeZone = 67;
		final int userTimeZoneToSend = 67;
		// For Pagination
		TableModel tableModel = new TableModel("scoreUser", request, response);
		
		tableModel.setExportTypes(ExportType.JEXCEL);
		int pageNo = 1;
		int maxRows = 15;
		boolean firstTime = false;
		try {
			pageNo = Integer.parseInt(request.getParameter("scoreUser_p_"));
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

		if (firstTime || pageNo == 1) {
			startCount = 1;
			// maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		final String fromDate ;
		final String toDate;
		if(request.getParameter("frmDate")!=null) 
		{
			fromDate = request.getParameter("frmDate");			
		}else
		{
			fromDate = null;
		}
		if(request.getParameter("tDate")!=null)
		{			
			toDate = request.getParameter("tDate");			
		}else
		{
			toDate = null;
		}
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<HELPDESK_ScoreDetail> page = new COMMON_Pagination<HELPDESK_ScoreDetail>();
			public int getTotalRows(Limit limit) {
				try {
					page = getScoreFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, empId, userTimeZoneToSend,
							fromDate, toDate);
				} catch (ParseException e) {					
					throw new COMMON_Exception("ParseException");
				}
				return page.getTotalCount();
			}

			public Collection<?> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		if (tableModel.isExporting()) {			
			tableModel.setItems(new PageItems() {		
				COMMON_Pagination<HELPDESK_ScoreDetail> newPage = new COMMON_Pagination<HELPDESK_ScoreDetail>();				
				public int getTotalRows(Limit limit) {
					try{						
					int countOfRows=getTotalNumOfRowsScoreDetail(limit,empId,fromDate,toDate);				
					newPage = getScoreFilteredResult(limit, startCountToPass,
							pageNoToPass, countOfRows, empId, userTimeZoneToSend,
							fromDate, toDate);
					}catch (ParseException e) {						
						throw new COMMON_Exception("ParseException");
					}
					return newPage.getTotalCount();
				}				
				public Collection<?> getItems(Limit arg0) {
					return newPage.getPageItems();
				}
			});
			tableModel.setTable(getExportTableScoreReport());	
		} else {
			tableModel.setTable(getHtmlTableForScoreDetail());
		}

		String view = tableModel.render();
		if (view == null) {
			return null; // an export
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationDat", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		
		HELPDESK_ScoreDetail scoreDetailForm=new HELPDESK_ScoreDetail();
		scoreDetailForm.setEMPLOYEE_NAME(employeeId);
		Date date = (Date) Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String today = formatter.format(date);
		if(fromDate==null){
			scoreDetailForm.setFROM_DATE(today);
		}else{
		scoreDetailForm.setFROM_DATE(fromDate);
		}
		if(toDate==null){
			scoreDetailForm.setTO_DATE(today);
		}else{
			scoreDetailForm.setTO_DATE(toDate);
		}
		
		request.setAttribute("employeeId", employeeId);
		model.addAttribute("ScoreDetail", scoreDetailForm);
		return new ModelAndView("HELPDESK_EngineerScoreDetail");
				
}
	
	@RequestMapping(value = "/HELPDESK_EngineerScoreDetail.htm", method = RequestMethod.POST)
	public ModelAndView getEngineerScoreDetailInDateRange(HELPDESK_ScoreDetail ScoreDetail,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws ParseException, ParserConfigurationException, SAXException, IOException {
		String employeeId =ScoreDetail.getEMPLOYEE_NAME();
		int b1 = employeeId.indexOf("(");
		int b2 = employeeId.indexOf(")");
		
		final String empId = employeeId.substring(b1 + 1, b2);
		final int userTimeZoneToSend = 67;
		// For Pagination
		TableModel tableModel = new TableModel("scoreUser", request, response);
		tableModel.addFilterMatcher(new MatcherKey(Date.class),
				new DateFilterMatcher("MM/yyyy"));
		int pageNo = 1;
		int maxRows = 15;
		boolean firstTime = false;
		try {
			pageNo = Integer.parseInt(request.getParameter("scoreUser_p_"));
		} catch (NumberFormatException e) {			
			firstTime = true;
		}
		catch(NullPointerException npe){
			firstTime = true;
		}
		try {
			maxRows = Integer.parseInt(request.getParameter("maxRows"));
		} catch (NumberFormatException e) {		
			firstTime = true;
		}
		catch(NullPointerException npe){
			firstTime = true;
		}

		int startCount = ((pageNo - 1) * maxRows) + 1;
		if (firstTime || pageNo == 1) {
			startCount = 1;
			// maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		final String fromDate = ScoreDetail.getFROM_DATE();
		final String toDate = ScoreDetail.getTO_DATE();
		tableModel.setExportTypes(ExportType.JEXCEL);
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<HELPDESK_ScoreDetail> page = new COMMON_Pagination<HELPDESK_ScoreDetail>();

			public int getTotalRows(Limit limit) {
				try {
					
					page = getScoreFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, empId, userTimeZoneToSend,
							fromDate, toDate);
				} catch (ParseException e) {
					throw new COMMON_Exception("ParseException");
				}
				return page.getTotalCount();
			}

			public Collection<?> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		if (tableModel.isExporting()) {
			tableModel.setItems(new PageItems() {
				COMMON_Pagination<HELPDESK_ScoreDetail> newPage = new COMMON_Pagination<HELPDESK_ScoreDetail>();
				
				 public int getTotalRows(Limit limit) {
					 try{
						 int countOfRows=getTotalNumOfRowsScoreDetail(limit,empId,fromDate,toDate);	
					newPage = getScoreFilteredResult(limit, startCountToPass,
							pageNoToPass, countOfRows, empId, userTimeZoneToSend,
							fromDate, toDate);
					 }catch(ParseException e){
						 throw new COMMON_Exception("ParseException");
					 }
					return newPage.getTotalCount();
				}				
				public Collection<?> getItems(Limit limit) {
					return newPage.getPageItems();
				}
			});
			tableModel.setTable(getExportTableScoreReport());
			tableModel.render();
		} else {
			tableModel.setTable(getHtmlTableForScoreDetail());
		}

		String view = tableModel.render();
		if (view == null) {
			return null; // an export
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationDat", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		
		
		HELPDESK_ScoreDetail scoreDetailForm=new HELPDESK_ScoreDetail();
		scoreDetailForm.setEMPLOYEE_NAME(employeeId);
		scoreDetailForm.setFROM_DATE(ScoreDetail.getFROM_DATE());
		scoreDetailForm.setTO_DATE(ScoreDetail.getTO_DATE());
		request.setAttribute("employeeId", employeeId);
		model.addAttribute("ScoreDetail", scoreDetailForm);
		return new ModelAndView("HELPDESK_EngineerScoreDetail");		
	
	}

	
	
	
	public COMMON_Pagination<HELPDESK_GroupMembersAvailability> getUserFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String empId,
			int userTimeZoneToSend, String fromDate, String toDate) throws ParseException{

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter
				.execute("UserAvailabilityLogInOut");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getLoggedUserList(startCount, pageNo, maxRows, buffer
				.toString(), empId, userTimeZoneToSend, fromDate, toDate);

	}
	
	public COMMON_Pagination<HELPDESK_ScoreDetail> getScoreFilteredResult(Limit limit,
			int startCount, int pageNo, int maxRows, String empId,
			int userTimeZoneToSend, String fromDate, String toDate) throws ParseException{

		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer buffer = listPageSearchFilter
				.execute("EngineerScoreDetailList");
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		return listDAO.getEngineerAuditLogList(startCount, pageNo, maxRows, buffer
				.toString(), empId, userTimeZoneToSend, fromDate, toDate);

	}

	public Table getHtmlTableforLoggedInUsers() {
		HtmlTable htmlTable = new HtmlTable().caption("Log In Out Details ")
				.width("100%");
		htmlTable.setStyleClass("table");
		HtmlRow htmlRow = new HtmlRow();
		htmlTable.setRow(htmlRow);

		HtmlColumn employeeId = new HtmlColumn("EMPLOYEE_NAME")
				.title("Employee Id");
		htmlRow.addColumn(employeeId);

		HtmlColumn dateTime = new HtmlColumn("DATE_TIME").title("Date Time");
		htmlRow.addColumn(dateTime);

		HtmlColumn status = new HtmlColumn("STATUS").title("Status");
		htmlRow.addColumn(status);

		return htmlTable;
	}
	
	public Table getHtmlTableForScoreDetail() {
		HtmlTable htmlTable = new HtmlTable().caption("Score Details ")
		.width("100%");
		htmlTable.setStyleClass("table");
		HtmlRow htmlRow=new HtmlRow();
		htmlTable.setRow(htmlRow);
		
		HtmlColumn employeeId=new HtmlColumn("EMPLOYEE_ID").title("Employee Id");
		htmlRow.addColumn(employeeId);
		
		HtmlColumn ticketID=new HtmlColumn("TICKET_ID").title("Ticket Id");
		ticketID.setFilterable(false);
		htmlRow.addColumn(ticketID);
		
		HtmlColumn weightage=new HtmlColumn("WEIGHTAGE").title("Ticket Weightage");
		weightage.setFilterable(false);
		htmlRow.addColumn(weightage);
		
		HtmlColumn oldTotalScore=new HtmlColumn("OLD_TOTAL_SCORE").title("Previous Score");
		oldTotalScore.setFilterable(false);
		htmlRow.addColumn(oldTotalScore);
		
		HtmlColumn newTotalScore=new HtmlColumn("NEW_TOTAL_SCORE").title("New Score");
		newTotalScore.setFilterable(false);
		htmlRow.addColumn(newTotalScore);
		
		HtmlColumn isFirstLogin=new HtmlColumn("IS_FIRST_LOGIN").title("Is First Time Login");
		isFirstLogin.setFilterable(false);
		htmlRow.addColumn(isFirstLogin);
		
		HtmlColumn createdBy=new HtmlColumn("CREATED_BY").title("Created By");
		htmlRow.addColumn(createdBy);
		
		HtmlColumn createdDate=new HtmlColumn("CREATED_DATE").title("Created Date");
		htmlRow.addColumn(createdDate);
		
		return htmlTable;
	}

	@RequestMapping(value = "/HELPDESK_GroupMembersAvailability.htm", method = RequestMethod.POST)
	public ModelAndView userAvailabilityy(HELPDESK_GroupMembersAvailability userAvailability,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		/*String employeeId = request.getParameter("employeeId");*/
		String employeeId =userAvailability.getEMPLOYEE_NAME();
		int b1 = employeeId.indexOf("(");
		int b2 = employeeId.indexOf(")");
		
		final String empId = employeeId.substring(b1 + 1, b2);
		// Defaulting userTime Zone to IST
        //Changed by Dhiren
		//int userTimeZone = 67;
		final int userTimeZoneToSend = 67;

		// For Pagination
		TableModel tableModel = new TableModel("loggedUser", request, response);
	

		tableModel.addFilterMatcher(new MatcherKey(Date.class),
				new DateFilterMatcher("MM/yyyy"));
		int pageNo = 1;
		int maxRows = 15;
		boolean firstTime = false;
		try {
			pageNo = Integer.parseInt(request.getParameter("loggedUser_p_"));
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

		if (firstTime || pageNo == 1) {
			startCount = 1;
			// maxRows = 15;
		} else {
			maxRows = (startCount + maxRows) - 1;
		}
		tableModel.setStateAttr("restore");
		// tableModel.setExportTypes(new ExportType[] { CSV, EXCEL });
		final int startCountToPass = startCount;
		final int pageNoToPass = pageNo;
		final int maxRowsToPass = maxRows;
		final String fromDate = userAvailability.getFROM_DATE();
		final String toDate = userAvailability.getTO_DATE();
		tableModel.setExportTypes(ExportType.JEXCEL);
		tableModel.setItems(new PageItems() {
			COMMON_Pagination<HELPDESK_GroupMembersAvailability> page = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();

			public int getTotalRows(Limit limit) {
				try {
					page = getUserFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, empId, userTimeZoneToSend,
							fromDate, toDate);
				} catch (ParseException e) {
					throw new COMMON_Exception("ParseException");
				}
				return page.getTotalCount();
			}

			public Collection<?> getItems(Limit limit) {
				return page.getPageItems();
			}
		});
		if (tableModel.isExporting()) {
			tableModel.setItems(new PageItems() {
				COMMON_Pagination<HELPDESK_GroupMembersAvailability> newPage = new COMMON_Pagination<HELPDESK_GroupMembersAvailability>();
				
				 public int getTotalRows(Limit limit) {
					 try{
					newPage = getUserFilteredResult(limit, startCountToPass,
							pageNoToPass, maxRowsToPass, empId, userTimeZoneToSend,
							fromDate, toDate);
					 }catch(ParseException e){
						 throw new COMMON_Exception("ParseException");
					 }
					return newPage.getTotalCount();
				}				
				public Collection<?> getItems(Limit limit) {
					return newPage.getPageItems();
				}
			});
			tableModel.setTable(getExportTableLoginReport());
			tableModel.render();
		} else {
			tableModel.setTable(getHtmlTableforLoggedInUsers());
		}

		String view = tableModel.render();
		if (view == null) {
			return null; // an export
		} else {
			// Setting a parameter to signal that this is an Ajax request.
			String ajax = request.getParameter("ajax");
			if (ajax != null && ajax.equals("true")) {
				byte[] contents = view.getBytes();
				try {
					response.getOutputStream().write(contents);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			} else { // Not using Ajax if invoke the controller for the first
				// time.
				request.setAttribute("paginationDat", view); // Set the Html in
				// the
				// request for the
				// JSP.
			}
		}
		request.setAttribute("employeeId", employeeId);
		model.addAttribute("UserAvailability", userAvailability);
		return new ModelAndView("HELPDESK_GroupMembersAvailability");
	}
	public int getTotalNumOfRows(Limit limit,String employeeId,String fromDate,String toDate)throws ParseException {
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();	
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer filterQuery = listPageSearchFilter
		.execute("UserAvailabilityLogInOut");
		return listDAO.getTotalNumberOfRows(employeeId,fromDate,toDate,filterQuery.toString());
	}
	
	public int getTotalNumOfRowsScoreDetail(Limit limit,String employeeId,String fromDate,String toDate)throws ParseException {
		
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_ListPageDAO listDAO = (COMMON_ListPageDAO) ctx
				.getBean("listDao");
		COMMON_ListPageSearch listPageSearchFilter = new COMMON_ListPageSearch();
		FilterSet filterSet = limit.getFilterSet();	
		Collection<Filter> filters = filterSet.getFilters();
		for (Filter filter : filters) {
			String property = filter.getProperty();
			String value = filter.getValue();
			listPageSearchFilter.addFilter(property, value);
		}
		StringBuffer filterQuery = listPageSearchFilter
		.execute("EngineerScoreDetailList");
		return listDAO.getTotalNumberOfRowsScoreDetail(employeeId,fromDate,toDate,filterQuery.toString());
	}
	public Table getExportTableLoginReport(){		
		Table table=new Table().caption("Log-in Log-out Report");
		Row row=new Row();
		table.setRow(row);
		Column EMPLOYEE_NAME=new Column("EMPLOYEE_NAME").title("EMPLOYEE NAME");
		Column DATE_TIME=new Column("DATE_TIME").title("DATE TIME");
		Column STATUS=new Column("STATUS").title("STATUS");
		row.addColumn(EMPLOYEE_NAME);
		row.addColumn(DATE_TIME);
		row.addColumn(STATUS);		
		return table;		
	}
	
	public Table getExportTableScoreReport(){
		Table table=new Table().caption("Score Report for Engineer");
		Row excelRow=new Row();
		table.setRow(excelRow);
		Column employeeId=new Column("EMPLOYEE_ID").title("Employee Id");
		excelRow.addColumn(employeeId);
		
		Column ticketID=new Column("TICKET_ID").title("Ticket Id");
		excelRow.addColumn(ticketID);
		
		Column weightage=new Column("WEIGHTAGE").title("Ticket Weightage");
		excelRow.addColumn(weightage);
		
		Column oldTotalScore=new Column("OLD_TOTAL_SCORE").title("Previous Score");		
		excelRow.addColumn(oldTotalScore);
		
		Column newTotalScore=new Column("NEW_TOTAL_SCORE").title("New Score");		
		excelRow.addColumn(newTotalScore);
		
		Column isFirstLogin=new Column("IS_FIRST_LOGIN").title("Is First Time Login");		
		excelRow.addColumn(isFirstLogin);
		
		Column createdBy=new Column("CREATED_BY").title("Created By");
		excelRow.addColumn(createdBy);
		
		Column createdDate=new Column("CREATED_DATE").title("Created Date");
		excelRow.addColumn(createdDate);
		return table;
	}
}