$(document).ready(function() {	
	$.ajaxSetup({ cache: false });
	parent.unblockUI();
	$.getJSON('bulletinMessage.htm',{},function(data) {
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data);
		}
		else {
		 for(var i=0;i<data.length;i++){
			 dataToInsertPT = '<tr id="'+data[i].BULLETIN_ID+'" class="creationScreenAlternateTR"><td align="left"><input type="checkbox" name="showInfo" id="checkBulletinMsg" value="'+data[i].BULLETIN_ID+'"/></td><td align="left">'+data[i].BULLETIN_DESCRIPTION+'</td></tr>';	
			$('#bulletinMsg tr:last').after(dataToInsertPT);	
		 }
		}
	});	
	$('#RemoveMsg').click(function() {
		var json;
		var n = $("input:checked").length; 
		var methodArray=new Array();
		var selectedTicketArray = "";
		$(":checked").each(function()
				{
				selectedTicketArray = selectedTicketArray +","+$(this).val();
				methodArray.push($(this).val());	
		});
		json='['+iteration(methodArray)+']';
		function iteration(array){ 									 
				for (var i = 0; i < array.length; i++){ 
								  array[i]= "\""+array[i]+"\"";										
				} 
				return array; 
		} 		
			$.getJSON('removeBulletinBoardMessage.htm',{jsonstring:json},function(data) {	
				
				if (data=="Error") {	
					alert("Error");
				}
				else {
					alert("Success");
									
					$('#bulletinMsg tr').find("input:checked").closest('tr').remove();
				//window.location.reload(true);
				parent.displaySubMenu('115','Bulletin Board');
}
			});//getjson is ending here
	});
});
