<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/adminConsole.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
<link media="screen" rel="stylesheet"
	href="<%=cssDirPath%>/colorbox.css" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jmesa.css"></link>
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/example.css"></link>
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/bp/print.css" media="print" />
<!--[if lt IE 8]><link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/ie.css" media="screen, projection"/><![endif]-->
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/bp/fancy-type/screen.css"
	media="screen, projection" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/colorbox.css" media="screen" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/common.css" />

<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/ui.multiselect.css" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/adminConsole.css" />
<link type="text/css" rel="stylesheet"
	href="<%=cssDirPath%>/jquery.autocomplete.css" />
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>

</head>

<body>
<% String token = null; %>
<table border="0" cellspacing="0" cellpadding="2" id="LOCATION_TABLE" width="90%"  class = "createTable" align='center'>
<tr><th colspan="4" style="background:#FAAC58"><b><font size='2' >Modify Location</font></b></th></tr>
<tr><td colspan="4"></td></tr>
	<tr id='CATEGORY_TR' align='left'>
			<td colspan="4" align='left'><span class="containerBlock1"><b>View Location</b><font color="brown"><b><i> (Select a Location and search for a Location Details)</i></b></font></span></td>
			</tr>
			<tr id='resTR'>
			<td><div id='resultMsgs'  class='resultMsgs'></div></td>
			</tr>
			<tr align='left'>
				<td class="labelAdmin" align='left' width='20%'>
					<span class="red_text">* </span>Location
				</td>
				<td width='30%' align='left'>
					<select class="myTextInput" id="locationToChange" name="locationToChange">
					<option selected="selected" value="0">--Please Select--</option>
					<c:forEach items="${locations}" var="type">
						<option value="${type.LOCATION_ID}" >${type.CITY}</option>
					</c:forEach>
				</select>
				</td>
				
			</tr>
			<tr align='left'>
				<td class="labelAdmin" align='left'></td>
				<td align='left'  width='20%'>					 	
					<input type="button" id="viewLocation" value="View" onclick="getLocationDetails()"/>
				</td>	
			</tr>
				
		</table>
	<table border="0" cellspacing="0" cellpadding="2" id="CATEGORY_DET_TABLE"  width="90%"  class = "createTable" align='center'>
			<tr align='left'>
				<td colspan="4" align='left'><span class="containerBlock1"><b>Location detail</b><font color="brown"><i> (Edit Location details )</i></font></span></td>				
			</tr>
			<tr>
			<td colspan="4" align='right'><input type="button"  class='submitButton_locationDetail' id="submitButton_categoryDetail" value="Update Location" onclick="validateRequest_LoactionDetail()"></input></td>
			</tr>
			<tr id="LOCATION_JMESA_TR" align='left'>
				<td align='left'>
					<div id="LOCATION_JMESA"><input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/></div>
				</td>
			</tr>
			
</table>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/ui.multiselect.js"></script>
<!--<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
-->
<script type="text/javascript"
	src="<%=jsDirPath%>/custom/ADMIN_ModifyLocation.js"></script>

<script type="text/javascript">


var locCheckboxArr=new Array();
var isValidJson=1;

var categoryJson="{ \"location\":{";
var endCategoryJson="}}";

var changedJson="{ \"location\":{";
var endChangedJson="}}";

var finalJsonToUpdate="";


function onInvokeExportAction(id) {		
	 var parameterString = createParameterStringForLimit(id);					
}

function onInvokeAction(id) {
	 setExportToLimit(id, '');
	
	  var locationId = $("#locationToChange :selected").text();
	
	  var finalString = "locationId="+ locationId+"&statusVal=''";
	  var parameterStringxx = createParameterStringForLimit(id);
	  var parameterString = finalString +"&"+ parameterStringxx;	
	  $.ajaxSetup({ cache: false });  
	  $.get("adminConsole_getLocationDetails.htm?"+parameterString,function(data) {
		 $("#LOCATION_JMESA_TR").removeClass();
		 $("#LOCATION_JMESA").removeClass();        
         $("#LOCATION_JMESA").html("");      
         $("#LOCATION_JMESA").html(data);
         //Reset the old values on Pagination
         resetTableOnPagination(locCheckboxArr,changedJson,'Location_Detail');
     });

	
	 	     
}
</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>