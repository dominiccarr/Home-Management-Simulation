/*
 * 
 */
package services;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import serviceui.MediaServerServiceUI;
import datastructure.Artist;
import datastructure.MusicRepository;
import datastructure.Speaker;
import datastructure.SpeakerList;

/**
 * The Class MediaServerService.
 */
public class MediaServerService extends Service {

	/** The music respository. */
	private final MusicRepository musicRespository = new MusicRepository(
			"src/resources/music.txt");

	/** The threads. */
	private List<StreamMusic> threads;

	/** The Constant SPEAKER_SERVICE_TYPE. */
	private static final String SPEAKER_SERVICE_TYPE = "speaker._udp.local."; // speakers
	// type
	/** The speakers list. */
	private final SpeakerList speakersList;

	private final MediaServerServiceUI ui;

	/**
	 * Instantiates a new media server service.
	 * 
	 * @param name
	 *            the name
	 */
	public MediaServerService(String name) {
		super(name, "mss._udp.local.");
		speakersList = new SpeakerList();
		threads = new LinkedList<StreamMusic>();
		ui = new MediaServerServiceUI(this, name);
		try {
			discoverSpeakers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Discover speakers.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void discoverSpeakers() throws IOException {
		jmdns.addServiceListener(SPEAKER_SERVICE_TYPE, new SampleListener());
	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.startsWith("play")) {
			int i = a.indexOf("loc=");
			i += "loc=".length();
			int j = a.indexOf(":");
			String location = a.substring(i, j);
			String s[] = a.split("-");
			sendBack("Playing " + s[0]);
			try {
				for (String d : s) {
					ui.updateArea(d);
				}
				playSong(a.substring(j + 1), location);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (a.equals("Stop")) {
			stopStream();
			sendBack("Stopping song");
		} else if (a.startsWith("search-")) {
			String[] d = a.split("-");
			search(d[1]);

		} else if (a.equals("Pause")) {
			pauseStream();
			sendBack("Pausing song");
		} else if (a.equals("Resume")) {
			resumeStream();
			sendBack("Resuming song");
		} else if (a.equals("Send List")) {
			sendList();
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	private void search(String string) {
		Vector<Artist> has = new Vector<Artist>();
		for (Artist a : musicRespository.getMusic()) {
			if (a.getName().contains(string)) {
				has.add(a);
			}
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(has);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			ui.updateArea("Couldn't send Results");
		}
	}

	/**
	 * provides a Vector of music to the client.
	 */
	private void sendList() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(musicRespository);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			ui.updateArea("Couldn't send music");
		}
	}

	/**
	 * Stream to.
	 * 
	 * @param details
	 *            the songs details
	 * @param loc
	 *            the location of the speakers to play to
	 * @throws UnknownHostException
	 *             the unknown host exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void streamTo(String details, String loc)
			throws UnknownHostException, IOException {
		for (Speaker s : speakersList.returnList()) {
			if (s.retLoc().equals(loc)) {
				new SendMessage(s.retIp(), s.retPort(), details);
				StreamMusic sm = new StreamMusic(s.retIp(), s.retPort());
				sm.start();
				threads.add(sm);
			}
		}
	}

	/**
	 * Removes the threads.
	 */
	private void removeThreads() {
		threads = new LinkedList<StreamMusic>();
	}

	/**
	 * Play song.
	 * 
	 * @param d
	 *            the d
	 * @param ls
	 *            the ls
	 * 
	 * @throws UnknownHostException
	 *             the unknown host exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void playSong(String d, String loc) throws UnknownHostException,
			IOException {
		streamTo(d, loc);
	}

	/**
	 * Stop stream.
	 */
	public void stopStream() {
		/* Go through list and stop and delete threads */
		if (threads.size() > 0) {
			for (StreamMusic sm : threads) {
				sm.stopStream();
			}
			removeThreads();
		} else {
			ui.updateArea("No streams to stop");
		}
	}

	/**
	 * Pause stream.
	 */
	public void pauseStream() {
		/* Stop but don't delete thread */
		if (threads.size() > 0) {
			for (StreamMusic sm : threads) {
				sm.pauseStream();
			}
		} else {
			System.out.println("No streams to pause");
		}
	}

	/**
	 * Resume stream.
	 */
	public void resumeStream() {
		if (threads.size() > 0) {
			for (StreamMusic sm : threads) {
				sm.resumeStream();
			}

		} else {
			ui.updateArea("No streams to resume");
		}
	}

	/**
	 * The Class StreamMusic.
	 */
	private class StreamMusic extends Thread {

		/** The port. */
		final private int port;

		/** The ip. */
		final private String ip;

		/** The play. */
		private boolean play;

		/** The clength. */
		private int clength;

		/** The length. */
		private final int length;

		/** The paused. */
		private boolean paused;

		/**
		 * Instantiates a new stream music.
		 * 
		 * 
		 * @param ip
		 *            the ip
		 * @param port
		 *            the port
		 * 
		 * @throws UnknownHostException
		 *             the unknown host exception
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		public StreamMusic(String ip, int port) throws UnknownHostException,
				IOException {
			this.ip = ip;
			this.port = port;
			this.play = true;
			this.clength = 0;
			this.length = 200;
			paused = false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			/* Send 1 char to speaker to let it know stream is still going */
			long seconds = (new Date()).getTime();
			while (play && clength <= length) {
				try {
					if (!paused) {
						long seconds2 = (new Date()).getTime();
						long throttle = 1000;
						/* Throttles sending */
						long total = seconds + throttle;
						if (seconds2 >= total) {
							Socket s = new Socket(ip, port);
							PrintWriter out = new PrintWriter(s
									.getOutputStream(), true);
							out.print("p");
							out.close();
							s.close();
							seconds = seconds2;
							clength++;
							ui.updateArea("update");
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (clength >= length) {
				removeThreads();
			}
		}

		/**
		 * Pause stream.
		 */
		public void pauseStream() {
			System.out.println("Paused");
			paused = true;
			new SendMessage(ip, port, "Paused");
			ui.updateArea("Pause");
		}

		/**
		 * Resume stream.
		 */
		public void resumeStream() {
			paused = false;
			new SendMessage(ip, port, "Resume");
		}

		/**
		 * Stop stream.
		 */
		public void stopStream() {
			play = false;
			ui.updateArea("Stop");
			new SendMessage(ip, port, "Stop");
		}
	}

	// Using his service listener class for testing
	/**
	 * The listener interface for receiving sample events. The class that is
	 * interested in processing a sample event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addSampleListener<code> method. When
	 * the sample event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see SampleEvent
	 */
	private class SampleListener implements ServiceListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.jmdns.ServiceListener#serviceAdded(javax.jmdns.ServiceEvent)
		 */
		public void serviceAdded(final ServiceEvent event) {
			System.out.println("Service added   : " + event.getName() + "."
					+ event.getType());
			event.getDNS().requestServiceInfo(event.getType(), event.getName(),
					0);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.jmdns.ServiceListener#serviceRemoved(javax.jmdns.ServiceEvent)
		 */
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed : " + event.getName() + "."
					+ event.getType());
			/* remove ip/port for speaker */
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.jmdns.ServiceListener#serviceResolved(javax.jmdns.ServiceEvent)
		 */
		public void serviceResolved(ServiceEvent event) {
			// Display some information about the service.
			System.out.println("Service resolved: " + event.getInfo().getName()
					+ ", host: " + event.getInfo().getHostAddress()
					+ ", port: " + event.getInfo().getPort());
			/* Add all found speakers to a list */
			String name = event.getInfo().getName();
			String ip = event.getInfo().getHostAddress();
			int port = event.getInfo().getPort();
			String location = event.getInfo().getTextString();
			int i = location.indexOf("Location=");
			i += "Location=".length();
			speakersList.addSpeaker(new Speaker(name, ip, port, location
					.substring(i)));
		}
	}

	@Override
	public String getStatus() {
		return "Media Server Online";
	}

	public static void main(String[] args) {
		new MediaServerService("Living room server");

	}
}