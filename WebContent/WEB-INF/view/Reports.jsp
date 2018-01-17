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
		
		button {
    background: url(images/gray_btn_bg.gif) repeat-x;
    overflow: visible;
    padding:5px;
    border: 1px solid #8a979d;
    cursor: pointer;
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-weight: bold;
    font-size: 11px;
    color: #000000;}
    
    button img{
    vertical-align: middle;
    }
    
    #ui-datepicker-div{display:none;}
    
    .radioLabel {
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 12px;
    color: #1A1A1A;
    font-weight: bold;
    padding: 6px 6px;
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
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.latest.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.ui.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/Reports.js"></script>

<form:form modelAttribute="GenerateReportBean" id="generateReport"
	action="Reports.htm" name="generateReport" method="post"
	enctype="multipart/form-data">
	<br></br>
	<table border="0" id="ticketcreationtable" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable">
		<tr class='none'>
			<td colspan="2"  align="center"><span class="containerBlock1">On Demand Report Generation</span></td>
		</tr>
		<tr>
			<td class="label">Function<span class="red_text">*</span></td>
			<td align="left"><select class="myTextInput" id="function" name="function"
				onChange="GetCategories(this,'category')"
				title="Mandatory , Select the function">
				<option selected="selected" value="">--Please Select--</option>
				<c:forEach items="${type}" var="type">
					<option value="${type.CATEGORY_ID}">${type.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td class="label">Category</td>
			<td align="left"><select class="myTextInput" id="category"
				name="category" title="Mandatory , Select the category">
				<option selected="selected" value="9999">--Select All--</option>
				<c:forEach items="${category}" var="category">
					<option value="${category.CATEGORY_ID}">${category.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td class="label">On Basis Of</td>
			<td align="left" class="radioLabel">
				<input type="radio" name="onBasisOf" id="onBasisOf" 
				value="0"  checked="checked" onclick="getCurrentStatusDropDown()"/>Current Status
				
				<input type="radio" name="onBasisOf" id="onBasisOf" 
				value="1" onclick="removeCurrentStatusDropDown()" />Created Date
				
				<input type="radio" name="onBasisOf" id="onBasisOf" 
				value="2" onclick="removeCurrentStatusDropDown()"/>Closed Date
				
				
			</td>
		</tr>
		<tr id="CurrentStatusTR">
			<td class="label">Current Status Of Ticket</td>
			<td align="left"><select class="myTextInput" id="status"
				name="status" title="Mandatory , Select the Status">
				<option selected="selected" value="0">--Select All--</option>
				<c:forEach items="${statusList}" var="statusList">
					<option value="${statusList.STATE_ID}">${statusList.NAME}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
				<td class="label">From<span class="red_text">*</span></td>
				<td align="left" title="Select the Start Date">
					<form:input type="text" readonly="true" id="startDate" class="myTextInput" 
					path="startDate" name="startDate" onKeyDown="return onKeyDown()"></form:input>
				</td>
				<td><input type="hidden" id="currentDate" value="${currentDate}"/></td>
			</tr>
		<tr>
			<td class="label">To<span class="red_text">*</span></td>
			<td align="left" title="Select the End Date">
				<form:input type="text" readonly="true" id="endDate" class="myTextInput" 
				path="endDate" name="endDate" onKeyDown="return onKeyDown()"></form:input>
			</td>
		</tr>
			
		<tr class="label">
			<td colspan="2" align="center"><button type="submit"
				id="reportSubmit"><img src="images/excel_logo.png"/> Download Report</button></td>
		</tr>
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
</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>
