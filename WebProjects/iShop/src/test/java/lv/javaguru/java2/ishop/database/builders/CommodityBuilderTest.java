package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lv.javaguru.java2.ishop.database.builders.ListBuilder;

import static junit.framework.Assert.assertEquals;
import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;
import static lv.javaguru.java2.ishop.database.builders.CommodityStorageBuilder.createCommodityStorage;
import static lv.javaguru.java2.ishop.database.builders.CommodityViewBuilder.createCommodityView;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class CommodityBuilderTest {

    ImageConverter converter = new ImageConverter();


    @Test
    public void testBuild() {
        Commodity commodity = createCommodity()
                .withId(1L)
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
                        .withId(1L)
                        .withName("Laptop")
                )
                .withCommodityStorages(new ListBuilder<CommodityStorage>()
                        .add(createCommodityStorage()
                                .withType(StorageType.local)
                                .withQuantity(30L)
                                .build()
                        )
                        .add(createCommodityStorage()
                                .withType(StorageType.remote)
                                .withQuantity(50L)
                                .build()
                        )

                        .getList()
                )
                .withCommodityViews(new ListBuilder<CommodityView>()
                        .add(createCommodityView()
                                .withPhoto(converter.getBytes("htc.jpg", ImageType.jpg))
                                .with(createCommodity())
                                .withPhotoType(ImageType.jpg)
                                .withGalleryPhoto(false)
                                .build()
                        )
                        .getList()
                )

                        .build();
        assertEquals(new Long(1L), commodity.getId());
        assertEquals(new Long(1L), commodity.getProducer().getId());
        assertEquals(new Long(1L), commodity.getCategory().getId());
        assertEquals("Laptop", commodity.getCategory().getName());
        //assertEquals(new CommodityStorage(null,null,StorageType.local,30L),commodity.getCommodityStorages().get(0));
        //assertEquals(new CommodityStorage(null,null,StorageType.remote,50L),commodity.getCommodityStorages().get(1));
        assertEquals(new CommodityView(new Commodity(), converter.getBytes("htc.jpg",ImageType.jpg),ImageType.jpg,false),
                     commodity.getCommodityViews().get(0));


    }

}
