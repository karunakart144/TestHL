var save_count_editGrpMem=0;
var saveRole_editGrpMem = [];

var groupRoleStatusCount=0;
var groupRoleStatusArr = [];

var groupRoleIdCount=0;
var groupRoleIdArr = [];

$(document).ready(function() {	
	
	$.ajaxSetup({ cache: false });
	
	$("#empResultMessage").hide();
	$("#show").focus();
	parent.unblockUI(); 	
});
var readd="";
function viewGroupMembersForGroup() { 
	
	var groupMemOption=$('input[name=groupMember]:radio:checked').val();
	closeNewMember();
	
	$.getJSON('getGroupMemberDetails.htm',{groupId:groupId,groupMemOption:groupMemOption},function(data) {
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data);
		}
		else {
			var htmldata='';
			if(groupStatus=='Active'){
			
				if(groupMemOption == "Active"){
					htmldata='<tr><th align="left" id="updateGroupRoleTR" style="display:none;"><input type="button" id="updateGroupRole" value="Update" onClick="validateBeforeUpdateGroupRole()" /></th><th colspan="4" align="center">Edit Member From Group</th></tr><tr id="header">'+
					'<th width="5">Edit</th><th>Member</th><th>Role</th><th>Member Status</th></tr>';
					
				}else{
					var htmldata='<tr><th colspan="4" align="center">Add Member To Group</th></tr><tr id="header">'+
					'<th width="5">Add</th><th>Member</th><th>Role</th><th>Member Status</th></tr>';
					
				}
				
				var j=0;
				for(var i=0;i<data.length;i++){
					
					if(data[i].member_id != groupManager){
					
					if(groupMemOption == "Active"){
						var k=parseInt(j)+1;
						htmldata+='<tr id="'+k+'"><td id="groupRoleId'+k+'" style="display:none">'+data[i].ROLE_ID+'</td><td id="memIdForRole'+k+'" style="display:none">'+data[i].member_id+'</td><td><input type="image" src="images/editnode.jpg" id="'+k+'" name="'+data[i].member_id+'" onclick="removeMem(this);" /></td><td>'+data[i].member_name_id+'</td><td id="groupRoleDynamic'+k+'">'+data[i].ROLE_NAME+'</td><td id="groupRoleStatusDynamic'+k+'">'+data[i].Group_Member_Status+'</td><td id="groupRoleValidation'+k+'" style="display:none;border:1px solid #4598b5;"></td></tr>';
						j++;
						
					}else{
						htmldata+='<tr><td><input type="image" src="images/addnode.jpg" id="'+data[i].member_id+'" name="'+data[i].member_id+'" onclick="reAddMember(this)" /></td><td>'+data[i].member_name_id+'</td><td>'+data[i].ROLE_NAME+'</td><td>'+data[i].Group_Member_Status+'</td></tr>';
					}
					
					}
				}				
				$("#activeGroupDetailsList").html(htmldata);
			}else if(groupStatus=='Inactive'){
			
				if(groupMemOption == "Active"){
					htmldata='<tr><th colspan="3" align="center">Active Members of Group</th></tr><tr id="header">'+
					'<th>Member</th><th>Role</th><th>Member Status</th></tr>';
					
				}else{
					var htmldata='<tr><th colspan="3" align="center">Inactive Members of Group</th></tr><tr id="header">'+
					'<th>Member</th><th>Role</th><th>Member Status</th></tr>';
					
				}
				
				
				for(var i=0;i<data.length;i++){
					
					if(data[i].member_id != groupManager){
					
					if(groupMemOption == "Active"){
						htmldata+='<tr><td>'+data[i].member_name_id+'</td><td>'+data[i].ROLE_NAME+'</td><td>'+data[i].Group_Member_Status+'</td></tr>';
					}else{
						htmldata+='<tr><td>'+data[i].member_name_id+'</td><td>'+data[i].ROLE_NAME+'</td><td>'+data[i].Group_Member_Status+'</td></tr>';
					}
					
					}
				}
				$("#inactiveGroupDetailsList").html(htmldata);
			}
		}
	});
}

var status=1;
var splChars = /^[a-zA-Z0-9\s&() :\/\._\-]*$/;

function checkEvent(){
	if(event.keyCode == 13){
		 getGroupDetails();
	}
}
function checkEventForSave(){
	if(event.keyCode == 13){
		saveNewMember();
	}
}

