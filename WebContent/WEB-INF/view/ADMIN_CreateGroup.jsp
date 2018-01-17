<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<% String jsDirPath = (String)getServletContext().getInitParameter("jsDirPath");  String cssDirPath = (String)getServletContext().getInitParameter("cssDirPath"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<title>IConnect : Unified Service Desk for Life & Health</title>
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/iconnect.css" />
	<link type="text/css" rel="stylesheet" href="<%=cssDirPath%>/ui.all.css" />		
	<script type="text/javascript">
if(top.location.href.substring(0, top.location.href.lastIndexOf("/")+1)!=window.location.href.substring(0, window.location.href.lastIndexOf("/")+1)){
	top.location=window.location;
}
</script>
</head>
<body>
<% String token = null; %>
<br></br>
	<table border="0" cellspacing="0" cellpadding="3" id="groupCreation" width="80%" align = "center" class = "createTable">
		<tr>
			<td colspan="3" align="center"><span class="containerBlock1">Create New Group</span></td>
		</tr>	
		<tr class="creationScreenAlternateTR">
			<td class="label">
				Function<span class="red_text">* </span>:
			</td>
			<td align="left" >
				<select class="myTextInput" id="functionToAdd" name="functionToAdd" onchange="getLocationsForFunction(this)">
					<option selected="selected" value="0">--Please Select--</option>
					<c:forEach items="${type}" var="type">
						<option value="${type.CATEGORY_ID}" >${type.NAME}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">
				Group Name<span class="red_text">* </span>:
			</td>
			<td>
				<input type="text" maxlength="100" style="width:263px;height:16px" name="Group_Name" id="Group_Name" class="myTextInput" onblur="checkGroupNameAvailability()" />
				<span id="groupResultMessage" class="invalid_text"></span>
			</td>			
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">
				Group Manager<span class="red_text">* </span>:
			</td>
			<td align="left">
				<input type="text" maxlength="10" style="width:263px;height:16px" name="Group_Manager" id="Group_Manager" class="myTextInput" 
				onblur="javascript:ValidateEmpId(document.getElementById('Group_Manager').value,empResultMessage);checkForGroupManager()"/>
				<span id="empResultMessage" class="invalid_text"></span>
				<input type="hidden" name="IsValidEmpId" value="false" id="IsValidEmpId" />
			</td>
		</tr>
		<tr>
			<td class="label">
				Manager Role<span class="red_text">* </span>:
			</td>
			<td align="left" >
				<select class="myTextInput" id="managerRole" name="managerRole">
					<option selected="selected" value="0">--Please Select Function--</option>
				</select>
			</td>
		</tr>
		<tr class="creationScreenAlternateTR">
			<td class="label">
				Location<span class="red_text">* </span>:
			</td>
			<td align="left" style="overlapping: hidden">
				<select class="myTextInput" id="locationId" name="locationId">
					<option selected="selected" value="0">--Please Select Function--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">
				Auto Assignment Required :
			</td>
			<td align="left" >
				<input type="radio" checked="checked" name="autoAssignment" id="autoAssignment" value="1"/>Yes
				<input type="radio"  name="autoAssignment" id="autoAssignment" value="0"/>No
			</td>
		</tr>
		<tr>
			<td colspan="3"><span class="containerBlock1">Add Operating Window</span></td>
		</tr>
		<tr>
			<td colspan="3">
				<table align="center" border="0" cellpadding="2" width="100%" class = "myDataTable">
					<tr>
						<th>Day</th>
						<th>Start Time(IST)<span class="red_text">* </span></th>
						<th>End Time(IST)<span class="red_text">* </span></th>
						<th>Next Working Day<span class="red_text">* </span></th>
					</tr>
					<tr align="center">
						<td class="bodyText" id="mon">MON</td>
						<td>
						<select id="mon_start_hh" name="mon_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="mon_start_mm" name="mon_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>
						<select id="mon_end_hh" name="mon_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:						
						<select id="mon_end_mm" name="mon_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_mon">TUE</td>
					</tr>
					<tr align="center">
						<td class="bodyText" id="tue">TUE</td>
						<td>
						<select id="tue_start_hh" name="tue_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="tue_start_mm" name="tue_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="tue_end_hh" name="tue_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="tue_end_mm" name="tue_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_tue">WED</td>
					</tr>
					<tr align="center">
						<td class="bodyText" id="wed">WED</td>
						<td>
						<select id="wed_start_hh" name="wed_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="wed_start_mm" name="wed_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="wed_end_hh" name="wed_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="wed_end_mm" name="wed_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_wed">THU</td>
					</tr>
					<tr align="center">
						<td class="bodyText" id="thu">THU</td>
						<td>
						<select id="thu_start_hh" name="thu_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="thu_start_mm" name="thu_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="thu_end_hh" name="thu_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="thu_end_mm" name="thu_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_thu">FRI</td>
					</tr>
					<tr align="center">
						<td class="bodyText" id="fri">FRI</td>
						<td>
						<select id="fri_start_hh" name="fri_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="fri_start_mm" name="fri_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="fri_end_hh" name="fri_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="fri_end_mm" name="fri_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_fri">MON</td>
						<td id="enable_sat_td">
							<input name="enable_sat" type="image" src="<%=request.getContextPath()%>/images/addnode.jpg" 
							id="enable_sat" onclick = "isSat=true;enableOperatingWindow(sat);"/>
						</td>
					</tr>
					<tr id="sat_tr" style="display: none"  align="center">
						<td class="bodyText" id="sat">SAT</td>
						<td>
						<select id="sat_start_hh" name="sat_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="sat_start_mm" name="sat_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="sat_end_hh" name="sat_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="sat_end_mm" name="sat_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_sat">MON</td>
						<td id="enable_sun_td">
							<input name="disable_sat" type="image" src="<%=request.getContextPath()%>/images/reject.png" 
							id="disable_sun" onclick = "isSat=false;disableOperatingWindow(sat);"/>
							<input name="enable_sun" type="image" src="<%=request.getContextPath()%>/images/addnode.jpg" 
							id="enable_sun" onclick = "isSun=true;enableOperatingWindow(sun);"/>
						</td>
					</tr>
					<tr id="sun_tr" style="display: none"  align="center">
						<td  class="bodyText" id="sun">SUN</td>
						<td>
						<select id="sun_start_hh" name="sun_start_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:
						<select id="sun_start_mm" name="sun_start_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td>	
						<select id="sun_end_hh" name="sun_end_hh">
							<option selected="selected" value="">HH</option>
							<% for(int i=0; i<24; i++){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>&nbsp;:					
						<select id="sun_end_mm" name="sun_end_mm">
							<option selected="selected" value="">MM</option>
							<% for(int i=0; i<60; i=i+30){ %>
							<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						</td>
						<td class="bodyText" id="next_wrk_sun">MON</td>
						<td id="disable_sun_td">
							<input name="disable_sun" type="image" src="<%=request.getContextPath()%>/images/reject.png" 
							id="disable_sun" onclick = "isSun=false;disableOperatingWindow(sun);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3"><span class="containerBlock1">Add Group Members</span></td>
		</tr>
		<tr>
			<td colspan="3">
				<table id="groupMembers" align="center" border="0" cellpadding="2" width="100%" class = "myDataTable">
					<tr>
						<th>Employee Id<span class="red_text">* </span></th>
						<th>Role<span class="red_text">* </span></th>
						<th>Access Level<span class="red_text">* </span></th>
						<th>Assignment Required</th>
					</tr>
					<tr id="engineer1">
						<td>
							<input type="text" maxlength="10" name="Engineer1" id="Engineer1" style="border:1px solid #4598b5;" onblur="checkEngineer(this);"/>
						</td>
						<td>
							<select id="role1" name="role1" style="border:1px solid #4598b5;">
								<option selected="selected" value="0">Please Select Function</option>
							</select>
						</td>
						<td>
							<select id="roleAccess1" name="roleAccess1" style="border:1px solid #4598b5;">
								<option selected="selected" value="0">Please Select Function</option>
							</select>
						</td>
						<td>
							<input type="checkbox" name="assignment1" id="assignment1" checked="checked" style="border:1px solid #4598b5;"/>
						</td>
						<td id="td_addEngineer1">
							<input name="addEngineer1" type="image" src="<%=request.getContextPath()%>/images/addnode.jpg" 
							id="addEngineer1" onclick = "isChange=true;addEngineer(this);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center" id="saveTD">
				<input type="button" value="Save" id="save"/>
				<input type="button" value="Cancel" id="cancel"/>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript" src="<%=jsDirPath%>/jquery-1.4.2.min.js"></script>	
	<script type="text/javascript" src="<%=jsDirPath%>/custom/ADMIN_CreateGroup.js"></script>
	<script type="text/javascript">
		var isChange=false;
		var isSat=false;
		var isSun=false;
	</script>
				<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
						<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>