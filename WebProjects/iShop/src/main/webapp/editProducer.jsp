<%@ page import="lv.javaguru.java2.ishop.domain.Producer" %>
<%@ page import="java.util.List" %>
<html>
<head><title>editProducer JSP</title></head>
<body>
<form action="editProducer" method="POST">
    <h2>Existing producers</h2>
    <%
        List<Producer> producers = (List<Producer>) request.getAttribute("model");
        if (producers != null) {
    %>
    <table border="1">
        <tr>
            <th width="24">ID</th>
            <th width="80">Name</th>
            <th width="200">Description</th>
            <th width="50">Delete</th>
            <th width="50">Edit</th>
        </tr>
        <%
            for (Producer producer : producers) {
        %>
        <%if (request.getParameter("edit")!=null&&request.getParameter("edit").equals(String.valueOf(producer.getId()))){%>
        <tr>
            <td > <%= producer.getId() %>
            </td>
            <td> <input type="text"  style="width:100%" name="editName" value="<%= producer.getName() %>">
            </td>
            <td> <input type="text"  style="width:100%" name="editDescription" value="<%= producer.getDescription() %>">
            </td>
        <%}else{%>
        <tr  >
            <td ><%= producer.getId() %>
            </td>
            <td><%= producer.getName() %>
            </td>
            <td><%= producer.getDescription() %>
            </td>
        <%}%>
            <td>
                <button type="submit" name="delete" value="<%=producer.getId()%>">Delete</button>
            </td>
            <td>
                <button type="submit" name="edit" value="<%=producer.getId()%>">Edit</button>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <button type="submit"  name="save">Save</button>
    <%
    } else {
    %>
    <b>Producers list is empty :(</b>
    <%
        }
    %>
    <br/>

    <fieldset style="width:0">
        <h3>Add new Producer to save into DB</h3>

        Producer name:        <input type="text" size="35" name="addName">
        <br />
        Producer description: <input type="text" size="35" name="addDescription">
        <br/>
        <input type="submit" name="add" value="Add"/>
        <br />


        <%= request.getAttribute("msg") %>
    </fieldset>
</form>
</body>
</html>