function ValidateEmpId() {
	$("#show").focus();
	var EmpId=$('#Group_Member').val();
	
	if(EmpId != ""){
		
		$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){

			if(Emp.NAME){
				document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
				$("#empResultMessage").removeClass("invalid_text");
				$("#empResultMessage").addClass("valid_text");
				$("#empResultMessage").show(); 
			}
			else{
				$.getJSON('validateEmployee_ex.htm',{employeeId:EmpId},function(Emp){

					if(Emp.NAME){
						document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("valid_text");
						$("#empResultMessage").show(); 
					}
					else{
						document.getElementById("empResultMessage").innerHTML ="Please enter valid EmployeeId";
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("invalid_text");
						$("#empResultMessage").show();
					}
				});
			}
		});
	}else{
		$("#empResultMessage").removeClass("invalid_text");
		$("#empResultMessage").hide();
	}
}

function getGroupDetails() {
	var functionId = $('#functionToChange').find('option:selected').val();
	var functionName = $('#functionToChange').find('option:selected').text();
	var memberId = $('#Group_Member').val();
	var status = $('input[name=status]:radio:checked').val();
	var statusName = "";
	$("#show").focus();
	
	if(status==1){
		statusName="Active";
	}else{
		statusName="Inactive"
	}
	saveGroups.splice(0, saveGroups.length);
	
	if(functionId == 0){
		$('#TextFieldSpan_functionToChange').text('');
		$('#functionToChange').after('<span class="error" id="TextFieldSpan_functionToChange" >Required!!</span>');
	}else{
		$('#TextFieldSpan_functionToChange').text('');
		
		$.getJSON('getGroupListForFunction.htm',{functionId:functionId,memberId:memberId,status:status},function(data){
			
			if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data);
			}
			else{
				var htmldata='';
				for(var i=0; i<data.length; i++){
					
					htmldata+='<tr id="row'+data[i].GROUP_ID+'"><td><input type="image" src="images/editnode.jpg" name="showInfo" id="edit_'+data[i].GROUP_ID+'" title="Edit" onClick="editGroup(this)" /><input type="image" src="images/Change_CS.png" name="showInfo" id="cancel_'+data[i].GROUP_ID+'" title="Cancel" onClick="cancelGroup(this)" style="display: none"/></td><input type="hidden" id="groupFunctionValue'+data[i].GROUP_ID+'" value="'+functionId+'"/><td id="groupName'+
					data[i].GROUP_ID+'" value="'+data[i].GROUP_NAME+'">'+data[i].GROUP_NAME+'</td><td id="groupManager'+data[i].GROUP_ID+'" value="'+data[i].MANAGER_NAME_ID+'">'+data[i].MANAGER_NAME_ID+'</td><input type="hidden" id="groupManagerValue'+data[i].GROUP_ID+'" value="'+data[i].MANAGER+'"/><td id="groupLocation'+data[i].GROUP_ID+'" value="'+data[i].LOCATION_ID+'">'+data[i].LOCATION_NAME+'</td><td id="autoAssignment'+data[i].GROUP_ID+'" value="'+data[i].AUTO_ASSIGNMENT_REQUIRED+'">'+data[i].AUTO_ASSIGNMENT_REQUIRED+'</td><td id="groupStatus'+data[i].GROUP_ID+
					'" value="'+data[i].STATUS+'">'+data[i].STATUS+'</td><td id="editDetails'+data[i].GROUP_ID+'"><a href="ADMIN_EditGroupDetails.htm?id='+data[i].GROUP_ID+'&groupName='+data[i].GROUP_NAME+'&groupManager='+data[i].MANAGER+'&groupManagerName='+data[i].MANAGER_NAME_ID+'&location='+data[i].LOCATION_NAME+'&status='+data[i].STATUS+'&function='+functionId+'"><img src="images/addmember.jpg" title="Modify Group Members"/></a></td></tr>';
					
				}
		
				var headerdata='<tr id="captionTR"><th colspan="8">'+functionName+' - '+statusName+' Groups</th></tr><tr id="updateGroupTH" style="display: none"><th><input type="button" id="updateGroup" value="Update" onClick="validateBeforeUpdate()" /></th><th colspan="7">'+functionName+' - '+statusName+'</th></tr><tr><th>Edit Group Details</th><th>Group Name</th><th>Group Manager</th><th>Base Location</th><th>Auto Assignment Required</th><th>Status</th><th>Edit Member Details</th></tr>';
				htmldata=headerdata+htmldata;
				$("#groupDetails").html(htmldata);
				
			}
			
		});
	}
}

