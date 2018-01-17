<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%@page import="com.igate.iconnect.BO.WORKFLOW_Role"%>
<%
String loginUser = (String)request.getSession().getAttribute("userLoginId");
User userObj = (User) request.getSession().getAttribute(loginUser);
String loginrole=userObj.getUserRole();
String id=request.getParameter("id");
List<WORKFLOW_Role> userRoleList=userObj.getUserRoleList();
int roleListSize=userRoleList.size();
String empName = userObj.getUserName();
String role = (String)userObj.getUserRole();
String subject=request.getParameter("subject");
String approvalExceptionFlag = userObj.getApprovalExceptionFlag();
/*if(subject.toString().contains("\"")){
	subject=subject.toString().replace("\"", "&#34;");
}*/
subject=URLEncoder.encode(subject.toString(), "UTF-8");
String category=request.getParameter("category");
String subcategory=request.getParameter("subcategory");
String function=request.getParameter("function");
String todoAction=request.getParameter("toDOAction"); // To Know whether the user clicked on View or Edit.

String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
String AI_search_URL=bundle.getString("AISearchURL");
/* ResourceBundle uninstallationCategory = ResourceBundle.getBundle("unistallation_category");
String categories=uninstallationCategory.getString("category_id"); */

%>

<%
String menuId=(String)session.getAttribute("menuId");
String parentMenuId=(String)session.getAttribute("parentMenuId");
String menuName=(String)session.getAttribute("menuName");
String parentMenuName=(String)session.getAttribute("parentMenuName");

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" href="<%=cssDirPath%>/validationEngine.jquery.css"
	media="screen" rel="stylesheet" />
	      <link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/iconnect.css" />
      <link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/ui.all.css" />
      <link media = "screen" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" />    
      <link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery.checkboxtree.css"/> 
      <link type="text/css" href="<%=cssDirPath%>/jquery.bt.css" media="screen"
	rel="stylesheet" />    
	 
	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/tab_style.css"/> 
	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/example.css"/> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/<%=cssDirPath%>/jmesa.css"></link>
	 <link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/user.css" />
	 <style>
		#ORCH_MSG_TR,#ASSET_MSG_TR{
		 font-weight: bold;
		text-decoration:blink;
		padding:5px;
		display:block;
		border:1px solid #00a1e4;
		background: #eee;
		}
		.tabnavi, .tabnavi ul{
		width:auto !important;
		}
	</style>  
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body>
<% String token = null; %>
<br>

