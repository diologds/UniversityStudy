<%@ page import="lv.javaguru.java2.ishop.domain.Category" %>
<%@ page import="java.util.List" %>
<html>
<head><title>Category JSP</title></head>
<body>
<h3>List of categories</h3>
<%
    List<Category> categories = (List<Category>) request.getAttribute("model");
    if (categories != null) {
%>
<table border="1">
    <tr>
        <th width="24">ID</th>
        <th width="80">Name</th>
        <%--<th></th>--%>
    </tr>
    <%
        for (Category category : categories) {
    %>
    <tr>
        <td><%= category.getId() %>
        </td>
        <td><%= category.getName() %>
        </td>
        <%--<td>
            <a href="#" onclick="<% System.out.println("Delete link clicked..."); %>">delete</a>
        </td>--%>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<b>Categories list is empty :(</b>
<%
    }
%>

</body>
</html>