<html>
<head>
</head>
<body>
<% String token = null; %>
${paginationData_Dept}
<% token = (String)request.getSession().getAttribute("OWASP_CSRFTOKEN"); %>
					<input type="hidden" name="OWASP_CSRFTOKEN" value="<%= token %>" />
</body>
</html>