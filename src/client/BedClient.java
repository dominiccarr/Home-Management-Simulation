/*
 * 
 */
package client;

import clientui.BedUI;

/**
 * Bed Client.
 * 
 * @author dominic
 */
public class BedClient extends Client {

	private final String WARM = "Warm";
	private boolean isWarming = false;

	/**
	 * Bed Client Constructor.
	 */
	public BedClient() {
		super();
		serviceType = "bed._udp.local.";
		ui = new BedUI(this);
		name = "Bedroom";
	}

	/**
	 * sends a message to warm the bed.
	 */
	public void warm() {
		if (!isWarming) {
			String a = sendMessage(WARM);
			if (a.equals(OK)) {
				isWarming = true;
				ui.updateArea("Bed is Warming");
			}
		} else {
			ui.updateArea("Bed already Warming");
		}
	}

	@Override
	public void updatePoll(String msg) {
		if (msg.equals("Bed is 100% warmed.")) {
			isWarming = false;
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new BedUI(this);
		isWarming = false;
	}
}