package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class CommodityBuilder {

    private Long id;
    private String name;
    private Producer producer;
    private Category category;
    private List<CommodityView> commodityViews = new ArrayList<CommodityView>();
    private List<CommodityStorage> commodityStorages = new ArrayList<CommodityStorage>();
    private List<WishedCommodity> wishedCommodities = new ArrayList<WishedCommodity>();
    private List<OrderedCommodity> orderedCommodities = new ArrayList<OrderedCommodity>();
    private String brand;
    private BigDecimal price;
    private String description;
    private String ref;
    private String url;

    private CommodityBuilder() {
    }

    public static CommodityBuilder createCommodity() {
        return new CommodityBuilder();
    }

    public Commodity build() {
        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setName(name);
        commodity.setProducer(producer);
        commodity.setCategory(category);
        commodity.setCommodityStorages(commodityStorages);
        commodity.setCommodityViews(commodityViews);
        commodity.setWishedCommodities(wishedCommodities);
        commodity.setOrderedCommodities(orderedCommodities);
        commodity.setBrand(brand);
        commodity.setDescription(description);
        commodity.setPrice(price);
        commodity.setRef(ref);
        commodity.setUrl(url);
        return commodity;
    }

    public CommodityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommodityBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public CommodityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }
    public CommodityBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }
    public CommodityBuilder withRef(String ref) {
        this.ref = ref;
        return this;
    }
    public CommodityBuilder withURL(String url) {
        this.url = url;
        return this;
    }
    public CommodityBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CommodityBuilder with(ProducerBuilder producerBuilder) {
        this.producer = producerBuilder.build();
        return this;
    }
    public CommodityBuilder with(CategoryBuilder categoryBuilder) {
        this.category = categoryBuilder.build();
        return this;
    }
    public CommodityBuilder withCommodityViews(List<CommodityView> commodityViews)
    {
        this.commodityViews = commodityViews;
        return  this;
    }
    public CommodityBuilder withCommodityStorages(List<CommodityStorage> commodityStorages)
    {
        this.commodityStorages = commodityStorages;
        return  this;
    }
    public CommodityBuilder withWishedCommodities(List<WishedCommodity> wishedCommodities)
    {
        this.wishedCommodities = wishedCommodities;
        return  this;
    }
    public CommodityBuilder withOrderedCommodities(List<OrderedCommodity> orderedCommodities)
    {
        this.orderedCommodities = orderedCommodities;
        return  this;
    }

}
