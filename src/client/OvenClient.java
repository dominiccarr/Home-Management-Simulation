package client;

import clientui.OvenUI;

/**
 * 
 * Oven Client.
 * 
 * @author rhys
 */
public class OvenClient extends Client {

	private final String POWER = "power";
	private final String CHANGETEMP = "change_temp";

	/**
	 * 
	 * Oven Client Constructor.
	 */
	public OvenClient() {
		super();
		serviceType = "Oven._udp.local.";
		ui = new OvenUI(this);
		name = "Oven";
	}

	public void power() {
		String a = sendMessage(POWER);
		if (a.equals(OK)) {
			ui.updateArea("Oven is on");
		} else {
			ui.updateArea("Oven is powering off");
		}
	}

	public void changeTemp(String s) {
		String a = sendMessage(CHANGETEMP + "-" + s);
		if (a.equals(OK)) {
			ui.updateArea("Temperature is being adjusted to " + s + " degrees");
		}
	}

	@Override
	public void disable() {
		super.disable();
	}
}