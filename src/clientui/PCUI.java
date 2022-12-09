package clientui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.PCClient;

/**
 * The Class PCUI.
 */
public class PCUI extends ClientUI {
	private static final long serialVersionUID = -4852343327422001853L;
	private final PCClient parent;
	private JButton sync;
	private JButton login;
	private JTextField user;
	private JPasswordField passwd;

	/**
	 * Instantiates a new PCUI.
	 * 
	 * @param homePCClient
	 *            the home pc client
	 */
	public PCUI(PCClient homePCClient) {
		super(homePCClient);
		parent = homePCClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		sync = new JButton("sync");
		sync.setEnabled(false);
		user = new JTextField();
		passwd = new JPasswordField();
		user.setPreferredSize(new Dimension(100, 30));
		passwd.setPreferredSize(new Dimension(100, 30));
		login = new JButton("Login");
		login.addActionListener(this);
		controls.add(user);
		controls.add(passwd);
		controls.add(login);
		add(new JButton[] { sync });
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		updateUI();
	}

	public void loggedin() {
		sync.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sync) {
			parent.syncContacts();
		}
		if (e.getSource() == login) {
			parent.login(user.getText(), passwd.getText());
		}
	}
}
