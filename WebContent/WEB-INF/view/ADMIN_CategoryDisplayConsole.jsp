<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/adminConsole.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />		
	      <link media = "screen" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" />  
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jmesa.css"></link>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/example.css"></link>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/print.css" media="print"/>
	<!--[if lt IE 8]><link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/ie.css" media="screen, projection"/><![endif]-->
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/fancy-type/screen.css" media="screen, projection"/>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" media = "screen"/>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/common.css" />	
		
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" /> 
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.multiselect.css" />	
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/adminConsole.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jquery.autocomplete.css" />	
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>	
</head>
<body>
<% String token = null; %>
<br></br>
<table border="0" cellspacing="0" cellpadding="2" id="CATEGORY_TABLE" width="80%"  class = "createTable" align='center'>
<tr><th colspan="4" style="background:#ffebbf"><b><font size='2' >Modify Category</font></b></th></tr>
			
<tr><td colspan="4"></td></tr>
	<tr id='CATEGORY_TR' align='left'>
			<td colspan="4" align='left'><span class="containerBlock1"><b>View Category</b><font color="brown"><b><i> (Select a Function and search for a Category)</i></b></font></span></td>
			</tr>
			<tr id='resTR'>
			<td><div id='resultMsgs'  class='resultMsgs'></div></td>
			</tr>
			<tr align='left' class="creationScreenAlternateTR">
				<td class="labelAdmin" align='left'>
					Function<span class="red_text">* </span>
				</td>
				<td width='30%' align='left'>
					<select id="function" name="function" style="height:26px">
						<option selected="selected" value="0">--Please Select--</option>
								<c:forEach items="${type}" var="type">
											<option value="${type.CATEGORY_ID}" >${type.NAME}</option>
								</c:forEach>
					</select>				
				</td>
				
			</tr>
			<tr id="newCountryTR" align='left'  width='20%'>
				<td class="labelAdmin" align='left'>
					<span class="red_text"></span>Category Name</td>
				<td align='left' > <input type="text" id="categoryName" maxlength="30" style="height: 19px;width: 225px;"></input></td>	
			</tr>
			<tr align='left' class="creationScreenAlternateTR">
				<td class="labelAdmin" align='left'></td>
				<td align='left'>					 	
					<input type="radio" checked="checked" name="status" id="statusId" value="1" onclick="getCategoryList()"/>Active&nbsp;
					<input type="radio"  name="status" id="statusId" value="0" onclick="getCategoryList()"/>In Active&nbsp;&nbsp;&nbsp;
					<input type="button" id="viewCategory" value="View" onclick="getCategoryList()"/> &nbsp;&nbsp;
					<input type="button"  class='submitButton_categoryDetail' id="submitButton_categoryDetail" value="Update Category" onclick="validateRequest_categoryDetail()"></input>
				</td>	
			</tr>
				
		</table>
	<table border="0" cellspacing="0" cellpadding="2" id="CATEGORY_DET_TABLE"  width="90%"  class = "createTable" align='center'>
			<tr align='left'>
				<td colspan="4" align='left'><span class="containerBlock1"><b>Category detail</b><font color="brown"><i> (Edit detail of category )</i></font></span></td>				
			</tr>
			<tr id="CATEGORY_JMESA_TR" align='left'>
				<td align='left'>
					<div id="CATEGORY_JMESA"><input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/></div>
				</td>
			</tr>
			
</table>
<div id="output1" style="display: none"></div>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/ui.multiselect.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_CategoryDisplayConsole.js"></script>	
<script type="text/javascript">
var catCheckboxArr=new Array();
var isValidJson=1;
$(document).ready(function() {
	parent.unblockUI();
	
	$("#viewCategory").click(function() {	
		//call category detail function		
		getCategoryList();
	});

	
});

var categoryJson="{ \"category\":{";
var endCategoryJson="}}";

var changedJson="{ \"category\":{";
var endChangedJson="}}";

var deletedApproverArray=new Array();
var approvrArray=new Array();
var valArray=new Array();

var oldApproverArr=new Array();
var newApp=new Array();
var newApproverArr=new Array();

var finalJsonToUpdate="";


function onInvokeExportAction(id) {		
	 var parameterString = createParameterStringForLimit(id);					
}

function onInvokeAction(id) {
	 setExportToLimit(id, '');
	 var statusIdVal = $('input[name=status]:radio:checked').val();	 
	  var functionId = $('#function').val();
	  var categoryName=$("#categoryName").val();		  
	  var finalString = "functionId="+ functionId + "&categoryName="+categoryName+"&statusVal="+statusIdVal+"&";
	  var parameterStringxx = createParameterStringForLimit(id);
	  var parameterString = finalString + parameterStringxx;	
	  $.ajaxSetup({ cache: false });  
	  $.get("adminConsole_getCategoryList.htm?"+parameterString,function(data) {
		 $("#CATEGORY_JMESA_TR").removeClass();
		 $("#CATEGORY_JMESA").removeClass();        
         $("#CATEGORY_JMESA").html("");      
         $("#CATEGORY_JMESA").html(data);
         //Reset the old values on Pagination
         resetTableOnPagination(catCheckboxArr,changedJson,'Category_Detail');
     });

	
	 	     
}
</script>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>