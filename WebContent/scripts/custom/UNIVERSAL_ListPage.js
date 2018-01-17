function isCheckBoxSelected() {
	var selected = false;
	var n = $("input:checked").length; 
	if(n!=0)
		selected = true;
	
	return selected;
}

function confirmAssignment() {
	if (!isCheckBoxSelected())
		parent.jbarOnFailure("Please select ticket to be assigned ");

}
function checkEngineerSelection(){
	if($('#groupMemberId').val()=="Select Group"){
		return false;
	}
	else
		return true;
}
function checkGroupSelection(){
	if($('#groupListId').val()=="Select"){
		return false;
	}
	else
		return true;
}

function updateAssignment() {
	if (!isCheckBoxSelected())
		parent.jbarOnFailure("Please select ticket to be assigned ");
	else if (!checkEngineerSelection())
	{
		parent.jbarOnFailure("Please select engineer name to be assigned ");
	}
	else if (!checkGroupSelection())
	{
		parent.jbarOnFailure("Please select group name to be assigned ");
	}
	else {
		var selectedTicketArray = "";
		var newAssignementNameWithID = $('#groupMemberId').val();
		var groupId = $("#groupListId").val();
		var n = $("input:checked").length; 
		$(":checked").each(function()
			{
			selectedTicketArray = selectedTicketArray +","+$(this).val();
		});

		
		$.getJSON('assignSave.htm', {
			selectedTicketArray : selectedTicketArray,
			newAssignementNameWithID : newAssignementNameWithID,groupId : groupId
		}, function(data) {

			parent.jbarOnSuccess(data);
		});

	}

} 
function getNextStateForApproval(ticketId,currentState) {
	$.getJSON('updateNextValidApprovalState.htm', {ticketId:ticketId,currentState:currentState
	}, function(data) {
		parent.jbarOnSuccess(data);
	});
} 


