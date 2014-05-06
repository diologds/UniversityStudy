<%@ page import="lv.javaguru.java2.ishop.domain.CommodityView" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Commodity View</title>
</head>
<%
    List<CommodityView> commodityView = (ArrayList) request.getAttribute("model");
%>
<body>
<h2> Commodity View </h2>
<%if (commodityView!= null && commodityView.size() > 0) {%>
<table border="1" style="width:500px">
    <tr>
        <td> Commodity View ID</td>
        <td> Commodity ID</td>
        <td> Photo</td>
        <td> Commodity Photo Type</td>
    </tr>
    <%for (CommodityView view : commodityView) {%>
    <tr>
        <td><%=view.getId()%>
        </td>
        <td><%=view.getCommodity().getId()%>
        </td>
        <td><img alt="Embedded Image" src="data:image/<%=view.getCommodityPhotoType()%>;base64,<%=view.getBase64()%>"
                 width="200" height="150"/></td>
        <td><%=view.getCommodityPhotoType()%>
        </td>
    </tr>
    <% } %>
</table>
<%} else { %>
    <h3> Commodity view table is empty</h3>
<%}%>
</body>
</html>