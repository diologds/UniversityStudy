<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="domain.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Page</title>
</head>
<body>
	<% User user = (User)session.getAttribute("user");%>
	
	<h1>User Name : <%=user.getName()%> </h1>
	<h1>User Surname : <%=user.getSurname()%> </h1>
	
	<% String image = (String) request.getAttribute("image"); %>
	<% System.out.println("Image : " + image); %>
	
	<img alt="Embedded Image" src="data:image/jpeg;base64,<%=image%>" />
</body>
</html>