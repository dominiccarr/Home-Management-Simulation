package client;

import clientui.ToasterUI;

/**
 * Toaster Client.
 * 
 */
public class ToasterClient extends Client {

	private static final String START_TOASTING = "Start Toasting";
	private boolean toasting = false;

	public ToasterClient() {
		super();
		serviceType = "toaster._udp.local.";
		ui = new ToasterUI(this);
		name = "Toaster";
	}

	@Override
	public void setUp(String server, int port) {
		super.setUp(server, port);
	}

	public void adjust(int i) {
		String a = sendMessage("level-" + i);
		if (a.equals(OK)) {
			((ToasterUI) ui).adjust(i);
		}
	}

	public void startToasting() {
		if (!toasting) {
			String a = sendMessage(START_TOASTING);
			if (a.equals("OK")) {
				toasting = true;
				ui.updateArea("Toasting has begun!");
			}
		} else {
			ui.updateArea("Toaster Already Active");
		}
	}

	@Override
	public void updatePoll(String msg) {
		if (msg.equals("toast 100% done")) {
			toasting = false;
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new ToasterUI(this);
	}
}
