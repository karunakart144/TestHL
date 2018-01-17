<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%@ page import="com.igate.iconnect.util.SessionListener" %>
<%
String id=request.getParameter("id");
String loginUsers = (String)request.getSession().getAttribute("userLoginId");
User userOb = (User) request.getSession().getAttribute(loginUsers);

String empNames = userOb.getUserName();
String roles = (String)userOb.getUserRole();
String roleId=(String)userOb.getUserRoleId();
String subjects=request.getParameter("subject");
String categorys=request.getParameter("category");
String subcategorys=request.getParameter("subcategory");
String functions=request.getParameter("function");
String todoActions=request.getParameter("toDOAction");// To Know whether the user clicked on View or Edit.
SessionListener counter = (SessionListener) session.getAttribute("counter");
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.treeview.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/layout-default-latest.css" />
<link type="text/css" rel="stylesheet"  href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet"  href="<%=cssDirPath%>/jbar_style.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/user.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" media="screen" />
</head>
<body onunload="ticketUnlocking()">

<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> -->
<!-- <script type="text/javascript" src="<%=jsDirPath%>/block.js"></script> -->
<!-- <script type="text/javascript" src="<%=jsDirPath%>/ddaccordion.js"></script> -->
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.accordion.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/effects.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.tabs.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.treeview.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/demo.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/debug.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/requiredfunctions.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_WorkFlow.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Header.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bar.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.blockUI.js"></script>  

<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
var accOpts_searchWindow = {
		  autoHeight: false,
		  collapsible: true,
		  alwaysOpen: false,
		  active: false
		  
		};
var accOpts_serviceWindow = {
		  autoHeight: false,
		  collapsible: true,
		  alwaysOpen: false, 
		  active: false
		  
		};
var roleIde=<%=roleId%>; 	

function resizeIframes(obj) {
	obj.style.height = 1100+ 'px';
}



/*
  When ticket open in Edit mode from List page
 	And after updating the ticket again opening in Edit Mode .
 	To aviod such situation added the below Variable
 	Changed By : Nagamanikanta(714599)
 */
var isInEditModeOpen = true;


$('document').ready(function(){
	
    $.ajaxSetup({ cache: false });
	var parentmenuId =1;
	var parentMenuName="HelpDesk";
	if(roleIde==1){
		parentmenuId=113;
		parentMenuName="Create Master Ticket";
	}
	if(parentmenuId==1)
	{
		 $('#centeriframe').attr('src', 'HELPDESK_Create.htm');		
 		  myLayout.initContent('center'); 
	}
  	
  	var html_SearchWindow=getSearchHtml_SearchWindow();
  	var html_ServiceWindow=getSearchHtml_ServiceWindow();

  	/*******Added by Poovamma(716302)-Right Side Search***********/
	$('#myAccordion_searchWindow', window.parent.document).html(html_SearchWindow);	
	$("#lastframe").attr("src","UNIVERSAL_Searchh.htm");
 	$("#myAccordion_searchWindow").accordion(accOpts_searchWindow);		
	$('#myAccordion_serviceWindow', window.parent.document).html(html_ServiceWindow);	
 	$("#myAccordion_serviceWindow").accordion(accOpts_serviceWindow); 	
 	$("#myAccordion_searchWindow").filter(":has(.ui-state-default)").accordion("activate", 0); 	
 	$("#myAccordion_serviceWindow").filter(":has(.ui-state-active)").accordion("activate",false);
 	$("#myAccordion_serviceWindow").hide();
 	/*******End Added by Poovamma(716302)***********/
 	displaySubMenu(parentmenuId,parentMenuName);  
}); 
/*******Added by Poovamma(716302)-Right Side Search***********/
function getSearchHtml_SearchWindow(){
	var html="<h2 align='left' ><a href='#' class='accor_head_search'>Search Window</a></h2>";	
	html+="<div class='sub_tab_bg'><iframe name='lastframe' id='lastframe' width='100%'  scrolling='no' frameborder='0' onload='javascript:resizeIframes(this);'/></div>";
	return html;
}

function getSearchHtml_ServiceWindow(){
	var html="<h2 align='left' ><a href='#' class='accor_head_search'>Service Window Details</a></h2><div class='sub_tab_bg'><div id='serviceWindowDiv'></div><br><div id='firstDivId'></div><div id='secondDivId'></div></div>";
	return html;	
}

function pageY(elem) {
    return elem.offsetParent ? (elem.offsetTop + pageY(elem.offsetParent)) : elem.offsetTop;
}

