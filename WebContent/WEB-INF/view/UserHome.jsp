<%
String modname=(String)request.getParameter("modname");
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
   String id=request.getParameter("id");
   String SSOFlag = "NA";
   int gradeLevel=0;
   String empIde = (String) request.getSession().getAttribute(
   "userLoginId");
   User userObject = (User) request.getSession().getAttribute(
           empIde);
   if(userObject.getWorkSpacePlanning_Grade_Level()!=null){
   	gradeLevel= Integer.parseInt(userObject.getWorkSpacePlanning_Grade_Level());
   }
 
   int gradeAccess=userObject.getWorkSpacePlanning_Access();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery-ui.css"  />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.treeview.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jbar_style.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/user.css" />
</head>
<body>

<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.tabs.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.treeview.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/demo.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/debug.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.session.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/requiredfunctions.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_WorkFlow.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Header.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bar.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.blockUI.js"></script>  
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
var ssoFlag ="<%=SSOFlag%>";
var gradeLevel=<%=gradeLevel%>;
var gradeAccess=<%=gradeAccess%>;
$('document').ready(function(){
	var moduleName=$.session("modname1");
	$("#UserConnect").show();
	$("#UserHome").hide();
	$("#"+moduleName).addClass('selected');
	$("#centeriframe").attr("src","${url}");

	var html_SearchWindow=getSearchHtml_SearchWindow();
	//Commented the below line for Unified Self Service
	//	$('#myAccordion_searchWindow', window.parent.document).html(html_SearchWindow);	
	$('#myAccordion_searchWindow').html(html_SearchWindow);	
	$("#lastframe").attr("src","UNIVERSAL_Searchh.htm");
	if(parseInt(gradeLevel,10)>=8 && parseInt(gradeAccess,10)>0){
		$("#WorkspacePlanning").attr('style','display:block');
		$("#WorkspacePlanning").show();
	}else{
		$("#WorkspacePlanning").attr('style','display:none');
	}
});
function invalidateSess(){
	  window.location.href='Logout.htm';
}
function getSearchHtml_SearchWindow(){
	
	var html="<div class='sub_tab_bg'><iframe name='lastframe' id='lastframe' width='100%'  scrolling='no' frameborder='0' onload='javascript:resizeIframes(this);'/></div>";
	return html;	
}
function resizeIframes(obj) {	
		obj.style.height = 1100+ 'px';
}
function loadPage(id){
	 var centerhref = centeriframe.location.href;
	 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
		 if(confirm("Do you want to continue with out updating the ticket?")){
			 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
			 releaseTicket(detailTicketId,1);
		 }else{
				return false;
			}
	  }

	//Updated parent.myLayout.close('east') to myLayout.close('east') for Unified Self Service
	var url = "";
	if(id=="RaiseNewTicket"){
		url = "HELPDESK_Create.htm";	
	}else if(id=="ExistingTickets"){
		
		url = "UNIVERSAL_ListPage.htm?menuId=38&parentMenuId=1&menuName=Existing Tickets&parentMenuName=HelpDesk";
		myLayout.close('east');
	}else if(id=="WaitingForApproval"){
		url = "UNIVERSAL_ListPage.htm?menuId=51&parentMenuId=1&menuName=Waiting For My Approval&parentMenuName=HelpDesk";
		myLayout.close('east');
			
	}else if(id=="ApprovedByMe"){
		url = "UNIVERSAL_ListPage.htm?menuId=94&parentMenuId=1&menuName=Approved by me&parentMenuName=HelpDesk";
		myLayout.close('east');
	}else if(id=="RejectedByMe"){
		url = "UNIVERSAL_ListPage.htm?menuId=95&parentMenuId=1&menuName=Rejected by me&parentMenuName=HelpDesk";
		myLayout.close('east');
	}else if(id=="ContactInfo"){
		url = "CONTACTUS_HelpDeskAndEscalation.htm";	
		myLayout.close('east');
	}else if(id=="ViewAssetDetail"){
		//ASSET TAB: Added to display the Asset tab for all Users(Added by 716302)
		url = "HELPDESK_AssetAllDetail.htm";	
		myLayout.close('east');
	}else if(id=="Home"){
		//parent.window.location.href="User.htm?";
		window.location.href="goSearchHome.htm";
		
	}else if(id=="CreateMasterTicket")  {
		url = "MASTER_Create.htm";	
		myLayout.close('east');
	}else if(id=="ViewMasterTicket")  {
		url = "UNIVERSAL_ListPage.htm?menuId=114&parentMenuId=114&menuName=View Master Ticket&parentMenuName=Helpdesk";	
		myLayout.close('east');
	}
	$('.topmenu a').removeClass('selected');
	$('#'+id).addClass('selected');
	 blockUI();
	$("#centeriframe").attr("src",url);
	
}

function jbarOnSuccess(message){
	$("#jbarmessage").bar({
		color 			 : 'BLACK',
		background_color : '#C8DD9C',
		position		 : 'bottom',
		removebutton     : true,
		message			 : message,
		time			 : 4000
	});
}
function jbarOnFailure(message){
	$("#jbarmessage").bar({
		color 			 : 'BLACK',
		background_color : 'RED',
		position		 : 'bottom',
		removebutton     : true	,
		message			 : message,
		time			 : 4000
	});
} 

function ticketUnlocking(){
	 var centerhref = centeriframe.location.href;
	 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
			 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
			 releaseTicket(detailTicketId,1);
	  }
}

function releaseTicket(ticketId,menuId){
	
	var jsonobj = '{"JSONARRAY":['+'{"TICKET_ID":"'+ticketId+'","MENU_ID":"'+menuId+'"}'+']}';
	
	$.getJSON('unlockTickets.htm', {json:jsonobj}, function(data) {
		if(data.status=="Success"){
		 	 window.frames["centeriframe"].inEditMode = false;
		}	
	});
} 

function iConnectLogOut(){
	 var centerhref = centeriframe.location.href;
	 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
		 if(confirm("Do you want to continue with out updating the ticket?")){
			 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
			 releaseTicket(detailTicketId,1);
		 }else{
				return false;
			}
	  }

	window.location.href='Logout.htm';
}
function blockUI(){
	$('.ui-layout-center').block({ css: {   
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
	$('.ui-layout-center').unblock();  
}   
</script>
<!-- Header Menu -->
<%@ include file="UserHeader.jsp"%>
<!-- End of Header Menu -->

<%@ include file="UNIVERSAL_EastPanel.jsp"%>


<!-- Center Panel -->
<%@ include file="UNIVERSAL_CenterPanel.jsp"%>
<!-- End of Center Panel -->

<!-- Footer -->
<%@ include file="UNIVERSAL_Footer.jsp"%>
<!-- End of Footer -->
<div class="content">
<input type="hidden" id="jbarmessage" name="jbarmessage"></input>
</div>
</body>
</html>