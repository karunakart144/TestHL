<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />		
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>	
</head>
<body>
<% String token = null; %>
	 <table width="80%" border="0" cellpadding="3" cellspacing="0" align="center">
		<tr class = "sectionHeadingBlockForAssignment">
			<td width = "70%">Bulletin Board Message</td>
			<td width = "30%">
				<table width="100%" border="0" cellpadding="3" cellspacing="0" align="right">
					<tr>
						<td style="font-size:10px; text-align:right;">&nbsp;</td>
						<td>
							<div>
								<input type="button" id="RemoveMsg" value="Remove"/>
							</div>
						</td>						
					</tr>
				</table>
			</td>
		</tr>		
	</table>
    <table width="80%" border="0" cellpadding="3" cellspacing="0" align="center" id="bulletinMsg" class = "createTable">	
		<tr>		
		</tr>
	</table>	
	<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=jsDirPath%>/custom/BULLETINBOARD_Remove.js"></script>
		<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
	</body>
</html>
