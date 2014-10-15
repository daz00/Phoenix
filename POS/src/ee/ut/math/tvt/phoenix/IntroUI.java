package ee.ut.math.tvt.phoenix;

//import com.jgoodies.looks.windows.WindowsLookAndFeel;

import javafx.scene.shape.Box;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;




// GUI 

public class IntroUI extends JFrame {

	private static final long serialVersionUID = 1L;


	public IntroUI() {


		setTitle("Intro");

		Properties prop = new Properties();
		Properties prop2 = new Properties();
		InputStream input = null;
		InputStream input2 = null;

		try {

			input = new FileInputStream("attributes.properties");
			input2 = new FileInputStream("version.properties");

			// load the .properties files
			prop.load(input);
			prop2.load(input2);

			DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			

			// get the .properties values 

			JLabel name = new JLabel("Team name: "+ prop.getProperty("teamName"));
			JLabel leader = new JLabel("Team leader: "+ prop.getProperty("teamLeader"));
			JLabel email = new JLabel("Team name: "+ prop.getProperty("leaderEmail"));
			JLabel members = new JLabel("Team members: "+ prop.getProperty("teamMembers"));
			JLabel version = new JLabel("Software version: "
					+ prop2.getProperty("build.major.number") + "."
					+ prop2.getProperty("build.revision.number") + "."
					+ prop2.getProperty("build.minor.number"));
			JLabel time = new JLabel(dateFormat.format(cal.getTime()));
			
			// Open the link where the logo is (We should probably move it to lib at some point)
			URL logo_url = new URL(prop.getProperty("teamLogo"));
			BufferedImage image = ImageIO.read(logo_url);
			JLabel logo = new JLabel(new ImageIcon(image));
			
			
			

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(logo);
			panel.add(name);
			panel.add(leader);
			panel.add(email);
			panel.add(members);
			panel.add(version);
			panel.add(time);
			
			
			
			
			
			
			
			
			
			
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


