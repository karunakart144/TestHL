$(document).ready(function() {
		//Added by Sali
$('#bulletinBoardDivId').hide();
		$.getJSON('bulletinMessage.htm', {
		}, function(data) {
			if (data.length != 0) {
				$('#bulletinBoardDivId').show();
				var bulltinHtml="";
				bulltinHtml=bulltinHtml+"<div id='bulletinBoard'><center><font color='white'>Bulletin Board</font></center></div>";
				bulltinHtml=bulltinHtml+"<marquee behavior='scroll' direction='up' scrollamount='3' onmouseover='this.stop();' onmouseout='this.start();' style='width:100%;'>";
				var options = [];
				$.each(data, function(item) {
					bulltinHtml+="<h4><u><center>"+data[item].BULLETIN_HEADER+"</center></u></h4><span><center>"+data[item].BULLETIN_DESCRIPTION+"</center><br></span>";
				});
				bulltinHtml+="</marquee>";
				
				$('#stcikyDivId').html(bulltinHtml);
			}
			
	});
//end
		/*******Added by Poovamma(716302) for Role Change in User Page*********/
		$(".signin").click(function (e) {
		    e.preventDefault();
		    $("fieldset#signin_menu").toggle();
		    $(".signin").toggleClass("menu-open");
		});

		$("fieldset#signin_menu").mouseup(function () {
		    return false
		});
		$(document).mouseup(function (e) {
		    if ($(e.target).parent("a.signin").length == 0) {
		        $(".signin").removeClass("menu-open");
		        $("fieldset#signin_menu").hide();
		    }
		});
		$('#userRoleId').change(function () {
		    var roleId = $(this).val();
		    var roleName = $("#userRoleId option:selected").text();
		    window.location.href = "RoleChanged.htm?roleId=" + roleId + "&roleName=" + roleName;
		});
		/*******End Of Added by Poovamma(716302) for Role Change in User Page*********/ 
//end
		//Added for Unified Self Service SSO
		if (ssoFlag != null) {				
			if(ssoFlag=="create"){	
			
			
				window.location.href ="UserHome.htm";
			}
			if(ssoFlag=="view"){
			
				window.location.href ="UserHome.htm";
			}
	 	 		
		}
});

