<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<% 
	String SSOFlag = (String)request.getSession().getAttribute("SSOFlag");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/user.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />

</head>

<body>
<% String token = null; %>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
function iConnectLogOut(){
	window.location.href='Logout.htm';
}
var ssoFlag ="<%=SSOFlag%>";

function setHeaderID(id){
	$.session("modname1",id);
	$.getJSON('UserHome1.htm',{modname : id},function() {
		
	});
	window.location.href="UserHome.htm";
}

</script>
<%@ include file="UserHeader.jsp"%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" height="440px">
		<tr>
	    	
	    	<td width="60%" align="left" valign="top">
            
            <!-- ============== User Screen Starts =============== -->
            <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" >
  				<tr>
    				<td width="50%" align="center" valign="middle">
                    
                    <!-- ============== New Ticket Starts =============== -->                
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  						<tr>
					    	<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('RaiseNewTicket')" href="#"><img src="images/new_ticket.jpg" alt="Raise New Ticket" title="Raise New Ticket" /></a></td>
    						<td width="75%" align="left" valign="middle" height="25%">
                            	<a onclick="setHeaderID('RaiseNewTicket')" href="#" class="head_link">Raise New Ticket</a><br />
                            	<span class="txt">Create new ticket related to<br/>ITIM/IS/CHCS/HR/Finance/Admin/OE/Delivery</span></td>
 						</tr>
					</table>
					</div>
					</div>
					<!-- ============== User Screen Ends =============== -->                    

        			</td>
    				<td width="50%" align="center" valign="middle">
                
                	<!-- ============== View Ticket Starts =============== -->
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    					<tr>
    			    		<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('ExistingTickets')" href="#"><img src="images/view_ticket.jpg" alt="View Existing Ticket" title="View Existing Ticket"/></a></td>
    			    		<td width="75%" align="left" valign="middle">
                        		<a onclick="setHeaderID('ExistingTickets')" href="#" class="head_link">View Existing Ticket</a><br />
    			      			<span class="txt">To get the list of tickets created in the past</span></td>
  			    		</tr>
  			  		</table>
  			  		</div>
  			  		</div>
                	<!-- ============== View Ticket Ends =============== -->
                
                	</td>
  				</tr>
  				<tr>
    				<td align="center" valign="middle">
                
                	<!-- ============== Approval Ticket Starts =============== -->
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    					<tr>
    			    		<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('WaitingForApproval')" href="#"><img src="images/approval.jpg" alt="Waiting for My Approval" title="Waiting for My Approval"/></a></td>
    			    		<td width="75%" align="left" valign="middle">
                        		<a onclick="setHeaderID('WaitingForApproval')" href="#" class="head_link">Waiting for My Approval</a><br />
    			      			<span class="txt">To get the list of tickets that seek approval</span></td>
  			    		</tr>
		    		</table>
		    		</div>
		    		</div>
                	<!-- ============== Approval Ticket Ends =============== -->
                
                	</td>
                	<td align="center" valign="middle">
                
                	<!-- ============== Contact Info Starts =============== -->
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    			  		<tr>
    			    		<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('ContactInfo')" href="#"><img src="images/contact.jpg" alt="Contact Info" title="Contact Info"/></a></td>
    			    		<td width="75%" align="left" valign="middle">
                        		<a onclick="setHeaderID('ContactInfo')" href="#" class="head_link">Helpdesk & Escalations - Contact</a><br />
    			      			<span class="txt">Information Center to get the help</span></td>
  			    		</tr>
  			  		</table>
  			  		</div>
  			  		</div>
                	<!-- ============== Contact Info Ends =============== -->
                
                	</td>
    				
 				</tr>
 				<tr>
 				<td align="center" valign="middle">
                
                	<!-- ============== Approved by me list Ticket Starts =============== -->
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    					<tr>
    			    		<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('ApprovedByMe')" href="#"><img src="images/showApp.png" alt="Approved by Me" title="Approved By Me"/></a></td>
    			    		<td width="75%" align="left" valign="middle">
                        		<a onclick="setHeaderID('ApprovedByMe')" href="#" class="head_link">Approved By Me</a><br />
    			      			<span class="txt">To get the list of tickets that are approved by me</span></td>
  			    		</tr>
  			  		</table>
  			  		</div>
  			  		</div>
                	<!-- ============== Approved by me list Ends =============== -->
                
                	</td>
    				<td align="center" valign="middle">
                
                	<!-- ============== Rejected by me list Starts =============== -->
                	<div class="outer">
    					<div class="inner"><img src="images/top.jpg" /><br />      
    					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    					<tr>
    			    		<td width="25%" align="center" valign="middle"><a onclick="setHeaderID('RejectedByMe')" href="#"><img src="images/showReject.png" alt="Rejected by Me" title="Rejected By Me"/></a></td>
    			    		<td width="75%" align="left" valign="middle">
                        		<a onclick="setHeaderID('RejectedByMe')" href="#" class="head_link">Rejected By Me</a><br />
    			      			<span class="txt">To get the list of tickets that are rejected by me</span></td>
  			    		</tr>
		    		</table>
		    		</div>
		    		</div>
                	<!-- ============== Rejected by me list Ends =============== -->
                
                	</td>
    				
 				</tr>
  				</table>
        	<!-- ============== User Screen Ends =============== -->    
            
		  </td>
			<td class="center" valign="top" style="padding-top:10px;">	<!--bulletin 

strs-->
							<div id="stcikyDivId" class="board">	
					<div id='bulletinBoard'><center><font color="white">Bulletin Board</font></center></div>
					</div>
						<!--bulletin ends --></td>
		</tr>
	</table>
<div class="footer">
	Copyright &copy; IGATE. All Rights Reserved.
</div>
<script type="text/javascript" src="<%=jsDirPath%>/custom/User.js"></script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>