function approvereject(TicketId,Status,Action,functionname,subcategory,priority,location,locationdetid,functionId){ 	
	var toShow = "Approve";
	if(Action == "Rejected"){
		toShow = "Reject";
	}else if(Action == "Need More Info"){
		toShow = "Missing Info Comments";
	}else if(Action=="Send to Helpdesk"){
		toShow = "Change of Category/Subcategory Comments";
	}
	var html = "<table width = '100%'  border='0' cellspacing='0' cellpadding='0' class='modalTable'><tr><th><B>"+toShow+" Ticket # ("+TicketId+")</B></th></tr></table><table width=80% align='middle'><tr><td class='label'><b>Comments:&nbsp;&nbsp;</b></td><td><textarea id='APPREJ_COMMENTS' maxlength=1000  rows=4 cols=40 ></textarea></td></tr>";
		html +="<tr id='exp_approval'><td class='label'>Exceptional Approval</td><td align='left'><input type='checkbox' name='EXCEPTIONAL_APPROVAL' id='EXCEPTIONAL_APPROVAL'/></td></tr>";
		html +="<tr id='start_dt'><td class='label'><b>Exception Start Date:&nbsp;&nbsp;</b></td><td><input type='text' readonly='true' id='EXCEPTION_START_DATE' name='EXCEPTION_START_DATE' onKeyDown='return onKeyDown()'/></td></tr><tr id='end_dt'><td class='label'><b>Exception End Date:&nbsp;&nbsp;</b></td><td><input type='text' readonly='true' id='EXCEPTION_END_DATE' name='EXCEPTION_END_DATE' onKeyDown='return onKeyDown()'/></td></tr>";
		html += "<tr id='specialcharmsg' style='display:none;align:center'><td colspan=2><font color='red'>&, <, >, \",=, \\u and ~ characters are not allowed</font></td></tr><tr><td colspan=2><input type='button' id=saveButton value=Save onClick='saveApprorRejectComments(\""+TicketId+"\",\""+Status+"\",\""+Action+"\",\""+functionname+"\",\""+subcategory+"\",\""+priority+"\",\""+location+"\",\""+locationdetid+"\",\""+functionId+"\")'></td></tr></table>";
		
	$(".ApproveAuditLog").colorbox({width:"65%", height:"95%",html:html,onComplete : function() { 
							$("#start_dt").hide();
							$("#end_dt").hide();							
							if((role_exception_approval!="IT Security")||(Action == "Rejected") || (Action == "Need More Info") || (Action=="Send to Helpdesk"))
									$("#exp_approval").hide();
							$('#EXCEPTION_START_DATE').attr("disabled",true);	
							$('#EXCEPTION_END_DATE').attr("disabled",true);	
							$('#EXCEPTION_START_DATE').addClass('index_datepicker'); 
							$('#EXCEPTION_END_DATE').addClass('index_datepicker'); 
							$('#EXCEPTION_START_DATE').datepicker({
								beforeShow: customRange
							});	
							$('#EXCEPTION_END_DATE').datepicker({
								 beforeShow: customRangeEnddt
							})
						}					
	});
	

	$('#APPREJ_COMMENTS').live('keyup blur', function() {
	    // Store the maxlength and value of the field.
	    var maxlength = $(this).attr('maxlength');
	    var val = $(this).val();

	    // Trim the field if it has content over the maxlength.
	    if (val.length > maxlength) {
	        $(this).val(val.slice(0, maxlength));
	    }
	});
	
	$("#EXCEPTIONAL_APPROVAL").live('click',function() {	
		if($("#EXCEPTIONAL_APPROVAL").is(':checked')){				
			$("#start_dt").show();
			$("#end_dt").show();
			$('#EXCEPTION_START_DATE').attr("disabled",false);									
		}else{
			$("#start_dt").hide();
			$("#end_dt").hide();
		}
	});
} 
function saveApprorRejectComments(TicketId,Status,Action,functionname,subcategory,priority,location,locationdetid,functionId) {
	if(checkspecialcharacters($("#APPREJ_COMMENTS").val())){
		$("#specialcharmsg").show();
	}
	else if((Action == "Rejected" || Action == "Need More Info") && ($("#APPREJ_COMMENTS").val()=="")){
		$("#specialcharmsg").hide();
		alert('Please enter the comments');
	}else if( $.trim($("#APPREJ_COMMENTS").val()).length==0 ){
		$("#specialcharmsg").hide();
		alert('Please enter the comments');
	}
	else if(($("#EXCEPTIONAL_APPROVAL").is(':checked')) && ($("#EXCEPTION_START_DATE").val()=="")){				
			alert('Please select the start date for Exceptional Approval');
	}else if(($("#EXCEPTIONAL_APPROVAL").is(':checked')) && ($("#EXCEPTION_END_DATE").val()=="")){				
			alert('Please select the end date for Exceptional Approval');
	}else{		
		$("#saveButton").attr('disabled','true');
		var jsonobj;
		if($("#EXCEPTIONAL_APPROVAL").is(':checked')){
			jsonobj = '{"TICKET_ID":"'+TicketId+'","WORKFLOW_STATE":"'+Status+'","ACTION":"'+Action+'","FUNCTION":"'+functionname+'","SUB_CATEGORY_ID":"'+subcategory+'","PRIORITY_ID":"'+priority+'","LOCATION_ID":"'+location+'","LOC_DET_ID":"'+locationdetid+'","FUNCTION_ID":"'+functionId+'","COMMENTS":"'+$("#APPREJ_COMMENTS").val()+'","IS_EXCEPTIONAL_APPROVAL":"'+'Y'+'","EXCEPTION_START_DATE":"'+$("#EXCEPTION_START_DATE").val()+'","EXCEPTION_END_DATE":"'+$("#EXCEPTION_END_DATE").val()+'"}';
		}else{
			jsonobj = '{"TICKET_ID":"'+TicketId+'","WORKFLOW_STATE":"'+Status+'","ACTION":"'+Action+'","FUNCTION":"'+functionname+'","SUB_CATEGORY_ID":"'+subcategory+'","PRIORITY_ID":"'+priority+'","LOCATION_ID":"'+location+'","LOC_DET_ID":"'+locationdetid+'","FUNCTION_ID":"'+functionId+'","COMMENTS":"'+$("#APPREJ_COMMENTS").val()+'","IS_EXCEPTIONAL_APPROVAL":"'+'N'+'","EXCEPTION_START_DATE":"'+''+'","EXCEPTION_END_DATE":"'+''+'"}';
		}		
		$.getJSON('updateApproveRejectForTicket.htm', {json:jsonobj}, function(data) {
			$.colorbox.close();
			if(data=="Success"){
				parent.jbarOnSuccess(data);
				window.location.reload(true);
			}else{
				parent.jbarOnFailure(data.message);
			}
			
		});
	}
}
function multiTicketUpdateApproval() {

	var jsonarray = "";
	var jsonobjs = "";
	var ticketIds = new Array();
	var i=0;
	var ticketidIndex="";
	var workflowIndex="";
	var functionIndex="";
	var j=0;
	$(".jmesa").find(".header").find("td").each(function(j){
		if($(this).text()=="Function"){
			functionIndex=j;
		}
		if($(this).text()=="Ticket #"){
			ticketidIndex=j;
		}
		if($(this).text()=="Status"){
			workflowIndex=j;
		}
	});
	
	
	$(".jmesa").find("table tbody").find("input:checked").each(function(){
		
		var obj =  $(this).closest("tr").find("td");
		var ticketID =obj.eq(ticketidIndex).text();
		var function_name =  obj.eq(functionIndex).text();
		var workflow_status = obj.eq(workflowIndex).text();
		
		var actionhtml=$(this).val();

		var splitArray = actionhtml.split(",");
		var subcategory=splitArray[1];
		var priority=splitArray[2];
		var location=splitArray[3];
		var locationdetid=splitArray[4];
		var functionId=splitArray[5];
		ticketIds[i] = ticketID;
		i++;
		jsonobjs+='{"TICKET_ID":"'+ticketID+'","WORKFLOW_STATE":"'+workflow_status+'","ACTION":"Approved","FUNCTION":"'+function_name+'","SUB_CATEGORY_ID":"'+subcategory+'","PRIORITY_ID":"'+priority+'","LOCATION_ID":"'+location+'","LOC_DET_ID":"'+locationdetid+'","FUNCTION_ID":"'+functionId+'","COMMENTS":"'+'","IS_EXCEPTIONAL_APPROVAL":"'+'N'+'","EXCEPTION_START_DATE":"'+''+'","EXCEPTION_END_DATE":"'+''+'"},';		
	});
	if(jsonobjs.length>0){
		jsonarray ='{"JSONARRAY":['+jsonobjs.substring(0,jsonobjs.length-1)+']}';		
		$.getJSON('approveTickets.htm', {json:jsonarray}, function(data) {
			if(data=="Success"){
				parent.jbarOnSuccess(data);
				$.each(ticketIds, function(index, value) { 
					window.location.reload(true);
			    });
			}else{
				parent.jbarOnFailure(data.message);
			}
		});
	}else{
		parent.jbarOnFailure('Please select any ticket to approve')
		}
} 




