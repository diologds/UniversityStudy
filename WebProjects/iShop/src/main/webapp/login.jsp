<%@ page import="lv.javaguru.java2.ishop.servlet.forms.LoginForm" %>
<%--
  Created by IntelliJ IDEA.
  User: nikolaylapshin
  Date: 07/03/14
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
    <style>
        <%@include file="/css/forms.css"%>
    </style>
</head>
<body>

<h1>Login Form</h1>
<%
    LoginForm loginForm = (LoginForm) request.getAttribute("model");

    if (loginForm.user != null) { %>
Welcome, <b><%=loginForm.user.getName() + " " + loginForm.user.getSurname()%>
</b>, please go to <a href="index">home page</a>.<br>
<a href="signIn?logout">Logout</a>
<% } else {
    if (loginForm.hasErrors()) { %>
<div class="errors">
    Please fix the following errors:
    <ul>
        <% for (String error : loginForm.getErrors()) { %>
        <li class="error"><%= error %>
        </li>
        <% } %>
    </ul>
</div>
<% }
    String loginError = loginForm.getError("login");
    String passwordError = loginForm.getError("password");
%>
<form method="post" action="signIn">
    <label for="login">Login</label>: <input name="login" id="login"
                                             value="<%=loginForm.login != null ? loginForm.login : "" %>"
                                             type="text">
    <span class="error"><%=loginError != null ? loginError : ""%></span><br>
    <label for="password">Password</label>: <input name="password" id="password"
                                                   value="<%=loginForm.password != null ? loginForm.password : ""%>"
                                                   type="password">
    <span class="error"><%=passwordError != null ? passwordError : ""%></span><br>
    <input type="submit" name="btnLogin" value="Log in">
</form>
<% } %>
</body>
</html>
