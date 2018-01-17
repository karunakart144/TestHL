$(document).ready(function(){ 
	$("#createmasterticket").click(function() {
		  $(".formError").hide();
		});
	$('#FUNCTION').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#CATEGORY').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#SUB_CATEGORY').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#SUBJECT').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#DESCRIPTION').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		}); 	
	$('#phonenumber').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$("#ASSIGNED_GROUP").bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
	});
	$("#ASSIGNED_TO").bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
	});
	$("#ECT").bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
	});
	$('#CCEmailID').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#LOCATION_ID').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#problemfile').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	
	
	//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
	$('#ECT').datetimepicker({
		beforeShow: customRange
		
	}).show();	
	$("#FUNCTION").change(function(){	
		$("#ASSIGNED_GROUP_ID").val('');
		 $("#ASSIGNED_TO").val('');		
		 document.getElementById("empResultMessage").innerHTML='';
		 $("#empResultMessage").hide(); 
	});

	 $('#osdetails').val($.client.os);
	$('#browserdetails').val($.client.browser +" "+ $.client.browserversion);
	parent.unblockUI();
	
		
		$("#FUNCTION").change(function(){
			if($("#FUNCTION :selected").val()=="1" || $("#FUNCTION :selected").val()=="256"){
			$('#ifirstMsgTR').show();
			//setInterval("$('.blinkClass').fadeOut(1000).fadeIn(1000);",0);
			}else{
				 $('#ifirstMsgTR').hide();
				
			}
			});
	
});
var options = { 
        target:        '#output1',   // target element(s) to be updated with
										// server response
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        dataType:  'json',
        type:'post'
}; 

var groupIdList=new Array();
$('#createmasterticket').ajaxForm(options); 

function showRequest(formData, jqForm, options) { 
	var funcnVal=$("#FUNCTION :selected").text();	
	$("#FUNCTION_NAME").val(funcnVal);
		if(ValidateBeforeTicketcreation()){
			$("#FUNCTION").hideAll();
			$("#ticketsubmit").attr("disabled",true);
			$("#ticketcancel").attr("disabled",true);
			return true;
		}else{
			return false;
		}
} 
function showResponse(response, statusText, xhr, $form)  { 
	var Error = response.error;
 if(response.error) {
	jQuery.each(response, function(key, val) {			
		$("#"+key+"").showerrormessage({message:val});
	});
	$("#ticketsubmit").removeAttr("disabled");
	$("#ticketcancel").removeAttr("disabled");
 }else if(response.exception){
	 parent.jbarOnFailure(response.message);
	 $("#ticketsubmit").removeAttr("disabled");
	 $("#ticketcancel").removeAttr("disabled");
 }
 else{
	 
		$('input, select,textarea').attr('disabled', true); 
		var successmessage = "";
		successmessage = 'Ticket #('+response.TICKET_ID+') created Successfully !  ';
		if(response.currentstate){
			successmessage +=' Current Status of Ticket:'+response.currentstate+'.  ';
		}
		if(response.ECT){
			successmessage +=' Expected Completion Time :'+response.ECT;
		}
		parent.jbarOnSuccess(successmessage);
 }
} 

var SubCategoryApprovalList = new Array();
var RecommendedPriorityList = new Array();
var SubCategoryFormList = new Array();
var SubCategoryCommentsList = new Array();
var prioritychangewarn =false; 

