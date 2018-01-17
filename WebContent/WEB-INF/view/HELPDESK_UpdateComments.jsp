<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IConnect : Unified Service Desk for Life & Health</title>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body onload="insertHTML()">
<% String token = null; %>
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){  
		$('textarea[maxlength],input[maxlength]').live('keyup blur', function() {
		    // Store the maxlength and value of the field.
		    var maxlength = $(this).attr('maxlength');
		    var val = $(this).val();
		
		    // Trim the field if it has content over the maxlength.
		    if (val.length > maxlength) {
		        $(this).val(val.slice(0, maxlength));
		    }
		});
	});
	function insertHTML(){
		$("#HTML").html(window.opener.document.getElementById("commentsHTML").innerHTML);
	}
	function addComments(objectid){
		var value=jQuery.trim($("#ENTRY_COMMENTS").val());
		if(value==""){
			$("#mandcommentstr").show();
		}
		else if(checkspecialcharacters($("#ENTRY_COMMENTS").val())){			
			$("#specialcharmsg").show();			
		}
		else{			
			window.opener.saveApprorRejectComments(objectid,value);
			window.close();
		}
	}
	
	function checkspecialcharacters(value){
		var checkstatus = false;
		if(value.indexOf("<")!=-1){
			checkstatus = true;
		}else if(value.indexOf("&")!=-1){
			checkstatus = true;
		}else if(value.indexOf(">")!=-1){
			checkstatus = true;
		}else if(value.indexOf('"')!=-1){
			checkstatus = true;
		}else if(value.indexOf("=")!=-1){
			checkstatus = true;
		}else if(value.indexOf("~")!=-1){
			checkstatus = true;
		}else if(value.indexOf("\\u")!=-1){
			checkstatus = true;
		}
	    return checkstatus;
	}
</script>
<div id="HTML">
</div>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>