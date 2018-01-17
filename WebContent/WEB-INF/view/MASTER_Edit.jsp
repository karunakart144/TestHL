
<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%
	String loginUser = (String) request.getSession().getAttribute(
			"userLoginId");
	User userObj = (User) request.getSession().getAttribute(
			loginUser);
	String id=(String) request.getAttribute("id");
	
	String mobileno = userObj.getMobile();
	String extnno = userObj.getExtension();
	String loginrole = userObj.getUserRole();
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<!--<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.bt.css" media="screen" />
--><link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/tab_style.css"/> 
	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/example.css"/> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/<%=cssDirPath%>/jmesa.css"/>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>

<style>

.tabnavi, .tabnavi ul{
width:auto !important;
}

</style>


</head>
<body>
<% String token = null; %>
<br>
<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td align="left" valign="bottom">
				<div id="hdEditMain" class="tabnavi">
					<ul> 
						<li><a href="MASTER_Edit.htm?id=<%=id%>" title="Details" class="selected">Details</a></li>
						<li><a href="#Attachment-Div" title="Attachments" onclick="getAttachmentsForMasterTicket(<%=id%>)">Attachments</a></li>
						<li><a href="#Linked-Tickets" title="Linked-Tickets" onclick="getLinkedTicketList(<%=id%>)">Linked-Tickets</a></li>						
						<li><a href="#Audit-Log" title="Audit-Log" onclick="getAuditList(<%=id%>,114)">Audit-Log</a></li> 
					</ul>
				</div>
			</td>
		</tr>
	</table><br>
<form:form modelAttribute="EditMasterTicketBean" id="editmasterticket"
	action="MASTER_Edit.htm" name="editmasterticket" method="post"
	enctype="multipart/form-data">
<table  width="80%" cellpadding="5" cellspacing="0" border="0" align="center" style="border: #C35617 2px solid;">
	<tr class = "sectionHeadingBlockForAssignment" >
		<td width="50%" align="left" valign="middle" height="25">Edit Master Ticket</td>
		<td width="50%" align="left" valign="middle">
			<div style="${editbuttonaccess}">
				<input type="button" id="Edit" value="Edit" onclick="LockTicket()"/> 					
				<input type="button" id="Update" value="Update"  onclick="UpdateIHDTicket()" />							
			</div>
		</td>
	</tr>

<tr>
<td colspan="2">
<table border="0" cellspacing="0" cellpadding="3" width="100%" align="center" >
<tr>
<td colspan="2">
<table  style="border: none" class="createTable" id="editTable" border="0" cellspacing="0" cellpadding="3"
		width="100%" align="center">
	<tr class="creationScreenAlternateTR"><td class="label">Master Ticket ID</td>
		<td><form:input path="MASTER_TICKET_ID" id="TICKET_ID" style="width:284px"/></td>
	</tr>	
