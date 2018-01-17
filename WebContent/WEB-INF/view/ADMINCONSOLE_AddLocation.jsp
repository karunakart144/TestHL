<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="java.util.*"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 

ResourceBundle bundle = ResourceBundle.getBundle("iconnect");
String[] approverIDList=bundle.getString("approverIDList").split(",");
String[] approverNameList=bundle.getString("approverNameList").split(",");
String html_approver="";
for(int i=0;i<approverIDList.length;i++){
	html_approver+="<option value=\""+approverIDList[i]+"\">"+approverNameList[i]+"</option>";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
	<title>IConnect : Unified Service Desk for Life & Health</title>
	
<link rel="stylesheet" type="text/css" href="<%=cssDirPath%>/tab_style.css"/> 
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jmesa.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/example.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/print.css" media="print"/>
<!--[if lt IE 8]><link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/ie.css" media="screen, projection"/><![endif]-->
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/fancy-type/screen.css" media="screen, projection"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" media = "screen"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/common.css" /> 
	
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/datepicker/jquery.datepick.css" />	
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
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>	
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.datepick.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/ui.multiselect.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_Security.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/datepicker/jquery.datepick.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMINCONSOLE_AddLocation.js"></script>
<div style="display: none;">
<img id="callImg" class="trigger" alt="Popup" title="Click me" src="images/calendar.gif" align="left"></img>
</div>
<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td align="left" valign="bottom">
				<div id="adminAddLocationSteps" class="tabnavi">
					<ul> 
						<li><a href="#Step-1" title="Step-1" onclick="getAdminTableStep1()">Step:1-Add Location</a></li>
						<li><a href="#Step-2" title="Step-2" onclick="getAdminTableStep2()">Step:2-Map groups</a></li>																 
					</ul>
				</div>
			</td>
		</tr>
</table>
<br/>
<div id="adminTableStep1">

	<table border="0" cellspacing="0" cellpadding="2" id="STEP1_TABLE" width="70%"  class = "createTable" align='center'>
	<tr><th colspan="4" style="background:#FAAC58"><b><font size='2' >ADD LOCATION</font></b></th></tr>
	<tr><td colspan="4"></td></tr>
	<tr id='COUNTRY_CITY_TR'>
			<td colspan="4" align='left'><span class="containerBlock1"><b>Add Country</b><font color="brown"><i> (Select a Function and add new Country and City )</i></font></span></td>
			</tr>
			<tr>
				<td class="labelAdmin">
					<span class="red_text">* </span>Function
				</td>
				<td width='30%' align='left'>
					<select id="function" name="function" class="myTextInputForSelect">
						<option selected="selected" value="0">--Please Select--</option>
								<c:forEach items="${functionList}" var="functionLst">
											<option value="${functionLst.CATEGORY_ID}" >${functionLst.NAME}</option>
								</c:forEach>
					</select>				
				</td>
			</tr>
			<tr>
				<td class="labelAdmin">
					<span class="red_text">* </span>Country
				</td>
				<td width='30%' align='left'>
					 	<select  id="country" name="country" class="myTextInputForSelect" >
							<option selected="selected" value="0" label="--Please Select--">
							</option>																					
						</select>		
						
						
						<span><a href="#" onclick="showCountryDiv('newCountryTR')" ><font color='brown'><b><i>Add New Country</i></b></font></a></span>
						
				</td>
			</tr>
			
			<tr id="newCountryTR">
				<td class="labelAdmin">
					<span class="red_text"></span>New Country</td>
				<td width='50%' align='left'> <input type="text" id="newCountry" size='47%' maxlength="30" onkeypress="return check(event)"></input></td>	
			</tr>
			<tr>
				<td class="labelAdmin">
					<span class="red_text">* </span>City
				</td>
				<td width='50%' align='left'> <input type="text" id="city" size='47%' onfocus="validateCity()" maxlength="50" onkeypress="return check(event)" ></input></td>	
			</tr>
			<tr id="BUILD_TR_1" style="display: none;">		
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add  building details</b><span class="red_text">* </span><font color="brown"><i>(Enter Building/Floor/ODC detail)</i></font></span></td>				
			</tr>			
			<tr id="BUILD_TR_2" style="display: none;">
				<td colspan="4"><table border="0" cellspacing="0" cellpadding="0" id="buildingDetTable" width="75%" align = "center" class = "myDataTable"></table></td>
			</tr>

			<tr id='OP_TIME_TR_1' style="display: none;">			
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add Operating Window</b><span class="red_text">* </span><font color="brown"><i>(Enter Operating Time Detail for new location)</i></font></span></td>
			</tr>
			<tr id='OP_TIME_TR_2' style="display: none;">
				<td colspan="4"><table border="0" cellspacing="0" cellpadding="0" id="operatingTimeDetTable" width="75%" align = "center" class = "myDataTable"></table></td>
			</tr>
			
			<tr id='OP_TIME_TR_HOL_1' style="display: none;">			
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add Holiday Exclusion Dates</b><span class="red_text">* </span><font color="brown"><i>(Select holiday list for the location.Maximum number of dates to be selected is 15)</i></font></span></td>
			</tr>
			<tr id='OP_TIME_TR_HOL_2' style="display: none;">
				<td colspan="4"><table border="0" cellspacing="0" cellpadding="0" id="operatingTimeHolidayTable" width="75%" align = "center" class = "myDataTable"></table></td>
			</tr>

			<tr id='APPRVR_MATRIX_TR_1' style="display: none;">
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add Approver Matrix</b><span class="red_text">* </span><font color="brown"><i> (Enter Employee Id and Role to be added as approver)</i></font></span></td>
			</tr>
			<tr id='APPRVR_MATRIX_TR_2' style="display: none;">
				<td colspan="4"><table border="0" cellspacing="0" cellpadding="0" id="approverDetTable" width="75%" align = "center" class = "myDataTable"></table></td>
			</tr>
			<tr id="submitButtonFirstTR">
				<td colspan="4" align='center'><input type="button" id="submitButton" value="Submit" onclick="validateRequestStep1()"></input></td>
			</tr>
			<tr>
			<td><input type="hidden" id="slaId" value=""></input></td>
			<td><input type="hidden" id="locationId" value=""></input></td>		
			</tr>
	</table>
</div>

<div id="adminTableStep2" style="display: none;">

	<table  border="0" cellspacing="0" cellpadding="2" id="STEP2_TABLE" width="70%"  class = "createTable" align='center'>
		<tr>
				<th colspan="4" style="background:#FAAC58"><b><font size='2' >MAP GROUP</font></b></th>
		</tr>
		<tr>
			<td colspan="4" align='right'><input type="button" id="submitButton_groupDetail" value="Update group" onclick="validateRequest_groupDetail()"></input></td>
		</tr>		
		<tr><td></td></tr>
		<tr>
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add default group detail</b><font color="brown"><i> (Select groups to map to location )</i></font></span></td>				
		</tr>
		<tr>
				<td><div id="GRP_DET_JMESA"><input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
				</div></td>
		</tr>
		<tr><td><br></br></td></tr>
		<tr>
				<td colspan="4" align='left'><span class="containerBlock1"><b>Add department group detail</b><font color="brown"><i> (Select groups to map to location for departments )</i></font></span></td>				
		</tr>		
		<tr>
				<td><div id="DEPT_GRP_DET_JMESA"></div></td>
		</tr>
		<tr><td></td></tr>
		<tr>
			<td colspan="4" align='center'><input type="button" id="submitButton_groupDetail" value="Update group" onclick="validateRequest_groupDetail()"></input></td>
		</tr>		
		
	</table>
</div>	

<div id="adminTableStep3" style="display: none;">
</div>

<div id="output1" style="display: none"></div>
<script type="text/javascript">
var isValidCCC=1;
var isValidTime=1;
//Array for Approver maximum rows-IT Funcn
var maxIDArr=new Array();
//To check or uncheck the groups on Pagination of Group Det table
var group_DetailArr=new Array();
var group_CheckboxArr=new Array();
var group_SubCategoryArr=new Array();

var group_DeptArr=new Array();
var group_CheckboxDeptArr=new Array();
var group_SubCategoryDeptArr=new Array();
var group_DeptAllArr=new Array();
//Array for Building maximum rows-IT/Admin Funcn
var maxIDArrBuilding=new Array();
//Json vars to Insert First Step Details
var finalJson="{";
var finalJsonStep2="{";
//Array for Approver ID list and Approver Name list for IT function
var approverIDOptionsHTML='<%=html_approver%>';
//Select all checkbox value for Grp Detail
var isCheckedAll=0;
var isCheckedAllDept=0;
var hourDiffZeroCount=0;
var allDIVArray=['BUILD_TR','BUILD_TR_2','OP_TIME_TR_1','OP_TIME_TR_2','OP_TIME_TR_HOL_1','OP_TIME_TR_HOL_2','APPRVR_MATRIX_TR_1','APPRVR_MATRIX_TR_2','STEP2_TABLE'];


//Validate and update group mapping
var isValidGroup=1;

var grpJson_GroupFunctionMapping="{";
var def_grpJson_GroupFunctionMapping="{";

var grpDeptJson_GroupFunctionMapping="{";
var defDept_grpJson_GroupFunctionMapping="{";

var page_no_dept = 1;
var page_no_grp = 1;

var isValidReq=0;
$(document).ready(function() {		
	parent.unblockUI();
	isValidCCC=1;
	isValidTime=1;
	
	//On change of Function
	$("#function").change(function(){
		if($("#TextFieldSpanfunction").length>0){			
			$("#TextFieldSpanfunction").remove();
		}

		var functionId = $(this).val();

		//Get Country List
		$.getJSON('getCountry.htm',{functionId : functionId},function(data) {
			var functionId = $(this).val();	
			if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data.message);
			}
			else {					
				var countryListOptions = [];
				countryListOptions.push('<option value="0">--Please Select--</option>');
				for(var i=0;i<data.length;i++){
					countryListOptions.push('<option value="'+data[i].COUNTRY+ '">'+ data[i].COUNTRY+ '</option>');
				}				
			$('#country').html(countryListOptions.join(''));
			}
		});	

		//Display For All Functions
		getOperatingTimeTable('functionChange');

		getOperatingHolidayTable('functionChange');

		//Display Divs as per Functions
		
		if($('#function option:selected').text()=='IT Infrastructure Management'){
			displayTableForApproverMatrix('functionChange');
			getBuildingRowForITOrISorAdmin($('#function option:selected').text(),'functionChange');
			$('#BUILD_TR_1').show();
			$('#BUILD_TR_2').show();
			$('#OP_TIME_TR_1').show();
			$('#OP_TIME_TR_2').show();	
			$('#OP_TIME_TR_HOL_1').show();
			$('#OP_TIME_TR_HOL_2').show();			
			$('#APPRVR_MATRIX_TR_1').show();
			$('#APPRVR_MATRIX_TR_2').show();		
			
		}else
		if($('#function option:selected').text()=='Admin'){
			getBuildingRowForITOrISorAdmin($('#function option:selected').text(),'functionChange');
			$('#BUILD_TR_1').show();
			$('#OP_TIME_TR_1').show();			
			$('#BUILD_TR_2').show();
			$('#OP_TIME_TR_2').show();			
			$('#OP_TIME_TR_HOL_1').show();
			$('#OP_TIME_TR_HOL_2').show();	
			
			$('#APPRVR_MATRIX_TR_1').hide();			
			$('#APPRVR_MATRIX_TR_2').hide();			
		}else if($('#function option:selected').text()=='HR'){			
			$('#OP_TIME_TR_1').show();			
			$('#OP_TIME_TR_2').show();		
			$('#OP_TIME_TR_HOL_1').show();
			$('#OP_TIME_TR_HOL_2').show();	
					
			$('#BUILD_TR_1').hide();			
			$('#BUILD_TR_2').hide();
			$('#APPRVR_MATRIX_TR_1').hide();			
			$('#APPRVR_MATRIX_TR_2').hide();
		}else if($('#function option:selected').text()=='Delivery' || $('#function option:selected').text()=='Finance' || $('#function option:selected').text()=='IGATE Corporate University' ||$('#function option:selected').text()=='Quality'){////TODO 10/16/2014 Nisha			
			$('#OP_TIME_TR_1').show();				
			$('#OP_TIME_TR_2').show();		
			$('#OP_TIME_TR_HOL_1').show();
			$('#OP_TIME_TR_HOL_2').show();	
						
			$('#BUILD_TR_1').hide();	
			$('#BUILD_TR_2').hide();
			$('#APPRVR_MATRIX_TR_1').hide();						
			$('#APPRVR_MATRIX_TR_2').hide();				
		}else{			
			$('#OP_TIME_TR_1').show();
			$('#OP_TIME_TR_2').show();		
			$('#OP_TIME_TR_HOL_1').show();
			$('#OP_TIME_TR_HOL_2').show();		
			
			$('#BUILD_TR_1').hide();
			$('#BUILD_TR_2').hide();			
			$('#APPRVR_MATRIX_TR_2').hide();		
			$('#APPRVR_MATRIX_TR_1').hide();
		}
		
		//Reset All the Values
		$("#country").val('');
		$("#newCountry").val('');
		$("#city").val('');
	});

	//On Country Change
	$("#country").change(function(){
		if($("#TextFieldSpancountry").length>0){			
			$("#TextFieldSpancountry").remove();
		}
		$("#city").val('');
	});
	
	$("#newCountryTR").hide();

		
});
function getAdminTableStep1(){
	$("#adminTableStep1").attr('style','display:block');
	$("#adminTableStep2").hide();
	$("#adminTableStep3").hide();
}
function getAdminTableStep2(){
	if(isValidReq==1){
		$("#adminTableStep2").attr('style','display:block');
		$("#adminTableStep3").hide();
		$("#adminTableStep1").hide();
	}else{
		parent.parent.jbarOnFailure("Please create a new Location before navigating to Step-2 !!!");
		isValidReq=0;
	}
}
function getAdminTableStep3(){
	$("#adminTableStep3").attr('style','display:block');
	$("#adminTableStep1").hide();
	$("#adminTableStep2").hide();
}

