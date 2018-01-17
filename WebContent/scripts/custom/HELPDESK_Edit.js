var ChangedArray = new Array();
var removeArray = new Array();
var alwaysDisabledArray = new Array();
var existingCommentsArray = new Array();
var enteredCommentsArray = new Array();
var SubCategoryApprovalList = new Array();
var SubCategoryRecomendPriorityList = new Array();
var mainWorkflowState = "";
var mainSubCategoryID = 0;
var mainFunctionID = 0;
var mainFuncionValue = "";
var odclocationsretrieved = false; // For ODC's and Admin function
var assignedToOnLoad = "";
var IsValidEmpId = false;
var isOrch = false;

$(document).ready(function(){
	
	/**********Emergency L1**********/
	
	if($("#FUNCTION_ID :selected").text()=="Information Systems"){
		$("#Emergency_Det").show();
	}else{
		$("#Emergency_Det").hide();
	}
	
	if(logged_user_role=="User"){
		$("#Emergency_Det").hide();
	}
	/**********Emergency L1**********/
	$('#DESCRIPTION').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#ADDITIONAL_INFO').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#RESOLUTION_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#PRIVATE_NOTES').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#OUT_OF_SLA_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#REOPEN_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#ONHOLD_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#FEEDBACK_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
    $('#ticketdetailsform').find(':input').each(function(){
        var elementId = $(this).attr('id');       
        if(elementId != 'Edit' && elementId != 'Update'){
        		$(this).attr('disabled','true');
        }
		if((elementId=='DESCRIPTION') ||(elementId=='ADDITIONAL_INFO')||(elementId=='RESOLUTION_COMMENTS')||(elementId=='PRIVATE_NOTES')
			||(elementId=='OUT_OF_SLA_COMMENTS')||(elementId=='ONHOLD_COMMENTS')||(elementId=='REOPEN_COMMENTS')||(elementId=='FEEDBACK_COMMENTS')){
			$(this).removeAttr("disabled");
			$(this).attr('readonly','true');
		}	
		/**********Auto Assignment************/	
				if(elementId=='WORKFLOW_STATE'){
					$(this).removeAttr("disabled");
					oldStatus=$(this).val();
					$(this).attr('disabled','true');
				}
				if(elementId=='PRIORITY_ID'){
					$(this).removeAttr("disabled");
					oldPriority=$(this).val();
					$(this).attr('disabled','true');
				}
				if(elementId=='ASSIGNED_TO'){
					$(this).removeAttr("disabled");
					oldAssignedTo=$(this).val();
					$(this).attr('disabled','true');
				}
				//Added by Sali
				if(elementId=='ASSIGNED_GROUP'){
					$(this).removeAttr("disabled");
					oldAssignedGroup=$(this).val();
					
					$(this).attr('disabled','true');
				}
		/**********Auto Assignment************/		
		   });
  //Added to remove Low priority for Premium users
    if($('#premium').val()=='Yes'){
    	$("#PRIORITY_ID option[value='1']").remove();
    }
    
    
		
    // Below code added to exclude iTrack related categories using Special preference column -START (ADDED BY KRUTHI)
		var CategoryId = $('#FUNCTION_ID').val();
		var catText = $('#CATEGORY_ID option:selected').text();
		var catVal = $('#CATEGORY_ID option:selected').val();
		
		$.ajax({
			async : false,
			type: 'GET',
			url: "getHelpDeskCategories.htm",
			data : { 
					CategoryId : CategoryId				
				   }, 
			success : function (categories) {
		var CategoryObj = document.getElementById("CATEGORY_ID");
		CategoryObj.innerHTML = "";
		var optn = "";
		$.ajax({
			async : false,
			type: 'GET',
			url: "getCategoryRoleMapping.htm",
			data : {}, 
			success : function (categoryRoleMapping) {	
			for(var i=0;i<categories.length;i++){
					var optn = document.createElement("OPTION");	
					if(categories[i].SPECIAL_PREFERENCES=='N'){
							optn.value = categories[i].CATEGORY_ID;
							optn.text = categories[i].NAME;
							CategoryObj.options.add(optn);
					}if(categories[i].SPECIAL_PREFERENCES=='Y'){
						var catID=categories[i].CATEGORY_ID;
						var catName=categories[i].NAME;			
						for(var j=0;j<categoryRoleMapping.length;j++){
							if((categoryRoleMapping[j].NAME==loginrole) && (categoryRoleMapping[j].CATEGORY_ID==catID)){
								optn.value =catID;
								optn.text =catName;													
								CategoryObj.options.add(optn);
							}
						}										
					}
			}
			}
		});
					}
	});
		
		var catFlag=false;
		$("#CATEGORY_ID option").each(function() {
			  if($(this).text() == ''+catText+'') {
			    $(this).attr('selected', 'selected');       
			    catFlag=true;
			  }
			});
		
		if(catFlag==false){
			 $('#CATEGORY_ID option:selected').text(catText);
			 $('#CATEGORY_ID option:selected').val(catVal);
			
		}
		
	//code added to exclude iTrack related categories using Special preference column -END (ADDED BY KRUTHI)
		
    $.each(nonemaparray, function(index, value) {         
    	$("#"+jQuery.trim(value)+"_TR").hide();
    });
    
    $('input,select,textarea').change(function() {
    	if(this.id!="FILTERLOGGEDINUSERS" && this.id!="BUILDING" && this.id!="FLOOR"){
    		AddValuetoArray(this.id,ChangedArray)
    	}
    });

    mainFunctionID = $("#FUNCTION_ID").val();
    mainWorkflowState = $("#WORKFLOW_STATE").val();
    mainFuncionValue = $("#FUNCTION_ID option:selected").text();
    if($("#SUB_CATEGORY_ID").val()!=""){
    	mainSubCategoryID = $("#SUB_CATEGORY_ID").val();
    }
	/************** Emergency L1*************/	
	$("#EMERGENCY_TYPE").change(function(){				
			if($("#EMERGENCY_TYPE").val()!="NA"){
				$("#SUB_STATUS").val("Submitted to DBA");
			}
			if($("#EMERGENCY_TYPE").val()=="NA"){
				$("#SUB_STATUS").val("NA");
				//$("#EMERGENCY_TYPE").val("NA");
				//AddValuetoArray("EMERGENCY_TYPE",ChangedArray);
			}
	});
	$("#SUB_STATUS").change(function(){				
			if($("#SUB_STATUS").val()=="NA"){
				//$("#IS_EMERGENCY_TICKET").val("No");
				//AddValuetoArray("IS_EMERGENCY_TICKET",ChangedArray);
				$("#EMERGENCY_TYPE").val("NA");
				AddValuetoArray("EMERGENCY_TYPE",ChangedArray);
			}			
	});
	$("#DEPLOYMENT_INSTRUCTION").change(function(){		
		AddValuetoArray("DEPLOYMENT_INSTRUCTION",ChangedArray);
	});
	$("#REASON_SEND_BACK").change(function(){		
		AddValuetoArray("REASON_SEND_BACK",ChangedArray);
	});
	/************** Emergency L1*************/
    /********************AUTO ASSIGNMENT*********************************************/
    
	$("#WORKFLOW_STATE").change(function(){		
		newStatus=$("#WORKFLOW_STATE").val();
		if(oldStatus!=newStatus){
		statusChangeFlag=1;
		}		
		checkWorkflowState();			
		//check for auto assignment enabled or not
		//If enabled and status is helpdesk queue ,then disable the assignto field
		//if not enabled ,then enable the assigned to field
		
		if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==1){			
			$("#ASSIGNED_TO").attr("disabled",true);
			$("#FILTERLOGGEDINUSERS").attr("disabled",true);
		}else if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==0){			
			$("#ASSIGNED_TO").attr("disabled",false);		
			$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
		}
	});
	$("#ASSIGNED_GROUP").change(function(){
		/*Check for auto assignment enabled or not-	If enabled and status is helpdesk queue ,then disable the assignedto field,if not enabled ,then enable the assigned to field	*/
		var groupID=$("#ASSIGNED_GROUP").val();	
			if($("#ASSIGNED_GROUP").val()!=""){				
				isAutoAssignmentReqdFlag=getIsAutoAssignedActive(groupID);				
			}
		
		newStatus = $("#WORKFLOW_STATE").val();
		if(newStatus == "Open"){			
			$("#ASSIGNED_TO").attr("disabled",true);
		}
			
		if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==1){			
			$("#ASSIGNED_TO").attr("disabled",true);
			$("#FILTERLOGGEDINUSERS").attr("disabled",true);
		}else if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==0){			
			$("#ASSIGNED_TO").attr("disabled",false);			
			$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
		}
		if($("#FUNCTION_ID option:selected").text()=="" || $("#FUNCTION_ID option:selected").text()==""){
			if($("#ASSIGNED_GROUP").val()!=""){
				assignedGroupChangeFlag=true;	
			}		
		}else{				
		getGroupMembers('group');	
		}
		AddValuetoArray("ASSIGNED_GROUP",ChangedArray);
		
	});	
	$("#ASSIGNED_TO").change(function(){	
		newAssignedTo=$("#ASSIGNED_TO").val();
		if(oldAssignedTo!=newAssignedTo){
		assignedToChangeFlag=1;
		}		
	});	
	$("#PRIORITY_ID").change(function(){			
		newPriority=$("#PRIORITY_ID").val();
		if(oldPriority!=newPriority){
		priorityChangeFlag=1;
	}	
		
	});
	/********************AUTO ASSIGNMENT*********************************************/
	$("#FUNCTION_ID").change(function(){
			var selectedFunction = this.options[this.selectedIndex].text;
	
		if($("#FUNCTION_ID").val()==""){
				$("#CATEGORY_ID").html("<option value=''>--Please Select Function--</option>");
	    		$("#SUB_CATEGORY_ID").html("<option value=''>--Please Select Category--</option>");
	    		$("#ASSIGNED_GROUP").html("<option value=''>--Please Select Function--</option>");
	    		$("#ASSIGNED_TO").html("<option value=''>--Please Select--</option>");
	    }
		else if(selectedFunction =="Function Correction Required"){
			$("#CATEGORY_ID").attr("disabled",true);
			$("#SUB_CATEGORY_ID").attr("disabled",true);
			$("#LOCATION_ID").attr("disabled",true);
			$("#FILTER_GROUP_LOCATION").attr("disabled",true);
			
    		$("#CATEGORY_ID").html("<option value=''>--Please Select Category--</option>");
    		$("#SUB_CATEGORY_ID").html("<option value=''>--Please Select Category--</option>");

			AddValuetoArray("CATEGORY_ID",ChangedArray);
			AddValuetoArray("SUB_CATEGORY_ID",ChangedArray);
			
		}else{
			$("#CATEGORY_ID").removeAttr("disabled");
			$("#SUB_CATEGORY_ID").removeAttr("disabled");
			$("#LOCATION_ID").removeAttr("disabled");
			$("#FILTER_GROUP_LOCATION").removeAttr("disabled");
			GetCategories(this,'CATEGORY_ID');
		} 
		if(selectedFunction == "Information Systems" || selectedFunction == "IT Infrastructure Management" || selectedFunction == "CHCS Applications"){
			$("#FILTER_GROUP_LOCATION_TR").show();
		}else{
			$("#FILTER_GROUP_LOCATION_TR").hide();
		}
		
		workflowstatus(this.options[this.selectedIndex].value);
	});
	$("#CATEGORY_ID").change(function(){
		GetCategories(this,'SUB_CATEGORY_ID');
		checkForOrchestrationChange();
	});
	$("#LOCATION_ID").change(function(){ 
		var selWorkflowText = $("#WORKFLOW_STATE option:selected").text();
		if(selWorkflowText.indexOf("Approv") == -1 && selWorkflowText!="HelpDesk Queue" && $("#FUNCTION_ID option:selected").text()!="Admin"){
			if( $("#FUNCTION_ID option:selected").text()=="Life and Health Operations Canada"){// Added by Nazeeb as part of L2 : 4369
			////TODO 10/16/2014 Nisha
				getHRiLearnGroups();	
			}
		}
		$('#FILTER_GROUP_LOCATION').val($('#LOCATION_ID').val());
		
		newStatus = $("#WORKFLOW_STATE").val();
		if(newStatus == "Open"){			
			$("#ASSIGNED_TO").html("<option value=''>--Please Select--</option>");
		}
	}); 

	
	$("#SUB_CATEGORY_ID").change(function(){
		var subcategoryvalue = this.options[this.selectedIndex].value;
		workflowstatus(subcategoryvalue);
		if(subcategoryvalue!="" && ($("#FUNCTION_ID option:selected").text()=="Life and Health Operations Canada" )){// Added by Nazeeb as part of L2 : 4369 
		////TODO 10/16/2014 Nisha
			$("#LOCATION_ID").removeAttr("disabled");
			filterLocations($("#SUB_CATEGORY_ID :selected").val());
			getHRiLearnGroups();
		}
	});
	
	$("#FILTER_GROUP_LOCATION").change(function(){
		getGroupsForFunction($('#FUNCTION_ID').find('option:selected').val(),$(this).find('option:selected').val());
	});
	
	$("#ON_BEHALF_OF").change(function(){
		IsValidEmpId = false;
	});
	
    $('#OS_DETAILS').val($.client.os);
	$('#BROWSER_DETAILS').val($.client.browser +" "+ $.client.browserversion);


   
	var IS_MASTER_LINK=$("#IS_MASTER_LINK").val();	
		
	if(logged_user_role!="User"){
		 $("#Link").removeAttr("disabled");
		 $("#DeLink").removeAttr("disabled");
		 $(".Link").colorbox();
		 if(IS_MASTER_LINK == 'No'){
		
		if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
			$("#Edit").hide();
			$("#Update").hide();
			$("#DeLink").hide();
			$("#Link").hide();
		}else if($("#WORKFLOW_STATE option:selected").text()=="HelpDesk Queue" ||$("#WORKFLOW_STATE option:selected").text()=="Waiting For User Response"
			||$("#WORKFLOW_STATE option:selected").text()=="Waiting For L1 Executive Response" || $("#WORKFLOW_STATE option:selected").text()=="Waiting For IT Function Head Approval"
				||$("#WORKFLOW_STATE option:selected").text()=="Waiting For Infosec Approval" ||$("#WORKFLOW_STATE option:selected").text()=="Waiting For Client Response" ||$("#WORKFLOW_STATE option:selected").text()=="Waiting For IS Engineer Response"
					||$("#WORKFLOW_STATE option:selected").text()=="Waiting For IT Engineer Response" ||$("#WORKFLOW_STATE option:selected").text()=="Sent For Approval" || $("#WORKFLOW_STATE option:selected").text()=="Waiting for CR Manager Approval" || $("#WORKFLOW_STATE option:selected").text()=="Waiting For Level1 Approval" || $("#WORKFLOW_STATE option:selected").text()=="Waiting For Level1 Approval"){ 
			$("#DeLink").hide();
			$("#Link").hide();
		}
		else{
		$("#Link").show();
		$("#DeLink").hide();
		}
	}else{	
		if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
			$("#Edit").hide();
			$("#Update").hide();
			$("#DeLink").hide();
			$("#Link").hide();
		}else if($("#WORKFLOW_STATE option:selected").text()=="HelpDesk Queue"){
			$("#DeLink").hide();
			$("#Link").hide();
		}else{	
		$("#Edit").show();
		$("#Update").show();
		$("#DeLink").show();	
		$("#Link").hide();
		}
	}
	} 
	
	checkStatusOfTicket();
	assignedToOnLoad = $("#ASSIGNED_TO option:selected").val();

	if (todoAction == 'edit' && parent.isInEditModeOpen) {
		LockTicket(); 
	}
	
