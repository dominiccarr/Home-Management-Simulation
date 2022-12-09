package services;

import serviceui.ServiceUI;

/**
 * The Class HeatingService.
 */
public class HeatingService extends Service {

	private int temp;

	/**
	 * Heating Service Constructor.
	 * 
	 * @param name
	 *            the name
	 */
	public HeatingService(String name, int temp) {
		super(name, "heating._udp.local.");
		this.temp = temp;
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
		} else if (a.startsWith("raise-")) {
			String[] s = a.split("-");
			temp += Integer.valueOf(s[1]);
			sendBack("OK-" + s[1]);
			ui.updateArea("Raising Temperture by " + s[1] + " degrees.");
		} else if (a.startsWith("lower-")) {
			String[] s = a.split("-");
			temp -= Integer.valueOf(s[1]);
			sendBack("OK-" + s[1]);
			ui.updateArea("Lowering Temperture by " + s[1] + " degrees.");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	@Override
	public String getStatus() {
		return "Your house is at " + temp + " degrees";
	}

	public static void main(String[] args) {
		new HeatingService("House", 20);
	}
}
