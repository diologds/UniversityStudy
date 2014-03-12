package loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadConfig {

	public static String[] loader() {
		Properties prop = new Properties();
		InputStream input = null;
		String[] array = new String[2];
		try {
			input = new FileInputStream(
					"C:/Users/akovalcuks/workspace/LoginApp/config/DBConfiguration.properties");
			prop.load(input);
			array[0] = prop.getProperty("db_name");
			array[1] = prop.getProperty("db_collection");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return array;
	}
}
