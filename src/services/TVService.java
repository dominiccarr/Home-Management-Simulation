/*
 * 
 */
package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import serviceui.ServiceUI;

/**
 * The Class TVService.
 */
public class TVService extends Service {

	private int channel;
	private final int volume;

	/**
	 * Instantiates a new tV service.
	 * 
	 * @param name
	 *            the name
	 * @param location
	 *            the location
	 */
	public TVService(String name, int chan, int vol) {
		super(name, "tv._udp.local.");
		channel = chan;
		volume = vol;
		ui = new ServiceUI(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see services.Service#performAction(java.lang.String)
	 */
	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.startsWith("Channel-")) {
			String[] s = a.split("-");
			channel = Integer.valueOf(s[1]);
			sendBack("OK");
			ui.updateArea("TV Set to channel " + s[1]);
		} else if (a.equals("Volume Up")) {
			sendBack("OK");
			ui.updateArea("Volume Increased");
		} else if (a.equals("Volume Down")) {
			sendBack("OK");
			ui.updateArea("Volume decreased");
		} else if (a.equals("getinitial")) {
			sendBack("channel:" + channel + ":volume:" + volume);
		} else if (a.equals("Listings")) {
			sendBack("Channel 1%Sons Of Anarchy-Channel 2%True Blood-Channel 3%Californication-Channel 4%Star Trek-Channel 5%The X Files-Channel 6%Sport-Channel 7%News-Channel 7%LOST-Channel 8%Weather-Channel 9%Eastenders");
			ui.updateArea("Sent Listings");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	public String sendMessage(String a_message, String serverHost,
			int serverPort) {
		String msg = "";
		try {
			Socket toServer = new Socket(serverHost, serverPort);
			PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
			out.println(a_message);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					toServer.getInputStream()));
			msg = in.readLine();
			out.close();
			toServer.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return msg;
	}

	@Override
	public String getStatus() {
		return "On channel " + channel;
	}

	public static void main(String[] args) {
		new TVService("living-room-tv", 1, 20);

	}

}
