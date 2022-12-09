package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

public class KettleService extends Service {

	private final Timer timer;
	private int percentboiled = 0;

	public KettleService(String name) {
		super(name, "kettle._udp.local.");
		status = "Kettle ready";
		timer = new Timer();
		ui = new ServiceUI(this, name);
	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("Boil")) {
			timer.schedule(new BoilTask(), 0, 2000);
			sendBack("OK");
			ui.updateArea("Kettle now Boiling");
		} else {
			sendBack(BAD_COMMAND);
		}
	}

	/**
	 * The Class RemindTask.
	 */
	class BoilTask extends TimerTask {
		@Override
		public void run() {
			if (percentboiled < 100) {
				percentboiled += 10;
				status = "kettle is " + percentboiled + "% boiled";
			} else {
				status = "kettle is boiled";
			}
		}
	}

	@Override
	public String getStatus() {
		return status;
	}

	public static void main(String[] args) {
		new KettleService("Kitchen-kettle");
	}

}
