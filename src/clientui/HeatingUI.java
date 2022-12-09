/*
 * 
 */
package clientui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JSlider;

import client.HeatingClient;

// TODO: Auto-generated Javadoc
/**
 * The Class HeatingUI.
 * 
 * @author dominic
 */
public class HeatingUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 293512545260683382L;

	/** The lower temp. */
	private JButton lowerTemp;

	/** The raise temp. */
	private JButton raiseTemp;

	private JSlider slider;

	/** The parent. */
	private final HeatingClient parent;

	/**
	 * Instantiates a new heating ui.
	 * 
	 * @param heatingClient
	 *            the heating client
	 */
	public HeatingUI(HeatingClient heatingClient) {
		super(heatingClient);
		parent = heatingClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		slider = new JSlider();
		slider.setMaximum(20);
		lowerTemp = new JButton("Lower");
		raiseTemp = new JButton("Raise");
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		add(new JButton[] { lowerTemp, raiseTemp });
		controls.add(slider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == lowerTemp) {
			parent.lowerTemp(slider.getValue());
		} else if (e.getSource() == raiseTemp) {
			parent.raiseTemp(slider.getValue());
		}
	}

}
