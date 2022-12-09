package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * 
 * The Class OvenService.
 */
public class OvenService extends Service {

	private final Timer timer;
	private int currentTemp;
	private int targetTemp;
	private boolean isOn;
	private boolean initial;

	/**
	 * 
	 * Oven Service Constructor.
	 * 
	 * @param name
	 * 
	 *            the name
	 */
	public OvenService(String name) {
		super(name, "Oven._udp.local.");
		timer = new Timer();
		currentTemp = 0;
		isOn = false;
		initial = true;
		ui = new ServiceUI(this, name);
	}

	@Override
	public void performAction(String a) {
		if (a.equals("get_status")) {
			sendBack(getStatus());
		} else if (a.equals("power")) {
			if (initial) {
				initial = false;
			}
			if (!isOn) {
				sendBack("OK");
				ui.updateArea("oven is powering on");
				isOn = true;
			} else {
				sendBack("NO");
				ui.updateArea("oven is powering off");
				isOn = false;
			}
		} else if (a.startsWith("change_temp-")) {
			String[] s = a.split("-");
			targetTemp = Integer.valueOf(s[1]);
			sendBack("OK");
			ui.updateArea("Setting temperature to " + targetTemp);
			timer.schedule(new TempTask(), 0, 1000);
		} else {
			sendBack(BAD_COMMAND + " - " + a);
		}

	}

	/**
	 * 
	 * The Class RemindTask.
	 */
	class TempTask extends TimerTask {

		@Override
		public void run() {
			if (currentTemp < targetTemp) {
				currentTemp++;
			} else if (currentTemp > targetTemp) {
				currentTemp--;
			}
		}
	}

	@Override
	public String getStatus() {
		if (initial) {
			return "Oven is on standby";
		} else
			return ("Oven temperature is currently " + currentTemp + " degrees");
	}

	public static void main(String[] args) {
		new OvenService("Kitchen-Oven");
	}
}