package lv.rtu.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Converter {

	public static byte[] getSoundBytes(String filename) {
		File file = new File(filename);
		InputStream fis;
		byte[] buffer = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(buffer, 0, buffer.length);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}