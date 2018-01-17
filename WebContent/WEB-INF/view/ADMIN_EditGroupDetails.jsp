<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
String groupId = request.getParameter("id");
String groupName = (String) request.getParameter("groupName");
String groupManager = (String) request.getParameter("groupManager");
String groupManagerName = (String) request.getParameter("groupManagerName");
String groupLocation =(String) request.getParameter("location");
String groupStatus =request.getParameter("status");
String functionId =request.getParameter("function");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />	
	<script type="text/javascript">	
/*  if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}  */
</script>
</head>
<body>
<% String token = null; %>
<br></br>
<table border="0" cellspacing="0" cellpadding="3" id="groupDetails" width="80%" align = "center" class = "createTable">
	<tr>
		<td colspan="4" align="center"><span class="containerBlock1">Edit Group Details
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="backToList" value="Back To Group Select" onclick="gotoListPage()"/></span>
		
		 
		
		</td>
	</tr>
	<tr>
		<td colspan="2" class="label">Group Name :</td>
		<td colspan="2" id="groupName"> <%=groupName %> </td>
	</tr>
	<tr class="creationScreenAlternateTR">
		<td colspan="2" class="label">Group Manager :</td>
		<td colspan="2"> <%=groupManagerName %> </td>
	</tr>
	<tr>
		<td colspan="2" class="label">Base Location :</td>
		<td colspan="2"> <%=groupLocation %> </td>
	</tr>
	<tr class="creationScreenAlternateTR">
		<td colspan="2" class="label">Group Status :</td>
		<td colspan="2"><%=groupStatus %> </td>
	</tr>
	<tr>
		<td colspan="2" class="label">Member Status :</td>
		<td align="left" colspan="2">
			<input type="radio" checked="checked" name="groupMember" id="groupMember" value="Active" onclick="viewGroupMembersForGroup()"/>Active
			<input type="radio"  name="groupMember" id="groupMember" value="InActive" onclick="viewGroupMembersForGroup()"/>Inactive
		</td>
	</tr>
	<%if(groupStatus.equalsIgnoreCase("Active")){ %>
	<tr class="creationScreenAlternateTR">
		<td colspan="2"></td>
		<td colspan="2" align="left">
			<input type="button" value="Add New Member" id="AddMember" onclick="addNewMember()"/>
			
		</td>
	</tr>
<%} %>
	<tr id="addNewMemberTR" style="display: none">
	
		<td colspan="4">
			<table id="groupMembers" align="center" border="0" cellpadding="2" width="70%" class = "myDataTable">
				<tr>
				<th colspan="3">Add Member</th>
				<th><input type="button" value="Save" id="save" onclick="saveNewMember()"/>
					<input type="button" value="Close" id="close" onclick="closeNewMember()"/>
				</th>
				</tr>
				<tr>
					<th>Employee Id<span class="red_text">* </span></th>
					<th>Role<span class="red_text">* </span></th>
					<th>Access Level<span class="red_text">* </span></th>
					<th>Assignment Required</th>
				</tr>
				<tr id="engineer1">
					<td>
						<input type="text" maxlength="10" name="Engineer1" id="Engineer1" style="border:1px solid #4598b5;" onblur="checkEngineer();"/>
					</td>
					<td>
						<select id="role1" name="role1" style="border:1px solid #4598b5;">
							
						</select>
					</td>
					<td>
						<select id="roleAccess1" name="roleAccess1" style="border:1px solid #4598b5;">
							
						</select>
					</td>
					<td id="assignmentTD">
						<input type="checkbox" name="assignment1" id="assignment1" checked="checked" style="border:1px solid #4598b5;"/>
					</td>
					</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="4">
		<%if(groupStatus.equalsIgnoreCase("Active")){ %>
			<table  border="0" cellspacing="0" cellpadding="3" id="activeGroupDetailsList" width="70%" align = "center" class = "myDataTable">
				
				<tr>
				<th align="left" style="display:none;" id="updateGroupRoleTR"><input type="button" id="updateGroupRole" value="Update" onClick="validateBeforeUpdateGroupRole()" /></th>
				<th colspan="4" align="center">
					Edit Member From Group
				</th></tr>
				<tr id="header">
					<th width="5">Edit</th>
					<th>Member</th>
					<th>Role</th>
					<th>Member Status</th>
				</tr>
				<c:set var="count" value="1" scope="page" />
				<c:forEach items="${type}" var="type">					
					<tr>
					    <td id="groupRoleId${count}" style="display:none">${type.ROLE_ID}</td>
					    <td id="memIdForRole${count}" style="display:none">${type.MEMBER_ID}</td>
						<td><input type="image" src="<%=request.getContextPath()%>/images/editnode.jpg" id=${count} name=${type.MEMBER_ID} onclick="removeMem(this);" /></td>
						<td>${type.member_name_id}</td>						
						<td id='groupRole${count}'>${type.ROLE_NAME}</td>
						<td id='groupRoleStatus${count}'>${type.Group_Member_Status}</td>
						<td id='groupRoleValidation${count}' style='display:none;border:1px solid #4598b5;'></td>
					</tr>
					<c:set var="count" value="${count + 1}" scope="page"/> 
				</c:forEach>
				
			</table>
		<%}else { %>
			<table  border="0" cellspacing="0" cellpadding="3" id="inactiveGroupDetailsList" width="70%" align = "center" class = "myDataTable">
				
				<tr>
				<th colspan="3" align="center">
					Active Members of Group
				</th></tr>
				<tr id="header">
					<th>Member</th>
					<th>Role</th>
					<th>Member Status</th>
				</tr>
				
				<c:forEach items="${type}" var="type">

					<tr id="groupDetailsTR">
						<td>${type.member_name_id}</td>
						<td>${type.ROLE_NAME}</td>
						<td>${type.Group_Member_Status}</td>
					</tr>

				</c:forEach>
				
			</table>				
		<%} %>
		</td>
	</tr>
</table>
	<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		var groupId=<%=groupId %>;
		var functionId=<%=functionId %>;
		var groupStatus='<%=groupStatus%>';
		var groupManager=<%=groupManager%>;
	</script>	
	<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_EditGroup.js"></script>
	<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>