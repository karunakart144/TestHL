<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/view/UNIVERSAL_Include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.igate.iconnect.BO.User"%>
<%
	String menuId=request.getParameter("menuId");
	String parentMenuId=request.getParameter("parentMenuId");
	String menuName=request.getParameter("menuName");
	String parentMenuName = request.getParameter("parentMenuName");
	String id=request.getParameter("id");
	String loginUser = (String)request.getSession().getAttribute("userLoginId");
	User userObj = (User) request.getSession().getAttribute(loginUser);
	String role = (String)userObj.getUserRole();
	
	session.setAttribute("menuId", menuId);
	session.setAttribute("parentMenuId",parentMenuId);
	session.setAttribute("menuName",menuName);
	session.setAttribute("parentMenuName",parentMenuName);
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jmesa.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/example.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/custom_screen.css" media="screen, projection"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/print.css" media="print"/>
<!--[if lt IE 8]><link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/ie.css" media="screen, projection"/><![endif]-->
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/fancy-type/screen.css" media="screen, projection"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" media = "screen"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/common.css" /> 
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" /> 
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.multiselect.css" />

<title>IConnect : Unified Service Desk for Life & Health</title>
<script type="text/javascript">
/* if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1) != window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
} */
</script>
<style type="text/css">
  .index_datepicker { 
  	z-index:10000;
  }
  
.myDataTable
{
    border-left:#a0a0a0 1px solid;
    background-color:#FFFFFF;    /* TABLE CONTENT BACKGROUND COLOR */
}

/*To use the alternate row style, add class=" alternaterow"*/
.myDataTable tr.alternaterow
{
    background-color:#f2fbfd;    /* TABLE ALTERNATE ROW COLOR */
}


.myDataTable th
{
	background:#a9e8fc url("../images/blueTableHead.gif") bottom repeat-x;
	padding:5px;/* 0px 3px 0px;*/
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:11px;
	color:#000000;  /* HEADER FONT COLOR */
 	border-right:#a0a0a0 1px solid;
 	border-top:#a0a0a0 1px solid;			
}

.createTable{
	color:#000000;
	border:#a0a0a0 1px solid;
 	
}
.myDataTable td
{
 	border-right:#a0a0a0 1px solid;
 	border-bottom:#a0a0a0 1px solid;
 	padding: 3px;
 	font-family:Verdana, Arial, Helvetica, sans-serif;
 	font-size:11px;
 	color:#000000;    /* TABLE CONTENT FONT COLOR */
}

.myDataTable td.borderlessTextInput
{
	border: 0px;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:11px;
	color:#1A1A1A;
}

.myDataTable a:link
{
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:10px;
	color:#3057a0;  /* LINKS FONT COLOR */
	text-decoration:underline;	
}

.myDataTable a:hover
{
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:10px;
	color:#000000;  /* LINKS HOVER FONT COLOR */
	text-decoration:none;		
}

.myDataTable a:visited
{
	color:#3057a0;  /* VISITED LINKS HOVER FONT COLOR --> SAME AS NORMAL LINKS */
}

.ticketsTable, .ticketsTable tr, .ticketsTable td
{
border: 1px solid black;
 text-align: center;
 margin:5px;
} 
  .red_text
{
	color:#FF0000;
	text-decoration:none;
}
#ticketsTable textarea,#ticketsTable input,#ticketsTable select{
margin:5px;
}
#ticketsTable textarea
{
height:41px;
width:242px;
 
}
  .blink
  {
  border:2px solid #ff0000;
  }
 </style>
</head>
<body>
<% String token = null; %>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_ListPage.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.ui.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.multiselect.js"></script>  
<form name="listForm" action="UNIVERSAL_ListPage.htm">
<div id="searchResult"><span class="screenHeading"></span> 
<input type="hidden" name="type" value="simplest"/>
<input type="hidden" value="<%=menuId %>" name="menuId"></input> 
<input type="hidden" value="<%=parentMenuId %>" name="parentMenuID"></input>
<input type="hidden" value="<%=menuName %>" name="menuName"></input>
<input type="hidden" value="<%=parentMenuName %>" name="parentMenuName"></input>
${paginationData}
</div>
</form>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
<script type="text/javascript">
var LoginID = '<%=(String) request.getSession().getAttribute(
"userLoginId")%>';
		var menuID = '<%=menuId%>';
		 var userid = "<%=loginUser%>";
		 var role_exception_approval="<%=role%>";
         <!-- highlight active example based on the parameter passed from request -->
         $(document).ready(function(){
        	if(menuID==8){
 				var timer;
 					function refreshPage(){
 						document.location=document.location.href;
 					}
 				timer=setTimeout(refreshPage,30*1000);
 			 }
            $("#btn-" + "<%=request.getParameter("type")%>").removeClass("normal");
            $("#btn-" + "<%=request.getParameter("type")%>").addClass("active");

            if(menuID==0){
            	$("div.jmesa").find("tr.toolbar").hide();
            	$("div.jmesa").find("tr.filter").hide();
            }else if(menuID == 114 || menuID == 118  || menuID == 119 || menuID ==120 || menuID==121){
            	$(".customizelistpage").hide();
            }
            $.ajaxSetup({ cache: false });
            parent.unblockUI();  // For unblocking processing image on load of this page content 
         });

		 function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
         if(menuID=='149'){
        	 $(".Core_Id").hide();
        	 $(".Allow_Closeable_Flag").hide();
        	 var ticketidIndex="";
     		var workflowIndex="";
     		var colorCodeIndex="";
     		var coreIdIndex="";
     		var allowCloseableFlag="";
     		$(".jmesa").find(".header").find("td").each(function(j){
     			if($(this).text()=="C"){
     				colorCodeIndex=j;
     			}
     			if($(this).text()=="Ticket #"){
     				ticketidIndex=j;
     			}
     			if($(this).text()=="Status"){
     				workflowIndex=j;
     			}
     			if($(this).text()=="Core Id"){
     				coreIdIndex=j;
     			}
     			if($(this).text()=="Allow Closeable Flag"){
     				allowCloseableFlag=j;
     			}
     		});
     		$(".jmesa").find("table tbody").find("input").each(function(){
     			var obj =  $(this).closest("tr").find("td");
     			var ticketID =obj.eq(ticketidIndex).text();
     			var colorCode =obj.eq(workflowIndex).find("img").attr("id");//obj.eq(2).attr();
     			//alert($(obj+" td:eq(2) img").attr('id'));
     			var workflow_status = obj.eq(workflowIndex).text();
     			var coreId=obj.eq(coreIdIndex).text();
     			var allowCloseable=obj.eq(allowCloseableFlag).text();
        			var workflow_status = obj.eq(workflowIndex).text();
        			if(workflow_status!="Work In Progress" && workflow_status!="Assigned" && workflow_status!="3rd Party Resolved"){
            			$("#"+ticketID+"").hide();

        			}
        			if(coreId!=0 && allowCloseableFlag!="Y"){
        				$("#"+ticketID+"").hide();
        			}
        			
        		});
         }


</script>
</body>
</html>