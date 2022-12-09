package clientui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.SecurityClient;

/**
 * The Class SecurityUI.
 * 
 * @author dominic
 */
public class SecurityUI extends ClientUI {

	private JButton checkAll;
	private JButton LockAll;
	private JButton lockWindows;
	private JButton lockDoors;
	private final SecurityClient parent;
	private static final long serialVersionUID = -1890131417596196179L;

	/**
	 * Instantiates a new security ui.
	 * 
	 * @param securityClient
	 *            the security client
	 */
	public SecurityUI(SecurityClient securityClient) {
		super(securityClient);
		parent = securityClient;
		init();
	}

	@Override
	public void addChoices(Vector<String> a) {
	}

	/**
	 * initilizes the GUI components.
	 */
	@Override
	public void init() {
		setLayout(null);
		controls = new JPanel();
		controls.setBounds(5, UIConstants.CONTROLY, UIConstants.COMPONENTWIDTH,
				50);
		controls.setLayout(new FlowLayout());
		controls.setBorder(BorderFactory.createLineBorder(Color.black));
		add(controls);
		textArea = new JTextArea();
		scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		add(scroll);
		checkAll = new JButton("Check All");
		LockAll = new JButton("Lock All");
		lockWindows = new JButton("Lock Windows");
		lockDoors = new JButton("Lock Doors");
		scroll.setBounds(5, 5, UIConstants.COMPONENTWIDTH, 340);
		add(new JButton[] { lockDoors, lockWindows, LockAll, checkAll });
	}

	@Override
	public void refresh(Vector<String> a) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == checkAll) {
			parent.checkAll();
		} else if (e.getSource() == LockAll) {
			parent.lockAll();
			parent.closeAll();
		} else if (e.getSource() == lockWindows) {
			parent.closeAll();
		} else if (e.getSource() == lockDoors) {
			parent.lockAll();
		}
	}
}
