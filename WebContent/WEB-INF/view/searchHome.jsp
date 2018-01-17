<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.igate.iconnect.BO.COMMON_Menu" %>
<%@ page import="com.igate.iconnect.BO.WORKFLOW_Role" %>
<%@ page import="com.igate.iconnect.BO.User" %>
<%@ page import="java.util.List" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<%  String empId = (String) request.getSession().getAttribute(
"userLoginId");
User userObj = (User) request.getSession().getAttribute(
        empId);
String empName = userObj.getUserName();
String location = userObj.getLocationCity();
String currentUserRole = userObj.getUserRole();
List<WORKFLOW_Role> userRoleList = userObj.getUserRoleList();
int gradeLevel=0;
if(userObj.getWorkSpacePlanning_Grade_Level()!=null){
	gradeLevel= Integer.parseInt(userObj.getWorkSpacePlanning_Grade_Level());
}
long currentUserRoleID = 0;
String SSOFlag ="NA";
int gradeAccess=userObj.getWorkSpacePlanning_Access();
//ResourceBundle bundle = ResourceBundle.getBundle("smartsearch");
//String iConnectURL=bundle.getString("iConnectURL");
String schema = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		String iConnectURL = schema + "://" + serverName + ":" + serverPort + contextPath;
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IConnect : Unified Service Desk for Life & Health - Search</title>
<link href="<%=cssDirPath%>/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=cssDirPath%>/pagination.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/jbar_style.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/ui.base.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/ui.theme.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/jquery.autocomplete.css" rel="stylesheet" />

<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.bar.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.dotdotdot.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.session.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/search.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/userSolution.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/User.js"></script>
<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
var context = "${pageContext.request.contextPath}";
loc = String(window.location);
var urlAppl = loc.substr(0,loc.indexOf(context));
var home = '<%=iConnectURL%>';
var gradeLevel=<%=gradeLevel%>;
var gradeAccess=<%=gradeAccess%>;


