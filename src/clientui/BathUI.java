/*
 * 
 */
package clientui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import client.BathClient;

/**
 * The Class BathUI.
 * 
 * @author dominic
 */
public class BathUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5769617930296992181L;

	/** The fill. */
	private JButton fill;

	/** The drain. */
	private JButton drain;

	/** The parent. */
	private final BathClient parent;

	/**
	 * Constructor for Bath UI.
	 * 
	 * @param bathClient
	 *            the bath client
	 */
	public BathUI(BathClient bathClient) {
		super(bathClient);
		parent = bathClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		drain = new JButton("Drain");
		fill = new JButton("Fill");
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		add(new JButton[] { fill, drain });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fill) {
			parent.fill();
		} else if (e.getSource() == drain) {
			parent.Drain();
		}
	}
}