<%-- 	<tr id="ifirstTicketId" style="display:none;"><td class="label">IFirst Incident ID</td>
		<td><form:input path="IFIRST_TICKET_ID" id="IFIRST_TICKET_ID" style="width:284px"/></td>
	</tr> --%>
	<tr>
		<td class="label">Requested By</td>
		<td><form:input path="CREATOR_NAME" id="CREATOR_NAME" style="width:284px"/></td>	
	</tr>
	<tr class="creationScreenAlternateTR">
		<td class="label">Function</td>
		<td><form:input path="FUNCTION_NAME" id="FUNCTION" style="width:284px"/><form:hidden path="FUNCTION" id="FUNCTION_ID" ></form:hidden></td>	
	</tr>	
	<tr>
		<td class="label">Category</td>
		<td><form:input path="CATEGORY_NAME" id="CATEGORY" style="width:284px"/></td>	
	</tr>
	<tr class="creationScreenAlternateTR">
		<td class="label">Sub Category</td>
		<td><form:input path="SUB_CATEGORY_NAME" id="SUB_CATEGORY" style="width:284px"/></td>	
	</tr>
	<tr>
		<td class="label">Subject</td>
		<td><form:input path="SUBJECT" id="SUBJECT" style="width:284px"/></td>	
	</tr>
	<tr class="creationScreenAlternateTR">
		<td class="label">Description</td>
		<td><form:textarea  rows="5" maxlength="500" path="DESCRIPTION" 
				id="DESCRIPTION" onmouseover="getDescVal(this.id)" style="height:70px !important;width:284px" /></td>	
	</tr>

	<tr>
		<td class="label">Impacted Location(s)</td>
		<td><form:input path="LOCATION_NAME" id="LOCATION_ID" style="width:284px" onmouseover="getDescVal(this.id)" /></td>	
	</tr>	
	<tr class="creationScreenAlternateTR">
		<td class="label">Contact Info</td>
		<td><form:input path="CONTACT_NO" id="CONTACT_NO" style="width:284px"/></td>	
	</tr>
	<tr>
		<td class="label">Workflow Status</td>
		<td><form:select path="WORKFLOW_STATE" id="WORKFLOW_STATE" class="myTextInputForEditPage">
				<form:options items="${WORKFLOW}" />
			</form:select></td>	
	</tr>
	
	<tr class="creationScreenAlternateTR">
		<td class="label">Assigned Group</td>
		<td><form:input path="ASSIGNED_GROUP_NAME" id="ASSIGNED_GROUP" style="width:284px"/></td>	
	</tr>
	<tr>
		<td class="label">Assigned To</td>
		<td><form:input path="ASSIGNED_TO_NAME" id="ASSIGNED_TO" style="width:284px"/></td>	
	</tr>
	<tr class="creationScreenAlternateTR">
		<td class="label">Expected Completion Time</td>
		<td><form:input path="ECT" id="ECT" style="width:284px"/></td>	
	</tr>
	<tr>
		<td class="label">Created By</td>
		<td><form:input path="CREATED_BY" id="CREATED_BY" style="width:284px"/></td>	
	</tr>
	<tr class="creationScreenAlternateTR">
		<td class="label">Created Date</td>
		<td><form:input path="CREATED_DATE" id="CREATED_DATE" style="width:284px"/></td>	
	</tr>
	<tr id="RESOLUTION_COMMENTS_TR">
		<td class="label">Resolution Comments</td>	
		<td><form:textarea  rows="5" cols="50" maxlength="1000" path="RESOLUTION_COMMENTS" 
				id="RESOLUTION_COMMENTS"   onmouseover="getDescVal(this.id)" style="height:70px !important;width:284px"/></td>	
	</tr>
	<tr id="CLOSED_DATE_TR" class="creationScreenAlternateTR">
		<td class="label">Closed Date</td>
		<td><form:input path="CLOSED_DATE" id="CLOSED_DATE" style="width:284px"/></td>	
	</tr>
	<tr id="VERSION_NO_TR">
			<td class="label">Version No</td>
			<td><form:input path="VERSION_NO" id="VERSION_NO" maxlength="5" style="width:284px"/></td>
	</tr>
</table>
</td>
</tr>
 <tr class="creationScreenAlternateTR">
       	<td align="center" id="UpdateButtonTd" style="${editbuttonaccess}"> 
       		<input type="button" id="Update" value="Update" onclick="UpdateIHDTicket()" />		
       	</td>
     </tr>
</table>
</td>
</tr>
</table>

</form:form>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
<div id="Linked-Tickets"  style="display:none" class="tabLiner">
<table width="100%" class="myDataTable" id="linkedTickets" cellpadding="0" cellspacing="0">
<tr>
	<th>Child Ticket ID</th>
	<th>Subject</th>
	<th>Description</th>
	<th>ECT</th>
