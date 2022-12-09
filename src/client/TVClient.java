package client;

import clientui.TVUI;

/**
 * TV Client.
 * 
 * @author dominic
 */
public class TVClient extends Client {

	private static final String CHOOSE_CHANNEL = "Channel-";
	private static final String GET_LISTINGS = "Listings";
	private static final String VOLUME_UP = "Volume Up";
	private static final String VOLUME_DOWN = "Volume Down";

	/**
	 * Constructor for the TVClient.
	 */
	public TVClient() {
		super();
		serviceType = "tv._udp.local.";
		ui = new TVUI(this);
		name = "TV";
	}

	/**
	 * Sends a message to the server to change the channel.
	 * 
	 * @param channel
	 *            - the channel we wish to change to
	 */
	public void choose(String channel) {
		String a = sendMessage(CHOOSE_CHANNEL + channel);
		if (a.equals(OK)) {
			((TVUI) ui).setChannel(Integer.valueOf(channel));
		}
	}

	public void volumeUp() {
		String a = sendMessage(VOLUME_UP);
		if (a.equals(OK)) {
			((TVUI) ui).volumeUp();
		}
	}

	public void volumeDown() {
		String a = sendMessage(VOLUME_DOWN);
		if (a.equals(OK)) {
			((TVUI) ui).volumeDown();
		}
	}

	/**
	 * get the TV listings.
	 */
	public void getListings() {
		String a = sendMessage(GET_LISTINGS);
		ui.clearArea();
		if (a.startsWith("Channel")) {
			String[] allchannels = a.replaceFirst(OK + "-", "").split("-");

			for (String s : allchannels) {
				String[] split = s.split("%");
				ui.updateArea(split[0] + "\n" + split[1]);
			}
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new TVUI(this);
	}
}