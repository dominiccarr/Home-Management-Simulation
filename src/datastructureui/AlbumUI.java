/*
 * 
 */
package datastructureui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import datastructure.Album;

/**
 * The Class AlbumUI.
 */
public class AlbumUI extends JPanel {

	private static final long serialVersionUID = 5641964766542246537L;
	private final JLabel details = new JLabel();
	public Album album;

	/**
	 * Instantiates a new album ui.
	 * 
	 * @param a
	 *            the a
	 * @param s
	 *            the s
	 */
	public AlbumUI(Album a) {
		album = a;
		setLayout(null);
		init();
		details.setText(a.getName());
		details.setFont(new Font("Arial", Font.PLAIN, 11));
	}

	/**
	 * sets up the GUI components.
	 */
	private void init() {
		add(details);
		details.setBounds(5, 12, 750, 25);
		setBorder(BorderFactory.createTitledBorder(album.getArtist()));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(460, 40);
	}
}