$(document).ready(function() {		
		
		$("#empResultMessage").hide();
		
		
		$("#addGroupMember").click(function() { 
				var empID=$("#Employee_ID").val();
				var groupID=$("#ADD_GROUP_ID").val();
				var roleID=$("#ADD_ROLE").val();
			if(getstatus()==true){
				$.getJSON('addGroupMember.htm',{groupID : groupID,empID:empID,roleID:roleID},function(data) {
					if (data.status && data.status=="Error") {	
						parent.jbarOnFailure(data.message);
					}
					else {
					if(data==1){
						parent.jbarOnSuccess('Employee '+$("#Employee_ID").val()+'('+$("#empResultMessage").html()+') added in '+$("#ADD_GROUP_ID").find("option:selected").text()+' Successfully');
					}
					else{
						parent.jbarOnFailure('Employee '+$("#Employee_ID").val()+'('+$("#empResultMessage").html()+') already exists in '+$("#ADD_GROUP_ID").find("option:selected").text()+'');
					}
					$("#addGroupMember").attr("disabled",true);
					}
				});
			}
		});		

		$("#functionToAdd").change(function(){
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
				$('#ADD_GROUP_ID').html(groupListOptions.join(''));
				}
			});	
			
			
			$.getJSON('getRole.htm',{functionId : functionId},function(data) {	
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {					
				var roleListOptions = [];
				roleListOptions.push('<option value="0">--Please Select--</option>');
				for(var i=0;i<data.length;i++){
					roleListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
				}				
			$('#ADD_ROLE').html(roleListOptions.join(''));
				}
			});	//getRole.htm
		});//function to add
		parent.unblockUI();  // For unblocking processing image on load of this page content
});
function ValidateEmpId(EmpId){
			if(/^[a-zA-Z0-9_.]+$/.test(EmpId)){
				$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
					if (Emp.status && Emp.status=="Error") {	
						parent.jbarOnFailure(Emp.message);
					}
					else {
					if(Emp.NAME){
						document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("valid_text");
						$("#empResultMessage").show();						
						$("#IsValidEmpId").val("true");
					}
					else{
						document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id and click on 'Validate' to verify";
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("invalid_text");
						$("#empResultMessage").show();						
						$("#IsValidEmpId").val("false");
						}
					}
				});
			}else{
				document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id and click on 'Validate' to verify";
				$("#empResultMessage").removeClass("valid_text");
				$("#empResultMessage").addClass("invalid_text");
				$("#empResultMessage").show();				
				$("#IsValidEmpId").val("false");
			}			
}
function getstatus(){
		var status = true;		
		if($('#Employee_ID').val()==""){	
			var id='Employee_ID';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='Employee_ID';
			$('#TextFieldSpan'+id+'').text('');
		}				
		if($('#IsValidEmpId').val()=="false"){	
				var id='empResultMessage';
				$('#TextFieldSpan'+id+'').text('');
				$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Please enter valid Employee id and click on Validate to verify!!'+''+'</span>');	
				status = false;				
		}else{
			var id='empResultMessage';
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
		if(($('#ADD_GROUP_ID').val()=="")||($('#ADD_GROUP_ID').val()=="--Please Select--")||($('#ADD_GROUP_ID').val()==0)){	
			var id='ADD_GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
				status = false;				
		}else{
			var id='ADD_GROUP_ID';
			$('#TextFieldSpan'+id+'').text('');
		}	
		if(($('#ADD_ROLE').val()=="")||($('#ADD_ROLE').val()=="--Please Select--")||($('#ADD_ROLE').val()==0)){
			var id='ADD_ROLE';
			$('#TextFieldSpan'+id+'').text('');
			$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
			status=false;
		}else{
			var id='ADD_ROLE';
			$('#TextFieldSpan'+id+'').text('');
		}
		return status;
}