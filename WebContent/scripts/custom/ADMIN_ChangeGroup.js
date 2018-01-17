$(document).ready(function() {		
		//var IsValidEmpId = false;
		$("#empResultMessage").hide();
		$("#REMOVE_GROUP_ID").change(function() { 
		var groupId = $(this).val();			
			$.getJSON('getGroupMember.htm',{groupId : groupId},function(data) {					
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {
				var groupMemberListOptions = [];
					groupMemberListOptions.push('<option value="0">--Please Select--</option>');
					for(var i=0;i<data.length;i++){
						groupMemberListOptions.push('<option value="'+ data[i].member_id+ '">'+ data[i].member_name_id+ '</option>');
					}
				$('#GROUP_MEMBER_ID').html(groupMemberListOptions.join(''));
				}
			});
		});
		$("#function").change(function(){
			var functionId = $(this).val();			
			$.getJSON('getGroup.htm',{functionId : functionId},function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {
					var groupListOptions = [];
					groupListOptions.push('<option value="0">--Please Select--</option>');
					for(var i=0;i<data.length;i++){
						groupListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
					}
				$('#REMOVE_GROUP_ID').html(groupListOptions.join(''));
				}
			});		
		});
		$("#removeGroupMem").click(function() { 
				var groupID=$("#REMOVE_GROUP_ID").val();
				var groupMemID=$("#GROUP_MEMBER_ID").val();								
				
				if(getstatus()==true){				
					$.getJSON('removeGroupMember.htm',{groupID : groupID,groupMemID:groupMemID},function(data) {
						if(data.status=="success")
							parent.jbarOnSuccess('Removed '+$("#GROUP_MEMBER_ID").find("option:selected").text()+' from '+$("#REMOVE_GROUP_ID").find("option:selected").text()+' Successfully');
						else
							parent.jbarOnFailure('Ticket# '+data.TicketID+' is assigned to '+$("#GROUP_MEMBER_ID").find("option:selected").text()+'. Kindly reassign the tickets then try removing');
					});
				}
		});
		parent.unblockUI();  // For unblocking processing image on load of this page content
});

function getstatus(){
		var status = true;		
		if(($('#REMOVE_GROUP_ID').val()=="")||($('#REMOVE_GROUP_ID').val()=="--Please Select--")||($('#REMOVE_GROUP_ID').val()==0)){	
			var id='REMOVE_GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='REMOVE_GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
		}		
		if(($('#function').val()=="")||($('#function').val()=="--Please Select--")||($('#function').val()==0)){	
			var id='function';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='function';
			$('#TextFieldSpan'+id+'').text('');
		}		
		if(($('#GROUP_MEMBER_ID').val()=="")||($('#GROUP_MEMBER_ID').val()=="--Please Select--")||($('#GROUP_MEMBER_ID').val()==0)){	
			var id='GROUP_MEMBER_ID';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='GROUP_MEMBER_ID';
			$('#TextFieldSpan'+id+'').text('');
		}		
	return status;
}