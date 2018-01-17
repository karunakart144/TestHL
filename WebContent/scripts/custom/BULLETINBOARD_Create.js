$(document).ready(function() {	

	parent.unblockUI();
});
var options = { 
        target:        '#output1',
       beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  
        dataType:  'json',
        type:'post'
    }; 
$('#createBulletinBoardBeanId').ajaxForm(options); 
function showResponse(response, statusText, xhr, $form)  { 
		var Error = response.error;
	 if(response.error) {
		jQuery.each(response, function(key, val) {			
			$("#"+key+"").showerrormessage({message:val});
		});
		$("#Save").removeAttr("disabled");
	 }else if(response.exception){
		 parent.jbarOnFailure(response.message);
		 $("#Save").removeAttr("disabled");
	 }
	 else{
			$('input, select,textarea').attr('disabled', true); 
			var successmessage = "";
			successmessage = 'Bulletin Message has been hosted Successfully !  ';
			parent.jbarOnSuccess(successmessage);
			parent.displaySubMenu('115','Bulletin Board');
	 }
}
function showRequest(formData, jqForm, options) { 
	// var queryString = $.param(formData);
 // alert('About to submit: \n\n' + queryString);
	if(ValidateBeforeTicketcreation()){
		return true;
	}else{
		return false;
	}
} 
function ValidateBeforeTicketcreation(){
	var isValidationError = false;
	if(jQuery.trim($("#description").val())==""){
				$("#description").focus();
				$("#description").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			if(jQuery.trim($("#header").val())==""){
				$("#header").focus();
				$("#header").showerrormessage({message:"* Mandatory"});
				isValidationError =true;
			}
			if($("#description").val().length > 500)
			{
				
				$("#description").focus();
				$("#description").showerrormessage({message:"Characters should not exceed 500"});
				isValidationError =true;
			}

return !isValidationError;
}
function countCharacters(id,max_chars,myelement)  
{ 
		
	counter = document.getElementById(id);  
field = document.getElementById(myelement).value;  
field_length = field.length;  
 if (field_length <= max_chars)  {     
// Here we Calculate remaining characters     
remaining_characters = max_chars-field_length;   
  // Now Update the counter on the page    
counter.innerHTML = remaining_characters;  
  }  
 }   
