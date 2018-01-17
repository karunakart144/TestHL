/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.springframework.context.ApplicationContext;

import com.igate.iconnect.BO.HELPDESK_Priority;
import com.igate.iconnect.BO.MASTER_Create;
import com.igate.iconnect.BO.WORKFLOW_States;
import com.igate.iconnect.constants.CustomDateFormatConstants;
import com.igate.iconnect.controller.HELPDESK_Edit_ReturnTypeVoidController;
import com.igate.iconnect.dao.COMMON_CacheDAO;
import com.igate.iconnect.dao.MASTER_CreateDAO;
import com.igate.iconnect.dao.WORKFLOW_DAO;

public class COMMON_ListPageColumnGenerator {

	String menuId = "";
	String roleId = "";
	String headerMenuNameforReference = "";
	String loggedInUser = "";
	String roleName = "";
	private static Logger log = Logger
			.getLogger(COMMON_ListPageColumnGenerator.class);
	private static final String COMMON_CACHE_IMPL = "GetMasterData";// Added by
	private static final String MASTER_EDITDAO_IMPL = "MasterTicketDAO"; // sali
	private static final String GROUP_ID_VARIABLE = "GROUP_ID";// Added by sali
	private static final String LOCATION_ID_VARIABLE = "LOCATION_ID";// Added by

	// sali

	public COMMON_ListPageColumnGenerator(String menuid, String roleid,
			String headerMenuName, String loogedinUserID, String rolename) {
		menuId = menuid;
		roleId = roleid;
		headerMenuNameforReference = headerMenuName;
		loggedInUser = loogedinUserID;
		roleName = rolename;
	}

	public HtmlColumn TicketIdWithLinkForHelpDesk() {
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

				try {
					subject = URLEncoder.encode(subject.toString(), "UTF-8");

				} catch (UnsupportedEncodingException e) {
					log.error("URL Encoding Error :" + e);
				}
				html.a().href().quote().append("HELPDESK_Edit.htm?id=").append(
						value).append("&subject=").append(subject).append(
						"&category=").append(categoryId)
						.append("&subcategory=").append(subcategoryId).append(
								"&function=").append(function).quote().close();
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		return ticketId;
	}

	public HtmlColumn TicketIdForHelpDesk() {
		return new HtmlColumn("TICKET_ID").title("Ticket #");
	}

	public HtmlColumn TicketIdForLockedTickets() {
		return new HtmlColumn("TICKET_ID").title("Ticket #");
	}

	// to Show Mail ID for locked mails menu
	public HtmlColumn mailIdForLockedMails() {
		return new HtmlColumn("MAIL_ID").title("Mail ID");
	}

	// to Show Locked Employee Name for Locked Mails
	public HtmlColumn lockedEmpNameForLockedMails() {
		return new HtmlColumn("LOCKED_BY").title("Locked By");
	}

	public HtmlColumn empNameForLockedTickets() {
		return new HtmlColumn("EMPLOYEE_NAME").title("Locked BY");
	}

	public HtmlColumn lockedDateForLockedTickets() {
		return new HtmlColumn("LOCKED_DATE").title("Locked Date");
	}

	// Added for exceptional start date and end date
	public HtmlColumn excptionalApprovalStDateForHelpDesk() {
		return new HtmlColumn("EXCEPTION_APP_ST_DATE")
				.title("ExceptionAppStDt");
	}

	public HtmlColumn excptionalApprovalEndDateForHelpDesk() {
		return new HtmlColumn("EXCEPTION_APP_END_DATE")
				.title("ExceptionAppEndDt");
	}
	/**********Auto Assignment************/
	public HtmlColumn highScoreOfEngineer(){
		return new HtmlColumn("HIGH_SCORE").title("High Score");
	}
	
	public HtmlColumn mediumScoreOfEngineer(){
		return new HtmlColumn("MEDIUM_SCORE").title("Medium Score");
	}
	
	public HtmlColumn lowScoreOfEngineer(){
		return new HtmlColumn("LOW_SCORE").title("Low Score");
	}
	
	public HtmlColumn totalScoreOfEngineer(){
		return new HtmlColumn("TOTAL_SCORE").title("Total Score");
	}
	
