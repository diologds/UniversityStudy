<%@ page import="lv.javaguru.java2.ishop.domain.Cart" %>
<%@ page import="lv.javaguru.java2.ishop.domain.CartItem" %>
<%--
  Created by IntelliJ IDEA.
  User: Iwan
  Date: 14.8.3
  Time: 09:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iShop - Shopping Cart</title>
    <style type="text/css">
        <%@include file="/css/cart.css"%>

    </style>
    <script type="text/javascript">
        function setRemove(type) {

            document.forms["removeFromCart"].elements["remCommodity"].value = type;
        }
    </script>
</head>
<body>
<% String formAction = (String)request.getSession().getAttribute("hostForm"); %>

<form name="removeFromCart" action="<%=formAction%>" method="post" >
  <input type="hidden" name="remCommodity" value="">
    <div id="cart">

    <% Cart cart = (Cart) request.getSession().getAttribute("cart"); %>

    <% if (cart == null) { %>
    <%= "Cart is null :("%>
    <% } else { %>
    <div>
    <% if (cart.getItems().size() == 0) { %>
    <b>Cart is empty</b>
    <% } else { %>

      <table class="cart">
        <thead>
        <tr>
            <th colspan="5">Your choice</th>
        </tr>
        </thead>
        <tbody>
        <% int i = 1; %>
        <% for (CartItem item : cart.getItems()) {%>
        <tr class="<%= i % 2 == 0 ? "even" : "odd" %>">

            <td class="article"><%= item.getName() %>
            </td>
            <td class="price">$<%= item.getPrice() %>
            </td>
            <td class="amount">x<%= item.getAmount() %>
            </td>
            <td class="price">$<%= item.getCost() %>
            </td>
            <td class="remove">
                 <% Long id = item.getCommodity().getId(); %>
                 <input type="submit" value="Remove" onclick="setRemove(<%=id%>);" />
            </td>
        </tr>
        <% i++; %>
        <% } %>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3"><b>Total:</b></td>
            <td><b>$<%= cart.getTotalCost() %>
            </b></td>
            <td></td>
        </tr>
        </tfoot>
      </table>
    </div>
    <br/>
    <div style="margin-left: 25px;">
    <a href="clientOrder">Check out</a>
    </div>
    <%}}%>

  </div>
  </form>
 </body>
</html>