function validateManager(obj){
	var EmpId = $(obj).val();
	var id = obj.id.slice(16,obj.id.length);
	
	var oldGroupManager=$('#groupManagerValue'+id).val();
	
	if(oldGroupManager != EmpId){
		if(EmpId != ""){
			
			$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
				
				if(Emp.NAME){
					$('#tdError'+id).remove();
					$('#updateGroup').attr('disabled',false);
				}
				else{
					status=0;
					$('#tdError'+id).remove();
					$('#editDetails'+id).after('<td id="tdError'+id+'"><span class="error" id="TextFieldSpan'+id+'" >Please enter valid EmployeeId</span></td>');
				}
			});

		}else{
			status=0;
			$('#tdError'+id).remove();
			$('#editDetails'+id).after('<td id="tdError'+id+'"><span class="error" id="TextFieldSpan'+id+'" >Please enter valid EmployeeId</span></td>');
		}

	}else{
		$('#tdError'+id).remove();
		$('#updateGroup').attr('disabled',false);
	}
}

function getUpdateMode(){
	
	if(updateStatus == true && $('#statusForUpdation').val() == "Success"){
	
		$('#updateGroupTH').attr('disabled',true);
	
		var jsonobj = "[";
		var old_jsonobj="[";
	
		for (var i=0; i<saveGroups.length; i++){
		
			var groupId=saveGroups[i];
			jsonobj+='["'+groupId+'",';
			old_jsonobj+='["'+groupId+'",';
			
			jsonobj+='"'+$('#groupNameText'+groupId).val()+'",';
			jsonobj+='"'+$('#groupManagerText'+groupId).val()+'",';
			//jsonobj+='"'+$('#managerRoleSelect'+groupId).val()+'",';
			//jsonobj+='"'+$('#groupLocationSelect'+groupId).find('option:selected').val()+'",';
			jsonobj+='"'+$('#autoAssignmentSelect'+groupId).find('option:selected').val()+'",';
			jsonobj+='"'+$('#groupStatusSelect'+groupId).find('option:selected').val()+'"],';
			
			old_jsonobj+='"'+$('#groupName'+groupId).attr('value')+'",';
			old_jsonobj+='"'+$('#groupManagerValue'+groupId).attr('value')+'",';
			//old_jsonobj+='"'+$('#managerRole'+groupId).val()+'",';	
			
			if($('#autoAssignment'+groupId).attr('value')=="Yes"){
				old_jsonobj+='"1",';
			}else{
				old_jsonobj+='"0",';
			}
			if($('#groupStatus'+groupId).attr('value')=="Active"){
				old_jsonobj+='"1"],';
			}else{
				old_jsonobj+='"0"],';
			}
		}
	
		old_jsonobj+=']';
		jsonobj+=']';
		
		$.getJSON('updateGroupList.htm', {json:jsonobj,oldjson:old_jsonobj}, function(data) {
			if(data=="Success"){
				$("#updateGroupTH").hide();
				$("#captionTR").show();
				parent.jbarOnSuccess('Successfully updated');
			}else if(data=="NoChange"){
				$("#updateGroupTH").hide();
				$("#captionTR").show();
				parent.jbarOnFailure('No Changes Made');
			}else if(data=="ExistingTickets"){
				$("#updateGroupTH").hide();
				$("#captionTR").show();
				parent.jbarOnFailure('Please close the assigned tickets');
			}else{
				parent.jbarOnFailure('ERROR in updation');
			}
			getGroupDetails();	
			
		});
		
		while (saveGroups.length > 0) {
			saveGroups.pop();
		}
		save_count=0;
		
		$('#statusForUpdation').val("Success");
		updateStatus=true;
		
	}else{
		$('#statusForUpdation').val("Success");
		updateStatus=true;
		return false;
	}
}

function addNewMember() {
	
	$("#addNewMemberTR").show();
	$("#save").attr('disabled',false);
	$("#save").show();
	$("#save").focus();
	$("#Engineer1").val('');
	$("#Engineer1").attr('disabled',false);
	$("#AddMember").hide();
		
	$.getJSON('getRole.htm',{functionId : functionId},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {					
		var roleListOptions = [];
		var managerRoleOptions = [];
		roleListOptions.push('<option value="0">--Please Select--</option>');
		for(var i=0;i<data.length;i++){
			roleListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
		}	
		$('#role1').html(roleListOptions.join(''));
		}
	});
	
	$.getJSON('getAccessLevel.htm',{},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {
			var accessListOptions = [];
			accessListOptions.push('<option value="0">--Please Select--</option>');
			for(var i=0;i<data.length;i++){
				accessListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			}
			$('#roleAccess1').html(accessListOptions.join(''));
		}
	});
	
	
	
	
}

