package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Producer;
import org.junit.Test;

import lv.javaguru.java2.ishop.database.builders.ListBuilder;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;


/**
 * Created by Rita on 14.18.3.
 */
public class CategoryBuilderTest {

    @Test
    public void testBuild(){
        Category category = createCategory()
                .withId(1L)
                .withName("Laptop")
                .with(new ListBuilder<Commodity>()
                        .add(createCommodity()
                                .withName("Z")
                                .withPrice(new BigDecimal(55))
                                .withRef("r")
                                .withBrand("b")
                                .withURL("u")
                                .withDescription("d")
                                .with(createProducer()
                                        .withId(1L)
                                        .withName("D")
                                        .withDescription("DD")
                                )
                                .with(createCategory()
                                )
                                .build())
                        .getList())
                .build();

        assertEquals(new Long (1L), category.getId());
        assertEquals("Laptop", category.getName());
        assertEquals(new Commodity(new Producer(1L,"D","DD"),new Category(),"Z",new
                BigDecimal(55),"d","b","r","u"),category.getCommodities().get(0));
    }
}
