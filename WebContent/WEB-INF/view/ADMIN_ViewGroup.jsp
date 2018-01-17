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
	<table border="0" cellspacing="0" cellpadding="3" id="grouprowcss" width="100%" align = "center" class = "createTable">
		<tr>
			<td colspan="2" align="center"><span class="containerBlock1">View Group</span></td>
		</tr>	
		<tr>
			<td class="label">
				<span class="red_text">* </span>Function
			</td>
			<td align="left" >
			<select  id="functionToAdd" name="function">
				<option selected="selected" value="0">--Please Select--</option>
					<c:forEach items="${type}" var="type">
								<option value="${type.CATEGORY_ID}" >${type.NAME}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">
				<span class="red_text">* </span>Group
			</td>			
			 <td align="left">
			 	<select class="myTextInputForSelect" id="GROUP_ID" name="GROUP_ID">
					<option selected="selected" value="--Please Select--" label="--Please Select--">
					</option>																					
				</select>						
				<input type="radio"  name="groupMember" id="active" value="Active" onClick="groupMemStatus()"/>Active
				<input type="radio"  name="groupMember" id="Inactive" value="InActive" onClick="groupMemStatus()" />In Active
				<input type="radio"  name="groupMember" id="all" value="All" onClick="groupMemStatus()" />All
			</td>			
		</tr>
		<tr>			
			<td class="label"><input type="button" id="viewGroupMember" value="View"/></td>			
		</tr>	
		<tr><td colspan="8">
			<table  border="0" cellspacing="0" cellpadding="3" id="groupMemrowcss" width="70%" align = "center" class = "myDataTable">
			</table>
		</td></tr>
	</table>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>	
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_ViewGroup.js"></script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>