function saveNewMember(){
	
	var roleID=$("#role1").val();
	var accessLevel=$("#roleAccess1").val();
	var assignment=0;
	if($('#assignment1').is(':checked')){		
		assignment=1
	}else{
		assignment=0;
	}
	$('#save').attr('disabled',true);
	$("#save").focus();
	var bgColor=$('#Engineer1').css("background-color");
	if(getStatus()==true && (bgColor!="red")){
		setTimeout(function() {		
			$.getJSON('addGroupMember.htm',{groupID : groupId,empID:$('#Engineer1').val(),roleID:roleID,accessLevel:accessLevel,assignment:assignment,readd:readd},function(data) {
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {
				if(data==1){
					
					parent.jbarOnSuccess('Employee '+$("#Engineer1").val()+' added in '+$("#groupName").html()+' Successfully');
					//reset cache & reload the page
					
					window.location.reload(true); 
					readd="";
					$("#Engineer1").attr('disabled',false);
				}
				else{
					parent.jbarOnFailure('Employee '+$("#Engineer1").val()+' already exists in '+$("#groupName").html());
				}
				closeNewMember();
				}
				
				$("#groupMember").attr('disabled',false);
				$("#viewGroupMember").show();
			
			});
		}, 1000);
	}else{
		$('#save').attr('disabled',false);
		$("#save").focus();
	}
	
}

function checkEngineer(){
	$("#save").focus();
	if(groupManager == $('#Engineer1').val() && $('#Engineer1').val() != ''){
		
		if($('#TextFieldSpan')){
			$('#tdError').remove();
			$('#Engineer1').css('background-color', 'red');
			$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Manager Id should not be added as engineer</span></td>');
		}
		status = false;	
	}else{
		var tdEnable=true;
		$.getJSON('validateEmployee.htm',{employeeId:$('#Engineer1').val()},function(Emp){

			if(Emp.NAME){
			
				tdEnable=true;
			}
			else{
				$.getJSON('validateEmployee_ex.htm',{employeeId:$('#Engineer1').val()},function(Emp){

					if(Emp.NAME){
						$('#tdError').remove();
						$('#Engineer1').css('background-color', 'red');
						$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Please add an existing Employee</span></td>');
					}
					else{
						$('#tdError').remove();
						$('#Engineer1').css('background-color', 'red');
						$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Please enter valid EmployeeId</span></td>');
					}
				});
				
				status=false;
				tdEnable=false;
			}
			
		});
		if(tdEnable){
			
			$('#Engineer1').css('background-color', 'white');
			$('#tdError').remove();
		}
	}
}

function getStatus(){
	
	var status=true;
	
	if($('#Engineer1').val()==""){
		status=false;
		if($('#TextFieldSpan')){
			$('#tdError').remove();
			$('#Engineer1').css('background-color', 'red');
			$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Please enter valid EmployeeId</span></td>');
		}
	}else{
		checkEngineer();
	}if($('#role1').val()==0){
		status=false;
		if($('#TextFieldSpan')){
			$('#tdError').remove();
			$('#role1').find('option:selected').css('background-color', 'red');
			$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Select value for highlighted fields</span></td>');
		}
	}
	if($('#roleAccess1').val()==0){
		status=false;
		if($('#TextFieldSpan')){
			$('#tdError').remove();
			$('#roleAccess1').find('option:selected').css('background-color', 'red');
			$('#assignmentTD').after('<td id="tdError"><span class="error" id="TextFieldSpan" >Select value for highlighted fields</span></td>');
		}
	}
	
	return status;
}

function reAddMember(obj){
	
	$("#addNewMemberTR").show();
	$("#save").show();
	$("#save").focus();
	$("#AddMember").hide();
	
	$('#Engineer1').attr('value',obj.name);
	$('#Engineer1').attr('disabled',true);
	readd="yes";
	
	$.getJSON('getRole.htm',{functionId : functionId},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {					
		var roleListOptions = [];
		//var managerRoleOptions = [];
		roleListOptions.push('<option value="0">--Please Select--</option>');
		for(var i=0;i<data.length;i++){
			roleListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
		}	
		$('#role1').html(roleListOptions.join(''));
		}
	});
	
	$.getJSON('getAccessLevel.htm',{},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {
			var accessListOptions = [];
			accessListOptions.push('<option value="0">--Please Select--</option>');
			for(var i=0;i<data.length;i++){
				accessListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			}
			$('#roleAccess1').html(accessListOptions.join(''));
		}
	});
	
	checkEngineer();
}

