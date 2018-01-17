<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=cssDirPath%>/pagination.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/search.js"></script>
<link href="<%=cssDirPath%>/styles.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
var context = "${pageContext.request.contextPath}";
loc = String(window.location);
var urlAppl = loc.substr(0,loc.indexOf(context));
$(document).ready(function(){
	if("${sessionScope.typeOfLink}" == "userSolution")
	{
	   		$("#docDiv").hide();
    		$("#contentDiv").hide();
    		$("#userSolutionDiv").show();
    		getTagDetails();
    }else if("${sessionScope.typeOfLink}" == "document")
    {
 		$("#contentDiv").hide();
		$("#userSolutionDiv").hide();
		$("#docDiv").show();
		
    }else if("${sessionScope.typeOfLink}" == "ticket")
    {
   		$("#docDiv").hide();
		$("#userSolutionDiv").hide();
		$("#contentDiv").show();
        getTicketDetails();
        
    }
});

</script>
</head>
<body>
			   
</body>


<body>
<% String token = null; %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"><!--
  <tr>
    <td colspan="3" align="center" valign="top" class="headerLine" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="header_bg">
      <tr>
        <td width="15%" align="left" valign="middle"><img src="images/iConnect_Logo.png" width="205" height="60" /></td>
        <td width="70%" align="center"><img src="images/header.png" /></td>
        <td width="15%" align="right" valign="middle"><img src="images/Capgemini_Search_Logo.png" width="150" height="73" /></td>
      </tr>
    </table></td>
  </tr>
  --><tr>
    <td width="62%" align="center" valign="top">
<form:form action="">	
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0"><!--
        <tr>
        <td align="right"> <a href="goSearchHome.htm?backToSrch=true"> Back to search</a> </td>
        </tr>
      --><tr>
        <td height="102" align="left" valign="top">
<div id="docDiv">
<iframe id="docPlaceHolder" src="DownloadAttachmentForSU.htm?link="+${sessionScope.docLink}" scrolling="no" style="overflow: hidden;" frameborder="0">
</iframe>
</div>
<div id="userSolutionDiv">
<table class="detailsTable" cellpadding="4px" cellspacing="4px">
<tr><td><b>Solution provided by : <span id="userName"></span></b></td></tr>
<tr><td id="userSolution"></td></tr>
</table>
</div>
<div id="contentDiv">
<table class="detailsTable" cellpadding="4px" cellspacing="4px"><tr><td><b> Category : </b> </td><td> <span id="detailsCat"></span> </td></tr>
		                    <tr><td><b> Place: </b> </td><td id="detailsPlace"></td></tr> 
		                    <tr><td><b> City  : </b></td><td id="detailsCity"> </td></tr>
		                    <tr><td><b> Subject  : </b></td><td id="detailsSub"></td></tr> 
		                    <tr><td><b> Description  : </b> </td><td id="detailsDes"></td></tr>
		                    <tr><td><b> Resolution  : </b> </td><td id="detailsRes"></td></tr>
		                    </table>
</div>

	<div id=userInfo style="margin: 20px;">  <br></br> 
  		<div id="userVote">
  			<img id="likeImg" src=images/searchAI/up.png onclick="updateLikeCount();"></img> 
			(<span id=likeCount></span>) &nbsp;&nbsp; 
			<img id="disLikeImg" src=images/searchAI/down.png onclick="updateDisLikeCount();"></img> 
			 (<span id=dislikeCount></span>)
			 <br></br> 
	    </div>
	    <div id="userCommentContainer" >
	    	<div id="userCommentHolder"></div>
	    	<div  >
	    	<div style="margin-top: 10px; ">
	    		<textarea rows="5" cols="40" id="userComment"></textarea>
	    	</div>
	    	<div style="margin-top: 10px; margin-left: 30px; " align="left">
	    	 <input type="button" value="Post Your Comment" onclick="postComments()">
	    	</div>
	    	</div>
	    </div>
	</div>

        </td>
        </tr>
    </table>
      </form:form>  
      <% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
    </td>
    <!--<td width="20%" align="left" valign="top" style="border-left: 1px dashed #dedede;">
    --><div id="div_name" style="display: none; position: absolute; float: right; z-index: 9999; background-color: #FAFAFA; border: 2px solid #e6e6e6; padding: 10px; text-align: left; margin: 0px 50px 0px 10px; width: 200px; vertical-align: middle; margin-top: 303px;">

	</div>
    <table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td></td>
      </tr>
    </table></td>
  </tr>  
</table>
</body>
</html>