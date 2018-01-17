<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />	
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>	
</head>
<body>
<% String token = null; %>
<br></br>
	<table border="0" cellspacing="0" width="80%" cellpadding="3"align = "center" class = "createTable">
		<tr>
			<td colspan="2" align="center"><span class="containerBlock1">Role Add/Remove</span></td>
		</tr>	
		<tr class="creationScreenAlternateTR">
			<td class="label">
				Employee Id :
			</td>
			<td align="left">
				<input type="text" maxlength="15" name="Role_Member" id="Role_Member" class="myTextInput" 
				onblur="ValidateEmpId()" onkeypress="checkEventEnterKey()" style="width:225px;height:16px"/>
				<span id="empResultMessage" class="invalid_text"></span>
				</td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td></td>
			<td align="left">
				<input type="button" value="View Role" id="showRole" onclick="getRoleMemDetails();ValidateEmpId()" /> 
				<input type="button" value="Add Role" id="addRole" onclick="addRoleToMem();ValidateEmpId()" />
			</td>
		</tr>
		<tr>
			<td> </td>
		</tr>
		<tr>
			<td> </td>
		</tr>
		<tr>
			<td colspan="2">
				<table  border="0" cellspacing="0" cellpadding="3" id="roleAddition" width="70%" align = "center" class = "myDataTable">
				
				</table>
			</td>
		</tr>
		<tr>
			<td> </td>
		</tr>
		<tr>
			<td><input type="hidden" id="statusForUpdation" value="Success"/> </td>
		</tr>
		<tr>
			<td colspan="2">
				<table  border="0" cellspacing="0" cellpadding="3" id="roleDetails" width="70%" align = "center" class = "myDataTable">
				
				</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>	
	<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_RoleManipulation.js"></script>
	<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>