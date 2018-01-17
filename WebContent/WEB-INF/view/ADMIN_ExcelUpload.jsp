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

	String mobileno = userObj.getMobile();
	String extnno = userObj.getExtension();
	String loginrole = userObj.getUserRole();
%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery-ui.css"/>
<!--	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery-ui-custom.css"/>	-->
	<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/jquery.ui.all.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
	<!--[if gte IE 9]>
	<style>
	.ui-autocomplete
	{
	left: 261px !important;
    top: -44px !important;
    position:relative;
    }
    </style>
	<![endif]-->
</head>
<body>
<% String token = null; %>
<form:form  enctype="multipart/form-data" modelAttribute="UploadForm" id = "UploadFormID" 
				 name="UploadFormID" action="ADMIN_ExcelUpload.htm" method="post">	
<table border="0" cellspacing="0" width="100%" align = "center" class = "createTable" id="uploadTable">

		<tr>
			<td colspan="3" align="center"><span class="containerBlock1">Excel Upload</span></td>
		</tr>
		
		<form:errors path="*" cssClass="errorblock" element="div" />
		<tr>
			<td class="label">
				Please select a file to upload<font color="red">*</font> :
			</td>
			<td align="left"><input type="file" name="ExcelUpload" id="ExcelUpload"  />
			<span id="ExcelUploadResultMessage" class="invalid_text"></span>			
			</td>			
		</tr>
		
		<tr>
			<td class="label">Table Name<span class="red_text">* </span>:</td>
			<td><input id="tableName" name="tableName" type="text" class="myTextInput"  maxlength="100" />
		<span id="tableNameResultMessage" class="invalid_text"></span></td>
		</tr>
		<tr>
			<td class="label">Sheet Name<span class="red_text">* </span>:</td>
			<td><input type="text"   maxlength="100" id="sheetName" name="sheetName" class="myTextInput"/>
				<span id="sheetResultMessage" class="invalid_text"></span>
			</td> 
		</tr>
		<tr>
			<td></td>
			<td align=left colspan="2"><input type="submit" value="Upload" id="UploadID"/></td>
		</tr>
	
		
</table>
</form:form>

<div id="output1" style="display: none"></div>

<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<!--<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.7.min.js" ></script>-->
<!--<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui-1.8.18.custom.min.js" ></script>-->
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui-custom.min.js" ></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js" ></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_ExcelUpload.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.client.js"></script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %> /">
</body>
</html>