function onInvokeExportAction(id) {		
	 var parameterString = createParameterStringForLimit(id);					
} 
function onInvokeAction(id) {	
	    setExportToLimit(id, '');
	    var functionId = $('#function').val();
	    var finalString = "functionId="+ functionId + "&";
	    var parameterStringxx = createParameterStringForLimit(id);
	    var parameterString = finalString + parameterStringxx;	    
	    
	    $.ajaxSetup({ cache: false });
	 	if(id=="Group_Detail"){   
	    $.get('ADMIN_Console_GrpListDetail.htm?' + parameterString, function (data) {
	    	page_no_grp = jQuery.jmesa.getTableFacade(id).limit.page;
	    	
	    	 $("#GRP_DET_JMESA_TR").removeClass();
			 $("#GRP_DET_JMESA").removeClass();        
	         $("#GRP_DET_JMESA").html("");      
	         $("#GRP_DET_JMESA").html(data);	 
	         resetGroupAndCheckBoxValuesOnPagination(group_DetailArr,group_CheckboxArr,"Group_Detail");       
	       
	    });    
	 }else if(id=="Group_Detail_Dept"){
		 $.get('ADMIN_Console_Dept_GrpListDetail.htm?' + parameterString, function (data) {
			 page_no_dept = jQuery.jmesa.getTableFacade(id).limit.page;
			 $("#DEPT_GRP_DET_JMESA_TR").removeClass();
			 $("#DEPT_GRP_DET_JMESA").removeClass();        
	         $("#DEPT_GRP_DET_JMESA").html("");      
	         $("#DEPT_GRP_DET_JMESA").html(data);
	         resetGroupAndCheckBoxValuesOnPagination(group_DeptArr,group_CheckboxDeptArr,"Group_Detail_Dept");   
	     
		 });
		 }
	  
	}

(function($,global,undefined){
    $.removeElementFromCollection = function(collection,key){
    
        // if the collections is an array
        if(collection instanceof Array)            {
                // use jquery's `inArray` method because ie8 
                // doesn't support the `indexOf` method
                if($.inArray(key,collection) != -1)
                    collection.splice($.inArray(key,collection),1);
            }
        // it's an object
        else if(collection.hasOwnProperty(key))
            delete collection.key;

        return collection;
    };
})(jQuery,window); 
</script>
	<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>