var buffer = 10; //scroll bar buffer
function resizeIframe() {
    var height = window.innerHeight || document.body.clientHeight || document.documentElement.clientHeight;
    height -= pageY(document.getElementById('lastframe'))+ buffer ;
    height = (height < 0) ? 0 : height;
    document.getElementById('lastframe').style.height = height + 'px';
}

/*******End Added by Poovamma(716302)-Right Side Search***********/
 function displaySubMenu(parentmenuId,parentMenuName){	
	 //parent.$("#myAccordion_serviceWindow").hide();
	 parent.$("#myAccordion_serviceWindow").accordion("destroy");
	 $("#lastframe").attr("src","UNIVERSAL_Searchh.htm");
	 
	 parent.$("#myAccordion_searchWindow").filter(":has(.ui-state-default)").accordion("activate", 0); 	

	 parent.$('#myAccordion_serviceWindow', window.parent.document).html(getSearchHtml_ServiceWindow());
	 parent.$("#myAccordion_serviceWindow").hide();
	 var centerhref = "";
	 try{centerhref = centeriframe.location.href;}
	 catch (e){}
	 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
		 if(confirm("Do you want to continue with out updating the ticket?")){
			 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
			 releaseTicket(detailTicketId,1);
		 }else{
				return false;
			}
	  }
		
	  var getmailId="";
	 if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1){
			getmailId =  window.frames["centeriframe"].document.getElementById("mailid").value;
	  }
	
	  if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1 && getmailId != '' && window.frames["centeriframe"].document.getElementById("ticketsubmit").disabled == false){
			 if(confirm("Do you want to continue with out creating the ticket?")){
				 var mailId = window.frames["centeriframe"].document.getElementById("mailid").value;
				 releaseTicket(mailId,4);
			 }else{
					return false;
				}
		  }
 
	$('.topmenu a').removeClass('selected');
	$('#'+parentmenuId).addClass('selected');
		$.getJSON('menuList.htm', {
			parentmenuId : parentmenuId
		}, function(data) {
				
			if (data.length != 0) {
			var treeLevelHTML ="<input type = 'hidden' name = 'parentMenu' value = '"+parentMenuName+"'/><ul id='browser' class='filetree'>";
			var options = [];
				$.each(data, function(item) {
					if(data[item].childExsists=="false"){
					if(data[item].parentId==parentmenuId)
					treeLevelHTML+="<li id='"+data[item].menuId+"'><span class='folder'  id='"+data[item].menuId+"'  onclick=\"displayRight(this.id,'"+parentmenuId+"','"+data[item].menuName+"','"+parentMenuName+"')\">"+ data[item].menuName+"</span></li>";
					}
					else{
						var childMenuId=data[item].menuId;
						
						
						treeLevelHTML+="<li id='"+data[item].menuId+"'><span class='folder' id='"+data[item].menuId+"' onclick=\"displayRight(this.id,'"+parentmenuId+"','"+data[item].menuName+"','"+parentMenuName+"')\">"+ data[item].menuName+"</span><ul>";
						var options = [];
						$.each(data, function(item) {
							if(data[item].parentId==childMenuId){
							treeLevelHTML+="<li id='"+data[item].menuId+"'><span class='file' id='"+data[item].menuId+"' onclick=\"displayRight(this.id,'"+parentmenuId+"','"+data[item].menuName+"','"+parentMenuName+"')\">"+ data[item].menuName+"</span></li>";
							}
							});
					treeLevelHTML+="</ul></li>";
						}
					
				});
				treeLevelHTML+="</ul>";
				
				if(roleIde==6){
					treeLevelHTML+="<br></br><div id=\"onlineUsers\"><font color=\"#0000FF\">Number of online user(s):"+ <%=counter.getActiveSessionNumber() %>+"</font></div>";
					}
				 $('div.ui-layout-west').html(treeLevelHTML);
				 myLayout.initContent('west');
			 
				$("#browser").treeview({
					collapsed: true,
					animated: "medium",
					control:"#sidetreecontrol",
					persist: "location"
					/*animated:"normal",
					persist: "cookie"*/
				});
		$.getJSON('bulletinMessage.htm', {
		}, function(data) {
			if (data.length != 0) {
				var pausecontent2=new Array();

				treeLevelHTML+="<br></br><div id='stcikyDivId' class='board'>"
					+"<div id='bulletinBoard'><center><font color='white'>Bulletin Board</font></center></div><marquee behavior='scroll' direction='up' "+
					"scrollamount='3' onmouseover='this.stop();' onmouseout='this.start();'  style='width:100%;'>";
				var options = [];
				$.each(data, function(item) {
					treeLevelHTML+="<h4><u><center>"+data[item].BULLETIN_HEADER+"</center></u></h4><span><center>"+data[item].BULLETIN_DESCRIPTION+"</center><br></span>";
				});
			}
			
			treeLevelHTML+="</marquee></div>";

		 $('div.ui-layout-west').html(treeLevelHTML);
			 myLayout.initContent('west');
		 
			$("#browser").treeview({
				collapsed: true,
				animated: "medium",
				control:"#sidetreecontrol",
				persist: "location"
				/*animated:"normal",
				persist: "cookie"*/
			});
		});
		
	}else{
		$('div.ui-layout-west').html("");
	}
	});
	parent.myLayout.open('west');
  if(parentmenuId==5)
  {//L2: 2786
	  $('#centeriframe').attr('src', 'Reports.htm');
	  myLayout.close('west');
  }else if(parentmenuId==3)
	{
	  	CHILD_MENU_ID = 39;	// This variable is referring in commonlistpage.js for list page column selection  window ; Changed By : 714599
		$('#centeriframe').attr('src', 'UNIVERSAL_ListPage.htm?menuId=39&parentMenuId='+parentmenuId+'&menuName=New&parentMenuName=NOC-Alerts'); 
		myLayout.initContent('center');
	}
	else if(parentmenuId==4)
	{
		CHILD_MENU_ID = 41;	// This variable is referring in commonlistpage.js for list page column selection  window ; Changed By : 714599
		$('#centeriframe').attr('src', 'UNIVERSAL_ListPage.htm?menuId=41&parentMenuId='+parentmenuId+'&menuName=New&parentMenuName=Mail-Tracker'); 
		myLayout.initContent('center');
	}	
	else if(parentmenuId==1)
	{
		if(roleIde==31){
			$('#centeriframe').attr('src', 'MASTER_Create.htm');
			myLayout.close('west');
		}else{
		$('#centeriframe').attr('src', 'HELPDESK_Create.htm');
		} 
		myLayout.initContent('center');
	}
	else if(parentmenuId==6)
	{
		$('#centeriframe').attr('src', 'CONTACTUS_HelpDeskAndEscalation.htm'); 
		myLayout.initContent('center');
		myLayout.close('west');
	}
	else if(parentmenuId==32)
	{
		//$('#centeriframe').attr('src', 'ADMIN_DataResetCache.htm'); 
		$('#centeriframe').attr('src','ADMIN_EditGroup.htm');
		myLayout.initContent('center');
	}
	else if(parentmenuId==115)
	{
		$('#centeriframe').attr('src', 'BULLETINBOARD_Create.htm'); 
		myLayout.initContent('center');
	}else if(parentmenuId==114){
		$('#centeriframe').attr('src','UNIVERSAL_ListPage.htm?menuId=114&parentMenuId='+parentmenuId+'&menuName=View Master Ticket&parentMenuName=Helpdesk'); 
		myLayout.initContent('center');
		myLayout.close('west');
	}else if(parentmenuId==113){
		$('#centeriframe').attr('src', 'MASTER_Create.htm');
		myLayout.close('west');
	}	

}