var save_count=0;
var saveGroups = [];

function editGroup(obj){
	$(obj).hide();
	
	var id=obj.id.slice(5,obj.id.length);
	
	$('#cancel_'+id).show();
	saveGroups.push(id);
	save_count++;
	
	if(save_count > 0){
		$("#updateGroupTH").show();
		$("#updateGroup").attr('disabled',false);
		$("#captionTR").hide();
	}
	
	var row="#row"+id;
	var functionId=$('#groupFunctionValue'+id).val();

	$(row).css({"background-color":"#F5EDDE"});
				 			
	$('#groupName'+id).html('<input class="myTextEdit" id="groupNameText'+id+'" type="text"  maxlength="100" value="' + $('#groupName'+id).text() + '" onblur="checkGroupNameAvailability(this)"/>');
	$('#groupManager'+id).html('<input class="myTextEdit" id="groupManagerText'+id+'" type="text" maxlength="10" value="' + $('#groupManagerValue'+id).val() + '" onblur="validateManager(this)"/>');

	//$('#managerRole'+id).html('<select class="myTextEdit" id="managerRoleSelect'+id+'"></select>');
	
 		$.getJSON('getRole.htm',{functionId : functionId},function(data) {	
 		if (data.status && data.status=="Error") {	
 			parent.jbarOnFailure(data.message);
 		}
 		else {					
	 		var roleListOptions = [];
	 		/*for(var i=0;i<data.length;i++){
	 			if(data[i].substring(0,data[i].indexOf(",", 0)).indexOf("Manager")!=-1 && functionId != '1020'){	
	 				$('#managerRoleSelect'+id).append($("<option></option>").attr("value",data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)).text(data[i].substring(0,data[i].indexOf(",", 0))));
	 				if(data[i].substring(data[i].indexOf(",", 0)+1,data[i].length) == $('#managerRole'+id).val())
	 					$('#managerRoleSelect'+id).find("option[value='" + data[i].substring(data[i].indexOf(",", 0)+1,data[i].length) +"']").attr('selected', true);
	 			}else if(data[i].substring(0,data[i].indexOf(",", 0)).indexOf("Group Quality Manager")!=-1 && functionId == '1020'){
	 				
	 				$('#managerRoleSelect'+id).append($("<option></option>").attr("value",data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)).text(data[i].substring(0,data[i].indexOf(",", 0))));
	 				if(data[i].substring(data[i].indexOf(",", 0)+1,data[i].length) == $('#managerRole'+id).val())
	 					$('#managerRoleSelect'+id).find("option[value='" + data[i].substring(data[i].indexOf(",", 0)+1,data[i].length) +"']").attr('selected', true);
	 			}
	 		}*/				
	 		$('#role1').html(roleListOptions.join(''));
 		}
 		});
	
	if($('#autoAssignment'+id).text()=="Yes"){
		$('#autoAssignment'+id).html('<select class="myTextEdit" id="autoAssignmentSelect'+id+'"><option value="1" selected="true">Yes</option><option value="0">No</option></select>');
	}else{
		$('#autoAssignment'+id).html('<select class="myTextEdit" id="autoAssignmentSelect'+id+'"><option value="1">Yes</option><option value="0" selected="true">No</option></select>');
	}
	if($('#groupStatus'+id).text()=="Active"){
		$('#groupStatus'+id).html('<select class="myTextEdit" id="groupStatusSelect'+id+'"><option value="1" selected="true">Active</option><option value="0">Inactive</option></select>');
	}else{
		$('#groupStatus'+id).html('<select class="myTextEdit" id="groupStatusSelect'+id+'"><option value="1">Active</option><option value="0" selected="true">Inactive</option></select>');
	}	
}

