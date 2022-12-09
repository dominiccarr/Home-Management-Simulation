/*
 * 
 */
package datastructureui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import datastructure.Song;

/**
 * The Class SongUI.
 */
public class SongUI extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5641964766542246537L;
	private final JLabel URLLabel = new JLabel();
	private final Song song;

	/**
	 * Instantiates a new song ui.
	 * 
	 * @param a
	 *            the a
	 * @param s
	 *            the s
	 */
	public SongUI(Song a) {
		setLayout(null);
		song = a;
		init();
		URLLabel.setText(a.getName());
		URLLabel.setFont(new Font("Arial", Font.PLAIN, 11));
	}

	/**
	 * sets up the GUI components.
	 */
	private void init() {
		add(URLLabel);
		URLLabel.setBounds(5, 13, 750, 25);
		setBorder(BorderFactory.createTitledBorder(song.getArtist() + " - "
				+ song.getAlbum()));
	}

	public Song retSong() {
		return song;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(460, 40);
	}
}