function GetCategories(obj,ElementId){
	prioritychangewarn = false;
	var CategoryId = obj.options[obj.selectedIndex].value; 
	
	if(($("#"+obj.id+" :selected").text()=="IT Infrastructure Management" || $("#"+obj.id+" :selected").text()=="Admin" ) && ElementId=="CATEGORY"){
			$("#SUB_CATEGORY").html("");
			//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
			//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
			
	}else if(ElementId=="CATEGORY"){
			$("#SUB_CATEGORY").html("");
			$("#subcategory").html("");
			//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
			//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
			
	}else{
		if(ElementId=="SUB_CATEGORY"){
			$("#SUB_CATEGORY").html("");
		}
	}	
	if(CategoryId!="0" && ElementId=="CATEGORY"){
		getLocationsForFunction(CategoryId);
	}else if(CategoryId=="0" && ElementId=="CATEGORY"){
		$('#LOCATION_ID').html($("<option></option>").attr("value",'0').text('--Please Select Function--'));
	}
	if(ElementId=="CATEGORY"){
		
		if($("#"+obj.id+" :selected").text()=="Finance"){
			$("#locationHeaderTd").html("Location<span class=\"red_text\">*</span>");
		}else{
			$("#locationHeaderTd").html("Impacted Location(s)<span class=\"red_text\">*</span>");
			$("#LOCATION_ID").attr("bt-xTitle","Mandatory , Select your current work location");
		}
	}
	if(ElementId=="SUB_CATEGORY"){
		var categoryval = $("#"+obj.id+" :selected").text();
		if(categoryval.match("Payroll")){
			$("#LOCATION_ID").attr("bt-xTitle","Mandatory, Select the location where the query persists");
		}else{
			$("#LOCATION_ID").attr("bt-xTitle","Mandatory , Select your current work location");
		}
	}
	if(CategoryId!="" && CategoryId!="0"){
		$.getJSON('getHelpDeskCategory.htm',{CategoryId:CategoryId},function(categories){

			var CategoryObj = document.getElementById(ElementId);
			CategoryObj.innerHTML = "";
				var optn = document.createElement("OPTION");
	    		optn.value = "";
	    		optn.text = "--Please Select--";
	    		CategoryObj.options.add(optn);

	    		if(ElementId=="SUB_CATEGORY"){
		    		SubCategoryApprovalList = new Array();
		    		RecommendedPriorityList = new Array();
					SubCategoryFormList = new Array();
	    		}	    	
				for(var i=0;i<categories.length;i++){
		    		var optn = document.createElement("OPTION");
			    		optn.value = categories[i].CATEGORY_ID;
			    		optn.text = categories[i].NAME;
			    	CategoryObj.options.add(optn);

			    	if(ElementId=="SUB_CATEGORY"){
			    		SubCategoryApprovalList[categories[i].CATEGORY_ID] = categories[i].IS_CHANGE_REQUEST;
			    	//	RecommendedPriorityList[categories[i].CATEGORY_ID] = categories[i].RECOMMENDED_PRIORITY;
						SubCategoryFormList[categories[i].CATEGORY_ID] = categories[i].LINK;
						SubCategoryCommentsList[categories[i].CATEGORY_ID] = categories[i].COMMENTS;
			    	}
			    }
		});
	}
	
	$("#formlinktoixchange").html("");
}


function ShowApprovalAttachment(subCatgID){
		var subCategoryId = subCatgID.options[subCatgID.selectedIndex].value;
		var functionvalue = $("#FUNCTION option:selected").text();
		var catvalue = $("#category option:selected").text();

 			$("#ApprovalAttachmentTR").hide();
			//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
			//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");

		
		if(SubCategoryFormList[subCategoryId]!='NULL' && SubCategoryFormList[subCategoryId]!=null)
		{
			var formName=SubCategoryFormList[subCategoryId].substring(SubCategoryFormList[subCategoryId].indexOf("[")+1,SubCategoryFormList[subCategoryId].indexOf("]"));
			var formLink=SubCategoryFormList[subCategoryId].substring(SubCategoryFormList[subCategoryId].lastIndexOf("[")+1,SubCategoryFormList[subCategoryId].lastIndexOf("]"));
			var showForm="Download: <a href=\""+formLink+"\" target='_blank'>"+formName+"</a>";
			$("#formlinktoixchange").addClass("invalid_text");
			if(SubCategoryCommentsList[subCategoryId]!='NULL' && SubCategoryCommentsList[subCategoryId]!=''){
				showForm=SubCategoryCommentsList[subCategoryId];
				$("#formlinktoixchange").html(showForm);
			}else{
			$("#formlinktoixchange").html(showForm);
			alert("Please Download the form and Attach the updated one for faster resolution of the concern.");
			}
		}else{
			$("#formlinktoixchange").html("");
		}
			
}

