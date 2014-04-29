<%@ page import="lv.javaguru.java2.ishop.domain.Category" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Commodity" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Producer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.servlet.mvc.data_model.CommodityEditData" %>
<%--
  Created by IntelliJ IDEA.
  User: Rita
  Date: 14.5.3
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iShop</title>
    <style>
        .error
        {
            color:red;
            font-size:0.8em;
        }
        .info
        {
            color:green;
        }
    </style>
</head>
<body>
<%
    CommodityEditData data = (CommodityEditData)request.getAttribute("model");
    List<Commodity> commodities = data.getCommodities();
    String dbMessage = data.getDbMessage();
    boolean failedDelete = data.isFailedDbOperation();
%>
<p>
    <%
        if (failedDelete)
        {
    %>
      <span class="error"><%= dbMessage %></span>
    <%
        }
        else
        {
    %>
    <span class="info"><%= dbMessage %></span>
    <%
        }
    %>
</p>

<p>
        <span style="font-weight: bold; font-size: 1.1em">Commodities list</span>

</p>
    <%
        if (commodities.isEmpty())
        {
    %>
     <span>Nothing to display</span>
     <p>
       <form action="commodityTask" method="post">
            <button style = "width: 80px" type="submit" name="add" value="add">Add New</button>
        </form>
     </p>
    <%
        }
        else
        {
    %>
<table border = 1>
     <tr>
        <th>Producer</th>
        <th>Category</th>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Brand</th>
        <th>Reference</th>
        <th>URL</th>
        <form action="commodityTask"method="post">
        <th >
            <button style = "width: 80px" type="submit"name="add" value="add">Add New</button></th>
        </form>
    </tr>
    <%
        for (Commodity commodity : commodities) {
    %>
    <tr>
        <td> <%= commodity.getProducer().getName() %>
        </td>
        <td><%=commodity.getCategory().getName() %>
        </td>
        <td><%=commodity.getName() %>
        </td>
        <td><%=commodity.getPrice() %>
        </td>
        <td><%=commodity.getDescription() %>
        </td>
        <td><%=commodity.getBrand() %>
        </td>
        <td><%=commodity.getRef() %>
        </td>
        <td><a href="<%=commodity.getUrl() %>"><%=commodity.getUrl()%></a>
        </td>
        <form action="commodityEdit"method="post">
        <td><button style = "width: 80px"type="submit" name="deleting"value="<%=commodity.getId()%>">Delete</button>
        </td>
        </form>
        <form action="commodityTask"method="post">
        <td>
            <button style = "width: 80px" type="submit" name="update"value="<%=commodity.getId()%>">Update</button>
        </td>
        </form>

    </tr>
    <%
        }
      }
    %>

</table>

</body>
</html>
