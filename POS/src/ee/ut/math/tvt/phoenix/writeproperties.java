package ee.ut.math.tvt.phoenix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class writeproperties {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("teamName", "Phoenix");
			properties.setProperty("teamLeader", "Antarctica");
			properties.setProperty("leaderEmail", "Nicole");
			properties.setProperty("teamMembers", "Julia Kaas, Taavi Kunto, Raina Liiva, Henry Maalinn");

			File file = new File("application.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Favorite Things");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//
	}
}
	


