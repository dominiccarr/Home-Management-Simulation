package clientui;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JTextField;

import client.OvenClient;

/**
 * 
 * The Class OvenUI.
 * 
 * 
 * 
 * @author rhys
 */

public class OvenUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5318589393275157185L;
	private final Timer timer;
	private JButton power;
	private JButton temperature;
	private JButton minutes;
	private JTextField tempText;
	private JTextField minsText;
	private final OvenClient parent;
	private boolean isOn;

	/**
	 * 
	 * Constructor for OvenUI.
	 * 
	 * 
	 * 
	 * @param OvenClient
	 * 
	 *            the Oven client
	 */

	public OvenUI(OvenClient OvenClient) {
		super(OvenClient);
		parent = OvenClient;
		timer = new Timer();
		init();
		isOn = false;
	}

	@Override
	public void init() {
		super.init();
		power = new JButton("Turn on");
		temperature = new JButton("Adjust temperature");
		tempText = new JTextField(3);
		minutes = new JButton("Timer (mins)");
		minsText = new JTextField(3);
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		add(new JButton[] { power, temperature });
		controls.add(tempText);
		add(new JButton[] { minutes });
		controls.add(minsText);
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see
	 * 
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == power) {
				if (isOn) {
					power.setText("Turn on");
					parent.power();
					parent.changeTemp("0");
					isOn = false;
				} else if (!isOn) {
					power.setText("Turn off");
					parent.power();
					isOn = true;
				}
			} else if (e.getSource() == temperature) {
				if (Integer.valueOf(tempText.getText()) < 0
						|| Integer.valueOf(tempText.getText()) > 220) {
					updateArea("Please enter a temperature up to 220 degrees");
				}

				else if (!isOn) {
					updateArea("Please turn oven on before adjusting temperature");
				}

				else
					parent.changeTemp(tempText.getText());
			}

			else if (e.getSource() == minutes) {
				int mins = Integer.valueOf(minsText.getText());
				if (!isOn) {
					updateArea("Pleae turn oven on before setting timer");
				} else if (mins > 180 || mins < 0) {
					updateArea("Maximum limit on timer is 180 minutes");
				}

				else {

					timer.schedule(new TimeTask(), mins * 60000);

					if (mins > 1) {

						updateArea("Oven will power off in " + mins
								+ " minutes");

					}

					else {

						updateArea("Oven will power off in " + mins + " minute");

					}

				}

			}

		} catch (NumberFormatException n) {

			updateArea("Please enter a valid number");

		}

	}

	class TimeTask extends TimerTask {

		@Override
		public void run() {
			if (isOn) {
				power.setText("Turn on");
				parent.power();
				parent.changeTemp("0");
				isOn = false;
			}
		}
	}
}