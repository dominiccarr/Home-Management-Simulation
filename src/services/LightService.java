/*
 * 
 */
package services;

import serviceui.ServiceUI;

/**
 * The Class LightService.
 */
public class LightService extends Service {

	/**
	 * Light Service Constructor.
	 * 
	 * @param name
	 *            the name
	 * @param location
	 *            the location
	 */
	public LightService(String name) {
		super(name, "light._udp.local.");
		status = "off";
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
		} else if (a.equals("ON")) {
			status = "on";
			sendBack("OK");
			ui.updateArea("Light On");
		} else if (a.equals("OFF")) {
			status = "off";
			sendBack("OK");
			ui.updateArea("Light Off");
		} else if (a.startsWith("DIM")) {
			status = "Dimmed";
			sendBack("OK");
			ui.updateArea("Lights dimmed 10%");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	@Override
	public String getStatus() {
		return "Light is " + status;
	}

	public static void main(String[] args) {
		new LightService("Living Room Light");
	}
}