function getProjectsForEmployee(){
	var EmpId = "";
	var IsValidEmpId=0;
	var helpdeskData="";
	
	if(!IsValidEmpId){
		EmpId = LoginID;
	}
	$.ajaxSetup({ cache: false });
	$.getJSON('getProjectDetails.htm',{employeeid:EmpId},function(projects){
			
		for(var i=0;i<projects.length;i++){
		
		 if(projects[i].ROLE_ID=='11')
		 {
			//$('#SecurityDelegateTo :input').val(projects[i].DELEGATE_TO); 
			$('#SecurityDelegatedBy').text(projects[i].EMPLOYEE_ID);
			$('#SecurityDelegateTo').text(projects[i].DELEGATE_TO);
		//	$('#SecurityStartDate').text(projects[i].START_DATE); 
			//$('#SecurityEndDate').text(projects[i].END_DATE); 
			$('#SecurityDelegateTo').val(projects[i].DELEGATE_TO_EMPID);
			
			if(projects[i].DELEGATE_TO.indexOf(EmpId)!=-1 && $('#SecurityDelegateTo').text()!="")
			{
			$('#SecurityEdit').hide();
			$('#SecurityCancel').hide();
			$('#SecurityRemove').hide();
			}else
			{
				$('#SecurityRemove').show();
				$('#SecurityEdit').hide();
			}
		 }
		 
		 if(projects[i].ROLE_ID=='9'){
			$('#TechcrDelegatedBy').text(projects[i].EMPLOYEE_ID); 
			$('#TechcrDelegateTo').text(projects[i].DELEGATE_TO); 
		//	$('#TechcrStartDate').text(projects[i].START_DATE); 
		//	$('#TechcrEndDate').text(projects[i].END_DATE); 
			$('#TechcrDelegateTo').val(projects[i].DELEGATE_TO_EMPID);
	
			if(projects[i].DELEGATE_TO.indexOf(EmpId)!=-1 && $('#TechcrDelegateTo').text()!="")
			{
			$('#TechcrEdit').hide();
			$('#TechcrCancel').hide();
			$('#TechcrRemove').hide();
			}
	else
			{
				$('#TechcrRemove').show();
				$('#TechcrEdit').hide();
			}
			
		}
		if(projects[i].ROLE_ID=='10'){
			$('#ITheadDelegatedBy').text(projects[i].EMPLOYEE_ID); 
			$('#ITheadDelegateTo').text(projects[i].DELEGATE_TO); 
			//$('#ITheadStartDate').text(projects[i].START_DATE); 
			//$('#ITheadEndDate').text(projects[i].END_DATE); 
			$('#ITheadDelegateTo').val(projects[i].DELEGATE_TO_EMPID);
			
			if(projects[i].DELEGATE_TO.indexOf(EmpId)!=-1 && $('#ITheadDelegateTo').text()!="")
			{
			$('#ITheadEdit').hide();
			$('#ITheadCancel').hide();
			$('#ITheadRemove').hide();
			}
		else
			{
				$('#ITheadRemove').show();
				$('#ITheadEdit').hide();
			}
		}
		if(projects[i].ROLE_ID=='24'){
			$('#SlmDelegatedBy').text(projects[i].EMPLOYEE_ID); 
			$('#SlmDelegateTo').text(projects[i].DELEGATE_TO); 
		//	$('#SlmStartDate').text(projects[i].START_DATE); 
		//	$('#SlmEndDate').text(projects[i].END_DATE); 
			$('#ITheadDelegateTo').val(projects[i].DELEGATE_TO_EMPID);
			
			if(projects[i].DELEGATE_TO.indexOf(EmpId)!=-1 && $('#SlmDelegateTo').text()!="")
			{
			$('#SlmEdit').hide();
			$('#SlmCancel').hide();
			$('#SlmRemove').hide();
			}
			else
			{
				$('#SlmRemove').show();
				$('#SlmEdit').hide();
			}
		}

		
		if(projects[i].ROLE_ID=='6'){
			
			
			var projectStartDate="";
			var projectEndDate="";
			if(projects[i].START_DATE!=null)
				projectStartDate=projects[i].START_DATE;
			if(projects[i].END_DATE!=null)
				projectEndDate=projects[i].START_DATE;

				
			var projectDisplayVal=projects[i].PROJECT_NAME+'('+projects[i].PROJECT_ID+')';
			
			if(projects[i].DELEGATE_TO.indexOf(EmpId)!=-1)
			{
				helpdeskData=helpdeskData+"<tr><td height='10' align='center' valign='middle' class='Empproj_id'><b>"+projectDisplayVal+"</b></td><td height='10' align='center' class='EditDelegated' value = '"+projects[i].DELEGATE_TO+"' valign='middle' id='"+projects[i].PROJECT_ID+"DelegateBy'>"+projects[i].EMPLOYEE_ID+"</td><td height='10' align='center' class='EditDelegated' valign='middle' id='"+projects[i].PROJECT_ID+"DelegateTo'>"+projects[i].DELEGATE_TO+"<input style='display:none' type='text' id='Delegator_Name5' onblur=\"javascript:ValidateEmpId(document.getElementById('Delegator_Name5').value,empResultMessage4)\" disabled value = '"+projects[i].DELEGATE_TO+"'>&nbsp</input></td><td></td></tr>";
				
			}
			else
			{
				helpdeskData=helpdeskData+"<tr><td height='10' align='center' valign='middle' class='Empproj_id'><b>"+projectDisplayVal+"</b></td><td height='10' align='center' class='EditDelegated' valign='middle' id='"+projects[i].PROJECT_ID+"DelegateBy'>"+projects[i].EMPLOYEE_ID+"</td><td height='10' align='center' class='EditDelegated' valign='middle' id='"+projects[i].PROJECT_ID+"DelegateTo'>"+projects[i].DELEGATE_TO+"<input style='display:none' type='text' id='Delegator_Name5' onblur=\"javascript:ValidateEmpId(document.getElementById('Delegator_Name5').value,empResultMessage4)\" disabled value = '"+projects[i].DELEGATE_TO+"'>&nbsp</input></td><td><img src='images/editnode.jpg' id='"+projects[i].PROJECT_ID+"Edit' value='"+projects[i].PROJECT_ID+"' onclick='EditDelegateApprover(\""+projects[i].PROJECT_ID+"\",\"6\")' style='cursor: pointer; cursor: hand;' title=\"Edit\"></img><img id='"+projects[i].PROJECT_ID+"Cancel' src='images/Change_CS.png' name='showInfo' '"+projects[i].PROJECT_ID+"Cancel' title='Cancel' style='cursor: pointer; cursor: hand; display:none;' onClick='cancelDelegateApprovals(\""+projects[i].PROJECT_ID+"\")'/><img src='images/reject.png' name='showInfo' id='"+projects[i].PROJECT_ID+"Remove' title='Remove' onClick='removeDelegateApprovals(\""+projects[i].PROJECT_ID+"\",\"6\")' style='cursor: pointer; cursor: hand; display:none;'/></td></tr>";
					
			}
			
		}
		 }
		
		$('#projectTableID tr:last').after(helpdeskData);
		
		var rows = $("#projectTableID tr:gt(6)"); 
		rows.each(function(index) {
			var tdElem = $("td:nth-child(3)", this);
			var tdLastElem = $("td:nth-child(4)", this);
			var tdId=$(tdElem).attr('id');
			try{
				var editIconName= tdId.replace("DelegateTo","") + "Edit";
				var removeIconName= tdId.replace("DelegateTo","") + "Remove";
				if($.trim($("#"+tdId).text()).length>0){
					
					$("#"+editIconName).hide();
					$("#"+removeIconName).show();
				}else{
					$("#"+editIconName).show();
					$("#"+removeIconName).hide();
				}
			}catch(err){
			}
		});
		
		
		
	}); 
}




$(document).unbind('keydown').bind('keydown', function (event) {
	var doPrevent = false;
	if (event.keyCode === 8) {
	var d = event.srcElement || event.target;
	if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT')) 
	|| d.tagName.toUpperCase() === 'TEXTAREA') {
	doPrevent = d.readOnly || d.disabled;
	}
	else {
	doPrevent = true;
	}
	}
	if (doPrevent) {
	event.preventDefault();
	}
	});






