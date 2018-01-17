$(document).ready(function(){
	
	$("#createhelpdeskticket").click(function() {
		  $(".formError").hide();
		});
	
	$('#function').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#category').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#subcategory').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#priority').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#subject').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#description').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#problemfile').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#approvalfile').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#onbeofEmpId').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#locationId').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#floor').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#cubicalselcode').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#phonenumber').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#altcontactnumber').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#EmpProject').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#CCEmailID').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#impactusercheck').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#impactusercount').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#validateEmployee').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#department').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
		//Added to implement the tool to track EX employees quereis for HR function
	$('#isEXEmployeeCheck').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
		//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");

		$('textarea[maxlength],input[maxlength]').on('keyup blur', function() {
	        // Store the maxlength and value of the field.
	        var maxlength = $(this).attr('maxlength');
	        var val = $(this).val();

	        // Trim the field if it has content over the maxlength.
	        if (val.length > maxlength) {
	            $(this).val(val.slice(0, maxlength));
	        }
		});
		$("#onbeofEmpId").change(function(){
			IsValidEmpId = false;
		});
		$("#onbeofEmpId").blur(function(){
			if(!IsValidEmpId){
				getProjectsForEmployee();
			}
		});
		
		$("#cubicalcode").click(function(){
			
			if($("#cubicalcode").val()==null || $("#cubicalcode").val()== ""){
				var floorID = $("#floor").val();
				GenerateCubicalCodes(floorID);
			}
		});
		
		//Added to implement the tool to track EX employees quereis for HR function
		if($("#mailTrackerType").val()=="HR"){
			
				
				if($("#fromAddress").val().toLowerCase().indexOf("@igate")<=0){	
					$("#isEXEmployeeCheckTR").attr("style", "display:block");
					$("#function option:contains(HR)").attr('selected', 'selected'); 	
					$("#category option:contains(Exit Related)").attr('selected', 'selected'); 	
					
					$("#function").attr('disabled','disabled');
					$("#category").attr('disabled','disabled');					
					$("#subcategory option:contains(Exit Related)").attr('selected', 'selected');
					$("#subcategory").attr('disabled','disabled');
					$('#locationId :selected').remove();  
					$('#locationId').append($("<option></option>").attr("value","1").text("Bangalore")).attr('disabled','disabled');
					$('#EmpProject :selected').remove(); 
					$("#EmpProject").append($("<option></option>").attr("value","").text("None"));
					
					isExEmpCheck=true;
				}	else	
				{
					$("#function option:contains(HR)").attr('selected', 'selected'); 	
					$("#category option:contains(Exit Related)").attr('selected', 'selected');
					$("#function").attr('disabled','disabled');
					$("#category").attr('disabled','disabled');		
					$("#subcategory option:contains(Exit Related)").attr('selected', 'selected');
					$("#subcategory").attr('disabled','disabled');
					$('#locationId :selected').remove();  
					$('#locationId').append($("<option></option>").attr("value","1").text("Bangalore")).attr('disabled','disabled');
					isExEmpCheck=false;
				}
			
			
		}
		
	    if($("select#EmpProject option").length>1){ 
	    	$("#EmpProject option[value='0']").remove();
	    }
	    
	    updatecontactnumber();
	    if(loginrole=="Level 0 Executive" || loginrole=="Level 0 Manager"){
	    	$("#sourceTR").show();
	    }else{
	    	$("#sourceTR").hide();
	    }
	    $('#osdetails').val($.client.os);
		$('#browserdetails').val($.client.browser +" "+ $.client.browserversion);
		parent.unblockUI();  // For unblocking processing image on load of this page content
		
		//Added to remove Low priority for Premium users
		$.getJSON('validateEmployee.htm',{employeeId:LoginID},function(Emp){
			if(Emp.IS_PREMIUM_MEMBER=="YES"){ 
				premium='Yes';
				$("#priority option[value='1']").remove();
			}
		});		
		if(logged_user_role=="User"){
			parent.document.getElementById("lastframe").src = "UNIVERSAL_Searchh.htm?subject=" + encodeURIComponent($("#subject").val()) + "&userName=" + username + "&empid=" + LoginID;
			parent.myLayout.open('east');
		}
		
		
});

