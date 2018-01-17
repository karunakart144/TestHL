function getAttachments(ticketID)  
{	
	if(inEditMode){
		if(confirm("Do you want to continue with out updating the ticket?")){
	    	inEditMode = false;
		  	unlockTheTicket($("#TICKET_ID").val(),1);
	      }else{
				return false;
			}
	}
	$("#hdEditMain ul li a").removeClass("selected");
	$("#hdEditMain ul li a").eq("1").addClass("selected");
	blockUI();

	$.ajaxSetup({ cache: false });
  
	$.getJSON("attachmentlist.htm",{ticketNo:ticketID},function(data){	
		
		$("#Audit-Log").hide();
		$("#ticketdetailsform").hide();
		$("#Attachment-Div").show();
		$("#Approver-Data").hide();
		$("#Asset-Data").hide();
		$("#Similar-Tickets").hide();
		$("#Related-Mails").hide();
		$("#Master-Ticket").hide();
		
		var attachmentHtml = "";
		var nextstate="";
		var referenceid="";
		var managerId=document.getElementById("MANAGER_ID").value;
		 if(managerId.indexOf('(')!=-1)
	     {
	    	 managerId=managerId.substring(managerId.indexOf('(')+1,managerId.length-1);
	     }
			attachmentHtml +='<table border="0" cellspacing="5" cellpadding="5" width="56%"><tr><th align="left" width="43%"><u> Attachment Type </u></th><th align="left"><u>Attachment Name</u></th></tr></table>';
			var statushtml="";
			var i=0;
			var j=0;
			var x=0;
			var k=0;
			var l=0;
			var m=0;
			var n=0;
			//modified by 720307
			var problemAttachFlag=false;
			var simpleAttachFlag=false;
			var problemDescAttachFlag=false;
			var reOpenDescAttachFlag=false;
			var attachmentHtmlForEmer = "";
			var reasonforApprove_Reject=false;
			//var emergencyForm="";
			var statusId = "";
			var statusName = "";
			var isExEmployee="";
			var attachFlag=false;
			
		if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data.message);
		}
		else{
			
		if(data.length!=0)
			{	
			var statuscheck=$('#WORKFLOW_STATE').val();	
			for( i=0;i<data.length;i++)
				 {		
					 nextstate=data[i].nextstate;	
					 statusId = data[i].workflow_STATE;
					 isExEmployee=data[i].is_EX_EMPLOYEE;
					//modified by 720307
					 statusName=data[i].workflow_NAME;
						 if(data[i].approver!="PROBLEM")
						 {						
							referenceid=data[i].nextapproverid;	
							
						 }
						 else
						{ 
							referenceid=data[i].nextapproverid;
							problemAttachFlag=true;
						}
						if(data[i].approver=="Simple Attachment")
						{
							j++;
		
			              if((loginrole=="Level 0 Executive"||loginrole=="Level 0 Manager"||loginrole=="Manager"||loginrole=="Executive"||loginrole=="IS Executive"||loginrole=="IS Manager"||loginrole=="Finance Executive"||loginrole=="Finance Manager"||loginrole=="Admin Executive"||loginrole=="Admin Manager"|| loginrole=="SAP Manager")){
							
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Attachment'+j+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="attachmentStatus" path="attachmentStatus" id = "attachmentStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								

                                if(data[i].reference_ID==7){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td></tr>';
								 }
                                if(data[i].reference_ID==9){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td></tr>';
								 }
                                if(data[i].reference_ID==10){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td></tr>';
								 }
			               }
			              else if((loginrole=="HR Executive"||loginrole=="HR Manager")&&(data[i].is_EX_EMPLOYEE==1)){
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Attachment'+j+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="attachmentStatus" path="attachmentStatus" id = "attachmentStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								

			            	  if(data[i].reference_ID==7){
			            		  attachFlag=true;
			            		  if(data[i].is_CHECKED==1)
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file"  style="display:none"/></input></td><td><input type="checkbox" name="attachmentCheckbox1"  id="attachmentCheckbox1"  path="attachmentCheckbox1" checked="checked" ></input></td></tr>';
			            		  else
								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td><td><input type="checkbox" name="attachmentCheckbox1" path="attachmentCheckbox1" id="attachmentCheckbox1"></input></td></tr>';

								 }
                              if(data[i].reference_ID==9){
                            	  attachFlag=true;
                            	  if(data[i].is_CHECKED==1)
  									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox2" id="attachmentCheckbox2" checked="checked"></input></td></tr>';
  			            		  else
  								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox2" id="attachmentCheckbox2"></input></td></tr>';
								 }
                              if(data[i].reference_ID==10){
                            	  attachFlag=true;
                            	  if(data[i].is_CHECKED==1)
  									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox3" id="attachmentCheckbox3" checked="checked" ></input></td></tr>';
  			            		  else
    								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox3" id="attachmentCheckbox3"></input></td></tr>';
								 }
			              }
			              else if((loginrole=="HR Executive"||loginrole=="HR Manager")&&(data[i].is_EX_EMPLOYEE==0)){
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Attachment'+j+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="attachmentStatus" path="attachmentStatus" id = "attachmentStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								

			            	  if(data[i].reference_ID==7){
			            		  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td></tr>';
								 }
                              if(data[i].reference_ID==9){
                            	  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td></tr>';
								 }
                              if(data[i].reference_ID==10){
                            	  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td></tr>';
								 }
			              }
			              else
								attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td><b>Attachment '+j+'  : </b></td><td style="width:50%;"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table></br>';

						} else if(data[i].approver=="Smart Search Attachment"){

							x++;
		
			              if((loginrole=="Level 0 Executive"||loginrole=="Level 0 Manager"||loginrole=="Manager"||loginrole=="Executive"||loginrole=="IS Executive"||loginrole=="IS Manager"||loginrole=="Finance Executive"||loginrole=="Finance Manager"||loginrole=="Admin Executive"||loginrole=="Admin Manager"|| loginrole=="SAP Manager")){
							
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Smart Search Attachment'+x+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>';								

                                if(data[i].reference_ID==7){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td></tr>';
								 }
                                if(data[i].reference_ID==9){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td></tr>';
								 }
                                if(data[i].reference_ID==10){
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td></tr>';
								 }
			               }
			              else if((loginrole=="HR Executive"||loginrole=="HR Manager")&&(data[i].is_EX_EMPLOYEE==1)){
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Smart Search Attachment'+x+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>';								

			            	  if(data[i].reference_ID==7){
			            		  attachFlag=true;
			            		  if(data[i].is_CHECKED==1)
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file"  style="display:none"/></input></td><td><input type="checkbox" name="attachmentCheckbox1"  id="attachmentCheckbox1"  path="attachmentCheckbox1" checked="checked" ></input></td></tr>';
			            		  else
								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td><td><input type="checkbox" name="attachmentCheckbox1" path="attachmentCheckbox1" id="attachmentCheckbox1"></input></td></tr>';

								 }
                              if(data[i].reference_ID==9){
                            	  attachFlag=true;
                            	  if(data[i].is_CHECKED==1)
  									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox2" id="attachmentCheckbox2" checked="checked"></input></td></tr>';
  			            		  else
  								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox2" id="attachmentCheckbox2"></input></td></tr>';
								 }
                              if(data[i].reference_ID==10){
                            	  attachFlag=true;
                            	  if(data[i].is_CHECKED==1)
  									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox3" id="attachmentCheckbox3" checked="checked" ></input></td></tr>';
  			            		  else
    								    attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td><td><input type="checkbox"  name="attachmentCheckbox3" id="attachmentCheckbox3"></input></td></tr>';
								 }
			              }
			              else if((loginrole=="HR Executive"||loginrole=="HR Manager")&&(data[i].is_EX_EMPLOYEE==0)){
			            	  attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td width="27%" name="APPROVER" id="APPROVER" path="APPROVER"><b>Smart Search Attachment'+j+' : </b></td><td width="50%"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="attachmentStatus" path="attachmentStatus" id = "attachmentStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								

			            	  if(data[i].reference_ID==7){
			            		  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT1" id="REFERENCE_ID1" name="REFERENCE_ATTACHMENT1" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile1" name="attachmentfile1" type="file" style="display:none"/></input></td></tr>';
								 }
                              if(data[i].reference_ID==9){
                            	  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT2" id="REFERENCE_ID2" name="REFERENCE_ATTACHMENT2" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile2" name="attachmentfile2" type="file" style="display:none"/></input></td></tr>';
								 }
                              if(data[i].reference_ID==10){
                            	  attachFlag=true;
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ATTACHMENT3" id="REFERENCE_ID3" name="REFERENCE_ATTACHMENT3" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="attachmentfile3" name="attachmentfile3" type="file" style="display:none"/></input></td></tr>';
								 }
			              }
			              else
								attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="90%"><tr><td><b>Attachment '+j+'  : </b></td><td style="width:50%;"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table></br>';

						
							
						}
						else
						{	
							if((data[i].approver!="PROBLEM") && (data[i].verified_BY=="-") && (loginrole=="Level 0 Executive"||loginrole=="Level 0 Manager")){
								attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm" id="editattachmentForm" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME" name="FUNCTION_NAME" value="'+data[i].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID" name="CATEGORY_ID" value="'+data[i].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE" name="WORKFLOW_STATE" value="'+data[i].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID" name="SUBCATEGORY_ID" value="'+data[i].subcategory_ID+'" ></input></td><td    style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID" name="REFERENCE_ID" value="'+data[i].reference_ID+'" ></input></td><td name="APPROVER" id="APPROVER" path="APPROVER"><b>'+data[i].approver+' : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="status" path="status" id = "status'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="select"  selected>Please Select</option><option value="Verified"  >Verified</option><option value="Rejected" >Rejected</option><option value="Replaced" >Replace</option></select></td><td><input type="submit" id="save'+i+'"  value="SAVE" onclick="javascript:validateBeforeSave(this.value,'+i+');"></input></td><td ><input id="FileUpload'+i+'" path="approvalfile" name="approvalfile" type="file" style="display:none" /></input></td></tr></table></form></br>';	
							}
							else{	
								
								
								if((data[i].approver!="Approval Mail") && (data[i].approver!="DB Scripts") && (data[i].approver!="UAT Report") && (data[i].approver!="Test Report")){
									  if((statusName=="Waiting For User Response" || statusName=="Need More Info")&&(loginrole=="User"))
									  {
										if(data[i].approver=="PROBLEM"){
											attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td><td name="APPROVER" id="APPROVER" path="APPROVER"><b>'+data[i].approver+' : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="problemStatus" path="problemStatus" id = "problemStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								
										    attachmentHtml +='<td style="display:none"><input path="REF_ID_PROBLEM_FILE" id="REFERENCE_ID1" name="REF_ID_PROBLEM_FILE" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="approvalproblemfile" name="approvalproblemfile" type="file" style="display:none"/></input></td></tr>';
										}
	                                    if(data[i].approver=="Problem Description"){
											attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td><td name="APPROVER" id="APPROVER" path="APPROVER"><b>'+data[i].approver+' : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="problemDescriptionStatus" path="problemDescriptionStatus" id = "problemDescriptionStatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value="selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								
	 										attachmentHtml +='<td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file" style="display:none"/></input></td></tr>';
	                                    }
									  }
									else  if((statusName=="Resolved/Closed" || statusName=="Re-Open")&&(loginrole=="User")){
										attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td><td name="APPROVER" id="APPROVER" path="APPROVER"><b>'+data[i].approver+' : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td><td><select name="reopenstatus" path="reopenstatus" id = "reopenstatus'+i+'" style="width:130px;" onchange="javascript:approval(this.value,'+i+');"><option value=" selected">Please Select</option><option value="Replaced" >Replace</option></select></td>';								
										if(data[i].approver=="Re-Open Description"){
	 										attachmentHtml +='<td style="display:none"><input path="REF_ID_APP_RE_OPEN_DESC_FILE" id="REFERENCE_ID3" name="REF_ID_APP_RE_OPEN_DESC_FILE" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="reopendescriptionfile" name="reopendescriptionfile" type="file" style="display:none"/></input></td></tr>';
									      }
										  if(data[i].approver=="PROBLEM"){
											    attachmentHtml +='<td style="display:none"><input path="REF_ID_PROBLEM_FILE" id="REFERENCE_ID1" name="REF_ID_PROBLEM_FILE" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="approvalproblemfile" name="approvalproblemfile" type="file" style="display:none"/></input></td></tr>';
											}
		                                  if(data[i].approver=="Problem Description"){
		 										attachmentHtml +='<td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="'+data[i].reference_ID+'" ></input></td><td><input  id="FileUpload'+i+'" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file" style="display:none"/></input></td></tr>';
		                                    }
	                                    }
									  else
									  {
										attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td><b>'+data[i].approver+' : </b></td><td style="width: 63%;"><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr>';
										attachmentHtml +='</table>';
									  }
								}
								
							}
						}
						  //modified by 720307
						if(((statusName=="Waiting For User Response")&&(loginrole=="User"))||((statusName=="Need More Info")&&(loginrole=="User"))||((statusName=="Resolved/Closed" || statusName=="Re-Open")&&(loginrole=="User")))
					    {  
							attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td>';								
					    }
						
						if(data[i].approver=="Problem Description"){
							problemDescAttachFlag=true;
						}
						if(data[i].approver=="Re-Open Description"){
							reOpenDescAttachFlag=true;
						}
						if(data[i].approver=="Reason for Approve/Reject"){
							reasonforApprove_Reject=true;
						}
						if(((statusName=="Waiting For IT Function Head Approval")&&(loginrole=="IT Function Head"))||((statusName=="Waiting For IS Head Approval")&&(loginrole=="IS Head"))||((statusName=="Need More Info")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((statusName=="Information Provided")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((statusName=="Waiting For Infosec Approval")&&(loginrole=="IT Security"))||((statusName=="Waiting For RO Approval")&&(userid==managerId))||((statusName=="Waiting For SLM Approval")&&(loginrole=="S/W License Mgr")))
					    {  
						    attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td>';								
						}
						//emergencyForm +='<form action="attachmentlist.htm" path="editattachmentForm6"  id="editattachmentForm6" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID6" name="TICKET_ID" value="'+data[i].ticket_ID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME6" name="FUNCTION_NAME" value="'+data[i].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID6" name="CATEGORY_ID" value="'+data[i].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE6" name="WORKFLOW_STATE" value="'+data[i].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID6" name="SUBCATEGORY_ID" value="'+data[i].subcategory_ID+'" ></input></td>';
						if((data[i].approver=="Approval Mail") && (loginrole!="User")){							
							//j++;
							k++;
							if((loginrole=="IS Executive") || (loginrole=="IS Manager")){ 
								if(($("#SUB_STATUS").val()!="Completed") && ($("#SUB_STATUS").val()!="Work In Progress") && ($("#SUB_STATUS").val()!="Submitted to DBA")){
									attachmentHtmlForEmer += '<table border="0" cellspacing="5" cellpadding="5" width="43%"><tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'<td align="left"><select id="replaceEmerAttachment" name="replaceEmerAttachment" style="width:130px;" onchange="javascript:replaceEmerAttachmentfun(this.value,\'replaceEmergencyAttachment\',\'saveForEmerAttachment\')"><option value="--Please Select--">--Please Select--</option><option value="Replace">Replace</option></select></td><td><input disabled="disabled" id="replaceEmergencyAttachment" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr></table>';
								}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="43%"><tr><td><b><span style="color:red">*</span>Approval Mail : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
								}
							}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="43%"><tr><td><b><span style="color:red">*</span>Approval Mail : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
							}
							//alert(j);
						}
						if((data[i].approver=="DB Scripts") && (loginrole!="User")){
							//j++;	
							l++;
							if((loginrole=="IS Executive") || (loginrole=="IS Manager")){ 
								if(($("#SUB_STATUS").val()!="Completed") && ($("#SUB_STATUS").val()!="Work In Progress")&& ($("#SUB_STATUS").val()!="Submitted to DBA")){
									attachmentHtmlForEmer += '<table border="0" cellspacing="5" cellpadding="5" width="41%"><tr><td  style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'<td align="left"><select id="replaceEmerAttachment1" name="replaceEmerAttachment1" style="width:130px;" onchange="javascript:replaceEmerAttachmentfun(this.value,\'replaceEmergencyAttachment1\',\'saveForEmerAttachment1\')"><option value="--Please Select--">--Please Select--</option><option value="Replace">Replace</option></select></td><td><input disabled="disabled" id="replaceEmergencyAttachment1" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr></table>';
								}else{									
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="41%"><tr><td><b>DB Scripts  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
								}
							}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="41%"><tr><td><b>DB Scripts  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
							}
							//alert(j);
						}
						if((data[i].approver=="UAT Report") && (loginrole!="User")){
							//j++;
							m++;
							if((loginrole=="IS Executive") || (loginrole=="IS Manager")){ 
								if(($("#SUB_STATUS").val()!="Completed") && ($("#SUB_STATUS").val()!="Work In Progress") && ($("#SUB_STATUS").val()!="Submitted to DBA")){
									attachmentHtmlForEmer += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td  style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'<td align="left"><select id="replaceEmerAttachment2" name="replaceEmerAttachment2" style="width:130px;" onchange="javascript:replaceEmerAttachmentfun(this.value,\'replaceEmergencyAttachment2\',\'saveForEmerAttachment2\')"><option value="--Please Select--">--Please Select--</option><option value="Replace">Replace</option></select></td><td><input disabled="disabled" id="replaceEmergencyAttachment2" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr></table>';
								}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td><b>UAT Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
								}
							}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td><b>UAT Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
							}
							//alert(j);
						}
						if((data[i].approver=="Test Report") && (loginrole!="User")){
							//j++;	
							n++;
							if((loginrole=="IS Executive") || (loginrole=="IS Manager")){ 
								if(($("#SUB_STATUS").val()!="Completed") && ($("#SUB_STATUS").val()!="Work In Progress") && ($("#SUB_STATUS").val()!="Submitted to DBA")){									
									attachmentHtmlForEmer += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td  style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'<td align="left"><select id="replaceEmerAttachment3" name="replaceEmerAttachment3" style="width:130px;" onchange="javascript:replaceEmerAttachmentfun(this.value,\'replaceEmergencyAttachment3\',\'saveForEmerAttachment3\')"><option value="--Please Select--">--Please Select--</option><option value="Replace">Replace</option></select></td><td><input disabled="disabled" id="replaceEmergencyAttachment3" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr></table>';
								}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td><b>Test Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
								}
							}else{
									attachmentHtml += '<table border="0" cellspacing="5" cellpadding="5" width="65%"><tr><td><b>Test Report  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr></table>';
							}
							
						}						
						//emergencyForm=emergencyForm+attachmentHtmlForEmer+'</form>';	
						//attachmentHtml+='</table></form>';
						//$('#Attachment-Div').html(attachmentHtml);										 
				 }					
				 if((loginrole=="Level 0 Executive"||loginrole=="Level 0 Manager"||loginrole=="Manager"||loginrole=="Executive"||loginrole=="IS Executive"||loginrole=="IS Manager"||loginrole=="Finance Executive"||loginrole=="Finance Manager"||loginrole=="Admin Executive"||loginrole=="Admin Manager"||loginrole=="HR Executive"||loginrole=="HR Manager" || loginrole=="SAP Manager"))
				 {
					 if(nextstate!=null)
					 {
						attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm1"  id="editattachmentForm1" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID4" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME4" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID4" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE4" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID4" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td><td    style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID4" name="REFERENCE_ID" value="'+referenceid+'" ></input></td><td><b>'+nextstate+':</b></td><td><input   id="FileUpload4" path="approvalfile" name="approvalfile" type="file"/></input></td><td><input   type="submit" id="saveForAttachment4" value="Update"></input></td></tr></table></form></br>';						
					 }	
					 if(statuscheck!="Closed(By User)"&&statuscheck!="Closed(By System)"&&statuscheck!="Resolved/Closed")
					 {				
						if(j==0)
							 {
								if(loginrole=="Executive"||loginrole=="Manager"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr></br><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID1" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME1" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID1" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE1" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID1" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
										attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="7" ></input></td><td><b>Attachment 1:</b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
									//}
								}
								else if(statusId == 28 && statuscheck=="Waiting For RO Approval"){	
									   attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr></br><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID1" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME1" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID1" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE1" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID1" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
									   attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="1" ></input></td><td><b>Reporting Officer :</b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
								}
								else{
									attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr></br><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID1" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME1" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID1" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE1" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID1" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="7" ></input></td><td><b>Attachment 1:</b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
								}
								if((statuscheck=="Assigned") || (statuscheck=="Work In Progress") || (statuscheck=="Re-Assigned")){
									if((loginrole=="IS Executive") || (loginrole=="IS Manager")){
										if(k==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Files supported:msg)</i></span>:</td><td><input id="FileUpload6" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr>';
										if(l==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts </b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Data Change.Files supported:txt)</i></span>:</td><td><input id="FileUpload7" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr>';
										if(m==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report </b> <span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Optional.Files supported:xls & xlsx)</i></span> :</td><td><input   id="FileUpload8" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr>';
										if(n==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"> <i>(mandatory for Emergency Change.Files supported:xls & xlsx)</i> </span>:</td><td><input id="FileUpload9" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr>';									
									}
								}
								if(attachmentHtmlForEmer!="")
										attachmentHtml +=attachmentHtmlForEmer;
								if(loginrole=="Executive"||loginrole=="Manager"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										attachmentHtml+='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr></table></form>';
									//}
								}else{
									if((attachFlag)&&(isExEmployee=="1")&&(loginrole=="HR Executive"||loginrole=="HR Manager" )){
										attachmentHtml +='<tr width="420px" style="color:red; font-weight:bold"><td colspan="3" style="font-style:italic;">(Please select the attachments which have to be sent via email.)</td></tr><tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
									}else{
									attachmentHtml+='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr></table></form>';
									}
								}
							 }
							 if(j==1)
							 {
								if(loginrole=="Executive"||loginrole=="Manager"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										
										attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm3"  id="editattachmentForm3" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr></br><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID2" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME2" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID2" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE2" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID2" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
										attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID2" name="REFERENCE_ID" value="9" ></input></td><td><b>Attachment 2:</b></td><td><input  id="FileUpload2" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
									//}
								}
								else{
									attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm3"  id="editattachmentForm3" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"><tr></br><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID2" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME2" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID2" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE2" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID2" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID2" name="REFERENCE_ID" value="9" ></input></td><td><b>Attachment 2:</b></td><td><input  id="FileUpload2" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
								}
								if((statuscheck=="Assigned") || (statuscheck=="Work In Progress") || (statuscheck=="Re-Assigned")){
									if((loginrole=="IS Executive") || (loginrole=="IS Manager")){							
										if(k==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Files supported:msg)</i></span>:</td><td><input id="FileUpload6" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr>';
										if(l==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Data Change.Files supported:txt)</i></span>:</td><td><input id="FileUpload7" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr>';
										if(m==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report</b> <span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Optional.Files supported:xls & xlsx)</i></span> :</td><td><input   id="FileUpload8" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr>';
										if(n==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i> (mandatory for Emergency Change.Files supported:xls & xlsx) </i></span>:</td><td><input id="FileUpload9" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr>';									
									}
								}
								if(attachmentHtmlForEmer!="")
										attachmentHtml +=attachmentHtmlForEmer;
								if(loginrole=="Manager"||loginrole=="Executive"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										attachmentHtml +='<tr><td><input   type="submit" id="saveForAttachment2" value="Update"></input></td></tr></table></form>';
									//}
								}else{
									if((attachFlag)&&(isExEmployee=="1")&&(loginrole=="HR Executive"||loginrole=="HR Manager" ))
										attachmentHtml +='<tr width="420px" style="color:red; font-weight:bold"><td colspan="3" style="font-style:italic;">(Please select the attachments which have to be sent via email.)</td></tr><tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
										else
									attachmentHtml +='<tr><td><input   type="submit" id="saveForAttachment2" value="Update"></input></td></tr></table></form>';
								    }
							 }
							 if(j==2)
							 {
								if(loginrole=="Manager"||loginrole=="Executive"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm4"  id="editattachmentForm4" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID3" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME3" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID3" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE3" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID3" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
										attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID3" name="REFERENCE_ID" value="10" ></input></td><td><b>Attachment 3:</b></td><td><input  id="FileUpload3" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
									//}
								}
								else{
									attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm4"  id="editattachmentForm4" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID3" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME3" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID3" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE3" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID3" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
									attachmentHtml += '<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID3" name="REFERENCE_ID" value="10" ></input></td><td><b>Attachment 3:</b></td><td><input  id="FileUpload3" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
								}
								if((statuscheck=="Assigned") || (statuscheck=="Work In Progress") || (statuscheck=="Re-Assigned")){
									if((loginrole=="IS Executive") || (loginrole=="IS Manager")){							
										if(k==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Files supported:msg)</i></span>:</td><td><input id="FileUpload6" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr>';
										if(l==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Data Change.Files supported:txt)</i></span>:</td><td><input id="FileUpload7" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr>';
										if(m==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report</b> <span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Optional.Files supported:xls & xlsx)</i></span> :</td><td><input   id="FileUpload8" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr>';
										if(n==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
											attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Emergency Change.Files supported:xls & xlsx)</i> </span>:</td><td><input id="FileUpload9" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr>';									
									}
								}
								if(attachmentHtmlForEmer!="")
										attachmentHtml +=attachmentHtmlForEmer;
								if(loginrole=="Manager"||loginrole=="Executive"){
									//if($("#EMERGENCY_TYPE").val()=="NA"){
										attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
									//}
								}else{
									if((attachFlag)&&(isExEmployee=="1")&&(loginrole=="HR Executive"||loginrole=="HR Manager" ))
										attachmentHtml +='<tr width="420px" style="color:red; font-weight:bold"><td colspan="3" style="font-style:italic;">(Please select the attachments which have to be sent via email.)</td></tr><tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
										else
									attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
								}
							 }	
							 if(j==3){	
								 if(k==0 || l==0 || m==0 || n==0)
									attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm4"  id="editattachmentForm4" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID3" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME3" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID3" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE3" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID3" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
									if((statuscheck=="Assigned") || (statuscheck=="Work In Progress") || (statuscheck=="Re-Assigned")){
										if((loginrole=="IS Executive") || (loginrole=="IS Manager")){							
											if(k==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
												attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Files supported:msg)</i></span>:</td><td><input id="FileUpload6" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr>';
											if(l==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
												attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Data Change.Files supported:txt)</i></span>:</td><td><input id="FileUpload7" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr>';
											if(m==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
												attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report</b> <span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Optional.Files supported:xls & xlsx)</i></span>:</td><td><input   id="FileUpload8" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr>';
											if(n==0 && loginrole!="User" && $("#SUB_STATUS").val()!="Completed")
												attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Emergency Change.Files supported:xls & xlsx)</i> </span>:</td><td><input id="FileUpload9" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr>';									
										}
									}
									if((attachmentHtmlForEmer!="") && (k!=0) && (l!=0) && (m!=0) && (n!=0)){
										attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm4"  id="editattachmentForm4" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID3" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME3" name="FUNCTION_NAME" value="'+data[i-1].function_NAME+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID3" name="CATEGORY_ID" value="'+data[i-1].category_ID+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE3" name="WORKFLOW_STATE" value="'+data[i-1].workflow_STATE+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID3" name="SUBCATEGORY_ID" value="'+data[i-1].subcategory_ID+'" ></input></td>';
										attachmentHtml +=attachmentHtmlForEmer;
										attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
									}
									if((attachmentHtmlForEmer!="") && (k==0 || l==0 || m==0 || n==0)){										
										attachmentHtml +=attachmentHtmlForEmer;										
									}
									
									if((k==0 || l==0 || m==0 || n==0) && (loginrole=="IS Executive") || (loginrole=="IS Manager"))
										attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';	
									else{
										if((attachFlag)&&(isExEmployee=="1")&&(loginrole=="HR Executive"||loginrole=="HR Manager" ))
											attachmentHtml +='<tr width="420px" style="color:red; font-weight:bold"><td colspan="3" style="font-style:italic;">(Please select the attachments which have to be sent via email.)</td></tr><tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';
										else
											attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment3" value="Update"></input></td></tr></table></form>';

									  }
							 }
							 
					 }	
					
					 $('#Attachment-Div').html(attachmentHtml);					 
				 }
				  //modified by 720307
					 if(((statusName=="Waiting For User Response")&&(loginrole=="User"))||((statusName=="Need More Info")&&(loginrole=="User"))){
							 if( problemAttachFlag==false)
								{    
								 
									attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_PROBLEM_FILE" id="REFERENCE_ID1" name="REF_ID_PROBLEM_FILE" value="5" ></input></td><td><b>Problem  </b></td><td><input  id="FileUpload1" path="approvalproblemfile" name="approvalproblemfile" type="file"/></input></td></tr>';
								}
							 if(problemDescAttachFlag==false)
								 {      
										attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="84" ></input></td><td><b>Problem Description </b></td><td><input  id="FileUpload2" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file"/></input></td></tr>';
								 }
							
									    attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>';
							 
							
					 } 
			          if((statusName=="Resolved/Closed" || statusName=="Re-Open")&&(loginrole=="User")){
			        	     if(reOpenDescAttachFlag==false){
								attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_RE_OPEN_DESC_FILE" id="REFERENCE_ID3" name="REF_ID_APP_RE_OPEN_DESC_FILE" value="82" ></input></td><td><b>Re-Open Description </b></td><td><input  id="FileUpload3" path="reopendescriptionfile" name="reopendescriptionfile" type="file"/></input></td></tr>';
			        	        }
			        	     if( problemAttachFlag==false)
								{    
								 
									attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_PROBLEM_FILE" id="REFERENCE_ID1" name="REF_ID_PROBLEM_FILE" value="5" ></input></td><td><b>Problem  </b></td><td><input  id="FileUpload1" path="approvalproblemfile" name="approvalproblemfile" type="file"/></input></td></tr>';
								}
							 if(problemDescAttachFlag==false)
								 {      
										attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="84" ></input></td><td><b>Problem Description </b></td><td><input  id="FileUpload2" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file"/></input></td></tr>';
								 }
			        	     attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>'; 
			          }
			          if(reasonforApprove_Reject==false){
							if(((statusName=="Waiting For IT Function Head Approval")&&(loginrole=="IT Function Head"))||((statusName=="Waiting For IS Head Approval")&&(loginrole=="IS Head"))||((statusName=="Need More Info")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((statusName=="Information Provided")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((statusName=="Waiting For Infosec Approval")&&(loginrole=="IT Security"))||((statusName=="Waiting For RO Approval")&&(userid==managerId))||((statusName=="Waiting For SLM Approval")&&(loginrole=="S/W License Mgr"))){
					        		    attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APPROVE_REJECT_DESC_FILE" id="REFERENCE_ID1" name="REF_ID_APPROVE_REJECT_DESC_FILE" value="83" ></input></td><td><b>Reason for Approve/Reject </b></td><td><input  id="FileUpload3" path="Approve_Reject_Description_File" name="Approve_Reject_Description_File" type="file"/></input></td></tr>';
										attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>';
					           }
			         }
					 attachmentHtml +='</table></form>';
					 $('#Attachment-Div').html(attachmentHtml);
				 
			   }		
		else{
			var subcategoryid=$('#SUB_CATEGORY_ID').val();
			var functionid=$('#FUNCTION_ID').find("option:selected").text();
			var categoryid=$('#CATEGORY_ID').val();
			var workflowstate=$('#WORKFLOW_STATE').val();
			if((loginrole=="Level 0 Executive"||loginrole=="Level 0 Manager"||loginrole=="Manager"||loginrole=="Executive"||loginrole=="IS Executive"||loginrole=="IS Manager" ||loginrole=="Finance Executive"||loginrole=="Finance Manager"||loginrole=="Admin Executive"||loginrole=="Admin Manager"||loginrole=="HR Executive"||loginrole=="HR Manager"||loginrole=="SAP Manager"))				 
			 {
				if(workflowstate=="Waiting For RO Approval")
				{
					attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm1"  id="editattachmentForm1" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID4" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME4" name="FUNCTION_NAME" value="'+functionid+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID4" name="CATEGORY_ID" value="'+categoryid+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE4" name="WORKFLOW_STATE" value="'+workflowstate+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID4" name="SUBCATEGORY_ID" value="'+subcategoryid+'" ></input></td><td    style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID4" name="REFERENCE_ID" value="1" ></input></td><td><b>Reporting Officer :</b></td><td><input   id="FileUpload4" path="approvalfile" name="approvalfile" type="file"/></input></td><td><input   type="submit" id="saveForAttachment4" value="Update"></input></td></tr></table></form>';
				}else
				{
					if(j==0 &&(workflowstate!="Closed(By User)"&&workflowstate!="Closed(By System)"&&workflowstate!="Resolved/Closed"))
					 {
						attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="NO" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID1" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME1" name="FUNCTION_NAME" value="'+functionid+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID1" name="CATEGORY_ID" value="'+categoryid+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE1" name="WORKFLOW_STATE" value="'+workflowstate+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID1" name="SUBCATEGORY_ID" value="'+subcategoryid+'" ></input></td>';
						attachmentHtml +='<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="7" ></input></td><td><b>Attachment 1:</b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
						if((workflowstate=="Assigned") || (workflowstate=="Work In Progress") || (workflowstate=="Re-Assigned")){
							if((loginrole=="IS Executive") || (loginrole=="IS Manager")){
								attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_APP_FILE" id="REFERENCE_ID6" name="REF_ID_APP_FILE" value="75" ></input></td><td><b><span style="color:red">*</span>Approval Mail</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Files supported:msg)</span></i>:</td><td><input   id="FileUpload6" path="IS_HEAD_APP_FILE" name="IS_HEAD_APP_FILE" type="file"/></input></td></tr>';
								attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_SCRIPT_FILE" id="REFERENCE_ID7" name="REF_ID_SCRIPT_FILE" value="76" ></input></td><td><b>DB Scripts</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Data Change.Files supported:txt)</span></i>:</td><td><input   id="FileUpload7" path="SCRIPT_FILE" name="SCRIPT_FILE" type="file"/></input></td></tr>';
								attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_UAT_REPORT" id="REFERENCE_ID8" name="REF_ID_UAT_REPORT" value="78" ></input></td><td><b>UAT Report</b> <span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(Optional.Files supported:xls & xlsx)</i></span>:</td><td><input   id="FileUpload8" path="UAT_REPORT_FILE" name="UAT_REPORT_FILE" type="file"/></input></td></tr>';
								attachmentHtml += '<tr><td style="display:none"><input path="REF_ID_TEST_REPORT" id="REFERENCE_ID9" name="REF_ID_TEST_REPORT" value="77" ></input></td><td><b>Test Report</b><span style="font-family:Verdana,Geneva,sans-serif;font-size:12px;"><i>(mandatory for Emergency Change.Files supported:xls & xlsx)</i> </span>:</td><td><input   id="FileUpload9" path="TEST_REPORT_FILE" name="TEST_REPORT_FILE" type="file"/></input></td></tr>';					
							}
						}
						attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr></table></form>';
					 }
					
				}
				$('#Attachment-Div').html(attachmentHtml);
			 }
			//modified by 720307
			else
			{ 
		    
				if(((workflowstate=="Waiting For User Response")&&(loginrole=="User"))||((workflowstate=="Need More Info")&&(loginrole=="User")))
			    {
					attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME" name="FUNCTION_NAME" value="'+functionid+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID" name="CATEGORY_ID" value="'+categoryid+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE" name="WORKFLOW_STATE" value="'+workflowstate+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID" name="SUBCATEGORY_ID" value="'+subcategoryid+'" ></input></td>';
					attachmentHtml +='<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="5" ></input></td><td><b>PROBLEM: </b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="84" ></input></td><td><b>Problem Description </b></td><td><input  id="FileUpload2" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>';
					attachmentHtml +='</table></form>';
					$('#Attachment-Div').html(attachmentHtml);
			    } 
				else if((workflowstate=="Resolved/Closed" || workflowstate=="Re-Open")&&(loginrole=="User")){
		    	    attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME" name="FUNCTION_NAME" value="'+functionid+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID" name="CATEGORY_ID" value="'+categoryid+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE" name="WORKFLOW_STATE" value="'+workflowstate+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID" name="SUBCATEGORY_ID" value="'+subcategoryid+'" ></input></td>';
					attachmentHtml +='<td style="display:none"><input path="REFERENCE_ID" id="REFERENCE_ID1" name="REFERENCE_ID" value="5" ></input></td><td><b>PROBLEM: </b></td><td><input  id="FileUpload1" path="approvalfile" name="approvalfile" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_PROBLEM_DESC_FILE" id="REFERENCE_ID2" name="REF_ID_APP_PROBLEM_DESC_FILE" value="84" ></input></td><td><b>Problem Description </b></td><td><input  id="FileUpload2" path="approvaldescriptionfile" name="approvaldescriptionfile" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APP_RE_OPEN_DESC_FILE" id="REFERENCE_ID3" name="REF_ID_APP_RE_OPEN_DESC_FILE" value="82" ></input></td><td><b>Re-Open Description </b></td><td><input  id="FileUpload3" path="reopendescriptionfile" name="reopendescriptionfile" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>';
					attachmentHtml +='</table></form>';
					$('#Attachment-Div').html(attachmentHtml);
		       }
				else if(((workflowstate=="Waiting For IT Function Head Approval")&&(loginrole=="IT Function Head"))||((workflowstate=="Waiting For IS Head Approval")&&(loginrole=="IS Head"))||((workflowstate=="Need More Info")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((workflowstate=="Information Provided")&&((loginrole=="IT Function Head")||(loginrole=="IT Security")||(loginrole=="S/W License Mgr")||(loginrole=="IS Head")))||((workflowstate=="Waiting For Infosec Approval")&&(loginrole=="IT Security"))||((workflowstate=="Waiting For RO Approval")&&(userid==managerId))||((workflowstate=="Waiting For SLM Approval")&&(loginrole=="S/W License Mgr"))){
					attachmentHtml += '<form action="attachmentlist.htm" path="editattachmentForm2"  id="editattachmentForm2" modelAttribute="AttachmentBean" method="post" enctype="multipart/form-data"><table border="0" cellspacing="5" cellpadding="5" width="80%"></br><tr><td  style="display:none" ><input path="GET_WORKFLOW_STATE" name="GET_WORKFLOW_STATE" id="GET_WORKFLOW_STATE" value="YES" ></input></td><td  style="display:none" ><input path="TICKET_ID" id="TICKET_ID" name="TICKET_ID" value="'+ticketID+'" ></input></td><td  style="display:none"><input path="FUNCTION_NAME" id="FUNCTION_NAME" name="FUNCTION_NAME" value="'+functionid+'" ></input></td><td  style="display:none"><input path="CATEGORY_ID" id="CATEGORY_ID" name="CATEGORY_ID" value="'+categoryid+'" ></input></td><td style="display:none"><input path="WORKFLOW_STATE" id="WORKFLOW_STATE" name="WORKFLOW_STATE" value="'+workflowstate+'" ></input></td><td style="display:none"><input path="SUBCATEGORY_ID" id="SUBCATEGORY_ID" name="SUBCATEGORY_ID" value="'+subcategoryid+'" ></input></td>';
					attachmentHtml +='<tr><td style="display:none"><input path="REF_ID_APPROVE_REJECT_DESC_FILE" id="REFERENCE_ID1" name="REF_ID_APPROVE_REJECT_DESC_FILE" value="83" ></input></td><td><b>Reason for Approve/Reject </b></td><td><input  id="FileUpload3" path="Approve_Reject_Description_File" name="Approve_Reject_Description_File" type="file"/></input></td></tr>';
					attachmentHtml +='<tr><td><input type="submit" id="saveForAttachment1" value="Update"></input></td></tr>';
					$('#Attachment-Div').html(attachmentHtml);
				}
				
				else
				    $("#Attachment-Div").html("<b>No Attachments.</b>")			
			}
		}
		
			 $('#editattachmentForm').ajaxForm(options); 
			 $('#editattachmentForm1').ajaxForm(options); 
			 $('#editattachmentForm2').ajaxForm(options);
			 $('#editattachmentForm3').ajaxForm(options);
			 $('#editattachmentForm4').ajaxForm(options);
			 $('#editattachmentForm6').ajaxForm(options);
			 $('#editattachmentForm7').ajaxForm(options);
			 $('#editattachmentForm8').ajaxForm(options);
			 $('#editattachmentForm9').ajaxForm(options);
			 
		}	 
    	unblockUI();

	});

}

