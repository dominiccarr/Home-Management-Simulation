package services;

import serviceui.ServiceUI;

public class DoorService extends Service {
	private boolean locked = false;

	public DoorService(String name) {
		super(name, "door._udp.local.");
		ui = new ServiceUI(this, name);
	}

	@Override
	public void performAction(String a) {
		if (a.equals("status")) {
			sendBack(getStatus());
		} else if (a.equals("lock")) {
			if (!locked) {
				change();
			}
		}
		if (a.equals("unlock")) {
			if (locked) {
				change();
			}
		}
	}

	private void change() {
		locked = !locked;
	}

	@Override
	public String getStatus() {
		return "Door: "
				+ (locked ? SERVICE_NAME + " is locked" : SERVICE_NAME
						+ " is open");
	}
}