var options = { 
        target:        '#output1',   // target element(s) to be updated with
										// server response
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        dataType:  'json',
        type:'post'
        // other available options:
        // url: url // override for form's 'action' attribute
        // type: type // 'get' or 'post', override for form's 'method' attribute
        // dataType: null // 'xml', 'script', or 'json' (expected server
		// response type)
        // clearForm: true // clear all form fields after successful submit
        // resetForm: true // reset the form after successful submit
 
        // $.ajax options can be used here too, for example:
        // timeout: 3000
    }; 
 
    // bind form using 'ajaxForm'
    $('#createhelpdeskticket').ajaxForm(options); 
 
// pre-submit callback
function showRequest(formData, jqForm, options) { 
	if(validateSpecialCharacters() &&  ValidateBeforeTicketcreation()){
		$("#function").hideAll();
		$("#ticketsubmit").attr("disabled",true);
		$("#ticketcancel").attr("disabled",true);
		return true;
	}else{
		return false;
	}
} 
 
// post-submit callback
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
			successmessage = 'Ticket #('+response.ticketid+') created Successfully !  ';
			if(response.currentstate){
				successmessage +=' Current Status of Ticket:'+response.currentstate+'.  ';
			}
			if(response.ECT){
				successmessage +=' Expected Completion Time :'+response.ECT;
			}
			parent.jbarOnSuccess(successmessage);
	 }
} 


	function showImpactedUserField(checBox,field)
	{
		document.getElementById("impactUserCounttr").style.visibility='visible';

		if(checBox.checked == true){
			document.getElementById("impactUserCounttr").style.visibility='visible';
			$("#impactusercount").val("");
			}
		else {

			document.getElementById("impactUserCounttr").style.visibility='hidden';
		}
	}


	
	var SubCategoryApprovalList = new Array();
	var RecommendedPriorityList = new Array();
	var SubCategoryFormList = new Array();
	var SubCategoryCommentsList = new Array();
	var IsValidEmpId = false;
	var EmpMobile="";
	var EmpExtn="";
		function GetCategories(obj,ElementId){
			prioritychangewarn = false;
			var CategoryId = obj.options[obj.selectedIndex].value; 
			var CategoryName=obj.options[obj.selectedIndex].text;
		 if(ElementId=="category"){
					$("#buildingTR").hide();
					$("#floorTR").hide();
					$("#cubicalTR").hide();
					$("#category").html("");
					$("#subcategory").html("");
					//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
					//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
					//GetSubjectList(CategoryId);
			}else{
				$("#ApprovalAttachmentTR").hide();
				if(ElementId=="subcategory"){
					$("#subcategory").html("");
				}
			}
			if(CategoryId!="0" && ElementId=="category"){
				getLocationsForFunction(CategoryId); 
			}else if(CategoryId=="0" && ElementId=="category"){
				$('#locationId').html($("<option></option>").attr("value",'0').text('--Please Select Function--'));
			}
			if(ElementId=="category"){
				/*
				 * Chnaged By :714599
				 * Comments : TO display the departments dropdown when function selected as HR or hide
				 */
				if($("#"+obj.id+" :selected").text()=="HR"){	
					
					if($("#department").val()=="0"){						
					getAllDepartments();
					$("#departmentTR").show();
					}
				}else{
					$("#departmentTR").hide();
				}

				/*
				 * Chnaged By :714599
				 * Comments : TO display the departments dropdown when function selected as HR or hide
				 */
				if($("#"+obj.id+" :selected").text()=="Finance"){
					$("#locationHeaderTd").html("Location<span class=\"red_text\">*</span>");
				}else{
					$("#locationHeaderTd").html("Current Location<span class=\"red_text\">*</span>");
					$("#locationId").attr("bt-xTitle","Mandatory , Select your current work location");
				}
			}
			if(ElementId=="subcategory"){
				/*
				 * Chnaged By :714599
				 * Comments : TO change the tooltip for location dropdown when category name contains Payroll 
				 */
					var categoryval = $("#"+obj.id+" :selected").text();
				if(categoryval.match("Payroll")){
					$("#locationId").attr("bt-xTitle","Mandatory, Select the location where the query persists");
				}else{
					$("#locationId").attr("bt-xTitle","Mandatory , Select your current work location");
				}
			}
			if(CategoryId!="" && CategoryId!="0"){
				$.getJSON('getHelpDeskCategories.htm',{CategoryId:CategoryId},function(categories){
					var CategoryObj = document.getElementById(ElementId);
						CategoryObj.innerHTML = "";
	
						var optn = document.createElement("OPTION");
			    		optn.value = "";
			    		optn.text = "--Please Select--";
			    		CategoryObj.options.add(optn);
	
			    		if(ElementId=="subcategory"){
				    		SubCategoryApprovalList = new Array();
				    		RecommendedPriorityList = new Array();
							SubCategoryFormList = new Array();
			    		}
			    	    $.getJSON('getHelpDeskCatRoleMapping.htm',{},function(categoryRoleMapping){
								for(var i=0;i<categories.length;i++){
										var optn = document.createElement("OPTION");
										//Modified by 712836
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
										if(ElementId=="subcategory"){												
											SubCategoryApprovalList[categories[i].CATEGORY_ID] = categories[i].IS_CHANGE_REQUEST;
											RecommendedPriorityList[categories[i].CATEGORY_ID] = categories[i].RECOMMENDED_PRIORITY;
											SubCategoryFormList[categories[i].CATEGORY_ID] = categories[i].LINK;
											SubCategoryCommentsList[categories[i].CATEGORY_ID] = categories[i].COMMENTS;
										}
								}
					   });
			    	    if(ElementId=="category" && CategoryName=="IT Infrastructure Management"){	
			    	    $.getJSON('getHelpDeskCatBUMapping.htm',{loginID:LoginID},function(categoryBUMapping){
			    	    	if(categoryBUMapping.length>0){
										var optn = document.createElement("OPTION");
										for(var j=0;j<categoryBUMapping.length;j++)
										{
										optn.value =categoryBUMapping[j].CATEGORY_ID;
										optn.text =categoryBUMapping[j].CATEGORY_NAME;													
										CategoryObj.options.add(optn);
										}
										 $("#category").html($("option", $("#category")).sort(function(a, b) {
									            return a.text == b.text ? 0 : a.text < b.text ? -1 : 1
									        }));
										 $("#category").val("");
							}	
					   });
			    	    }
				});
			}
			$("#formlinktoixchange").html("");
			
		}

		function GetSubjectList(categoryid){
			$.getJSON('getSubjectsforIHD.htm',{CategoryId:categoryid},function(subjects){

				var SubjectObj = document.getElementById("subjectlist");
					SubjectObj.innerHTML = "";
				var optn = document.createElement("OPTION");
		    		optn.value = "";
		    		optn.text = "--Please Select--";
		    		SubjectObj.options.add(optn);
		    		for(var i=0;i<subjects.length;i++){
			    		var optn = document.createElement("OPTION");
				    		optn.value = subjects[i].description;
				    		optn.text = subjects[i].description;
				    		SubjectObj.options.add(optn);
				    }
		    		if(subjects.length > 0){
						var optn = document.createElement("OPTION");
			    		optn.value = "others";
			    		optn.text = "others";
			    		SubjectObj.options.add(optn);
			    		$("#subjectinput").hide();
			    		$("#subjectselect").show();
		    		}else{
		    			if(!$("#subjectinput").is(":visible")){
		    				$("#subject").val("");
		    			}
		    			$("#subjectinput").show();
		    			$("#subjectselect").hide();
		    		}

			});
		}
		function addSubject(obj){
			var subject = obj.options[obj.selectedIndex].value;
			if(subject == "others"){
				$("#subjectinput").show();
			}else{
				$("#subject").val(subject);	
				$("#subjectinput").hide();
			}
		}
		function ValidateEmpId(EmpId){
			//Added to implement the tool to track EX employees quereis for HR function
			var isEXEmployee=$("#isEXEmployeeCheck").val();
			 var checked = $('#isEXEmployeeCheck').attr('checked'); 
			 $("#isEXEmployeeCheck").removeAttr('disabled');
			if(/^[a-zA-Z0-9_.]+$/.test(EmpId) ){	
				
				if(isExEmpCheck==true){					
					$.getJSON('validateEmployee_ex.htm',{employeeId:EmpId},function(Emp){
						
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
								//Added to remove Low priority for Premium users
								//$("#priority option[value='1']").remove(); 			
							}
							IsValidEmpId =true; 
							if(Emp.IS_ACTIVE=="1"){
								$("#isEXEmployeeCheck").attr('checked',false);
								$("#isEXEmployeeCheckTR").hide();
								$("#isEXEmp_Value").val(0);
								getEmployeeMobileAndExtn();
								if(EmpId!=LoginID)
								{
									//cubicleCodeEdit();
								}
								else
								{
									getEmpCubicleCodeMappedOrNot();
								}
							}else{
								$("#isEXEmployeeCheck").attr('checked',true);
								$("#isEXEmployeeCheckTR").show();
								$("#isEXEmployeeCheck").attr('disabled','disabled');
								$("#isEXEmp_Value").val(1);
								var seloption = $("#phoneoption").find("option:selected").text();
								if(seloption=="Mobile"){
									$("#phonenumber").val("918041041801");
								}else{
									$("#phonenumber").val("1234");
								}
								EmpMobile="918041041801";
								EmpExtn="1234";
							}
							
							$('#locationId :selected').remove();  
							$('#locationId').append($("<option></option>").attr("value","1").text("Bangalore"));
							$('#EmpProject :selected').remove(); 
							$("#EmpProject").append($("<option></option>").attr("value","").text("None"));	
							if($("#function option:selected").text()== "HR"){								
								getAllDepartments();
								$("#department option[text='28']").attr("selected",true); 
								$("#reqDeptName").attr("style", "display:none");
								$("#reqDeptName").hide();
								$("#deptTD").attr("style", "display:none");
								$("#deptTD").hide();
								
							}
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
				$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
		
					if(Emp.NAME){
						document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("valid_text");
						$("#empResultMessage").show(); 
				
						/*if(Emp.IS_PREMIUM_MEMBER=="YES"){  
													
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
							//Added to remove Low priority for Premium users
							$("#priority option[value='1']").remove(); 
						}
						else
						{		
								var a =$('#priority option').text();
								if(a.indexOf("LOW") ==-1)
								 {
									$('#priority').append($("<option selected='selected'></option>").attr("value","1").text("LOW"));
									
								 }
									
						}*/

						IsValidEmpId =true; 
						$("#approvalFlag").val("0");
						getProjectsForEmployee();
						getEmployeeMobileAndExtn();
						if(EmpId!=LoginID)
						{
							//cubicleCodeEdit();
						}
						else
						{
							getEmpCubicleCodeMappedOrNot();
						}
						if($("#function option:selected").text()== "HR"){
							getAllDepartments();
						}
					}
					else{
						

						document.getElementById("empResultMessage").innerHTML ="Please enter Authorized Employee id and click on 'Validate' to verify";
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("invalid_text");
						$("#empResultMessage").show();
						IsValidEmpId = false;
					}
				});
				}
				}else{
				document.getElementById("empResultMessage").innerHTML ="Please enter Authorized Employee id and click on 'Validate' to verify";
				$("#empResultMessage").removeClass("valid_text");
				$("#empResultMessage").addClass("invalid_text");
				$("#empResultMessage").show();
				IsValidEmpId = false;
			}
			
			$("#onbeofEmpId").hideerrormessage();
		}


		function ValidateBeforeTicketcreation(){

			$("#function").hideAll();
			var isValidationError = false;

			if($("#function").val()=="0"){
				$("#function").focus();
				$("#function").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			var selectedfunction = $("#function option:selected").text(); 
			if($("#category").val()=="" && (selectedfunction=="Life and Health Operations Canada")){// Added by Nazeeb as part of L2 : 4369
				$("#category").focus();
				$("#category").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			if($("#subcategory").val()=="" && (selectedfunction=="Life and Health Operations Canada")){ // Added by Nazeeb as part of L2 : 4369
				$("#subcategory").focus();
				$("#subcategory").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}

	
			if($("#priority").val()=="0"){
				$("#priority").focus();
				$("#priority").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			
			if($("#locationId").val()=="0" || $("#locationId").val()==""){
				$("#locationId").focus();
				$("#locationId").showerrormessage({message:"* Mandatory"});
				isValidationError =true; 
			}
		
			var sub=jQuery.trim($("#subject").val());
			if($("#subjectselect").is(":visible")){
				if($("#subjectlist").val()=="" || $("#subjectlist").val()==null){
					$("#subjectlist").focus();
					$("#subjectlist").showerrormessage({message:"* Mandatory"});
					isValidationError = true;
				}else if($("#subjectlist").val()=="others" && sub==""){
					$("#subject").focus();
					$("#subject").showerrormessage({message:"* Mandatory"});
					isValidationError = true;
				}
			}else{
				if(sub==""){
					$("#subject").focus();
					$("#subject").showerrormessage({message:"* Mandatory"});
					isValidationError = true;
				}
			}
			var desc=jQuery.trim($("#description").val());
			if(desc==""){
				$("#description").focus();
				$("#description").showerrormessage({message:"* Mandatory"});
				isValidationError = true;
			}
			var problemfileval = $("#problemfile").val();
				if((problemfileval.length) >0){
					problemfileval = problemfileval.substring(problemfileval.lastIndexOf("\\")+1, problemfileval.lastIndexOf("."));
					if(problemfileval.length >150){
						$("#problemfile").focus();
						$("#problemfile").showerrormessage({message:"* File name should not exceed more than 150 characters"});
						isValidationError = true;
					}
				} 
			var approvalfileval = $("#approvalfile").val();
			if((approvalfileval.length) >0){
				approvalfileval = approvalfileval.substring(approvalfileval.lastIndexOf("\\")+1, approvalfileval.lastIndexOf("."));
				if(approvalfileval.length >150){
					$("#approvalfile").focus();
					$("#approvalfile").showerrormessage({message:"* File name should not exceed more than 150 characters"});
					isValidationError = true;
				}
			}
			
			if($("#onbeofEmpId").val()!="" && !IsValidEmpId){
				$("#onbeofEmpId").focus();
				$("#onbeofEmpId").showerrormessage({message:"Please enter Authorized Employee id and click on 'Validate' to verify"});
				isValidationError =true;
			}
			if($("#mailTrackerType").val()=="HR"){
				if($("#onbeofEmpId").val()==""){
					$("#onbeofEmpId").focus();
					$("#onbeofEmpId").showerrormessage({message:"Please enter Authorized Employee id and click on 'Validate' to verify"});
					isValidationError =true;
					return !isValidationError;
			}
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
				}else if($("#phoneoption").val()=="Mobile" && (phonenum.length<10 || phonenum.length >15)){
					$("#phonenumber").focus();
					$("#phonenumber").showerrormessage({message:"Please provide 10-15 digit number"});
					isValidationError =true;
				}else if($("#phoneoption").val()=="Extn" && phonenum.length >15){
					$("#phonenumber").focus();
					$("#phonenumber").showerrormessage({message:"Please provide 10-15 digit number"});
					isValidationError =true;
				}
			}
			if($("#altcontactnumber").val()!=""){
				if(!(/^[0-9]+$/.test($("#altcontactnumber").val()))){
					$("#altcontactnumber").focus();
					$("#altcontactnumber").showerrormessage({message:"Not a Valid Number"});
					isValidationError =true;
					}
			}
			if($("#EmpProject").val()==""){
				
				if($("#toAddressMailId").val()==null ){
					$("#EmpProject").focus();
					$("#EmpProject").showerrormessage({message:"* Mandatory."});
					isValidationError =true;
				}
			}
			
			var emailaddressVal = $("#CCEmailID").val();
		
	        if(emailaddressVal!="" && !validateEmailIds(emailaddressVal)) {	
	        	$("#CCEmailID").focus();
	        	$("#CCEmailID").showerrormessage({message:"one or more email addresses entered is invalid!"});
	            isValidationError =true;
	        }
	        if($("#impactusercheck").attr("checked") && $("#impactusercount").val()==""){
	        	$("#impactusercount").focus();
	        	$("#impactusercount").showerrormessage({message:"* Mandatory"});
	        	isValidationError =true;
	        }else if($("#impactusercheck").attr("checked") && !(/^[0-9]+$/.test($("#impactusercount").val()))){
	        	$("#impactusercount").focus();
				$("#impactusercount").showerrormessage({message:"Invalid Number !"});
				isValidationError =true;
			}else if($("#impactusercheck").attr("checked") && $("#impactusercount").val()=="0"){
				$("#impactusercount").focus();
				$("#impactusercount").showerrormessage({message:"Please provide Impacted User count >0"});
	        }
	        	$('#projectname').val($('#EmpProject option:selected').text()); 
	        return !isValidationError;
		}
		function validateSpecialCharacters(){

			$("#function").hideAll();
			var isValidationError = true;
			if(document.getElementById("floorTR").style.display!="none"){
				var cubicalcode = jQuery.trim($("#cubicalcode").val());
				if($("#cubicalcode").is(":visible") && checkspecialcharacters(cubicalcode)){
					$("#cubicalcode").showerrormessage({message:"&, <, >, \",= and ~ characters are not allowed"});
					isValidationError =false;
				}
			}
			
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
		    return checkstatus;
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
		
		var prioritychangewarn =false; 
		var odclocationsretrieved = false; // For ODC's and Admin function

	 	function ShowApprovalAttachment(subCatgID){
	 		var subCategoryId = subCatgID.options[subCatgID.selectedIndex].value;
	 		var functionvalue = $("#function option:selected").text();
	 		var catvalue = $("#category option:selected").text();
			var subCatValue = $("#subcategory option:selected").text();
	 		
				if(SubCategoryApprovalList[subCategoryId] && (functionvalue=="Life and Health Operations Canada")){
					alert(" Please Note: Selected Category and SubCategory combination will go for Approval.");
		 			$("#ApprovalAttachmentTR").hide();
					//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
					//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
				}
				else{
		 			$("#ApprovalAttachmentTR").hide();
					//$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
					//$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
		 		}
				if(RecommendedPriorityList[subCategoryId]){
					$("#priority option[value='"+RecommendedPriorityList[subCategoryId]+"']").attr("selected",true);
					prioritychangewarn = true;
				}else{
					prioritychangewarn = false;
				}
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
				filterLocations(subCategoryId);
		 	}   

	 	function showwarnmessage(){
	 		if(prioritychangewarn){
				var subCategoryId = $("#subcategory option:selected").val();
	 			var recomededpriority = RecommendedPriorityList[subCategoryId];
	 				if(recomededpriority=="1")
						recomededpriority ="LOW";
					if(recomededpriority=="2")
						recomededpriority ="MEDIUM";
					if(recomededpriority=="3")
						recomededpriority ="HIGH";

	 			if(recomededpriority!=$("#priority option:selected").text()){
	 				alert('Recommended priority for selected category and sub category is '+recomededpriority+'. This will be changed back to original value as per defined Policies.')
	 			}
	 		}
	 	}
	 	
		var SelectedObj ="";
		var SelIds = "";

		function downloadAttachment(path,name){
			var json = '{"ATTACHMENT_PATH":"'+path+'","ATTACHMENT_NAME":"'+name+'"}'; 
				json = json.replace(/&/g,"*amper*");
			window.location.href ="DownloadAttachment.htm?json="+json; 
		}
		
		function showAttachmentSelect(){
			$("#attachmentSelect").show();
			$("#attachmentDisplay").hide();
		}
		
		function getProjectsForEmployee(){
			var EmpId = "";
			if(IsValidEmpId){
				EmpId = $("#onbeofEmpId").val(); 
			}else if(!IsValidEmpId){
				EmpId = LoginID;
			}
			$("#EmpProject").html("");
			$.getJSON('getProjectList.htm',{employeeid:EmpId},function(projects){
				for(var i=0;i<projects.length;i++){
					$('#EmpProject').append($("<option></option>").attr("value",projects[i].PROJECT_VALUE).text(projects[i].DISPLAY_NAME)); 
				 }
				if($("select#EmpProject option").length==0){ 
				    	$('#EmpProject').append($("<option></option>").attr("value","0").text("None"));
				 }
			}); 
		}

		function getEmployeeMobileAndExtn()
				{
					if(IsValidEmpId){
						EmpId = $("#onbeofEmpId").val(); 
					}else if(!IsValidEmpId){
						EmpId = LoginID;
					}
					
					$.getJSON('getEmployeeMobileAndExtn.htm',{employeeid:EmpId},function(MobileAndExtn){
						
							var seloption = $("#phoneoption").find("option:selected").text();
							
							if(seloption=="Mobile"){
								$("#phonenumber").val(MobileAndExtn[0]);
							}else{
								$("#phonenumber").val(MobileAndExtn[1]);
							}
							EmpMobile=MobileAndExtn[0];
							EmpExtn=MobileAndExtn[1];
							
						
					});
				}




		function updatecontactnumber(){
			var seloption = $("#phoneoption").find("option:selected").text();
			if(IsValidEmpId){
				var seloption = $("#phoneoption").find("option:selected").text();
				if(seloption=="Mobile"){
					$("#phonenumber").val(EmpMobile);
				}else{
					$("#phonenumber").val(EmpExtn);
				}
					
			}else if(!IsValidEmpId){
				if(seloption=="Mobile"){
					$("#phonenumber").val(mobilenumber);
				}else{
					$("#phonenumber").val(extensionnumber);
				}
			}
			
			
		}
		
		function checkempid()
		{
			 
			if( $("#onbeofEmpId").val()=="")				
			{
				$("#empResultMessage").hide();
				var seloption = $("#phoneoption").find("option:selected").text();
				 if(!IsValidEmpId)
					 {
						if(seloption=="Mobile")
							{
								$("#phonenumber").val(mobilenumber);
							}
							else
							{
								$("#phonenumber").val(extensionnumber);
							}
					}
			}
			else
			{
				$("#empResultMessage").show();
			}
			
		}

		function clearproblemattachment(){
			 $("#problemattachTD").html('<input name="problemfile" id="problemfile" title="Attach supporting document related to your problem. (Screen shots, Image, Documents)"  type="file"/> &nbsp; <a href="#" class="label" onclick="clearproblemattachment()" >Clear Attachment</a>');
		}
		function clearapproveattachment(){
			 $("#approverattachTD").html('<input name="approvalfile" id="approvalfile" title="Attach email or other documents from your approving manager approving your request" type="file"/> &nbsp; <a href="#" class="label" onclick="clearapproveattachment()">Clear Approver Attachment</a>');
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
		
		function getLocationsForFunction(function_id)
		{
			$.getJSON('getLocationListForFunction.htm',{functionid:function_id},function(locations){
				
				$('#locationId').html($("<option></option>").attr("value",'0').text('--Please Select--'));
				for(var i=0;i<locations.length;i++){
					$('#locationId').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
				 }
				$('#buildingId').html($("<option></option>").attr("value",'').text('--Please Select Location--'));
				$('#floor').html($("<option></option>").attr("value",'').text('--Please Select Building--'));
				
			if($("#function :selected").text()=="IT Infrastructure Management"){ 
				getEmpCubicleCodeMappedOrNot();
			}
			else
			{
				//cubicleCodeEdit();
			}
			});
		}
		/* Changed By :714599
		 * Below fucntion using to filter the locations on selection of category
		 */ 

		function filterLocations(categoryID){
			$.ajaxSetup({ cache: false });
				$.getJSON('getLocationListForSubcategory.htm',{categoryid:categoryID},function(locations){
					
					$('#locationId').html($("<option></option>").attr("value",'0').text('--Please Select--'));
					for(var i=0;i<locations.length;i++){
						$('#locationId').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
					 }
					$('#floor').html($("<option></option>").attr("value",'').text('--Please Select Building--'));					
				});
		}

		function showPriority(id){
			var id=$(id).attr("id");
			var catId="";
			var category="category";
			if(id=="category"){
				catId=$('#'+id).val();
			}else if(id="subcategory"){
				catId=$('#'+id).val();
				if(catId==""){
					catId=$('#'+category).val();
				}
			}
			
			$.getJSON('getPriorityBasedOnCategory.htm',{CategoryId:catId},function(priorities){
				$('#priority').html("");
				$.each(priorities, function(key, value) { 
				     $('#priority')
				         .append($("<option></option>")
				         .attr("value",value.RECOMMENDED_PRIORITY)
				         .text(value.DESCRIPTION));
				});
			});
		}
		
		function replaceMSWordCharacters(text) {
			text.value = text.value.replace(/[\u2018\u2019\u201A]/g, "\'");	
			text.value = text.value.replace(/[\u201C\u201D\u201E]/g, "\"");
			text.value = text.value.replace(/[\t]/g, " ");
			text.value = text.value.replace(/[\u2013\u2014]/g, "-");
			text.value = text.value.replace(/[\u2022]/g, "*");
		}
		
		
		