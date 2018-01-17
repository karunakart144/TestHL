<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/validationEngine.jquery.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.bt.css" media="screen" />
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body>
<% String token = null; %>
<br></br>
<form:form modelAttribute="CreateBulletinBoardBean" id="createBulletinBoardBeanId"
	action="BULLETINBOARD_Create.htm" name="createBulletinBoardBean" method="post"
	enctype="multipart/form-data">
	<table border="0" id="bulletionBoardcreationtable" cellspacing="0"
		cellpadding="3" width="80%" align="center" class="createTable">
		<tr class='none'>
			<td colspan="2" align="center"><span class="containerBlock1">New Bulletin Board Creation</span></td>
		</tr>
		<tr><td class="label" valign="top">Bulletin Header<span class="red_text">*</span></td><td><form:input path="header" id="header" class="myTextInput" /></td></tr>
		<tr class="creationScreenAlternateTR">
			<td class="label" valign="top">Bulletin Message<span class="red_text">*</span></td>
			<td><form:textarea rows="6" id="description" path="description"
				style="width:270px" maxlength="500" onkeyup="javascript:countCharacters('mycounter',500,'description')"></form:textarea>
			</td>
				</tr>
				<tr class="none"><td colspan="2" align="center"><font size="1">Character Remaining : <span id="mycounter">500</span></font></td></tr>
			<tr class="creationScreenAlternateTR">
			<td colspan="2" align="center"><input type="Submit"
				id="Save" value="Save"></td>
		</tr>
	</table>
	<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</form:form>

<div id="output1" style="display: none"></div>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/BULLETINBOARD_Create.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bt.min.js"></script>	
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
</body>
</html>
