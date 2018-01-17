var auditID,previousST,currentST,changedBY,changedDT,actionToAudit,dataToInsertAT,ticketNO,historyID,auditDataDT,fieldValue,oldVal,newVal,responseTime;

function getAuditList(ticketNumber,menuid){ 
	if(inEditMode){
		if(confirm("Do you want to continue with out updating the ticket?")){
	    	inEditMode = false;
		  	unlockTheTicket($("#TICKET_ID").val(),1);
	      }else{
				return false;
			}
	} 
	$("#hdEditMain ul li a").removeClass("selected");
	$("#hdEditMain ul li a").eq("7").addClass("selected");
	blockUI();
	ticketNO=ticketNumber;
	$("#auditLog").find("tr:gt(1)").remove(); 
	$.ajaxSetup({ cache: false });
	$.getJSON("auditloglist.htm",{ticketNo:ticketNO,menuID:menuid},function(data){										
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}else{
			if(data.length!=0)
			{	
				dataToInsertAT ="<tr><th>History ID</th><th>Previous State</th><th>Current State</th>";
				dataToInsertAT +="<th>Changed By</th><th>Changed Date</th><th>Action</th><th>Response Time</th></tr>"; 

				for(var i=0;i<data.length;i++)
				 {
									 auditID=data[i].historyId; 
									 previousST=data[i].previousState;
									 currentST=data[i].currentState;
									 changedBY=data[i].changedBy;
									 changedDT=data[i].changedDate;
									 actionToAudit=data[i].action;		
									 responseTime=data[i].responseTime;
									 dataToInsertAT += '<tr><td width="10%"><a href="#" id="showAuditInfo" onClick="getAuditDetails('+auditID+',1)">'+auditID+'</a></td>'+
											'<td width="13%">'+previousST+'</td>'+
											'<td width="20%">'+currentST+'</td>'+
											'<td width="20%">'+changedBY+'</td>'+
											'<td width="25%">'+changedDT+'</td>'+
											'<td width="22%">'+actionToAudit+'</td>'+
											'<td width="22%">'+responseTime+'</td></tr>';		
									 

				 }			

				$('#auditLog').html(dataToInsertAT);
				$("#Audit-Log").show(); 
				$("#ticketdetailsform").hide();
				$("#Attachment-Div").hide();
				$("#Approver-Data").hide();
				$("#Asset-Data").hide();
				$("#Similar-Tickets").hide();
				$("#Related-Mails").hide();
				$("#Master-Ticket").hide();
			}			
		}
		unblockUI(); 
	});
}

function DisplayDivs(div1,div2){
	$("#"+div1).show();
	$("#"+div2).hide();
}


function getAuditDetails(historyid,menuid) 
{			
		$.ajaxSetup({ cache: false });
		$.getJSON("auditloglistdetails.htm",{historyID:historyid,menuID:menuid},function(data){		
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}else{
			var auditDataDT = "";
			var dataArray = data.success;
			if(data.success && dataArray.field.length!=0)
			{
						 var test = dataArray.field;
						 var testOldVal = dataArray.oldValue;
						 var testNewVal = dataArray.newValue;
						 	
						 auditDataDT ='<tr><td align = "right"><input type="button" value="Back to List" onclick=\'DisplayDivs("auditLog","auditLogDetails")\'></input></td></tr><tr><td><table class="myDataTable" cellpadding="0" cellspacing="0">';
						 auditDataDT +="<tr><th>Field</th><th>Old Value</th><th>New Value</th></tr>";	
						 for(var i=0;i<dataArray.field.length;i++)
						 {
							 		var oldvalue = testOldVal[i].replace(/brlinebreakbreak/g,'</br>');
						 				oldvalue = oldvalue.replace(/singquotessingquotes/g,"'");
						 			var newvalue = testNewVal[i].replace(/brlinebreakbreak/g,'</br>');
						 				newvalue = newvalue.replace(/singquotessingquotes/g,"'");
										 auditDataDT += '<tr><td width="10%">'+test[i]+'</td>'+
											'<td width="13%">'+oldvalue+'</td>'+
											'<td width="20%">'+newvalue+'</td>'+
											'</tr>';	
									 									
						 }
						 auditDataDT +="</table></td></tr>"
						 
						 $('#auditLogDetails').html(auditDataDT);	
						 DisplayDivs('auditLogDetails','auditLog');
			}else if(data.error){
				alert(data.error.errormessage);
			}
		}
	});
}

