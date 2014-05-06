<%@ page import="lv.javaguru.java2.ishop.domain.Category" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Commodity" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Producer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.servlet.mvc.data_model.CommodityAddData" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: Ann
  Date: 28/03/14
  Time: 19:15
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
        .errorInput
        {
            color:red;
            font-size: smaller;
            border: none;
            width: 250px;
        }

    </style>
</head>
<body>
<%
    CommodityAddData data = (CommodityAddData)request.getAttribute("model");
    List<Category> categories = data.getCategories();
    List<Producer> producers = data.getProducers();
    String dbMessage = data.getDbMessage();
    Map<String,Object> inputData = data.getInputData();
    Producer selectedProducer = (Producer)inputData.get("producer");
    Category selectedCategory = (Category)inputData.get("category");
    Map<String,String> inputErrors = data.getInputErrors();
    boolean isNewForm = data.isNewForm();


%>

<p>

    <span class="error"><%= dbMessage %></span>

</p>

<p>
    <span style="font-weight: bold; font-size: 1.1em">Add commodity</span>
</p>

<%
    if (!isNewForm)
    {
%>

<form action="commodityTask" method="post">

    <table border=0>
        <tr>
            <td>Producer:</td>
            <td>
                <select name="idProducer">
                    <%
                        if (selectedProducer==null)
                        {
                    %>
                    <option value="0">select</option>
                    <%
                    }
                    else
                    {
                    %>
                    <option value="<%=selectedProducer.getId()%>"><%= selectedProducer.getName() %></option>
                    <%
                        }
                        for (Producer producer : producers)
                        {
                    %>
                    <option value="<%=producer.getId()%>"><%=producer.getName()%></option>
                    <%
                        }
                    %>

                </select>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("producer")%></span>
            </td>
        </tr>
        <tr>
            <td>Category:</td>
            <td>
                <select name="idCategory">
                    <%
                        if (selectedCategory==null)
                        {
                    %>
                    <option value="0">select</option>
                    <%
                    }
                    else
                    {
                    %>
                    <option value="<%=selectedCategory.getId()%>"><%= selectedCategory.getName() %></option>
                    <%
                        }
                        for (Category category : categories)
                        {
                    %>
                    <option value="<%=category.getId()%>"><%=category.getName()%></option>
                    <%
                        }
                    %>

                </select>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("category")%></span>
            </td>
        </tr>
        <tr>
            <td>Commodity name:</td>
            <td>
                <input type="text"size="35" name="name" value="<%=inputData.get("name") %>"/>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("name")%></span>
            </td>
        </tr>
        <tr>
            <td>Price:</td>
            <td>
                <input type="text" size="35"name="price" value="<%=inputData.get("price") %>"/>
            </td>
            <td>
                <span class="error"> <%=inputErrors.get("price")%></span>
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                <input type="text"size="35" name="description" value="<%=inputData.get("description") %>"/>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("description")%></span>
            </td>
        </tr>
        <tr>
            <td> Brand:</td>
            <td>
                <input type="text" size="35"name="brand" value="<%=inputData.get("brand") %>"/>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("brand")%></span>
            </td>
        </tr>
        <tr>
            <td>Reference:</td>
            <td>
                <input type="text" size="35"name="ref" value="<%=inputData.get("ref") %>"/>
            </td>
            <td>
                <span class="error"> <%=inputErrors.get("ref")%></span>
            </td>
        </tr>
        <tr>
            <td>URL:</td>
            <td>
                <input type="text" size="35"name="url" value="<%=inputData.get("url") %>"/>
            </td>
            <td>
                <span class="error"><%=inputErrors.get("url")%></span>
            </td>
        </tr>

    </table>
    <button type="submit" name="adding" value="adding">Submit</button>
    <button type="submit" name="undoAdding" value="undoAdding">Undo</button>
    <input type="submit" name="cancelAdding" value="Cancel"/>

</form>
<%
    }
%>
<%
    if (isNewForm)
    {
%>

<form action="commodityTask" method="post">

    <table border=0>
        <tr>
            <td>Producer:</td>
            <td> <select name="idProducer">
                <option value="0">select</option>
                <% for (Producer producer : producers)
                {
                %>
                <option value="<%=producer.getId()%>"><%=producer.getName()%></option>
                <%
                    }
                %>
            </select>
            </td>
        </tr>
        <tr>
            <td>Category:</td>
            <td>
                <select name="idCategory">
                    <option value="0">select</option>
                    <%
                        for (Category category : categories)
                        {
                    %>
                    <option value="<%=category.getId()%>"><%=category.getName()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>Commodity name:</td>
            <td> <input type="text"size="35" name="name" value=""/></td>
        </tr>
        <tr>
            <td>Price:</td>
            <td><input type="text" size="35"name="price" value=""/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text"size="35" name="description" value=""/></td>
        </tr>
        <tr>
            <td> Brand:</td>
            <td><input type="text" size="35"name="brand" value=""/></td>
        </tr>
        <tr>
            <td>Reference:</td>
            <td><input type="text" size="35"name="ref" value=""/></td>
        </tr>
        <tr>
            <td>URL:</td>
            <td> <input type="text" size="35"name="url" value=""/></td>
        </tr>

    </table>
    <button type="submit" name="adding" value="adding">Submit</button>
    <button type="submit" name="undoAdding" value="undoAdding">Undo</button>
    <input type="submit" name="cancelAdding" value="Cancel"/>

</form>
<%

    }
%>

</body>
</html>
