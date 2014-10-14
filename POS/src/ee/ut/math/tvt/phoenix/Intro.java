package ee.ut.math.tvt.phoenix;

import org.apache.log4j.Logger;
import ee.ut.math.tvt.phoenix.IntroUI;

public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	
	public static void main(String args[]) {

		IntroUI ui = new IntroUI();
		ui.setVisible(true);

	log.info("Intro started");
	
	}
	
}
