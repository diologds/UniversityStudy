package domain;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTools {

	public static byte[] extractBytes(String ImageName) {
		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}
}