	public HtmlColumn employeeIDForScoreOfEngineer() {
		HtmlColumn employeeName = new HtmlColumn("EMPLOYEE_NAME")
		.title("Employee Name");
		final String empNameProperty = employeeName.getProperty();
		employeeName.setCellEditor(new CellEditor() {

			public Object getValue(Object item, String property, int rowcount) {
			
			Object value = new BasicCellEditor().getValue(item,	empNameProperty, rowcount);
			HtmlBuilder html = new HtmlBuilder();	
			html.a().href().quote().append("HELPDESK_EngineerScoreDetail.htm?employeeId=").append(value).quote().close();
			html.append(value);
			html.aEnd();
	
			return html.toString();
	
		}
		});
		return employeeName;
	}
	/**********END Auto Assignment************/
	public HtmlColumn unlockButtonForLockedTickets() {
		HtmlColumn unlock = new HtmlColumn("UNLOCK").title("Unlock");
		unlock.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticketID = new HtmlCellEditor().getValue(item,
						"TICKET_ID", rowcount);
				String menuID = "";

				if (menuId.equalsIgnoreCase("62")
						|| menuId.equalsIgnoreCase("96")) {
					menuID = "1";
				} else if (menuId.equalsIgnoreCase("63")) {
					menuID = "2";
				} else if (menuId.equalsIgnoreCase("106")) {
					menuID = "4";
				}
				final String menuIDForUnlock = menuID;
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("button").value("Unlock").name("Unlock")
						.onclick(
								"javascript:unlockTheTicket('"
										+ ticketID.toString() + "','"
										+ menuIDForUnlock + "',this);").end();
				return html.toString();
			}

		});
		unlock.setFilterable(false);
		return unlock;
	}

	public HtmlColumn unlockButtonForLockedMails() {
		HtmlColumn unlock = new HtmlColumn("UNLOCK").title("Unlock");
		unlock.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticketID = new HtmlCellEditor().getValue(item,
						"MAIL_ID", rowcount);
				// Changed by Dhiren
				// String menuID = "4";
				final String menuIDForUnlock = "4";
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("button").value("Unlock").name("Unlock")
						.onclick(
								"javascript:unlockTheTicket('"
										+ ticketID.toString() + "','"
										+ menuIDForUnlock + "',this);").end();
				return html.toString();
			}

		});
		unlock.setFilterable(false);
		return unlock;
	}

	public HtmlColumn employeeNameForLoggedInMembers() {
		/* return new HtmlColumn("EMPLOYEE_NAME").title("Employee Name"); */
		HtmlColumn employeeName = new HtmlColumn("EMPLOYEE_NAME")
				.title("Employee Name");
		final String empNameProperty = employeeName.getProperty();
		employeeName.setCellEditor(new CellEditor() {

			public Object getValue(Object item, String property, int rowcount) {
				// TODO Auto-generated method stub
				Object value = new BasicCellEditor().getValue(item,
						empNameProperty, rowcount);
				HtmlBuilder html = new HtmlBuilder();

				html.a().href().quote().append(
						"HELPDESK_GroupMembersAvailability.htm?employeeId=")
						.append(value).quote().close();
				html.append(value);
				html.aEnd();

				return html.toString();

			}
		});
		return employeeName;
	}

	public HtmlColumn loginStatusforLoggedInMembers() {
		HtmlColumn availability = new HtmlColumn("STATUS")
				.title("Availability");
		availability.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object availability = new HtmlCellEditor().getValue(item,
						"STATUS", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (availability.toString().equalsIgnoreCase("offline")) {

					html.img().src("images/offline.png").title("Offline")
							.close();
				} else if (availability.toString().equalsIgnoreCase("online")) {

					html.img().src("images/online.png").title("Online").close();
				}
				return html.toString();
			}
		});
		availability.setFilterEditor(new AvailabilityDroplistFilterEditor());

		return availability;
	}

	public HtmlColumn attachmentForHelpDesk() {

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

		return attachment;
	}

	public HtmlColumn isTicketLocked() {

		HtmlColumn ticketLock = new HtmlColumn("LOCK").title("L");
		ticketLock.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticketLock = new HtmlCellEditor().getValue(item, "LOCK",
						rowcount);
				Object ticketLockedBy = new HtmlCellEditor().getValue(item,
						"LOCKED_BY", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (ticketLock.toString().equalsIgnoreCase("Yes")) {
					html.img().src("images/lock.gif").title(
							"Locked By:" + ticketLockedBy.toString());
				}
				return html.toString();
			}
		});
		ticketLock.setFilterable(false);
		return ticketLock;
	}

	//added for Orchestration
	public HtmlColumn isAutomation() {

		HtmlColumn autoTicket = new HtmlColumn("AUTOMATION").title("AA");
		autoTicket.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object autoTicket = new HtmlCellEditor().getValue(item, "AUTOMATION",
						rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (autoTicket.toString().equalsIgnoreCase("Yes")) {
					html.img().src("images/Automation.gif").title("Assigned for Automation");
				}
				else if (autoTicket.toString().equalsIgnoreCase("Failed")) {
					html.img().src("images/Automation.gif").title("Automation Failed");
				}
				return html.toString();
			}
		});
		autoTicket.setFilterable(false);
		return autoTicket;
	}