function cancelGroup(obj){
	$(obj).hide();
	
	var removeId=obj.id.slice(7,obj.id.length);
	
	saveGroups.splice($.inArray(removeId, saveGroups),1);   
	
	$('#edit_'+removeId).show();
	$('#row'+removeId).css({"background-color":"#FFFFFF"});
	
	save_count--;
	
	if(save_count <= 0){
		$('#updateGroupTH').hide();
		$("#captionTR").show();
	}
	
	$('#tdError'+removeId).remove();
	
	$('#groupName'+removeId).html($('#groupName'+removeId).attr('value'));
	$('#groupManager'+removeId).html($('#groupManager'+removeId).attr('value'));
	//$('#managerRole'+removeId).html($('#managerRoleValue'+removeId).val());
	$('#autoAssignment'+removeId).html($('#autoAssignment'+removeId).attr('value'));
	$('#groupStatus'+removeId).html($('#groupStatus'+removeId).attr('value'));
	
	
	$('#groupNameText'+removeId).remove();
	$('#groupManagerText'+removeId).remove();
	//$('#managerRoleSelect'+removeId).remove();
	$('#autoAssignmentSelect'+removeId).remove();
	$('#groupStatusSelect'+removeId).remove();
}

function closeNewMember(){
	
	$("#addNewMemberTR").hide();
	$("#save").hide();
	$("#AddMember").show();
	
	$('#tdError').remove();
	$('#Engineer1').css('background-color', 'white');
	readd="";
}

function checkGroupNameAvailability(obj){
	
	var groupName=$(obj).val();
	var groupId=obj.id.slice(13,obj.id.length);
	var selectedCategory=$('#groupFunctionValue'+groupId).val();

	if($.trim(groupName) != ""){
	
		if($.trim(groupName) != $.trim($('#groupName'+groupId).attr('value'))){			
				$.getJSON('getGroupNameDetailsList.htm',{functionId : selectedCategory},function(data) {
					var matchFound=false;
					if (data.status && data.status=="Error") {	
						parent.jbarOnFailure(data.message);
					}
					else {					
						for(var i=0;i<data.length;i++){
							if($.trim(groupName).toUpperCase() == $.trim(data[i].substring(0,data[i].indexOf(",", 0))).toUpperCase()){
								matchFound=true;
							}
						}
						
						if(matchFound){
							$('#tdError'+groupId).remove();
							$('#editDetails'+groupId).after('<td id="tdError'+groupId+'"><span class="error" id="TextFieldSpan'+groupId+'" >Group Name Exists</span></td>');
						}else{				
							$('#tdError'+groupId).remove();	
							$('#updateGroup').attr('disabled',false);
						}
						
					}
				});	
			
			
		}else if (!splChars.test($.trim(groupName))) {
				$('#tdError'+currentGroupId).remove();
				$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Group Name is with Special Characters</span></td>');
				updateStatus = false;
		}else{
			$('#tdError'+groupId).remove();
			$('#updateGroup').attr('disabled',false);
		}
	}else{
		$('#tdError'+groupId).remove();
		$('#editDetails'+groupId).after('<td id="tdError'+groupId+'"><span class="error" id="TextFieldSpan'+groupId+'" >Please enter valid group name</span></td>');
	}
	
}

function gotoListPage(){
	
	document.location.href = 'ADMIN_EditGroup.htm?';
	
}

var updateStatus=true;
function validateBeforeUpdate(){

	for(var i=0; i<saveGroups.length; i++){
			iterationOfForLoop(saveGroups[i]);
	}
	
	
	setTimeout(function() {
		if(updateStatus == true && $('#statusForUpdation').val() == "Success"){
			getUpdateMode();
		}else{
			$('#statusForUpdation').val("Success");
			updateStatus=true;
			return false;
		}
	}, 1000);
}