function ValidateEmpId(EmpId,obj){
	var ID = EmpId.replace(/\D/g,'');

	if(LoginID==ID){
	alert("You are delegating approval to your self");
	}
	else
	$.getJSON('validateEmployee.htm',{employeeId:ID},function(Emp){
	if (Emp.status && Emp.status=="Error") { 
	parent.jbarOnFailure(Emp.message);
	}else {
	if(Emp.NAME){
	}else{
	alert("Please enter valid Employee id");
	}
	}
	});

	}



/*function datepicker(Id) {

	$('#'+Id+'StartDateVal').datepicker({

	minDate: 0,

	onSelect: function(selected) {

	$('#'+Id+'EndDateVal').datepicker("option","minDate", selected)

	}

	});

	$('#'+Id+'EndDateVal').datepicker({minDate: 0

	,

	onSelect: function(selected) {

	$('#'+Id+'StartDateVal').datepicker("option","maxDate", selected)

	}

	}); 

	}*/




var saveEdit = [];
var old_jsonobj="[";
function EditDelegateApprover(Id,role){
	
	$('#buttonId').show();
	saveEdit.push(Id);
	$('#'+Id+'Cancel').show();
	$('#'+Id+'Edit').hide();
	$('#'+Id+'Remove').hide();
	
	var dname = $('#'+Id+'DelegateTo').text();
	$('#'+Id+'DelegateTo').html('<input type=\"text\" id=\"'+Id+'DelegateToVal\" size=\"25\" maxlength=\"7\" onblur=\"javascript:ValidateEmpId(document.getElementById(this.id).value)\" value=\"'+ $('#'+Id+'DelegateTo').text()+'\"></input>');
//	$('#'+Id+'StartDate').html('<input type=\"text\" id=\"'+Id+'StartDateVal\" size=\"10\" onclick=\"datepicker(\''+Id+'\')\" class=\"datapicker\" type=\"text\" readonly=\"readonly\" value=\"'+ $('#'+Id+'StartDate').text()+'\"></input>');
	//$('#'+Id+'EndDate').html('<input type=\"text\" id=\"'+Id+'EndDateVal\" size=\"10\" onclick=\"datepicker(\''+Id+'\')\" class=\"datapicker\" type=\"text\" readonly=\"readonly\" value=\"'+ $('#'+Id+'EndDate').text()+'\"></input>');
		old_jsonobj+='["'+Id+'",';
		old_jsonobj+='"'+$('#'+Id+'DelegateToVal').val()+'",';
	//	old_jsonobj+='"'+$('#'+Id+'StartDateVal').val()+'",';
	//	old_jsonobj+='"'+$('#'+Id+'EndDateVal').val()+'"';
		old_jsonobj+='],';

}



// KRUTHI CHANGES - REVOKE DATE MENTIONED BY THE INFOSEC APPROVER: START
function revokeAccess(ticketID,revokeDate){
	var toShow = "Revoke Access Provided";
	var html = "<table width = '100%'  border='0' cellspacing='0' cellpadding='0' class='modalTable'><tr><th><B>"+toShow+" Ticket # ("+ticketID+")</B></th></tr></table><table width=80% align='middle'>";
		html +="<tr id='revoke_dt'><td class='label'><b>Access Revoke Date:&nbsp;&nbsp;</b></td><td><input type='text' readonly='true' id='REVOKE_DATE' name='REVOKE_DATE' onKeyDown='return onKeyDown()'/></td></tr>";
		html += "<tr id='specialcharmsg' style='display:none;align:center'><td colspan=2><font color='red'>&, <, >, \",=, \\u and ~ characters are not allowed</font></td></tr><tr><td colspan=2><input type='button' id=saveButton value=Save onClick='saveRevokeDate(\""+ticketID+"\")'></td></tr></table>";
		
	$(".checkRevokeDate").colorbox({width:"35%", height:"45%",html:html,onComplete : function() { 
							$('#REVOKE_DATE').addClass('index_datepicker'); 
							$('#REVOKE_DATE').datepicker({
								beforeShow: customRange
							});
						}					
	});
}

function saveRevokeDate(ticketId) { 
	
	if($("#REVOKE_DATE").val()==""){
		alert('Please enter the date');
	}else{
		var revokeDate=$("#REVOKE_DATE").val();
		$("#saveId").attr('disabled','true');
		$.getJSON('updateRevokeDate.htm', {ticketId:ticketId,revokeDate:revokeDate}, function(data) {
			$.colorbox.close();
			if(data=="Success"){
				parent.jbarOnSuccess(data);
				window.location.reload(true);
			}else{
				parent.jbarOnFailure(data)
			}
			
		});
	}
}
//KRUTHI CHANGES - REVOKE DATE MENTIONED BY THE INFOSEC APPROVER: END

function unlockTheTicket(ticketId,menuId,obj){
	
	if(confirm("Are you sure you want to unlock the Ticket ?")){
		var jsonobj = '{"JSONARRAY":['+'{"TICKET_ID":"'+ticketId+'","MENU_ID":"'+menuId+'"}'+']}';
		
		$.getJSON('unlockTickets.htm', {json:jsonobj}, function(data) {
			if(data.status=="Success"){
				$(obj).closest("td").closest("tr").remove();
				parent.jbarOnSuccess(data.status);
			}else if(data.status=="Error"){
				parent.jbarOnFailure(data.message)
			}
			
		});

	}
}

function convertMailtoHelpdesk(mailID,mailTrackerType){
		// parent.displaySubMenu('1');
	
	var json =getJSONtoLockTicket(mailID);
	$.postJSON("lockMail.htm",{jsonString:json},function(data){
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}else{
		if ((data.status && data.status=="success" && data.length != 0) || (data.LOCKED_BY==userid)) {
			window.location.href="convertMailtoHelpdesk.htm?mailid="+mailID+"&mailTrackerType="+mailTrackerType;
		}else if(data.status=="error"){
			parent.jbarOnFailure("This Mail(#"+mailID+") is locked by "+data.NAME+"( "+data.LOCKED_BY+" )");
			}
		}
	});
		
}
function discardMail(mailID,obj,mailtrackertype){
		$.getJSON('DiscardMail.htm',{mailid:mailID,mailtrackerType:mailtrackertype},function(response){
			if(response=="SUCCESS"){
				$(obj).closest("td").closest("tr").remove();
				parent.jbarOnSuccess('Discarded Successfully.')
			}
		});
}
function getJSONtoLockTicket(mailID){

	var jsonString = '{';
		jsonString +='"MAIL_ID":"'+mailID+'","DATA":"';
	var xmldata = "<data></data>";
    jsonString = jsonString+xmldata+'","MENU_ID":"4"}';	
	return jsonString;
	
}

