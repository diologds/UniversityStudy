<%@ page import="lv.javaguru.java2.ishop.domain.Category" %>
<%@ page import="java.util.List" %>
<html>
<head><title>editCategory JSP</title></head>
<body>
<form action="editCategory" method="POST">
<h2>Existing categories</h2>
<%
    List<Category> categories = (List<Category>) request.getAttribute("model");
    if (categories != null) {
%>
<table border="1">
    <tr>
        <th width="24">ID</th>
        <th width="80">Name</th>
        <th width="80">Delete</th>
    </tr>
    <%
        for (Category category : categories) {
    %>
    <tr>
        <td ><%= category.getId() %>
        </td>
        <td><%= category.getName() %>
        </td>
        <td>
            <input type="checkbox" name="deleteIds" value="<%= category.getId() %>" />
        </td>
    </tr>
    <%
        }
    %>
</table>
    <input type="submit" name="delete" value="Delete"/>
<%
} else {
%>
<b>Categories list is empty :(</b>
<%
    }
%>
<h3>Add new Category to save into DB</h3>

    Category name: <input type="text" name="categoryName" value="categoryName">
    <br />
    <input type="submit" name="submit" value="Submit"/>
    <br />


<%= request.getAttribute("msg") %>
</form>
</body>
</html>
