
var roleOldStatusCount=0;
var roleOldStatusArr = [];
var updateStatus=true;
$(document).ready(function() {		

	$.ajaxSetup({ cache: false });

	$("#empResultMessage").hide();
	$("#showRole").focus();
	parent.unblockUI(); 

	$("#addRole").hide();	

/*	$("#roleId").live('change', function() {				
				if(($("#roleId").val()!="--Please Select--") && ($("#roleId").val()=="10")){					
					$("#LocAddition").show();
					var employeeId = $('#Role_Member').val();			
					$.getJSON('MemRoleForLocAddition.htm',{employeeId:employeeId},function(data){
						if (data.status && data.status=="Error") {	
							parent.jbarOnFailure(data);
						}else{							
							var htmldata=''; 
							htmldata+='<select class="myTextInput" style="height:70px;" id="roleIdLoc" name="roleIdLoc" multiple="multiple">'+
								'<option selected="selected" value="--Please Select--" label="--Please Select--">'+
								'</option>';					
							for(var i=0; i<data.length; i++){ 
								htmldata+='<option value="'+data[i].LOCATION_ID+'">'+data[i].CITY+'</option>';						  											
							}
							htmldata+='</select>';																			
							$("#LocAddRole").html(htmldata);							
						}						
					});
			
				}else{
					$("#LocAddition").hide();
					$("#roleIdLoc").hide();
				}
	});*/

});

var save_count=0;
var saveRole = [];

function checkEventEnterKey(){
	if(event.keyCode == 13){
		getRoleMemDetails();
	}
}

//for validate employee
function ValidateEmpId() {
	$("#showRole").focus();
	var EmpId=$('#Role_Member').val();
	
	if(EmpId != ""){
		
		$.getJSON('validateEmployeeForAdminConsole.htm',{employeeId:EmpId},function(Emp){

			if(Emp.NAME){
				document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
				$("#empResultMessage").removeClass("invalid_text");
				$("#empResultMessage").addClass("valid_text");
				$("#empResultMessage").show(); 
				$('#Role_Member').css('background-color', 'white');
				$("#showRole").show();
			}
			else{
				$.getJSON('validateEmployee_ex.htm',{employeeId:EmpId},function(Emp){

					if(Emp.NAME){
						document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("valid_text");
						$("#empResultMessage").show(); 
						$('#Role_Member').css('background-color', 'white');
						$("#showRole").show();
					}
					else{
						document.getElementById("empResultMessage").innerHTML ="Please enter valid EmployeeId";
						$("#empResultMessage").removeClass("invalid_text");
						$("#empResultMessage").addClass("invalid_text");
						$("#empResultMessage").show();
						$("#showRole").hide();
						$("#addRole").hide();
						$("#roleDetails").hide();
						$("#roleAddition").hide();
						$('#Role_Member').css('background-color', 'red');
					}
				});
			}
		});
	}else{
		document.getElementById("empResultMessage").innerHTML ="Please enter valid EmployeeId";
		$("#empResultMessage").removeClass("invalid_text");
		$("#empResultMessage").addClass("invalid_text");
		$("#empResultMessage").show();
		$("#showRole").hide();
		$("#addRole").hide();
		$("#roleDetails").hide();
		$("#roleAddition").hide();
		$('#Role_Member').css('background-color', 'red');
	}
}

//Getting the role member details 

