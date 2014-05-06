package lv.javaguru.java2.ishop.servlet.mvc.data_model;

import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ann on 22/02/14.
 */
public class IndexData
{

    private List<Category> categories = new ArrayList<Category>();
    private List<Commodity> commodities = new ArrayList<Commodity>();
    private List<Long> quantities = new ArrayList<Long>();


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Commodity> commodities) {
        this.commodities = commodities;
    }

    public List<Long> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Long> quantities) {
        this.quantities = quantities;
    }
}
