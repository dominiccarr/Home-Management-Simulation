package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.Vector;

import javax.jmdns.ServiceInfo;

import clientui.MediaServerUI;
import datastructure.Artist;
import datastructure.MusicRepository;

/**
 * Media server client.
 * 
 * @author Dominic Carr
 */
public class MediaServerClient extends Client {

	private final String LIST = "Send List";
	private final String SEARCH = "search-";
	private final String PLAY = "play loc=";
	private final String PAUSE = "Pause";
	private final String RESUME = "Resume";

	/**
	 * Constructor for the media server client.
	 */
	public MediaServerClient() {
		super();
		serviceType = "mss._udp.local.";
		ui = new MediaServerUI(this);
		name = "Media";
	}

	/**
	 * Sends a request to the media server to play a song.
	 * 
	 * @param Song
	 *            the song
	 */
	public void play(String Song, String loc) {
		System.out.println(PLAY + loc + ":" + Song);
		String a = sendMessage(PLAY + loc + ":" + Song);
		if (a.equals(OK)) {
			ui.updateArea(Song + "is now being played");
		}
	}

	/**
	 * Sends a request to the server to pause the currently playing song.
	 */
	public void pause() {
		String a = sendMessage(PAUSE);
	}

	public void resume() {
		String a = sendMessage(RESUME);
	}

	/**
	 * Sends a message to the server requesting its music.
	 */
	public void musiclist() {
		ObjectInputStream objectIn;
		try {
			toServer = new Socket(serverHost, serverPort);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(LIST);
			objectIn = new ObjectInputStream(toServer.getInputStream());
			MusicRepository musicList = (MusicRepository) objectIn.readObject();
			((MediaServerUI) ui).buildSongUI(musicList);
			out.close();
			toServer.close();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}
	}

	public void search(String text) {
		ObjectInputStream objectIn;
		try {
			toServer = new Socket(serverHost, serverPort);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(SEARCH + text);
			objectIn = new ObjectInputStream(toServer.getInputStream());
			Vector<Artist> songs = (Vector<Artist>) objectIn.readObject();
			((MediaServerUI) ui).addSearchresults(songs);
			out.close();
			toServer.close();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}
	}

	@Override
	public String sendMessage(String a_message) {
		Vector<ServiceInfo> d = new Vector<ServiceInfo>();
		d.addAll(services.values());
		Collections.shuffle(d);
		this.switchService(d.firstElement());
		return super.sendMessage(a_message);
	}

	@Override
	public void disable() {
		super.disable();
		ui = new MediaServerUI(this);
	}

	public void stop() {
		String a = sendMessage("Stop");
		if (a.equals("Stopping song")) {

		}
	}
}
