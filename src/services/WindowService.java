package services;

import serviceui.ServiceUI;

public class WindowService extends Service {
	private boolean closed = false;

	public WindowService(String name) {
		super(name, "window._udp.local.");
		ui = new ServiceUI(this, name);
	}

	@Override
	public void performAction(String a) {
		if (a.equals("status")) {
			sendBack(getStatus());
		} else if (a.equals("close")) {
			if (!closed) {
				openclose();
			}
		}
		if (a.equals("open")) {
			if (closed) {
				openclose();
			}
		}
	}

	private void openclose() {
		closed = !closed;
	}

	@Override
	public String getStatus() {
		return "Window: " + SERVICE_NAME + (closed ? " Is closed" : " Is Open");
	}
}
