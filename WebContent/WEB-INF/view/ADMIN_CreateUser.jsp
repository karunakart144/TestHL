<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%
	String loginUser = (String) request.getSession().getAttribute(
			"userLoginId");
	User userObj = (User) request.getSession().getAttribute(loginUser);

	String mobileno = userObj.getMobile();
	String extnno = userObj.getExtension();
	String loginrole = userObj.getUserRole();
%>
<%
	String jsDirPath = (String) getServletContext().getInitParameter(
			"jsDirPath");
	String cssDirPath = (String) getServletContext().getInitParameter(
			"cssDirPath");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/jquery.bt.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
<%
	String token = null;
%>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.ui.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.bgiframe.min.js"></script>
<!--[if IE]><script src="<%=jsDirPath%>/excanvas.compiled.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.min.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/custom/ADMIN_CreateUser.js"></script>
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
<form:form modelAttribute="admincreateuserBean" id="admincreateuser"
	action="ADMIN_CreateUser.htm" name="admincreateuser" method="post"
	enctype="multipart/form-data">
	<br />
	<br />
	<table border="0" id="admincreateusertable" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable">
		<tr class='none'>
			<td colspan="2"><span class="containerBlock1" align="center">Add/Edit User Profile</span></td>
		</tr>
		<tr>
			<td class="label">Employee ID to Search<span class="red_text">*</span>:
			</td>
			<td><form:input path="employeeIdToSearch"
					id="employeeIdToSearch" max-length="25"
					onkeypress="validateEmpID(this.id,event);" class="myTextInput"/>&nbsp;&nbsp;<input type="button"
				id="searchButton" value="Search" onclick="getUserProfileInfo();"></input></td>
		</tr>
		<tr>
			<td class="label">Employee ID<span class="red_text">*</span>:
			</td>
			<td><form:input path="employeeId" id="employeeId"
					max-length="25" onkeypress="validateEmpID(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Employee Name<span class="red_text">*</span>:
			</td>
			<td><form:input path="employeeName" id="employeeName"
					max-length="15" onkeypress="validateEmpName(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Email Address :</td>
			<td><form:input path="emailAddress" id="emailAddress"
					max-length="100" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Reporting Manager<span class="red_text">*</span>:
			</td>
			<td><form:input path="reportingManager" id="reportingManager"
					max-length="25" onkeypress="validateEmpID(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Location<span class="red_text">*</span>:
			</td>
			<td><select id="locationId" name="locationId" class="myTextInput">
					<option selected="selected" value="0">--Please Select--</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">Time Zone<span class="red_text">*</span>:
			</td>
			<td><select id="timeZone" name="timeZone" class="myTextInput">
					<option selected="selected" value="0">--Please Select--</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">DU ID :</td>
			<td><form:input path="duId" id="duId" max-length="10"
					onkeypress="validateEmpID(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Mobile Number :</td>
			<td><form:input path="mobilenumber" id="mobilenumber"
					max-length="25" onkeypress="validatePhoneNumber(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Extension Number :</td>
			<td><form:input path="extNumber" id="extNumber" max-length="25"
					onkeypress="validatePhoneNumber(this.id,event);" class="myTextInput"/></td>
		</tr>
		<tr>
			<td class="label">Gender<span class="red_text">*</span>:
			</td>
			<td><select id="gender" name="gender" class="myTextInput">
					<option selected="selected" value="0">--Please Select--</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">Status<span class="red_text">*</span>:
			</td>
			<td><select id="status" name="status" class="myTextInput">
					<option selected="selected" value="0">--Please Select--</option>
			</select></td>
		</tr>
		<tr class="none">
			<td colspan="2" align="center"><input type="submit" id="submitUser" value="Submit"></input></td>
		</tr>
		
	</table>
	<form:input path="buttonType" id="buttonType" type="hidden"/>
	
	<%
		token = (String) request.getSession().getAttribute(
					"OWASP_CSRFTOKEN");
	%>
	<input type="hidden" name="OWASP_CSRFTOKEN" value="<%=token%>" />
</form:form>
<div id="output1" style="display: none"></div>
<script type="text/javascript">
	var LoginID = '
<%=(String) request.getSession().getAttribute("userLoginId")%>';
var mobilenumber = '<%=mobileno%>';
var extensionnumber = '<%=extnno%>';
var loginrole = '<%=loginrole%>';
</script>
</body>
</html>