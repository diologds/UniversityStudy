package lv.javaguru.java2.ishop.database.builders;

import org.junit.Test;

import lv.javaguru.java2.ishop.domain.Producer;

import static junit.framework.Assert.assertEquals;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class ProducerBuilderTest {

    @Test
    public void testBuild() {
        Producer producer = createProducer()
                .withId(1L)
                .withName("Z")
                .withDescription("D")
                .build();

       // assertEquals(new Long(1L), producer.getId());
        assertEquals("Z", producer.getName());
        assertEquals("D", producer.getDescription());

    }

    @Test
    public void testProducerWithDefaultProperties() {
        Producer producer = createProducer().build();

        //assertEquals(new Long(1L), producer.getId());
        assertEquals("Z", producer.getName());
        assertEquals("D", producer.getDescription());
    }


}
