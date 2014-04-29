package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.ImageConverter;
import lv.javaguru.java2.ishop.domain.ImageType;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.CommodityViewBuilder.createCommodityView;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;

public class CommodityViewBuilderTest {

    ImageConverter converter = new ImageConverter();

    @Test
    public void testBuild(){
        CommodityView view = createCommodityView()
                .withId(1L)
                .withPhoto(converter.getBytes("htc.jpg", ImageType.jpg))
                .withPhotoType(ImageType.jpg)
                .withGalleryPhoto(false)
                .with(createCommodity()
                        .withId(1L)
                        .withName("Z")
                        .with(createProducer()
                                .withId(1L)
                                .withName("D")
                                .withDescription("DD")
                        )
                        .with(createCategory()
                                .withId(1L)
                                .withName("Laptop")
                        )
                )
                .build();
        assertEquals(new Long(1L), view.getId());
        assertEquals(new Long(1L), view.getCommodity().getId());
        assertEquals(ImageType.jpg, view.getCommodityPhotoType());
        assertEquals(Arrays.equals(converter.getBytes("htc.jpg", ImageType.jpg), view.getCommodityPhoto()) , true);
        assertEquals(false, view.isGalleryPhoto());
        }
    }


