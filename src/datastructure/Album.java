/*
 * 
 */
package datastructure;

import java.io.Serializable;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class Album.
 */
public class Album implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3544922374786031231L;
	
	/** The songs. */
	private Vector<Song> songs = new Vector<Song>();
	
	/** The name. */
	private String name;
	
	/** The artist. */
	private String artist;
	
	/**
	 * Constructor.
	 * 
	 * @param album the album
	 * @param Artist the artist
	 */
	public Album(String album, String Artist) {
		name = album;
		this.artist = Artist;
	}

	/**
	 * gets the songs.
	 * 
	 * @return the songs
	 */
	public Vector<Song> getSongs() {
		return songs;
	}

	/**
	 * Adds a song.
	 * 
	 * @param a the a
	 */
	public void add(Song a) {
		songs.add(a);
	}

	/**
	 * gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * gets the artist.
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return artist + ": " + name + " " + songs.size() + " Tracks";
	}

}
