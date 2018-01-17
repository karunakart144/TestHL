<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%@page import="com.igate.iconnect.BO.WORKFLOW_Role"%>
<%
	String loginUser = (String) request.getSession().getAttribute(
			"userLoginId");
	User userObj = (User) request.getSession().getAttribute(
			loginUser);
	String empName = userObj.getUserName();
	String mobileno = userObj.getMobile();
	String extnno = userObj.getExtension();
	String loginrole = userObj.getUserRole();
	String approvalExceptionFlag = userObj.getApprovalExceptionFlag();
	String subject="";
	List<WORKFLOW_Role> userRoleList=userObj.getUserRoleList();
	int roleListSize=userRoleList.size();
%> 
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery.ui.all.css">
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css">
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.bt.latest.css" media="screen" />

<!--[if gte IE 9]>
	<style>
	.ui-autocomplete
	{
	left: 261px !important;
    top: -44px !important;
    position:relative;
    }
    </style>
	<![endif]-->

<style>	
		.ui-autocomplete {
			position: absolute;
			top: 489px;
			left: 362px;
			cursor: default;
			height: 150px;
			overflow: auto;
			width: 219px !important;
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
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui-custom.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bgiframe.min.js"></script>
<!--[if IE]><script src="<%=jsDirPath%>/excanvas.compiled.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.latest.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/HELPDESK_Create.js"></script>



<form:form modelAttribute="CreateHelpdeskBean" id="createhelpdeskticket"
	action="HELPDESK_Create.htm" name="createhelpdeskticket" method="post"
	enctype="multipart/form-data">
	<br></br>
	<table border="0" id="ticketcreationtable" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable">
		<tr class='none'>
			<td colspan="2"><span class="containerBlock1">New Ticket
			Creation</span></td>
		</tr>
<!--Added by gk820877 on 5/19/2015 -->
		<c:choose>
  <c:when test="${empty sourceValue}">
  	<tr style="display: none" id="sourceTR" class="creationScreenAlternateTR">
			<td class="label">Source<span class="red_text">*</span></td>
			<td align="left"><form:select width="100%" class="myTextInput"
				id="source" path="source">
				<form:option value="Web">Web</form:option>
				<form:option value="Email">Email</form:option>
				<form:option value="Phone">Phone</form:option>
				<form:option value="Walk-In">Walk-In</form:option>
			</form:select></td>
		</tr>
  </c:when>
  <c:otherwise>
    <tr style="display: none" id="sourceTR">
			<td class="label">Source<span class="red_text">*</span></td>
			<td align="left"><form:select width="100%" class="myTextInput"
				id="source" path="source">
				<form:option value="${sourceValue}">${sourceValue}</form:option>
			</form:select></td>
		</tr>
  </c:otherwise>
</c:choose>
				<tr>
			<td class="label">Function<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="function" name="function"
				onChange="GetCategories(this,'category');loadSearchPageFunc()"
				title="Mandatory , Select the function that your problem or request is related.">
				<option selected="selected" value="0">--Please Select--</option>
				<c:forEach items="${type}" var="type">
					<option value="${type.CATEGORY_ID}">${type.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">Category<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="category"
				name="category" onChange="GetCategories(this,'subcategory');loadSearchPageCategry();"
				title="Category is the service, technology or application to which your problem may be related to. Select appropriate category.">
				<option selected="selected" value="0">--Please Select--</option>
				<c:forEach items="${category}" var="category">
					<option value="${category.CATEGORY_ID}">${category.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr id="SUB_CATEGORY_ID_TR">
			<td class="label">Sub Category<span class="red_text">*</span></td>
			<td align="left"><select
				class="myTextInput" id="subcategory" name="subcategory"
				onChange="ShowApprovalAttachment(this);loadSearchPageSubCategry();showPriority(this);"
				title="Select the sub category that defines your problem accurately.">
				<option selected="selected" value="0">--Please Select--</option>
				<c:forEach items="${subcategory}" var="subcategory">
					<option value="${subcategory.CATEGORY_ID}">${subcategory.NAME}</option>
				</c:forEach>
			</select>
			<table>
				<tr>
					<td id="formlinktoixchange"></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr id="departmentTR" style="display: none">
			<td class="label" id="reqDeptName">Requestor Department<span class="red_text">*</span></td>
			<td align="left" id="deptTD"><!--<select class="myTextInput" id="department"
				name="department" onChange=""
				title="Select the department that you are belongs to.">
			</select>-->	
			
			<form:input path="department" id="department" name="department" 
				class="myTextInput" /></td>			
		</tr>
		<tr id="priorityTR" class="creationScreenAlternateTR">
			<td class="label">Priority<span class="red_text">*</span></td>
			<td align="left">
				<select class="myTextInput" id="priority" onchange="showwarnmessage()" name="priority">
					<option selected="selected" value="0">--Please Select--</option>
				</select>
			</td>
		</tr>
		<tr id="subjectlistTR">
			<td class="label">Subject<span class="red_text">*</span></td>
			<td align="left"><span id="subjectselect" style="display: none">
			<select class="myTextInput" id="subjectlist" name="subjectlist"
				onChange="addSubject(this)"
				title="Mandatory, Write your problem statement (100 characters).">
			</select> </span> <span id="subjectinput"><form:input maxlength="100"
				path="subject" id="subject" class="myTextInput" onchange="loadSearchPage()"
				title="Mandatory, Write your problem statement (100 characters)." onblur="replaceMSWordCharacters(this)"/></span>
			</td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label" valign="top">Description<span class="red_text">*</span></td>
			<td><form:textarea rows="6" id="description" path="description"
				style="width:270px" maxlength="2000"
				title="Mandatory , Describe your problem in detail. Provide all details that will help the engineers resolve your problem quicker.
        Please mention your PC Host Name/Computer Name for faster resolution" onblur="replaceMSWordCharacters(this)"></form:textarea>
			</td>
		</tr>
		<tr id="attachmentSelect" style="${attachmentselect}">
			<td class="label"><form:label for="problemfile"
				path="problemfile">Attachment</form:label></td>
			<td align="left" id="problemattachTD"><form:input
				path="problemfile" id="problemfile"
				title="Attach supporting document related to your problem. (Screen shots, Image, Documents)"
				type="file" /> &nbsp; <a href="#" class="label"
				onclick="clearproblemattachment()">Clear Attachment</a></td>
		</tr>
		<tr id="attachmentDisplay" style="${attachmentdisplay}">
			<td class="label"><form:label for="problemfile"
				path="problemfile">Attachment</form:label></td>
			<td><form:hidden path="attachmentName" id="attachmentName" /> <form:hidden
				path="attachmentPath" id="attachmentPath" /> <a href="#"
				id="showAttachInfo"
				onclick="downloadAttachment('${CreateHelpdeskBean.attachmentPath}','${CreateHelpdeskBean.attachmentName}')">${CreateHelpdeskBean.attachmentName}</a>
			<input type="button" value="Remove" onclick="showAttachmentSelect()"></input>
			</td>
		</tr>
		<tr id="ApprovalAttachmentTR" style="display: none">
			<td class="label"><form:label for="approvalfile"
				path="approvalfile">Approver Attachment</form:label></td>
			<td align="left" id="approverattachTD"><form:input
				path="approvalfile" id="approvalfile"
				title="Attach email or other documents from your approving manager approving your request"
				type="file" /> &nbsp; <a href="#" class="label"
				onclick="clearapproveattachment()">Clear Approver Attachment</a></td>
		</tr>
		<tr style="display: none" id="isEXEmployeeCheckTR">
		<td class="label">Is Ex Employee </td>
		<td><input type="checkbox" size="80" path="isEXEmployeeCheck"
				id="isEXEmployeeCheck" name="isEXEmployeeCheck"
				onclick="" checked="checked"
				title="Uncheck the box if the requestor is not an active employee" /><span
				id="isEXEmployeerror"
				style="display: none; color: red; font-size: 12px; font-family: Trebuchet MS, Arial, Helvetica, 
				sans-serif; font-style: italic"></span></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">On Behalf of(EmployeeID)</td>
			<td align="left"><form:input class="myTextInput"
				id="onbeofEmpId" onBlur="checkempid()" maxlength="25"
				path="onbeofEmpId"
				title="If you are raising this ticket for someone else, provide his/her employee ID for validation" />
			&nbsp;<a id="validateEmployee"
				href="javascript:ValidateEmpId(document.getElementById('onbeofEmpId').value)"
				class="label"
				title="Confirm the existence of active Employee in Organization."><u>Validate</u>&nbsp;</a><span
				id="empResultMessage" class="invalid_text"></span></td>
		</tr>

		<tr>
			<td class="label" id="locationHeaderTd">Current Location<span class="red_text">*</span></td>
			<td><select id="locationId" name="locationId"
				class="myTextInput" onChange="GetBuildings()"
				title="Mandatory , Select your current work location">
				<option selected="selected" value="0">--Please Select function--</option>
			<!--
				<c:forEach items="${locations}" var="locations">
					<option value="${locations.LOCATION_ID}"><c:out
						value="${locations.CITY}" /></option>
				</c:forEach>
			-->
				</select>
			</td>
		</tr>
		<tr style="display: none" id="buildingTR">
			<td class="label">Building<span class="red_text">*</span></td>
			<td><select id="buildingId" name="buildingId"
				class="myTextInputForSelect" onChange="GetFloors();getODCS();"
				title="Mandatory , Select the building you are currently operating from">
				<option selected="selected" value="0">--Please Select--</option>
			</select></td>
		</tr>
		<tr style="display: none" id="floorTR">
			<td id="floorTD" class="label">Floor<span class="red_text">*&nbsp;</span>
			</td>
			<td id="floorselectTD"><select name="floor" id="floor"
				class="myTextInputForSelect" width="100%"
				onchange="GenerateCubicalCodes(this.options[this.selectedIndex].value)"
				title="Mandatory , Select the floor that you are currently operating from">
				<option selected="selected" value="">--Please Select--</option>
			</select></td>

		</tr>
		<!-- <tr style="display: none" id="cubicalTR">
			<td class="label" id="cubicalTD"><span id="cubicallabel">Cubicle
			Code</span><span class="red_text">*&nbsp;</span></td>
			<td><span id="cubicalselectTD" style="display: none"> <select
				name="cubicalselcode" id="cubicalselcode" class="myTextInput"
				onChange="$('#cubicalcode').val($(this).val())"
				title="Mandatory, Provide your cubicle/Workstation number.">
				<option selected="selected" value="0">--Please Select--</option>
			</select> </span> <span id="cubicalinputTD"><input type="text"
				id="cubicalcode" maxlength="70" title="Cubical Code"
				name="cubicalcode" /></span>&nbsp;&nbsp;<a id="cubicleCodeEdit" style="display:none;"
				href="javascript:cubicleCodeEdit()"
				class="label"><u>Edit</u>&nbsp;</a></td>
		</tr> -->
		<tr id="odcTR" style="display: none">
			<td class="label">ODC<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="odc"
				name="odc" 
				title="Select the ODC that you are belongs to.">
				<option value="">Please Select the Building</option>
			</select></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">Contact Number(Primary)<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="phoneoption"
				name="phoneoption" onchange="updatecontactnumber()">
				<option>Mobile</option>
				<option selected="selected">Extn</option>
			</select>
			</td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label"><span></span></td>
			<td>
			 <input type="text" class="myTextInput" maxlength="15"
				id="phonenumber" name="phonenumber"
				title="Mandatory, Either provide Cell No( Format:{Country Code}{Cell No} e.g. 919123456789) OR Office Extension No." /><span
				id="phoneerror"
				style="display: none; color: red; font-size: 12px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif; font-style: italic">*
			Mandatory</span></td>
		</tr>
		<tr>
			<td class="label">Alternate Contact Number</td>
			<td align="left"><input type="text" class="myTextInput"
				id="altcontactnumber" title="Alternate Contact Number"
				maxlength="15" name="altcontactnumber" /><span id="altphoneerror"
				style="display: none; color: red; font-size: 12px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif; font-style: italic">
			* Mandatory</span></td>
		</tr>
		<!-- L2:3186 the below field class has been changed to disply the full content of project -->
		<tr class="creationScreenAlternateTR">
			<td class="label">Project<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput"
				id="EmpProject" name="EmpProject"
				title="Mandatory, Allocated Project.">
				<c:choose>
				<c:when test="${projectsSize eq 0}">
				<option value="0">None</option>
				</c:when>
				<c:otherwise>
				<c:forEach items="${projects}" var="projects">
					<option value="${projects.PROJECT_VALUE}">${projects.DISPLAY_NAME}</option>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</select></td>
		</tr>
		<tr>
			<td class="label">CC to (EmailID)</td>
			<td align="left"><form:input type="text" class="myTextInput"
				id="CCEmailID" maxlength="500" name="CCEmailID" path="CCEmailID"
				title="Employees marked in CC(Use Comma(,) as seperator for multiple entries) will be notified on status change via an Email."></form:input><span
				id="erroremail" style="display: none">Enter a valid email
			address.</span></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">Multiple users/systems impacted</td>
			<td align="left"><input type="checkbox" size="80"
				id="impactusercheck" name="impactusercheck"
				onclick="showImpactedUserField(this,'impactUserCounttr')"
				title="If the problem you are raising impacts multiple users/systems, check this box."></td>
		</tr>

		<tr style="visibility: hidden" id="impactUserCounttr">
			<td class="label">Approx. number of users/systems affected by
			this incident<span class="red_text">*</span></td>
			<td align="left"><input type="text" name="impactusercount"
				maxlength="10" id="impactusercount" class="myTextInput"
				title="Provide the number of users/systems impacted by the problem you are raising. By default this count is taken as one.">
			<span id="errorimpactusers"
				style="display: none; color: red; font-size: 12px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif; font-style: italic">Please
			Enter the count of users.</span></td>
		</tr>
		<tr class="none" style="display: none">
			<td class="label">Created Date<span class="red_text">*</span></td>
			<td align="left"><form:input path="createddate" id="createddate"
				class="myTextInput" /> <form:input path="mailid" id="mailid"
				class="myTextInput" /><form:input path="mailTrackerType" id="mailTrackerType"
				class="myTextInput" /></td>
		</tr>
		<tr class="none" style="display: none">
			<td class="label">OsBrowser Details<span class="red_text">*</span></td>
			<td align="left"><form:input path="osdetails" id="osdetails"
				class="myTextInput" /> <form:input path="browserdetails" id="browserdetails"
				class="myTextInput" /><form:input path="projectname" id="projectname"
				class="myTextInput" /></td>
		</tr>
		<tr class="none" style="display: none">
			<td class="label">buildingID<span class="red_text">*</span></td>
			<td align="left"><form:input path="building" id="building"
				class="myTextInput" /></td> 
		</tr>
		<tr class="none" style="display: none">
		<td><form:input path="toAddressMailId" id="toAddressMailId" class="myTextInput"></form:input>
		
		<form:input path="isEXEmp_Value" id="isEXEmp_Value" class="myTextInput" value="0"></form:input>
		<form:input path="fromAddress" id="fromAddress" class="myTextInput"></form:input>
		</td>
		</tr>
		<tr class="none" style="display: none">
		<td><form:input path="function" id="function" class="myTextInput"></form:input>
		<form:input path="category" id="category" class="myTextInput"></form:input>
		<form:input path="subcategory" id="function" class="subcategory"></form:input>
		<form:input path="locationId" id="function" class="locationId"></form:input></td>
		</tr>

		<tr class="none" style="display: none">
		<td><form:input path="orchestrationInput1"
				id="orchestrationInput1" class="myTextInput"></form:input></td>
		</tr>
		<tr class="none" style="display: none">
		<td><input id="selectInput" class="myTextInput"></input></td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td colspan="2" align="center"><input type="submit"
				id="ticketsubmit" value="Submit"></input></td>
		</tr>
		<form:input path="approvalFlag" id="approvalFlag" type="hidden" class="approvalFlag"></form:input>
	</table>
	
</form:form>
<div id="output1" style="display: none"></div>

<script type="text/javascript">
var LoginID = '<%=(String) request.getSession().getAttribute(
							"userLoginId")%>';
var mobilenumber = '<%=mobileno%>';
var extensionnumber = '<%=extnno%>';
var loginrole = '<%=loginrole%>';
var isExEmpCheck=false;
var logged_user_role = "<%=loginrole%>";
var roleListSize="<%=roleListSize%>";
var username ="<%=empName%>";
var approvalFlag ="0";
var orchFlag = false;
var ticketID="";
$("#approvalFlag").val(approvalFlag);
//Added for Orchestration
var orchArray = new Array();
var automationFlag = false;

function loadSearchPage(){
	var subjectText=$("#subject").val();
	parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject="+subjectText+"&userName=" + username + "&empid=" + LoginID+ "&ticketID=" + ticketID;
}
/*Added by Mohit(816452) to filter the result on the basis of selection of Function/Category/Sub-Category */
function loadSearchPageFunc(){
	var functionText=$("#function option:selected").text();
	if(functionText != "--Please Select--"){
	parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject="+functionText+"&userName=" + username + "&empid=" + LoginID+ "&ticketID=" + ticketID;
	}
}

function loadSearchPageCategry(){
	var categoryText=$("#category option:selected").text();
	if(categoryText != "--Please Select--"){
	parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject="+categoryText+"&userName=" + username + "&empid=" + LoginID+ "&ticketID=" + ticketID;
	}
}

function loadSearchPageSubCategry(){
	var categoryText=$("#category option:selected").text();
	var subCategoryText=$("#subcategory option:selected").text();
	if(subCategoryText != "--Please Select--"){
	parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject="+categoryText+"_"+subCategoryText+"&userName=" + username + "&empid=" + LoginID+ "&ticketID=" + ticketID;
	}
}
/*Change of added by Mohit(816452) to filter the result on the basis of selection of Function/Category/Sub-Category */
</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>
