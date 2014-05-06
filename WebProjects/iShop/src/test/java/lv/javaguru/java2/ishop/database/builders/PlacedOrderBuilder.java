package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ann on 22/03/14.
 */
public class PlacedOrderBuilder
{
    private Long id;
    private List<OrderedCommodity> orderedCommodities = new ArrayList<OrderedCommodity>();
    private Customer customer;
    private BigDecimal total;
    private OrderStatus status;
    private OrderDeliveryType deliveryType;

    private PlacedOrderBuilder() {}

    public static PlacedOrderBuilder createPlacedOrder(){
        return  new PlacedOrderBuilder();
    }
    public PlacedOrder build()
    {
        PlacedOrder order = new PlacedOrder();
        order.setId(id);
        order.setOrderedCommodities(orderedCommodities);
        order.setCustomer(customer);
        order.setTotal(total);
        order.setStatus(status);
        order.setDeliveryType(deliveryType);
        return  order;
    }

    public PlacedOrderBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }
    public PlacedOrderBuilder with(CustomerBuilder customerBuilder)
    {
        this.customer = customerBuilder.build();
        return this;
    }
    public PlacedOrderBuilder withTotal(BigDecimal total)
    {
        this.total = total;
        return this;
    }
    public PlacedOrderBuilder withStatus(OrderStatus status)
    {
        this.status = status;
        return this;
    }
    public PlacedOrderBuilder withDeliveryType(OrderDeliveryType deliveryType)
    {
        this.deliveryType = deliveryType;
        return this;
    }
    public PlacedOrderBuilder withOrderedCommodities(List<OrderedCommodity> orderedCommodities )
    {
        this.orderedCommodities = orderedCommodities;
        return  this;
    }


}
