<%@ page import="lv.javaguru.java2.ishop.domain.Commodity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.domain.CommodityStorage" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Customer" %>
<%@ page import="java.util.Map" %>
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
<h3>Commodity storage list</h3>
<%
    Map<String, Object> map = (Map<String, Object>)request.getAttribute("model");
    List<Commodity> commodities = (List)map.get("commodities");
    List<CommodityStorage> storages = (List)map.get("storages");
%>

<form action="/commodityStorageEdit" method="post">
    <table border="1">
        <tr>
            <th width="40">Commodity</th>
            <th width="40">Type</th>
            <th width="40">Quantity</th>
        </tr>
        <%
           for (CommodityStorage storage : storages) {
        %>
        <tr>
            <td> <%= storage.getCommodity().getName() %>
            </td>
            <td><%=storage.getType() %>
            </td>
            <td><%=storage.getQuantity() %>
            </td>
            <td>
                <button type="submit" name="delete" value="<%=storage.getId()%>">Delete</button>
            </td>
            <td>
                <button type="submit" name="edit" value="<%=storage.getId()%>">Edit</button>
            </td>

        </tr>
        <%
            }
        %>

    </table>
</form>


<% if (request.getParameter("edit")==null){%>
<h3>Add CommodityStorage data to save into DB</h3>

<form action="/commodityStorageEdit" method="post">
    <fieldset style="width:0">
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
    <input type="submit" name="adding" value="Submit"/>

    </fieldset>
    <%= request.getAttribute("msg") %>
</form>
<%} else {
%>
<h3>Edit CommodityStorage data</h3>

<form action="/commodityStorageEdit" method="post">
    <fieldset style="width:0">
        <input type="hidden" name="idStorage" value="<%=request.getParameter("edit")%>">
        <%
            for (CommodityStorage storage : storages) {
                if (Long.parseLong(request.getParameter("edit"))== storage.getId()){

        %>
        Commodity:
        <select name="idCommodity">
            <option value="<%= storage.getCommodity().getId() %>"><%= storage.getCommodity().getName() %></option>
            <% for (Commodity commodity : commodities) {%>
            <option value="<%=commodity.getId()%>"><%=commodity.getName()%></option>
            <%}%>
        </select>
        <br />
        Storage type:
        <select name="storageType">
            <option value="<%=storage.getType() %>"><%=storage.getType() %></option>
            <option value="local">Local</option>
            <option value="remote">Remote</option>
        </select>
        <br />
        Quantity: <input type="text" name="quantity" value="<%=storage.getQuantity() %>">
        <br />
        <input type="submit" name="editing" value="Submit"/>
        <input type="submit" name="cancel" value="Cancel"/>
        <%}
        }%>
    </fieldset>
    <%= request.getAttribute("msg") %>
</form>
<%}%>
<%//}%>
</body>
</html>