<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td align="left" valign="bottom">
				<div id="hdEditMain" class="tabnavi">
					<ul> 
						<li><a href="HELPDESK_Edit.htm?id=<%=id%>&subject=<%=subject%>&category=<%=category%>&subcategory=<%=subcategory%>&function=<%=function%>" title="Details" class="selected">Details</a></li>
						<li><a href="#Attachment-Div" title="Attachments" onclick="getAttachments(<%=id%>)">Attachments</a></li>
						<li><a href="#Approver-Data" title="Approver" onclick="getApproversList(<%=id%>)">Approver</a></li>
						<li><a href="#Similar-Tickets" title="Similar Tickets" onclick="getSimilarTicketsData()">Similar Tickets</a></li>
					<!-- 	<li><a href="#Asset-Data" title="Asset Data" onclick="getAssetData()">Asset Data</a></li> -->
						<li><a href="#Related-Mails" onclick="getRelatedMails()" title="Related Mails">Related Mails</a></li>
						<li><a href="#Master-Ticket" title="Master-Ticket" onclick="getMasterTicket()">Master Ticket</a></li>
						<li><a href="#Audit-Log" title="Audit-Log" onclick="getAuditList(<%=id%>,1)">Audit-Log</a></li>
						 
					</ul>
				</div>
			</td>
		</tr>
	</table><br>
	
	<form:form modelAttribute="helpDeskObj" id="ticketdetailsform" action="HELPDESK_Edit.htm" name="ticketdetailsform" method="post"
	enctype="multipart/form-data">
	<table width="80%" cellpadding="0" cellspacing="0" border="0" align="center">		
			<tr>
			<td width="80%" align="center" valign="middle">
					<div style="${editbuttonaccess}" id="editAccess">
						<input type="button" id="Feedback" value="Feedback"	onclick="getFeedbackPopUp()" style="display:none" class="feedbackPopup"/> 
						<input type="button" id="Edit" value="Edit"	onclick="LockTicket()"/> 
						<input type="button" id="Link" value="Link"	class="colorboxpopupWindow" onclick="LockTicket1()"/>					
						<input type="button" id="Update" value="Update" onclick="UpdateIHDTicket()" />					
						<input type="button" id="DeLink" value="DeLink" onclick="DeLinkTicket()" style="display:none"/>		
					</div>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<span id="ORCH_MSG_TR" class="blinkText" style="display:none">Computer Name which is provided in ticket is not in IGATE network. Please connect the computer to IGATE network and change the ticket status to Responded to Processed with Software Installation.</span>	
					<span id="ASSET_MSG_TR" class="blinkClass" style="display:none">Kindly update the Asset for the Requestor in Asset Portal to get the "Resolved/Closed" status for this ticket.</span>
				<table class="createTable" id="editTable" border="0" cellspacing="0" cellpadding="3"
						width="100%" align="center">
						<tr class='none'>
							<td colspan="2"><span class="containerBlock1">Ticket Information
							<c:if test="${helpDeskObj.PREMIUM =='Yes'}">
								<img src="images/Premium.gif"/><span class="blink"><font color="red">Premium</font></span>
							</c:if>
							<input type="hidden" id="premium" value="${helpDeskObj.PREMIUM}"/>
							</span></td>
						</tr>
						<tr id="TICKET_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Ticket ID</td>
							<td><form:input path="TICKET_ID" id="TICKET_ID" style="width:284px"/></td>
						</tr>
						<% if(loginrole.equalsIgnoreCase("user")){%>
						<tr id="ADDITIONAL_INFO_TR">
							<td class="label">Additional Info</td>
							<td><form:textarea rows="5" style="width:284px" maxlength="500" path="ADDITIONAL_INFO"
								id="ADDITIONAL_INFO" onKeyDown="return onKeyDown()"/>
								<a href="#" onclick="replaceSpecialCharacters('ADDITIONAL_INFO')"></a>
								<input type="button" class="colorboxpopup" id="Edit" value="Add Additional Info" onClick="appendnotes('ADDITIONAL_INFO')"/>
								</td>
						</tr>
						<% }%>
						<tr id="WORKFLOW_STATE_TR" class="creationScreenAlternateTR">
							<td class="label">Status</td>
							<td><form:select path="WORKFLOW_STATE" id="WORKFLOW_STATE" class="myTextInputForEditPage">
								<form:options items="${WORKFLOW}" />
							</form:select></td>
						</tr>
						<tr id="FUNCTION_ID_TR">
							<td class="label">Function</td>
							<td><form:select path="FUNCTION_ID" id="FUNCTION_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${FUNCTION}" var="FUNCTION">
									<form:option value="${FUNCTION.CATEGORY_ID}">${FUNCTION.NAME}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="CATEGORY_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Category</td>
							<td><form:select path="CATEGORY_ID" id="CATEGORY_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${CATEGORY}" var="CATEGORY">
									<form:option value="${CATEGORY.CATEGORY_ID}">${CATEGORY.NAME}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="SUB_CATEGORY_ID_TR">
							<td class="label">Sub Category</td>
							<td><form:select path="SUB_CATEGORY_ID" id="SUB_CATEGORY_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${SUBCATEGORY}" var="SUBCATEGORY">
									<form:option value="${SUBCATEGORY.CATEGORY_ID}">${SUBCATEGORY.NAME}</form:option>
								</c:forEach>
							</form:select></td>
						</tr> 
						<tr id="DEPT_ID_TR" style="display:none">
							<td class="label">Requestor Department</td>
							<td id="deptTD">				
								<form:input path="DEPT_NAME" id="DEPT_NAME" style="width:284px" /></td>
							<td>
								<form:select path="DEPT_ID" id="DEPT_ID" style="display:none">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${DEPARTMENTS}" var="DEPARTMENTS">
									<form:option value="${DEPARTMENTS.DEPT_ID}">${DEPARTMENTS.DEPT_NAME}</form:option>
								</c:forEach>
								</form:select>
							</td>
						</tr> 
						<%-- <tr id="RECOMMENDED_PRIORITY_TR" style="${recomendedprioritydisplay}">
							<td class="label">Recommended Priority</td>
							<td><input name="RECOMMENDED_PRIORITY" id="RECOMMENDED_PRIORITY" value="${recomendedpriority}"/></td>
						</tr> --%>
						<tr id="PRIORITY_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Priority</td>
							<td><form:select path="PRIORITY_ID" id="PRIORITY_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${PRIORITYLIST}" var="PRIORITYLIST">
								<!-- Added to remove Low priority for Premium users -->
								<%-- <c:if test="${helpDeskObj.PREMIUM =='No'}">
									<form:option id="${PRIORITYLIST.PRIORITYID}" value="${PRIORITYLIST.PRIORITYID}">${PRIORITYLIST.DESCRIPTION}</form:option>
								</c:if>
								<c:if test="${helpDeskObj.PREMIUM =='Yes'}">
									<c:if test="${PRIORITYLIST.PRIORITYID != '1'}">
									<form:option value="${PRIORITYLIST.PRIORITYID}">${PRIORITYLIST.DESCRIPTION}</form:option>
									</c:if>
								</c:if> --%>
								<form:option id="${PRIORITYLIST.PRIORITYID}" value="${PRIORITYLIST.PRIORITYID}">${PRIORITYLIST.DESCRIPTION}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="SUBJECT_TR">
							<td class="label">Subject</td>
							<td><form:input path="SUBJECT"
								id="SUBJECT" maxlength="100"  style="width:284px"/></td>
						</tr>
						<tr id="DESCRIPTION_TR" class="creationScreenAlternateTR">
							<td class="label">Description</td>
							<td ><form:textarea  rows="5" style="width:284px" path="DESCRIPTION" 
								id="DESCRIPTION" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
							</td>
						</tr>	
						<%-- <tr id="iTrackLinkTR"><td colspan=2 align="right"><a href="#"  class="iTrackID"  onclick="getiTrackPage()" title="iTrack SFP Details." style="margin-right: 170px;">View</a></td></tr> --%>	
						<tr id="LOCATION_ID_TR">
							<td class="label">Location</td>
							<td><form:select path="LOCATION_ID" id="LOCATION_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${LOCATION}" var="LOCATION">
									<form:option value="${LOCATION.LOCATION_ID}"><c:out value="${LOCATION.CITY}" /></form:option>
								</c:forEach>
							</form:select></td>
						</tr> 
						<tr id="LOC_DET_ID_TR" style="display:none">
							<td class="label">Location Detail ID</td>
							<td><form:input path="LOC_DET_ID" id="LOC_DET_ID" style="width:284px"/></td>
						</tr>
			<%-- 			<tr id="BUILDING_TR">
							<td class="label">Building</td>
							<td><form:select path="BUILDING" id="BUILDING"> 
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${BUILDINGS}" var="BUILDINGS">
									<form:option value="${BUILDINGS.BUILDING}"><c:out value="${BUILDINGS.BUILDING}" /></form:option>
								</c:forEach>
							</form:select>
							</td>
						</tr>
						<tr id="FLOOR_TR">
							<td class="label">Floor</td>
							<td><form:select path="FLOOR" id="FLOOR">
								<form:option value="" label="--Please Select Building--" />
								<c:forEach items="${FLOORS}" var="FLOORS">
									<form:option value="${FLOORS.FLOOR}"><c:out value="${FLOORS.FLOOR}" /></form:option>
								</c:forEach>
							</form:select>
						</tr> --%>
						<tr id="CUBICLE_CODE_TR" class="creationScreenAlternateTR">
							<td class="label">Cubicle Code</td> 
							<td><form:input path="CUBICLE_CODE" id="CUBICLE_CODE" style="width:284px"/></td>
						</tr>
				<%-- 		<tr id="ODC_ID_TR"> 
							<td class="label">ODC</td>
							<td><form:select path="ODC_ID" id="ODC_ID"> 
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${ODCS}" var="ODCS">
									<form:option value="${ODCS.ODC_ID}"><c:out value="${ODCS.ODC_NAME}" /></form:option>
								</c:forEach>
							</form:select>
							</td>
						</tr> --%>
						<tr id="COPY_TO_TR">
							<td class="label">CC to (EmailID) :</td>
							<td><form:input path="COPY_TO" id="COPY_TO" style="width:284px" maxlength="500" /></td>
						</tr>
						<tr id="SOURCE_TR" class="creationScreenAlternateTR">
							<td class="label">Source</td>
							<td><form:select path="SOURCE" id="SOURCE" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<form:option value="Web">Web</form:option>
								<form:option value="Email">Email</form:option>
								<form:option value="Phone">Phone</form:option>
								<form:option value="Walk-In">Walk-In</form:option>
								<form:option value="Mobile">Mobile</form:option>
								<form:option value="iTrack">iTrack</form:option>
							</form:select></td>
						</tr>
						<tr id="SEVERITY_ID_TR">
							<td class="label">Severity</td>
							<td><form:select path="SEVERITY_ID" id="SEVERITY_ID" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${SEVERITYLIST}" var="SEVERITY">
									<form:option value="${SEVERITY.SEVERITY_ID}">${SEVERITY.DESCRIPTION}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="FILTER_GROUP_LOCATION_TR" style="${filterlocationDisplay}">
							<td class="label">Filter Groups By Location</td>
							<td><form:select path="FILTER_GROUP_LOCATION" id="FILTER_GROUP_LOCATION">
								<c:forEach items="${LOCATION}" var="LOCATION">
									<form:option value="${LOCATION.LOCATION_ID}"><c:out value="${LOCATION.CITY}" /></form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="ASSIGNED_GROUP_TR" class="creationScreenAlternateTR">
							<td class="label">Assigned Group</td>
							<td><form:select path="ASSIGNED_GROUP" id="ASSIGNED_GROUP" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${ASSIGNEDGROUPLIST}" var="GROUP">
									<form:option value="${GROUP.GROUP_ID}">${GROUP.GROUP_NAME}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr id="ASSIGNED_TO_TR">
							<td class="label">Assigned To</td>
							<td><form:select path="ASSIGNED_TO" id="ASSIGNED_TO" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${ASSIGNEDTO}" var="ASSIGNEDTO">
									<form:option value="${ASSIGNEDTO.member_id}">${ASSIGNEDTO.member_name_id}</form:option>
								</c:forEach>
							</form:select>
							<input type="checkbox" id="FILTERLOGGEDINUSERS" name="FILTERLOGGEDINUSERS" onclick="getGroupMembers('checkbox')" checked>All</input> 
							</td> 
						</tr>
						<tr id="CREATED_DATE_TR" class="creationScreenAlternateTR">
							<td class="label">Created Date</td>
							<td><form:input path="CREATED_DATE" id="CREATED_DATE" style="width:284px" /></td>
						</tr>
						<tr id="CREATED_DATE_STORAGE_TR" style="display:none">
							<td class="label">Created Date Storage</td>
							<td><form:input path="CREATED_DATE_STORAGE" id="CREATED_DATE_STORAGE" /></td>
						</tr>
						<!-- Modified by Sali -->
						<tr id="CHANGED_DATE_STORAGE_TR" style="display:none">
							<td class="label">Changed Date Storage</td>
							<td><form:input path="CHANGED_DATE_STORAGE" id="CHANGED_DATE_STORAGE" /></td>
						</tr>
						<tr id="IS_CHANGE_REQUEST_TR" style="display:none">
							<td class="label">Changed Date Storage</td>
							<td><form:input path="IS_CHANGE_REQUEST" id="IS_CHANGE_REQUEST" /></td>
						</tr>
						<tr id="IS_MASTER_LINK_TR" style="display:none">
							<td></td>
							<td><form:input path="IS_MASTER_LINK" id="IS_MASTER_LINK" /></td>		
						</tr>
						<!-- eND OF CHANGE -->
						<tr id="ECT_TR">
							<td class="label">Expected Completion Time</td>
							<td><form:input path="ECT" id="ECT"  style="width:284px" /></td>
						</tr>
						<tr id="ECT_STORAGE_TR" style="display:none">
							<td class="label">ECT Storage</td>
							<td><form:input path="ECT_STORAGE" id="ECT_STORAGE" /></td>
						</tr>
						<tr id="TOTAL_SLA_TIME_TR" class="creationScreenAlternateTR">
							<td class="label">Total SLA Time</td>
							<td><form:input path="TOTAL_SLA_TIME" id="TOTAL_SLA_TIME" style="width:284px"/></td>
						</tr>
						<tr id="TIME_REMAINING_TR">
							<td class="label">Time Remaining</td>
							<td><form:input path="TIME_REMAINING" id="TIME_REMAINING" style="width:284px"/></td>
						</tr>
						<tr id="SLA_STATUS_TR" class="creationScreenAlternateTR">
							<td class="label">SLA Status</td>
							<td><form:input path="SLA_STATUS" id="SLA_STATUS" style="width:284px"/></td>
						</tr>
						<tr id="USERS_IMPACTED_TR">
							<td class="label">Impacted Users</td>
							<td><form:input path="USERS_IMPACTED" id="USERS_IMPACTED" maxlength="5" style="width:284px"/></td>
						</tr>
						<tr id="CREATED_BY_TR" class="creationScreenAlternateTR">
							<td class="label">Created By</td>
							<td><form:input path="CREATED_BY" id="CREATED_BY" style="width:284px" /></td>
						</tr>
						<tr class='none' id="Emergency_Det">
							<td colspan="2"><span class="containerBlock1">Deployment Activity</span></td>
						</tr>
					<%-- 	<tr id="IS_EMERGENCY_TICKET_TR">
							<td class="label">Emergency Ticket</td>
							<td>
								<form:select path="IS_EMERGENCY_TICKET" id="IS_EMERGENCY_TICKET"> 				 	
								 	<form:option value="No" label="No" />
								 	<form:option value="Yes" label="Yes" />
								 </form:select>
							</td>
						</tr> --%>
						<%-- <tr id="EMERGENCY_TYPE_TR">
							<td class="label">Activity Type</td>
							<td>
								<form:select path="EMERGENCY_TYPE" id="EMERGENCY_TYPE"> 
									<form:option value="NA" label="NA" />
								 	<form:option value="Data Change" label="Data Change" />
								 	<form:option value="Emergency Change" label="Emergency Change" />
								 </form:select>
							</td>
						</tr> --%>
					<%-- 	<tr id="LEVEL2_GROUP_TR">
							<td class="label">Level2 Group</td>
							<td><form:input path="LEVEL2_GROUP" id="LEVEL2_GROUP" style="width:284px" value="DBA" />
								<input type="hidden" id="DBAMemCheck" value="${DBAMemChk}"/>
								<input type="hidden" id="attachmentCountScript" value="${attachmentCountScript}"/>
								<input type="hidden" id="attachmentCountTestRep" value="${attachmentCountTestRep}"/>
								<input type="hidden" id="attachmentCountAppMail" value="${attachmentCountAppMail}"/>
							</td>
						</tr> --%>
						<%-- <tr id="LEVEL2_ASSIGNED_ENGINEER_TR">
							<td class="label">Level2 Assigned Engineer</td>
							<td><form:input path="LEVEL2_ASSIGNED_ENGINEER" id="LEVEL2_ASSIGNED_ENGINEER" size="45"/></td>
						</tr> --%>
					<%-- 	<tr id="SUB_STATUS_TR">
							<td class="label">Activity Status</td>
							<td>
								<form:select path="SUB_STATUS" id="SUB_STATUS"> 
									<form:option value="NA" label="NA" />
								 	<form:option value="Submitted to DBA" label="Submitted to DBA" />				 	
									<form:option value="Work In Progress" label="Work In Progress" />
									<form:option value="Send Back to IS team" label="Send Back to IS team" />
									<form:option value="Completed" label="Completed" />
								 </form:select>
							</td>
						</tr> --%>
					<%-- 	<tr id="DEPLOYMENT_INSTRUCTION_TR">
							<td class="label">Deployment Instruction</td>
							<td><form:textarea rows="5" style="width:284px" maxlength="500" path="DEPLOYMENT_INSTRUCTION" id="DEPLOYMENT_INSTRUCTION"/></td>
						</tr> --%>
					<%-- 	<tr id="REASON_SEND_BACK_TR">
							<td class="label">Reason for Send Back</td>
							<td><form:textarea rows="5" style="width:284px" maxlength="500" path="REASON_SEND_BACK" id="REASON_SEND_BACK"/></td>
						</tr> --%>
						<tr class='none'>
							<td colspan="2"><span class="containerBlock1">User
							Information</span></td>
						</tr>
				
						<tr id="REQUESTED_BY_TR" class="creationScreenAlternateTR">
							<td class="label">Requested By</td>
							<td><form:input path="REQUESTED_BY" id="REQUESTED_BY" style="width:284px" /></td>
						</tr>
						<tr id="ON_BEHALF_OF_TR">
							<td class="label">On Behalf Of(EmployeeID)</td>
							<td><form:input path="ON_BEHALF_OF" id="ON_BEHALF_OF" maxlength="10" style="width:284px" />
								<a id="validateEmployee"
									href="javascript:ValidateEmpId(document.getElementById('ON_BEHALF_OF').value)"
									class="label"
									title="Confirm the existence of active Employee in Organization." style="display:none"><u>Validate</u>&nbsp;
								</a>
								<span
									id="empResultMessage" class="invalid_text">
								</span>
							</td>
						</tr>
					<%-- 	<tr id="GRADE_TR">
							<td class="label">Grade</td>
							<td><form:input path="GRADE" id="GRADE" /></td>
						</tr> --%>
						<%-- <tr id="REPORTING_MGR_TR">
							<td class="label">Reporting Manager</td>
							<td><input type="text" id="REPORTING_MGR" disabled
								value="${REQUESTOR.REPORTING_MANAGER_NAME}(${REQUESTOR.REPORTING_MANAGER_ID})" style="width:284px" /></td>
						</tr> --%>
						<tr id="PROJECT_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Project</td>
							<td>
							<form:input path="PROJECT_NAME" id="PROJECT_ID" style="width:284px"/>
							</td></tr>
							
						<tr id="MASTER_PROJECT_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Master Project</td>
							<td><form:input path="PROJECT_NAME" id="PROJECT_NAME" style="width:284px"/></td>
						</tr>
						<tr id="LEVEL2_PROJECT_ID_TR">
							<td class="label">Level 2 Project</td>
							<td><form:input path="LEVEL_2_PROJECT_NAME" id="LEVEL_2_PROJECT_NAME" style="width:284px" /></td>
						</tr>
						<tr id="LEVEL3_PROJECT_ID_TR" class="creationScreenAlternateTR">
							<td class="label">Level 3 Project</td>
							<td><form:input path="LEVEL_3_PROJECT_NAME" id="LEVEL_3_PROJECT_NAME" style="width:284px" /></td>
						</tr>
						<tr id="MANAGER_ID_TR" style="display:none">
							<td class="label">Project/Process Manager</td>
							<td><form:input path="MANAGER_ID" id="MANAGER_ID" style="width:284px" /></td>
						</tr>
						<tr id="CUSTOMER_NAME_TR"> 
							<td class="label">Customer Name</td>
							<td><input type="text" id="CUSTOMER_NAME" style="width:284px" disabled
								value="${REQUESTOR.CUSTOMER_NAME}" /></td>
						</tr>
						<tr id="DEPARTMENT_TR" class="creationScreenAlternateTR">
							<td class="label">Department</td>
							<td><input type="text" id="DEPARTMENT" style="width:284px" disabled
								value="${REQUESTOR.DU_NAME}" /></td>
						</tr>
						<!--<tr id="ORGANIZATION_TR">
							<td class="label">Organization</td>
							<td><input type="text" id="ORGANIZATION" disabled
								value="${REQUESTOR.ORGANIZATION}" /></td>
						</tr>-->
						<tr id="CONTACT_NO_TR">
							<td class="label">Contact Number(Primary)</td>
							<td><form:input path="CONTACT_NO" id="CONTACT_NO" style="width:284px" maxlength="15" /></td>
						</tr>
						<tr id="ALTERNATE_CONTACT_NO_TR" class="creationScreenAlternateTR">
							<td class="label">Alternate Contact Number</td>
							<td><form:input path="ALTERNATE_CONTACT_NO"
								id="ALTERNATE_CONTACT_NO" maxlength="15" style="width:284px"/></td>
						</tr>
						<tr id="USER_LOCATION_TR">
							<td class="label">User Location</td>
							<td><input type="text" id="USER_LOCATION" style="width:284px" disabled
								value="${REQUESTOR.CITY}"></input></td>
						</tr>
						<tr class='none'>
							<td colspan="2"><span class="containerBlock1">Response
							Information</span></td>
						</tr>
						<% if(!loginrole.equalsIgnoreCase("user")){%>
						<tr id="ADDITIONAL_INFO_TR" class="creationScreenAlternateTR">
							<td class="label">Additional Info</td>
							<td><form:textarea rows="5" style="width:284px" maxlength="500" path="ADDITIONAL_INFO"
								id="ADDITIONAL_INFO" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
								<a href="#" onclick="replaceSpecialCharacters('ADDITIONAL_INFO')"></a>
								<input type="button" class="colorboxpopup" id="Edit" value="Add Additional Info" onClick="appendnotes('ADDITIONAL_INFO')"/>
								</td>
						</tr>
						<% }%>
						<tr id="RESOLUTION_STATUS_TR" style="display:none;">
							<td class="label">Resolution Status</td>
							<td>
							<form:select path="RESOLUTION_STATUS" id="RESOLUTION_STATUS">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${RESOLUTIONSTATUS}" var="RESOLUTIONSTATUS">
									<form:option value="${RESOLUTIONSTATUS.REASON_ID}">${RESOLUTIONSTATUS.DESCRIPTION}</form:option>
								</c:forEach>
							</form:select>
							</td>
						</tr>
						<tr id="RESOLUTION_COMMENTS_TR">
							<td class="label">Resolution Comments</td>
							<td><form:textarea rows="5" style="width:284px" maxlength="999" path="RESOLUTION_COMMENTS"
								id="RESOLUTION_COMMENTS" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()" />
								<a href="#" onclick="replaceSpecialCharacters('RESOLUTION_COMMENTS')">
							</a></td>
						</tr>
						<tr id="PRIVATE_NOTES_TR" class="creationScreenAlternateTR">
							<td class="label">Private Notes</td>
							<td><form:textarea rows="5" style="width:284px" path="PRIVATE_NOTES"
								id="PRIVATE_NOTES" maxlength="999" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
								<a href="#" onclick="replaceSpecialCharacters('PRIVATE_NOTES')"></a>
								<input type="button" class="colorboxpopup" id="Edit" value="Add Private Notes" onClick="appendnotes('PRIVATE_NOTES')"/> 
							</td> 
						</tr>
						<tr id="OUT_OF_SLA_REASON_TR">
							<td class="label">Out of SLA Reason</td>
							<td><form:select path="OUT_OF_SLA_REASON" id="OUT_OF_SLA_REASON" class="myTextInputForEditPage">
								<form:option value="" label="--Please Select--" />
								<c:forEach items="${SLAREASONS}" var="SLAREASONS">
									<form:option value="${SLAREASONS.REASON_ID}">${SLAREASONS.DESCRIPTION}</form:option>
								</c:forEach>
							</form:select></td> 
						</tr>
						<tr id="OUT_OF_SLA_COMMENTS_TR" class="creationScreenAlternateTR">
							<td class="label">Out of SLA Comments</td>
							<td><form:textarea rows="5" style="width:284px" path="OUT_OF_SLA_COMMENTS"
								id="OUT_OF_SLA_COMMENTS" maxlength="999" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
								<a href="#" onclick="replaceSpecialCharacters('OUT_OF_SLA_COMMENTS')">
							</a></td>
						</tr>
						<tr id="CLOSED_DATE_TR">
							<td class="label">Closed Date</td>
							<td><form:input path="CLOSED_DATE" id="CLOSED_DATE" style="width:284px"/></td>
						</tr>
						<tr id="ONHOLD_COMMENTS_TR" class="creationScreenAlternateTR">
							<td class="label">OnHold Comments</td>
							<td><form:textarea rows="5" style="width:284px" path="ONHOLD_COMMENTS"
								id="ONHOLD_COMMENTS" maxlength="999" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()" />
								<a href="#" onclick="replaceSpecialCharacters('ONHOLD_COMMENTS')">
							</a></td>
						</tr>
						<tr id="REMINDER_DATE_TR">
							<td class="label">Reminder Date</td>
							<td><form:input path="REMINDER_DATE" id="REMINDER_DATE" style="width:284px" /></td>
						</tr>
						<tr id="FEEDBACK_TR" class="creationScreenAlternateTR">
							<td class="label">FeedBack</td>
							<td>
								<!--<form:select path="FEEDBACK" id="FEEDBACK">
									<form:option value="None" label="None" />
									<form:option value="Good" label="Good" />
									<form:option value="Average" label="Average" />
									<form:option value="Bad" label="Bad" />
								</form:select>
							--><form:input path="FEEDBACK" id="FEEDBACK" style="width:284px"></form:input></td>
						</tr>
						<tr id="FEEDBACK_COMMENTS_TR">
							<td class="label">FeedBack Comments</td>
							<td>
							<form:textarea rows="5" style="width:284px" path="FEEDBACK_COMMENTS"
								id="FEEDBACK_COMMENTS" maxlength="999" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
							</td>
						</tr>
						<tr id="REOPEN_COMMENTS_TR" class="creationScreenAlternateTR">
							<td class="label">Reopen Comments</td>
							<td><form:textarea rows="5" style="width:284px" path="REOPEN_COMMENTS"
								id="REOPEN_COMMENTS" maxlength="999" onmouseover="getDescVal1(this.id)" onKeyDown="return onKeyDown()"/>
							<a href="#" onclick="replaceSpecialCharacters('REOPEN_COMMENTS')">
							</a></td>
						</tr>
						<%-- <tr id="INVOICE_NO_TR">
							<td class="label">Invoice No</td>
							<td><form:input path="INVOICE_NO" id="INVOICE_NO" maxlength="30" /></td>
						</tr>
						<tr id="VENDOR_NAME_TR">
							<td class="label">Vendor Name</td>
							<td><form:input path="VENDOR_NAME" id="VENDOR_NAME" maxlength="50" /></td>
						</tr> --%>
						<tr id="VERSION_NO_TR">
							<td class="label">Version No</td>
							<td><form:input path="VERSION_NO" id="VERSION_NO" maxlength="5" style="width:284px"/></td>
						</tr>
						<!-- Modified by Sali -->
						<tr id="OS_DETAILS_TR" style="display:none">
							<td class="label">Os Details</td>
							<td><input name="OS_DETAILS" id="OS_DETAILS" /></td>
						</tr>
						<tr id="BROWSER_DETAILS_TR" style="display:none">
							<td class="label">Browser Details</td>
							<td><input name="BROWSER_DETAILS" id="BROWSER_DETAILS" /></td> 
						</tr>
						<tr>
						<td><form:hidden path="REFERENCE_ID" id="REFERENCE_ID" ></form:hidden></td>
						</tr>
						<!-- eND OF CHANGE -->
				        <!-- Modified by Sankari(712836) -->
				         <tr id="SEARCH_REFERENCE_TR" class="creationScreenAlternateTR">
							<td class="label">Search Reference</td>
							<td align="left"><form:checkbox size="80"
						id="SEARCH_REFERENCE" path="SEARCH_REFERENCE"
						onclick="$(this).val($(this).attr('checked'))"
								title="If you want to provide this resolution comments for search page, check this box." /></td>
						</tr>		
				<tr class="none" style="display: none">
					<td><input id="selectInput" class="myTextInput"></input></td>
				</tr>
	  </table>
		
		
		 		 </td>
		   </tr>
			<tr>
		       	<td align="center" id="UpdateButtonTd" style="${editbuttonaccess}"> 
		       		<input type="button" id="Update" value="Update" onclick="UpdateIHDTicket()" />		
		       	</td>
		    </tr>
	</table>
	<input type="hidden" id="SUB_PROJECT_ID" name="SUB_PROJECT_ID"></input>
	<form:input type="hidden" id="LEVEL_2_PROJECT_ID" path="LEVEL_2_PROJECT_ID"></form:input>
	<form:input type="hidden" id="LEVEL_3_PROJECT_ID" path="LEVEL_3_PROJECT_ID"></form:input>
	<form:input type="hidden" id="LEVEL_3_PROJECT_ID" path="LEVEL_3_PROJECT_ID"></form:input>
	<form:input type="hidden" id="IS_AUTOMATION_REQUIRED" path="IS_AUTOMATION_REQUIRED"></form:input>
	<form:input type="hidden" id="IS_ORCH_REQUIRED" path="IS_ORCH_REQUIRED"></form:input>
	<form:input type="hidden" id="CORE_ID" path="CORE_ID"></form:input>
	<form:input type="hidden" id="ALLOW_CLOSEABLE_FLAG" path="ALLOW_CLOSEABLE_FLAG"></form:input>
	</form:form>