/*	public HtmlColumn isPremiumTicket() {
		HtmlColumn premium = new HtmlColumn("PREMIUM").title("P");
		premium.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object premiumTicket = new HtmlCellEditor().getValue(item,
						"PREMIUM", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (premiumTicket.toString().equalsIgnoreCase("Yes")) {
					html.img().src("images/Premium.gif").title("Premium");
				}
				return html.toString();
			}
		});
		premium.setFilterable(false);
		return premium;
	}*/

	// Added Dept Name viewable to the executives/engineers:

	public HtmlColumn deptNameForTicketRequestor() {
		HtmlColumn duName = new HtmlColumn("DU_NAME").title("DU");
		duName.setFilterable(false);
		return duName;

	}

	// End of Dept Name view

	// Added for Check FCS column to be shown in Queue Menu
	public HtmlColumn checkFCSForHelpdesk() {
		HtmlColumn checkFCS = new HtmlColumn("CHECK_FCS").title("CS");
		checkFCS.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object changecatSubcat = new HtmlCellEditor().getValue(item,
						"CHECK_FCS", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (changecatSubcat.toString().equalsIgnoreCase("Yes")) {
					html.img().src("images/Check_FCS.png").title("ChangeCS");
				}
				return html.toString();
			}
		});
		checkFCS.setFilterable(false);
		return checkFCS;
	}

	// Addition End
	public HtmlColumn SelectColumnForHelpDesk() {
		HtmlColumn select = new HtmlColumn("SELECT").title("S");
		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"ISSUE_ID", rowcount);
				Object ticektIdVal1 = new HtmlCellEditor().getValue(item,
						"TICKET_ID", rowcount);
				Object currentStateVal = new HtmlCellEditor().getValue(item,
						"WORKFLOW_STATE", rowcount);
				Object functionVal = new HtmlCellEditor().getValue(item,
						"FUNCTION", rowcount);
				Object subCategoryVal = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_ID", rowcount);
				Object priorityVal = new HtmlCellEditor().getValue(item,
						"PRIORITY_ID", rowcount);
				Object locationVal = new HtmlCellEditor().getValue(item,
						"LOCATION_ID", rowcount);
				Object locDetailVal = new HtmlCellEditor().getValue(item,
						"LOC_DET_ID", rowcount);
				Object functionIdVal = new HtmlCellEditor().getValue(item,
						"FUNCTION_ID", rowcount);
				String ticketId = String.valueOf(ticektIdVal1);
				String currentState = String.valueOf(currentStateVal);
				String function = String.valueOf(functionIdVal);
				String subcategory = String.valueOf(subCategoryVal);
				String priority = String.valueOf(priorityVal);
				String location = String.valueOf(locationVal);
				String locationdetail = String.valueOf(locDetailVal);
				String actionhtml = String.valueOf(ticektIdVal1) + ","
						+ subcategory + "," + priority + "," + location + ","
						+ locationdetail + "," + function;
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("checkbox").value(actionhtml).id(String.valueOf(ticektIdVal1)).end();

				return html.toString();
			}
		});
		select.setFilterable(false);
		return select;
	}

	public HtmlColumn colorcodeForHelpDesk() {
		HtmlColumn colourCode = new HtmlColumn("COLOUR_CODE").title("C");
		colourCode.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"TICKET_ID", rowcount);
				String ticketId = String.valueOf(ticektIdVal);
				Object functionId = new HtmlCellEditor().getValue(item,
						"FUNCTION_ID", rowcount);
				Object subCategoryId = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_ID", rowcount);
				
				Object priorityId = new HtmlCellEditor().getValue(item,
						"PRIORITY_ID", rowcount);
				Object locationId = new HtmlCellEditor().getValue(item,
						"LOCATION_ID", rowcount);
				Object organization = new HtmlCellEditor().getValue(item,
						"ORGANIZATION", rowcount);
				Object ectDB = new HtmlCellEditor().getValue(item, "ECT",
						rowcount);
				Object status = new HtmlCellEditor().getValue(item,
						"WORKFLOW_STATE", rowcount);
				Object group = new HtmlCellEditor().getValue(item,
						"ASSIGNED_GROUP_ID", rowcount);
				if (!String.valueOf(status).contains(("Resolved/Closed"))
						&& !String.valueOf(status).contains(("Closed"))) {

					SimpleDateFormat dateformatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd-MM-yyyy hh:mm:ss a");
					// Date date = null;
					Date dateect = null;
					try {
						if (!String.valueOf(ectDB).equalsIgnoreCase("-"))
							dateect = formatter.parse(String.valueOf(ectDB));
					} catch (ParseException e) {
						log
								.error("Parse exception while getting colorcodeForHelpDesk : "
										+ e);
						/* e.printStackTrace(); */
					}

					String imageName = "";
					int functionIdAsINT = Integer.parseInt(String
							.valueOf(functionId));
					int priorityIdAsINT = Integer.parseInt(String
							.valueOf(priorityId));
					/*
					 * int locationIdAsINT = Integer.parseInt(String
					 * .valueOf(locationId));
					 */
					if (dateect != null) {
						String ect_date_yyyy_MM_dd = dateformatter
								.format(dateect);
						// Added by Sali on April13th to modify colurcode logic
						ApplicationContext ctx = COMMON_AppContext.getCtx();
						COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
								.getBean(COMMON_CACHE_IMPL);
						List<Map<String, Object>> groupList = MasterDataImpl
								.getIHDGroupMaster();
						long group_base_locationId = 0l;
						for (Map<String, Object> stringObj : groupList) {
							if (stringObj.get(GROUP_ID_VARIABLE) != null) {
								if (stringObj
										.get(GROUP_ID_VARIABLE)
										.toString()
										.equalsIgnoreCase(String.valueOf(group))) {
									group_base_locationId = Long
											.parseLong(stringObj.get(
													LOCATION_ID_VARIABLE)
													.toString());
								}
							}
						}

						Object coloucode = new HtmlCellEditor().getValue(item,
								"COLOUR_CODE", rowcount);
					
						
						if(String.valueOf(coloucode).equalsIgnoreCase("GREEN"))
							imageName="images/green.jpg";
						else if(String.valueOf(coloucode).equalsIgnoreCase("RED"))
							imageName="images/red.jpg";
						else if(String.valueOf(coloucode).equalsIgnoreCase("AMBER"))
							imageName="images/amber.jpg";
						else if(String.valueOf(coloucode).equalsIgnoreCase("WHITE"))
							imageName="images/white.jpg";
						else
							return "";
						/*imageName = HELPDESK_ColorcodeAlgorithm.getcolorcode(
								String.valueOf(status), ticketId, String
										.valueOf(group), ect_date_yyyy_MM_dd,
								functionIdAsINT, priorityIdAsINT,
								group_base_locationId, String
										.valueOf(organization),Integer.parseInt(String
												.valueOf(subCategoryId)));*/
					} else
						return "";
					HtmlBuilder html = new HtmlBuilder();
					html.img().src(imageName).title("Color Code").id(imageName).close();
					return html.toString();
				} else
					return "";
			}
		});
		colourCode.setFilterable(false);
		return colourCode;
	}
	
	public HtmlColumn colorcodetTimeRemainingForHelpDesk() {
		HtmlColumn colourCodeTimeRemaining = new HtmlColumn("TIME_REMAINING").title("Time Remaining");
		colourCodeTimeRemaining.setFilterable(false);
		return colourCodeTimeRemaining;
	}
	
	public HtmlColumn coreIdForiTrackTickets() {
		HtmlColumn coreId = new HtmlColumn("CORE_ID").title("Core Id");
		coreId.setFilterable(false);
		coreId.setHeaderClass("Core_Id");
		coreId.setStyle("display:none");
		return coreId;
	}
	
	public HtmlColumn allowCloseableFlagForiTrackTickets() {
		HtmlColumn coreId = new HtmlColumn("ALLOW_CLOSEABLE_FLAG").title("Allow Closeable Flag");
		coreId.setFilterable(false);
		coreId.setHeaderClass("Allow_Closeable_Flag");
		coreId.setStyle("display:none");
		return coreId;
	}

	public HtmlColumn priorityForHelpDesk() {
		HtmlColumn priority = new HtmlColumn("PRIORITY").title("Priority");
		priority.setFilterEditor(new AvailablePriorityDroplistFilterEditor());
		return priority;
	}

	public HtmlColumn requestedByForHelpDesk() {
		return new HtmlColumn("REQUESTOR_NAME").title("Requested By");
	}

	public HtmlColumn requestorLocationForHelpDesk() {
		return new HtmlColumn("LOCATION_AREA").title("Current Location");
	}

	public HtmlColumn subjectForHelpdesk() {
		return new HtmlColumn("SUBJECT").title("Subject");
	}

	public HtmlColumn creationDateForHelpdesk() {
		return new HtmlColumn("CREATED_DATE").title("Creation Date");
	}
	
	//Added for Emergency L1 changes
	public HtmlColumn activityTypeForHelpdesk() {
		return new HtmlColumn("ACTIVITY_TYPE").title("Activity Type");
	}

	public HtmlColumn descriptionForHelpDesk() {
		HtmlColumn description = new HtmlColumn("DESCRIPTION")
				.title("Description");
		// description.setWidth("50px");
		return description;
	}

	public HtmlColumn functionForHelpDesk() {
		HtmlColumn function = new HtmlColumn("FUNCTION").title("Function");
		function.setFilterEditor(new AvailableFunctionsDroplistFilterEditor());
		return function;
	}

	public HtmlColumn categoryForHelpDesk() {
		return new HtmlColumn("CATEGORY").title("Category");
	}

	public HtmlColumn assignedToForHelpDesk() {
		return new HtmlColumn("ASSIGNED_TO").title("Assigned To");
	}

	public HtmlColumn slaStatusForHelpDesk() {
		return new HtmlColumn("SLA_STATUS").title("SLA");
	}

	public HtmlColumn closedDateForHelpDesk() {
		return new HtmlColumn("CLOSED_DATE").title("Closed Date");
	}

	public HtmlColumn ectForHelpDesk() {
		HtmlColumn ect = new HtmlColumn("ECT").title("ECT");
		ect.setFilterable(true);
		return ect;
	}

	public HtmlColumn workflowstateForHelpDesk() {
		HtmlColumn workFlowStatus = new HtmlColumn("WORKFLOW_STATE")
				.title("Status");
		workFlowStatus
				.setFilterEditor(new AvailableStatesDroplistFilterEditor());
		return workFlowStatus;
	}

	public HtmlColumn subcategoryForHelpDesk() {
		return new HtmlColumn("SUBCATEGORY").title("SubCategory");
	}

	public HtmlColumn approveRejectForHelpDesk() {
		HtmlColumn action = new HtmlColumn("ACTION").title("Action");
		action.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"TICKET_ID", rowcount);
				Object currentStateVal = new HtmlCellEditor().getValue(item,
						"WORKFLOW_STATE", rowcount);
				Object functionVal = new HtmlCellEditor().getValue(item,
						"FUNCTION", rowcount);
				Object subCategoryVal = new HtmlCellEditor().getValue(item,
						"SUB_CATEGORY_ID", rowcount);
				Object priorityVal = new HtmlCellEditor().getValue(item,
						"PRIORITY_ID", rowcount);
				Object locationVal = new HtmlCellEditor().getValue(item,
						"LOCATION_ID", rowcount);
				Object locDetailVal = new HtmlCellEditor().getValue(item,
						"LOC_DET_ID", rowcount);
				Object functionIdVal = new HtmlCellEditor().getValue(item,
						"FUNCTION_ID", rowcount);
				String ticketId = String.valueOf(ticektIdVal);
				String currentState = String.valueOf(currentStateVal);
				String function = String.valueOf(functionVal);
				String subcategory = String.valueOf(subCategoryVal);
				String priority = String.valueOf(priorityVal);
				String location = String.valueOf(locationVal);
				String locationdetail = String.valueOf(locDetailVal);
				HtmlBuilder html = new HtmlBuilder();
				html.a().href("#").styleClass("ApproveAuditLog").onclick(
						"javascript:approvereject('" + ticketId + "','"
								+ currentState + "','Approved','" + function
								+ "','" + subcategory + "','" + priority
								+ "','" + location + "','" + locationdetail
								+ "','" + functionIdVal + "');").close();
				html.img().src("images/approve.png").title("Approve").close();
				// html.append("Approve");
				html.aEnd();
				html.append(" | ");
				html.a().href("#").styleClass("ApproveAuditLog").onclick(
						"javascript:approvereject('" + ticketId + "','"
								+ currentState + "','Rejected','" + function
								+ "','" + subcategory + "','" + priority
								+ "','" + location + "','" + locationdetail
								+ "','" + functionIdVal + "');").close();
				html.img().src("images/reject.png").title("Reject").close();
				// html.append("Reject");
				html.aEnd();
				if ((menuId.equalsIgnoreCase("12")
						|| menuId.equalsIgnoreCase("48")
						|| menuId.equalsIgnoreCase("108")) && !functionVal.toString().equalsIgnoreCase("Life and Health Operations Canada")) {
					html.append(" | ");
					html.a().href("#").styleClass("ApproveAuditLog").onclick(
							"javascript:approvereject('" + ticketId + "','"
									+ currentState + "','Need More Info','"
									+ function + "','" + subcategory + "','"
									+ priority + "','" + location + "','"
									+ locationdetail + "','" + functionIdVal
									+ "');").close();
					html.img().src("images/User_Info.png").title(
							"Get More Info").close();
					// html.append("Need More Info");
					html.aEnd();
					html.append(" | ");
					html.a().href("#").styleClass("ApproveAuditLog").onclick(
							"javascript:approvereject('" + ticketId + "','"
									+ currentState + "','Send to Helpdesk','"
									+ function + "','" + subcategory + "','"
									+ priority + "','" + location + "','"
									+ locationdetail + "','" + functionIdVal
									+ "');").close();
					html.img().src("images/Change_CS.png").title(
							"Send to Helpdesk").close();
					// html.append("Need More Info");
				}
				
				if ((menuId.equalsIgnoreCase("12")
						|| menuId.equalsIgnoreCase("48")
						|| menuId.equalsIgnoreCase("108")) && functionVal.toString().equalsIgnoreCase("Life and Health Operations Canada")) {
					html.append(" | ");
					html.a().href("#").styleClass("ApproveAuditLog").onclick(
							"javascript:approvereject('" + ticketId + "','"
									+ currentState + "','Need More Info','"
									+ function + "','" + subcategory + "','"
									+ priority + "','" + location + "','"
									+ locationdetail + "','" + functionIdVal
									+ "');").close();
					html.img().src("images/User_Info.png").title(
							"Get More Info").close();
					// html.append("Need More Info");
					html.aEnd();
				}
				return html.toString();

			}
		});
		action.setFilterable(false);
		return action;
	}

	// Below method added for revoke access date provision to Approver - ADDED
	// BY KRUTHI.
	public HtmlColumn revokeAccessForTicket() {
		HtmlColumn action = new HtmlColumn("ACTION").title("Action");
		action.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"TICKET_ID", rowcount);

				String ticketId = String.valueOf(ticektIdVal);
				String exceptionDateId = String.valueOf(new HtmlCellEditor()
						.getValue(item, "EXCEPTION_APP_ST_DATE", rowcount));
				HtmlBuilder html = new HtmlBuilder();
				if (!exceptionDateId.equals("-")
						&& roleName.equalsIgnoreCase("IT Security")) {
					html.a().href("#").styleClass("checkRevokeDate").onclick(
							"javascript:revokeAccess('" + ticketId + "');")
							.close();
					html.img().src("images/Revoke_Access.gif").title(
							"access revoke").close();
				}
				// html.append("Approve");
				html.aEnd();

				return html.toString();

			}
		});
		action.setFilterable(false);
		return action;

	}

	// Method added for revoke access date provision to Approver - ADDED BY
	// KRUTHI- END
	public HtmlColumn selectForTechCR() {
		HtmlColumn select = new HtmlColumn("SELECT").title("S");
		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"ISSUE_ID", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				html.input().type("checkbox")
						.value(String.valueOf(ticektIdVal)).end();

				return html.toString();
			}
		});
		select.setFilterable(false);
		return select;
	}

	public HtmlColumn issueIDForTechCR() {
		HtmlColumn ISSUE_ID = new HtmlColumn("ISSUE_ID").title("Tech-CR ID");
		ISSUE_ID.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, property,
						rowcount);
				HtmlBuilder html = new HtmlBuilder();
				html.a().href().quote().append("TECHCR_Edit.htm?techCRId=")
						.append(value).quote().close();
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		return ISSUE_ID;
	}

	public HtmlColumn createdByForTechCR() {
		return new HtmlColumn("CREATED_BY").title("Created By");
	}

	public HtmlColumn severityForTechCR() {
		return new HtmlColumn("SEVERITY_NAME").title("Severity");
	}

	public HtmlColumn assignedToForTechCR() {
		return new HtmlColumn("ASSIGNED_EMPIDANDNAME").title("Assigned To");
	}

	public HtmlColumn actionDescForTechCR() {
		return new HtmlColumn("ACTION_DESC").title("Action");
	}

	public HtmlColumn groupNameForTechCR() {
		return new HtmlColumn("GROUP_NAME").title("Group");
	}

	public HtmlColumn categoryForTechCR() {
		return new HtmlColumn("PARENT_CATEGORY_NAME").title("Category");
	}

	public HtmlColumn subcategoryForTechCR() {
		return new HtmlColumn("CHILD_CATEGORY_NAME").title("Sub Category");
	}

	public HtmlColumn approverForTechCR() {
		return new HtmlColumn("APPROVER_EMPIDANDNAME")
				.title("Approving Manager");
	}

	public HtmlColumn subjectForTechCR() {
		return new HtmlColumn("SUBJECT").title("Subject");
	}

	public HtmlColumn workflowStateForTechCR() {
		return new HtmlColumn("WORKFLOW_STATE_NAME").title("Status");
	}

	public HtmlColumn approveRejectForTechCR() {
		HtmlColumn action = new HtmlColumn("ACTION").title("Action");
		action.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object ticektIdVal = new HtmlCellEditor().getValue(item,
						"ISSUE_ID", rowcount);
				Object currentStateVal = new HtmlCellEditor().getValue(item,
						"WORKFLOW_STATE_NAME", rowcount);
				Object groupVal = new HtmlCellEditor().getValue(item,
						"GROUP_NAME", rowcount);
				String ticketId = String.valueOf(ticektIdVal);
				String currentState = String.valueOf(currentStateVal);
				String group = String.valueOf(groupVal);
				HtmlBuilder html = new HtmlBuilder();
				html.a().href("#").styleClass("ApproveAuditLog").onclick(
						"javascript:techCRapprovereject('" + ticketId + "','"
								+ currentState + "','" + group
								+ "','Approved');").close();
				html.append("Approve");
				html.aEnd();
				html.append(" | ");
				html.a().href("#").styleClass("ApproveAuditLog").onclick(
						"javascript:techCRapprovereject('" + ticketId + "','"
								+ currentState + "','" + group
								+ "','Rejected');").close();
				html.append("Reject");
				html.aEnd();
				return html.toString();

			}
		});
		action.setFilterable(false);
		return action;
	}

	public HtmlColumn buttonsForMailTrackerNoc() {
		HtmlColumn buttons = new HtmlColumn("CONVERT").title("Convert");
		buttons.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, "MAIL_ID",
						rowcount);
				Object statusval = new HtmlCellEditor().getValue(item,
						"STATUS", rowcount);
				HtmlBuilder html = new HtmlBuilder();

				/*
				 * Changed By : 714599 Comments : Below variable mailtrackerType
				 * is used to refer the Differentiate the table like when HR
				 * Executive/Manager tries to access the Mail the details will
				 * be coming from IC_HR_MAIL_TRACKER_DETAILS else if any level0
				 * executive tries to access details will come from
				 * IC_MAIL_TRACKER_DETAILS
				 */
				ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
				String rolestoRestrictToupdateTicket = bundle
						.getString("rolesForHRMailTracker");
				String[] roles_List = rolestoRestrictToupdateTicket.split(",");

				String mailtrackerType = "";
				List<String> rolesList = Arrays.asList(roles_List);
				if (rolesList.contains(roleId)) {
					mailtrackerType = "HR";
				} else {
					mailtrackerType = "HelpDesk";
				}
				//GK820877 TODAY
				if (headerMenuNameforReference.equalsIgnoreCase("Mail-Tracker")) {
					html.input().type("button").value("Convert").onclick(
							"javascript:convertMailtoHelpdesk('"+ String.valueOf(value) + "','"
									+ mailtrackerType +"')").end();
					/*html.input().type("button").value("Discard").onclick(
							"javascript:discardMail('" + String.valueOf(value)
									+ "',this,'" + mailtrackerType + "')")
							.end();*/
				} else if (headerMenuNameforReference
						.equalsIgnoreCase("NOC-Alerts")) {
					html.input().type("button").value("Convert").onclick(
							"javascript:convertMailtoTechCR('"
									+ String.valueOf(value) + "')").end();
					html.input().type("button").value("Discard").onclick(
							"javascript:discardTechCRMail('"
									+ String.valueOf(value) + "',this)").end();
				}

				if (String.valueOf(statusval).equalsIgnoreCase("DISCARD")) {
					html = new HtmlBuilder();
					html.append("DISCARDED");
				}
				return html.toString();
			}
		});
		buttons.setFilterable(false);
		return buttons;
	}
	
	public HtmlColumn discardButtonsForMailTrackerNoc() {
		HtmlColumn discardButtons = new HtmlColumn("DISCARD").title("Discard");
		discardButtons.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, "MAIL_ID",
						rowcount);
				Object source = new HtmlCellEditor().getValue(item, "SOURCE",
						rowcount);
				Object statusval = new HtmlCellEditor().getValue(item,
						"STATUS", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
				String rolestoRestrictToupdateTicket = bundle
						.getString("rolesForHRMailTracker");
				String[] roles_List = rolestoRestrictToupdateTicket.split(",");

				String mailtrackerType = "";
				List<String> rolesList = Arrays.asList(roles_List);
				if (rolesList.contains(roleId)) {
					mailtrackerType = "HR";
				} else {
					mailtrackerType = "HelpDesk";
				}
				if ((headerMenuNameforReference.equalsIgnoreCase("Mail-Tracker")) && (!source.toString().equalsIgnoreCase("Mapps"))) {
					/*html.input().type("button").value("Convert").onclick(
							"javascript:convertMailtoHelpdesk('"
									+ String.valueOf(value) + "','"
									+ mailtrackerType + "')").end();*/
					html.input().type("button").value("Discard").onclick(
							"javascript:discardMail('" + String.valueOf(value)
									+ "',this,'" + mailtrackerType + "')")
							.end();
				} /*else if (headerMenuNameforReference
						.equalsIgnoreCase("NOC-Alerts")) {
					html.input().type("button").value("Convert").onclick(
							"javascript:convertMailtoTechCR('"
									+ String.valueOf(value) + "')").end();
					html.input().type("button").value("Discard").onclick(
							"javascript:discardTechCRMail('"
									+ String.valueOf(value) + "',this)").end();
				}
*/
				/*if (String.valueOf(statusval).equalsIgnoreCase("DISCARD")) {
					html = new HtmlBuilder();
					html.append("DISCARDED");
				}*/
				return html.toString();
			}
		});
		discardButtons.setFilterable(false);
		return discardButtons;
	}

	public HtmlColumn mailIDForMailTrackerNOC() {
		HtmlColumn mail_id = new HtmlColumn("MAIL_ID").title("Mail ID");
		mail_id.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, "MAIL_ID",
						rowcount);
				Object description = new HtmlCellEditor().getValue(item,
						"DESCRIPTION", rowcount);
				Object premiumMail = new HtmlCellEditor().getValue(item,
						"IS_PREMIUM_MAIL", rowcount);
				Object attachmentName = new HtmlCellEditor().getValue(item,
						"ATTACHMENT_NAME", rowcount);
				Object attachmentPath = new HtmlCellEditor().getValue(item,
						"ATTACHMENT_PATH", rowcount);

				HtmlBuilder html = new HtmlBuilder();
				if (attachmentName == null || attachmentPath == null) {
					html.a().href("#").styleClass("ApproveAuditLog").onclick(
							"javascript:displayMailDetail('" + value + "','"
									+ premiumMail + "');").close();
				} else {
					html.a().href("#").onclick(
							"javascript:displayMailEML('" + value + "','"
									+ attachmentName + "','" + attachmentPath
									+ "');").close();
				}
				html.append(value);
				html.aEnd();

				html.span().style("display:none").id(value + "_Html").close()
						.append(description.toString()).spanEnd();
				return html.toString();
			}
		});
		return mail_id;
	}

	public HtmlColumn ticketIDForMailTrackerNOC() {
		return new HtmlColumn("REFERENCE_ID").title("Ticket #");
	}

	public HtmlColumn fromAddressForMailTrackerNOC() {
		return new HtmlColumn("FROM_ADDRESS").title("From");
	}

	public HtmlColumn subjectForMailTrackerNOC() {
		return new HtmlColumn("SUBJECT").title("Subject");
	}

	public HtmlColumn descriptionForMailTrackerNOC() {
		return new HtmlColumn("DESCRIPTION").title("Description");
	}

	public HtmlColumn receivedDateForMailTrackerNOC() {
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
		return RECEIVED_DATE;
	}
	
	//Added by gk820877 on 5/12/2015
	public HtmlColumn sourceForMailTrackerNOC() {
		return new HtmlColumn("SOURCE").title("Source");
	}

	public HtmlColumn isPremiumMailForMailTracker() {

		HtmlColumn ispremiumMail = new HtmlColumn("IS_PREMIUM_MAIL")
				.title("Premium");
		ispremiumMail.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object premiumMail = new HtmlCellEditor().getValue(item,
						"IS_PREMIUM_MAIL", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (premiumMail.toString().equalsIgnoreCase("1")) {
					html.img().src("images/Premium.gif").title("Premium Mail");
				}
				return html.toString();
			}
		});
		ispremiumMail.setFilterable(false);
		return ispremiumMail;

	}

	public HtmlColumn isChangeRequestForHelpDesk() {
		HtmlColumn changeReq = new HtmlColumn("IS_CHANGE_REQUEST").title("CR");
		changeReq.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object changeReq = new HtmlCellEditor().getValue(item,
						"IS_CHANGE_REQUEST", rowcount);
				HtmlBuilder html = new HtmlBuilder();
				if (changeReq.toString().equalsIgnoreCase("Yes")) {

					html.img().src("images/ChangeRequestList.png").title(
							"Change Request").close();
				} else {
					html.img().src("images/HelpdeskList.png").title("Helpdesk")
							.close();
				}
				return html.toString();
			}
		});
		changeReq.setFilterable(false);
		return changeReq;
	}

	/*
	 * This method created to provide View & Edit buttons in List page.
	 */
	public HtmlColumn ViewOrEditActionForHelpDesk() {
		HtmlColumn viewOREdit = new HtmlColumn("ACTION_ON_TICKET")
				.title("View/Edit");
		viewOREdit.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new HtmlCellEditor().getValue(item, "TICKET_ID",
						rowcount);
				Object categoryId = new HtmlCellEditor().getValue(item,
						"CATEGORY_ID", rowcount);

				Object workFlowState = new HtmlCellEditor().getValue(item,
						"WORKFLOW_STATE", rowcount);

				Object subcategoryId = new HtmlCellEditor().getValue(item,
						"SUBCATEGORY_ID", rowcount);
				Object subject = new HtmlCellEditor().getValue(item, "SUBJECT",
						rowcount);
				try {
					subject = URLEncoder.encode(subject.toString(), "UTF-8");

				} catch (UnsupportedEncodingException e) {
					log.error("URL Encoding Error :" + e);
				}
				Object function = new HtmlCellEditor().getValue(item,
						"FUNCTION", rowcount);
				HtmlBuilder html = new HtmlBuilder();

				html.a().href().quote().append("HELPDESK_Edit.htm?id=").append(
						value).append("&subject=").append(subject).append(
						"&category=").append(categoryId)
						.append("&subcategory=").append(subcategoryId).append(
								"&function=").append(function).append(
								"&toDOAction=view").quote().close();
				html.append("View");
				html.aEnd();

				if (isTicketEditable(item, rowcount)) {
					html.append(" | ");
					html.a().href().quote().append("HELPDESK_Edit.htm?id=")
							.append(value).append("&subject=").append(subject)
							.append("&category=").append(categoryId).append(
									"&subcategory=").append(subcategoryId)
							.append("&function=").append(function).append(
									"&toDOAction=edit").quote().close();
					html.append("Edit");
					html.aEnd();
				}
				ApplicationContext ctx = COMMON_AppContext.getCtx();
				MASTER_CreateDAO MasterDaoImpl = (MASTER_CreateDAO) ctx
						.getBean(MASTER_EDITDAO_IMPL);
				MASTER_Create masterBean = MasterDaoImpl.getIsChildLinked(value
						.toString());
				if (masterBean.getREFERENCE_ID() == null) {
					ResourceBundle bundle = ResourceBundle
							.getBundle("iconnect");
					String wfStates = bundle
							.getString("masterTicketInvalidWorkflowState");
					String[] wfStates_List = wfStates.split(",");
					boolean isValidWorkFlowState = true;
					for (int index = 0; index < wfStates_List.length; index++) {
						if (workFlowState.toString().equals(
								wfStates_List[index])) {
							isValidWorkFlowState = false;
						}
						if (isValidWorkFlowState == false) {
							break;
						}
					}

					if (isValidWorkFlowState == true) {
						html.append(" | ");
						html.a().href().quote().append("#").quote().onclick(
								"getPopUpWindow(" + value + "); return false;")
								.styleClass("colorboxpopupWindow").close();

						html.append("Link Master");

						html.aEnd();
					}
				}

				return html.toString();
			}
		});
		viewOREdit.setFilterable(false);
		return viewOREdit;
	}

	/*
	 * This method provided to say whether the passed ticket is having Edit
	 * access to logged in member or not
	 */

	public boolean isTicketEditable(Object item, int rowcount) {

		Object functionname = new HtmlCellEditor().getValue(item, "FUNCTION",
				rowcount);
		Object groupid = new HtmlCellEditor().getValue(item,
				"ASSIGNED_GROUP_ID", rowcount);
		Object assigned_To = new HtmlCellEditor().getValue(item, "ASSIGNED_TO",
				rowcount);
		Object state = new HtmlCellEditor().getValue(item, "WORKFLOW_STATE",
				rowcount);
		Object requestor = new HtmlCellEditor().getValue(item,
				"REQUESTOR_NAME", rowcount);

		String function = functionname.toString();
		String assignedGroupID = String.valueOf(groupid);
		String assignedTo = String.valueOf(assigned_To);
		if (assignedTo.equalsIgnoreCase("-") || assignedTo == null) {
			assignedTo = null;
		} else {
			try {
				assignedTo = assignedTo.substring(
						assignedTo.lastIndexOf("(") + 1, assignedTo
								.lastIndexOf(")"));
			} catch (StringIndexOutOfBoundsException e) {
				System.out
						.println("in Stringindexoutof Bound Exception for Giving Edit status . Ticket#:"
								+ new HtmlCellEditor().getValue(item,
										"TICKET_ID", rowcount));
				assignedTo = null;
			}
		}
		String workflowState = String.valueOf(state);
		String requestedBy = String.valueOf(requestor);
		requestedBy = requestedBy.substring(requestedBy.lastIndexOf("(") + 1,
				requestedBy.lastIndexOf(")"));

		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		String workflowID = workflowimpl.getWorkflowID(function);

		boolean loginuserExists = false;
		boolean isbelongsToGroup = false;

		List<Map<String, Object>> assignedtolist = null;
		if (assignedGroupID != null)
			assignedtolist = HELPDESK_Edit_ReturnTypeVoidController
					.getLoggedInGroupMembers("All", assignedGroupID);
		else
			assignedtolist = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> anAssignedto : assignedtolist) {
			if (anAssignedto.containsValue(loggedInUser)) {
				loginuserExists = true;
				isbelongsToGroup = true;
				break;
			}
		}

		if (function.equalsIgnoreCase("Information Systems")
				|| function.equalsIgnoreCase("IT Infrastructure Management")
				|| function.equalsIgnoreCase("Function Correction Required")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}
			if (workflowState.equalsIgnoreCase("Out of Operation Hours")
					&& (roleName.equalsIgnoreCase("Level 0 Executive") || roleName
							.equalsIgnoreCase("Level 0 Manager"))) {
				loginuserExists = true;
			}
			if (assignedTo != null && !loginuserExists) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() >= 1) {
					return true;
				}
			}

		} else if (function.equalsIgnoreCase("Admin")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}
			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("Admin Executive")
					&& !roleName.equalsIgnoreCase("Admin Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}

		} else if (function.equalsIgnoreCase("HR")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}

			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("HR Executive")
					&& !roleName.equalsIgnoreCase("HR Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}
		} else if (function.equalsIgnoreCase("Finance")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}

			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("Finance Executive")
					&& !roleName.equalsIgnoreCase("Finance Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}

		} else if (function.equalsIgnoreCase("CHCS Applications")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}
			if (workflowState.equalsIgnoreCase("Out of Operation Hours")
					&& (roleName.equalsIgnoreCase("Level 0 Executive") || roleName
							.equalsIgnoreCase("Level 0 Manager"))) {
				loginuserExists = true;
			}
			if (assignedTo != null && !loginuserExists) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() >= 1) {
					return true;
				}
			}
		} else if (function.equalsIgnoreCase("IGATE Corporate University")) {//TODO 09/16/2014 SALI

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}

			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("iLearn Executive")
					&& !roleName.equalsIgnoreCase("iLearn Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}

		} else if (function.equalsIgnoreCase("Quality")) {////TODO 10/16/2014 Nisha

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}

			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("Quality Manager")
					&& !roleName.equalsIgnoreCase("Group Quality Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}

		} else if (function.equalsIgnoreCase("Delivery")) {

			if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& roleName.equalsIgnoreCase("User")) {
				loginuserExists = true;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& isbelongsToGroup) {
				loginuserExists = true;
			}
			if (assignedTo != null
					&& workflowState.equalsIgnoreCase("HelpDesk Queue")) {
				loginuserExists = true;
			}

			if (assignedTo != null && !loginuserExists) {
				return false;
			} else if (workflowState.equalsIgnoreCase("Open")
					&& !roleName.equalsIgnoreCase("SAP Executive")
					&& !roleName.equalsIgnoreCase("SAP Manager")
					&& !requestedBy.equalsIgnoreCase(loggedInUser)) {
				return false;
			} else if (requestedBy.equalsIgnoreCase(loggedInUser)
					&& !roleName.equalsIgnoreCase("User") && !isbelongsToGroup) {
				return false;
			} else {
				List<String> editpermissionmap = workflowimpl
						.getEditOnlyPermissionForField(workflowID, roleName,
								workflowState);
				if (editpermissionmap.size() > 1) {
					return true;
				}
			}

		}
		return false;
	}
}

