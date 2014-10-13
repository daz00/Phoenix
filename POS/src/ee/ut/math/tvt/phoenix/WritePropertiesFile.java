package ee.ut.math.tvt.phoenix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WritePropertiesFile {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("build.revision.number", "0");
			properties.setProperty("build.minor.number", "0");
			properties.setProperty("build.major.number", "0");
			properties.setProperty("build.number", "build.major.number+.+build.minor.number+.+build.revision.number");
			

			File file = new File("version.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Version");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}