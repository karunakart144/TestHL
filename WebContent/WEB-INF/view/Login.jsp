<%@ include file="/WEB-INF/view/UNIVERSAL_Include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>

<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/login_style.css" />
  <style id="antiClickjack">body{display:none;}</style>
                                
                                <script type="text/javascript">
                                if (self === top) {
                                
                 var antiClickjack = document.getElementById("antiClickjack");
                                antiClickjack.parentNode.removeChild(antiClickjack);
                                }                              

                </script>
</head>

<body>
<% String token = null; %>
<div class="main_container">

	<div class="inner_container">
		<div class="login_header_bg">Welcome</div>
	</div>
    
    <div>&nbsp;</div>
    
    <div class="inner_container">
    
    	<!-- ============================ Left Panel Starts ================================ -->
    
    	<div class="left_panel">
    	<img src="<%=request.getContextPath()%>/images/Logo.png" /><br />   	
    	</div>
         
		<!-- ============================ Left Panel Ends ================================ -->
        
      	<div class="right">
        
        	<!-- ============================ Middle Panel Starts ================================ -->  
        
	        <div class="middle_panel">
            
            	<div class="inner_container">
                
                	<div class="login">
                	<form:form method="POST" commandName="UserBean" id = "login" name="form">
		            <table cellspacing="0" cellpadding="15" align="center">
			            <tr>
							<td align="left" valign="middle">
								<table cellspacing="0" cellpadding="5"
										style="color: #FFFFFF; font-size: 13px;" id = "lockImageTable">
									<tr>
										<td colspan="2">
					        				<p><img src="<%=request.getContextPath()%>/images/key.jpg" /></p>
					        				<p class="button">Fill in your User ID and Password</p><br />
		        						</td>
		        					</tr>
		        				</table >
		        				<table cellspacing="0" cellpadding="8" id = "loginTable">
   									<tr align="center">
   										<td colspan="2">
											<form:errors path="loginId" cssClass="error bodytext" />
											<form:errors path="password" cssClass="error bodytext" />
										</td>
									</tr>
		        					<tr id = "loginIdTR">
		        						<td class="labels">
						        			User ID: </td>
						            	<td>
						            				<form:input path="loginId" autocomplete="off"	id="loginId" />
						            			</td>
			        				</tr>
			        				<tr id = "passwordTR">
										<td class="labels">Password: </td>
						            			<td><form:password path="password" autocomplete="off"
														id="passWordId" cssClass="bodytext" />
						      					</td>
			        				</tr>
			        				<tr id = "domainTR" style="display:none">
			        					<td class="labels"></td>
										<td><input id="roleListSize" type="hidden"	value="${roleListSize}"/>
										</td>
									</tr>
			        				<tr>
												<td>&nbsp;</td>
												<td><input class="button" TYPE="IMAGE" src="<%=request.getContextPath()%>/images/login_button.jpg" name="login"
													id="login" value="Login"/><input id="buttonName" name = "buttonName"type="hidden"	value="${button}"/></td>
									</tr>
			      				</table>
			      			<table id="roleSelectionTable" width="80%" cellspacing="0" cellpadding="5" style="color: #FFFFFF; font-size: 13px;">
							<tr>
							<td colspan="3">
								
                                    </td>
							</tr>
                                <tr>
									<td colspan="3">
										 <img src="<%=request.getContextPath()%>/images/login_success.jpg" />
                                    </td>
                                </tr>
                                <tr>
									<td colspan="3" class="message">Authentication was Success! Please Select a Role to Continue</td>
                                </tr>
                                <tr>
									<td width="5%" align="right" class="button">Role:</td>
									<td align="left" colspan="2">
										<form:select path="userRoleId" id = "userRoleId">
											<form:options items="${userRoleList}" itemValue="roleId" itemLabel="roleName"/>
										</form:select>
                                    </td>
                                </tr>
                                <tr>
									<td>&nbsp;</td>
									<td align="left" width="10%">
										<input name="login1" TYPE="IMAGE" src="<%=request.getContextPath()%>/images/continue_button.jpg" id="login1" value="Continue" onclick = "javascript:continueLogin();"/>
									</td>
									<td align="left">
										<div id="logoutButton" align="left">
											<a href="#" onclick="iConnectLogOut();"><img src="images/logout.png" alt="Logout" title="Logout"/></a>
										</div>
                                    </td>
                                </tr>
							</table>	
			      			</td>
		      			</tr>
	      			</table>	
					<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
	      			</form:form>
            		</div>
                
            </div>
</div>
            
            <!-- ============================ Middle Panel ends ================================ -->
            
            <!-- ============================ Right Panel Starts ================================ -->  
            
    	    <div class="right_panel">
            
				<div class="logo">
        			<img src="<%=request.getContextPath()%>/images/Capgemini_Login_Logo.png"/>
				</div>
        		

			<div id="stcikyDivId" class="board">	
					<div id='bulletinBoard'><center><font color="white">Bulletin Board</font></center></div>
			</div>
			           
            <!-- ============================ Right Panel Ends ================================ -->  
            
       	</div>
        
	</div>
    <div class="contact">
				<div class="contact_info"><span>Helpdesk Hotline:</span> 1234 &nbsp; | &nbsp; <span>Helpdesk Phone:</span> +91 80 41041234 &nbsp; | &nbsp; <span>USA, Canada:</span> +1800 670 0199 (Toll Free) / +1 978 964 9062  &nbsp; | &nbsp; <span>EUROPE, UK:</span> +44 208 283 2310 (Toll Free) &nbsp; | &nbsp;<span>Australia:</span> 61-353353050 &nbsp; | &nbsp;<span>Email:</span> <a href="mailto:LH.Helpdesk@igate.com">LH.Helpdesk@igate.com</a>
            </div>
	</div><!--
   <div class="empty_div">&nbsp;</div>
	<div id="copy">Copyright &copy; IGATE All Rights Reserved.</div>
   -->
   
    <div class="footer">
			<div class="footer_info" align='center'>Copyright &copy; IGATE. All Rights Reserved.</div>
	</div>
   
   </div>
   </div>
</body>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/Login.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script>
if(parent.location != window.location){
	parent.iConnectLogOut();
}
function iConnectLogOut(){
	window.location.href='Logout.htm';
}
</script>
</html>