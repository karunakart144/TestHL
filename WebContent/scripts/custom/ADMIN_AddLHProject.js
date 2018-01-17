var projOldStatusCount=0;
var projOldStatusArr = [];
var updateStatus=true;
var save_count=0;
var saveProj = [];

$(document).ready(function() { 
	$("#LHProjecttable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
	$("#addLHProjecttable tr:not(.none):visible:odd").addClass("creationScreenAlternateTR");
	parent.unblockUI(); 
	
	 $('#admincreateuser').contents().live('change', function(e) {
		 $(".formError").hide();
	});
	
	 getLHProjectDetails();
});




function validateEmpID(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[a-zA-Z0-9]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only alphanumeric characters allowed."});
    return false;
	
}

function validatePhoneNumber(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[0-9-+]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only Numbers and - + characters allowed."});
    return false;
	
}


function validateEmpName(id,e){
	$(".formError").hide();
    var regex = new RegExp("^[a-zA-Z0-9 ]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    e.preventDefault();
    $("#"+id).focus();
	$("#"+id).showerrormessage({message:"* Only alphanumeric characters allowed."});
    return false;
	
}

    
    
    function displayAddProject(){
    	$("#addLHProjectDiv").show();
    	$("#updateButton").show();
    	
    }
    
    function validateProjID(id,e){
    	$(".formError").hide();
        var regex = new RegExp("^[a-zA-Z0-9_]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }
        e.preventDefault();
        $("#"+id).focus();
    	$("#"+id).showerrormessage({message:"* Only alphanumeric characters and _ allowed."});
        return false;
    	
    }
    
    function addLHProjectId(){
    	$("#addProject").attr("disabled",true);
    	$("#updateButton").attr("disabled",true);
    	var projectId=$("#projectId").val();
    	
    	if($.trim(projectId)==""){
    		$("#projectId").focus();
			$("#projectId").showerrormessage({message:"* Mandatory"});	
			 $("#addProject").removeAttr("disabled");
			 $("#updateButton").removeAttr("disabled");
    	}else{
    	
    	$.postJSON("addLHProject.htm",{projectId:projectId},function(response){
    		if(response.response!="Success"){
       		 $("#updateButton").removeAttr("disabled");
       		parent.jbarOnFailure(response.response);
       	}else{
       		$('input, select,textarea').attr('disabled', true); 
       		parent.jbarOnSuccess(response.response);
       	}
    	});
    	}
    }
    

    function getLHProjectDetails() {	
    	updateStatus=true;

    		$.postJSON('getLHProjectList.htm',{},function(data){
    				var htmldata='';
    				for(var i=0; i<data.length; i++){
    					htmldata+='<tr id="row'+i+'"><td id="editProjectHide'+i+'"><input type="image" src="images/editnode.jpg" name="showInfo" id="edit_'+i+'" title="Edit" onClick="editProject(this)" /><input type="image" src="images/Change_CS.png" name="showInfo" id="cancel_'+i+'" title="Cancel" onClick="cancelProject(this)" style="display: none"/></td>'+
    						'<input type="hidden" id="projId'+i+'" value="'+data[i].PROJECT_ID+'"/>';
    					htmldata+='<td>'+data[i].PROJECT_ID+'</td>';
    					htmldata+='<td>'+data[i].PROJECT_NAME+'</td>';
    						htmldata+='<td id="projStatus'+i+'" value="'+data[i].IS_ACTIVE+'">'+data[i].IS_ACTIVE+'</td>';
    					 htmldata+='</tr>';
    					
    				}
    		
    				var headerdata='<tr id="updateProjTH" style="display: none"><th><input type="button" id="updateProj" value="Update" onClick="validateBeforeUpdateRole()" /></th><th colspan="7">Edit</th></tr><tr><th id="projEditHeader">Edit</th><th>Project ID</th><th>Project Name</th><th>Project Status</th></tr>';
    				htmldata=headerdata+htmldata;
    				$("#projectDetails").html(htmldata);
    				
    			
    		});	
    }
    
    
    function editProject(obj){
    	$(obj).hide();	
    	var id=obj.id.slice(5,obj.id.length);	
    	$('#cancel_'+id).show();
    	saveProj.push(id);
    	save_count++;

    	if(save_count > 0){
    		$("#updateProjTH").show();
    		$("#updateProj").attr('disabled',false);
    	}

    	var row="#row"+id;
    	$(row).css({"background-color":"#F5EDDE"});	
    	projOldStatusArr.push($('#projStatus'+id).text());	
    	projOldStatusCount++;
    	if($('#projStatus'+id).text()=="Active"){
    		$('#projStatus'+id).html('<select class="myTextEdit" id="projStatusSelect'+id+'" onchange="setFlagForUpdate();"><option value="1" selected="true">Active</option><option value="0">Inactive</option></select>');
    	}else{
    		$('#projStatus'+id).html('<select class="myTextEdit" id="projStatusSelect'+id+'" onchange="setFlagForUpdate();"><option value="1">Active</option><option value="0" selected="true">Inactive</option></select>');
    	}	
    	updateStatus=false;
    }

    function cancelProject(obj){
    	
    	$(obj).hide();
    	
    	var removeId=obj.id.slice(7,obj.id.length);
    	
    	saveProj.splice($.inArray(removeId, saveProj),1);   
    	
    	$('#edit_'+removeId).show();
    	$('#row'+removeId).css({"background-color":"#FFFFFF"});
    	
    	save_count--;
    	
    	if(save_count <= 0){
    		$('#updateProjTH').hide();
    	}
    	
    	$('#projStatus'+removeId).html($('#projStatus'+removeId).attr('value'));	
    	updateStatus=false;
    }
    
    function setFlagForUpdate(){
    	updateStatus=true;
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
    	
    		$('#updateProjTH').attr('disabled',true);
    	
    		var jsonobj = "[[";		
    		for (var i=0; i<saveProj.length; i++){									
    			jsonobj+='"'+$('#projId'+saveProj[i]).val()+'",';
    			jsonobj+='"'+$('#projStatusSelect'+saveProj[i]).val()+'"';
    		}
    		
    		jsonobj+=']]';
    		alert("jsonobj::"+jsonobj);
    		$.postJSON('updateProjectList.htm',{json:jsonobj}, function(data) {
    			if(data=="Success"){
    				$("#updateProjTH").hide();
    				parent.jbarOnSuccess('Successfully updated');
    				projOldStatusArr.pop();
    			}else if(data=="NoChange"){
    				$("#updateProjTH").hide();
    				parent.jbarOnFailure('No Changes Made');
    			}else{
    				parent.jbarOnFailure('ERROR in updation');
    			}
    			getLHProjectDetails();				
    		});
    		
    		while (saveProj.length > 0) {
    			saveProj.pop();
    		}
    		save_count=0;
    		projOldStatusCount=0;
    		$('#statusForUpdation').val("Success");
    		updateStatus=true;			
    }
    
