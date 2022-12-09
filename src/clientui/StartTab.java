package clientui;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartTab extends JPanel {

	private static final long serialVersionUID = -3155956843416480490L;
	public String welcome = "Welcome to your Home Management System\n\n\n";
	private final String about = "\n\nTabs will be added as your homes services are detected";
	private final JLabel aboutLabel = new JLabel(about);
	private final JLabel welcomeLabel = new JLabel(welcome);
	private final JLabel imageLabel = new JLabel(new ImageIcon(
			"src/resources/house.png"));

	public StartTab() {
		setLayout(new FlowLayout());
		add(welcomeLabel);
		add(imageLabel);
		add(aboutLabel);
	}
}