</tr>
</table>
</div>
  	<div id="Audit-Log" style="display:none" class="tabLiner">
				<table width="100%" class="myDataTable" id="auditLog" cellpadding="0" cellspacing="0">
					<tr>
						<th width="10%">History ID</th>
						<th width="13%">Previous State</th>
						<th width="20%">Current State</th>
						<th width="20%">Changed By</th>
						<th width="25%">Created Date</th>
						<th width="22%">Action</th>						
					</tr>					
				</table>	
				<table width="100%" id="auditLogDetails" style="display:none">
					<tr><td width="15%" align = "left"><input type="button" value="Back to List" onclick="DisplayDivs('auditLog','auditLogDetails')"/></td></tr>
					<tr>						
						<th width="10%">Field</th>
						<th width="13%">Old Value</th>
						<th width="20%">New Value</th>										
					</tr>					
				</table>	
							 
            </div>
 <div id="Attachment-Div" style="display:none" class="tabLiner"><br>
            	<table border="0" id="attachment_field" class="myDataTable" cellspacing="5" cellpadding="5" width="80%">
					<tr>
						<th> Attachment Type </th>
						<th>Attachment Name</th>					
					</tr>		
				</table>
</div>
      <script src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/MASTER_Edit.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
	  <script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
      <script src="<%=jsDirPath%>/json.min.js"></script>
      <script src="<%=jsDirPath%>/custom/MASTER_AuditLog.js"></script>  
      <script src="<%=jsDirPath%>/custom/UNIVERSAL_Attachment.js"></script> 
      <script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.popupWindow.js"></script>  
      <!--[if IE]><script src="<%=jsDirPath%>/excanvas.compiled.js" type="text/javascript" charset="utf-8"></script><![endif]--> 
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.min.js"></script>
      <script type="text/javascript" src="<%=jsDirPath%>/jquery.blockUI.js"></script>
      
        
 
<script type="text/javascript">
	var logged_user_role = "<%=loginrole%>";
	var looged_user_id = "<%=loginUser%>";
	var viewmap = "${viewpermissionmap}";
		viewmap = viewmap.substring(1,viewmap.length-1);
	var viewmaparray = new Array();
		if(viewmap!=""){
			viewmaparray = viewmap.split(",")
		}	
	var editmap = "${editpermissionmap}";
		editmap = editmap.substring(1,editmap.length-1);
	var editmaparray = new Array();
		if(editmap!=""){
			editmaparray = editmap.split(",");
		}
	var nonemap = "${nonepermissionmap}";
		nonemap = nonemap.substring(1,nonemap.length-1);
	var nonemaparray = new Array();
		if(nonemap!=""){
			nonemaparray = nonemap.split(",");
		} 

		
	$('textarea[maxlength],input[maxlength]').live('keyup blur', function() {
        // Store the maxlength and value of the field.
        var maxlength = $(this).attr('maxlength');
        var val = $(this).val();

        // Trim the field if it has content over the maxlength.
        if (val.length > maxlength) {
            $(this).val(val.slice(0, maxlength));
        }
	});

	$('.blink').each(function() {
							    var elem = $(this);
							    setInterval(function() {
							        if (elem.css('visibility') == 'hidden') {
							            elem.css('visibility', 'visible');
							        } else {
							            elem.css('visibility', 'hidden');
							        }    
							    }, 500);
							}); 

	 function blockUI(){
     	$.blockUI({ css: { 
             border: 'none', 
             padding: '15px', 
             backgroundColor: '#000', 
             '-webkit-border-radius': '10px', 
             '-moz-border-radius': '10px', 
             opacity: .5, 
             color: '#fff' 
         } });
     }
     function unblockUI(){
     	$.unblockUI(); 
     }     
 
</script>
<div id="output1" style="display: none"></div>
<script type="text/javascript">
var LoginID = '<%=(String) request.getSession().getAttribute(
							"userLoginId")%>';
var mobilenumber = '<%=mobileno%>';
var extensionnumber = '<%=extnno%>';
var loginrole = '<%=loginrole%>';
</script>
</body>

</html>