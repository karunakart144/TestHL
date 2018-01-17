$(document).ready(function() { 
	$("#admincreateusertable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
	parent.unblockUI(); 
	
	$.getJSON('getMasterData.htm',{},function(data) {
		for(var i=0;i<data.locationList.length;i++){
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var j=0;j<data.locationList.length;j++){
				options.push('<option value="' + data.locationList[j].LOCATION_ID + '">'
						+ data.locationList[j].CITY + '</option>');
			}
			$("#locationId").html(options.join(''));
		}
		
		for(var i=0;i<data.timeZoneList.length;i++){
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var j=0;j<data.timeZoneList.length;j++){
				options.push('<option value="' + data.timeZoneList[j].TIMEZONE_ID + '">'
						+ data.timeZoneList[j].DESCRIPTION + '</option>');
			}
			$("#timeZone").html(options.join(''));
		}
		
	});
	
	var genderOptions = [];
	genderOptions.push('<option value="">--Please Select--</option>');
	genderOptions.push('<option value="M">Male</option>');
	genderOptions.push('<option value="F">Female</option>');
	$("#gender").html(genderOptions.join(''));
	var statusOptions = [];
	statusOptions.push('<option value="">--Please Select--</option>');
	statusOptions.push('<option value="1">Active</option>');
	statusOptions.push('<option value="0">InActive</option>');
	$("#status").html(statusOptions.join(''));
	 $('#admincreateuser').contents().live('change', function(e) {
		 $(".formError").hide();
	});
	
});


function getUserProfileInfo(){
	var empId=$("#employeeIdToSearch").val();
	if($.trim(empId)==""){
		$("#employeeIdToSearch").focus();
		$("#employeeIdToSearch").showerrormessage({message:"* Enter the employee ID to search."});
	}
	$.postJSON("getEmployeeProfileInfo.htm",{empId:empId},function(data){
		for(var i=0;i<data.length;i++){
			$("#employeeId").val(data[i].EMPLOYEE_ID);
			$('#employeeId').attr('readonly', true)
			$("#employeeName").val(data[i].EMPLOYEE_NAME);
			$("#emailAddress").val(data[i].EMAIL_ADDRESS);
			$("#reportingManager").val(data[i].REPORTING_MANAGER);
			$("#locationId").val(data[i].LOCATION_ID);
			$("#timeZone").val(data[i].USER_TIMEZONE);
			$("#duId").val(data[i].DU_ID);
			$("#mobilenumber").val(data[i].MOBILE_NUMBER);
			$("#extNumber").val(data[i].CONTACT_NUMBER);
			$("#gender").val(data[i].GENDER);
			if(data[i].IS_ACTIVE==true){
			$("#status").val("1");
			}else{
				$("#status").val("0");
			}
		}
		
	});
}


function validateEmpID(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[a-zA-Z0-9]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only alphanumeric characters allowed."});
    return false;
	
}

function validatePhoneNumber(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[0-9-+]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only Numbers and - + characters allowed."});
    return false;
	
}


function validateEmpName(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[a-zA-Z0-9 ]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only alphanumeric characters allowed."});
    return false;
	
}

var options = { 
        target:        '#output1',   // target element(s) to be updated with
										// server response
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        dataType:  'json',
        type:'post'
    }; 
 
    $('#admincreateuser').ajaxForm(options); 
    
    
 // pre-submit callback
    function showRequest(formData, jqForm, options) { 
    	if(ValidateBeforeUsercreation()){
    		$("#submitUser").attr("disabled",true);
    		if($("#employeeId").attr('readonly')){
    			$("#buttonType").val("Edit");
    		}else{
    			$("#buttonType").val("Add");
    		}
    		
    		return true;
    	}else{
    		return false;
    	}
   
    } 
     
    // post-submit callback
    function showResponse(response, statusText, xhr, $form)  { 
    	if(response.response!="Success"){
    		 $("#submitUser").removeAttr("disabled");
    		parent.jbarOnFailure(response.response);
    	}else{
    		$('input, select,textarea').attr('disabled', true); 
    		parent.jbarOnSuccess(response.response);
    	}
    }
    
    
    function ValidateBeforeUsercreation(){
    	$(".formError").hide();
    			var isValidationError = false;
    			
    			if($.trim($("#employeeId").val())==""){
    				$("#employeeId").focus();
    				$("#employeeId").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			if($.trim($("#employeeName").val())==""){
    				$("#employeeName").focus();
    				$("#employeeName").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			
    			if($.trim($("#emailAddress").val())==""){
    				$("#emailAddress").focus();
    				$("#emailAddress").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			if($("#locationId").val()==""){
    				$("#emailAddress").focus();
    				$("#emailAddress").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}

    			if($("#reportingManager").val()==""){
    				$("#reportingManager").focus();
    				$("#reportingManager").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			if($("#timeZone").val()=="" ){ 
    				$("#timeZone").focus();
    				$("#timeZone").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			if($("#gender").val()=="" ){ 
    				$("#gender").focus();
    				$("#gender").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			
    			if($("#status").val()=="" ){ 
    				$("#status").focus();
    				$("#status").showerrormessage({message:"* Mandatory"});
    				isValidationError =true;
    			}
    			
    			if($.trim($("#emailAddress").val())!=""){
    				
    				var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
    			     if(!pattern.test($.trim($("#emailAddress").val()))){
    			     $("#emailAddress").focus();
     				$("#emailAddress").showerrormessage({message:"* Invalid Email Address"});
    			     isValidationError =true;
    				}
    			}

    	        return !isValidationError;
    		}
    
    
    function validateProjID(id,e){
    	$(".formError").hide();
        var regex = new RegExp("^[a-zA-Z0-9_]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }
        e.preventDefault();
        $("#"+id).focus();
    	$("#"+id).showerrormessage({message:"* Only alphanumeric characters and _ allowed."});
        return false;
    	
    }
    
    

    
