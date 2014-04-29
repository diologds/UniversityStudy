package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.domain.WishedCommodity;

/**
 * Created by Ann on 22/03/14.
 */
public class WishedCommodityBuilder
{
    private Long id;
    private Commodity commodity;
    private Customer customer;

    private WishedCommodityBuilder(){}

    public static WishedCommodityBuilder createWishedCommodity()
    {
        return new WishedCommodityBuilder();
    }
    public WishedCommodity build()
    {
        WishedCommodity wishedCommodity = new WishedCommodity();
        wishedCommodity.setId(id);
        wishedCommodity.setCommodity(commodity);
        wishedCommodity.setCustomer(customer);
        return wishedCommodity;
    }
    public WishedCommodityBuilder withId(Long id)
    {
        this.id = id;
        return  this;
    }

    public WishedCommodityBuilder with(CommodityBuilder commodityBuilder)
    {
        this.commodity = commodityBuilder.build();
        return this;
    }
    public WishedCommodityBuilder with(CustomerBuilder customerBuilder)
    {
        this.customer = customerBuilder.build();
        return this;
    }
}