<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">

          
            <div id="Audit-Log" style="display:none" class="tabLiner">
				<table width="100%" class="myDataTable" id="auditLog" cellpadding="0" cellspacing="0">
					<tr>
						<th width="10%">History ID</th>
						<th width="13%">Previous State</th>
						<th width="20%">Current State</th>
						<th width="20%">Changed By</th>
						<th width="25%">Changed Date</th>
						<th width="22%">Action</th>						
					</tr>					
				</table>	
				<table width="100%" id="auditLogDetails" style="display:none">
					<tr><td width="15%" align = "left"><input type="button" value="Back to List" onclick="DisplayDivs('auditLog','auditLogDetails')"/></td></tr>
					<tr>						
						<th width="10%">Field</th>
						<th width="13%">Old Value</th>
						<th width="20%">New Value</th>										
					</tr>					
				</table>	
							 
            </div>          
            <div id="Attachment-Div" style="display:none" class="tabLiner"><br>
            	<table border="0" id="attachment_field" cellspacing="5" cellpadding="5" width="80%">
					<tr>
						<th> Attachment Type </th>
						<th>Attachment Name</th>					
					</tr>		
				</table>
            </div>
            <!--<div id="Asset-Data">
            	No Data
            </div>-->
            <div id="Approver-Data" style="display:none" class="tabLiner"><br>
   				<table width="100%" class="myDataTable" id="ApproverLog" cellpadding="0" cellspacing="0">
				</table>	
            </div>
            <div id="Asset-Data" style="display:none" class="tabLiner"><br>
            	<table width="100%" class="myDataTable" id="ticketAssetLink" cellpadding="0" cellspacing="0">			
				</table>
            </div>
            <div id="Similar-Tickets" style="display:none" class="tabLiner"><br>
            </div>
            <div id="Related-Mails" style="display:none" class="tabLiner"><br>
            </div>
              <div id="Master-Ticket"  style="display:none" class="tabLiner">
			<table width="100%" id="masterTicketDett" class="myDataTable"  cellpadding="0" cellspacing="0">
				<tr>
					<th>Master Ticket ID</th>
					<th>Workflow Status</th>
					<th>Subject</th>
					<th>Description</th>
					<th>ECT</th>
				</tr>
			</table>
			</div>
           