function getWorkLoad(groupMember,memberName)
{
var memberworkLoadHTML = "<div class = 'MYDIV'><table class='myDataTable' width='100%'><tr><th>"+memberName+'('+groupMember+')'+"</th></tr></table><table class='myDataTable' width='100%'><tr><th colspan='4'>No. Of Tickets</th></tr><tr><th>CATEGORY</th><th>HIGH</th><th>MEDIUM</th><th>LOW</th></tr>";
var lowcount=0;
var mediumcount=0;
var highcount=0;
$.getJSON('groupMemberWorkLoad.htm', {
		groupMember : groupMember
	}, function(data) {
		if (data.length != 0) {
			var options = [];
			$.each(data, function(item) {
				lowcount = lowcount+data[item].low;
				mediumcount = mediumcount+data[item].medium;
				highcount = highcount+data[item].high;
				memberworkLoadHTML=memberworkLoadHTML+"<tr><td>"+data[item].CATEGORY+"</td><td>"+data[item].high+"</td><td>"+data[item].medium+"</td><td>"+data[item].low+"</td></tr>";
			});
			memberworkLoadHTML=memberworkLoadHTML+"<tr><td>TOTAL</td><td>"+highcount+"</td><td>"+mediumcount+"</td><td>"+lowcount+"</td><tr></table></div>";
			$('#secondDivId').html(memberworkLoadHTML); 
		}else{
			$('#secondDivId').html("<div class = 'MYDIV'><table class='myDataTable' width='100%'><tr><th>"+memberName+'('+groupMember+')'+"</th></tr><tr><th>No Tickets Assigned</th></tr></table></div>");
		}
	});
}
var CHILD_MENU_ID = 0;	// This variable is referring in commonlistpage.js for list page column selection  window ; Changed By : 714599
	function displayRight(menuId,parentMenuId,menuName,parentMenuName) {
		parent.$("#myAccordion_serviceWindow").hide();
		$("#lastframe").attr("src","UNIVERSAL_Searchh.htm");
		parent.$("#myAccordion_searchWindow").filter(":has(.ui-state-default)").accordion("activate", 0); 	
		isInEditModeOpen = true; // This is referring in HELPDESK_Edit.js to check whether to open the ticket in Edit mode or not when open in Edit from List page
	 	CHILD_MENU_ID = menuId;	// This variable is referring in commonlistpage.js for list page column selection window ; Changed By : 714599
		 var centerhref = "";
		 try{centerhref = centeriframe.location.href;}
		 catch (e){}
		 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
			 if(confirm("Do you want to continue with out updating the ticket?")){
				 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
				 releaseTicket(detailTicketId,1);
			 }else{
					return false;
				}
		  }

		 var getmailId="";
		 if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1){
				getmailId =  window.frames["centeriframe"].document.getElementById("mailid").value;
				}
		 if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1 && getmailId != ''  && window.frames["centeriframe"].document.getElementById("ticketsubmit").disabled == false){
			 if(confirm("Do you want to continue without creating the ticket?")){
				 var mailId = window.frames["centeriframe"].document.getElementById("mailid").value;
				 releaseTicket(mailId,4);
			 }else{
					return false;
				}
		  }
		 blockUI();
		if(menuId == '36' || menuId == '37' || menuId == '22')
		{
			$('#centeriframe').attr('src', 'construction.htm'); 
			myLayout.initContent('center');
		}else if(menuId == '7'){
			$('#centeriframe').attr('src', 'HELPDESK_Create.htm'); 
		}else if(menuId == '65'){		
			$('#centeriframe').attr('src', 'ADMIN_DataResetCache.htm'); 
			myLayout.initContent('center');
		}else if(menuId == '98'){				
			$('#centeriframe').attr('src', 'ADMIN_WorkflowResetCache.htm'); 
			myLayout.initContent('center');
		}else if((menuId=='101')||(menuId=='100')){
			$('#centeriframe').attr('src','ADMIN_ViewGroup.htm');
			myLayout.initContent('center');
		}else if(menuId=='102'){
			$('#centeriframe').attr('src','ADMIN_ChangeGroup.htm');
			myLayout.initContent('center');
		}else if(menuId=='103'){
			$('#centeriframe').attr('src','ADMIN_AddGroup.htm');
			myLayout.initContent('center');
		}else if((menuId=='104')){
			$('#centeriframe').attr('src','ADMIN_ViewGroup.htm');
			myLayout.initContent('center');
		}else if(menuId=='113'){			
			$('#centeriframe').attr('src','MASTER_Create.htm');
			myLayout.initContent('center');
		}else if((menuId=='116')){
			$('#centeriframe').attr('src','BULLETINBOARD_Create.htm');
			myLayout.initContent('center');
		}
		else if((menuId=='117')){
			$('#centeriframe').attr('src','BULLETINBOARD_Remove.htm');
			myLayout.initContent('center');
		}else if(menuId=='134'){
			$('#centeriframe').attr('src','ADMIN_CreateGroup.htm');
			myLayout.initContent('center');
		}else if(menuId=='135' || menuId=='133'){
			$('#centeriframe').attr('src','ADMIN_EditGroup.htm');
			myLayout.initContent('center');
		}else if(menuId=='136' || menuId=='137'){
			$('#centeriframe').attr('src', 'ADMINCONSOLE_AddLocation.htm');
			myLayout.close('west');
		}else if(menuId=='139' || menuId=='140' || menuId=='141'){
			$('#centeriframe').attr('src', 'ADMIN_CategoryDisplayConsole.htm');
			myLayout.close('west');
		}//Added for Role menu under settings
		else if(menuId == '143'){				
			$('#centeriframe').attr('src', 'ADMIN_RoleManipulation.htm'); 
			myLayout.initContent('center');
		}
		else if(menuId == '138'){
			$('#centeriframe').attr('src','ADMIN_ModifyLocation.htm');
		myLayout.initContent('center');
		}else if(menuId == '144'){				
			$('#centeriframe').attr('src', 'ADMIN_ExcelUpload.htm'); 
		myLayout.initContent('center');
		}else if(menuId == '145'){				
			$('#centeriframe').attr('src', 'ADMIN_ExcelUpload.htm'); 
			myLayout.initContent('center');
		}	
		else if(menuId == '146'){
			$('#centeriframe').attr('src','HELPDESK_EmployeeProfile.htm');
		myLayout.initContent('center');
		}else if(menuId == '197'){
				$('#centeriframe').attr('src','ADMIN_CreateUser.htm');
				myLayout.initContent('center');
				}else if(menuId == '198'){
					$('#centeriframe').attr('src','ADMIN_AddLHProject.htm');
					myLayout.initContent('center');
					}
		else{
			$('#centeriframe').attr('src', 'UNIVERSAL_ListPage.htm?menuId='+ menuId+'&parentMenuId='+parentMenuId+'&menuName='+menuName+'&parentMenuName='+parentMenuName);
			myLayout.initContent('center');			
		}

	}

	function jbarOnSuccess(message){
		// Special Handling for IE
		isIE = $.browser.msie && !$.support.opacity; // Detects IE6,7,8.  IE9 supports opacity.  Feature detection alone gave a false positive on at least one phone browser and on some development versions of Chrome, hence the user-agent test.
		isIE6OrIE9 = isIE && $.browser.version ==6 || $.browser.version ==9;
		if(isIE6OrIE9==true)
		{
			$("#jbarmessage").bar({
				color 			 : 'BLACK',
				background_color : '#C8DD9C',
				position		 : 'bottom',
				removebutton     : true,
				message			 : message,
				time			 : 12000
			});	
		}else
		{		
		$("#jbarmessage").bar({
			color 			 : 'BLACK',
			background_color : '#C8DD9C',
			position		 : 'bottom',
			removebutton     : true,
			message			 : message,
			time			 : 12000
		});
		}
	}
	function jbarOnFailure(message){
		// Special Handling for IE
		isIE = $.browser.msie && !$.support.opacity; // Detects IE6,7,8.  IE9 supports opacity.  Feature detection alone gave a false positive on at least one phone browser and on some development versions of Chrome, hence the user-agent test.
		isIE6OrIE9 = isIE && $.browser.version ==6 || $.browser.version ==9;
		if(isIE6OrIE9==true)
		{
			$("#jbarmessage").bar({
				color 			 : 'BLACK',
				background_color : 'RED',
				position		 : 'bottom',
				removebutton     : true	,
				message			 : message,
				time			 : 12000
			});
		}else
		{		
		$("#jbarmessage").bar({
			color 			 : 'BLACK',
			background_color : 'RED',
			position		 : 'bottom',
			removebutton     : true	,
			message			 : message,
			time			 : 12000
		});
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
	function ticketUnlocking(){
		
  		 var centerhref = "";
		 try{centerhref = centeriframe.location.href;}
		 catch (e){}
		 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode){
				 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
				 releaseTicket(detailTicketId,1);
		  } 
		 if(isChng==false)
		 {			
		  window.location.href='Logout.htm';
		 }
}

	function iConnectLogOut(){
	   	 var centerhref = "";
		 try{centerhref = centeriframe.location.href;}
		 catch (e){} 
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
	if(roleIde==6){
		var auto_refresh = setInterval(
	    		function ()
	    			{   					
	    			 			$('#onlineUsers').load('numberOfUsers.htm', function(data){
	        			 		$('#onlineUsers').html('<font color=\"#0000FF\">Number of online user(s):'+ data+'</font>')}).fadeIn("slow");
	    		}, 10000); // autorefresh the content of the div after
	    			  //every 10000 milliseconds(10sec)
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
<%@ include file="UNIVERSAL_Header.jsp"%>
<!-- End of Header Menu -->

<!-- West Panel -->
<%@ include file="UNIVERSAL_WestPanel.jsp"%>
<!-- End of West Panel -->

<!-- East Panel -->
<%@ include file="UNIVERSAL_EastPanel.jsp"%>
<!-- End of East Panel -->

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