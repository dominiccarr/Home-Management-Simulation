package clientui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.MediaServerClient;
import datastructure.Album;
import datastructure.Artist;
import datastructure.MusicRepository;
import datastructure.Song;
import datastructureui.AlbumUI;
import datastructureui.ArtistUI;
import datastructureui.SongUI;

/**
 * The Class MediaServerUI.
 * 
 * @author dominic
 */
public class MediaServerUI extends ClientUI implements MouseListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2782515456278695098L;
	private JTabbedPane tabbedpane;
	private JPanel songs;
	private JPanel artist;
	private JTextField searchfor;
	private JButton searchbutton;
	private JButton stopbutton;
	private JButton pausebutton;
	private Vector<SongUI> song;
	private final JButton back = new JButton("Back");
	private final MediaServerClient parent;
	private JButton a;
	private final String[] rooms = { "living room", "kitchen", "bedroom" };
	private final JComboBox roombox;
	private JLabel room;
	private Vector<ArtistUI> srtistuis;

	/**
	 * Instantiates a new media server ui.
	 * 
	 * @param mediaServerClient
	 *            the media server client
	 */
	public MediaServerUI(MediaServerClient mediaServerClient) {
		super(mediaServerClient);
		parent = mediaServerClient;
		roombox = new JComboBox(rooms);
		init();
	}

	@Override
	public void init() {
		setLayout(null);
		room = new JLabel("Your room:");
		add(room);
		room.setBounds(5, 5, 200, 30);
		add(roombox);
		roombox.setBounds(170, 5, 200, 30);

		controls = new JPanel();
		controls.setBounds(5, UIConstants.CONTROLY, UIConstants.COMPONENTWIDTH,
				50);
		controls.setLayout(new FlowLayout());
		controls.setBorder(BorderFactory.createLineBorder(Color.black));
		add(controls);
		textArea = new JTextArea();
		addTabbed(300, 300);
		a = new JButton("list");
		stopbutton = new JButton("Stop");
		pausebutton = new JButton("Pause");
		add(new JButton[] { back, a, stopbutton, pausebutton });
		searchfor = new JTextField();
		searchbutton = new JButton("Search");
		searchbutton.addActionListener(this);
		searchfor.setPreferredSize(new Dimension(100, 30));
		add(searchfor);
		add(searchbutton);
		searchbutton.setBounds(210, 35, 200, 30);
		searchfor.setBounds(5, 35, 200, 30);
	}

	@Override
	public void addChoices(Vector<String> a) {
	}

	@Override
	public void refresh(Vector<String> a) {
	}

	/**
	 * Adds the tabbed.
	 * 
	 * @param height
	 *            the height
	 * @param gd
	 *            the gd
	 */
	public void addTabbed(int height, int gd) {
		songs = new JPanel();
		artist = new JPanel();
		artist.setPreferredSize(new Dimension(500, 5000));
		tabbedpane = new JTabbedPane();
		add(tabbedpane);
		tabbedpane.setBounds(0, 60, UIConstants.COMPONENTWIDTH + 10, 370);
		JScrollPane scroll = new JScrollPane();
		songs.setPreferredSize(new Dimension(400, height));
		scroll.setViewportView(songs);
		tabbedpane.addTab("Songs", scroll);
		JScrollPane scroll3 = new JScrollPane();
		scroll3.setViewportView(artist);
		scroll3
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedpane.addTab("Artists", scroll3);
	}

	/**
	 * Builds the song ui.
	 * 
	 * @param s
	 *            the s
	 */
	public void buildSongUI(MusicRepository s) {
		song = new Vector<SongUI>();
		Vector<Album> adss = new Vector<Album>();
		Vector<AlbumUI> asdss = new Vector<AlbumUI>();
		srtistuis = new Vector<ArtistUI>();
		Collection<Artist> art = s.getMusic();
		for (Artist a : art) {
			adss.addAll(a.getAlbums().values());
			ArtistUI yemp = new ArtistUI(a, this);
			yemp.addMouseListener(this);
			srtistuis.add(yemp);
			for (Album dff : a.getAlbums().values()) {
				AlbumUI temp = new AlbumUI(dff);
				temp.addMouseListener(this);
				asdss.add(temp);
			}
		}
		for (Album al : adss) {
			al.getSongs();
			for (Song son : al.getSongs()) {
				SongUI temp = new SongUI(son);
				temp.addMouseListener(this);
				song.add(temp);
			}
		}

		int amount = song.size();
		remove(tabbedpane);
		addTabbed(26 * amount, 26 * asdss.size());
		for (SongUI ui : song) {
			songs.add(ui);
		}

		for (ArtistUI ui : srtistuis) {
			artist.add(ui);
		}

		this.updateUI();
	}

	/**
	 * Play.
	 * 
	 * @param a
	 *            the a
	 */
	public void play(String a) {
		parent.play(a, (String) roombox.getSelectedItem());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a) {
			parent.musiclist();
		} else if (e.getSource() == searchbutton) {
			if (!searchfor.getText().equals("")) {
				parent.search(searchfor.getText());
			}
		} else if (e.getSource() == pausebutton) {
			if (pausebutton.getText().equals("Pause")) {
				parent.pause();
				pausebutton.setText("Resume");
			} else {
				parent.resume();
				pausebutton.setText("Pause");
			}
		} else if (e.getSource() == stopbutton) {
			parent.stop();
		} else if (e.getSource() == back) {
			artist.removeAll();
			for (ArtistUI string : srtistuis) {
				artist.add(string);
			}
			updateUI();
		}

	}

	public void addSearchresults(Vector<Artist> songs2) {
		artist.removeAll();
		for (Artist string : songs2) {
			ArtistUI a = new ArtistUI(string, this);
			a.addMouseListener(this);
			artist.add(a);
		}
		updateUI();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().getClass().equals(ArtistUI.class)) {
			ArtistUI source = (ArtistUI) e.getSource();
			HashMap<String, Album> s = source.theArtist.getAlbums();
			artist.removeAll();
			for (Album a : s.values()) {
				AlbumUI d = new AlbumUI(a);
				d.addMouseListener(this);
				artist.add(d);
			}
			updateUI();
		} else if (e.getSource().getClass().equals(AlbumUI.class)) {
			System.out.println("happened");
			AlbumUI source = (AlbumUI) e.getSource();
			Vector<Song> s = source.album.getSongs();
			artist.removeAll();
			for (Song song : s) {
				SongUI d = new SongUI(song);
				d.addMouseListener(this);
				artist.add(d);
			}
			updateUI();
		} else if (e.getSource().getClass().equals(SongUI.class)) {
			SongUI source = (SongUI) e.getSource();
			String song = source.retSong().toString();
			parent.play(song, (String) roombox.getSelectedItem());
			updateUI();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