/**Added by Poovamma(716302) to pass SUBJECT value to the Search Part**/
	//var subjectValue=$("#SUBJECT").val();	
	//subjectValue=subjectValue.replace(/!qt/g,"%22");
	//subjectValue=subjectValue.replace(/\"/g,"%22");
	/*Condition added to Check if the User has Multiple Roles to open the Right Iframe*/
	if(roleListSize>1 && logged_user_role!="User"){
	parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject=" + subjectVal_Search + "&userName=" + username + "&empid=" + userid + "&ticketID=" + id + "&menuID=" +menuId;
	parent.myLayout.open('east');
	}
	/*End of Condition added to Check if the User has Multiple Roles to open the Right Iframe*/
/**End Added by Poovamma(716302) to pass SUBJECT value to the Search Part**/
//Removal of 'Busy with Other Activities' from OUT SLA Reason drop down for IT/IS/CHCS function.
if($("#FUNCTION_ID").find("option:selected").val()=='1'||$("#FUNCTION_ID").find("option:selected").val()=='256'||$("#FUNCTION_ID").find("option:selected").val()=='799')
{
	
	if($("#OUT_OF_SLA_REASON").find("option:selected").val()==''||
	$("#OUT_OF_SLA_REASON").find("option:selected").val()=='10')
	{
		$('#OUT_OF_SLA_REASON option').each(function(){
			if (this.value == '10') 
				{
					$("#OUT_OF_SLA_REASON option[value='10']").remove(); 
				}
			});
	}
}
if(($("#FUNCTION_ID :selected").text()=="Life and Health Operations Canada") && $("#WORKFLOW_STATE option:selected").text()=="Out of Operation Hours"){
	$("#editAccess").show();
	$("#Link").hide();
	hdGroupForFinance();
}


if($("#SOURCE option:selected").text()!="iTrack"){
	$("#iTrackLinkTR").hide();
}
var selectedSubCategory=$("#SUB_CATEGORY_ID :selected").val();
//var category=categories.split(',');


if(logged_user_role=="User"){
	$("#TOTAL_SLA_TIME_TR").hide();
	$("#TIME_REMAINING_TR").hide();
}


if($("#TIME_REMAINING").val()==""){
	$("#TIME_REMAINING_TR").hide();
}
if(logged_user_role=="User"){
	$("#MASTER_PROJECT_ID_TR").hide();
	
	if($.trim($("#LEVEL_3_PROJECT_ID").val()).length!=0){
		$("#LEVEL2_PROJECT_ID_TR").hide();
		$("#PROJECT_ID_TR").hide();
	}else if($.trim($("#LEVEL_2_PROJECT_ID").val()).length!=0){
		$("#LEVEL3_PROJECT_ID_TR").hide();
		$("#PROJECT_ID_TR").hide();
	}else{
		$("#LEVEL2_PROJECT_ID_TR").hide();
		$("#LEVEL3_PROJECT_ID_TR").hide();
	}
	
}else{
	$("#PROJECT_ID_TR").hide();
	
	if($.trim($("#PROJECT_NAME").val()).length==0){
		$("#MASTER_PROJECT_ID_TR").hide();
	}
	if($.trim($("#LEVEL_2_PROJECT_NAME").val()).length==0){
		$("#LEVEL2_PROJECT_ID_TR").hide();
	}
	if($.trim($("#LEVEL_3_PROJECT_NAME").val()).length==0){
		$("#LEVEL3_PROJECT_ID_TR").hide();
	}
}

if($("#PROJECT_ID").val()=="None(0)" || $("#PROJECT_NAME").val()=="None(0)"){
	$("#PROJECT_ID").val("None");
	$("#PROJECT_NAME").val("None");
	$("#MASTER_PROJECT_ID_TR").hide();
	$("#PROJECT_ID_TR").show();
}
					if (($("#SOURCE option:selected").text() == "iTrack") && $('#WORKFLOW_STATE').val()!="Resolved/Closed"
							&& $("#CORE_ID").val()!=0 && $("#ALLOW_CLOSEABLE_FLAG").val()!="Y") {
						$('#WORKFLOW_STATE option')
								.each(
										function() {
											if (this.value == 'Resolved/Closed') {
												$(
														"#WORKFLOW_STATE option[value='Resolved/Closed']")
														.remove();
											}
										});
						if(logged_user_role!="User" && ($('#WORKFLOW_STATE').val()=="Work In Progress" || $('#WORKFLOW_STATE').val()=="Assigned" || $('#WORKFLOW_STATE').val()=="3rd Party Resolved")){
						$("#ASSET_MSG_TR").show();
						setInterval("$('.blinkClass').fadeOut(500).fadeIn(500);",2000);
					}
					}
					if ($("#IS_ORCH_REQUIRED").val() == "true"
							&& $('#WORKFLOW_STATE').val() == "Waiting For User Response" && logged_user_role=="User") {
						$("#ORCH_MSG_TR").show();
						setInterval("$('.blinkText').fadeOut(500).fadeIn(500);",2000);
					}
					

					if ($("#WORKFLOW_STATE option:selected").text() == "Need More Info" || $("#WORKFLOW_STATE option:selected").text() == "Information Provided" || $("#WORKFLOW_STATE option:selected").text()=="Waiting For Level1 Approval" || $("#WORKFLOW_STATE option:selected").text()=="Waiting For Level2 Approval") {
						$("#DeLink").hide();
						$("#Link").hide();
						$("#editAccess").hide();

}
					
					var requestedBy=$("#REQUESTED_BY").val();
					requestedBy=requestedBy.substring(requestedBy.indexOf("(")+1,requestedBy.indexOf(")"));
					if(logged_user_role=="User" && requestedBy!=looged_user_id && (menuId==94 || menuId==51)){
						$("#editAccess").hide();
					}
					

});


function getFormattedDate(date) {
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear().toString().slice(2);
    return day + '-' + month + '-' + year;
}
function AddValuetoArray(value,array){
	if(jQuery.inArray(value, array)==-1){
		array.push(value);
	}
}
function GetCategories(obj,ElementId){

			var CategoryId = obj.options[obj.selectedIndex].value;
			if(($("#"+obj.id+" :selected").text()=="IT Infrastructure Management") && ElementId=="CATEGORY_ID"){
					$("#PRIORITY_ID_TR").show();
					getGroupsForFunction(CategoryId,$("#LOCATION_ID").find("option:selected").val());
					if($("#"+obj.id).val()!=mainFunctionID){
						$("#BUILDING").removeAttr("disabled");
						$("#FLOOR").removeAttr("disabled");
						$("#CUBICLE_CODE").removeAttr("disabled");
					}
					
			}else if(ElementId=="CATEGORY_ID"){
					$("#PRIORITY_ID_TR").show();
					getGroupsForFunction(CategoryId,$("#LOCATION_ID").find("option:selected").val());
					$("#BUILDING").attr("disabled",true);
					$("#FLOOR").attr("disabled",true);
					$("#CUBICLE_CODE").attr("disabled",true);
			}else{
				if(ElementId=="SUB_CATEGORY_ID"){
					$("#SUB_CATEGORY_ID").html("<option value=''>--Please Select Category--</option>"); 
				}
			}
			if(ElementId=="CATEGORY_ID"){ 
				if($("#"+obj.id+" :selected").text()=="HR"){
					$("#DEPT_ID").removeAttr("disabled");
					$("#DEPT_ID_TR").show();
					if($("#DEPT_ID").val()==""){						
					getAllDepartments();
					}
				}else{
					$("#DEPT_ID_TR").hide();
				}
				if($("#"+obj.id+" :selected").text()=="Admin"){
					$("#ODC_ID_TR").show();
				}else{
					$("#ODC_ID_TR").hide();
				}
				$("#CATEGORY_ID").html("");
	    		$("#SUB_CATEGORY_ID").html("<option value=''>--Please Select Category--</option>");

	    		AddValuetoArray("CATEGORY_ID",ChangedArray);
	    		AddValuetoArray("SUB_CATEGORY_ID",ChangedArray);
				getLocationsForFunction(CategoryId); 
			}

			if(CategoryId!=""){
				$.getJSON('getHelpDeskCategories.htm',{CategoryId:CategoryId},function(categories){
					if (categories.status && categories.status=="Error") {	
						parent.jbarOnFailure(categories.message);
					}else{
					var CategoryObj = document.getElementById(ElementId);
						CategoryObj.innerHTML = "";
	
						var optn = document.createElement("OPTION");
			    		optn.value = "";
			    		optn.text = "--Please Select--";
			    		CategoryObj.options.add(optn);
	
			    		if(ElementId=="SUB_CATEGORY_ID"){
				    		SubCategoryApprovalList = new Array();
				    		SubCategoryRecomendPriorityList = new Array();
			    		}
			    		 $.getJSON('getCategoryRoleMapping.htm',{},function(categoryRoleMapping){	

								for(var i=0;i<categories.length;i++){
										var optn = document.createElement("OPTION");
										if(categories[i].SPECIAL_PREFERENCES=='N'){
												optn.value = categories[i].CATEGORY_ID;
												optn.text = categories[i].NAME;
												CategoryObj.options.add(optn);
										}if(categories[i].SPECIAL_PREFERENCES=='Y'){
											var catID=categories[i].CATEGORY_ID;
											var catName=categories[i].NAME;			
											for(var j=0;j<categoryRoleMapping.length;j++){
												if((categoryRoleMapping[j].NAME==loginrole) && (categoryRoleMapping[j].CATEGORY_ID==catID)){
													optn.value =catID;
													optn.text =catName;													
													CategoryObj.options.add(optn);
												}
											}										
										}										
										if(ElementId=="SUB_CATEGORY_ID"){												
											SubCategoryApprovalList[categories[i].CATEGORY_ID] = categories[i].IS_CHANGE_REQUEST;
											SubCategoryRecomendPriorityList[categories[i].CATEGORY_ID] = categories[i].RECOMMENDED_PRIORITY;
										
										}
								}
					   });
					}
				});
			}			
}

function getGroupsForFunction(function_id,location_id){	
	
	if(function_id!="" && location_id!="" && ($("#FUNCTION_ID option:selected").text()!="Finance" || $("#FUNCTION_ID option:selected").text()!="Admin")){
		$.getJSON('hdgetGroupsforFunction.htm',{functionid:function_id,locationid:location_id},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var i=0;i<response.length;i++){
				options.push('<option value="' + response[i].GROUP_ID + '">'
						+ response[i].GROUP_NAME + '</option>');
			}
			$("#ASSIGNED_GROUP").html(options.join(''));
			$("#ASSIGNED_TO").html('<option value="">--Please Select--</option>'); 
				if(response.length>=1 && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue"){ 
					$("#ASSIGNED_GROUP").removeAttr("disabled"); 
					$("#ASSIGNED_TO").removeAttr("disabled");
				}
				
			}
		});
	}
	
	
}



function hdGroupForAdminFinance(){
	if($("#FUNCTION_ID option:selected").text()=="Finance" || $("#FUNCTION_ID option:selected").text()=="Admin" || $("#FUNCTION_ID option:selected").text()=="Travel And Transport"){
		var locationid=$("#LOCATION_ID option:selected").val();
		var categoryid=$("#SUB_CATEGORY_ID option:selected").val();
		$.getJSON('hdgetGroupsforFinanceAdminFunction.htm',{categoryid:categoryid,locationid:locationid},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var i=0;i<response.length;i++){
				options.push('<option value="' + response[i].GROUP_ID + '">'
						+ response[i].GROUP_NAME + '</option>');
			}
			$("#ASSIGNED_GROUP").html(options.join(''));
			$("#ASSIGNED_TO").html('<option value="">--Please Select--</option>'); 
				if(response.length>=1 && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue"){ 
					$("#ASSIGNED_GROUP").removeAttr("disabled"); 
					$("#ASSIGNED_TO").removeAttr("disabled");
				}
			}
			
		});
		
	}
	
}

function hdGroupForFinance(){
	if($("#FUNCTION_ID option:selected").text()=="Finance" || $("#FUNCTION_ID option:selected").text()=="Admin" || $("#FUNCTION_ID option:selected").text()=="Travel And Transport"){
		var locationid=$("#LOCATION_ID option:selected").val();
		var categoryid=$("#SUB_CATEGORY_ID option:selected").val();
		$.getJSON('hdgetGroupsforFinanceAdminFunction.htm',{categoryid:categoryid,locationid:locationid},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var i=0;i<response.length;i++){
				options.push('<option value="' + response[i].GROUP_ID + '">'
						+ response[i].GROUP_NAME + '</option>');
			}
			$("#ASSIGNED_GROUP").html(options.join(''));
			$("#ASSIGNED_GROUP").val(oldAssignedGroup);
			$("#ASSIGNED_TO").val(oldAssignedTo);
			
			
			}
			
		});
		
	}
	
}

