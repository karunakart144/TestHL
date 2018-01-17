$(document).ready(function(){
	
	$("#generateReport").click(function() {
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
	$('#startDate').datepicker({
		beforeShow: customStartDateRange
	}).show();	

	$("#startDate").change(function() {   
		if($("#startDate").val()=="" || $("#startDate").val()==null){
			$('#endDate').attr("disabled",true);
		}
		else{
			$('#endDate').attr('value', '');
			$('#endDate').attr("disabled",false);	
			$('#endDate').datepicker({
			beforeShow: customEndDateRange
			}).show();
		}
		
	});
	
		$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");

		$('textarea[maxlength],input[maxlength]').on('keyup blur', function() {
	        // Store the maxlength and value of the field.
	        var maxlength = $(this).attr('maxlength');
	        var val = $(this).val();

	        // Trim the field if it has content over the maxlength.
	        if (val.length > maxlength) {
	            $(this).val(val.slice(0, maxlength));
	        }
		});
	    
	    $('#osdetails').val($.client.os);
		$('#browserdetails').val($.client.browser +" "+ $.client.browserversion);
		parent.unblockUI();  // For unblocking processing image on load of this page content
		
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
    $('#generateReport').ajaxForm(options); 
 
// pre-submit callback
function showRequest(formData, jqForm, options) { 

	if(ValidateBeforeGeneratingReport()){
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
		$("#reportSubmit").removeAttr("disabled");
	 }else if(response.exception){
		 parent.jbarOnFailure(response.message);
		 $("#reportSubmit").removeAttr("disabled");
	 }
	 else {
		 parent.jbarOnSuccess(response);
	 }
} 
	
	var SubCategoryApprovalList = new Array();
	var RecommendedPriorityList = new Array();
	var SubCategoryFormList = new Array();
	var SubCategoryCommentsList = new Array();
	var IsValidEmpId = false;
		function GetCategories(obj,ElementId){
			var CategoryId = obj.options[obj.selectedIndex].value; 
			var CategoryName=obj.options[obj.selectedIndex].text;
		 if(ElementId=="category"){
					$("#buildingTR").hide();
					$("#floorTR").hide();
					$("#cubicalTR").hide();
					$("#category").html("");
					$("#subcategory").html("");
					$("#ticketcreationtable tr").removeClass("creationScreenAlternateTR");
					$("#ticketcreationtable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
			}else{
				$("#ApprovalAttachmentTR").hide();
				if(ElementId=="subcategory"){
					$("#subcategory").html("");
				}
			}

			if(CategoryId!="" && CategoryId!="0"){
				$.getJSON('getHelpDeskCategories.htm',{CategoryId:CategoryId},function(categories){
					var CategoryObj = document.getElementById(ElementId);
						CategoryObj.innerHTML = "";
	
						var optn = document.createElement("OPTION");
			    		optn.value = "9999";
			    		optn.text = "--Select All--";
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
								}
					   });
				});
			}
			$("#formlinktoixchange").html("");
			
		}

		function ValidateBeforeGeneratingReport(){
	    	$(".formError").hide();
			var isValidationError = false;
			
			if($.trim($("#function").val())==""){
				$("#function").focus();
				$("#function").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			if($.trim($("#startDate").val())==""){
				$("#startDate").focus();
				$("#startDate").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			if($("#endDate").val()==""){
				$("#endDate").focus();
				$("#endDate").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}

			return !isValidationError;
		}
		
		var SelectedObj ="";
		var SelIds = "";

		function downloadAttachment(path,name){
			var json = '{"ATTACHMENT_PATH":"'+path+'","ATTACHMENT_NAME":"'+name+'"}'; 
				json = json.replace(/&/g,"*amper*");
			window.location.href ="DownloadAttachment.htm?json="+json; 
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
		function customStartDateRange(input){	
			var maxRange = new Date($("#currentDate").val());
			return {
					 dateFormat: 'mm/dd/yy',
					  maxDate: maxRange
				};
		}
		function customEndDateRange(input){
			
		var minRange = new Date($("#startDate").val());
	    var maxRange = new Date($("#currentDate").val());
	    return {
					 dateFormat: 'mm/dd/yy',
					 minDate: minRange,
					 maxDate: maxRange
				};
			
		}
		
		function getCurrentStatusDropDown(){
				$("#CurrentStatusTR").show();
		}
		
		function removeCurrentStatusDropDown(){
				$("#CurrentStatusTR").hide();
				$("#status").val("0");
				
		}