function iterationOfForLoop(currentGroupId){

	
	var currentGroupName=$('#groupNameText'+currentGroupId).val();
	var selectedCategory=$('#groupFunctionValue'+currentGroupId).val();
	
	
	var newManager = $('#groupManagerText'+currentGroupId).val();
	var oldManager=$('#groupManagerValue'+currentGroupId).val();
	
	//for group name validation
	if($.trim(currentGroupName) != $.trim($('#groupName'+currentGroupId).attr('value'))){
	
		if($.trim(currentGroupName) != ""){
			setTimeout(function() {
			$.getJSON('getGroupNameDetailsList.htm',{functionId : selectedCategory},function(data) {
				var matchFound=false;
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {					
					for(var i=0;i<data.length;i++){
						if($.trim(currentGroupName).toUpperCase() == $.trim(data[i].substring(0,data[i].indexOf(",", 0))).toUpperCase()){
							matchFound=true;
						}
					}
					
					if(matchFound){
						$('#tdError'+currentGroupId).remove();
						$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Group Name Exists</span></td>');
						
						$('#statusForUpdation').val("Fail");
						$('#updateGroup').attr('disabled',true);
						updateStatus=false;
						
					}else{				
						$('#tdError'+currentGroupId).remove();
						if (!splChars.test($.trim(currentGroupName))) {
							$('#tdError'+currentGroupId).remove();
							$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Group Name is with Special Characters</span></td>');
							$('#updateGroup').attr('disabled',true);
	                         updateStatus = false;
	                     }else{
						//for group manager validation
						
						if(newManager != ""){
							if($('#groupStatusSelect'+currentGroupId).find('option:selected').val()=='1'){
									$.getJSON('validateEmployee.htm',{employeeId:newManager},function(Emp){
										if(Emp.NAME){
											$('#tdError'+currentGroupId).remove();
										}else{
											$('#tdError'+currentGroupId).remove();
											$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Please enter valid EmployeeId</span></td>');
											
											$('#statusForUpdation').val("Fail");
											$('#updateGroup').attr('disabled',true);
											updateStatus=false;
											
										}
									});
							}
						}else{
							$('#tdError'+currentGroupId).remove();
							$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Please enter valid EmployeeId</span></td>');
							
							$('#statusForUpdation').val("Fail");
							$('#updateGroup').attr('disabled',true);
							updateStatus=false;
							
						}
					}
					}
					
				}
			});	
		}, 100);
		}else{
			$('#tdError'+currentGroupId).remove();
			$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Please enter valid group name</span></td>');
			
			$('#statusForUpdation').val("Fail");
			$('#updateGroup').attr('disabled',true);
			updateStatus=false;
			
		}
	}else{
		$('#tdError'+currentGroupId).remove();
		
		//for group manager validation
		
		if(newManager != ""){
			if($('#groupStatusSelect'+currentGroupId).find('option:selected').val()=='1'){
					$.getJSON('validateEmployee.htm',{employeeId:newManager},function(Emp){
						if(Emp.NAME){
							$('#tdError'+currentGroupId).remove();
						}else{
							$('#tdError'+currentGroupId).remove();
							$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Please enter valid EmployeeId</span></td>');
							
							$('#statusForUpdation').val("Fail");
							$('#updateGroup').attr('disabled',true);
							updateStatus=false;
							
						}
					});
			}
		}else{
			$('#tdError'+currentGroupId).remove();
			$('#editDetails'+currentGroupId).after('<td id="tdError'+currentGroupId+'"><span class="error" id="TextFieldSpan'+currentGroupId+'" >Please enter valid EmployeeId</span></td>');
			
			$('#statusForUpdation').val("Fail");
			$('#updateGroup').attr('disabled',true);
			updateStatus=false;
			
		}
	}
}