var usrLocation = "${sessionScope.location}";
var subject = "";
var tagList = null ;
var availableTags = new Array();
//Added for Role Change in search page
$(".signin").click(function (e) {
    e.preventDefault();
    $("fieldset#signin_menu").toggle();
    $(".signin").toggleClass("menu-open");
});
var ssoFlag ="<%=SSOFlag%>";
var srchTxtVar="${sessionScope.srchTxt}";
//End Added for Role Change in search page
$(document).ready(function(){

if(parseInt(gradeLevel,10)>=8 && parseInt(gradeAccess,10)>0){
	
	$("#WorkspacePlanningLi").attr('style','display:block');
	$("#WorkspacePlanning").show();

	
}
$.getJSON('bulletinMessage.htm', {
		}, function(data) {
			if (data.length != 0) {
				var bulltinHtml="";
				bulltinHtml=bulltinHtml+"<div id='bulletinBoard'><center><strong>Bulletin Board</strong></center></div>";
				bulltinHtml=bulltinHtml+"<marquee behavior='scroll' direction='up' scrollamount='3' onmouseover='this.stop();' onmouseout='this.start();' style='width:100%;'>";
				var options = [];
				$.each(data, function(item) {
					bulltinHtml+="<h4><u><center>"+data[item].BULLETIN_HEADER+"</center></u></h4><span><center>"+data[item].BULLETIN_DESCRIPTION+"</center><br></span>";
				});
				bulltinHtml+="</marquee>";
				$('#bulletinBoard').html(bulltinHtml);
			}else{
				$('#bulletinBoard').hide();
			}
			
	});



	
	
	$('#textfield').click(function(event){
		 event.stopPropagation(); 
		 if($(this).val() == 'Search Your Query')
		 {
			 $(this).val('');
			 $(this).css('color' , '#000000');
		 }

	});
	$('#button').click(function(event){
		 event.stopPropagation(); 
	});

	$('#tagTable').click(function(event){
		 event.stopPropagation(); 
	});

	$(document).mouseup(function (e){
			    var container = $('#container').find('ul');
			    if (container.has(e.target).length === 0){
			        container.hide();
			    }
			});
	
	$("#textfieldContainer").keyup(function(event){
		
		switch(event.keyCode) {
			case 33:
				movePrevOption();
				break;
			case 34:
				moveNextOption();
				break;
			case 38:
				movePrevOption();
				break;
			case 40:
				moveNextOption();
				break;
			case 13:
				if($('li').hasClass('ui-state-hover'))
				{
					$("#textfield").val($('#selectedOption').text());
					$('#container').find('ul').remove();
				}else{
					$('#container').find('ul').remove();
					submitTxt();
				}
				break;
			default:
				showSuggestions();
				break;

		}
	});

	/**
	Added by Mohit(816452)/307 to add the Top Ten Viewed Documents onLoad on the screen
	**/
	

	if("${backToSrch}" == "true" && srchTxtVar.replace(/[^a-zA-Z 0-9]+/g,'') != "Search Your Query" )
	{
		    $('#textfield').css('color' , '#666666');
			$('#textfield').val(srchTxtVar.replace(/[^a-zA-Z 0-9]+/g,''));
			srchTxtList = "${sessionScope.srchTxtList}";
			srchTxtList = String(srchTxtList);
			var srchTxtArray = new Array();
			srchTxtArray = srchTxtList.split(",");
				//$('#solutionBoard').show();
			   $.each(srchTxtArray , function(count){
					$('#tagName').append('<option>'+srchTxtArray[count]+'</option>');
				});

				submitTxt();
	}
	else{
	
	$.ajaxSetup({ cache: false });
	$.ajaxSetup({ async: false });
	$.getJSON("getTopTenViewedDocuments.htm",function(data){
		var docArr = data;
		var link = "";
		var id = "";
        var isTag = false;
        var isPolicyDoc = false;
      	var imgSrc = "";
        var alt = "";
        var type="";
        var downloadFileType="doc|docx|ppt|pptx|xls|xlsx"
        var usageCount="";
        var newcontent="";
        if(docArr.length>0){
        	 $("#smartSearchImg").hide();
		  for (count = 0; count < docArr.length; count++) {

		
				id = docArr[count].id;
				$.ajaxSetup({async:false});
				$.ajax({	
					  type: 'GET',
						url :'getUsageCount.htm', 
						data:{docId:id} ,
						success: function(data){
					usageCount=data;
					}
				});
				if(docArr[count].id.indexOf('TG') != -1)
				{
					isTag = true;
				}
				else if(docArr[count].id.indexOf('DC') != -1)
				{
					link = docArr[count].srch_links ;
					imgSrc="images/searchAI/Doc.png";
					alt="Document";
				}
				else if(docArr[count].id.indexOf('PD_') != -1)
				{
					isPolicyDoc = true;
					link = docArr[count].srch_links ;
					imgSrc="images/searchAI/Doc.png";
					alt="Policy Document";
				}
				else
				{
					link = "";
					imgSrc="images/searchAI/ticket.png";
					alt="IConnect Ticket";
				}
				if(isTag)
				  {
					 if(usageCount!= 0){
						  newcontent +="<div>";
						  }
					 	  newcontent +="<div class=\"result\">";
					  newcontent +="<a href=\"goSearchResult.htm?id="+id+"&docLink="+link+"&srchTxt="+$('#textfield').val().replace(/[^a-zA-Z 0-9]+/g,'')+"&isTag="+isTag+"\" onclick=updateUsageCount('"+id+"') ><img src=\"images/searchAI/User.png\" alt=\"User Solution\"/> "+docArr[count].srch_links_name+"</a>";
					  newcontent +="<span>"+docArr[count].srch_summary+"</span><span></span>";
					  newcontent +="</div>";
					  if(usageCount!= 0){
						  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
						  }	

				  }else if(isPolicyDoc)
				  {
					  if(usageCount!= 0){
						  newcontent +="<div>";
						  }
					 	  newcontent +="<div class=\"result\">";
						  newcontent +="<a onmouseover=\"document.getElementById('div_name').style.display='';showAddInfo('"+docArr[count].srch_func+"', '"+docArr[count].srch_catg+"','"+docArr[count].srch_sub_catg+"')\" ";
						  newcontent +="onmouseout=\"document.getElementById('div_name').style.display='none';\"  ";

						  link = String(link);
                         	if( link.indexOf('html') !=-1 && link.indexOf('htm') !=-1 && link.indexOf('pdf')!=-1 )
                            {
                           	 newcontent +="href=\""+link+"\" onclick=updateUsageCount('"+id+"')><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                            }else
                            {
                           	 newcontent +="href=\"#\" onclick=\"window.open('"+link+"');onclick=updateUsageCount('"+id+"')\" ><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                            }
					  
						  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
						  newcontent +="</div>";
						  if(usageCount!= 0){
							  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
							  }	
				  }
				  else
				  {
					  
					  if(usageCount!= 0){
						  newcontent +="<div>";
						  }
					  newcontent +="<div class=\"result\">";
					  newcontent +="<a onmouseover=\"document.getElementById('div_name').style.display='';showAddInfo('"+docArr[count].srch_func+"', '"+docArr[count].srch_catg+"','"+docArr[count].srch_sub_catg+"')\" ";
					  newcontent +="onmouseout=\"document.getElementById('div_name').style.display='none';\"  ";
					  newcontent  +="href=\"goSearchResult.htm?id="+id+"&docLink="+link+"&srchTxt="+$('#textfield').val().replace(/[^a-zA-Z 0-9]+/g,'')+"&isTag="+isTag+"\" onclick=updateUsageCount('"+id+"')><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
					  newcontent +="<span >  "+docArr[count].srch_summary+"</span>";
					  newcontent +="</div>";
					  if(usageCount!= 0){
						  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
						  }
					 	
				  }
				isTag = false;
			}

		  $('#Searchresult').html(newcontent);
       
		 
		  
		    $(".result").dotdotdot({
	 	    ellipsis        : '... ',
	        wrap            : 'word',
	        after           : null,
	        watch           : false,
	        height          : null,
	        tolerance       : 0,
       callback        : function( isTruncated, orgContent ) {},
	        lastCharacter   : {
	            remove          : [ ' ', ',', ';', '.', '!', '?' ],
	            noEllipsis      : []
	        }
		 
			});
        }else{
        	$("#smartSearchImg").show();
        	$("#smartSearch").hide();
        }
	});}
	/**
	End of added by Mohit(816452)/307 to add the Top Ten Viewed Documents onLoad on the screen
	**/
});


$('#userRoleId').change(function () {
    var roleId = $(this).val();
    var roleName = $("#userRoleId option:selected").text();
    window.location.href = "RoleChanged.htm?roleId=" + roleId + "&roleName=" + roleName;
});
function invalidateSess(){
	  window.location.href='Logout.htm';
}
var docArrLength = 0;
var start =0 ;
function pageselectCallback(page_index, jq) {
	start = (page_index * 10)+1;
	var newcontent = ' ';
	if (docArrLength > 10) {
		end = (page_index * 10) + 10 ;
		if(docArrLength > end )
		{
		  $("#totalCont").text("Showing "+start+" to "+end+" of "+docArrLength+" results "  );
		}else
		{
		  $("#totalCont").text("Showing "+start+" to "+docArrLength+" of "+docArrLength+" results "  );
		}
	} else {
		 $("#totalCont").text("Showing "+start+" to "+docArrLength+" of "+docArrLength+" results "  );
	}
	if (page_index == 0)
	{
		start = 0;
	} else
	{
		start =start -1;
	}
	var string =$("#textfield").val().replace(/[^a-zA-Z 0-9]+/g,'');
	var count = string.split(" ");
	if(count.length == 1 || count.length == 2)
	{
		mm =1;
	}else
	{
		mm =1;
	}
	$.ajax({
		  type: 'GET',
			url :  home+'/select?q='
			+ $("#textfield").val().replace(/[^a-zA-Z 0-9]+/g,'') + '&mm='+mm+'&wt=json&start='+start+'&timSt'+new Date(),
		  dataType: "json",
		  success: function(xml){
		  docArrLength = xml.response.numFound;		  
	    if(docArrLength == 0)
	    {
	 		  $("#Pagination").hide();
	 		  $("#totalCont").text("No result Found");
	 		  $('#Searchresult').html('');

	    }
	    else{
			 
	  		  var docArr = xml.response.docs;
			  var link = "";
			  var id = "";
	          var isTag = false;
	          var isPolicyDoc = false;
           	  var imgSrc = "";
              var alt = "";
              var type="";
              var downloadFileType="doc|docx|ppt|pptx|xls|xlsx"
              var usageCount="";
              //Added by Mohit(816452) to get the count for each article
              if(docArr.length>0){
            	  $("#smartSearchImg").hide();
			  for (count = 0; count < docArr.length; count++) {

			
					id = docArr[count].id;
					$.ajaxSetup({async:false});
					$.ajax({
						  type: 'GET',
							url :'getUsageCount.htm', 
							data:{docId:id} ,
							success: function(data){
						usageCount=data;
						}
					});
			//End of added by Mohit(816452) to get the count for each article
								
					
					
					if(docArr[count].id.indexOf('TG') != -1)
					{
						isTag = true;
					}
					else if(docArr[count].id.indexOf('DC') != -1)
					{
						link = docArr[count].srch_links ;
						imgSrc="images/searchAI/Doc.png";
						alt="Document";
					}
					else if(docArr[count].id.indexOf('PD_') != -1)
					{
						isPolicyDoc = true;
						link = docArr[count].srch_links ;
						imgSrc="images/searchAI/Doc.png";
						alt="Policy Document";
					}
					else
					{
						link = "";
						imgSrc="images/searchAI/ticket.png";
						alt="IConnect Ticket";
					}
					  if(isTag)
					  {
						  if(usageCount!= 0){
							  newcontent +="<div>";
							  }
						 	  newcontent +="<div class=\"result\">";
						  newcontent +="<a href=\"goSearchResult.htm?id="+id+"&docLink="+link+"&srchTxt="+$('#textfield').val().replace(/[^a-zA-Z 0-9]+/g,'')+"&isTag="+isTag+"\" onclick=updateUsageCount('"+id+"') \"><img src=\"images/searchAI/User.png\" alt=\"User Solution\"/> "+docArr[count].srch_links_name+"</a>";
						  newcontent +="<span>"+docArr[count].srch_summary+"</span><span></span>";
						  newcontent +="</div>";
						  if(usageCount!= 0){
							  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
							  }
					  }else if(isPolicyDoc)
					  {
						  if(usageCount!= 0){
							  newcontent +="<div>";
							  }
						 	  newcontent +="<div class=\"result\">";
							  newcontent +="<a onmouseover=\"document.getElementById('div_name').style.display='';showAddInfo('"+docArr[count].srch_func+"', '"+docArr[count].srch_catg+"','"+docArr[count].srch_sub_catg+"')\" ";
							  newcontent +="onmouseout=\"document.getElementById('div_name').style.display='none';\"  ";

							  link = String(link);
                              	if( link.indexOf('html') !=-1 && link.indexOf('htm') !=-1 && link.indexOf('pdf')!=-1 )
                                 {
                                	 newcontent +="href=\""+link+"\" onclick=updateUsageCount('"+id+"');\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                                 }else
                                 {
                                	 newcontent +="href=\"#\" onclick=\"window.open('"+link+"');onclick=updateUsageCount('"+id+"');\" ><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                                 }
						  
							  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
							  newcontent +="</div>";
							  if(usageCount!= 0){
								  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
								  }	
					  }
					  else
					  { 
						  if(usageCount!= 0){
							  newcontent +="<div>";
							  }
					 	  newcontent +="<div class=\"result\">";
						  newcontent +="<a onmouseover=\"document.getElementById('div_name').style.display='';showAddInfo('"+docArr[count].srch_func+"', '"+docArr[count].srch_catg+"','"+docArr[count].srch_sub_catg+"')\" ";
						  newcontent +="onmouseout=\"document.getElementById('div_name').style.display='none';\"  ";
						 newcontent  +="href=\"goSearchResult.htm?id="+id+"&docLink="+link+"&srchTxt="+$('#textfield').val().replace(/[^a-zA-Z 0-9]+/g,'')+"&isTag="+isTag+"\" onclick=updateUsageCount('"+id+"')><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
						  newcontent +="<span>  "+docArr[count].srch_summary+"</span>";
						  newcontent +="</div>";
						  if(usageCount!= 0){
							  newcontent +="<span class=\"countview\">"+usageCount+" views</span></div>";
							  }
					  }
					  isTag = false;
				}

			  $('#Searchresult').html(newcontent);
	  
			  $("#Pagination").show();
			  
			    $(".result").dotdotdot({
		 	    ellipsis        : '... ',
		        wrap            : 'word',
		        after           : null,
		        watch           : false,
		        height          : null,
		        tolerance       : 0,
	        callback        : function( isTruncated, orgContent ) {},
		        lastCharacter   : {
		            remove          : [ ' ', ',', ';', '.', '!', '?' ],
		            noEllipsis      : []
		        }
		    });
              }else{
      	    	$("#smartSearchImg").show();
              	$("#smartSearch").hide();
      	    }
	    	}
		  }   
		});
	return false;
}

function setSubjectFlag(){
	$("#isSaveSubjectReqd").val('1');
}
function getOptionsFromForm() {
	var opt = {
		callback : pageselectCallback
	};
	opt.prev_text = "Prev";
	opt.next_text = "Next";
	$("input:text")
			.each(
					function() {
						opt[this.name] = this.className.match(/numeric/) ? parseInt(this.value)
								: this.value;
					});
	var htmlspecialchars ={ "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;"}
	$.each(htmlspecialchars, function(k,v){
	opt.prev_text = opt.prev_text.replace(k,v);
	opt.next_text = opt.next_text.replace(k,v);
	})
	return opt;
}
function showAddInfo(funcName, categ, subCateg ,t)
{

	var txt = 
	"<ul class=\"addInfoContent\"><li>Function Name : "+funcName+"</li><li>Category : "+categ+"</li><li>Sub Category : "+subCateg+"</li></ul>";
	$('#div_name').show();
	$('#div_name').html(txt);
}

function getSubject(){
	
	var sub=$('#textfield').val();
	if($('#textfield').val() == 'Search Your Query'){
		sub='';
	}	
	//alert(encodeURIComponent(sub));
	sub=encodeURIComponent(sub);
	window.location.href="UserHome.htm";
	return false; 
	
}
function setHeaderID(id){
	$.session("modname1",id);
	$.getJSON('UserHome1.htm',{modname : id},function() {
		
	});
	window.location.href="UserHome.htm";
}


</script>

</head>

<body>
<% String token = null; %>
<table width="102%" border="0" align="center" cellpadding="0"
	cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td colspan="3" align="center" valign="top" class="headerLine">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="header_bg">
			<tr>
				<td width="15%" align="left" valign="middle"><img
					src="images/searchAI/iConnect_Logo.png" width="205" height="60" /></td>
				<td width="70%" align="center"><img
					src="images/searchAI/header.png" /></td>
				<td width="15%" align="right" valign="middle"><img
					src="images/searchAI/Capgemini_Search_Logo.png" width="130" height="60"  style="margin-right:6px"/></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2" align="right"><span>Welcome <b><%=empName %></b>,<%=location %>
				</span> <a href="login" class="signin"><img src="images/profile.png"
					alt="My Profile" title="My Profile" /></a> <a
					onclick="iConnectLogOut();"><img src="images/logout.png"
					alt="Logout" title="Logout" /></a></td>
				<td>
				<fieldset id="signin_menu">
				<form id="changeRole" action="/iconnect/RoleChanged.htm"
					method="post"><select name="userRoleId" id="userRoleId"
					onchange="isChange()">
					<%
		for(int i = 0; i < userRoleList.size(); i++){
			WORKFLOW_Role role = userRoleList.get(i);
			if(currentUserRole.equalsIgnoreCase(role.getRoleName())){
				currentUserRoleID = role.getRoleId();
		%>
					<option value="<%= role.getRoleId()%>" selected="selected"><%= role.getRoleName()%></option>
					<%
			}else{
		%>
					<option value="<%= role.getRoleId()%>"><%= role.getRoleName()%></option>
					<%
			}
	}
		
		%>

				</select></form>

				</fieldset>

				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="18%" height="69" align="center" valign="top"
			style="border-right: 1px dashed #dedede;">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<div class="board" id="bulletinBoard"></div>
				</td>
			</tr>
			<tr>
				<td>
				<div class="helpdesk">
				<div><strong>Helpdesk - IConnect</strong></div>
				<ul>
					<li><a onclick="setHeaderID('RaiseNewTicket')" href="#" >Raise
					New Ticket</a></li>
					<li><a onclick="setHeaderID('ExistingTickets')" href="#">View
					Existing Ticket</a></li>
					<li><a onclick="setHeaderID('WaitingForApproval')" href="#">Waiting
					for My Approval</a></li>
					<li><a onclick="setHeaderID('ApprovedByMe')" href="#"> Approved
					By Me</a></li>
					<li><a onclick="setHeaderID('RejectedByMe')" href="#">Rejected
					By Me</a></li>
				<!-- 	<li><a onclick="setHeaderID('CreateServiceTicket')" href="#">Create Service Ticket</a></li>
					<li><a onclick="setHeaderID('ViewServiceTicket')" href="#">View Service Ticket</a></li> -->
					<li><a onclick="setHeaderID('ContactInfo')" href="#">Contact
					Info</a></li>
				</ul>
				</div>
				</td>
			</tr>
		</table>
		</td>
		<td width="62%" align="center" valign="top">
		
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" >
<!--Changed by Mohit(816452) for enhancement in UI -->
<tr>
				<td height="140" align="center" valign="top">
			<div id ="smartSearchImg" style="display:none"><img src="images//searchAI/CominSoonSmartSearch.png" /></div>
				<table width="100%"  border="0" align="center"  id ="smartSearch" cellpadding="0"
					cellspacing="0">

					<tr>

						<td height="80" align="center" valign="middle">
						<table cellpadding="0" border="0" cellspacing="0" width="700px"
							style="margin-top: 10px;" id="textfieldContainer">

							<tr>
								<td width="65%" height="70px" class="search searchBG1"
									align="center"><input name="textfield" type="text"
									id="textfield" value="Search Your Query"
									onfocus="if(this.value == 'Search Your Query') {this.value='';this.style.color='#000';}"
									onblur="if(this.value == '') {this.value='Search Your Query';this.style.color='#aaa';}" />

								</td>
								<td id="searchBG2" width="21%" valign="top" align="left"><input
									class="searchbtn" type="image"
									src="images//searchAI/Search_Button.png" name="button"
									id="button" value="Search"
									onclick="setSubjectFlag();submitTxt()" alt="Search" /></td>
							</tr>
							<tr>
								<td width="65%" id="container" align="left"></td>
								<td></td>
							</tr>

							<tr>
								<td align="left" colspan="2">
								<div style="width: 100%;">
								<div class="bulb-img"><img src="images//searchAI/Bulb.png" />
								</div>
								<div class="home-tip">Still raising tickets for common issues?<br/>
								Try our Smart Search feature with frequently used documents for an instant solution!!
								</div>
								</div>
								</td>

							</tr>

						</table>
						</td>
					</tr>

				</table>
				</td>
			</tr>

<!--End of changed by Mohit(816452) for enhancement in UI -->
			<tr>
				<td align="left" valign="middle" class="total"><span
					id="totalCont"></span></td>
			</tr>
			<tr>
				<td height="102" align="left" valign="top">
				<div id="Searchresult"></div>
				<div id="Pagination" class="pagination" style="display: none;">
				<span class="current prev">Prev</span> <span class="current">1</span>
				<a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a>
				<a href="#">6</a> <a href="#">7</a> <a href="#">8</a> <a href="#">9</a>
				<a href="#">10</a> <span>...</span> <a href="#">60</a> <a href="#">61</a>
				<a class="next" href="#">Next</a></div>
				</td>
			</tr>
		</table>

		</td>
		<td width="20%" align="left" valign="top"
			style="border-left: 1px dashed #dedede;">
			
			<form:form
			id="UserComment" modelAttribute="UserComment"
			action="saveUserTag.htm" name="UserComment" method="post">
			<table width="200" border="0" align="center" cellpadding="0"
				cellspacing="0" id="tagTableComingSoon">
				</table>
			<table width="200" border="0" align="center" cellpadding="0"
				cellspacing="0" id="tagTable" style="display:none">
				<tr>
					<td>
					<div style="width: 100%; height: auto; text-align: center;">
					<img src="images//searchAI/Message.gif" /></div>
					<div
						style="width: 100%; height: auto; margin-top: -16px; text-align: center;">
					<img src="images//searchAI/Features.png" /></div>
					</td>
				</tr>

			</table>
		</form:form>
		<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
		<div id="div_name"
			style="display: none; position: absolute; float: right; z-index: 9999; background-color: #FFEBBF; border: 2px solid #e6e6e6; padding: 10px; text-align: left; margin: 0px 50px 0px 10px; width: 200px; vertical-align: middle; margin-top: 10px;">
		</div>
		</td>
	</tr>
</table>
<div class="footer">Copyright &copy; IGATE. All Rights Reserved.</div>
<div id="outputSaveSol" style="display: none"></div>
<div class="content"><input type="hidden" id="jbarmessageSaveSol"
	name="jbarmessageSaveSol"></input></div>
</body>
</html>