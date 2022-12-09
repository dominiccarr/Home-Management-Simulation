package client;

import clientui.KettleUI;

/**
 * Kettle Client
 * 
 * @author dominic
 * 
 */
public class KettleClient extends Client {

	private final String BOIL = "Boil";
	private final String OFF = "Off";
	private boolean isBoiling = false;

	public KettleClient() {
		super();
		serviceType = "kettle._udp.local.";
		ui = new KettleUI(this);
		name = "Kettle";
	}

	/**
	 * Sends a request to the service to fill and boil the kettle
	 */
	public void boil() {
		if (!isBoiling) {
			String a = sendMessage(BOIL);
			if (a.equals(OK)) {
				isBoiling = true;
				ui.updateArea("Kettle is boiling");
			}
		} else {
			ui.updateArea("kettle is already Boiling");
		}
	}

	public void off() {
		String a = sendMessage(OFF);
		if (a.equals(OK)) {
			ui.updateArea("Kettle is off");
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new KettleUI(this);
		isBoiling = false;
	}

	@Override
	public void updatePoll(String msg) {
		if (msg.equals("kettle is 100% boiled")) {
			isBoiling = false;
		}
	}
}
