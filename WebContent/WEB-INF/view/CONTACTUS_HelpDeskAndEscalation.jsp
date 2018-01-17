<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Contact Screen</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/user.css" />
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>

<body>
<% String token = null; %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="500" align="left" valign="top" rowspan="5">
		<div class="inner_cont"><img src="images/top_cont.jpg" /><br />
		  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
		  
		  <tr>
			    <td align="center" valign="middle"><img src="images/help_docs.png" width="100" height="60" /></td>
			    <td align="left" valign="middle">
               	  <span class="head_link">User Guide</span><br />
					<span class="txt"><a href="https://ispace.ig.capgemini.com/Documents/LHICONNECT_User_Guide.pdf" target="_blank">Download</a></span>
                </td>
		  </tr>
		  
	      <tr>
			    <td width="20%" height="63" align="center" valign="middle"><img src="images/phone1.jpg" width="60" height="60" /></td>
			    <td width="80%" align="left" valign="middle">
   	        <a href="#" class="head_link">Helpdesk Hotline</a><br />
   		    <span class="txt">1234</span></td>
	       </tr> 
			  <tr>
			    <td height="70" align="center" valign="middle"><img src="images/phone2.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
        			<a href="#" class="head_link">Helpdesk Phone</a><br />
					<span class="txt"> +91 80 41041801</span></td>
		      </tr>
			  <tr>
			    <td height="70" align="center" valign="middle"><img src="images/us_can.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
                	<a href="#" class="head_link">USA, Canada</a><br />
				<span class="txt">+1800 670 0199 (Toll Free) / +1 978 964 9062</span></td>
		      </tr>
			  <tr> 
			    <td height="70" align="center" valign="middle"><img src="images/eur_uk.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
           	    <a href="#" class="head_link">EUROPE, UK</a><br />
					<span class="txt">+44 208 283 2310 (Toll Free)</span></td>
		      </tr>
			  <tr>
			    <td align="center" valign="middle"><img src="images/aus.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
                	<span class="head_link">Australia</span><br />
					<span class="txt">61-353353050</span>
                </td>
		      </tr>
			  <tr>
			    <td align="center" valign="middle"><img src="images/mail.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
   	        <span class="head_link">Email</span><br />
		    		<span class="txt"><a href="mailto:LH.Helpdesk@igate.com">LH.Helpdesk@igate.com</a></span>
                </td>
		      </tr>
			 <!--  <tr>
			    <td align="center" valign="middle"><img src="images/FAQ.jpg" width="60" height="60" /></td>
			    <td align="left" valign="middle">
               	  <span class="head_link">FAQ</span><br />
					<span class="txt"><a href="https://ispace.ig.capgemini.com/Documents/iConnect-FAQ.pdf" target="_blank">Download</a></span>
                </td>
		      </tr> -->
		  </table>
		</div>
    </td>
	<td align="center" valign="top"> 
	  <div class="rightside">
	  <span>Escalation Matrix</span>
	  </div>
	    <c:forEach items="${functionList}" var="type">
    <div id="myAccordion">
  
				
				<h2 align="left"><a href="#" class="accor_head" title="Click to View IS Contact">${type.WORKFLOW_NAME}<img src="images/arrow-down.gif" align="right" style="border:none" title="Click to View IS Contact"></img></a></h2>		
							
							<div class="sub_tab_bg">
        						<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="my_sub_tab">
									  <tr class="my_sub_tab_head">
										<td width="10%" align="center" valign="middle"><strong>Escalation<br />Level</strong></td>
										<td width="15%" align="center" valign="middle"><strong>Role</strong></td>
										<td width="15%" align="center" valign="middle"><strong>Name</strong></td>
										<td width="30%" align="center" valign="middle"><strong>Email Id</strong></td>
										<td width="25%" align="center" valign="middle"><strong>Phone</strong></td>
									 </tr>
									 
									 <c:forEach items="${functiondataList}" var="typ">
									 <c:if test="${type.WORKFLOW_NAME eq typ.WORKFLOW_NAME}">
									 <tr class="my_sub_tab_data">
											<td height="22" align="left" valign="middle">${typ.ESCALATION_LEVEL}</td>
											<td align="left" valign="middle">${typ.DESIGNATION_NAME}</td>
											<td align="left" valign="middle">${typ.EMPLOYEE_NAME}</td>
											<td align="left" valign="middle"><p><a href="mailto:${typ.EMAIL_ADDRESS}">${typ.EMAIL_ADDRESS}</a></p></td>
											<td align="left" valign="middle"><p>Hotline/LL:${typ.CONTACT_NUMBER} <br />
													Cell:${typ.MOBILE_NUMBER} </p></td>
									</tr>
									</c:if>
									 </c:forEach>
								 
						 </table>
						
						 </div>
						 
						  

	</div>  
	</c:forEach> 
	</td>
		</tr>
  	    </table>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.core.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.accordion.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/effects.core.js"></script>
<script type="text/javascript">
var accOpts = {
		  autoHeight: false,
		  collapsible: true,
		  alwaysOpen: false	  
		  
		};

$(document).ready(function() { 
		  $("#myAccordion").accordion(accOpts);
		  $("#myAccordion").filter(":has(.ui-state-active)").accordion("activate",false);
		  parent.unblockUI();
}); 
</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>

</html>