<br>

 
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.accordion.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/HELPDESK_Edit.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/Orchestration.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
	  <script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
      <script src="<%=jsDirPath%>/json.min.js"></script>
      <script src="<%=jsDirPath%>/custom/HELPDESK_AuditLog.js"></script>  
      <script src="<%=jsDirPath%>/custom/UNIVERSAL_Attachment.js"></script> 
      <script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.popupWindow.js"></script>  
      <!--[if IE]><script src="<%=jsDirPath%>/excanvas.compiled.js" type="text/javascript" charset="utf-8"></script><![endif]--> 
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.min.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.blockUI.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/custom/HELPDESK_AssetInformation.js"></script> 
        
 
<script type="text/javascript">
	var logged_user_role = "<%=loginrole%>";
	var looged_user_id = "<%=loginUser%>";
	var search_URL="<%=AI_search_URL%>";
	var roleListSize="<%=roleListSize%>";
	<%-- var categories="<%=categories%>"; --%>


	var menuID = '<%=menuId%>';
	var parentMenuId = '<%=parentMenuId%>';
	var menuName = '<%=menuName%>';
	var parentMenuName = '<%=parentMenuName%>';
	var approvalFlag ="0";

/********************AUTO ASSIGNMENT*********************************************/
 
 var priorityChangeFlag=0;
 var statusChangeFlag=0;
 var assignedToChangeFlag=0;
 var oldPriority='';
 var newPriority='';
 var oldStatus='';
 var newStatus='';
 var oldAssignedTo='';
 var newAssignedTo='';
