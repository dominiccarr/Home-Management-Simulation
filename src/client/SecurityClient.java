package client;

import java.util.Collections;
import java.util.Vector;

import javax.jmdns.ServiceInfo;

import clientui.SecurityUI;

/**
 * Security Client.
 * 
 * @author dominic
 */
public class SecurityClient extends Client {

	private static final String CLOSE_ALL = "close all";
	private static final String CLOSE = "close +";
	private static final String LOCK_ALL = "lock all";
	private static final String LOCK = "lock +";

	/**
	 * Constructor for the security client.
	 */
	public SecurityClient() {
		super();
		serviceType = "security._udp.local.";
		ui = new SecurityUI(this);
		name = "Security";
	}

	/**
	 * closes a window.
	 * 
	 * @param window
	 *            the window
	 */
	public void close(String window) {
		String a = sendMessage(CLOSE + window);
		if (a.equals(OK)) {
			ui.updateArea(window + "is closed");
		}
	}

	/**
	 * locks a window.
	 * 
	 * @param door
	 *            the door
	 */
	public void lock(String door) {
		String a = sendMessage(LOCK + door);
		if (a.equals(OK)) {
			ui.updateArea(door + "is locked");
		}
	}

	/**
	 * closes all windows.
	 */
	public void closeAll() {
		String a = sendMessage(CLOSE_ALL);
		if (a.equals(OK)) {
			ui.updateArea("All Windows locked");
		}
	}

	/**
	 * locks all doors.
	 */
	public void lockAll() {
		String a = sendMessage(LOCK_ALL);
		if (a.equals(OK)) {
			ui.updateArea("All Doors locked");
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new SecurityUI(this);
	}

	@Override
	public String sendMessage(String a_message) {
		Vector<ServiceInfo> d = new Vector<ServiceInfo>();
		d.addAll(services.values());
		Collections.shuffle(d);
		this.switchService(d.firstElement());
		return super.sendMessage(a_message);
	}

	public void checkAll() {
		String a = sendMessage("check");
		if (a.startsWith(OK)) {
			String[] s = a.split(",");
			for (int i = 1; i < s.length; i++) {
				ui.updateArea(s[i]);
			}
		}
	}
}
