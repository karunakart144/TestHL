
var options = {
	    target: '#outputSaveSol',
	    beforeSubmit: showRequestSaveSol,
	    success: showResponseSaveSol,
	    dataType: 'json',
	    type: 'post'
	};

function saveTagComment()
{
  $("#UserComment").submit();
}


	function jbarOnSuccessSaveSol(message) {
	    $("#jbarmessageSaveSol").bar({
	        color: 'BLACK',
	        background_color: '#ffebbf',
	        position: 'bottom',
	        removebutton: true,
	        message: message,
	        time: 1000000
	    });
	}

	function jbarOnFailureSaveSol(message) {
	    $("#jbarmessageSaveSol").bar({
	        color: 'BLACK',
	        background_color: 'RED',
	        position: 'bottom',
	        removebutton: true,
	        message: message,
	        time: 1000000
	    });
	}
	$('#UserComment').ajaxForm(options);
	function showRequestSaveSol(formData, jqForm, options) {

       if(jQuery.trim($('#tagDesc').val()).length > 0)
       {
    	   return true;
       }else
       {
    	   jbarOnSuccessSaveSol("Please add your solution before post.");
           return false;  
           
       }

	}
function showResponseSaveSol(response, statusText, xhr, $form)
{
	

    if(response.result == "1")
    {
    	$('#tagDesc').val('');
    	jbarOnSuccessSaveSol("Your solution has been posted.")
    }else{
    	jbarOnFailureSaveSol("Your solution has not been posted.")
    }
}

