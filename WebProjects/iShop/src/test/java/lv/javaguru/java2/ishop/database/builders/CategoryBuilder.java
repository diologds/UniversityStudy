package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 14.18.3.
 */
public class CategoryBuilder {
    private Long id;
    private String name;
    private List<Commodity> commodities = new ArrayList<Commodity>();

    private CategoryBuilder(){

    }
    public static CategoryBuilder createCategory(){
        return new CategoryBuilder();
    }

    public Category build(){
        Category category = new Category(id,name);
        category.setCommodities(commodities);
        return  category;
    }
    public CategoryBuilder withId(Long id){
        this.id=id;
        return this;
    }
    public CategoryBuilder withName(String name){
        this.name=name;
        return this;
    }
    public CategoryBuilder with( List<Commodity> commodities)
    {
        this.commodities = commodities;
        return this;
    }
}

