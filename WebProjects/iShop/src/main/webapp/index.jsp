<%--
  Created by IntelliJ IDEA.
  User: Ann
  Date: 21/02/14
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Category" %>
<%@ page import="lv.javaguru.java2.ishop.domain.Commodity" %>
<%@ page import="lv.javaguru.java2.ishop.servlet.mvc.data_model.IndexData" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>--%>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>iShop</title>

    <style type="text/css">

    <%@include file="/css/index.css"%>
    <%@include file="/css/commodityList.css"%>
    <%@include file="/css/indexLayout.css"%>
    </style>

    <script type="text/javascript">
        function setBuy(type) {

              document.forms["buy"].elements["addCommodity"].value = type;
        }
    </script>
</head>
<body>


<div id="wrapper1">

    <%@include file="/static/registerMenu.jsp"%>
    <%@include file="/static/searchMenu.jsp"%>
    <%@include file="/static/navMenu.jsp"%>

</div>


<div id="wrapper2">
    <%

        IndexData data = (IndexData)request.getAttribute("model");
        List<Category> categories = data.getCategories();
        List<Commodity> commodities = data.getCommodities();

    %>

    <div id="sidebar">

        <ul id="categories">
            <%

                for (Category category: categories)
                 {
                     String link = "displayCategory?category="+category.getId();

            %>
              <li><a href="<%=link%>"> <%=category.getName()%> </a></li>
            <%
                 }
             %>
        </ul>



    </div>

    <div id="main">

        <%
            if (commodities.isEmpty())
            {
        %>
           <h3>No products found!</h3>
        <%
        }
            else
            {
        %>

        <form name="buy"  method ="post" action="index">
            <input type="hidden" name="addCommodity" value="">

            <table id="commodity" >
                <caption>Products available:</caption>
                <colgroup>
                    <col id="commodityName"/>
                    <col id="commodityPrice"/>
                    <col id="commodityBrand "/>
                    <col id="commodityRef"/>
                    <col id="buyButton"/>
                </colgroup>
                <tr>
                    <th scope="col">Product</th>
                    <th scope="col">Price</th>
                    <th scope="col">Brand</th>
                    <th scope="col">Ref No</th>
                    <th scope="col">&nbsp;</th>
                </tr>
                <%

                    for (Commodity commodity: commodities)
                    {
                        String idCommodity = commodity.getId().toString();
                        String link = "displayCommodityDescription?commodity="+idCommodity+"&type=overview";

                %>
                <tr>
                    <td><a href="<%= link %>"> <%= commodity.getName()%> </a></td>
                    <td> <%= commodity.getPrice()%> </td>
                    <td><%= commodity.getBrand() %> </td>
                    <td><%= commodity.getRef()%> </td>
                    <td>
                        <input type="submit" value="Buy" onclick="setBuy(<%= idCommodity%>);" />
                    </td>


                </tr>
                <%
                    }
                %>

            </table>
        </form>
        <%
            }
        %>

    </div>
   <%
        String servletPath = request.getServletPath();
        String hostForm = servletPath.substring(1).split("\\.")[0];
        session.setAttribute("hostForm",hostForm);

   %>
   <div id="rightbar">

        <%@include file="/dynamic/cart.jsp"%>
     </form>
    </div>


 <br/>
    <%@include file="/static/footer.jsp"%>

</div>

</body>
</html>