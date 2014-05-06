<%@ page import="lv.javaguru.java2.ishop.domain.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>

<h2>All orders </h2>

<input type="search" class="light-table-filter" data-table="order-table" placeholder="Filter">

<%if (request.getAttribute("message") != null) {%>
<h3>
    <font color="red"><%=(String) request.getAttribute("message")%></font>
</h3>
<%}%>

<body>
<%
    FilterInfo dataFilter = (FilterInfo) request.getAttribute("model");

    List<FilterData> filterData = (List) request.getAttribute("filterData");
%>
<p>Change Order Status: </p>

<p>

<form method="post" name="change" action="ordersView">

    Order ID:
    <select name="orderChangeID">
        <option value="null">select</option>
        <% for (PlacedOrder order : dataFilter.getOrders()) {%>
        <option value="<%=order.getId()%>"><%=order.getId()%>
        </option>
        <%}%>
    </select>

    Order Status:
    <select name="orderChangeValue">
        <option value="null">select</option>
        <option value="neworder">New</option>
        <option value="inProgress">In progress</option>
        <option value="ready">Ready</option>
        <option value="delivered">Delivered</option>
    </select>

    <input type="submit" name="submitChange" value="Change"/>

</form>
</p>

<P>Delete Order: </p>

<p>

<form method="post" name="delete" action="ordersView">

    Order ID:
    <select name="orderDeleteID">select</option>
        <option value="null">select</option>
        <% for (PlacedOrder order : dataFilter.getOrders()) {%>
        <option value="<%=order.getId()%>"><%=order.getId()%>
        </option>
        <%}%>
    </select>

    <input type="submit" name="submitDelete" value="Delete"/>

</form>
</p>

<P>Filter Data : </p>

<form method="post" name="frm" action="ordersView">

    Commodity Name:
    <select name="commodityName">
        <option value="0">select</option>
        <% for (Commodity commodity : dataFilter.getCommodities()) {%>
        <option value="<%=commodity.getId()%>"><%=commodity.getName()%>
        </option>
        <%}%>
    </select>

    Commodity Brand:
    <select name="commodityBrand">
        <option value="0">select</option>
        <% for (Commodity commodity : dataFilter.getCommodities()) {%>
        <option value="<%=commodity.getId()%>"><%=commodity.getBrand()%>
        </option>
        <%}%>
    </select>

    Order Status:
    <select name="orderStatus">
        <option value="0">select</option>
        <% for (String status : dataFilter.getOrderStatuses()) {%>
        <option value="<%=status%>"><%=status%>
        </option>
        <%}%>
    </select>

    Order Delivery Type:
    <select name="orderDeliveryType">
        <option value="0">select</option>
        <% for (String deliveryType : dataFilter.getDeliveryTypes()) {%>
        <option value="<%=deliveryType%>"><%=deliveryType%>
        </option>
        <%}%>
    </select>

    Customer e-mail:
    <select name="customerEMail">
        <option value="0">select</option>
        <% for (Customer customer : dataFilter.getCustomers()) {%>
        <option value="<%=customer.getId()%>"><%=customer.getEmail()%>
        </option>
        <%}%>
    </select>
    <input type="submit" name="find" value="Find"/>
</form>
</p>

<p>
        <%if (filterData!= null && filterData.size() > 0) {%>
<table border="1" style="width:500px" class="order-table table">
    <thead>
    <tr>
        <td> Order ID</td>
        <td> Customer Name</td>
        <td> Commodity Name</td>
        <td> Order Price </td>
        <td> Order Status </td>
        <td> Delivery type</td>
        <td> Delivery Address</td>
    </tr>
    </thead>
    <%for (FilterData data : filterData) {%>
    <tr>
        <td><%=data.getPlacedOrder().getId()%></td>
        <td><%=data.getCustomer().getName() %>  , <%=data.getCustomer().getSurname() %> </td>
        <td><%=data.getCommodity().getName()%></td>
        <td><%=data.getOrderedCommodity().getOrderedCommodityPrice()%></td>
        <td><%=data.getPlacedOrder().getStatus()%></td>
        <td><%=data.getPlacedOrder().getDeliveryType()%></td>
        <td><%=data.getPlacedOrder().getDeliveryType().getValue().equals("tocustomeraddress") ? data.getCustomer().getAddress() : "NaN" %></td>
    </tr>
    <% } %>
</table>
<%} else { %>
<h3> Order list is Empty</h3>
<%}%>

</p>

</body>

<script>

    (function(document) {
        'use strict';

        var LightTableFilter = (function(Arr) {

            var _input;

            function _onInputEvent(e) {
                _input = e.target;
                var tables = document.getElementsByClassName(_input.getAttribute('data-table'));
                Arr.forEach.call(tables, function(table) {
                    Arr.forEach.call(table.tBodies, function(tbody) {
                        Arr.forEach.call(tbody.rows, _filter);
                    });
                });
            }

            function _filter(row) {
                var text = row.textContent.toLowerCase(), val = _input.value.toLowerCase();
                row.style.display = text.indexOf(val) === -1 ? 'none' : 'table-row';
            }

            return {
                init: function() {
                    var inputs = document.getElementsByClassName('light-table-filter');
                    Arr.forEach.call(inputs, function(input) {
                        input.oninput = _onInputEvent;
                    });
                }
            };
        })(Array.prototype);

        document.addEventListener('readystatechange', function() {
            if (document.readyState === 'complete') {
                LightTableFilter.init();
            }
        });

    })(document);

</script>

</html>