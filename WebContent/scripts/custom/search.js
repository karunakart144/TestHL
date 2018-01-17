
var voteOption = '' ;

function submitTxt()
{
	var newcontent = ' ';
	var optInit = getOptionsFromForm();
	var totalPages = 0;
	var string =$("#textfield").val();
	var count = string.split(" ");
	if(count.length == 1 || count.length == 2)
	{
		mm =1;
	}else
	{
		mm =1;
	}
	if( $("#textfield").val().localeCompare('Search Your Query') != 0)
	{
$.ajax({
	  type: 'GET',
		url :   home+'/select?q='
		+ $("#textfield").val() + '&mm='+mm+'&wt=json&start='+start+'&timSt'+new Date(),
	  dataType: "json",
	  success: function(xml){
	  docArrLength = xml.response.numFound;
    if(docArrLength == 0)
    {
    	  $("#Pagination").hide();
 		 $("#totalCont").text("No result Found");
 		  $('#Searchresult').html('');
 		 if(string == "")
 		 {
 			$("#searchTip").show();
 			$("#onResultNotFound").hide();
 			$("#onResultFound").hide();
 		}
 		 else
 		 {
 			$("#searchTip").hide();
 			$("#onResultNotFound").show();
 			$("#onResultFound").hide();
 		 }
 		
	 	 
 		  getCorrctedSpell();
 		  

    }
    else{
		  $("#Pagination").pagination(docArrLength, optInit);
		  //$('#solutionBoard').show(); Changed by Mohit(816452) to temporarily disable the solution board
		  $("#searchTip").hide();
	 	  $("#onResultNotFound").hide();
	 	 $("#onResultFound").show();
	}
	  }   
	});

var isPresentCount = 0;

if(($("#textfield").val().localeCompare('Search Your Query')!=0))
{
	
  $.each($('#tagName option') ,function(){

		    if($(this).val().localeCompare($("#textfield").val()) == 0)
		    {
		    	isPresentCount = isPresentCount+1;
		    }

  });
}
  if(isPresentCount == 0)
  {
	 if(jQuery.trim($('#textfield').val()).length > 0 )
	 {
		$('#tagName').prepend('<option style="width: 95%;">'+$("#textfield").val()+'</option>');
	 }
  }
  var tagList = $('#tagName');
  if($('#tagName option').length > 0)
  {
  tagList[0].selectedIndex = 0;
  }

}
	if($("#isSaveSubjectReqd").val()=="1"){
		$.getJSON("saveUserSubject.htm", { subject : $('#textfield').val() }, function(data){});
	}
	
return false;
}
function postComments()
{
      var cmt = $("#userComment").val();
      var ucmt = '';
      if(cmt.length > 500)
      {
            alert("You can post maximum 500 characters.");
      }
      else
      {
      var html ="";
      if(jQuery.trim($('#userComment').val()).length > 0 )
      {
            $.ajaxSetup({ cache: false });
            
            ucmt=cmt.replace(/&/g,"&#38;");
            ucmt = ucmt.replace(/\n/g,'brlinebreakbreak');
        ucmt=ucmt.replace(/</g,"&#60;");
        ucmt=ucmt.replace(/>/g,"&#62;");
        ucmt=ucmt.replace(/=/g,"&#61;");          
        ucmt=ucmt.replace(/!qt/g,"&#34;");
        ucmt=ucmt.replace(/\"/g,"&#34;");
        ucmt=ucmt.replace(/\'/g,"&#39;");
        ucmt=ucmt.replace(/~/g,"&#126;");   
        ucmt=ucmt.replace(/\\/g,"&#92;");
        ucmt=ucmt.replace(/\//g,"&#47;");   
       


    
            $.getJSON("saveUserComment.htm", { usercomment: ucmt }, function(data) { 
                        for(count = 0; count < data.docCmnt.userComment.length; count++)
                        {
                              ucmt = String(data.docCmnt.userComment[count].comment);
                             ucmt =ucmt.replace(/brlinebreakbreak/g,'<br>');
                              html = html + "<div class=\"commentDiv\" style=\"width:300px; border-bottom: 2px solid #e4e4e4\"><p>"+data.docCmnt.userComment[count].userName+":</p><p>"+ucmt+"</p></div>";
                        }     $("#userCommentHolder").html(html);
                  });
            $("#userComment").val('');
            }
      }
}

function updateLikeCount()
{
	if(voteOption == ''){
		voteOption = 'L';
		$('#disLikeImg').attr('src' , 'images/searchAI/down_hidden.png');
		$('#likeImg').attr('src' , 'images/searchAI/up_hidden.png');
//	}
//	if(voteOption == 'L')
//	{
		$.ajaxSetup({ cache: false });
	$.getJSON("saveUserVote.htm", { vote : '1' }, function(data) {
		$("#likeCount").html(data.docVote.LikeCount);
		$("#dislikeCount").html(data.docVote.DisLikeCount);
		 });
	}
}

function updateDisLikeCount()
{
	if(voteOption == ''){
		voteOption = 'D';
		$('#likeImg').attr('src' , 'images/searchAI/up_hidden.png');
		$('#disLikeImg').attr('src' , 'images/searchAI/down_hidden.png');
//	}
//	if(voteOption == 'D')
//	{
		$.ajaxSetup({ cache: false });
	$.getJSON("saveUserVote.htm", { vote : '2' }, function(data) {
		$("#likeCount").html(data.docVote.LikeCount);
		$("#dislikeCount").html(data.docVote.DisLikeCount);
		 });
	}
}

function getUsrComment(){
	$.ajaxSetup({ cache: false });
		$.getJSON("getResultDetail.htm?timSt"+new Date(), function(data) {
			var html = "";
		$("#likeCount").html(data.docVote.LikeCount);
		$("#dislikeCount").html(data.docVote.DisLikeCount);
		for(count = 0; count < data.docCmnt.userComment.length; count++)
		{
			html += "<div class=\"commentDiv\" style=\"width:300px; border-bottom: 2px solid #e4e4e4\"><p>"+data.docCmnt.userComment[count].userName+":</p><p>"+data.docCmnt.userComment[count].comment+"</p></div>";
		}
		 $("#userCommentHolder").html(html);
		 
		 });
	}

/*Added by Mohit(816452) to save the no. viewers for an article*/
function updateUsageCount(documentId)
{
	$.ajaxSetup({ async:false });
	$.getJSON("saveUsageCount.htm", { docId : documentId });
}
/*End of added by Mohit(816452) to save the no. viewers for an article*/


function getTicketDetails()
{
	$.ajaxSetup({ cache: false });
	$.getJSON("getTicketDetail.htm?timSt"+new Date(), function(data) {
		$("#detailsCat").html(data.Category);
		$("#detailsPlace").html(data.place);
		$("#detailsCity").html(data.city);
		$("#detailsSub").html(data.subject);
		$("#detailsDes").html(data.Description);
        $("#detailsRes").html(data.Resolution);
		 });
	getUsrComment();	
}

function OpenPopup(link,id,srchTxt,isTag) {
    var url="goSearchResult_Universal.htm?id="+id+"&docLink="+link+"&srchTxt="+srchTxt+"&isTag="+isTag; 
    window.open(url,'window','width=600,height=480,scrollbars=yes,status=yes,location=no,titlebar=no,toolbar=no');
     return true;
}
function iConnectLogOut(){
	window.location.href='Logout.htm';
}

function getTagDetails()
{
	$.ajaxSetup({ cache: false });
	$.getJSON("getTagDetail.htm?timSt"+new Date(), function(data) {
		$("#userName").html(data.UserName);
		$("#userSolution").html(data.Description);
		});
	getUsrComment();	
}

function loadFrame(link)
{
	if(link.length > 0 )
	{
		//$("#docPlaceHolder").attr('src',"ftp://192.168.120.190/iConnectAI/"+link);
		$("#docPlaceHolder").attr('src', "DownloadAttachmentForSU.htm?link=" + link);
	}
}

function setHt()
{
	var ht = $('iframe').contents().height();
    var wt = $('iframe').contents().width();
	$('iframe').css('height',ht );
	$('iframe').css('width',wt );
	getUsrComment();
}

function showSuggestions()
{
	$('#container').empty();
	if($("#textfield").val() != null && $("#textfield").val() != '' && $("#textfield").val() != 'Search Your Query')
	{
		$.ajaxSetup({ cache: false });
		$.getJSON(home+'/suggest?spellcheck.q='+$('#textfield').val()+'&wt=json',function(data){
		menu = $( "<ul></ul>" )
		.addClass( "ui-autocomplete ui-menu ui-widget ui-widget-content ui-corner-all search-tip seachSuggestion" ).css({
		    'z-index': '1',
		    'display': 'block'
	});
        if(data.spellcheck != null && data.spellcheck != undefined)
        {
        if(data.spellcheck.suggestions.length > 0)
        {
        var strSuggestion=String(data.spellcheck.suggestions);
		var arr = strSuggestion.split(",");
		var index = 1;
	    for(var count=0;count<arr.length;count=count+2)
	    {
	    	if($.trim(arr[count]).localeCompare($.trim($("#textfield").val()))==0)
	    	{
	    		index = count+1;
	    	}
	    }
	    if(data.spellcheck != null && data.spellcheck != undefined)
	    {
	    if(data.spellcheck.suggestions[index].suggestion.length > 0)
	    {
		arr = String(data.spellcheck.suggestions[index].suggestion).split(",");
		$.each(arr , function(index){
			$( "<li></li>" ).attr('index',index).addClass("ui-menu-item")
			.css({
			    'text-align' : 'left'})
			.append( $( "<a></a>" ).css("tabindex","-1").addClass("ui-corner-all").text( arr[index])
					.mouseenter(function( event ) {
						$(this).addClass("ui-state-hover");
						$(this).attr("id" , "selectedOption")
					})
					.mouseleave(function() {
						$(this).removeClass("ui-state-hover");
						$(this).attr("id" , "");
					})
					.click(function() {
						$("#textfield").val($(this).text());
						$('#container').find('ul').remove();
					}))
			.appendTo( menu );
		});
		menu.appendTo('#container');
		$('#container').find('ul').width($('#container').width()-15);
	    }
	    }
	}
    }
	});
	}
}

function getCorrctedSpell()
{
	$.ajaxSetup({ cache: false });
	$.getJSON(home+'/spellCheck?spellcheck.q='+$('#textfield').val()+'&spellcheck=true&spellcheck.collate=true&wt=json',function(data){
		
        if(data.spellcheck != null)
        {
        	if(data.spellcheck.suggestions != null)
        	{
        		var strSuggestion=String(data.spellcheck.suggestions);
        		var arr = strSuggestion.split(",");
        		if(String(arr[arr.length-2]).localeCompare("collation") == 0)
        		{
        			$("#totalCont").html("<b><center>Did you mean <a href=\"#\" onclick=\"setSrchTxtWithSuggest('"+arr[arr.length-1]+"')\"><i>"+arr[arr.length-1]+"</i></a>?<br/><br/>No result found for "+$('#textfield').val().replace(/[^a-zA-Z 0-9]+/g,'')+"</center></b>");
        		}else
        		{
        			$("#totalCont").html("<center>No result found</center>");
        		}
        	}
        }
	});
}

function setSrchTxtWithSuggest(txt)
{
	$('#textfield').val(txt);
	submitTxt();
}
function movePrevOption()
{
    if($('li').hasClass('ui-state-hover'))
    {
       var cur = $(".ui-state-hover");
       var prev = $(".ui-state-hover").prev()
       cur.removeClass("ui-state-hover");
       cur.attr("id" , "");
       prev.attr("id" , "selectedOption");
       prev.addClass("ui-state-hover");
    }else
    {
		$('#container').find('ul').find('li').last().addClass("ui-state-hover");
		$('#container ul').find('li').last().attr("id" , "selectedOption");
    }
}
function moveNextOption()
{
    if($('li').hasClass('ui-state-hover'))
    {
        var cur = $(".ui-state-hover");
        var nxt = $(".ui-state-hover").next()
        cur.removeClass("ui-state-hover");
        cur.attr("id" , "");
        nxt.attr("id" , "selectedOption");
        nxt.addClass("ui-state-hover");
    	
    }else
    {
		$('#container').find('ul').find('li').first().addClass("ui-state-hover");
		$('#container').find('ul').find('li').first().attr("id" , "selectedOption");
    }
}

function updateAttachmentToTicket(requestID,link){
	$.getJSON('downloadFileToUpload.htm',{requestID:requestID,docLink:link},function(data){
		var successmessage = "";
		if(data.status=="SUCESS"){
			successmessage="Document has been attached to the ticket.";
			parent.jbarOnSuccess(successmessage);
		}else if(data.status=="EXISTS"){
			successmessage="Document already attached to this ticket.";
			parent.jbarOnFailure(successmessage);
		}else if(data.status=="LIMIT EXCEEDED"){
			successmessage="Only 5 attachments can be tagged to a ticket.";
			parent.jbarOnFailure(successmessage);
		}else{
			successmessage="Failed to attach the document to the ticket";	
			parent.jbarOnFailure(successmessage);
		}
		
		
	});

}

function inactivateAttachmentFromTicket(requestID,link){
	$.getJSON('inactivateAttachmentFromTicket.htm',{requestID:requestID,docLink:link},function(data){
		var successmessage = "";
		if(data.status=="SUCESS"){
			successmessage="Document has been removed from the ticket.";
			parent.jbarOnSuccess(successmessage);
		}else if(data.status=="EXISTS"){
			successmessage="Document not attached to this ticket to remove.";
			parent.jbarOnFailure(successmessage);
		}else if(data.status=="LIMIT EXCEEDED"){
			successmessage="Max attachment limit exceeded ";
			parent.jbarOnFailure(successmessage);
		}else{
			successmessage="Failed to remove the document from the ticket";	
			parent.jbarOnFailure(successmessage);
		}
		
		
	});

}

