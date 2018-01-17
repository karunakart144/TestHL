$(document).ready(function() {
	
	$.getJSON('getResetCacheMethodsForWorkflow.htm',{},function(data) {
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data);
		}
		else {
		 for(var i=0;i<data.length;i++){
			 dataToInsertPT = '<tr  class="creationScreenAlternateTR"><td align="left"><input type="checkbox" name="showInfo" id="checkResetCacheForWorkflow"/></td><td align="left">'+data[i]+'</td></tr>';	
			$('#resetCache tr:last').after(dataToInsertPT);	
		 }
		}
	});	
	$('#workflowCache').click(function() {
		var methodArray=new Array();
		var json;
			$('#resetCache tr').each(function(index) {			
				if(($(this).find("#checkResetCacheForWorkflow").attr('checked')==true)){
						methodArray.push($(this).find("td").eq(1).text());						
				}
			});			
			json='['+iteration(methodArray)+']';
			function iteration(array){ 									 
					for (var i = 0; i < array.length; i++){ 
									  array[i]= "\""+array[i]+"\"";										
					} 
					return array; 
			} 		
			$.getJSON('invokeResetCacheMethodsForWorkflow.htm',{jsonstring:json},function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data);
				}
				else {
				if(data==true){
					alert("Success");
					$('input[type=checkbox]').removeAttr('checked');					
					}
				}
			});//getjson is ending here
	});//Click function is ending here
	
	parent.unblockUI();  // For unblocking processing image on load of this page content
});


