$(document).ready(function() {
	$.ajaxSetup({ cache: false });
	$('#loginId').focus();
	$('#roleSelectionTable').hide();
	$('#bulletinBoard').hide();
		$('#stcikyDivId').hide();
	$.validator.methods.myCustomValidation = function(value, element, param) {
	    // Perform custom validation and return true or false
		var loginstatus = true;
		if(value.indexOf("<")!=-1){
			loginstatus = false;
		}else if(value.indexOf("&")!=-1){
			loginstatus = false;
		}else if(value.indexOf(">")!=-1){
			loginstatus = false;
		}else if(value.indexOf('"')!=-1){
			loginstatus = false;
		}else if(value.indexOf("'")!=-1){
			loginstatus = false;
		}else if(value.indexOf("=")!=-1){
			loginstatus = false;
		}else if(value.indexOf("~")!=-1){
			loginstatus = false;
		}
	    return loginstatus;
	};
	$("#login").validate( {
		rules : {
			loginId : {
				required : true,
				myCustomValidation: true 
			},
			password : {
				required : true
			}
		},
		messages : {
			loginId : {
				required : "Required !",
				myCustomValidation : "&, <, >, \",',= and ~ characters are not allowed"
			},
			password : {
				required : "Required !"
			}

		}
	});

	var roleSize = $('#roleListSize').val();
	if (roleSize > 1) {

		$("#loginTable").hide();
		$("#lockImageTable").hide();
		$('#roleSelectionTable').show();
		$('#userGuideDivID').hide();
		
		//$("#roleName").show();
		$('#banner').hide();
	}
		$('#bulletinBoard').hide();
		$.getJSON('bulletinMessage.htm', {
		}, function(data) {
			if (data.length != 0) {
				$('#bulletinBoard').show();
				var bulltinHtml="";
				bulltinHtml=bulltinHtml+"<div id='bulletinBoard'><center><font color='white'>Bulletin Board</font></center></div>";
				bulltinHtml=bulltinHtml+"<marquee behavior='scroll' direction='up' scrollamount='3' onmouseover='this.stop();' onmouseout='this.start();'  style='width:100%;'>";
				var options = [];
				$.each(data, function(item) {
					bulltinHtml+="<h4><u><center>"+data[item].BULLETIN_HEADER+"</center></u></h4><span><center>"+data[item].BULLETIN_DESCRIPTION+"</center><br></span>";
				});
				bulltinHtml+="</marquee>";
				$('#bulletinBoard').show();
				$('#stcikyDivId').show();
				$('#stcikyDivId').html(bulltinHtml);
			}
			
	});

});

function continueLogin(){
	document.form.submit();
	$("#login1").attr("disabled","disabled");
}
