package client;

import clientui.BathUI;

/**
 * Bath Client.
 * 
 * @author dominic
 */
public class BathClient extends Client {

	private final String FILL = "Fill";
	private final String EMPTY = "Drain";

	/**
	 * Bath Client Constructor.
	 */
	public BathClient() {
		super();
		serviceType = "bath._udp.local.";
		ui = new BathUI(this);
		name = "Baths";
	}

	/**
	 * Fill.
	 */
	public void fill() {
		String a = sendMessage(FILL);
		if (a.equals(OK)) {
			ui.updateArea("Bath is Filling");
		} else if (a.equals(BAD_COMMAND)) {
			ui.updateArea("You issued an invalid command");
		}
	}

	/**
	 * Drain.
	 */
	public void Drain() {
		String a = sendMessage(EMPTY);
		if (a.equals(OK)) {
			ui.updateArea("Bath is draining");
		}
	}

	@Override
	public void disable() {
		super.disable();
		ui = new BathUI(this);
	}
}