function workflowstatus(subcategoryid){
	
	var functionName = $("#FUNCTION_ID option:selected").text();
	if(functionName=="HR" && ($("#DEPT_ID").val()==null || $("#DEPT_ID").val()=="")){	
		getAllDepartments();		
	}else{
		$("#FUNCTION_ID").hideAll(); 
	}
	if(subcategoryid!=""){
		var prevworkflow_status = $("#WORKFLOW_STATE").val();
		//By Nisha --changed for HR Helpdesk --start
		var json = '{"FUNCTION_NAME":"'+$("#FUNCTION_ID option:selected").text()+'","SUBCATEGORY_ID":"'+subcategoryid+'","OLD_SUBCATEGORY_ID":"'+mainSubCategoryID+'","OLD_FUNCTION":"'+mainFuncionValue+'","DEPT_ID":"'+$("#DEPT_ID").val()+'","OLD_WORKFLOW_STATE":"'+mainWorkflowState+'","TICKET_ID":"'+$("#TICKET_ID").val()+'","IS_APPROVAL_REQUIRED":"'+approvalExceptionFlag+'","CATEGORY_ID":"'+$("#CATEGORY_ID option:selected").val()+'"}';
		//By Nisha --changed for HR Helpdesk --end
		$.getJSON('hdgetApprovalStateforTicket.htm',{jsonString:json},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
				var options = [];
				for(var i=0;i<response.length;i++){
					options.push('<option value="' + response[i] + '">'
							+ response[i] + '</option>');
				}
				if($("#WORKFLOW_STATE").val()!=response[0]){
					AddValuetoArray("WORKFLOW_STATE",ChangedArray);
				}
				$("#WORKFLOW_STATE").html(options.join(''));
				var curworkflow_status = response[0];
				if(curworkflow_status.indexOf("Approv") != -1 || curworkflow_status=="HelpDesk Queue"){ 					
					newStatus=curworkflow_status;
					statusChangeFlag=1;
					$("#ASSIGNED_GROUP").attr("disabled",true);
					$("#ASSIGNED_TO").attr("disabled",true);					
					$("#FILTER_GROUP_LOCATION").attr("disabled",true);
					$("#ASSIGNED_GROUP").val("");
					newAssignedTo=null;
					assignedToChangeFlag=1;
					$("#ASSIGNED_TO").val("");
					$("#FILTERLOGGEDINUSERS").attr("disabled",true);
					AddValuetoArray("ASSIGNED_GROUP",ChangedArray); 
					AddValuetoArray("ASSIGNED_TO",ChangedArray);
				}else {
					$.each(editmaparray, function(index, value) { 
						if(jQuery.trim(value) == "ASSIGNED_GROUP"){
							$("#ASSIGNED_GROUP").removeAttr("disabled");
							$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
							$("#FILTER_GROUP_LOCATION").removeAttr("disabled");
						}
						if(jQuery.trim(value) == "ASSIGNED_TO"){
							$("#ASSIGNED_TO").removeAttr("disabled");
							$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
							$("#FILTER_GROUP_LOCATION").removeAttr("disabled");
						}
				    });
				}
				if(mainWorkflowState!=curworkflow_status){
					AddValuetoArray("WORKFLOW_STATE",ChangedArray);
				}
			}
		});
		$("#RECOMMENDED_PRIORITY").val($("#PRIORITY_ID").children('option[text="'+SubCategoryRecomendPriorityList[subcategoryid]+'NA"]').val());
	}
}

function getGroupMembers(from){
	
	/*********Added by Poovamma(716302)to show the right side Accordion for Work List***************/	
		parent.$("#myAccordion_serviceWindow").show();
		if(parent.$("#myAccordion_serviceWindow").find('h2').hasClass("ui-state-default"))
		{
			parent.$("#myAccordion_serviceWindow").filter(":has(.ui-state-default)").accordion("activate", 0);
		}	 
		parent.$("#myAccordion_searchWindow").filter(":has(.ui-state-active)").accordion("activate",false);
		
	/*********End Added by Poovamma(716302) to show the right side Accordion For Worklist************/
	if($("#FUNCTION_ID").val()==""){
		$("#FUNCTION_ID").focus();
		$("#FUNCTION_ID").showerrormessage({message:"* Mandatory"});
		return; 
	}else{
		$("#FUNCTION_ID").hideAll();
	}
	var elt = document.getElementById('ALTERNATE_CONTACT_NO');
	var groupName = $("#ASSIGNED_GROUP option:selected").val();
	var type="filter";
	  if($("#FILTERLOGGEDINUSERS").attr("checked")){
		  type="All";
	  }
	if(groupName!=""){
		var functionID = $("#FUNCTION_ID option:selected").val();
		var locationID = $("#FILTER_GROUP_LOCATION option:selected").val();
				
		$.ajaxSetup({ cache: false });
		var membURL='hdgetGroupmembers.htm?timeForReq='+new Date().getTime();
		$.getJSON(membURL, {
			groupId : groupName,
			type : type,
			functionid : functionID,
			locationid : locationID,
			from : from
		}, function(response) {
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
			var data = response.MEMBER_DETAILS;
				if (data.length != 0) {			
					var options = [];
					var roles = new Array();
					//options.push('<option value="">--Please Select--</option>');
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

			
			if(from!="checkbox"){
		    	var servicewindowhtml='<TABLE class="myDataTable"><TR><TH></TH><TH><B>Mon</B></TH><TH><B>Tue</B></TH><TH><B>Wed</B></TH><TH><B>Thu</B></TH><TH><B>Fri</B></TH><TH><B>Sat</B></TH><TH><B>Sun</B></TH></TR>';
					servicewindowhtml+='<TR><TH alt="Start Time" title="Start Time"><B>ST:</B></TH><TD id="MONSTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="TUESTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD>';
					servicewindowhtml+='<TD id="WEDSTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="THUSTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="FRISTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD>';
					servicewindowhtml+='<TD id="SATSTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="SUNSTART_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD></TR><TR><TH alt="End Time" title="End Time"><B>ET:</B></TH><TD id="MONEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="TUEEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="WEDEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="THUEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD>';
					servicewindowhtml+='<TD id="FRIEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="SATEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD><TD id="SUNEND_TIME"><IMG SRC="images/Wrong.png" WIDTH="10" HEIGHT="10" BORDER="0"  ALIGN="CENTER"></TD></TR>';
					
				$('#serviceWindowDiv', window.parent.document).html(servicewindowhtml); 
				
				
	        	var servicewindow = response.SERVICE_WINDOW_DETAILS;
	        	for(var i=0;i<servicewindow.length;i++){
	        		var starttime = servicewindow[i].START_TIME;
	        			starttime = starttime.substring(0,5);
	        		var endtime = servicewindow[i].END_TIME;
	        			endtime = endtime.substring(0,5);
	        		$("#"+servicewindow[i].DAY+"START_TIME", window.parent.document).html(starttime);
	        		$("#"+servicewindow[i].DAY+"END_TIME", window.parent.document).html(endtime);
	        	}

		    }
			    var exists = false;
				$('#WORKFLOW_STATE option').each(function(){
				    if (this.value == 'Out of Operation Hours') {
				        exists = true;
				        return false;
				    }
				});
			var functionName  = $("#FUNCTION_ID :selected").text();
		    if(from!="checkbox" && exists && !response.OPERATION_HOURS_AVAILABLE && functionName!="HR" && functionName!="IGATE Corporate University"){
		    	if(confirm("Opertaion Hours are not available for this Group, so do u want to move the ticket to Out of Operation Hours status?")){
		    		$("#WORKFLOW_STATE option[value='Out of Operation Hours']").attr("selected",true);
		    		AddValuetoArray("WORKFLOW_STATE",ChangedArray);
		    		$("#ASSIGNED_TO").attr("disabled",true);		
		    		$("#FILTERLOGGEDINUSERS").attr("disabled",true);
					AddValuetoArray("ASSIGNED_TO",ChangedArray);
		    	}else{
		    		if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==1){			
		    			$("#ASSIGNED_TO").attr("disabled",true);
		    			$("#FILTERLOGGEDINUSERS").attr("disabled",true);
		    		}else if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==0){			
		    			$("#ASSIGNED_TO").attr("disabled",false);		
		    			$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
		    		}
		    	}
		    }
			}
		});
		var selfunctionName  = $("#FUNCTION_ID :selected").text();
		var EngineerScoreHeader='';
		if(isAutoAssignmentReqdFlag==0){
			EngineerScoreHeader='Engineer Score';
		}else{
			EngineerScoreHeader='Logged In Engineer Score';
		}
			if(selfunctionName!="HR"){
			
		var workLoadHTML = "<table class='myDataTable' width='100%'><tr><th colspan='5'>"+EngineerScoreHeader+"</th></tr><tr><th>Executive</th><th alt='Total Score' title='Total Score'>T</th><th alt='High Priority Score' title='High Priority Score'>H</th><th alt='Medium Priority Score' title='Medium Priority Score'>M</th><th alt='Low Priority Score' title='Low Priority Score'>L</th></tr>";
			
			$.ajaxSetup({ cache: false });
			$.getJSON('groupWorkLoad.htm', {
				groupName : groupName
			}, function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}else{
				if (data.length != 0) {
					var options = [];
					$.each(data, function(item) {
						
					workLoadHTML = workLoadHTML+"<tr><td><a href=\"#\" onclick=\"getWorkLoad('"+data[item].ASSIGNED_TO+"','"+data[item].NAME+"')\" >"+data[item].NAME+"</a></td><td>"+ data[item].total+"</td><td>"+ data[item].high+"</td><td>"+ data[item].medium+"</td><td>"+ data[item].low+"</td></tr>";
					});
					workLoadHTML=workLoadHTML+"</table>";
					$('#firstDivId', window.parent.document).html(workLoadHTML);
					$("#secondDivId", window.parent.document).html("");
					parent.myLayout.initContent('east');
					parent.myLayout.open('east');
				}else{
					workLoadHTML = workLoadHTML+"<tr><td colspan=\"5\" align=\"center\">No record found</td></tr>";
					workLoadHTML=workLoadHTML+"</table>";
					$('#firstDivId', window.parent.document).html(workLoadHTML); 
					
					$("#secondDivId", window.parent.document).html("");
					parent.myLayout.initContent('east');
					parent.myLayout.open('east');
				}
				}
			});
		}else{
			$('#firstDivId', window.parent.document).html(""); 
			$("#secondDivId", window.parent.document).html("");
		}
	}else{
		$("#ASSIGNED_TO").html('<option value="">--Please Select--</option>');
	}
	if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==1){			
		$("#ASSIGNED_TO").attr("disabled",true);
		$("#FILTERLOGGEDINUSERS").attr("disabled",true);
	}else if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==0){			
		$("#ASSIGNED_TO").attr("disabled",false);		
		$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
	}
}



var inEditMode = false;
function LockTicket(){

	if(!inEditMode){
		if($('#DeLink').is(':visible')){
			editmaparray= new Array();
		editmaparray.push("ADDITIONAL_INFO");
		}
		if(editmaparray.length>=1){			
			
			var json =getJSONtoLockTicket();
			//var URL="lockIHDTicket.htm?jsonString="+json;
			$.postJSON("lockIHDTicket.htm",{jsonString:json},function(data){
				if (data.status && data.status=="success" && data.length != 0) {
					if(($("#EMERGENCY_TYPE option:selected").text()!="NA") && ($("#SUB_STATUS option:selected").text()=="Submitted to DBA"
							|| $("#SUB_STATUS option:selected").text()=="Work In Progress")){										 
						 if(logged_user_role=="IT Engineer" || logged_user_role=="IT Manager"){
							 showInEditMode();
						 }else{
							 parent.jbarOnFailure("Permission denied to edit the ticket");
						 }
					}else{
						showInEditMode();
					}
				}else if(data.LOCKED_BY==userid){
					if(($("#EMERGENCY_TYPE option:selected").text()!="NA") && ($("#SUB_STATUS option:selected").text()=="Submitted to DBA"
							|| $("#SUB_STATUS option:selected").text()=="Work In Progress")){										 
						 if(logged_user_role=="IT Engineer" || logged_user_role=="IT Manager"){
							 showInEditMode();
						 }else{
							 parent.jbarOnFailure("Permission denied to edit the ticket");
						 }
					}else{
						showInEditMode();
					}
				}else{
					parent.jbarOnFailure("The ticket is locked by "+data.NAME+"( "+data.LOCKED_BY+" )");
				}
			});
		}else{
			parent.jbarOnFailure("Permission denied to edit the ticket");
		}
	}
}