function getRoleMemDetails() {
	var EmpId=$("#Role_Member").val();

	if(EmpId !== ""){
		
	$("#addRole").show();
	$("#roleAddition").hide();
	updateStatus=true;
	var employeeId = $('#Role_Member').val();			

		$.getJSON('MemRoleDetail.htm',{employeeId:employeeId},function(data){
			
			if (data.status && data.status=="Error") {
				parent.jbarOnFailure(data);
			}else{
				var htmldata='';
				for(var i=0; i<data.length; i++){
					
					htmldata+='<tr id="row'+i+'"><td id="editRoleHide'+i+'"><input type="image" src="images/editnode.jpg" name="showInfo" id="edit_'+i+'" title="Edit" onClick="editRole(this)" /><input type="image" src="images/Change_CS.png" name="showInfo" id="cancel_'+i+'" title="Cancel" onClick="cancelRole(this)" style="display: none"/></td>'+
						'<input type="hidden" id="loc'+i+'" value="'+data[i].LocationId+'"/>'+
						'<input type="hidden" id="role'+i+'" value="'+data[i].ROLE_ID+'"/><td id="roleName'+
					    i+'" >'+data[i].NAME+'</td><td id="LocName'+i+'" value="'+data[i].Location+'">'+data[i].Location+'</td>';
					 if(data[i].LocationStatus!="-")
						htmldata+='<td id="roleStatus'+i+'" value="'+data[i].LocationStatus+'">'+data[i].LocationStatus+'</td>';
					 else
						htmldata+='<td id="roleStatus'+i+'" value="'+data[i].Status+'">'+data[i].Status+'</td>';
					 htmldata+='</tr>';
					
				}
		
				var headerdata='<tr id="updateRoleTH" style="display: none"><th><input type="button" id="updateRole" value="Update" onClick="validateBeforeUpdateRole()" /></th><th colspan="7">Edit</th></tr><tr><th id="roleEditHeader">Edit</th><th>Role Name</th><th>Location</th><th>Role Status</th></tr>';
				htmldata=headerdata+htmldata;
				$("#roleDetails").show();
				$("#roleDetails").html(htmldata);
			}
			
		});	
	}else{
		document.getElementById("empResultMessage").innerHTML ="Please enter valid EmployeeId";
		$("#empResultMessage").removeClass("invalid_text");
		$("#empResultMessage").addClass("invalid_text");
		$("#empResultMessage").show();
		$("#showRole").hide();
		$("#addRole").hide();
		$("#roleDetails").hide();
		$('#Role_Member').css('background-color', 'red');
	}
}

function editRole(obj){
	$(obj).hide();	
	var id=obj.id.slice(5,obj.id.length);	
	$('#cancel_'+id).show();
	saveRole.push(id);
	save_count++;

	if(save_count > 0){
		$("#updateRoleTH").show();
		$("#updateRole").attr('disabled',false);
		//$("#captionTR").hide();
	}

	var row="#row"+id;
	$(row).css({"background-color":"#F5EDDE"});	

	roleOldStatusArr.push($('#roleStatus'+id).text());	
	roleOldStatusCount++;
	if($('#roleStatus'+id).text()=="Active"){
		$('#roleStatus'+id).html('<select class="myTextEdit" id="roleStatusSelect'+id+'" onchange="setFlagForUpdate();"><option value="1" selected="true">Active</option><option value="0">Inactive</option></select>');
	}else{
		$('#roleStatus'+id).html('<select class="myTextEdit" id="roleStatusSelect'+id+'" onchange="setFlagForUpdate();"><option value="1">Active</option><option value="0" selected="true">Inactive</option></select>');
	}	
	updateStatus=false;
}

function cancelRole(obj){
	
	$(obj).hide();
	
	var removeId=obj.id.slice(7,obj.id.length);
	
	saveRole.splice($.inArray(removeId, saveRole),1);   
	
	$('#edit_'+removeId).show();
	$('#row'+removeId).css({"background-color":"#FFFFFF"});
	
	save_count--;
	
	if(save_count <= 0){
		$('#updateRoleTH').hide();
		//$("#captionTR").show();
	}
	
	//$('#tdError'+removeId).remove();
	
	$('#roleStatus'+removeId).html($('#roleStatus'+removeId).attr('value'));	
	updateStatus=false;
}


function validateBeforeUpdateRole(){
			
	setTimeout(function() {		
		if(updateStatus == true && $('#statusForUpdation').val() == "Success"){
			getUpdateMode();
		}else{
			parent.jbarOnFailure('No Changes Made');
			$('#statusForUpdation').val("Success");			
			return false;
		}
	}, 1000);
}

