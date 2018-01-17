$(document).ready(function() {
	
	$("#viewGroupMember").click(function() { 
		var groupId = $("#GROUP_ID").val();		
		var groupMemOption=$("input[@name='groupMember']:checked").val();
		if(getstatus()==true){
			$.getJSON('getGroupMemberDetails.htm',{groupId : groupId,groupMemOption:groupMemOption},function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data);
				}
				else {
					var htmldata="<tr><th>Group Members</th><th>Group Members Status</th></tr>";
					for(var i=0;i<data.length;i++){	
						htmldata+='<tr><td>'+data[i].member_name_id+'</td><td>'+data[i].Group_Member_Status+'</td></tr>';
					}
					$("#groupMemrowcss").html(htmldata);
				}
			});
		}
	});

	$("#functionToAdd").change(function(){
				var functionId = $(this).val();			
			$.getJSON('getGroup.htm',{functionId : functionId},function(data) {	
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data);
				}
				else {
					var groupListOptions = [];
					groupListOptions.push('<option value="0">--Please Select--</option>');
					for(var i=0;i<data.length;i++){
						groupListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
					}				
				$('#GROUP_ID').html(groupListOptions.join(''));
				}
			});					
	});		
	parent.unblockUI();  // For unblocking processing image on load of this page content
});
function getstatus(){
		var status = true;		
		if (!$("input[@name='groupMember']:checked").val()) {	
			var id='all';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='all';
			$('#TextFieldSpan'+id+'').text('');
		}						
		if(($('#functionToAdd').val()=="")||($('#functionToAdd').val()=="--Please Select--")||($('#functionToAdd').val()==0)){	
			var id='functionToAdd';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='functionToAdd';
			$('#TextFieldSpan'+id+'').text('');
		}		
		if(($('#GROUP_ID').val()=="")||($('#GROUP_ID').val()=="--Please Select--")||($('#GROUP_ID').val()==0)){	
			var id='GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
		}		
	return status;
}
function groupMemStatus(){
	var groupId = $("#GROUP_ID").val();		
		var groupMemOption=$("input[@name='groupMember']:checked").val();
		if(getstatus()==true){
			$.getJSON('getGroupMemberDetails.htm',{groupId : groupId,groupMemOption:groupMemOption},function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data);
				}
				else {
					var htmldata="<tr><th>Group Members</th><th>Group Members Status</th></tr>";
					for(var i=0;i<data.length;i++){	
						htmldata+='<tr><td>'+data[i].member_name_id+'</td><td>'+data[i].Group_Member_Status+'</td></tr>';
					}
					$("#groupMemrowcss").html(htmldata);
				}
			});
		}
}