function showInEditMode(){
	inEditMode = true;
	alwaysDisabledArray.push("PRIVATE_NOTES");
	alwaysDisabledArray.push("ADDITIONAL_INFO");
	$.each(editmaparray, function(index, value) { 
		if(jQuery.inArray(jQuery.trim(value), alwaysDisabledArray)==-1){
			$("#"+jQuery.trim(value)).removeAttr("disabled");
			var elementID = jQuery.trim(value);
			
			if(elementID=="ASSIGNED_TO"){
				$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
				getGroupMembersOnload("group");
			}
			if(elementID=="ASSIGNED_GROUP"){
				$("#FILTER_GROUP_LOCATION").removeAttr("disabled");
			}	
			if(elementID=="FUNCTION_ID"){
					if($("#FUNCTION_ID").val()=="632"){
					if($("#DEPT_NAME").val()==''){
					$("#deptTD").hide();
					$("#DEPT_ID").show();
					$("#DEPT_ID").removeAttr("disabled");
					}
				} 
				if($("#CREATED_BY").val()=="SYSTEM(SYSTEM)" && $("#REQUESTED_BY").val()=="SYSTEM(SYSTEM)"){ 
					$("#ON_BEHALF_OF").removeAttr("disabled");
					$("#validateEmployee").show();
				}
				//Modified by Sankari for Web Services
				if($("#SOURCE option:selected").text()=="iTrack"){
					$("#FUNCTION_ID").attr("disabled",true);										
				}
			}
			//Added by Sankari for Web Services
			if((elementID=="CATEGORY_ID") && ($("#SOURCE option:selected").text()=="iTrack")){
				$("#CATEGORY_ID").attr("disabled",true);
			}
			//Added by Sankari for Web Services
			if((elementID=="SUB_CATEGORY_ID") && ($("#SOURCE option:selected").text()=="iTrack")){
				$("#SUB_CATEGORY_ID").attr("disabled",true);
			}
			//Added by Sankari for Web Services			
			if(($("#SOURCE option:selected").text()=="iTrack") && $("#WORKFLOW_STATE option:selected").text()=="HelpDesk Queue"){				
				$("#BUILDING").removeAttr("disabled");
				$("#FLOOR").removeAttr("disabled");
				$("#CUBICLE_CODE").removeAttr("disabled");
			}
			if($("#SEARCH_REFERENCE").is(':checked')){
				$("#SEARCH_REFERENCE").attr("disabled",true);
			}
			if((jQuery.trim(value)=='DESCRIPTION') ||(jQuery.trim(value)=='ADDITIONAL_INFO')||(jQuery.trim(value)=='RESOLUTION_COMMENTS')||(jQuery.trim(value)=='PRIVATE_NOTES')
			||(jQuery.trim(value)=='OUT_OF_SLA_COMMENTS')||(jQuery.trim(value)=='ONHOLD_COMMENTS')||(jQuery.trim(value)=='REOPEN_COMMENTS')){						
						$("#"+jQuery.trim(value)).removeAttr("disabled");
						$("#"+jQuery.trim(value)).removeAttr("readonly");						
			}
		}		
		/*********************Emergency L1 changes************/

		if($("#SUB_STATUS").val()=="Submitted to DBA"){
			$("#SUB_STATUS").removeAttr("disabled");
		}
		if($("#SUB_STATUS").val()=="Completed"){
			$("#SUB_STATUS").attr("disabled",true);
			//$("#IS_EMERGENCY_TICKET").attr("disabled",true);
			$("#EMERGENCY_TYPE").attr("disabled",true);
			$("#DEPLOYMENT_INSTRUCTION").attr("disabled",true);
			$("#REASON_SEND_BACK").attr("disabled",true);
		}
		if(logged_user_role=="IS Executive" || logged_user_role=="IS Manager"){
			if(($("#EMERGENCY_TYPE option:selected").text()!="NA") && ($("#SUB_STATUS option:selected").text()=="Send Back to IS team")){	
				$('#SUB_STATUS option:nth-child(' + 5 + ')').attr('disabled', 'disabled');
				$('#SUB_STATUS option:nth-child(' + 3 + ')').attr('disabled', 'disabled');
				$("#SUB_STATUS").removeAttr("disabled");
				$('#SUB_STATUS option:nth-child(' + 1 + ')').removeAttr("disabled");
				$('#SUB_STATUS option:nth-child(' + 2 + ')').removeAttr("disabled");
			}
		}
		if(logged_user_role=="IT Engineer" || logged_user_role=="IT Manager"){	
			if($("#SUB_STATUS option:selected").text()=="Work In Progress"){	
				$('#SUB_STATUS option:nth-child(' + 2 + ')').attr('disabled', 'disabled');
			}
			$('#SUB_STATUS option:nth-child(' + 1 + ')').attr('disabled', 'disabled');			
			$('#SUB_STATUS option:nth-child(' + 5 + ')').removeAttr("disabled");
			$('#SUB_STATUS option:nth-child(' + 3 + ')').removeAttr("disabled");
		}
		/*********************Emergency L1 changes************/
    });
	
	
	$('.colorboxpopup').popupWindow({ 
		windowURL:'/iconnect/HELPDESK_UpdateComments.htm', 
		windowName:'swip' ,
		height:300,
		centerScreen:1 
	}); 
	
	var groupIDs=$("#ASSIGNED_GROUP").val();		
	if($("#ASSIGNED_GROUP").val()!=""){		
		isAutoAssignmentReqdFlag=getIsAutoAssignedActive(groupIDs);	
			if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==1){			
				$("#ASSIGNED_TO").attr("disabled",true);
				$("#FILTERLOGGEDINUSERS").attr("disabled",true);
			}else if(oldStatus=="HelpDesk Queue" && isAutoAssignmentReqdFlag==0){			
				$("#ASSIGNED_TO").attr("disabled",false);
				$("#FILTERLOGGEDINUSERS").removeAttr("disabled");
				getGroupMembersOnload("group");
			}
	}

	 if($("#WORKFLOW_STATE option:selected").text()=="HelpDesk Queue" && ($("#FUNCTION_ID").find("option:selected").val()!='1'&& $("#FUNCTION_ID").find("option:selected").val()!='256'&& $("#FUNCTION_ID").find("option:selected").val()!='799' && $("#FUNCTION_ID").find("option:selected").val()!='565' && $("#FUNCTION_ID").find("option:selected").val()!='1080' && $("#FUNCTION_ID").find("option:selected").val()!='729' && $("#FUNCTION_ID").find("option:selected").val()!='632')){
       	getGroupsForFunction($('#FUNCTION_ID').find('option:selected').val(),$('#LOCATION_ID').find('option:selected').val());
       	
       }else if($("#FUNCTION_ID").find("option:selected").val()=='632'){
    	   getHRiLearnGroups();
    	   
       }	 

}