function convertMailtoTechCR(mailID){
	window.location.href='techCRNOCAlerts.htm?MailID='+mailID+'';
}

function discardTechCRMail(mailID,obj){
		$.getJSON('DiscardNocAlerts.htm',{MailID:mailID},function(response){			
			if(response=="SUCCESS"){
				$(obj).closest("td").closest("tr").remove();
				parent.jbarOnSuccess('Discarded Successfully.')
			}
		});
} 

function displayMailDetail(mailid,prmMail){
	var html =$("#"+mailid+"_Html").html(); 
	if(prmMail=="1"){
		html = "<table width = '100%'  border='0' cellspacing='0' cellpadding='0' class='modalTable'><tr><th><B>Mail Id # ("+mailid+") </B>&nbsp;<img src='images/Premium.gif'/></th></tr></table><textarea rows='20' cols='20' readonly>"+html+"</textarea>";
	}else{
		html = "<table width = '100%'  border='0' cellspacing='0' cellpadding='0' class='modalTable'><tr><th><B>Mail Id # ("+mailid+") </B></th></tr></table><textarea rows='20' cols='20' readonly>"+html+"</textarea>";
	}
	$(".ApproveAuditLog").colorbox({width:"70%", height:"70%",html:html});
} 

function displayMailEML(mailid,attachmentName,attachmentPath){
	
	var json = '{"ATTACHMENT_PATH":"'+attachmentPath.replace(/#/g,"*hash*")+'","ATTACHMENT_NAME":"'+attachmentName.replace(/#/g,"*hash*")+'"}';
	json = json.replace(/&/g,"*amper*");
	if(name.indexOf(".htm")!= -1)
	{
	var rndVal;
	var wOpen;
	var sOptions;

	sOptions = 'scrollbars=yes,toolbar=no,status=no,menubar=no';
    sOptions = sOptions  + ',width=850';
    sOptions = sOptions  + ',height=850' ;

	rndVal = parseInt((60000 * Math.random()) + 1);

	var link = "DownloadAttachment.htm?json="+json;
	wOpen = window.open('',rndVal,sOptions);
	wOpen.location = link;
    wOpen.focus();
	wOpen.moveTo(50,50);
	}
	else{
	window.location.href ="DownloadAttachment.htm?json="+json;
	}	
}

function checkspecialcharacters(value){
	var checkstatus = false;
	if(value.indexOf("<")!=-1){
		checkstatus = true;
	}else if(value.indexOf("&")!=-1){
		checkstatus = true;
	}else if(value.indexOf(">")!=-1){
		checkstatus = true;
	}else if(value.indexOf('"')!=-1){
		checkstatus = true;
	}else if(value.indexOf("=")!=-1){
		checkstatus = true;
	}else if(value.indexOf("~")!=-1){
		checkstatus = true;
	}else if(value.indexOf("\\u")!=-1){
		checkstatus = true;
	}
    return checkstatus;
}
 
 var userSelectionCoulmnsForMenu = new Array();
 var defaultCoulmnsForMenu = new Array();
 var mandatorycolumns = new Array();
function displayCustomization(){
	var MENUID = parent.childMenuID;
	
	var JSON = '{"MENU_ID":"'+parent.CHILD_MENU_ID+'","EMPLOYEE_ID":"'+parent.EMPLOYEE_ID+'","ROLE_ID":"'+parent.ROLE_ID+'"}';
	var html = "<table width = '100%'  border='0' cellspacing='0' cellpadding='0' class='modalTable'><tr><tr><td><select id='columnlist' class='multiselect' multiple='multiple' name='columnlist'>";
		
	$.getJSON('getColumnListForMenu.htm',{json:JSON},function(result){
		if (result.status && result.status=="Error") {	
			parent.jbarOnFailure(result.message);
		}else{
		var columnList = result.MenuColumnList;
		var userSelectionList = result.UserSelectionList;
		var defaultColumnList = result.DefaultColumnsForMenu;
			userSelectionCoulmnsForMenu = new Array();
			defaultCoulmnsForMenu = new Array();
			mandatorycolumns	  = new Array();
		for(var i=0;i<userSelectionList.length;i++){
			userSelectionCoulmnsForMenu.push(userSelectionList[i].COLUMN_ID);
		}
		for(var i=0;i<columnList.length;i++){
			if(columnList[i].IS_MANDATORY){
				mandatorycolumns.push(columnList[i].COLUMN_ID);
			}
		}
		for(var i=0;i<defaultColumnList.length;i++){
			defaultCoulmnsForMenu.push(defaultColumnList[i].COLUMN_ID);
		}
			if(userSelectionCoulmnsForMenu.length<=0){
				for(var i=0;i<columnList.length;i++){
					var column_id = columnList[i].COLUMN_ID;
/* 					if(jQuery.inArray(column_id, defaultCoulmnsForMenu)==-1){ 
 						html+="<option value='"+column_id+"'>"+columnList[i].DISPLAY_NAME+"</option>"; 
 					}else{
 */				
				if(jQuery.inArray(column_id, defaultCoulmnsForMenu)==-1){
					html+="<option value='"+column_id+"'>"+columnList[i].DISPLAY_NAME+"</option>";
				}else{
					html+="<option value='"+column_id+"' selected='selected'>"+columnList[i].DISPLAY_NAME+"</option>";
				}
// }
				}
			}else{
				var columnValues = new Array();
				for(var i=0;i<columnList.length;i++){
					var column_id = columnList[i].COLUMN_ID;
					if(jQuery.inArray(column_id, userSelectionCoulmnsForMenu)==-1){
						html+="<option value='"+column_id+"'>"+columnList[i].DISPLAY_NAME+"</option>";
					}else{
						columnValues[column_id] = columnList[i].DISPLAY_NAME;
					}
				}
				for(var i=0;i<userSelectionCoulmnsForMenu.length;i++){
				  html+="<option value='"+userSelectionCoulmnsForMenu[i]+"' selected='selected'>"+columnValues[userSelectionCoulmnsForMenu[i]]+"</option>";
				}
			}
		html+="</select></td></tr><tr><td><input type='button' value='Save' onclick='saveCustomizationList()'></td></tr></table>";
		$.colorbox({html:html,onComplete : function() { 
                  $(".multiselect").multiselect();
 		 }});
		}
	});
}

function getuserSelectionColumnJson(){

	var postjson = '{"USER_SELECTION_FOR_MENU":[';
	var userselectedColumnstoSave = new Array();
	var displayOrder =1;
		$("#columnlist option:selected").each(function(){
			var selectedColumnID = parseInt(this.value);
			if(jQuery.inArray(selectedColumnID, userSelectionCoulmnsForMenu)==-1){
				postjson += '{"COLUMN_ID":"'+selectedColumnID+'","DISPLAY_ORDER":"'+displayOrder+'","MENU_ID":"'+parent.CHILD_MENU_ID+'","EMPLOYEE_ID":"'+parent.EMPLOYEE_ID+'","ROLE_ID":"'+parent.ROLE_ID+'","MODE":"I"},';
			}else{
				postjson += '{"COLUMN_ID":"'+selectedColumnID+'","DISPLAY_ORDER":"'+displayOrder+'","MENU_ID":"'+parent.CHILD_MENU_ID+'","EMPLOYEE_ID":"'+parent.EMPLOYEE_ID+'","ROLE_ID":"'+parent.ROLE_ID+'","MODE":"U"},';
			}			
			userselectedColumnstoSave.push(selectedColumnID);
			displayOrder ++;
		});
		for(var i=0;i<userSelectionCoulmnsForMenu.length;i++){
			if(jQuery.inArray(parseInt(userSelectionCoulmnsForMenu[i]), userselectedColumnstoSave)==-1){
				postjson += '{"COLUMN_ID":"'+userSelectionCoulmnsForMenu[i]+'","DISPLAY_ORDER":"0","MENU_ID":"'+parent.CHILD_MENU_ID+'","EMPLOYEE_ID":"'+parent.EMPLOYEE_ID+'","ROLE_ID":"'+parent.ROLE_ID+'","MODE":"D"},';
			}
		}
		postjson=postjson.substring(0,postjson.length-1)+']}';
		return postjson;
}
var notselectedcolumns = new Array();
function validateCustomizationBeforeSave(){
	var validationStatus = true;
	var selectedcolumnsarray = new Array();
	var columnselectedSize = $("#columnlist option:selected").size();
	if(columnselectedSize <=0){
		validationStatus = false;
	}
	$("#columnlist option:selected").each(function(){
		selectedcolumnsarray.push(parseInt(this.value));
	});
	notselectedcolumns = new Array();
	$.each(mandatorycolumns, function(index, value) {         
    	if(jQuery.inArray(parseInt(value), selectedcolumnsarray)==-1){
    		notselectedcolumns.push($("#columnlist option[value='"+value+"']").text());
    		validationStatus = false;
    	}
    });
    return validationStatus;
}
function saveCustomizationList(){
	if(!validateCustomizationBeforeSave()){
		if($("#columnlist option:selected").size()<=0){
			alert('Please select atleast one column.');
		}else {
			alert(notselectedcolumns+" column is Mandatory.");
		}
	}else{
	 	var JSON = getuserSelectionColumnJson();
		$.getJSON('saveSelectedColumnListForMenu.htm',{json:JSON},function(result){	
			$.colorbox.close();
			window.location.reload(true);			
		});
	}
}
function onKeyDown(e){
	   if(window.event.keyCode == 8) { 
				var targ;
	            if (!e) var e = window.event;
	            if (e.target) targ = e.target;
	            else if (e.srcElement) targ = e.srcElement;
				if(targ.readOnly)
					return false;
	   }
}
function customRange(input){		
		 return {		 		  
		  minDate: 0,     
		  onSelect: getST		 
		};
}
function getST()
{				
	$("#EXCEPTION_END_DATE").val('');
	$('#EXCEPTION_END_DATE').attr("disabled",false);			
}
function customRangeEnddt(){
	var startDt=$("#EXCEPTION_START_DATE").val();
	return {		 		  
		  minDate: startDt     		  
		};
}

var mstrTicketVal=null;
var firstTime=true;
var prevArray=new Array();
var masterTicketId=null;
function getPopUpWindow(id){
	var html1="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='modalTable'> " +
	"<tr><td>Master Ticket Id </td><td><select id='masterId' name='masterId' onchange='getMasterDiv(this.selectedIndex);'><option id='0'>Please Select</option>";	
	var html2="";	
	var html4="";	
	var subject="";	
	
	var description="";	
	$.ajaxSetup({ cache: false });
	$.getJSON('getMasterTicketList.htm',{id:id},function(result){
		var masterTicketIdList=result.MasterTicketList;
		
		for(var i=0;i<masterTicketIdList.length;i++){
			html2+="<option id='"+masterTicketIdList[i].TICKET_ID+"'>"+masterTicketIdList[i].TICKET_ID+"</option>";	
			}
			var html3="</select></td></tr>";
			html4=html4+html1+html2+html3;	
			html4=html4+"<tr><td colspan='4'><table colspan='4' id='MstDiv' width = '100%' class='myDataTable' style='display:none;align:center'>";
			html4+="<tr><th>SERVICE TICKET ID</th><th>SUBJECT</th><th>DESCRIPTION</th><th>STATUS</th><th>CREATED BY</th><th>CREATED DATE</th></tr>";
			
			for(var i=0;i<masterTicketIdList.length;i++){			
				html4+="<tr id=_"+masterTicketIdList[i].TICKET_ID+" style='display:none;align:center'><td>"+masterTicketIdList[i].TICKET_ID+"</td><td>"+masterTicketIdList[i].SUBJECT+"</td><td>"+masterTicketIdList[i].DESCRIPTION+"</td><td>"+masterTicketIdList[i].WORKFLOW_NAME+"</td><td>"+masterTicketIdList[i].CREATED_NAME+"</td><td>"+masterTicketIdList[i].CREATED_DATE+"</td></tr>";
			}	
			html4+="</table></td></tr><tr><td id='SaveButtn' style='display:none;align:center class='label' colspan='2' align='center' ><input type='button' id='Save' value='Save' onclick='saveMsg("+id+","+$("#masterId").val()+");return false;'></tr>" +
				"<tr><td colspan='2' id='SaveMessage' style='display:none;align:center'><div id='sMsg'><font color='Green'><b>Selected ticket"+id+" added successfully to the Master ticket</b></font></div id='sMsg'></td></tr></table>";			
		$('.colorboxpopupWindow').colorbox({width:"65%", height:"95%",html:html4,onComplete : function() { 		
		}
	});
	
});
}

function getMasterDiv(id)
{	
	var prevId=null;
	if(prevArray.length!=0){
		 prevId=prevArray[0];
	}	
	$("#MstDiv").show();
	if(firstTime==false){
	$("#_"+prevId).hide();
	}
	
	$("#_"+$("#masterId option:selected").text()).show();
	masterTicketId=$("#masterId option:selected").text();	
	$("#SaveButtn").show();
	
	if(prevArray.length!=0){
	prevArray=new Array();
		prevArray.push($("#masterId option:selected").text());
	}
	if(firstTime){
		prevArray.push($("#masterId option:selected").text());
	}
	firstTime=false;
	
}


function saveMsg(ticktId,MasterTicketId){
	var jsonobj = '{"TICKET_ID":"'+ticktId+'","MASTER_TICKET_ID":"'+masterTicketId+'"}';
	$.ajaxSetup({ cache: false });
	$.getJSON('saveChildToMaster.htm', {json:jsonobj}, function(data) {		
		if(data=="SUCCESS"){
			$("#SaveButtn").hide();		
			window.location.reload(true);
			parent.jbarOnSuccess('Successfully linked Ticket '+ticktId+' to Master Ticket '+masterTicketId+' ');
		}else{
			parent.jbarOnFailure(' Ticket not linked');
		}
		
	});

}
function showAllSelectedtickets(){
	var i=0;
	var functionID='256';
	var ticketSelectionFlag=false;
	var ticketidIndex="";
	var workflowIndex="";
	var colorCodeIndex="";
	var subcategoryIndex="";

	$.getJSON('getOutOfSLAReasons.htm',{functionID:functionID},function(reasons){
		
		
	var popupHtml="<table class='ticketsTable' id='ticketsTable' width='100%' border='1' align='center' cellpadding='5' cellspacing='1' >";
	popupHtml+="<tr style='background:#FAAC58'><td align='center' valign='middle'><b>TICKET ID</b></td><td align='center' valign='middle'><b>RESOLUTION COMMENTS</b></td><td align='center' valign='middle'><b>OUT OF SLA REASON</b></td><td align='center'><b>OUT OF SLA COMMENTS</b></td><td align='center'><b>Resolution Status</b></td>";
	
	$(".jmesa").find(".header").find("td").each(function(j){
		if($(this).text()=="C"){
			colorCodeIndex=j;
		}
		if($(this).text()=="Ticket #"){
			ticketidIndex=j;
		}
		if($(this).text()=="Status"){
			workflowIndex=j;
		}
		if($(this).text()=="SubCategory"){
			subcategoryIndex=j;
		}
	});
	
	
	$(".jmesa").find("table tbody").find("input:checked").each(function(){
		
		var obj =  $(this).closest("tr").find("td");
		var ticketID =obj.eq(ticketidIndex).text();
		var colorCode =obj.eq(colorCodeIndex).find("img").attr("id");
		var workflow_status = obj.eq(workflowIndex).text();
		var subCatgoryId = obj.eq(subcategoryIndex).text();
		
		if(ticketID!=''){
			ticketSelectionFlag=true;
			popupHtml+="<tr><td align='center'>"+ticketID+"</td><td align='center' ><textarea cols='50' rows='5' maxlength='999' class='resolutionComments' id=\""+ticketID+"resolutionComments\" onkeyup='valueToCopy(this.id,document.getElementById(this.id).value)' type=\"text\" ></textarea><span class='red_text' style='vertical-align:top;'>*</span></td><td ><select  id=\""+ticketID+"slaReasons\" disabled class='slaReasons'><option value=''>--Please Select--</option></select><span id=\""+ticketID+"slaReasonsSpan\" class='red_text' style='vertical-align:top;'></span></td><td><textarea cols='50' rows='5'  maxlength='999' type=\"text\" class='outOfSlaComments' id=\""+ticketID+"outOfSlaComments\" disabled></textarea><span id=\""+ticketID+"outOfSlaCommentsSpan\"  class='red_text' style='vertical-align:top;'></span></td><td ><select  id=\""+ticketID+"exceptionalApproval\" disabled class='exceptionalApproval'><option value=''>--Please Select--</option></select><span id=\""+ticketID+"exceptionalApprovalSpan\" class='red_text' style='vertical-align:top;'></span></td>";
			
		}
	});
	if(ticketSelectionFlag==true){
	popupHtml+="</table><input type='button' id='updateTicket' value='Update' style='margin-left:8px' onclick='updateTicket()'></input><input type='button' id='closeColorbox' value='Cancel' style='margin-left:8px' onclick='cancelColorbox()'></input>";
	$.colorbox({width:'100%', height:'100%',scrolling:true,html:popupHtml,onComplete : function() {
		}})
		var options = [];
	options.push('<option value="">--Please Select--</option>');
	for(var i=0;i<reasons.getOUTSLAReason.length;i++){
		options.push('<option value="' + reasons.getOUTSLAReason[i].REASON_ID + '">'
				+ reasons.getOUTSLAReason[i].DESCRIPTION + '</option>');
	}
	$(".slaReasons").html(options.join(''));
	
	var options1 = [];
	options1.push('<option value="">--Please Select--</option>');
	for(var i=0;i<reasons.getUninstallationTicketReasons.length;i++){
		options1.push('<option value="' + reasons.getUninstallationTicketReasons[i].REASON_ID + '">'
				+ reasons.getUninstallationTicketReasons[i].DESCRIPTION + '</option>');
	}
	$(".exceptionalApproval").html(options1.join(''));
	$(".jmesa").find("table tbody").find("input:checked").each(function(){
		
		var obj =  $(this).closest("tr").find("td");
		var ticketID =obj.eq(ticketidIndex).text();
		var colorCode =obj.eq(colorCodeIndex).find("img").attr("id");
		var workflow_status = obj.eq(workflowIndex).text();
		var subCatgoryId = obj.eq(subcategoryIndex).text();
		if(ticketID!='' && colorCode=='images/red.jpg'){
			$("#"+ticketID+"slaReasonsSpan").text("*");
			$("#"+ticketID+"outOfSlaCommentsSpan").text("*");
			$("#"+ticketID+"slaReasons").removeAttr("disabled");
			$("#"+ticketID+"outOfSlaComments").removeAttr("disabled");
		}
		if(subCatgoryId=="Project HW/SW Uninstallation Request"){
			$("#"+ticketID+"exceptionalApproval").removeAttr("disabled");
		}
		
		$("table#ticketsTable tr").each(function(i,v){
			if(i>0 && i!=1){
			ticketID= $(this).children('td:first').text();
			$("#"+ticketID+"resolutionComments").attr('onkeyup','').unbind('click');
			}
		});
		
	});
	}else{
		parent.jbarOnFailure("Kindly select the ticket(s)");
		
	}
	});
	

}

function valueToCopy(id,value){
	var pos=$("#"+id).getCursorPosition();
	$(".resolutionComments").val(value);
	$(this).setCursorPosition(pos,id);
}

function updateTicket(){
	var ticketID="";
	var jsonarray = "";
	var jsonobjs = "";
	var flag=false;
	var ticketIDs="";
	
	$("table#ticketsTable tr").each(function(i,v){
		if(i>0){
		ticketID= $(this).children('td:first').text();
		ticketIDs+=ticketID+",";
		}
		if(ticketID!=''){
			$("#"+ticketID+"outOfSlaComments").removeClass('blink');
			$("#"+ticketID+"resolutionComments").removeClass('blink');
			$("#"+ticketID+"slaReasons").removeClass('blink');
			$("#"+ticketID+"exceptionalApproval").removeClass('blink');
			var inputDisabledFlag=$("#"+ticketID+"slaReasons").attr('disabled');
			var l_resolutionCommentsFlag=$("#"+ticketID+"exceptionalApproval").attr('disabled');
			if($.trim($("#"+ticketID+"resolutionComments").val())==""){
				flag=true;
				$("#"+ticketID+"resolutionComments").addClass('blink');
			}
			if(inputDisabledFlag==false){
				if($("#"+ticketID+"slaReasons").val()==""){
					flag=true;
					$("#"+ticketID+"slaReasons").addClass('blink');
				}
				if($.trim($("#"+ticketID+"outOfSlaComments").val())==""){
					flag=true;
					$("#"+ticketID+"outOfSlaComments").addClass('blink');
				}
			}
			if(l_resolutionCommentsFlag==false){
				if($("#"+ticketID+"exceptionalApproval").val()==""){
					flag=true;
					$("#"+ticketID+"exceptionalApproval").addClass('blink');
				}
				}
		jsonobjs+='{"TICKET_ID":"'+ticketID+'","RESOLUTION_COMMENTS":"'+$("#"+ticketID+"resolutionComments").val()+'","OUT_OF_SLA_REASON":"'+$("#"+ticketID+"slaReasons :selected").val()+'","OUT_OF_SLA_REASON_NAME":"'+$("#"+ticketID+"slaReasons :selected").text()+'","OUT_OF_SLA_COMMENTS":"'+$("#"+ticketID+"outOfSlaComments").val()+'","RESOLUTION_STATUS":"'+$("#"+ticketID+"exceptionalApproval").val()+'"},';
		}
	});
	if(ticketIDs!=''){
		$.getJSON('slaStatus.htm', {ticketIDs:ticketIDs}, function(data) {
			for(var i=0;i<data.length;i++){
				var disabledFlag=$("#"+data[i]+"slaReasons").attr('disabled');
				if(disabledFlag==true){
					flag=true;
					$("#"+data[i]+"slaReasons").removeAttr('disabled');
					$("#"+data[i]+"outOfSlaComments").removeAttr('disabled');
					$("#"+data[i]+"slaReasons").addClass('blink');
					$("#"+data[i]+"outOfSlaComments").addClass('blink');
				}
			}
			if(flag==false){
			$("#updateTicket").hide();
			$("#closeColorbox").hide();
			$(".slaReasons").attr('disabled',true);
			$(".resolutionComments").attr('disabled',true);
			$(".outOfSlaComments").attr('disabled',true);
			$(".exceptionalApproval").attr('disabled',true);
			if(jsonobjs.length>0){
				jsonarray ='{"JSONARRAY":['+jsonobjs.substring(0,jsonobjs.length-1)+']}';	
				$.getJSON('closeMultipleTicket.htm', {json:jsonarray}, function(data) {
					if(data.status=="Success"){
						$.colorbox.close();
						parent.jbarOnSuccess("Success");
						window.location.reload(true);
					}else{
						parent.jbarOnFailure("Error in updation");
						$("#updateTicket").show();
						$("#closeColorbox").show();
						$(".slaReasons").removeAttr('disabled');
						$(".resolutionComments").removeAttr('disabled');
						$(".outOfSlaComments").removeAttr('disabled');
						$(".exceptionalApproval").removeAttr('disabled');
					}
				});
			}
		}
			
		});
	}

	
}

function cancelColorbox(){
	$.colorbox.close();
}

(function ($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if('selectionStart' in el) {
            pos = el.selectionStart;
        } else if('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);

new function($) {
	  $.fn.setCursorPosition = function(pos,id) {
		  $("#"+id).get(0).setSelectionRange(pos, pos);
	  }
	}(jQuery);

function selectAllTicket(){
	var flag=false;
	var colorCodeIndex="";
	var ticketidIndex="";
	var workflowIndex="";
	$(".jmesa").find(".header").find("td").each(function(j){
		if($(this).text()=="C"){
			colorCodeIndex=j;
		}
		if($(this).text()=="Ticket #"){
			ticketidIndex=j;
		}
		if($(this).text()=="Status"){
			workflowIndex=j;
		}
	});
	
$(".jmesa").find("table tbody").find("input:unchecked").each(function(){
		var obj =  $(this).closest("tr").find("td");
		var ticketID =obj.eq(ticketidIndex).text();
		var colorCode =obj.eq(colorCodeIndex).find("img").attr("id");
		var workflow_status = obj.eq(workflowIndex).text();
			if($("#"+ticketID).is(':visible')){
				flag=true;
				$("#"+ticketID).attr('checked',true);
			}
		});
if(flag==false){
	parent.jbarOnFailure("No tickets to select.");
	
}
	
}

