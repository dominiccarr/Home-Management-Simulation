/*
 * 
 */
package datastructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class MusicRepository.
 */
public class MusicRepository implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6637135603032904723L;

	/** The music. */
	private final File music;

	/** The artists. */
	private final HashMap<String, Artist> artists = new HashMap<String, Artist>();

	/**
	 * Constructor.
	 * 
	 * @param a
	 *            the a
	 */
	public MusicRepository(String a) {
		music = new File(a);
		System.out.println(music.exists());
		readIn();
	}

	/**
	 * reads in the music repository from a file.
	 */
	private void readIn() {
		try {
			BufferedReader fileread = new BufferedReader(new FileReader(music));
			String line = null;
			while ((line = fileread.readLine()) != null) {
				String[] s = line.split("-");
				String Artist = s[0];
				String Album = s[1];
				String Song = s[2];
				Artist a = new Artist(Artist);
				Album b = new Album(Album, Artist);
				if (!artists.containsKey(Artist)) {
					artists.put(a.getName(), a);
				}
				a = artists.get(a.getName());

				if (!a.getAlbums().containsKey(Album)) {
					a.getAlbums().put(b.getName(), b);
				}
				b = a.getAlbums().get(Album);
				b.add(new Song(Song, "1", Album, Artist));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * returns the music.
	 * 
	 * @return the music
	 */
	public Collection<Artist> getMusic() {
		return artists.values();
	}
}
