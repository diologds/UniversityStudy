<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="domain.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
    "http://www.w3.org/TR/html4/DTD/strict.dtd">
    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">
<title>User Page</title>
</head>
<body>
	<% User user = (User)session.getAttribute("user");%>
	<h1>User Name : <%=user.getName()%> </h1>
	<h1>User Surname : <%=user.getSurname()%> </h1>
	<img src="${pageContext.request.contextPath}/ImageServlet?imageName=<%=user.getImage().getFilename()%>" />
</body>
</html>