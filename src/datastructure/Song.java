/*
 * 
 */
package datastructure;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Song.
 */
public class Song implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2189668201477890728L;
	
	/** The name. */
	private String name;
	
	/** The duration. */
	private String duration;
	
	/** The album. */
	private String album;
	
	/** The artist. */
	private String artist;

	/**
	 * Song constructor.
	 * 
	 * @param song the song
	 * @param dur the dur
	 * @param Album the album
	 * @param artist the artist
	 */
	public Song(String song, String dur, String Album, String artist) {
		name = song;
		duration = dur;
		album = Album;
		this.artist = artist;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return name + "-" + album + "-" + artist;
		
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	
	/**
	 * Gets the album.
	 * 
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Gets the artist.
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

}
