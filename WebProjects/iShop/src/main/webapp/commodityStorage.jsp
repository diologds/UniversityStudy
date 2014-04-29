<%@ page import="lv.javaguru.java2.ishop.domain.Commodity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lv.javaguru.java2.ishop.domain.CommodityStorage" %>
<%--
  Created by IntelliJ IDEA.
  User: Rita
  Date: 14.21.2
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CommodityStorage JSP</title></head>
<body>
<%
    List<Commodity> commodities = (ArrayList)request.getAttribute("model");
%>

<h3>Add CommodityStorage data to save into DB</h3>

<form action="/commodityStorage" method="POST">
    Commodity:
    <select name="idCommodity">
        <option value="0">select</option>
        <% for (Commodity commodity : commodities) {%>
        <option value="<%=commodity.getId()%>"><%=commodity.getName()%></option>
        <%}%>
    </select>
    <br />
    Storage type:
    <select name="storageType">
        <option value="0">select</option>
        <option value="local">Local</option>
        <option value="remote">Remote</option>
    </select>
    <br />
    Quantity: <input type="text" name="quantity">
    <br />
    <input type="submit" name="submit" value="Submit"/>
</form>


</body>
</html>