var assignedGroupChangeFlag=0;
var isAutoAssignmentActiveArray=new Array();
var isAutoAssignmentReqdFlag=0;

var approvalExceptionFlag="${helpDeskObj.IS_APPROVAL_REQUIRED}";
/********************AUTO ASSIGNMENT*********************************************/	
	
	var isMasterTicketLinked="${helpDeskObj.IS_MASTER_LINK}";
	var viewmap = "${viewpermissionmap}";
		viewmap = viewmap.substring(1,viewmap.length-1);
	var viewmaparray = new Array();
		if(viewmap!=""){
			viewmaparray = viewmap.split(",")
		}
	var editmap = "${editpermissionmap}";
		editmap = editmap.substring(1,editmap.length-1);
	var editmaparray = new Array();
		if(editmap!=""){
			editmaparray = editmap.split(",");
		}
	var nonemap = "${nonepermissionmap}";
		nonemap = nonemap.substring(1,nonemap.length-1);
	var nonemaparray = new Array();
		if(nonemap!=""){
			nonemaparray = nonemap.split(",");
		} 

		var roleListForRestriction = "${rolesToRestrictTheUpdateTicket}";
		roleListForRestriction = roleListForRestriction.substring(1,roleListForRestriction.length-1);
	var roleListForRestrictionarray = new Array();
		if(roleListForRestriction!=""){
			roleListForRestrictionarray = roleListForRestriction.split(",");
			var temp = new Array();
			for(var i=0;i<roleListForRestrictionarray.length;i++){
				temp.push(jQuery.trim(roleListForRestrictionarray[i]));
			}
			roleListForRestrictionarray = new Array();
			roleListForRestrictionarray = temp;
		} 
	$('textarea[maxlength],input[maxlength]').live('keyup blur', function() {
        // Store the maxlength and value of the field.
        var maxlength = $(this).attr('maxlength');
        var val = $(this).val();

        // Trim the field if it has content over the maxlength.
        if (val.length > maxlength) {
            $(this).val(val.slice(0, maxlength));
        }
	});

	$('.blink').each(function() {
							    var elem = $(this);
							    setInterval(function() {
							        if (elem.css('visibility') == 'hidden') {
							            elem.css('visibility', 'visible');
							        } else {
							            elem.css('visibility', 'hidden');
							        }    
							    }, 500);
							}); 
	
 
