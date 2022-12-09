/*
 * 
 */
package clientui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import client.ClientManager;

/**
 * The Class ClientManagerUI.
 * 
 * @author dominic
 */
public class ClientManagerUI extends JFrame {

	/** The all panels. */
	private final JTabbedPane allPanels;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4512962459244007477L;

	/**
	 * Constructor for the GUI.
	 * 
	 * @param clientManager
	 *            the client manager
	 */
	public ClientManagerUI(final ClientManager clientManager) {
		super("Home Management");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clientManager.end();
			}
		});
		setResizable(false);
		pack();
		setSize(UIConstants.UIWIDTH, UIConstants.UIWIDTH);
		setLocation(setPosition(this));
		allPanels = new JTabbedPane();
		allPanels.add("Begin", new StartTab());
		getContentPane().add(allPanels);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		update(this.getGraphics());
	}

	/**
	 * positions the JFrame in the center of the screen.
	 * 
	 * @param c
	 *            the c
	 * 
	 * @return the point
	 */
	public static Point setPosition(Component c) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - c.getWidth()) / 2);
		return new Point(x, 0);
	}

	/**
	 * Adds a panel to tabbed pane.
	 * 
	 * @param a
	 *            the a
	 * @param name
	 *            the name
	 */
	public void addPanel(JPanel a, String name) {
		allPanels.addTab(name, a);
	}

	/**
	 * removes a panel
	 * 
	 * @param returnUI
	 */
	public void removePanel(JPanel returnUI) {
		allPanels.remove(returnUI);
	}
}