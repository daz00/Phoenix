package ee.ut.math.tvt.phoenix;

//import com.jgoodies.looks.windows.WindowsLookAndFeel;

import javax.swing.JFrame;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

// GUI 

public class IntroUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(IntroUI.class);

	public IntroUI() {

		

		setTitle("Intro");

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("attributes.properties");

			// load a properties file
			prop.load(input);

			// get the property values 

			JLabel name = new JLabel("Team name: "+ prop.getProperty("teamName"));
			JLabel leader = new JLabel("Team leader: "+ prop.getProperty("teamLeader"));
			JLabel email = new JLabel("Team name: "+ prop.getProperty("leaderEmail"));
			JLabel members = new JLabel("Team members: "+ prop.getProperty("teamMembers"));
			ImageIcon logo = new ImageIcon(prop.getProperty("teamLogo"));
			
			JLabel teamlogo = new JLabel(logo);
			teamlogo.setIcon(logo);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(name);
			panel.add(leader);
			panel.add(email);
			panel.add(members);
			
			
			
	add(panel);
			pack();


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


	}


}


