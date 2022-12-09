/*

 * 

 */

package clientui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import client.DishwasherClient;

/**
 * 
 * The Class DishwasherUI.
 * 
 * 
 * 
 * @author rhys
 */

public class DishwasherUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5318589393275157185L;
	private final DishwasherClient parent;
	private JButton wash;
	private JComboBox cycle;
	private JComboBox temperature;
	private JLabel cycleLabel;
	private JLabel temperatureLabel;
	private boolean isRunning;

	/**
	 * 
	 * Constructor for DishwasherUI.
	 * 
	 * 
	 * 
	 * @param DishwasherClient
	 * 
	 *            the Dishwasher client
	 */

	public DishwasherUI(DishwasherClient dishwasherclient) {
		super(dishwasherclient);
		parent = dishwasherclient;
		isRunning = false;
		init();
	}

	@Override
	public void init() {
		super.init();
		wash = new JButton("Start Wash");
		temperatureLabel = new JLabel("Temperature");
		String[] temps = { "70", "65", "60", "55" };
		temperature = new JComboBox(temps);
		cycleLabel = new JLabel("Cycle type");
		String[] cycles = { "Long", "Medium", "Short" };
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		cycle = new JComboBox(cycles);
		controls.add(temperatureLabel);
		controls.add(temperature);
		controls.add(cycleLabel);
		controls.add(cycle);
		add(new JButton[] { wash });
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
		if (e.getSource() == wash) {
			if (isRunning) {
				wash.setText("Start wash");
				parent.cancel();
				updateArea("Wash cancelled");
				isRunning = false;
			} else {
				isRunning = true;
				wash.setText("Stop wash");
				parent.wash((temperature.getSelectedItem()).toString(), (cycle
						.getSelectedItem()).toString());
				updateArea("Dishwasher is starting cycle type "
						+ cycle.getSelectedItem() + " at temperature "
						+ temperature.getSelectedItem());
			}
		}
	}

	@Override
	// override in order to update ui when wash is complete
	public void updateArea(String string) {
		if (textArea.getText().equals("")) {
			textArea.append(string);
		} else {
			textArea.append("\n" + string);
		}

		if (string.equals("Wash is 100% complete")) {
			wash.setText("Start wash");
			isRunning = false;
		}
	}
}