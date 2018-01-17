<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
   
  // ResourceBundle bundle = ResourceBundle.getBundle("smartsearch");
  // String iConnectURL=bundle.getString("iConnectURL");
  String schema = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		String iConnectURL = schema + "://" + serverName + ":" + serverPort + contextPath;
		//System.out.println("iConnectURL:::"+iConnectURL);

%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Artificial Intelligence - Search</title>
<link href="<%=cssDirPath%>/UNIVERSAL_Search_Style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=cssDirPath%>/ui.base.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/ui.theme.css" rel="stylesheet" />
<link href="<%=cssDirPath%>/jquery.autocomplete.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.dotdotdot.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/search.js"></script>
<link href="<%=cssDirPath%>/UNIVERSAL_Search_pagination_new.css" rel="stylesheet" />
<script type="text/javascript">
var context = "${pageContext.request.contextPath}";
loc = String(window.location);
var urlAppl = loc.substr(0,loc.indexOf(context));
var home = '<%=iConnectURL%>';
var usrLocation = "${sessionScope.location}";
var searchText="${sessionScope.subject}";
var ticketID="${sessionScope.ticketID}";
var menuID="${sessionScope.menuID}";
$(document).ready(function(){
	if("${backToSrch}" == "true")
	{
		    $('#textfield').css('color' , '#666666');
			$('#textfield').val("${sessionScope.srchTxt}");
			submitTxt();
	}
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
	
	$("#searchTip").show();
	$("#onResultNotFound").hide();
	$("#onResultFound").hide();
	$('#textfield').val(searchText.replace(/%22/g," "));
	submitTxt();
});

