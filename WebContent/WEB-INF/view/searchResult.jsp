<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.igate.iconnect.BO.User" %>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<%  String empId = (String) request.getSession().getAttribute(
"userLoginId");
User userObj = (User) request.getSession().getAttribute(
        empId);
String empName = userObj.getUserName();
String location = userObj.getLocationCity();
String currentUserRole = userObj.getUserRole();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=cssDirPath%>/pagination.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.session.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/search.js"></script>
<link href="<%=cssDirPath%>/styles.css" rel="stylesheet" type="text/css" />
<title>Search Results</title>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
var context = "${pageContext.request.contextPath}";
loc = String(window.location);
var urlAppl = loc.substr(0,loc.indexOf(context));
$(document).ready(function(){
$.getJSON('bulletinMessage.htm', {
		}, function(data) {
			if (data.length != 0) {
				var bulltinHtml="";
				bulltinHtml=bulltinHtml+"<div id='bulletinBoard'><center><strong>Bulletin Board</strong></center></div>";
				bulltinHtml=bulltinHtml+"<marquee behavior='scroll' direction='up' scrollamount='3' onmouseover='this.stop();' onmouseout='this.start();'  style='width:100%;'>";
				var options = [];
				$.each(data, function(item) {
					bulltinHtml+="<h4><u><center>"+data[item].BULLETIN_HEADER+"</center></u></h4><span><center>"+data[item].BULLETIN_DESCRIPTION+"</center><br></span>";
				});
				bulltinHtml+="</marquee>";
				$('#bulletinBoard').html(bulltinHtml);
			}
			
	});
	if("${sessionScope.typeOfLink}" == "userSolution")
	{
	   		$("#docDiv").hide();
    		$("#contentDiv").hide();
    		$("#userSolutionDiv").show();
    		getTagDetails();
    }else if("${sessionScope.typeOfLink}" == "document")
    {
    	loadFrame("${sessionScope.docLink}");
		$("#contentDiv").hide();
		$("#userSolutionDiv").hide();
		$("#docDiv").show();
		getUsrComment();
		
    }else if("${sessionScope.typeOfLink}" == "policyDocument")
    {
		loadFrame("${sessionScope.docLink}");
  		$("#contentDiv").hide();
		$("#userSolutionDiv").hide();
		$("#docDiv").show();
		getUsrComment();
		
    }
    else if("${sessionScope.typeOfLink}" == "ticket")
    {
    	
   		$("#docDiv").hide();
		$("#userSolutionDiv").hide();
		$("#contentDiv").show();
        getTicketDetails();
    }
 

});


function suppressBackspace(evt) {
    evt = evt || window.event;
    var target = evt.target || evt.srcElement;

    if (evt.keyCode == 8 && !/input|textarea/i.test(target.nodeName)) {
        return false;
     }
}

function setHeaderID(id){
	$.session("modname1",id);
	$.getJSON('UserHome1.htm',{modname : id},function() {
		
	});
	window.location.href="UserHome.htm";
}
document.onkeydown = suppressBackspace;
document.onkeypress = suppressBackspace;
</script>
</head>
<body>
<% String token = null; %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td colspan="2" align="center" valign="top" class="headerLine" >
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="header_bg">
      <tr>
        <td width="15%" align="left" valign="middle"><img src="images/searchAI/iConnect_Logo.png" width="205" height="60" /></td>
        <td width="70%" align="center"><img src="images/searchAI/header.png" /></td>
        <td width="15%" align="right" valign="middle"><img src="images/searchAI/Capgemini_Search_Logo.png" width="130" height="60"  style="margin-right:6px" />
        </td>
      </tr>
      <tr>
      <td></td>
           <td colspan="2" align="right"><span>Welcome <b><%=empName %></b>,<%=location %> </span><a onclick="iConnectLogOut();"><img src="images/searchAI/logout.png" alt="Logout" title="Logout"/></a></td>
      </tr>
    </table></td>
  </tr>