class AvailabilityDroplistFilterEditor extends DroplistFilterEditor {
	@Override
	protected List<Option> getOptions() {
		List<Option> options = new ArrayList<Option>();
		options.add(new Option("Offline", "Offline"));
		options.add(new Option("Online", "Online"));
		return options;
	}
}

class AvailablePriorityDroplistFilterEditor extends DroplistFilterEditor {
	@Override
	protected List<Option> getOptions() {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<HELPDESK_Priority> ListofPriorities = MasterDataImpl
				.getAllPriorities();
		List<Option> options = new ArrayList<Option>();
		for (HELPDESK_Priority priority : ListofPriorities) {
			options.add(new Option(priority.getDESCRIPTION(), priority
					.getDESCRIPTION()));
		}
		return options;
	}
}

class AvailableFunctionsDroplistFilterEditor extends DroplistFilterEditor {
	@Override
	protected List<Option> getOptions() {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		COMMON_CacheDAO MasterDataImpl = (COMMON_CacheDAO) ctx
				.getBean("GetMasterData");
		List<Map<String, Object>> functions = MasterDataImpl.getCategoriesById(
				"PARENT_ID", 0);
		List<Option> options = new ArrayList<Option>();
		for (Map<String, Object> function : functions) {
			options.add(new Option(function.get("NAME").toString(), function
					.get("NAME").toString()));
		}
		return options;
	}
}

class AvailableStatesDroplistFilterEditor extends DroplistFilterEditor {
	@Override
	protected List<Option> getOptions() {
		ApplicationContext ctx = COMMON_AppContext.getCtx();
		WORKFLOW_DAO workflowimpl = (WORKFLOW_DAO) ctx
				.getBean("workFlowDAOImpl");
		List<WORKFLOW_States> ListofStates = workflowimpl.getAllStates("1");
		List<Option> options = new ArrayList<Option>();
		List<String> availableStates = new ArrayList<String>();

		for (WORKFLOW_States workflowstate : ListofStates) {
			if (!workflowstate.getState().equalsIgnoreCase(""))
				availableStates.add(workflowstate.getState());
		}

		ListofStates = workflowimpl.getAllStates("2");
		for (WORKFLOW_States workflowstate : ListofStates) {
			if (!workflowstate.getState().equalsIgnoreCase(""))
				availableStates.add(workflowstate.getState());
		}
		Collections.sort(availableStates);
		for (String workflowstate : availableStates) {
			options.add(new Option(workflowstate, workflowstate));
		}
		return options;
	}
}
