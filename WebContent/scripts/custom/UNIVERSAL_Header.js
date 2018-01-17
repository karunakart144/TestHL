var confirmBoxRequired = true;
$(document).ready(function () {
		myLayout = $('body').layout({
		});
		 $(".signin").click(function(e) {
                e.preventDefault();
                $("fieldset#signin_menu").toggle();
                $(".signin").toggleClass("menu-open");
            });

            $("fieldset#signin_menu").mouseup(function() {
                return false
            });
            $(document).mouseup(function(e) {
                if($(e.target).parent("a.signin").length==0) {
                    $(".signin").removeClass("menu-open");
                    $("fieldset#signin_menu").hide();
                }
            });    
            $('#userRoleId').change(function() {

            	 var centerhref = "";
	       		 try{centerhref = centeriframe.location.href;}
	       		 catch (e){}
            	 if(centerhref.indexOf("HELPDESK_Edit.htm") != -1 && window.frames["centeriframe"].inEditMode && confirmBoxRequired){ 
            		 if(confirm("Do you want to continue with out updating the ticket?")){
            			 var detailTicketId = window.frames["centeriframe"].document.getElementById("TICKET_ID").value;
            			 releaseTicket(detailTicketId,1);
            		 }else{
            			 	confirmBoxRequired = false;
            			 	//$("#userRoleId").val(loggedinroleID); 
            			 	$("fieldset#signin_menu").hide();
            			 	$("#userRoleId option[value='"+loggedinroleID+"']").attr("selected",true);
            			 	confirmBoxRequired  = true;
            				return false;
            			}
            	  }
					var getmailId="";
					if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1){
							getmailId =  window.frames["centeriframe"].document.getElementById("mailid").value;
					}
					if(centerhref.indexOf("convertMailtoHelpdesk.htm") != -1 && getmailId != '' && confirmBoxRequired){
						 if(confirm("Do you want to continue with out creating the ticket?")){
								var mailId = window.frames["centeriframe"].document.getElementById("mailid").value;
								releaseTicket(mailId,4);
						}else{
            			 	confirmBoxRequired = false;
            			 	//$("#userRoleId").val(loggedinroleID); 
            			 	$("fieldset#signin_menu").hide();
            			 	$("#userRoleId option[value='"+loggedinroleID+"']").attr("selected",true);
            			 	confirmBoxRequired  = true;
            				return false;
            			}
            	  }
            	 
				 var roleId = $(this).val();
				 var roleName = $("#userRoleId option:selected").text();
					window.location.href="RoleChanged.htm?roleId="+roleId+"&roleName="+roleName;
      		});
		
 	});

