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
	src="<%=jsDirPath%>/custom/ADMIN_AddLHProject.js"></script>
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
	<br />
	<br />
	<table border="0" id="LHProjecttable" cellspacing="0" cellpadding="3"
		width="80%" align="center" class="createTable">
		<tr>
			<td colspan="2" align="center"><span class="containerBlock1">Life and Health Projects</span></td>
		</tr>
		<tr>
			<td><input type="hidden" id="statusForUpdation" value="Success"/> </td>
		</tr>
		<tr>
		<td colspan="2">
				<table  border="0" cellspacing="0" cellpadding="3" id="projectDetails" width="70%" align = "center" class = "myDataTable">
				</table>
			</td>
	</tr>
	<tr>
		<td align="center"><input type="button" id="addProject" value="Add Project" onclick="displayAddProject();"></input> </td>
	</tr>
	</table>
	
	<br/>
	<div style="display:none" id="addLHProjectDiv">
		<table border="0" id="addLHProjecttable" cellspacing="0"
			cellpadding="3" width="80%" align="center" class="createTable">
		<tr>
			<td colspan="2" align="center"><span class="containerBlock1">Add Life and Health Project</span></td>
		</tr>
		<tr>
			<td class="label">Project ID<span class="red_text">*</span>:
			</td>
			<td><input name="projectId" id="projectId" max-length="25"
				onkeypress="validateProjID(this.id,event);" /></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
			<input type="button" id="updateButton" value="Update" onclick="addLHProjectId();"></input>
			</td>		
		</tr>
		</table>
		
	</div>

	<%
		token = (String) request.getSession().getAttribute(
					"OWASP_CSRFTOKEN");
	%>
	<input type="hidden" name="OWASP_CSRFTOKEN" value="<%=token%>" />
<div id="output1" style="display: none"></div>
<script type="text/javascript">
var LoginID = '<%=(String) request.getSession().getAttribute("userLoginId")%>';
var mobilenumber = '<%=mobileno%>
	';
	var extensionnumber = '
<%=extnno%>';
var loginrole = '<%=loginrole%>';
</script>
</body>
</html>