function removeMem(obj){
	$("#updateGroupRole").show();
	$("#updateGroupRoleTR").show();
	var i=parseInt(obj.id);	
	var rowId=parseInt(obj.id)+1;	
	$('#activeGroupDetailsList').find('tr:eq('+rowId+')').each(function(){ 										
			saveRole_editGrpMem.push(i);
			save_count_editGrpMem++;
			$(this).find('td').each (function() {				
				if($(this).attr("id").indexOf('groupRoleDynamic')>= 0){					
					$.getJSON('getAccessLevel.htm',{},function(data) {	
						if (data.status && data.status=="Error") {	
							parent.jbarOnFailure(data.message);
						}
						else {
							var accessListOptions = [];
							accessListOptions.push('<option value="0">--Please Select--</option>');
							for(var j=0;j<data.length;j++){
								accessListOptions.push('<option value="'+data[j].substring(data[j].indexOf(",", 0)+1,data[j].length)+ '">'+ data[j].substring(0,data[j].indexOf(",", 0))+ '</option>');
							}	
							groupRoleIdArr.push($('#groupRoleId'+i).text());
							groupRoleIdCount++;
							$('#groupRoleDynamic'+i).html('<select class="myTextEdit" id="grouproleDetSelect'+i+'">'+accessListOptions.join('')+'</select>');
						}
					});
				}
				if($(this).attr("id").indexOf('groupRoleStatusDynamic')>= 0){
					groupRoleStatusArr.push($('#groupRoleStatusDynamic'+i).text());
					groupRoleStatusCount++;
					if($('#groupRoleStatusDynamic'+i).text()=="Active"){
						$('#groupRoleStatusDynamic'+i).html('<select class="myTextEdit" id="grouproleStatusSelect'+i+'"><option value="1" selected="true">Active</option><option value="0">Inactive</option></select>');
					}else{
						$('#groupRoleStatusDynamic'+i).html('<select class="myTextEdit" id="grouproleStatusSelect'+i+'"><option value="1">Active</option><option value="0" selected="true">Inactive</option></select>');
					}	
				}
				if($(this).attr("id").indexOf('groupRole')>= 0){						
					$.getJSON('getAccessLevel.htm',{},function(data) {	
						if (data.status && data.status=="Error") {	
							parent.jbarOnFailure(data.message);
						}
						else {
							var accessListOptions = [];
							accessListOptions.push('<option value="0">--Please Select--</option>');
							for(var j=0;j<data.length;j++){
								accessListOptions.push('<option value="'+data[j].substring(data[j].indexOf(",", 0)+1,data[j].length)+ '">'+ data[j].substring(0,data[j].indexOf(",", 0))+ '</option>');
							}	
							groupRoleIdArr.push($('#groupRoleId'+i).text());
							groupRoleIdCount++;
							$('#groupRole'+i).html('<select class="myTextEdit" id="grouproleDetSelect'+i+'">'+accessListOptions.join('')+'</select>');
						}
					});
				}
				if($(this).attr("id").indexOf('groupRoleStatus')>= 0){	
					groupRoleStatusArr.push($('#groupRoleStatus'+i).text());
					groupRoleStatusCount++;
					if($('#groupRoleStatus'+i).text()=="Active"){
						$('#groupRoleStatus'+i).html('<select class="myTextEdit" id="grouproleStatusSelect'+i+'"><option value="1" selected="true">Active</option><option value="0">Inactive</option></select>');
					}else{
						$('#groupRoleStatus'+i).html('<select class="myTextEdit" id="grouproleStatusSelect'+i+'"><option value="1">Active</option><option value="0" selected="true">Inactive</option></select>');
					}	
				}				
			});		
  });	
}
function validateBeforeUpdateGroupRole(){ 
	var jsonobj = "[[";		
	for (var i=0; i<saveRole_editGrpMem.length; i++){
		  $('#groupRoleValidation'+saveRole_editGrpMem[i]).hide();
		  $('#groupRole'+saveRole_editGrpMem[i]).css('background-color', 'white');
		  $('#groupRoleDynamic'+saveRole_editGrpMem[i]).css('background-color', 'white');
		  if($('#grouproleDetSelect'+saveRole_editGrpMem[i]).val()==0){
			 $('#groupRoleDynamic'+saveRole_editGrpMem[i]).css('background-color', 'red');
			 $('#groupRole'+saveRole_editGrpMem[i]).css('background-color', 'red');
			 $('#groupRoleValidation'+saveRole_editGrpMem[i]).show();
			 $('#groupRoleValidation'+saveRole_editGrpMem[i]).html('<span class="error" id="TextFieldSpan'+i+'" >Mandatory</span>');
				return false;
		  }
		jsonobj+='"'+$('#grouproleDetSelect'+saveRole_editGrpMem[i]).val()+'",';			
				
		jsonobj+='"'+$('#grouproleStatusSelect'+saveRole_editGrpMem[i]).val()+'",';	

		jsonobj+='"'+$('#memIdForRole'+saveRole_editGrpMem[i]).text()+'",';	

		jsonobj+='"'+groupId+'",';	

		jsonobj+='"'+groupRoleIdArr[i]+'",';	

		jsonobj+='"'+groupRoleStatusArr[i]+'"],[';	
	}

	jsonobj = jsonobj.substring(0,(jsonobj.length)-2);			
	jsonobj+=']';
	$.getJSON('changeGroupMemberRole.htm', {json:jsonobj}, function(data) {
			if(data=="Success"){
				$("#updateGroupRole").hide();
				$("#updateGroupRoleTR").hide();
				parent.jbarOnSuccess('Successfully updated');
			}else if(data=="NoChange"){				
				parent.jbarOnFailure('No Changes Made');
			}else{
				parent.jbarOnFailure('ERROR in updation');
			}		
			viewGroupMembersForGroup();
	});
		
	while (saveRole_editGrpMem.length > 0) {
		saveRole_editGrpMem.pop();
		groupRoleIdArr.pop();
		groupRoleStatusArr.pop();
	}
	save_count_editGrpMem=0;
	groupRoleStatusCount=0;
	groupRoleIdCount=0;

	$("#updateGroupRole").hide();
	$("#updateGroupRoleTR").hide();
}