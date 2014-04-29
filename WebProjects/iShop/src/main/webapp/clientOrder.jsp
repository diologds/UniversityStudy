<%@ page import="lv.javaguru.java2.ishop.domain.Cart" %>
<%@ page import="lv.javaguru.java2.ishop.domain.CartItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
<%@include file="/css/clientOrder.css"%>
</style>

<html>
<head>
    <title></title>
</head>
<%
    Cart cart = (Cart) request.getSession().getAttribute("cart");
%>
<body>
<h2> Your Order </h2>
<%if (request.getAttribute("model") != null) {%>
<h3>
    <font color="red"><%=(String) request.getAttribute("model")%></font>
</h3>
<%}
    if (cart != null && cart.getItems().size() > 0) {
%>

<table>
    <thead>
        <tr>
            <th>Image</th>
            <th>Description</th>
            <th>Modification</th>
        </tr>
    </thead>
    <tbody>
    <%
        for (CartItem item : cart.getItems()) {
    %>
    <tr>
        <td>
            <img src="ImageServlet?imageName=<%=item.getImageFile()%>" alt="<%=item.getCommodity().getName()%>"
                 width="200" height="150"/>
        </td>
        <td>
            <p> Item name : <%=item.getCommodity().getName()%></p>
            <p> Amount : <%= item.getAmount()%> </p>
            <p> Price : <%= item.getCost()%></p>
        </td>
        <td>
            <br><a href="clientOrder?inc=<%= item.getCommodity().getId() %>"><input type="button" value="Add one more"></a>
            <br><a href="clientOrder?dec=<%= item.getCommodity().getId() %>"><input type="button" value="Remove one"></a>
            <br><a href="clientOrder?remove=<%= item.getCommodity().getId() %>"><input type="button" value="Remove item"></a>
        </td>
    </tr>
    </tbody>
    <% } %>
    <tfoot>
        <tr>
        Total Price : <%=cart.getTotalCost()%>
        </tr>
    </tfoot>
</table>
<p>
<%if(session.getAttribute("user") != null){%>
<form action="clientOrder" method="POST">
    <select name="deliveryType">
        <option value="null">select</option>
        <option value="toCustomerAddress">Delivery</option>
        <option value="inOffice">In office</option>
    </select>
    <input type="submit" name="checkout" value="Checkout"/>
</form>
<%} else {%>
<h2>Please login :</h2>
<form action="signIn" method="POST">
    <input type="submit" name="loginRedirection" value="Go to login page"/>
</form>

<%}%>
</p>

<%} else { %>
<h3> Your cart is empty</h3>
<%}%>

</body>
</html>