var icount=0;

function approval(data,i)
{
	icount = i;
	if(data=='Replaced')
	{
		$('#FileUpload'+i).show();
		
	}
	else
	{
		$('#FileUpload'+i).hide();
	}
}

//DownloadAttachment.htm?attachmentname='+data[i].attachment_PATH+'
function downloadAttachment(path,name){
	var json = '{"ATTACHMENT_PATH":"'+escape(path.replace(/#/g,"*hash*"))+'","ATTACHMENT_NAME":"'+escape(name.replace(/#/g,"*hash*"))+'"}';//Added Escape Function to handle Escape Chars as part of L2 : 4200 by Nazeeb
		json = json.replace(/&/g,"*amper*");
		if(name.indexOf(".htm")!= -1)
		{
		var rndVal;
		var wOpen;
		var sOptions;

		sOptions = 'scrollbars=yes,toolbar=no,status=no,menubar=no';
	    sOptions = sOptions  + ',width=850';
	    sOptions = sOptions  + ',height=850' ;

		rndVal = parseInt((60000 * Math.random()) + 1);

		var link = "DownloadAttachment.htm?json="+json;
		wOpen = window.open('',rndVal,sOptions);
		wOpen.location = link;
	    wOpen.focus();
		wOpen.moveTo(50,50);
		}
		else{
		window.location.href ="DownloadAttachment.htm?json="+json;
		}	
}
function getAttachmentsForTCR(issueID) 
{	
	
	blockUI();
	$("#issueupdation").hide();
	$("#TechCR_Risk").hide();
	$("#TechCR_RCA").hide();   
	$("#Attachment-Div").show();
	$.ajaxSetup({ cache: false });
	$.getJSON("attachmentlistForTCR.htm",{issueNo:issueID},function(data){
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}else{
		var attachmentHtml = '<form action="attachmentlistForTCR.htm"  id="edittechCRattachmentForm" modelAttribute="AttachmentBeanForTCR" method="post" enctype="multipart/form-data">';
		attachmentHtml+='<table border="0" id="attachment_field" cellspacing="5" cellpadding="5" width="100%" style="border-bottom: 1px solid #d3d3d3;">';
		attachmentHtml +='<tr><td colspan=1><u><b>Attachment Type</u></b></td><td><b><u>Attachment Name</u></b></td></tr>';
		
		if(data.length!=0)
			{					
				 for(var i=0;i<data.length;i++)
				 {				
					 attachmentHtml+='<tr><td align="left"><form action="attachmentlistForTCR.htm"  id="edittechCRattachmentForm" modelAttribute="AttachmentBeanForTCR" method="post" enctype="multipart/form-data"><tr><td style="display:none"><input id="ISSUE_ID" name="ISSUE_ID" value="'+data[i].issue_ID+'" ></input></td><td style="display:none"><input name="WORKFLOW_STATE" value="'+$("#WORKFLOW_STATE").val()+'" ></input></td>';
					 if(data[i].attachment_NAME != null){
					 attachmentHtml += '<td align="left"><b>Attachment : </b></td><td align="left"><a href="#" id="showAttachInfo" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'<td ><input id="replaceAttachment" name="ATTACHMENT" type="file" style="display:none" /></input></td><td align="left"><select id="removeAttachment" name="removeAttachment" disabled="disabled" style="width:130px;" onchange="javascript:replaceAttachmentforTechCR(this.value)"><option value="--Please Select--">--Please Select--</option><option value="Replace">Replace</option><option value="Remove">Remove</option></select></td><td><input type="button" id="EditAttachment" value="Edit" onclick="javascript:lockAttachment()"/>&nbsp;&nbsp;<input disabled="disabled" type="submit" id="saveForAttachment" value="Update"></input></td></tr>';
					 }
					 if(data[i].approver_ATTACHMENT_NAME != null){						 
						 attachmentHtml += '<tr><td align="left"><b>Approver Attachment : </b></td><td align="left"><a href="#" id="showApprovalAttachInfo" onclick="downloadAttachment(\''+data[i].approver_ATTACHMENT_PATH+'\',\''+data[i].approver_ATTACHMENT_NAME+'\')">'+data[i].approver_ATTACHMENT_NAME+'</td></tr>'; 
					 }
					 attachmentHtml +='</td></tr></form></table>';
					 $('#Attachment-Div').html(attachmentHtml);	
				 }				
			}else{	
				attachmentHtml += '<tr><td style="display:none"><input id="ISSUE_ID" name="ISSUE_ID" value="'+issueID+'" ></input></td><td style="display:none"><input  name="WORKFLOW_STATE" value="'+$("#WORKFLOW_STATE").val()+'" ></input></td><td><b>Attachment : </b></td><td><input disabled="disabled" id="replaceAttachment" name="ATTACHMENT" type="file"/></input></td><td><input type="button" id="EditAttachment" value="Edit" onclick="javascript:lockAttachment()"/>&nbsp;&nbsp;<input disabled="disabled" type="submit" id="saveForAttachment" value="Update"></input></td></tr></form>';						 
				attachmentHtml += '</table>';
				$('#Attachment-Div').html(attachmentHtml);		
			}	
			
			if($("#editPermissionRenderingFlag").val()=="true"){								
				$("#saveForAttachment").show();
				$("#EditAttachment").show();
			}else{
				$("#saveForAttachment").hide();
				$("#EditAttachment").hide();
			}	
			 $('#edittechCRattachmentForm').ajaxForm(techCRoptions); 
			 
		}
		unblockUI();
	});
}
var options = { 
        target:        '#output1',   // target element(s) to be updated with server response 
        beforeSubmit:  showRequest,  // pre-submit callback 
        success:       showResponse,  // post-submit callback 
        dataType:  'json',
        type:'post'
        // other available options: 
        //url:       url         // override for form's 'action' attribute 
        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
        //clearForm: true        // clear all form fields after successful submit 
        //resetForm: true        // reset the form after successful submit 
 
        // $.ajax options can be used here too, for example: 
        //timeout:   3000 
    }; 
 
var techCRoptions = { 
        target:        '#output1',   // target element(s) to be updated with server response 
        beforeSubmit:  showRequestforTechCR,  // pre-submit callback 
        success:       showResponseforTechCR,  // post-submit callback 
        dataType:  'json',
        type:'post'
        // other available options: 
        //url:       url         // override for form's 'action' attribute 
        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
        //clearForm: true        // clear all form fields after successful submit 
        //resetForm: true        // reset the form after successful submit 
 
        // $.ajax options can be used here too, for example: 
        //timeout:   3000 
    }; 
    // bind form using 'ajaxForm' 
   // $('#editattachmentForm').ajaxForm(options); 
 
//pre-submit callback 
function showRequest(formData, jqForm, options) { 	
  var queryString = $.param(formData);   
  var changesMadeVerify=new Array();  
   for(var r=0;r<formData.length;r++){	
	    //modified by 720307
		if(formData[r].name=="approvalfile" || formData[r].name=="APPROVER" || formData[r].name=="status" || formData[r].name=="IS_HEAD_APP_FILE" || formData[r].name=="SCRIPT_FILE" || formData[r].name=="UAT_REPORT_FILE" || formData[r].name=="TEST_REPORT_FILE"||formData[r].name=="approvaldescriptionfile"||formData[r].name=="reopendescriptionfile"||formData[r].name=="Approve_Reject_Description_File"||formData[r].name=="approvalproblemfile"||formData[r].name=="attachmentfile1"||formData[r].name=="attachmentfile2"||formData[r].name=="attachmentfile3"||formData[r].name=="attachmentCheckbox1"||formData[r].name=="attachmentCheckbox2"||formData[r].name=="attachmentCheckbox3"||formData[r].name!="attachmentCheckbox1"||formData[r].name!="attachmentCheckbox2"||formData[r].name!="attachmentCheckbox3"){			
			
			if(formData[r].value!="" || formData[r].value.length > 0)
				changesMadeVerify.push(formData[r].value);
		}		
   }
   if(changesMadeVerify.length<=0){
		parent.jbarOnFailure("No Changes Made");
		return false;
   }
   // alert('About to submit: \n\n' + queryString);
   /*var matchPos_TestRep =queryString.substring(queryString.lastIndexOf("&TEST_REPORT_FILE=")+20,queryString.length);
   var matchPosApp =queryString.search("&approvalfile=");
   var matchPosIS_HeadApp =queryString.search("&IS_HEAD_APP_FILE=&");     
   if((matchPosIS_HeadApp == -1) && (matchPos_TestRep.length == 0)){	  
	    parent.jbarOnFailure("Kindly attach the Test Report");
	   return false;
   }
   if((matchPosIS_HeadApp != -1) && (matchPos_TestRep.length > 0)){	  
	    parent.jbarOnFailure("Kindly attach the IS Head Approval Mail");	   
	    return false;
   }     */
} 
// post-submit callback 
function showResponse(response, statusText, xhr, $form)  { 	
	if (response.error) {		
		jQuery.each(response, function(key, val) {				
		parent.jbarOnFailure(response.problemfile+" for "+response.msg);
		});		
	}else if(response.status=="success"){
			getAttachments($("#TICKET_ID").val()); 
			$('#FileUpload'+icount).hideAll();
			parent.jbarOnSuccess('Attached Successfully.');			
	} else if(response.status=="No Change"){
		parent.jbarOnFailure("No Changes Made");
	}
} 

function validateBeforeSave(approvalfile)
{
	var validationstatus = true;
	if((approvalfile.length) >0){		
		approvalfile = approvalfile.substring(approvalfile.lastIndexOf("\\")+1, approvalfile.lastIndexOf("."));
		if(approvalfile.length >150){
			$('#FileUpload'+icount).focus();
			$('#FileUpload'+icount).showerrormessage({message:"* File name should not exceed more than 150 characters"});
			validationstatus =  false;
		}
	}			
	return validationstatus;
}	
/********Added for Emergency L1**********/
function replaceEmerAttachmentfun(replaceAttachment,fileId,buttonId)
{				
	if(replaceAttachment == "Replace"){		
		$('#'+fileId+'').removeAttr("disabled");	
		//$('#'+buttonId+'').removeAttr("disabled");
	}else{		
		$('#'+fileId+'').attr('disabled','true');
		//$('#'+buttonId+'').attr('disabled','true');						
	}	
}
/****************************************/
function validateTechCRBeforeSave(fileName)
{
	var validationstatus = true;		
	if((fileName.length) >0){
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));		
		if(fileName.length >150){			
			var element='replaceAttachment';
			$('#TextFieldSpan'+element+'').text('');		
			$('#'+element+'').after('<span class="error" id="TextFieldSpan'+element+'" >'+'File name should not exceed more than 150 characters'+''+'</span>');			
			validationstatus =  false;
		}else{		
			var element='replaceAttachment';
			$('#TextFieldSpan'+element+'').text('');	
		}
	}else{		
		var element='replaceAttachment';
		$('#TextFieldSpan'+element+'').text('');	
	}
	if((fileName.length==0)&&($("#removeAttachment").val()!="Remove")){
		validationstatus =  false;
	}
	return validationstatus;
}	
function replaceAttachmentforTechCR(removeAttachment)
{	
	if(removeAttachment == "Replace"){		
		$("#replaceAttachment").show();		
	}else{		
		$("#replaceAttachment").hide();		
		var element='replaceAttachment';
		$('#TextFieldSpan'+element+'').text('');			
	}	
}

