package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * 
 * The Class DishwasherService.
 */
public class DishwasherService extends Service {

	private final Timer timer;
	private int washStage;
	private String cycleType;
	private boolean initial;
	private boolean cancelled;

	/**
	 * 
	 * Dishwasher Service Constructor.
	 * 
	 * 
	 * 
	 * @param name
	 * 
	 *            the name
	 */

	public DishwasherService(String name) {
		super(name, "Dishwasher._udp.local.");
		timer = new Timer();
		washStage = 0;
		initial = true;
		cancelled = false;
		ui = new ServiceUI(this, name);
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see services.Service#performAction(java.lang.String)
	 */
	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("cancel")) {
			cancelled = true;
			sendBack("OK");
		} else if (a.startsWith("start_wash-")) {
			cancelled = false;
			initial = false;
			String[] s = a.split("-");
			cycleType = s[2];
			washStage = 0;
			timer.schedule(new RemindTask(), 0, 4000);
			ui.updateArea("Starting wash at temperature " + s[1]
					+ " and cycle type " + s[2]);
			sendBack("OK");
		} else {
			sendBack(BAD_COMMAND + " - " + a);
		}

	}

	/**
	 * 
	 * The Class RemindTask.
	 */
	class RemindTask extends TimerTask {

		/*
		 * 
		 * (non-Javadoc)
		 * 
		 * 
		 * 
		 * @see java.util.TimerTask#run()
		 */

		@Override
		public void run() {
			if (washStage < 100) {
				if (cycleType.equals("Long")) {
					washStage++;
				} else if (cycleType.equals("Medium")) {
					if (washStage + 2 > 100) {
						washStage++;
					} else
						washStage += 2;
				}

				else if (cycleType.equals("Short")) {
					if (washStage + 3 > 100) {
						washStage++;
					} else
						washStage += 3;
				}
			}
		}
	}

	@Override
	public String getStatus() {
		if (initial) {
			return ("Dishwasher is on standby");
		} else if (cancelled) {
			return "";
		} else
			return ("Wash is " + washStage + "% complete");
	}

	public static void main(String[] args) {
		new DishwasherService("Kitchen-Dishwasher");
	}
}
