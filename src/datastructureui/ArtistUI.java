package datastructureui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clientui.MediaServerUI;
import datastructure.Artist;

/**
 * The Class AlbumUI.
 */
public class ArtistUI extends JPanel {
	private static final long serialVersionUID = 5641964766542246537L;
	private final JLabel details = new JLabel();
	public Artist theArtist;

	/**
	 * Instantiates a new album ui.
	 * 
	 * @param a
	 *            the a
	 * @param s
	 *            the s
	 */
	public ArtistUI(Artist a, MediaServerUI s) {
		theArtist = a;
		setLayout(null);
		init();
		details.setText(a.toString());
		details.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	/**
	 * sets up the GUI components.
	 */
	private void init() {
		add(details);
		details.setBounds(5, 5, 750, 25);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(460, 40);
	}
}