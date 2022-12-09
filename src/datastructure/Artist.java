/*
 * 
 */
package datastructure;

import java.io.Serializable;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Artist.
 */
public class Artist implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8878161552553027839L;

	/** The name. */
	private final String name;

	/** The albums. */
	private final HashMap<String, Album> albums = new HashMap<String, Album>();

	/**
	 * Artist Constructor.
	 * 
	 * @param string
	 *            the string
	 */
	public Artist(String string) {
		name = string;
	}

	/**
	 * Adds an album.
	 * 
	 * @param a
	 *            the a
	 */
	public void add(Album a) {
		albums.put(a.getName(), a);
	}

	/**
	 * returns the albums.
	 * 
	 * @return the albums
	 */
	public HashMap<String, Album> getAlbums() {
		return albums;
	}

	/**
	 * gets the album name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * To String.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return name;
	}
}
