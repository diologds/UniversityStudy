<%--
  Created by IntelliJ IDEA.
  User: Fatum
  Date: 3/6/14
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>iShop sign up</title>
</head>
<body>
<form action="/signUp" method="POST">
    <h3>Sign Up</h3>
    <table>
        <tr>
            <td>Customer name:</td><td><input type="text" name="customerName"></td>
        </tr>
        <tr>
            <td>Customer surname:</td><td><input type="text" name="customerSurname"></td>
        </tr>
        <tr>
            <td>Customer personal code:</td><td><input type="text" name="customerPersonalCode"></td>
        </tr>
        <tr>
            <td>&nbsp</td><td>&nbsp</td>
        </tr>
        <tr>
            <td>Customer email:</td><td><input type="text" name="customerEmail"></td>
        </tr>
        <tr>
            <td>Customer email confirm:</td><td><input type="text" name="customerEmailConfirm"></td>
        </tr>
        <tr>
            <td>&nbsp</td><td>&nbsp</td>
        </tr>
        <tr>
            <td>Customer phone:</td><td><input type="text" name="customerPhone"></td>
        </tr>
        <tr>
            <td>&nbsp</td><td>&nbsp</td>
        </tr>
        <tr>
            <td>Customer password:</td><td><input type="text" name="customerPassword"></td>
        </tr>
        <tr>
            <td>Customer password confirm:</td><td><input type="text" name="customerPasswordConfirm"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><font color="red">&nbsp <%= request.getAttribute("msg") %></font> </td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" name="signup" value="Sign Up"/></td>
        </tr>

    </table>
</form>


</body>
</html>
