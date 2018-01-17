var ChangedArray = new Array();
var removeArray = new Array();
$(document).ready(function(){
	$('#DESCRIPTION').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});

	$('#RESOLUTION_COMMENTS').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
	$('#LOCATION_ID').bt({
		  trigger: ['hover', 'hout'],
		  positions: ['right']
		});
    $('#editmasterticket').find(':input').each(function(){
        var elementId = $(this).attr('id');       
        if(elementId != 'Edit' && elementId != 'Update'){
        	
        		$(this).attr('disabled','true');
        }	
        if(elementId=='RESOLUTION_COMMENTS' || elementId=='DESCRIPTION' || elementId=='LOCATION_ID'){        	
        	$(this).removeAttr("disabled");
        	$(this).attr('readonly','true');
        }
     
       
    });  
    $.each(nonemaparray, function(index, value) {         
    	$("#"+jQuery.trim(value)+"_TR").hide();
    });
    $('input,select,textarea').change(function() {
    	if(this.id!="FILTERLOGGEDINUSERS" && this.id!="BUILDING" && this.id!="FLOOR"){
    		AddValuetoArray(this.id,ChangedArray)
    	}
    });
    if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
    	if($("#CLOSED_DATE").val()!=""){    	
    		$("#RESOLUTION_COMMENTS").attr('readonly','true');
    	}else{
    		 $('#RESOLUTION_COMMENTS').removeAttr('readonly'); 
    	}
    }
    
    $("#WORKFLOW_STATE").change(function(){
    	  if($("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
    		  $("#RESOLUTION_COMMENTS_TR").show();    		  
    		  $('#RESOLUTION_COMMENTS').removeAttr('readonly'); 
    	    }
    	AddValuetoArray("WORKFLOW_STATE",ChangedArray);
	});
    
    if($("#IFIRST_TICKET_ID").val()!=0 && $("#IFIRST_TICKET_ID").val()!=""){
    	$("#ifirstTicketId").show();
    }
    
});
var confirmresult = true;
var inEditMode = false;
function LockTicket(){
	if(!inEditMode){
		if(editmaparray.length>=1){
			var json =getJSONtoLockTicket();
			var URL="lockIHDTicket.htm?jsonString="+json;
			$.postJSON("lockIHDMasterTicket.htm",{jsonString:json},function(data){
				if (data.status && data.status=="success" && data.length != 0) {			
					showInEditMode();
				}else if(data.LOCKED_BY==looged_user_id){
					showInEditMode();
				}else{
					parent.jbarOnFailure("The ticket is locked by "+data.NAME+"( "+data.LOCKED_BY+" )");
				}
			});
		}else{
			parent.jbarOnFailure("Permission denied to edit the ticket");
		}
	}
showInEditMode();
}
function chkAddValuetoArray(value,array,chkarray){
	if(jQuery.inArray(value, chkarray)==-1){
		array.push(value);
	}
}
function showInEditMode(){
	inEditMode = true;	
	$.each(editmaparray, function(index, value) { 
	
			$("#"+jQuery.trim(value)).removeAttr("disabled");		
			
		
    });
}
function validateTicketDetails(){
	var isValidationError = true;
	if(!$("#WORKFLOW_STATE").attr("disabled")&& $("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed" && logged_user_role!="User"){
		var resolution_comms = jQuery.trim($("#RESOLUTION_COMMENTS").val());
		if(!$("#RESOLUTION_COMMENTS").attr("disabled") && resolution_comms==""){
			$("#RESOLUTION_COMMENTS").focus();
			$("#RESOLUTION_COMMENTS").showerrormessage({message:"* Mandatory"});
			AddValuetoArray("RESOLUTION_COMMENTS",ChangedArray);
			isValidationError =false;
		}
		if($("#RESOLUTION_COMMENTS").val().length>1000){
			$("#RESOLUTION_COMMENTS").focus();
			$("#RESOLUTION_COMMENTS").showerrormessage({message:"Resolution comments cannot have more than 1000 characters"});
			isValidationError =false;
		}
	}
	return isValidationError;
}

function getJsonForUpdateTicket(){
	var jsonArray ="";
	var jsonString = "";
	var auditLogJson = "";
	var validationStatus = true ;
		if(ChangedArray.length >0){
			validationStatus = validateTicketDetails();
		}
		if(ChangedArray.length!=0 && validationStatus && getConfirmResult()){
		
		chkAddValuetoArray("WORKFLOW_STATE",removeArray,ChangedArray);
		AddValuetoArray("WORKFLOW_STATE",ChangedArray);
		AddValuetoArray("FUNCTION_ID",ChangedArray);
		AddValuetoArray("FUNCTION",ChangedArray);
		AddValuetoArray("VERSION_NO",ChangedArray);	
		AddValuetoArray("ECT",ChangedArray);	
		var TicketId = $("#TICKET_ID").val();
		var functionId=$("#FUNCTION_ID").val();
		jsonString = '{';
		jsonString +='"TICKET_ID":"'+TicketId+'"';
		auditLogJson = jsonString;
		$.each(ChangedArray, function(index, value) { 
			var test = value.replace(/^\s+/,"");			
			var attrtype = $("#"+test).attr("type"); 
			
	        if(attrtype.indexOf("select") != -1){
	        	jsonString+=',"'+jQuery.trim(value)+'":"'+$("#"+test+" option:selected").val().replace(/"/g,"'")+'"';
	        	auditLogJson +=',"'+jQuery.trim(value)+'":"'+$("#"+test+" option:selected").text().replace(/"/g,"'")+'"';
	        }else{
	        	jsonString+=',"'+jQuery.trim(value)+'":"'+$("#"+test).val().replace(/"/g,"!qt")+'"';
	        	auditLogJson +=',"'+jQuery.trim(value)+'":"'+$("#"+test).val().replace(/"/g,"!qt")+'"';
	        }
		}); 	
		jsonString +='}';
		auditLogJson +='}';
		
		jsonArray ='{"updateJson":'+jsonString;
		jsonArray +=',"auditlogJson":'+auditLogJson;
		jsonArray +=',"removingJson":['+removeArray+']}';
		}else if(!validationStatus){
			jsonArray="Error";
		}
		jsonArray = jsonArray.replace(/\n/g,'brlinebreakbreak');	
		return jsonArray;
}
function AddValuetoArray(value,array){
	if(jQuery.inArray(value, array)==-1){
		array.push(value);
	}
}
function UpdateIHDTicket(){  
	var json = getJsonForUpdateTicket();
	if(json !="Error" && json!=="" && confirmresult){ 
		$("#Update").attr('disabled','true');
		$("#Edit").attr('disabled','true');
		$("#UpdateButtonTd").find("input:button").attr('disabled','true'); 
		$.ajaxSetup({ cache: false });
		$.postJSON("iConnectMasterTicketUpdation.htm",{jsonString:json},function(data){
			if (data.status=="Success") {	
				$('#editmasterticket').find(':input').each(function(){
			        var elementId = $(this).attr('id');			    
			        $(this).attr('disabled','true');
				
			    });
				ChangedArray = new Array();
				//alwaysDisabledArray = new Array();
				inEditMode = false;				
				parent.jbarOnSuccess(data.message); 
				parent.isInEditModeOpen = false;
				window.location.reload();
				if($("#CLOSED_DATE").val()!=""){
			    	$("#RESOLUTION_COMMENTS").removeAttr("disabled");
			    	}
			}else if(data.status=="Error"){ 
				$("#Update").removeAttr('disabled');
				$("#Edit").removeAttr('disabled');
				$("#UpdateButtonTd").find("input:button").removeAttr('disabled');    
				parent.jbarOnFailure(data.message)
			}
			
	});
	
	}else if(json !="Error"  && confirmresult){
		parent.jbarOnFailure('No Changes Made');
	}
	
}

function getConfirmResult(){
	var status = true;
	if(logged_user_role=="User" && mainWorkflowState=="Resolved/Closed" && $("#WORKFLOW_STATE option:selected").text()=="Resolved/Closed"){
        $("#WORKFLOW_STATE").focus();
        $("#WORKFLOW_STATE").showerrormessage({message:"Ticket cannot be updated"});
        status = false;
    } else if(jQuery.inArray("WORKFLOW_STATE", ChangedArray)==-1 && loginrole!="User"){
          status = confirm("Do you want to continue with out changing the Workflow Status ?")
    }
    confirmresult = status;
    return status;
}
var linkedTicketsHTML="";
function getLinkedTicketList(id){
	$("#hdEditMain ul li a").removeClass("selected");
	$("#hdEditMain ul li a").eq("2").addClass("selected");

	$.ajaxSetup({ cache: false });
	$.getJSON('getChildTicketListForMaster.htm',{masterId:id},function(result){
		var childTicket=result.childTicketList;
		if(childTicket.length!=0){			
			$("#linkedTickets").find("tr:gt(1)").remove(); 
			linkedTicketsHTML="<tr><th>Child Ticket ID</th><th>Subject</th><th>Description</th>";
			linkedTicketsHTML+="</tr>";
			for(var i=0;i<childTicket.length;i++)
			 {					
				linkedTicketsHTML+="<tr><td>"+childTicket[i].TICKET_ID+"</td><td>"+childTicket[i].SUBJECT+"</td><td>"+childTicket[i].DESCRIPTION+"</td></tr>";
				
			 }
		}else{			
			$("#linkedTickets").find("tr:gt(1)").remove(); 
			
			linkedTicketsHTML+="<tr><td>No records Found</td></tr>"
		}
		
		$('#linkedTickets').html(linkedTicketsHTML);		
		$('#Linked-Tickets').show();
		$("#Audit-Log").hide(); 
		$("#editmasterticket").hide();
		$("#Attachment-Div").hide();	
		linkedTicketsHTML="";
	});
}

function getJSONtoLockTicket(){

	var TicketId = $("#TICKET_ID").val();
	var jsonString = '{';
		jsonString +='"TICKET_ID":"'+TicketId+'","DATA":"';
	var xmldata = "<data>";
	if(editmaparray.length>=1){
		$.each(editmaparray, function(index, value) {
			var test = value.replace(/^\s+/,"");
			var val1=$("#"+test).val();
			var val2=val1;
			if((test=='RESOLUTION_COMMENTS')){
				val2=val1.replace(/&/g,"&#38;");
				val2=val2.replace(/</g,"&#60;");
				val2=val2.replace(/>/g,"&#62;");
				val2=val2.replace(/=/g,"&#61;");		
				val2=val2.replace(/!qt/g,"&#34;");
				val2=val2.replace(/\"/g,"&#34;");
				val2=val2.replace(/\'/g,"&#39;");
				val2=val2.replace(/~/g,"&#126;");	
				val2=val2.replace(/\\/g,"&#92;");
				val2=val2.replace(/\//g,"&#47;");			
			}
			//if(test!='ADDITIONAL_INFO'){
			var attrtype = $("#"+test).attr("type"); 
			
	        if(attrtype.indexOf("select") != -1){
	        	xmldata+="<"+jQuery.trim(value)+">"+$("#"+test+" option:selected").text()+"</"+jQuery.trim(value)+">";
	        }else{
	        	xmldata+="<"+jQuery.trim(value)+">"+val2+"</"+jQuery.trim(value)+">";
	        }
		//	}
		}); 
		if(jQuery.inArray("WORKFLOW_STATE", editmaparray)==-1){
			xmldata+="<WORKFLOW_STATE>"+$("#WORKFLOW_STATE option:selected").text()+"</WORKFLOW_STATE>";
		}
		xmldata+="<ECT>"+$("#ECT").val()+"</ECT>";
		xmldata+="<VERSION_NO>"+$("#VERSION_NO").val()+"</VERSION_NO>";
	}
	xmldata+= "</data>";
    jsonString = jsonString+xmldata+'","MENU_ID":"114"}';	
    jsonString = jsonString.replace(/\n/g,'brlinebreakbreak');
	return jsonString;

}

function unlockTheTicket(ticketId,menuId){
	
	var jsonobj = '{"JSONARRAY":['+'{"TICKET_ID":"'+ticketId+'","MENU_ID":"'+menuId+'"}'+']}';
	$.ajaxSetup({ cache: false });
	$.getJSON('unlockMasterTickets.htm', {json:jsonobj}, function(data) {
		if(data.status=="Success"){
		}	
	});
} 
function getDescVal(thisobj)
{
	var descriptionVal= $("#"+thisobj).val();
	var descrValue='';
	var descriptionLength=$("#"+thisobj).val().length;
	var i=0;
	var j=0;
	
	while(i<descriptionLength){
		j=j+25;
		descrValue=descrValue+descriptionVal.substring(i,j)+" \n";
		
		i=i+25;
	}
	$("#"+thisobj).attr("bt-xTitle",descrValue);	
}
