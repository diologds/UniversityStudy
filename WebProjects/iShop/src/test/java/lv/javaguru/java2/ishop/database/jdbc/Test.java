package lv.javaguru.java2.ishop.database.jdbc;
import lv.javaguru.java2.ishop.domain.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Created by Ann on 28/02/14.
 */
public class Test {
    private static Logger logger = Logger.getLogger(Test.class.getName());

        public static void main(String[] args)
        {
            UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES);
            Boolean res1 = validator.isValid("http://www.apple.com/");
            logger.log(Level.INFO, res1.toString());
        }

}