var docArrLength = 0;
var start =0 ;
function setSubjectFlag(){
	$("#isSaveSubjectReqd").val('1');
}
function downloadAttachment(url){
	location.href=url;
}
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
	var string =$("#textfield").val();
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
			+ $("#textfield").val() + '&mm='+mm+'&wt=json&start='+start+'&timSt'+new Date(),
		  dataType: "json",
		  success: function(xml){
		  docArrLength = xml.response.numFound;
	    if(docArrLength == 0)
	    {
		    //Changed by Mohit to show the proper image as per result
	 		  $("#Pagination").hide();
	 		  //$("#totalCont").text("No result Found");
	 		  $('#Searchresult').html('');
	 		 $("#searchTip").hide();
	 		$("#onResultNotFound").show();
	 		$("#onResultFound").hide();

	    }
	    else{
	    	  $("#searchTip").hide();
		 	  $("#onResultNotFound").hide();
		 	 $("#onResultFound").show();
	  		  var docArr = xml.response.docs;
			  var link = "";
			  var id = "";
	          var isTag = false;
	          var isPolicyDoc = false;
           	  var imgSrc = "";
              var alt = "";
              var type="";
              var downloadFileType="doc|docx|ppt|pptx|xls|xlsx"
			  for (count = 0; count < docArr.length; count++) {

					id = docArr[count].id;
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
					 	  newcontent +="<div class=\"result\">";
						  newcontent += "<a href=\"#\" onclick=\"onclick=updateUsageCount('"+id+"');OpenPopup('"+link+"','"+id+"','"+$('#textfield').val()+"','"+isTag+"');\"><img src=\"images/searchAI/User.png\" alt=\"User Solution\"/>"+docArr[count].srch_links_name+"</a>";
						  newcontent +="<span>"+docArr[count].srch_summary+"</span><span></span>";
						  newcontent +="</div>";	

					  }else if(isPolicyDoc)
					  {
					 	  newcontent +="<div class=\"result\">"
							  newcontent +="<a onmouseover=\"document.getElementById('div_name').style.display='';showAddInfo('"+docArr[count].srch_func+"', '"+docArr[count].srch_catg+"','"+docArr[count].srch_sub_catg+"')\" ";
							  newcontent +="onmouseout=\"document.getElementById('div_name').style.display='none';\"  ";

							  link = String(link);
                              	if( link.indexOf('html') !=-1 && link.indexOf('htm') !=-1 && link.indexOf('pdf')!=-1 )
                                 {
                                	 newcontent +="href=\""+link+"\" onclick=updateUsageCount('"+id+"')\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                                 }else
                                 {
                                	 newcontent +="href=\"#\" onclick=\"window.open('"+link+"');updateUsageCount('"+id+"')\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
                                 }
						  
							  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
							  newcontent +="</div>";	
					  }
				 
					  {
						  if(link != "")
						  {
							  if ( ticketID !="" && (menuID==13 || menuID==46)){	
						 	  newcontent +="<div class=\"result\">"
							  newcontent +="<div class='linkhref'><a href='#' onclick=\"updateUsageCount('"+id+"');downloadAttachment('DownloadAttachmentForAI.htm?docLink="+link+"')\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a></div>";
							  newcontent +="<div class='attach'><img src=\"images/searchAI/attachment-icon.png\" style=\"cursor: pointer\" onclick=\"updateAttachmentToTicket('"+ticketID+"','"+link+"')\"alt=\"Attachment\"/ title=\"Link to Ticket\"/><img src=\"images/searchAI/attachment-remove.png\" style=\"cursor: pointer\" onclick=\"inactivateAttachmentFromTicket('"+ticketID+"','"+link+"')\"alt=\"Remove Attachment\"/ title=\"Remove from Ticket\"/></div>";
							  newcontent +="<div style='clear:both'></div>";
							  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
							  newcontent +="</div>";
							}else{
								 newcontent +="<div class=\"result\">"
									  newcontent +="<div class='linkhref'><a href='#' onclick=\"updateUsageCount('"+id+"');downloadAttachment('DownloadAttachmentForAI.htm?docLink="+link+"')\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a></div>";
									 // newcontent +="<div class='attach'><img src=\"images/searchAI/attachment-icon.png\" style=\"cursor: pointer\" onclick=\"updateAttachmentToTicket('"+ticketID+"','"+link+"')\"alt=\"Attachment\"/ title=\"Link to Ticket\"/><img src=\"images/searchAI/attachment-remove.png\" style=\"cursor: pointer\" onclick=\"inactivateAttachmentFromTicket('"+ticketID+"','"+link+"')\"alt=\"Remove Attachment\"/ title=\"Remove from Ticket\"/></div>";
									  newcontent +="<div style='clear:both'></div>";
									  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
									  newcontent +="</div>";
							}	
						  }
						  else
						  {
						 	  newcontent +="<div class=\"result\">"
							  newcontent +="<a href=\"#\" onclick=\"updateUsageCount('"+id+"');OpenPopup('"+link+"','"+id+"','"+$('#textfield').val()+"','"+isTag+"')\"><img src=\""+imgSrc+"\" alt=\""+alt+"\"/> "+docArr[count].srch_links_name+"</a>";
							  newcontent +="<span>  "+docArr[count].srch_summary+"</span><span></span>";
							  newcontent +="</div>";

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
	    	}
		  }   
		});

	return false;
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
$(document).keypress(function(event	) {
	if(event.which == 13)
	{
		submitTxt();
	}
});
function showAddInfo(funcName, categ, subCateg ,t)
{
	var txt = 
"<ul class=\"addInfoContent\"><li>Function Name : "+funcName+"</li><li>Category : "+categ+"</li><li>Sub Category : "+subCateg+"</li></ul>";
    $('#div_name').html(txt);
}
</script>
</head>

<body>
<% String token = null; %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
   <!-- <td colspan="3" align="center" valign="top" class="headerLine" >
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="header_bg">
      <tr>
        <td width="15%" align="left" valign="middle"><img src="images/iConnect_Logo.png" width="205" height="60" /></td>
        <td width="70%" align="center"><img src="images/header.png" /></td>
        <td width="15%" align="right" valign="middle"><img src="images/Capgemini_Search_Logo.png" width="150" height="73" /></td>
      </tr>
    </table>
    </td>
  </tr>
  -->
  <!--<tr>
    <td width="15%" height="69" align="center" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>
        	<div class="board">
          		<div><strong>Bulletin Board</strong></div>
        	</div>
		</td>
      </tr>
      <tr>
        <td>
    <div class="helpdesk">
          		<div><strong>Helpdesk - iConnect</strong></div>
            <ul>
            	<li><a>Raise New Ticket</a></li>
                <li><a>View Existing Ticket</a></li>
                <li><a>Waiting For My Approval</a></li>
                <li><a>Approved By Me</a></li>
                <li><a>Rejected By Me</a></li>
                <li><a>HelpDesk & Escalations</a></li>
            </ul>
                
        	</div>
        </td>
        </tr>
   </table></td> -->
		<td width="70%" align="center" valign="top">

		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<!--  width="350"=width="800"      -->
				<td height="85" align="center" valign="middle">
				<table width="350" border="0" align="center" cellpadding="0"
					cellspacing="0">
<!-- Changed by Mohit(816452) for enhacement in UI -->
					<tr>
					<td height="80" align="center" valign="top">
						<table cellpadding="0" cellspacing="0" id="textfieldContainer">

							<tr>
								<td width="100%" class="search" align="left"><input
									name="textfield" type="text" id="textfield"
									value="Search Your Query"
									onfocus="if(this.value != '') {this.value='';this.style.color='#000';}"
									onblur="if(this.value == '') {this.value='Search Your Query';this.style.color='#aaa';}" /></td>
							</tr>
							<tr>
								<td align="center"><input style="margin-left: -30px;"
									type="image" name="button" id="button" value="Search"
									onclick="setSubjectFlag();submitTxt()"
									src="images//searchAI/SearchbtnRaise.png" /></td>
							</tr>
							<tr>
								<td><input type="hidden" id='isSaveSubjectReqd' value='0'></input></td>
							</tr>
							<tr>
								<td width="85%" id="container" align="left"></td>
							</tr>
						</table>
<!-- End of changed by Mohit(816452) for enhacement in UI -->
						</td>

					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<div id="searchTip" class="searchTip"><img width="90%"
					src="images//searchAI/OnLoadTip.png" /></div>
				<div id="onResultFound" class="searchTip"><img width="90%"
					src="images//searchAI/OnResFound.png" /></div>
				<div id="onResultNotFound" class="searchTip"><img width="90%"
					src="images//searchAI/OnResNotFound.png" /></div>
				</td>
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
		<td width="15%" align="center" valign="top"></td>
	</tr>
	<!--
  <tr>
    <td height="25" colspan="3" align="center" valign="middle" class="footer">Copyrights &copy;IGATE All Rights Reserved.</td>
  </tr>
-->
</table>
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>