function getUpdateMode(){
	
		$('#updateRoleTH').attr('disabled',true);
	
		var jsonobj = "[[";		
		for (var i=0; i<saveRole.length; i++){									

			jsonobj+='"'+$('#role'+saveRole[i]).val()+'",';
			
			jsonobj+='"'+$('#Role_Member').val()+'",';
			jsonobj+='"'+$('#roleStatusSelect'+saveRole[i]).val()+'",';
			jsonobj+='"'+$('#loc'+saveRole[i]).val()+'",';
			
			if(roleOldStatusArr[i]=="Active")
				jsonobj+='"1"],[';
			if(roleOldStatusArr[i]=="Inactive")
				jsonobj+='"0"],[';
		}
		
		jsonobj = jsonobj.substring(0,(jsonobj.length)-2);	
		
		jsonobj+=']';
		
		$.getJSON('updateRoleList.htm',{json:jsonobj}, function(data) {
			if(data=="Success"){
				$("#updateRoleTH").hide();
				//$("#captionTR").show();
				parent.jbarOnSuccess('Successfully updated');
				roleOldStatusArr.pop();
			}else if(data=="NoChange"){
				$("#updateRoleTH").hide();
				//$("#captionTR").show();
				parent.jbarOnFailure('No Changes Made');
			}else{
				parent.jbarOnFailure('ERROR in updation');
			}
			getRoleMemDetails();				
		});
		
		while (saveRole.length > 0) {
			saveRole.pop();
		}
		save_count=0;
		roleOldStatusCount=0;
		$('#statusForUpdation').val("Success");
		updateStatus=true;			
}
function addRoleToMem(){	
	$("#updateRoleTH").hide();
    $('#roleDetails tr').each(function(i){ 			
		$(this).find('td').each (function() {
			if($(this).attr("id").indexOf('editRoleHide')>= 0){
				$(this).hide();				
			}
		});
	});
	$("#roleEditHeader").hide();
	$("#roleAddition").show();		
	var employeeId = $('#Role_Member').val();	
	var htmldata='';

		$.getJSON('MemRoleForAddition.htm',{employeeId:employeeId},function(data){
			
			if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data);
			}else{
				var htmldata='<tr><td align="left" colspan="4">'; 
					htmldata+='<select class="myTextInput" id="roleId" name="roleId">'+
					      '<option selected="selected" value="">--Please Select--'+
					      '</option>';
					
					for(var i=0; i<data.length; i++){ 
						htmldata+='<option value="'+data[i].ROLE_ID+'">'+data[i].NAME+'</option>';						  											
					}
				htmldata+='</select></td><td id="LocAddRole" colspan="4"></td></tr>';
				var headerdata='<tr id="captionTR"><th colspan="8">Role Addition</tr><tr><th colspan="3"><input type="button" id="updateRoleAddition" value="Update" onClick="validateBeforeRoleAddition()" /></th><th colspan="5">Add Role</th></tr><tr><th colspan="4">Role Name</th><th colspan="4" style="display: none" id="LocAddition">Location</th></tr>';
				htmldata=headerdata+htmldata;
				$("#roleAddition").html(htmldata);				
			}			
	 });	
				var headerdata='<tr id="captionTR"><tr><th colspan="2"><input disabled="disabled" type="button" id="updateRoleAddition" value="Update" onClick="validateBeforeRoleAddition()" /></th><th colspan="7">Add Role</th></tr><tr><th colspan="5">Location</th></tr>';
				htmldata=headerdata+htmldata;
				$("#roleAddition").html(htmldata);		
}
function validateBeforeRoleAddition(){	

		var jsonobj = "{";		
		var locJsonobj = "{";		

		jsonobj+='"RoleId":'+'"'+jQuery.trim($("#roleId option:selected").val())+'",';

		jsonobj+='"EmpId":'+'"'+jQuery.trim($('#Role_Member').val())+'"}';

		locJsonobj+='"Location":[';

		var locStatus=false;
	/*	$('#roleIdLoc :selected').each(function(i, selected){ 	
			if(jQuery.trim($(selected).val())!="--Please Select--"){
				locJsonobj+='"'+jQuery.trim($(selected).val())+'",';	
				locStatus=true;
			}
		});*/
		if(locStatus==true)
			locJsonobj = locJsonobj.substring(0,(locJsonobj.length)-1);	
		
		locJsonobj+=']}';		
		
		/*if((locStatus==false) && (jQuery.trim($("#roleId option:selected").val()))=="10"){
			parent.jbarOnFailure('Kindly select the Location for IT Functional Head Role');
		}else{*/
			$.getJSON('insertRoleLoc.htm', {json:jsonobj,locJsonobj:locJsonobj}, function(data) {
				if(data=="Success"){
					$("#roleAddition").hide();				
					parent.jbarOnSuccess('Successfully updated');
					 $('#roleDetails tr').each(function(i){ 			
							$(this).find('td').each (function() {
								if($(this).attr("id").indexOf('editRoleHide')>= 0){
									$(this).show();				
								}
							});
					});
					$("#roleEditHeader").show();
				}else if(data=="NoChange"){				
					parent.jbarOnFailure('No Changes Made');
				}else{
					parent.jbarOnFailure('ERROR in updation');
				}			
				getRoleMemDetails();				
			});
		//}
}
function setFlagForUpdate(){
	updateStatus=true;
}