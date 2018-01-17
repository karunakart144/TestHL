<%@ page import="com.igate.iconnect.BO.COMMON_Menu" %>
<%@ page import="com.igate.iconnect.BO.WORKFLOW_Role" %>
<%@ page import="com.igate.iconnect.BO.User" %>
<%@ page import="java.util.List" %>
<div class="ui-layout-north" style="padding: 0px;">
<div class="header">
<div style="text-align: right;">
<%  String empId = (String) request.getSession().getAttribute(
"userLoginId");
User userObj = (User) request.getSession().getAttribute(
        empId);
String empName = userObj.getUserName();
String currentUserRole = userObj.getUserRole();
List<WORKFLOW_Role> userRoleList = userObj.getUserRoleList();


long currentUserRoleID = 0;

%> 
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
		
  $(document).ready(function(){
	if(parseInt(gradeLevel,10)>=8 && parseInt(gradeAccess,10)>0){
		$("#WorkspacePlanning").attr('style','display:block');
		$("#WorkspacePlanning").show();
	}
  });
	
</script>

<div id="container">
	
	<div id="topnav" class="topnav">
	<div style="text-align:right;"> 
        Welcome <strong><%=empName %></strong><a href="login" class="signin"><img src="images/profile.png" alt="My Profile" title="My Profile"/></a><a href="#" onclick="iConnectLogOut();"><img src="images/logout.png" alt="Logout" title="Logout"/></a><img src="images/Capgemini_Header_Logo.png"/>
                	<br/>	            	
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

<span class="title"><img src="images/iConnect_Logo.png" title="Logo"/></span><br />
</div>
<div class="topmenu" id="UserHome"> 
<a href="User.htm?" id="HomeScreen" class = "selected">Home</a>&nbsp;
</div>
<div class="topmenu" id="UserConnect" style="display:none;"> 
<a href="#" id="Home" onclick="loadPage(this.id)"  title="Self Help">Home&nbsp;</a>
 <a href="#" id="RaiseNewTicket" onclick="loadPage(this.id)"  title="Raise New Ticket">New</a>
 <a href="#" id="ExistingTickets" onclick="loadPage(this.id)"  title="View Existing Ticket">View</a>
 <a href="#" id="WaitingForApproval" onclick="loadPage(this.id)"  title="Waiting For My Approval">Waiting for Approval</a>
 <a href="#" id="ApprovedByMe" onclick="loadPage(this.id)"  title="Approved By Me">Approved By Me</a>
 <a href="#" id="RejectedByMe" onclick="loadPage(this.id)"  title="Rejected By Me">Rejected By Me</a>
<!--  <a href="#" id="CreateServiceTicket" onclick="loadPage(this.id)" title="Create Service Ticket">Create Service Ticket</a> 
 <a href="#" id="ViewServiceTicket" onclick="loadPage(this.id)" title="View Service Ticket">View Service Ticket</a>  -->
 <!-- <a href="#" id="ViewAssetDetail" onclick="loadPage(this.id)"  title="View-Asset">View-Asset</a> -->
 <a href="#" id="ContactInfo" onclick="loadPage(this.id)" title="Esclations & Contact Info">Contact Info</a> 
<!--  <a href="#" id="WorkspacePlanning" onclick="loadPage(this.id)" title="Workspace Planning" style="display:none" >Workspace Planning</a> -->
  
</div>
</div>
