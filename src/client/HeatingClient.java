package client;

import clientui.HeatingUI;

/**
 * Heating Client.
 * 
 * @author dominic
 */
public class HeatingClient extends Client {

	private final String RAISE = "raise-";
	private final String LOWER = "lower-";

	/**
	 * Constructor for the heating client.
	 */
	public HeatingClient() {
		super();
		serviceType = "heating._udp.local.";
		ui = new HeatingUI(this);
		name = "Heating";
	}

	/**
	 * Sends a request to the server to increase the temp.
	 * 
	 * @param amount
	 *            the amount
	 */
	public void raiseTemp(int amount) {
		String a = sendMessage(RAISE + amount);
		if (a.matches(OK + "-.*")) {
			String[] s = a.split("-");
			ui.updateArea("Temp is being raised " + s[1] + " degrees");
		}

	}

	/**
	 * Sends a request to the server to decrease the temp.
	 * 
	 * @param amount
	 *            the amount
	 */
	public void lowerTemp(int amount) {
		String a = sendMessage(LOWER + amount);
		if (a.matches(OK + "-.*")) {
			String[] s = a.split("-");
			ui.updateArea("Temp is being lowered " + s[1] + " degrees");
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new HeatingUI(this);
	}
}