<tr>
<td>
  <div style="border-right: 1px dashed #dedede; float: left;width:20%">
    
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td>
        	<div class="board" id="bulletinBoard">
          		<div><strong>Bulletin Board</strong></div>
          		<marquee behavior="scroll" direction="up" scrollamount="3" onmouseover="this.stop();" onmouseout="this.start();  style='width:100%;'">
        		<h5><u><center>IConnect Smart Search</center></u></h5><span><center>Smart search is coming soon...</center><br></span>
        		</marquee>
        	
        	</div>
		</td>
      </tr>
  <tr>
        <td>
    <div class="helpdesk">
          		<div><strong>Helpdesk - IConnect</strong></div>
           <ul>
					<li><a onclick="setHeaderID('RaiseNewTicket')" href="#" >Raise
					New Ticket</a></li>
					<li><a onclick="setHeaderID('ExistingTickets')" href="#">View
					Existing Ticket</a></li>
					<li><a onclick="setHeaderID('WaitingForApproval')" href="#">Waiting
					for My Approval</a></li>
					<li><a onclick="setHeaderID('ApprovedByMe')" href="#"> Approved
					By Me</a></li>
					<li><a onclick="setHeaderID('RejectedByMe')" href="#">Rejected
					By Me</a></li>
		<!-- 			<li><a onclick="setHeaderID('CreateServiceTicket')" href="#">Create Service Ticket</a></li>
					<li><a onclick="setHeaderID('ViewServiceTicket')" href="#">View Service Ticket</a></li> -->
					<li><a onclick="setHeaderID('ContactInfo')" href="#">Contact
					Info</a></li>
				</ul>
        	</div>
        </td>
      </tr>
    </table>    
    </div>
<div style="float: left ;width:60%">
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr height="40px;">
        <td align="right"><div class="backBtn"> <a href="goSearchHome.htm?backToSrch=true" ><img alt="Back To Search" src="images/searchAI/back_search.png"> Back to search </a> </div></td>
        </tr>
      <tr>
        <td height="102" align="left" valign="top">
<!--	<div id="embedded" style="overflow:hidden; margin:0px; padding:0px;"></div>-->

<div id="docDiv" height="420px">
<iframe id="docPlaceHolder" height="400px" width="100%" style="overflow: hidden;" frameborder="0" > </iframe>
</div>

<div id="userSolutionDiv">
<table class="detailsTable" cellpadding="4px" cellspacing="4px">
<tr><td><b>Solution provided by : <span id="userName"></span></b></td></tr>
<tr><td id="userSolution"></td></tr>
</table>
</div>
<div id="contentDiv">
<table class="detailsTable" cellpadding="4px" cellspacing="4px"><tr><td><b> Category : </b> </td><td> <span id="detailsCat"></span> </td></tr>
		                    <tr><td><b> Place: </b> </td><td id="detailsPlace"></td></tr> 
		                    <tr><td><b> City  : </b></td><td id="detailsCity"> </td></tr>
		                    <tr><td><b> Subject  : </b></td><td id="detailsSub"></td></tr> 
		                    <tr><td><b> Description  : </b> </td><td id="detailsDes"></td></tr>
		                    <tr><td><b> Resolution  : </b> </td><td id="detailsRes"></td></tr>
		                    </table>
</div>

	<div id=userInfo style="margin-top: 20px; margin-left: 5px;">  <br></br> 
  		<div id="userVote">
  			<img src=images/searchAI/up.png onclick="updateLikeCount();" id="likeImg"></img> 
			(<span id=likeCount></span>) &nbsp;&nbsp; 
			<img src=images/searchAI/down.png onclick="updateDisLikeCount();" id="disLikeImg"></img> 
			 (<span id=dislikeCount></span>)
			 <br></br> 
	    </div>
	    <div id="userCommentContainer" >
	    	<div id="userCommentHolder"></div>
	    	<div  >
	    	<div style="margin-top: 10px; ">
	    		<textarea rows="5" cols="40" id="userComment"></textarea>
	    	</div>
	    	<div style="margin-top: 10px; margin-left: 30px; " align="left">
	    	 <input type="button" value="Post Your Comment" onclick="postComments()">
	    	</div>
	    	</div>
	    </div>
	</div>

        </td>
        </tr>
    </table>

  </div>
</td></tr>
<tr>
<td>
<div class="footer" style="clear:both">
	Copyright &copy; IGATE. All Rights Reserved.
</div>
</td>
</tr>

 </table>
      <% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>