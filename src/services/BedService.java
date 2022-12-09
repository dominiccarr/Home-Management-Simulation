package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * The Class BedService.
 */
public class BedService extends Service {

	/** The timer. */
	private final Timer timer;

	/** The percent hot. */
	private int percentHot;

	/**
	 * Bed Service Constructor.
	 * 
	 * @param name
	 *            the name
	 */
	public BedService(String name) {
		super(name, "bed._udp.local.");
		timer = new Timer();
		percentHot = 0;
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
		} else if (a.equals("Warm")) {
			timer.schedule(new RemindTask(), 0, 2000);
			sendBack("OK");
			ui.updateArea("Warming Bed");
		} else {
			sendBack(BAD_COMMAND + " - " + a);
		}
	}

	/**
	 * The Class RemindTask.
	 */
	class RemindTask extends TimerTask {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			if (percentHot < 100) {
				percentHot += 10;
			}
		}
	}

	@Override
	public String getStatus() {
		return "Bed is " + percentHot + "% warmed.";
	}

	public static void main(String[] args) {
		new BedService("Dominic's");
	}
}