function getJSONtoLockTicket(){

	var TicketId = $("#TICKET_ID").val();
	var jsonString = '{';
		jsonString +='"TICKET_ID":"'+TicketId+'","DATA":"';
	var xmldata = "<data>";
	if(editmaparray.length>=1){
		$.each(editmaparray, function(index, value) {
			var test = value.replace(/^\s+/,"");			
			var val1=$("#"+test).val();
			var val2=val1;
			if((test=="ADDITIONAL_INFO")||(test=='DESCRIPTION') ||(test=='RESOLUTION_COMMENTS')||(test=='PRIVATE_NOTES')
					||(test=='OUT_OF_SLA_COMMENTS')||(test=='ONHOLD_COMMENTS')||(test=='REOPEN_COMMENTS')||(test=='SUBJECT')||(test=='FEEDBACK_COMMENTS')){
				val2=val1.replace(/&/g,"&#38;");
				val2=val2.replace(/</g,"&#60;");
				val2=val2.replace(/>/g,"&#62;");
				val2=val2.replace(/=/g,"&#61;");		
				val2=val2.replace(/!qt/g,"&#34;");
				val2=val2.replace(/\"/g,"&#34;");
				val2=val2.replace(/\'/g,"&#39;");
				val2=val2.replace(/~/g,"&#126;");	
				val2=val2.replace(/\\/g,"&#92;");
				val2=val2.replace(/\//g,"&#47;");			
			}
			var attrtype = $("#"+test).attr("type"); 
	        if(attrtype.indexOf("select") != -1){
	        	xmldata+="<"+jQuery.trim(value)+">"+$("#"+test+" option:selected").text()+"</"+jQuery.trim(value)+">";
	        }else{
	        	xmldata+="<"+jQuery.trim(value)+">"+val2+"</"+jQuery.trim(value)+">";
	        }
		}); 
		if(jQuery.inArray("WORKFLOW_STATE", editmaparray)==-1){
			xmldata+="<WORKFLOW_STATE>"+$("#WORKFLOW_STATE option:selected").text()+"</WORKFLOW_STATE>";
		}
		xmldata+="<ECT>"+$("#ECT_STORAGE").val()+"</ECT>";
		xmldata+="<VERSION_NO>"+$("#VERSION_NO").val()+"</VERSION_NO>";
	}
	xmldata+= "</data>";
    jsonString = jsonString+xmldata+'","MENU_ID":"1"}';	
    jsonString = jsonString.replace(/\n/g,'brlinebreakbreak');
	return jsonString;

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

function UpdateIHDTicket(){  
	
	//Added by 717170 for HR
    var emailaddressVal = $("#COPY_TO").val();
	
    if(emailaddressVal!="" && !validateEmailIds(emailaddressVal)) {	
    	$("#COPY_TO").focus();
    	$("#COPY_TO").showerrormessage({message:"one or more email addresses entered is invalid!"});
        isValidationError =true;
        return false;
    }
    
	if(!validateSpecialCharacters())
		return false;
	var json = getJsonForUpdateTicket();	
	if(json !="Error" && json!=="" && confirmresult){ 
		
		$("#Update").attr('disabled','true');
		$("#Edit").attr('disabled','true');
		$("#UpdateButtonTd").find("input:button").attr('disabled','true'); 
		
		
		$.ajaxSetup({ cache: false });
		$.postJSON("iConnectIHDTicketUpdation.htm?approvalExceptionFlag="+approvalExceptionFlag,{jsonString:json},function(data){
			if (data.status=="Success") {
				if(data.WORKFLOW_STATE=='Resolved/Closed' || menuId=="8"){
					parent.isInEditModeOpen = true;
					parent.$('#centeriframe').attr('src', previous_Url);
					parent.jbarOnSuccess(data.message);
					getEngineerScore();
					return true;		
					}
				else		
				{
				$('#ticketdetailsform').find(':input').each(function(){
			        var elementId = $(this).attr('id');
			        		$(this).attr('disabled','true');
					if((elementId=='DESCRIPTION') ||(elementId=='ADDITIONAL_INFO')||(elementId=='RESOLUTION_COMMENTS')||(elementId=='PRIVATE_NOTES')
							||(elementId=='OUT_OF_SLA_COMMENTS')||(elementId=='ONHOLD_COMMENTS')||(elementId=='REOPEN_COMMENTS')){
							$(this).removeAttr("disabled");
							$(this).attr('readonly','true');
					}
			    });
				ChangedArray = new Array();
				alwaysDisabledArray = new Array();
				inEditMode = false;
				parent.jbarOnSuccess(data.message); 
				parent.isInEditModeOpen = false;
				parent.$('#centeriframe').attr('src', hd_url);		
			}
				getEngineerScore();
			}else if(data.status=="Error"){ 
				$("#Update").removeAttr('disabled');
				$("#Edit").removeAttr('disabled');
				$("#UpdateButtonTd").find("input:button").removeAttr('disabled');    
				parent.jbarOnFailure(data.message)
			}
		});
	}else if(json !="Error"  && confirmresult){
		parent.jbarOnFailure('No Changes Made');
	}
}


function getJsonForUpdateTicket(){
	
	var jsonArray ="";
	var jsonString = "";
	var auditLogJson = "";
	var autoAssignmentFlagJson="";
	var editJsonOrch = "";
	var validationStatus = true ;
		if(ChangedArray.length >0){
			validationStatus = validateTicketDetails();
		}
		
		
	if(ChangedArray.length!=0 && validationStatus && getConfirmResult()){		
		chkAddValuetoArray("CREATED_DATE_STORAGE",removeArray,ChangedArray);
		chkAddValuetoArray("LOCATION_ID",removeArray,ChangedArray);
		chkAddValuetoArray("FUNCTION_ID",removeArray,ChangedArray);
		chkAddValuetoArray("CATEGORY_ID",removeArray,ChangedArray);
		chkAddValuetoArray("SUB_CATEGORY_ID",removeArray,ChangedArray);
		chkAddValuetoArray("ASSIGNED_GROUP",removeArray,ChangedArray);
		chkAddValuetoArray("WORKFLOW_STATE",removeArray,ChangedArray);
		chkAddValuetoArray("PRIORITY_ID",removeArray,ChangedArray);
		chkAddValuetoArray("LOC_DET_ID",removeArray,ChangedArray);
		chkAddValuetoArray("CHANGED_DATE_STORAGE",removeArray,ChangedArray);//Modified by Sali
		chkAddValuetoArray("IS_CHANGE_REQUEST",removeArray,ChangedArray);//Modified by Sali
		chkAddValuetoArray("FILTER_GROUP_LOCATION",removeArray,ChangedArray);//Modified by Sali on Sep 13th
		chkAddValuetoArray("OS_DETAILS",removeArray,ChangedArray);//Modified by Mani on Nov 23th
		chkAddValuetoArray("BROWSER_DETAILS",removeArray,ChangedArray);//Modified by Mani on Nov 13th
		chkAddValuetoArray("PROJECT_ID",removeArray,ChangedArray);//Modified by Mani on April 20th
		chkAddValuetoArray("REQUESTED_BY",removeArray,ChangedArray);//Modified by Mani on April 20th
		chkAddValuetoArray("SOURCE",removeArray,ChangedArray);//Modified by Sankari on June 14th for iTrack integration
		
		AddValuetoArray("CREATED_DATE_STORAGE",ChangedArray);
		AddValuetoArray("LOCATION_ID",ChangedArray);
		AddValuetoArray("FUNCTION_ID",ChangedArray);
		AddValuetoArray("CATEGORY_ID",ChangedArray);
		AddValuetoArray("SUB_CATEGORY_ID",ChangedArray);
		AddValuetoArray("ASSIGNED_GROUP",ChangedArray);
		AddValuetoArray("WORKFLOW_STATE",ChangedArray);
		AddValuetoArray("PRIORITY_ID",ChangedArray);
		AddValuetoArray("VERSION_NO",ChangedArray);
		AddValuetoArray("LOC_DET_ID",ChangedArray);//Added by Sali
		AddValuetoArray("CHANGED_DATE_STORAGE",ChangedArray);//Modified by Sali
		AddValuetoArray("IS_CHANGE_REQUEST",ChangedArray);//Modified by Sali
		AddValuetoArray("FILTER_GROUP_LOCATION",ChangedArray);//Modifed by Sali on Sep 13th
		AddValuetoArray("OS_DETAILS",ChangedArray);//Modified by Mani
		AddValuetoArray("BROWSER_DETAILS",ChangedArray);//Modifed by Mani on Nov 23th
		AddValuetoArray("ODC_ID",removeArray);//Modified by Mani on Dec 1st to update the odc in different table
		AddValuetoArray("DEPT_ID",ChangedArray);//Added by Poovamma on May 08,2012 to update the approver details
		AddValuetoArray("DEPT_ID",removeArray);//Modified by Mani on Dev 1st to update the odc in different table
		AddValuetoArray("PROJECT_ID",ChangedArray);//Modified by Mani on April 20th to update the approver details 
		AddValuetoArray("REQUESTED_BY",ChangedArray);//Modified by Mani on April 20th to update the approver details
		AddValuetoArray("SOURCE",ChangedArray);//Modified by Sankari on June 14th for iTrack integration
		//AddValuetoArray("IS_EMERGENCY_TICKET",removeArray);//Added for Emergency L1
		//AddValuetoArray("EMERGENCY_TYPE",removeArray);//Added for Emergency L1
		//AddValuetoArray("LEVEL2_ASSIGNED_ENGINEER",removeArray);//Added for Emergency L1
		//AddValuetoArray("SUB_STATUS",removeArray);//Added for Emergency L1
	//AddValuetoArray("SUB_PROJECT_ID",ChangedArray);
		/*********************Emergency L1 changes************/
			if($("#SUB_STATUS").val()=="Completed" && $("#LEVEL2_ASSIGNED_ENGINEER").val()==""){	
				$("#LEVEL2_ASSIGNED_ENGINEER").val(looged_user_id+"("+username+")");
				AddValuetoArray("LEVEL2_ASSIGNED_ENGINEER",ChangedArray);
			}
			if($("#SUB_STATUS").val()=="Work In Progress" && $("#LEVEL2_ASSIGNED_ENGINEER").val()==""){	
				$("#LEVEL2_ASSIGNED_ENGINEER").val(looged_user_id+"("+username+")");
				AddValuetoArray("LEVEL2_ASSIGNED_ENGINEER",ChangedArray);
			}
			if($("#SUB_STATUS").val()=="Send Back to IS team" && $("#LEVEL2_ASSIGNED_ENGINEER").val()==""){	
				$("#LEVEL2_ASSIGNED_ENGINEER").val(looged_user_id+"("+username+")");
				AddValuetoArray("LEVEL2_ASSIGNED_ENGINEER",ChangedArray);
			}
		/*********************Emergency L1 changes************/

		var TicketId = $("#TICKET_ID").val();
		jsonString = '{';
			jsonString +='"TICKET_ID":"'+TicketId+'"';
			auditLogJson = jsonString; 
		$.each(ChangedArray, function(index, value) { 			
			var test = value.replace(/^\s+/,"");
			var attrtype = $("#"+test).attr("type"); 
	        if(attrtype.indexOf("select") != -1){				
	        	jsonString+=',"'+jQuery.trim(value)+'":"'+$("#"+test+" option:selected").val().replace(/"/g,"'")+'"';
	        	auditLogJson +=',"'+jQuery.trim(value)+'":"'+$("#"+test+" option:selected").text().replace(/"/g,"'")+'"';
				/******************Emergency L1**********/
				if(test=='EMERGENCY_TYPE'){	
					if($("#EMERGENCY_TYPE option:selected").text()=="NA")
						$("#SUB_STATUS").val("NA");
					jsonString+=',"SUB_STATUS":"'+$("#SUB_STATUS option:selected").text()+'"';
	        		auditLogJson +=',"SUB_STATUS":"'+$("#SUB_STATUS option:selected").text()+'"';
				}
				if(test=='SUB_STATUS'){
					if(logged_user_role=="IT Engineer" || logged_user_role=="IT Manager"){
						$("#LEVEL2_ASSIGNED_ENGINEER").val(looged_user_id+"("+username+")");
						jsonString+=',"LEVEL2_ASSIGNED_ENGINEER":"'+$("#LEVEL2_ASSIGNED_ENGINEER").val()+'"';
		        		auditLogJson +=',"LEVEL2_ASSIGNED_ENGINEER":"'+$("#LEVEL2_ASSIGNED_ENGINEER").val()+'"';
					}
					if(($("#SUB_STATUS option:selected").text()=="Submitted to DBA") 
							|| ($("#SUB_STATUS option:selected").text()=="Send Back to IS team") 
							|| ($("#SUB_STATUS option:selected").text()=="Completed"))
						jsonString+=',"'+'IS_EMERGENCY_TICKET'+'":"'+'Yes'+'"';
				}
				/******************Emergency L1**********/
	        }else{				
	        	if(test=='SEARCH_REFERENCE'){															
					if($("#SEARCH_REFERENCE").is(':checked')){															
								jsonString+=',"'+jQuery.trim(value)+'":"'+'1'+'"';
								auditLogJson +=',"'+jQuery.trim(value)+'":"'+'1'+'"';
					}else{
								jsonString+=',"'+jQuery.trim(value)+'":"'+'0'+'"';
								auditLogJson +=',"'+jQuery.trim(value)+'":"'+'0'+'"';
					}
				}
	        	else if(test == 'ORCH_VALUES')
	        	{
	        		editJsonOrch+='{"'+jQuery.trim(value)+'":"'+$("#"+test).val().replace(/"/g,"!qt")+'"';
	    			editJsonOrch+=',"orch_FIELD_ID":"'+$("#orch_FIELD_ID").val().replace(/"/g,"!qt")+'"}';
	        	}else{
					jsonString+=',"'+jQuery.trim(value)+'":"'+$("#"+test).val().replace(/"/g,"!qt")+'"';
					auditLogJson +=',"'+jQuery.trim(value)+'":"'+$("#"+test).val().replace(/"/g,"!qt")+'"';
				}
	        }
		}); 	
		
		jsonString+=',"REQUESTED_BY":"'+$("#REQUESTED_BY").val()+'"';
		jsonString+=',"IS_AUTOMATION_REQUIRED":"'+$("#IS_AUTOMATION_REQUIRED").val()+'"';
		jsonString+=',"IS_ORCH_REQUIRED":"'+$("#IS_ORCH_REQUIRED").val()+'"';
		jsonString +='}';
		auditLogJson +='}';			
		/******************AUTO ASSIGNEMENT*************************************/
		
		autoAssignmentFlagJson='{"priorityChangeFlag":"'+priorityChangeFlag+'","statusChangeFlag":"'+statusChangeFlag+'","assignedToChangeFlag":"'+assignedToChangeFlag+'","isAutoAssignmentReqdFlag":"'+isAutoAssignmentReqdFlag;
		autoAssignmentFlagJson+='","newPriority":"'+newPriority+'","newStatus":"'+newStatus+'","newAssignedTo":"'+newAssignedTo+'","assignedGroup":"'+$("#ASSIGNED_GROUP").val()+'",';
		autoAssignmentFlagJson+='"oldPriority":"'+oldPriority+'","oldStatus":"'+oldStatus+'","oldAssignedTo":"'+oldAssignedTo+'","assignedGroupChangeFlag":"'+assignedGroupChangeFlag+'","oldAssignedGroup":"'+oldAssignedGroup+'"}';		
		/******************END AUTO ASSIGNEMENT*********************************/
		jsonArray ='{"updateJson":'+jsonString;
		jsonArray +=',"auditlogJson":'+auditLogJson;
		jsonArray +=',"autoAssignmentFlagJson":'+autoAssignmentFlagJson;
		jsonArray +=',"removingJson":['+removeArray+']}';
	}
	else if(!validationStatus){
		jsonArray="Error";
	}
	
	
	jsonArray = jsonArray.replace(/\n/g,'brlinebreakbreak');	
	return jsonArray;
}

function validateSpecialCharacters(){

	$("#function").hideAll();
	var isValidationError = true;
	$.each(ChangedArray, function(index, value) {		
		var test = value.replace(/^\s+/,"");
		var attrtype = $("#"+test).attr("type"); 
		if(test=='ADDITIONAL_INFO' || test=='RESOLUTION_COMMENTS' || test=='PRIVATE_NOTES' 
			|| test=='OUT_OF_SLA_REASON' || test=='OUT_OF_SLA_COMMENTS' || test=='ONHOLD_COMMENTS' || test=="REOPEN_COMMENTS" || test=="FEEDBACK_COMMENTS")
		{
			isValidationError =true;
			
		}else
		{
			 if(attrtype.indexOf("select") == -1){
		        	var value = $("#"+test).val();
		        	if(checkspecialcharacters(value)){
		        		$("#"+test+"").showerrormessage({message:"&, <, >, \",=, \\u, and ~ characters are not allowed"});
						isValidationError =false;
		        	}
				 }
				
		}
		
	}); 
	
    return isValidationError;
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
	}
	else if(value.indexOf("\\u")!=-1){
		checkstatus = true;
	}
    return checkstatus;
}

function validateTicketDetails(){
	
	$("#FUNCTION_ID").hideAll();
	var isValidationError = true;
	if(!$("#FUNCTION_ID").attr("disabled") && $("#FUNCTION_ID").val()==""){
		$("#FUNCTION_ID").focus();
		$("#FUNCTION_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(!$("#CATEGORY_ID").attr("disabled") && $("#CATEGORY_ID").val()=="" && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue"){
		$("#CATEGORY_ID").focus();
		$("#CATEGORY_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if($("#SUB_CATEGORY_ID").get(0).options.length>1 && !$("#SUB_CATEGORY_ID").attr("disabled") && $("#SUB_CATEGORY_ID").val()=="" && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue"){ 
		$("#SUB_CATEGORY_ID").focus();
		$("#SUB_CATEGORY_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	} 
 	if($("#DEPT_ID").is(":visible") && $("#FUNCTION_ID option:selected").text()=="HR" && !$("#DEPT_ID").attr("disabled") && ($("#DEPT_ID").val()=="" || $("#DEPT_ID").val()=="0")){
		$("#DEPT_ID").focus();
		$("#DEPT_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if($("#ODC_ID").is(":visible") && $("#FUNCTION_ID option:selected").text()=="Admin" && $("#SUB_CATEGORY_ID option:selected").text()=="ODC access" && !$("#ODC_ID").attr("disabled") && ($("#ODC_ID").val()=="" || $("#ODC_ID").val()=="0")){
		$("#ODC_ID").focus();
		$("#ODC_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(!$("#LOCATION_ID").attr("disabled") && ($("#LOCATION_ID").val()=="" || $("#LOCATION_ID").val()=="0")){
		$("#LOCATION_ID").focus();
		$("#LOCATION_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	var isBuildingFloorRequired = false;
	if($("#FUNCTION_ID :selected").text()=="IT Infrastructure Management" || $("#FUNCTION_ID :selected").text()=="Admin"){
		isBuildingFloorRequired = true;
	}
	if(isBuildingFloorRequired && $("#BUILDING").is(":visible") && !$("#BUILDING").attr("disabled") && ($("#BUILDING").val()=="" || ($("#BUILDING").val()=="0" && $("#BUILDING option:selected").text()!="Others"))){
		$("#BUILDING").focus();
		$("#BUILDING").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(isBuildingFloorRequired && !$("#FLOOR").attr("disabled") && $("#FLOOR").is(":visible") && $("#FLOOR").val()==""){
		$("#FLOOR").focus();
		$("#FLOOR").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(!$("#CUBICAL_CODE").attr("disabled") && $("#CUBICAL_CODE").is(":visible") && $("#CUBICAL_CODE").val()==""){ 
		$("#CUBICAL_CODE").focus();
		$("#CUBICAL_CODE").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	
	if(!$("#ASSIGNED_GROUP").attr("disabled") && $("#ASSIGNED_GROUP").val()=="" && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue"){
		$("#ASSIGNED_GROUP").focus();
		$("#ASSIGNED_GROUP").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	
	
	if(!$("#ASSIGNED_TO").attr("disabled") && $("#ASSIGNED_TO").val()=="" && $("#WORKFLOW_STATE option:selected").text()!="Open" && $("#WORKFLOW_STATE option:selected").text()!="HelpDesk Queue" && $("#WORKFLOW_STATE option:selected").text()!="Withdraw"){
		$("#ASSIGNED_TO").focus();
		$("#ASSIGNED_TO").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(!$("#PRIORITY_ID").attr("disabled") && $("#PRIORITY_ID").val()==""){
		$("#PRIORITY_ID").focus();
		$("#PRIORITY_ID").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	if(!$("#WORKFLOW_STATE").attr("disabled")&& $("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" && logged_user_role!="User"){
		var Sla_Status = getSLAStatus(); 
		$("#SLA_STATUS").val(Sla_Status);
		AddValuetoArray("SLA_STATUS",ChangedArray);
		if(Sla_Status=="OUT"){
			var out_of_sla_comms = jQuery.trim($("#OUT_OF_SLA_COMMENTS").val());
			if(!$("#OUT_OF_SLA_REASON").attr("disabled") && $("#OUT_OF_SLA_REASON").val()==""){
				$("#OUT_OF_SLA_REASON").focus();
				$("#OUT_OF_SLA_REASON").showerrormessage({message:"* Mandatory"});
				isValidationError =false;
			}
			if(!$("#OUT_OF_SLA_COMMENTS").attr("disabled") && out_of_sla_comms==""){
				$("#OUT_OF_SLA_COMMENTS").focus();
				$("#OUT_OF_SLA_COMMENTS").showerrormessage({message:"* Mandatory"});
				isValidationError =false;
			}
		}
		var resolution_comms = jQuery.trim($("#RESOLUTION_COMMENTS").val());
		if(!$("#RESOLUTION_COMMENTS").attr("disabled") && resolution_comms==""){
			$("#RESOLUTION_COMMENTS").focus();
			$("#RESOLUTION_COMMENTS").showerrormessage({message:"* Mandatory"});
			AddValuetoArray("RESOLUTION_COMMENTS",ChangedArray);
			isValidationError =false;
		}
	}
//Modified by 720307
	if(((jQuery.inArray("FUNCTION_ID", ChangedArray)!=-1) && (jQuery.inArray("PRIVATE_NOTES", ChangedArray)==-1))||((jQuery.inArray("PRIVATE_NOTES", ChangedArray)==-1) && ($("#WORKFLOW_STATE option:selected").text()=="Re-Assigned" ))){
		$("#PRIVATE_NOTES").focus();  
		$("#PRIVATE_NOTES").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}
	
	if(!$("#REOPEN_COMMENTS").attr("disabled")&& $("#WORKFLOW_STATE option:selected").text()=="Re-Open"){
		var reopen_comments = jQuery.trim($("#REOPEN_COMMENTS").val());
		if(reopen_comments==""){
			$("#REOPEN_COMMENTS").focus();
			$("#REOPEN_COMMENTS").showerrormessage({message:"* Mandatory"});
			AddValuetoArray("REOPEN_COMMENTS",ChangedArray); 
			isValidationError =false;
		}
	}

	//L2:4200,Added Additional Info as Mandatory in Waiting for User Response, Responded and Information Provided Status by Nazeeb as part of L2 : 
	if((jQuery.inArray("ADDITIONAL_INFO", ChangedArray)==-1) && ($("#WORKFLOW_STATE option:selected").text()=="Waiting For User Response" || $("#WORKFLOW_STATE option:selected").text()=="Responded" || $("#WORKFLOW_STATE option:selected").text()=="Information Provided" || $("#WORKFLOW_STATE option:selected").text()=="Withdraw" )){
		$("#ADDITIONAL_INFO").focus();  
		$("#ADDITIONAL_INFO").showerrormessage({message:"* Mandatory"});
		isValidationError =false;
	}

	
	/******************Emergency L1********************/
	if(!$("#EMERGENCY_TYPE").attr("disabled")&& $("#EMERGENCY_TYPE option:selected").text()!="NA"){
		if(!$("#SUB_STATUS").attr("disabled")&& $("#SUB_STATUS option:selected").text()=="NA"){
			$("#SUB_STATUS").focus();
			$("#SUB_STATUS").showerrormessage({message:"* Mandatory"});
			isValidationError =false;
		}		
		if(!$("#DEPLOYMENT_INSTRUCTION").attr("disabled")&& $("#DEPLOYMENT_INSTRUCTION").val()==""){
			$("#DEPLOYMENT_INSTRUCTION").focus();
			$("#DEPLOYMENT_INSTRUCTION").showerrormessage({message:"* Mandatory"});
			AddValuetoArray("DEPLOYMENT_INSTRUCTION",ChangedArray);
			isValidationError =false;
		}
	}
	if(!$("#SUB_STATUS").attr("disabled")&& $("#SUB_STATUS option:selected").text()=="Send Back to IS team"){
			if(!$("#REASON_SEND_BACK").attr("disabled")&& $("#REASON_SEND_BACK").val()==""){
				$("#REASON_SEND_BACK").focus();
				$("#REASON_SEND_BACK").showerrormessage({message:"* Mandatory"});
				AddValuetoArray("REASON_SEND_BACK",ChangedArray);
				isValidationError =false;
			}
	}	
	if(!$("#EMERGENCY_TYPE").attr("disabled")&& $("#EMERGENCY_TYPE option:selected").text()=="NA"){
		$("#DEPLOYMENT_INSTRUCTION").val("");
	}
	if(($("#EMERGENCY_TYPE option:selected").text()!="NA") && ($("#SUB_STATUS option:selected").text()=="Submitted to DBA" ||
			$("#SUB_STATUS option:selected").text()=="Send Back to IS team")){
		if(($("#WORKFLOW_STATE option:selected").text()!="Work In Progress") && ($("#WORKFLOW_STATE option:selected").text()!="Assigned") && ($("#WORKFLOW_STATE option:selected").text()!="Re-Assigned")){
			$("#WORKFLOW_STATE").focus();
			$("#WORKFLOW_STATE").showerrormessage({message:"Ticket cannot be closed until Deployment activity is completed"});
			isValidationError =false;
		}
	}
	if(!$("#ASSIGNED_TO").attr("disabled") && (jQuery.inArray("EMERGENCY_TYPE", ChangedArray)!=-1) && (jQuery.inArray(jQuery.trim(logged_user_role), roleListForRestrictionarray)!=-1) && $("#ASSIGNED_TO").val()!=looged_user_id && isavialableInDropDown("ASSIGNED_TO",looged_user_id)){
        $("#ASSIGNED_TO").focus();
        $("#ASSIGNED_TO").showerrormessage({message:"Please assign the ticket to your self before updating the ticket"});
        isValidationError =false;
    }
	if(!$("#ASSIGNED_TO").attr("disabled") && (jQuery.inArray("EMERGENCY_TYPE", ChangedArray)!=-1) && (jQuery.inArray(jQuery.trim(logged_user_role), roleListForRestrictionarray)!=-1) && $("#ASSIGNED_TO").val()!=looged_user_id && isavialableInDropDown("ASSIGNED_TO",looged_user_id)){
        $("#ASSIGNED_TO").focus();
        $("#ASSIGNED_TO").showerrormessage({message:"Please assign the ticket to your self before updating the ticket"});
        isValidationError =false;
    }
	if(!$("#ASSIGNED_TO").attr("disabled") && (jQuery.inArray("SUB_STATUS", ChangedArray)!=-1) && (jQuery.inArray(jQuery.trim(logged_user_role), roleListForRestrictionarray)!=-1) && $("#ASSIGNED_TO").val()!=looged_user_id && isavialableInDropDown("ASSIGNED_TO",looged_user_id)){
        $("#ASSIGNED_TO").focus();
        $("#ASSIGNED_TO").showerrormessage({message:"Please assign the ticket to your self before updating the ticket"});
        isValidationError =false;
    }
	if($("#EMERGENCY_TYPE option:selected").text()=="Data Change"){
		if(($("#attachmentCountScript").val()!=1) && ($("#attachmentCountAppMail").val()!=1)){
			parent.jbarOnFailure("Kindly attach the Approval Mail & DB Scripts - for Data Change");
			isValidationError =false;
		}
		if(($("#attachmentCountScript").val()==1) && ($("#attachmentCountAppMail").val()!=1)){
			parent.jbarOnFailure("Kindly attach the Approval Mail - for Data Change");
			isValidationError =false;
		}
		if(($("#attachmentCountScript").val()!=1) && ($("#attachmentCountAppMail").val()==1)){
			parent.jbarOnFailure("Kindly attach the DB Scripts - for Data Change");
			isValidationError =false;
		}		
	}
	if($("#EMERGENCY_TYPE option:selected").text()=="Emergency Change"){
		if(($("#attachmentCountAppMail").val()!=1) && ($("#attachmentCountTestRep").val()!=1)){
			parent.jbarOnFailure("Kindly attach the Approval Mail & Test Reports - for Emergency Change");
			isValidationError =false;
		}
		if(($("#attachmentCountAppMail").val()==1) && ($("#attachmentCountTestRep").val()!=1)){
			parent.jbarOnFailure("Kindly attach the Test Reports - for Emergency Change");
			isValidationError =false;
		}
		if(($("#attachmentCountAppMail").val()!=1) && ($("#attachmentCountTestRep").val()==1)){
			parent.jbarOnFailure("Kindly attach the Approval Mail - for Emergency Change");
			isValidationError =false;
		}
	}
	
	/******************Emergency L1********************/
	

	
	if($("#RESOLUTION_STATUS_TR").is(':visible')){
		if($("#WORKFLOW_STATE").val()=="Resolved/Closed"){
		if($("#RESOLUTION_STATUS").val()==""){
			$("#RESOLUTION_STATUS").focus();
			$("#RESOLUTION_STATUS").showerrormessage({message:"* Mandatory"});
			isValidationError =false;
			}
		}
	 }
	
	return isValidationError;
}

function getSLAStatus(){
	var slastatus = "IN";
	var json = '{"TICKET_ID":"'+$("#TICKET_ID").attr('value')+'","MENU_ID":"1","LOCATION_ID":"'+$("#LOCATION_ID").attr('value');
		json += '","CREATED_DATE":"'+$("#CREATED_DATE_STORAGE").attr('value')+'","ECT":"'+$("#ECT_STORAGE").attr('value')+'","SUB_CATEGORY_ID":"'+$("#SUB_CATEGORY_ID").attr('value')+'","PRIORITY_ID":"'+$("#PRIORITY_ID").attr('value')+'","FUNCTION_ID":"'+$("#FUNCTION_ID").attr('value')+'","ASSIGNED_GROUP":"'+$("#ASSIGNED_GROUP").attr('value')+'","FILTER_GROUP_LOCATION":"'+$("#FILTER_GROUP_LOCATION").attr('value')+'"}';
		var json = $.ajax({
        type: 'GET',
        url: 'hdSLAStatusforTicket.htm',
        dataType: 'json',
        success: function(data){slastatus=data},
        data: {jsonString : json},
        async: false
    });
	return slastatus;
}

function appendnotes(objectID){ 
	
	var enteredcomments = "";
	if(jQuery.inArray(objectID, alwaysDisabledArray)==-1){
		parent.jbarOnFailure('Permission denied to enter the comment');
		return false;
	}
	if(jQuery.inArray(objectID, enteredCommentsArray)!=-1){
		enteredcomments = enteredCommentsArray[objectID];
	}
	if(jQuery.inArray(objectID, existingCommentsArray)==-1){
		AddValuetoArray(objectID,existingCommentsArray);
		existingCommentsArray[objectID]=$("#"+objectID+"").val();
	}	
	var html = "<table width=80% align='middle'><tr><td class='label' colspan=2 align='left'><b>Comments:&nbsp;&nbsp;</b></td></tr><tr><td colspan=2 align='right'><textarea id='ENTRY_COMMENTS' maxlength='200' rows=10 cols=40 >"+enteredcomments+"</textarea></td></tr>";
		html += "<tr id='mandcommentstr' style='display:none'><td colspan=2 align='center'>* Comments are mandatory</td></tr>" +
				"<tr id='specialcharmsg' style='display:none;align:center'><td colspan=2><font color='red'>&, <, >, \",=, \\u and ~ characters are not allowed</font></td></tr><tr><td colspan=2 align='center'><input type='button' value='Add' onClick='addComments(\""+objectID+"\");'></td></tr></table>";
		
			$("#commentsHTML").html(html);
		 
}

function saveApprorRejectComments(objectID,value) { 
	
	var existvalue = existingCommentsArray[objectID];
	var entrycomments = value;
		if(entrycomments==""){
			entrycomments = existvalue;
		}else{
			entrycomments = "By :"+username+"("+userid+"),\nDate:"+getESTDateTime()+",\nComments:"+entrycomments+"\n----------------\n"+existvalue;
		}
		$("#"+objectID+"").val(entrycomments);
		AddValuetoArray(objectID,enteredCommentsArray); 
		enteredCommentsArray[objectID]= value;
		AddValuetoArray(objectID,ChangedArray);
}
var curdate = new Date();

function chkAddValuetoArray(value,array,chkarray){
	if(jQuery.inArray(value, chkarray)==-1){
		array.push(value);
	}
}

function getESTDateTime(){
	var ESTDateTime  = "";
	
	var json = $.ajax({
        type: 'GET',
        url: 'getESTTime.htm',
        dataType: 'json',
        success: function(data){ESTDateTime=data.datetime},
        data: {jsonString : json},
        async: false
    });
	
	return ESTDateTime;
}

var confirmresult = true;
function getConfirmResult(){
    var status = true;
    if(!$("#ASSIGNED_TO").attr("disabled") && $("#WORKFLOW_STATE option:selected").text()!="Re-Assigned" && (jQuery.inArray(jQuery.trim(logged_user_role), roleListForRestrictionarray)!=-1) && $("#ASSIGNED_TO").val()!=looged_user_id && isavialableInDropDown("ASSIGNED_TO",looged_user_id)){
          $("#ASSIGNED_TO").focus();
          $("#ASSIGNED_TO").showerrormessage({message:"Please assign the ticket to your self before updating the ticket"});
          status = false;
    }else if(logged_user_role=="User" && mainWorkflowState=="Resolved/Closed" && $("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
        $("#WORKFLOW_STATE").focus();
        $("#WORKFLOW_STATE").showerrormessage({message:"Please change the ticket status to Re-Open before updating the ticket"});
        status = false;
    }else if(logged_user_role=="User" && mainWorkflowState=="Waiting For User Response" && $("#WORKFLOW_STATE option:selected").text()=="Waiting For User Response"){
        $("#WORKFLOW_STATE").focus();
        $("#WORKFLOW_STATE").showerrormessage({message:"Please change the ticket status to Responded before updating the ticket"});
        status = false;
    }else if(logged_user_role=="User" && mainWorkflowState=="Need More Info" && $("#WORKFLOW_STATE option:selected").text()=="Need More Info"){
        $("#WORKFLOW_STATE").focus();
        $("#WORKFLOW_STATE").showerrormessage({message:"Please change the ticket status to Information Provided before updating the ticket"});
        status = false;
    }else if(assignedToOnLoad==$("#ASSIGNED_TO option:selected").val() && $("#WORKFLOW_STATE option:selected").text()=="Re-Assigned" && mainWorkflowState!=$("#WORKFLOW_STATE option:selected").text()){
        $("#ASSIGNED_TO").focus();
        $("#ASSIGNED_TO").showerrormessage({message:"Mandatory ,when the status is changed to Re-Assigned"});
        status = false;
    }else if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" && !$("#ASSIGNED_TO").attr("disabled") && assignedToOnLoad!=$("#ASSIGNED_TO option:selected").val() && $("#ASSIGNED_TO option:selected").val()!=userid){
        $("#ASSIGNED_TO").focus();
        $("#ASSIGNED_TO").showerrormessage({message:"Assigned-To should not be changed while closing the ticket"});
        status = false;
    }else if($("#CREATED_BY").val()=="SYSTEM(SYSTEM)" && $("#REQUESTED_BY").val()=="SYSTEM(SYSTEM)" && !IsValidEmpId){
			$("#ON_BEHALF_OF").focus();
			$("#ON_BEHALF_OF").showerrormessage({message:"Please enter Authorized Employee id and click on 'Validate' to verify"});
		status = false;
    }else if(!$("#ON_BEHALF_OF").attr("disabled") && $("#ON_BEHALF_OF").val()!="" && !IsValidEmpId){
			$("#ON_BEHALF_OF").focus();
			$("#ON_BEHALF_OF").showerrormessage({message:"Please enter Authorized Employee id and click on 'Validate' to verify"});
	    status = false;
	}
    else if(jQuery.inArray("WORKFLOW_STATE", ChangedArray)==-1 && loginrole!="User"){
    	if($('#DeLink').is(':visible')){
    		status=true;
    	}else if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" ||$("#WORKFLOW_STATE option:selected").text()=="Closed(By System)" || $("#WORKFLOW_STATE option:selected").text()=="AutoClosed(By System)"){
    		status=true;
    	}else if($("#EMERGENCY_TYPE option:selected").text()!="NA" && $("#SUB_STATUS option:selected").text()=="Submitted to DBA"){
			status=true;
		}else if($("#EMERGENCY_TYPE option:selected").text()!="NA" && $("#SUB_STATUS option:selected").text()=="Completed" && (logged_user_role=="IT Engineer" || logged_user_role=="IT Manager")){			
			status=true;
		}else{
			if(!$("#WORKFLOW_STATE").attr("disabled"))
				status = confirm("Do you want to continue with out changing the Workflow Status ?")
    	}
    }
    else if($("#WORKFLOW_STATE").val()== "Assigned For Automation"){ 

    	if(!isOrchReopen){
        	$("#WORKFLOW_STATE").focus();
    		$("#WORKFLOW_STATE").showerrormessage({message:" Orchestration was re-opened.Hence please select the status Assigned!"});
        	status = false;
        	}
    } 
    confirmresult = status;
    return status;
}

function unlockTheTicket(ticketId,menuId){
	
	var jsonobj = '{"JSONARRAY":['+'{"TICKET_ID":"'+ticketId+'","MENU_ID":"'+menuId+'"}'+']}';
	
	$.getJSON('unlockTickets.htm', {json:jsonobj}, function(data) {
		if(data.status=="Success"){
		}	
	});
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

var locDetIDforFloors = new Array(); 
function GetFloors(){
	if(($("#FUNCTION_ID :selected").text()=="IT Infrastructure Management" || $("#FUNCTION_ID :selected").text()=="Admin") && $("#BUILDING").val()!=""){
		var buildingval = $('#BUILDING').val();
		$.getJSON('getFloorsforBuilding.htm',{buildingname:buildingval},function(floors){
			if (floors.status && floors.status=="Error") {	
				parent.jbarOnFailure(floors.message);
			}else{
			$("#FLOOR").html("");
			$('#FLOOR').append($("<option></option>").attr("value",'').text('--Please Select Floor--'));
			for(var i=0;i<floors.length;i++){
				locDetIDforFloors[floors[i].FLOOR] = floors[i].LOC_DET_ID;
				$('#FLOOR').append($("<option></option>").attr("value",floors[i].LOC_DET_ID).text(floors[i].FLOOR)); 
			 }
			}
		});
	}else{
		$("#FLOOR").html("");
		$('#FLOOR').append($("<option></option>").attr("value",'').text('--Please Select Floor--'));
	}
}

function updateLocationDetail(){
	if($("#SUB_CATEGORY_ID :selected").text()=="ODC access"){
		var buildingid = locationDetailIDsForBuilding[$("#BUILDING").val()];
		$("#LOC_DET_ID").val(buildingid);
	
	}else{
		var floorid = locDetIDforFloors[$("#FLOOR option:selected").text()];
		$("#LOC_DET_ID").val(floorid);
	}
	AddValuetoArray("LOC_DET_ID",ChangedArray);
}
function replaceSpecialCharacters(id){			
		var temp = new String(document.getElementById(id).value);	
		temp =  temp.replace(/[^a-zA-Z0-9-,:;.?!@#$%^~<>&"*()={}|\s\t\n]+/g,' ');
			  
		document.getElementById(id).value = temp;				
}
function replace(string,text,by) {
		// Replaces text with by in string
		var strLength = string.length, txtLength = text.length;
		if ((strLength == 0) || (txtLength == 0)) return string;
			var i = string.indexOf(text);
		if ((!i) && (text != string.substring(0,txtLength))) return string;
		if (i == -1) return string;
			var newstr = string.substring(0,i) + by;
		if (i+txtLength < strLength)
			newstr += replace(string.substring(i+txtLength,strLength),text,by);
		return newstr;
}

function checkWorkflowState(){
	if($("#WORKFLOW_STATE option:selected").text()=="Out of Operation Hours"){
		    		$("#ASSIGNED_TO").attr("disabled",true);
		    		$("#ASSIGNED_TO").val("");
					AddValuetoArray("ASSIGNED_TO",ChangedArray);
	}else if($("#WORKFLOW_STATE option:selected").text()=="Re-Open"){
		$("#SLA_STATUS").val('NA');
		AddValuetoArray("SLA_STATUS",ChangedArray);
	}else if(!$("#ASSIGNED_GROUP").attr("disabled") && logged_user_role!="User"){
					$("#ASSIGNED_TO").attr("disabled",false);
	}

	/*
	 * Changed By : 714599
	 * Comments : Assigned to should be selected as login member when 
	 * function is Finance and state is resolved/Closed .(This should be done on change of workflowstate 
	 */
	var functionName = $("#FUNCTION_ID option:selected").text();
	if(functionName=="Finance" && $("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
		$("#ASSIGNED_TO").val(looged_user_id);
		AddValuetoArray("ASSIGNED_TO",ChangedArray);
	}
}
function getGroupMembersOnload(from){ 
	var groupName = $("#ASSIGNED_GROUP option:selected").val();
	var type="filter";
	  if($("#FILTERLOGGEDINUSERS").attr("checked")){
		  type="All";
	  }
	  var assigned_to = $("#ASSIGNED_TO option:selected").val();
	if(groupName!=""){
		var functionID = $("#FUNCTION_ID option:selected").val();
		var locationID = $("#FILTER_GROUP_LOCATION option:selected").val();
		$.getJSON('hdgetGroupmembers.htm', {
			groupId : groupName,
			type : type,
			functionid : functionID,
			locationid : locationID,
			from : from
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
					if(assigned_to!=""){ 
						$("#ASSIGNED_TO").val(assigned_to);		
					}
				}else{
					$("#ASSIGNED_TO").html('<option value="">--Please Select--</option>');
				}
			}
			});
	}
}

function getLocationsForFunction(function_id)
{ 
	if($("#FUNCTION_ID").val()==""){
		$("#FUNCTION_ID").focus();
		$("#FUNCTION_ID").showerrormessage({message:"* Mandatory"});
		return;
	}else{
		$("#FUNCTION_ID").hideAll();
	}
	var functionName = $("#FUNCTION_ID option:selected").text();
	if((functionName=="Admin" || functionName=="Finance" || functionName=="HR") && $("#SUB_CATEGORY_ID").val()==""){
		$("#LOCATION_ID").attr("disabled",true);  
		$("#BUILDING").attr("disabled",true);
		$("#FLOOR").attr("disabled",true);
		$("#CUBICLE_CODE").attr("disabled",true);  
		return ; 
	}
	
	var selectedLocationVal = $('#LOCATION_ID').val(); 
	
	$.getJSON('getLocationListForFunction.htm',{functionid:function_id},function(locations){ 
		if (locations.status && locations.status=="Error") {	
			parent.jbarOnFailure(locations.message);
		}else{
		var functionName = $("#FUNCTION_ID option:selected").text();
		$('#LOCATION_ID').html($("<option></option>").attr("value",'0').text('--Please Select--')); 
		$('#FILTER_GROUP_LOCATION').html($("<option></option>").attr("value",'0').text('--Please Select--'));  
		for(var i=0;i<locations.length;i++){
			$('#LOCATION_ID').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
			$('#FILTER_GROUP_LOCATION').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
		 }
		$('#LOCATION_ID').val(selectedLocationVal);
		$("#LOCATION_ID").removeAttr("disabled");  
		$('#FILTER_GROUP_LOCATION').val($('#LOCATION_ID').val());
			if(functionName=="Admin" || functionName=="IT Infrastructure Management"){
				$("#BUILDING").removeAttr("disabled");  
				$("#FLOOR").removeAttr("disabled");
				$("#CUBICLE_CODE").removeAttr("disabled");

				if($('#LOCATION_ID').val()!=""){
					GetBuildings();
				}
			} 
		}
		if(functionName == "Information Systems" || functionName == "IT Infrastructure Management" || functionName == "CHCS Applications"){
			$("#FILTER_GROUP_LOCATION_TR").show();
		}else{
			$("#FILTER_GROUP_LOCATION_TR").hide();
		}
	});
}

/* Changed By :714599
 * Below fucntion using to filter the locations on selection of category
 */ 

function filterLocations(categoryID){
		$.getJSON('getLocationListForSubcategory.htm',{categoryid:categoryID},function(locations){
			if (locations.status && locations.status=="Error") {	
				parent.jbarOnFailure(locations.message);
			}else{

			$("#FILTER_GROUP_LOCATION_TR").hide(); 
			
			$('#LOCATION_ID').find('option').remove().end();
			$('#LOCATION_ID').html($("<option></option>").attr("value",'0').text('--Please Select--'));
			$('#FILTER_GROUP_LOCATION').html($("<option></option>").attr("value",'0').text('--Please Select--'));  
			for(var i=0;i<locations.length;i++){
				$('#LOCATION_ID').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
				$('#FILTER_GROUP_LOCATION').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
			 }
			var functionName = $("#FUNCTION_ID option:selected").text();
			if(functionName=="Admin"){
				$("#LOCATION_ID").removeAttr("disabled");
				$("#BUILDING").removeAttr("disabled");
				$("#FLOOR").removeAttr("disabled");
				$("#CUBICLE_CODE").removeAttr("disabled");
			}else if(functionName=="Finance" || functionName=="HR" || functionName=="Travel And Transport"){
				$("#LOCATION_ID").removeAttr("disabled");
			}
			}
		});
}
var locationDetailIDsForBuilding = new Array();
function GetBuildings(){
	if($("#FUNCTION_ID :selected").text()=="IT Infrastructure Management" || $("#FUNCTION_ID :selected").text()=="Admin"){ 
		var LocationID = $('#LOCATION_ID').val();
			var locationtext = $('#LOCATION_ID option:selected').text();
			locationtext = jQuery.trim(locationtext);
			locationtext = new String(locationtext).toLowerCase();
		$("#BUILDING").html("");
		$("#FLOOR").html("");
		$.ajaxSetup({ cache: false });	
		$.getJSON('getBuildingsforLocation.htm',{locationID:LocationID},function(buildings){
			if (buildings.status && buildings.status=="Error") {	
				parent.jbarOnFailure(buildings.message);
			}else{
			$('#BUILDING').append($("<option></option>").attr("value",'0').text('--Please Select Building--'));
			for(var i=0;i<buildings.length;i++){
				$('#BUILDING').append($("<option></option>").attr("value",buildings[i].BUILDING).text(buildings[i].BUILDING));
				locationDetailIDsForBuilding[buildings[i].BUILDING] = buildings[i].LOC_DET_ID;
			 }
			$("#FLOOR").html("<option value=''>--Please Select Floor--</option>");
			}
		});
	}		
} 


function getHRiLearnGroups(){
	
	var functionName  = $("#FUNCTION_ID :selected").text();
	
	var validationStatustoGetGroups = true;
	if(functionName=="HR" || functionName=="IGATE Corporate University" || functionName=="Quality" || functionName=="Delivery" || functionName=="Legal" || functionName=="Life and Health Operations Canada"){// Added by Nazeeb as part of L2 : 4369	
	////TODO 10/16/2014 Nisha
	var LocationID = $('#LOCATION_ID').val();
		var locationDetID = $("#LOC_DET_ID").val();
		var DeptID = $("#DEPT_ID").val();
		var subcategoryID = $("#SUB_CATEGORY_ID").val();
		
		if(LocationID=="" || LocationID=="0" || subcategoryID=="" || subcategoryID=="0"){
			validationStatustoGetGroups = false;
		}
		
		if(functionName=="HR" && (DeptID=="0" || DeptID=="")){
			validationStatustoGetGroups = false;
		}
		if(validationStatustoGetGroups){
			$.getJSON('hdgetGroupsforAdminFinHRFunction.htm',{functionname:functionName,subcatID:subcategoryID,deptid:DeptID,locid:LocationID,locdetID:locationDetID},function(response){
				if (response.status && response.status=="Error") {	
					parent.jbarOnFailure(response.message);
				}else{
				var options = [];
				options.push('<option value="">--Please Select--</option>');
				for(var i=0;i<response.length;i++){
					options.push('<option value="' + response[i].GROUP_ID + '">'
							+ response[i].GROUP_NAME + '</option>');
				}
				$("#ASSIGNED_GROUP").html(options.join(''));
				AddValuetoArray("ASSIGNED_GROUP",ChangedArray);
				AddValuetoArray("ASSIGNED_TO",ChangedArray);
				$("#ASSIGNED_GROUP").val(oldAssignedGroup);
				$("#ASSIGNED_TO").val(oldAssignedTo);
				if(response.length>=1){
					$("#ASSIGNED_GROUP").removeAttr("disabled"); 
					$("#ASSIGNED_TO").removeAttr("disabled");
				}
				}
			});

		}
	} else if(functionName=="Admin"){
		var LocationID = $('#LOCATION_ID').val();
		var locationDetID = $("#LOC_DET_ID").val();
		var DeptID = $("#DEPT_ID").val();
		var subcategoryID = $("#SUB_CATEGORY_ID").val();
		
		if(LocationID=="" || LocationID=="0" || subcategoryID=="" || subcategoryID=="0"){
			validationStatustoGetGroups = false;
		}
		if(functionName=="Admin" && (locationDetID=="0" || locationDetID=="" || locationDetID==null || locationDetID=="null")){
			validationStatustoGetGroups = false;
		}
		
		if(validationStatustoGetGroups){
			$.getJSON('hdgetGroupsforAdmin.htm',{functionname:functionName,subcatID:subcategoryID,deptid:DeptID,locid:LocationID,locdetID:locationDetID},function(response){
				if (response.status && response.status=="Error") {	
					parent.jbarOnFailure(response.message);
				}else{
				var options = [];
				options.push('<option value="">--Please Select--</option>');
				for(var i=0;i<response.length;i++){
					options.push('<option value="' + response[i].GROUP_ID + '">'
							+ response[i].GROUP_NAME + '</option>');
				}
				$("#ASSIGNED_GROUP").html(options.join(''));
				AddValuetoArray("ASSIGNED_GROUP",ChangedArray);
				AddValuetoArray("ASSIGNED_TO",ChangedArray); 
				if(response.length>=1){
					$("#ASSIGNED_GROUP").removeAttr("disabled"); 
					$("#ASSIGNED_TO").removeAttr("disabled");
				}
				}
			});

		}
		
		
		
	}
}


function onLoadAdminFunctions(){
	var functionName  = $("#FUNCTION_ID :selected").text();
	
	var validationStatustoGetGroups = true;

	var LocationID = $('#LOCATION_ID').val();
	var locationDetID = $("#LOC_DET_ID").val();
	var DeptID = $("#DEPT_ID").val();
	var subcategoryID = $("#SUB_CATEGORY_ID").val();
	
	if(LocationID=="" || LocationID=="0" || subcategoryID=="" || subcategoryID=="0"){
		validationStatustoGetGroups = false;
	}
	if(functionName=="Admin" && (locationDetID=="0" || locationDetID=="" || locationDetID==null || locationDetID=="null")){
		validationStatustoGetGroups = false;
	}
	
	if(validationStatustoGetGroups){
		$.getJSON('hdgetGroupsforAdminOnLoad.htm',{functionname:functionName,subcatID:subcategoryID,deptid:DeptID,locid:LocationID,locdetID:locationDetID},function(response){
			if (response.status && response.status=="Error") {	
				parent.jbarOnFailure(response.message);
			}else{
			var options = [];
			options.push('<option value="">--Please Select--</option>');
			for(var i=0;i<response.length;i++){
				options.push('<option value="' + response[i].GROUP_ID + '">'
						+ response[i].GROUP_NAME + '</option>');
			}
			$("#ASSIGNED_GROUP").html(options.join(''));
			AddValuetoArray("ASSIGNED_GROUP",ChangedArray);
			AddValuetoArray("ASSIGNED_TO",ChangedArray); 
			$("#ASSIGNED_GROUP").val(oldAssignedGroup);
			$("#ASSIGNED_TO").val(oldAssignedTo);
			}
		});

	}
	
	
	

}

function getODCS(){
	if($("#SUB_CATEGORY_ID :selected").text()=="ODC access"){
		var buildingID = locationDetailIDsForBuilding[$("#BUILDING").val()];
		$.getJSON('odcList.htm',{locdetID:buildingID},function(odcs){
			if (odcs.status && odcs.status=="Error") {	
				parent.jbarOnFailure(odcs.message);
			}else{
			$('#ODC_ID').html($("<option></option>").attr("value",'').text('--Please Select--'));
			for(var i=0;i<odcs.length;i++){
				$('#ODC_ID').append($("<option></option>").attr("value",odcs[i].ODC_ID).text(odcs[i].ODC_NAME)); 
			 }
			}
		});
	}
}
var departmentsRetrieved = false;
function getAllDepartments(){ 
	if(!departmentsRetrieved){
		var requestBy=$("#REQUESTED_BY").val();
		var indexOfOpenBrace=requestBy.indexOf("(");
		var indexOfCloseBrace=requestBy.indexOf(")");
		var requestorId=null;
		requestorId=requestBy.substring(indexOfOpenBrace+1,indexOfCloseBrace);			
		$.getJSON('departmentList.htm',{employeeID:requestorId},function(departments){			
		if(departments.length==1)
		{				
			$('#DEPT_ID').html($("<option></option>").attr("value",departments[0].DEPT_ID).text(departments[0].DEPT_NAME));
			$("#DEPT_ID option[text='"+departments[0].DEPT_ID+"']").attr("selected",true); 
			AddValuetoArray('DEPT_ID',ChangedArray);
			$('#DEPT_ID').hide();			
			$("#DEPT_NAME").val(departments[0].DEPT_NAME);			
			$("#DEPT_NAME").show();	
			$("#deptTD").show();
			
			
		}else{
			$("#DEPT_NAME").hide();
			$("#deptTD").hide();
			if (departments.status && departments.status=="Error") {	
				parent.jbarOnFailure(departments.message);
			}else{
				$("#deptTD").hide();
			$('#DEPT_ID').html($("<option></option>").attr("value",'').text('--Please Select--'));
			for(var i=0;i<departments.length;i++){
				$('#DEPT_ID').append($("<option></option>").attr("value",departments[i].DEPT_ID).text(departments[i].DEPT_NAME)); 
			 }
			$('#DEPT_ID').show();
			$('#DEPT_ID').removeAttr("disabled");  
			departmentsRetrieved = true;
			}
			}
		});
	}
}

function isavialableInDropDown(objid,val){
	var returnval = false;
	$("#"+objid+" option").each(function(){
		if(this.value==val){
			returnval =  true;
		}
	});
	return returnval;
}
function getDescVal1(thisobj)
{
	var descriptionVal= $("#"+thisobj).val();
	var descrValue='';
	var descriptionLength=$("#"+thisobj).val().length;
	var i=0;
	var j=0;
	while(i<descriptionLength){
		j=j+100;
		descrValue=descrValue+descriptionVal.substring(i,j)+" \n";
		i=i+100;
	}
$("#"+thisobj).attr("bt-xTitle",descrValue);	
}

function ValidateEmpId(EmpId){
	if(/^[a-zA-Z0-9_.]+$/.test(EmpId)){
		$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){

			if(Emp.NAME){ 
				document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
				$("#empResultMessage").removeClass("invalid_text");
				$("#empResultMessage").addClass("valid_text");
				$("#empResultMessage").show(); 
				if(Emp.IS_PREMIUM_MEMBER=="YES"){  
											
					document.getElementById("empResultMessage").innerHTML = "&nbsp;<img src='images/Premium.gif' alt='Premium Member' title='Premium Member'/>"+$("#empResultMessage").html()+"<span class='blink'><b><font color='red'> Premium Member</font></b></span>";
					$('.blink').each(function() {
					    var elem = $(this);
					    setInterval(function() {
					        if (elem.css('visibility') == 'hidden') {
					            elem.css('visibility', 'visible');
					        } else {
					            elem.css('visibility', 'hidden');
					        }    
					    }, 500);
					}); 
				}
				$("#REQUESTED_BY").val(EmpId);
				AddValuetoArray("REQUESTED_BY",ChangedArray)
				IsValidEmpId =true; 
			} 
			else{
				document.getElementById("empResultMessage").innerHTML ="Please enter Authorized Employee id and click on 'Validate' to verify";
				$("#empResultMessage").removeClass("invalid_text");
				$("#empResultMessage").addClass("invalid_text");
				$("#empResultMessage").show();
				IsValidEmpId = false;
			}
		});
	}else{
		document.getElementById("empResultMessage").innerHTML ="Please enter Authorized Employee id and click on 'Validate' to verify";
		$("#empResultMessage").removeClass("valid_text");
		$("#empResultMessage").addClass("invalid_text");
		$("#empResultMessage").show();
		IsValidEmpId = false;
	}
	$("#ON_BEHALF_OF").hideerrormessage();
}
var firstTime=true;
var prevArray=new Array();
var masterTicketId=null;
function LockTicket1(){
	
	var id=$("#TICKET_ID").val();
	var html1="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='modalTable'> " +
	"<tr><td>Master Ticket Id </td><td><select id='masterId' name='masterId' onchange='getMasterDiv(this.options[this.selectedIndex].value)'><option id='0'>Please Select</option>";
	
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
		html4+="<tr><th>SUBJECT</th><th>DESCRIPTION</th><th>STATUS</th><th>CREATED BY</th><th>CREATED DATE</th></tr>";		
		for(var i=0;i<masterTicketIdList.length;i++){
			html4+="<tr id=_"+masterTicketIdList[i].TICKET_ID+" style='display:none;align:center'><td>"+masterTicketIdList[i].SUBJECT+"</td><td>"+masterTicketIdList[i].DESCRIPTION+"</td><td>"+masterTicketIdList[i].WORKFLOW_NAME+"</td><td>"+masterTicketIdList[i].CREATED_NAME+"</td><td>"+masterTicketIdList[i].CREATED_DATE+"</td></tr>";
		}	
		html4+="</table></td></tr><tr><td id='SaveButtn' style='display:none;align:center class='label' colspan='2' align='center' ><input type='button' id='Save' value='Save' onclick='saveMsg("+id+","+$("#masterId").val()+")'></tr>" +
				"<tr><td colspan='2' id='SaveMessage' style='display:none;align:center'><div id='sMsg'><font color='Green'><b>Selected ticket"+id+" added successfully to the Master ticket</b></font></div id='sMsg'></td></tr></table>";
		
		$('.colorboxpopupWindow').colorbox({width:"65%", height:"95%",html:html4,onComplete : function() { 		
		}
	});
});
}


function DeLinkTicket(){
	var ticketId=$("#TICKET_ID").val();
	$.ajaxSetup({ cache: false });
	if(confirm("Do you want to Delink the ticket?")){
		 var json = '{"CHILD_TICKET_ID":"'+ticketId+'"}';
		$.getJSON('DeLinkChildTicket.htm',{json:json},function(result){
		
			if(result=="SUCCESS"){	
				window.location.reload(true);
				parent.jbarOnSuccess(result); 
			
			}else{
				parent.jbarOnFailure(result);
			}
		});
	}
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
			$("#Link").hide();
			$("#Edit").hide();
			$("#Update").hide();
			$("#DeLink").show();
			parent.jbarOnSuccess('Successfully linked Ticket '+ticktId+' to Master Ticket '+masterTicketId+' ');
		}else{
			parent.jbarOnFailure(' Ticket not added');
		}
		
	});

}

var masterTicketHTML='';
function getMasterTicket(){
	var masterId=$("#REFERENCE_ID").val();
	$("#hdEditMain ul li a").removeClass("selected");
	$("#hdEditMain ul li a").eq("5").addClass("selected");
	if(masterId==""){
		$("#masterTicketDett").find("tr:gt(1)").remove();		
		masterTicketHTML+="<tr><td>No records Found</td></tr>";
		$("#Master-Ticket").show();
		$("#masterTicketDett").html(masterTicketHTML);
		$("#Audit-Log").hide();
		$("#ticketdetailsform").hide();
		$("#Attachment-Div").hide();
		$("#Approver-Data").hide();
		/*$("#Asset-Data").hide();*/
		$("#Similar-Tickets").hide();
		$("#Related-Mails").hide();		
		masterTicketHTML="";
	}else{
	$.getJSON('getMasterTicketDet.htm',{masterId:masterId},function(masterCreate){
		if(masterCreate!=null){
			$("#masterTicketDett").find("tr:gt(1)").remove();			
			masterTicketHTML="<tr><th>Master Ticket ID</th><th>Workflow Status</th><th>Subject</th><th>Description</th>";
			masterTicketHTML+="<th>ECT</th></tr>";
			masterTicketHTML+="<tr><td>"+masterId+"</td><td>"+masterCreate.workflow_NAME+"</td><td>"+masterCreate.subject+"</td><td>"+masterCreate.description+"</td><td>"+masterCreate.ect+"</td></tr>";
		}else{
			
			masterTicketHTML+="<tr><td>No records Found</td></tr>"
		}
		
		$("#Master-Ticket").show();
		$("#masterTicketDett").html(masterTicketHTML);
		$("#Audit-Log").hide();
		$("#ticketdetailsform").hide();
		$("#Attachment-Div").hide();
		$("#Approver-Data").hide();
		/*$("#Asset-Data").hide();*/
		$("#Similar-Tickets").hide();
		$("#Related-Mails").hide();		
		masterTicketHTML="";
	});
	
	}
	
}

//Feed Back Enhancement

function checkStatusOfTicket(){
	
	if(($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" ||$("#WORKFLOW_STATE option:selected").text()=="Closed(By System)"
		|| $("#WORKFLOW_STATE option:selected").text()=="Closed(By User)" || $("#WORKFLOW_STATE option:selected").text()=="Closed with Success"
			|| $("#WORKFLOW_STATE option:selected").text()=="Closed with Rollback" || $("#WORKFLOW_STATE option:selected").text()=="AutoClosed(By System)") && (logged_user_role=="User")){
		$("#Feedback").removeAttr("disabled");
		$("#Feedback").show();
		//}
	}
	if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" ||$("#WORKFLOW_STATE option:selected").text()=="Closed(By System)"
		 || $("#WORKFLOW_STATE option:selected").text()=="AutoClosed(By System)"){
		if(validateSearchPreferences()){
			$("#checkEditMode").removeAttr("style");
			$("#UpdateButtonTd").removeAttr("style");
			$("#Edit").show();			
			$("#Update").show();
			$("#Feedback").hide();
			$("#Link").hide();
			$("#DeLink").hide();
		}
		if(isMasterTicketLinked=="Yes"){
			$("#Edit").hide();
			$("#Update").hide();
		}
	}
}

function getFeedbackPopUp(){
	if($("#FEEDBACK_COMMENTS").val()=="" || $("#FEEDBACK_COMMENTS").val()==null){	
		
	var popupHtml="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='myDataTable'>";
	popupHtml+="<tr><th colspan='2'>Feedback </th></tr>";
	popupHtml+="<tr><td><input type='radio' name='feedbck' id='Poor' value='Poor' onclick='showCommentsBox()'/>Poor</td></tr><tr><td><input type='radio' id='Fair' name='feedbck' value='Fair' onclick='showCommentsBox()'/>Fair</td></tr><tr><td><input type='radio' id='Good' value='Good' name='feedbck' onclick='showCommentsBox()'/>Good</td></tr><tr><td><input type='radio' id='Excellent' value='Excellent' name='feedbck' onclick='showCommentsBox()'/>Excellent</td></tr>";
	popupHtml+="<tr id='commentsTR' style='display:none'><th>Feedback Comments</th></tr>";
	popupHtml+="<tr style='display:none' id='rateTR'><td><textarea id='FEEDBACK_COMMENTS' rows=7 cols=70 ></textarea></td></tr>";
	popupHtml+="<tr id='saveButtonTR' style='display:none'><td align='center'><input type='button' id='saveComment' onclick='saveFeedbackComment()' value='Save'/></td></tr>"
	popupHtml+="</table>";
	$('.feedbackPopup').colorbox({width:"65%", height:"95%",html:popupHtml,onComplete : function() { 		
	}
	});
	}else{
		parent.jbarOnFailure("Feedback has already been entered.");
	}
}
function showCommentsBox(){
	$("#commentsTR").show();
	$("#rateTR").show();
	$("#saveButtonTR").show();
	
}

function saveFeedbackComment(){
	var ticketID=$("#TICKET_ID").val();	
	var wfStatus=$("#WORKFLOW_STATE option:selected").text();
	var action="U";
	var functionName=$("#FUNCTION_ID option:selected").text();
	var subCategoryName=$("#SUB_CATEGORY_ID option:selected").text();
	var priorityName=$("#PRIORITY option:selected").text();
	var locationName=$("#LOCATION_ID option:selected").text();	
	var functionID=$("#FUNCTION_ID").val();
	var feedBackComments=$("#FEEDBACK_COMMENTS").val().replace(/"/g,"!qt");
	var feedBackRate=$('input:radio[name=feedbck]:checked').val();
	var validFlag=validateFeedbackVal(feedBackRate,feedBackComments);
	if(validFlag==true){
	jsonobj = '{"TICKET_ID":"'+ticketID+'","WORKFLOW_STATE":"'+wfStatus+'","ACTION":"'+action+'","FUNCTION":"'+functionName+'","SUB_CATEGORY_ID":"'+subCategoryName+'","PRIORITY_ID":"'+priorityName+'","LOCATION_ID":"'+locationName+'","FUNCTION_ID":"'+functionID+'","FEEDBACK_COMMENTS":"'+feedBackComments+'","FEEDBACK_RATE":"'+feedBackRate+'"}';	
	$.ajaxSetup({ cache: false });
	$.getJSON('saveFeedbackComments.htm', {json:jsonobj}, function(data) {		
		$.colorbox.close();
		if(data=="Success"){
			parent.jbarOnSuccess(data);
			window.location.reload(true);
		}else
		{
			parent.jbarOnFailure(data.message);
		}
	});
	}else{
		alert('Please enter valid feedback comments');
	}
}

function validateFeedbackVal(feedBackRate,feedBackComments){
	var flag=true;
	if(feedBackComments==" " || feedBackComments==null  || jQuery.trim(feedBackComments).length==0){		
		flag=false;
	}
return flag;
}
function validateSearchPreferences(){
	if((logged_user_role=="Group Quality Manager")||(logged_user_role=="IS Manager")||(logged_user_role=="IT Manager")||
			(logged_user_role=="Admin Manager")||(logged_user_role=="CHCS Manager")||(logged_user_role=="Finance Manager")||
			(logged_user_role=="HR Manager")||(logged_user_role=="iLearn Manager")){
		return true;
	}else{
		return false;
	}
}
/**********************Auto Assignment****************************************************************/
function  getIsAutoAssignedActive(groupId){
var isAAFlag=0;
	$.ajaxSetup({ cache: false });	

		 $.ajax({
			 	async: false,
			 	type: 'GET',
		    	url: "hdgetGroupsForAA.htm",		    	
		    	dataType: 'json',
		    	data : { 
			 	groupid : groupId				
				   },
		    	success: function(response) {
					   if (response.status && response.status=="Error") {	
							parent.jbarOnFailure(response.message);
						}else{
						
						for(var i=0;i<response.length;i++){				
							isAutoAssignmentReqdFlag=response[i].IS_AUTO_ASSIGNMENT_REQUIRED;							
						}
						}
		    	}
		    });
	

		
		return isAutoAssignmentReqdFlag;
}
	

/**********************END: Auto Assignment****************************************************************/

