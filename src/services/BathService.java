/*
 * 
 */
package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * The Class BathService.
 */
public class BathService extends Service {

	/** The timer. */
	private Timer timer;

	/** The percent full. */
	private int percentFull;

	boolean filling = false;

	boolean draining = false;

	/**
	 * Bath Service constructor.
	 * 
	 * @param name
	 *            the name
	 */
	public BathService(String name) {
		super(name, "bath._udp.local.");
		timer = new Timer();
		percentFull = 0;
		status = "Bath Empty";
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
		} else if (a.equals("Fill")) {
			timer = new Timer();
			timer.schedule(new RemindTask(), 0, 1 * 1000);
			sendBack("OK");
			ui.updateArea("Bath is filling");
		} else if (a.equals("Drain")) {
			status = "Draining Bath";
			timer.cancel();
			sendBack("OK");
			ui.updateArea("Bath is Draining");
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
			if (percentFull < 100) {
				percentFull += 10;
				status = "Bath is " + percentFull + "% full";
			} else {
				status = "Bath is full";
			}
		}
	}

	@Override
	public String getStatus() {
		return status;
	}

	public static void main(String[] args) {
		new BathService("Dominic-Ensuite");
	}
}
