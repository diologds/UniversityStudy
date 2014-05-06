<%@ page import="lv.javaguru.java2.ishop.domain.PlacedOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Customer" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="lv.javaguru.java2.ishop.domain.OrderDeliveryType" %>
<%@ page import="lv.javaguru.java2.ishop.domain.OrderStatus" %>
<%--
  Created by IntelliJ IDEA.
  User: nikolaylapshin
  Date: 23/02/14
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    HashMap<String, Object> map = (HashMap<String, Object>)  request.getAttribute("model");
    List<PlacedOrder> orders = (List<PlacedOrder>) map.get("orders");
    %>
Total <%=orders.size()%>
<% Integer i = 1; %>
<% for (PlacedOrder order: orders) { %>
<div><%=i++%>. <%=order.toString()%></div>
<% } %>
New order
<form method="get" action="placedOrder">
    Customer: <select name="customer-id">
    <%
        List<Customer> customers = (List<Customer>) map.get("customers");
        for (Customer customer: customers) {
    %>
    <option value="<%=customer.getId()%>"><%=customer.getSurname()%> <%=customer.getName()%></option>
    <%
} %>
    </select> <br>
    Total: <input type="text" name="total"><br>
    Delivery Type: <select name="delivery-type">
    <% for (OrderDeliveryType type : OrderDeliveryType.values()) { %>
    <option value="<%=type%>"><%=type.name()%></option>
    <% } %>
    </select><br>
    Status: <select name="status">
    <% for (OrderStatus status : OrderStatus.values()) { %>
    <option value="<%=status%>"><%=status.name()%></option>
    <% } %>
    </select><br>
    <input type="submit" name="btnAdd" value="Add">
</form>
</body>
</html>
