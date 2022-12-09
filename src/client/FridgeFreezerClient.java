package client;

import clientui.FridgeUI;

/**
 * Fridge Client
 * 
 * @author dominic
 * 
 */
public class FridgeFreezerClient extends Client {

	private final String GET_CONTENTS = "getContents";
	private final String GET_LIST = "getList";

	public FridgeFreezerClient() {
		super();
		serviceType = "fridge._udp.local.";
		ui = new FridgeUI(this);
		name = "Fridge";
	}

	/**
	 * Sends a request to the server to get the contents of the fridge + freezer
	 */
	public void getContents() {
		String a = sendMessage(GET_CONTENTS);
		String[] items = a.replaceAll("\\[|\\]", "").split(", ");
		for (String s : items) {
			ui.updateArea(s);
		}
	}

	/**
	 * sends a request to the server to get a list of products needed
	 */
	public void getList() {
		String a = sendMessage(GET_LIST);
		String[] items = a.split("-");
		for (String s : items) {
			ui.updateArea(s);
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new FridgeUI(this);
	}
}