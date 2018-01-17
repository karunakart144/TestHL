
$(document).ready(function(){ 
$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
$("#employeeProfile tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");


});

$('input').bind('input', function() {
  $(this).val($(this).val().replace(/[^a-z0-9]/gi, ''));
});

function getEmployeeDetails(){
	
	
	var EmpID=$('#idToSearch').val();
	$("#employeeProfile").hide();
	var id='idToSearch';	
	if(EmpID==""){
	
	$('#TextFieldSpan'+id+'').text('');
	$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
	
	}else{	
	$('#TextFieldSpan'+id+'').html('');
		
	$.getJSON('getEmployeeDetails.htm',{employeeid:EmpID},function(employeeProfile){
		if (employeeProfile.status=="Error" || employeeProfile=="") {	
		parent.jbarOnFailure("Enter the valid Employee ID");
		}else{
		$("#employeeProfile").show();
		for(var i=0;i<employeeProfile.length;i++){
		$("#employeeID").text(employeeProfile[i].EMPLOYEE_ID);
		$("#employeeName").text(employeeProfile[i].NAME);
		$("#employeeEmailAddress").text(employeeProfile[i].EMAIL_ADDRESS);
		$("#employeeGrade").text(employeeProfile[i].GRADE);
		$("#employeeDesignation").text(employeeProfile[i].DESIGNATION);
		$("#employeeLocation").text(employeeProfile[i].LOCATION);
		$("#employeeRO").text(employeeProfile[i].REPORTING_MANAGER_ID);
		$("#employeeMobileNo").text(employeeProfile[i].MOBILE_NUMBER);
		$("#employeeDept").text(employeeProfile[i].DEPT_NAME);
		$("#isPremium").text(employeeProfile[i].IS_PREMIUM_MEMBER);
		$("#employeeStatus").text(employeeProfile[i].IS_ACTIVE);
		}
		}
		});
	}
}

$(document).ready(function() { 
	
	parent.unblockUI(); 

});