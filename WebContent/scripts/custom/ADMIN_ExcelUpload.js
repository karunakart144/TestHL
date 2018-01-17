	  $(document).ready(function() {	
		  parent.unblockUI(); 	

		  //Start
		  
		  $('#tableName').autocomplete({ 
              source: function( request, response ) {
              $.ajax({
                    url: "get_country_list.htm",
                    dataType: "json",
                    data: {
                          maxRows:12,
                          term: request.term
                    },
                    success: function( data ) {
                          response( $.map(data, function(item){                                     
                                return {
                                      label: item.Table_Name,
                                      value: item.Table_Name,
                                      
                                }
                          }));
                        
                    }
              });
        },
        minLength: 2,
        select: function( event, ui ) {
        	
             $('#tableName').text( ui.item.label);
              $('#tableName').val(ui.item.value);
        },
        open: function() {
              $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
        },
        close: function() {
              $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
        }
  });


		  //End
			  
});
	  

var options = { 
	        target:        '#output1',   // target element(s) to be updated with
	      								// server response
	        beforeSubmit:  showRequest,  // pre-submit callback
	        success:       showResponse,  // post-submit callback
	        dataType:  'json',
	        type:'post'
	
	    }; 
	 
	    // bind form using 'ajaxForm'
		$('#UploadFormID').ajaxForm(options); 

		
function validateForm()
{
	var sheetName=$('#sheetName').val();
	var tableName=$('#tableName').val();
	var ExcelUpload=$('#ExcelUpload').val();
	var isValidationError = false;
	if(ExcelUpload==""||ExcelUpload==null){				
		document.getElementById("ExcelUpload").focus();
		document.getElementById("ExcelUploadResultMessage").innerHTML="* Mandatory";
		isValidationError =true;
		}
	 if(jQuery.trim($("#tableName").val())==0){				
		document.getElementById("tableName").focus();
		document.getElementById("tableNameResultMessage").innerHTML="* Mandatory";
		tableNameResultMessage
		isValidationError =true;
		}
	if (jQuery.trim($("#sheetName").val())=="")
		{
		document.getElementById("sheetName").focus();
		document.getElementById("sheetResultMessage").innerHTML="* Mandatory";
		isValidationError =true;
		}
	else
	{
		document.getElementById("ExcelUploadResultMessage").innerHTML=" ";
		document.getElementById("tableNameResultMessage").innerHTML=" ";
		document.getElementById("sheetResultMessage").innerHTML=" ";
	}
	return !isValidationError;
}

	    
	    function showRequest(formData, jqForm, options) {
	    	if(validateForm()){
	    			$("#UploadID").attr("disabled",true);
	    			$("#ExcelUpload").attr("disabled",true);
	    			$("#tableName").attr("disabled",true);
	    			$("#sheetName").attr("disabled",true);
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
			$("#UploadID").removeAttr("disabled");
		 }else if(response.exception){
			 parent.jbarOnFailure("Exception Occurred! ");
			 $("#UploadID").removeAttr("disabled");
		 }
		  if(response.status=="SUCCESS")
		  	{
				$('input, select').attr('disabled', true); 
				var successmessage = "";
				successmessage = 'Successfully Updated !  ';
				
				parent.jbarOnSuccess(successmessage);
		 }
		  else
		  {
			  $('input, select').attr('disabled', true); 
				var successmessage = "";
				successmessage +=' Not Updated ! Occured Exception '+response.exception;		
				parent.jbarOnSuccess(successmessage);
		  }
	}
