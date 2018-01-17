<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/view/UNIVERSAL_Include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/jmesa.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/example.css"></link>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/custom_screen.css" media="screen, projection"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/print.css" media="print"/><!--	
--><!--[if lt IE 8]><link  type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/ie.css" media="screen, projection"/><![endif]-->
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/bp/fancy-type/screen.css" media="screen, projection"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/colorbox.css" media = "screen"/>
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/common.css" /> 
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" /> 
<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.multiselect.css" /> 	
<title>IConnect : Unified Service Desk for Life & Health</title>	
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body class="bodyContainer">
<% String token = null; %>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.minCANT4BS2.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jmesa.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.validate.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.ui.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-ui.min.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/ui.multiselect.js"></script> 
<script type="text/javascript" src="<%=jsDirPath%>/iConnectSecurity.js"></script>
<form:form modelAttribute="UserAvailability" name="userLogInLogOutForm" action="HELPDESK_GroupMembersAvailability.htm" >
<div id="fromToDateDiv">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class = "createTable" >
<tr>
<td colspan="6"><span class="containerBlock1">Select a Date Range</span></td>
</tr>
<tr>
<td align="center"><b>From Date</b></td>
<td><form:input type="text" id="fromDate"  class="myTextInput" readonly="true"   path="FROM_DATE" ></form:input>
<a href="#" onclick="clearDateField('fromDate')"><img src="images/clear.png" alt="Clear From Date" title="Clear From Date"/>
			</a></td>

<td   align="center"><b>To Date</b></td>
<td><form:input type="text" id="toDate" class="myTextInput" readonly="true"   path="TO_DATE" ></form:input>
<a href="#" onclick="clearDateField('toDate')"><img src="images/clear.png" alt="Clear To Date" title="Clear To Date"/>
			</a></td>
<td align="left"><input type="button" id="Go" value="Go" onclick="isValidateDate()" ></td>
<td ><form:input type="button" value="Go Back To List" id="goToList" onclick="goBackToList()" path=""></form:input></td>	

</tr>
</table>


</div>



<div>${paginationDat}</div>
<input type="hidden" id="employeeId" name="employeeId" id="employeeId" value="<%=(String)request.getParameter("employeeId")%>" ></input>
<form:input type="hidden" path="EMPLOYEE_NAME"/>
</form:form>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
<script type="text/javascript">		
         <!-- highlight active example based on the parameter passed from request -->
         $(document).ready(function(){
           
        	 $('#fromDate').datepicker({changeYear: true, changeMonth: true, maxDate: 0}).show();
        	 $('#toDate').datepicker({changeYear: true, changeMonth: true,maxDate: 0}).show();
            $("#btn-" + "<%=request.getParameter("type")%>").removeClass("normal");
            $("#btn-" + "<%=request.getParameter("type")%>").addClass("active");          
            $.ajaxSetup({ cache: false });
            $('#ui-datepicker-div').hide();
         });

		 function onInvokeAction(id) {
			    $.jmesa.setExportToLimit(id, '');
			    $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
		 function onInvokeExportAction(id) {
				
			 var parameterString = createParameterStringForLimit(id);					
			 var frmDate=$("#fromDate").val();
			 var tDate=$("#toDate").val();
			 var parameterStringDate='&frmDate='+frmDate+'&tDate='+tDate;
			location.href = '${pageContext.request.contextPath}/HELPDESK_GroupMembersAvailability.htm?'+
								parameterString+'&employeeId='+$("#employeeId").val()+parameterStringDate;
			}  

		 function clearDateField(dateId){
             $('#'+dateId).val('');
         }

         function goBackToList()
         {
             var url="UNIVERSAL_ListPage.htm?menuId=99&parentMenuId=1&menuName=LoggedIn Members&parentMenuName=Helpdesk";
            
             document.location.href = url;
         }
         function isValidateDate(){
             var frmDt=$("#fromDate").val();
             var toDt=$("#toDate").val();
             if(frmDt.length==0 && toDt.length==0){
            	 alert('Please select From Date and To Date Field');
             }else if(frmDt.length!=0 && toDt.length==0){
                 alert('Please select To Date Field');
             }else if(frmDt.length==0 && toDt.length!=0){
                 alert('Please select From Date Field');
             }else if(compareDates(frmDt,toDt)==true){
                 alert('End Date Should be Greater than Start Date!!');
             }
             else {                
                 $("#UserAvailability").submit();
             }
         }
         function compareDates(dt1,dt2){	
            var dateGraterFlg = false;
            var dateLesserFlg = false;
            var arr1 = dt1.split('/');
            var arr2 = dt2.split('/');
            var year1;                
            var year2;               
     		var timeArr1 = arr1[2].split(' ');
     		var timeArr2 = arr2[2].split(' ');
     		year1 = timeArr1[0]; 
     		year2 = timeArr2[0];
    		var compareDt1 = new Date( parseInt(year1,10),parseInt(arr1[1]-1,10),parseInt(arr1[0],10),0,0,0);          
            var compareDt2 =new Date( parseInt(year2,10),parseInt(arr2[1]-1,10),parseInt(arr2[0],10),0,0,0);			
            if(parseInt(Date.parse(compareDt1.toString())) > parseInt(Date.parse(compareDt2.toString()))){    							
               return true;
            }else if(parseInt(Date.parse(compareDt1.toString())) <= parseInt(Date.parse(compareDt2.toString()))){							
               return false;
            } else {                                   
               return true;
            }					
    	 }   
         
</script>

 
</body>

</html>