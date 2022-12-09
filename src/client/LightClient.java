package client;

import clientui.LightUI;

/**
 * Light Client.
 * 
 * @author dominic
 */
public class LightClient extends Client {

	/** The ON. */
	private final String ON = "ON";

	/** The OFF. */
	private final String OFF = "OFF";

	/** The DIM. */
	private final String DIM = "DIM";

	/**
	 * Constructor for the light client.
	 */
	public LightClient() {
		super();
		serviceType = "light._udp.local.";
		ui = new LightUI(this);
		name = "Lights";
	}

	/**
	 * Sends a message to the server to turn the light on.
	 */
	public void on() {
		String a = sendMessage(ON);
		if (a.equals(OK)) {
			ui.updateArea("Light is on");
		}
	}

	/**
	 * sends a message to the server to turn the light off.
	 */
	public void off() {
		String a = sendMessage(OFF);
		if (a.equals(OK)) {
			ui.updateArea("Light is off");
		}
	}

	/**
	 * Sends a message to the service to dim the light.
	 * 
	 * @param i
	 *            the i
	 */
	public void dim() {
		String a = sendMessage(DIM);
		if (a.equals(OK)) {
			ui.updateArea("light is dimmed by 10%");
		}
	}

	@Override
	public void updatePoll(String a) {
		if (a.equals("Light is on")) {
			((LightUI) ui).on();
		}
		if (a.equals("Light is off")) {
			((LightUI) ui).turnOff();
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new LightUI(this);
	}
}
