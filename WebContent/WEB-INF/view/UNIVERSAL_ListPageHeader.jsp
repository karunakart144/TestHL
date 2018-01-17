<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  
   String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); 
%>
<link 	type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/json.min.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/custom/UNIVERSAL_AdvancedSearch.js"></script>
<script type="text/javascript" src="<%=jsDirPath%>/jquery.layout-latest.js"></script>
<script type="text/javascript">
var menuId ="1";
var workLoadHTML="";
	$.getJSON('groupList.htm', {
			menuId : menuId
		}, function(data) {
			if (data.length != 0) {
				var options = [];
				options.push('<option>Select</option>');
				$.each(data, function(item) {
					options.push('<option value="' + data[item].GROUP_ID + '">'
							+ data[item].NAME + '</option>');
				});
				
				$("#groupListId").html(options.join(''));
			}
		});
	
function getGroupMember() {

	var groupName = $('#groupListId').val();
	$.getJSON('groupMember.htm', {
		groupName : groupName
	}, function(data) {
		if (data.length != 0) {

			
			var options = [];
			$.each(data, function(item) {
				options.push('<option value="' + data[item].member_name_id + '">'
						+ data[item].member_name_id + '</option>');
			});
			$("#groupMemberId").html(options.join(''));
		}
	});
	workLoadHTML = "<table class='myDataTable' width='100%'><th>Excutive</th><th>W</th><th>L</th><th>M</th><th>H</th>";
	$.getJSON('groupWorkLoad.htm', {
		groupName : groupName
	}, function(data) {
		if (data.length != 0) {
			var options = [];
			$.each(data, function(item) {
				
			workLoadHTML = workLoadHTML+"<tr><td><a href=\"#\" onclick=\"getWorkLoad('"+data[item].ASSIGNED_TO+"','"+data[item].NAME+"')\" >"+data[item].NAME+"</a></td><td>"+ data[item].total+"</td><td>"+ data[item].low+"</td><td>"+ data[item].medium+"</td><td>"+ data[item].high+"</td></tr>";
			});
			workLoadHTML=workLoadHTML+"</table>";
			$('#firstDivId', window.parent.document).html(workLoadHTML); 
			myLayout.initContent('east');
			//$('#firstDivId').html(workLoadHTML);
		}
	});
}		
</script>
<% String token = null; %>
 <form>
   <div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!--<tr class="sectionHeadingBlock">
			<td align="left">Open Tickets</td>
			<td>&nbsp;&nbsp;</td>
		</tr>
		--><tr class="containerBlock1">
			<td colspan="6" align="right">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="5%" align="left" valign="middle">Group</td>
					<td width="11%" align="left" valign="middle"><select
						name="select" class="myTextInput" name="groupList"
						id="groupListId" onchange="getGroupMember()">
						</select></td>
					<td width="8%" align="right" valign="middle">Engineer</td>
					<td width="16%" align="left" valign="middle"><select
						name="groupMember" id="groupMemberId" class="myTextInput">
						<option>Select Group</option>
					</select></td>
					<td width="10%" align="left" valign="middle"><input
						type="button" value="Assign" onclick="updateAssignment()"/></td>
					<td><input id="singleSearchId" type="text" align="right"></input></td>
					<td width="3%" align="left" valign="middle"><img
						src="images/search.gif" onclick="dispSearchResult()"/></td>
					<td width="3%" align="left"><img id="goAdvSearch" title="Advance Search" src="images/arrow_dwn.png" onclick="displayDiv()"/></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	  <div id="advancedSearch" style="display: none;" >
   			<table class="advanceSearchDiv">
				<tr>
					<td>
						<table>
							<tr>
								<td class="searchQuery"> Select Query Fields </td>
								<td>	
									<select id="fieldSelectorId" name="fieldSelector" size="3" multiple="multiple" onclick="">
									</select>
								 </td>
							</tr>
						<tr></tr>
						<tr>
							<td></td>
							<td valign="middle">&nbsp;&nbsp;&nbsp;<input type="button" value="Add fields to search Query" onclick="getSelectedOptions(this.form.fieldSelector)"/></td>
					
						</tr>
						</table>
					</td>
			
				
				 <td>
				 	<table>
						<tr id="addSearchQueryROWID"></tr>
				 	
						<tr id="matchCase" style="display: none;">
				 			<td > Match :</td>
				 			<td>
				 				 <select name="secAdminSelectOption" id="searchCondID">
					 			<option>Any</option>
					 			<option>All</option>
					 			</select>
					 		</td>
				 			<td> </td><td valign="middle">&nbsp;&nbsp;&nbsp;<input type="button" value="Search" onclick="advanceSearchResult(this.form.fieldSelector)"></td>
				 		</tr>
						
				 	</table>
				 	 				 				 				 
				 </td>
			</tr>
			
		</table>
   	
   </div>
   </form>
   <% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>">
 