function showRequestforTechCR(formData, jqForm, techCRoptions) {	
   return validateTechCRBeforeSave($("#replaceAttachment").val());   
} 
// post-submit callback 
function showResponseforTechCR(response, statusText, xhr, $form)  { 	
	if (response.error) {		
		jQuery.each(response, function(key, val) {				
			var element='replaceAttachment';
		$('#TextFieldSpan'+element+'').text('');		
		$('#'+element+'').after('<span class="error" id="TextFieldSpan'+element+'" >'+response.problemfile+''+'</span>');	
		});		
	} else if(response.exception){
		 parent.jbarOnFailure(response.message);		
	 }else {		
		var element='replaceAttachment';
		$('#TextFieldSpan'+element+'').text('');		
		getAttachmentsForTCR($("#ISSUE_ID").val());		
		if($("#removeAttachment").val()=='Remove')
			parent.jbarOnSuccess('Removed Successfully.');		
		else
			parent.jbarOnSuccess('Attached Successfully.');						
	}	
} 
function lockAttachment(){
	
	var jsonString = '{';
            jsonString +='"ISSUE_ID":"'+$("#ISSUE_ID").val()+'","DATA":"';
      var xmldata = "<data>";
	  xmldata+="<"+'ATTACHMENT'+">"+$("#replaceAttachment").val()+"</"+'ATTACHMENT'+">";
	   xmldata+="<"+'WORKFLOW_STATE'+">"+$("#WORKFLOW_STATE").val()+"</"+'WORKFLOW_STATE'+">";
	  xmldata+= "</data>";
	  jsonString = jsonString+xmldata+'","MENU_ID":"2"}';    
	  $.ajaxSetup({ cache: false });
	  $.getJSON('checkIfRecordExists.htm',{jsonString : jsonString},function(data) {
		  if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data.message);
			}else{
			 if (data.status && data.length != 0) {
					$("#removeAttachment").removeAttr("disabled");
					$("#saveForAttachment").removeAttr("disabled");
					$("#replaceAttachment").removeAttr("disabled");
			 }else{
					if(data.LoginId!=data.LOCKED_BY){
						parent.jbarOnFailure("The Tech-CR Id is locked by "+data.NAME+"( "+data.LOCKED_BY+" )");
					}else{
						$("#removeAttachment").removeAttr("disabled");
						$("#saveForAttachment").removeAttr("disabled");
						$("#replaceAttachment").removeAttr("disabled");
					}
			 	}
			}
	  });
}

