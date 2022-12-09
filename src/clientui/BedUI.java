/*
 * 
 */
package clientui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import client.BedClient;

/**
 * The Class BedUI.
 * 
 * @author dominic
 */
public class BedUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5318589393275157185L;

	/** The warm. */
	private JButton warm;

	/** The parent. */
	private final BedClient parent;

	/**
	 * Constructor for BedUI.
	 * 
	 * @param bedClient
	 *            the bed client
	 */
	public BedUI(BedClient bedClient) {
		super(bedClient);
		parent = bedClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		warm = new JButton("Warm");
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		add(new JButton[] { warm });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == warm) {
			parent.warm();
		}
	}
}