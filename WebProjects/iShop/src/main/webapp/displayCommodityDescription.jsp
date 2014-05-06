<%@ page import="lv.javaguru.java2.ishop.servlet.mvc.data_model.DisplayCommodityDescriptionData" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.ishop.domain.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Ann
  Date: 27/02/14
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>iShop</title>

    <style type="text/css">

        <%@include file="/css/index.css"%>
        <%@include file="/css/displayDesc.css"%>
        <%@include file="/css/commodityOverview.css"%>
        <%@include file="/css/commodityDetails.css"%>
        <%@include file="/css/commodityComments.css"%>
        <%@include file="/css/indexLayout.css"%>
    </style>


    <script type="text/javascript">

        function setBuy(type) {

            document.forms["buy"].elements["addCommodity"].value = type;
        }
        function setDisplay(idCommodity) {

            document.forms["buy"].elements["commodity"].value = idCommodity;

        }
        function setType(reqType)
        {
            document.forms["buy"].elements["type"].value = reqType;
        }
        function setWishList(idCommodity) {

            document.forms["addToWishList"].elements["addCommodity"].value = idCommodity;
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
        String reqType = request.getParameter("type");

        DisplayCommodityDescriptionData  data = (DisplayCommodityDescriptionData )request.getAttribute("model");
        List<Category> categories = data.getCategories();
        List<Comment> comments = data.getComments();
        //List<Like> likes = data.getLikes();
        Commodity commodity = data.getCommodity();
        String producerName = data.getProducer().getName();
        Long quantity = data.getQuantity();
        List<String> imageNames = data.getImageNames();
        //Customer customer = (Customer) request.getSession().getAttribute("user");

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
        <div id="gallery">

         <%


             for (String imageName: imageNames)
                     {


         %>

           <div class="photo">
               <%--<img src="${pageContext.request.contextPath}/ImageServlet?imageName=<%=imageName%>" alt="<%=imageName%>"/>--%>
               <img src="ImageServlet?imageName=<%=imageName%>" alt="<%=imageName%>"/>
           </div>

         <%
                      }

         %>
        </div>



               <div id="action">
                   <div id="price">
                       <%=commodity.getPrice()%> &euro;
                   </div>
                   <%
                       if (quantity==0)
                       {
                   %>

                       <h5>Product is out of stock!</h5>
                   <%
                       }
                       else
                       {

                   %>
                  <form name="buy"  method ="post" action="displayCommodityDescription">
                     <input type="hidden" name="addCommodity"/>
                     <input type="hidden" name="commodity" value="<%= commodity.getId()%>" />
                     <input type="hidden" name="type" value="<%= reqType%>"/>


                     <input type="submit" name="buy" value="Buy" onclick="setBuy(<%= commodity.getId()%>)" />

                   </form>
                   <%
                       }

                   %>
                   <%--
                   <form name="likeForm" method="post" action="displayCommodityDescription">
                       <%if (customer==null) {%>
                       <input type="hidden" name="addLike" value="<%=commodity.getId()%>"/>
                       <input type="hidden" name="idCommodity" value="<%=commodity.getId()%>"/>
                       <input type="submit" name="like" value="Like (<%=likes.size()%>)"/>
                       <%}%>
                   </form>
                    --%>

                   <form name="addToWishList"  method ="post" action="displayCommodityDescription">
                       <input type="hidden" name="addCommodity"/>
                       <input type="submit" name="wishList" value="Add to Wish List" onclick="setWishList(<%= commodity.getId()%>)" />
                   </form>


               </div>
        <%

            String overviewLink = "displayCommodityDescription?commodity="+commodity.getId()+"&type=overview";
            String detailsLink = "displayCommodityDescription?commodity="+commodity.getId()+"&type=details";
            String commentsLink = "displayCommodityDescription?commodity="+commodity.getId()+"&type=comments";
        %>
        <div id="desc">
            <div id="descNav">
                <ul>
                    <li><a href="<%=overviewLink%>" name="commodityOverview">Overview</a></li>
                    <li><a href="<%=detailsLink%>" name="commodityDetails">Details</a></li>
                    <li><a href="<%=commentsLink%>" name="commodityComments">Comments (<%=comments.size()%>)</a></li>
                </ul>
            </div>
            <%
            if (reqType.equals("overview"))
            {
            %>
              <table id="commodityOverview">
                    <colgroup>
                     <col id="commodityOverviewCol1"/>
                        <col id="commodityOverviewCol2"/>
                    </colgroup>
                    <tr>
                         <td>Name:</td>
                         <td><%=commodity.getName()%></td>
                    </tr>
                    <tr>
                         <td>In Stock:</td>
                         <td><%=quantity%></td>
                    </tr>
                    <tr>
                         <td>Ref:</td>
                         <td><%=commodity.getRef()%></td>
                    </tr>
                     <tr>
                        <td>Producer:</td>
                        <td><a href="<%=commodity.getUrl()%>"><%=producerName%></a></td>
                    </tr>
                    <tr>
                        <td>Brand:</td>
                        <td><%=commodity.getBrand()%></td>
                    </tr>

                </table>
            <%
            }
            if (reqType.equals("details"))
            {
            %>
                <div id="commodityDetails">
                     <p><%=commodity.getDescription()%></p>
                </div>
            <%
             }
             if (reqType.equals("comments"))
             {
            %>
            <div id="commodityComments">
                <form name="commentForm"  method ="post" action="displayCommodityDescription">
                    <h3>Type your comment here:</h3>
                    <textarea name="comment" cols="40" rows="3"></textarea>
                    <input type="hidden" name="commodity" value="<%=commodity.getId()%>"/>
                    <input type="hidden" name="type" value="<%=reqType%>"/>
                    <input type="hidden" name="addComment" value="<%=commodity.getId()%>"/>
                    </br>
                    <input type="submit" value="Add Comment"/>
                    <input type="reset" value="Clear">
                    </br>
                    </br>
                </form>
                <table>
                    <%

                        for (lv.javaguru.java2.ishop.domain.Comment c : comments) {
                    %>
                    <tr>
                        <hr>
                        <%if (c.isLoggedIn()) {%>
                        <font size="-1">By <%= c.getName()%></font>
                        <%}else {%>
                        <font size="-1">By <%= c.getName() + c.getId() %></font>
                        <%}%>
                        <p><%= c.getComment() %></p>
                        <font size="-2"><%= c.getDate() %></font>
                        </br>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <%
                }
            %>

        </div>


    </div>
    <%
        String servletPath = request.getServletPath();
        String[] nameParts = servletPath.split("\\.");
        String servletName = nameParts[0];
        String pageAddress = request.getContextPath()+servletName+"?"+ "type=" +reqType+ "&" +
                                     "commodity=" + commodity.getId() + "&";
        session.setAttribute("hostForm", pageAddress);


    %>
    <div id="rightbar">
        <%@include file="/dynamic/cart.jsp"%>
    </div><br />
    <%@include file="/static/footer.jsp"%>

</div>


</body>
</html>
