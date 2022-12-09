/*
 * 
 */
package services;

import serviceui.SpeakerUI;

/**
 * The Class SpeakersService.
 */
public class SpeakersService extends Service {

	private int length = 0;
	private final SpeakerUI ui;

	/**
	 * Instantiates a new speakers service.
	 * 
	 * @param name
	 *            the name
	 */
	public SpeakersService(String name, String location) {
		super(name, "speaker._udp.local.", "Location=" + location);
		ui = new SpeakerUI(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see services.Service#performAction(java.lang.String)
	 */
	@Override
	public void performAction(String a) {
		if (a.length() == 1) {
			if (length % 10 == 0) {
				ui.updateArea("update");
			} else {
				ui.updateArea("update");
			}

		} else if (a.equals("Stop")) {
			length = 0;
			ui.updateArea("Stopped");
		} else if (a.equals("Paused")) {
		} else if (a.equals("Resume")) {
		} else {
			String[] songDetails = a.split("-");
			if (songDetails.length == 3) {
				ui.updateArea("Name: " + songDetails[0]);
				ui.updateArea("Artist: " + songDetails[1]);
				ui.updateArea("Album: " + songDetails[2]);
			} else {
				ui.updateArea("Unknown song format");
			}
		}
	}

	@Override
	public String getStatus() {
		return "Operational";
	}

	public static void main(String[] args) {
		new SpeakersService("s2.speaker._udp.local.", "kitchen");

	}

}