</script>

<script type="text/javascript">  
            function getApproversList(ticketID){

            	if(inEditMode){
            		if(confirm("Do you want to continue with out updating the ticket?")){
            	    	inEditMode = false;
            		  	unlockTheTicket($("#TICKET_ID").val(),1);
            	      }else{
            				return false;
            			}
            	}
            	$("#hdEditMain ul li a").removeClass("selected");
            	$("#hdEditMain ul li a").eq("2").addClass("selected");
                        	
            	blockUI();
	            	$.getJSON("hdAppRejListforTicket.htm",{ticketNo:ticketID},function(data){		

	    				$("#Audit-Log").hide();
	    				$("#ticketdetailsform").hide();
	    				$("#Attachment-Div").hide();
	    				$("#Approver-Data").show();
	    				$("#Asset-Data").hide();
	    				$("#Similar-Tickets").hide();
	    				$("#Related-Mails").hide();
	    				$("#Master-Ticket").hide();
	    				
	            		            	
	            		var attachmentHtml = "";
	            			attachmentHtml +="<tr><th>Ticket ID</th><th>Comments</th><th>Exception Approval</th><th>Exception Approval Start Dt</th><th>Exception Approval End Dt</th><th>Action</th><th>Action By</th><th>Action Date</th></tr>";
	            		if(data.length!=0)
	            			{	
	            				 for(var i=0;i<data.length;i++) 
	            				 {
	            					 attachmentHtml +="<tr>";
	            					 attachmentHtml +="<td>"+data[i].ticket_ID+"</td>";
	            					 attachmentHtml +="<td>"+data[i].comments+"</td>";
									 attachmentHtml +="<td>"+data[i].is_EXCEPTIONAL_APPROVAL+"</td>";
									 if(data[i].exception_START_DATE!=null)
										attachmentHtml +="<td>"+data[i].exception_START_DATE+"</td>";
									 else
										attachmentHtml +="<td>"+'-'+"</td>";
									 if(data[i].exception_END_DATE!=null)
										attachmentHtml +="<td>"+data[i].exception_END_DATE+"</td>";
									 else
										attachmentHtml +="<td>"+'-'+"</td>";									 
	            					 attachmentHtml +="<td>"+data[i].status+"</td>";
	            					 attachmentHtml +="<td>"+data[i].created_NAME+"( "+data[i].created_BY+" )</td>";
	            					 attachmentHtml +="<td>"+data[i].created_DATE+"</td>";
	            					 attachmentHtml +="</tr>";		
	            				}		
	            				 $("#ApproverLog").html(attachmentHtml)	;	
	            			}		
	            		else{
	            			$("#ApproverLog").html("<b>No Data.</b>")
	            		}
	            		unblockUI();
	            	});

            	
                }

            function getSimilarTicketsData(){ 

            	if(inEditMode){
            		if(confirm("Do you want to continue with out updating the ticket?")){
            	    	inEditMode = false;
            		  	unlockTheTicket($("#TICKET_ID").val(),1);
            	      }else{
            				return false;
            			}
            	}
            	$("#hdEditMain ul li a").removeClass("selected");
            	$("#hdEditMain ul li a").eq("3").addClass("selected");
            	blockUI();            	          	
            		var catId=$('#CATEGORY_ID option:selected').val();
            	var subCatId=$('#SUB_CATEGORY_ID option:selected').val();
            	var similrTicketsURL = "HELPDESK_SimilarTicketslistPage.htm?menuId=0&parentMenuId=1&menuName=Closed Request&parentMenuName=HelpDesk&subject=<%=subject%>&category="+catId+"&subcategory="+subCatId;
            	 $.ajaxSetup({ cache: false });
           	  $.getJSON(similrTicketsURL,{},function(data) {	
           		 
        		$("#Audit-Log").hide();
				$("#ticketdetailsform").hide();
				$("#Attachment-Div").hide();
				$("#Approver-Data").hide();
				$("#Asset-Data").hide();
				$("#Similar-Tickets").show();
				$("#Similar-Tickets").html(data);
				$("#Related-Mails").hide();
				$("#Master-Ticket").hide();

				$("#Similar-Tickets div.jmesa").find("tr.toolbar").hide();
            	$("#Similar-Tickets div.jmesa").find("tr.filter").hide();
            	unblockUI();
           	  });
            }
 
            function getRelatedMails(){

            	if(inEditMode){
            		if(confirm("Do you want to continue with out updating the ticket?")){
            	    	inEditMode = false;
            		  	unlockTheTicket($("#TICKET_ID").val(),1);
            	      }else{
            				return false;
            			}
            	}
            	
            	$("#hdEditMain ul li a").removeClass("selected");
            	$("#hdEditMain ul li a").eq("5").addClass("selected");
            	blockUI();
            	var relatedMailsURL=null;
                var fucntion = $("#FUNCTION_ID :selected").text(); 
            	if(fucntion=="HR"){
            	 relatedMailsURL = "HELPDESK_RelatedMailslistPage_HR.htm?menuId=0&functionName=HR&parentMenuId=4&menuName=RelatedMails&parentMenuName=Mail-Tracker&ticketid=<%=id%>";
            	}else{
            		relatedMailsURL = "HELPDESK_RelatedMailslistPage.htm?menuId=0&parentMenuId=4&menuName=RelatedMails&parentMenuName=Mail-Tracker&ticketid=<%=id%>";                	
            	}
             	 $.ajaxSetup({ cache: false });
            	  $.getJSON(relatedMailsURL,{},function(data) {	

              		$("#Audit-Log").hide();
      				$("#ticketdetailsform").hide();
      				$("#Attachment-Div").hide();
      				$("#Approver-Data").hide();
      				$("#Asset-Data").hide();
      				$("#Similar-Tickets").hide();
      				$("#Master-Ticket").hide();

      				$("#Related-Mails").html(data); 
      				$("#Related-Mails").show();
      				 
    				$("#Related-Mails div.jmesa").find("tr.toolbar").hide(); 
                	$("#Related-Mails div.jmesa").find("tr.filter").hide();
                	unblockUI();
            	  });
               }
            
            var userid = "<%=loginUser%>";
            var username ="<%=empName%>";
            var loginrole = "<%=role%>";
            var fucntion = "<%=function%>"; 
            var todoAction = "<%=todoAction%>";
            var subjectVal_Search="<%=subject%>";
            var id="<%=id%>";
           	var hd_url="HELPDESK_Edit.htm?id=<%=id%>&subject=<%=subject%>&category=<%=category%>&subcategory=<%=subcategory%>&function=<%=function%>";
            var previous_Url="UNIVERSAL_ListPage.htm?menuId=<%=menuId%>&parentMenuId=<%=parentMenuId%>&menuName=<%=menuName%>&parentMenuName=<%=parentMenuName%>";
            //var previous_Url="UNIVERSAL_ListPage.htm?menuId=13&parentMenuId=1&menuName=Open Request&parentMenuName=Helpdesk";
         if(loginrole=="User"){
      //      	$("#hdEditMain").find("li").eq("2").hide();
            	$("#hdEditMain").find("li").eq("3").hide();
            	//$("#hdEditMain").find("li").eq("4").hide();
            	$("#hdEditMain").find("li").eq("4").hide();
            	$("#hdEditMain").find("li").eq("5").hide();
            }else if(fucntion!="Information Systems" && fucntion!="IT Infrastructure Management"){
            	$("#hdEditMain").find("li").eq("3").hide();
            	$("#hdEditMain").find("li").eq("4").hide();
            }

            $(document).ready(function(){
            	$("#hdEditMain ul li a").eq("0").click(function(){
            		blockUI(); 
                 });
            });
            function blockUI(){
            	$.blockUI({ css: { 
                    border: 'none', 
                    padding: '15px', 
                    backgroundColor: '#000', 
                    '-webkit-border-radius': '10px', 
                    '-moz-border-radius': '10px', 
                    opacity: .5, 
                    color: '#fff' 
                } });
            }
            function unblockUI(){
            	$.unblockUI(); 
            }     
            var menuId='<%=menuId%>';

      
</script>	   
 <div style="display:none" id="commentsHTML"></div>  
</body>
</html>