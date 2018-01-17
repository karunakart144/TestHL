<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%
	String loginUser = (String) request.getSession().getAttribute(
			"userLoginId");
	User userObj = (User) request.getSession().getAttribute(
			loginUser);

	String mobileno = userObj.getMobile();
	String extnno = userObj.getExtension();
	String loginrole = userObj.getUserRole();
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.bt.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
<!-- 	 <style>
		#ifirstMsg{
		 font-weight: bold;
		text-decoration:blink;
		padding:5px;
		display:block;
		border:1px solid #00a1e4;
		background: #eee;
		}
		
	</style>  -->
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body>
<% String token = null; %>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.ui.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/MASTER_Create.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bgiframe.min.js"></script>
<!--[if IE]><script src="<%=jsDirPath%>/excanvas.compiled.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<form:form modelAttribute="CreateMasterTicketBean" id="createmasterticket"
	action="MASTER_Create.htm" name="createmasterticket" method="post"
	enctype="multipart/form-data">
	<br></br>
	<table border="0" id="ticketcreationtable" cellspacing="0"	cellpadding="3" width="80%" align="center" class="createTable">
				<tr class='none'>
					<td colspan="2"><span class="containerBlock1">Master Ticket Creation</span></td>
					
		</tr>
		<!-- <tr id="ifirstMsgTR" style="display:none"><td colspan="2" align="center"><span  id="ifirstMsg" class="blinkClass" >For below selected function IFirst incident will be created.</span></td></tr> -->
		<tr>
			<td class="label">Function<span class="red_text">*</span></td>
			<td align="left"><select id="FUNCTION" name="FUNCTION" class="myTextInput"
				onChange="GetCategories(this,'CATEGORY')"
				title="Mandatory , Select the function that your problem or request is related to. Select IT Infrastructure Management for IT infrastructure related issues and Information Systems for application related issues">
				<option selected="selected" value="0">--Please Select--</option>
				<c:forEach items="${type}" var="type">
					<option value="${type.CATEGORY_ID}">${type.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">Category<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="CATEGORY"
				name="CATEGORY" onChange="GetCategories(this,'SUB_CATEGORY')"
				title="Category is the service, technology or application to which your problem may be related to. Select appropriate category.">
			</select></td>
		</tr>
		<tr>
			<td class="label">Sub Category<span class="red_text">*</span></td>
			<td align="left"><select
				class="myTextInput" id="SUB_CATEGORY" name="SUB_CATEGORY"
				onChange="ShowApprovalAttachment(this)"
				title="Select the sub category that defines your problem accurately.">
			</select>
			<table>
				<tr>
					<td id="formlinktoixchange"></td>
				</tr>
			</table>
			</td>
		</tr>
			<tr class="creationScreenAlternateTR">
			<td class="label" id="locationHeaderTd">Impacted Location(s)<span class="red_text">*</span></td>
			<td><select id="LOCATION_ID" name="LOCATION_ID"
				class="myTextInputForSelect" style="width:270px"
				title="Mandatory , Choose the impacted location(s), If for All locations ,then select the ALL Checkbox" multiple="multiple">
				<option selected="selected" value="0">--Please Select function--</option>
				</select>
				<input type="checkbox" id="ALL_LOCATIONS" name="ALL_LOCATIONS" onclick="isAllLocationChecked()" >All</input>
			</td>
			
		</tr>
		<tr id="subjectlistTR">
			<td class="label">Subject<span class="red_text">*</span></td>
			<td align="left"><span id="subjectinput"><form:input maxlength="100"
				path="SUBJECT" id="SUBJECT" class="myTextInput"
				title="Mandatory, Write your problem statement (100 characters)." /></span>
			</td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label" valign="top">Description<span class="red_text">*</span></td>
			<td><form:textarea rows="6" id="DESCRIPTION" path="DESCRIPTION"
				style="width:270px" maxlength="500"
				title="Mandatory , Describe your problem in detail. Provide all details that will help the engineers resolve your problem quicker.
        			   Please mention your PC Host Name/Computer Name for faster resolution"></form:textarea>
				</td>
		</tr>	
	
		<tr id="ASSIGNED_TO_TR">
			<td class="label">Assigned To(Employee ID)<span class="red_text">*</span></td>
			<td><!--<form:select path="ASSIGNED_TO" id="ASSIGNED_TO" title="Mandatory,Select to whom the master ticket has to be assigned-Search by employee name or employee id">
				<form:option value="" label="--Please Select--" />
				<c:forEach items="${ASSIGNEDTO}" var="ASSIGNEDTO">
					<form:option value="${ASSIGNEDTO.member_id}">${ASSIGNEDTO.member_name_id}</form:option>
				</c:forEach>
			</form:select>-->
			 <form:input type="text" class="myTextInput"
				id="ASSIGNED_TO" maxlength="90" name="ASSIGNED_TO" path="ASSIGNED_TO"
				title="Mandatory,Select to whom the master ticket has to be assigned-Search by employee id"></form:input><span
					id="empResultMessage" class="invalid_text">
				</span>					
				<a href="#" onclick="getGroups()"/><img src="images/search.png" alt="Click to populate Assigned Group" id="seachImg" title="Click to populate Assigned Group"/>
				<span class="invalid_text" id="searchMsg" >(Pls click on search button to populate the Assigned Group)
				</span>			
			</td> 
		</tr>	
			<tr id="ASSIGNED_GROUP_TR" class="creationScreenAlternateTR">
			<td class="label">Assigned Group<span class="red_text">*</span></td>
			<td><form:select path="ASSIGNED_GROUP_ID" id="ASSIGNED_GROUP_ID" class="myTextInput"  title="Mandatory,Select the group to which this master ticket can be assigned">
				<form:option value="" label="--Please Select--" />		
			</form:select><!--<form:input type="text" class="myTextInput" id="ASSIGNED_GROUP" path="ASSIGNED_GROUP" title="Mandatory,Select the group to which Master Ticket has to be assigned"></form:input>
			<a href="#" onclick="clearField()"><img src="images/clear.png" alt="Clear" title="Clear"/>
			--></td>
		</tr>
		<tr>
		<td class="label">Expected Completion Time<span class="red_text">*</span></td>
			<td><form:input id="ECT" class="myTextInput" readonly="true" path="ECT" title="Mandatory,Choose the expected completion time" onKeyDown="return onKeyDown()"></form:input></td>
		</tr>	
		<tr id="attachmentSelect" style="${attachmentselect}" class="creationScreenAlternateTR">
			<td class="label"><form:label for="problemfile"
				path="problemfile">Attachment</form:label></td>
			<td align="left" id="problemattachTD"><form:input
				path="problemfile" id="problemfile"
				title="Attach supporting document related to your problem. (Screen shots, Image, Documents)"
				type="file" /> &nbsp; <a href="#" class="label"
				onclick="clearproblemattachment()">Clear Attachment</a></td>
		</tr>
		<tr>
			<td class="label">Contact Information<span class="red_text">*</span></td>
			<td align="left"><form:input  class="myTextInput" maxlength="15"
				id="phonenumber"  path="phonenumber"
				title="Mandatory, Either provide Cell No( Format:{Country Code}{Cell No} e.g. 919123456789) OR Office Extension No." /><span
				id="phoneerror"
				style="display: none; color: red; font-size: 12px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif; font-style: italic">*
			Mandatory</span></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">CC to (EmailID)</td>
			<td align="left"><input type="text" class="myTextInput"
				id="CCEmailID" maxlength="90" name="CCEmailID"
				title="Employees marked in CC(Use Comma(,) as seperator for multiple entries) will be notified on status change via an Email."><span
				id="erroremail" style="display: none">Enter a valid email
			address.</span></td>
		</tr>
		<tr class="none" style="display: none">
			<td class="label">Created Date<span class="red_text">*</span></td>
			<td align="left"><form:input path="CREATED_DATE" id="CREATED_DATE"
				class="myTextInput" /></td>
		</tr>
		<tr class="none" style="display: none">
			<td class="label">OsBrowser Details<span class="red_text">*</span></td>
			<td align="left"><form:input path="OS_DETAILS" id="osdetails"
				class="myTextInput" /> <form:input path="BROWSER_DETAILS" id="browserdetails"
				class="myTextInput" /></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td colspan="2" align="center"><input type="submit"
				id="ticketsubmit" value="Submit"></td>
		</tr>
		<tr><td><form:hidden path="FUNCTION_NAME" id="FUNCTION_NAME" /></td></tr>
	</table>
	<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</form:form>
<div id="output1" style="display: none"></div>
<script type="text/javascript">
var LoginID = '<%=(String) request.getSession().getAttribute(
							"userLoginId")%>';
var mobilenumber = '<%=mobileno%>';
var extensionnumber = '<%=extnno%>';
var loginrole = '<%=loginrole%>';


</script>
</body>
</html>