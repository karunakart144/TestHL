<%@ page import="com.igate.iconnect.BO.COMMON_Menu" %>
<%@ page import="com.igate.iconnect.BO.WORKFLOW_Role" %>
<%@ page import="com.igate.iconnect.BO.User" %>
<%@ page import="java.util.List" %>
<%
String cssPath = (String)getServletContext().getInitParameter("cssDirPath");

String jsPath = (String)getServletContext().getInitParameter("jsDirPath");
 
String empId = (String) request.getSession().getAttribute(
"userLoginId");
User userObj = (User) request.getSession().getAttribute(
        empId);
String empName = userObj.getUserName();
String currentUserRole = userObj.getUserRole();
long currentUserRoleID = 0;
List<WORKFLOW_Role> userRoleList = userObj.getUserRoleList();
%>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/gamification.css" />
<style>
#cboxTitle{display:none !important;}
</style>
<% String token = null; %>
<div class="ui-layout-north" style="padding: 0px;height:100px!important;">
<div class="header">
<div style="float:left;" class="title"><img src="images/iConnect_Logo.png" title="Logo"/></div>


<div> 
<div id="container">
	
	<div id="topnav" class="topnav">
	
       
	<div style="text-align:right;"> 
      Welcome <strong><%=empName %></strong> <a href="login" class="signin"><img src="images/profile.png" alt="My Profile" title="My Profile"/></a> <a href="#" onclick="iConnectLogOut();"><img src="images/logout.png" alt="Logout" title="Logout"/></a>
      <img src="images/Capgemini_Header_Logo.png"/>  
        	
   	</div> 
	</div>
	<fieldset id="signin_menu">
	<form id = "changeRole" action="/iconnect/RoleChanged.htm" method="POST">
	<select name = "userRoleId" id = "userRoleId" onchange="isChange()">
	<%
		for(int i = 0; i < userRoleList.size(); i++){
			WORKFLOW_Role role = userRoleList.get(i);
			if(currentUserRole.equalsIgnoreCase(role.getRoleName())){
				currentUserRoleID = role.getRoleId();
		%>
		<option value="<%= role.getRoleId()%>" selected = "selected"><%= role.getRoleName()%></option>
		<%
			}else{
		%>
		<option value="<%= role.getRoleId()%>"><%= role.getRoleName()%></option>
		<%
			}
	}
		
		%>
      
	  </select>
	  </form>
      
  </fieldset>
	
</div>

</div>


</div>
<div class="topmenu">
<%
String headerName = "";
String headerId="";
String headerClass="";
 List<COMMON_Menu> parentMenuList=(List<COMMON_Menu>) request.getSession().getAttribute("parentMenu");
for(int i = 0; i < parentMenuList.size(); i++){

	COMMON_Menu parentMenuObj = parentMenuList.get(i);
    headerName = parentMenuObj.getMenuName();
    headerId=parentMenuObj.getMenuId().toString();
    if(headerName.contains("Contact"))
    {
    	headerClass="Escalations-Contact";
    }else
    {
    	headerClass=headerName;
    }
%>
<a href="#"  onclick="displaySubMenu(this.id,'<%=headerName%>')" id="<%=headerId %>" ><%=headerName%></a>
<%
}
 %>
 </div>
<script type="text/javascript" src="<%=jsPath%>/custom/UNIVERSAL_Security.js"></script>
</div>
<script type="text/javascript">
var loggedinroleID = "<%=currentUserRoleID%>";
var EMPLOYEE_ID = "<%=empId%>";
var ROLE_ID = "<%=currentUserRoleID%>";
var isChng=false;
function isChange()
{
	isChng=true;
}

</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />