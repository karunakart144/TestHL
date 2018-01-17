<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.bt.css" media="screen" />
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body>
<% String token = null; %>
<br></br>
<table border="0" id="ticketcreationtable" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable">
<tr><th colspan="4" style="background:#FFEBBF"><b><font size='2' >Employee Profile</font></b></th></tr>

<tr>
<td class="label" align="center" valign="top" value="">Employee ID:<span class="red_text">*</span></td>
<td align="left"><input type="text" id="idToSearch" name="Employee ID" maxlength="10" title= "Provide his/her Employee ID"></input></td>
</tr>

<tr align='left'>
<td class="labelAdmin"></td>
<td><input id="employeeSearch" type="button" onclick="getEmployeeDetails()" value="Search"></input></td>
</tr>
</table>


<table border="0" id="employeeProfile" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable" style="display:none">
<tr><th colspan="4" style="background:#FFEBBF"><b><font size='2' >Employee Details</font></b></th></tr>
<tr>
<td class="label" valign="top">Employee ID: </td>
<td align="left" id="employeeID"></td>
</tr>
<tr>
<td class="label" valign="top">Name: </td>
<td align="left" id="employeeName"></td>
</tr>
<tr>
<td class="label" valign="top">Email Address: </td>
<td align="left" id="employeeEmailAddress"></td>
</tr>
<tr>
<td class="label" valign="top">Grade:</td>
<td align="left" id="employeeGrade"></td>
</tr>
<tr>
<td class="label" valign="top">Designation: </td>
<td align="left" id="employeeDesignation"></td>
</tr>
<tr>
<td class="label" valign="top">Location: </td>
<td align="left" id="employeeLocation"></td>
</tr>
<tr>
<td class="label" valign="top">Reporting Manager: </td>
<td align="left" id="employeeRO"></td>
</tr>
<tr>
<td class="label" valign="top">Department: </td>
<td align="left" id="employeeDept"></td>
</tr>
<tr>
<td class="label" valign="top">Mobile Number: </td>
<td align="left" id="employeeMobileNo"></td>
</tr>
<td class="label" valign="top">Employee Status: </td>
<td align="left" id="employeeStatus"></td>
</tr>

</table>


<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/HELPDESK_EmployeeProfile.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>