function getGroupsForFunctionAll(function_id){	
	 groupIdList=new Array();
	var groupNameList=new Array();
	//Added to include indexOf function in ArrayList
	if(!Array.indexOf){
	    Array.prototype.indexOf = function(obj){
	        for(var i=0; i<this.length; i++){
	            if(this[i]==obj){
	                return i;
	            }
	        }
	        return -1;
	    }
	}		
	//End ------Added to include indexOf function in ArrayList
	if(function_id!=""){
		$.getJSON('getMasterGroupsforFunctionAll.htm',{functionid:function_id},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{						
				
				for(var i=0;i<response.length;i++){
					groupIdList.push(response[i].GROUP_ID);
					groupNameList.push(response[i].GROUP_NAME);
					
				}
					$("input#ASSIGNED_GROUP").autocomplete({
					    source: groupNameList,
					    select: function(event, ui) {						
						var selectedObj=ui.item;
						var index=groupNameList.indexOf(selectedObj.value);						
						var assignedGrpId=groupIdList[index];
						$("#ASSIGNED_GROUP_ID").val(assignedGrpId);
						getGroupMembers(assignedGrpId);
						
					}
					});
			}
		});

	}
	
}

function getGroupsForFunction(){
	var function_id=$("#FUNCTION").val();
	var location_id=$("#LOCATION_ID").val();
	groupIdList=new Array();
	var groupNameList=new Array();
	//Added to include indexOf function in ArrayList
	if(!Array.indexOf){
	    Array.prototype.indexOf = function(obj){
	        for(var i=0; i<this.length; i++){
	            if(this[i]==obj){
	                return i;
	            }
	        }
	        return -1;
	    }
	}		
	//End ------Added to include indexOf function in ArrayList
	
	
	 
	if( $('#LOCATION_ID :selected').length > 0){
		var selectednumbers = new Array();
        //build an array of selected values
       
        $('#LOCATION_ID :selected').each(function(i, selected) {
            selectednumbers[i] = $(selected).val();
        });
        var json = '{"functionid":"'+function_id+'","locationid":"'+selectednumbers+'"}';
	if(function_id!=""){
		
		$.getJSON('getMasterGroupsforFunction.htm',{json:json},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{						
				
				for(var i=0;i<response.length;i++){
					groupIdList.push(response[i].GROUP_ID);
					groupNameList.push(response[i].GROUP_NAME);
					
				}
					$("input#ASSIGNED_GROUP").autocomplete({
					    source: groupNameList,
					    select: function(event, ui) {						
						var selectedObj=ui.item;
						var index=groupNameList.indexOf(selectedObj.value);						
						var assignedGrpId=groupIdList[index];
						$("#ASSIGNED_GROUP_ID").val(assignedGrpId);
						getGroupMembers(assignedGrpId);
						
					}
					});
			}
			
		});
	}
	}
	
}
function getLocationsForFunction(function_id){
	$.getJSON('getLocationListForFunction.htm',{functionid:function_id},function(locations){		
		$('#LOCATION_ID').html($("<option></option>").attr("value",'0').text('--Please Select--'));
		for(var i=0;i<locations.length;i++){
			$('#LOCATION_ID').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
		 }		
	});
}
function getGroupMembers(id){ 
								if($("#FUNCTION").val()==""){
								$("#FUNCTION").focus();
								$("#FUNCTION").showerrormessage({message:"* Mandatory"});
								return; 
								}else{
									$("#FUNCTION").hideAll();
								}	
	var groupName = id;
	if(groupName!=""){
						var functionID = $("#FUNCTION option:selected").val();
						var locationID = null;
						$.getJSON('MASTER_getGroupmember.htm', {
							groupId : groupName,
							type : null,
							functionid : functionID,
							locationid : locationID,
							from : 'group'
						}, function(response) {
								if (response.status && response.status=="Error") {	
									parent.jbarOnFailure(response.message);
								}else{
									var data = response.MEMBER_DETAILS;
									if (data.length != 0) {			
										var options = [];
										var roles = new Array();
										var html = '<option value="">--Please Select--</option>';
										$.each(data, function(item) {
											var role_name = jQuery.trim(data[item].ROLE_NAME);
											if(jQuery.inArray(role_name, roles)==-1){
												roles.push(role_name);
												options[role_name] = [];
											}
										});
										$.each(data, function(item) {
											if(data[item].ASSIGNMENT_REQUIRED){
												options[jQuery.trim(data[item].ROLE_NAME)].push('<option value="' + data[item].member_id + '">'
												+ data[item].member_name_id + '</option>');
											}
										});
										for(var i=0;i<roles.length;i++){
											var optionhtml = "";
											var option = options[roles[i]];
											for(var k=0;k<option.length;k++){
												optionhtml +=option[k];						
											}
											if(optionhtml!=""){
												html+='<optgroup label="'+roles[i]+'">'+optionhtml+'</optgroup>';
											}
										}
										$("#ASSIGNED_TO").html(html);
									}else{
									$("#ASSIGNED_TO").html('<option value="">--Please Select--</option>');
									}			
									var selfunctionName  = $("#FUNCTION :selected").text();
								}

		
	});
						}
}

