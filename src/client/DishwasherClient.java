package client;

import clientui.DishwasherUI;

/**
 * 
 * Dishwasher Client.
 * 
 * @author rhys
 */
public class DishwasherClient extends Client {

	/**
	 * 
	 * Dishwasher Client Constructor.
	 */
	public DishwasherClient() {
		super();
		serviceType = "Dishwasher._udp.local.";
		ui = new DishwasherUI(this);
		name = "Dishwasher";
	}

	public void wash(String t, String c) {
		String a = sendMessage("start_wash-" + t + "-" + c);
		if (a.equals("OK")) {
			ui.updateArea("Wash Started");
		}
	}

	public void cancel() {
		String a = sendMessage("cancel");
		if (a.equals("OK")) {
			ui.updateArea("Wash Cancelled");
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new DishwasherUI(this);
	}

}