function getAttachmentsForMasterTicket(id){
	if(inEditMode){
		if(confirm("Do you want to continue with out updating the ticket?")){
	    	inEditMode = false;
		  	//unlockTheTicket($("#TICKET_ID").val(),1);
	      }else{
				return false;
			}
	}
	
	$("#hdEditMain ul li a").removeClass("selected");
	$("#hdEditMain ul li a").eq("1").addClass("selected");
	//blockUI();
	$.ajaxSetup({ cache: false });
	$.getJSON("attachmentMasterlist.htm",{ticketNo:id},function(data){
		$("#Audit-Log").hide();
		$("#editmasterticket").hide();
		$("#Attachment-Div").show();	
		$('#Linked-Tickets').hide();
		var attachmentHtml = "";
		attachmentHtml +='<tr><th width="43%"><u> Attachment Type </u></th><th><u>Attachment Name</u></th></tr>';
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
	}
	else{
	if(data.length!=0)
		{	
		 for( i=0;i<data.length;i++)
		 {

			 attachmentHtml += '<tr><td><b>Attachment '+(i+1)+'  : </b></td><td><a href="#" id="showAttachInfo'+i+'" onclick="downloadAttachment(\''+data[i].attachment_PATH+'\',\''+data[i].attachment_NAME+'\')">'+data[i].attachment_NAME+'</a></td>'+'</tr>';
			
		 } 
		}
	$('#Attachment-Div').html(attachmentHtml);

	
}
	});
}