function ValidateBeforeTicketcreation(){

			$("#FUNCTION").hideAll();
			var isValidationError = false;
			if($("#FUNCTION").val()=="0"){
				$("#FUNCTION").focus();
				$("#FUNCTION").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			var selectedfunction = $("#FUNCTION option:selected").text(); 
			
			if($("#CATEGORY").val()=="" || $("#CATEGORY").val()=="0" || $("#CATEGORY").val()==null){
				$("#CATEGORY").focus();
				$("#CATEGORY").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			
			if($("#SUB_CATEGORY").val()=="" || $("#SUB_CATEGORY").val()=="0"  || $("#SUB_CATEGORY").val()==null){ 
				$("#SUB_CATEGORY").focus();
				$("#SUB_CATEGORY").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}	
			
			 if($("#ALL_LOCATIONS").attr("checked")==true){
				 $("#LOCATION_ID").val("0") ;
				 
			 }else if($("#ALL_LOCATIONS").attr("checked")==false){
				if($("#LOCATION_ID").val()=="0" || $("#LOCATION_ID").val()==""){
					$("#LOCATION_ID").focus();
					$("#LOCATION_ID").showerrormessage({message:"* Mandatory"});
					isValidationError =true; 
			}
			 }
			var sub=jQuery.trim($("#SUBJECT").val());
			
				if(sub==""){
					$("#SUBJECT").focus();
					$("#SUBJECT").showerrormessage({message:"* Mandatory"});
					isValidationError = true;
				}
			
			var desc=jQuery.trim($("#DESCRIPTION").val());
			if(desc==""){
				$("#DESCRIPTION").focus();
				$("#DESCRIPTION").showerrormessage({message:"* Mandatory"});
				isValidationError = true;
			}
			
			if($("#DESCRIPTION").val().length>500){
				$("#DESCRIPTION").focus();
				$("#DESCRIPTION").showerrormessage({message:"Description cannot be more than 500 characters. "});
				isValidationError = true;
			}
			
			var assignedTo=jQuery.trim($("#ASSIGNED_TO").val());
			if(assignedTo==""){
				$("#ASSIGNED_TO").focus();
				$("#ASSIGNED_TO").showerrormessage({message:"* Mandatory"});
				isValidationError = true;
			}
			
			var assignedTo=jQuery.trim($("#ECT").val());
			if(assignedTo==""){
				$("#ECT").focus();
				$("#ECT").showerrormessage({message:"* Mandatory"});
				isValidationError = true;
			}
			var assignedGrp=$("#ASSIGNED_GROUP_ID").val();			
			if(assignedGrp=="" || assignedGrp=="0"){
				$("#ASSIGNED_GROUP_ID").focus();
				$("#ASSIGNED_GROUP_ID").showerrormessage({message:"Please select valid Assigned Group"});
				isValidationError =true; 
			}			
			if($("#phonenumber").val()==""){
				$("#phonenumber").focus();
				$("#phonenumber").showerrormessage({message:"*Mandatory"});
					isValidationError =true;
			}
			else if($("#phonenumber").val()!=""){
				var phonenum = $("#phonenumber").val();
				if(!(/^[0-9]+$/.test($("#phonenumber").val()))){
					$("#phonenumber").focus();
					$("#phonenumber").showerrormessage({message:"Invalid Number !"});
					isValidationError =true;
				}else if(phonenum.length >15){
					$("#phonenumber").focus();
					$("#phonenumber").showerrormessage({message:"Please provide 10-15 digit number"});
					isValidationError =true;
				}
			}		
			if($("#EmpProject").val()==""){
				$("#EmpProject").focus();
				$("#EmpProject").showerrormessage({message:"* Mandatory."});
				isValidationError =true;
			}
			var emailaddressVal = $("#CCEmailID").val();		
	        if(emailaddressVal!="" && !validateEmailIds(emailaddressVal)) {	
	        	$("#CCEmailID").focus();
	        	$("#CCEmailID").showerrormessage({message:"one or more email addresses entered is invalid!"});
	            isValidationError =true;
	        }	  
	       
	         return !isValidationError;
}

function validateEmailIds(emails){
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var email = emails.split(',');
	var resultstatus = true;
	for (var i = 0; i < email.length; i++) {
		if (!emailReg.test(email[i])) {
			resultstatus  = false;
		}
	}
	if(emails!="" && email.length==0){
		resultstatus = false;
	}
	return resultstatus;
}

function customRange(input){	
	
	
		 return {
		  // showAnim: "drop",
		  dateFormat: 'mm/dd/yyyy HH:MM:ss',
		  minDate: -1,
		  maxDate:+2
		 // onSelect: getST
		
		 // displayClose:true
		};
	

}


function clearproblemattachment(){
	 $("#problemattachTD").html('<input name="problemfile" id="problemfile" title="Attach supporting document related to your problem. (Screen shots, Image, Documents)"  type="file"/> &nbsp; <a href="#" class="label" onclick="clearproblemattachment()" >Clear Attachment</a>');
}
function isAllLocationChecked(){
	  if($("#ALL_LOCATIONS").attr("checked")){
		  
		  
		  getGroupsForFunctionAll($("#FUNCTION").val());
		  
	  }
}
function clearField(){
	$("#ASSIGNED_GROUP").val('');
	         
}
	  

function getGroups(){	
	var employeeId=$("#ASSIGNED_TO").val();
	var isEmpError = true;
	var isValidationError = true;	
	var functionId = $("#FUNCTION option:selected").val();	
	var isData=true;
	var isFunctionValidationError=true;
	if(/^[a-zA-Z0-9_.]+$/.test(employeeId)){
		 isValidationError = true;
		 isEmpError=true;
	} if(employeeId==""){
		 $("#ASSIGNED_TO").focus();		
		 isValidationError = false;
		 isEmpError=false;
	 }	if(functionId=="" || functionId=="0"){
		 $("#FUNCTION").focus();		
		 isValidationError = false;
		 isFunctionValidationError=false;
	 }
	 
	
	
	if(isValidationError==true || isFunctionValidationError==true){
		$.getJSON('getGroupsForMember.htm',{employeeId:employeeId,functionId:functionId},function(result){
			var data = result.GROUP_LIST;
			$('#ASSIGNED_GROUP_ID').html($("<option></option>").attr("value",'0').text('--Please Select--'));
			if (data.length != 0) {	
				for(var i=0;i<data.length;i++){
				$('#ASSIGNED_GROUP_ID').append($("<option></option>").attr("value",data[i].GROUP_ID).text(data[i].GROUP_NAME));
				}}else{				
					isValidationError = false;		
					isData=false;
					isEmpError=false;
				}
		
		});
		
	}	 
		if(isValidationError==false){
			if(isFunctionValidationError==false){
				$("#FUNCTION").showerrormessage({message:"Please select valid Function"});
			}else if(isData==false){
				$("#ASSIGNED_TO").showerrormessage({message:"Please select valid employee id"});
			}
			else
			{
				$("#ASSIGNED_TO").showerrormessage({message:"Please select valid employee id"});
			}
		}else{
			if(isEmpError==true){
				$.getJSON('validateEmployee.htm',{employeeId:employeeId},function(Emp){
				if(Emp.NAME){ 
					document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("valid_text");
					$("#empResultMessage").show(); 
				}else
					{isValidationError = false;
					document.getElementById("empResultMessage").innerHTML ="Please enter Authorized Employee id and click on 'Validate' to verify";
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("invalid_text");
					$("#empResultMessage").show();
					
					$("#searchMsg").hide();
					}
				});
			}
		}
		isValidationError=true;
		isFunctionValidationError=true;
}
	//restrict backspace for date time
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
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  












